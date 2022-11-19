package com.maersk.bookingsystem.controllers;

import com.maersk.bookingsystem.dto.CheckBookingRequest;
import com.maersk.bookingsystem.dto.CheckBookingResponse;
import com.maersk.bookingsystem.dto.ConfirmBookingRequest;
import com.maersk.bookingsystem.dto.ConfirmBookingResponse;
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
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    private final Validator validator;

    @Autowired
    public BookingController(BookingService bookingService, Validator validator) {
        this.bookingService = bookingService;
        this.validator = validator;
    }

    @PostMapping("/check")
    public CheckBookingResponse checkBooking(@RequestBody @Valid CheckBookingRequest checkBookingRequest) {
        log.info("Request received to check the availability - [{}]", checkBookingRequest);
        boolean isBookingAvailable = bookingService.checkBookingAvailability(checkBookingRequest);
        return CheckBookingResponse.builder().available(isBookingAvailable).build();
    }

    @PostMapping("/confirm")
    public ConfirmBookingResponse confirmBooking(@RequestBody @Valid ConfirmBookingRequest confirmBookingRequest) {
        log.info("Request received to confirm the booking - [{}]", confirmBookingRequest);
        String bookingRef = bookingService.confirmBooking(confirmBookingRequest);
        return ConfirmBookingResponse.builder().bookingRef(bookingRef).build();
    }

    private boolean validate(ConfirmBookingRequest confirmBookingRequest) {

        Set<ConstraintViolation<ConfirmBookingRequest>> constraintViolations = validator.validate(confirmBookingRequest);

        if (constraintViolations != null && !constraintViolations.isEmpty()) {
            StringJoiner stringJoiner = new StringJoiner(" ");
            constraintViolations.forEach(
                    loginModelConstraintViolation ->
                            stringJoiner
                                    .add(loginModelConstraintViolation.getPropertyPath().toString())
                                    .add(":")
                                    .add(loginModelConstraintViolation.getMessage()));
            throw new RuntimeException(stringJoiner.toString());
        }

        return true;
    }

}
