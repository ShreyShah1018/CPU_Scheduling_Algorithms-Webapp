import java.util.ArrayList;

public abstract class Algorithm {
	int partition(ArrayList<process> processList, int low, int high) 
	{ 
		int pivot = processList.get(high).arrival; 
		int i = (low-1); // index of smaller element 
		for (int j=low; j<high; j++) 
		{ 
			// If current process's arrival time is smaller/is early than or 
			// equal to pivot 
			if (processList.get(j).arrival <= pivot) 
			{ 
				i++; 
				process temp = processList.get(j);
				processList.set(j, processList.get(i));
				processList.set(i, temp);
			} 
		} 

		// swap process object at (i+1)th index with and
		// process object at high index (or pivot) 
		process temp = processList.get(i+1);
		processList.set(i+1, processList.get(high));
		processList.set(high, temp);
		return i+1; 
	} 


	/* The main function that implements QuickSort() 
	processList --> ArrayList to be sorted according to arrival time, 
	low --> Starting index, 
	high --> Ending index */
	void sort(ArrayList<process> processList, int low, int high) 
	{ 
		if (low < high) 
		{ 
			/* pi is partitioning index, arr[pi] is 
			now at right place */
			int pi = partition(processList, low, high); 

			// Recursively sort elements before 
			// partition and after partition 
			sort(processList, low, pi-1); 
			sort(processList, pi+1, high); 
		} 
	}
	
	public abstract float getTurnAroundTime();
	public abstract float getWaitTime();
}
