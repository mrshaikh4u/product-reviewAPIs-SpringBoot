package com.mrshaikh4u.demos.reviewservice.service;

import com.mrshaikh4u.demos.reviewservice.exceptions.InputParameterInvalidException;

/**
 * Any class implementing this interface assume to be validatable entity
 * 
 * @author Rahil
 *
 */
public interface Validatable {
	public boolean validate() throws InputParameterInvalidException;
}
