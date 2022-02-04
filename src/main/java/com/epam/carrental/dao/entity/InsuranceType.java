package com.epam.carrental.dao.entity;

public enum InsuranceType {
    BASE, STANDARD, PREMIUM;

    @Override
    public String toString() {
        return "InsuranceType{" +
                "name=" + name() +
                ", index=" + ordinal() + '}';
    }
}
