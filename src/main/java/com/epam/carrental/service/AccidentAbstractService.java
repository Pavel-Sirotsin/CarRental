package com.epam.carrental.service;

import com.epam.carrental.service.dto.AccidentDTO;
import com.epam.carrental.service.exception.ServiceException;
import org.apache.http.HttpRequest;

import java.util.Map;

/**
 * The {@code AccidentAbstractService} class expands {@code BaseService} interface
 * by additional functions for handling {@link AccidentDTO} object's information.
 */
public abstract class AccidentAbstractService implements BaseService<AccidentDTO> {
    /**
     * Get an accident by the booking ID.
     * @param index the booking ID value.
     * @return the {@link AccidentDTO} object.
     * @throws ServiceException if {@code DAOException} occurs.
     */
    public abstract AccidentDTO getByBookingId(Integer index) throws ServiceException;
    /**
     * Create a new accident using booking id and insurance data of rented car.
     * @param params the {@link Map} collection with {@link HttpRequest} parameters.
     * @return the {@code boolean} true if change is successful, otherwise false.
     * @throws ServiceException if {@code DAOException} occurs.
     */
    public abstract boolean createByBookingId(Map<String, String[]> params) throws ServiceException;
}
