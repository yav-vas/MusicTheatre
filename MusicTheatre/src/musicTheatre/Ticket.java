package musicTheatre;

import java.util.*;

public abstract class Ticket {
	
	// TODO: add maximum for the tickets count
	private static final int MAX_TICKETS = 100000;
	
	private static Random random = new Random();
	
	private static ArrayList<Ticket> tickets = new ArrayList<Ticket>();
	private static int currentTicketNumber = random.nextInt(Integer.MAX_VALUE - MAX_TICKETS - 1); // some random number so that it looks more believable
	private static int ticketsCount = 0;
	
	private Performance performance;
	private int price;
	private int number;
	private boolean checked;
	private boolean valid;
	private String checker;
	private Seat seat;
	
	public Ticket() {
		
	}
	
	public Ticket(Performance performance, int price, Seat seat) throws IllegalArgumentException {
		if (ticketsCount >= MAX_TICKETS) 
			throw new IllegalArgumentException("Cannot create more tickets, the limit of " + MAX_TICKETS + " has been reached!");
		this.performance = performance;
		setPrice(price);
		number = currentTicketNumber;
		checked = false;
		valid = true;
		setSeat(seat);
		currentTicketNumber++;
		ticketsCount++;
		getTickets().add(this);
	}

	public Performance getPerformance() {
		return performance;
	}
	
	public Seat getSeat() {
		return seat;
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(int price) throws IllegalArgumentException{
		if (price <= 0) throw new IllegalArgumentException("Price cannot be zero or negative");
		
		this.price = price;
	}
	
	public boolean getValid() {
		return valid;
	}
	
	public int getTicketNumber() {
		return this.number;
	}
	
	public void setSeat(Seat seat) {
		if (seat.isTaken())
			throw new IllegalArgumentException("This seat is taken");
		
		this.seat = seat;
	}
	
	private void setValid(boolean valid) {
		this.valid = valid;
	}
	
	public static ArrayList<Ticket> getTickets() {
		return tickets;
	}

	public static void setTickets(ArrayList<Ticket> tickets) {
		Ticket.tickets = tickets;
	}

	public abstract String printTicket();
	
	public void cancelTicket() {
		setValid(false);
	}
	
	public static void cancelTicketsForPerformance(Performance performance) {
		for (Ticket ticket : getTickets())
			if (ticket.performance == performance) // TODO: better use equals method
				ticket.cancelTicket();
	}
	
	public static boolean ticketNumberExists(int numberToCheck) {
		for (Ticket ticket : getTickets())
			if (ticket.number == numberToCheck && ticket.valid == true)
				return true;
		
		return false;
	}
}
