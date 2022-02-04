package com.epam.carrental.dao.impl;

import com.epam.carrental.dao.UserAbstractDAO;
import com.epam.carrental.dao.entity.AccountDAO;
import com.epam.carrental.dao.entity.UserDAO;
import com.epam.carrental.dao.entity.UserRole;
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


public class UserDAOImpl extends UserAbstractDAO {
    private static final Logger logger = LogManager.getFormatterLogger(UserDAOImpl.class);

    @Override
    public UserDAO findByLogin(String login) throws DAOException {
        logger.traceEntry("- try to find a user by login.");

        Connection connection = ConnectionPoolImpl.INSTANCE.getConnection();

        UserDAO user = null;

        try (
                PreparedStatement statement = connection.prepareStatement(
                        SqlQuery.SELECT_USER_BY_LOGIN,
                        ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE)
        ) {
            logger.debug("line(%d), connection valid: %s", 43, new ObjectMessage(connection.isValid(0)));
            logger.debug("line(%d), statement: %s", 44, new ObjectMessage(statement));

            statement.setString(1, login);
            ResultSet result = statement.executeQuery();
            logger.debug("line(%d), resultSet: %s", 48, new ObjectMessage(result.getMetaData()));

            while (result.next()) {
                user = new UserDAO(
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        UserRole.valueOf(result.
                                getString(4).
                                toUpperCase()),
                        result.getInt(5));
            }

        } catch (SQLException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new DAOException(ex);

        } finally {
            ConnectionPoolImpl.INSTANCE.releaseConnection(connection);
        }

        if (user != null) {
            logger.debug("line(%d), user: %s", 70, new ObjectMessage(user));
            return user;
        } else {
            logger.debug("the user does not exist.");
            throw new DAOException(MessageManager.WARNING_LOG_IN);
        }

    }

    @Override
    public List<UserDAO> findAll() throws DAOException {
        logger.traceEntry("- try to find all users.");

        List<UserDAO> users = new ArrayList<>();
        Connection connection = ConnectionPoolImpl.INSTANCE.getConnection();

        try (
                Statement statement = connection.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                ResultSet result = statement.executeQuery(SqlQuery.SELECT_ALL_USERS)
        ) {
            logger.debug("line(%d), connection valid: %s", 92, new ObjectMessage(connection.isValid(0)));
            logger.debug("line(%d), statement: %s", 93, new ObjectMessage(statement));
            logger.debug("line(%d), resultSet: %s", 94, new ObjectMessage(result.getMetaData()));

            while (result.next()) {
                users.add(new UserDAO(result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        UserRole.valueOf(result.getString(4).toUpperCase()),
                        result.getInt(5)));
            }

        } catch (SQLException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new DAOException(MessageManager.WRONG_ID, ex);
        } finally {
            ConnectionPoolImpl.INSTANCE.releaseConnection(connection);
        }

        logger.traceExit("done ? : %b", users.isEmpty());
        return users;
    }

    @Override
    public boolean deleteById(int index) throws DAOException {
        logger.traceEntry("- try to delete a user by id.");

        Connection connection = ConnectionPoolImpl.INSTANCE.getConnection();

        int result;
        try (
                PreparedStatement deleteStatement = connection.prepareStatement(SqlQuery.DELETE_USER_BY_ID);
                PreparedStatement selectStatement = connection.prepareStatement(SqlQuery.SELECT_USER_BY_ID)
        ) {
            logger.debug("line(%d), connection valid: %s", 126, new ObjectMessage(connection.isValid(0)));
            logger.debug("line(%d), deleteStatement: %s", 127, new ObjectMessage(deleteStatement));
            logger.debug("line(%d), selectStatement: %s", 128, new ObjectMessage(selectStatement));

            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            selectStatement.setInt(1, index);

            ResultSet resultSet = selectStatement.executeQuery();
            logger.debug("line(%d), resultSet: %s", 136, new ObjectMessage(resultSet.getMetaData()));

            if (resultSet.next()) {
                deleteStatement.setInt(1, index);
                result = deleteStatement.executeUpdate();
                logger.debug("line(%d), result: %d", 141, result);
                connection.commit();
            } else {
                logger.debug("booking doesn't exist.");
                throw new SQLException();
            }

        } catch (SQLException ex) {
            logger.throwing(Level.ERROR, ex);
            ConnectionPoolImpl.INSTANCE.rollBack(connection);
            throw new DAOException(MessageManager.WRONG_ID, ex);
        } finally {
            ConnectionPoolImpl.INSTANCE.releaseWithAutoCommit(connection);
        }

        logger.traceExit("done.");
        return result != 0;
    }

    @Override
    public boolean update(UserDAO userDAO) throws DAOException {
        logger.traceEntry("- try to update the user.");

        Connection connection = ConnectionPoolImpl.INSTANCE.getConnection();

        int result;
        try (PreparedStatement statement =
                     connection.prepareStatement(SqlQuery.UPDATE_USER_BY_ID)) {
            logger.debug("line(%d), connection valid: %s", 169, new ObjectMessage(connection.isValid(0)));
            logger.debug("line(%d), statement: %s", 170, new ObjectMessage(statement));

            statement.setString(1, userDAO.getLogin());
            statement.setString(2, userDAO.getPassword());
            statement.setString(3, userDAO.getUserRole().name());
            statement.setInt(4, userDAO.getId());

            result = statement.executeUpdate();

        } catch (SQLException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new DAOException(MessageManager.WARNING_UPDATE, ex);
        } finally {
            ConnectionPoolImpl.INSTANCE.releaseConnection(connection);
        }

        logger.traceExit("done, result is: %d", result);
        return result != 0;
    }

    @Override
    public boolean makeTransaction(UserDAO user, AccountDAO account) throws DAOException {
        logger.traceEntry("- try to make a transaction: user-account.");

        int accountStatementResult;
        int userStatementResult;

        Connection connection = ConnectionPoolImpl.INSTANCE.getConnection();

        try (
                PreparedStatement accountStatement = connection.prepareStatement(SqlQuery.CREATE_ACCOUNT);
                PreparedStatement userStatement = connection.prepareStatement(SqlQuery.CREATE_USER)
        ) {
            logger.debug("line(%d), connection valid: %s", 203, new ObjectMessage(connection.isValid(0)));
            logger.debug("line(%d), userStatement: %s", 204, new ObjectMessage(userStatement));
            logger.debug("line(%d), accountStatement: %s", 205, new ObjectMessage(accountStatement));

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

            accountStatementResult = accountStatement.executeUpdate();
            logger.debug("line(%d), accountStatementResult: %d", 222, accountStatementResult);

            if (user.getId() == null) {
                userStatement.setNull(1, MysqlType.INT.getJdbcType());
            } else {
                userStatement.setInt(1, user.getId());
            }

            userStatement.setString(2, user.getLogin());
            userStatement.setString(3, user.getPassword());
            userStatement.setString(4, user.getUserRole().name());

            userStatementResult = userStatement.executeUpdate();
            logger.debug("line(%d), userStatementResult: %d", 235, userStatementResult);

            connection.commit();

        } catch (SQLException ex) {
            logger.throwing(Level.ERROR, ex);
            ConnectionPoolImpl.INSTANCE.rollBack(connection);
            throw new DAOException(MessageManager.WARNING_SIGN_UP, ex);
        } finally {
            ConnectionPoolImpl.INSTANCE.releaseWithAutoCommit(connection);
        }

        logger.traceExit(" done.");
        return userStatementResult != 0;
    }

}
