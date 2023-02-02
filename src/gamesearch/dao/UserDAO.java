package gamesearch.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import gamesearch.core.Game;
import gamesearch.core.User;

import org.jasypt.util.password.StrongPasswordEncryptor;

public class UserDAO {
	

	
	private Connection myConn;

	public UserDAO() throws Exception {
		Properties props = new Properties();
		props.load(new FileInputStream("kursach.properties"));

		String user = props.getProperty("user"); 
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");

		myConn = DriverManager.getConnection(dburl, user, password);

		System.out.println("UserDAO: DB connection successful to: " + dburl);
	}
	
	public List<User> getAllUsers() throws Exception {
		List<User> list = new ArrayList<>();

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from `users`");

			while (myRs.next()) {
				User tempUser = convertRowToUser(myRs);
				list.add(tempUser);
			}

			return list;
		} finally {
			close(myStmt, myRs);
		}
	}

	private User convertRowToUser(ResultSet myRs) throws SQLException {

		User user = new User();

		user.setId(myRs.getInt("id"));
		user.setFirstName(myRs.getString("first name"));
		user.setLastName(myRs.getString("last name"));
		user.setPassword(myRs.getString("password"));
		user.setIs_admin(myRs.getBoolean("is admin"));
		
		return user;

	}

	public User convertNameToUser(String LastFirstName) throws SQLException {
		
		User theUser;

		Statement myStmt = null;
		ResultSet myRs = null;

		String[] arr = LastFirstName.split(" ");
		String LastName = arr[0];
		String FirstName = arr[1];
		
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from `users` where `last name`=\"" + 
			LastName + "\" AND `first name` =\"" + FirstName + "\""); //TODO: prevent from creating several equal users 
			myRs.next();
			theUser = convertRowToUser(myRs);
			return theUser;
		} finally {
			close(myStmt, myRs);
		}
		
	}
	
	private void close(Statement myStmt, ResultSet myRs) throws SQLException {
		try {
			if (myStmt != null)
				myStmt.close();
			if (myRs != null)
				myRs.close();
		} catch (Exception e) {
		}
		;
	}

	public boolean authenticate(User theUser, String plainTextPswd) throws Exception {
		boolean result = false;
		
		
		
		String encryptedPasswordFromDatabase = getEncryptedPassword(theUser.getId());
		StrongPasswordEncryptor pswdEncryptor = new StrongPasswordEncryptor();
		
		result = pswdEncryptor.checkPassword(plainTextPswd, encryptedPasswordFromDatabase);
		
		return result;
	}

	private String getEncryptedPassword(int id) throws Exception {
		String encryptedPassword = null;
		
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			
			myStmt = myConn.prepareStatement("select `password` from `users` where `id` = ?");
			myStmt.setInt(1, id);
			
			myRs = myStmt.executeQuery();
			myRs.next();
			encryptedPassword = myRs.getString(1);
			return encryptedPassword;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
