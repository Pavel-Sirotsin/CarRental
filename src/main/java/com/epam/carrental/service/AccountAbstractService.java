package com.epam.carrental.service;

import com.epam.carrental.service.dto.AccountDTO;
import com.epam.carrental.service.exception.ServiceException;

/**
 * The {@code AccountAbstractService} class expands {@code BaseService} interface
 * by additional functions for handling {@link AccountDTO} object's information.
 */
public abstract class AccountAbstractService implements BaseService<AccountDTO> {
    /**
     * Get an account by user id.
     * @param index value of user ID.
     * @return the {@link AccountDTO} object.
     * @throws ServiceException if {@code DAOException} occurs.
     */
    public abstract AccountDTO getByUserId(int index) throws ServiceException;

}
