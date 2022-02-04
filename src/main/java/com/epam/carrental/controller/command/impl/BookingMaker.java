package com.epam.carrental.controller.command.impl;

import com.epam.carrental.controller.HttpServletHandler;
import com.epam.carrental.controller.command.Command;
import com.epam.carrental.controller.command.util.AttributeManager;
import com.epam.carrental.controller.command.util.PageManager;
import com.epam.carrental.controller.command.util.ParameterManager;
import com.epam.carrental.service.BookingAbstractService;
import com.epam.carrental.service.PaymentAbstractService;
import com.epam.carrental.service.ServiceProvider;
import com.epam.carrental.service.dto.AccountDTO;
import com.epam.carrental.service.dto.BookingDTO;
import com.epam.carrental.service.dto.CarDTO;
import com.epam.carrental.service.dto.UserDTO;
import com.epam.carrental.service.exception.ServiceException;
import com.epam.carrental.service.util.DataCounter;
import com.epam.carrental.service.validator.impl.AccountValidator;
import com.epam.carrental.service.validator.impl.BookingValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class BookingMaker implements Command {
    private static final Logger logger = LogManager.getFormatterLogger(BookingMaker.class);

    private static final String PAY_NOW = "now";
    private static final String PAY_LATER = "later";

    private final BookingValidator bookingValidator = new BookingValidator();
    private final AccountValidator accountValidator = new AccountValidator();

    private final BookingAbstractService bookingServiceImpl = ServiceProvider.INSTANCE.getBookingImpl();
    private final PaymentAbstractService paymentServiceImpl = ServiceProvider.INSTANCE.getPaymentImpl();

    @Override
    public void execute(HttpServletHandler handler) throws ServletException, IOException {
        logger.traceEntry("- try to make a new order.");
        HttpSession session = handler.getSession();
        CarDTO carDTO = (CarDTO) session.getAttribute(AttributeManager.CAR_SESSION_ATTRIBUTE);
        logger.debug("line(%d), carDTO: %s", 37, new ObjectMessage(carDTO));

        if (carDTO == null) {
            handler.sendRedirect(PageManager.INDEX_PATH);
        } else {
            UserDTO userDTO = (UserDTO) session.getAttribute(AttributeManager.USER_SESSION_ATTRIBUTE);

            String payment = handler.getParameter(ParameterManager.PAYMENT_PARAMETER);
            String rentalDate = handler.getParameter(ParameterManager.RENTAL_DATE_PARAMETER);
            String returnDate = handler.getParameter(ParameterManager.RETURN_DATE_PARAMETER);
            String rentalLocation = handler.getParameter(ParameterManager.RENTAL_LOCATION_PARAMETER);
            String returnLocation = handler.getParameter(ParameterManager.RETURN_LOCATION_PARAMETER);
            String name = handler.getParameter(ParameterManager.NAME_PARAMETER);
            String surname = handler.getParameter(ParameterManager.SURNAME_PARAMETER);
            String email = handler.getParameter(ParameterManager.EMAIL_PARAMETER);
            String phone = handler.getParameter(ParameterManager.PHONE_PARAMETER);
            String license = handler.getParameter(ParameterManager.LICENSE_PARAMETER);

            BookingDTO bookingDTO = new BookingDTO(null);
            bookingDTO.rentalDate = rentalDate;
            bookingDTO.rentalLocation = rentalLocation;
            bookingDTO.returnDate = returnDate;
            bookingDTO.returnLocation = returnLocation;
            bookingDTO.paid = false;
            bookingDTO.rejected = false;
            bookingDTO.car = carDTO;
            bookingDTO.accidentFree = true;

            try {

                if (userDTO == null) {
                    AccountDTO anonymous = new AccountDTO(null);
                    anonymous.name = name;
                    anonymous.surname = surname;
                    anonymous.email = email;
                    anonymous.phoneNumber = phone;
                    anonymous.drivingLicense = license;
                    bookingDTO.account = anonymous;
                    logger.debug("line(%d), booking by anonymous account: %s", 74, new ObjectMessage(anonymous));

                    bookingValidator.validate(bookingDTO);
                    accountValidator.validate(bookingDTO.account);
                    bookingDTO.daysAmount = DataCounter.countDay(bookingDTO.rentalDate, bookingDTO.returnDate);
                    bookingDTO.sum = DataCounter.countSum(bookingDTO.daysAmount, bookingDTO.car);
                    bookingDTO.paid = true;

                    session.setAttribute(AttributeManager.BOOKING_SESSION_ATTRIBUTE, bookingDTO);
                    logger.debug("forward to the payment block page.");
                    handler.sendRedirect(PageManager.PAYMENT_PATH);

                } else {
                    bookingDTO.account = userDTO.account;
                    logger.debug("line(%d), booking by registered account: %s", 96, new ObjectMessage(userDTO.account));

                    bookingDTO.id = bookingServiceImpl.createReturnID(bookingDTO);
                    session.setAttribute(AttributeManager.BOOKING_SESSION_ATTRIBUTE, bookingDTO);

                    if (payment.equals(PAY_NOW)) {
                        logger.debug("forward to the payment block page.");
                        handler.sendRedirect(PageManager.PAYMENT_PATH);
                    } else if (payment.equals(PAY_LATER)) {
                        logger.debug("forward to the result page.");
                        handler.sendRedirect(PageManager.RESULT_PATH);
                    }

                }
            } catch (ServiceException ex) {
                logger.throwing(Level.ERROR, ex);
                handler.setAttribute(AttributeManager.BOOKING_RESULT_ATTRIBUTE, ex.getMessage());
                handler.forward(PageManager.BOOKING_FORM_PAGE_PATH);
            }
        }
        logger.traceExit("done.");
    }

}
