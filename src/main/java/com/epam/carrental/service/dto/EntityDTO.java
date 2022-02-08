package com.epam.carrental.service.dto;

import java.io.Serializable;

/**
 * The {@code EntityDTO} abstract class was integrated in the {@code BaseService} interface
 * to extend the capabilities of Service functions and realise low coupling.
 * <p>Implements {@link Cloneable} and {@link Serializable}.</p>
 * <p>Stores individual id of each DTO object.</p>
 */
public abstract class EntityDTO implements Cloneable{
    /**
     * The field holding {@code Integr} ID.
     */
    public Integer id;

    /**
     * Creates new EntityDTO with new ID.
     *
     * @param id the value of index.
     */
    public EntityDTO(Integer id) {
        this.id = id;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
