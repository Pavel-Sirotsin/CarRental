package com.epam.carrental.dao.impl;

import com.epam.carrental.dao.InsuranceAbstractDAO;
import com.epam.carrental.dao.entity.InsuranceDAO;
import com.epam.carrental.dao.entity.InsuranceType;
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

public class InsuranceDAOImpl extends InsuranceAbstractDAO {
    private static final Logger logger = LogManager.getFormatterLogger(InsuranceDAOImpl.class);

    @Override
    public InsuranceDAO findById(int carId) throws DAOException {
        logger.traceEntry("- try to find an insurance by the car id.");

        Connection connection = ConnectionPoolImpl.INSTANCE.getConnection();

        InsuranceDAO insurance = null;

        try (
                PreparedStatement statement = connection.prepareStatement(
                        SqlQuery.SELECT_INSURANCE_BY_ID,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE)
        ) {
            logger.debug("line(%d), connection valid: %s", 39, new ObjectMessage(connection.isValid(0)));
            logger.debug("line(%d), statement: %s", 40, new ObjectMessage(statement));

            statement.setInt(1, carId);
            ResultSet result = statement.executeQuery();
            logger.debug("line(%d), resultSet: %s", 44, new ObjectMessage(result.getMetaData()));

            while (result.next()) {
                insurance = new InsuranceDAO(result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        InsuranceType.valueOf(result.getString(4).toUpperCase()),
                        result.getDouble(5),
                        result.getDouble(6));
            }

        } catch (SQLException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new DAOException(MessageManager.WRONG_ID, ex);
        } finally {
            ConnectionPoolImpl.INSTANCE.releaseConnection(connection);
        }

        logger.traceExit(" with insurance: %s", new ObjectMessage(insurance));
        return insurance;
    }

    @Override
    public InsuranceDAO findByBookingId(Integer index) throws DAOException {
        logger.traceEntry("- try to find an insurance by booking id.");

        Connection connection = ConnectionPoolImpl.INSTANCE.getConnection();

        InsuranceDAO insurance = null;
        try (
                PreparedStatement statement = connection.prepareStatement(
                        SqlQuery.SELECT_INSURANCE_BY_BOOKING_ID,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE)
        ) {
            logger.debug("line(%d), connection valid: %s", 79, new ObjectMessage(connection.isValid(0)));
            logger.debug("line(%d), prepareStatement: %s", 80, new ObjectMessage(statement));

            statement.setInt(1, index);
            ResultSet result = statement.executeQuery();
            logger.debug("line(%d), resultSet: %s", 84, new ObjectMessage(result.getMetaData()));

            while (result.next()) {
                insurance = new InsuranceDAO(result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        InsuranceType.valueOf(result.getString(4).toUpperCase()),
                        result.getDouble(5),
                        result.getDouble(6));

            }

        } catch (SQLException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new DAOException(MessageManager.WRONG_ID, ex);
        } finally {
            ConnectionPoolImpl.INSTANCE.releaseConnection(connection);
        }

        logger.traceExit(" with insurance: %s", new ObjectMessage(insurance));
        return insurance;
    }

    @Override
    public List<InsuranceDAO> findAll() throws DAOException {
        return Collections.emptyList();
    }

}
