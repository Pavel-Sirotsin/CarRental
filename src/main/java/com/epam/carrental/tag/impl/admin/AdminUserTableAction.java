package com.epam.carrental.tag.impl.admin;

import com.epam.carrental.tag.TagCommand;
import com.epam.carrental.service.ServiceProvider;
import com.epam.carrental.service.UserAbstractService;
import com.epam.carrental.service.dto.UserDTO;
import com.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AdminUserTableAction implements TagCommand {
    private static final Logger logger = LogManager.getFormatterLogger(AdminUserTableAction.class);

    private final UserAbstractService userService = ServiceProvider.INSTANCE.getUserImpl();

    @Override
    public String execute(String language, String parameter) throws ServiceException {
        logger.traceEntry("- try to create admin user table.");
        List<UserDTO> users = userService.getAll();

        StringBuilder sb = new StringBuilder();

        for (UserDTO user : users) {
            sb.append("<tr><th scope=\"row\">");
            sb.append(user.id);
            sb.append("</th>\n<td>");
            sb.append(user.account.name);
            sb.append("</td>\n<td>");
            sb.append(user.account.surname);
            sb.append("</td>\n<td>");
            sb.append(user.account.email);
            sb.append("</td>\n<td>");
            sb.append(user.account.phoneNumber);
            sb.append("</td>\n<td>");
            sb.append(user.account.drivingLicense);
            sb.append("</td>\n<td>");
            sb.append(user.login);
            sb.append("</td>\n<td>");
            sb.append(user.password);
            sb.append("</td>\n<td>");
            sb.append(user.role.name());
            sb.append("</td></tr>\n");
        }
        logger.traceExit("done by StringBuilder - %d", sb.length());
        return sb.toString();
    }

}
