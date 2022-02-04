package com.epam.carrental.dao.entity;

import java.util.Objects;

public class BookingDAO extends EntityDAO {
    private final Integer accountId;
    private final String rentalDate;
    private final String rentalLocation;
    private final String returnDate;
    private final String returnLocation;
    private final Integer daysAmount;
    private final Double sum;
    private final Boolean paid;
    private final Boolean rejected;
    private final String rejectionReason;
    private final Integer carId;
    private final Boolean accidentFree;

    private BookingDAO(Builder builder) {
        super(builder.id);
        this.accountId = builder.accountId;
        this.rentalDate = builder.rentalDate;
        this.rentalLocation = builder.rentalLocation;
        this.returnDate = builder.returnDate;
        this.returnLocation = builder.returnLocation;
        this.daysAmount = builder.daysAmount;
        this.sum = builder.sum;
        this.paid = builder.paid;
        this.rejected = builder.rejected;
        this.rejectionReason = builder.rejectionReason;
        this.carId = builder.carId;
        this.accidentFree = builder.accidentFree;

    }

    public static class Builder {
        private final Integer id;
        private Integer accountId;
        private String rentalDate;
        private String rentalLocation;
        private String returnDate;
        private String returnLocation;
        private Integer daysAmount;
        private Double sum;
        private Boolean paid;
        private Boolean rejected;
        private String rejectionReason;
        private Integer carId;
        private Boolean accidentFree;


        public Builder(Integer id) {
            this.id = id;
        }

        public Builder accountId(Integer accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder rentalDate(String rentalDate) {
            this.rentalDate = rentalDate;
            return this;
        }

        public Builder rentalLocation(String rentalLocation) {
            this.rentalLocation = rentalLocation;
            return this;
        }

        public Builder returnDate(String returnDate) {
            this.returnDate = returnDate;
            return this;
        }

        public Builder returnLocation(String returnLocation) {
            this.returnLocation = returnLocation;
            return this;
        }

        public Builder daysAmount(Integer daysAmount) {
            this.daysAmount = daysAmount;
            return this;
        }

        public Builder sum(Double sum) {
            this.sum = sum;
            return this;
        }

        public Builder paid(Boolean paid) {
            this.paid = paid;
            return this;
        }

        public Builder rejected(Boolean rejected) {
            this.rejected = rejected;
            return this;
        }

        public Builder rejectionReason(String rejectionReason) {
            this.rejectionReason = rejectionReason;
            return this;
        }

        public Builder accidentFree(Boolean accidentFree) {
            this.accidentFree = accidentFree;
            return this;
        }

        public Builder carId(Integer carId) {
            this.carId = carId;
            return this;
        }

        public BookingDAO build() {
            return new BookingDAO(this);
        }
    }

    public Integer getAccountId() {
        return accountId;
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

    public Boolean isPaid() {
        return paid;
    }

    public Boolean isRejected() {
        return rejected;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public Integer getCarId() {
        return carId;
    }

    public Boolean isAccidentFree() {
        return accidentFree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookingDAO)) return false;
        if (!super.equals(o)) return false;

        BookingDAO booking = (BookingDAO) o;

        if (!Objects.equals(accountId, booking.accountId)) return false;
        if (!Objects.equals(rentalDate, booking.rentalDate)) return false;
        if (!Objects.equals(rentalLocation, booking.rentalLocation)) return false;
        if (!Objects.equals(returnDate, booking.returnDate)) return false;
        if (!Objects.equals(returnLocation, booking.returnLocation)) return false;
        if (!Objects.equals(daysAmount, booking.daysAmount)) return false;
        if (!Objects.equals(sum, booking.sum)) return false;
        if (!Objects.equals(paid, booking.paid)) return false;
        if (!Objects.equals(rejected, booking.rejected)) return false;
        if (!Objects.equals(rejectionReason, booking.rejectionReason))
            return false;
        if (!Objects.equals(carId, booking.carId)) return false;
        return Objects.equals(accidentFree, booking.accidentFree);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (accountId != null ? accountId.hashCode() : 0);
        result = 31 * result + (rentalDate != null ? rentalDate.hashCode() : 0);
        result = 31 * result + (rentalLocation != null ? rentalLocation.hashCode() : 0);
        result = 31 * result + (returnDate != null ? returnDate.hashCode() : 0);
        result = 31 * result + (returnLocation != null ? returnLocation.hashCode() : 0);
        result = 31 * result + (daysAmount != null ? daysAmount.hashCode() : 0);
        result = 31 * result + (sum != null ? sum.hashCode() : 0);
        result = 31 * result + (paid != null ? paid.hashCode() : 0);
        result = 31 * result + (rejected != null ? rejected.hashCode() : 0);
        result = 31 * result + (rejectionReason != null ? rejectionReason.hashCode() : 0);
        result = 31 * result + (carId != null ? carId.hashCode() : 0);
        result = 31 * result + (accidentFree != null ? accidentFree.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BookingDAO{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", rentalDate=" + rentalDate +
                ", rentalLocation='" + rentalLocation + '\'' +
                ", returnDate=" + returnDate +
                ", returnLocation='" + returnLocation + '\'' +
                ", daysAmount=" + daysAmount +
                ", sum=" + sum +
                ", paid=" + paid +
                ", rejected=" + rejected +
                ", rejectionReason='" + rejectionReason + '\'' +
                ", carId=" + carId +
                ", accidentFree=" + accidentFree +
                '}';
    }
}



