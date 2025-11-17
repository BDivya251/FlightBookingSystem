package com.flight.dtoReq;


import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingRequest {
	@NotBlank(message = "User name is required")
    private String userName;
	@Email(message = "Enter a valid email")
    @NotBlank(message = "Email is required")
    private String userEmail;
	 @NotNull(message = "Journey date is required")
    private LocalDate journeyDate;
	 @NotNull(message = "Meal type is required")
    private String mealType;
	 @NotNull(message="Round Trip is reuired")
	 private boolean roundTrip;
    private List<PassengerRequest> passengers;
    public boolean getRoundTrip() {
    	return this.roundTrip;
    }
    public void setRoundTrip(boolean a) {
    	this.roundTrip=a;
    }
	
   private Integer SeatNumber;
    private Long onwardFlightId;
    private Long returnFlightId;

}
