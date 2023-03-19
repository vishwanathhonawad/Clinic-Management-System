package com.demo.spring.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.dto.CredentialsDTO;
import com.demo.spring.entity.Credentials;
import com.demo.spring.exception.InvalidPasswordException;
import com.demo.spring.exception.UserExistsException;
import com.demo.spring.exception.UserNotExistsException;
import com.demo.spring.service.CredentialsService;
import com.demo.spring.util.Message;

import io.micrometer.core.annotation.Timed;

@RestController
public class CredentialsController {

	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	@Autowired
	CredentialsService credentialsService;

	/**
	 * this method will check for credentials
	 * 
	 * @param credentialsDTO
	 * @return
	 * @throws UserNotExistsException
	 */
	@PostMapping(path = "/find", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "request.find.user")
	public ResponseEntity<Message> finduser(@RequestBody CredentialsDTO credentialsDTO) throws UserNotExistsException {
		Credentials credentials = new Credentials(credentialsDTO.getUserName(), credentialsDTO.getPassword());
		logger.info("This find user method had call to credentials service");
		return ResponseEntity.ok(credentialsService.findUser(credentials));
	}

	/**
	 * this method will update the password
	 * 
	 * @param credentialsDTO
	 * @return
	 * @throws UserNotExistsException
	 * @throws InvalidPasswordException 
	 */
	@PatchMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "request.update.password")
	public ResponseEntity<Message> updatePassword(@RequestBody CredentialsDTO credentialsDTO)
			throws UserNotExistsException, InvalidPasswordException {
		Credentials credentials = new Credentials(credentialsDTO.getUserName(), credentialsDTO.getPassword());
		logger.info("This update password method had call to credentials service");
		return ResponseEntity.ok(credentialsService.updateUser(credentials));
	}

	/**
	 * this method will create new user
	 * 
	 * @param credentialsDTO
	 * @return
	 * @throws UserExistsException
	 * @throws InvalidPasswordException
	 */
	@PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "request.save.user")
	public ResponseEntity<Message> createUser(@RequestBody CredentialsDTO credentialsDTO)
			throws UserExistsException, InvalidPasswordException {
		Credentials credentials = new Credentials(credentialsDTO.getUserName(), credentialsDTO.getPassword());
		logger.info("This create user method had call to credentials service");
		return ResponseEntity.ok(credentialsService.createUser(credentials));
	}

}