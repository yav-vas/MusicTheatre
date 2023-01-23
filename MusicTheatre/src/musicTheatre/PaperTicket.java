package musicTheatre;

public class PaperTicket extends Ticket {

	private String issuingCashierName;
	
	public PaperTicket(Performance performance, int row, int seatOnRow, String issuingCashierName) {
		super(performance, row, seatOnRow);
		
		setIssuingCashierName(issuingCashierName);
	}
	
	private void setIssuingCashierName(String issuingCashierName) {
		this.issuingCashierName = issuingCashierName;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append(super.toString());
		result.append("Issued by: " + issuingCashierName + "\n");
		
		return result.toString();
	}
	
}
