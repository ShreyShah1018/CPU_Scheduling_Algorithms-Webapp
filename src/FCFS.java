import java.util.ArrayList;

public class FCFS extends Algorithm {
	int currentTime = 0;
	int[] currentTiming;
	int turnAroundTime, waitTime, completionTime, startingTime;
	ArrayList<String> chart;
	ArrayList<process> processList;

	/*
	 * Time Complexity: nLog(n), nLog(n), n^2 
	 * best, average, worst time complexity respectively
	 * where n = no. of processes
	 */
	FCFS(ArrayList<process> processList) {
		this.processList = processList;
		int processExe = 0;// ---> Number of processes executed
		int totalProcesses = processList.size();// ---> Total number of processes to be executed
		int processIndex = 0;// ---> Index of the processes
		chart = new ArrayList<String>();

		/* Sorting the process according to arrival time
		 * using: QuickSort (fastest sorting algorithm)
		 * timeComplexity: nLog(n), nLog(n), n^2;
		 * best, average, worst time complexity respectively
		 */
		sort(processList, 0, totalProcesses-1);
		

		/* Step 2: Selection of which process to be run sequentially and displaying
		 * their process id, current time and remaining burst time
		 * Time Complexity: O(n) 
		 * where n = no. of processes
		 */
		while (processExe != totalProcesses) {
			int prevIndex = processIndex;
			
			// if the process is ready for execution then CPU shall execute it
			// prevIndex is decremented because CPU is not idle
			if( currentTime >= processList.get(processIndex).arrival ) {
				prevIndex -= 1;
			}
			
			/* Condition to check that CPU is idle or not
			 * if idle then prevIndex == processIndex
			 * and processIndex != -1 if the arrival time of first process is > 0
			 */
			if (prevIndex != processIndex && processIndex != -1) {
				startingTime = currentTime;

				// Increments the Current time after execution selected process by its burst time
				currentTime += processList.get(processIndex).burst;
				
				// add it chart (required to make the gantt chart)
				chart.add(processExe, currentTime + "," + processList.get(processIndex).name);

				// calculate the completionTime, turnAroundTime and WaitTime for the process
				processList.get(processIndex).completionTime = currentTime;
				processList.get(processIndex).turnAroundTime = processList.get(processIndex).completionTime - processList.get(processIndex).arrival;
				processList.get(processIndex).waitTime = processList.get(processIndex).turnAroundTime - processList.get(processIndex).burst;
				
				// set value of completed
				processList.get(processIndex).completed = true;
				
				processExe += 1;
				processIndex+=1;
			}
			/* else part executes when no process is available for execution
			 * CPU is IDLE. And time is incremented by 1 and CPU stays idle for that time
			 * until a process is available for execution
			 */ 
			else {
				currentTime+=1;
				// case where first process has arrival time > 0
				if( processExe==0 ) {
					chart.add(processExe, currentTime + ",idle");
					totalProcesses += 1;
					processExe += 1;
				}
				else {
					// if last second was also idle update its time to current time
					if(chart.get(processExe-1).contains("idle") && processExe>0){
						chart.remove(processExe-1);
						chart.add(processExe-1, currentTime + ",idle");
					}
					// if CPU has executed and now it has become idle
					else {
						chart.add(processExe, currentTime + ",idle");
						totalProcesses += 1;
						processExe += 1;
					}
				}
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