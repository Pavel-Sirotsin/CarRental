package com.epam.carrental.service.impl;

import com.epam.carrental.dao.BookingAbstractDAO;
import com.epam.carrental.dao.DAOProvider;
import com.epam.carrental.dao.entity.BookingDAO;
import com.epam.carrental.dao.exception.DAOException;
import com.epam.carrental.service.RejectionAbstractService;
import com.epam.carrental.service.ServiceProvider;
import com.epam.carrental.service.converter.BaseConverter;
import com.epam.carrental.service.converter.impl.BookingConverter;
import com.epam.carrental.service.dto.AccountDTO;
import com.epam.carrental.service.dto.BookingDTO;
import com.epam.carrental.service.dto.CarDTO;
import com.epam.carrental.service.dto.RejectionDTO;
import com.epam.carrental.service.exception.ServiceException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RejectionServiceImplTest {
    private static RejectionDTO rejectionNew;
    private static RejectionDTO rejectionEmpty;
    private static BookingDTO bookingForTest;
    private static boolean actual;
    private static Map<String, String[]> params = new HashMap<>();
    private static String[] bookingId = {String.valueOf(100)};
    private static String[] rejectionReason = {"Test.txt reason"};

    private static RejectionAbstractService rejectionImpl = ServiceProvider.INSTANCE.getRejectionImpl();
    private static BookingAbstractDAO bookingDAOImpl = DAOProvider.INSTANCE.getBookingImpl();
    private static BaseConverter<BookingDTO, BookingDAO> bookingConverter = new BookingConverter();

    @BeforeAll
    static void init() throws DAOException {
        bookingForTest = new BookingDTO(100);
        bookingForTest.account = new AccountDTO(1);
        bookingForTest.paid = false;
        bookingForTest.car = new CarDTO(1);
        bookingForTest.accidentFree = true;
        bookingForTest.rentalDate = "2030-01-01T00:00";
        bookingForTest.rentalLocation = "airport";
        bookingForTest.returnDate = "2030-01-05T00:00";
        bookingForTest.returnLocation = "airport";
        bookingForTest.rejected = false;
        bookingForTest.daysAmount = 10;
        bookingForTest.sum = 199.99;
        bookingDAOImpl.create(bookingConverter.convert(bookingForTest));

        params.put("bookingId", bookingId);
        params.put("rejectionReason", rejectionReason);

        rejectionNew = new RejectionDTO(null);
        rejectionNew.rejectionReason = rejectionReason[0];
        rejectionNew.bookingId = 100;
    }

    @Test
    @Order(1)
    @DisplayName("Insert into the Data Table a new Rejection using Booking id")
    void createRejectionByBookingId() throws ServiceException {
        actual = rejectionImpl.createById(params);
        assertAll(
                () -> assertTrue(actual),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
        );
    }


    @Test
    @Order(2)
    @DisplayName("Get Rejection using Booking id")
    void getRejectionById() throws ServiceException {
        rejectionEmpty = rejectionImpl.getById(bookingForTest.id);
        assertAll(
                () -> assertNotNull(rejectionEmpty.rejectionReason),
                () -> assertNotNull(rejectionEmpty.id),
                () -> assertNotNull(rejectionEmpty.bookingId),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
        );
    }

    @AfterAll
    static void tearDownAll() throws DAOException {
        bookingDAOImpl.deleteById(bookingForTest.id);
        rejectionNew = null;
        rejectionEmpty = null;
        rejectionImpl = null;
        rejectionReason = null;
        params = null;
        bookingDAOImpl = null;
        bookingConverter = null;
        bookingId = null;
        bookingForTest = null;


    }

}