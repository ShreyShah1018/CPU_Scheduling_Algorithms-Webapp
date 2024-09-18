import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

@SuppressWarnings("serial")
public class X_Factor extends JPanel {

	int n = -1, c=0;
	Color pc,sc, bg;
	ArrayList<process> processes = new ArrayList<process>();
	JLabel pidLabel, arrivalTimeLabel, burstTimeLabel, priorityLabel;
	JLabel TimeQuantumLabel, priorityCheckBoxLabel;
	JTextField TimeQuantumField;
	JButton AddProcess, RemoveProcess, Simulate;
	JCheckBox checkPriority;
	JPanel panel;
	MainFrame parent;
	JFrame BarGraph = new JFrame();

	public X_Factor(int x, int y, Color Background, Color Secondary, MainFrame parent) {
		this.parent = parent;
		bg = Background;
		sc = Secondary;
		setBounds(0,0,980, 690);
		parent.frames.add(BarGraph);
		
		// Creating a JPanel
		panel = new JPanel();
		panel.setBounds(0,0,980, 690);
		panel.setBackground(Background);
		add(panel); 
		
		// creating JLabel and JTextField
		TimeQuantumLabel = new JLabel("Time Quantum:");
		TimeQuantumLabel.setBounds(680, 50, 220, 25);
		TimeQuantumLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		
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
		TimeQuantumField.setBounds(840, 48, 40, 30);
		TimeQuantumField.setColumns(10);
		
		// creating JLabel
		priorityCheckBoxLabel = new JLabel("Priority Based Scheduling:");
		priorityCheckBoxLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		priorityCheckBoxLabel.setBounds(150, 50, 250, 25);
		
		// creating JCheckBox
		checkPriority = new JCheckBox();
		checkPriority.setHorizontalAlignment(SwingConstants.LEFT);
		checkPriority.setFocusPainted(false);
		checkPriority.setBackground(Background);
		checkPriority.setBounds(385, 51, 20, 25);
		checkPriority.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				reCheck();				
			}
		});
		
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
		
		// reCheck to ensure the additional Components are added and visible on screen
		reCheck();
		
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
					ArrayList<process> copy = new ArrayList<process>();
					
					// deep copy of processes ArrayList
					for(process i: processes)
						copy.add(i.clone());
					Algorithm algo;
					DefaultCategoryDataset dataset = new DefaultCategoryDataset();
					
					if(checkPriority.isSelected()) {
						algo = new PS(copy);
						System.out.println("1.FCFS 2.SJF 3.SRTF 4.RR");
						System.out.println(String.format("Turn Around: %.2f\tWait Time: %.2f",algo.getTurnAroundTime(),algo.getWaitTime()));
						dataset.addValue(algo.getWaitTime(),"Wait Time","FCFS");
						dataset.addValue(algo.getTurnAroundTime(),"Turn Around Time","FCFS");
						
						copy.clear();
						for(process i: processes)
							copy.add(i.clone());
						algo = new PSPR(copy);
						System.out.println(String.format("Turn Around: %.2f\tWait Time: %.2f",algo.getTurnAroundTime(),algo.getWaitTime()));
						dataset.addValue(algo.getWaitTime(),"Wait Time","SJF");
						dataset.addValue(algo.getTurnAroundTime(),"Turn Around Time","SJF");
						
						copy.clear();
						for(process i: processes)
							copy.add(i.clone());
						algo = new SRTF(copy);
						System.out.println(String.format("Turn Around: %.2f\tWait Time: %.2f",algo.getTurnAroundTime(),algo.getWaitTime()));
						dataset.addValue(algo.getWaitTime(),"Wait Time","SRTF");
						dataset.addValue(algo.getTurnAroundTime(),"Turn Around Time","SRTF");
						
						copy.clear();
						for(process i: processes)
							copy.add(i.clone());
						algo = new RR(copy,Integer.parseInt(TimeQuantumField.getText()));
						System.out.println(String.format("Turn Around: %.2f\tWait Time: %.2f",algo.getTurnAroundTime(),algo.getWaitTime()));
						dataset.addValue(algo.getWaitTime(),"Wait Time","RR");
						dataset.addValue(algo.getTurnAroundTime(),"Turn Around Time","RR");
					}
					else {
						algo = new FCFS(copy);
						System.out.println("1.FCFS 2.SJF 3.SRTF 4.RR");
						System.out.println(String.format("Turn Around: %.2f\tWait Time: %.2f",algo.getTurnAroundTime(),algo.getWaitTime()));
						dataset.addValue(algo.getWaitTime(),"Wait Time","FCFS");
						dataset.addValue(algo.getTurnAroundTime(),"Turn Around Time","FCFS");
						
						copy.clear();
						for(process i: processes)
							copy.add(i.clone());
						algo = new SJF(copy);
						System.out.println(String.format("Turn Around: %.2f\tWait Time: %.2f",algo.getTurnAroundTime(),algo.getWaitTime()));
						dataset.addValue(algo.getWaitTime(),"Wait Time","SJF");
						dataset.addValue(algo.getTurnAroundTime(),"Turn Around Time","SJF");
						
						copy.clear();
						for(process i: processes)
							copy.add(i.clone());
						algo = new SRTF(copy);
						System.out.println(String.format("Turn Around: %.2f\tWait Time: %.2f",algo.getTurnAroundTime(),algo.getWaitTime()));
						dataset.addValue(algo.getWaitTime(),"Wait Time","SRTF");
						dataset.addValue(algo.getTurnAroundTime(),"Turn Around Time","SRTF");

						
						copy.clear();
						for(process i: processes)
							copy.add(i.clone());
						algo = new RR(copy,Integer.parseInt(TimeQuantumField.getText()));
						System.out.println(String.format("Turn Around: %.2f\tWait Time: %.2f",algo.getTurnAroundTime(),algo.getWaitTime()));
						dataset.addValue(algo.getWaitTime(),"Wait Time","RR");
						dataset.addValue(algo.getTurnAroundTime(),"Turn Around Time","RR");
					}
					
					BarGraph.setTitle("Chart");
					JFreeChart chart = ChartFactory.createBarChart("Algorithm Comparison", // Chart Title
							"Algorithm", // Category axis
							"Time Units", // Value axis
							dataset, PlotOrientation.VERTICAL, true, true, false);

					ChartPanel panel = new ChartPanel(chart);
					BarGraph.setContentPane(panel);
					BarGraph.setBackground(new Color(0xf2f2f2));
					BarGraph.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					BarGraph.setSize(400, 400);
					BarGraph.setLocationRelativeTo(null);
					BarGraph.setVisible(true);
					BarGraph.setAlwaysOnTop(true);
					
					BufferedImage image = new BufferedImage(BarGraph.getWidth(), BarGraph.getHeight(), BufferedImage.TYPE_INT_RGB);
		            Graphics2D graphics2D = image.createGraphics();
		            BarGraph.paint(graphics2D);
		            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
		            String time = LocalDateTime.now().format(formatter);
		            try {
						ImageIO.write(image,"jpeg", new File("C:\\OUTPUTS\\Comparison"+time+".png"));
						System.out.println("Saved");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		            
		            dataset = new DefaultCategoryDataset();
					for(int i=1; i<=5; i++) {
						copy.clear();
						for(process j: processes)
							copy.add(j.clone());
						algo = new RR(copy,i);
						System.out.println(String.format("Turn Around: %.2f\tWait Time: %.2f",algo.getTurnAroundTime(),algo.getWaitTime()));
						dataset.addValue(algo.getWaitTime(),"Wait Time", String.valueOf(i));
						dataset.addValue(algo.getTurnAroundTime(),"Turn Around Time",String.valueOf(i));
					}
					
					JFrame LineChart = new JFrame();
					LineChart.setTitle("Time Quantum Impact");
					JFreeChart chart2 = ChartFactory.createLineChart("Time Quantum Comparison", // Chart Title
							"Time Quantum", // Category axis
							"Time Units", // Value axis
							dataset, PlotOrientation.VERTICAL, true, true, false);
					ChartPanel comPanel = new ChartPanel(chart2);
					LineChart.setContentPane(comPanel);
					LineChart.setBackground(new Color(0xf2f2f2));
					LineChart.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					LineChart.setSize(400, 400);
					LineChart.setLocationRelativeTo(null);
					LineChart.setVisible(true);
					LineChart.setAlwaysOnTop(true);
					
					BufferedImage image2 = new BufferedImage(LineChart.getWidth(), LineChart.getHeight(), BufferedImage.TYPE_INT_RGB);
		            graphics2D = image2.createGraphics();
		            LineChart.paint(graphics2D);
		            time = LocalDateTime.now().format(formatter);
		            try {
						ImageIO.write(image2,"jpeg", new File("C:\\OUTPUTS\\TimeQuantum"+time+".png"));
						System.out.println("Saved");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
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
		panel.add(priorityCheckBoxLabel);
		panel.add(checkPriority);
		panel.add(TimeQuantumLabel);
		panel.add(TimeQuantumField);
		panel.add(pidLabel);
		panel.add(arrivalTimeLabel);
		panel.add(burstTimeLabel);
		if(checkPriority.isSelected())
			panel.add(priorityLabel);
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
		if(checkPriority.isSelected())
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
			if(checkPriority.isSelected())
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
			if(checkPriority.isSelected())
				p.priority =  Integer.parseInt(p.priorityField.getText());
			p.name = "P" + p.processIDField.getText();
		}
	}
	
	boolean valid() {
		// Checks if all Fields have values or not, if not then returns False else True
		if( TimeQuantumField.getText().length()==0) {
			JOptionPane.showMessageDialog(parent, "Time Quantum cannot be empty!");
			return false;
		}
		if( TimeQuantumField.getText().compareToIgnoreCase("0")==0) {
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
			if( checkPriority.isSelected() &&  p.priorityField.getText().length()==0) {
				JOptionPane.showMessageDialog(parent, "Fields cannot be empty!");
				return false;
			}
		}
		return true;
	}
	void reCheck() {		
		/*
		 * Change the screen to accommodate the additional inputs required by 
		 * Priority CPU Scheduling Algorithm (ie Priority input for each process)
		 * And remove the priority input if Algorithm selected is not Priority
		 */
		if(checkPriority.isSelected()) {
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
	}
}