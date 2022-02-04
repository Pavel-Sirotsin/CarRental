package com.epam.carrental.tag.impl.car;

import com.epam.carrental.tag.TagCommand;
import com.epam.carrental.service.BaseService;
import com.epam.carrental.service.dto.CarDTO;
import com.epam.carrental.service.exception.ServiceException;
import com.epam.carrental.service.impl.CarServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import java.util.Locale;
import java.util.ResourceBundle;

public class CarDescriptionAction implements TagCommand {
    private static final Logger logger = LogManager.getFormatterLogger(CarDescriptionAction.class);

    private static final String BASE_NAME = "locale";

    private final BaseService<CarDTO> carImpl = new CarServiceImpl();

    @Override
    public String execute(String language, String parameter) throws ServiceException {
        logger.traceEntry("- try to insert <ctg:info> with car description.");
        int index = Integer.parseInt(parameter);
        logger.debug("Car id: %d", index);
        CarDTO car = carImpl.getById(index);
        logger.debug("line(%d), car: %s", 27, new ObjectMessage(car));
        language = language.replace("_", "-");

        Locale locale = Locale.forLanguageTag(language);
        ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME, locale);
        logger.traceExit("done with locale: %s", new ObjectMessage(locale));
        return String.format("%s: %s<br>%s: %s<br>%s: %d<br>%s: %s<br>%s: %s %s",
                bundle.getString("label.fuel"),
                car.fuelType.equalsIgnoreCase("gasoline") ?
                        bundle.getString("label.gasoline") :
                        bundle.getString("label.diesel"),
                bundle.getString("label.gear"),
                car.gearBox.equalsIgnoreCase("automatic") ?
                        bundle.getString("label.automatic") :
                        bundle.getString("label.manual"),
                bundle.getString("label.doors"),
                car.doors,
                bundle.getString("label.conditioner"),
                car.airConditioning ?
                        bundle.getString("label.yes") :
                        bundle.getString("label.no"),
                bundle.getString("label.trunk"),
                car.trunkCapacity,
                bundle.getString("label.bag"));
    }
}

