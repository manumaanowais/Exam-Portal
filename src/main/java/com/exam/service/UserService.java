package com.exam.service;

import java.util.Set;

import com.exam.model.User;
import com.exam.model.UserRole;

public interface UserService {
	// Create User
	public User createUser(User user, Set<UserRole> userRoles) throws Exception;

	// Delete User by userid
	public void deleteUser(Long userId);

	// Get User by username
	public User getUser(String username);
	
	//Update User
	public void updateUser(User user, Set<UserRole> userRoles) throws Exception;
	
	//Get User by email
	public User getByEmail(String email);
	//Encryption
	public String encrypt(String password) throws Exception;
	
	//Decryption
	public String decrypt(String password) throws Exception;
}
