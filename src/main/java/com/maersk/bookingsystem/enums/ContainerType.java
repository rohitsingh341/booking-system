package com.maersk.bookingsystem.enums;

public enum ContainerType {
    DRY,
    REEFER;

    public static ContainerType fromString(String enumStr) {
        for (ContainerType containerType: ContainerType
                .values()) {
            if (containerType.name().equals(enumStr)) {
                return containerType;
            }
        }
        return null;
    }
}
