package com.epam.carrental.service.impl;

import com.epam.carrental.dao.AccountAbstractDAO;
import com.epam.carrental.dao.DAOProvider;
import com.epam.carrental.dao.entity.AccountDAO;
import com.epam.carrental.dao.exception.DAOException;
import com.epam.carrental.service.AccountAbstractService;
import com.epam.carrental.service.converter.BaseConverter;
import com.epam.carrental.service.converter.impl.AccountConverter;
import com.epam.carrental.service.dto.AccountDTO;
import com.epam.carrental.service.exception.ServiceException;
import com.epam.carrental.service.validator.impl.AccountValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;
/**
 * The {@code AccountServiceImpl} class implements methods from the {@code BaseService} interface
 * and the {@link AccountAbstractService} class. Is used to manipulate of an account information
 * at the service layer.
 */
public class AccountServiceImpl extends AccountAbstractService {
    private static final Logger logger = LogManager.getFormatterLogger(AccountServiceImpl.class);
    /**
     * The field holding the account implementation of the DAO layer.
     */
    private final AccountAbstractDAO accountDAOImpl = DAOProvider.INSTANCE.getAccountImpl();
    /**
     * The field holding the {@link AccountConverter} implementation.
     */
    private final BaseConverter<AccountDTO, AccountDAO> converter = new AccountConverter();
    /**
     * The field holding the {@link AccountValidator} implementation.
     */
    private final AccountValidator validator = new AccountValidator();

    @Override
    public AccountDTO getById(int index) throws ServiceException {
        logger.traceEntry("- try to get account by id.");
        AccountDTO accountDto;
        try {
            accountDto = converter.convert(accountDAOImpl.findById(index));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        logger.traceExit(" with accountDto: %s", new ObjectMessage(accountDto));
        return accountDto;
    }

    @Override
    public AccountDTO getByUserId(int userId) throws ServiceException {
        logger.traceEntry("- try to get account by user id.");
        AccountDTO accountDto;
        try {
            accountDto = converter.convert(accountDAOImpl.findByUserId(userId));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        logger.traceExit(" with accountDto: %s", new ObjectMessage(accountDto));
        return accountDto;
    }

    @Override
    public boolean create(AccountDTO accountDto) throws ServiceException {
        logger.traceEntry("- try to create a new account.");
        validator.validate(accountDto);

        AccountDAO accountDAO = converter.convert(accountDto);
        logger.debug("line(%d), accountDAO: %s", 63, new ObjectMessage(accountDAO));

        boolean result;
        try {
            result = accountDAOImpl.create(accountDAO);
        } catch (DAOException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new ServiceException(ex.getMessage(), ex);
        }

        logger.traceExit(" done.");
        return result;
    }

    @Override
    public boolean update(AccountDTO accountDTO) throws ServiceException {
        logger.traceEntry("- try to update account.");
        validator.validate(accountDTO);

        AccountDAO accountDAO = converter.convert(accountDTO);
        logger.debug("line(%d), accountDAO: %s", 83, new ObjectMessage(accountDAO));

        boolean result;
        try {
            result = accountDAOImpl.update(accountDAO);
        } catch (DAOException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new ServiceException(ex.getMessage(), ex);
        }

        logger.traceExit(" done.");
        return result;
    }

    @Override
    public boolean delete(AccountDTO accountDTO) throws ServiceException {
        logger.traceEntry("- try to delete account by id.");
        boolean result;
        try {
            result = accountDAOImpl.deleteById(accountDTO.id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        logger.traceExit(" with: %b", result);
        return result;
    }

}
