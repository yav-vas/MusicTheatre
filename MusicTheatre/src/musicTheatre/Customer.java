package musicTheatre;

import java.util.*;

public class Customer extends User {
	
	private static final int MAX_EMAIL_LENGTH = 256;

	private String email;
	private String realName;
	
	public Customer(String username, String password, String email, String realName) {
		super(username, password);
		
		setEmail(email);
		setRealName(realName);
	}
	
	public void setEmail(String email) {
		if (email == null || email.length() == 0)
			throw new IllegalArgumentException("The email cannot be null or empty");
		
		if (email.length() > MAX_EMAIL_LENGTH)
			throw new IllegalArgumentException("The email length cannot be more than 256 characters");
		
		this.email = email;
	}
	
	public void setRealName(String realName) {
		if (realName == null || realName.length() == 0)
			throw new IllegalArgumentException("The real name cannot be null or empty");
		
		for (int i = 0; i < realName.length(); i++)
			if (!(Character.isLetter(realName.charAt(i)) || Character.isWhitespace(realName.charAt(i))))
				throw new IllegalArgumentException("The real name must contain only letters");
	}
	
	public void buyTicket(Scanner in) {
		Performance selectedPerformance = Performance.selectPerformance(in);
		
		selectedPerformance.printSeats();
		
		Seat[][] performanceSeats = selectedPerformance.getSeats();
		
		boolean completed = false;
		
		do {
			try {
				System.out.println("Select a row: ");
				int selectedRow = Integer.parseInt(in.nextLine());
				
				System.out.println("Select a seat: ");
				int selectedSeat = Integer.parseInt(in.nextLine());
				
				// TODO: make the ticket be OnlineTicket
				Ticket ticket = new OnlineTicket(selectedPerformance, performanceSeats[selectedRow][selectedSeat].getSeatPrice(),
						performanceSeats[selectedRow][selectedSeat], email, getUsername(), realName);
				performanceSeats[selectedRow][selectedSeat].setTicket(ticket);
				System.out.println(ticket.printTicket());
				completed = true;
			} catch (IllegalArgumentException ex) {
				System.out.println(ex.getMessage() + " Try again!");
			} catch (ArrayIndexOutOfBoundsException ex) {
				System.out.println("The seat was not among the available for selection! Try again!");
			}
		} while (!completed);
	}
	
	public void showTickets() {
		System.out.println(OnlineTicket.getTicketsByBuyerUsername(getUsername()));
	}
	
	public static User registerUser(Scanner in) {
		System.out.println("Let's start with the registration of a customer");
		System.out.println("A customer will be able to:");
		System.out.println("\t1. Buy tickets");
		System.out.println("\t2. Modify/Cancel tickets");
		System.out.println("\t3. See all his tickets");
		
		do {			
			System.out.println("Now it is time to choose a username and a password");
			
			System.out.println("Username (max 10 characters, only letters): ");
			String username = in.nextLine();
			
			System.out.println("Password (min " + MINIMUM_PASSWORD_LENGTH + 
					" characters, max " + MAXIMUM_PASSWORD_LENGTH + " characters, only letters and digits, at least "
					+ MINIMUM_DIGITS_COUNT + " digits) - in an external window");
			String password = User.inputPassword();
			
			System.out.println("Email: ");
			String email = in.nextLine();
			
			System.out.println("Name (only letters and spaces): ");
			String realName = in.nextLine();
			
			try {
				User customer = new Customer(username, password, email, realName);
				return customer;
			} catch (IllegalArgumentException ex) {
				System.out.println(ex.getMessage() + "Try again!");
			}
		} while (true);
	}

	@Override
	public void welcomeUser(Scanner in) {
		final int MIN_OPTION = 1;
		final int MAX_OPTION = 5;
		
		System.out.println("A customer with the username " + this.getUsername() + " has logged in");
		
		do {
			System.out.println("Choose an option from the following menu:");
			System.out.println("\t1. Buy ticket");
			System.out.println("\t2. Modify ticket");
			System.out.println("\t3. Cancel ticket");
			System.out.println("\t4. See all purchased tickets");
			System.out.println("\t5. Logout");
			
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
					buyTicket(in);
					break;
				case 2:
					// TODO: modify ticket
					break;
				case 3:
					// TODO: cancel ticket
					break;
				case 4:
					showTickets();
					return;
				case 5:
					return;
			}
		} while(true);
	}
	
}
