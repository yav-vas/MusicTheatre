package musicTheatre;

import java.util.*;

public class Performance implements Unique, Comparable<Performance> {

	private static ArrayList<Performance> performances = new ArrayList<Performance>();
	
	private String title;
	private String composer;
	private String date;
	private String startTime;
	private String length;
	private Hall hall;
	private int[] prices;
	private Seat[][] seats;
	
	public Performance() {
		
	}
	
	public Performance(String title, String composer, String date, String startTime,
			String length, Hall hall, int[] prices, SeatClass[] seatClasses) {
		this.title = title;
		this.composer = composer;
		this.date = date;
		setStartTime(startTime);
		setLength(length);
		this.hall = hall;
		setPrices(prices);
		setSeats(seatClasses);
		isUnique(this);
		performances.add(this);
		performances.sort(null);
	}
	
	private void setStartTime(String startTime) {
		validateTime(startTime);
		this.startTime = startTime;
	}
	
	private void setLength(String length) {
		validateTime(length);
		this.length = length;
	}
	
	private void validateTime(String time) throws IllegalArgumentException {
		if (time.length() != 5)
			throw new IllegalArgumentException("The time input string should have exactly 5 characters");
		
		if (time.charAt(2) != ':')
			throw new IllegalArgumentException("The middle character is not a semicolon");
		
		for (int i = 0; i < time.length(); i++) {
			if (i == 2) continue;
			if (!Character.isDigit(time.charAt(i)))
				throw new IllegalArgumentException("Time was not set properly: Aside from the semicolon all other characters must be digits!");
		}
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getComposer() {
		return composer;
	}
	
	public static Performance getPerformanceAtIndex(int index) {
		return performances.get(index);
	}
	
	public Hall getHall() {
		return hall;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getStartTime() {
		return startTime;
	}
	
	public String getLength() {
		return length;
	}
	
	public int getPrice(SeatClass seatClass) {
		return prices[seatClass.ordinal()];
	}
	
	private void setPrices(int[] prices) throws IllegalArgumentException {
		this.prices = new int[prices.length];
		for (int i = 0; i < prices.length; i++) {
			if (prices[i] <= 0)
				throw new IllegalArgumentException("Price on row " + i + " cannot be negative or 0!");
			this.prices[i] = prices[i];
		}
	}
	
	public Seat[][] getSeats() {
		return seats;
	}
	
	private void setSeats(SeatClass[] seatClasses) {
		seats = new Seat[hall.getRows()][hall.getSeatsPerRow()];
		for (int i = 0; i < hall.getRows(); i++)
			for (int j = 0; j < hall.getSeatsPerRow(); j++)
				seats[i][j] = new Seat(i, j, seatClasses[i], this, this.hall);
	}
	
	public static void cancelPerformance(Performance performance) throws RuntimeException {
		if (!performances.remove(performance)) // the performance is deleted
			throw new RuntimeException("Problem with the cancelation of the performance! No performance found!");
		performance = null; // the performance is null
	}

	public String printSeats() {
		StringBuilder result = new StringBuilder();
		result.append("Legend: \n");
		result.append("\t0 - Seat is taken\n");
		
		for (int i = 0; i < SeatClass.values().length; i++) {
			result.append("\t" + (i + 1) + " - " + SeatClass.values()[i] + '\n');
		}
		result.append("   ");
		for (int i = 0; i < hall.getSeatsPerRow(); i++) {
			result.append(String.format("%-3d", Seat.getActualSeatOnRow(i, hall)));
		}
		result.append('\n');
		
		for (int i = 0; i < hall.getRows(); i++) {
			result.append(String.format("%-3d", Seat.getActualRow(i, hall)));
			for (int j = 0; j < hall.getSeatsPerRow(); j++) {
				if (seats[i][j].isTaken())
					result.append(String.format("%-3d", 0));
				else
					result.append(String.format("%-3d", seats[i][j].getSeatClass().toNumber()));
			}
			result.append('\n');
		}
		
		result.append("----STAGE----\n");
		
		return result.toString();
	}
	
	public static String printPerformances() {
		StringBuilder result = new StringBuilder();
		
		for (int i = 0; i < performances.size(); i++) {
			result.append("Performance number " + i + ":\n");
			result.append(performances.get(i));
		}
		
		return result.toString();
	}
	
	// check if the array list performances is empty
	public static boolean isPerformancesEmpty() {
		return performances.isEmpty();
	}
	
	public static Performance selectPerformance(Scanner in) {
		System.out.println(Performance.printPerformances());
		
		if(Performance.isPerformancesEmpty()) // if no performances exist abort the operation
			throw new IllegalArgumentException("No performances are available. An admin can create a performance!");
		
		Performance selectedPerformance = null;
		
		do {
			try {
				System.out.println("Enter the number of the desired performance: ");
				int choice = Integer.parseInt(in.nextLine());
				selectedPerformance = Performance.getPerformanceAtIndex(choice);
				return selectedPerformance;
			} catch (NumberFormatException ex) {
				System.out.println("The input must be a number! Try again!");
			} catch (IndexOutOfBoundsException ex) {
				System.out.println("The entered number was not among the allowed for selection! Try again!");
			}
		} while(true);
	}
	
	// unique if the hall and the date are not the same
	public static boolean isUnique(Performance performanceToCheck) {
		for (Performance performance : performances)
			if (performance.equals(performanceToCheck))
				throw new IllegalArgumentException("There is already an existing performance in the same hall"
													+ " on the same date with the same title");
		return true;
	}
	
	// equals if title, date and hall match
	@Override
	public boolean equals(Object o) {
		if (o.getClass() != this.getClass())
			return false;
		
		Performance check = (Performance) o;
		
		return check.getTitle().equals(title) && check.getDate().equals(date) && check.getHall().equals(hall);
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append("Title: " + title + "\n");
		result.append("Composer: " + composer + '\n');
		result.append("Date: " + date + "\n");
		result.append("Start time: " + startTime + '\n');
		result.append("Lenght: " + length + '\n');
		
		return result.toString();
	}

	@Override
	public int compareTo(Performance o) {
		return this.title.compareTo(o.getTitle());
	}
	
}
