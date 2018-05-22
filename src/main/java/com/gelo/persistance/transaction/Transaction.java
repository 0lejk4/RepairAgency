package com.gelo.persistance.transaction;

import com.gelo.persistance.ConnectionManager;
import com.gelo.persistance.exception.TransactionRuntimeException;
import org.apache.log4j.LogManager;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The interface Transaction.
 * Represents transaction as functional interface and has methods that are used by transaction manager.
 * This manages the transaction process.
 */
@FunctionalInterface
public interface Transaction {

    /**
     * Some process that occurs in transaction.
     * Basically this will be multiple DAO`s doing something one by one
     * in one transaction.
     * So developer can just place an lambda, because this is FunctionalInterface.
     * In order to get data developer should use effectively final variables "hacks" like using Object[] vars in such lambdas.
     */
    void process();

    /**
     * Tx.
     * Performs transaction and rollbacks if something goes wrong
     * and also throws corresponding TransactionRuntimeException
     * that service should catch in order to return data properly.
     *
     * @param cm                        the cm
     * @param transaction               the transaction
     * @param transactionIsolationLevel the transaction isolation level
     */
    static void tx(ConnectionManager cm, Transaction transaction, int transactionIsolationLevel) {
        Connection conn = cm.getConnection();
        if (conn == null) {
            throw new TransactionRuntimeException();
        }

        boolean autoCommit;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            if(conn.getTransactionIsolation() != transactionIsolationLevel) {
                conn.setTransactionIsolation(transactionIsolationLevel);
            }

            transaction.process();
            conn.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LogManager.getLogger(Transaction.class).error("Cannot rollback transaction", e1);
            }

            throw new TransactionRuntimeException(e);
        } catch (RuntimeException e) {
            throw new TransactionRuntimeException(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                LogManager.getLogger(Transaction.class).error("Cannot close connection", e);
            }
        }
    }

    /**
     * Tx.
     * Delegates transaction execution to other method
     * with TRANSACTION_READ_COMMITTED isolation level
     *
     * @param cm          the cm
     * @param transaction the transaction
     */
    static void tx(ConnectionManager cm, Transaction transaction) {
        tx(cm, transaction, Connection.TRANSACTION_READ_COMMITTED);
    }
}
