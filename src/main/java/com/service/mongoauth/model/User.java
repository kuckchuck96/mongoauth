package com.service.mongoauth.model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.ToString;

@Document
@Data
@ToString
public class User {

	@Id
	private String id;

	@NotBlank(message = "Email is a required attribute.")
	private String email;

	@NotBlank(message = "Name is a required attribute.")
	private String name;

	@NotBlank(message = "Password is a required attribute.")
	private String password;
	private String[] roles = { "USER" };
	private boolean isVerified = false;
	private LocalDateTime createdAt = LocalDateTime.now();
	private LocalDateTime updatedAt = LocalDateTime.now();

}
