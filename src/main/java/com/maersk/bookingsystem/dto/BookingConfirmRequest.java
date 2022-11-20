package com.maersk.bookingsystem.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.maersk.bookingsystem.enums.ContainerType;
import com.maersk.bookingsystem.validators.ValueOfEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import javax.validation.constraints.Size;

@Getter
@Value
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BookingConfirmRequest {

    @ValueOfEnum(enumClass = ContainerType.class, message = "Must be a valid container")
    private String containerType;

    private int containerSize;

    @Size(min = 5, max = 20)
    private String origin;

    @Size(min = 5, max = 20)
    private String destination;

    private int quantity;

    private String timestamp;
}
