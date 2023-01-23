package musicTheatre;

import java.util.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public abstract class User implements Unique {
	
	private static ArrayList<User> users = new ArrayList<User>();
	
	// username requirements constants
	protected static final int MAXIMUM_USERNAME_LENGTH = 10;
	
	// password requirements constants
	protected static final int MINIMUM_PASSWORD_LENGTH = 5;
	protected static final int MAXIMUM_PASSWORD_LENGTH = 20;
	protected static final int MINIMUM_DIGITS_COUNT = 2;
	
	private String username;
	private String password;
	private Date registrationDate;
	
	public User() {
		
	}
	
	public User(String username, String password) {
		setUsername(username);
		setPassword(password);
		registrationDate = new Date(); // the user is created now
		isUnique(this);
		users.add(this);
	}
	
	public String getUsername() {
		return this.username;
	}
	
	private String getPassword() {
		return this.password;
	}
	
	public Date getRegistrationDate() {
		return this.registrationDate;
	}
	
	protected void setUsername(String username) 
			throws IllegalArgumentException {
		if (username == null || username.length() == 0) {
			throw new IllegalArgumentException("The username cannot be null or empty!");
		}
		
		if (username.length() > MAXIMUM_USERNAME_LENGTH) {
			throw new IllegalArgumentException("The username length cannot be more than " + MAXIMUM_USERNAME_LENGTH + "!");
		}
		
		// check if an username contains only letters
		for (int i = 0; i < username.length(); i++) {
			if (!Character.isLetter(username.charAt(i))) {
				throw new IllegalArgumentException("The username must contain only letters");
			}
		}
		
		this.username = username;
	}
	
	protected void setPassword(String password) 
			throws IllegalArgumentException {
		if (password == null || password.length() == 0) {
			throw new IllegalArgumentException("The password cannot be null or empty!");
		}
		
		if (password.length() < MINIMUM_PASSWORD_LENGTH) {
			throw new IllegalArgumentException("The password must contain at least " + 
												MINIMUM_PASSWORD_LENGTH + " characters!");
		}
		
		if (password.length() > MAXIMUM_PASSWORD_LENGTH)
			throw new IllegalArgumentException("The password must contain no more than "
												+ MAXIMUM_PASSWORD_LENGTH + " characters!");
		
		// check if password contains at least MINIMUM_DIGITS_COUNT digits and only letters and digits
		int digitsCount = 0;
		
		for (int i = 0; i < password.length(); i++) {
			if (!Character.isLetterOrDigit(password.charAt(i)))
				throw new IllegalArgumentException("The password must contain only letters and digits");
			
			if (Character.isDigit(password.charAt(i)))
				digitsCount++;
		}
		
		if (digitsCount < MINIMUM_DIGITS_COUNT) 
			throw new IllegalArgumentException("The password must contain at least "
												+ MINIMUM_DIGITS_COUNT + " digits");
		
		this.password = password;
	}
	
	// a static method to register a user called from Main and
	// guiding the user throughout the procedure of a registration
	public static User registerUser(Scanner in) {
		final int MIN_OPTION = 1;
		final int MAX_OPTION = 4;
		
		System.out.println("Select a type of user you want to register:");
		System.out.println("\t1. Admin");
		System.out.println("\t2. Cashier");
		System.out.println("\t3. Ticket inspector");
		System.out.println("\t4. Customer");
		
		int option = 0;
		
		boolean completed = false;
		do {
			try {
				option = Integer.parseInt(in.nextLine());
				
				if (option < MIN_OPTION || option > MAX_OPTION)
					throw new IllegalArgumentException("The chosen option is not among the available for selection!");
				
				completed = true;
			} catch (NumberFormatException ex) {
				System.out.println("The input must be a number! Try again!");
			} catch (IllegalArgumentException ex) {
				System.out.println(ex.getMessage());
			}
		} while(!completed);
		
		switch(option) {
			case 1:
				return Admin.registerUser(in);
			case 2:
				return Cashier.registerUser(in);
			case 3:
				return Checker.registerUser(in);
			default: // case 4:
				return Customer.registerUser(in);
		}
	}
	
	// an abstract method to present the User with its options
	public abstract void welcomeUser(Scanner in);
	
	// static method to display an external window for password input
	public static String inputPassword() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Enter a password:");
		JPasswordField passwordField = new JPasswordField(10);
		panel.add(label);
		panel.add(passwordField);
		JOptionPane.showMessageDialog(null, panel, "Password", JOptionPane.PLAIN_MESSAGE);
		
		char[] passwordArray = passwordField.getPassword();
		String password = new String(passwordArray);
		
		return password;
	}
	
	// basic user login, nothing specific to the User type
	public static User loginUser(Scanner in) {
		System.out.println("Welcome, please enter an username and a password to start.");
		
		int tries = 0;
		final int MAX_TRIES = 0;
		do {
			tries++;
			System.out.println("Username: ");
			String username = in.nextLine();
			System.out.println("Password (in an external window): ");
			String password = User.inputPassword();
			
			for (User user : users)
				if (user.getUsername().equals(username) && user.getPassword().equals(password))
					return user;
			
			System.out.println("A user with this username and password was not found in the system!");
			
			if (tries == MAX_TRIES)
				System.out.println(MAX_TRIES + " unsuccessful tries to login were attempted! Now returning to the main menu");
		} while(true);
	}
	
	// check if a user with the same username already exists
	public static boolean isUnique(User userToCheck) {
		String username = userToCheck.getUsername();
		for (User user : users)
			if (user.getUsername().equals(username))
				throw new IllegalArgumentException("A user with this username already exists!");
		
		return true;
	}
	
}