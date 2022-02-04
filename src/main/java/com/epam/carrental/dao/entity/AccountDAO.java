package com.epam.carrental.dao.entity;

import java.util.Objects;

public class AccountDAO extends EntityDAO {
    private final String name;
    private final String surname;
    private final String email;
    private final String phoneNumber;
    private final String drivingLicense;

    private AccountDAO(Builder builder) {
        super(builder.id);
        name = builder.name;
        surname = builder.surname;
        email = builder.email;
        phoneNumber = builder.phoneNumber;
        drivingLicense = builder.drivingLicense;

    }

    public static class Builder {
        private final Integer id;
        private String name;
        private String surname;
        private String email;
        private String phoneNumber;
        private String drivingLicense;

        public Builder(Integer id) {
            this.id = id;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder drivingLicense(String drivingLicense) {
            this.drivingLicense = drivingLicense;
            return this;
        }

        public AccountDAO build() {
            return new AccountDAO(this);
        }
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountDAO)) return false;
        if (!super.equals(o)) return false;

        AccountDAO that = (AccountDAO) o;

        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(surname, that.surname)) return false;
        if (!Objects.equals(email, that.email)) return false;
        if (!Objects.equals(phoneNumber, that.phoneNumber)) return false;
        return Objects.equals(drivingLicense, that.drivingLicense);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (drivingLicense != null ? drivingLicense.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "AccountDAO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", drivingLicense='" + drivingLicense + '\'' +
                '}';
    }
}
