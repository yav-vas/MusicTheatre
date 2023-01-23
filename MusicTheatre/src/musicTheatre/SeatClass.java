package musicTheatre;

public enum SeatClass {
	CHEAP, MIDDLE, EXPENSIVE;
	
	public int toNumber() {
		return this.ordinal() + 1;
	}
	
	public static String showOptions() {
		StringBuilder result = new StringBuilder();
		
		for (int i = 0; i < SeatClass.values().length; i++) {
			result.append(i + ": " + SeatClass.values()[i] + "\n");
		}
		
		return result.toString();
	}
}
