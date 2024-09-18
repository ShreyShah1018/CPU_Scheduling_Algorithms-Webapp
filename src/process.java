import javax.swing.JLabel;
import javax.swing.JTextField;

public class process {
	JTextField processIDField, arrivalTimeField, burstTimeField, priorityField;
	JLabel i;
	int arrival, burst, priority, remaining;
	int completionTime, turnAroundTime, waitTime ;
	boolean completed;
	String name;
	
	void setValues(int arrival,int burst,int priority, String name) {
		this.arrival = arrival;
		this.burst = burst;
		this.remaining = burst;
		this.priority = priority;
		this.name = name;
	}
	
	public process clone() {
		process t = new process();
		t.arrival = this.arrival;
		t.burst = this.burst;
		t.remaining = this.remaining;
		t.priority = this.priority;
		t.name = this.name;
		return t;
	}
}
