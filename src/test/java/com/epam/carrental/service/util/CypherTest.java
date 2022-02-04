package com.epam.carrental.service.util;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CypherTest {
    private static String password = "1234";
    private static String encryptedPassword = "MTIzNA==";

    @Test
    @DisplayName("Should return String value of encrypted password")
    void encryptPassword() {
        String expected = "MTIzNA==";
        String actual = Cypher.encryptPassword(password);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should return String value of decrypted password")
    void decryptPassword() {
        String expected = "1234";
        String actual = Cypher.decryptPassword(encryptedPassword);
        assertEquals(expected, actual);
    }

    @AfterAll
    static void tearDownAll(){
        password = null;
        encryptedPassword = null;
    }
}