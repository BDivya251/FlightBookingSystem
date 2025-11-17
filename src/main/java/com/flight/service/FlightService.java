package com.flight.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.flight.Entity.Airline;
import com.flight.Entity.Flight;
import com.flight.Repository.FlightRepository;
import com.flight.dtoReq.SearchFlightRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlightService {
	 private final FlightRepository flightRepository;
	 private final AirlineService airlineService;
	 public List<Flight> displayFlights(){
		 List<Flight> flights=flightRepository.findAll();
		 return flights;
	 }
	 public Flight addInventory(Flight request) throws Exception {
		Airline airline = airlineService.findByName(request.getAirline().getAirlineName())
                .orElseThrow(() -> new Exception("Airline not found"));
		if (flightRepository.existsByFlightNumber(request.getFlightNumber())) {
            throw new Exception("Flight with same number already exists!");
        }

        Flight flight = new Flight();

        flight.setAirline(airline);
        flight.setFlightNumber(request.getFlightNumber());
        flight.setFromPlace(request.getFromPlace());
        flight.setToPlace(request.getToPlace());
        flight.setDepartureDateTime(request.getDepartureDateTime());
        flight.setArrivalDateTime(request.getArrivalDateTime());
        flight.setPrice(request.getPrice());
        flight.setTotalSeats(request.getTotalSeats());
        flight.setSeatsAvailable(request.getTotalSeats());

        return flightRepository.save(flight);
	}
	 
	
	 public Map<String, List<Flight>> searchFlights(SearchFlightRequest req) {

	     LocalDateTime start = req.getDate().atStartOfDay();
	     LocalDateTime end = req.getDate().atTime(23, 59, 59);

	    
	     List<Flight> onwardFlights =
	             flightRepository.findByFromPlaceAndToPlaceAndDepartureDateTimeBetween(
	                     req.getFromPlace(),
	                     req.getToPlace(),
	                     start,
	                     end
	             );

	     Map<String, List<Flight>> result = new HashMap<>();
	     result.put("onward", onwardFlights);

	    
	     if (req.isRoundTrip()) {
	         List<Flight> returnFlights =
	                 flightRepository.findByFromPlaceAndToPlaceAndDepartureDateTimeBetween(
	                         req.getToPlace(),
	                         req.getFromPlace(),
	                         start,
	                         end
	                 );

	         result.put("return", returnFlights);
	     }

	     return result;
	 }

}
