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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.devnote.ejb.DirectoryFacadeLocal;
import org.devnote.entries.Directory;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 * Build tree model for primefaces with existing directory structure.
 * @author Stanislav Nepochatov
 */
@Named(value = "directoryTreeBuilder")
@RequestScoped
public class DirectoryTreeBuilder {
    
    /**
     * Directory facade bean.
     */
    @EJB
    private DirectoryFacadeLocal dirBean;
    
    /**
     * Tree structure root directory.
     */
    private TreeNode root;
    
    /**
     * Bean initialization method.
     */
    @PostConstruct
    public void init() {
        root = new DefaultTreeNode("Root", null);
        
        Map<String, TreeNode> pathMap = new HashMap<>();
        
        List<Directory> sortedDirs = dirBean.findAllSortByPath();
        if (sortedDirs.isEmpty()) {
            System.err.println("LIST EMPTY!");
        }
        for (Directory current: sortedDirs) {
            String parentName = current.getParentFullPath();
            if (parentName.isEmpty()) {
                System.err.println("PROCESSING TO ROOT: " + current.getPath());
                TreeNode insertedToRoot = new DefaultTreeNode(current.getPath(), root);
                root.getChildren().add(insertedToRoot);
                pathMap.put(current.getPath(), insertedToRoot);
            } else {
                if (pathMap.containsKey(current.getParentFullPath())) {
                    TreeNode parent = pathMap.get(current.getParentFullPath());
                    TreeNode insertedInside = new DefaultTreeNode(current.getShortName(), parent);
                    parent.getChildren().add(insertedInside);
                    pathMap.put(current.getPath(), insertedInside);
                    System.err.println("PROCESSING TO: " + current.getPath() + " -> " + parent);
                }
            }
        }
    }
    
    /**
     * Root getter.
     * @return root directory with structure;
     */
    public TreeNode getRoot() {
        return root;
    }
    
}
