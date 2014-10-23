/*
 * Copyright (C) 2014 Stanislav Nepochatov
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

package org.devnote.entries;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Directory entity.<br/>
 * <br/>
 * Directory contains notes and may contain other directories, 
 * Directory linked to the single user.
 * @author Stanislav Nepochatov
 * @since DevNote 0.1
 */
@Entity
@Table(name = "Directory")
@NamedQueries({
    @NamedQuery(name = "Directory.findAll", query = "SELECT d FROM Directory d"),
    @NamedQuery(name = "Directory.findAllSortPath", query = "SELECT d FROM Directory d ORDER BY d.path"),
    @NamedQuery(name = "Directory.findById", query = "SELECT d FROM Directory d WHERE d.id = :id")})
public class Directory implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Entity id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    /**
     * Path of this directory.<br/>
     * <br/>
     * By 'path' means a name of directory including it's parents for root. 
     * For example: Test.Inner.Deep is a Deep directory which has Inner parent, and Inner has Test parent. 
     * Single dot is a separator for directory folding. 
     * There is also a root directory which has no name (path = ""),
     */
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "path")
    private String path;
    
    /**
     * List of notes related to this directory.
     * @see Note
     */
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "dirId")
    private List<Note> noteList;
    
    /**
     * User which is an owner of this directory.
     */
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;

    /**
     * Default constructor.
     */
    public Directory() {
    }

    /**
     * Simple parametrick constructor.
     * @param id entity id;
     */
    public Directory(Integer id) {
        this.id = id;
    }

    /**
     * Parametrick constructor (with path).
     * @param id entity id;
     * @param path directory path;
     */
    public Directory(Integer id, String path) {
        this.id = id;
        this.path = path;
    }

    /**
     * Get entity id.
     * @return id entity id;
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set entity id.
     * @param id entity id to set;
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get path of directory.
     * @return string with path;
     */
    public String getPath() {
        return path;
    }

    /**
     * Set path of directory;
     * @param path directory path to set;
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Get all note in this directory.
     * @return list with notes;
     * @see Note
     */
    public List<Note> getNoteList() {
        return noteList;
    }

    /**
     * Set list of notes to this directory.
     * @param noteCollection list of notes to set;
     * @see Note
     */
    public void setNoteList(List<Note> noteCollection) {
        this.noteList = noteCollection;
    }

    /**
     * Get user which own this directory.
     * @return user entity;
     */
    public User getUserId() {
        return userId;
    }

    /**
     * Set user which own this directory (change ownership).
     * @param userId user entity to set;
     */
    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Directory)) {
            return false;
        }
        Directory other = (Directory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.devnote.entries.Directory[ id=" + id + " ]";
    }
    
    /**
     * Get full name of parent directory.<br/>
     * <b>Covered by unit test.</b>
     * @return parent name or empty string if parent is root;
     */
    public String getParentFullPath() {
        int dotIndex = -1;
        int beginIndex = 0;
        
        for (int runIndex = 0; runIndex < path.length(); runIndex++) {
            char curr = path.charAt(runIndex);
            if (curr == '.') {
                dotIndex = runIndex;
            }
        }
        
        if (dotIndex > 0) {
            return path.substring(beginIndex, dotIndex);
        } else {
            return "";
        }
    }
    
    /**
     * Get short name of this directory (without parent path).<br/>
     * <b>Covered by unit test.</b>
     * @return short directory name, for example for 'Test1.Test2.Test3', this 
     * will return only 'Test3'
     */
    public String getShortName() {
        int dotIndex = -1;
        
        for (int runIndex = 0; runIndex < path.length(); runIndex++) {
            char curr = path.charAt(runIndex);
            if (curr == '.') {
                dotIndex = runIndex;
            }
        }
        
        if (dotIndex > 0) {
            return path.substring(dotIndex + 1);
        } else {
            return path;
        }
    }
}
