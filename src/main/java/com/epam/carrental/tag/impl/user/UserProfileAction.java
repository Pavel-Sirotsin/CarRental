package com.epam.carrental.tag.impl.user;

import com.epam.carrental.tag.TagCommand;
import com.epam.carrental.tag.util.BundleStringFormatter;
import com.epam.carrental.service.AccidentAbstractService;
import com.epam.carrental.service.BookingAbstractService;
import com.epam.carrental.service.RejectionAbstractService;
import com.epam.carrental.service.ServiceProvider;
import com.epam.carrental.service.dto.AccidentDTO;
import com.epam.carrental.service.dto.BookingDTO;
import com.epam.carrental.service.dto.RejectionDTO;
import com.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class UserProfileAction implements TagCommand {
    private static final Logger logger = LogManager.getFormatterLogger(UserProfileAction.class);

    private final BookingAbstractService bookingService = ServiceProvider.INSTANCE.getBookingImpl();

    private final RejectionAbstractService rejectionService = ServiceProvider.INSTANCE.getRejectionImpl();

    private final AccidentAbstractService accidentService = ServiceProvider.INSTANCE.getAccidentImpl();

    private static final String BASE_NAME = "locale";

    @Override
    public String execute(String language, String parameter) throws ServiceException {
        logger.traceEntry("- try to create user profile table.");
        Integer index = Integer.parseInt(parameter);
        logger.debug("User id: %d", index);
        language = language.replace("_", "-");

        Locale locale = Locale.forLanguageTag(language);
        ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME, locale);

        List<BookingDTO> orders = bookingService.getAllByAccountId(index);
        logger.debug("Orders: %d", orders.size());
        StringBuilder sb = new StringBuilder();

        for (BookingDTO order : orders) {
            String carName = String.join(" ", order.car.brand, order.car.model);
            String carDescription = BundleStringFormatter.getCarDescription(bundle, order.car);
            String totalForTable = BundleStringFormatter.getExchangedAmount(bundle, order.sum);

            sb.append("<tr><th scope=\"row\">");
            sb.append(order.id);
            sb.append("</th>\n<td>");
            sb.append(order.rentalDate);
            sb.append("</td>\n<td>");
            sb.append(order.rentalLocation);
            sb.append("</td>\n<td>");
            sb.append(order.returnDate);
            sb.append("</td>\n<td>");
            sb.append(order.returnLocation);
            sb.append("</td>\n<td>");
            sb.append(order.daysAmount);
            sb.append("</td>\n<td>");
            sb.append(totalForTable);
            sb.append("</td>\n");

            if (order.paid) {
                sb.append("<td><img class=\"btn-sm btn-success disabled\" src=\"img/clipboard-check.svg\" alt=\"square image\"></td>\n");
            } else {
                String totalForPayment = BundleStringFormatter.getExchangedAmount(bundle, order.sum);
                logger.debug("Total payment: %s", totalForPayment);

                sb.append("<td><button type=\"button\" class=\"btn-sm btn-warning\" data-bs-toggle=\"modal\" data-bs-target=\"#paid-");
                sb.append(order.id);
                sb.append("\">\n");
                sb.append("<img src=\"img/credit-card.svg\" alt=\"square image\"></button>\n");
                sb.append("<div class=\"modal fade\" id=\"paid-");
                sb.append(order.id);
                sb.append("\" data-bs-backdrop=\"static\" data-bs-keyboard=\"false\" tabindex=\"-1\" aria-labelledby=\"staticBackdropLabel\" aria-hidden=\"true\">\n");
                sb.append("<div class=\"modal-dialog\">\n");
                sb.append("<div class=\"modal-content\">\n");
                sb.append("<div class=\"modal-header\">\n");
                sb.append("<h5 class=\"modal-title\">№: ");
                sb.append(order.id);
                sb.append("</h5>\n");
                sb.append("<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button></div>\n");
                sb.append("<div class=\"modal-body\">\n");
                sb.append("<p>\n");
                sb.append(bundle.getString("booking.result.total"));
                sb.append(totalForPayment);
                sb.append("</p>\n");
                sb.append("<a type=\"button\" class=\"btn btn-success w-25\" href=\"controller?command=profilePayment&bookingId=");
                sb.append(order.id);
                sb.append("\">");
                sb.append(bundle.getString("label.to.payment"));
                sb.append("</a>\n");
                sb.append("</div>\n</div>\n</div>\n</div>\n</td>");
            }

            sb.append("<td><button type=\"button\" class=\"btn-sm btn-info\" data-bs-toggle=\"modal\" data-bs-target=\"#car-");
            sb.append(order.car.id);
            sb.append("\">\n");
            sb.append("<img src=\"img/info-square.svg\" alt=\"square image\"></button>\n");
            sb.append("<div class=\"modal fade\" id=\"car-");
            sb.append(order.car.id);
            sb.append("\" data-bs-backdrop=\"static\" data-bs-keyboard=\"false\" tabindex=\"-1\" aria-labelledby=\"staticBackdropLabel\" aria-hidden=\"true\">\n");
            sb.append("<div class=\"modal-dialog\">\n");
            sb.append("<div class=\"modal-content\">\n");
            sb.append("<div class=\"modal-header\">\n");
            sb.append("<h5 class=\"modal-title\">");
            sb.append(carName);
            sb.append("</h5>\n");
            sb.append("<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>\n");
            sb.append("</div>\n");
            sb.append("<div class=\"modal-body\">\n");
            sb.append("<p class=\"text-start\">");
            sb.append(carDescription);
            sb.append("</p>\n");
            sb.append("</div>\n</div>\n</div>\n</div>\n</td>\n");

            if (order.rejected) {
                RejectionDTO rejection = rejectionService.getById(order.id);
                logger.debug("line(%d), rejection: %s", 122, new ObjectMessage(rejection));

                sb.append("<td><button type=\"button\" class=\"btn-sm btn-primary\" data-bs-toggle=\"modal\" data-bs-target=\"#rejection-");
                sb.append(rejection.id);
                sb.append("\">\n");
                sb.append("<img src=\"img/body-text.svg\" alt=\"square image\"></button>\n");
                sb.append("<div class=\"modal fade\" id=\"rejection-");
                sb.append(rejection.id);
                sb.append("\" data-bs-backdrop=\"static\" data-bs-keyboard=\"false\" tabindex=\"-1\" aria-labelledby=\"staticBackdropLabel\" aria-hidden=\"true\">\n");
                sb.append("<div class=\"modal-dialog\">\n");
                sb.append("<div class=\"modal-content\">\n");
                sb.append("<div class=\"modal-header\">\n");
                sb.append("<h5 class=\"modal-title\">№: ");
                sb.append(order.id);
                sb.append("</h5>\n");
                sb.append("<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button></div>\n");
                sb.append("<div class=\"modal-body\">\n");
                sb.append("<p class=\"text-start\">");
                sb.append(rejection.rejectionReason);
                sb.append("</p>\n");
                sb.append("</div></div></div></div></td>");

            } else {
                sb.append("<td><img src=\"img/body-text.svg\" alt=\"square image\"></td>\n");
            }

            if (order.accidentFree) {
                sb.append("<td><img src=\"img/envelope-exclamation.svg\" alt=\"excl image\"></td></tr>\n");
            } else {
                AccidentDTO accident = accidentService.getByBookingId(order.id);
                logger.debug("line(%d), accident: %s", 153, new ObjectMessage(accident));

                if (accident.reclaimAmount > 0) {
                    String reclamation = BundleStringFormatter.getProfileReclamation(bundle, accident.reclaimAmount);
                    logger.debug("Reclamation: %s", reclamation);

                    sb.append("<td><button type=\"button\" class=\"btn-sm btn-warning\" data-bs-toggle=\"modal\" data-bs-target=\"#accident-");
                    sb.append(accident.id);
                    sb.append("\">\n");
                    sb.append("<img src=\"img/envelope-exclamation.svg\" alt=\"square image\"></button>\n");
                    sb.append("<div class=\"modal fade\" id=\"accident-");
                    sb.append(accident.id);
                    sb.append("\" data-bs-backdrop=\"static\" data-bs-keyboard=\"false\" tabindex=\"-1\" aria-labelledby=\"staticBackdropLabel\" aria-hidden=\"true\">\n");
                    sb.append("<div class=\"modal-dialog\">\n");
                    sb.append("<div class=\"modal-content\">\n");
                    sb.append("<div class=\"modal-header\">\n");
                    sb.append("<h5 class=\"modal-title\">№: ");
                    sb.append(order.id);
                    sb.append("</h5><button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button></div>\n");
                    sb.append("<div class=\"modal-body\">\n");
                    sb.append("<p class=\"text-start\">");
                    sb.append(reclamation);
                    sb.append("</p></div>\n</div>\n</div>\n</div>\n</td></tr>");
                } else {
                    sb.append("<td><img src=\"img/envelope-exclamation.svg\" alt=\"excl image\"></td></tr>\n");
                }

            }

        }
        logger.traceExit("done by StringBuilder: %d", sb.length());

        return sb.toString();
    }
}
