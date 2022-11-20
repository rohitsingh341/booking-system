package com.maersk.bookingsystem.controllers;

import com.maersk.bookingsystem.dto.BookingCheckRequest;
import com.maersk.bookingsystem.dto.BookingCheckResponse;
import com.maersk.bookingsystem.dto.BookingConfirmRequest;
import com.maersk.bookingsystem.dto.BookingsAvailabilityCheckResponse;
import com.maersk.bookingsystem.enums.ContainerType;
import com.maersk.bookingsystem.repositories.BookingRepository;
import com.maersk.bookingsystem.repositories.IDHolderRepository;
import com.maersk.bookingsystem.sao.BookingsAvailabilityChecker;
import com.maersk.bookingsystem.services.BookingService;
import com.maersk.bookingsystem.services.IDGeneratorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@WebFluxTest
@Import({BookingService.class, IDGeneratorService.class})
class BookingControllerTest {

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private IDHolderRepository idHolderRepository;

    @MockBean
    private BookingsAvailabilityChecker bookingsAvailabilityChecker;

    @Autowired
    private WebTestClient webClient;

    @DisplayName("Booking is not available from external API")
    @Test
    void checkBooking_bookingNotAvailable() {

        BookingCheckRequest bookingCheckRequest = BookingCheckRequest.builder()
                .containerSize(1)
                .containerType(ContainerType.DRY.name())
                .destination("Singapore")
                .origin("Southampton")
                .quantity(5)
                .build();

        webClient.post()
                .uri(BookingController.REST_BINDINGS + BookingController.CHECK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(bookingCheckRequest))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody().jsonPath("$.available").isEqualTo(false);

        Mockito.verify(bookingsAvailabilityChecker, times(1)).checkAvailability(bookingCheckRequest);
    }

    @DisplayName("Booking is available from external API")
    @Test
    void checkBooking_bookingAvailable() {

        BookingCheckRequest bookingCheckRequest = BookingCheckRequest.builder()
                .containerSize(1)
                .containerType(ContainerType.DRY.name())
                .destination("Singapore")
                .origin("Southampton")
                .quantity(5)
                .build();

        BookingsAvailabilityCheckResponse bookingsAvailabilityCheckResponse = BookingsAvailabilityCheckResponse.builder().availableSpace(1).build();
        Mockito.when(bookingsAvailabilityChecker.checkAvailability(bookingCheckRequest)).thenReturn(bookingsAvailabilityCheckResponse);

        webClient.post()
                .uri(BookingController.REST_BINDINGS + BookingController.CHECK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(bookingCheckRequest))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody().jsonPath("$.available").isEqualTo(true);

        Mockito.verify(bookingsAvailabilityChecker, times(1)).checkAvailability(bookingCheckRequest);
    }

    @Test
    void confirmBooking() {

        BookingConfirmRequest bookingConfirmRequest = BookingConfirmRequest.builder()
                .containerSize(1)
                .containerType(ContainerType.REEFER.name())
                .destination("Singapore")
                .origin("Southampton")
                .quantity(2)
                .build();

        webClient.post()
                .uri(BookingController.REST_BINDINGS + BookingController.CONFIRM)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(bookingConfirmRequest))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody().jsonPath("$.bookingRef").isEqualTo("957000001");
    }
}