package com.flight.controller;

import com.flight.Entity.Airline;
//import com.flight.dto.FlightInventoryRequest;
import com.flight.Entity.Flight;
import com.flight.service.AirlineService;
import com.flight.service.FlightService;

import jakarta.validation.Valid;
//import com.flight.Service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/flight/airline/inventory")
@RequiredArgsConstructor
public class AdminController {

    private final FlightService flightService;
    private final AirlineService airlineService;
    @PostMapping("/add")
    public ResponseEntity<Flight> addInventory(@Valid @RequestBody Flight request) throws Exception {
        Flight flight = flightService.addInventory(request);
        return ResponseEntity.ok(flight);
    }
    @PostMapping("/addAirline")
    public ResponseEntity<Airline> addAirline(@Valid @RequestBody String name) throws Exception {
        Airline s = airlineService.addAirline(name);
    	return ResponseEntity.ok(s);
    }
}

