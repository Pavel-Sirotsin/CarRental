package com.epam.carrental.dao.entity;

import java.io.Serializable;
import java.util.Objects;

public abstract class EntityDAO implements Serializable, Cloneable {
    protected Integer id;

    public EntityDAO(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityDAO entityDao = (EntityDAO) o;

        return Objects.equals(id, entityDao.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "EntityDAO{" +
                "id=" + id +
                '}';
    }


}
