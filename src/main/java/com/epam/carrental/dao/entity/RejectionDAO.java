package com.epam.carrental.dao.entity;

import java.util.Objects;

public class RejectionDAO extends EntityDAO {
    private String rejectionReason;
    private Integer bookingId;

    public RejectionDAO(Integer id) {
        super(id);
    }

    public RejectionDAO(Integer id, String rejectionReason, Integer bookingId) {
        super(id);
        this.rejectionReason = rejectionReason;
        this.bookingId = bookingId;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RejectionDAO)) return false;
        if (!super.equals(o)) return false;

        RejectionDAO that = (RejectionDAO) o;

        if (!Objects.equals(rejectionReason, that.rejectionReason))
            return false;
        return Objects.equals(bookingId, that.bookingId);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (rejectionReason != null ? rejectionReason.hashCode() : 0);
        result = 31 * result + (bookingId != null ? bookingId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RejectionDAO{" +
                "id=" + id +
                ", rejectionReason='" + rejectionReason + '\'' +
                ", bookingId=" + bookingId +
                '}';
    }
}
