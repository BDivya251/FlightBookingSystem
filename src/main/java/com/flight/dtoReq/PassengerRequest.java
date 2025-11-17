package com.flight.dtoReq;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PassengerRequest {
	 @NotBlank(message = "Passenger name is required")
    private String passengerName;
   
	 @NotBlank(message = "Gender is required")
	private String gender;

	@Min(value = 1, message = "Age must be at least 1")
	@Max(value = 120, message = "Age cannot exceed 120")
    private Integer age;
	private Integer seatNumber;
}
