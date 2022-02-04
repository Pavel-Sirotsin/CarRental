package com.epam.carrental.dao.sqlutil;

public class SqlQuery {
    /*
     * Car table queries:
     */
    public static final String SELECT_ALL_CARS =
            "SELECT id," +
                    "brand, " +
                    "model, " +
                    "fuel_type, " +
                    "gear_box, " +
                    "doors, " +
                    "air_conditioning, " +
                    "trunk_capacity, " +
                    "price_per_day, " +
                    "insurance_id, " +
                    "img_url FROM car";

    public static final String SELECT_CAR_BY_ID =
            "SELECT id, " +
                    "brand, " +
                    "model, " +
                    "fuel_type, " +
                    "gear_box, " +
                    "doors, " +
                    "air_conditioning, " +
                    "trunk_capacity, " +
                    "price_per_day, " +
                    "insurance_id, " +
                    "img_url FROM car WHERE id = ?";


    /*
     * User table queries:
     */

    public static final String SELECT_ALL_USERS =
            "SELECT id, " +
                    "login, " +
                    "password, " +
                    "role, " +
                    "account_id " +
                    "FROM user";

    public static final String SELECT_USER_BY_ID =
            "SELECT id, " +
                    "login, " +
                    "password, " +
                    "role, " +
                    "account_id " +
                    "FROM user " +
                    "WHERE id = ?";

    public static final String SELECT_USER_BY_LOGIN =
            "SELECT id, " +
                    "login, " +
                    "password, " +
                    "role, " +
                    "account_id " +
                    "FROM user " +
                    "WHERE login = ?";

    public static final String DELETE_USER_BY_ID =
            "DELETE FROM account " +
                    "USING user " +
                    "INNER JOIN account " +
                    "ON user.account_id = account.id " +
                    " WHERE user.id = ?";

    public static final String CREATE_USER =
            "INSERT INTO user" +
                    "(id, " +
                    "login, " +
                    "password, " +
                    "role, " +
                    "account_id) " +
                    "VALUES (?, ?, ?, ?, (SELECT LAST_INSERT_ID() account))";

    public static final String UPDATE_USER_BY_ID =
            "UPDATE user set " +
                    "login = ?, " +
                    "password = ?, " +
                    "role = ?" +
                    "WHERE id = ?";

    /*
     * Account table queries:
     */

    public static final String SELECT_ACCOUNT_BY_USER_ID =
            "SELECT  account.id, " +
                    "name, " +
                    "surname, " +
                    "email, " +
                    "phone_number, " +
                    "driving_license " +
                    "FROM account " +
                    "INNER JOIN user ON " +
                    "account.id = user.account_id " +
                    "WHERE user.id = ?";

    public static final String SELECT_ACCOUNT_BY_ID =
            "SELECT  account.id, " +
                    "name, " +
                    "surname, " +
                    "email, " +
                    "phone_number, " +
                    "driving_license " +
                    "FROM account " +
                    "WHERE id = ?";

    public static final String UPDATE_ACCOUNT_BY_ID =
            "UPDATE  account set " +
                    "name = ?, " +
                    "surname = ?, " +
                    "email = ?, " +
                    "phone_number = ?, " +
                    "driving_license = ? " +
                    "WHERE id = ?";

    public static final String CREATE_ACCOUNT =
            "INSERT INTO account " +
                    "(id, " +
                    "name, " +
                    "surname, " +
                    "email, " +
                    "phone_number, " +
                    "driving_license) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

    public static final String DELETE_ACCOUNT_BY_ID =
            "DELETE FROM account WHERE id = ?";

    /*
     * Insurance table queries:
     */

    public static final String SELECT_INSURANCE_BY_ID =
            "SELECT id, " +
                    "number, " +
                    "company_name, " +
                    "type, " +
                    "max_amount, " +
                    "cost " +
                    "FROM insurance " +
                    "WHERE id = ?";

    public static final String SELECT_INSURANCE_BY_BOOKING_ID =
            "SELECT insurance.id, " +
                    "number, " +
                    "company_name, " +
                    "type, " +
                    "max_amount, " +
                    "cost " +
                    "FROM insurance " +
                    "INNER JOIN car " +
                    "ON insurance.id = car.insurance_id " +
                    "WHERE car.id = (SELECT car_id FROM booking WHERE booking.id = ?)";

    /*
     * Booking table queries:
     */

    public static final String SELECT_BOOKING_BY_ID =
            "SELECT id, " +
                    "account_id, " +
                    "rental_date, " +
                    "rental_location, " +
                    "return_date, " +
                    "return_location, " +
                    "days_amount, " +
                    "sum, " +
                    "paid, " +
                    "rejected, " +
                    "car_id, " +
                    "accident_free " +
                    "FROM booking WHERE id = ?";

    public static final String SELECT_ALL_ORDERS =
            "SELECT id, " +
                    "account_id, " +
                    "rental_date, " +
                    "rental_location, " +
                    "return_date, " +
                    "return_location, " +
                    "days_amount, " +
                    "sum, " +
                    "paid, " +
                    "rejected, " +
                    "car_id, " +
                    "accident_free " +
                    "FROM booking";

    public static final String DELETE_BOOKING_BY_ID =
            "DELETE FROM booking WHERE id = ?";

    public static final String SELECT_BOOKING_ID =
            "SELECT id FROM booking WHERE id = LAST_INSERT_ID()";

    public static final String SELECT_BOOKING_BY_ACCOUNT_ID =
            "SELECT id, " +
                    "account_id, " +
                    "rental_date, " +
                    "rental_location, " +
                    "return_date, " +
                    "return_location, " +
                    "days_amount, " +
                    "sum, " +
                    "paid, " +
                    "rejected, " +
                    "car_id, " +
                    "accident_free " +
                    "FROM booking WHERE account_id = ?";

    public static final String CREATE_BOOKING_BY_ACCOUNT =
            "INSERT INTO booking " +
                    "(id, " +
                    "account_id, " +
                    "rental_date, " +
                    "rental_location, " +
                    "return_date, " +
                    "return_location, " +
                    "days_amount, " +
                    "sum, " +
                    "paid, " +
                    "rejected, " +
                    "car_id, " +
                    "accident_free) " +
                    "VALUES (?, (SELECT LAST_INSERT_ID() account), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String CREATE_BOOKING =
            "INSERT INTO booking " +
                    "(id, " +
                    "account_id, " +
                    "rental_date, " +
                    "rental_location, " +
                    "return_date, " +
                    "return_location, " +
                    "days_amount, " +
                    "sum, " +
                    "paid, " +
                    "rejected, " +
                    "car_id, " +
                    "accident_free) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String UPDATE_BOOKING_BY_PAYMENT =
            "UPDATE booking SET " +
                    "paid = 1 " +
                    "WHERE id = ?";

    public static final String UPDATE_BOOKING_BY_REJECTION =
            "UPDATE booking SET " +
                    "rejected = 1 " +
                    "WHERE id = ?";

    public static final String UPDATE_BOOKING_BY_ACCIDENT =
            "UPDATE booking SET " +
                    "accident_free = ? " +
                    "WHERE id = ?";

    /*
     * Accident table queries:
     */

    public static final String SELECT_ACCIDENT_BY_BOOKING_ID =
            "SELECT id, " +
                    "damage_description, " +
                    "damage_amount, " +
                    "reclaim_amount, " +
                    "booking_id " +
                    "FROM accident WHERE booking_id = ?";

    public static final String CREATE_ACCIDENT =
            "INSERT INTO accident " +
                    "(id, " +
                    "damage_description, " +
                    "damage_amount, " +
                    "reclaim_amount, " +
                    "booking_id) " +
                    "VALUES (?, ?, ?, ?, ?)";

    public static final String UPDATE_ACCIDENT =
            "UPDATE accident SET " +
                    "reclaim_amount = 0.00 " +
                    "WHERE booking_id = ?";

    public static final String DELETE_ACCIDENT_BY_BOOKING_ID =
            "DELETE FROM accident WHERE booking_id = ?";

    /*
     * Rejection table queries:
     */

    public static final String SELECT_REJECTION_BY_BOOKING_ID =
            "SELECT id, " +
                    "rejection_reason, " +
                    "booking_id " +
                    "FROM rejection WHERE booking_id = ?";

    public static final String CREATE_REJECTION =
            "INSERT INTO rejection " +
                    "(rejection_reason, " +
                    "booking_id) " +
                    "VALUES (?,?)";

    public static final String DELETE_REJECTION_BY_BOOKING_ID =
            "DELETE FROM rejection WHERE booking_id = ?";

    /*
     * Payment table queries:
     */

    public static final String CREATE_PAYMENT =
            "INSERT INTO payment " +
                    "(id, " +
                    "holder_name, " +
                    "card_number, " +
                    "expiration_date, " +
                    "cvv, " +
                    "sum, " +
                    "booking_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    public static final String SELECT_PAYMENT_BY_BOOKING_ID =
            "SELECT id, " +
                    "holder_name, " +
                    "card_number, " +
                    "expiration_date, " +
                    "cvv, " +
                    "sum, " +
                    "reclamation, " +
                    "booking_id " +
                    "FROM payment WHERE booking_id = ?";

    public static final String CREATE_PAYMENT_BY_BOOKING =
            "INSERT INTO payment " +
                    "(id, " +
                    "holder_name, " +
                    "card_number, " +
                    "expiration_date, " +
                    "cvv, " +
                    "sum, " +
                    "booking_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, (SELECT LAST_INSERT_ID() booking))";

    public static final String UPDATE_PAYMENT_BY_BOOKING_ID =
            "UPDATE payment SET " +
                    "reclamation = ? " +
                    "WHERE booking_id = ?";

    public static final String DELETE_PAYMENT_BY_ID =
            "DELETE account " +
                    "FROM account INNER JOIN booking INNER JOIN payment " +
                    "WHERE account.id = booking.account_id " +
                    "AND booking.id = payment.booking_id " +
                    "AND payment.id = ?";

    private SqlQuery() {
    }
}


