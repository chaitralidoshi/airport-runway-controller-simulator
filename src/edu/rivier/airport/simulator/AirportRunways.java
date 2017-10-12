package edu.rivier.airport.simulator;

/**
 * Class: AirportRunways.java
 * This class AirportRunways provides definitions of constants and helper methods for 
 * the Airport simulation. 
 * @author: Chaitrali Jayantilal Doshi 
 * Update Date: 10/12/2017
 */

import java.util.HashMap;
import java.util.Map;

public final class AirportRunways {
	
	public static final int NUM_RUNWAYS = 6; // Number of runways in this simulation
	
	public static final int NUM_AIRPLANES = 7; // Number of airplanes in this simulation
	
	public static final int MAX_LANDING_REQUESTS = 6; // Maximum number of simultaneous landing requests that Air
														// Traffic Control can handle
	// Runway numbers
	public static final int RUNWAY_4L = 0;
	public static final int RUNWAY_4R = 1;
	public static final int RUNWAY_9 = 2;
	public static final int RUNWAY_14 = 3;
	public static final int RUNWAY_15L = 4;
	public static final int RUNWAY_15R = 5;

	/**
	 * The following variables and methods are used to detect violations of the
	 * rules of this simulation.
	 */

	private static int[] runwayInUse = new int[NUM_RUNWAYS]; // Keeps track of how many airplanes are attempting to land on a
																// given runway

	private static int numLandingRequests = 0; // Keeps track of the number of simultaneous landing requests
	
	public static final Map<Integer, String> runwayHashmap = createRunwayHashmap();

	private static Map<Integer, String> createRunwayHashmap() {
		Map<Integer, String> map = new HashMap<Integer, String>();

		map.put(RUNWAY_4L, "4L");
		map.put(RUNWAY_4R, "4R");
		map.put(RUNWAY_9, "9");
		map.put(RUNWAY_14, "14");
		map.put(RUNWAY_15L, "15L");
		map.put(RUNWAY_15R, "15R");

		return map;
	} // end createHashmap()

	public static String runwayName(int runwayNumber) {
		if (runwayHashmap.containsKey(runwayNumber)) {
			return runwayHashmap.get(runwayNumber);
		} else {
			return "Unknown runway " + runwayNumber;
		}

	} // end runwayName()

	

	/**
	 * requestRunway() and finishedWithRunway() are helper methods for keeping
	 * track of the airport status
	 */

	public synchronized static void requestRunway(int runwayNumber) {
		runwayInUse[runwayNumber]++;
		numLandingRequests++;

	} // end requestRunway()

	public synchronized static void finishedWithRunway(int runwayNumber) {
		runwayInUse[runwayNumber]--;
		numLandingRequests--;

	} // end finishedWithRunway()

	/**
	 * Checking the status of the airport with respect to any violation of the
	 * rules.
	 */
	public synchronized static void checkAirportStatus(int requestedRunway) {
		boolean crash = false; // Set to true if any rule is violated

		System.out.println("\nChecking airport status for requested Runway " + runwayName(requestedRunway) + "...");

		requestRunway(requestedRunway);

		// Check the number of landing requests
		System.out.println("Number of simultaneous landing requests == " + numLandingRequests);

		if (numLandingRequests > MAX_LANDING_REQUESTS) {
			System.out.println("***** The number of simultaneous landing requests exceeds Air Traffic Control limit of "
					+ MAX_LANDING_REQUESTS + "!");
			crash = true;
		}

		// Check the occupancy of each runway
		for (int i = 0; i < NUM_RUNWAYS; i++) {
			System.out.println("Number of planes landing on runway " + runwayName(i) + " == " + runwayInUse[i]);

			if (runwayInUse[i] > 1) {
				System.out.println(
						"***** The number of planes landing on runway " + runwayName(i) + " is greater than 1!");
				crash = true;
			}
		}

		// If any of the rules have been violated, terminate the simulation
		if (crash) {
			System.out.println(
					"***** CRASH! One or more rules have been violated. Due to the crash, the airport is closed!");
			System.exit(-1); // Abnormal program termination
		}

		// Status check is normal
		System.out.println("Status check complete, no rule violations (yay!)\n");

	} // end checkAirportStatus()

} // end class AirportRunways
