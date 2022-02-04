package com.epam.carrental.service;

import com.epam.carrental.service.dto.UserDTO;
import com.epam.carrental.service.exception.ServiceException;
import org.apache.http.HttpRequest;

import java.util.Map;

/**
 * The {@code UserAbstractService} class expands {@code BaseService} interface
 * by additional functions for handling {@link UserDTO} object's information.
 */
public abstract class UserAbstractService implements BaseService<UserDTO> {
    /**
     * Create a new user profile using {@link UserDTO} object.
     * @param userDTO value.
     * @return the {@code boolean} true if change is successful, otherwise false.
     * @throws ServiceException if {@code DAOException} occurs.
     */
    public abstract boolean createByUserEntity(UserDTO userDTO) throws ServiceException;
    /**
     * Get a user by login and password - user logination.
     * @param userDTO value.
     * @return the {@link UserDTO} object with user profile information.
     * @throws ServiceException if {@code DAOException} occurs.
     */
    public abstract UserDTO getByLogin(UserDTO userDTO) throws ServiceException;
    /**
     * Update user profile using {@link UserDTO} object and {@code Account} fields
     * from {@link HttpRequest} parameters.
     * @param userDTO value.
     * @param params {@link Map} collection with {@link HttpRequest} parameters.
     * @return the {@link UserDTO} object with changes.
     * @throws ServiceException if {@code DAOException} occurs.
     */
    public abstract UserDTO updatePartly(UserDTO userDTO, Map<String, String[]> params) throws ServiceException;
    /**
     * Delete a user profile using id.
     * @param params {@link Map} collection with {@link HttpRequest} parameters.
     * @return the {@code boolean} true if change is successful, otherwise false.
     * @throws ServiceException if {@code DAOException} occurs.
     */
    public abstract boolean deleteById(Map<String, String[]> params) throws ServiceException;

}
