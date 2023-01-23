package musicTheatre;

import java.util.*;

public abstract class Ticket {
	
	private static final int MAX_TICKETS = 100000;
	
	private static Random random = new Random();
	
	private static ArrayList<Ticket> tickets = new ArrayList<Ticket>();
	private static int currentTicketNumber = random.nextInt(Integer.MAX_VALUE - MAX_TICKETS - 1); // some random number so that it looks more believable
	private static int ticketsCount = 0;
	
	private Performance performance;
	private int number;
	private boolean checked;
	private boolean valid;
	private String checker;
	private Seat seat;
	
	public Ticket() {
		
	}
	
	public Ticket(Performance performance, int row, int seatOnRow) throws IllegalArgumentException {
		if (ticketsCount >= MAX_TICKETS) 
			throw new IllegalArgumentException("Cannot create more tickets, the limit of " + MAX_TICKETS + " has been reached!");
		this.performance = performance;
		number = currentTicketNumber;
		checked = false;
		valid = true;
		setSeat(row, seatOnRow);
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
	
	public boolean getValid() {
		return valid;
	}
	
	public int getTicketNumber() {
		return this.number;
	}
	
	public String getChecker() {
		return this.checker;
	}
	
	private void setSeat(int row, int seatOnRow) {
		int arrayRow = Seat.getArrayRow(row, performance.getHall());
		int arraySeatOnRow = Seat.getArraySeatOnRow(seatOnRow, performance.getHall());
		
		Seat seat = performance.getSeats()[arrayRow][arraySeatOnRow];
		
		if (seat.isTaken())
			throw new IllegalArgumentException("This seat is taken");
		
		this.seat = seat;
	}
	
	private void setValid(boolean valid) {
		this.valid = valid;
	}
	
	public int getPrice() {
		return seat.getSeatPrice();
	}
	
	public static ArrayList<Ticket> getTickets() {
		return tickets;
	}
	
	public void cancelTicket() throws IllegalArgumentException {
		if (checker != null)
			throw new IllegalArgumentException("The ticket cannot be canceled! It has already been checked by " + checker);
			
		setValid(false);
	}
	
	public static int cancelTicketsForPerformance(Performance performance) {
		int canceledTicketsCount = 0;
		for (Ticket ticket : getTickets())
			if (ticket.performance.equals(performance)) {
				ticket.cancelTicket();
				canceledTicketsCount++;
			}
		return canceledTicketsCount;
	}
	
	public static Ticket ticketWithNumber(int numberToFind) throws IllegalArgumentException {
		for (Ticket ticket : getTickets())
			if (ticket.number == numberToFind) {
				if (ticket.valid == false)
					throw new IllegalArgumentException("The ticket is no longer valid!");
				if (ticket.checked == true)
					throw new IllegalArgumentException("Ticket has already been checked!");
				return ticket;
			}
		
		throw new IllegalArgumentException("No ticket was found!");
	}
	
	public String check(String checkerName) {
		setValid(false);
		this.checker = checkerName;
		this.checked = true;
		
		StringBuilder result = new StringBuilder();
		
		result.append("The ticket with number: " + this.number + " has been checked and is valid.\n");
		result.append("The seat is on the " + getSeat().getOddOrEven() + " side of the hall.");
		
		return result.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		if (valid == false) result.append("This ticket is INVALID!\n");
		result.append("Ticket number: \n" + getTicketNumber() + '\n');
		result.append("\tPerformance: \n" + getPerformance().toString() + '\n');
		result.append("\tSeat: \n" + getSeat().toString() + '\n');
		result.append("Price: " + getPrice() + " BGN\n");
		
		return result.toString();
	}
}
