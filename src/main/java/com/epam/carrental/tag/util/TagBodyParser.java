package com.epam.carrental.tag.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagBodyParser {
    private static final Logger logger = LogManager.getFormatterLogger(TagBodyParser.class);

    private static final Pattern PATTERN = Pattern.compile("(\\w+)=([\\d.]+)");

    public static Map<String, String> parse(String bodyContext) {
        logger.traceEntry("- try to parse tag body params.");
        Matcher matcher = PATTERN.matcher(bodyContext);
        logger.debug("line(%d), matcher: %s", 19, new ObjectMessage(matcher));
        Map<String, String> actionNames = new HashMap<>();

        while (matcher.find()) {
            String name = matcher.group(1);
            String value = matcher.group(2);
            actionNames.put(name, value);
        }
        logger.traceExit(" with Map params: %d", actionNames.size());
        return actionNames;
    }

}
