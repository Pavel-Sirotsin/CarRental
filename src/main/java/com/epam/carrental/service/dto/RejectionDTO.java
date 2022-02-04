package com.epam.carrental.service.dto;
/**
 * The {@code RejectionDTO} class doesn't encapsulate fields
 * and isn't a java bean class. Was implemented to copy fields
 * of DAO object and then transfer information to the {@code Controller} layer.
 * <p>Consists of getters for use in JSP pages and {@link Object#toString()} for debugging.</p>
 * */
public class RejectionDTO extends EntityDTO {

    public String rejectionReason;
    public Integer bookingId;

    public RejectionDTO(Integer id) {
        super(id);
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    @Override
    public String toString() {
        return "RejectionDTO{" +
                "id=" + id +
                ", rejectionReason='" + rejectionReason + '\'' +
                ", bookingId=" + bookingId +
                '}';
    }
}
