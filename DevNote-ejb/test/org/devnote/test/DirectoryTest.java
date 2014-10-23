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

package org.devnote.test;

import org.devnote.entries.Directory;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Some test of directory class integrity.
 * @author Stanislav Nepochatov
 */
public class DirectoryTest {
    
    /**
     * Run parent getter test. This method should return path to directory 
     * except directory name (last name after dot). For example, 
     * directory with path Test1.Test2.Test3 has name Test3 and path Test1.Test2 to it. 
     * Method should return Test1.Test2 string.
     */
    @Test
    public void getParentFullPathTest() {
        String testDir1 = "Test.Test1.Test2";
        String testDir1Res = "Test.Test1";
        Directory testDir1Ref = new Directory();
        testDir1Ref.setPath(testDir1);
        assertEquals(testDir1Res, testDir1Ref.getParentFullPath());
        
        String testDir2 = "Заметки.IT.Смартфоны.Samsung.S3";
        String testDir2Res = "Заметки.IT.Смартфоны.Samsung";
        Directory testDir2Ref = new Directory();
        testDir2Ref.setPath(testDir2);
        assertEquals(testDir2Res, testDir2Ref.getParentFullPath());
        
        String testDir3 = "Разное";
        String testDir3Res = "";
        Directory testDir3Ref = new Directory();
        testDir3Ref.setPath(testDir3);
        assertEquals(testDir3Res, testDir3Ref.getParentFullPath());
    }
    
    /**
     * Run short name getter test. This method should return name of directory 
     * except path to it from result. For example, with directory path Test1.Test2.Test3 this 
     * method should return only Test3 string.
     */
    @Test
    public void getShortNameTest() {
        String testDir1 = "Test.Test1.Test2";
        String testDir1Res = "Test2";
        Directory testDir1Ref = new Directory();
        testDir1Ref.setPath(testDir1);
        assertEquals(testDir1Res, testDir1Ref.getShortName());
        
        String testDir2 = "Заметки.IT.Смартфоны.Samsung.S3";
        String testDir2Res = "S3";
        Directory testDir2Ref = new Directory();
        testDir2Ref.setPath(testDir2);
        assertEquals(testDir2Res, testDir2Ref.getShortName());
        
        String testDir3 = "Разное";
        String testDir3Res = "Разное";
        Directory testDir3Ref = new Directory();
        testDir3Ref.setPath(testDir3);
        assertEquals(testDir3Res, testDir3Ref.getShortName());
    }
    
}
