package com.epam.carrental.service.impl;

import com.epam.carrental.controller.command.util.ParameterManager;
import com.epam.carrental.dao.AccidentAbstractDAO;
import com.epam.carrental.dao.DAOProvider;
import com.epam.carrental.dao.InsuranceAbstractDAO;
import com.epam.carrental.dao.entity.AccidentDAO;
import com.epam.carrental.dao.entity.InsuranceDAO;
import com.epam.carrental.dao.exception.DAOException;
import com.epam.carrental.service.AccidentAbstractService;
import com.epam.carrental.service.converter.BaseConverter;
import com.epam.carrental.service.converter.impl.AccidentConverter;
import com.epam.carrental.service.converter.impl.InsuranceConverter;
import com.epam.carrental.service.dto.AccidentDTO;
import com.epam.carrental.service.dto.BookingDTO;
import com.epam.carrental.service.dto.InsuranceDTO;
import com.epam.carrental.service.exception.ServiceException;
import com.epam.carrental.service.util.DataCounter;
import com.epam.carrental.service.validator.impl.AccidentValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import java.util.Map;

/**
 * The {@code AccidentServiceImpl} class implements methods from the {@code BaseService} interface
 * and the {@link AccidentAbstractService} class. Is used to manipulate of an accident information
 * at the service layer.
 */
public class AccidentServiceImpl extends AccidentAbstractService {
    private static final Logger logger = LogManager.getFormatterLogger(AccidentServiceImpl.class);
    /**
     * The field holding the accident implementation of the DAO layer.
     */
    private final AccidentAbstractDAO accidentDAOImpl = DAOProvider.INSTANCE.getAccidentImpl();
    /**
     * The field holding the {@link AccidentConverter} implementation.
     */
    private final BaseConverter<AccidentDTO, AccidentDAO> accidentConverter = new AccidentConverter();
    /**
     * The field holding the {@link AccidentValidator} implementation.
     */
    private final AccidentValidator accidentValidator = new AccidentValidator();
    /**
     * The field holding the insurance implementation of the DAO layer.
     */
    private final InsuranceAbstractDAO insuranceDAOImpl = DAOProvider.INSTANCE.getInsuranceImpl();
    /**
     * The field holding the {@link InsuranceConverter} implementation.
     */
    private final BaseConverter<InsuranceDTO, InsuranceDAO> insuranceConverter = new InsuranceConverter();

    @Override
    public AccidentDTO getByBookingId(Integer index) throws ServiceException {
        logger.traceEntry("- try to get an accident by booking id.");
        AccidentDTO accident;
        logger.debug("line(%d), index: %d", 44, index);
        try {
            accident = accidentConverter.convert(accidentDAOImpl.findByBookingId(index));
            logger.debug("Accident" + accident);
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
        logger.traceExit(" with accident: %s", new ObjectMessage(accident));
        return accident;
    }

    @Override
    public boolean createByBookingId(Map<String, String[]> params) throws ServiceException {
        logger.traceEntry("- try to create accident by booking id using insurance data.");
        String bookingId = params.get(ParameterManager.BOOKING_ID_PARAMETER)[0];
        accidentValidator.validateId(bookingId);
        int index = Integer.parseInt(bookingId);
        logger.debug("line(%d), index: %d", 61, index);

        String accidentCost = params.get(ParameterManager.ACCIDENT_COST_PARAMETER)[0];
        accidentValidator.validateAmount(accidentCost);
        Double damageAmount = Double.parseDouble(accidentCost);

        String accidentDescription = params.get(ParameterManager.ACCIDENT_DAMAGE_PARAMETER)[0];
        accidentValidator.validateDamageDescription(accidentDescription);

        InsuranceDTO insuranceDTO;

        try {
            insuranceDTO = insuranceConverter.convert(insuranceDAOImpl.findByBookingId(index));
        } catch (DAOException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new ServiceException(ex.getMessage(), ex);
        }
        logger.debug("line(%d), insuranceDTO: %s", 78, new ObjectMessage(insuranceDTO));
        AccidentDTO accidentDTO = new AccidentDTO(null);
        accidentDTO.damageDescription = accidentDescription;
        accidentDTO.damageAmount = Double.parseDouble(accidentCost);
        accidentDTO.reclaimAmount = DataCounter.countReclaimAmount(insuranceDTO.maxAmount, damageAmount);
        accidentDTO.booking = new BookingDTO(index);

        AccidentDAO accidentDAO = accidentConverter.convert(accidentDTO);
        logger.debug("line(%d), accidentDAO: %s", 86, new ObjectMessage(accidentDAO));

        boolean result;
        try {
            result = accidentDAOImpl.create(accidentDAO);
        } catch (DAOException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new ServiceException(ex.getMessage(), ex);
        }
        logger.traceExit(" done.");
        return result;
    }

    @Override
    public boolean delete(AccidentDTO accident) throws ServiceException {
        logger.traceEntry("- try to delete accident by booking id.");
        boolean result;
        try {
            result = accidentDAOImpl.deleteById(accident.booking.id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        logger.traceExit(" with: %b", result);
        return result;
    }

}
