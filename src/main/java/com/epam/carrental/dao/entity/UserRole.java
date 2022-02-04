package com.epam.carrental.dao.entity;

public enum UserRole {
    CLIENT, ADMIN;

    @Override
    public String toString() {
        return "UserRole{" +
                "name=" + name() +
                ", index=" + ordinal() + '}';
    }
}
