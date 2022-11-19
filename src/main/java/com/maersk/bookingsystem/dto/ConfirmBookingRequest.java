package com.maersk.bookingsystem.dto;

import com.maersk.bookingsystem.enums.ContainerType;
import lombok.Getter;

@Getter
public class ConfirmBookingRequest {
    private ContainerType containerType;
    private int containerSize;
    private String origin;
    private String destination;
    private int quantity;
    private String timestamp;
}
