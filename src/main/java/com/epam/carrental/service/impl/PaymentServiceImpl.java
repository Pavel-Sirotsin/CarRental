package com.epam.carrental.service.impl;

import com.epam.carrental.dao.DAOProvider;
import com.epam.carrental.dao.PaymentAbstractDAO;
import com.epam.carrental.dao.entity.AccountDAO;
import com.epam.carrental.dao.entity.BookingDAO;
import com.epam.carrental.dao.entity.PaymentDAO;
import com.epam.carrental.dao.exception.DAOException;
import com.epam.carrental.service.PaymentAbstractService;
import com.epam.carrental.service.converter.BaseConverter;
import com.epam.carrental.service.converter.impl.AccountConverter;
import com.epam.carrental.service.converter.impl.BookingConverter;
import com.epam.carrental.service.converter.impl.PaymentConverter;
import com.epam.carrental.service.dto.AccountDTO;
import com.epam.carrental.service.dto.BookingDTO;
import com.epam.carrental.service.dto.PaymentDTO;
import com.epam.carrental.service.exception.ServiceException;
import com.epam.carrental.service.validator.impl.PaymentValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

public class PaymentServiceImpl extends PaymentAbstractService {
    private static final Logger logger = LogManager.getFormatterLogger(PaymentServiceImpl.class);

    private final PaymentAbstractDAO paymentDAOImpl = DAOProvider.INSTANCE.getPaymentImpl();

    private final BaseConverter<PaymentDTO, PaymentDAO> paymentConverter = new PaymentConverter();
    private final BaseConverter<BookingDTO, BookingDAO> bookingConverter = new BookingConverter();
    private final BaseConverter<AccountDTO, AccountDAO> accountConverter = new AccountConverter();

    private final PaymentValidator paymentValidator = new PaymentValidator();

    @Override
    public PaymentDTO getById(int index) throws ServiceException {
        logger.traceEntry("- try to get payment by booking id.");
        PaymentDTO payment;
        try {
            payment = paymentConverter.convert(paymentDAOImpl.findByBookingId(index));
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
        logger.traceExit(" with payment: %s", new ObjectMessage(payment));
        return payment;
    }

    @Override
    public boolean create(PaymentDTO paymentDTO) throws ServiceException {
        logger.traceEntry("- try to create payment using booking id.");
        paymentValidator.validate(paymentDTO);

        PaymentDAO paymentDAO = paymentConverter.convert(paymentDTO);

        boolean result;
        try {
            result = paymentDAOImpl.create(paymentDAO);
        } catch (DAOException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new ServiceException(ex.getMessage(), ex);
        }

        logger.traceExit(" done.");
        return result;
    }

    @Override
    public boolean update(PaymentDTO paymentDTO) throws ServiceException {
        logger.traceEntry("- try to update payment.");
        PaymentDAO paymentDAO = paymentConverter.convert(paymentDTO);

        boolean result;
        try {
            result = paymentDAOImpl.update(paymentDAO);
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }

        logger.traceExit("done.");
        return result;
    }

    @Override
    public boolean createByAnonymous(PaymentDTO paymentDTO) throws ServiceException {
        logger.traceEntry("- try to create a new payment with anonymous account.");
        paymentValidator.validate(paymentDTO);

        PaymentDAO paymentDAO = paymentConverter.convert(paymentDTO);
        logger.debug("line(%d), paymentDAO: %s", 93, new ObjectMessage(paymentDAO));
        BookingDAO bookingDAO = bookingConverter.convert(paymentDTO.booking);
        logger.debug("line(%d), bookingDAO: %s", 95, new ObjectMessage(bookingDAO));
        AccountDAO accountDAO = accountConverter.convert(paymentDTO.booking.account);
        logger.debug("line(%d), accountDAO: %s", 97, new ObjectMessage(accountDAO));

        boolean result;
        try {
            result = paymentDAOImpl.createByAnonymous(bookingDAO, accountDAO, paymentDAO);
        } catch (DAOException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new ServiceException(ex.getMessage(), ex);
        }

        logger.traceExit(" done.");
        return result;
    }

    @Override
    public boolean delete(PaymentDTO paymentDTO) throws ServiceException {
        logger.traceEntry("- try to delete payment.");
        boolean result;
        try {
            result = paymentDAOImpl.deleteById(paymentDTO.id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        logger.traceExit(" with: %b", result);
        return result;
    }
}
