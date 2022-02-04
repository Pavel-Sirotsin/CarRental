package com.epam.carrental.service.impl;

import com.epam.carrental.controller.command.util.ParameterManager;
import com.epam.carrental.dao.BaseDAO;
import com.epam.carrental.dao.DAOProvider;
import com.epam.carrental.dao.entity.RejectionDAO;
import com.epam.carrental.dao.exception.DAOException;
import com.epam.carrental.service.RejectionAbstractService;
import com.epam.carrental.service.converter.BaseConverter;
import com.epam.carrental.service.converter.impl.RejectionConverter;
import com.epam.carrental.service.dto.RejectionDTO;
import com.epam.carrental.service.exception.ServiceException;
import com.epam.carrental.service.validator.impl.RejectionValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import java.util.Map;

public class RejectionServiceImpl extends RejectionAbstractService {
    private static final Logger logger = LogManager.getFormatterLogger(RejectionServiceImpl.class);

    private final BaseConverter<RejectionDTO, RejectionDAO> rejectionConverter = new RejectionConverter();

    private final RejectionValidator rejectionValidator = new RejectionValidator();

    private final BaseDAO<RejectionDAO> rejectionDAOImpl = DAOProvider.INSTANCE.getRejectionImpl();


    @Override
    public boolean createById(Map<String, String[]> params) throws ServiceException {
        logger.traceEntry("- try to create rejection using booking id.");
        String bookingId = params.get(ParameterManager.BOOKING_ID_PARAMETER)[0];
        rejectionValidator.validateId(bookingId);
        logger.debug("line(%d), bookingId: %s", 38, bookingId);

        String rejectionReason = params.get(ParameterManager.REJECTION_REASON_PARAMETER)[0];
        rejectionValidator.validateReason(rejectionReason);
        logger.debug("line(%d), rejectionReason: %s", 42, rejectionReason);

        RejectionDTO rejectionDTO = new RejectionDTO(null);
        rejectionDTO.rejectionReason = rejectionReason;
        rejectionDTO.bookingId = Integer.parseInt(bookingId);

        RejectionDAO rejectionDAO = rejectionConverter.convert(rejectionDTO);

        boolean result;
        try {
            result = rejectionDAOImpl.create(rejectionDAO);
        } catch (DAOException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new ServiceException(ex.getMessage(), ex);
        }

        logger.traceExit(" with rejectionDAO: %s", new ObjectMessage(rejectionDAO));
        return result;
    }

    @Override
    public RejectionDTO getById(int index) throws ServiceException {
        logger.traceEntry("- try to get rejection by booking id.");
        RejectionDTO rejection;
        try {
            rejection = rejectionConverter.convert(rejectionDAOImpl.findById(index));
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
        logger.traceExit(" with rejection: %s", new ObjectMessage(rejection));
        return rejection;
    }

}
