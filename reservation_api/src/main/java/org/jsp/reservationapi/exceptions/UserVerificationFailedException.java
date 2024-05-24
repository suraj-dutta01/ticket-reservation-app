package org.jsp.reservationapi.exceptions;

public class UserVerificationFailedException extends RuntimeException{
	public UserVerificationFailedException(String message) {
		super(message);
	}

}
