package com.epam.carrental.dao.pool;

import com.epam.carrental.dao.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.FormattedMessage;
import org.apache.logging.log4j.message.ObjectMessage;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Enumeration;
import java.util.Queue;
import java.util.Timer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static com.epam.carrental.dao.pool.PropertiesUtil.CONFIG;
/**
 * The {@code ConnectionPoolImpl} class implements {@code ConnectionPool} interface
 * and holds a {@code queue} of connections sized by amount from config file.
 *
 * <p>In addition, this class provides several methods for getting
 * and releasing {@code connection}, some of them have {@link Connection#rollback()}
 * and {@link Connection#setAutoCommit(boolean)} function for {@link Exception} cause.</p>
 * <p>In a moment of initialization starts a {@link java.util.Timer} with a {@link java.util.TimerTask}
 * for checking amount of {@code connections} on leaks.</p>
 * @author Pavel Sirotsin
 * */
public enum ConnectionPoolImpl implements ConnectionPool {
    INSTANCE;

    private static final Logger logger = LogManager.getFormatterLogger(ConnectionPoolImpl.class);
    /**
     * A {@link BlockingQueue} constant holding the free connections.
     */
    private static final BlockingQueue<Connection> FREE_CONNECTIONS;
    /**
     * A {@link Queue} constant holding the release connections.
     */
    private static final Queue<Connection> USED_CONNECTIONS;
    /**
     * A {@code int} constant holding the default value of pool size.
     */
    private static final int DEFAULT_POOL_SIZE;
    /**
     * A {@link Timer} constant holding the {@code Timer} with schedule.
     */
    private static final Timer TIMER = new Timer();
    /**
     * A {@link BlockingQueue} constant holding the connection {@code Exception} information.
     */
    private static final String CONNECTION_EXCEPTION = "Can't connect to database, check configuration file";

    static {
        DEFAULT_POOL_SIZE = Integer.parseInt(CONFIG.getProperty("maxActive"));
        FREE_CONNECTIONS = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        USED_CONNECTIONS = new ArrayDeque<>(DEFAULT_POOL_SIZE);
        init();
    }
    /**
     * A getter for free connection collection.
     * @return the {@code Integer} value of collection size.
     */
    public int getFreeSize() {
        return FREE_CONNECTIONS.size();
    }
    /**
     * A getter for released connection collection.
     * @return the {@code Integer} value of collection size.
     */
    public int getInUseSize() {
        return USED_CONNECTIONS.size();
    }

    private static void init() {
        logger.traceEntry("- pool initialization.");

        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
                FREE_CONNECTIONS.offer(DriverManager.
                        getConnection(CONFIG.getProperty("url"), CONFIG));
            }

        } catch (SQLException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new IllegalStateException(CONNECTION_EXCEPTION, ex);
        }

        TaskLeak checkOnLeaks = new TaskLeak();
        TIMER.scheduleAtFixedRate(checkOnLeaks, 0, TimeUnit.HOURS.toMillis(5));

        logger.traceExit("pool initialized by amount: %d", FREE_CONNECTIONS.size());
    }

    private Connection validateConnection(Connection connection) throws DAOException {
        logger.traceEntry("- try to validate connection");
        try {
            logger.debug("line(%d), connection: %b", 75, connection.isValid(0));
            if (connection.isValid(0)) {
                logger.traceExit(" done.");
                return connection;
            } else {
                connection.close();
                logger.traceExit(" connection closed, got new connection.");
                return DriverManager.getConnection(CONFIG.getProperty("url"), CONFIG);
            }

        } catch (SQLException e) {
            logger.throwing(Level.ERROR, e);
            throw new DAOException(e);
        }
    }

    /**
     * Returns a free connection. Takes it from {@code FREE_CONNECTIONS}
     * and puts then into the {@code USED_CONNECTIONS}.
     * Before return validates it by {@link Connection#isValid(int)} method.
     * @return the {@link Connection} value.
     * @throws DAOException if {@link InterruptedException} arises.
     *
     */
    @Override
    public Connection getConnection() throws DAOException {
        logger.traceEntry(new FormattedMessage("try to get a Connection by a Thread: %s", Thread.currentThread().getName()));

        Connection connection;
        try {
            connection = FREE_CONNECTIONS.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.throwing(Level.ERROR, e);
            throw new DAOException(e);
        }
        connection = validateConnection(connection);
        USED_CONNECTIONS.offer(connection);
        logger.debug("line(%d), used amount: %d", 68, USED_CONNECTIONS.size());

        logger.traceExit("done, available count: %d", FREE_CONNECTIONS.size());
        return connection;
    }
    /**
     * Releases a connection after using. Remove it from {@code USED_CONNECTIONS}
     * and put then into the {@code FREE_CONNECTIONS}.
     */
    @Override
    public void releaseConnection(Connection connection) {
        logger.traceEntry("- try to return the Connection.");

        USED_CONNECTIONS.remove(connection);
        FREE_CONNECTIONS.offer(connection);

        logger.traceExit("done, available count: %d", FREE_CONNECTIONS.size());
    }
    /**
     * Releases a connection with {@link Connection#setAutoCommit(boolean)} option.
     * @throws DAOException if {@link SQLException} arises.
     */
    @Override
    public void releaseWithAutoCommit(Connection connection) throws DAOException {
        logger.traceEntry("- try to release a connection and set autoCommit - true");

        try {
            connection.setAutoCommit(true);
            releaseConnection(connection);
        } catch (SQLException e) {
            logger.throwing(Level.WARN, e);
            throw new DAOException(e);
        }

        logger.traceExit(" with connection: %s", new ObjectMessage(connection));
    }
    /**
     * Rolls back a transaction on a current {@code Connection} using
     * {@link Connection#rollback()} method.
     * @throws DAOException if {@link SQLException} arises.
     */
    @Override
    public void rollBack(Connection connection) throws DAOException {
        logger.traceEntry("- try to rollBack current changes");

        try {
            connection.rollback();
        } catch (SQLException e) {
            logger.throwing(Level.WARN, e);
            throw new DAOException(e);
        }

        logger.traceExit(" with connection: %s", new ObjectMessage(connection));
    }
    /**
     * Destroys the {@code ConnectionPool} removing all connections from {@code FREE_CONNECTIONS}
     * collection and closes it by {@link Connection#close()} method.
     * @throws DAOException if {@link SQLException} arises.
     */
    @Override
    public void destroyPool() throws DAOException {
        logger.traceEntry("- try to destroy the Pool of connections.");
        TIMER.cancel();

        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                try {
                    FREE_CONNECTIONS.take().close();
                } catch (SQLException e) {
                    logger.throwing(Level.WARN, e);
                    throw new DAOException(e);
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                logger.throwing(Level.ERROR, ex);
                throw new DAOException(ex);
            }
        }

        logger.traceExit(" with empty: %b", FREE_CONNECTIONS.isEmpty());

    }
    /**
     * Unregisters JDBC drivers that were created by {@link DriverManager}.
     * <p>Iterates through an {@link Enumeration} collection invoking {@link DriverManager#deregisterDriver(Driver)} method.
     * @throws DAOException if {@link SQLException} arises.
     */
    @Override
    public void unregisterDrivers() throws DAOException {
        logger.traceEntry("- try to unregister all of this connections drivers.");

        Enumeration<Driver> it = DriverManager.getDrivers();
        while (it.hasMoreElements()) {
            try {
                DriverManager.deregisterDriver(it.nextElement());
            } catch (SQLException e) {
                logger.throwing(Level.WARN, e);
                throw new DAOException(e);
            }
        }

        logger.traceExit("with empty: %b", USED_CONNECTIONS.isEmpty());
    }

}
