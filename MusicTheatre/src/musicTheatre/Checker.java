package musicTheatre;

import java.util.*;

public class Checker extends User {

	public static void checkTicket(Scanner in) {
		System.out.println("Enter a ticket number: ");
		int number = Integer.parseInt(in.next());
		System.out.println(Ticket.ticketNumberExists(number));
		// TODO: make better output
		// TODO: add checker and checked on this ticket
	}

	@Override
	public void welcomeUser(Scanner in) {
		// TODO Auto-generated method stub
		
	}
}
