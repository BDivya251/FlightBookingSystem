package com.flight.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.flight.Entity.Booking;
import com.flight.Entity.Flight;
import com.flight.Entity.PassengerDetail;
import com.flight.Repository.BookingRepository;
import com.flight.Repository.FlightRepository;
import com.flight.dtoReq.BookingRequest;
import com.flight.dtoReq.PassengerRequest;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class BookingService {

    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;

    public Booking bookFlight(Long flightId, BookingRequest request) throws Exception {

        boolean roundTrip=false;
        if(request.getRoundTrip()==true) {
        	roundTrip=true;
        }
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new Exception("Flight not found"));

        int seatsNeeded = request.getPassengers().size();

      
        if (flight.getSeatsAvailable() < seatsNeeded) {
            throw new Exception("Not enough seats available");
        }

       
        flight.setSeatsAvailable(flight.getSeatsAvailable() - seatsNeeded);
        flightRepository.save(flight);

        
        Booking booking = new Booking();
        booking.setFlight(flight);
        booking.setUserName(request.getUserName());
        booking.setUserEmail(request.getUserEmail());
        booking.setJourneyDate(request.getJourneyDate());
        booking.setBookingDateTime(LocalDateTime.now());
        booking.setSeatsBooked(seatsNeeded);
        booking.setMealType(request.getMealType());
        booking.setStatus("BOOKED");
        booking.setPnr(generatePNR());

        
        List<PassengerDetail> passengerDetails = new ArrayList<>();

        int seatCounter = 1;

        for (PassengerRequest p : request.getPassengers()) {

            PassengerDetail passenger = new PassengerDetail();
            passenger.setPassengerName(p.getPassengerName());
            passenger.setGender(p.getGender());
            passenger.setAge(p.getAge());
            passenger.setSeatNumber(seatCounter++);  // generate seat
            passenger.setBooking(booking);

            passengerDetails.add(passenger);
        }

        booking.setPassengers(passengerDetails);

        return bookingRepository.save(booking);
    }

    private String generatePNR() {
        return "PNR" + System.currentTimeMillis();
    }
    public Booking getBookingWithPNR(String pnr) throws Exception {
    	Booking booking = bookingRepository.findByPnr(pnr)
    			.orElseThrow(()->new Exception("PNR not found"));
    	return booking;
    }
    public String cancelBookingBasedOnPNR(String pnr) throws Exception {
    	Booking booking = bookingRepository.findByPnr(pnr)
    			.orElseThrow(()->new Exception("PNR not found"));
    	System.out.println(booking.getFlight().getSeatsAvailable());
    	int seatsToAdd = booking.getSeatsBooked();
    	Flight flight = booking.getFlight();
    	flight.setSeatsAvailable(flight.getSeatsAvailable()+seatsToAdd);
    	booking.setStatus("CANCELLED");
    	bookingRepository.save(booking);
    	System.out.println(booking.getFlight().getSeatsAvailable());
    	return "Successfully canceled";
    }
    public List<Booking> detailsBasedOnEmail(String email){
    	return bookingRepository.findByUserEmail(email);
    }
}

