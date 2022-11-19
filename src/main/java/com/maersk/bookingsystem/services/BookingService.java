package com.maersk.bookingsystem.services;

import com.maersk.bookingsystem.dto.ConfirmBookingRequest;
import com.maersk.bookingsystem.model.Bookings;
import com.maersk.bookingsystem.model.IDHolder;
import com.maersk.bookingsystem.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {

    @Autowired
    public BookingRepository bookingRepository;

    @Autowired
    public IDGeneratorService idGeneratorService;

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
                .containerType(confirmBookingRequest.getContainerType().name())
                .destination(confirmBookingRequest.getDestination())
                .origin(confirmBookingRequest.getOrigin())
                .quantity(confirmBookingRequest.getQuantity())
                .build();
    }
}
