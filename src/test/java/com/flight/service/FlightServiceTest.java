package com.flight.service;

import com.flight.Entity.Airline;
import com.flight.Entity.Flight;
import com.flight.Repository.FlightRepository;
import com.flight.dtoReq.SearchFlightRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private AirlineService airlineService;

    @InjectMocks
    private FlightService flightService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddInventorySuccess() throws Exception {
        Airline airline = new Airline();
        airline.setAirlineName("Indigo");

        Flight request = new Flight();
        request.setAirline(airline);
        request.setFlightNumber("6E123");
        request.setFromPlace("Hyd");
        request.setToPlace("Delhi");
        request.setDepartureDateTime(LocalDateTime.now());
        request.setArrivalDateTime(LocalDateTime.now().plusHours(2));
        request.setPrice(5000.0);
        request.setTotalSeats(100);

        when(airlineService.findByName("Indigo"))
                .thenReturn(Optional.of(airline));

        when(flightRepository.existsByFlightNumber("6E123"))
                .thenReturn(false);

        when(flightRepository.save(any(Flight.class)))
                .thenReturn(request);

        Flight result = flightService.addInventory(request);

        assertNotNull(result);
        assertEquals("Indigo", result.getAirline().getAirlineName());
        verify(flightRepository, times(1)).save(any(Flight.class));
    }

    @Test
    void testAddInventoryAirlineNotFound() {
        Flight f = new Flight();
        Airline a = new Airline();
        a.setAirlineName("Fake");
        f.setAirline(a);

        when(airlineService.findByName("Fake"))
                .thenReturn(Optional.empty());

        Exception ex = assertThrows(Exception.class,
                () -> flightService.addInventory(f));

        assertEquals("Airline not found", ex.getMessage());
    }

    @Test
    void testSearchFlightsOneWay() {

        SearchFlightRequest req = new SearchFlightRequest();
        req.setFromPlace("Hyd");
        req.setToPlace("Delhi");
        req.setDate(LocalDate.now());
        req.setRoundTrip(false);

        Flight flight = new Flight();
        flight.setFromPlace("Hyd");
        flight.setToPlace("Delhi");

        when(flightRepository.findByFromPlaceAndToPlaceAndDepartureDateTimeBetween(
                anyString(), anyString(), any(), any()))
                .thenReturn(List.of(flight));

        Map<String, List<Flight>> result = flightService.searchFlights(req);

        assertEquals(1, result.get("onward").size());
        assertFalse(result.containsKey("return"));
    }

    @Test
    void testSearchFlightsRoundTrip() {

        SearchFlightRequest req = new SearchFlightRequest();
        req.setFromPlace("Hyd");
        req.setToPlace("Delhi");
        req.setDate(LocalDate.now());
        req.setRoundTrip(true);

        Flight onward = new Flight();
        Flight ret = new Flight();

        when(flightRepository.findByFromPlaceAndToPlaceAndDepartureDateTimeBetween(
                anyString(), anyString(), any(), any()))
                .thenReturn(List.of(onward))
                .thenReturn(List.of(ret));

        Map<String, List<Flight>> result = flightService.searchFlights(req);

        assertEquals(1, result.get("onward").size());
        assertEquals(1, result.get("return").size());
    }
}
