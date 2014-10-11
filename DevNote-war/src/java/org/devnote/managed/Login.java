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

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.devnote.ejb.UserFacadeLocal;
import org.devnote.entries.User;

/**
 * Login managed bean.
 * @author Stanislav Nepochatov
 */
@Named(value = "login")
@RequestScoped
public class Login {
    
    /**
     * User ejb instance.
     */
    @EJB
    private UserFacadeLocal userBean;
    
    @ManagedProperty(value="#{userSession}")
    @Inject
    private UserSession session;
    
    /**
     * User empty instance.
     */
    private User currentUser = new User();

    /**
     * Default constructor.
     */
    public Login() {
    }
    
    /**
     * Login action. This action checks given login and password and perform user login.
     */
    public String login() {
        User findedUser = userBean.findByLogin(currentUser.getLogin());
        if (findedUser != null && currentUser.getLogin().equals(findedUser.getLogin()) && currentUser.getPassw().equals(findedUser.getPassw())) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", findedUser.getLogin());
            if (findedUser.getIsAdmin()) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isAdmin", "true");
            }
            userBean.performLogin(findedUser);
            session.setCurrentUser(currentUser);
            return "success";
        } else {
            FacesContext.getCurrentInstance().addMessage("login-error", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login error, check your login and password!", ""));
            return "failed";
        }
    }
    
    /**
     * Logout action. Invalidate session.
     */
    public void logout() {
        //TODO make logout action.
    }

    /**
     * Get current user.
     * @return user instance;
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
