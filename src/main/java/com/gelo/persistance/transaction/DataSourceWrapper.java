package com.gelo.persistance.transaction;

import org.apache.log4j.LogManager;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;


/**
 * The type Data source wrapper.
 * This wrapper has thread local connection wrapper to handle transactions.
 * The thing is that it return same connection for one thread and this gives
 * ability to obtain connection in DAO by its own without use of service
 */
public class DataSourceWrapper implements DataSource {


    private DataSource ds;

    /**
     * Each thread has it`s iwn copy of this connection
     */
    private final ThreadLocal<ConnectionWrapper> connThreadLocal = new ThreadLocal<>();

    /**
     * Instantiates a new Data source wrapper.
     *
     * @param ds the ds
     */
    public DataSourceWrapper(DataSource ds) {
        this.ds = ds;
    }

    /**
     * On free.
     * Closes previous connection and removes it from thread local var
     *
     * @throws SQLException the sql exception
     */
    void onFree() throws SQLException {
        ConnectionWrapper conn = connThreadLocal.get();
        conn.getConnection().close();
        connThreadLocal.remove();
    }

    /**
     * It creates thread local connection or gets already existing and borrows it( increases txLevel of connection wrapper )
     */
    @Override
    public Connection getConnection() throws SQLException {
        if (connThreadLocal.get() != null) {
            ConnectionWrapper conn = connThreadLocal.get();
            conn.borrow();
            return conn;
        }

        ConnectionWrapper conn = new ConnectionWrapper(ds.getConnection(), this);
        conn.borrow();
        connThreadLocal.set(conn);
        return conn;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return ds.getLoginTimeout();
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return ds.getLogWriter();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return ds.getParentLogger();
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        ds.setLoginTimeout(seconds);
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        ds.setLogWriter(out);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return ds.isWrapperFor(iface);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return ds.unwrap(iface);
    }

    /**
     * Clean.
     */
    public void clean() {
        ConnectionWrapper conn = connThreadLocal.get();

        if (conn != null) {
            try {
                conn.getConnection().close();
            } catch (SQLException e) {
                LogManager.getLogger(DataSourceWrapper.class).error("Cannot close connection", e);
            } finally {
                connThreadLocal.remove();
            }
        }
    }
}
