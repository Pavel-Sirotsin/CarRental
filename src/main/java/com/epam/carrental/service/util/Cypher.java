package com.epam.carrental.service.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Cypher {
    private static final Logger logger = LogManager.getFormatterLogger(Cypher.class);

    private Cypher() {}

    public static String encryptPassword(String password) {
        logger.traceEntry("- try to encrypt user password.");
        byte[] bytes = password.getBytes(StandardCharsets.UTF_8);
        Base64.Encoder encoder = Base64.getEncoder();
        String encodeResult = encoder.encodeToString(bytes);
        logger.traceExit(" with: %s", encodeResult);
        return encodeResult;
    }

    public static String decryptPassword(String password) {
        logger.traceEntry("- try to decrypt user password.");
        Base64.Decoder decoder = Base64.getDecoder();
        String decodedResult = new String(decoder.decode(password));
        logger.traceExit(" with: %s", decodedResult);
        return decodedResult;
    }

}
