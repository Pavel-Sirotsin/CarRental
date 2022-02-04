package com.epam.carrental.controller.command.impl;

import com.epam.carrental.controller.HttpServletHandler;
import com.epam.carrental.controller.command.Command;
import com.epam.carrental.controller.command.util.AttributeManager;
import com.epam.carrental.controller.command.util.PageManager;
import com.epam.carrental.controller.command.util.ParameterManager;
import com.epam.carrental.service.PaymentAbstractService;
import com.epam.carrental.service.ServiceProvider;
import com.epam.carrental.service.dto.BookingDTO;
import com.epam.carrental.service.dto.PaymentDTO;
import com.epam.carrental.service.exception.ServiceException;
import com.epam.carrental.service.util.MessageManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PaymentMaker implements Command {
    private static final Logger logger = LogManager.getFormatterLogger(PaymentMaker.class);

    private final PaymentAbstractService paymentImpl = ServiceProvider.INSTANCE.getPaymentImpl();

    @Override
    public void execute(HttpServletHandler handler) throws ServletException, IOException {
        logger.traceEntry("- try to make a payment.");
        HttpSession session = handler.getSession();
        BookingDTO bookingDTO = (BookingDTO) session.getAttribute(AttributeManager.BOOKING_SESSION_ATTRIBUTE);
        logger.debug("line(%d), bookingDTO: %s", 33, new ObjectMessage(bookingDTO));

        if (bookingDTO == null) {
            handler.sendRedirect(PageManager.INDEX_PATH);
        } else {
            String holder = handler.getParameter(ParameterManager.HOLDER_PARAMETER);
            String number = handler.getParameter(ParameterManager.CARD_NUMBER_PARAMETER);
            String date = handler.getParameter(ParameterManager.EXPIRATION_DATE_PARAMETER);
            String cvv = handler.getParameter(ParameterManager.CARD_CVV_PARAMETER);

            PaymentDTO paymentDTO = new PaymentDTO(null);
            paymentDTO.holderName = holder;
            paymentDTO.cardNumber = number;
            paymentDTO.expirationDate = date;
            paymentDTO.cvv = cvv;
            paymentDTO.sum = bookingDTO.sum;
            paymentDTO.booking = bookingDTO;
            logger.debug("line(%d), paymentDTO: %s", 51, new ObjectMessage(paymentDTO));

            try {

                if (bookingDTO.account.id == null) {
                    logger.debug("anonymous order.");
                    paymentImpl.createByAnonymous(paymentDTO);
                } else {
                    logger.debug("order from client.");
                    paymentImpl.create(paymentDTO);
                }

                session.removeAttribute(AttributeManager.BOOKING_SESSION_ATTRIBUTE);
                session.setAttribute(AttributeManager.PAYMENT_RESULT_ATTRIBUTE, MessageManager.SUCCESS_PAYMENT);
                logger.debug("redirect to index page with result: %s", MessageManager.SUCCESS_PAYMENT);
                handler.sendRedirect(PageManager.INDEX_PATH);

            } catch (ServiceException ex) {
                logger.throwing(Level.ERROR, ex);
                handler.setAttribute(AttributeManager.PAYMENT_RESULT_ATTRIBUTE, ex.getMessage());
                handler.forward(PageManager.PAYMENT_BLOCK_PAGE_PATH);
            }
        }
        logger.traceExit("done.");
    }
}
