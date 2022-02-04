package com.epam.carrental.dao.impl;

import com.epam.carrental.dao.PaymentAbstractDAO;
import com.epam.carrental.dao.entity.AccountDAO;
import com.epam.carrental.dao.entity.BookingDAO;
import com.epam.carrental.dao.entity.PaymentDAO;
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

public class PaymentDAOImpl extends PaymentAbstractDAO {
    private static final Logger logger = LogManager.getFormatterLogger(PaymentDAOImpl.class);

    @Override
    public boolean create(PaymentDAO paymentDAO) throws DAOException {
        logger.traceEntry("- try to create a new payment.");

        Connection connection = ConnectionPoolImpl.INSTANCE.getConnection();

        int result;
        try (
                PreparedStatement paymentStatement = connection.prepareStatement(SqlQuery.CREATE_PAYMENT);
                PreparedStatement bookingStatement = connection.prepareStatement(SqlQuery.UPDATE_BOOKING_BY_PAYMENT)
        ) {
            logger.debug("line(%d), connection valid: %s", 38, new ObjectMessage(connection.isValid(0)));
            logger.debug("line(%d), paymentCreateStatement: %s", 39, new ObjectMessage(paymentStatement));
            logger.debug("line(%d), bookingUpdateStatement: %s", 40, new ObjectMessage(bookingStatement));

            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            if (paymentDAO.getId() == null) {
                paymentStatement.setNull(1, MysqlType.INT.getJdbcType());
            } else {
                paymentStatement.setInt(1, paymentDAO.getId());
            }

            paymentStatement.setString(2, paymentDAO.getHolderName());
            paymentStatement.setString(3, paymentDAO.getCardNumber());
            paymentStatement.setString(4, paymentDAO.getExpirationDate());
            paymentStatement.setString(5, paymentDAO.getCvv());
            paymentStatement.setDouble(6, paymentDAO.getSum());
            paymentStatement.setInt(7, paymentDAO.getBookingId());

            paymentStatement.executeUpdate();

            bookingStatement.setInt(1, paymentDAO.getBookingId());

            result = bookingStatement.executeUpdate();

            connection.commit();
        } catch (SQLException ex) {
            logger.throwing(Level.ERROR, ex);
            ConnectionPoolImpl.INSTANCE.rollBack(connection);
            throw new DAOException(MessageManager.WRONG_DATA, ex);
        } finally {
            ConnectionPoolImpl.INSTANCE.releaseWithAutoCommit(connection);
        }

        logger.traceExit(" with result: %d", result);
        return result != 0;
    }

    @Override
    public boolean update(PaymentDAO paymentDAO) throws DAOException {
        logger.traceEntry("- try to update payment.");

        Connection connection = ConnectionPoolImpl.INSTANCE.getConnection();

        int paymentStatementResult;
        int accidentStatementResult;
        try (
                PreparedStatement paymentStatement = connection.prepareStatement(SqlQuery.UPDATE_PAYMENT_BY_BOOKING_ID);
                PreparedStatement accidentStatement = connection.prepareStatement(SqlQuery.UPDATE_ACCIDENT)
        ) {
            logger.debug("line(%d), connection valid: %s", 89, new ObjectMessage(connection.isValid(0)));
            logger.debug("line(%d), paymentStatement: %s", 90, new ObjectMessage(paymentStatement));
            logger.debug("line(%d), accidentStatement: %s", 91, new ObjectMessage(accidentStatement));

            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            paymentStatement.setDouble(1, paymentDAO.getReclamation());
            paymentStatement.setInt(2, paymentDAO.getBookingId());

            paymentStatementResult = paymentStatement.executeUpdate();
            logger.debug("line(%d), paymentStatementResult: %d", 100, paymentStatementResult);

            accidentStatement.setInt(1, paymentDAO.getBookingId());

            accidentStatementResult = accidentStatement.executeUpdate();
            logger.debug("line(%d), accidentStatementResult: %d", 105, accidentStatementResult);

            connection.commit();
        } catch (SQLException ex) {
            logger.throwing(Level.ERROR, ex);
            ConnectionPoolImpl.INSTANCE.rollBack(connection);
            throw new DAOException(MessageManager.WRONG_ID, ex);
        } finally {
            ConnectionPoolImpl.INSTANCE.releaseWithAutoCommit(connection);
        }

        logger.traceExit(" done.");
        return accidentStatementResult != 0;
    }

    @Override
    public PaymentDAO findByBookingId(Integer index) throws DAOException {
        logger.traceEntry("- try to find payment by booking id.");

        Connection connection = ConnectionPoolImpl.INSTANCE.getConnection();

        PaymentDAO payment = null;

        try (
                PreparedStatement statement = connection.prepareStatement(
                        SqlQuery.SELECT_PAYMENT_BY_BOOKING_ID,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE)
        ) {
            logger.debug("line(%d), connection valid: %s", 134, new ObjectMessage(connection.isValid(0)));
            logger.debug("line(%d), statement: %s", 135, new ObjectMessage(statement));

            statement.setInt(1, index);
            ResultSet result = statement.executeQuery();
            logger.debug("line(%d), result: %s", 139, new ObjectMessage(result.getMetaData()));

            while (result.next()) {
                payment = new PaymentDAO(
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        result.getDouble(6),
                        result.getDouble(7),
                        result.getInt(8));
            }

        } catch (SQLException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new DAOException(ex);
        } finally {
            ConnectionPoolImpl.INSTANCE.releaseConnection(connection);
        }

        logger.traceExit(" with payment: %s", new ObjectMessage(payment));
        return payment;
    }

    @Override
    public boolean createByAnonymous(BookingDAO booking, AccountDAO account, PaymentDAO payment) throws DAOException {
        logger.traceEntry("- try to make a transaction: booking-account-payment.");

        int accountStatementResult;
        int bookingStatementResult;
        int paymentStatementResult;

        Connection connection = ConnectionPoolImpl.INSTANCE.getConnection();

        try (
                PreparedStatement accountStatement = connection.prepareStatement(SqlQuery.CREATE_ACCOUNT);
                PreparedStatement bookingStatement = connection.prepareStatement(SqlQuery.CREATE_BOOKING_BY_ACCOUNT);
                PreparedStatement paymentStatement = connection.prepareStatement(SqlQuery.CREATE_PAYMENT_BY_BOOKING);
        ) {
            logger.debug("line(%d), connection valid: %s", 179, new ObjectMessage(connection.isValid(0)));
            logger.debug("line(%d), accountStatement: %s", 180, new ObjectMessage(accountStatement));
            logger.debug("line(%d), bookingStatement: %s", 181, new ObjectMessage(bookingStatement));
            logger.debug("line(%d), paymentStatement: %s", 182, new ObjectMessage(paymentStatement));

            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            if (account.getId() == null) {
                accountStatement.setNull(1, MysqlType.INT.getJdbcType());
            } else {
                accountStatement.setInt(1, account.getId());
            }

            accountStatement.setString(2, account.getName());
            accountStatement.setString(3, account.getSurname());
            accountStatement.setString(4, account.getEmail());
            accountStatement.setString(5, account.getPhoneNumber());
            accountStatement.setString(6, account.getDrivingLicense());

            if (booking.getId() == null) {
                bookingStatement.setNull(1, MysqlType.INT.getJdbcType());
            } else {
                bookingStatement.setInt(1, booking.getId());
            }

            bookingStatement.setString(2, booking.getRentalDate());
            bookingStatement.setString(3, booking.getRentalLocation());
            bookingStatement.setString(4, booking.getReturnDate());
            bookingStatement.setString(5, booking.getReturnLocation());
            bookingStatement.setInt(6, booking.getDaysAmount());
            bookingStatement.setDouble(7, booking.getSum());
            bookingStatement.setBoolean(8, booking.isPaid());
            bookingStatement.setBoolean(9, booking.isRejected());
            bookingStatement.setInt(10, booking.getCarId());
            bookingStatement.setBoolean(11, booking.isAccidentFree());

            if (payment.getId() == null) {
                paymentStatement.setNull(1, MysqlType.INT.getJdbcType());
            } else {
                paymentStatement.setInt(1, payment.getId());
            }

            paymentStatement.setString(2, payment.getHolderName());
            paymentStatement.setString(3, payment.getCardNumber());
            paymentStatement.setString(4, payment.getExpirationDate());
            paymentStatement.setString(5, payment.getCvv());
            paymentStatement.setDouble(6, payment.getSum());

            accountStatementResult = accountStatement.executeUpdate();
            logger.debug("line(%d), accountStatementResult: %d", 229, accountStatementResult);

            bookingStatementResult = bookingStatement.executeUpdate();
            logger.debug("line(%d), bookingStatementResult: %d", 232, bookingStatementResult);

            paymentStatementResult = paymentStatement.executeUpdate();
            logger.debug("line(%d), paymentStatementResult: %d", 235, paymentStatementResult);

            connection.commit();
        } catch (SQLException ex) {
            logger.throwing(Level.ERROR, ex);
            ConnectionPoolImpl.INSTANCE.rollBack(connection);
            throw new DAOException(MessageManager.WRONG_DATA, ex);
        } finally {
            ConnectionPoolImpl.INSTANCE.releaseWithAutoCommit(connection);
        }

        logger.traceExit(" with: %d");
        return paymentStatementResult != 0;
    }

    @Override
    public boolean deleteById(int index) throws DAOException {
        logger.traceEntry("- try to delete a payment by id.");

        Connection connection = ConnectionPoolImpl.INSTANCE.getConnection();

        int result;
        try (
                PreparedStatement deleteStatement = connection.prepareStatement(SqlQuery.DELETE_PAYMENT_BY_ID);
        ) {
            logger.debug("line(%d), connection: %s", 260, new ObjectMessage(connection.isValid(0)));
            logger.debug("line(%d), deleteStatement: %s", 261, new ObjectMessage(deleteStatement));

            deleteStatement.setInt(1, index);
            result = deleteStatement.executeUpdate();

        } catch (SQLException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new DAOException(MessageManager.WARNING_UPDATE, ex);
        } finally {
            ConnectionPoolImpl.INSTANCE.releaseConnection(connection);
        }

        logger.traceExit(" with: %b", result);
        return result != 0;
    }

}


