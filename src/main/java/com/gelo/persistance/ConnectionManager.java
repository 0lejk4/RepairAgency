package com.gelo.persistance;

import com.gelo.persistance.transaction.DataSourceWrapper;
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
public class ConnectionManager {
    private static Properties properties = new Properties();
    private javax.sql.DataSource ds;
    private static Logger logger = Logger.getLogger(ConnectionManager.class);

    /*
      Loads properties for db from db-config.properties file
     */
    static {
        try {
            properties.load(ConnectionManager.class.getClassLoader()
                    .getResourceAsStream("db-config.properties"));
        } catch (IOException e) {
            logger.error("db-config.properties file load error" + e);
        }
    }

    /**
     * Checks is mode is testing or not.
     * Uses container specific connection pool using jndi if mode is not testing.
     * Else uses DBCP library connection pool with properties from config file
     */
    public ConnectionManager() {
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
                javax.sql.DataSource unwrappedDs = (javax.sql.DataSource) cxt.lookup(properties.getProperty("jndi.uri"));
                ds = new DataSourceWrapper(unwrappedDs);
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets connection.
     *
     * @return the connection
     */
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = this.ds.getConnection();
        } catch (SQLException e) {
            logger.error("Cant get connection");
        }

        if (connection == null) {
            logger.error("Connection unreachable");
        }

        return connection;
    }

    /**
     * Close connection.
     *
     * @param connection the connection
     */
    public void closeConnection(Connection connection) {
        DbUtils.closeQuietly(connection);
    }

}