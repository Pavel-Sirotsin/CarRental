package com.epam.carrental.dao;

import com.epam.carrental.dao.entity.AccountDAO;
import com.epam.carrental.dao.exception.DAOException;

public abstract class AccountAbstractDAO implements BaseDAO<AccountDAO> {

    public abstract AccountDAO findByUserId(int userId) throws DAOException;
}
