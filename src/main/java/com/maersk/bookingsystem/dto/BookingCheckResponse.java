package com.maersk.bookingsystem.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class BookingCheckResponse {
    private boolean available;
}
