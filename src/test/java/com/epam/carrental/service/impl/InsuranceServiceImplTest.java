package com.epam.carrental.service.impl;

import com.epam.carrental.service.BaseService;
import com.epam.carrental.service.ServiceProvider;
import com.epam.carrental.service.dto.InsuranceDTO;
import com.epam.carrental.service.exception.ServiceException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InsuranceServiceImplTest {
    private static InsuranceDTO insuranceEmpty;
    private static Integer index = 1;

    private static BaseService<InsuranceDTO> insuranceImpl = ServiceProvider.INSTANCE.getInsuranceImpl();

    @Test
    @DisplayName("Get Insurance using id")
    void getInsuranceById() throws ServiceException {
        insuranceEmpty = insuranceImpl.getById(index);
        assertAll(
                () -> assertNotNull(insuranceEmpty.id),
                () -> assertNotNull(insuranceEmpty.cost),
                () -> assertNotNull(insuranceEmpty.maxAmount),
                () -> assertNotNull(insuranceEmpty.number),
                () -> assertNotNull(insuranceEmpty.companyName),
                () -> assertNotNull(insuranceEmpty.type),
                () -> assertDoesNotThrow(() -> new ServiceException(), "Should not throw ServiceException")
        );
    }

    @AfterAll
    static void tearDownAll(){
        insuranceEmpty = null;
        index = null;
        insuranceImpl = null;
    }

}