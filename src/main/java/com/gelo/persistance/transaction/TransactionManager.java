package com.gelo.persistance.transaction;

import com.gelo.persistance.ConnectionManager;

/**
 * The type Transaction manager.
 */
public class TransactionManager {

    private ConnectionManager cm;

    /**
     * Instantiates a new Transaction manager.
     *
     * @param cm the cm
     */
    public TransactionManager(ConnectionManager cm) {
        this.cm = cm;
    }

    /**
     * Tx. Runs the transaction
     *
     * @param tx the tx
     */
    public void tx(Transaction tx) {
        Transaction.tx(cm, tx);
    }
}
