package com.epam.carrental.service.converter.impl;

import com.epam.carrental.dao.entity.BookingDAO;
import com.epam.carrental.dao.exception.DAOException;
import com.epam.carrental.service.AccountAbstractService;
import com.epam.carrental.service.BaseService;
import com.epam.carrental.service.ServiceProvider;
import com.epam.carrental.service.converter.BaseConverter;
import com.epam.carrental.service.dto.AccountDTO;
import com.epam.carrental.service.dto.BookingDTO;
import com.epam.carrental.service.dto.CarDTO;
import com.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;
/**
 * The {@code BookingConverter} class implements methods from {@code BaseConverter} interface
 * and makes simple conversion from {@link BookingDTO} object to {@link BookingDAO} object.
 * */
public class BookingConverter implements BaseConverter<BookingDTO, BookingDAO> {
    private static final Logger logger = LogManager.getFormatterLogger(BookingConverter.class);
    /**
     * The field holding account implementation of Service layer.
     */
    private final AccountAbstractService accountServiceImpl = ServiceProvider.INSTANCE.getAccountImpl();
    /**
     * The field holding car implementation of Service layer.
     */
    private final BaseService<CarDTO> carServiceImpl = ServiceProvider.INSTANCE.getCarImpl();
    /**
     * Converts {@link BookingDAO} object to {@link BookingDTO} considering to the foreign key ID.
     * @param bookingDAO the value to be converted.
     * @return the new {@link BookingDTO} object.
     * @throws ServiceException if {@link DAOException} aries.
     */
    @Override
    public BookingDTO convert(BookingDAO bookingDAO) throws ServiceException {
        logger.traceEntry("- try to convert Booking from DAO to DTO.");
        AccountDTO accountDTO = accountServiceImpl.getById(bookingDAO.getAccountId());
        logger.debug("line(%d), plus Object accountDto: %s", 24, new ObjectMessage(accountDTO));
        CarDTO carDto = carServiceImpl.getById(bookingDAO.getCarId());
        logger.debug("line(%d), plus Object carDto: %s", 27, new ObjectMessage(carDto));

        BookingDTO bookingDTO = new BookingDTO(bookingDAO.getId());
        bookingDTO.account = accountDTO;
        bookingDTO.rentalDate = bookingDAO.getRentalDate();
        bookingDTO.rentalLocation = bookingDAO.getRentalLocation();
        bookingDTO.returnDate = bookingDAO.getReturnDate();
        bookingDTO.returnLocation = bookingDAO.getReturnLocation();
        bookingDTO.daysAmount = bookingDAO.getDaysAmount();
        bookingDTO.sum = bookingDAO.getSum();
        bookingDTO.paid = bookingDAO.isPaid();
        bookingDTO.rejected = bookingDAO.isRejected();
        bookingDTO.car = carDto;
        bookingDTO.accidentFree = bookingDAO.isAccidentFree();
        logger.traceExit(" with bookingDTO: %s", new ObjectMessage(bookingDTO));
        return bookingDTO;
    }
    /**
     * Converts {@link BookingDTO} object to {@link BookingDAO}.
     * All object type references will be changed on {@code Integer} value.
     * @param    bookingDTO the value to be converted.
     * @return   the new {@link BookingDAO} object with the IDs,
     *           which corresponds to the foreign key in a database.
     */
    @Override
    public BookingDAO convert(BookingDTO bookingDTO) {
        logger.debug("converting Booking from DTO to DAO.");
        return new BookingDAO.Builder(bookingDTO.id).
                accountId(bookingDTO.account.id).
                rentalDate(bookingDTO.rentalDate).
                rentalLocation(bookingDTO.rentalLocation).
                returnDate(bookingDTO.returnDate).
                returnLocation(bookingDTO.returnLocation).
                daysAmount(bookingDTO.daysAmount).
                sum(bookingDTO.sum).
                paid(bookingDTO.paid).
                rejected(bookingDTO.rejected).
                carId(bookingDTO.car.id).
                accidentFree(bookingDTO.accidentFree).build();
    }
}
