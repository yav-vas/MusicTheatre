package musicTheatre;

import java.util.*;

public class Customer extends User {
	
	private static final int MAX_EMAIL_LENGTH = 256;

	private String email;
	private String realName;
	private ArrayList<Ticket> ticketsOfUser;
	
	public Customer(String username, String password, String email, String realName) {
		super(username, password);
		
		setEmail(email);
		setRealName(realName);
		ticketsOfUser = new ArrayList<Ticket>();
	}
	
	private void setEmail(String email) {
		if (email == null || email.length() == 0)
			throw new IllegalArgumentException("The email cannot be null or empty");
		
		if (email.length() > MAX_EMAIL_LENGTH)
			throw new IllegalArgumentException("The email length cannot be more than 256 characters");
		
		this.email = email;
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
				System.out.println(ex.getMessage() + " Try again!");
			}
		} while (true);
	}

	@Override
	public void welcomeUser(Scanner in) {
		final int MIN_OPTION = 1;
		final int MAX_OPTION = 4;
		
		System.out.println("A customer with the username " + this.getUsername() + " has logged in");
		
		do {
			System.out.println("Choose an option from the following menu:");
			System.out.println("\t1. Buy ticket");
			System.out.println("\t2. Cancel ticket");
			System.out.println("\t3. See all purchased tickets");
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
					buyTicket(in);
					break;
				case 2:
					cancelTicket(in);
					break;
				case 3:
					showTickets();
					break;
				case 4:
					return;
			}
		} while(true);
	}

	private void buyTicket(Scanner in) {
		Performance selectedPerformance;
		
		try {
			selectedPerformance = Performance.selectPerformance(in);
		} catch (IllegalArgumentException ex) {
			System.out.println(ex.getMessage());
			return;
		}
		
		System.out.println(selectedPerformance.printSeats());
		
		boolean completed = false;
		
		do {
			try {
				System.out.println("Select a row: ");
				int selectedRow = Integer.parseInt(in.nextLine());
				
				System.out.println("Select a seat: ");
				int selectedSeat = Integer.parseInt(in.nextLine());
				
				Ticket ticket = new OnlineTicket(selectedPerformance, selectedRow, selectedSeat, email, realName);
				ticket.getSeat().setTicket(ticket);
				ticketsOfUser.add(ticket);
				System.out.println(ticket);
				completed = true;
			} catch (IllegalArgumentException ex) {
				System.out.println(ex.getMessage() + " Try again!");
			} catch (ArrayIndexOutOfBoundsException ex) {
				System.out.println("The seat was not among the available for selection! Try again!");
			}
		} while (!completed);
	}
	
	private void cancelTicket(Scanner in) {
		System.out.println("Select a ticket you want to cancel by entering its number: ");
		showTickets();
		
		try {
			int ticketNumber = Integer.parseInt(in.nextLine());
			for (Ticket ticket : ticketsOfUser)
				if (ticket.getTicketNumber() == ticketNumber) {
					ticket.cancelTicket();
					System.out.println("Ticket canceled.");
					return;
				}
			throw new IllegalArgumentException("Ticket was not found! Try again!");		
		} catch (NumberFormatException ex) {
			System.out.println("The input must be a number! Try again!");
		} catch (IllegalArgumentException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	private void showTickets() {
		boolean areThereTickets = false;
		
		for (Ticket ticket : ticketsOfUser)
			if (ticket.getValid() == true) {
				areThereTickets = true;
				System.out.println(ticket);
			}
		
		if (!areThereTickets)
			System.out.println("No tickets bought! Go and buy some!");
	}
	
}
