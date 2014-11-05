/*
 * Copyright (C) 2014 spoilt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.devnote.managed;

import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.devnote.ejb.DirectoryFacadeLocal;
import org.devnote.ejb.NoteFacadeLocal;
import org.devnote.ejb.VersionFacadeLocal;
import org.devnote.entries.Directory;
import org.devnote.entries.Note;
import org.devnote.entries.Version;
import org.devnote.service.Hash;
import org.devnote.wrappers.primefaces.DirectoryWrapper;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;

/**
 * Event handler bean.
 * @author Stanislav Nepochatov
 */
@Named(value = "eventHandler")
@SessionScoped
public class EventHandler implements Serializable {
    
    /**
     * Notes ejb facade.
     */
    @EJB
    private NoteFacadeLocal noteBean;
    
    /**
     * Versions ejb facade.
     */
    @EJB
    private VersionFacadeLocal versionBean;
    
    /**
     * Directory ejb facade.
     */
    @EJB
    private DirectoryFacadeLocal directoryBean;
    
    /**
     * User session managed bean.
     */
    @ManagedProperty(value="#{userSession}")
    @Inject
    private UserSession session;
    
    /**
     * Current selected directory.
     */
    private DirectoryWrapper currentDir;
    
    /**
     * Current selected note.
     */
    private Note currentNote;
    
    /**
     * Current browsed version of note.
     */
    private Version currentVersion;
    
    /**
     * Current browsed version id.
     */
    private int currentVersionId;
    
    /**
     * Editor form visibility flag.
     */
    private Boolean enableEdit = false;
    
    /**
     * Current edit header of note.<br/>
     * If this is new note, header is empty. 
     * If there is exists note, its header will be used.
     */
    private String currentEditHeader;
    
    /**
     * Current note edit text.<br/>
     * If this is new note, text is empty.
     * If there is note, last version text will taken.
     */
    private String currentEditText;

    /**
     * Get current directory wrapper.
     * @return the currentDir
     */
    public DirectoryWrapper getCurrentDir() {
        return currentDir;
    }

    /**
     * Current directory setter.
     * @param currentDir the directory to set;
     */
    public void setCurrentDir(DirectoryWrapper currentDir) {
        this.currentDir = currentDir;
    }
    
    /**
     * Get current note.
     * @return the currentNote
     */
    public Note getCurrentNote() {
        return currentNote;
    }

    /**
     * Current note setter.
     * @param currentNote the note to set;
     */
    public void setCurrentNote(Note currentNote) {
        this.currentNote = currentNote;
    }
    
    /**
     * Get current version.
     * @return the currentVersion
     */
    public Version getCurrentVersion() {
        return currentVersion;
    }
    
    /**
     * Current version to set.
     * @param currentVersion the version to set;
     */
    public void setCurrentVersion(Version currentVersion) {
        this.currentVersion = currentVersion;
    }
    
    /**
     * Get editor active flag.
     * @return true: if user edit note / false: if user read note;
     */
    public Boolean getEnableEdit() {
        return enableEdit;
    }

    /**
     * Set editor active flag.
     * @param enableEdit flag to set;
     */
    public void setEnableEdit(Boolean enableEdit) {
        this.enableEdit = enableEdit;
    }
    
    /**
     * Get current version id.
     * @return id of current browsed version;
     */
    public int getCurrentVersionId() {
        return currentVersionId;
    }

    /**
     * Set current version id.
     * @param currentVersionId id to set;
     */
    public void setCurrentVersionId(int currentVersionId) {
        this.currentVersionId = currentVersionId;
    }
    
    /**
     * Directory node selection event listener method.
     * @param e selection from primefaces;
     */
    public void onDirSelected(NodeSelectEvent e) {
        this.enableEdit = false;
        this.currentDir = ((DirectoryWrapper) e.getTreeNode());
        Directory newWrapped = directoryBean.find(currentDir.getWrapped().getId());
        this.currentDir.setWrapped(newWrapped);
        this.currentNote = null;
        this.currentVersion = null;
    }
    
    /**
     * Note entry selection event listener method.
     * @param e selection from primefaces;
     */
    public void onNoteSelected(SelectEvent e) {
        this.enableEdit = false;
        Note eventNote = (Note) e.getObject();
        this.currentNote = noteBean.find(eventNote.getId());
        this.currentVersion = versionBean.find(currentNote.getVersionList().get(currentNote.getVersionList().size() - 1).getId());
        this.currentVersionId = this.currentVersion.getId();
    }
    
    /**
     * Version entry selection event listener method.
     * @param e selection from primefaces;
     */
    public void onVersionSelected(ValueChangeEvent e) {
        this.currentVersion = versionBean.find(this.currentVersionId);
    }
    
    /**
     * Get text for note display.
     * @return text of current selected version;
     */
    public String getCurrentText() {
        if (this.currentVersion != null) {
            return this.currentVersion.getNoteText();
        } else {
            return "Choose note.";
        }
    }
    
    /**
     * Get text for note edit.
     * @return text of current selected version;
     */
    public String getCurrentEditText() {
        if (this.currentVersion != null) {
            return versionBean.find(this.currentNote.getLastVersionId().getId()).getNoteText();
        } else {
            return "";
        }
    }
    
    /**
     * Set edited text to temp variable.
     * @param currentEditText user edited text;
     */
    public void setCurrentEditText(String currentEditText) {
        this.currentEditText = currentEditText;
    }
    
    /**
     * @return the currentEditHeader
     */
    public String getCurrentEditHeader() {
        if (this.currentNote != null) {
            return noteBean.find(this.currentNote.getId()).getHeader();
        } else {
            return "";
        }
    }

    /**
     * @param currentEditHeader the currentEditHeader to set
     */
    public void setCurrentEditHeader(String currentEditHeader) {
        this.currentEditHeader = currentEditHeader;
    }
    
    /**
     * Save new version of note or create new note with edited version.
     */
    public void saveNewVersion() {
        Version newVersion = new Version();
        newVersion.setNoteText(currentEditText);
        newVersion.setSaveDate(new Date());
        newVersion.setHash(Hash.getHash(currentEditText));
        newVersion.setUserId(session.getCurrentUser());
        newVersion.setNoteId(currentNote);
        versionBean.create(newVersion);
        
        currentNote.setHeader(currentEditHeader);
        currentNote.setLastVersionId(newVersion);
        currentNote.getVersionList().add(newVersion);
        currentNote.setLastVersionDate(newVersion.getSaveDate());
        noteBean.edit(currentNote);
        
        Directory newWrapped = directoryBean.find(currentDir.getWrapped().getId());
        this.currentDir.setWrapped(newWrapped);
        
        this.currentVersion = newVersion;
        this.currentVersionId = newVersion.getId();
        this.enableEdit = false;
    }
    
    /**
     * Cancel new version creating, back to view mode.
     */
    public void dropNewVersion() {
        this.currentEditText = null;
        this.enableEdit = false;
    }
}
