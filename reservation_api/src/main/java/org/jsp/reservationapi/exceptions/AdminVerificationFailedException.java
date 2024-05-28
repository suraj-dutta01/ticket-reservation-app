package org.jsp.reservationapi.exceptions;

@SuppressWarnings("serial")
public class AdminVerificationFailedException extends RuntimeException{
public AdminVerificationFailedException(String message) {
	super(message);
}
}
