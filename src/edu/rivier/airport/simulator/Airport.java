package edu.rivier.airport.simulator;

/**
 * Class : Airport.java
 * This class provides a way to test the AirportRunwayController simulator project(Starting point 
 * for the program). In this each thread represents an Airplane. 
 * @author: Chaitrali Jayantilal Doshi 
 * Update Date: 10/12/2017
 */

public class Airport {

	public static void main(String[] args) {
		AirportServer airportServer = new AirportServer();

		Airplane airPlanes[] = new Airplane[AirportRunways.NUM_AIRPLANES];
		RunAirplane airPlaneTasks[] = new RunAirplane[AirportRunways.NUM_AIRPLANES];
		Thread airPlaneThreads[] = new Thread[AirportRunways.NUM_AIRPLANES];

		// Create and launch the individual Airplane threads
		for (int airplane = 0; airplane < AirportRunways.NUM_AIRPLANES; airplane++) {
			airPlanes[airplane] = new Airplane(airplane, airportServer);
			airPlaneTasks[airplane] = new RunAirplane(airPlanes[airplane]);
			airPlaneThreads[airplane] = new Thread(airPlaneTasks[airplane]);
			airPlaneThreads[airplane].start();
		}

	} // end main

} // end class Airport
