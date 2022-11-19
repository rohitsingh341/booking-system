package com.maersk.bookingsystem.dto;

import com.maersk.bookingsystem.enums.ContainerType;

public class CheckBookingRequest {
    private ContainerType containerType;
    private int containerSize;
    private String origin;
    private String destination;
    private int quantity;
}
