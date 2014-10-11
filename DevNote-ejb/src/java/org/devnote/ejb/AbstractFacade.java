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
import javax.persistence.EntityManager;

/**
 * Abstract facade for EJB.
 * @author Stanislav Nepochatov
 */
public abstract class AbstractFacade<T> {
    
    /**
     * Facade type pointer.
     */
    private Class<T> entityClass;

    /**
     * Facade constructor.
     * @param entityClass type to apply;
     */
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Entity manager variable,
     * @return full functional entity manager;
     */
    protected abstract EntityManager getEntityManager();

    /**
     * Create entity in database (INSERT).
     * @param entity object to create;
     */
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    /**
     * Edit entity in database (UPDATE).
     * @param entity object ot edit;
     */
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    /**
     * Remove entity from database (DROP).
     * @param entity object ot delete;
     */
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    /**
     * Find entity in database;
     * @param id unique id of entity;
     * @return finded entity with specified id;
     */
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    /**
     * Find all entities of this type.
     * @return list with all entities with this type;
     */
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     * Find range of entityes of this type.
     * @param range int array with range;
     * @return list with range of enteties;
     */
    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    /**
     * Return count of entities of this type.
     * @return integer count;
     */
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
