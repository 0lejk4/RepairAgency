package com.gelo.model.dao.generic;

import java.io.Serializable;
import java.util.List;

/**
 * Unified interface for controlling persistent entities
 *
 * @param <T>  type of persistent unit
 * @param <PK> primary key type
 */
public interface GenericDao<T extends Identified<PK>, PK extends Serializable> {

    /**
     * Creates new record for the given object
     */
    Long persist(T object) ;

    /**
     * Return entity with corresponding Primary Key or null
     */
    T findByPK(PK key) ;

    /**
     * Updates object in db
     */
    void update(T object) ;

    /**
     * Deletes object from db
     */
    void delete(T object) ;

    /**
     * Return list of all records in db
     */
    List<T> findAll() ;
}
