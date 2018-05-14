package com.gelo.model.dao;

import java.sql.Connection;

/**
 * Interface for classes that have ability to manage connection for themselves
 */
public interface Connectible {

    /**
     * Connection setter
     * @param connection input connection
     */
    void setConnection(Connection connection);

    /**
     * Connection getter
     * @return connection of current class
     */
    Connection getConnection();
}
