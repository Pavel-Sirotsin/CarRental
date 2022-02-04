package com.epam.carrental.controller.command.impl.paging;

import com.epam.carrental.controller.HttpServletHandler;
import com.epam.carrental.controller.command.Command;
import com.epam.carrental.controller.command.util.AttributeManager;
import com.epam.carrental.controller.command.util.PageManager;
import com.epam.carrental.controller.command.util.ParameterManager;
import com.epam.carrental.service.BaseService;
import com.epam.carrental.service.ServiceProvider;
import com.epam.carrental.service.dto.CarDTO;
import com.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ShowBookingFormPage implements Command {
    private static final Logger logger = LogManager.getFormatterLogger(ShowBookingFormPage.class);

    private final BaseService<CarDTO> service = ServiceProvider.INSTANCE.getCarImpl();

    @Override
    public void execute(HttpServletHandler handler) throws ServletException, IOException {
        logger.traceEntry("- try to forward to the booking form page.");
        HttpSession session = handler.getSession();
        String carId = handler.getParameter(ParameterManager.CAR_ID_PARAMETER);
        int index = Integer.parseInt(carId);
        logger.debug("Car id is: %d", index);

        CarDTO car = null;
        try {
            car = service.getById(index);
        } catch (ServiceException e) {
            logger.throwing(Level.ERROR, e);
        }
        session.setAttribute(AttributeManager.CAR_SESSION_ATTRIBUTE, car);
        logger.debug("line(%d), car: %s", 41, new ObjectMessage(car));

        handler.forward(PageManager.BOOKING_FORM_PAGE_PATH);
        logger.traceExit("done.");
    }
}
