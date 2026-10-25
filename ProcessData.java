/********************************************************
Author: Patrick Vandergrift
Date: Sept. 9th, 2026
*********************************************************/

//CLASS ProcessData
/********************************************************
Purpose: This class creates a process
Attributes: processNum (int), active (int), done (int),
  totalTime (int), arrivalTime (int), remainingTime (int)
  waitTime (int), tt (int)
Methods: ProcessData()
**********************************************************/
public class ProcessData
{
  int processNum; //Track process#
  int active; //Process is currently competing for CPU time (Unactive: 0, Active:1)
  int done; //Process is terminated (Not done: 0, Done: 1)
  int totalTime; //Total CPU time (Random value)
  int arrivalTime; //Arrival time (Random value)
  int remainingTime; //Remaining CPU time
  int waitTime; //Wait time
  int tt; //Turnaround Time
  
  public ProcessData()
  {
    this.processNum = 0;
    this.active = 0;
    this.done = 0;
    this.totalTime = 0;
    this.arrivalTime = 0;
    this.remainingTime = 0;
    this.waitTime = 0;
    this.tt = 0;
  }
  
  public ProcessData(int pNum, int totalTime, int arrivalTime)
  {
    this.processNum = pNum;
    this.totalTime = totalTime;
    this.arrivalTime = arrivalTime;
    this.remainingTime = totalTime;
  }
}
