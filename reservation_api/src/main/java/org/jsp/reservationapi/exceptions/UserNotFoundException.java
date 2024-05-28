package org.jsp.reservationapi.exceptions;

@SuppressWarnings("serial")
public class UserNotFoundException extends RuntimeException{
public UserNotFoundException(String message) {
	super(message);
}
}
