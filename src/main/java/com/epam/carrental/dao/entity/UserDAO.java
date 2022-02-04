package com.epam.carrental.dao.entity;

import java.util.Objects;

public class UserDAO extends EntityDAO {
    private String login;
    private String password;
    private UserRole userRole;
    private Integer accountId;

    public UserDAO(Integer id) {
        super(id);
    }

    public UserDAO(Integer id,
                   String login,
                   String password,
                   UserRole userRole,
                   Integer accountId) {
        super(id);
        this.login = login;
        this.password = password;
        this.userRole = userRole;
        this.accountId = accountId;

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserDAO user = (UserDAO) o;

        if (!Objects.equals(login, user.login)) return false;
        if (!Objects.equals(password, user.password)) return false;
        if (!Objects.equals(accountId, user.accountId)) return false;
        return Objects.equals(userRole, user.userRole);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (accountId != null ? accountId.hashCode() : 0);
        result = 31 * result + (userRole != null ? userRole.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + userRole +
                ", accountId=" + accountId +
                '}';
    }


}
