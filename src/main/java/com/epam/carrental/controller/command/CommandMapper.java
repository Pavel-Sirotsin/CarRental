package com.epam.carrental.controller.command;

import com.epam.carrental.controller.HttpServletHandler;
import com.epam.carrental.controller.command.impl.BookingMaker;
import com.epam.carrental.controller.command.impl.LanguageChanger;
import com.epam.carrental.controller.command.impl.LogIn;
import com.epam.carrental.controller.command.impl.LogOut;
import com.epam.carrental.controller.command.impl.PaymentFromProfile;
import com.epam.carrental.controller.command.impl.PaymentMaker;
import com.epam.carrental.controller.command.impl.ProfileEditor;
import com.epam.carrental.controller.command.impl.SignUp;
import com.epam.carrental.controller.command.impl.adminpanel.AccidentAppender;
import com.epam.carrental.controller.command.impl.adminpanel.AccidentReclaimer;
import com.epam.carrental.controller.command.impl.adminpanel.BookingAppender;
import com.epam.carrental.controller.command.impl.adminpanel.BookingDeleter;
import com.epam.carrental.controller.command.impl.adminpanel.RejectionAppender;
import com.epam.carrental.controller.command.impl.adminpanel.UserAppender;
import com.epam.carrental.controller.command.impl.adminpanel.UserDeleter;
import com.epam.carrental.controller.command.impl.paging.*;
import com.epam.carrental.controller.command.util.ParameterManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public enum CommandMapper {
    INSTANCE;
    private static final Logger logger = LogManager.getFormatterLogger(CommandMapper.class);

    private static final ConcurrentMap<String, Command> PERFORMERS = new ConcurrentHashMap<>();

    static {
        logger.debug("static initializer for commands.");
        PERFORMERS.put("error", new ShowError500());
        PERFORMERS.put("index", new ShowIndexPage());
        PERFORMERS.put("home", new ShowHomePage());
        PERFORMERS.put("contact", new ShowContactPage());
        PERFORMERS.put("faq", new ShowFAQPage());
        PERFORMERS.put("bookingForm", new ShowBookingFormPage());
        PERFORMERS.put("regForm", new ShowNewUserForm());
        PERFORMERS.put("profile", new ShowUserPage());
        PERFORMERS.put("profileEditor", new ShowProfileEditorPage());
        PERFORMERS.put("adminUser", new ShowAdminUserTable());
        PERFORMERS.put("adminBooking", new ShowAdminBookingTable());
        PERFORMERS.put("adminCar", new ShowAdminCarTable());
        PERFORMERS.put("result", new ShowBookingResultPage());
        PERFORMERS.put("payment", new ShowPaymentBlockPage());
        PERFORMERS.put("addUser", new UserAppender());
        PERFORMERS.put("deleteUser", new UserDeleter());
        PERFORMERS.put("deleteOrder", new BookingDeleter());
        PERFORMERS.put("addOrder", new BookingAppender());
        PERFORMERS.put("addRejection", new RejectionAppender());
        PERFORMERS.put("addAccident", new AccidentAppender());
        PERFORMERS.put("reclaim", new AccidentReclaimer());
        PERFORMERS.put("setLang", new LanguageChanger());
        PERFORMERS.put("makePayment", new PaymentMaker());
        PERFORMERS.put("profilePayment", new PaymentFromProfile());
        PERFORMERS.put("signUp", new SignUp());
        PERFORMERS.put("logIn", new LogIn());
        PERFORMERS.put("logOut", new LogOut());
        PERFORMERS.put("editProfile", new ProfileEditor());
        PERFORMERS.put("book", new BookingMaker());
        logger.debug("Map of performers: %d", PERFORMERS.size());
    }

    public void callPerformer(HttpServletHandler handler) throws ServletException, IOException {
        logger.traceEntry("- try to call right performer.");
        String parameter = handler.getParameter(ParameterManager.COMMAND_PARAMETER);

        try {
            PERFORMERS.get(parameter).execute(handler);
            logger.traceExit("done.");
        } catch (Exception ex) {
            logger.throwing(Level.ERROR, ex);
            logger.debug("go to the error page.");
            PERFORMERS.get("error").execute(handler);
            throw new ServletException(ex);
        }
    }
}
