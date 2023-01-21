package musicTheatre;

import java.util.*;

public class Performance implements Unique {
	// TODO: add sorting of performances by date and time and title
	
	// TODO: fix the order of the methods

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
		throw new IllegalArgumentException("Cannot create empty performance");
	}
	
	public Performance(String title, String composer, String date, String startTime,
			String length, Hall hall, int[] prices, SeatClass[] seatClasses) {
		this.title = title;
		this.composer = composer;
		this.date = date;
		this.startTime = startTime;
		this.length = length;
		this.hall = hall;
		setPrices(prices);
		setSeats(seatClasses);
		
		performances.add(this);
	}
	
	public String getTitle() {
		return title;
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
	
	public int getPrice(SeatClass seatClass) {
		return prices[seatClass.ordinal()];
	}
	
	public void setPrices(int[] prices) throws IllegalArgumentException {
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
	
	public void setSeats(SeatClass[] seatClasses) {
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

	public void printSeats() { // TODO: make it return a String, rather than directly print it
		// TODO: write nicer print statements with formatting
		System.out.println("Legend:");
		System.out.println("\t0 - Seat is taken");
		
		for (int i = 0; i < SeatClass.values().length; i++) {
			System.out.println("\t" + (i + 1) + " - " + SeatClass.values()[i]);
		}
		System.out.print("  ");
		for (int i = 0; i < hall.getSeatsPerRow(); i++) {
			System.out.print(i);
		}
		System.out.println();
		
		for (int i = 0; i < hall.getRows(); i++) {
			System.out.print(i + " ");
			for (int j = 0; j < hall.getSeatsPerRow(); j++) {
				if (seats[i][j].isTaken())
					System.out.print("0");
				else
					System.out.print(seats[i][j].getSeatClass().toNumber());
			}
			System.out.println();
		}
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
				System.out.println("The input must be a number! Try again!"); // TODO: decide if try again is on a new line or not
			} catch (IndexOutOfBoundsException ex) {
				System.out.println("The entered number was not among the allowed for selection! Try again!");
			}
		} while(true);
	}
	
	// unique if the title, the hall and the date are not the same
	public static boolean isUnique(Performance performanceToCheck) {
		String title = performanceToCheck.getTitle();
		Hall hall = performanceToCheck.getHall();
		String date = performanceToCheck.getDate();
		for (Performance performance : performances) {
			if (performance.getDate().equals(date) && performance.getHall().equals(hall))
				return false;
		}
		return true;
	}
	
	@Override
	public String toString() { // TODO: make the method return more advanced results
		StringBuilder string = new StringBuilder();
		
		string.append("Name: " + title + "\n");
		string.append("Date: " + date + "\n");
		
		return string.toString();
	}
	
}
