package com.gelo.model.dao.generic.impl;

import com.gelo.model.dao.generic.GenericDao;
import com.gelo.model.dao.generic.Identified;
import com.gelo.model.dao.impl.ConnectibleImpl;
import com.gelo.model.exception.DatabaseException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * Abstract class with basic CRUD operations done using JDBC
 *
 * @param <T>  type of persistent entity
 * @param <PK> type of primary key
 */
public abstract class AbstractJDBCDao<T extends Identified<PK>, PK extends Long> extends ConnectibleImpl implements GenericDao<T, PK> {
    private Logger logger = Logger.getLogger(this.getClass());


    /**
     * Instantiates a new Abstract jdbc dao.
     *
     * @param connection the connection
     */
    public AbstractJDBCDao(Connection connection) {
        super(connection);
    }

    /**
     * Instantiates a new Abstract jdbc dao.
     */
    public AbstractJDBCDao() {
    }

    /**
     * Gets table name.
     *
     * @return Return table name
     */
    public abstract String getTableName();

    /**
     * Returns sql query to get all records
     * <p/>
     * SELECT * FROM [Table]
     *
     * @return the select query
     */
    public abstract String getSelectQuery();

    /**
     * Returns sql query to count all records
     * <p/>
     * SELECT * FROM [Table]
     *
     * @return the select query
     */
    public abstract String getCountQuery();

    /**
     * Returns sql query to insert into table
     * <p/>
     * INSERT INTO [Table] ([column, column, ...]) VALUES (?, ?, ...);
     *
     * @return the create query
     */
    public abstract String getCreateQuery();

    /**
     * Returns sql query to update entity in table
     * <p/>
     * UPDATE [Table] SET [column = ?, column = ?, ...] WHERE id = ?;
     *
     * @return the update query
     */
    public abstract String getUpdateQuery();

    /**
     * Return sql query to delete entity from table
     * <p/>
     * DELETE FROM [Table] WHERE id= ?;
     *
     * @return the delete query
     */
    public abstract String getDeleteQuery();

    /**
     * Processes ResultSet and returns parsed contents of ResultSet.
     *
     * @param rs the rs
     * @return the list
     * @throws DatabaseException the database exception
     */
    protected abstract List<T> parseResultSet(ResultSet rs) throws DatabaseException;

    /**
     * Sets insert statement arguments using objects fields.
     *
     * @param ps     the ps
     * @param object the object
     * @throws DatabaseException the database exception
     */
    protected abstract void prepareStatementForInsert(PreparedStatement ps, T object) throws DatabaseException;

    /**
     * Sets update statement arguments using objects fields.
     *
     * @param ps     the ps
     * @param object the object
     * @throws DatabaseException the database exception
     */
    protected abstract void prepareStatementForUpdate(PreparedStatement ps, T object) throws DatabaseException;

    /**
     * Inserts entity into table
     *
     * @param object entity to be inserted
     * @throws DatabaseException exception thrown when exception occurs
     */
    @Override
    public void persist(T object) throws DatabaseException {

        String sql = getCreateQuery();
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            prepareStatementForInsert(statement, object);
            int count = statement.executeUpdate();
            if (count != 1) {
                logger.error("On persist modify more then 1 record: " + count);
                throw new DatabaseException("On persist modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            logger.error(e);
            throw new DatabaseException(e);
        }
    }

    /**
     * Finds entity by primary key value
     *
     * @param key key value
     * @return entity found or null
     * @throws DatabaseException exception thrown when exception occurs
     */
    @Override
    public T findByPK(Long key) throws DatabaseException {
        List<T> list;
        String sql = getSelectQuery();
        sql += " WHERE " + getTableName() + ".id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setLong(1, key);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            logger.error(e);
            throw new DatabaseException(e);
        }
        if (list == null || list.size() == 0) {
            return null;
        }
        if (list.size() > 1) {
            logger.error("Received more than one record.");
            throw new DatabaseException("Received more than one record.");
        }
        return list.iterator().next();
    }

    /**
     * Updates entity
     *
     * @param object entity used for update
     * @throws DatabaseException exception thrown when exception occurs
     */
    @Override
    public void update(T object) throws DatabaseException {
        String sql = getUpdateQuery();
        try (PreparedStatement statement = getConnection().prepareStatement(sql);) {
            prepareStatementForUpdate(statement, object); // заполнение аргументов запроса оставим на совесть потомков
            int count = statement.executeUpdate();
            if (count != 1) {
                logger.error("On update modify more then 1 record: " + count);

                throw new DatabaseException("On update modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            logger.error(e);

            throw new DatabaseException(e);
        }
    }

    /**
     * Deletes entity from table
     *
     * @param object entity to be deleted
     * @throws DatabaseException exception thrown when exception occurs
     */
    @Override
    public void delete(T object) throws DatabaseException {
        String sql = getDeleteQuery();
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            try {
                statement.setObject(1, object.getId());
            } catch (Exception e) {
                logger.error(e);
                throw new DatabaseException(e);
            }
            int count = statement.executeUpdate();
            if (count != 1) {
                logger.error("On delete modify more then 1 record: " + count);
                throw new DatabaseException("On delete modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            logger.error(e);
            throw new DatabaseException(e);
        }
    }

    /**
     * Return list of all entities from table
     *
     * @return list of entities
     * @throws DatabaseException exception thrown when exception occurs
     */
    @Override
    public List<T> findAll() throws DatabaseException {
        return findAll(null, null, null, null, null);
    }

    /**
     * Method that could be used in derived classes to create powerful select statements with different capabilities
     *
     * @param whereClause some where clauses
     * @param orderField  field that should be used to order entities
     * @param ascending   ascending or descending, only works if orderField is present
     * @param limit       how many records to limit
     * @param offset      the offset before first record
     * @return list of entities found using created query
     * @throws DatabaseException exception thrown when exception occurs
     */
    protected List<T> findAll(String whereClause, String orderField, Boolean ascending, Integer limit, Integer offset) throws DatabaseException {
        List<T> list;
        StringBuilder queryBuilder = new StringBuilder(getSelectQuery());
        if (whereClause != null) {
            queryBuilder.append(" WHERE ").append(whereClause);
        }
        if (ascending == null) {
            ascending = true;
        }
        if (orderField != null) {
            queryBuilder.append(" ORDER BY ").append(orderField).append(ascending ? " ASC " : " DESC ");
        }

        if (limit != null) {
            queryBuilder.append(" LIMIT ").append(limit);
        }

        if (offset != null) {
            queryBuilder.append(" OFFSET ").append(offset);
        }

        try (PreparedStatement statement = getConnection().prepareStatement(queryBuilder.toString())) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            logger.error(e);
            throw new DatabaseException(e);
        }
        return list;
    }

    /**
     * Method that could be used in derived classes to count
     * records with some where clause if it is present
     *
     * @param whereClause some where clauses
     * @return list of entities found using created query
     * @throws DatabaseException exception thrown when exception occurs
     */
    protected Long count(String whereClause) throws DatabaseException {
        Long count = 0L;

        StringBuilder queryBuilder = new StringBuilder(getCountQuery());
        if (whereClause != null) {
            queryBuilder.append(" WHERE ").append(whereClause);
        }

        try (PreparedStatement statement = getConnection().prepareStatement(queryBuilder.toString())) {
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                count = rs.getLong(1);
            }
        } catch (Exception e) {
            logger.error(e);
            throw new DatabaseException(e);
        }

        return count;
    }

}
