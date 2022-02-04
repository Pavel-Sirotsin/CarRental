package com.epam.carrental.service.impl;

import com.epam.carrental.dao.BookingAbstractDAO;
import com.epam.carrental.dao.DAOProvider;
import com.epam.carrental.dao.exception.DAOException;
import com.epam.carrental.service.BookingAbstractService;
import com.epam.carrental.service.ServiceProvider;
import com.epam.carrental.service.dto.AccountDTO;
import com.epam.carrental.service.dto.BookingDTO;
import com.epam.carrental.service.dto.CarDTO;
import com.epam.carrental.service.dto.InsuranceDTO;
import com.epam.carrental.service.exception.ServiceException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookingServiceImplTest {
    private boolean actual = false;

    private static List<BookingDTO> bookingListEmpty;
    private static BookingDTO bookingEmpty;
    private static CarDTO carNew;
    private static InsuranceDTO insuranceNew;
    private static Map<String, String[]> params = new HashMap<>();
    private static BookingDTO bookingNew;
    private static BookingDTO bookingUpdated;
    private static Integer index = 2;
    private static Integer returnedId;
    private static String[] carId = {String.valueOf(index)};
    private static String[] userId = {String.valueOf(index)};
    private static String[] rentalDate = {"2030-01-01T00:00"};
    private static String[] returnDate = {"2030-01-10T00:00"};
    private static String[] rentalLocation = {"airport"};
    private static String[] returnLocation = {"airport"};

    private static BookingAbstractService bookingImpl = ServiceProvider.INSTANCE.getBookingImpl();
    private static BookingAbstractDAO bookingDAOImpl = DAOProvider.INSTANCE.getBookingImpl();

    @BeforeAll
    static void init() {
        insuranceNew = new InsuranceDTO(index);
        insuranceNew.cost = 10.99;
        carNew = new CarDTO(index);
        carNew.pricePerDay = 10.99;
        carNew.insurance = insuranceNew;

        bookingNew = new BookingDTO(null);
        bookingNew.account = new AccountDTO(2);
        bookingNew.rentalDate = rentalDate[0];
        bookingNew.rentalLocation = rentalLocation[0];
        bookingNew.returnDate = returnDate[0];
        bookingNew.returnLocation = returnLocation[0];
        bookingNew.daysAmount = 1;
        bookingNew.sum = 100.00;
        bookingNew.paid = true;
        bookingNew.rejected = false;
        bookingNew.car = carNew;
        bookingNew.accidentFree = true;

        params.put("carId", carId);
        params.put("userId", userId);
        params.put("rentalDate", rentalDate);
        params.put("returnDate", returnDate);
        params.put("rentalLocation", rentalLocation);
        params.put("returnLocation", returnLocation);
    }

    @BeforeEach
    void setUp() {
        bookingEmpty = null;
        bookingListEmpty = null;
        actual = false;
    }

    @Test
    @Order(1)
    @DisplayName("Get all orders from the Data Table in List")
    void getAllOrdersInList() throws ServiceException {
        bookingListEmpty = bookingImpl.getAll();
        assertAll(
                () -> assertNotNull(bookingListEmpty),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
        );
    }

    @Test
    @Order(2)
    @DisplayName("Get an order using id")
    void geById() throws ServiceException {
        bookingEmpty = bookingImpl.getById(index);
        assertAll(
                () -> assertNotNull(bookingEmpty.id),
                () -> assertNotNull(bookingEmpty.car),
                () -> assertNotNull(bookingEmpty.daysAmount),
                () -> assertNotNull(bookingEmpty.rentalDate),
                () -> assertNotNull(bookingEmpty.returnDate),
                () -> assertNotNull(bookingEmpty.sum),
                () -> assertNotNull(bookingEmpty.paid),
                () -> assertNotNull(bookingEmpty.accidentFree),
                () -> assertNotNull(bookingEmpty.account),
                () -> assertNotNull(bookingEmpty.rejected),
                () -> assertNotNull(bookingEmpty.rentalLocation),
                () -> assertNotNull(bookingEmpty.returnLocation),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
        );
    }

    @Test
    @Order(3)
    @DisplayName("Get all orders using Account id")
    void getAllByAccountId() throws ServiceException {
        bookingListEmpty = bookingImpl.getAllByAccountId(index);
        assertAll(
                () -> assertNotNull(bookingListEmpty),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
        );
    }

    @Test
    @Order(4)
    @DisplayName("Insert into the Data Table a new Booking and return id")
    void createBookingAndReturnId() throws ServiceException {
        returnedId = bookingImpl.createReturnID(bookingNew);
        assertAll(
                () -> assertNotNull(returnedId),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
        );
    }

    @Test
    @Order(5)
    @DisplayName("Insert into the Data Table a new Booking using car and account id")
    void createBookingByCarAndAccountId() throws ServiceException {
        String[] bookingId = {String.valueOf(100)};
        params.put("bookingId", bookingId);
        actual = bookingImpl.createByUserAndCarId(params);
        assertAll(
                () -> assertTrue(actual),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
        );
    }

    @Test
    @Order(6)
    @DisplayName("Insert into the Data Table a new Booking")
    void createBooking() throws ServiceException {
        bookingNew.id = 101;
        actual = bookingImpl.create(bookingNew);
        assertAll(
                () -> assertTrue(actual),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
        );
    }

    @Test
    @Order(7)
    @DisplayName("Delete a Booking by id")
    void deleteById() throws ServiceException {
        String[] bookingId = {String.valueOf(returnedId)};
        params.put("bookingId", bookingId);
        actual = bookingImpl.deleteById(params);
        assertAll(
                () -> assertTrue(actual),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
        );
    }

    @AfterAll
    static void tearDownAll() throws DAOException {
        bookingDAOImpl.deleteById(100);
        bookingDAOImpl.deleteById(101);
        params = null;
        carNew = null;
        bookingListEmpty = null;
        bookingNew = null;
        bookingUpdated = null;
        bookingEmpty = null;
        returnedId = null;
        index = null;
        bookingImpl = null;
        bookingDAOImpl = null;
    }
}