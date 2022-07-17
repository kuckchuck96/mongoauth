package com.service.mongoauth.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.mongoauth.dto.UserDto;
import com.service.mongoauth.model.User;
import com.service.mongoauth.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/createUser")
	public UserDto createUser(@RequestBody @Valid User user) throws Exception {
		return modelMapper.map(userService.createUser(user), UserDto.class);
	}

}
