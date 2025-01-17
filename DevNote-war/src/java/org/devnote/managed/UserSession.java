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

package org.devnote.managed;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.devnote.entries.User;

/**
 * User session bean. Contains information about current user.
 * @author Stanislav Nepochatov
 */
@Named(value = "UserSession")
@SessionScoped
public class UserSession implements Serializable {
    
    /**
     * Current user for this session.
     */
    private User currentUser;

    /**
     * Defualt constructor.
     */
    public UserSession() {
    }

    /**
     * Get current user in session.
     * @return the current user;
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Set current user.
     * @param currentUser user to set;
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    
}
