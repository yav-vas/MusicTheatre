package musicTheatre;

import java.util.*;

public class Hall implements Unique {
	
	public static ArrayList<Hall> halls = new ArrayList<Hall>();
	
	private static final int MINIMUM_NAME_LENGTH = 5;
	private static final int MAXIMUM_NAME_LENGTH = 25;

	private String name;
	private int rows;
	private int seatsPerRow;
	
	public Hall() {
		
	}
	
	public Hall(String name, int rows, int seatsPerRow) {
		setName(name);
		this.rows = rows;
		this.seatsPerRow = seatsPerRow;
		isUnique(this);
		halls.add(this);
	}
	
	public String getName() {
		return this.name;
	}
	
	private void setName(String name) {
		if (name == null || name.length() == 0) 
			throw new IllegalArgumentException("The name of the hall must not be empty or null");
		
		if (name.length() < MINIMUM_NAME_LENGTH)
			throw new IllegalArgumentException("The lenght of the name must be at least " + MINIMUM_NAME_LENGTH);
		
		if (name.length() > MAXIMUM_NAME_LENGTH)
			throw new IllegalArgumentException("The length of the name must be no more than " + MAXIMUM_NAME_LENGTH);
		
		this.name = name;
	}
	
	public int getRows() {
		return this.rows;
	}
	
	private void setRows(int rows) {
		if (rows <= 0)
			throw new IllegalArgumentException("The number of rows cannot be equal to or less than 0!");
		
		this.rows = rows;
	}
	
	public int getSeatsPerRow() {
		return this.seatsPerRow;
	}
	
	private void setSeatsPerRow(int seatsPerRow) {
		if (seatsPerRow <= 0)
			throw new IllegalArgumentException("The number of seats per row cannot be equal to or less than 0!");
		
		this.seatsPerRow = seatsPerRow;
	}
	
	// check if a hall with the same name already exists
	public static boolean isUnique(Hall hallToCheck) {
		String name = hallToCheck.getName();
		for (Hall hall : halls)
			if (hall.getName().equals(name))
				return true;
				
		return false;
	}
	
	public static String printHalls() {
		StringBuilder result = new StringBuilder();
		
		if (halls.size() == 0) {
			result.append("No halls are available. An admin can create a new hall.");
		}
		
		for (int i = 0; i < halls.size(); i++) {
			result.append("Hall number " + i + ":\n");
			result.append(halls.get(i).toString());
		}
		
		return result.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hall other = (Hall) obj;
		return Objects.equals(name, other.name) && rows == other.rows && seatsPerRow == other.seatsPerRow;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append("\tName: " + this.name + "\n");
		result.append("\tRows: " + this.rows + "\n");
		result.append("\tSeats on each row: " + this.seatsPerRow + "\n");
		
		return result.toString();
	}
	
}
