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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.devnote.service.Hash;

/**
 * User entity.<br/>
 * <br/>
 * This entity contains general information about system user.
 * @author Stanislav Nepochatov
 */
@Entity
@Table(name = "User")
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name = "User.findByLogin", query = "SELECT u FROM User u WHERE u.login = :login"),
    @NamedQuery(name = "User.findByPassw", query = "SELECT u FROM User u WHERE u.passw = :passw"),
    @NamedQuery(name = "User.findByCrtDate", query = "SELECT u FROM User u WHERE u.crtDate = :crtDate"),
    @NamedQuery(name = "User.findByLogDate", query = "SELECT u FROM User u WHERE u.logDate = :logDate"),
    @NamedQuery(name = "User.findByIsAdmin", query = "SELECT u FROM User u WHERE u.isAdmin = :isAdmin"),
    @NamedQuery(name = "User.findByIsEnabled", query = "SELECT u FROM User u WHERE u.isEnabled = :isEnabled")})
public class User implements Serializable {
    
    
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
     * Login of user.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "login")
    private String login;
    
    /**
     * User password hash (simple MD5).
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "passw")
    private String passw;
    
    /**
     * Date of user creation.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "crt_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date crtDate;
    
    /**
     * Date of last user login.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "log_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date logDate;
    
    /**
     * Admin user flag (use with care!).
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_admin")
    private boolean isAdmin;
    
    /**
     * User enable flag (disabled user cann't login).
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_enabled")
    private boolean isEnabled;
    
    /**
     * List of all user notes.
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userId")
    private List<Note> noteList;
    
    /**
     * List of all user versions.
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userId")
    private List<Version> versionList;
    
    /**
     * List of all user directories.
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userId")
    private List<Directory> directoryList;

    /**
     * Default constructor.
     */
    public User() {
    }

    /**
     * Simple parametrick constructor.
     * @param id entity id;
     */
    public User(Integer id) {
        this.id = id;
    }

    /**
     * Parametrick constructor.
     * @param id entity id;
     * @param login user login;
     * @param passw user password hash (MD5);
     * @param crtDate date of creation;
     * @param logDate date of last login;
     * @param isAdmin admin flag;
     * @param isEnabled enable flag;
     */
    public User(Integer id, String login, String passw, Date crtDate, Date logDate, boolean isAdmin, boolean isEnabled) {
        this.id = id;
        this.login = login;
        this.passw = passw;
        this.crtDate = crtDate;
        this.logDate = logDate;
        this.isAdmin = isAdmin;
        this.isEnabled = isEnabled;
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
     * Get user login.
     * @return string with login;
     */
    public String getLogin() {
        return login;
    }

    /**
     * Set user login.
     * @param login string login to set;
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Get user password hash.
     * @return md5 string hash;
     */
    public String getPassw() {
        return passw;
    }

    /**
     * Set user password hash.
     * @param passw hash string to set;
     */
    public void setPassw(String passw) {
        this.passw = passw;
    }
    
    /**
     * Get roaw user password.
     * @return always null
     */
    public String getRawPassw() {
        return null;
    }
    
    /**
     * Set hash string of user password with specified raw password.
     * @param raw plain password string;
     */
    public void setRawPassw(String raw) {
        this.passw = Hash.getHash(raw);
    }

    /**
     * Get date of creation.
     * @return date of user creation;
     */
    public Date getCrtDate() {
        return crtDate;
    }

    /**
     * Set date of creation.
     * @param crtDate user creation date to set;
     */
    public void setCrtDate(Date crtDate) {
        this.crtDate = crtDate;
    }

    /**
     * Get last login date.
     * @return date of user last login;
     */
    public Date getLogDate() {
        return logDate;
    }

    /**
     * Set last login date.
     * @param logDate date of last login to set;
     */
    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    /**
     * Get admin flag.
     * @return true if user is administrator / false if user is regular;
     */
    public boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * Set admin flag.
     * @param isAdmin new admin flag to set;
     */
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * Get user enable flag.
     * @return true if user is enabled / false if user disabled;
     */
    public boolean getIsEnabled() {
        return isEnabled;
    }

    /**
     * Set user enable flag.
     * @param isEnabled new enable flag to set;
     */
    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
     * Get list of user notes.
     * @return list with all notes of this user;
     */
    public List<Note> getNoteList() {
        return noteList;
    }

    /**
     * Set list of user notes.
     * @param noteList new list of notes to set;
     */
    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }

    /**
     * Get list of users versions entries.
     * @return list with all versions of this user;
     */
    public List<Version> getVersionList() {
        return versionList;
    }

    /**
     * Set list of users version entries.
     * @param versionList new list of versions to set;
     */
    public void setVersionList(List<Version> versionList) {
        this.versionList = versionList;
    }

    /**
     * Get list of users directory.
     * @return list with all directories of this user;
     */
    public List<Directory> getDirectoryList() {
        return directoryList;
    }

    /**
     * Set list of users directory.
     * @param directoryList new version list to set;
     */
    public void setDirectoryList(List<Directory> directoryList) {
        this.directoryList = directoryList;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.devnote.entries.User[ id=" + id + " ]";
    }
    
}
