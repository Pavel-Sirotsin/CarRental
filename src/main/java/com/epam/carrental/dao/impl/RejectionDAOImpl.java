package com.epam.carrental.dao.impl;

import com.epam.carrental.dao.BaseDAO;
import com.epam.carrental.dao.entity.RejectionDAO;
import com.epam.carrental.dao.exception.DAOException;
import com.epam.carrental.dao.pool.ConnectionPoolImpl;
import com.epam.carrental.dao.sqlutil.SqlQuery;
import com.epam.carrental.service.util.MessageManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class RejectionDAOImpl implements BaseDAO<RejectionDAO> {
    private static final Logger logger = LogManager.getFormatterLogger(RejectionDAOImpl.class);

    @Override
    public boolean create(RejectionDAO rejection) throws DAOException {
        logger.traceEntry("- try to create a rejection.");

        Connection connection = ConnectionPoolImpl.INSTANCE.getConnection();

        int result;
        try (
                PreparedStatement rejectionStatement = connection.prepareStatement(SqlQuery.CREATE_REJECTION);
                PreparedStatement bookingSelectStatement = connection.prepareStatement(SqlQuery.SELECT_BOOKING_BY_ID);
                PreparedStatement bookingUpdateStatement = connection.prepareStatement(SqlQuery.UPDATE_BOOKING_BY_REJECTION)
        ) {
            logger.debug("line(%d), connection valid: %s", 36, new ObjectMessage(connection.isValid(0)));
            logger.debug("line(%d), rejectionCreateStatement: %s", 37, new ObjectMessage(rejectionStatement));
            logger.debug("line(%d), bookingSelectStatement: %s", 38, new ObjectMessage(bookingSelectStatement));
            logger.debug("line(%d), bookingUpdateStatement: %s", 39, new ObjectMessage(bookingUpdateStatement));

            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            bookingSelectStatement.setInt(1, rejection.getBookingId());

            ResultSet resultSet = bookingSelectStatement.executeQuery();
            logger.debug("line(%d), resultSet: %s", 47, new ObjectMessage(resultSet));

            if (resultSet.next()) {
                rejectionStatement.setString(1, rejection.getRejectionReason());
                rejectionStatement.setInt(2, rejection.getBookingId());

                result = rejectionStatement.executeUpdate();
                logger.debug("line(%d), rejectionStatement result: %d", 53, result);

                bookingUpdateStatement.setInt(1, rejection.getBookingId());
                result = bookingUpdateStatement.executeUpdate();
                logger.debug("line(%d), bookingUpdateStatement result: %d", 57, result);

                connection.commit();
            } else {
                throw new SQLException();
            }

        } catch (SQLException ex) {
            logger.throwing(Level.ERROR, ex);
            ConnectionPoolImpl.INSTANCE.rollBack(connection);
            throw new DAOException(MessageManager.WRONG_ID_OR_DONE, ex);
        } finally {
            ConnectionPoolImpl.INSTANCE.releaseWithAutoCommit(connection);
        }

        logger.traceExit(" done. ");
        return result != 0;
    }

    @Override
    public List<RejectionDAO> findAll() throws DAOException {
        return Collections.emptyList();
    }

    @Override
    public RejectionDAO findById(int index) throws DAOException {
        logger.traceEntry("- try to find rejection by booking id.");

        Connection connection = ConnectionPoolImpl.INSTANCE.getConnection();

        RejectionDAO rejection = null;

        try (
                PreparedStatement statement = connection.prepareStatement(
                        SqlQuery.SELECT_REJECTION_BY_BOOKING_ID,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE)
        ) {
            logger.debug("line(%d), connection valid: %s", 96, new ObjectMessage(connection.isValid(0)));
            logger.debug("line(%d), statement: %s", 97, new ObjectMessage(statement));

            statement.setInt(1, index);
            ResultSet result = statement.executeQuery();
            logger.debug("line(%d), resultSet: %s", 101, new ObjectMessage(result.getMetaData()));

            while (result.next()) {
                rejection = new RejectionDAO(
                        result.getInt(1),
                        result.getString(2),
                        result.getInt(3));
            }

        } catch (SQLException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new DAOException(ex);
        } finally {
            ConnectionPoolImpl.INSTANCE.releaseConnection(connection);
        }

        logger.traceExit(" with rejection: %s", new ObjectMessage(rejection));
        return rejection;
    }

}
