package com.epam.carrental.service.impl;

import com.epam.carrental.service.AccountAbstractService;
import com.epam.carrental.service.ServiceProvider;
import com.epam.carrental.service.dto.AccountDTO;
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
class AccountServiceImplTest {
    private static AccountDTO accountNew;
    private static AccountDTO accountEmpty;
    private static Integer index = 1;
    private boolean actual;

    private static AccountAbstractService accountImpl = ServiceProvider.INSTANCE.getAccountImpl();

    @BeforeAll
    static void init() {
        accountNew = new AccountDTO(100);
        accountNew.name = "firstName";
        accountNew.surname = "lastName";
        accountNew.email = "email@gmail.com";
        accountNew.phoneNumber = "+37529123456";
        accountNew.drivingLicense = "AB123456";

    }

    @BeforeEach
    void setUp() {
        accountEmpty = null;
        actual = false;
    }

    @Test
    @Order(1)
    @DisplayName("Insert a new Account into the Data Table")
    void insertAccount() throws ServiceException {
        actual = accountImpl.create(accountNew);
        assertAll(
                () -> assertTrue(actual),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
        );
    }

    @Test
    @Order(2)
    @DisplayName("Update an Account by new Data")
    void updateAccount() throws ServiceException {
        accountNew.drivingLicense = "ZY000111";
        actual = accountImpl.update(accountNew);
        assertAll(
                () -> assertTrue(actual),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
        );
    }

    @Test
    @Order(3)
    @DisplayName("Get an Account by id")
    void getAccountById() throws ServiceException {
        accountEmpty = accountImpl.getById(accountNew.id);
        assertAll(
                () -> assertNotNull(accountEmpty.drivingLicense),
                () -> assertNotNull(accountEmpty.email),
                () -> assertNotNull(accountEmpty.id),
                () -> assertNotNull(accountEmpty.phoneNumber),
                () -> assertNotNull(accountEmpty.name),
                () -> assertNotNull(accountEmpty.surname),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
        );
    }

    @Test
    @Order(4)
    @DisplayName("Get an Account by User id")
    void getAccountByUserId() throws ServiceException {
        accountEmpty = accountImpl.getByUserId(index);
        assertAll(
                () -> assertNotNull(accountEmpty.drivingLicense),
                () -> assertNotNull(accountEmpty.email),
                () -> assertNotNull(accountEmpty.id),
                () -> assertNotNull(accountEmpty.phoneNumber),
                () -> assertNotNull(accountEmpty.name),
                () -> assertNotNull(accountEmpty.surname),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
        );
    }

    @Test
    @Order(5)
    @DisplayName("Delete an Account using id")
    void deleteAccountById() throws ServiceException {
        actual = accountImpl.delete(accountNew);
        assertAll(
                () -> assertTrue(actual),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
        );
    }

    @AfterAll
    static void tearDownAll() {
        accountNew = null;
        accountEmpty = null;
        index = null;
        accountImpl = null;
    }

}