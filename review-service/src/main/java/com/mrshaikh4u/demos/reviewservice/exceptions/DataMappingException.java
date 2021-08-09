package com.mrshaikh4u.demos.reviewservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Failed during data mapping")
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public class DataMappingException extends RuntimeException {

	private String errorMessage;
	private Throwable exception;
	/**
	 * Unique ID for Serialized object
	 */
	private static final long serialVersionUID = -8790211652911971729L;

	public DataMappingException(String errorMessage) {
		super(errorMessage);
	}

}
