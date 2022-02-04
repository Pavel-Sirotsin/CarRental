package com.epam.carrental.service.validator;

import com.epam.carrental.service.dto.EntityDTO;
import com.epam.carrental.service.exception.ServiceException;
import com.epam.carrental.service.util.MessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractValidator<T extends EntityDTO> {
    private static final Logger logger = LogManager.getLogger(AbstractValidator.class);
    private static final String REGEX_ID = "^\\d{1,1000}$";

    private static final Pattern PATTERN_ID = Pattern.compile(REGEX_ID);

    public void validateId(String index) throws ServiceException {
        logger.traceEntry("- try to validate EntityDTO id.");
        logger.debug("line(%d), PATTERN_ID: %s", 23, new ObjectMessage(PATTERN_ID));
        Matcher matcher = PATTERN_ID.matcher(String.valueOf(index));
        logger.debug("line(%d), matcher: %s", 23, new ObjectMessage(matcher));

        if (!matcher.matches()) {
            logger.error(MessageManager.WRONG_ID);
            throw new ServiceException(MessageManager.WRONG_ID);
        }

        logger.traceExit("done.");
    }

    protected void validate(T entity) throws ServiceException{};

}
