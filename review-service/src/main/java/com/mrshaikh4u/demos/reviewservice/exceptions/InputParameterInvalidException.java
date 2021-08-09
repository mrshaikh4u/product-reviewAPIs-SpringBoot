package com.mrshaikh4u.demos.reviewservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid input passed please refer to API documentation for correct input details")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class InputParameterInvalidException extends RuntimeException {

	private String errorMessage;
	private Throwable exception;
	private String fieldName ;
	private String fieldValue;
	private String objectName;
	/**
	 * Unique ID for Serialized object
	 */
	private static final long serialVersionUID = -8790211652911971729L;

	public InputParameterInvalidException(String errorMessage) {
		super(errorMessage);
	}
	public InputParameterInvalidException(String errorMessage,String fieldName) {
		super(errorMessage);
		this.fieldName = fieldName;
	}

	public InputParameterInvalidException(String errorMessage, Throwable ex) {
		super(errorMessage);
		this.errorMessage = errorMessage;
		this.exception = ex;
	}

	public InputParameterInvalidException(String errorMessage, String fieldName, String fieldValue) {
		super(errorMessage);
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public InputParameterInvalidException(String objectName, String errorMessage, String fieldName, String fieldValue) {
		super(errorMessage);
		this.objectName = objectName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
}
