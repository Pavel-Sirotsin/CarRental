package com.epam.carrental.service.dto;

import java.io.Serializable;
/**
 * The {@code AccidentDTO} class doesn't encapsulate fields
 * and isn't a java bean class. Was implemented to copy fields
 * of DAO object and then transfer information to the {@code Controller} layer.
 * <p>Consists of getters for use in JSP pages and {@link Object#toString()} for debugging.</p>
 * */
public class AccidentDTO extends EntityDTO implements Serializable {
    public String damageDescription;
    public Double damageAmount;
    public Double reclaimAmount;
    public BookingDTO booking;

    public AccidentDTO(Integer id) {
        super(id);
    }

    public String getDamageDescription() {
        return damageDescription;
    }

    public Double getDamageAmount() {
        return damageAmount;
    }

    public Double getReclaimAmount() {
        return reclaimAmount;
    }

    public BookingDTO getBooking() {
        return booking;
    }

    @Override
    public String toString() {
        return "AccidentDTO{" +
                "damageDescription='" + damageDescription + '\'' +
                ", damageAmount=" + damageAmount +
                ", reclaimAmount=" + reclaimAmount +
                ", booking=" + booking +
                ", id=" + id +
                '}';
    }
}
