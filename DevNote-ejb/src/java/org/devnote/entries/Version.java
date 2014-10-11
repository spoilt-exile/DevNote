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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
/**
 * Version entity.<br/>
 * <br/>
 * Version contain single edit of note text. 
 * Every version handle whole text of note and it's metadata.
 * @author Stanislav Nepochatov
 */
@Entity
@Table(name = "Version")
@NamedQueries({
    @NamedQuery(name = "Version.findAll", query = "SELECT v FROM Version v"),
    @NamedQuery(name = "Version.findById", query = "SELECT v FROM Version v WHERE v.id = :id"),
    @NamedQuery(name = "Version.findByHash", query = "SELECT v FROM Version v WHERE v.hash = :hash"),
    @NamedQuery(name = "Version.findBySaveDate", query = "SELECT v FROM Version v WHERE v.saveDate = :saveDate")})
public class Version implements Serializable {
    
    
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
     * Hash of this version. 
     * MD5 compute checksum with whole text and timestamp.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "hash")
    private String hash;
    
    /**
     * Date of creation.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "save_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date saveDate;
    
    /**
     * Text of note version.
     */
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 16777215)
    @Column(name = "note_text")
    private String noteText;
    
    /**
     * Parent note entity.
     */
    @JoinColumn(name = "note_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Note noteId;
    
    /**
     * User which owns this version.
     */
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;

    /**
     * Default constructor.
     */
    public Version() {
    }

    /**
     * Simple parametricj constructor.
     * @param id entity id;
     */
    public Version(Integer id) {
        this.id = id;
    }

    /**
     * Full parametrick constructor.
     * @param id entity id;
     * @param hash version hash checksum;
     * @param saveDate creation date;
     * @param noteText note text;
     */
    public Version(Integer id, String hash, Date saveDate, String noteText) {
        this.id = id;
        this.hash = hash;
        this.saveDate = saveDate;
        this.noteText = noteText;
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
     * Get version hash.
     * @return md5 hash of version;
     */
    public String getHash() {
        return hash;
    }

    /**
     * Set version hash.
     * @param hash new version hash to set;
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * Get creation date.
     * @return date of version creation;
     */
    public Date getSaveDate() {
        return saveDate;
    }

    /**
     * Set creation date.
     * @param saveDate new date of creation to set;
     */
    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }

    /**
     * Get note text of this version.
     * @return string with text;
     */
    public String getNoteText() {
        return noteText;
    }

    /**
     * Set note text of this version. 
     * Usually version should accessed only for read-only after creation.
     * @param noteText new text to set;
     */
    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    /**
     * Get parent note of this version.
     * @return note which contain this version;
     */
    public Note getNoteId() {
        return noteId;
    }

    /**
     * Set parent note of this version. 
     * @param noteId new note object to set;
     */
    public void setNoteId(Note noteId) {
        this.noteId = noteId;
    }

    /**
     * Get user which owns this version.
     * @return user o0bject;
     */
    public User getUserId() {
        return userId;
    }

    /**
     * Set user which owns this version (change ownership). 
     * Usually it is nonesens, cause version doesn't work without note parent.
     * @param userId new user to set;
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Version)) {
            return false;
        }
        Version other = (Version) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.devnote.entries.Version[ id=" + id + " ]";
    }
    
}
