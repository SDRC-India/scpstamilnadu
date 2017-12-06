package org.sdrc.scpstamilnadu.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;



public class DataEntryDateExceededException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3695227677490991112L;

	public DataEntryDateExceededException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DataEntryDateExceededException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public DataEntryDateExceededException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public DataEntryDateExceededException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public DataEntryDateExceededException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	
	
}
