import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.Choice;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class Simulator extends JPanel {

	int n = -1, c=0;
	Color pc,sc, bg;
	ArrayList<process> processes = new ArrayList<process>();
	JLabel pidLabel, arrivalTimeLabel, burstTimeLabel, priorityLabel;
	JLabel algorithmLabel, TimeQuantumLabel, preemptiveLabel;
	JTextField TimeQuantumField;
	JButton AddProcess, RemoveProcess, Simulate;
	Choice algorithmOptions;
	JCheckBox checkPreemptive;
	JPanel panel;
	GanttChart ganttChart;
	MainFrame parent;

	public Simulator(int x, int y, Color Background, Color Secondary, String selectedAlgorithm, MainFrame parent) {
		this.parent = parent;
		bg = Background;
		sc = Secondary;
		setBounds(0,0,980, 690);
		
		// Creating a JPanel
		panel = new JPanel();
		panel.setBounds(0,0,980, 690);
		panel.setBackground(Background);
		add(panel);
		
		// creating JLabel 
		algorithmLabel = new JLabel("Algorithm: ");
		algorithmLabel.setForeground(Secondary);
		algorithmLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		algorithmLabel.setBounds(150,50,160,25);   
		
		// creating JLabel and JTextField
		TimeQuantumLabel = new JLabel("Time Quantum:");
		// added integrity constrains on TimeQuantumField that input is integer
		TimeQuantumField = new JTextField();
		TimeQuantumField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char ch = e.getKeyChar();
				if (!(Character.isDigit(ch))) {
					e.consume();
				}
			}
		});
		
		// creating JLabel
		preemptiveLabel = new JLabel("Preemptive:");
		preemptiveLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		preemptiveLabel.setBounds(150, 90, 200, 25);
		
		// creating JCheckBox
		checkPreemptive = new JCheckBox();
		checkPreemptive.setHorizontalAlignment(SwingConstants.LEFT);
		checkPreemptive.setFocusPainted(false);
		checkPreemptive.setBackground(Background);
		checkPreemptive.setBounds(260, 90, 20, 25);
		
		// creating JLabel
		pidLabel = new JLabel("PID");
		pidLabel.setForeground(Secondary);
		pidLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		pidLabel.setBounds(150, 150, 60, 25);

		// creating JLabel
		arrivalTimeLabel = new JLabel("Arrival Time");
		arrivalTimeLabel.setForeground(Secondary);
		arrivalTimeLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		arrivalTimeLabel.setBounds(300, 150, 300, 25);

		// creating JLabel
		burstTimeLabel = new JLabel("Burst  Time");
		burstTimeLabel.setForeground(Secondary);
		burstTimeLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		burstTimeLabel.setBounds(520, 150, 200, 25);
		
		// creating JLabel
		priorityLabel = new JLabel("Priority");
		priorityLabel.setForeground(Secondary);
		priorityLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		priorityLabel.setBounds(750, 150, 200, 25);
		
		// creating Choice -> drop down menu with CPU Scheduling Algorithms as options
		algorithmOptions = new Choice();
		algorithmOptions.setFont(new Font("Tahoma", Font.PLAIN, 20));
		algorithmOptions.setBounds(280, 45, 300, 25);
		algorithmOptions.add("First Come First Serve");
		algorithmOptions.add("Shortest Job First");
		algorithmOptions.add("Round Robin");
		algorithmOptions.add("Priority");
		
		// setting active algorithmOptions option to selectedAlgorithm (FCFS, SJF, RR, PS)
		switch(selectedAlgorithm) {
		case "FCFS": algorithmOptions.select(0);
					 break;
		case "SJF": algorithmOptions.select(1);
		 			 break;
		case "SRTF":algorithmOptions.select(1);
					checkPreemptive.setSelected(true);
		 			 break;
		case "RR": algorithmOptions.select(2);
		 			 break;
		case "PS": algorithmOptions.select(3);
		 			 break;
		case "PSPR": algorithmOptions.select(3);
					checkPreemptive.setSelected(true);
		 			break;
		}
		
		// reCheck to ensure the additional Components are added and visible on screen
		reCheck();
		
		/* adding ItemListener to algorithmOptions to respond to change in Algorithm option
		 * say from FCFS to RR then screen should update and add TimeQuantum Label and TextField
		 */
		algorithmOptions.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				reCheck();
			}
		});
		// to add at least 1 process on screen for input by default
		new_process();
		
		// creating JButton. With functionality to decrease the number of Processes for input by 1
		RemoveProcess = new JButton();
		RemoveProcess.setForeground(Secondary);
		RemoveProcess.setIcon(new ImageIcon(new ImageIcon(Simulator.class.getResource("/Images/Remove.png")).getImage().getScaledInstance(280, 45, java.awt.Image.SCALE_SMOOTH)));
		RemoveProcess.setPressedIcon(new ImageIcon(new ImageIcon(Simulator.class.getResource("/Images/RemovePressed.png")).getImage().getScaledInstance(280, 45, java.awt.Image.SCALE_SMOOTH)));
		RemoveProcess.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				delete_process();
			}
			public void mouseEntered(MouseEvent e) {
				RemoveProcess.setIcon(new ImageIcon(new ImageIcon(Simulator.class.getResource("/Images/RemoveHover.png")).getImage().getScaledInstance(280, 45, java.awt.Image.SCALE_SMOOTH)));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				RemoveProcess.setIcon(new ImageIcon(new ImageIcon(Simulator.class.getResource("/Images/Remove.png")).getImage().getScaledInstance(280, 45, java.awt.Image.SCALE_SMOOTH)));
			}
		});
		RemoveProcess.setFont(new Font("Tahoma", Font.PLAIN, 17));
		RemoveProcess.setBounds(520, 520, 150, 45);
		RemoveProcess.setBorderPainted(false);
		RemoveProcess.setFocusable(false);
		panel.add(RemoveProcess);
		
		// creating JButton. With functionality to increase the number of Processes for input by 1
		AddProcess = new JButton("");
		AddProcess.setIcon(new ImageIcon(new ImageIcon(Simulator.class.getResource("/Images/Add.png")).getImage().getScaledInstance(280, 45, java.awt.Image.SCALE_SMOOTH)));
		AddProcess.setPressedIcon(new ImageIcon(new ImageIcon(Simulator.class.getResource("/Images/AddPressed.png")).getImage().getScaledInstance(280, 45, java.awt.Image.SCALE_SMOOTH)));
		AddProcess.setForeground(Secondary);
		AddProcess.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new_process();
			}
			public void mouseEntered(MouseEvent e) {
				AddProcess.setIcon(new ImageIcon(new ImageIcon(Simulator.class.getResource("/Images/AddHover.png")).getImage().getScaledInstance(280, 45, java.awt.Image.SCALE_SMOOTH)));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				AddProcess.setIcon(new ImageIcon(new ImageIcon(Simulator.class.getResource("/Images/Add.png")).getImage().getScaledInstance(280, 45, java.awt.Image.SCALE_SMOOTH)));
			}
		});
		AddProcess.setFont(new Font("Tahoma", Font.PLAIN, 17));
		AddProcess.setBounds(280, 520, 150, 45);
		AddProcess.setBorderPainted(false);
		AddProcess.setFocusable(false);
		panel.add(AddProcess);
		
		// creating JButton. With functionality to Simulate to Al
		Simulate = new JButton();
		Simulate.setForeground(Secondary);
		Simulate.setIcon(new ImageIcon(new ImageIcon(Simulator.class.getResource("/Images/Simulate.png")).getImage().getScaledInstance(280, 45, java.awt.Image.SCALE_SMOOTH)));
		Simulate.setPressedIcon(new ImageIcon(new ImageIcon(Simulator.class.getResource("/Images/SimulatePressed.png")).getImage().getScaledInstance(280, 45, java.awt.Image.SCALE_SMOOTH)));
		Simulate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/* if all input fields are valid only then Call respective Simulator class and
				 * run the algorithm with the input given by the user  
				 */
				if(valid()) {
					//System.out.println(algorithmOptions.getSelectedItem());
					setValues();
					while(parent.frames.size()>0) {
						parent.frames.get(parent.frames.size()-1).dispose();
						parent.frames.remove(parent.frames.size()-1);
					}
					ArrayList<process> copy = new ArrayList<process>();
					
					// deep copy of processes ArrayList
					for(process i: processes)
						copy.add(i.clone());
					
					switch(algorithmOptions.getSelectedItem()) {
					case "First Come First Serve":
							FCFS fcfs = new FCFS(copy);
							ganttChart = new GanttChart(fcfs.getChart(), fcfs.processList, Background, parent);
							ganttChart.setLocation(150,400);
							ganttChart.TableFrame.setLocation(150,620);
							ganttChart.setAlwaysOnTop(true);
							updateHisData("FCFS");
							break;
					case "Shortest Job First":
							if(checkPreemptive.isSelected()) {
								SRTF sp = new SRTF(copy);
								ganttChart = new GanttChart(sp.getChart(), sp.processList, Background, parent);
								ganttChart.setLocation(150,400);
								ganttChart.TableFrame.setLocation(150,620);
								ganttChart.setAlwaysOnTop(true);
								updateHisData("SRTF");
							}
							else {
								SJF s = new SJF(copy);
								ganttChart = new GanttChart(s.getChart(), s.processList, Background, parent);
								ganttChart.setLocation(150,400);
								ganttChart.TableFrame.setLocation(150,620);
								ganttChart.setAlwaysOnTop(true);
								updateHisData("SJF");
							}
							break;
					case "Round Robin":
							RR rr = new RR(copy, Integer.parseInt(TimeQuantumField.getText()));
							ganttChart = new GanttChart(rr.getChart(), rr.processList, Background, parent);
							ganttChart.setLocation(150,400);
							ganttChart.TableFrame.setLocation(150,620);
							ganttChart.setAlwaysOnTop(true);
							updateHisData("RR");
							break;
					case "Priority":
							if(checkPreemptive.isSelected()) {
								PSPR ps = new PSPR(copy);
								ganttChart = new GanttChart(ps.getChart(), ps.processList, Background, parent);
								ganttChart.setLocation(150,400);
								ganttChart.TableFrame.setLocation(150,620);
								ganttChart.setAlwaysOnTop(true);
								updateHisData("PSPR");
							}
							else {
								PS ps = new PS(copy);
								ganttChart = new GanttChart(ps.getChart(), ps.processList, Background, parent);
								ganttChart.setLocation(150,400);
								ganttChart.TableFrame.setLocation(150,620);
								ganttChart.setAlwaysOnTop(true);
								updateHisData("PS");
							}
							break;
					}
				}
				else {
					// Errors
				}
			}
			public void mouseEntered(MouseEvent e) {
				Simulate.setIcon(new ImageIcon(new ImageIcon(Simulator.class.getResource("/Images/SimulateHover.png")).getImage().getScaledInstance(280, 45, java.awt.Image.SCALE_SMOOTH)));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				Simulate.setIcon(new ImageIcon(new ImageIcon(Simulator.class.getResource("/Images/Simulate.png")).getImage().getScaledInstance(280, 45, java.awt.Image.SCALE_SMOOTH)));
			}
		});
		Simulate.setFont(new Font("Tahoma", Font.PLAIN, 17));
		Simulate.setBounds(330, 590, 280, 45);
		Simulate.setBorderPainted(false);
		Simulate.setFocusable(false);
		
		// adding all the components to panel (JPanel object)
		panel.add(Simulate);		
		panel.add(algorithmOptions);
		panel.add(algorithmLabel);
		panel.add(pidLabel);
		panel.add(arrivalTimeLabel);
		panel.add(burstTimeLabel);
		if(algorithmOptions.getSelectedItem() == "Priority") {
			panel.add(priorityLabel);
			panel.add(preemptiveLabel);
			panel.add(checkPreemptive);
		}
		panel.setLayout(null);
		repaint();
	}
	
	/*
	 * To remove process object from screen and release memory
	 */
	void new_process() {
		if(n==9) {		// for now only 10 processes can be accepted
			return;
		}
		n+=1;
		process obj = new process();
		
		// added integrity constrains on processID that input is integer
		obj.processIDField = new JTextField();
		obj.processIDField.setText(Integer.toString(n+1));
		obj.processIDField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char ch = e.getKeyChar();
				if (!(Character.isDigit(ch))) {
					e.consume();
				}
			}
		});
		obj.processIDField.setBounds(150, 180+(n*30), 60, 25);
		
		// added integrity constrains on arrivalTime that input is integer
		obj.arrivalTimeField = new JTextField();
		obj.arrivalTimeField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char ch = e.getKeyChar();
				if (!(Character.isDigit(ch))) {
					e.consume();
				}
			}
		});
		obj.arrivalTimeField.setBounds(300, 180+(n*30), 124, 25);
		
		// added integrity constrains on burstTime that input is integer
		obj.burstTimeField = new JTextField();
		obj.burstTimeField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char ch = e.getKeyChar();
				if (!(Character.isDigit(ch))) {
					e.consume();
				}
			}
		});
		obj.burstTimeField.setBounds(520, 180+(n*30), 120, 25);
		
		// added integrity constrains on priority that input is integer
		obj.priorityField = new JTextField();
		obj.priorityField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char ch = e.getKeyChar();
				if (!(Character.isDigit(ch))) {
					e.consume();
				}
			}
		});
		obj.priorityField.setBounds(750, 180+(n*30), 80, 25);
		
		// i is JLabel to show the process index on screen
		obj.i = new JLabel(Integer.toString(n+1)+")");
		obj.i.setFont(new Font("Tahoma", Font.PLAIN, 17));
		obj.i.setBounds(120, 180+(n*30), 30, 20);
		
		// add the obj (new process) to "processes" (ArrayList)
		processes.add(obj);
		
		panel.add(obj.arrivalTimeField);
		panel.add(obj.burstTimeField);
		panel.add(obj.processIDField);
		
		// adds obj.priority to screen is user has selected Priority algorithm for Simulation 
		if(algorithmOptions.getSelectedItem() == "Priority")
			panel.add(obj.priorityField);
		
		panel.add(obj.i);
		panel.repaint();
	}
	
	/*
	 * To remove process object from screen and release memory
	 */
	void delete_process() {
		// removes process if ArrayList is not empty
		if(n>-1) {
			process obj = this.processes.remove(n);
			panel.remove(obj.arrivalTimeField);
			panel.remove(obj.burstTimeField);
			panel.remove(obj.processIDField);
			
			// removes obj.priority from screen is user has selected Priority algorithm for Simulation 
			if(algorithmOptions.getSelectedItem() == "Priority")
				panel.remove(obj.priorityField);
			
			panel.remove(obj.i);
			n-=1;
			panel.repaint();
		}
	}
	
	void setValues() {
		// Takes input from TextFields and convert them into integer values and stores it in arrival, burst, and priority
		for(process p: processes) {
			p.arrival =  Integer.parseInt(p.arrivalTimeField.getText());
			p.burst =  Integer.parseInt(p.burstTimeField.getText());
			p.remaining = p.burst;
			if(algorithmOptions.getSelectedItem() == "Priority")
				p.priority =  Integer.parseInt(p.priorityField.getText());
			p.name = "P" + p.processIDField.getText();
		}
	}
	
	boolean valid() {
		// Checks if all Fields have values or not, if not then returns False else True
		if( algorithmOptions.getSelectedItem() == "Round Robin" &&  TimeQuantumField.getText().length()==0) {
			JOptionPane.showMessageDialog(parent, "Time Quantum cannot be empty!");
			return false;
		}
		if( algorithmOptions.getSelectedItem() == "Round Robin" &&  TimeQuantumField.getText().compareToIgnoreCase("0")==0) {
			JOptionPane.showMessageDialog(parent, "Time Quantum value cannot be 0");
			return false;
		}
		
		for(process p: processes) {
			if(p.arrivalTimeField.getText().length()==0 || p.burstTimeField.getText().length()==0 || p.processIDField.getText().length()==0) {
				JOptionPane.showMessageDialog(parent, "Fields cannot be empty!");
				return false;
			}
			if(p.burstTimeField.getText().compareToIgnoreCase("0")==0) {
				JOptionPane.showMessageDialog(parent, "Burst Time cannot be 0");
				return false;
			}
			if( algorithmOptions.getSelectedItem() == "Priority" &&  p.priorityField.getText().length()==0) {
				JOptionPane.showMessageDialog(parent, "Fields cannot be empty!");
				return false;
			}
		}
		return true;
	}
	void reCheck() {
		/*
		 * Change the screen to accommodate the additional inputs required by 
		 * Round Robin CPU Scheduling Algorithm (ie Time Quantum)
		 * And remove the Time Quantum input if Algorithm selected is not Round Robin
		 */
		if( algorithmOptions.getSelectedItem() == "Round Robin") {
			TimeQuantumField = new JTextField();
			TimeQuantumField.setBounds(840, 48, 40, 30);
			panel.add(TimeQuantumField);
			TimeQuantumField.setColumns(10);
			
			TimeQuantumLabel = new JLabel("Time Quantum:");
			TimeQuantumLabel.setBounds(680, 50, 220, 25);
			TimeQuantumLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
			panel.add(TimeQuantumLabel);
			repaint();
		}
		else {
			panel.remove(TimeQuantumField);
			panel.remove(TimeQuantumLabel);
			repaint();
		}
		
		/*
		 * Change the screen to accommodate the additional inputs required by 
		 * Priority CPU Scheduling Algorithm (ie Priority input for each process)
		 * And remove the priority input if Algorithm selected is not Priority
		 */
		if(algorithmOptions.getSelectedItem()  == "Priority") {
			panel.add(priorityLabel);
			for(process obj: processes) {
				panel.add(obj.priorityField);
				panel.repaint();
			}
			repaint();
		}
		else {
			panel.remove(priorityLabel);
			for(process obj: processes) {
				panel.remove(obj.priorityField);
				panel.repaint();
			}
			repaint();
		}
		
		/*
		 * Change the screen to accommodate the additional inputs required by 
		 * Shortest Job First CPU Scheduling Algorithm (ie algo is preemptive or not)
		 * And remove the preemptive input if Algorithm selected is not Shortest Job First
		 */
		if(algorithmOptions.getSelectedItem()  == "Shortest Job First" || algorithmOptions.getSelectedItem()  == "Priority") {					
			panel.add(preemptiveLabel);
			panel.add(checkPreemptive);
			repaint();
		}
		else {
			panel.remove(preemptiveLabel);
			panel.remove(checkPreemptive);
			repaint();
		}
	}
	void updateHisData(String algo){
		for(int i=0; i< this.parent.data.length; i++) {
			if(algo.compareToIgnoreCase((String)this.parent.data[i][0]) == 0) {
				this.parent.data[i][1]= (int)this.parent.data[i][1] + 1;
			}
		}
	}
}