package com.maersk.bookingsystem.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Builder
@Getter
@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class
BookingsAvailabilityCheckResponse {
    private int availableSpace;
}
