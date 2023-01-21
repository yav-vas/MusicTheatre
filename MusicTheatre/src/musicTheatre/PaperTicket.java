package musicTheatre;

public class PaperTicket extends Ticket {

	private String issuingCashierName;
	
	public PaperTicket(Performance performance, int price, Seat seat, String issuingCashierName) {
		super(performance, price, seat);
		
		setIssuingCashierName(issuingCashierName);
	}
	
	public void setIssuingCashierName(String issuingCashierName) {
		this.issuingCashierName = issuingCashierName;
	}
	
	@Override
	public String printTicket() {
		StringBuilder ticket = new StringBuilder();
		
		ticket.append("This is ticket with number: " + getTicketNumber() + "\n");
		ticket.append("Performance: " + getPerformance().getTitle() + "\n");
		ticket.append("Price: " + getPrice() + "\n");
		ticket.append(getSeat().toString());
		ticket.append("Issued by: " + issuingCashierName + "\n");
		// TODO: Add more info about the ticket
		
		return ticket.toString();
	}
	
}
