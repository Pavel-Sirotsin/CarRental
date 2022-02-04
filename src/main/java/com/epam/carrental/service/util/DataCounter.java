package com.epam.carrental.service.util;

import com.epam.carrental.service.dto.CarDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DataCounter {
    private static final Logger logger = LogManager.getFormatterLogger(DataCounter.class);

    private DataCounter() {
    }

    public static Integer countDay(String rentalDate, String returnDate) {
        logger.traceEntry("- try to count amount of days.");
        logger.debug("line(%d), rentalDate: %s", 18, rentalDate);
        logger.debug("line(%d), returnDate: %s", 19, returnDate);
        LocalDateTime rentalLDT = LocalDateTime.
                parse(rentalDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateTime returnLDT = LocalDateTime.
                parse(returnDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        long result = ChronoUnit.DAYS.between(rentalLDT, returnLDT);
        logger.traceExit(" with: %d", result);

        return Math.toIntExact(result);
    }

    public static Double countSum(Integer days, CarDTO carDTO) {
        logger.traceEntry("- try to count cost of rent.");
        logger.debug("line(%d), days: %d", 34, days);
        logger.debug("line(%d), price: %,.2f", 35, carDTO.pricePerDay);
        return (days * carDTO.pricePerDay) + carDTO.insurance.cost;
    }

    public static Double countReclaimAmount(Double maxBenefit, Double damageAmount) {
        logger.traceEntry("- try to count reclamation amount.");
        double result = maxBenefit - damageAmount;
        logger.debug("line(%d), result: %,.2f", 42, result);
        return result < 0 ? Math.abs(result) : 0.00;
    }

}
