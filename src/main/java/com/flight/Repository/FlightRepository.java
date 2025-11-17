package com.flight.Repository;

import com.flight.Entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    boolean existsByFlightNumber(String flightNumber);

   
    List<Flight> findByFromPlaceAndToPlaceAndDepartureDateTimeBetween(
            String from, String to, LocalDateTime start, LocalDateTime end);

    
}
