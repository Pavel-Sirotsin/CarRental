package com.epam.carrental.tag.impl.car;

import com.epam.carrental.tag.TagCommand;
import com.epam.carrental.service.BaseService;
import com.epam.carrental.service.ServiceProvider;
import com.epam.carrental.service.dto.CarDTO;
import com.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

public class CarInsurancePriceAction implements TagCommand {
    private static final Logger logger = LogManager.getFormatterLogger(CarInsurancePriceAction.class);

    private final BaseService<CarDTO> carImpl = ServiceProvider.INSTANCE.getCarImpl();

    private static final Double EXCHANGE_RATE = 2.5;

    private static final String LOCALE_RU = "ru-RU";

    @Override
    public String execute(String language, String parameter) throws ServiceException {
        logger.traceEntry("- try to insert <ctg:info> with insurance price.");
        int index = Integer.parseInt(parameter);
        logger.debug("Car id: %d", index);

        CarDTO car = carImpl.getById(index);
        language = language.replace("_", "-");
        logger.debug("line(%d), car insurance: %s", 29, new ObjectMessage(car.insurance));

        return language.equals(LOCALE_RU) ?
                String.format("%.2f", car.insurance.cost * EXCHANGE_RATE) :
                String.format("%.2f", car.insurance.cost);
    }
}

