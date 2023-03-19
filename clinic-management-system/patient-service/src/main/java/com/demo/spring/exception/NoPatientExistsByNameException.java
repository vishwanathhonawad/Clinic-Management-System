package com.demo.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.demo.spring.util.Message;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@ControllerAdvice
public class NoPatientExistsByNameException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ExceptionHandler
	public ResponseEntity<Message> handleNoPatientExistsByNameException(NoPatientExistsByNameException ex) {
		return ResponseEntity.ok(new Message("Patient Not found.."));
	}

}
