package com.epam.carrental.service.impl;

import com.epam.carrental.service.BaseService;
import com.epam.carrental.service.ServiceProvider;
import com.epam.carrental.service.dto.CarDTO;
import com.epam.carrental.service.exception.ServiceException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarServiceImplTest {
    static private List<CarDTO> carListEmpty;
    static private CarDTO carEmpty;
    static private Integer index = 1;

    private static BaseService<CarDTO> carImpl = ServiceProvider.INSTANCE.getCarImpl();

    @Test
    @DisplayName("Get all cars in List")
    void getAllCars() throws ServiceException {
        carListEmpty = carImpl.getAll();
        assertAll(
                () -> assertNotNull(carListEmpty),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
        );
    }

    @Test
    @DisplayName("Get Car using id")
    void getCarById() throws ServiceException {
        carEmpty = carImpl.getById(index);
        assertAll(
                () -> assertNotNull(carEmpty.id),
                () -> assertNotNull(carEmpty.insurance),
                () -> assertNotNull(carEmpty.pricePerDay),
                () -> assertNotNull(carEmpty.brand),
                () -> assertNotNull(carEmpty.model),
                () -> assertNotNull(carEmpty.doors),
                () -> assertNotNull(carEmpty.airConditioning),
                () -> assertNotNull(carEmpty.fuelType),
                () -> assertNotNull(carEmpty.gearBox),
                () -> assertNotNull(carEmpty.trunkCapacity),
                () -> assertNotNull(carEmpty.imageURL),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
        );
    }

    @AfterAll
    static void tearDownAll() {
        carEmpty = null;
        carListEmpty = null;
        index = null;
        carImpl = null;
    }
}