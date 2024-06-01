package org.jsp.reservationapi.exceptions;

import java.io.ObjectInputStream.GetField;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsp.reservationapi.dto.ResponseStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@RestControllerAdvice
public class ReservationExceptionHandler {
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
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> UserNotFoundExceptionHandler(UserNotFoundException exception){
		ResponseStructure<String> structure=new ResponseStructure<>();
		structure.setMessage("User Not Found");
		structure.setData(exception.getMessage());
		structure.setStatusCode(HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(structure);
	}
	@ExceptionHandler(UserVerificationFailedException.class)
	public ResponseEntity<ResponseStructure<String>> UserVerificationFailedExceptionHandler(UserVerificationFailedException exception){
		ResponseStructure<String> structure=new ResponseStructure<>();
		structure.setMessage("User Not Found");
		structure.setData(exception.getMessage());
		structure.setStatusCode(HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(structure);
	}
	@ExceptionHandler(BusNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> BusNotFoundExceptionHandler(BusNotFoundException exception){
		ResponseStructure<String> structure=new ResponseStructure<>();
		structure.setMessage("Bus Not Found");
		structure.setData(exception.getMessage());
		structure.setStatusCode(HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(structure);
	}
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationException(MethodArgumentNotValidException exception){
		Map<String,String> errors=new HashMap<>();
		List<ObjectError> objectErrors=exception.getBindingResult().getAllErrors();
		for(ObjectError objectError:objectErrors) {
			String fieldName=((FieldError)objectError).getField();
			String errorMessage=objectError.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		}
		return errors;
	}

}
