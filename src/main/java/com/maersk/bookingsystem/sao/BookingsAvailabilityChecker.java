package com.maersk.bookingsystem.sao;

import com.maersk.bookingsystem.dto.BookingsAvailabilityCheckResponse;
import com.maersk.bookingsystem.dto.CheckBookingRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;
import java.util.Random;

@Service
public class BookingsAvailabilityChecker {

    @Value("${external.bookings.availability-check.api.endpoint}")
    private String externalApiEndpoint;

    public BookingsAvailabilityCheckResponse checkAvailability(CheckBookingRequest checkBookingRequest) {
        URI uri = UriComponentsBuilder.fromHttpUrl(Objects.requireNonNull(externalApiEndpoint)).build().toUri();

        return BookingsAvailabilityCheckResponse.builder().availableSpace(getAvailableSpace()).build();

        //TODO: if the endpoint is working we can call the below
//        return WebClient.create()
//                .post()
//                .uri(uri)
//                .bodyValue(checkBookingRequest)
//                .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON)
//                .retrieve().bodyToMono(BookingsAvailabilityCheckResponse.class).block();
    }

    /**
     *  To mimic the behaviour - the method returns a random number from 0 to 2, which says whether the space is available or not
     * @return int
     */
    private int getAvailableSpace() {
        return new Random().nextInt(2);
    }
}