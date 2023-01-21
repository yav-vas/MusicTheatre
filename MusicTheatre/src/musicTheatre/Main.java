package musicTheatre;

import java.util.*;

public class Main {

	public static void main(String[] args) {
		
		try {
			QRCodeGenerator.base();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		Scanner in = new Scanner(System.in);
		
		User admin = new Admin("alabala", "adminadmin1234");
		admin.welcomeUser(in);
		
		User customer = new Customer("iavor", "buybuy123456", "yav.vasilev@gmail.com", "Iavor Vassilev");
		customer.welcomeUser(in);
		
		System.out.println("Hello, welcome to the new advanced ticket managemant system for the Music Theatre!");
		
		// User admin = Admin.registerUser(in);
		
		// admin.welcomeUser(in);
		
		// the application will prompt for a login forever
		// after a successful login the methods for each user are to be called
		do {
			final int MIN_OPTION = 1;
			final int MAX_OPTION = 3;
			// TODO: print menu with options
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
