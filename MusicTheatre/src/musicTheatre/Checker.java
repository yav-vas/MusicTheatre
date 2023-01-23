package musicTheatre;

import java.util.*;

public class Checker extends User {
	
	private String realName;
	
	public Checker(String username, String password, String realName) {
		super(username, password);
		
		setRealName(realName);
	}
	
	private void setRealName(String realName) {
		if (realName == null || realName.length() == 0)
			throw new IllegalArgumentException("The real name cannot be null or empty");
		
		for (int i = 0; i < realName.length(); i++)
			if (!(Character.isLetter(realName.charAt(i)) || Character.isWhitespace(realName.charAt(i))))
				throw new IllegalArgumentException("The real name must contain only letters");
		
		this.realName = realName;
	}

	public static User registerUser(Scanner in) {
		System.out.println("Let's start with the registration of a ticket checker");
		System.out.println("A ticket checker will be able to:");
		System.out.println("\t1. Check tickets");
		
		do {			
			System.out.println("Now it is time to choose a username and a password");
			
			System.out.println("Username (max 10 characters, only letters): ");
			String username = in.nextLine();
			
			System.out.println("Password (min " + MINIMUM_PASSWORD_LENGTH + 
					" characters, max " + MAXIMUM_PASSWORD_LENGTH + " characters, only letters and digits, at least "
					+ MINIMUM_DIGITS_COUNT + " digits) - in an external window");
			String password = User.inputPassword();
			
			System.out.println("Name (only letters and spaces): ");
			String realName = in.nextLine();
			
			try {
				User checker = new Checker(username, password, realName);
				return checker;
			} catch (IllegalArgumentException ex) {
				System.out.println(ex.getMessage() + "Try again!");
			}
		} while (true);
	}

	@Override
	public void welcomeUser(Scanner in) {
		final int MIN_OPTION = 1;
		final int MAX_OPTION = 2;
		
		System.out.println("A ticket checker with the username " + getUsername() + " has logged in");
		
		do {
			System.out.println("Choose an option from the following menu:");
			System.out.println("\t1. Check ticket");
			System.out.println("\t2. Logout");
			
			int option = 0;
			
			boolean completed = false;
			do {
				try {
					option = Integer.parseInt(in.nextLine());
					if (option < MIN_OPTION || option > MAX_OPTION) 
						throw new IllegalArgumentException("The entered number must be between " + MIN_OPTION + " and " + MAX_OPTION);
					completed = true;
				} catch (NumberFormatException ex) {
					System.out.println("The input must be a number! Try again!");
				} catch (IllegalArgumentException ex) {
					System.out.println(ex.getMessage() + " Try again!");
				}
			} while (!completed);
			
			switch (option) {
				case 1:	
					checkTicket(in);
					break;
				case 2:
					return;
			}
		} while(true);
	}

	private void checkTicket(Scanner in) {
		System.out.println("Enter a ticket number to check for: ");
		
		do {
			try {
				int number = Integer.parseInt(in.nextLine());
				Ticket ticket = Ticket.ticketWithNumber(number);
				System.out.println(ticket.check(realName).toString());
				return;
			} catch (NumberFormatException ex) {
				System.out.println("The ticket number must be a *number*! Try again!");
			} catch (IllegalArgumentException ex) {
				System.out.println(ex.getMessage());
			}
		} while(true);
	}
}
