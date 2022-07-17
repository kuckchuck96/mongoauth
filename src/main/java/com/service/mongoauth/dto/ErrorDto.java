package com.service.mongoauth.dto;

import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ErrorDto {

	private HttpStatus status;
	private Map<String, String> required;
	private String message;

}
