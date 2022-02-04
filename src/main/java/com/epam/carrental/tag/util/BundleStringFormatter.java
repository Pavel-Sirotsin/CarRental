package com.epam.carrental.tag.util;

import com.epam.carrental.service.dto.AccidentDTO;
import com.epam.carrental.service.dto.AccountDTO;
import com.epam.carrental.service.dto.CarDTO;
import com.epam.carrental.service.dto.InsuranceDTO;
import com.epam.carrental.service.dto.PaymentDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import java.util.ResourceBundle;

public class BundleStringFormatter {
    private static final Logger logger = LogManager.getFormatterLogger(BundleStringFormatter.class);

    private static final Double EXCHANGE_RATE = 2.5;

    private static final String RU = "ru";

    private BundleStringFormatter() {
    }

    public static String getCarDescription(ResourceBundle bundle, CarDTO car) {
        logger.debug("Try to format car description using: %s and %s",
                new ObjectMessage(bundle),
                new ObjectMessage(car));
        return String.format("%s: %s<br>%s: %s<br>%s: %d<br>%s: %s<br>%s: %s %s",
                bundle.getString("label.fuel"),
                car.fuelType.equalsIgnoreCase("gasoline") ?
                        bundle.getString("label.gasoline") :
                        bundle.getString("label.diesel"),
                bundle.getString("label.gear"),
                car.gearBox.equalsIgnoreCase("automatic") ?
                        bundle.getString("label.automatic") :
                        bundle.getString("label.manual"),
                bundle.getString("label.doors"),
                car.doors,
                bundle.getString("label.conditioner"),
                car.airConditioning ?
                        bundle.getString("label.yes") :
                        bundle.getString("label.no"),
                bundle.getString("label.trunk"),
                car.trunkCapacity,
                bundle.getString("label.bag"));
    }

    public static String insuranceDescription(ResourceBundle bundle, InsuranceDTO insurance) {
        logger.debug("Try to format insurance description using: %s and %s",
                new ObjectMessage(bundle),
                new ObjectMessage(insurance));
        return String.format("%s: %s<br>%s: %s<br>%s: %s<br>%s: %,.2f$<br>%s: %,.2f$",
                bundle.getString("insurance.number"),
                insurance.number,
                bundle.getString("insurance.company"),
                insurance.companyName,
                bundle.getString("insurance.type"),
                insurance.type.name(),
                bundle.getString("insurance.max.amount"),
                insurance.maxAmount,
                bundle.getString("insurance.cost"),
                insurance.cost);
    }

    public static String getPaymentDescription(ResourceBundle bundle, PaymentDTO payment) {
        String paymentAmount = getExchangedAmount(bundle, payment.sum);
        logger.debug("Try to format payment description using: %s and %s",
                new ObjectMessage(bundle),
                new ObjectMessage(payment));
        return String.format("%s:<br>%s<br>%s<br>%s<br>%s<br>%s %s",
                bundle.getString("title.payment.block"),
                payment.holderName,
                payment.cardNumber,
                payment.expirationDate,
                payment.cvv,
                bundle.getString("booking.result.total"),
                paymentAmount);
    }

    public static String getAccidentDescription(ResourceBundle bundle, AccidentDTO accident) {
        logger.debug("Try to format accident description using: %s and %s",
                new ObjectMessage(bundle),
                new ObjectMessage(accident));
        return String.format("%s %s<br>%s %s$",
                bundle.getString("admin.accident.damage"),
                accident.damageDescription,
                bundle.getString("admin.accident.cost"),
                accident.damageAmount);
    }

    public static String getAccountDescription(ResourceBundle bundle, AccountDTO account) {
        logger.debug("Try to format account description using: %s and %s",
                new ObjectMessage(bundle),
                new ObjectMessage(account));
        return String.format("%s %s<br>%s %s<br>E-mail: %s<br>%s %s<br>%s %s",
                bundle.getString("label.first.name"),
                account.name,
                bundle.getString("label.last.name"),
                account.surname,
                account.email,
                bundle.getString("label.phone"),
                account.phoneNumber,
                bundle.getString("label.license"),
                account.drivingLicense);
    }

    public static String getExchangedAmount(ResourceBundle bundle, Double amount) {
        String language = bundle.getLocale().getLanguage();
        return language.equals(RU) ?
            String.format("%,.2f%s", (amount * EXCHANGE_RATE), bundle.getString("label.sign")) :
            String.format("%,.2f%s", amount, bundle.getString("label.sign"));
    }

    public static String getProfileReclamation(ResourceBundle bundle, Double amount) {
        return String.format("%s %,.2f$", bundle.getString("profile.reclamation.info"), amount);
    }

    public static String getAccidentReclaimCost(ResourceBundle bundle, Double amount) {
        return String.format("%s %,.2f$", bundle.getString("admin.accident.reclaim"), amount);
    }

    public static String getPaymentReclaimCost(ResourceBundle bundle, Double amount) {
        return String.format("%s %,.2f$", bundle.getString("payment.block.reclamation"), amount);
    }

}


