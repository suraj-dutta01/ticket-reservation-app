package org.jsp.reservationapi.exceptions;

@SuppressWarnings("serial")
public class TicketNotFoundException extends RuntimeException{
	public TicketNotFoundException(String message) {
		super(message);
	}

}
