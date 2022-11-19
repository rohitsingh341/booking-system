package com.maersk.bookingsystem.repositories;

import com.maersk.bookingsystem.model.IDHolder;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface IDGeneratorRepository extends CassandraRepository<IDHolder, String> {
}
