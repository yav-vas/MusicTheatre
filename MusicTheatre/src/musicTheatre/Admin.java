package musicTheatre;

import java.util.*;
import javax.swing.*;

public class Admin extends User {
	
	private static final int MINIMUM_PASSWORD_LENGTH = 10;
	
	public Admin(String username, String password) {
		super(username, password);
	}
	
	@Override
	protected void setPassword(String password) {
		if (password.length() < MINIMUM_PASSWORD_LENGTH) {
			throw new IllegalArgumentException("Password must contain at least " + MINIMUM_PASSWORD_LENGTH + " characters!");
		}
		
		super.setPassword(password);
	}
	
	public static User registerUser(Scanner in) {
		System.out.println("Let's start with the registration of an admin");
		System.out.println("An admin will be able to:");
		System.out.println("\t1. Create new halls");
		System.out.println("\t2. Create new performances.");
		System.out.println("\t3. Cancel performances.");
		
		do {			
			System.out.println("Now it is time to choose a username and a password:");
			
			System.out.println("Username (max 10 characters, only letters): ");
			
			String username = in.nextLine();
			
			System.out.println("Password (min " + MINIMUM_PASSWORD_LENGTH + 
					" characters, max " + MAXIMUM_PASSWORD_LENGTH + " characters, only letters and digits, at least "
					+ MINIMUM_DIGITS_COUNT + " digits) - in an external window");
			String password = User.inputPassword();
			
			try {
				User admin = new Admin(username, password);
				return admin;
			} catch (IllegalArgumentException ex) {
				System.out.println(ex.getMessage());
				System.out.println("Try again!");
			}
		} while (true);
	}
	
	@Override
	public void welcomeUser(Scanner in) {
		final int MIN_OPTION = 1;
		final int MAX_OPTION = 4;
		
		System.out.println("An admin with the username " + this.getUsername() + " has logged in");
		
		do {
			System.out.println("Choose an option from the following menu:");
			System.out.println("\t1. Create new hall");
			System.out.println("\t2. Create new performance");
			System.out.println("\t3. Cancel an existing performance");
			System.out.println("\t4. Logout");
			
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
					createHall(in);
					break;
				case 2:
					createPerformance(in);
					break;
				case 3:
					cancelPerformance(in);
					break;
				case 4:
					return;
			}
		} while(true);
	}

	// ask all the necessary questions to create a new Hall object
	private void createHall(Scanner in) {
		System.out.println("This Hall creation wizard will help you create a new hall for performances:");
		
		// TODO: Save the data somehow so that it is not used in case of failed attempt to create a hall (exception thrown)
		
		System.out.println("Choose a name for the new hall (e.g. Main hall):");
		String name = in.nextLine();
		
		System.out.println("Choose how many rows does the hall have:");
		boolean completed = false;
		int rows = 0;
		do {
			try {
				rows = Integer.parseInt(in.nextLine()); // TODO: all input of numbers must be followed by in.next() in the catch block
									 // TODO: implement for all number inputs
				completed = true; // executed only if the operation is successful
			} catch (NumberFormatException ex) {
				System.out.println("The input must be a number!");
			}
		} while(!completed); // a do-while loop is needed as a user may get the input wrong multiple times
		
		System.out.println("Choose how many seats does each row in the hall have:");
		completed = false;
		int seatsPerRow = 0;
		do {
			try {
				seatsPerRow = Integer.parseInt(in.nextLine());
				completed = true; // executed only if the operation is successful
			} catch (NumberFormatException ex) {
				System.out.println("The input must be a number!");
			}
		} while(!completed); // a do-while loop is needed as a user may get the input wrong multiple times
		
		try {
			Hall hall = new Hall(name, rows, seatsPerRow);
			System.out.println("The hall was created successfully!");
			System.out.println(hall);
		} catch (IllegalArgumentException ex) {
			System.out.println(ex.getMessage());
			System.out.println("Try again!");
		}
	}
	
	private void createPerformance(Scanner in) {
		System.out.println("This Performance creation wizard will help you create a new performance:");
		
		System.out.println(Hall.printHalls());
		System.out.println("Enter the number of the desired hall: ");
		
		Hall chosenHall = null;
		
		boolean completed = false;
		do {
			try {
				int choice = Integer.parseInt(in.nextLine());
				chosenHall = Hall.halls.get(choice);
				completed = true;
			} catch (NumberFormatException ex) {
				System.out.println("The input must be a number! Try again!"); // TODO: decide if try again is on a new line or not
			} catch (IndexOutOfBoundsException ex) {
				System.out.println("The entered number was not among the allowed for selection! Try again!");
			}
		} while(!completed);
		
		System.out.println("Enter the title of the performance: ");
		String title = in.nextLine();
		
		System.out.println("Enter the name of the composer of the piece: ");
		String composer = in.nextLine();
		
		System.out.println("Enter the date of the performance: "); // TODO: support for real Date class, not just meaningless String
		String date = in.nextLine();
		
		System.out.println("Enter the start time of the performance in the format HH:MM : "); // TODO: check if the format is really entered
		String startTime = in.nextLine();
		
		System.out.println("Enter the lenght of the performance in the format HH:MM "); // TODO: check if the format is really entered
		String length = in.nextLine();
		
		System.out.println("Now it is time to choose the prices for the performance and to assign seat category to each seat.");
		System.out.println("There are " + SeatClass.values().length + " seat categories - " + Arrays.toString(SeatClass.values()) + " and each seat category has a unique price.\n"
				+ "Seat categories are assigned on a row by row base. This means that for row of a specific number all of the seats are of the same category.\n"
				+ "For each row you will be required to enter a number indicating your choice.\n");
		
		// TODO: two performance types - morning and evening; per day only one performance per type per hall
		
		int[] prices = new int[SeatClass.values().length]; // TODO: performance constructor that uses this array, not stupid variables
		
		for (int i = 0; i < SeatClass.values().length; i++) {
			completed = false;
			do {
				try {
					System.out.println("Enter the price for " + SeatClass.values()[i]);
					prices[i] = Integer.parseInt(in.nextLine());
					completed = true;
				} catch (NumberFormatException ex) {
					System.out.println("Input must be a number! Try again!");
				}
			} while(!completed);
		}
		
		System.out.println("Now for each row - back to front you will be required to enter a number, indicating the seat category.");
		System.out.println("The options are the following:\n" + SeatClass.showOptions());
		SeatClass[] categories = new SeatClass[chosenHall.getRows()];
		for (int i = 0; i < chosenHall.getRows(); i++) {
			completed = false;
			do {
				try {
					System.out.println("Enter the price category for row: " + i);
					categories[i] = SeatClass.values()[Integer.parseInt(in.nextLine())];
					completed = true;
				} catch (NumberFormatException ex) {
					System.out.println("Input must be a number! Try again!");
				} catch (IndexOutOfBoundsException ex) {
					System.out.println(ex.getMessage());
				}
			} while(!completed);
		}
		
		try {
			Performance performance = new Performance(title, composer, date, startTime, length, chosenHall, prices, categories);
			System.out.println("The performance was successfully created!");
			System.out.println(performance);
			System.out.println("If you find any errors, then cancel the performance immediately and try again!");
		} catch (IllegalArgumentException ex) {
			System.out.println(ex.getMessage() + "Try again!");
		}
		
	}
	
	private void cancelPerformance(Scanner in) {
		System.out.println("This Perfomance cancelation wizard will guide you through the procedure of performance cancelation.");
		System.out.println("Before you continue it is important to understand the consequences of such an action.");
		System.out.println("\t1. The action is irreversalbe!");
		System.out.println("\t2. All the tickets for the performance will be marked as unvalid and"
							+ " each customer will be required to change its performance!");
		System.out.println("\t3. Email will be sent to all customers with online tickets!");
		
		System.out.println("Are you sure you want to continue? Y/N");
		boolean completed = false;
		do {
			String answer = in.nextLine();
			if (answer.equals("Y")) completed = true;
			else if (answer.equals("N")) System.out.println("The operation was aborted by the user!");
			else System.out.println("Invalid answer! Try again!");
		} while(!completed);
		
		try {
			Performance performanceToCancel = Performance.selectPerformance(in);
			Ticket.cancelTicketsForPerformance(performanceToCancel); // TODO: add print info about how many tickets are now invalid
			Performance.cancelPerformance(performanceToCancel);
		} catch (IllegalArgumentException ex) {
			System.out.println(ex.getMessage());
		} catch (RuntimeException ex) {
			System.out.println(ex.getMessage());
		}
		
	}
	
}
