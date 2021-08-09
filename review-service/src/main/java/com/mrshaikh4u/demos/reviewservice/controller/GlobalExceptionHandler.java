package com.mrshaikh4u.demos.reviewservice.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.mrshaikh4u.demos.reviewservice.error.ApiValidationError;
import com.mrshaikh4u.demos.reviewservice.error.AppError;
import com.mrshaikh4u.demos.reviewservice.exceptions.DataMappingException;
import com.mrshaikh4u.demos.reviewservice.exceptions.DataNotFoundException;
import com.mrshaikh4u.demos.reviewservice.exceptions.DataNotSavedException;
import com.mrshaikh4u.demos.reviewservice.exceptions.InputParameterInvalidException;
import com.mrshaikh4u.demos.reviewservice.exceptions.OperationNotPermittedException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * This class handles application exceptions globally. each method is intend to
 * provide handling for specific exception and provides meaningful errorCode and
 * errorMessage and returns HTTP status code
 * 
 * @author Rahil
 *
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	

	@ExceptionHandler(InputParameterInvalidException.class)
	protected ResponseEntity<Object> handleInputParameterInvalidException(InputParameterInvalidException ex) {
		AppError error = new AppError(HttpStatus.BAD_REQUEST);
		error.setMessage(ex.getMessage());
		error.setSubErrors(Stream
		                         .of(new ApiValidationError(ex.getObjectName(), ex.getFieldName(), ex.getFieldValue(),
		                                 ex.getErrorMessage()))
		                         .collect(Collectors.toList()));
		if (ex.getException() != null)
			error.setDebugMessage(ex.getException()
			                        .getLocalizedMessage());
		return buildResponseEntity(error);
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleGenericExceptions(Exception ex) {
		AppError error = new AppError(HttpStatus.INTERNAL_SERVER_ERROR);
		error.setMessage("Internal error");
		error.setDebugMessage(ex.getLocalizedMessage());
		return buildResponseEntity(error);
	}

	/**
	 * Handles DataNotFoundException. 
	 *
	 * @param ex the DataNotFoundException
	 * @return the AppError object
	 */
	@ExceptionHandler(DataNotFoundException.class)
	protected ResponseEntity<Object> handleDataNotFound(DataNotFoundException ex) {
		AppError error = new AppError(NOT_FOUND);
		error.setMessage(ex.getMessage());
		if (ex.getException() != null)
			error.setDebugMessage(ex.getException()
			                        .getLocalizedMessage());
		return buildResponseEntity(error);
	}

	/**
	 * Handles OperationNotPermittedException. 
	 * @param ex the OperationNotPermittedException
	 * @return the AppError object
	 */
	@ExceptionHandler(OperationNotPermittedException.class)
	protected ResponseEntity<Object> handleOperationNotPermitted(OperationNotPermittedException ex) {
		AppError error = new AppError(HttpStatus.METHOD_NOT_ALLOWED);
		error.setMessage(ex.getMessage());
		if (ex.getException() != null)
			error.setDebugMessage(ex.getException()
			                        .getLocalizedMessage());
		return buildResponseEntity(error);
	}


	@ExceptionHandler(DataMappingException.class)
	protected ResponseEntity<Object> handleDataMapping(DataMappingException ex) {
		AppError error = new AppError(HttpStatus.INTERNAL_SERVER_ERROR);
		error.setMessage(ex.getMessage());
		if (ex.getException() != null)
			error.setDebugMessage(ex.getException()
			                        .getLocalizedMessage());
		return buildResponseEntity(error);
	}
	/**
	 * Handle NoHandlerFoundException.
	 *
	 * @param ex
	 * @param headers
	 * @param status
	 * @param request
	 * @return
	 */
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
	        HttpStatus status, WebRequest request) {
		AppError error = new AppError(BAD_REQUEST);
		error.setMessage(
		        String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));
		error.setDebugMessage(ex.getMessage());
		return buildResponseEntity(error);
	}

	/**
	 * Handle javax.persistence.EntityNotFoundException
	 */
	@ExceptionHandler(javax.persistence.EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(javax.persistence.EntityNotFoundException ex) {
		return buildResponseEntity(new AppError(HttpStatus.NOT_FOUND, ex));
	}

	private ResponseEntity<Object> buildResponseEntity(AppError error) {
		return new ResponseEntity<>(error, error.getStatus());
	}

	@ExceptionHandler(DataNotSavedException.class)
	protected ResponseEntity<Object> handleDataNotSavedException(DataNotSavedException ex) {
		AppError error = new AppError(HttpStatus.INTERNAL_SERVER_ERROR);
		error.setMessage(ex.getMessage());
		if (ex.getException() != null)
			error.setDebugMessage(ex.getException()
			                        .getLocalizedMessage());
		return buildResponseEntity(error);
	}

	@ExceptionHandler(BadCredentialsException.class)
	protected ResponseEntity<Object> handleDataNotSavedException(BadCredentialsException ex) {
		AppError error = new AppError(HttpStatus.BAD_REQUEST);
		error.setMessage(ex.getMessage());
		error.setDebugMessage(ex.getLocalizedMessage());
		return buildResponseEntity(error);
	}

}
