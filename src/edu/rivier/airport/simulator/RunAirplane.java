package edu.rivier.airport.simulator;

/**
 * Class: RunAirplane.java
 * This class provides a thread for the landing loop
 * @author: Chaitrali Jayantilal Doshi 
 * Update Date: 10/12/2017
 */

public class RunAirplane implements Runnable {
	private Airplane airplane;

	public RunAirplane(Airplane airplane) {
		this.airplane = airplane;
	}

	@Override
	public void run() {
		this.airplane.land();

	} // end run

} // end class Airplane
