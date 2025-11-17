
package com.flight.service;

import com.flight.Entity.*;
import com.flight.Repository.BookingRepository;
import com.flight.Repository.FlightRepository;
import com.flight.dtoReq.BookingRequest;
import com.flight.dtoReq.PassengerRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBookFlightSuccess() throws Exception {

        Long flightId = 1L;

        Flight flight = new Flight();
        flight.setSeatsAvailable(5);

        when(flightRepository.findById(flightId))
                .thenReturn(Optional.of(flight));

        BookingRequest req = new BookingRequest();
        req.setUserName("Divya");
        req.setUserEmail("divya@mail.com");
        req.setJourneyDate(LocalDate.now().plusDays(5));
        req.setMealType("Veg");

        PassengerRequest p = new PassengerRequest();
        p.setPassengerName("Divya");
        p.setGender("Female");
        p.setAge(22);

        req.setPassengers(List.of(p));

        Booking saved = new Booking();
        saved.setBookingId(10L);

        when(bookingRepository.save(any(Booking.class)))
                .thenReturn(saved);

        Booking result = bookingService.bookFlight(flightId, req);

        assertNotNull(result);
        assertEquals(10L, result.getBookingId());
        verify(flightRepository, times(1)).save(flight);
    }

    @Test
    void testBookFlightFlightNotFound() {

        when(flightRepository.findById(1L))
                .thenReturn(Optional.empty());

        BookingRequest req = new BookingRequest();
        req.setPassengers(List.of(new PassengerRequest()));

        Exception ex = assertThrows(Exception.class, () ->
                bookingService.bookFlight(1L, req));

        assertEquals("Flight not found", ex.getMessage());
    }

    @Test
    void testBookFlightNotEnoughSeats() {

        Flight flight = new Flight();
        flight.setSeatsAvailable(0);

        when(flightRepository.findById(1L))
                .thenReturn(Optional.of(flight));

        BookingRequest req = new BookingRequest();
        req.setPassengers(List.of(new PassengerRequest()));

        Exception ex = assertThrows(Exception.class, () ->
                bookingService.bookFlight(1L, req));

        assertEquals("Not enough seats available", ex.getMessage());
    }

    @Test
    void testCancelBooking() throws Exception {

        Booking booking = new Booking();
        booking.setSeatsBooked(2);

        Flight flight = new Flight();
        flight.setSeatsAvailable(5);

        booking.setFlight(flight);

        when(bookingRepository.findByPnr("PNR1"))
                .thenReturn(Optional.of(booking));

        String result = bookingService.cancelBookingBasedOnPNR("PNR1");

        assertEquals("Successfully canceled", result);
        assertEquals(7, flight.getSeatsAvailable());
    }

    @Test
    void testGetBookingWithPNR() throws Exception {

        Booking booking = new Booking();
        booking.setPnr("PNR123");

        when(bookingRepository.findByPnr("PNR123"))
                .thenReturn(Optional.of(booking));

        Booking result = bookingService.getBookingWithPNR("PNR123");

        assertEquals("PNR123", result.getPnr());
    }
}
