/*********************************************************************************
Author: Patrick Vandergrift
Date: Sept. 9th, 2026
Description: Study the impact of different scheduling algorithms -- 
  FIFO, SJF (Shortest Job First), and SRT (Shortest Remaining Time) -
  on the average turnaround time of concurrent processes.
*********************************************************************************/

public class Main 
{
  public static void main(String[] args) 
  {
    int n = 3;
    Sim s = new Sim();
    System.out.println("Running FIFO Simulation");
    s.simulation(n, "FIFO");
    System.out.print("\n");
    System.out.println("Running SJF Simulation");
    s.simulation(n, "SJF");
    System.out.print("\n");
    System.out.println("Running SRT Simulation");
    s.simulation(n, "SRT");
    System.out.print("\n");
  }
}
