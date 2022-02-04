package com.epam.carrental.dao.pool;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The {@code PropertiesUtil} class read a file with application configuration
 * from the resources path and load it into a new created  {@link java.util.Properties} entity.
 */
final class PropertiesUtil {
    private static final Logger logger = LogManager.getFormatterLogger(PropertiesUtil.class);
    /**
     * A constant holding the {@code String} type message for exception cause.
     * */
    private static final String PROPS_EX = "Check properties file!";
    /**
     * A constant holding the {@code Properties} entity with configuration information.
     * */
    public static final Properties CONFIG = new Properties();
    /**
     * @throws     RuntimeException if the configuration file
     *             doesn't exist or have invalid data.
     */
    private PropertiesUtil() {
    }

    static {
        logger.traceEntry("- configuration file initialization.");

        try {
            InputStream input = PropertiesUtil.class.
                    getClassLoader().
                    getResourceAsStream("application.properties");
            CONFIG.load(input);
        } catch (IOException ex) {
            logger.throwing(Level.WARN, ex);
            throw new RuntimeException(PROPS_EX, ex);
        }

        logger.traceExit(" with CONFIG: %s", new ObjectMessage(CONFIG));
    }

}
