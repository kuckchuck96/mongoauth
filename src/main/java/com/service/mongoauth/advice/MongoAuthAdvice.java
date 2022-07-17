package com.service.mongoauth.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.service.mongoauth.dto.ErrorDto;
import com.service.mongoauth.exception.UserAlreadyExistsException;

@RestControllerAdvice
public class MongoAuthAdvice {

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorDto handleFieldErrors(MethodArgumentNotValidException ex) {
		ErrorDto errorDto = new ErrorDto();
		errorDto.setStatus(HttpStatus.BAD_REQUEST);

		Map<String, String> errorMap = new HashMap<>();

		ex.getFieldErrors().forEach(err -> errorMap.put(err.getField(), err.getDefaultMessage()));

		errorDto.setRequired(errorMap);

		return errorDto;
	}

	@ResponseStatus(code = HttpStatus.CONFLICT)
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ErrorDto handleExistingUser(UserAlreadyExistsException ex) {
		ErrorDto errorDto = new ErrorDto();
		errorDto.setStatus(HttpStatus.CONFLICT);
		errorDto.setMessage(ex.getMessage());
		return errorDto;
	}

}
