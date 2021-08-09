package com.mrshaikh4u.demos.productservice.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This class is used to store validation error with more details about errors
 * 
 * @author Rahil
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ApiValidationError {
	private String object;
	private String field;
	private Object rejectedValue;
	private String message;

	ApiValidationError(String object, String message) {
		this.object = object;
		this.message = message;
	}
}
