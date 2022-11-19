package com.maersk.bookingsystem.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.maersk.bookingsystem.enums.ContainerType;
import com.maersk.bookingsystem.validators.ValueOfEnum;
import lombok.Getter;
import lombok.Value;

import javax.validation.constraints.Size;

@Getter
@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CheckBookingRequest {

    @ValueOfEnum(enumClass = ContainerType.class, message = "Must be a valid Container Type")
    private String containerType;

    private int containerSize;

    @Size(min = 5, max = 20)
    private String origin;

    @Size(min = 5, max = 20)
    private String destination;

    private int quantity;
}
