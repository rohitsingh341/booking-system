package com.maersk.bookingsystem.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CheckBookingResponse {
    private boolean available;
}
