package com.epam.carrental.dao.impl;

import com.epam.carrental.dao.BookingAbstractDAO;
import com.epam.carrental.dao.entity.BookingDAO;
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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl extends BookingAbstractDAO {
    private static final Logger logger = LogManager.getFormatterLogger(BookingDAOImpl.class);

    @Override
    public BookingDAO findById(int index) throws DAOException {
        logger.traceEntry("- try to find an booking by id.");

        Connection connection = ConnectionPoolImpl.INSTANCE.getConnection();

        BookingDAO booking = null;

        try (
                PreparedStatement statement =
                        connection.prepareStatement(SqlQuery.SELECT_BOOKING_BY_ID)
        ) {
            logger.debug("line(%d), connection valid: %s", 38, new ObjectMessage(connection.isValid(0)));
            logger.debug("line(%d), prepareStatement: %s", 38, new ObjectMessage(statement));

            statement.setInt(1, index);
            ResultSet result = statement.executeQuery();
            logger.debug("line(%d), resultSet: %s", 43, new ObjectMessage(result.getMetaData()));

            while (result.next()) {
                booking = new BookingDAO.
                        Builder(result.getInt(1)).
                        accountId(result.getInt(2)).
                        rentalDate(result.getString(3)).
                        rentalLocation(result.getString(4)).
                        returnDate(result.getString(5)).
                        returnLocation(result.getString(6)).
                        daysAmount(result.getInt(7)).
                        sum(result.getDouble(8)).
                        paid(result.getBoolean(9)).
                        rejected(result.getBoolean(10)).
                        carId(result.getInt(11)).
                        accidentFree(result.getBoolean(12)).
                        build();

            }

        } catch (SQLException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new DAOException(MessageManager.WRONG_ID, ex);
        } finally {
            ConnectionPoolImpl.INSTANCE.releaseConnection(connection);
        }

        logger.traceExit(" with booking: %s", new ObjectMessage(booking));
        return booking;
    }

    @Override
    public Integer createReturnID(BookingDAO booking) throws DAOException {
        logger.traceEntry("- try to create a new booking and return id.");

        int statementResult;

        Connection connection = ConnectionPoolImpl.INSTANCE.getConnection();

        int returnedId;
        try (
                PreparedStatement createStatement = connection.prepareStatement(SqlQuery.CREATE_BOOKING);
                Statement selectStatement = connection.createStatement()
        ) {
            logger.debug("line(%d), connection valid: %s", 87, new ObjectMessage(connection.isValid(0)));
            logger.debug("line(%d), createStatement: %s", 88, new ObjectMessage(createStatement));
            logger.debug("line(%d), selectStatement: %s", 89, new ObjectMessage(selectStatement));

            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            if (booking.getId() == null) {
                createStatement.setNull(1, MysqlType.INT.getJdbcType());
            } else {
                createStatement.setInt(1, booking.getId());
            }

            createStatement.setInt(2, booking.getAccountId());
            createStatement.setString(3, booking.getRentalDate());
            createStatement.setString(4, booking.getRentalLocation());
            createStatement.setString(5, booking.getReturnDate());
            createStatement.setString(6, booking.getReturnLocation());
            createStatement.setInt(7, booking.getDaysAmount());
            createStatement.setDouble(8, booking.getSum());
            createStatement.setBoolean(9, booking.isPaid());
            createStatement.setBoolean(10, booking.isRejected());
            createStatement.setInt(11, booking.getCarId());
            createStatement.setBoolean(12, booking.isAccidentFree());

            statementResult = createStatement.executeUpdate();
            logger.debug("line(%d), statementResult: %d", 113, statementResult);

            ResultSet resultSet = selectStatement.executeQuery(SqlQuery.SELECT_BOOKING_ID);
            logger.debug("line(%d), resultSet: %s", 116, new ObjectMessage(resultSet.getMetaData()));

            resultSet.next();
            returnedId = resultSet.getInt(1);

            connection.commit();

        } catch (SQLException ex) {
            logger.throwing(Level.ERROR, ex);
            ConnectionPoolImpl.INSTANCE.rollBack(connection);
            throw new DAOException(MessageManager.WRONG_DATA, ex);
        } finally {
            ConnectionPoolImpl.INSTANCE.releaseWithAutoCommit(connection);
        }

        logger.traceExit(" with: %d", returnedId);
        return returnedId;
    }

    @Override
    public List<BookingDAO> findAllByAccountId(Integer index) throws DAOException {
        logger.traceEntry("- try to find all bookings by user id.");

        Connection connection = ConnectionPoolImpl.INSTANCE.getConnection();

        List<BookingDAO> bookingDAOList = new ArrayList<>();

        try (PreparedStatement statement =
                     connection.prepareStatement(SqlQuery.SELECT_BOOKING_BY_ACCOUNT_ID)
        ) {
            logger.debug("line(%d), connection valid: %s", 146, new ObjectMessage(connection.isValid(0)));
            logger.debug("line(%d), statement: %s", 147, new ObjectMessage(statement));

            statement.setInt(1, index);
            ResultSet result = statement.executeQuery();
            logger.debug("line(%d), result: %s", 151, new ObjectMessage(result.getMetaData()));

            while (result.next()) {
                bookingDAOList.add(new BookingDAO.
                        Builder(result.getInt(1)).
                        accountId(result.getInt(2)).
                        rentalDate(result.getString(3)).
                        rentalLocation(result.getString(4)).
                        returnDate(result.getString(5)).
                        returnLocation(result.getString(6)).
                        daysAmount(result.getInt(7)).
                        sum(result.getDouble(8)).
                        paid(result.getBoolean(9)).
                        rejected(result.getBoolean(10)).
                        carId(result.getInt(11)).
                        accidentFree(result.getBoolean(12)).build());

            }

        } catch (SQLException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new DAOException(ex);
        } finally {
            ConnectionPoolImpl.INSTANCE.releaseConnection(connection);
        }

        logger.traceExit("done, the collection size is: %d", bookingDAOList.size());
        return bookingDAOList;
    }

    @Override
    public boolean create(BookingDAO booking) throws DAOException {
        logger.traceEntry("- try to create a new booking.");

        Connection connection = ConnectionPoolImpl.INSTANCE.getConnection();

        int statementResult;
        try (
                PreparedStatement statement = connection.prepareStatement(SqlQuery.CREATE_BOOKING)
        ) {
            logger.debug("line(%d), connection valid: %s", 191, new ObjectMessage(connection.isValid(0)));
            logger.debug("line(%d), prepareStatement: %s", 192, new ObjectMessage(statement));

            if (booking.getId() == null) {
                statement.setNull(1, MysqlType.INT.getJdbcType());
            } else {
                statement.setInt(1, booking.getId());
            }

            statement.setInt(2, booking.getAccountId());
            statement.setString(3, booking.getRentalDate());
            statement.setString(4, booking.getRentalLocation());
            statement.setString(5, booking.getReturnDate());
            statement.setString(6, booking.getReturnLocation());
            statement.setInt(7, booking.getDaysAmount());
            statement.setDouble(8, booking.getSum());
            statement.setBoolean(9, booking.isPaid());
            statement.setBoolean(10, booking.isRejected());
            statement.setInt(11, booking.getCarId());
            statement.setBoolean(12, booking.isAccidentFree());

            statementResult = statement.executeUpdate();
            logger.debug("line(%d), statementResult: %d", 262, statementResult);

        } catch (SQLException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new DAOException(MessageManager.WRONG_DATA, ex);
        } finally {
            ConnectionPoolImpl.INSTANCE.releaseConnection(connection);
        }

        logger.traceExit(" done.");
        return statementResult != 0;
    }

    @Override
    public List<BookingDAO> findAll() throws DAOException {
        logger.traceEntry("- try to find all orders.");

        List<BookingDAO> orders = new ArrayList<>();
        Connection connection = ConnectionPoolImpl.INSTANCE.getConnection();

        try (Statement statement = connection.createStatement()) {
            logger.debug("line(%d), connection valid: %s", 234, new ObjectMessage(connection.isValid(0)));
            logger.debug("line(%d), statement: %s", 235, new ObjectMessage(statement));

            ResultSet result = statement.executeQuery(SqlQuery.SELECT_ALL_ORDERS);
            logger.debug("line(%d), resultSet: %s", 238, new ObjectMessage(result.getMetaData()));

            while (result.next()) {

                orders.add(new BookingDAO.
                        Builder(result.getInt(1)).
                        accountId(result.getInt(2)).
                        rentalDate(result.getString(3)).
                        rentalLocation(result.getString(4)).
                        returnDate(result.getString(5)).
                        returnLocation(result.getString(6)).
                        daysAmount(result.getInt(7)).
                        sum(result.getDouble(8)).
                        paid(result.getBoolean(9)).
                        rejected(result.getBoolean(10)).
                        carId(result.getInt(11)).
                        accidentFree(result.getBoolean(12)).
                        build());
            }

        } catch (SQLException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new DAOException(ex);

        } finally {
            ConnectionPoolImpl.INSTANCE.releaseConnection(connection);
        }

        logger.traceExit(" with orders: %d", orders.size());
        return orders;
    }

    @Override
    public boolean deleteById(int index) throws DAOException {
        logger.traceEntry("- try to delete the booking by id.");

        int result;
        Connection connection = ConnectionPoolImpl.INSTANCE.getConnection();

        try (
                PreparedStatement deleteStatement = connection.prepareStatement(SqlQuery.DELETE_BOOKING_BY_ID);
                PreparedStatement selectStatement = connection.prepareStatement(SqlQuery.SELECT_BOOKING_BY_ID)
        ) {
            logger.debug("line(%d), connection valid: %s", 281, new ObjectMessage(connection.isValid(0)));
            logger.debug("line(%d), deleteStatement: %s", 282, new ObjectMessage(deleteStatement));
            logger.debug("line(%d), selectStatement: %s", 283, new ObjectMessage(selectStatement));

            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            selectStatement.setInt(1, index);

            ResultSet resultSet = selectStatement.executeQuery();
            logger.debug("line(%d), resultSet: %s", 291, new ObjectMessage(resultSet.getMetaData()));

            if (resultSet.next()) {
                deleteStatement.setInt(1, index);
                result = deleteStatement.executeUpdate();
                logger.debug("line(%d), result: %d", 296, result);
                connection.commit();
            } else {
                throw new SQLException();
            }

        } catch (SQLException ex) {
            logger.throwing(Level.ERROR, ex);
            ConnectionPoolImpl.INSTANCE.rollBack(connection);
            throw new DAOException(MessageManager.WRONG_ID, ex);
        } finally {
            ConnectionPoolImpl.INSTANCE.releaseWithAutoCommit(connection);
        }

        logger.traceExit(" done.");
        return result != 0;
    }

}
