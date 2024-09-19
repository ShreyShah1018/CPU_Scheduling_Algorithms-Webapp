import java.util.ArrayList;

public class RR extends Algorithm {
	int currentTime = 0, ran=0;
	int[] currentTiming;
	int turnAroundTime, waitTime, completionTime, startingTime;
	ArrayList<String> chart;
	ArrayList<process> processList;

	/*
	 * Time Complexity: n^2
	 * where n = no. of processes
	 */
	RR(ArrayList<process> processList, int timeQuanta) {
		this.processList = processList;
		int processExe = 0;// ---> Number of processes executed
		int totalProcesses = processList.size();// ---> Total number of processes to be executed
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
			ran = 0;
			for(process p: processList) {
				if( !(currentTime < p.arrival) && !p.completed) {
					if(p.remaining > timeQuanta) {
						currentTime += timeQuanta;
						p.remaining -= timeQuanta;
					}
					else {
						currentTime += p.remaining;
						p.remaining = 0;						
					}
					if(p.remaining == 0) {
						// calculate the completionTime, turnAroundTime and WaitTime for the process
						p.completionTime = currentTime;
						p.turnAroundTime = p.completionTime - p.arrival;
						p.waitTime = p.turnAroundTime - p.burst;
		
						// set value of completed
						p.completed = true;
						
						// increment the no. of process executed by 1
						processExe+=1;
					}
					chart.add(currentTime + "," + p.name);
					ran+=1;
				}
			}
			if( ran == 0 ) {
				System.out.println("> idle "+currentTime);
				currentTime += 1;
				chart.add(currentTime + ",idle");
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
	/*
	public static void main(String[] args) {
		process p1 = new process(), p2 = new process(), p3 = new process(), p4 = new process(), p5 = new process();
		p1.setValues(1, 4, 1, "p1");
		p2.setValues(1, 3, 1, "p2");
		p3.setValues(14, 3, 1, "p3");
		p4.setValues(1, 5, 1, "p4");
		p5.setValues(14, 3, 1, "p5");

		ArrayList<process> p = new ArrayList<process>();
		p.add(p1);
		p.add(p2);
		p.add(p3);
		p.add(p4);
		p.add(p5);

		RR s = new RR(p, 2);
		GanttChart g = new GanttChart(s.getChart(), p, new Color(0xf1f1f1));
	}
	*/