package com.gelo.persistance;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;

public class ConnectionManagerTest {
    private static ConnectionManager connectionManager;

    @BeforeClass
    public static void setUp() {
        connectionManager = new ConnectionManager();
    }

    @Test
    public void getConnection() {
        Connection connection = connectionManager.getConnection();
        Connection connection1 = connectionManager.getConnection();

        Assert.assertNotNull(connection);
        Assert.assertNotNull(connection1);
        Assert.assertNotSame(connection, connection1);
    }

    @Test
    public void closeConnection() {
        Connection connection = connectionManager.getConnection();
        Connection connection1 = connectionManager.getConnection();
        connectionManager.closeConnection(connection);
        connectionManager.closeConnection(connection1);
        Assert.assertNotNull(connection);
        Assert.assertNotNull(connection1);
    }
}