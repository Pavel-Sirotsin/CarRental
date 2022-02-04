package com.epam.carrental.service.converter.impl;

import com.epam.carrental.dao.entity.AccountDAO;
import com.epam.carrental.service.converter.BaseConverter;
import com.epam.carrental.service.dto.AccountDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;
/**
 * The {@code AccountConverter} class implements methods from {@code BaseConverter} interface
 * and makes simple conversion from {@link AccountDTO} object to {@link AccountDAO} object.
 * */
public class AccountConverter implements BaseConverter<AccountDTO, AccountDAO> {
    private static final Logger logger = LogManager.getFormatterLogger(AccountConverter.class);
    /**
     * Converts {@link AccountDAO} object to {@link AccountDTO}.
     * @param accountDAO the value to be converted.
     * @return the new {@link AccountDTO} object.
     */
    @Override
    public AccountDTO convert(AccountDAO accountDAO) {
        logger.traceEntry("- try to convert Account from DAO to DTO.");
        AccountDTO accountDTO = new AccountDTO(accountDAO.getId());
        accountDTO.name = accountDAO.getName();
        accountDTO.surname = accountDAO.getSurname();
        accountDTO.email = accountDAO.getEmail();
        accountDTO.phoneNumber = accountDAO.getPhoneNumber();
        accountDTO.drivingLicense = accountDAO.getDrivingLicense();
        logger.traceExit(" with accountDTO: %s", new ObjectMessage(accountDTO));
        return accountDTO;
    }
    /**
     * Converts {@link AccountDTO} object to {@link AccountDAO}.
     * @param    accountDTO the value to be converted.
     * @return   the {@link AccountDAO} object.
     */
    @Override
    public AccountDAO convert(AccountDTO accountDTO) {
        logger.debug("converting Account from DTO to DAO.");
        return new AccountDAO.Builder(accountDTO.id).
                name(accountDTO.name).
                surname(accountDTO.surname).
                email(accountDTO.email).
                phoneNumber(accountDTO.phoneNumber).
                drivingLicense(accountDTO.drivingLicense).
                build();
    }
}
