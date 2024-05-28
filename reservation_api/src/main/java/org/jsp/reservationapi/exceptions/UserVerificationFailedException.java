package org.jsp.reservationapi.exceptions;

@SuppressWarnings("serial")
public class UserVerificationFailedException extends RuntimeException{
	public UserVerificationFailedException(String message) {
		super(message);
	}

}
