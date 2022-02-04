package com.epam.carrental.service.converter.impl;

import com.epam.carrental.dao.entity.PaymentDAO;
import com.epam.carrental.dao.exception.DAOException;
import com.epam.carrental.service.BookingAbstractService;
import com.epam.carrental.service.ServiceProvider;
import com.epam.carrental.service.converter.BaseConverter;
import com.epam.carrental.service.dto.BookingDTO;
import com.epam.carrental.service.dto.PaymentDTO;
import com.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

/**
 * The {@code PaymentConverter} class implements methods from {@code BaseConverter} interface
 * and makes simple conversion from {@link PaymentDTO} object to {@link PaymentDAO} object.
 */
public class PaymentConverter implements BaseConverter<PaymentDTO, PaymentDAO> {
    private static final Logger logger = LogManager.getFormatterLogger(PaymentConverter.class);
    /**
     * The field holding booking implementation of Service layer.
     */
    private final BookingAbstractService bookingServiceImpl = ServiceProvider.INSTANCE.getBookingImpl();
    /**
     * Converts {@link PaymentDAO} object to {@link PaymentDTO} considering to the foreign key ID.
     *
     * @param paymentDAO the value to be converted.
     * @return the new {@link PaymentDTO} object.
     * @throws ServiceException if {@link DAOException} aries.
     */
    @Override
    public PaymentDTO convert(PaymentDAO paymentDAO) throws ServiceException {
        logger.traceEntry("- try to convert Payment from DAO to DTO.");
        BookingDTO bookingDTO = bookingServiceImpl.getById(paymentDAO.getBookingId());

        PaymentDTO paymentDTO = new PaymentDTO(paymentDAO.getId());
        paymentDTO.holderName = paymentDAO.getHolderName();
        paymentDTO.cardNumber = paymentDAO.getCardNumber();
        paymentDTO.expirationDate = paymentDAO.getExpirationDate();
        paymentDTO.cvv = paymentDAO.getCvv();
        paymentDTO.sum = paymentDAO.getSum();
        paymentDTO.reclamation = paymentDAO.getReclamation();
        paymentDTO.booking = bookingDTO;
        logger.traceExit(" with paymentDTO: %s", new ObjectMessage(paymentDTO));
        return paymentDTO;
    }
    /**
     * Converts {@link PaymentDTO} object to {@link PaymentDAO}.
     * All object type references will be changed on {@code Integer} value.
     *
     * @param paymentDTO the value to be converted.
     * @return the new {@link PaymentDAO} object with the IDs,
     * which corresponds to the foreign key in a database.
     */
    @Override
    public PaymentDAO convert(PaymentDTO paymentDTO) {
        logger.debug("try to convert Payment from DTO to DAO.");
        return new PaymentDAO(paymentDTO.id,
                paymentDTO.holderName,
                paymentDTO.cardNumber,
                paymentDTO.expirationDate,
                paymentDTO.cvv,
                paymentDTO.sum,
                paymentDTO.reclamation,
                paymentDTO.booking.id);
    }
}
