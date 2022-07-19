package com.todo.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/*
 * Custom Exception Handler
 */
//@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	// @ExceptionHandler(value = { ResourceNotFoundException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {

		String bodyOfResponse = "Error: This is application specific custom error";

		ErrorBody errB = new ErrorBody(HttpStatus.NOT_FOUND);
		errB.setMessage(bodyOfResponse);

		return handleExceptionInternal(ex, errB, new HttpHeaders(), HttpStatus.NOT_FOUND, request);

	}

	class ErrorBody {
		int code;
		String message;
		String status;

		public ErrorBody(HttpStatus httpStatus) {
			this.status = httpStatus.name();
			this.code = httpStatus.value();
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public int getCode() {
			return code;
		}

		public void setErrorCode(int code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

	}

}
