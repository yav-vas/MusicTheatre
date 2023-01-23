package musicTheatre;

public class Seat {

	private Hall hall;
	private Performance performance;
	private int row;
	private int seatOnRow;
	private SeatClass seatClass;
	private Ticket ticket;
	
	public Seat() {
		
	}
	
	public Seat(int row, int seatOnRow, SeatClass seatClass, Performance performance, Hall hall) {
		this.row = row;
		this.seatOnRow = seatOnRow;
		this.seatClass = seatClass;
		this.performance = performance;
		this.hall = hall;
	}
	
	public int getRow() {
		return row;
	}
	
	public String getOddOrEven() {
		if (getActualSeatOnRow() % 2 == 0)
			return "even";
		else
			return "odd";
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
	
	private int getActualRow() {
		return Seat.getActualRow(row, hall);
	}
	
	public static int getActualRow(int row, Hall hall) {
		int rows = hall.getRows();
		
		return rows - row;
	}
	
	private int getActualSeatOnRow() {
		return Seat.getActualSeatOnRow(seatOnRow, hall);
	}
	
	public static int getActualSeatOnRow(int seatOnRow, Hall hall) {
		int seatsOnRow = hall.getSeatsPerRow();
		
		int middle = seatsOnRow / 2;
		
		if (seatOnRow <= middle) // odd
			return 1 + seatOnRow * 2;
		else // even
			return (seatsOnRow - seatOnRow) * 2;
	}
	
	public static int getArrayRow(int actualRow, Hall hall) {
		int rows = hall.getSeatsPerRow();
		
		return rows - actualRow;
	}
	
	public static int getArraySeatOnRow(int seatOnRow, Hall hall) {
		int seatsOnRow = hall.getSeatsPerRow();
		
		if (seatOnRow % 2 == 0) // even
			return seatsOnRow - (seatOnRow / 2);
		else // odd
			return (seatOnRow - 1) / 2;
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
