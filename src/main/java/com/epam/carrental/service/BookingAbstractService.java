package com.epam.carrental.service;

import com.epam.carrental.service.dto.BookingDTO;
import com.epam.carrental.service.exception.ServiceException;
import org.apache.http.HttpRequest;

import java.util.List;
import java.util.Map;
/**
 * The {@code BookingAbstractService} class expands {@code BaseService} interface
 * by additional functions for handling {@link BookingDTO} object's information.
 */
public abstract class BookingAbstractService implements BaseService<BookingDTO> {
    /**
     * Create a new booking profile using {@link BookingDTO} object.
     * @param bookingDTO value.
     * @return the {@code Integer} ID value.
     * @throws ServiceException if {@code DAOException} occurs.
     */
    public abstract Integer createReturnID(BookingDTO bookingDTO) throws ServiceException;
    /**
     * Get all client's orders in {@link List} collection using user ID.
     * @param index the user id value.
     * @return the {@link List} collection of orders.
     * @throws ServiceException if {@code DAOException} occurs.
     */
    public abstract List<BookingDTO> getAllByAccountId(Integer index) throws ServiceException;
    /**
     * Delete a booking using id.
     * @param params the {@link Map} collection of {@link HttpRequest} parameters.
     * @return the {@code boolean} true if change is successful, otherwise false.
     * @throws ServiceException if {@code DAOException} occurs.
     */
    public abstract boolean deleteById(Map<String, String[]> params) throws ServiceException;
    /**
     * Create a new booking using user and car ID.
     * @param params the {@link Map} collection of {@link HttpRequest} parameters.
     * @return the {@code boolean} true if change is successful, otherwise false.
     * @throws ServiceException if {@code DAOException} occurs.
     */
    public abstract boolean createByUserAndCarId(Map<String, String[]> params) throws ServiceException;

}
