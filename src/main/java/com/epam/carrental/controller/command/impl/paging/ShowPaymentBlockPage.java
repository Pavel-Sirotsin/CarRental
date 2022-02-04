package com.epam.carrental.controller.command.impl.paging;

import com.epam.carrental.controller.HttpServletHandler;
import com.epam.carrental.controller.command.Command;
import com.epam.carrental.controller.command.util.AttributeManager;
import com.epam.carrental.controller.command.util.PageManager;
import com.epam.carrental.service.dto.BookingDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ShowPaymentBlockPage implements Command {
    private static final Logger logger = LogManager.getFormatterLogger(ShowPaymentBlockPage.class);

    @Override
    public void execute(HttpServletHandler handler) throws ServletException, IOException {
        logger.traceEntry("- try to forward to the payment page.");
        HttpSession session = handler.getSession();
        BookingDTO booking = (BookingDTO) session.getAttribute(AttributeManager.BOOKING_SESSION_ATTRIBUTE);
        logger.debug("line(%d), booking: %s", 24, new ObjectMessage(booking));

        if (booking == null) {
            logger.debug("redirect to index page.");
            handler.sendRedirect(PageManager.INDEX_PATH);
        } else {
            handler.forward(PageManager.PAYMENT_BLOCK_PAGE_PATH);
        }
        logger.traceExit("done.");
    }
}

