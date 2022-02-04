package com.epam.carrental.service.dto;

import com.epam.carrental.dao.entity.InsuranceType;
/**
 * The {@code InsuranceDTO} class doesn't encapsulate fields
 * and isn't a java bean class. Was implemented to copy fields
 * of DAO object and then transfer information to the {@code Controller} layer.
 * <p>Consists of getters for use in JSP pages and {@link Object#toString()} for debugging.</p>
 * */
public class InsuranceDTO extends EntityDTO {

    public String number;
    public String companyName;
    public InsuranceType type;
    public Double maxAmount;
    public Double cost;

    public InsuranceDTO(Integer id) {
        super(id);
    }

    public String getNumber() {
        return number;
    }

    public String getCompanyName() {
        return companyName;
    }

    public InsuranceType getType() {
        return type;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public Double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "InsuranceDTO{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", companyName='" + companyName + '\'' +
                ", type=" + type +
                ", maxAmount=" + maxAmount +
                ", cost=" + cost +
                '}';
    }
}
