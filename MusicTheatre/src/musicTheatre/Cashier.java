package musicTheatre;

import java.util.*;

public class Cashier extends User {
	
	public Cashier(String username, String password) {
		super(username, password);
	}

	public static User registerUser(Scanner in) {
		System.out.println("Let's start with the registration of an admin");
		System.out.println("An admin will be able to:");
		System.out.println("\t1. Sell tickets");
		System.out.println("\t2. Change tickets");
		
		do {			
			System.out.println("Now it is time to choose a username and a password:");
			
			System.out.println("Username (max 10 characters, only letters): ");
			
			String username = in.nextLine();
			
			System.out.println("Password (min " + MINIMUM_PASSWORD_LENGTH + 
					" characters, max " + MAXIMUM_PASSWORD_LENGTH + " characters, only letters and digits, at least "
					+ MINIMUM_DIGITS_COUNT + " digits) - in an external window");
			String password = User.inputPassword();
			
			try {
				User cashier = new Cashier(username, password);
				return cashier;
			} catch (IllegalArgumentException ex) {
				System.out.println(ex.getMessage());
				System.out.println("Try again!");
			}
		} while (true);
	}

	@Override
	public void welcomeUser(Scanner in) {
		final int MIN_OPTION = 1;
		final int MAX_OPTION = 2;
		
		System.out.println("A cashier with the username " + this.getUsername() + " has logged in");
		
		do {
			System.out.println("Choose an option from the following menu:");
			System.out.println("\t1. Sell ticket");
			System.out.println("\t2. Change ticket");
			System.out.println("\t3. Logout");
			
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
					sellTicket(in);
					break;
				case 2:
					changeTicket(in);
					break;
				case 3:
					return;
			}
		} while(true);
	}
	
	public void sellTicket(Scanner in) {
		Performance selectedPerformance;
		try {
			selectedPerformance = Performance.selectPerformance(in);
		} catch (IllegalArgumentException ex){
			System.out.println(ex.getMessage());
			return;
		}
		
		selectedPerformance.printSeats();
		
		System.out.println("If 0 is printed, then the seat is taken!");
		
		Seat[][] performanceSeats = selectedPerformance.getSeats();
		
		boolean completed = false;
		
		do {
			try {
				System.out.println("Select a row: ");
				int selectedRow = Integer.parseInt(in.nextLine()); // TODO: add try-catch
				
				System.out.println("Select a seat: ");
				int selectedSeat = Integer.parseInt(in.nextLine()); // TODO: add try-catch
				
				Ticket ticket = new PaperTicket(selectedPerformance, performanceSeats[selectedRow][selectedSeat].getSeatPrice(),
						performanceSeats[selectedRow][selectedSeat], this.getUsername()); // TODO: implement each cashier different username
				performanceSeats[selectedRow][selectedSeat].setTicket(ticket);
				System.out.println(ticket.printTicket());
				completed = true;
			} catch (NumberFormatException ex) {
				System.out.println("The input must be a number! Try again!");
			} catch (IllegalArgumentException ex) {
				System.out.println(ex.getMessage());
			}
		} while (!completed);
	}
	
	// if change has to be given - don't give any; 
	// if the new ticket costs more => ask for the money
	public void changeTicket(Scanner in) { 
		
	}
	
	// give 90% of the ticket price back
	public void cancelTicket(Scanner in) {
		
	}
}
