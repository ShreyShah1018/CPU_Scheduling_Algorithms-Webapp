import java.util.ArrayList;

public class SJF extends Algorithm {
	int currentTime = 0, minBurstTime = 999;
	int[] currentTiming;
	int turnAroundTime, waitTime, completionTime, startingTime;
	ArrayList<String> chart;
	ArrayList<process> processList;

	/*
	 * Time Complexity: n^2 
	 * where n = no. of processes
	 */
	SJF(ArrayList<process> processList) {
		this.processList = processList;
		int processExe = 0;// ---> Number of processes executed
		int totalProcesses = processList.size();// ---> Total number of processes to be executed
		int processIndex = -1;// ---> Index of the processes
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
			int prevIndex = processIndex;
			for (process p : processList) {
				// check if process is not completed and if the process has arrived
				if (p.arrival <= currentTime && p.completed == false) {
					// condition to select the process with minimum burst time so far
					if (p.burst < minBurstTime) {
						minBurstTime = p.burst;
						processIndex = processList.indexOf(p);
					}
				}
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

	/*
	public static void main(String[] args) {
		process p1 = new process(), p2 = new process(), p4 = new process();
		p1.setValues(6, 2, 1, "p1");
		p1.burstTimeField = new JTextField();
		p1.burstTimeField.setText("2");

		p2.setValues(0, 2, 1, "p2");
		p2.burstTimeField = new JTextField();
		p2.burstTimeField.setText("4");

		p4.setValues(1, 1, 1, "p4");
		p4.burstTimeField = new JTextField();
		p4.burstTimeField.setText("1");

		ArrayList<process> p = new ArrayList<process>();
		p.add(p1);
		p.add(p2);
		p.add(p4);
		@SuppressWarnings("unused")
		SJF s = new SJF(p);
	}
	*/
}