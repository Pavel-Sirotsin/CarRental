package com.epam.carrental.service.dto;
/**
 * The {@code AccountDTO} class doesn't encapsulate fields
 * and isn't a java bean class. Was implemented to copy fields
 * of DAO object and then transfer information to the {@code Controller} layer.
 * <p>Consists of getters for use in JSP pages and {@link Object#toString()} for debugging.</p>
 * */
public class AccountDTO extends EntityDTO {
    public String name;
    public String surname;
    public String email;
    public String phoneNumber;
    public String drivingLicense;

    public AccountDTO(Integer id) {
        super(id);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDrivingLicense() {
        return drivingLicense;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", drivingLicense='" + drivingLicense + '\'' +
                ", id=" + id +
                '}';
    }
}
