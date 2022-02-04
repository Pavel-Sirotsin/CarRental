package com.epam.carrental.service.impl;

import com.epam.carrental.controller.command.util.ParameterManager;
import com.epam.carrental.dao.DAOProvider;
import com.epam.carrental.dao.UserAbstractDAO;
import com.epam.carrental.dao.entity.AccountDAO;
import com.epam.carrental.dao.entity.UserDAO;
import com.epam.carrental.dao.exception.DAOException;
import com.epam.carrental.service.AccountAbstractService;
import com.epam.carrental.service.ServiceProvider;
import com.epam.carrental.service.UserAbstractService;
import com.epam.carrental.service.converter.BaseConverter;
import com.epam.carrental.service.converter.impl.AccountConverter;
import com.epam.carrental.service.converter.impl.UserConverter;
import com.epam.carrental.service.dto.AccountDTO;
import com.epam.carrental.service.dto.UserDTO;
import com.epam.carrental.service.exception.ServiceException;
import com.epam.carrental.service.util.Cypher;
import com.epam.carrental.service.util.MessageManager;
import com.epam.carrental.service.validator.impl.AccountValidator;
import com.epam.carrental.service.validator.impl.UserValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserServiceImpl extends UserAbstractService {
    private static final Logger logger = LogManager.getFormatterLogger(UserServiceImpl.class);

    private final UserAbstractDAO userDAOImpl = DAOProvider.INSTANCE.getUserImpl();
    private final AccountAbstractService accountServiceImpl = ServiceProvider.INSTANCE.getAccountImpl();

    private final BaseConverter<UserDTO, UserDAO> userConverter = new UserConverter();
    private final BaseConverter<AccountDTO, AccountDAO> accountConverter = new AccountConverter();

    private final UserValidator userValidator = new UserValidator();
    private final AccountValidator accountValidator = new AccountValidator();

    @Override
    public List<UserDTO> getAll() throws ServiceException {
        logger.traceEntry("- try to get all Users.");
        List<UserDTO> userDTOList = new ArrayList<>();

        try {
            List<UserDAO> userDAOList = userDAOImpl.findAll();
            logger.debug("line(%d), userDAOList: %d", 51, userDAOList.size());

            for (UserDAO userDAO : userDAOList) {
                userDTOList.add(userConverter.convert(userDAO));
            }

        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
        logger.traceExit(" done.");
        return userDTOList;
    }

    @Override
    public boolean createByUserEntity(UserDTO userDTO) throws ServiceException {
        logger.traceEntry("- try to create a new user and account.");

        userValidator.validate(userDTO);
        accountValidator.validate(userDTO.account);
        userDTO.password = Cypher.encryptPassword(userDTO.password);

        UserDAO userDAO = userConverter.convert(userDTO);
        logger.debug("line(%d), userDAO: %s", 73, new ObjectMessage(userDAO));
        AccountDAO accountDAO = accountConverter.convert(userDTO.account);
        logger.debug("line(%d), accountDAO: %s", 75, new ObjectMessage(accountDAO));

        boolean result;
        try {
            result = userDAOImpl.makeTransaction(userDAO, accountDAO);
        } catch (DAOException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new ServiceException(ex.getMessage(), ex);
        }

        logger.traceExit("done.");
        return  result;
    }

    @Override
    public UserDTO getByLogin(UserDTO userDTO) throws ServiceException {
        logger.traceEntry("- try to get user by login.");

        userValidator.validate(userDTO);

        UserDAO userDAO;

        try {
            userDAO = userDAOImpl.findByLogin(userDTO.login);

            String inputPassword = userDTO.password;
            logger.debug("line(%d), inputPassword: %s", 102, inputPassword);
            String basedPassword = userDAO.getPassword();
            basedPassword = Cypher.decryptPassword(basedPassword);
            logger.debug("line(%d), basedPassword: %s", 104, basedPassword);

            userValidator.checkPassword(inputPassword, basedPassword);

            userDTO = userConverter.convert(userDAO);

        } catch (DAOException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new ServiceException(ex.getMessage(), ex);
        }

        logger.traceExit(" with userDAO: %s", new ObjectMessage(userDAO));
        return userDTO;
    }

    @Override
    public UserDTO updatePartly(UserDTO userDTO, Map<String, String[]> params) throws ServiceException {
        logger.traceEntry("- try to update partly user profile.");
        String block = params.get(ParameterManager.BLOCK_PARAMETER)[0];
        logger.debug("line(%d), block: %s", 123, block);

        if(userDTO.account.id == null){
            userDTO.account = accountServiceImpl.getByUserId(userDTO.id);
        }

        switch (block) {
            case "first" : {
                String name = params.get(ParameterManager.NAME_PARAMETER)[0];
                logger.debug("line(%d), name: %s", 133, name);
                String surname = params.get(ParameterManager.SURNAME_PARAMETER)[0];
                logger.debug("line(%d), surname: %s", 135, surname);
                String email = params.get(ParameterManager.EMAIL_PARAMETER)[0];
                logger.debug("line(%d), email: %s", 137, email);

                userDTO.account.name = name;
                userDTO.account.surname = surname;
                userDTO.account.email = email;

                try {
                    accountServiceImpl.update(userDTO.account);
                } catch (ServiceException ex) {
                    logger.throwing(Level.ERROR, ex);
                    throw new ServiceException(ex.getMessage(), ex);
                }

            }
            break;
            case "second" : {
                String phone = params.get(ParameterManager.PHONE_PARAMETER)[0];
                logger.debug("line(%d), phone: %s", 151, phone);
                String license = params.get(ParameterManager.LICENSE_PARAMETER)[0];
                logger.debug("line(%d), license: %s", 153, license);

                userDTO.account.phoneNumber = phone;
                userDTO.account.drivingLicense = license;

                try {
                    accountServiceImpl.update(userDTO.account);
                } catch (ServiceException ex) {
                    logger.throwing(Level.ERROR, ex);
                    throw new ServiceException(ex.getMessage(), ex);
                }


            }
            break;
            case "third" : {
                String login = params.get(ParameterManager.USER_NAME_PARAMETER)[0];
                logger.debug("line(%d), login: %s", 168, login);
                String newPassword = params.get(ParameterManager.NEW_PASSWORD_PARAMETER)[0];
                logger.debug("line(%d), newPassword: %s", 170, newPassword);
                String inputPassword = params.get(ParameterManager.USER_PASSWORD_PARAMETER)[0];
                logger.debug("line(%d), inputPassword: %s", 172, inputPassword);

                userValidator.validateLogin(login);
                userValidator.validatePassword(inputPassword);
                userValidator.validatePassword(newPassword);

                String userEncryptedPassword = userDTO.password;
                String userDecryptedPassword = Cypher.decryptPassword(userEncryptedPassword);
                userValidator.checkPassword(inputPassword, userDecryptedPassword);

                newPassword = Cypher.encryptPassword(newPassword);

                userDTO.login = login;
                userDTO.password = newPassword;
                UserDAO userDAO = userConverter.convert(userDTO);

                try {
                    userDAOImpl.update(userDAO);
                } catch (DAOException ex) {
                    logger.throwing(Level.ERROR, ex);
                    throw new ServiceException(ex.getMessage(), ex);
                }

            }
            break;
            default : throw new ServiceException(MessageManager.WRONG_DATA);
        }
        logger.traceExit(" with userDTO: %s", new ObjectMessage(userDTO));
        return userDTO;
    }

    @Override
    public boolean deleteById(Map<String, String[]> params) throws ServiceException {
        logger.traceEntry("- try to delete user by id.");
        String userId = params.get(ParameterManager.USER_ID_PARAMETER)[0];

        userValidator.validateId(userId);
        int index = Integer.parseInt(userId);
        logger.debug("line(%d), index: %d", 222, index);

        boolean result;
        try {
            result = userDAOImpl.deleteById(index);
        } catch (DAOException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }

        logger.traceExit(" done.");
        return result;
    }

}
