package com.demo.spring.service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.entity.Credentials;
import com.demo.spring.exception.InvalidPasswordException;
import com.demo.spring.exception.UserExistsException;
import com.demo.spring.exception.UserNotExistsException;
import com.demo.spring.repository.CredentialsRepository;
import com.demo.spring.util.Message;

@Service
public class CredentialsService {

	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	@Autowired
	CredentialsRepository credentialsRepository;

	static final String REG = "^(?=.*\\d)(?=\\S+$)(?=.*[@#$%^&+=])(?=.*[a-z])(?=.*[A-Z]).{8,10}$";
	static final Pattern PATTERN = Pattern.compile(REG);

	public Message findUser(Credentials credentials) throws UserNotExistsException {
		List<Credentials> listCred = credentialsRepository.findUser(credentials.getUserName(),
				credentials.getPassword());
		if (listCred.isEmpty()) {
			logger.error("Exception : User Not Exists Exception Thrown");
			throw new UserNotExistsException();
		} else {
			logger.info("User is Present With Given Credentials");
			return new Message("User Found");
		}
	}

	public Message updateUser(Credentials credentials) throws UserNotExistsException, InvalidPasswordException {
		Optional<Credentials> credOp = credentialsRepository.findById(credentials.getUserName());
		if (credOp.isEmpty()) {
			logger.error("Exception : User Not Exists Exception Thrown");
			throw new UserNotExistsException();
		} else {
			if (PATTERN.matcher(credentials.getPassword()).matches()) {
				credentialsRepository.updateUser(credentials.getUserName(), credentials.getPassword());
				logger.info("User Credentials updated ");
				return new Message("User Updated");
			} else {
				logger.error("Exception : Invalid Password Exception Thrown");
				throw new InvalidPasswordException();
			}
		}
	}

	public Message createUser(Credentials credentials) throws UserExistsException, InvalidPasswordException {
		Optional<Credentials> credOp = credentialsRepository.findById(credentials.getUserName());
		if (credOp.isPresent()) {
			logger.error("Exception : User Exists Exception Thrown");
			throw new UserExistsException();
		} else {
			if (PATTERN.matcher(credentials.getPassword()).matches()) {
				credentialsRepository.save(credentials);
				logger.info("User Saved");
				return new Message("User Saved");
			} else {
				logger.error("Exception : Invalid Password Exception Thrown");
				throw new InvalidPasswordException();
			}
		}
	}
}