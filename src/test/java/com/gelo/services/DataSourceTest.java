package com.gelo.services;

import org.apache.commons.dbutils.DbUtils;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class DataSourceTest {

    @Test
    public void getInstance() {
        Assert.assertSame(DataSource.getInstance(), DataSource.getInstance());
    }

    @Test
    public void getConnection() throws SQLException {

        Connection connection1 = DataSource.getInstance().getConnection();
        Connection connection2 = DataSource.getInstance().getConnection();
        Assert.assertNotNull(connection1);
        Assert.assertNotNull(connection2);

        Assert.assertNotSame(connection1, connection2);
    }

    @Test
    public void closeConnection() throws SQLException {
        Connection connection1 = DataSource.getInstance().getConnection();
        Connection connection2 = DataSource.getInstance().getConnection();

        DataSource.closeConnection(connection1);
        DbUtils.closeQuietly(connection2);

        Assert.assertNotNull(connection1);
        Assert.assertNotNull(connection2);
    }
}