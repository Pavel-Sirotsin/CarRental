package com.epam.carrental.controller.command.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PageManager {
    private static final Logger logger = LogManager.getFormatterLogger(PageManager.class);

    public static final String PAGE_500 = "/WEB-INF/jsp/page_500.jsp";
    public static final String CONTACT_PAGE_PATH = "/WEB-INF/jsp/contact.jsp";
    public static final String FAQ_PAGE_PATH = "/WEB-INF/jsp/faq.jsp";
    public static final String HOME_PAGE_PATH = "/WEB-INF/jsp/home.jsp";
    public static final String REG_FORM_PAGE_PATH = "/WEB-INF/jsp/user/reg_form.jsp";
    public static final String BOOKING_FORM_PAGE_PATH = "/WEB-INF/jsp/order/booking_form.jsp";
    public static final String BOOKING_RESULT_PAGE_PATH = "/WEB-INF/jsp/order/booking_result.jsp";
    public static final String PAYMENT_BLOCK_PAGE_PATH = "/WEB-INF/jsp/order/payment_block.jsp";
    public static final String PROFILE_PAGE_PATH = "/WEB-INF/jsp/user/profile.jsp";
    public static final String PROFILE_EDITOR_PAGE_PATH = "/WEB-INF/jsp/user/profile_editor.jsp";
    public static final String ADMIN_USER_PAGE_PATH = "/WEB-INF/jsp/admin/admin_user.jsp";
    public static final String ADMIN_BOOKING_PAGE_PATH = "/WEB-INF/jsp/admin/admin_booking.jsp";
    public static final String ADMIN_CAR_PAGE_PATH = "/WEB-INF/jsp/admin/admin_car.jsp";
    public static final String INDEX_PATH = "/index.jsp";
    public static final String RESULT_PATH = "/result.jsp";
    public static final String PAYMENT_PATH = "/payment.jsp";

    private PageManager() {
    }

    public static String getPage(String name) {
        logger.traceEntry("- try to get page from the list.");
        logger.traceExit("done with page: %s", name);
        switch (name) {
            case "contact":
                return CONTACT_PAGE_PATH;
            case "faq":
                return FAQ_PAGE_PATH;
            case "regForm":
                return REG_FORM_PAGE_PATH;
            case "bookingForm":
                return BOOKING_FORM_PAGE_PATH;
            case "bookingResult":
                return BOOKING_RESULT_PAGE_PATH;
            case "profile":
                return PROFILE_PAGE_PATH;
            case "userTable":
                return ADMIN_USER_PAGE_PATH;
            case "bookingTable":
                return ADMIN_BOOKING_PAGE_PATH;
            case "carTable":
                return ADMIN_CAR_PAGE_PATH;
            case "profileEditor":
                return PROFILE_EDITOR_PAGE_PATH;
            case "500":
                return PAGE_500;
            default:
                return HOME_PAGE_PATH;
        }
    }
}
