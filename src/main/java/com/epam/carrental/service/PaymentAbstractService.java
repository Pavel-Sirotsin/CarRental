package com.epam.carrental.service;

import com.epam.carrental.service.dto.PaymentDTO;
import com.epam.carrental.service.exception.ServiceException;
/**
 * The {@code PaymentAbstractService} class expands {@code BaseService} interface
 * by additional functions for handling {@link PaymentDTO} object's information.
 */
public abstract class PaymentAbstractService implements BaseService<PaymentDTO> {
    /**
     * Create a payment for anonymous booking using {@link PaymentDTO}.
     * @param paymentDTO value.
     * @return the {@code boolean} true if change is successful, otherwise false.
     * @throws ServiceException if {@code DAOException} occurs.
     */
    public abstract boolean createByAnonymous(PaymentDTO paymentDTO) throws ServiceException;

}
