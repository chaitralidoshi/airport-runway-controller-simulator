package edu.rivier.airport.simulator;

/**
 *  Class: AirportServer.java
 *  This class implements the methods to validate the rules, provides 
 *  a required runways by airplanes without violating the rules. 
 *  Used Semaphores and locks to achieve mutual exclusion
 *  @author: Chaitrali Jayantilal Doshi 
 *  Update Date: 10/12/2017
 */

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class AirportServer {

	// Declarations of Semaphores and Locks
	private final Lock runwaysLock = new ReentrantLock(true);
	// Used to enforce mutual exclusion for acquiring & releasing runways

	/**
	 * ***** Add declarations of your own Semaphores (and any Locks, if
	 * necessary) here *****
	 */

	Condition resourceAvailable = runwaysLock.newCondition();
	private final Semaphore listOfRunways[] = new Semaphore[AirportRunways.NUM_RUNWAYS];

	// Constants and Random number generator for use in Thread sleep calls
	private static final int MAX_TAXI_TIME = 10; // Maximum time the airplane will occupy the requested
													// runway after landing, in milliseconds

	private static final int MAX_WAIT_TIME = 100; // Maximum time between landings, in milliseconds
	private static Random r = new Random(0);

	// Default constructor for AirportServer class
	public AirportServer() {

		// ***** Initialize any Semaphores and/or Locks here as necessary *****
		for (int i = 0; i < AirportRunways.NUM_RUNWAYS; i++) {
			listOfRunways[i] = new Semaphore(1, true);
		}

	} // end AirportServer default constructor

	// Called by an Airplane when it wishes to land on a runway
	public void reserveRunway(int airplaneNum, int runway) {

		// Acquire runway(s)

		runwaysLock.lock(); // Begin critical region

		System.out.println("Airplane #" + airplaneNum + " is acquiring any needed runway(s) for landing on Runway "
				+ AirportRunways.runwayName(runway));
		
		
		/**
		 * ***** Synchronization logic *****
		 */

		while (listOfRunways[runway].availablePermits() == 0) {
			try {
				resourceAvailable.signal();
				resourceAvailable.await();

			} catch (InterruptedException e) { 
				e.printStackTrace();
			}
		} // end of while loop

		//checking the violations of the rules
		
		if (runway == AirportRunways.RUNWAY_4L) {

			while ((listOfRunways[AirportRunways.RUNWAY_15L].availablePermits() == 0
					|| listOfRunways[AirportRunways.RUNWAY_15R].availablePermits() == 0)
					|| listOfRunways[AirportRunways.RUNWAY_4L].availablePermits() == 0) {
				try {
					resourceAvailable.signal();
					resourceAvailable.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			try {

				listOfRunways[runway].acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} // end of 1st if block

		if (runway == AirportRunways.RUNWAY_4R) {

			while ((listOfRunways[AirportRunways.RUNWAY_15L].availablePermits() == 0
					|| listOfRunways[AirportRunways.RUNWAY_15R].availablePermits() == 0
					|| listOfRunways[AirportRunways.RUNWAY_9].availablePermits() == 0)
					|| listOfRunways[AirportRunways.RUNWAY_4R].availablePermits() == 0) {
				try {
					resourceAvailable.signal();
					resourceAvailable.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			try {

				listOfRunways[runway].acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}// end of 2nd if block

		if (runway == AirportRunways.RUNWAY_9) {

			while ((listOfRunways[AirportRunways.RUNWAY_4R].availablePermits() == 0
					|| listOfRunways[AirportRunways.RUNWAY_15R].availablePermits() == 0)
					|| listOfRunways[AirportRunways.RUNWAY_9].availablePermits() == 0) {
				try {
					resourceAvailable.signal();
					resourceAvailable.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			try {

				listOfRunways[runway].acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} // end of 3rd if block

		if (runway == AirportRunways.RUNWAY_14) {
			while (listOfRunways[AirportRunways.RUNWAY_14].availablePermits() == 0) {
				try {
					resourceAvailable.signal();
					resourceAvailable.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			try {

				listOfRunways[runway].acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} // end of 4th if block

		if (runway == AirportRunways.RUNWAY_15L) {

			while ((listOfRunways[AirportRunways.RUNWAY_4L].availablePermits() == 0
					|| listOfRunways[AirportRunways.RUNWAY_4R].availablePermits() == 0)
					|| listOfRunways[AirportRunways.RUNWAY_15L].availablePermits() == 0) {
				try {
					resourceAvailable.signal();
					resourceAvailable.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			try {

				listOfRunways[runway].acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} // end of 5th if block

		if (runway == AirportRunways.RUNWAY_15R) {

			while ((listOfRunways[AirportRunways.RUNWAY_4L].availablePermits() == 0
					|| listOfRunways[AirportRunways.RUNWAY_4R].availablePermits() == 0
					|| listOfRunways[AirportRunways.RUNWAY_9].availablePermits() == 0)
					|| listOfRunways[AirportRunways.RUNWAY_15R].availablePermits() == 0) {
				try {
					resourceAvailable.signal();
					resourceAvailable.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			try {

				listOfRunways[runway].acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} // end of 6th if block

		// Check status of the airport for any rule violations
		AirportRunways.checkAirportStatus(runway);

		runwaysLock.unlock(); // End critical region

		// Taxi for a random number of milliseconds
		int taxiTime = r.nextInt(MAX_TAXI_TIME);
		System.out.println("Airplane #" + airplaneNum + " is taxiing on Runway " + AirportRunways.runwayName(runway)
				+ " for " + taxiTime + " milliseconds");
		try {
			Thread.sleep(taxiTime);
		} catch (InterruptedException ex) {
			System.out.println("Airplane #" + airplaneNum + ", Runway " + AirportRunways.runwayName(runway)
					+ ": taxi sleep was interrupted");
		}

	} // end reserveRunway()

	// Called by an Airplane when it is finished landing
	public void releaseRunway(int airplaneNum, int runway) {

		// Release the landing runway and any other needed runways
		runwaysLock.lock(); // Begin critical region

		System.out.println("Airplane #" + airplaneNum + " is releasing any needed runway(s) after landing on Runway "
				+ AirportRunways.runwayName(runway));

		listOfRunways[runway].release();
		resourceAvailable.signalAll();

		// Update the status of the airport to indicate that the landing is
		// complete
		AirportRunways.finishedWithRunway(runway);

		runwaysLock.unlock(); // End critical region

		// Wait for a random number of milliseconds before requesting the next
		// landing for this Airplane
		int waitTime = r.nextInt(MAX_WAIT_TIME);
		System.out.println(
				"Airplane #" + airplaneNum + " is waiting for " + waitTime + " milliseconds before landing again");
		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException ex) {
			System.out.println("Airplane #" + airplaneNum + ": waitTime sleep was interrupted");
		}

	} // end releaseRunway()

} // end class AirportServer