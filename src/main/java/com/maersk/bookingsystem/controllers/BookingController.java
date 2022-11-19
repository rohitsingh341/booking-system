package com.maersk.bookingsystem.controllers;

import com.maersk.bookingsystem.dto.CheckBookingRequest;
import com.maersk.bookingsystem.dto.CheckBookingResponse;
import com.maersk.bookingsystem.dto.ConfirmBookingRequest;
import com.maersk.bookingsystem.dto.ConfirmBookingResponse;
import com.maersk.bookingsystem.repositories.BookingRepository;
import com.maersk.bookingsystem.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController( BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/check")
    public CheckBookingResponse checkBooking( CheckBookingRequest checkBookingRequest) {
        return CheckBookingResponse.builder().available(true).build();
    }

    @PostMapping("/confirm")
    public ConfirmBookingResponse confirmBooking(@RequestBody @Valid ConfirmBookingRequest confirmBookingRequest) {
        String bookingRef = bookingService.confirmBooking(confirmBookingRequest);
        return ConfirmBookingResponse.builder().bookingRef(bookingRef).build();
    }

}
