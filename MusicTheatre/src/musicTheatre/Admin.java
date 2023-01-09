package musicTheatre;

import java.util.*;

public class Admin extends User {
	
	public Admin() {
		super();
	}
	
	public Admin(String username, String password) {
		super(username, password);
	}
	
	public static Admin registerAdmin(Scanner in) {	
		String username, password;
		
		System.out.println("Let's start with the registration of an admin");
		System.out.println("An admin will be able to:");
		System.out.println("\t1. Create new halls");
		System.out.println("\t2. Create new performances.");
		System.out.println("\t3. Cancel/Modify performances.");
		
		boolean completed = false;
		do {			
			System.out.println("Now it is time to choose a username and a password:");
			
			System.out.println("Username (max 10 characters, only letters): ");
			username = in.nextLine();
			
			System.out.println("Password (min 10 characters, max 20 characters, only letters and digits, at least 2 digits): ");
			// TODO: override setPassword method to require a minimum of 10 characters
			// TODO: set the minimums and maximums to be constants, not fixed numbers
			password = in.nextLine();
			
			try {
				Admin admin = new Admin(username, password);
				completed = true;
			} catch (IllegalArgumentException ex) {
				System.out.println(ex.getMessage());
			}
		} while (!completed);
		
		System.out.println("Successfully registered an admin!");
		return new Admin(username, password);
	}

	// ask all the necessary questions to create a new Hall object
	public void createHall(Scanner in) {
		System.out.println("This Hall creation wizard will help you create a new hall for performances:");
		
		boolean completed = false;
		do {
			System.out.println("Do you want to cancel the procedure? (Y/N)");
			String answer = in.nextLine();
			if (answer.equals("Y")) return;
			if (answer.equals("N")) completed = true;
			else System.out.println("Invalid input, try again!");
		} while(!completed);
		
		completed = false;
		do {
			System.out.println("Select a name for the new hall (e.g. Main hall):");
			String name = in.nextLine();
			if (!Hall.checkExistingHall(name)) completed = true;
			else System.out.println("There is already a hall with this name, try again!");
		} while(!completed);
		
	}
	
}
