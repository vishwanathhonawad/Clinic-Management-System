package com.demo.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.demo.spring.util.Message;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@ControllerAdvice
public class DoctorNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ExceptionHandler
	public ResponseEntity<Message> handleDoctorNotFoundException(DoctorNotFoundException ex) {
		return ResponseEntity.ok(new Message("Doctor not found"));
		
	}

	

}
