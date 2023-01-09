package musicTheatre;

import java.util.*;

public class Main {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		System.out.println("Hello, welcome to the new advanced ticket managemant system for the Music Theatre!");
		
		Admin admin = Admin.registerAdmin(in);
		
		admin.createHall(in);
	}

}
