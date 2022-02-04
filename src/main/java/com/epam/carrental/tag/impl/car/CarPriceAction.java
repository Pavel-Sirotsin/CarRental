package com.epam.carrental.tag.impl.car;

import com.epam.carrental.tag.TagCommand;
import com.epam.carrental.service.BaseService;
import com.epam.carrental.service.ServiceProvider;
import com.epam.carrental.service.dto.CarDTO;
import com.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

public class CarPriceAction implements TagCommand {
    private static final Logger logger = LogManager.getFormatterLogger(CarPriceAction.class);

    private final BaseService<CarDTO> carImpl = ServiceProvider.INSTANCE.getCarImpl();

    private static final Double EXCHANGE_RATE = 2.5;

    private static final String LOCALE_RU = "ru_RU";

    @Override
    public String execute(String language, String parameter) throws ServiceException {
        logger.traceEntry("- try to insert <ctg:info> with car price.");
        int index = Integer.parseInt(parameter);
        CarDTO car = carImpl.getById(index);
        logger.debug("line(%d), car: %s", 25, new ObjectMessage(car));

        return language.equals(LOCALE_RU) ?
                String.format("%,.2f", car.pricePerDay * EXCHANGE_RATE) :
                String.format("%,.2f", car.pricePerDay);
    }
}