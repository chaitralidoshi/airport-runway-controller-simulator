package edu.rivier.airport.simulator;

/**
 * Class: Airplane.java
 * This class Airplane provides functionality for setting requested runways, landing.
 * @author: Chaitrali Jayantilal Doshi 
 * Update Date: 10/12/2017
 */

import java.util.Random;

public class Airplane {
	private int airplaneNum;
	private AirportServer airportServer;

	private int requestedRunway; // Picked at random
	private static Random r = new Random(0);

	// Value constructor for the Airplane class
	public Airplane(int airplaneNum, AirportServer airportServer) {
		this.airplaneNum = airplaneNum;
		this.airportServer = airportServer;

	} // end Airplane value constructor

	// Setter method for requestedRunway
	public void setRequestedRunway(int requestedRunway) {
		this.requestedRunway = requestedRunway;
	}

	// The run() method in class RunAirplane will call this land() method
	public void land() {
		while (true) {
			// Get ready to land
			requestedRunway = r.nextInt(AirportRunways.NUM_RUNWAYS);
			airportServer.reserveRunway(airplaneNum, requestedRunway);

			// Landing complete
			airportServer.releaseRunway(airplaneNum, requestedRunway);

			// Wait on the ground for a while (to prevent starvation of other
			// airplanes)
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				System.out.println("Airplane #" + airplaneNum + ": starvation-prevention sleep was interrupted");
			}

		} // end while

	} // end land

} // end class Airplane
