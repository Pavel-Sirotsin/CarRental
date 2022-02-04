package com.epam.carrental.dao;

import com.epam.carrental.dao.entity.EntityDAO;
import com.epam.carrental.dao.exception.DAOException;

import java.util.Collections;
import java.util.List;


public interface BaseDAO<T extends EntityDAO> {

    default List<T> findAll() throws DAOException {
        return Collections.emptyList();
    }

    default T findById(int key) throws DAOException {
        return null;
    }

    default boolean deleteById(int key) throws DAOException {
        return false;
    }

    default boolean create(T t) throws DAOException {
        return false;
    }

    default boolean update(T t) throws DAOException {
        return false;
    }


}