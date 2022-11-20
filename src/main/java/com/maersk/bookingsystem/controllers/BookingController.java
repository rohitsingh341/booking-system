package com.maersk.bookingsystem.controllers;

import com.maersk.bookingsystem.dto.BookingCheckRequest;
import com.maersk.bookingsystem.dto.BookingCheckResponse;
import com.maersk.bookingsystem.dto.BookingConfirmRequest;
import com.maersk.bookingsystem.dto.BookingConfirmResponse;
import com.maersk.bookingsystem.services.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.Set;
import java.util.StringJoiner;

@Slf4j
@RestController
@RequestMapping(BookingController.REST_BINDINGS)
public class BookingController {

    public static final String REST_BINDINGS = "/api/bookings";
    public static final String CHECK = "/check";
    public static final String CONFIRM = "/confirm";

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping(CHECK)
    public BookingCheckResponse checkBooking(@RequestBody @Valid BookingCheckRequest bookingCheckRequest) {
        log.info("Request received to check the availability - [{}]", bookingCheckRequest);
        boolean isBookingAvailable = bookingService.checkBookingAvailability(bookingCheckRequest);
        return BookingCheckResponse.builder().available(isBookingAvailable).build();
    }

    @PostMapping(CONFIRM)
    public BookingConfirmResponse confirmBooking(@RequestBody @Valid BookingConfirmRequest bookingConfirmRequest) {
        log.info("Request received to confirm the booking - [{}]", bookingConfirmRequest);
        String bookingRef = bookingService.confirmBooking(bookingConfirmRequest);
        return BookingConfirmResponse.builder().bookingRef(bookingRef).build();
    }
}
