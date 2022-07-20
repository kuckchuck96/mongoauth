package com.service.mongoauth.service;

import java.util.List;

import com.service.mongoauth.model.User;

public interface UserService {

	public User createUser(User user) throws Exception;

	public User getUser(String email, String password) throws Exception;

	public void removeUnverifiedOldUsers() throws Exception;
	
	public List<User> getUsersByName(String keyword) throws Exception;

}
