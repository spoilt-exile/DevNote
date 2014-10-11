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

import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.devnote.entries.User;

/**
 * User facade.
 * @author Stanislav Nepochatov
 */
@Stateless
public class UserFacade extends AbstractFacade<User> implements UserFacadeLocal {
    
    /**
     * Entity manager.
     */
    @PersistenceContext(unitName = "DevNote-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Default constructor.
     */
    public UserFacade() {
        super(User.class);
    }

    @Override
    public User findByLogin(String login) {
        EntityManager em = this.getEntityManager();
        TypedQuery<User> tr = em.createNamedQuery("User.findByLogin", User.class);
        tr.setParameter("login", login);
        try {
            return tr.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public void performLogin(User logined) {
        logined.setLogDate(new Date());
        this.edit(logined);
    }
    
}
