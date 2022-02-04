package com.epam.carrental.dao.entity;

import java.util.Objects;

public class InsuranceDAO extends EntityDAO {
    private String number;
    private String companyName;
    private InsuranceType type;
    private Double maxAmount;
    private Double cost;

    public InsuranceDAO(Integer id) {
        super(id);
    }

    public InsuranceDAO(Integer id,
                        String number,
                        String companyName,
                        InsuranceType type,
                        Double maxAmount,
                        Double cost) {
        super(id);
        this.number = number;
        this.companyName = companyName;
        this.type = type;
        this.maxAmount = maxAmount;
        this.cost = cost;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public InsuranceType getType() {
        return type;
    }

    public void setType(InsuranceType type) {
        this.type = type;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InsuranceDAO)) return false;
        if (!super.equals(o)) return false;

        InsuranceDAO that = (InsuranceDAO) o;

        if (!Objects.equals(number, that.number)) return false;
        if (!Objects.equals(companyName, that.companyName)) return false;
        if (type != that.type) return false;
        if (!Objects.equals(maxAmount, that.maxAmount)) return false;
        return Objects.equals(cost, that.cost);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (maxAmount != null ? maxAmount.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "InsuranceDAO{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", companyName='" + companyName + '\'' +
                ", type=" + type +
                ", maxAmount=" + maxAmount +
                ", cost=" + cost +
                '}';
    }
}
