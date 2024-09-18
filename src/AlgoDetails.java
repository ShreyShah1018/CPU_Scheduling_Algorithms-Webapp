import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultCaret;
import javax.swing.JTextArea;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class AlgoDetails extends JPanel {

	/**
	 * Create the panel.
	 */
	JLabel AlgorithmNameLabel, AdvantageLabel, DisadvantageLabel;
	JTextArea AlgoDetail, AlgoAdvantage, AlgoDisadvantage;
	JScrollPane scrollPane;

	public AlgoDetails(String AlgoName, Color Background, MainFrame parent) {
		JFrame check = parent;

		// if the application if DeIconified then refresh the location
		// to avoid the floating screen bug
		check.addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {
			}

			public void windowClosing(WindowEvent e) {
			}

			public void windowClosed(WindowEvent e) {
			}

			public void windowIconified(WindowEvent e) {
			}

			public void windowDeiconified(WindowEvent e) {
				setLocation(300, 30);
				scrollPane.setLocation(300, 30);
			}

			public void windowActivated(WindowEvent e) {
			}

			public void windowDeactivated(WindowEvent e) {
			}
		});

		// setting the layout and bounds of this panel
		setBounds(0, 0, 980, 690);
		setLayout(null);

		// creating scrollPane and adding it to panel
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 980, 690);
		add(scrollPane);

		// creating new JPanel and adding all content to it.
		JPanel ContentPanel = new JPanel();
		ContentPanel.setLayout(null);
		ContentPanel.setPreferredSize(new Dimension(690, 1000));
		ContentPanel.setBackground(Background);

		// Making ContentPanel scroll-able
		scrollPane.setViewportView(ContentPanel);

		/*
		 * creating a JLabel and adding it to ContentPanel JLabel to display the
		 * Algorithm Name i.e. "First Come First Serve" or "Round Robin"
		 */
		AlgorithmNameLabel = new JLabel(AlgoName);
		AlgorithmNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		AlgorithmNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
		AlgorithmNameLabel.setBounds(312, 36, 356, 45);
		ContentPanel.add(AlgorithmNameLabel);

		/*
		 * creating JButton, when clicked will call CallSimulator of Parent Class (here
		 * MainFrame), where simulator will be set to the algorithm user was reading.
		 * i.e. Shortest Job First
		 */
		JButton Simulate = new JButton("");
		// MouseAdapter to handle mouse event, like mouseEntered, mouseExited and
		// mouseClicked
		Simulate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				parent.CallSimulator(AlgoName);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				Simulate.setIcon(new ImageIcon(new ImageIcon(MainFrame.class.getResource("/Images/SimulateHover.png"))
						.getImage().getScaledInstance(225, 45, java.awt.Image.SCALE_SMOOTH)));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				Simulate.setIcon(new ImageIcon(new ImageIcon(MainFrame.class.getResource("/Images/Simulate.png"))
						.getImage().getScaledInstance(225, 45, java.awt.Image.SCALE_SMOOTH)));
			}
		});

		// Designing and Styling the JButton
		Simulate.setIcon(new ImageIcon(new ImageIcon(MainFrame.class.getResource("/Images/Simulate.png")).getImage()
				.getScaledInstance(225, 45, java.awt.Image.SCALE_SMOOTH)));
		Simulate.setPressedIcon(new ImageIcon(new ImageIcon(MainFrame.class.getResource("/Images/SimulatePressed.png"))
				.getImage().getScaledInstance(225, 45, java.awt.Image.SCALE_SMOOTH)));
		Simulate.setBounds(700, 100, 225, 45);
		Simulate.setBorderPainted(false);
		Simulate.setFocusable(false);

		// adding it to ContentPanel
		ContentPanel.add(Simulate);

		// creating JTextArea and Styling it and positioning it
		AlgoDetail = new JTextArea();
		AlgoDetail.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		AlgoDetail.setEditable(false);
		AlgoDetail.setText("Algo Details will be here");
		AlgoDetail.setLineWrap(true);
		AlgoDetail.setWrapStyleWord(true);
		AlgoDetail.setBounds(57, 200, 788, 200);
		AlgoDetail.setBackground(Background);

		// creating JLabel and Styling it and positioning it
		AdvantageLabel = new JLabel("Advantages");
		AdvantageLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
		AdvantageLabel.setBounds(60, 370, 400, 35);

		// creating JTextArea and Styling it and positioning it
		AlgoAdvantage = new JTextArea();
		AlgoAdvantage.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		AlgoAdvantage.setEditable(false);
		AlgoAdvantage.setLineWrap(true);
		AlgoAdvantage.setWrapStyleWord(true);
		AlgoAdvantage.setBounds(70, 410, 788, 200);
		AlgoAdvantage.setBackground(Background);

		// creating JLabel and Styling it and positioning it
		DisadvantageLabel = new JLabel("Disadvantages");
		DisadvantageLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
		DisadvantageLabel.setBounds(60, 540, 400, 30);

		// creating JTextArea and Styling it and positioning it
		AlgoDisadvantage = new JTextArea();
		AlgoDisadvantage.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		AlgoDisadvantage.setEditable(false);
		AlgoDisadvantage.setLineWrap(true);
		AlgoDisadvantage.setWrapStyleWord(true);
		AlgoDisadvantage.setBounds(70, 580, 788, 300);
		AlgoDisadvantage.setBackground(Background);
		
		/* Bug: scrollPane auto scroll down.
		 * Following it the fix:
		 */
		DefaultCaret caret = (DefaultCaret) AlgoDisadvantage.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);

		ContentPanel.add(AlgoDisadvantage);
		ContentPanel.add(DisadvantageLabel);
		ContentPanel.add(AlgoAdvantage);
		ContentPanel.add(AdvantageLabel);
		ContentPanel.add(AlgoDetail);

		try {
			readfile(AlgoName);
		} catch (IOException e1) {
			System.out.println(e1);
		}
	}

	void readfile(String Algo) throws IOException {
		
		/* String File to get filename.
		 * As file are stored as FCFS.txt, RR.txt
		 * and are required by Application
		 */
		String file = Algo + ".txt";
		
		// File reader to get input from file.
		FileReader fl = new FileReader(file);
		
		// BufferedReader to get input from file as character stream.
		BufferedReader rd = new BufferedReader(fl);

		// String fullName value is Algorithm full name present on 1st line of the file
		String fullName = rd.readLine();
		
		// Use the fullName where required
		AlgorithmNameLabel.setText(fullName);
		AdvantageLabel.setText("Advangates of " + fullName + ":");
		DisadvantageLabel.setText("Disadvangates of " + fullName + ":");
		
		// ArrayList of String to dynamically store the Lines, read from file
		ArrayList<String> lines = new ArrayList<String>();
		String line = rd.readLine();

		/* reading lines till end of file as while loop checks on condition.
		 * Condition: read till line containing only "+" symbol is reached
		 * here, line = rd.readline();
		 * before while loop works as initialization
		 */
		while (!line.equalsIgnoreCase("+")) {
			lines.add(line);
			line = rd.readLine();
		}
		
		/* joining all the lines with '\n'
		 * eg: let Lines be: "Line1", "Line2", "Line3"
		 * then, after joining: "Line1\nLine2\nLine3"
		 */
		AlgoDetail.setText(String.join("\n", lines));

		// to drop the limiter (here "+") line
		lines.clear();
		
		/* reading lines till end of file as while loop checks on condition.
		 * Condition: read till line containing only "-" symbol is reached
		 * here, line = rd.readline();
		 * before while loop works as initialization
		 */
		line = rd.readLine();
		while (!line.equalsIgnoreCase("-")) {
			lines.add(line);
			line = rd.readLine();
		}
		
		/* joining all the lines with '\n'
		 * eg: let Lines be: "Line1", "Line2", "Line3"
		 * then, after joining: "Line1\nLine2\nLine3"
		 */
		AlgoAdvantage.setText(String.join("\n", lines));
		
		// to drop the limiter (here "-") line
		lines.clear();
		
		/* reading lines till end of file as while loop checks on condition.
		 * Condition: read till end of file
		 * here, line = rd.readline();
		 * before while loop works as initialization
		 */
		line = rd.readLine();
		while (line != null) {
			lines.add(line);
			line = rd.readLine();
		}
		
		/* joining all the lines with '\n'
		 * eg: let Lines be: "Line1", "Line2", "Line3"
		 * then, after joining: "Line1\nLine2\nLine3"
		 */
		AlgoDisadvantage.setText(String.join("\n", lines));
		rd.close();
		
		// calling position to accommodate the space accordingly
		position(Algo);
	}

	// To Position the TextArea and Labels to accommodate the space on screen effectively
	void position(String Algo) {
		switch (Algo) {
		case "FCFS":
			// default position are as per needs so no additional adjustment required here
			break;
		case "SJF":
			// adjusting the Labels and TextArea position
			AdvantageLabel.setBounds(60, 350, 400, 35);
			AlgoAdvantage.setBounds(70, 390, 788, 280);
			DisadvantageLabel.setBounds(60, 620, 400, 30);
			AlgoDisadvantage.setBounds(70, 670, 788, 300);
			break;
		case "RR":
			// adjusting the Labels and TextArea position
			AdvantageLabel.setBounds(60, 310, 400, 35);
			AlgoAdvantage.setBounds(70, 350, 788, 280);
			DisadvantageLabel.setBounds(60, 650, 400, 30);
			AlgoDisadvantage.setBounds(70, 690, 788, 300);
			break;
		case "PS":
			// adjusting the Labels and TextArea position
			AdvantageLabel.setBounds(60, 390, 400, 35);
			AlgoAdvantage.setBounds(70, 430, 788, 280);
			DisadvantageLabel.setBounds(60, 660, 400, 30);
			AlgoDisadvantage.setBounds(70, 700, 788, 300);
			break;
		}
	}
}
