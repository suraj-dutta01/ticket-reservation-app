package org.jsp.reservationapi.exceptions;

public class AdminVerificationFailedException extends RuntimeException{
public AdminVerificationFailedException(String message) {
	super(message);
}
}
