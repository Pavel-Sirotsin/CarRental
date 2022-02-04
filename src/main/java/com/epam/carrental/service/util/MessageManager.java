package com.epam.carrental.service.util;

public class MessageManager {
    public static final String WRONG_ID = "wrong.id";
    public static final String WRONG_DATA = "wrong.data";
    public static final String WRONG_ID_OR_DONE = "wrong.id.or.done";

    //Commands message
    public static final String SUCCESS_SIGN_UP = "success.sign.up";
    public static final String SUCCESS_LOG_IN = "success.log.in";
    public static final String ALREADY_LOGGED_IN = "already.log.in";
    public static final String SUCCESS_PAYMENT = "success.payment.block";
    public static final String WARNING_SIGN_UP = "transaction.warning";
    public static final String WARNING_LOG_IN = "warning.log.in";
    public static final String WARNING_UPDATE = "warning.update";
    public static final String SUCCESS_OPERATION = "success.operation";

    // Account data message
    public static final String WRONG_NAME_FORMAT = "wrong.name";
    public static final String WRONG_SURNAME_FORMAT = "wrong.surname";
    public static final String WRONG_EMAIL_FORMAT = "wrong.email";
    public static final String WRONG_PHONE_FORMAT = "wrong.phone";
    public static final String WRONG_LICENSE_FORMAT = "wrong.license";

    //User data message
    public static final String WRONG_LOGIN_FORMAT = "wrong.login";
    public static final String WRONG_PASSWORD_FORMAT = "wrong.password";
    public static final String WRONG_ROLE_FORMAT = "wrong.role";

    //Order data message
    public static final String WRONG_DATE_FORMAT = "wrong.date";
    public static final String WRONG_DATE_DATA = "wrong.outdated";
    public static final String WRONG_MIN_DAYS = "wrong.minimum";
    public static final String WRONG_LOCATION = "wrong.location";

    //Rejection data message
    public static final String WRONG_CONTENT_FORMAT = "wrong.content";

    //Accident data message
    public static final String WRONG_NUMBER_FORMAT = "wrong.number";

    //Payment data message
    public static final String WRONG_CREDIT_CARD_DATA = "wrong.credit.card.data";

    private MessageManager() {}

}
