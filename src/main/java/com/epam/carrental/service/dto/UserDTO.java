package com.epam.carrental.service.dto;

import com.epam.carrental.dao.entity.UserRole;
/**
 * The {@code UserDTO} class doesn't encapsulate fields
 * and isn't a java bean class. Was implemented to copy fields
 * of DAO object and then transfer information to the {@code Controller} layer.
 * <p>Consists of getters for use in JSP pages and {@link Object#toString()} for debugging.</p>
 * */
public class UserDTO extends EntityDTO {
    public String login;
    public String password;
    public UserRole role;
    public AccountDTO account;

    public UserDTO(Integer id) {
        super(id);
    }

    public Integer getId() {
        return id;
    }

    public UserRole getRole() {
        return role;
    }

    public AccountDTO getAccount() {
        return account;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", account=" + account +
                '}';
    }
}
