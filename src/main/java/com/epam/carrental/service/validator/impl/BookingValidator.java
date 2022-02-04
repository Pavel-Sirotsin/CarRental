package com.epam.carrental.service.validator.impl;

import com.epam.carrental.service.dto.BookingDTO;
import com.epam.carrental.service.exception.ServiceException;
import com.epam.carrental.service.util.MessageManager;
import com.epam.carrental.service.validator.AbstractValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookingValidator extends AbstractValidator<BookingDTO> {
    private static final Logger logger = LogManager.getFormatterLogger(BookingValidator.class);

    private static final String REGEX_DATE = "^\\d{4}[.-]\\d{2}[.-]\\d{2}[ T]{1}\\d{2}:\\d{2}$";
    private static final String REGEX_LOCATION = "^[\\w /&&[^\\d]]{1,30}$";

    private static final Integer MIN_DAYS = 1;

    private static final LocalDateTime NOW = LocalDateTime.now().minusDays(1);

    private static final Pattern PATTERN_DATE =
            Pattern.compile(REGEX_DATE, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
    private static final Pattern PATTERN_LOCATION =
            Pattern.compile(REGEX_LOCATION, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.UNICODE_CHARACTER_CLASS);

    private Matcher matcher;

    @Override
    public void validate(BookingDTO bookingDTO) throws ServiceException {
        validateDate(bookingDTO.rentalDate);
        validateDate(bookingDTO.returnDate);
        validateOutdated(bookingDTO);
        validateLocation(bookingDTO.rentalLocation);
        validateLocation(bookingDTO.returnLocation);
    }

    private void validateDate(String date) throws ServiceException {
        logger.traceEntry("- try to validate orderId date.");
        logger.debug("line(%d), PATTERN_DATE: %s", 44, new ObjectMessage(PATTERN_DATE));
        matcher = PATTERN_DATE.matcher(date);
        logger.debug("line(%d), matcher: %s", 47, new ObjectMessage(matcher));


        if (!matcher.matches()) {
            logger.warn(MessageManager.WRONG_DATE_FORMAT);
            throw new ServiceException(MessageManager.WRONG_DATE_FORMAT);
        }
        logger.traceExit("done.");
    }

    private void validateOutdated(BookingDTO bookingDTO) throws ServiceException {
        logger.traceEntry("- try to validate date on outdated.");
        LocalDateTime rentalDate = LocalDateTime.
                parse(bookingDTO.rentalDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateTime returnDate = LocalDateTime.
                parse(bookingDTO.returnDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        if (rentalDate.isAfter(returnDate)) {
            logger.warn(MessageManager.WRONG_DATE_FORMAT);
            throw new ServiceException(MessageManager.WRONG_DATE_DATA);
        }

        if (rentalDate.isBefore(NOW)) {
            logger.warn(MessageManager.WRONG_DATE_FORMAT);
            throw new ServiceException(MessageManager.WRONG_DATE_DATA);
        }

        if (ChronoUnit.DAYS.between(rentalDate, returnDate) < MIN_DAYS) {
            logger.warn(MessageManager.WRONG_DATE_FORMAT);
            throw new ServiceException(MessageManager.WRONG_MIN_DAYS);
        }
        logger.traceExit(" done.");
    }

    private void validateLocation(String location) throws ServiceException {
        logger.traceEntry("- try to validate order location.");
        logger.debug("line(%d), PATTERN_LOCATION: %s", 83, new ObjectMessage(PATTERN_LOCATION));
        matcher = PATTERN_LOCATION.matcher(location);
        logger.debug("line(%d), matcher: %s", 85, new ObjectMessage(matcher));

        if (!matcher.matches()) {
            logger.warn(MessageManager.WRONG_LOCATION);
            throw new ServiceException(MessageManager.WRONG_LOCATION);
        }

        logger.traceExit("done.");
    }
}
