/***************************************************
Author: Patrick Vandergrift
Date: Sept. 9th, 2026
****************************************************/

import java.util.*;
import java.util.Random;
import java.util.ArrayList;

//CLASS Sim
/********************************************************
Purpose: This class creates Simulations
Public: Contains a constructor, Simulation, printTable
Private: Methods
**********************************************************/
public class Sim
{
  int numberOfProcess; //Number of processes the user wants
  String algType; //Algorithm Type (FIFO, SJF, SRT)
  
  //Constructors
  Sim()
  {
    this.numberOfProcess = 0;
    this.algType = " ";
  }
  
  Sim(int n, String type)
  {
    this.numberOfProcess = n;
    this.algType = type;
  }

  //Void makeProcess Description
  /********************************************************
  Purpose: makes new a process
  Incoming: int numProcess
  Outgoing: processList
  **********************************************************/
  private List<ProcessData> makeProcess(int numProcess)
  {
    Random randT = new Random(); //Random Total Time
    List<ProcessData> processList = new ArrayList<>();
    int min=2, maxT=20, maxA=0, tempTotalTime=0;
    int[] tList = new int[numProcess];
    
    for(int i=0; i<numProcess; i++)
    {
      tList[i] = randT.nextInt(maxT - min + 1) + min;
      tempTotalTime += tList[i];
    }
    
    maxA = tempTotalTime/2; //Max possible value for Arrival time
    for(int i=0; i<numProcess; i++)
    {
      int arrivalTime = randT.nextInt(maxA + 1);
      processList.add(new ProcessData(i, tList[i], arrivalTime));
    }
    return processList;
  }
  
  //Void makeActive Description
  /********************************************************
  Purpose: makes new a process active
  Incoming: List<ProcessData> processList, int totalTime
  Outgoing: None
  **********************************************************/
  private void makeActive(List<ProcessData> processList, int totalTime)
  {
    for(ProcessData p : processList)
    {
      if(p.done == 0 && p.arrivalTime <= totalTime)
      {
        p.active = 1;
      }
    }
  }

  //Void checkDone Description
  /********************************************************
  Purpose: Checks if all processes are done
  Incoming: List<ProcessData> processList
  Outgoing: processList.stream().allMatch(p -> p.done == 1);
  **********************************************************/
  private boolean checkDone(List<ProcessData> processList)
  {
    /******************************************
      int check=0;
      for(ProcessData p : processList){
      if(p.done == 1){
      check++;
      }
      }
      if(check == numberOfProcess){
      return true;
      }
      else{
      return false;
      }
    ***********************************************/
    //Same thing, just showing I know what it does
    return processList.stream().allMatch(p -> p.done == 1);
  }

  //Void nextProcess Description
  /********************************************************
  Purpose: gets next process
  Incoming: List<ProcessData> processList, String algType
  Outgoing: activeP.get(0);
  **********************************************************/
  private ProcessData nextProcess(List<ProcessData> processList, String algType)
  {
    List<ProcessData> activeP = new ArrayList<>(); //Tracking active processes
    for(ProcessData p : processList)
    {
      if(p.active == 1)
      {
        activeP.add(p);
      }
    }
    
    if(activeP.isEmpty())
    {
      return null;
    }
    
    switch(algType)
    {
      case "FIFO":
        activeP.sort(Comparator.comparingInt(p -> p.arrivalTime));
        break;
      case "SJF":
        activeP.sort(Comparator.comparingInt((ProcessData p) -> p.totalTime).thenCom-
        paringInt(p -> p.arrivalTime));
        break;
      case "SRT":
        activeP.sort(Comparator.comparingInt((ProcessData p) -> p.remainingTime).the-
        nComparingInt(p -> p.arrivalTime));
        break;
    }
    return activeP.get(0);
  }
  
  //Void getArrivalTime Description
  /********************************************************
  Purpose: gets arrival times
  Incoming: List<ProcessData> processList, int t
  Outgoing: arrival
  **********************************************************/
  private int getArrivalTime(List<ProcessData> processList, int t) //t is current time
  { 
    int arrival = -1; //Min value
    for(ProcessData p : processList)
    {
      if(p.done == 0 && p.arrivalTime > t)
      {
        if(arrival == -1 || p.arrivalTime < arrival)
        {
          arrival = p.arrivalTime;
        }
      }
    }
    return arrival;
  }


  //Void simulation Description
  /********************************************************
  Purpose: simulation
  Incoming: List<ProcessData> processList, String algType
  Outgoing: None
  **********************************************************/
  public void simulation(int numberOfProcess, String algType)
  {
    List<ProcessData> processList = makeProcess(numberOfProcess);
    int t =0; //Current Time
    while(!checkDone(processList))
    {
      makeActive(processList, t); // Update to active
      ProcessData p = nextProcess(processList, algType);
      if(p == null) //if no p (process)
      {
        t++;
        continue;
      }
      
      if (algType == "FIFO" || algType == "SJF")
      {
        //Non-preemptive
        p.waitTime = t- p.arrivalTime;
        t += p.remainingTime;
        //Update state
        p.remainingTime = 0;
        p.done = 1;
        p.active = 0;
        p.tt = t - p.arrivalTime;
      }
      else if(algType == "SRT")
      {
        //Preemptive
        int next = getArrivalTime(processList, t);
        int runTime = p.remainingTime; //Next arrival
        if(next != -1 && next < (t + p.remainingTime))
        {
          runTime = next - t;
        }
        
        t += runTime;
        p.remainingTime -= runTime;
        if(p.remainingTime == 0)
        {
          p.done = 1;
          p.active = 0;
          p.tt = t - p.arrivalTime;
          p.waitTime = p.tt - p.totalTime;
        }
      }
    }
    
    printTable(processList);
    double avgTT = processList.stream().mapToDouble(p -> p.tt).average().orElse(0.0);
    System.out.printf("Average Turnaround Time: %.2f\n", avgTT);
  }

  //Void printTable Description
  /********************************************************
  Purpose: print Table
  Incoming: List<ProcessData> processList
  Outgoing: None
  **********************************************************/
  private void printTable(List<ProcessData> processList)
  {
    System.out.printf("%-9s %-8s %-6s %-14s %-16s %-20s %-11s %-17s\n",
    "Process|", "Active|", "Done|", "Arrival Time|", "Total CPU Time|", "Remaining CPU
    Time|", "Wait Time|", "Turnaround Time");
    System.out.println("-".repeat(110));
    
    for(ProcessData p : processList)
    {
      System.out.printf("%-9d %-8d %-6d %-14d %-16d %-20d %-11d %-17d\n",
      p.processNum, p.active, p.done, p.arrivalTime, p.totalTime, p.remainingTime,
      p.waitTime, p.tt);
    }
  }
} //End of Class
