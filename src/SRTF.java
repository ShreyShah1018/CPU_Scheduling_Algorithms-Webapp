import java.util.ArrayList;


public class SRTF extends Algorithm {
	int currentTime = 0, minBurstTime = 999;
	int[] currentTiming;
	int turnAroundTime, waitTime, completionTime, startingTime;
	ArrayList<String> chart;
	ArrayList<process> processList;

	/*
	 * Time Complexity: n^2 
	 * where n = no. of processes
	 */
	SRTF(ArrayList<process> processList) {
		this.processList = processList;
		int processExe = 0;// ---> Number of processes executed
		int totalProcesses = processList.size();// ---> Total number of processes to be executed
		int processIndex = -1;// ---> Index of the processes
		int available = 0;	// to check if any process is currently available for execution
		chart = new ArrayList<String>();

		/* Sorting the process according to arrival time
		 * using: QuickSort (fastest sorting algorithm)
		 * timeComplexity: nLog(n), nLog(n), n^2;
		 * best, average, worst time complexity respectively
		 */
		sort(processList, 0, totalProcesses-1);
		

		/* Step 2: Selection of which process to be run sequentially and displaying
		 * their process id, current time and remaining burst time
		 * Time Complexity: O(n^2) 
		 * where n = no. of processes
		 */
		while (processExe != totalProcesses) {
			minBurstTime = 999;
			available = 0;
			for (process p : processList) {
				// check if process is not completed and if the process has arrived
				if (p.arrival <= currentTime && p.completed == false) {
					// condition to select the process with minimum remaining time so far
					if (p.remaining < minBurstTime) {
						minBurstTime = p.remaining;
						processIndex = processList.indexOf(p);
						available+=1;
					}
				}
			}

			// Increments the Current time after execution selected process by 1
			currentTime += 1;
			
			// condition to check if a process is available for execution
			// "available" will be non zero if there is at least one process ready for execution
			if( available != 0) {
				// decrements the remaining time of process by 1
				processList.get(processIndex).remaining-=1;
				chart.add(processExe, currentTime + "," + processList.get(processIndex).name);
				
				// if after execution of process remaining time is 0 then process has executed and is terminated
				if(processList.get(processIndex).remaining==0) {
					// calculate the completionTime, turnAroundTime and WaitTime for the process
					processList.get(processIndex).completionTime = currentTime;
					processList.get(processIndex).turnAroundTime = processList.get(processIndex).completionTime - processList.get(processIndex).arrival;
					processList.get(processIndex).waitTime = processList.get(processIndex).turnAroundTime - processList.get(processIndex).burst;
	
					// set value of completed
					processList.get(processIndex).completed = true;
					
					// increment the no. of process executed by 1
					processExe+=1;
				}
			}
			// if there is no process is available at current time then cpu is idle
			else {
				chart.add(processExe, currentTime + ",idle");
				processExe += 1;
				totalProcesses += 1;
			}
		}
	}
	
	// returns chart (i.e. ArrayList<String> object)
	public String[] getChart() {
		// converts ArrayList to Array of String
		String ganttChart[] = chart.toArray(new String[chart.size()]);
		return ganttChart;
	}
	@Override
	public float getTurnAroundTime() {
		float TAT = 0;
		for(process p: processList) {
			TAT += p.turnAroundTime;
		}
		TAT = TAT/processList.size();
		return TAT;
	}

	@Override
	public float getWaitTime() {
		float WT = 0;
		for(process p: processList) {
			WT += p.waitTime;
		}
		WT = WT/processList.size();
		return WT;
	}
}