package com.epam.carrental.controller.command;

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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

final class CommandFactory {
    private static final Logger logger = LogManager.getFormatterLogger(CommandFactory.class);

    private final ConcurrentMap<String, Command> performers = new ConcurrentHashMap<>();

    {
        logger.debug("initializer for commands.");
        performers.put("error", new ShowError500());
        performers.put("index", new ShowIndexPage());
        performers.put("home", new ShowHomePage());
        performers.put("contact", new ShowContactPage());
        performers.put("faq", new ShowFAQPage());
        performers.put("bookingForm", new ShowBookingFormPage());
        performers.put("regForm", new ShowNewUserForm());
        performers.put("profile", new ShowUserPage());
        performers.put("profileEditor", new ShowProfileEditorPage());
        performers.put("adminUser", new ShowAdminUserTable());
        performers.put("adminBooking", new ShowAdminBookingTable());
        performers.put("adminCar", new ShowAdminCarTable());
        performers.put("result", new ShowBookingResultPage());
        performers.put("payment", new ShowPaymentBlockPage());
        performers.put("addUser", new UserAppender());
        performers.put("deleteUser", new UserDeleter());
        performers.put("deleteOrder", new BookingDeleter());
        performers.put("addOrder", new BookingAppender());
        performers.put("addRejection", new RejectionAppender());
        performers.put("addAccident", new AccidentAppender());
        performers.put("reclaim", new AccidentReclaimer());
        performers.put("setLang", new LanguageChanger());
        performers.put("makePayment", new PaymentMaker());
        performers.put("profilePayment", new PaymentFromProfile());
        performers.put("signUp", new SignUp());
        performers.put("logIn", new LogIn());
        performers.put("logOut", new LogOut());
        performers.put("editProfile", new ProfileEditor());
        performers.put("book", new BookingMaker());
        logger.debug("Map of performers: %d", performers.size());
    }

    public Command getCommand(String name) {
        return performers.get(name);
    }

}
