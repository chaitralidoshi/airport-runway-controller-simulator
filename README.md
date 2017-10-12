# airport-runway-controller-simulator

## Description
Airport traffic controller simulator project attempts to simulate the working of Boston Logan airport using Java Threads, Semaphores to enforce synchronization and mutual exclusion on the use of the runways. 

As seen in the diagram below, the runways cross each other so there are constraints on what runways are avaiblable when certain others are in use.

## Rules
Airport simulation uses six runways labeled 4L, 4R, 9, 14, 15L, & 15R
- Runway 4L may be used simultaneously with 4R
- Runway 9 may be used simultaneously with 15L
- Runway 14 may be used simultaneously with any other runway
- Runway 15L may be used simultaneously with 15R
- Runway 9 may not be used simultaneously with 4R or 15R
- Runways 15L or 15R may not be used simultaneously with 4L or 4R
- Only one airplane at a time may occupy a given runway
- Due to restrictions of Air Traffic Control, only six requests for landing may be active simultaneously
- Seven airplanes are continuously attempting to land at the airport

![Alt text](Airport-Runway-Diagram.png?raw=true "Logan Airport")


## Running the project:

- Download or clone the project

### If using Eclipse
- Import the project to eclipse
- The program does not take any arguments
- Airport.java is the starting point of the project
- Run this to start the simulator
- Kill the program to stop the simulator

### If using command line 
- Go to the src directory
- To compile use the following command: javac edu/rivier/airport/simulator/*.java
- To run the program use the following command: java edu.rivier.airport.simulator.Airport
  
