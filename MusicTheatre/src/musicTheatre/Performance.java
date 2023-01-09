package musicTheatre;

import java.util.*;

public class Performance {

	public static ArrayList<Performance> performances = new ArrayList<Performance>();
	
	private String title;
	private String composer;
	private String mainActor;
	private Date date;
	private String startTime;
	private int length;
	private Hall hall;
	private double cheapPrice;
	private double middlePrice;
	private double expensivePrice;
	
	public Performance() {
		throw new IllegalArgumentException("Cannot create empty performance");
	}
	
	public Performance(String title, String composer, String mainActor, Date date, String startTime,
			int length, Hall hall, double cheapPrice, double middlePrice, double expensivePrice) {
		
	}
	
}
