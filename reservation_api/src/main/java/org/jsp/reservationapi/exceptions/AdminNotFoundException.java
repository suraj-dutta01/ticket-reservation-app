package org.jsp.reservationapi.exceptions;

@SuppressWarnings("serial")
public class AdminNotFoundException extends RuntimeException {
	public AdminNotFoundException(String message) {
		super(message);
	}

}
