package com.flight.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flight.Entity.Booking;
import com.flight.Entity.Flight;
import com.flight.dtoReq.BookingRequest;
import com.flight.dtoReq.SearchFlightRequest;
import com.flight.service.BookingService;
import com.flight.service.FlightService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1.0/flight")
@RequiredArgsConstructor
public class flightController {
	private final FlightService flightService;
	private final BookingService bookingService;
	@GetMapping("/search")
	public List<Flight> getFlights(){
		return flightService.displayFlights();
	}
	@PostMapping("/search")
	public Map<String, List<Flight>> searchFlight(@Valid @RequestBody SearchFlightRequest req){
		return flightService.searchFlights(req);
	}
	@PostMapping("/booking/{flightId}")
	public Booking booking(
			@PathVariable Long flightId,
			@RequestBody BookingRequest re
			) throws Exception {
		return bookingService.bookFlight(flightId,re);
	}
	@GetMapping("/ticket/{pnr}")
	public Booking getBasedOnPNR(@PathVariable String pnr) throws Exception {
		return bookingService.getBookingWithPNR(pnr);
	}
	
	@DeleteMapping("/booking/cancel/{pnr}")
	public String delOnPNR(@PathVariable String pnr) throws Exception{
		return bookingService.cancelBookingBasedOnPNR(pnr);
	}
	
	@GetMapping("/booking/history/{email}")
	public List<Booking> findDetailsByEmail(@PathVariable String email){
		return bookingService.detailsBasedOnEmail(email);
	}
}
