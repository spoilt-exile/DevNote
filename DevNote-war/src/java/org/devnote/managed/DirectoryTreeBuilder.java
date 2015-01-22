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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;
import org.devnote.ejb.DirectoryFacadeLocal;
import org.devnote.entries.Directory;
import org.devnote.wrappers.primefaces.DirectoryWrapper;
import org.primefaces.model.TreeNode;

/**
 * Build tree model for primefaces with existing directory structure.
 * @author Stanislav Nepochatov
 */
@Named(value = "directoryTreeBuilder")
@SessionScoped
public class DirectoryTreeBuilder implements Serializable {
    
    /**
     * Directory facade bean.
     */
    @EJB
    private DirectoryFacadeLocal dirBean;
    
    /**
     * User session bean.
     */
    @Inject
    @ManagedProperty(value="#{userSession}")
    private UserSession session;
    
    /**
     * Tree structure root directory.
     */
    private TreeNode root;
    
    /**
     * Current selected directory.
     */
    private DirectoryWrapper selected;
    
    /**
     * Bean initialization method.
     */
    @PostConstruct
    public void init() {
        root = new DirectoryWrapper();
        
        Map<String, TreeNode> pathMap = new HashMap<>();
        
        List<Directory> sortedDirs = dirBean.findAllSortByPath(session.getCurrentUser());
        if (sortedDirs.isEmpty()) {
            System.err.println("LIST EMPTY!");
        }
        for (Directory current: sortedDirs) {
            String parentName = current.getParentFullPath();
            if (parentName.isEmpty()) {
                System.err.println("PROCESSING TO ROOT: " + current.getPath());
                TreeNode insertedToRoot = new DirectoryWrapper(current, root);
                root.getChildren().add(insertedToRoot);
                pathMap.put(current.getPath(), insertedToRoot);
                insertedToRoot.setExpanded(true);
            } else {
                if (pathMap.containsKey(current.getParentFullPath())) {
                    TreeNode parent = pathMap.get(current.getParentFullPath());
                    TreeNode insertedInside = new DirectoryWrapper(current, parent);
                    parent.getChildren().add(insertedInside);
                    pathMap.put(current.getPath(), insertedInside);
                    insertedInside.setExpanded(true);
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
    
    /**
     * Get selected directory node.
     * @return primefaces treenode;
     */
    public DirectoryWrapper getSelected() {
        return selected;
    }
    
    public void setSelected(TreeNode selected) {
        this.selected = (DirectoryWrapper) selected;
    }
}
