package com.epam.carrental.service.impl;

import com.epam.carrental.service.AccidentAbstractService;
import com.epam.carrental.service.ServiceProvider;
import com.epam.carrental.service.dto.AccidentDTO;
import com.epam.carrental.service.dto.BookingDTO;
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
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccidentServiceImplTest {
    private static AccidentDTO accidentNew;
    private static Map<String, String[]> params = new HashMap<>();
    private static String[] accidentDescription = {"Test.txt description"};
    private static String[] accidentCost = {"1000.99"};
    private static String[] bookingId = {"1"};

    private static Integer index = 1;
    private boolean actual;
    private AccidentDTO accidentEmpty;

    private static AccidentAbstractService accidentImpl = ServiceProvider.INSTANCE.getAccidentImpl();

    @BeforeAll
    static void init() {
        accidentNew = new AccidentDTO(null);
        accidentNew.damageDescription = accidentDescription[0];
        accidentNew.damageAmount = Double.parseDouble(accidentCost[0]);
        accidentNew.reclaimAmount = 0.0;
        accidentNew.booking = new BookingDTO(index);

        params.put("accidentDescription", accidentDescription);
        params.put("accidentCost", accidentCost);
        params.put("bookingId", bookingId);
    }

    @BeforeEach
    void setUp() {
        actual = false;
        accidentEmpty = null;
    }

    @Test
    @Order(1)
    @DisplayName("Insert a new Accident into the Data Table using a Booking id")
    void insertAccidentUsingBookingId() throws ServiceException {
        actual = accidentImpl.createByBookingId(params);
        assertAll(
                () -> assertTrue(actual),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
                );
    }

    @Test
    @Order(2)
    @DisplayName("Get an Accident from the Data Table using a Booking id")
    void getAccidentByBookingId() throws ServiceException {
        accidentEmpty = accidentImpl.getByBookingId(1);
        assertAll(
                () -> assertNotNull(accidentEmpty.id),
                () -> assertNotNull(accidentEmpty.booking),
                () -> assertNotNull(accidentEmpty.reclaimAmount),
                () -> assertNotNull(accidentEmpty.damageDescription),
                () -> assertNotNull(accidentEmpty.damageAmount),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
        );
    }

    @Test
    @Order(3)
    @DisplayName("Delete an Accident using Booking id")
    void deleteAccidentUsingBookingId() throws ServiceException {
        actual = accidentImpl.delete(accidentNew);
        assertAll(
                () -> assertTrue(actual),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
        );
    }

    @AfterAll
    static void tearDownAll() {
        accidentNew = null;
        params = null;
        accidentDescription = null;
        accidentCost = null;
        bookingId = null;
        index = null;
        accidentImpl = null;

    }

}