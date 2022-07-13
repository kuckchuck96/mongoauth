package com.service.mongoauth.config.jwt;

import java.io.Serializable;

import com.service.mongoauth.dto.UserDto;

import lombok.Data;

@Data
public class JwtResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3221098522422701457L;

	private final UserDto user;
	private final String token;

}
