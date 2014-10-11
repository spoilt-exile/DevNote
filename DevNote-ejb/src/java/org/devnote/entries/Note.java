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
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Note entity,<br/>
 * <br/>
 * Note contains general data about user document excepts a text of note. 
 * Text of the note containing in Version entity,
 * @see Version
 * @author Stanislav Nepochatov
 */
@Entity
@Table(name = "Note")
@NamedQueries({
    @NamedQuery(name = "Note.findAll", query = "SELECT n FROM Note n"),
    @NamedQuery(name = "Note.findById", query = "SELECT n FROM Note n WHERE n.id = :id"),
    @NamedQuery(name = "Note.findByLastVersionDate", query = "SELECT n FROM Note n WHERE n.lastVersionDate = :lastVersionDate")})
public class Note implements Serializable {
    
    
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
     * Header of the note.
     */
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "header")
    private String header;
    
    /**
     * Date of last note modification.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "last_version_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastVersionDate;
    
    /**
     * Directory which contain this note.
     */
    @JoinColumn(name = "dir_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Directory dirId;
    
    /**
     * User which owns this note.
     */
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User userId;
    
    /**
     * Last version of this note.
     */
    @JoinColumn(name = "last_version_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Version lastVersionId;
    
    /**
     * List of all notes version.
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "noteId")
    private List<Version> versionList;

    /**
     * Default constructor.
     */
    public Note() {
    }

    /**
     * Simple parametrick constructor.
     * @param id entity id;
     */
    public Note(Integer id) {
        this.id = id;
    }

    /**
     * Parametrick constructor.
     * @param id entity id;
     * @param header note header message;
     */
    public Note(Integer id, String header) {
        this.id = id;
        this.header = header;
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
     * Get header message.
     * @return string with header;
     */
    public String getHeader() {
        return header;
    }

    /**
     * Set header message.
     * @param header message to set;
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * Get last modification date.
     * @return date object;
     */
    public Date getLastVersionDate() {
        return lastVersionDate;
    }

    /**
     * Set last modification date.
     * @param lastVersionDate date object to set;
     */
    public void setLastVersionDate(Date lastVersionDate) {
        this.lastVersionDate = lastVersionDate;
    }

    /**
     * Get parent directory.
     * @return parent directory object;
     */
    public Directory getDirId() {
        return dirId;
    }

    /**
     * Set parent directory (move note).
     * @param dirId parent directory to set;
     */
    public void setDirId(Directory dirId) {
        this.dirId = dirId;
    }

    /**
     * Get user which owns this note.
     * @return user entity;
     */
    public User getUserId() {
        return userId;
    }

    /**
     * Set user which owns this note (change ownership).<br/>
     * <b>WARNING!</b> To complete changing ownership you should change 
     * ownership of all versions as well.
     * @param userId user entry to set;
     */
    public void setUserId(User userId) {
        this.userId = userId;
    }

    /**
     * Get last version of this message.
     * @return version entity.
     */
    public Version getLastVersionId() {
        return lastVersionId;
    }

    /**
     * Set last version of this message (message update).
     * @param lastVersionId version entity to set;
     */
    public void setLastVersionId(Version lastVersionId) {
        this.lastVersionId = lastVersionId;
    }

    /**
     * Get all versions of this message.
     * @return list with versions;
     */
    public List<Version> getVersionList() {
        return versionList;
    }

    /**
     * Set version list of this message.
     * @param versionList list with versions to set;
     */
    public void setVersionList(List<Version> versionList) {
        this.versionList = versionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Note)) {
            return false;
        }
        Note other = (Note) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.devnote.entries.Note[ id=" + id + " ]";
    }
    
}
