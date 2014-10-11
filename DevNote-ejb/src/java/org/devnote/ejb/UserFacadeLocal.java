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

package org.devnote.ejb;

import java.util.List;
import javax.ejb.Local;
import org.devnote.entries.User;

/**
 * User local interface.
 * @author Stanislav Nepochatov
 */
@Local
public interface UserFacadeLocal {
    
    // Standard methods

    void create(User user);

    void edit(User user);

    void remove(User user);

    User find(Object id);

    List<User> findAll();

    List<User> findRange(int[] range);

    int count();
    
    // My methods
    
    /**
     * Find user by login.
     * @param login user name to search;
     * @return finded user;
     */
    public User findByLogin(String login);
    
    /**
     * Update user infromation during login.
     * @param logined user which enters to the system;
     */
    public void performLogin(User logined);
    
}
