package com.roomautomation.dao;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Repository;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.roomautomation.pojo.User;
import com.roomautomation.service.*;

import com.datastax.driver.core.*;

@Repository
public class UserDao {

	public final Logger logger = Logger.getLogger(UserDao.class.getName());
	User user;
	Cluster cluster;
	Session session;
	PreparedStatement statement;
	BoundStatement boundStatement;
	PasswordEncryptionService service = new PasswordEncryptionService();

	// method for authenticating user
	public User authenticateUser(String userName, String password) {
		user=new User();
		String pass = null;
		int userid = 0;
		String firstName = null;
		String lastName = null;
		String role = null;
		String name=userName;
		
		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		session = cluster.connect("roomautomation");

		statement = session.prepare("SELECT * FROM users WHERE username=? ALLOW FILTERING");
		boundStatement = new BoundStatement(statement);

		// create the bound statement and initialise it with your prepared
		// statement
		ResultSet results = session.execute(boundStatement.bind(userName));
		
		for (Row row : results) {
			pass = row.getString("password");
			firstName = row.getString("firstname");
			lastName = row.getString("lastname");
			role = row.getString("role");
			userid = row.getInt("userid");
			
		}

		if (pass.equals(password)) {
			user.setUsername(name);
			user.setPassword(password);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setRole(role);
			user.setUserId(userid);

		} else {
		}
		return user;
	}

	public int findMaxId() {
		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		session = cluster.connect("roomautomation");

		ResultSet results = session.execute("select MAX(userid) from users");
        if (results.isExhausted()) {
              return 1;
        }
        else
        {
               int i=0;
        
               for(Row row :results)
               {
                     i=row.getInt(0);
               }
               return (i+1);
        }


	}

	public String addUser(User user2) {
		System.out.println("in dao " + user2.toString());
		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		session = cluster.connect("roomautomation");

		PreparedStatement statement = session
				.prepare("INSERT INTO users(userid,username,firstname,lastname,password,role) VALUES (?,?,?,?,?,?)");
		BoundStatement boundStatement = new BoundStatement(statement);

		ResultSet results = session.execute(boundStatement.bind(user2.getUserId(), user2.getUserName(),
				user2.getFirstName(), user2.getLastName(), user2.getPassword(), user2.getRole()));
		for (Row row : results) {
			if (row.getInt(0) < 0)
				return "error";
		}
		session.close();
		return "success";

	}

	public String updateUser(String firstName, String lastName, String userName, int userId,String role) {
		System.out.println("in dao  update method" + firstName + lastName + userName + userId + role);
		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		session = cluster.connect("roomautomation");
		
		PreparedStatement statement = session
				.prepare("update users set firstname=?,lastname=?,role=? where username=? and userid=?");
		BoundStatement boundStatement = new BoundStatement(statement);
		
		
		ResultSet results = session.execute(boundStatement.bind(firstName,lastName,role,userName,userId));
		if(results!=null){
		return "success";
		}
		else
			return "fail";

	}

	public String deleteUser(String userName, int userId) {
		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		session = cluster.connect("roomautomation");
		
		PreparedStatement statement = session
				.prepare("delete from users  where username=? and userid=?");
		BoundStatement boundStatement = new BoundStatement(statement);
		
		
		ResultSet results = session.execute(boundStatement.bind(userName,userId));
		if(results!=null){
		return "success";
		}
		else
			return "fail";

	}

	public List<User> showAllUsers()
	{
		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		session = cluster.connect("roomautomation");
		
		
		List<User> list=new ArrayList<>();
	
		ResultSet results = session.execute("select * from users");
		for (Row row : results) {
			
			User user=new User();
			user.setUserId(row.getInt("userid"));
			user.setLastName(row.getString("lastname"));
			user.setUserName(row.getString("username"));
			user.setRole(row.getString("role"));
			user.setPassword(row.getString("password"));
			user.setFirstName(row.getString("firstname"));
			list.add(user);
		}
		
		return list;
	}

}