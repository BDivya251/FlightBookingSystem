package com.flight.service;

import com.flight.Entity.Airline;
import com.flight.Repository.AirlineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AirlineService {
	
    private final AirlineRepository airlineRepository;

   public Airline addAirline(String airline) {
	   Airline a = new Airline();
	   a.setAirlineName(airline);
	   return airlineRepository.save(a);
   }
    public Optional<Airline> findByName(String airline) {
        return airlineRepository.findByAirlineName(airline);
    }
}