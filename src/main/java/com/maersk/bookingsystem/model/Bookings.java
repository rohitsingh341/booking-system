package com.maersk.bookingsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
@Builder
@Data
@AllArgsConstructor
public class Bookings {

    @PrimaryKeyColumn(
            type = PrimaryKeyType.PARTITIONED)
    private Integer id;
    @Column("container_type")
    private String containerType;
    @Column("container_size")
    private int containerSize;
    private String origin;
    private String destination;
    private int quantity;
    private String timestamp;
}
