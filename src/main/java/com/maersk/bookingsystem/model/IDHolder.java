package com.maersk.bookingsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("id_holder")
@Data
@AllArgsConstructor
public class IDHolder {

    @PrimaryKeyColumn(
            type = PrimaryKeyType.PARTITIONED)
    @Id
    private String context;
    private Integer id;
}
