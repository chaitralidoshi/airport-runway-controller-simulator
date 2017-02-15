package edu.rivier.simulator;

/**
 *  AirportServer.java
 *  This class implements the methods called by the Airplanes
 */

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.concurrent.Semaphore.*;



public class AirportServer
{   
   // Declarations of Semaphores and Locks
   private final Lock runwaysLock = new ReentrantLock(); // Used to enforce mutual exclusion for acquiring & releasing runways
   
   /**
    *  ***** Add declarations of your own Semaphores (and any Locks, if necessary) here *****
    */
    Semaphore numRunways;
   
   // Constants and Random number generator for use in Thread sleep calls
   private static final int MAX_TAXI_TIME = 10; // Maximum time the airplane will occupy the requested runway after landing, in milliseconds
   private static final int MAX_WAIT_TIME = 100; // Maximum time between landings, in milliseconds
   private static Random r = new Random( 0 );

   
   // Default constructor for AirportServer class
   public AirportServer()
   {
      // ***** Initialize any Sempahores and/or Locks here as necessary *****
	   numRunways = new Semaphore(AirportRunways.NUM_RUNWAYS, true);
   } // end AirportServer default constructor


   // Called by an Airplane when it wishes to land on a runway 
   public void reserveRunway( int airplaneNum, int runway )
   {
      // Acquire runway(s)
      runwaysLock.lock();  // Begin critical region
      
      System.out.println( "Airplane #" + airplaneNum + " is acquiring any needed runway(s) for landing on Runway " + AirportRunways.runwayName(runway) );
      /**
       *  ***** Add your synchronization here! *****
       */
      try {
		numRunways.acquire();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
      // Check status of the airport for any rule violations
      AirportRunways.checkAirportStatus( runway );
      
      
      
      runwaysLock.unlock(); // End critical region
      
      
      // Taxi for a random number of milliseconds
      int taxiTime = r.nextInt( MAX_TAXI_TIME );
      System.out.println( "Airplane #" + airplaneNum + " is taxiing on Runway " + AirportRunways.runwayName(runway) + " for " + taxiTime + " milliseconds");
      try
      {
         Thread.sleep( taxiTime );
      }
      catch ( InterruptedException ex )
      {
         System.out.println( "Airplane #" + airplaneNum + ", Runway " + AirportRunways.runwayName(runway) + ": taxi sleep was interrupted");
      }
      numRunways.release();
   } // end reserveRunway()
  

   // Called by an Airplane when it is finished landing 
   public void releaseRunway( int airplaneNum, int runway )
   {
      // Release the landing runway and any other needed runways
      runwaysLock.lock(); // Begin critical region
      
      System.out.println( "Airplane #" + airplaneNum + " is releasing any needed runway(s) after landing on Runway " + AirportRunways.runwayName(runway) );
      /**
       *  ***** Add your synchronization here! *****
       */

      // Update the status of the airport to indicate that the landing is complete
      AirportRunways.finishedWithRunway( runway );
      
      runwaysLock.unlock(); // End critical region

      
      // Wait for a random number of milliseconds before requesting the next landing for this Airplane
      int waitTime = r.nextInt( MAX_WAIT_TIME );
      System.out.println( "Airplane #" + airplaneNum + " is waiting for " + waitTime + " milliseconds before landing again" );
      try
      {
         Thread.sleep( waitTime );
      }
      catch ( InterruptedException ex )
      {
         System.out.println( "Airplane #" + airplaneNum + ": waitTime sleep was interrupted");
      }
   
   } // end releaseRunway()
   
} // end class AirportServer

