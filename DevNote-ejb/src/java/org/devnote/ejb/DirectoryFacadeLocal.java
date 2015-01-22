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
import org.devnote.entries.Directory;
import org.devnote.entries.User;

/**
 * Directory local interface.
 * @author Stanislav Nepochatov
 */
@Local
public interface DirectoryFacadeLocal {
    
    // Standard methods

    void create(Directory directory);

    void edit(Directory directory);

    void remove(Directory directory);

    Directory find(Object id);

    List<Directory> findAll();

    List<Directory> findRange(int[] range);

    int count();
    
    // My methods
    
    /**
     * Find directories but sorted by path. Useful for creating tree.
     * @return list of directories sorted by path;
     */
    List<Directory> findAllSortByPath(User userId);
    
}
