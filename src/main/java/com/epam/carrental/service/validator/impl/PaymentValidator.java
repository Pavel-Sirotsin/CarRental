package com.epam.carrental.service.validator.impl;

import com.epam.carrental.service.dto.PaymentDTO;
import com.epam.carrental.service.exception.ServiceException;
import com.epam.carrental.service.util.MessageManager;
import com.epam.carrental.service.validator.AbstractValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaymentValidator extends AbstractValidator<PaymentDTO> {
    private static final Logger logger = LogManager.getFormatterLogger(PaymentValidator.class);

    private static final String REGEX_NAME = "^[A-Z&&[^\\d]]{1,20} [A-Z&&[^\\d]]{1,20}$";
    private static final String REGEX_CARD_NUMBER = "^(?<=)[\\d ]{19}$";
    private static final String REGEX_CVV = "^\\d{3}$";
    private static final String REGEX_DATE = "^\\d{2}/\\d{2}$";

    private static final Pattern PATTERN_NAME =
            Pattern.compile(REGEX_NAME, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
    private static final Pattern PATTERN_CARD_NUMBER =
            Pattern.compile(REGEX_CARD_NUMBER, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
    private static final Pattern PATTERN_CVV =
            Pattern.compile(REGEX_CVV, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
    private static final Pattern PATTERN_DATE =
            Pattern.compile(REGEX_DATE, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

    private Matcher matcher;

    @Override
    public void validate(PaymentDTO paymentDTO) throws ServiceException {
        validateHolder(paymentDTO.holderName);
        validateCard(paymentDTO.cardNumber);
        validateDate(paymentDTO.expirationDate);
        validateCvv(paymentDTO.cvv);
    }

    private void validateHolder(String name) throws ServiceException {
        logger.traceEntry("- try to validate payment-name.");
        logger.debug("line(%d), PATTERN_NAME: %s", 42, new ObjectMessage(PATTERN_NAME));
        matcher = PATTERN_NAME.matcher(name);
        logger.debug("line(%d), matcher: %s", 45, new ObjectMessage(matcher));

        if (!matcher.matches()) {
            logger.warn(MessageManager.WRONG_CREDIT_CARD_DATA);
            throw new ServiceException(MessageManager.WRONG_CREDIT_CARD_DATA);
        }

        logger.traceExit("done.");
    }
    private void validateCard(String card) throws ServiceException {
        logger.traceEntry("- try to validate payment-card number.");
        logger.debug("line(%d), PATTERN_CARD_NUMBER: %s", 56, new ObjectMessage(PATTERN_CARD_NUMBER));
        matcher = PATTERN_CARD_NUMBER.matcher(card);
        logger.debug("line(%d), matcher: %s", 58, new ObjectMessage(matcher));

        if (!matcher.matches()) {
            logger.warn(MessageManager.WRONG_CREDIT_CARD_DATA);
            throw new ServiceException(MessageManager.WRONG_CREDIT_CARD_DATA);
        }

        logger.traceExit("done.");
    }
    private void validateDate(String date) throws ServiceException {
        logger.traceEntry("- try to validate payment-expiration date.");
        logger.debug("line(%d), PATTERN_DATE: %s", 69, new ObjectMessage(PATTERN_DATE));
        matcher = PATTERN_DATE.matcher(date);
        logger.debug("line(%d), matcher: %s", 71, new ObjectMessage(matcher));

        if (!matcher.matches()) {
            logger.warn(MessageManager.WRONG_CREDIT_CARD_DATA);
            throw new ServiceException(MessageManager.WRONG_CREDIT_CARD_DATA);
        }

        logger.traceExit("done.");
    }
    private void validateCvv(String cvv) throws ServiceException {
        logger.traceEntry("- try to validate payment-cvv.");
        logger.debug("line(%d), PATTERN_CVV: %s", 82, new ObjectMessage(PATTERN_CVV));
        matcher = PATTERN_CVV.matcher(cvv);
        logger.debug("line(%d), matcher: %s", 84, new ObjectMessage(matcher));

        if (!matcher.matches()) {
            logger.warn(MessageManager.WRONG_CREDIT_CARD_DATA);
            throw new ServiceException(MessageManager.WRONG_CREDIT_CARD_DATA);
        }

        logger.traceExit("done.");
    }
}
