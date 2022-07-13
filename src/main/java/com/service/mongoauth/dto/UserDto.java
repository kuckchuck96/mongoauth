package com.service.mongoauth.dto;

import lombok.Data;

@Data
public class UserDto {

	private String id;
	private String email;
	private String name;
	private String[] roles;
	private boolean isVerified;

}
