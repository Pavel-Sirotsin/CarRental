package com.epam.carrental.tag.impl.admin;

import com.epam.carrental.tag.TagCommand;
import com.epam.carrental.tag.util.BundleStringFormatter;
import com.epam.carrental.service.BaseService;
import com.epam.carrental.service.ServiceProvider;
import com.epam.carrental.service.dto.CarDTO;
import com.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class AdminCarTableAction implements TagCommand {
    private static final Logger logger = LogManager.getFormatterLogger(AdminCarTableAction.class);

    private final BaseService<CarDTO> carServiceImpl = ServiceProvider.INSTANCE.getCarImpl();

    private static final String BASE_NAME = "locale";

    @Override
    public String execute(String language, String parameter) throws ServiceException {
        logger.traceEntry("- try to create admin car table.");
        List<CarDTO> cars = carServiceImpl.getAll();
        logger.debug("Cars: %d", cars.size());
        language = language.replace("_", "-");
        logger.debug("Language: %s", language);

        Locale locale = Locale.forLanguageTag(language);
        ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME, locale);

        StringBuilder sb = new StringBuilder();

        for (CarDTO car : cars) {
            String insuranceDescription = BundleStringFormatter.insuranceDescription(bundle, car.insurance);
            logger.debug("Insurance: %s", insuranceDescription);

            sb.append("<tr><th scope=\"row\">");
            sb.append(car.id);
            sb.append("</th>\n<td>");
            sb.append(car.brand);
            sb.append("</td>\n<td>");
            sb.append(car.model);
            sb.append("</td>\n<td>");
            sb.append(car.fuelType);
            sb.append("</td>\n<td>");
            sb.append(car.gearBox);
            sb.append("</td>\n<td>");
            sb.append(car.doors);
            sb.append("</td>\n");

            String conditioned = car.airConditioning ?
                    bundle.getString("label.yes") :
                    bundle.getString("label.no");

            sb.append("<td>");
            sb.append(conditioned);
            sb.append("</td>\n<td>");
            sb.append(car.trunkCapacity);
            sb.append(bundle.getString("label.bag"));
            sb.append("</td>\n");

            String totalPrice = BundleStringFormatter.getExchangedAmount(bundle, car.pricePerDay);
            logger.debug("Car price: %s", totalPrice);

            sb.append("<td>");
            sb.append(totalPrice);
            sb.append("</td>\n<td><button type=\"button\" class=\"btn-sm btn-info\" data-bs-toggle=\"modal\" data-bs-target=\"#insurance-");
            sb.append(car.id);
            sb.append("\"><img src=\"img/credit-card.svg\" alt=\"square image\"></button>");
            sb.append("<div class=\"modal fade\" id=\"insurance-");
            sb.append(car.id);
            sb.append("\" data-bs-backdrop=\"static\" data-bs-keyboard=\"false\" tabindex=\"-1\" aria-labelledby=\"staticBackdropLabel\" aria-hidden=\"true\">");
            sb.append("<div class=\"modal-dialog\">");
            sb.append("<div class=\"modal-content\">");
            sb.append("<div class=\"modal-header\">\n");
            sb.append("<h5 class=\"modal-title\">");
            sb.append(bundle.getString("label.insurance"));
            sb.append("</h5>\n<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>\n");
            sb.append("</div>\n<div class=\"modal-body\">");
            sb.append("<p class=\"text-start\">");
            sb.append(insuranceDescription);
            sb.append("</p></div></div></div></div></td></tr>\n");

        }
        logger.traceExit("done by StringBuilder: %d", sb.length());
        return sb.toString();
    }
}
