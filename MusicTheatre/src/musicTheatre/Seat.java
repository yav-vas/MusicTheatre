package musicTheatre;

public class Seat {

	private Hall hall;
	private Performance performance;
	private int row;
	private int seatOnRow;
	private SeatClass seatClass;
	private Ticket ticket;
	private int price;
	
	public Seat() {
		
	}
	
	public Seat(int row, int seatOnRow, SeatClass seatClass, Performance performance, Hall hall) {
		this.row = row;
		this.seatOnRow = seatOnRow;
		this.seatClass = seatClass;
		this.performance = performance;
		this.hall = hall;
		this.price = getSeatPrice();
	}
	
	public SeatClass getSeatClass() {
		return seatClass;
	}
	
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	
	public Hall getHall() {
		return hall;
	}
	
	public boolean isTaken() {
		return ticket != null && ticket.getValid(); // ticket == null => not taken; ticket != null => taken
	}
	
	public int getSeatPrice() {
		return performance.getPrice(seatClass);
	}
	
	// TODO: getActualRow and getActualSeatOnRow -> make them private
	// TODO: make the tickets use these methods and also when printing the hall
	// TODO: the array-based counts should only be used for internal storage; all displayed this should use the methods
	
	public int getActualRow() {
		return Seat.getActualRow(row, hall);
	}
	
	public static int getActualRow(int row, Hall hall) {
		int rows = hall.getRows();
		
		return rows - row;
	}
	
	public int getActualSeatOnRow() {
		return Seat.getActualSeatOnRow(seatOnRow, hall);
	}
	
	public static int getActualSeatOnRow(int seatOnRow, Hall hall) {
		int seatsOnRow = hall.getSeatsPerRow();
		
		int middle = seatsOnRow / 2;
		
		// odd
		if (seatOnRow <= middle) {
			return 1 + seatOnRow * 2;
		} else { // even
			return (seatsOnRow - seatOnRow) * 2;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append("Hall name: " + hall.getName() + '\n');
		result.append("Row: " + getActualRow() + '\n');
		result.append("Seat: " + getActualSeatOnRow() + '\n');
		
		return result.toString();
	}
	
}
