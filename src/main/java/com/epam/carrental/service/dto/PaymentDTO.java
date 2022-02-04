package com.epam.carrental.service.dto;
/**
 * The {@code PaymentDTO} class doesn't encapsulate fields
 * and isn't a java bean class. Was implemented to copy fields
 * of DAO object and then transfer information to the {@code Controller} layer.
 * <p>Consists of getters for use in JSP pages and {@link Object#toString()} for debugging.</p>
 * */
public class PaymentDTO extends EntityDTO {
    public String holderName;
    public String cardNumber;
    public String expirationDate;
    public String cvv;
    public Double sum;
    public Double reclamation;
    public BookingDTO booking;

    public PaymentDTO(Integer id) {
        super(id);
    }

    public String getHolderName() {
        return holderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public Double getSum() {
        return sum;
    }

    public BookingDTO getBooking() {
        return booking;
    }

    public Double getReclamation() {
        return reclamation;
    }

    @Override
    public String toString() {
        return "PaymentDTO{" +
                "id=" + id +
                ", holderName='" + holderName + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", cvv='" + cvv + '\'' +
                ", sum=" + sum +
                ", reclamation=" + reclamation +
                ", bookingId=" + booking.id +
                '}';
    }
}
