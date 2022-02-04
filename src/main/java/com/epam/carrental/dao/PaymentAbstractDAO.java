package com.epam.carrental.dao;

import com.epam.carrental.dao.entity.AccountDAO;
import com.epam.carrental.dao.entity.BookingDAO;
import com.epam.carrental.dao.entity.PaymentDAO;
import com.epam.carrental.dao.exception.DAOException;

public abstract class PaymentAbstractDAO implements BaseDAO<PaymentDAO> {

    public abstract PaymentDAO findByBookingId(Integer index) throws DAOException;

    public abstract boolean createByAnonymous(BookingDAO bookingDAO, AccountDAO accountDAO, PaymentDAO paymentDAO) throws DAOException;

}
