package com.epam.carrental.tag;

import com.epam.carrental.tag.impl.admin.AdminBookingTableAction;
import com.epam.carrental.tag.impl.admin.AdminCarTableAction;
import com.epam.carrental.tag.impl.admin.AdminUserTableAction;
import com.epam.carrental.tag.impl.booking.BookingPriceAction;
import com.epam.carrental.tag.impl.car.CarDescriptionAction;
import com.epam.carrental.tag.impl.car.CarImageAction;
import com.epam.carrental.tag.impl.car.CarInsurancePriceAction;
import com.epam.carrental.tag.impl.car.CarNameAction;
import com.epam.carrental.tag.impl.car.CarPriceAction;
import com.epam.carrental.tag.impl.user.UserProfileAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public enum TagActionMapper {
    INSTANCE;
    private static final Logger logger = LogManager.getFormatterLogger(TagActionMapper.class);

    private static final Map<String, TagCommand> TAG_ACTIONS = new HashMap<>();

    static {
        TAG_ACTIONS.put("name", new CarNameAction());
        TAG_ACTIONS.put("price", new CarPriceAction());
        TAG_ACTIONS.put("description", new CarDescriptionAction());
        TAG_ACTIONS.put("image", new CarImageAction());
        TAG_ACTIONS.put("userBooking", new UserProfileAction());
        TAG_ACTIONS.put("insurancePrice", new CarInsurancePriceAction());
        TAG_ACTIONS.put("bookingPrice", new BookingPriceAction());
        TAG_ACTIONS.put("userTable", new AdminUserTableAction());
        TAG_ACTIONS.put("bookingTable", new AdminBookingTableAction());
        TAG_ACTIONS.put("carTable", new AdminCarTableAction());
    }

    public static TagCommand getTagAction(String name) {
        logger.traceEntry("- try to get tag action command.");
        TagCommand command = TAG_ACTIONS.get(name);
        logger.traceExit("done with: %s", name);
        return command;
    }

}
