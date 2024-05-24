package org.jsp.reservationapi.exceptions;

import org.jsp.reservationapi.dto.ResponseStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
public class AdminExceptionHandler extends ResponseEntityExceptionHandler{
	@ExceptionHandler(AdminNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> AdminNotFoundExceptionHandler(AdminNotFoundException exception){
		ResponseStructure<String> structure=new ResponseStructure<>();
		structure.setMessage("Admin Not Found");
		structure.setData(exception.getMessage());
		structure.setStatusCode(HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(structure);
	}
	
	@ExceptionHandler(AdminVerificationFailedException.class)
	public ResponseEntity<ResponseStructure<String>> AdminVerificationExceptionHandler(AdminVerificationFailedException exception){
		ResponseStructure<String> structure=new ResponseStructure<>();
		structure.setMessage("Admin Verification Failed");
		structure.setData(exception.getMessage());
		structure.setStatusCode(HttpStatus.FORBIDDEN.value());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(structure);
	}

}
