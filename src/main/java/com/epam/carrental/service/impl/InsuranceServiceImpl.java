package com.epam.carrental.service.impl;

import com.epam.carrental.dao.BaseDAO;
import com.epam.carrental.dao.DAOProvider;
import com.epam.carrental.dao.entity.InsuranceDAO;
import com.epam.carrental.dao.exception.DAOException;
import com.epam.carrental.service.BaseService;
import com.epam.carrental.service.converter.BaseConverter;
import com.epam.carrental.service.converter.impl.InsuranceConverter;
import com.epam.carrental.service.dto.InsuranceDTO;
import com.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

public class InsuranceServiceImpl implements BaseService<InsuranceDTO> {
    private static final Logger logger = LogManager.getFormatterLogger(InsuranceServiceImpl.class);

    private final BaseConverter<InsuranceDTO, InsuranceDAO> converter = new InsuranceConverter();
    private final BaseDAO<InsuranceDAO> insuranceDaoImpl = DAOProvider.INSTANCE.getInsuranceImpl();

    @Override
    public InsuranceDTO getById(int carId) throws ServiceException {
        logger.traceEntry("- try to get insurance by id.");
        InsuranceDTO insuranceDto;

        try {
            insuranceDto = converter.convert(insuranceDaoImpl.findById(carId));
        } catch (DAOException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }
        logger.traceExit(" with insuranceDto: %s", new ObjectMessage(insuranceDto));
        return insuranceDto;
    }
}
