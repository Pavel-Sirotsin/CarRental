package com.epam.carrental.service.validator.impl;

import com.epam.carrental.service.dto.AccidentDTO;
import com.epam.carrental.service.exception.ServiceException;
import com.epam.carrental.service.util.MessageManager;
import com.epam.carrental.service.validator.AbstractValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccidentValidator extends AbstractValidator<AccidentDTO> {
    private static final Logger logger = LogManager.getFormatterLogger(AccidentValidator.class);

    private static final String REGEX_ACCIDENT_DAMAGE = "^[\\w\\s\\d\\p{Punct}[а-яА-Я]]{1,250}$";
    private static final String REGEX_ACCIDENT_AMOUNT = "^\\d{1,4}.\\d{0,4}$";

    private static final Pattern PATTERN_ACCIDENT_DAMAGE =
            Pattern.compile(REGEX_ACCIDENT_DAMAGE, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

    private static final Pattern PATTERN_ACCIDENT_AMOUNT =
            Pattern.compile(REGEX_ACCIDENT_AMOUNT, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

    private Matcher matcher;

    public void validateDamageDescription(String content) throws ServiceException {
        logger.traceEntry("- try to validate accident-damage-description.");
        matcher = PATTERN_ACCIDENT_DAMAGE.matcher(content);
        logger.debug("line(%d), PATTERN_ACCIDENT_DAMAGE: %s", 34, new ObjectMessage(PATTERN_ACCIDENT_DAMAGE));

        if (!matcher.matches()) {
            logger.warn(MessageManager.WRONG_CONTENT_FORMAT);
            throw new ServiceException(MessageManager.WRONG_CONTENT_FORMAT);
        }

        logger.traceExit("done.");
    }

    public void validateAmount(String content) throws ServiceException {
        logger.traceEntry("- try to validate accident-amount.");
        matcher = PATTERN_ACCIDENT_AMOUNT.matcher(content);
        logger.debug("line(%d), PATTERN_ACCIDENT_AMOUNT: %s", 49, new ObjectMessage(PATTERN_ACCIDENT_AMOUNT));

        if (!matcher.matches()) {
            logger.warn(MessageManager.WRONG_NUMBER_FORMAT);
            throw new ServiceException(MessageManager.WRONG_NUMBER_FORMAT);
        }

        logger.traceExit("done.");
    }
}
