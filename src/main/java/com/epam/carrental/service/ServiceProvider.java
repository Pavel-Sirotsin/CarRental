package com.epam.carrental.service;

import com.epam.carrental.service.dto.CarDTO;
import com.epam.carrental.service.dto.InsuranceDTO;
import com.epam.carrental.service.impl.AccidentServiceImpl;
import com.epam.carrental.service.impl.AccountServiceImpl;
import com.epam.carrental.service.impl.BookingServiceImpl;
import com.epam.carrental.service.impl.CarServiceImpl;
import com.epam.carrental.service.impl.InsuranceServiceImpl;
import com.epam.carrental.service.impl.PaymentServiceImpl;
import com.epam.carrental.service.impl.RejectionServiceImpl;
import com.epam.carrental.service.impl.UserServiceImpl;

public enum ServiceProvider {
    INSTANCE;

    private static final BaseService<CarDTO> carImpl = new CarServiceImpl();
    private static final AccountAbstractService accountImpl = new AccountServiceImpl();
    private static final AccidentAbstractService accidentImpl = new AccidentServiceImpl();
    private static final BookingAbstractService bookingImpl = new BookingServiceImpl();
    private static final BaseService<InsuranceDTO> insuranceImpl = new InsuranceServiceImpl();
    private static final UserAbstractService userImpl = new UserServiceImpl();
    private static final RejectionAbstractService rejectionImpl = new RejectionServiceImpl();
    private static final PaymentAbstractService paymentImpl = new PaymentServiceImpl();


    public UserAbstractService getUserImpl() {
        return userImpl == null ? new UserServiceImpl() : userImpl;
    }

    public BaseService<CarDTO> getCarImpl() {
        return carImpl == null ? new CarServiceImpl() : carImpl;
    }

    public AccidentAbstractService getAccidentImpl() {
        return accidentImpl == null ? new AccidentServiceImpl() : accidentImpl;
    }

    public BookingAbstractService getBookingImpl() {
        return bookingImpl == null ? new BookingServiceImpl() : bookingImpl;
    }

    public BaseService<InsuranceDTO> getInsuranceImpl() {
        return insuranceImpl == null ? new InsuranceServiceImpl() : insuranceImpl;
    }

    public AccountAbstractService getAccountImpl() {
        return accountImpl == null ? new AccountServiceImpl() : accountImpl;
    }

    public PaymentAbstractService getPaymentImpl() {
        return paymentImpl == null ? new PaymentServiceImpl() : paymentImpl;
    }

    public RejectionAbstractService getRejectionImpl() {
        return rejectionImpl == null ? new RejectionServiceImpl() : rejectionImpl;
    }


}
