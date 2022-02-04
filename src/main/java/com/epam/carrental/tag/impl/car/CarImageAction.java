package com.epam.carrental.tag.impl.car;

import com.epam.carrental.tag.TagCommand;
import com.epam.carrental.service.BaseService;
import com.epam.carrental.service.dto.CarDTO;
import com.epam.carrental.service.exception.ServiceException;
import com.epam.carrental.service.impl.CarServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

public class CarImageAction implements TagCommand {
    private static final Logger logger = LogManager.getFormatterLogger(CarImageAction.class);

    private final BaseService<CarDTO> carImpl = new CarServiceImpl();

    @Override
    public String execute(String language, String parameter) throws ServiceException {
        logger.traceEntry("- try to insert <ctg:info> with car img path.");
        int index = Integer.parseInt(parameter);
        logger.debug("Car id: %d", index);
        CarDTO car = carImpl.getById(index);
        logger.traceExit("done with car: %s", new ObjectMessage(car));
        return car.imageURL;
    }
}
