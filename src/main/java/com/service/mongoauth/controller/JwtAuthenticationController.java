package com.service.mongoauth.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.service.mongoauth.config.jwt.JwtRequest;
import com.service.mongoauth.config.jwt.JwtResponse;
import com.service.mongoauth.config.jwt.JwtTokenUtil;
import com.service.mongoauth.config.jwt.JwtUserDetailsService;
import com.service.mongoauth.dto.UserDto;
import com.service.mongoauth.service.UserService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	@Value("${verify.email}")
	private boolean verifyEmail;

	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody @Valid JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());

		final String token = jwtTokenUtil.generateToken(userDetails);

		final UserDto userDto = modelMapper
				.map(userService.getUser(userDetails.getUsername(), userDetails.getPassword()), UserDto.class);

		if (!verifyEmail) {
			return ResponseEntity.ok(new JwtResponse(userDto, token));
		}

		return userDto.isVerified() && verifyEmail ? ResponseEntity.ok(new JwtResponse(userDto, token))
				: ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Kindly verify your email.");
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

}
