package com.epam.carrental.service.converter;

import com.epam.carrental.dao.entity.EntityDAO;
import com.epam.carrental.service.dto.EntityDTO;
import com.epam.carrental.service.exception.ServiceException;

/**
 * The {@code BaseConverter} interface provides converting from DTO entity
 * to DAO.
 */
public interface BaseConverter<T extends EntityDTO, K extends EntityDAO> {
    /**
     * Converts from {@link EntityDAO} object to {@link EntityDTO}.
     */
    T convert(K k) throws ServiceException;
    /**
     * Converts from {@link EntityDTO} object to {@link EntityDAO}
     */
    K convert(T t);

}
