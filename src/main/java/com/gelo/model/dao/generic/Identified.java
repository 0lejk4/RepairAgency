package com.gelo.model.dao.generic;

import java.io.Serializable;

/**
 * Interface of identified entities
 */
public interface Identified<PK extends Serializable> {

    /**
     * @return primary key of entity
     */
    public PK getId();
}
