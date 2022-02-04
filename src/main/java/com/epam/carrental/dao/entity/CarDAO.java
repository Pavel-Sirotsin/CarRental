package com.epam.carrental.dao.entity;

import java.util.Objects;

public class CarDAO extends EntityDAO {
    private final String brand;
    private final String model;
    private final String fuelType;
    private final String gearBox;
    private final Integer doors;
    private final Boolean airConditioning;
    private final Integer trunkCapacity;
    private final Double pricePerDay;
    private final Integer insuranceId;
    private final String imageURL;


    private CarDAO(Builder builder) {
        super(builder.id);
        brand = builder.brand;
        model = builder.model;
        fuelType = builder.fuelType;
        gearBox = builder.gearBox;
        doors = builder.doors;
        airConditioning = builder.airConditioning;
        trunkCapacity = builder.trunkCapacity;
        pricePerDay = builder.pricePerDay;
        insuranceId = builder.insuranceId;
        imageURL = builder.imageURL;

    }

    public static class Builder {
        private final Integer id;
        private String brand;
        private String model;
        private String fuelType;
        private String gearBox;
        private Integer doors;
        private Boolean airConditioning;
        private Integer trunkCapacity;
        private Double pricePerDay;
        private Integer insuranceId;
        private String imageURL;


        public Builder(Integer id) {
            this.id = id;
        }

        public Builder brand(String brand) {
            this.brand = brand;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder fuelType(String fuelType) {
            this.fuelType = fuelType;
            return this;
        }

        public Builder gearBox(String gearBox) {
            this.gearBox = gearBox;
            return this;
        }

        public Builder doors(Integer doors) {
            this.doors = doors;
            return this;
        }

        public Builder airConditioning(Boolean airConditioning) {
            this.airConditioning = airConditioning;
            return this;
        }

        public Builder trunkCapacity(Integer trunkCapacity) {
            this.trunkCapacity = trunkCapacity;
            return this;
        }

        public Builder pricePerDay(Double pricePerDay) {
            this.pricePerDay = pricePerDay;
            return this;
        }

        public Builder insuranceId(Integer insuranceId) {
            this.insuranceId = insuranceId;
            return this;
        }

        public Builder imageURL(String url) {
            this.imageURL = url;
            return this;
        }

        public CarDAO build() {
            return new CarDAO(this);
        }
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getGearBox() {
        return gearBox;
    }

    public Integer getDoors() {
        return doors;
    }

    public Boolean isAirConditioning() {
        return airConditioning;
    }

    public Integer getTrunkCapacity() {
        return trunkCapacity;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public Integer getInsuranceId() {
        return insuranceId;
    }

    public String getImageURL() {
        return imageURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarDAO)) return false;
        if (!super.equals(o)) return false;

        CarDAO car = (CarDAO) o;

        if (!Objects.equals(brand, car.brand)) return false;
        if (!Objects.equals(model, car.model)) return false;
        if (!Objects.equals(fuelType, car.fuelType)) return false;
        if (!Objects.equals(gearBox, car.gearBox)) return false;
        if (!Objects.equals(doors, car.doors)) return false;
        if (!Objects.equals(airConditioning, car.airConditioning))
            return false;
        if (!Objects.equals(trunkCapacity, car.trunkCapacity)) return false;
        if (!Objects.equals(pricePerDay, car.pricePerDay)) return false;
        if (!Objects.equals(insuranceId, car.insuranceId)) return false;
        return Objects.equals(imageURL, car.imageURL);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (fuelType != null ? fuelType.hashCode() : 0);
        result = 31 * result + (gearBox != null ? gearBox.hashCode() : 0);
        result = 31 * result + (doors != null ? doors.hashCode() : 0);
        result = 31 * result + (airConditioning != null ? airConditioning.hashCode() : 0);
        result = 31 * result + (trunkCapacity != null ? trunkCapacity.hashCode() : 0);
        result = 31 * result + (pricePerDay != null ? pricePerDay.hashCode() : 0);
        result = 31 * result + (insuranceId != null ? insuranceId.hashCode() : 0);
        result = 31 * result + (imageURL != null ? imageURL.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CarDAO{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", gearBox='" + gearBox + '\'' +
                ", doors=" + doors +
                ", airConditioning=" + airConditioning +
                ", trunkCapacity=" + trunkCapacity +
                ", pricePerDay=" + pricePerDay +
                ", insuranceId=" + insuranceId +
                ", imageURL='" + imageURL + '\'' +
                ", id=" + id +
                '}';
    }
}
