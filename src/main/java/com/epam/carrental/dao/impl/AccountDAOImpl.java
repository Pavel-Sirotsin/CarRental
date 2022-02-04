package com.epam.carrental.dao.impl;

import com.epam.carrental.dao.AccountAbstractDAO;
import com.epam.carrental.dao.entity.AccountDAO;
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

public class AccountDAOImpl extends AccountAbstractDAO {
    private static final Logger logger = LogManager.getFormatterLogger(AccountDAOImpl.class);

    @Override
    public AccountDAO findByUserId(int userId) throws DAOException {
        logger.traceEntry("- try to find an accountDAO by user id: %d", userId);
        AccountDAO accountDAO = null;
        Connection connection = ConnectionPoolImpl.INSTANCE.getConnection();

        try (PreparedStatement statement =
                     connection.prepareStatement(SqlQuery.SELECT_ACCOUNT_BY_USER_ID)) {
            logger.debug("line(%d), network timeOut: %b", 32, new ObjectMessage(connection.isValid(0)));

            statement.setInt(1, userId);
            logger.debug("line(%d), statement: %s", 35, new ObjectMessage(statement));

            ResultSet result = statement.executeQuery();
            logger.debug("line(%d), resultSet: %s", 38, new ObjectMessage(result.getMetaData()));

            while (result.next()) {
                accountDAO = new AccountDAO.
                        Builder(result.getInt(1)).
                        name(result.getString(2)).
                        surname(result.getString(3)).
                        email(result.getString(4)).
                        phoneNumber(result.getString(5)).
                        drivingLicense(result.getString(6)).
                        build();
            }

        } catch (SQLException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new DAOException(ex);
        } finally {
            ConnectionPoolImpl.INSTANCE.releaseConnection(connection);
        }

        logger.debug("line(%d), accountDAO: %s", 59, new ObjectMessage(accountDAO));
        return accountDAO;
    }

    @Override
    public AccountDAO findById(int index) throws DAOException {
        logger.traceEntry("- try to find an accountDAO by user id.");

        AccountDAO accountDAO = null;
        Connection connection = ConnectionPoolImpl.INSTANCE.getConnection();

        try (PreparedStatement statement =
                     connection.prepareStatement(SqlQuery.SELECT_ACCOUNT_BY_ID)) {
            logger.debug("line(%d), connection valid: %s", 72, new ObjectMessage(connection.isValid(0)));

            statement.setInt(1, index);
            logger.debug("line(%d), statement: %s", 75, new ObjectMessage(statement));

            ResultSet result = statement.executeQuery();
            logger.debug("line(%d), resultSet: %s", 78, new ObjectMessage(result.getMetaData()));

            while (result.next()) {
                accountDAO = new AccountDAO.
                        Builder(result.getInt(1)).
                        name(result.getString(2)).
                        surname(result.getString(3)).
                        email(result.getString(4)).
                        phoneNumber(result.getString(5)).
                        drivingLicense(result.getString(6)).
                        build();
            }

        } catch (SQLException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new DAOException(ex);
        } finally {
            ConnectionPoolImpl.INSTANCE.releaseConnection(connection);
        }

        logger.debug("line(%d), accountDAO: %s", 98, new ObjectMessage(accountDAO));
        return accountDAO;
    }


    @Override
    public boolean create(AccountDAO account) throws DAOException {
        logger.traceEntry("- try to create an account.");

        Connection connection = ConnectionPoolImpl.INSTANCE.getConnection();

        int result;
        try (PreparedStatement statement = connection.prepareStatement(SqlQuery.CREATE_ACCOUNT)) {
            logger.debug("line(%d), connection valid: %s", 111, new ObjectMessage(connection.isValid(0)));
            logger.debug("line(%d), statement: %s", 112, new ObjectMessage(statement));

            if (account.getId() == null) {
                statement.setNull(1, MysqlType.INT.getJdbcType());
            } else {
                statement.setInt(1, account.getId());
            }
            statement.setString(2, account.getName());
            statement.setString(3, account.getSurname());
            statement.setString(4, account.getEmail());
            statement.setString(5, account.getPhoneNumber());
            statement.setString(6, account.getDrivingLicense());

            result = statement.executeUpdate();

        } catch (SQLException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new DAOException(ex);
        } finally {
            ConnectionPoolImpl.INSTANCE.releaseConnection(connection);
        }

        logger.traceExit(" with: %d", result);
        return result != 0;
    }

    @Override
    public boolean update(AccountDAO accountDAO) throws DAOException {
        logger.traceEntry("- try to update an account.");

        Connection connection = ConnectionPoolImpl.INSTANCE.getConnection();

        int result;
        try (PreparedStatement statement = connection.prepareStatement(SqlQuery.UPDATE_ACCOUNT_BY_ID)) {
            logger.debug("line(%d), network timeOut: %s", 146, new ObjectMessage(connection.isValid(0)));
            logger.debug("line(%d), statement: %s", 147, new ObjectMessage(statement));

            statement.setString(1, accountDAO.getName());
            statement.setString(2, accountDAO.getSurname());
            statement.setString(3, accountDAO.getEmail());
            statement.setString(4, accountDAO.getPhoneNumber());
            statement.setString(5, accountDAO.getDrivingLicense());
            statement.setInt(6, accountDAO.getId());

            result = statement.executeUpdate();
            logger.debug("line(%d), resultSet: %d", 157, result);

        } catch (SQLException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new DAOException(MessageManager.WARNING_UPDATE, ex);
        } finally {
            ConnectionPoolImpl.INSTANCE.releaseConnection(connection);
        }

        logger.traceExit(" with: %d", result);
        return result != 0;
    }

    @Override
    public boolean deleteById(int index) throws DAOException {
        logger.traceEntry("- try to delete an account by id.");

        Connection connection = ConnectionPoolImpl.INSTANCE.getConnection();

        int result;
        try (
                PreparedStatement deleteStatement = connection.prepareStatement(SqlQuery.DELETE_ACCOUNT_BY_ID);
        ) {
            logger.debug("line(%d), connection: %s", 180, new ObjectMessage(connection.isValid(0)));
            logger.debug("line(%d), deleteStatement: %s", 181, new ObjectMessage(deleteStatement));

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
