package com.gelo.model.dao.impl;

import com.gelo.model.dao.Connectible;

import java.sql.Connection;

/**
 * Implementation of Connectible interface that simply stores connection
 */
public abstract class ConnectibleImpl implements Connectible {
    private Connection connection;

    /**
     * Constructor with connection parameter
     * @param connection connection to be stored
     */
    public ConnectibleImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * Default constructor
     */
    public ConnectibleImpl() {
    }

    /**
     * @return current stored connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * @param connection connection to be stored
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
