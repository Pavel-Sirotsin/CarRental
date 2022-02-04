package com.epam.carrental.service.converter.impl;

import com.epam.carrental.dao.entity.CarDAO;
import com.epam.carrental.dao.exception.DAOException;
import com.epam.carrental.service.BaseService;
import com.epam.carrental.service.converter.BaseConverter;
import com.epam.carrental.service.dto.CarDTO;
import com.epam.carrental.service.dto.InsuranceDTO;
import com.epam.carrental.service.exception.ServiceException;
import com.epam.carrental.service.impl.InsuranceServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

/**
 * The {@code CarConverter} class implements methods from {@code BaseConverter} interface
 * and makes simple conversion from {@link CarDTO} object to {@link CarDAO} object.
 */
public class CarConverter implements BaseConverter<CarDTO, CarDAO> {
    private static final Logger logger = LogManager.getFormatterLogger(CarConverter.class);
    /**
     * The field holding insurance implementation of Service layer.
     */
    private final BaseService<InsuranceDTO> insuranceImpl = new InsuranceServiceImpl();
    /**
     * Converts {@link CarDAO} object to {@link CarDTO} considering to the foreign key ID.
     *
     * @param carDAO the value to be converted.
     * @return the new {@link CarDTO} object.
     * @throws ServiceException if {@link DAOException} aries.
     */
    @Override
    public CarDTO convert(CarDAO carDAO) throws ServiceException {
        logger.traceEntry("- try to convert Car from DAO to DTO.");
        InsuranceDTO insuranceDTO = insuranceImpl.getById(carDAO.getInsuranceId());
        logger.debug("line(%d), plus Object insuranceDTO: %s", 23, new ObjectMessage(insuranceDTO));

        CarDTO carDto = new CarDTO(carDAO.getId());
        carDto.brand = carDAO.getBrand();
        carDto.model = carDAO.getModel();
        carDto.fuelType = carDAO.getFuelType();
        carDto.gearBox = carDAO.getGearBox();
        carDto.doors = carDAO.getDoors();
        carDto.airConditioning = carDAO.isAirConditioning();
        carDto.trunkCapacity = carDAO.getTrunkCapacity();
        carDto.pricePerDay = carDAO.getPricePerDay();
        carDto.insurance = insuranceDTO;
        carDto.imageURL = carDAO.getImageURL();
        logger.traceExit(" with carDto: %s", new ObjectMessage(carDto));
        return carDto;
    }
    /**
     * Converts {@link CarDTO} object to {@link CarDAO}.
     * All object type references will be changed on {@code Integer} value.
     *
     * @param carDTO the value to be converted.
     * @return the new {@link CarDAO} object with the IDs,
     * which corresponds to the foreign key in a database.
     */
    @Override
    public CarDAO convert(CarDTO carDTO) {
        logger.debug("converting Car from DTO to DAO.");
        return new CarDAO.Builder(carDTO.id).
                brand(carDTO.brand).
                model(carDTO.model).
                fuelType(carDTO.fuelType).
                gearBox(carDTO.gearBox).
                doors(carDTO.doors).
                airConditioning(carDTO.airConditioning).
                trunkCapacity(carDTO.trunkCapacity).
                pricePerDay(carDTO.pricePerDay).
                insuranceId(carDTO.insurance.id).
                imageURL(carDTO.imageURL).build();
    }
}
