package com.epam.carrental.service;

import com.epam.carrental.service.dto.RejectionDTO;
import com.epam.carrental.service.exception.ServiceException;
import org.apache.http.HttpRequest;

import java.util.Map;
/**
 * The {@code RejectionAbstractService} class expands {@code BaseService} interface
 * by additional functions for handling {@link RejectionDTO} object's information.
 */
public abstract class RejectionAbstractService implements BaseService<RejectionDTO> {
    /**
     * Create a rejection using id.
     * @param params {@link Map} collection with {@link HttpRequest} parameters.
     * @return the {@code boolean} true if change is successful, otherwise false.
     * @throws ServiceException if {@code DAOException} occurs.
     */
    public abstract boolean createById(Map<String, String[]> params) throws ServiceException;
}
