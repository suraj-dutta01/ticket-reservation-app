package org.jsp.reservationapi.exceptions;

public class AdminNotFoundException extends RuntimeException {
	public AdminNotFoundException(String message) {
		super(message);
	}

}
