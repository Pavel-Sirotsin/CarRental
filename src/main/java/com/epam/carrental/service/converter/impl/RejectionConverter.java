package com.epam.carrental.service.converter.impl;

import com.epam.carrental.dao.entity.RejectionDAO;
import com.epam.carrental.service.converter.BaseConverter;
import com.epam.carrental.service.dto.RejectionDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;
/**
 * The {@code RejectionConverter} class implements methods from {@code BaseConverter} interface
 * and makes simple conversion from {@link RejectionDTO} object to {@link RejectionDAO} object.
 */
public class RejectionConverter implements BaseConverter<RejectionDTO, RejectionDAO> {
    private static final Logger logger = LogManager.getFormatterLogger(RejectionConverter.class);
    /**
     * Converts {@link RejectionDAO} object to {@link RejectionDTO}.
     *
     * @param rejectionDAO the value to be converted.
     * @return the new {@link RejectionDTO} object.
     */
    @Override
    public RejectionDTO convert(RejectionDAO rejectionDAO) {
        logger.traceEntry("- try to convert Rejection from DAO to DTO.");
        RejectionDTO rejectionDTO = new RejectionDTO(rejectionDAO.getId());
        rejectionDTO.rejectionReason = rejectionDAO.getRejectionReason();
        rejectionDTO.bookingId = rejectionDAO.getBookingId();
        logger.traceExit(" with rejectionDTO: %s", new ObjectMessage(rejectionDTO));
        return rejectionDTO;
    }
    /**
     * Converts {@link RejectionDTO} object to {@link RejectionDAO}.
     * All object type references will be changed on {@code Integer} value.
     *
     * @param rejectionDTO the value to be converted.
     * @return the new {@link RejectionDAO} object.
     */
    @Override
    public RejectionDAO convert(RejectionDTO rejectionDTO) {
        logger.debug(" try to convert Rejection from DTO to DAO.");
        return new RejectionDAO(rejectionDTO.id,
                rejectionDTO.rejectionReason,
                rejectionDTO.bookingId);
    }
}
