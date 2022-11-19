package com.maersk.bookingsystem.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ConfirmBookingResponse {
    private String bookingRef;
}
