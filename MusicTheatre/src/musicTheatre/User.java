package musicTheatre;

import java.util.*;

public abstract class User {
	
	private static ArrayList<User> users = new ArrayList<User>();
	
	protected String username;
	private String password;
	
	protected User() {
		throw new IllegalArgumentException("A user must be initialized with an username and a password!");
	}
	
	protected User(String username, String password) {
		setUsername(username);
		setPassword(password);
		users.add(this);
	}
	
	protected String getUsername() {
		return this.username;
	}
	
	private void setUsername(String username) 
			throws IllegalArgumentException {
		if (username == null || username.length() == 0) {
			throw new IllegalArgumentException("The username cannot be null or empty!");
		}
		
		if (username.length() > 10) {
			throw new IllegalArgumentException("The username length cannot be more than 10!");
		}
		
		// check if an username contains only letters
		for (int i = 0; i < username.length(); i++) {
			if (!Character.isLetter(username.charAt(i))) {
				throw new IllegalArgumentException("The username must contain only letters");
			}
		}
		
		this.username = username;
	}
	
	private void setPassword(String password) 
			throws IllegalArgumentException {
		if (password == null || password.length() == 0) {
			throw new IllegalArgumentException("The password cannot be null or empty!");
		}
		
		if (password.length() < 5) {
			throw new IllegalArgumentException("The password must contain at least 5 characters!");
		}
		
		if (password.length() > 20) {
			throw new IllegalArgumentException("The password must contain no more than 20 characters!");
		}
		
		// check if password contains at least 2 digits and only letters and digits
		int digitsCount = 0;
		
		for (int i = 0; i < password.length(); i++) {
			if (!Character.isLetterOrDigit(password.charAt(i))) {
				throw new IllegalArgumentException("The password must contain only letters and digits");
			}
			
			if (Character.isDigit(password.charAt(i))) {
				digitsCount++;
			}
		}
		
		if (digitsCount < 2) throw new IllegalArgumentException("The password must contain at least 2 digits");
		
		this.password = password;
	}
	
	// a private method for checking the password in order not to reveal it
	private boolean checkPassword(String attemptedPassword) {
		return password.equals(attemptedPassword);
	}
	
	public static User loginUser(String username, String password) {
		for (User user : users) {
			if (user.getUsername() == username && user.checkPassword(password)) {
				return user;
			}
		}
		
		return null;
	}
	
}