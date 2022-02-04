package com.epam.carrental.dao.entity;

import java.util.Objects;

public class AccidentDAO extends EntityDAO {
    private String damageDescription;
    private Double damageAmount;
    private Double reclaimAmount;
    private Integer bookingId;

    public AccidentDAO(Integer id) {
        super(id);
    }

    public AccidentDAO(Integer id, String damageDescription, Double damageAmount, Double reclaimAmount, Integer bookingId) {
        super(id);
        this.damageDescription = damageDescription;
        this.damageAmount = damageAmount;
        this.reclaimAmount = reclaimAmount;
        this.bookingId = bookingId;
    }

    public String getDamageDescription() {
        return damageDescription;
    }

    public void setDamageDescription(String damageDescription) {
        this.damageDescription = damageDescription;
    }

    public Double getDamageAmount() {
        return damageAmount;
    }

    public void setDamageAmount(Double damageAmount) {
        this.damageAmount = damageAmount;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Double getReclaimAmount() {
        return reclaimAmount;
    }

    public void setReclaimAmount(Double reclaimAmount) {
        this.reclaimAmount = reclaimAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccidentDAO)) return false;
        if (!super.equals(o)) return false;

        AccidentDAO that = (AccidentDAO) o;

        if (!Objects.equals(damageDescription, that.damageDescription))
            return false;
        if (!Objects.equals(damageAmount, that.damageAmount)) return false;
        if (!Objects.equals(reclaimAmount, that.reclaimAmount))
            return false;
        return Objects.equals(bookingId, that.bookingId);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (damageDescription != null ? damageDescription.hashCode() : 0);
        result = 31 * result + (damageAmount != null ? damageAmount.hashCode() : 0);
        result = 31 * result + (reclaimAmount != null ? reclaimAmount.hashCode() : 0);
        result = 31 * result + (bookingId != null ? bookingId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AccidentDAO{" +
                "damageDescription='" + damageDescription + '\'' +
                ", damageAmount=" + damageAmount +
                ", reclaimAmount=" + reclaimAmount +
                ", bookingId=" + bookingId +
                ", id=" + id +
                '}';
    }
}
