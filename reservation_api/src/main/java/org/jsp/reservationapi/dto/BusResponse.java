package org.jsp.reservationapi.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusResponse {
	private int id;
	private String name;
	private String bus_number;
	private int number_of_seats;
	private String from_location;
	private String to_location;
	private LocalDate date_of_departure;	

}
