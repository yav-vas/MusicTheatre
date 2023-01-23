package musicTheatre;

import java.util.*;

public class Main {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		// test cases
		
//		int[] prices = {15, 20, 30};
//		SeatClass[] seatClasses = {SeatClass.CHEAP, SeatClass.MIDDLE, SeatClass.EXPENSIVE};
//		
//		Performance performance = new Performance("Two ears", "Iavor", "23 May", "15:30", "00:30", 
//				new Hall("Main hall", 3, 3), prices, seatClasses);
//		
//		User cashier = new Cashier("Iavor", "buybuy1234");
//		User checker = new Checker("inspector", "checkcheck1234", "Checker Name");
//		
//		cashier.welcomeUser(in);
//		checker.welcomeUser(in);
		
		System.out.println("Hello, welcome to the new advanced ticket managemant system for the Music Theatre!");
		
		User admin = Admin.registerUser(in);
		admin.welcomeUser(in);
		
		// the application will prompt for a login until option 3 is chosen
		// after a successful login the methods for each user are to be called
		do {
			final int MIN_OPTION = 1;
			final int MAX_OPTION = 3;
			System.out.println("Select one of the following options:");
			System.out.println("\t1. Login");
			System.out.println("\t2. Register a new user");
			System.out.println("\t3. Quit the system. This will terminate the program and all data will be lost");
			
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
			
			try {
				switch(option) {
					case 1: {
						User user = User.loginUser(in);
						user.welcomeUser(in);
						break;
					}
					case 2: {
						User user = User.registerUser(in);
						user.welcomeUser(in);
						break;
					}
					case 3: {
						if(quitTheSystem(in)) {
							in.close();
							return;
						}
					}
				}
			} catch(Exception ex) {
				System.out.println("An error occured! Logging you out! Try again! The error is: ");
				System.out.println(ex.getMessage());
			}
		} while (true);
	}
	
	public static boolean quitTheSystem(Scanner in) {
		System.out.println("Are you sure you want to quit? (Y/N)");
		
		String input = in.nextLine();
		
		do {
			switch (input) {
				case "Y":
					return true;
				case "N":
					return false;
				default:
					System.out.println("Invalid input, try again!");
			}
		} while(true);
	}

}
