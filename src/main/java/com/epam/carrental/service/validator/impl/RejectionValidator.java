package com.epam.carrental.service.validator.impl;

import com.epam.carrental.service.dto.RejectionDTO;
import com.epam.carrental.service.exception.ServiceException;
import com.epam.carrental.service.util.MessageManager;
import com.epam.carrental.service.validator.AbstractValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RejectionValidator extends AbstractValidator<RejectionDTO> {
    private static final Logger logger = LogManager.getFormatterLogger(RejectionValidator.class);

    private static final String REGEX_REJECTION_REASON = "^[\\w\\s\\d\\p{Punct}[а-яА-Я]]{1,250}$";

    private static final Pattern PATTERN_REJECTION_REASON =
            Pattern.compile(REGEX_REJECTION_REASON, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

    private Matcher matcher;

    public void validateReason(String content) throws ServiceException {
        logger.traceEntry("- try to validate rejection-reason.");
        logger.debug("line(%d), PATTERN_REJECTION_REASON: %s", 31, new ObjectMessage(PATTERN_REJECTION_REASON));
        matcher = PATTERN_REJECTION_REASON.matcher(content);
        logger.debug("line(%d), matcher: %s", 34, new ObjectMessage(matcher));

        if (!matcher.matches()) {
            logger.warn(MessageManager.WRONG_CONTENT_FORMAT);
            throw new ServiceException(MessageManager.WRONG_CONTENT_FORMAT);
        }

        logger.traceExit("done.");
    }
}
