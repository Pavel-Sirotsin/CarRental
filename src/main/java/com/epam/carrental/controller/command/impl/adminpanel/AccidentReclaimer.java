package com.epam.carrental.controller.command.impl.adminpanel;

import com.epam.carrental.controller.HttpServletHandler;
import com.epam.carrental.controller.command.Command;
import com.epam.carrental.controller.command.util.AttributeManager;
import com.epam.carrental.controller.command.util.PageManager;
import com.epam.carrental.controller.command.util.ParameterManager;
import com.epam.carrental.service.PaymentAbstractService;
import com.epam.carrental.service.ServiceProvider;
import com.epam.carrental.service.dto.BookingDTO;
import com.epam.carrental.service.dto.PaymentDTO;
import com.epam.carrental.service.dto.UserDTO;
import com.epam.carrental.service.exception.ServiceException;
import com.epam.carrental.service.util.MessageManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AccidentReclaimer implements Command {
    private static final Logger logger = LogManager.getFormatterLogger(AccidentReclaimer.class);

    private final PaymentAbstractService paymentServiceImpl = ServiceProvider.INSTANCE.getPaymentImpl();

    @Override
    public void execute(HttpServletHandler handler) throws ServletException, IOException {
        logger.traceEntry("- try to send a reclamation to the client.");
        HttpSession session = handler.getSession();
        UserDTO sessionUser = (UserDTO) session.getAttribute(AttributeManager.USER_SESSION_ATTRIBUTE);
        logger.debug("line(%d), sessionUser: %s", 33, new ObjectMessage(sessionUser));

        if (sessionUser == null) {
            handler.sendRedirect(PageManager.INDEX_PATH);
        } else {
            String bookingId = handler.getParameter(ParameterManager.BOOKING_ID_PARAMETER);
            Integer index = Integer.parseInt(bookingId);
            String reclaimAmount = handler.getParameter(ParameterManager.ACCIDENT_RECLAIM_PARAMETER);

            PaymentDTO paymentDTO = new PaymentDTO(null);
            paymentDTO.booking = new BookingDTO(index);
            paymentDTO.reclamation = Double.parseDouble(reclaimAmount);
            logger.debug("line(%d), paymentDTO: %s", 43, new ObjectMessage(paymentDTO));

            try {
                paymentServiceImpl.update(paymentDTO);
                handler.setAttribute(AttributeManager.ADMIN_ACTION_RESULT,
                        MessageManager.SUCCESS_OPERATION);
                logger.debug("go to admin page with result: %s", MessageManager.SUCCESS_OPERATION);
                handler.forward(PageManager.ADMIN_BOOKING_PAGE_PATH);
            } catch (ServiceException ex) {
                logger.throwing(Level.ERROR, ex);
                handler.setAttribute(AttributeManager.ADMIN_ACTION_RESULT, ex.getMessage());
                handler.forward(PageManager.ADMIN_BOOKING_PAGE_PATH);
            }

        }
        logger.traceExit("done.");
    }
}
