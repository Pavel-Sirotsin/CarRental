package com.epam.carrental.service.impl;

import com.epam.carrental.dao.AccountAbstractDAO;
import com.epam.carrental.dao.DAOProvider;
import com.epam.carrental.dao.UserAbstractDAO;
import com.epam.carrental.dao.entity.UserRole;
import com.epam.carrental.service.ServiceProvider;
import com.epam.carrental.service.UserAbstractService;
import com.epam.carrental.service.dto.AccountDTO;
import com.epam.carrental.service.dto.UserDTO;
import com.epam.carrental.service.exception.ServiceException;
import com.epam.carrental.service.util.Cypher;
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
class UserServiceImplTest {
    private static List<UserDTO> userListEmpty;
    private static UserDTO userNew;
    private static UserDTO userEmpty;
    private static AccountDTO accountForTest;
    private static boolean actual;
    private static String[] block = {"first"};
    private static String[] name = {"TestName"};
    private static String[] surname = {"TestSurname"};
    private static String[] email = {"email@gmail.com"};
    private static String[] phone = {"+375291234566"};
    private static String[] license = {"AB123456"};
    private static String[] userName = {"Login"};
    private static String[] userPassword = {"Password"};
    private static String[] newPassword = {"UpdatedPassword"};
    private static String[] userId = {"100"};
    private static Map<String, String[]> params = new HashMap<>();
    private static UserAbstractService userImpl = ServiceProvider.INSTANCE.getUserImpl();
    private static UserAbstractDAO userDAOImpl = DAOProvider.INSTANCE.getUserImpl();
    private static AccountAbstractDAO accountDAOImpl = DAOProvider.INSTANCE.getAccountImpl();


    @BeforeAll
    static void init() {
        accountForTest = new AccountDTO(null);
        accountForTest.name = name[0];
        accountForTest.surname = surname[0];
        accountForTest.email = email[0];
        accountForTest.phoneNumber = phone[0];
        accountForTest.drivingLicense = license[0];

        userNew = new UserDTO(100);
        userNew.login = userName[0];
        userNew.role = UserRole.CLIENT;
        userNew.password = userPassword[0];
        userNew.account = accountForTest;

        params.put("block", block);
        params.put("firstName", name);
        params.put("lastName", surname);
        params.put("email", email);
        params.put("phone", phone);
        params.put("license", license);
        params.put("userName", userName);
        params.put("userPassword", userPassword);
        params.put("newPassword", newPassword);
        params.put("userId", userId);
    }

    @BeforeEach
    void setUp() {
        actual = false;
        userEmpty = null;
    }

    @Test
    @Order(1)
    @DisplayName("Get all users in List")
    void getAllUsers() throws ServiceException {
        userListEmpty = userImpl.getAll();
        assertAll(
                () -> assertNotNull(userListEmpty),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
        );
    }

    @Test
    @Order(2)
    void makeTransaction() throws ServiceException {
        actual = userImpl.createByUserEntity(userNew);
        assertAll(
                () -> assertTrue(actual),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
        );
    }

    @Test
    @Order(3)
    @DisplayName("Update User profile partly by new data")
    void updateUserAccountData() throws ServiceException {
        name[0] = "UpdatedName";
        userEmpty = userImpl.updatePartly(userNew, params);
        assertAll(
                () -> assertEquals(name[0], userEmpty.account.name),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
        );
        block[0] = "second";
        phone[0] = "+37529000000";
        userEmpty = userImpl.updatePartly(userNew, params);
        assertAll(
                () -> assertEquals(phone[0], userEmpty.account.phoneNumber),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
        );
        block[0] = "third";
        userName[0] = "UpdatedLogin";
        userNew.password = Cypher.encryptPassword(userPassword[0]);
        userEmpty = userImpl.updatePartly(userNew, params);
        assertAll(
                () -> assertEquals(userName[0], userEmpty.login),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
        );
    }

    @Test
    @Order(4)
    @DisplayName("Get User using Login and Password")
    void getUserByLoginWithCorrectPassword() throws ServiceException {
        userNew.login = "UpdatedLogin";
        userNew.password = newPassword[0];
        userEmpty = userImpl.getByLogin(userNew);
        assertAll(
                () -> assertNotNull(userEmpty.id),
                () -> assertNotNull(userEmpty.account),
                () -> assertNotNull(userEmpty.login),
                () -> assertNotNull(userEmpty.password),
                () -> assertNotNull(userEmpty.role),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
        );

    }

    @Test
    @Order(5)
    @DisplayName("Should throw ServiceException with message")
    void getUserByLoginWithWrongPassword() throws ServiceException {
        userNew.login = "UpdatedLogin";
        userNew.password = "WrongPassword";
        Exception exception = assertThrows(ServiceException.class, () -> userImpl.getByLogin(userNew));
        String expected = "warning.log.in";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    @Order(6)
    @DisplayName("Delete User using id")
    void deleteUserById() throws ServiceException {
        actual = userImpl.deleteById(params);
        assertAll(
                () -> assertTrue(actual),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
        );
    }

    @AfterAll
    static void tearDownAll() {
        userNew = null;
        userEmpty = null;
        userImpl = null;
        userListEmpty = null;
        userDAOImpl = null;
        accountForTest = null;
        accountDAOImpl = null;
        params = null;
    }

}