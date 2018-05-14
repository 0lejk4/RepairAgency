package com.gelo.model.dao.generic;

import com.gelo.model.dao.Connectible;
import com.gelo.model.exception.DatabaseException;

import java.io.Serializable;
import java.util.List;

/**
 * Unified interface for controlling persistent entities
 *
 * @param <T>  type of persistent unit
 * @param <PK> primary key type
 */
public interface GenericDao<T extends Identified<PK>, PK extends Serializable> extends Connectible {

    /**
     * Creates new record for the given object
     */
    void persist(T object) throws DatabaseException;

    /**
     * Return entity with corresponding Primary Key or null
     */
    T findByPK(PK key) throws DatabaseException;

    /**
     * Updates object in db
     */
    void update(T object) throws DatabaseException;

    /**
     * Deletes object from db
     */
    void delete(T object) throws DatabaseException;

    /**
     * Return list of all records in db
     */
    List<T> findAll() throws DatabaseException;
}
