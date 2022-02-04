package com.epam.carrental.service.converter.impl;

import com.epam.carrental.dao.entity.AccidentDAO;
import com.epam.carrental.dao.exception.DAOException;
import com.epam.carrental.service.BookingAbstractService;
import com.epam.carrental.service.ServiceProvider;
import com.epam.carrental.service.converter.BaseConverter;
import com.epam.carrental.service.dto.AccidentDTO;
import com.epam.carrental.service.dto.BookingDTO;
import com.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;
/**
 * The {@code AccidentConverter} class implements methods from {@code BaseConverter} interface
 * and makes simple conversion from {@link AccidentDTO} object to {@link AccidentDAO} object.
 * */
public class AccidentConverter implements BaseConverter<AccidentDTO, AccidentDAO> {
    private static final Logger logger = LogManager.getFormatterLogger(AccidentConverter.class);
    /**
     * The field holding booking implementation of Service layer.
     */
    private final BookingAbstractService bookingServiceImpl = ServiceProvider.INSTANCE.getBookingImpl();
    /**
     * Converts {@link AccidentDAO} object to {@link AccidentDTO} considering to the foreign key ID.
     * @param accidentDAO the value to be converted.
     * @return the new {@link AccidentDTO} object.
     * @throws ServiceException if {@link DAOException} aries.
     */
    @Override
    public AccidentDTO convert(AccidentDAO accidentDAO) throws ServiceException {
        logger.traceEntry("- try to convert Accident from DAO to DTO.");
        BookingDTO bookingDTO = bookingServiceImpl.getById(accidentDAO.getBookingId());
        logger.debug("line(%d), plus Object bookingDTO: %s", 22, new ObjectMessage(bookingDTO));

        AccidentDTO accidentDTO = new AccidentDTO(accidentDAO.getId());
        accidentDTO.damageDescription = accidentDAO.getDamageDescription();
        accidentDTO.damageAmount = accidentDAO.getDamageAmount();
        accidentDTO.reclaimAmount = accidentDAO.getReclaimAmount();
        accidentDTO.booking = bookingDTO;
        logger.traceExit(" with accidentDTO: %s", new ObjectMessage(accidentDTO));
        return accidentDTO;
    }
    /**
     * Converts {@link AccidentDTO} object to {@link AccidentDAO}.
     * All object type references will be changed on {@code Integer} value.
     * @param    accidentDTO the value to be converted.
     * @return   the {@link AccidentDAO} object with the IDs,
     *           which corresponds to the foreign key in a database.
     */
    @Override
    public AccidentDAO convert(AccidentDTO accidentDTO) {
        logger.debug("converting DTo to DAO.");
        return new AccidentDAO(accidentDTO.id,
                accidentDTO.damageDescription,
                accidentDTO.damageAmount,
                accidentDTO.reclaimAmount,
                accidentDTO.booking.id);
    }
}
