package com.service.mongoauth.config.jwt;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7754708797766071689L;
	private String email;
	private String password;

}
