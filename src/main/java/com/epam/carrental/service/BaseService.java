package com.epam.carrental.service;

import com.epam.carrental.dao.exception.DAOException;
import com.epam.carrental.service.dto.EntityDTO;
import com.epam.carrental.service.exception.ServiceException;

import java.util.Collections;
import java.util.List;

/**
 * The {@code BaseService} interface uses CRUD model and consists of base
 * functions that need to get, delete and create information
 * by different indexes and objects in the database.
 * <p>All methods have default specifier, there is no strong recommendation to implement all of it.</p>
 */
public interface BaseService<T extends EntityDTO> {
    default List<T> getAll() throws ServiceException {
        return Collections.emptyList();
    }

    /**
     * Get {@link EntityDTO} by index.
     * @param index the integer value of ID.
     * @return T the {@link EntityDTO} object.
     * @throws ServiceException if {@link DAOException} occurs.
     */
    default T getById(int index) throws ServiceException {
        return null;
    }
    /**
     * Delete row in the table using Object.
     * @param  object value.
     * @return the {@code boolean} true if change is successful, otherwise false.
     * @throws ServiceException if {@link DAOException} occurs.
     */
    default boolean delete(T object) throws ServiceException {
        return false;
    }
    /**
     * Create row in the table using Object.
     * @param  object value.
     * @return the {@code boolean} true if change is successful, otherwise false.
     * @throws ServiceException if {@link DAOException} occurs.
     */
    default boolean create(T object) throws ServiceException {
        return false;
    }
    /**
     * Update row in the table using Object.
     * @param  object value.
     * @return the {@code boolean} true if change is successful, otherwise false.
     * @throws ServiceException if {@link DAOException} occurs.
     */
    default boolean update(T object) throws ServiceException {
        return false;
    }

}
