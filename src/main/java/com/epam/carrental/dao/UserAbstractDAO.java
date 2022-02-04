package com.epam.carrental.dao;

import com.epam.carrental.dao.entity.AccountDAO;
import com.epam.carrental.dao.entity.UserDAO;
import com.epam.carrental.dao.exception.DAOException;

public abstract class UserAbstractDAO implements BaseDAO<UserDAO> {

    public abstract boolean makeTransaction(UserDAO user, AccountDAO account) throws DAOException;

    public abstract UserDAO findByLogin(String login) throws DAOException;

}
