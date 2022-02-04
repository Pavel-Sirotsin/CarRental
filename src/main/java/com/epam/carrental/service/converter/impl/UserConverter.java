package com.epam.carrental.service.converter.impl;

import com.epam.carrental.dao.entity.UserDAO;
import com.epam.carrental.dao.exception.DAOException;
import com.epam.carrental.service.AccountAbstractService;
import com.epam.carrental.service.ServiceProvider;
import com.epam.carrental.service.converter.BaseConverter;
import com.epam.carrental.service.dto.AccountDTO;
import com.epam.carrental.service.dto.UserDTO;
import com.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;
/**
 * The {@code UserConverter} class implements methods from {@code BaseConverter} interface
 * and makes simple conversion from {@link UserDTO} object to {@link UserDAO} object.
 */
public class UserConverter implements BaseConverter<UserDTO, UserDAO> {
    private static final Logger logger = LogManager.getFormatterLogger(UserConverter.class);
    /**
     * The field holding account implementation of Service layer.
     */
    private final AccountAbstractService accountServiceImpl = ServiceProvider.INSTANCE.getAccountImpl();
    /**
     * Converts {@link UserDAO} object to {@link UserDTO} considering to the foreign key ID.
     *
     * @param userDAO the value to be converted.
     * @return the new {@link UserDTO} object.
     * @throws ServiceException if {@link DAOException} aries.
     */
    @Override
    public UserDTO convert(UserDAO userDAO) throws ServiceException {
        logger.traceEntry("- try to convert a User from DAO to DTO.");
        AccountDTO accountDto = accountServiceImpl.getByUserId(userDAO.getId());
        logger.debug("line(%d), plus object AccountDto: %s", 22, new ObjectMessage(accountDto));

        UserDTO userDTO = new UserDTO(userDAO.getId());
        userDTO.login = userDAO.getLogin();
        userDTO.password = userDAO.getPassword();
        userDTO.role = userDAO.getUserRole();
        userDTO.account = accountDto;
        logger.traceExit(" done.");
        return userDTO;
    }
    /**
     * Converts {@link UserDTO} object to {@link UserDAO}.
     * All object type references will be changed on {@code Integer} value.
     *
     * @param userDTO the value to be converted.
     * @return the new {@link UserDAO} object with the IDs,
     * which corresponds to the foreign key in a database.
     */
    @Override
    public UserDAO convert(UserDTO userDTO) {
        logger.debug(" try to convert User from DTO to DAO.");
        return new UserDAO(
                userDTO.id,
                userDTO.login,
                userDTO.password,
                userDTO.role,
                userDTO.account.id);
    }
}
