package com.epam.carrental.dao;

import com.epam.carrental.dao.entity.AccidentDAO;
import com.epam.carrental.dao.exception.DAOException;

public abstract class AccidentAbstractDAO implements BaseDAO<AccidentDAO> {

    public abstract AccidentDAO findByBookingId(Integer index) throws DAOException;

}
