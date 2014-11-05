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

package org.devnote.wrappers.primefaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.devnote.entries.Directory;
import org.primefaces.model.TreeNode;

/**
 * Wrapper class to represent directory as primefaces treenode.
 * @see org.devnote.entries.Directory
 * @author Stanislav Nepochatov
 */
public class DirectoryWrapper implements TreeNode, Serializable {
    
    /**
     * Type of node.
     */
    public static final String DEFAULT_TYPE = "default";
    
    /**
     * Wrapped directory instance.
     * @see org.devnote.entries.Directory
     */
    private Directory wrapped;
    
    /**
     * Parent of this node.
     */
    private final TreeNode parent;
    
    /**
     * List of childrens.
     */
    private final List<TreeNode> childrens;
    
    /**
     * Expanded flag.
     */
    private Boolean expanded = false;
    
    /**
     * Selected flag.
     */
    private Boolean selected = false;
    
    /**
     * Some row key ???
     */
    private String rowKey;
    
    /**
     * Root directory wrapper constructor.
     */
    public DirectoryWrapper() {
        wrapped = new Directory();
        wrapped.setPath("Root");
        parent = null;
        childrens = new ArrayList<>();
    }
    
    /**
     * Parametrick constructor.
     * @param current current wrapped directory.
     * @param inParent parent wrapper.
     */
    public DirectoryWrapper(Directory current, TreeNode inParent) {
        wrapped = current;
        parent = inParent;
        childrens = new ArrayList<>();
    }

    @Override
    public String getType() {
        return DEFAULT_TYPE;
    }

    @Override
    public Object getData() {
        return this.wrapped.getShortName();
    }

    @Override
    public List<TreeNode> getChildren() {
        return this.childrens;
    }

    @Override
    public TreeNode getParent() {
        return this.parent;
    }

    @Override
    public void setParent(TreeNode tn) {
        //Do nothing.
    }

    @Override
    public boolean isExpanded() {
        return this.expanded;
    }

    @Override
    public void setExpanded(boolean bln) {
        this.expanded = bln;
    }

    @Override
    public int getChildCount() {
        return this.childrens.size();
    }

    @Override
    public boolean isLeaf() {
        return this.childrens.isEmpty();
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean bln) {
        this.selected = bln;
    }

    @Override
    public boolean isSelectable() {
        return true;
    }

    @Override
    public void setSelectable(boolean bln) {
        //Do nothing
    }

    @Override
    public boolean isPartialSelected() {
        return false;
    }

    @Override
    public void setPartialSelected(boolean bln) {
        //Do nothing
    }

    @Override
    public void setRowKey(String string) {
        this.rowKey = string;
    }

    @Override
    public String getRowKey() {
        return this.rowKey;
    }
    
    /**
     * Get wrapped directory.
     * @return directory inside wrapper;
     */
    public Directory getWrapped() {
        return this.wrapped;
    }
    
    /**
     * Set wrapped directory.
     * @param wrapped refreshed directory;
     */
    public void setWrapped(Directory wrapped) {
        this.wrapped = wrapped;
    }
    
    @Override
    public String toString() {
        return (String) this.getData();
    }
    
}
