package com.epam.carrental.controller.command.impl;

import com.epam.carrental.controller.HttpServletHandler;
import com.epam.carrental.controller.command.Command;
import com.epam.carrental.controller.command.util.AttributeManager;
import com.epam.carrental.controller.command.util.PageManager;
import com.epam.carrental.controller.command.util.ParameterManager;
import com.epam.carrental.dao.entity.UserRole;
import com.epam.carrental.service.ServiceProvider;
import com.epam.carrental.service.UserAbstractService;
import com.epam.carrental.service.dto.AccountDTO;
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

public class SignUp implements Command {
    private static final Logger logger = LogManager.getFormatterLogger(SignUp.class);

    private final UserAbstractService service = ServiceProvider.INSTANCE.getUserImpl();

    @Override
    public void execute(HttpServletHandler handler) throws ServletException, IOException {
        logger.traceEntry("- try to sign up a new user.");
        HttpSession session = handler.getSession();
        String firstName = handler.getParameter(ParameterManager.NAME_PARAMETER);
        String lastName = handler.getParameter(ParameterManager.SURNAME_PARAMETER);
        String email = handler.getParameter(ParameterManager.EMAIL_PARAMETER);
        String phoneNumber = handler.getParameter(ParameterManager.PHONE_PARAMETER);
        String drivingLicense = handler.getParameter(ParameterManager.LICENSE_PARAMETER);
        String userName = handler.getParameter(ParameterManager.USER_NAME_PARAMETER);
        String password = handler.getParameter(ParameterManager.USER_PASSWORD_PARAMETER);

        AccountDTO accountDTO = new AccountDTO(null);
        accountDTO.name = firstName;
        accountDTO.surname = lastName;
        accountDTO.email = email;
        accountDTO.phoneNumber = phoneNumber;
        accountDTO.drivingLicense = drivingLicense;

        UserDTO userDTO = new UserDTO(null);
        userDTO.login = userName;
        userDTO.password = password;
        userDTO.role = UserRole.CLIENT;
        userDTO.account = accountDTO;
        logger.debug("line(%d), userDTO: %s", 54, new ObjectMessage(userDTO));

        try {
            service.createByUserEntity(userDTO);
            session.setAttribute(AttributeManager.REGISTRATION_RESULT_ATTRIBUTE,
                    MessageManager.SUCCESS_SIGN_UP);
            logger.debug("redirect to the index page with result: %s",MessageManager.SUCCESS_SIGN_UP);
            handler.sendRedirect(PageManager.INDEX_PATH);
        } catch (ServiceException ex) {
            ex.printStackTrace();

            logger.throwing(Level.ERROR, ex);
            handler.setAttribute(AttributeManager.REGISTRATION_RESULT_ATTRIBUTE,
                    ex.getMessage());
            handler.forward(PageManager.REG_FORM_PAGE_PATH);
        }
        logger.traceExit("done.");
    }
}
