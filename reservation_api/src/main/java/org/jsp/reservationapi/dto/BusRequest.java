package org.jsp.reservationapi.dto;

import java.time.LocalDate;

import lombok.Data;
@Data
public class BusRequest {

	private String name;
	private String bus_number;
	private int number_of_seats;
	private String from_location;
	private String to_location;
	private LocalDate date_of_departure;
}