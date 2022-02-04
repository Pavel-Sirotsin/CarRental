package com.epam.carrental.service.impl;

import com.epam.carrental.controller.command.util.ParameterManager;
import com.epam.carrental.dao.BookingAbstractDAO;
import com.epam.carrental.dao.DAOProvider;
import com.epam.carrental.dao.entity.BookingDAO;
import com.epam.carrental.dao.exception.DAOException;
import com.epam.carrental.service.AccountAbstractService;
import com.epam.carrental.service.BaseService;
import com.epam.carrental.service.BookingAbstractService;
import com.epam.carrental.service.ServiceProvider;
import com.epam.carrental.service.converter.BaseConverter;
import com.epam.carrental.service.converter.impl.BookingConverter;
import com.epam.carrental.service.dto.AccountDTO;
import com.epam.carrental.service.dto.BookingDTO;
import com.epam.carrental.service.dto.CarDTO;
import com.epam.carrental.service.exception.ServiceException;
import com.epam.carrental.service.util.DataCounter;
import com.epam.carrental.service.validator.impl.BookingValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * The {@code BookingServiceImpl} class implements methods from the {@code BaseService} interface
 * and the {@link BookingAbstractService} class. Is used to manipulate of a booking information
 * at the service layer.
 */
public class BookingServiceImpl extends BookingAbstractService {
    private static final Logger logger = LogManager.getFormatterLogger(BookingServiceImpl.class);
    /**
     * The field holding the reference type value of the {@link BookingConverter} implementation.
     */
    private final BaseConverter<BookingDTO, BookingDAO> bookingConverter = new BookingConverter();
    /**
     * The field holding the reference type value of the {@link BookingValidator} implementation.
     */
    private final BookingValidator bookingValidator = new BookingValidator();
    /**
     * The field holding the reference type value of the {@link AccountServiceImpl} implementation.
     */
    private final AccountAbstractService accountService = ServiceProvider.INSTANCE.getAccountImpl();
    /**
     * The field holding the reference type value of the {@link CarServiceImpl} implementation.
     */
    private final BaseService<CarDTO> carService = ServiceProvider.INSTANCE.getCarImpl();
    /**
     * The field holding the booking implementation of DAO layer.
     */
    private final BookingAbstractDAO bookingDAOImpl = DAOProvider.INSTANCE.getBookingImpl();

    @Override
    public List<BookingDTO> getAll() throws ServiceException {
        logger.traceEntry("- try to get all orders.");
        List<BookingDTO> ordersDTO = new ArrayList<>();

        try {
            List<BookingDAO> ordersDAO = bookingDAOImpl.findAll();

            for (BookingDAO order : ordersDAO) {
                ordersDTO.add(bookingConverter.convert(order));
            }

        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
        logger.traceExit(" with orders %d", ordersDTO.size());
        return ordersDTO;
    }

    @Override
    public BookingDTO getById(int key) throws ServiceException {
        logger.traceEntry("- try to get booking by id.");
        BookingDTO bookingDTO;
        try {
            bookingDTO = bookingConverter.convert(bookingDAOImpl.findById(key));
        } catch (DAOException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }
        logger.traceExit(" with bookingDTO = %s", new ObjectMessage(bookingDTO));
        return bookingDTO;
    }

    @Override
    public Integer createReturnID(BookingDTO bookingDTO) throws ServiceException {
        logger.traceEntry("- try to create a new booking.");
        bookingValidator.validate(bookingDTO);

        bookingDTO.daysAmount = DataCounter.countDay(bookingDTO.rentalDate, bookingDTO.returnDate);
        bookingDTO.sum = DataCounter.countSum(bookingDTO.daysAmount, bookingDTO.car);

        BookingDAO bookingDAO = bookingConverter.convert(bookingDTO);
        logger.debug("line(%d), bookingDAO: %s", 82, new ObjectMessage(bookingDAO));

        int returnedId;
        try {
            returnedId = bookingDAOImpl.createReturnID(bookingDAO);
        } catch (DAOException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new ServiceException(ex);
        }

        logger.traceExit(" with id=%d", returnedId);
        return returnedId;
    }

    @Override
    public List<BookingDTO> getAllByAccountId(Integer index) throws ServiceException {
        logger.traceEntry("- try to get all orders using account id.");
        List<BookingDTO> bookingDTOList = new ArrayList<>();
        logger.debug("line(%d), index: %d", 100, index);

        try {
            List<BookingDAO> bookingDAOList = bookingDAOImpl.findAllByAccountId(index);

            for (BookingDAO bookingDAO : bookingDAOList) {
                bookingDTOList.add(bookingConverter.convert(bookingDAO));
            }

        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
        logger.traceExit(" with  %d", bookingDTOList.size());
        return bookingDTOList;
    }

    @Override
    public boolean deleteById(Map<String, String[]> params) throws ServiceException {
        logger.traceEntry("- try to delete booking by id.");
        String bookingId = params.get(ParameterManager.BOOKING_ID_PARAMETER)[0];

        bookingValidator.validateId(bookingId);
        int index = Integer.parseInt(bookingId);
        logger.debug("line(%d), index: %d", 123, index);

        boolean result;
        try {
            result = bookingDAOImpl.deleteById(index);
        } catch (DAOException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }

        logger.traceExit(" done.");
        return result;
    }

    @Override
    public boolean createByUserAndCarId(Map<String, String[]> params) throws ServiceException {
        logger.traceEntry("- try to create booking using car id and account id.");
        String carId = params.get(ParameterManager.CAR_ID_PARAMETER)[0];
        bookingValidator.validateId(carId);
        int carIndex = Integer.parseInt(carId);
        logger.debug("line(%d), carIndex: %d", 142, carIndex);

        String userId = params.get(ParameterManager.USER_ID_PARAMETER)[0];
        bookingValidator.validateId(userId);
        int userIndex = Integer.parseInt(userId);
        logger.debug("line(%d), userIndex: %d", 147, userIndex);

        String rentalDate = params.get(ParameterManager.RENTAL_DATE_PARAMETER)[0];
        String returnDate = params.get(ParameterManager.RETURN_DATE_PARAMETER)[0];
        String rentalLocation = params.get(ParameterManager.RENTAL_LOCATION_PARAMETER)[0];
        String returnLocation = params.get(ParameterManager.RETURN_LOCATION_PARAMETER)[0];

        AccountDTO accountDTO = accountService.getByUserId(userIndex);
        CarDTO carDTO = carService.getById(carIndex);

        BookingDTO bookingDTO;
        if (params.get(ParameterManager.BOOKING_ID_PARAMETER) != null) {
            String bookingId = params.get(ParameterManager.BOOKING_ID_PARAMETER)[0];
            int bookingIndex = Integer.parseInt(bookingId);
            bookingDTO = new BookingDTO(bookingIndex);
        } else {
            bookingDTO = new BookingDTO(null);
        }

        bookingDTO.account = accountDTO;
        bookingDTO.rentalDate = rentalDate;
        bookingDTO.rentalLocation = rentalLocation;
        bookingDTO.returnDate = returnDate;
        bookingDTO.returnLocation = returnLocation;
        bookingDTO.paid = false;
        bookingDTO.rejected = false;
        bookingDTO.car = carDTO;
        bookingDTO.accidentFree = true;
        bookingValidator.validate(bookingDTO);

        bookingDTO.daysAmount =
                DataCounter.countDay(bookingDTO.rentalDate, bookingDTO.returnDate);
        bookingDTO.sum =
                DataCounter.countSum(bookingDTO.daysAmount, bookingDTO.car);

        BookingDAO bookingDAO = bookingConverter.convert(bookingDTO);
        logger.debug("line(%d), bookingDAO: %s", 184, new ObjectMessage(bookingDAO));

        boolean result;
        try {
            result = bookingDAOImpl.create(bookingDAO);
        } catch (DAOException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new ServiceException(ex.getMessage(), ex);
        }

        logger.traceExit(" done.");
        return result;
    }

    @Override
    public boolean create(BookingDTO bookingDTO) throws ServiceException {
        logger.traceEntry("- try to create a new booking.");
        bookingValidator.validate(bookingDTO);

        bookingDTO.daysAmount = DataCounter.countDay(bookingDTO.rentalDate, bookingDTO.returnDate);
        bookingDTO.sum = DataCounter.countSum(bookingDTO.daysAmount, bookingDTO.car);

        BookingDAO bookingDAO = bookingConverter.convert(bookingDTO);
        logger.debug("line(%d), bookingDAO: %s", 207, new ObjectMessage(bookingDAO));

        boolean result;
        try {
            result = bookingDAOImpl.create(bookingDAO);
        } catch (DAOException ex) {
            logger.throwing(Level.ERROR, ex);
            throw new ServiceException(ex);
        }
        logger.traceExit(" done.");
        return result;
    }

}
