package com.maersk.bookingsystem.services;

import com.maersk.bookingsystem.dto.BookingsAvailabilityCheckResponse;
import com.maersk.bookingsystem.dto.CheckBookingRequest;
import com.maersk.bookingsystem.dto.ConfirmBookingRequest;
import com.maersk.bookingsystem.model.Bookings;
import com.maersk.bookingsystem.model.IDHolder;
import com.maersk.bookingsystem.repositories.BookingRepository;
import com.maersk.bookingsystem.sao.BookingsAvailabilityChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
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
    public String confirmBooking(ConfirmBookingRequest confirmBookingRequest) {
        IDHolder idHolder = idGeneratorService.getCurrentIdHolder();

        Bookings bookings = convertToDBEntity(confirmBookingRequest, idHolder.getId());
        bookingRepository.save(bookings);

        idGeneratorService.incrementIDValue(idHolder);
        return String.valueOf(bookings.getId());
    }

    private Bookings convertToDBEntity(ConfirmBookingRequest confirmBookingRequest, Integer id) {
        return Bookings.builder()
                .id(id)
                .containerSize(confirmBookingRequest.getContainerSize())
                .containerType(confirmBookingRequest.getContainerType())
                .destination(confirmBookingRequest.getDestination())
                .origin(confirmBookingRequest.getOrigin())
                .quantity(Integer.valueOf(confirmBookingRequest.getQuantity()))
                .timestamp(confirmBookingRequest.getTimestamp())
                .build();
    }

    public boolean checkBookingAvailability(CheckBookingRequest checkBookingRequest) {
        BookingsAvailabilityCheckResponse bookingsAvailabilityCheckResponse = bookingsAvailabilityChecker.checkAvailability(checkBookingRequest);
        return Objects.nonNull(bookingsAvailabilityCheckResponse) && bookingsAvailabilityCheckResponse.getAvailableSpace() > 0;
    }
}
