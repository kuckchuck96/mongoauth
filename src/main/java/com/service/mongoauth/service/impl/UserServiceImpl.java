package com.service.mongoauth.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.service.mongoauth.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mongodb.MongoException;
import com.service.mongoauth.exception.UserAlreadyExistsException;
import com.service.mongoauth.model.User;
import com.service.mongoauth.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User createUser(User user) throws Exception {
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		if (Optional.ofNullable(userRepository.findByEmail(user.getEmail())).isPresent()) {
			throw new UserAlreadyExistsException(
					String.format("User already exists with %s. Please try using a different email.", user.getEmail()));
		}

		final User newUser = Optional.ofNullable(userRepository.save(user))
				.orElseThrow(() -> new MongoException("Unable to create the user."));

		LOGGER.info(String.format("New user has been created with email: %s.", newUser.getEmail()));

		return newUser;
	}

	@Override
	public User getUser(String email, String password) throws Exception {
		final User user = Optional.ofNullable(userRepository.findByEmailAndPassword(email, password))
				.orElseThrow(() -> new Exception(String.format("Unable to find the user with email: %s.", email)));
		return user;
	}

	@Override
	public void removeUnverifiedOldUsers() throws Exception {
		Optional.ofNullable(userRepository.findByisVerifiedAndUpdatedAt(LocalDateTime.now().minusDays(30L)))
				.ifPresent(users -> {
					LOGGER.info(String.format("Total users removed: %d.", users.size()));
					userRepository.deleteAll(users);
				});
	}

	@Override
	public List<User> getUsersByName(String keyword) throws Exception {
		if (StringUtils.isNotBlank(keyword)) {
			List<User> searchedList = userRepository.findUsersByRegexpName(keyword);
			LOGGER.info(String.format("Total searched appearences for \"%s\": %d", keyword, searchedList.size()));
			return searchedList;
		}
		return new ArrayList<>();
	}

}
