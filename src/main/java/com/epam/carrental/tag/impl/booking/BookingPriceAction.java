package com.epam.carrental.tag.impl.booking;

import com.epam.carrental.tag.TagCommand;
import com.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BookingPriceAction implements TagCommand {
    private static final Logger logger = LogManager.getFormatterLogger(BookingPriceAction.class);

    private static final Double EXCHANGE_RATE = 2.5;

    private static final String LOCALE_RU = "ru_RU";

    @Override
    public String execute(String language, String parameter) throws ServiceException {
        logger.traceEntry("- try to insert <ctg:info> with booking price.");
        Double sum = Double.parseDouble(parameter);
        logger.traceExit("done with booking cost: %,.2f", sum);
        return language.equals(LOCALE_RU) ?
                String.format("%.2f", sum * EXCHANGE_RATE) :
                String.format("%.2f", sum);
    }
}
