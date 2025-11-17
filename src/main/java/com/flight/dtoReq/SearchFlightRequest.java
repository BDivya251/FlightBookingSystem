package com.flight.dtoReq;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SearchFlightRequest {
	@NotBlank(message = "From place cannot be empty")
    private String fromPlace;
	@NotBlank(message = "To place cannot be empty")
    private String toPlace;
	 @NotNull(message = "Date is required")
    private LocalDate date;
	 @NotNull(message = "Write false if 1-Way")
    private boolean roundTrip;
	public String getFromPlace() {
		return fromPlace;
	}
	public void setFromPlace(String fromPlace) {
		this.fromPlace = fromPlace;
	}
	public String getToPlace() {
		return toPlace;
	}
	public void setToPlace(String toPlace) {
		this.toPlace = toPlace;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public boolean isRoundTrip() {
		return roundTrip;
	}
	public void setRoundTrip(boolean roundTrip) {
		this.roundTrip = roundTrip;
	}  

    
}