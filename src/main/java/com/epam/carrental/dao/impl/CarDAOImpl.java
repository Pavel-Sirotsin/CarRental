package com.epam.carrental.dao.impl;

import com.epam.carrental.dao.BaseDAO;
import com.epam.carrental.dao.entity.CarDAO;
import com.epam.carrental.dao.exception.DAOException;
import com.epam.carrental.dao.pool.ConnectionPoolImpl;
import com.epam.carrental.dao.sqlutil.SqlQuery;
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


public class CarDAOImpl implements BaseDAO<CarDAO> {
    private static final Logger logger = LogManager.getFormatterLogger(CarDAOImpl.class);

    @Override
    public CarDAO findById(int key) throws DAOException {
        logger.traceEntry("- try to find a car by id.");
        CarDAO car = null;
        Connection connection = ConnectionPoolImpl.INSTANCE.getConnection();

        try (
                PreparedStatement statement = connection.prepareStatement(
                        SqlQuery.SELECT_CAR_BY_ID,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE)
        ) {
            logger.debug("line(%d), connection valid: %s", 37, new ObjectMessage(connection.isValid(0)));
            logger.debug("line(%d), prepareStatement: %s", 38, new ObjectMessage(statement));

            statement.setInt(1, key);
            ResultSet result = statement.executeQuery();
            logger.debug("line(%d), resultSet: %s", 40, new ObjectMessage(result.getMetaData()));

            while (result.next()) {
                car = new CarDAO.
                        Builder(result.getInt(1)).
                        brand(result.getString(2)).
                        model(result.getString(3)).
                        fuelType(result.getString(4)).
                        gearBox(result.getString(5)).
                        doors(result.getInt(6)).
                        airConditioning(result.getBoolean(7)).
                        trunkCapacity(result.getInt(8)).
                        pricePerDay(result.getDouble(9)).
                        insuranceId(result.getInt(10)).
                        imageURL(result.getString(11)).
                        build();
            }

        } catch (SQLException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new DAOException(ex);
        } finally {
            ConnectionPoolImpl.INSTANCE.releaseConnection(connection);
        }

        logger.traceExit(" with car: %s", new ObjectMessage(car));
        return car;
    }


    @Override
    public List<CarDAO> findAll() throws DAOException {
        logger.traceEntry("- try to find all cars.");

        List<CarDAO> allCars = new ArrayList<>();
        Connection connection = ConnectionPoolImpl.INSTANCE.getConnection();

        try (Statement statement = connection.createStatement()) {
            logger.debug("line(%d), connection valid: %s", 80, new ObjectMessage(connection.isValid(0)));
            logger.debug("line(%d), statement: %s", 81, new ObjectMessage(statement));

            ResultSet result = statement.executeQuery(SqlQuery.SELECT_ALL_CARS);
            logger.debug("line(%d), resultSet: %s", 84, new ObjectMessage(result.getMetaData()));

            while (result.next()) {

                allCars.add(new CarDAO.
                        Builder(result.getInt(1)).
                        brand(result.getString(2)).
                        model(result.getString(3)).
                        fuelType(result.getString(4)).
                        gearBox(result.getString(5)).
                        doors(result.getInt(6)).
                        airConditioning(result.getBoolean(7)).
                        trunkCapacity(result.getInt(8)).
                        pricePerDay(result.getDouble(9)).
                        insuranceId(result.getInt(10)).
                        imageURL(result.getString(11)).
                        build());
            }

        } catch (SQLException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new DAOException(ex);

        } finally {
            ConnectionPoolImpl.INSTANCE.releaseConnection(connection);
        }

        logger.traceExit(" with: %b", allCars.size());
        return allCars;
    }

}
