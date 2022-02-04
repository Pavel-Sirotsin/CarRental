package com.epam.carrental.dao;

import com.epam.carrental.dao.entity.CarDAO;
import com.epam.carrental.dao.entity.RejectionDAO;
import com.epam.carrental.dao.impl.AccidentDAOImpl;
import com.epam.carrental.dao.impl.AccountDAOImpl;
import com.epam.carrental.dao.impl.BookingDAOImpl;
import com.epam.carrental.dao.impl.CarDAOImpl;
import com.epam.carrental.dao.impl.InsuranceDAOImpl;
import com.epam.carrental.dao.impl.PaymentDAOImpl;
import com.epam.carrental.dao.impl.RejectionDAOImpl;
import com.epam.carrental.dao.impl.UserDAOImpl;

public enum DAOProvider {
    INSTANCE;

    private static final AccidentAbstractDAO accidentImpl = new AccidentDAOImpl();
    private static final AccountAbstractDAO accountImpl = new AccountDAOImpl();
    private static final BookingAbstractDAO bookingImpl = new BookingDAOImpl();
    private static final BaseDAO<CarDAO> carImpl = new CarDAOImpl();
    private static final InsuranceAbstractDAO insuranceImpl = new InsuranceDAOImpl();
    private static final PaymentAbstractDAO paymentImpl = new PaymentDAOImpl();
    private static final BaseDAO<RejectionDAO> rejectionImpl = new RejectionDAOImpl();
    private static final UserAbstractDAO userImpl = new UserDAOImpl();

    public BookingAbstractDAO getBookingImpl() {

        return bookingImpl;
    }

    public AccountAbstractDAO getAccountImpl() {
        return accountImpl;
    }


    public PaymentAbstractDAO getPaymentImpl() {
        return paymentImpl;
    }

    public AccidentAbstractDAO getAccidentImpl() {
        return accidentImpl;
    }

    public InsuranceAbstractDAO getInsuranceImpl() {
        return insuranceImpl;
    }

    public BaseDAO<CarDAO> getCarImpl() {
        return carImpl;
    }

    public BaseDAO<RejectionDAO> getRejectionImpl() {
        return rejectionImpl;
    }

    public UserAbstractDAO getUserImpl() {
        return userImpl;
    }
}
