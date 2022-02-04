package com.epam.carrental.service.validator.impl;

import com.epam.carrental.service.dto.AccountDTO;
import com.epam.carrental.service.exception.ServiceException;
import com.epam.carrental.service.util.MessageManager;
import com.epam.carrental.service.validator.AbstractValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountValidator extends AbstractValidator<AccountDTO> {
    private static final Logger logger = LogManager.getFormatterLogger(AccountValidator.class);

    private static final String REGEX_NAME = "^[\\w -[а-яА-Я]&&[^\\d]]{1,30}$";
    private static final String REGEX_SURNAME = "^[\\w -[а-яА-Я]&&[^\\d]]{1,30}$";
    private static final String REGEX_EMAIL = "^[\\w_.-]+@[a-z0-9_-]+\\.[a-z]{2,6}$";
    private static final String REGEX_PHONE = "^\\+(?:[0-9]-?){6,14}[0-9]$";
    private static final String REGEX_LICENSE = "^[\\w ]{1,20}$";

    private static final Pattern PATTERN_NAME =
            Pattern.compile(REGEX_NAME, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
    private static final Pattern PATTERN_SURNAME =
            Pattern.compile(REGEX_SURNAME, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
    private static final Pattern PATTERN_PHONE =
            Pattern.compile(REGEX_PHONE, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
    private static final Pattern PATTERN_EMAIL =
            Pattern.compile(REGEX_EMAIL, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
    private static final Pattern PATTERN_LICENSE =
            Pattern.compile(REGEX_LICENSE, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

    private Matcher matcher;

    @Override
    public void validate(AccountDTO account) throws ServiceException {
        validateName(account.name);
        validateSurname(account.surname);
        validateEmail(account.email);
        validatePhone(account.phoneNumber);
        validateLicense(account.drivingLicense);
    }

    public void validateName(String name) throws ServiceException {
        logger.traceEntry("- try to validate account-name.");
        matcher = PATTERN_NAME.matcher(name);
        logger.debug("line(%d), PATTERN_NAME: %s", 48, new ObjectMessage(PATTERN_NAME));

        if (!matcher.matches()) {
            logger.warn(MessageManager.WRONG_NAME_FORMAT);
            throw new ServiceException(MessageManager.WRONG_NAME_FORMAT);
        }

        logger.traceExit("done.");
    }

    public void validateSurname(String surname) throws ServiceException {
        logger.traceEntry("- try to validate account-surname.");
        matcher = PATTERN_SURNAME.matcher(surname);
        logger.debug("line(%d), PATTERN_SURNAME: %s", 63, new ObjectMessage(PATTERN_SURNAME));

        if (!matcher.matches()) {
            logger.warn(MessageManager.WRONG_SURNAME_FORMAT);
            throw new ServiceException(MessageManager.WRONG_SURNAME_FORMAT);
        }

        logger.traceExit("done.");
    }

    public void validateEmail(String email) throws ServiceException {
        logger.traceEntry("- try to validate account-email.");
        matcher = PATTERN_EMAIL.matcher(email);
        logger.debug("line(%d), PATTERN_EMAIL: %s", 77, new ObjectMessage(PATTERN_EMAIL));

        if (!matcher.matches()) {
            logger.warn(MessageManager.WRONG_EMAIL_FORMAT);
            throw new ServiceException(MessageManager.WRONG_EMAIL_FORMAT);
        }

        logger.traceExit("done.");
    }

    public void validatePhone(String phone) throws ServiceException {
        logger.traceEntry("- try to validate account-phone.");
        matcher = PATTERN_PHONE.matcher(phone);
        logger.debug("line(%d), PATTERN_PHONE: %s", 91, new ObjectMessage(PATTERN_PHONE));

        if (!matcher.matches()) {
            logger.warn(MessageManager.WRONG_PHONE_FORMAT);
            throw new ServiceException(MessageManager.WRONG_PHONE_FORMAT);
        }

        logger.traceExit("done.");
    }

    public void validateLicense(String license) throws ServiceException {
        logger.traceEntry("- try to validate account-license.");
        matcher = PATTERN_LICENSE.matcher(license);
        logger.debug("line(%d), PATTERN_LICENSE: %s", 105, new ObjectMessage(PATTERN_LICENSE));

        if (!matcher.matches()) {
            logger.warn(MessageManager.WRONG_LICENSE_FORMAT);
            throw new ServiceException(MessageManager.WRONG_LICENSE_FORMAT);
        }

        logger.traceExit("done.");
    }
}
