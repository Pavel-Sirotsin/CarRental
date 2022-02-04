package com.epam.carrental.service.converter.impl;

import com.epam.carrental.dao.entity.InsuranceDAO;
import com.epam.carrental.service.converter.BaseConverter;
import com.epam.carrental.service.dto.InsuranceDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

/**
 * The {@code InsuranceConverter} class implements methods from {@code BaseConverter} interface
 * and makes simple conversion from {@link InsuranceDTO} object to {@link InsuranceDAO} object.
 */
public class InsuranceConverter implements BaseConverter<InsuranceDTO, InsuranceDAO> {
    private static final Logger logger = LogManager.getFormatterLogger(InsuranceConverter.class);

    /**
     * Converts {@link InsuranceDAO} object to {@link InsuranceDTO}.
     *
     * @param insuranceDAO the value to be converted.
     * @return the new {@link InsuranceDTO} object.
     */
    @Override
    public InsuranceDTO convert(InsuranceDAO insuranceDAO) {
        logger.traceEntry("- try to convert Insurance from DAO to DTO.");
        InsuranceDTO insuranceDTO = new InsuranceDTO(insuranceDAO.getId());
        insuranceDTO.number = insuranceDAO.getNumber();
        insuranceDTO.companyName = insuranceDAO.getCompanyName();
        insuranceDTO.type = insuranceDAO.getType();
        insuranceDTO.maxAmount = insuranceDAO.getMaxAmount();
        insuranceDTO.cost = insuranceDAO.getCost();
        logger.traceExit(" with insuranceDTO: %s", new ObjectMessage(insuranceDTO));
        return insuranceDTO;

    }

    /**
     * Converts {@link InsuranceDTO} object to {@link InsuranceDAO}.
     *
     * @param insuranceDTO the value to be converted.
     * @return the new {@link InsuranceDAO} object.
     */
    @Override
    public InsuranceDAO convert(InsuranceDTO insuranceDTO) {
        logger.debug("converting Insurance from DTO to DAO.");
        return new InsuranceDAO(insuranceDTO.id,
                insuranceDTO.number,
                insuranceDTO.companyName,
                insuranceDTO.type,
                insuranceDTO.maxAmount,
                insuranceDTO.cost);
    }
}
