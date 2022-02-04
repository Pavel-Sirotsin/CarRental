package com.epam.carrental.service.impl;

import com.epam.carrental.dao.BaseDAO;
import com.epam.carrental.dao.DAOProvider;
import com.epam.carrental.dao.entity.CarDAO;
import com.epam.carrental.dao.exception.DAOException;
import com.epam.carrental.service.BaseService;
import com.epam.carrental.service.converter.BaseConverter;
import com.epam.carrental.service.converter.impl.CarConverter;
import com.epam.carrental.service.dto.CarDTO;
import com.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import java.util.ArrayList;
import java.util.List;

public class CarServiceImpl implements BaseService<CarDTO> {
    private static final Logger logger = LogManager.getFormatterLogger(CarServiceImpl.class);

    private final BaseDAO<CarDAO> carDaoImpl = DAOProvider.INSTANCE.getCarImpl();

    private final BaseConverter<CarDTO, CarDAO> converter = new CarConverter();

    @Override
    public List<CarDTO> getAll() throws ServiceException {
        logger.traceEntry("- try to get all cars.");
        List<CarDTO> carDTOList = new ArrayList<>();

        try {
            List<CarDAO> carDAOList = carDaoImpl.findAll();

            for (CarDAO carDAO : carDAOList) {
                carDTOList.add(converter.convert(carDAO));
            }

        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
        logger.traceExit(" with: %d", carDTOList.size());
        return carDTOList;
    }

    @Override
    public CarDTO getById(int id) throws ServiceException {
        logger.traceEntry("- try to get car by id.");
        CarDTO car;
        try {
            car = converter.convert(carDaoImpl.findById(id));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        logger.traceExit(" with car: %s", new ObjectMessage(car));
        return car;
    }

}
