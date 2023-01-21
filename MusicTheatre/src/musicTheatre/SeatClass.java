package musicTheatre;

public enum SeatClass {
	CHEAP, MIDDLE, EXPENSIVE;
	
	public int toNumber() { // TODO: rename all the usages of toString to toNumber()
		// TODO: use ordinal
		if (this == CHEAP) return 1;
		if (this == MIDDLE) return 2;
		else return 3; // this == EXPENSIVE
	}
	
	public static String showOptions() {
		StringBuilder result = new StringBuilder();
		
		for (int i = 0; i < SeatClass.values().length; i++) {
			result.append(i + ": " + SeatClass.values()[i] + "\n");
		}
		
		return result.toString();
	}
}
