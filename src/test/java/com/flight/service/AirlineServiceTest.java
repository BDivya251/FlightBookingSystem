package com.flight.service;

import com.flight.Entity.Airline;
import com.flight.Repository.AirlineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AirlineServiceTest {

    @Mock
    private AirlineRepository airlineRepository;

    @InjectMocks
    private AirlineService airlineService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddAirline() {
        Airline airline = new Airline();
        airline.setAirlineName("Indigo");

        when(airlineRepository.save(any(Airline.class)))
                .thenReturn(airline);

        Airline result = airlineService.addAirline("Indigo");

        assertNotNull(result);
        assertEquals("Indigo", result.getAirlineName());
        verify(airlineRepository, times(1)).save(any(Airline.class));
    }

    @Test
    void testFindByName() {
        Airline airline = new Airline();
        airline.setAirlineName("AirIndia");

        when(airlineRepository.findByAirlineName("AirIndia"))
                .thenReturn(Optional.of(airline));

        Optional<Airline> result = airlineService.findByName("AirIndia");

        assertTrue(result.isPresent());
        assertEquals("AirIndia", result.get().getAirlineName());
    }
}
