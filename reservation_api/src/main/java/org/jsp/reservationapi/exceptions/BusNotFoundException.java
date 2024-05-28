package org.jsp.reservationapi.exceptions;

@SuppressWarnings("serial")
public class BusNotFoundException extends RuntimeException{

	public BusNotFoundException(String message) {
		super(message);
	}
}
