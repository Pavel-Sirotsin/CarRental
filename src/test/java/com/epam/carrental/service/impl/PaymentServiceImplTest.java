package com.epam.carrental.service.impl;

import com.epam.carrental.dao.AccidentAbstractDAO;
import com.epam.carrental.dao.AccountAbstractDAO;
import com.epam.carrental.dao.BookingAbstractDAO;
import com.epam.carrental.dao.DAOProvider;
import com.epam.carrental.dao.entity.AccidentDAO;
import com.epam.carrental.dao.entity.BookingDAO;
import com.epam.carrental.dao.exception.DAOException;
import com.epam.carrental.service.PaymentAbstractService;
import com.epam.carrental.service.ServiceProvider;
import com.epam.carrental.service.converter.BaseConverter;
import com.epam.carrental.service.converter.impl.AccidentConverter;
import com.epam.carrental.service.converter.impl.BookingConverter;
import com.epam.carrental.service.dto.AccidentDTO;
import com.epam.carrental.service.dto.AccountDTO;
import com.epam.carrental.service.dto.BookingDTO;
import com.epam.carrental.service.dto.CarDTO;
import com.epam.carrental.service.dto.InsuranceDTO;
import com.epam.carrental.service.dto.PaymentDTO;
import com.epam.carrental.service.exception.ServiceException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PaymentServiceImplTest {
    private boolean actual;

    private static PaymentDTO paymentEmpty;
    private static PaymentDTO paymentNew;
    private static InsuranceDTO insuranceForTest;
    private static AccidentDTO accidentForTest;
    private static CarDTO carNew;
    private static AccountDTO accountForTest;
    private static BookingDTO bookingForTest;

    private static PaymentAbstractService paymentImpl = ServiceProvider.INSTANCE.getPaymentImpl();

    private static BookingAbstractDAO bookingDAOImpl = DAOProvider.INSTANCE.getBookingImpl();
    private static AccidentAbstractDAO accidentDAOImpl = DAOProvider.INSTANCE.getAccidentImpl();
    private static AccountAbstractDAO accountDAOImpl = DAOProvider.INSTANCE.getAccountImpl();

    private static BaseConverter<BookingDTO, BookingDAO> bookingConverter = new BookingConverter();
    private static BaseConverter<AccidentDTO, AccidentDAO> accidentConverter = new AccidentConverter();


    @BeforeAll
    static void init() throws DAOException {
        insuranceForTest = new InsuranceDTO(1);
        insuranceForTest.cost = 5.99;

        carNew = new CarDTO(1);
        carNew.pricePerDay = 9.99;
        carNew.insurance = insuranceForTest;

        accountForTest = new AccountDTO(null);
        accountForTest.name = "firstName";
        accountForTest.surname = "lastName";
        accountForTest.email = "email@gmail.com";
        accountForTest.phoneNumber = "+37529123456";
        accountForTest.drivingLicense = "AB123456";

        bookingForTest = new BookingDTO(100);
        bookingForTest.account = new AccountDTO(1);
        bookingForTest.paid = false;
        bookingForTest.car = carNew;
        bookingForTest.accidentFree = true;
        bookingForTest.rentalDate = "2030-01-01T00:00";
        bookingForTest.rentalLocation = "airport";
        bookingForTest.returnDate = "2030-01-05T00:00";
        bookingForTest.returnLocation = "airport";
        bookingForTest.rejected = false;
        bookingForTest.daysAmount = 10;
        bookingForTest.sum = 199.99;
        bookingDAOImpl.create(bookingConverter.convert(bookingForTest));

        accidentForTest = new AccidentDTO(null);
        accidentForTest.damageDescription = "Test.txt description";
        accidentForTest.booking = bookingForTest;
        accidentForTest.damageAmount = 599.99;
        accidentForTest.reclaimAmount = 0.00;

        accidentDAOImpl.create(accidentConverter.convert(accidentForTest));

        paymentNew = new PaymentDTO(null);
        paymentNew.cvv = "111";
        paymentNew.sum = 100.99;
        paymentNew.cardNumber = "5418 5296 3741 8523";
        paymentNew.holderName = "Billy Idol";
        paymentNew.booking = bookingForTest;
        paymentNew.expirationDate = "11/12";

    }

    @BeforeEach
    void setUp() {
        actual = false;
    }

    @Test
    @Order(1)
    @DisplayName("Get a Payment using id")
    void getPaymentById() throws ServiceException {
        paymentEmpty = paymentImpl.getById(1);
        assertAll(
                () -> assertNotNull(paymentEmpty.id),
                () -> assertNotNull(paymentEmpty.booking),
                () -> assertNotNull(paymentEmpty.cardNumber),
                () -> assertNotNull(paymentEmpty.cvv),
                () -> assertNotNull(paymentEmpty.expirationDate),
                () -> assertNotNull(paymentEmpty.holderName),
                () -> assertNotNull(paymentEmpty.reclamation),
                () -> assertNotNull(paymentEmpty.sum),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
        );
    }

    @Test
    @Order(2)
    @DisplayName("Insert into the Data table a new Payment")
    void insertPayment() throws ServiceException {
        actual = paymentImpl.create(paymentNew);
        assertAll(
                () -> assertTrue(actual),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
        );
    }

    @Test
    @Order(3)
    @DisplayName("Update Payment using Booking id and Accident data")
    void updatePayment() throws ServiceException {
        paymentNew.reclamation = 299.89;
        actual = paymentImpl.update(paymentNew);
        assertAll(
                () -> assertTrue(actual),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
        );
    }

    @Test
    @Order(4)
    @DisplayName("Insert into the table a new Payment using Booking from anonymous Account")
    void insertPaymentByBookingAndAccount() throws ServiceException {
        bookingForTest.id = null;
        bookingForTest.account = accountForTest;
        paymentNew.id = 100;
        actual = paymentImpl.createByAnonymous(paymentNew);
        assertAll(
                () -> assertTrue(actual),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
        );
    }

    @Test
    @Order(5)
    @DisplayName("Delete Payment Transaction including Account, Booking using id")
    void deletePaymentTransactionById() throws ServiceException {
        actual = paymentImpl.delete(paymentNew);
        assertAll(
                () -> assertTrue(actual),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
        );
    }

    @AfterAll
    static void tearDownAll() throws DAOException {
        bookingDAOImpl.deleteById(100);
        paymentNew = null;
        paymentEmpty = null;
        paymentImpl = null;
        bookingDAOImpl = null;
        bookingForTest = null;
        accountForTest = null;
        accountDAOImpl = null;
        accidentDAOImpl = null;
        insuranceForTest = null;
        carNew = null;
        bookingConverter = null;
        accidentConverter = null;

    }

}