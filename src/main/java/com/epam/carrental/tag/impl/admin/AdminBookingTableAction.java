package com.epam.carrental.tag.impl.admin;

import com.epam.carrental.tag.TagCommand;
import com.epam.carrental.tag.util.BundleStringFormatter;
import com.epam.carrental.service.AccidentAbstractService;
import com.epam.carrental.service.BookingAbstractService;
import com.epam.carrental.service.PaymentAbstractService;
import com.epam.carrental.service.RejectionAbstractService;
import com.epam.carrental.service.ServiceProvider;
import com.epam.carrental.service.dto.AccidentDTO;
import com.epam.carrental.service.dto.BookingDTO;
import com.epam.carrental.service.dto.PaymentDTO;
import com.epam.carrental.service.dto.RejectionDTO;
import com.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class AdminBookingTableAction implements TagCommand {
    private static final Logger logger = LogManager.getFormatterLogger(AdminBookingTableAction.class);

    private final BookingAbstractService bookingServiceImpl = ServiceProvider.INSTANCE.getBookingImpl();
    private final RejectionAbstractService rejectionServiceImpl = ServiceProvider.INSTANCE.getRejectionImpl();
    private final AccidentAbstractService accidentServiceImpl = ServiceProvider.INSTANCE.getAccidentImpl();
    private final PaymentAbstractService paymentServiceImpl = ServiceProvider.INSTANCE.getPaymentImpl();

    private static final String BASE_NAME = "locale";

    @Override
    public String execute(String language, String parameter) throws ServiceException {
        logger.traceEntry("- try to create admin booking table.");
        List<BookingDTO> orders = bookingServiceImpl.getAll();
        logger.debug("Orders: %d", orders.size());

        language = language.replace("_", "-");
        logger.debug("Language: %s", language);

        Locale locale = Locale.forLanguageTag(language);
        ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME, locale);

        StringBuilder sb = new StringBuilder();

        for (BookingDTO order : orders) {
            String orderId = String.join(" ",
                    bundle.getString("admin.booking.order.id"),
                    String.valueOf(order.id));
            String orderTotalSum = BundleStringFormatter.getExchangedAmount(bundle, order.sum);
            String carName = String.join(" ", order.car.brand, order.car.model);
            String carDescription = BundleStringFormatter.getCarDescription(bundle, order.car);
            String accountDescription = BundleStringFormatter.getAccountDescription(bundle, order.account);

            sb.append("<tr><th scope=\"row \">");
            sb.append(order.id);
            sb.append("</th>\n<td><button type=\"button\" class=\"btn-sm btn-info\" data-bs-toggle=\"modal\" data-bs-target=\"#client-");
            sb.append(order.id);
            sb.append("\">\n<img src=\"img/person-lines-fill.svg\" alt=\"square image\"></button>\n");
            sb.append("<div class=\"modal fade\" id=\"client-");
            sb.append(order.id);
            sb.append("\" data-bs-backdrop=\"static\" data-bs-keyboard=\"false\" tabindex=\"-1\" aria-labelledby=\"staticBackdropLabel\" aria-hidden=\"true\">\n");
            sb.append("<div class=\"modal-dialog\">\n");
            sb.append("<div class=\"modal-content\">\n");
            sb.append("<div class=\"modal-header\">\n");
            sb.append("<h5 class=\"modal-title\">");
            sb.append(orderId);
            sb.append("</h5>\n<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button></div>\n");
            sb.append("<div class=\"modal-body\">\n");
            sb.append("<p class=\"text-start\">");
            sb.append(accountDescription);
            sb.append("</p>\n</div></div></div></div></td>\n");
            sb.append("<td>");
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
            sb.append(orderTotalSum);
            sb.append("</td>\n");

            if (order.paid) {
                PaymentDTO payment = paymentServiceImpl.getById(order.id);
                String paymentDescription = BundleStringFormatter.getPaymentDescription(bundle, payment);
                logger.debug("Payment: %s", paymentDescription);

                sb.append("<td><button type=\"button\" class=\"btn-sm btn-success\" data-bs-toggle=\"modal\" data-bs-target=\"#paid-");
                sb.append(order.id);
                sb.append("\">\n");
                sb.append("<img src=\"img/clipboard-check.svg\" alt=\"square image\"></button>\n");
                sb.append("<div class=\"modal fade\" id=\"paid-");
                sb.append(order.id);
                sb.append("\" data-bs-backdrop=\"static\" data-bs-keyboard=\"false\" tabindex=\"-1\" aria-labelledby=\"staticBackdropLabel\" aria-hidden=\"true\">\n");
                sb.append("<div class=\"modal-dialog\">\n");
                sb.append("<div class=\"modal-content\">\n");
                sb.append("<div class=\"modal-header\">\n");
                sb.append("<h5 class=\"modal-title\">");
                sb.append(orderId);
                sb.append("</h5>\n");
                sb.append("<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button></div>\n");
                sb.append("<div class=\"modal-body\">\n");
                sb.append("<p class=\"text-start\">\n");
                sb.append(paymentDescription);
                sb.append("</p>\n");

                if (payment.reclamation > 0) {
                    String paymentReclaimAmount = BundleStringFormatter.getPaymentReclaimCost(bundle, payment.reclamation);
                    logger.debug("Payment reclamation amount: %s", paymentReclaimAmount);
                    sb.append("<p class=\"text-start text-warning fw-bold\">");
                    sb.append(paymentReclaimAmount);
                    sb.append("</p>\n");
                }

                sb.append("</div>\n</div>\n</div>\n</div>\n</td>");
            } else {
                sb.append("<td><img class=\"btn-sm btn-warning disabled\" src=\"img/clipboard-x.svg\" alt=\"square image\"></td>\n");
            }

            if (order.rejected) {
                RejectionDTO rejection = rejectionServiceImpl.getById(order.id);
                logger.debug("line(%d), rejection: %s", 126, new ObjectMessage(rejection));

                sb.append("<td><button type=\"button\" class=\"btn-sm btn-primary\" data-bs-toggle=\"modal\" data-bs-target=\"#rejection-");
                sb.append(order.id);
                sb.append("\">\n<img src=\"img/body-text.svg\" alt=\"square image\"></button>\n");
                sb.append("<div class=\"modal fade\" id=\"rejection-");
                sb.append(order.id);
                sb.append("\" data-bs-backdrop=\"static\" data-bs-keyboard=\"false\" tabindex=\"-1\" aria-labelledby=\"staticBackdropLabel\" aria-hidden=\"true\">\n");
                sb.append("<div class=\"modal-dialog\">\n");
                sb.append("<div class=\"modal-content\">\n");
                sb.append("<div class=\"modal-header\">\n");
                sb.append("<h5 class=\"modal-title\">");
                sb.append(orderId);
                sb.append("</h5>\n<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button></div>\n");
                sb.append("<div class=\"modal-body\"><p>");
                sb.append(rejection.rejectionReason);
                sb.append("</p></div></div></div></div></td>");
            } else {
                sb.append("<td><img src=\"img/body-text.svg\" alt=\"square image\"></td>\n");
            }

            sb.append("<td><button type=\"button\" class=\"btn-sm btn-info\" data-bs-toggle=\"modal\" data-bs-target=\"#car-");
            sb.append(order.id);
            sb.append("\"><img src=\"img/info-square.svg\" alt=\"square image\"></button>");
            sb.append("<div class=\"modal fade\" id=\"car-");
            sb.append(order.id);
            sb.append("\" data-bs-backdrop=\"static\" data-bs-keyboard=\"false\" tabindex=\"-1\" aria-labelledby=\"staticBackdropLabel\" aria-hidden=\"true\">");
            sb.append("<div class=\"modal-dialog\">");
            sb.append("<div class=\"modal-content\">");
            sb.append("<div class=\"modal-header\">\n");
            sb.append("<h5 class=\"modal-title\">");
            sb.append(carName);
            sb.append("</h5>\n<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button></div>\n");
            sb.append("<div class=\"modal-body\">\n");
            sb.append("<p class=\"text-start\">");
            sb.append(carDescription);
            sb.append("</p>\n</div></div></div></div></td>\n");

            if (order.accidentFree) {
                sb.append("<td><img src=\"img/cone-striped.svg\" alt=\"square image\"></td></tr>\n");
            } else {
                AccidentDTO accident = accidentServiceImpl.getByBookingId(order.id);
                logger.debug("line(%d), accident: %s", 169, new ObjectMessage(accident));
                String accidentDescription = BundleStringFormatter.getAccidentDescription(bundle, accident);
                logger.debug("Accident description: %s",  accidentDescription);
                String accidentReclaimAmount = BundleStringFormatter.getAccidentReclaimCost(bundle, accident.reclaimAmount);
                logger.debug("Accident reclaim amount: %s", accidentReclaimAmount);

                if (accident.reclaimAmount <= 0) {
                    sb.append("<td><button type=\"button\" class=\"btn-sm btn-info\" data-bs-toggle=\"modal\" data-bs-target=\"#accident-");
                    sb.append(order.id);
                    sb.append("\">\n<img src=\"img/cone-striped.svg\" alt=\"square image\"></button>\n");
                } else {
                    sb.append("<td><button type=\"button\" class=\"btn-sm btn-danger\" data-bs-toggle=\"modal\" data-bs-target=\"#accident-");
                    sb.append(order.id);
                    sb.append("\">\n<img src=\"img/cone-striped.svg\" alt=\"square image\"></button>\n");
                }

                sb.append("<div class=\"modal fade\" id=\"accident-");
                sb.append(order.id);
                sb.append("\" data-bs-backdrop=\"static\" data-bs-keyboard=\"false\" tabindex=\"-1\" aria-labelledby=\"staticBackdropLabel\" aria-hidden=\"true\">\n");
                sb.append("<div class=\"modal-dialog\">\n");
                sb.append("<div class=\"modal-content\">\n");
                sb.append("<div class=\"modal-header\">\n");
                sb.append("<h5 class=\"modal-title\">");
                sb.append(orderId);
                sb.append("</h5>\n<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button></div>\n");
                sb.append("<div class=\"modal-body\">\n");
                sb.append("<p class=\"text-start\">");
                sb.append(accidentDescription);
                sb.append("</p>\n");

                if (accident.reclaimAmount <= 0) {
                    sb.append("</div></div></div></div></td></tr>\n");
                } else {
                    sb.append("<form class=\"modal-footer\">\n");
                    sb.append("<input type=\"hidden\" name=\"command\" value=\"reclaim\"></input>\n");
                    sb.append("<input type=\"hidden\" name=\"bookingId\" value=\"");
                    sb.append(order.id);
                    sb.append("\"></input>\n");
                    sb.append("<input type=\"hidden\" name=\"reclaimAmount\" value=\"");
                    sb.append(accident.reclaimAmount);
                    sb.append("\"></input>\n");
                    sb.append("<text class=\"fw-bold\">");
                    sb.append(bundle.getString("admin.accident.extra"));
                    sb.append("</text>\n");
                    sb.append("<button type=\"submit\" class=\"btn btn-danger\"><i>");
                    sb.append(accidentReclaimAmount);
                    sb.append("</i></button>\n");
                    sb.append("</form></div></div></div></td></tr>\n");
                }
            }
        }
        logger.traceExit("done by StringBuilder: %d", sb.length());
        return sb.toString();
    }
}
