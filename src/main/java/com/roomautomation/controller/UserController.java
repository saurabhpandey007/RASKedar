package com.roomautomation.controller;

import java.util.List;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.LogManager;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.roomautomation.pojo.DummyUser;
import com.roomautomation.pojo.User;
import com.roomautomation.service.PasswordEncryptionService;
import com.roomautomation.service.PasswordGeneration;
import com.roomautomation.service.UserService;
import org.apache.log4j.Logger;

@CrossOrigin
@RestController
@RequestMapping("/project")
public class UserController {

	private Logger logger = Logger.getLogger(UserController.class);
	@Autowired
	private UserService userService;

	String role = "invalid";

	@RequestMapping(value = "/authenticateUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE, headers = "Accept=Text/plain")
	public String aunthenticateUser(@RequestBody DummyUser dummyUser) {
		logger.info("call to user controller authentication method");

		User user = userService.aunticationUser(dummyUser.getUserName(), dummyUser.getPassword());
		if (user == null)
			return role;

		role = user.getRole();

		dummyUser.setPassword(null);
		dummyUser.setUserName(user.getUsername());
		return role;

	}

	@RequestMapping(value = "/addUser/{firstName}/{lastName}/{userName}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE, headers = "Accept=text/plain")
	public String addUser(@PathVariable String firstName, @PathVariable String lastName, @PathVariable String userName)
			throws ParseException {
		User user = new User();

		String password = PasswordGeneration.generateRandomPassword();

		user.setUserId(userService.findMaxId());
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setRole("user");
		user.setPassword(password);
		user.setUsername(userName);

		return userService.addUser(user);

	}

	// update user method
	@RequestMapping(value = "/updateUser/{firstName}/{lastName}/{userName}/{userId}/{role}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE, headers = "Accept=text/plain")
	public String updateUser(@PathVariable String firstName, @PathVariable String lastName,
			@PathVariable String userName, @PathVariable int userId, @PathVariable String role) throws ParseException {
		User user = new User();
		String result = userService.updateUser(firstName, lastName, userName, userId, role);
		return result;
	}

	// delete user method
	@RequestMapping(value = "/deleteUser/{userName}/{userId}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE, headers = "Accept=Text/plain")
	public String deleteUser(@PathVariable String userName, @PathVariable int userId) throws ParseException {
		User user = new User();
		String result = userService.deleteUser(userName, userId);
		return result;
	}

	// show users
	@RequestMapping(value = "/showAllUsers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public List<User> showAllUsers() throws ParseException {

		java.util.List<User> result = userService.showAllUsers();
		return result;
	}

}
