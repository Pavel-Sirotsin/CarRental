package com.epam.carrental.dao.pool;

import com.epam.carrental.dao.exception.DAOException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Implementing this interface allows an object
 * to manipulate {@link java.sql.Connection}
 * created by {@link java.sql.DriverManager} using JDBC.
 */
interface ConnectionPool {

    Connection getConnection() throws InterruptedException, DAOException;

    void releaseConnection(Connection connection);

    void destroyPool() throws SQLException, InterruptedException, DAOException;

    void unregisterDrivers() throws SQLException, DAOException;

    void releaseWithAutoCommit(Connection connection) throws DAOException;

    void rollBack(Connection connection) throws DAOException;

}
