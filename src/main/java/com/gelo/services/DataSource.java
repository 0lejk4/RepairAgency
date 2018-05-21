package com.gelo.services;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The type Data source.
 */
public class DataSource {
    private static Properties properties = new Properties();
    private javax.sql.DataSource ds;
    private static Logger logger = Logger.getLogger(DataSource.class);

    /*
      Loads properties for db from db-config.properties file
     */
    static {
        try {
            properties.load(DataSource.class.getClassLoader()
                    .getResourceAsStream("db-config.properties"));
        } catch (IOException e) {
            logger.error("db-config.properties file load error" + e);
        }
    }

    private static class SingletonHolder {
        private static final DataSource HOLDER_INSTANCE = new DataSource();
    }

    /**
     * Gets singleton instance.
     *
     * @return the instance
     */
    public static DataSource getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    /**
     * Checks is mode is testing or not.
     * Uses container specific connection pool using jndi if mode is not testing.
     * Else uses DBCP library connection pool with properties from config file
     */
    private DataSource() {
        if (properties.getProperty("mode").equals("test")) {
            ds = new BasicDataSource();
            ((BasicDataSource) ds).setDriverClassName(properties.getProperty("driverClass"));
            ((BasicDataSource) ds).setUsername(properties.getProperty("userName"));
            ((BasicDataSource) ds).setPassword(properties.getProperty("password"));
            ((BasicDataSource) ds).setUrl(properties.getProperty("url"));
        } else {
            InitialContext cxt;
            try {
                cxt = new InitialContext();
                ds = (javax.sql.DataSource) cxt.lookup(properties.getProperty("jndi.uri"));

            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets connection.
     *
     * @return the connection
     * @throws SQLException the sql exception
     */
    public Connection getConnection() throws SQLException {
        return this.ds.getConnection();
    }

    /**
     * Close connection.
     *
     * @param connection the connection
     */
    public static void closeConnection(Connection connection) {
        DbUtils.closeQuietly(connection);
    }

}