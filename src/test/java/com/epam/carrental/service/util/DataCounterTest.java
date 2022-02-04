package com.epam.carrental.service.util;

import com.epam.carrental.service.dto.CarDTO;
import com.epam.carrental.service.dto.InsuranceDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataCounterTest {
    private static String rentalDate;
    private static String returnDate;
    private static CarDTO car;
    private static InsuranceDTO insurance;
    private static Double maxBenefit;
    private static Double damageAmount;

    @BeforeAll
    static void init() {
        rentalDate = "2030-01-01T00:00";
        returnDate = "2030-01-10T00:00";
        maxBenefit = 2.00;
        damageAmount = 3.00;

        insurance = new InsuranceDTO(null);
        insurance.cost = 1.00;
        car = new CarDTO(null);
        car.pricePerDay = 10.00;
        car.insurance = insurance;

    }

    @Test
    @DisplayName("Should return Integer value of counted days.")
    void countDaysFromDates() {
        Integer expected = 9;
        Integer actual = DataCounter.countDay(rentalDate, returnDate);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should return Double value the total rent.")
    void countTotalSumOfRent() {
        Double expected = 91.00;
        Double actual = DataCounter.countSum(9, car);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should return 0.00.")
    void countReclaimAmountIfDamageBigger() {
        damageAmount = 1.00;
        Double expected = 0.00;
        Double actual = DataCounter.countReclaimAmount(maxBenefit, damageAmount);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should return double the amount of the reclaim.")
    void countReclaimAmountIfDamageSmaller() {
        Double expected = 1.00;
        Double actual = DataCounter.countReclaimAmount(maxBenefit, damageAmount);
        assertEquals(expected, actual);
    }

    @AfterAll
    static void tearDown() {
        rentalDate = null;
        returnDate = null;
        car = null;
        insurance = null;
        maxBenefit = null;
        damageAmount = null;
    }
}