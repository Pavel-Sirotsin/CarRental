package com.epam.carrental.service.dto;
/**
 * The {@code BookingDTO} class doesn't encapsulate fields
 * and isn't a java bean class. Was implemented to copy fields
 * of DAO object and then transfer information to the {@code Controller} layer.
 * <p>Consists of getters for use in JSP pages and {@link Object#toString()} for debugging.</p>
 * */
public class BookingDTO extends EntityDTO {
    public AccountDTO account;
    public String rentalDate;
    public String rentalLocation;
    public String returnDate;
    public String returnLocation;
    public Integer daysAmount;
    public Double sum;
    public Boolean paid;
    public Boolean rejected;
    public CarDTO car;
    public Boolean accidentFree;

    public BookingDTO(Integer id) {
        super(id);
    }

    public Integer getId() {
        return id;
    }

    public AccountDTO getAccount() {
        return account;
    }

    public String getRentalDate() {
        return rentalDate;
    }

    public String getRentalLocation() {
        return rentalLocation;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public String getReturnLocation() {
        return returnLocation;
    }

    public Integer getDaysAmount() {
        return daysAmount;
    }

    public Double getSum() {
        return sum;
    }

    public Boolean getPaid() {
        return paid;
    }

    public Boolean getRejected() {
        return rejected;
    }

    public CarDTO getCar() {
        return car;
    }

    public Boolean getAccidentFree() {
        return accidentFree;
    }

    @Override
    public String toString() {
        return "BookingDTO{" +
                "account=" + account +
                ", rentalDate='" + rentalDate + '\'' +
                ", rentalLocation='" + rentalLocation + '\'' +
                ", returnDate='" + returnDate + '\'' +
                ", returnLocation='" + returnLocation + '\'' +
                ", daysAmount=" + daysAmount +
                ", sum=" + sum +
                ", paid=" + paid +
                ", rejected=" + rejected +
                ", car=" + car +
                ", accidentFree=" + accidentFree +
                ", id=" + id +
                '}';
    }
}
