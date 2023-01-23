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
		final int MAX_OPTION = 3;
		
		System.out.println("A cashier with the username " + this.getUsername() + " has logged in");
		
		do {
			System.out.println("Choose an option from the following menu:");
			System.out.println("\t1. Sell ticket");
			System.out.println("\t2. Cancel ticket");
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
					cancelTicket(in);
					break;
				case 3:
					return;
			}
		} while(true);
	}
	
	private void sellTicket(Scanner in) {
		Performance selectedPerformance;
		
		try {
			selectedPerformance = Performance.selectPerformance(in);
		} catch (IllegalArgumentException ex) {
			System.out.println(ex.getMessage());
			return;
		}
		
		System.out.println(selectedPerformance.printSeats());
		
		do {
			try {
				System.out.println("Select a row: ");
				int selectedRow = Integer.parseInt(in.nextLine());
				
				System.out.println("Select a seat: ");
				int selectedSeat = Integer.parseInt(in.nextLine());
				
				Ticket ticket = new PaperTicket(selectedPerformance,
						selectedRow, selectedSeat, this.getUsername());
				ticket.getSeat().setTicket(ticket);
				System.out.println(ticket);
				return;
			} catch (NumberFormatException ex) {
				System.out.println("The input must be a number! Try again!");
			} catch (IllegalArgumentException ex) {
				System.out.println(ex.getMessage());
			}
		} while (true);
	}
	
	private void cancelTicket(Scanner in) {
		final double REFUND_RATE = 0.7; // 70% refund
		boolean completed = false;
		do {
			System.out.println("Are you sure you want to continue? Y/N");
			String input = in.nextLine();
			
			switch (input) {
				case "N":
					System.out.println("The procedure has been aborted by the user!");
					return;
				case "Y":
					completed = true;
					break;
				default:
					System.out.println("Invalid input! Try again!");
						
			}
		} while(!completed);
		
		do {
			try {
				System.out.println("Enter ticket number: ");
				int number = Integer.parseInt(in.nextLine());
				
				Ticket ticket = Ticket.ticketWithNumber(number);
				int price = ticket.getPrice();
				System.out.println((int)(price * REFUND_RATE) + " BGN refund is returnted!");
				ticket.cancelTicket();
				return;
			} catch (NumberFormatException ex) {
				System.out.println("The ticket number must be a *number*! Try again!");
			} catch (IllegalArgumentException ex) {
				System.out.println(ex.getMessage());
			}
		} while(true);
	}
}
