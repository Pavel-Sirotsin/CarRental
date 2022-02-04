package com.epam.carrental.service.dto;
/**
 * The {@code CarDTO} class doesn't encapsulate fields
 * and isn't a java bean class. Was implemented to copy fields
 * of DAO object and then transfer information to the {@code Controller} layer.
 * <p>Consists of getters for use in JSP pages and {@link Object#toString()} for debugging.</p>
 * */
public class CarDTO extends EntityDTO {
    public String brand;
    public String model;
    public String fuelType;
    public String gearBox;
    public Integer doors;
    public Boolean airConditioning;
    public Integer trunkCapacity;
    public Double pricePerDay;
    public InsuranceDTO insurance;
    public String imageURL;

    public CarDTO(Integer id) {
        super(id);
    }

    public Integer getId() {
        return id;
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

    public Boolean getAirConditioning() {
        return airConditioning;
    }

    public Integer getTrunkCapacity() {
        return trunkCapacity;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public InsuranceDTO getInsurance() {
        return insurance;
    }

    public String getImageURL() {
        return imageURL;
    }

    @Override
    public String toString() {
        return "CarDTO{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", gearBox='" + gearBox + '\'' +
                ", doors=" + doors +
                ", airConditioning=" + airConditioning +
                ", trunkCapacity=" + trunkCapacity +
                ", pricePerDay=" + pricePerDay +
                ", insurance=" + insurance +
                ", imageURL='" + imageURL + '\'' +
                ", id=" + id +
                '}';
    }
}
