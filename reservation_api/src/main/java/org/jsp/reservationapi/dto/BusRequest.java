package org.jsp.reservationapi.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class BusRequest {
    @NotBlank(message = "Name is Mendatory")
	private String name;
    @NotBlank(message = "Bus number is mendatory")
	private String bus_number;
    
	private int number_of_seats;
	@NotBlank(message = "From location is mendatory")
	private String from_location;
	@NotBlank(message = "To location is mendatory")
	private String to_location;	
	private LocalDate date_of_departure;
}
