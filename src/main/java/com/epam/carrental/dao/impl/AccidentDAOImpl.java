package com.epam.carrental.dao.impl;

import com.epam.carrental.dao.AccidentAbstractDAO;
import com.epam.carrental.dao.entity.AccidentDAO;
import com.epam.carrental.dao.exception.DAOException;
import com.epam.carrental.dao.pool.ConnectionPoolImpl;
import com.epam.carrental.dao.sqlutil.SqlQuery;
import com.epam.carrental.service.util.MessageManager;
import com.mysql.cj.MysqlType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AccidentDAOImpl extends AccidentAbstractDAO {
    private static final Logger logger = LogManager.getFormatterLogger(AccidentDAOImpl.class);

    @Override
    public boolean create(AccidentDAO accident) throws DAOException {
        logger.traceEntry("- try to create an accident case.");

        Connection connection = ConnectionPoolImpl.INSTANCE.getConnection();

        int result;
        try (
                PreparedStatement accidentStatement = connection.prepareStatement(SqlQuery.CREATE_ACCIDENT);
                PreparedStatement bookingSelectStatement = connection.prepareStatement(SqlQuery.SELECT_BOOKING_BY_ID);
                PreparedStatement bookingUpdateStatement = connection.prepareStatement(SqlQuery.UPDATE_BOOKING_BY_ACCIDENT)
        ) {
            logger.debug("line(%d), connection valid: %s", 37, new ObjectMessage(connection.isValid(0)));
            logger.debug("line(%d), accidentStatement: %s", 36, new ObjectMessage(accidentStatement));
            logger.debug("line(%d), bookingSelectStatement: %s", 37, new ObjectMessage(bookingSelectStatement));
            logger.debug("line(%d), bookingUpdateStatement: %s", 38, new ObjectMessage(bookingUpdateStatement));

            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            bookingSelectStatement.setInt(1, accident.getBookingId());

            ResultSet resultSet = bookingSelectStatement.executeQuery();
            logger.debug("line(%d), resultSet: %s", 48, new ObjectMessage(resultSet.getMetaData()));

            if (resultSet.next()) {
                if (accident.getId() == null) {
                    accidentStatement.setNull(1, MysqlType.INT.getJdbcType());
                } else {
                    accidentStatement.setInt(1, accident.getId());
                }
                accidentStatement.setString(2, accident.getDamageDescription());
                accidentStatement.setDouble(3, accident.getDamageAmount());
                accidentStatement.setDouble(4, accident.getReclaimAmount());
                accidentStatement.setInt(5, accident.getBookingId());

                result = accidentStatement.executeUpdate();
                logger.debug("line(%d), result of executeUpdate: %d", 55, result);

                bookingUpdateStatement.setInt(1, 0);
                bookingUpdateStatement.setInt(2, accident.getBookingId());
                bookingUpdateStatement.executeUpdate();

                connection.commit();
            } else {
                logger.debug("booking doesn't exist.");
                throw new SQLException();
            }

        } catch (SQLException ex) {
            logger.throwing(Level.ERROR, ex);
            ConnectionPoolImpl.INSTANCE.rollBack(connection);
            throw new DAOException(MessageManager.WRONG_ID_OR_DONE, ex);
        } finally {
            ConnectionPoolImpl.INSTANCE.releaseWithAutoCommit(connection);
        }

        logger.traceExit(" done.");
        return result != 0;
    }

    @Override
    public AccidentDAO findByBookingId(Integer index) throws DAOException {
        logger.traceEntry("- try to find an accident by id.");

        Connection connection = ConnectionPoolImpl.INSTANCE.getConnection();

        AccidentDAO accident = null;
        try (
                PreparedStatement statement =
                        connection.prepareStatement(SqlQuery.SELECT_ACCIDENT_BY_BOOKING_ID)
        ) {
            logger.debug("line(%d), connection: %s", 98, new ObjectMessage(connection.isValid(0)));
            logger.debug("line(%d), statement: %s", 99, new ObjectMessage(statement));

            statement.setInt(1, index);
            ResultSet result = statement.executeQuery();
            logger.debug("line(%d), result: %s", 103, new ObjectMessage(result.getMetaData()));

            while (result.next()) {
                accident = new AccidentDAO(
                        result.getInt(1),
                        result.getString(2),
                        result.getDouble(3),
                        result.getDouble(4),
                        result.getInt(5));

            }

        } catch (SQLException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new DAOException(ex);
        } finally {
            ConnectionPoolImpl.INSTANCE.releaseConnection(connection);
        }

        logger.traceExit(" with accident: %s", new ObjectMessage(accident));
        return accident;
    }

    @Override
    public boolean deleteById(int index) throws DAOException {
        logger.traceEntry("- try to delete an accident by booking id.");

        Connection connection = ConnectionPoolImpl.INSTANCE.getConnection();

        int result;
        try (
                PreparedStatement deleteStatement = connection.prepareStatement(SqlQuery.DELETE_ACCIDENT_BY_BOOKING_ID);
                PreparedStatement updateStatement = connection.prepareStatement(SqlQuery.UPDATE_BOOKING_BY_ACCIDENT)
        ) {
            logger.debug("line(%d), connection: %s", 137, new ObjectMessage(connection.isValid(0)));
            logger.debug("line(%d), deleteStatement: %s", 138, new ObjectMessage(deleteStatement));
            logger.debug("line(%d), updateStatement: %s", 136, new ObjectMessage(updateStatement));

            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            deleteStatement.setInt(1, index);
            deleteStatement.executeUpdate();

            updateStatement.setInt(1, 1);
            updateStatement.setInt(2, index);
            result = updateStatement.executeUpdate();

            connection.commit();
        } catch (SQLException ex) {
            logger.throwing(Level.ERROR, ex);
            ConnectionPoolImpl.INSTANCE.rollBack(connection);
            throw new DAOException(MessageManager.WARNING_UPDATE,ex);
        } finally {
            ConnectionPoolImpl.INSTANCE.releaseWithAutoCommit(connection);
        }

        logger.traceExit(" with: %b", result);
        return result != 0;
    }
}
