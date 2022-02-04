package com.epam.carrental.controller.command.impl;

import com.epam.carrental.controller.HttpServletHandler;
import com.epam.carrental.controller.command.Command;
import com.epam.carrental.controller.command.util.AttributeManager;
import com.epam.carrental.controller.command.util.PageManager;
import com.epam.carrental.controller.command.util.ParameterManager;
import com.epam.carrental.service.BookingAbstractService;
import com.epam.carrental.service.ServiceProvider;
import com.epam.carrental.service.dto.BookingDTO;
import com.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PaymentFromProfile implements Command {
    private static final Logger logger = LogManager.getFormatterLogger(PaymentFromProfile.class);

    private final BookingAbstractService bookingImpl = ServiceProvider.INSTANCE.getBookingImpl();

    @Override
    public void execute(HttpServletHandler handler) throws ServletException, IOException {
        logger.traceEntry("- try to make payment from profile.");
        HttpSession session = handler.getSession();
        String bookingId = handler.getParameter(ParameterManager.BOOKING_ID_PARAMETER);
        int index = Integer.parseInt(bookingId);
        logger.debug("Booking id: %d", index);
        BookingDTO bookingDTO;

        try {
            bookingDTO = bookingImpl.getById(index);
            logger.debug("line(%d), bookingDTO: %s", 37, new ObjectMessage(bookingDTO));
            session.setAttribute(AttributeManager.BOOKING_SESSION_ATTRIBUTE, bookingDTO);
            logger.debug("forward to payment block page.");
            handler.forward(PageManager.PAYMENT_BLOCK_PAGE_PATH);
        } catch (ServiceException ex) {
            logger.throwing(Level.ERROR, ex);
            handler.setAttribute(AttributeManager.ID_ATTRIBUTE, ex.getMessage());
            handler.forward(PageManager.PROFILE_PAGE_PATH);
        }
        logger.traceExit("done ");
    }
}
