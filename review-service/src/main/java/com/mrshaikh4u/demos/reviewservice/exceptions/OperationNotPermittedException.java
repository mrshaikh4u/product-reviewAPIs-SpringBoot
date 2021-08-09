package com.mrshaikh4u.demos.reviewservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED, reason = "Requested not permitted for this user")
@Data
@AllArgsConstructor
@ApiModel(description = "Exception to be thrown when requesting user is not permitted for the operation")
@EqualsAndHashCode(callSuper = false)
public class OperationNotPermittedException extends RuntimeException {

	private String errorMessage;
	private Throwable exception;
	/**
	 * Unique ID for Serialized object
	 */
	private static final long serialVersionUID = -8790211652911971729L;

	public OperationNotPermittedException(String errorMessage) {
		super(errorMessage);
	}

}
