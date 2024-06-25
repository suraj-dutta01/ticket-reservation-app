package org.jsp.reservationapi.controller;

import org.jsp.reservationapi.dto.ResponseStructure;
import org.jsp.reservationapi.dto.TicketResponse;
import org.jsp.reservationapi.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/tickets")
public class TicketController {
	@Autowired
	private TicketService ticketService;
    
	@PostMapping("/{userId}/{busId}/{numberOfSeats}")
	public ResponseEntity<ResponseStructure<TicketResponse>> bookTicket(@PathVariable int userId ,@PathVariable int busId,@PathVariable int numberOfSeats) {
		return ticketService.bookTicket(userId, busId, numberOfSeats);
	}
	@PostMapping("/cancel/{busId}/{userId}/{ticketId}")
	public ResponseEntity<ResponseStructure<String>> cancelTicket(@PathVariable int busId,@PathVariable int userId,@PathVariable int ticketId) {
		return ticketService.cancelTicket(busId, userId, ticketId);
	}
}
