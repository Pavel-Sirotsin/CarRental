package com.epam.carrental.dao.entity;

import java.util.Objects;

public class PaymentDAO extends EntityDAO {
    private String holderName;
    private String cardNumber;
    private String expirationDate;
    private String cvv;
    private Double sum;
    private Double reclamation;
    private Integer bookingId;

    public PaymentDAO(Integer id) {
        super(id);
    }

    public PaymentDAO(Integer id, String holderName, String cardNumber, String expirationDate, String cvv, Double sum, Double reclamation, Integer bookingId) {
        super(id);
        this.holderName = holderName;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.sum = sum;
        this.reclamation = reclamation;
        this.bookingId = bookingId;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Double getReclamation() {
        return reclamation;
    }

    public void setReclamation(Double reclamation) {
        this.reclamation = reclamation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentDAO)) return false;
        if (!super.equals(o)) return false;

        PaymentDAO that = (PaymentDAO) o;

        if (!Objects.equals(holderName, that.holderName)) return false;
        if (!Objects.equals(cardNumber, that.cardNumber)) return false;
        if (!Objects.equals(expirationDate, that.expirationDate))
            return false;
        if (!Objects.equals(cvv, that.cvv)) return false;
        if (!Objects.equals(sum, that.sum)) return false;
        if (!Objects.equals(reclamation, that.reclamation)) return false;
        return Objects.equals(bookingId, that.bookingId);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (holderName != null ? holderName.hashCode() : 0);
        result = 31 * result + (cardNumber != null ? cardNumber.hashCode() : 0);
        result = 31 * result + (expirationDate != null ? expirationDate.hashCode() : 0);
        result = 31 * result + (cvv != null ? cvv.hashCode() : 0);
        result = 31 * result + (sum != null ? sum.hashCode() : 0);
        result = 31 * result + (reclamation != null ? reclamation.hashCode() : 0);
        result = 31 * result + (bookingId != null ? bookingId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PaymentDAO{" +
                "id=" + id +
                ", holderName='" + holderName + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", cvv='" + cvv + '\'' +
                ", sum=" + sum +
                ", reclamation=" + reclamation +
                ", bookingId=" + bookingId +
                '}';
    }

}
