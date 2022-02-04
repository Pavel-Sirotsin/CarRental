package com.epam.carrental.dao;

import com.epam.carrental.dao.entity.InsuranceDAO;
import com.epam.carrental.dao.exception.DAOException;

public abstract class InsuranceAbstractDAO implements BaseDAO<InsuranceDAO> {

    public abstract InsuranceDAO findByBookingId(Integer index) throws DAOException;

}
