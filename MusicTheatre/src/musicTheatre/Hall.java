package musicTheatre;

import java.util.*;

public class Hall {
	
	public static ArrayList<Hall> halls = new ArrayList<Hall>();

	private String name;
	private int seatCapacity;
	private ArrayList<ArrayList<Seat>> seats = new ArrayList<ArrayList<Seat>>();
	
	public String getName() {
		return this.name;
	}
	
	// check if a hall with the same name already exists
	public static boolean checkExistingHall(String name) {
		for (Hall hall : halls)
			if (hall.getName().equals(name))
				return false;
				
		return true;
	}
	
}
