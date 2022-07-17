package com.service.mongoauth.config.jwt;

import lombok.Data;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

@Data
public class JwtRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7754708797766071689L;

	@NotBlank(message = "Email is a required attribute.")
	@Email(message = "Email should be valid.")
	private String email;

	@NotBlank(message = "Password is a required attribute.")
	@Length(min = 6, max = 18, message = "Password must be between 6 to 18 characters long.")
	private String password;

}
