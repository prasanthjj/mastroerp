package com.erp.mastro.exception.handler;

import com.erp.mastro.exception.MastroDataException;
import com.erp.mastro.exception.MastroServiceException;
import com.erp.mastro.exception.model.MastroError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class MastroExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(MastroDataException.class)
	public ResponseEntity<MastroError> mapException(MastroDataException exception) {
		MastroError error = new MastroError(HttpStatus.NO_CONTENT.value(), exception.getMessage());
		return new ResponseEntity<>(error, HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler(MastroServiceException.class)
	public ResponseEntity<MastroError> mapException(MastroServiceException exception) {
		MastroError error = new MastroError(HttpStatus.UNPROCESSABLE_ENTITY.value(), exception.getMessage());
		return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
	}

}
