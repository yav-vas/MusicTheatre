package musicTheatre;

import java.util.ArrayList;

public class OnlineTicket extends Ticket {

	private String email;
	private String buyerUsername;
	private String ownersName;
	
	public OnlineTicket(Performance performance, int price, Seat seat, String email, String buyerUsername, String ownersName) {
		super(performance, price, seat);
		this.email = email;
		this.buyerUsername = buyerUsername;
		this.ownersName = ownersName;
	}
	
	public static String getTicketsByBuyerUsername(String username) {
		ArrayList<Ticket> result = new ArrayList<Ticket>();
		
		for (Ticket ticket : getTickets())
			if (ticket instanceof OnlineTicket)
				if (((OnlineTicket) ticket).buyerUsername.equals(username))
					result.add(ticket);
		
		StringBuilder resultString = new StringBuilder();
		
		for (Ticket ticket : result)
			resultString.append(ticket);
		
		return resultString.toString();
	}
	
	@Override
	public void cancelTicket() {
		super.cancelTicket();
		
		System.out.println("An email is being sent to: " + email);
	}
	
	@Override
	public String printTicket() {
		// TODO: create QR code and print it
		return "The ticket's QR code is available in the file " + super.getTicketNumber() + ".jpg";
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append("Performance: \n" + super.getPerformance() + '\n');
		result.append("\tSeat: \n" + super.getSeat().toString() + '\n');
		result.append("Date: " + super.getPerformance().getDate() + '\n'); // if only you could get date irl with super.getDate() :) jk
		result.append("Start time: " + super.getPerformance().getDate() + '\n');
		result.append("Owner: " + ownersName + '\n');
		
		return result.toString();
	}

}
