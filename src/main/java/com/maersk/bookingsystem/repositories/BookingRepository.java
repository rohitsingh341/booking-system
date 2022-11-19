package com.maersk.bookingsystem.repositories;

import com.maersk.bookingsystem.model.Bookings;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface BookingRepository extends CassandraRepository<Bookings, Integer> {
}
