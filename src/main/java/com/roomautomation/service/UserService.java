package com.roomautomation.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roomautomation.dao.UserDao;
import com.roomautomation.pojo.User;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	public User aunticationUser(String userName, String password) {

		System.out.println("in service before method call");
		User user=userDao.authenticateUser(userName,password);
		System.out.println("in service after method call");
		return user;
	}
	public int findMaxId() {
		return userDao.findMaxId();
	}
	public String addUser(User user) {

		return userDao.addUser(user);
		
	}
	public String updateUser(String firstName, String lastName, String userName,int userId,String role) {
		return userDao.updateUser(firstName,lastName,userName,userId,role);
		
		
	}
	public String deleteUser(String userName, int userId) {
		return userDao.deleteUser(userName,userId);
	}
	public List<User> showAllUsers() {
		return userDao.showAllUsers();
	}

	
	
	
	
}
