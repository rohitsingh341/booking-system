package com.maersk.bookingsystem.services;

import com.maersk.bookingsystem.dto.BookingsAvailabilityCheckResponse;
import com.maersk.bookingsystem.dto.BookingCheckRequest;
import com.maersk.bookingsystem.dto.BookingConfirmRequest;
import com.maersk.bookingsystem.model.Bookings;
import com.maersk.bookingsystem.model.IDHolder;
import com.maersk.bookingsystem.repositories.BookingRepository;
import com.maersk.bookingsystem.sao.BookingsAvailabilityChecker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Slf4j
public class BookingService {

    public final BookingRepository bookingRepository;

    public final IDGeneratorService idGeneratorService;

    private final BookingsAvailabilityChecker bookingsAvailabilityChecker;

    @Autowired
    public BookingService(BookingRepository bookingRepository, IDGeneratorService idGeneratorService, BookingsAvailabilityChecker bookingsAvailabilityChecker) {
        this.bookingRepository = bookingRepository;
        this.idGeneratorService = idGeneratorService;
        this.bookingsAvailabilityChecker = bookingsAvailabilityChecker;
    }

    @Transactional
    public String confirmBooking(BookingConfirmRequest bookingConfirmRequest) {
        IDHolder idHolder = idGeneratorService.getCurrentIdHolder();

        Bookings bookings = convertToDBEntity(bookingConfirmRequest, idHolder.getId());
        bookingRepository.save(bookings);
        log.info("Persisted Bookings in DB - [{}]", bookings);

        idGeneratorService.incrementIDValue(idHolder);
        return String.valueOf(bookings.getId());
    }

    private Bookings convertToDBEntity(BookingConfirmRequest bookingConfirmRequest, Integer id) {
        return Bookings.builder()
                .id(id)
                .containerSize(bookingConfirmRequest.getContainerSize())
                .containerType(bookingConfirmRequest.getContainerType())
                .destination(bookingConfirmRequest.getDestination())
                .origin(bookingConfirmRequest.getOrigin())
                .quantity(Integer.valueOf(bookingConfirmRequest.getQuantity()))
                .timestamp(bookingConfirmRequest.getTimestamp())
                .build();
    }

    public boolean checkBookingAvailability(BookingCheckRequest bookingCheckRequest) {
        BookingsAvailabilityCheckResponse bookingsAvailabilityCheckResponse = bookingsAvailabilityChecker.checkAvailability(bookingCheckRequest);
        if (Objects.nonNull(bookingsAvailabilityCheckResponse) && bookingsAvailabilityCheckResponse.getAvailableSpace() > 0) {
            log.info("Space is available for booking is - [{}]", bookingsAvailabilityCheckResponse.getAvailableSpace());
            return true;
        }
        return false;
    }
}
