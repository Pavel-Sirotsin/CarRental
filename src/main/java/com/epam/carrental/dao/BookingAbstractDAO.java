package com.epam.carrental.dao;

import com.epam.carrental.dao.entity.BookingDAO;
import com.epam.carrental.dao.exception.DAOException;

import java.util.List;

public abstract class BookingAbstractDAO implements BaseDAO<BookingDAO> {

    public abstract Integer createReturnID(BookingDAO user) throws DAOException;

    public abstract List<BookingDAO> findAllByAccountId(Integer index) throws DAOException;

}
