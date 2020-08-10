package com.sarfo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountExistException extends RuntimeException {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(AccountExistException.class);


	public AccountExistException() {
		logger.info("Account with email already exist");
	}
	
	public AccountExistException(String message) {
		super(message);
		logger.info(message);
	}	
}
