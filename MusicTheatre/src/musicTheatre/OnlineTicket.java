package musicTheatre;

public class OnlineTicket extends Ticket {

	private String email;
	private String ownersName;
	
	public OnlineTicket(Performance performance, int row, int seatOnRow, String email, String ownersName) {
		super(performance, row, seatOnRow);
		this.email = email;
		this.ownersName = ownersName;
	}
	
	@Override
	public void cancelTicket() {
		super.cancelTicket();
		
		System.out.println("An email is being sent to: " + email);
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append(super.toString());
		result.append("Owner: " + ownersName + '\n');
		
		return result.toString();
	}

}
