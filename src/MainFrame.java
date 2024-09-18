import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	
	int orgX = 0, orgY = 0;
	JPanel ContentPanel;
	Color Primary, Secondary, Background, upperColor;
	MainFrame parent;
	Object data[][] = {{"FCFS",0},
					   {"SJF",0},
					   {"SRTF",0},
					   {"PS",0},
					   {"PSPR",0},
					   {"RR",0}}; 
	ArrayList<JFrame> frames = new ArrayList<JFrame>();
	
	void closeALL() {
		for(JFrame j: frames) {
			j.dispose();
		}
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		parent = this;
		Primary = new Color(0xFFB3B3);
		Secondary = new Color(0x27233A);
		Background = new Color(0xB3FFFF);
		upperColor = new Color(0x505168);
		learn();

		setBounds(100, 100, 1280, 720);
		this.setUndecorated(true);

		JPanel navigationBar = new JPanel();
		navigationBar.setBackground(Secondary);
		navigationBar.setBounds(0, 30, 300, 690);
		getContentPane().add(navigationBar);
		navigationBar.setLayout(null);

		JButton navHome = new JButton("");
		navHome.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				while(frames.size()>0) {
					frames.get(frames.size()-1).dispose();
					frames.remove(frames.size()-1);
				}
				ContentPanel.removeAll();
				remove(ContentPanel);
				ContentPanel = new HomeScreen(Background, parent);
				ContentPanel.setBounds(300, 30, 980, 690);
				getContentPane().add(ContentPanel);
				repaint();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				navHome.setIcon(new ImageIcon(MainFrame.class.getResource("/Images/HomeHover.png")));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				navHome.setIcon(new ImageIcon(MainFrame.class.getResource("/Images/Home.png")));
			}
		});
		navHome.setIcon(new ImageIcon(MainFrame.class.getResource("/Images/Home.png")));
		navHome.setPressedIcon(new ImageIcon(MainFrame.class.getResource("/Images/HomePressed.png")));
		navHome.setBounds(0, 228, 300, 60);
		navHome.setBorderPainted(false);
		navHome.setFocusable(false);
		navigationBar.add(navHome);

		JButton navSimulator = new JButton("");
		navSimulator.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ContentPanel.removeAll();
				remove(ContentPanel);
				ContentPanel = new Simulator(300, 30, Background,Secondary,(String)data[max()][0], parent);
				ContentPanel.setBounds(300, 30, 980, 690);
				getContentPane().add(ContentPanel);
				repaint();
			}
			public void mouseEntered(MouseEvent e) {
				navSimulator.setIcon(new ImageIcon(MainFrame.class.getResource("/Images/SimulatorHover.png")));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				navSimulator.setIcon(new ImageIcon(MainFrame.class.getResource("/Images/Simulator.png")));
			}
		});
		navSimulator.setIcon(new ImageIcon(MainFrame.class.getResource("/Images/Simulator.png")));
		navSimulator.setPressedIcon(new ImageIcon(MainFrame.class.getResource("/Images/SimulatorPressed.png")));
		navSimulator.setBounds(0, 290, 300, 60);
		navSimulator.setBorderPainted(false);
		navSimulator.setFocusable(false);
		navigationBar.add(navSimulator);

		JButton navAbout = new JButton("");
		navAbout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				while(frames.size()>0) {
					frames.get(frames.size()-1).dispose();
					frames.remove(frames.size()-1);
				}
				ContentPanel.removeAll();
				remove(ContentPanel);
				ContentPanel = new AboutPage(Primary, Secondary, Background);
				ContentPanel.setBounds(300, 30, 980, 690);
				getContentPane().add(ContentPanel);
				repaint();
			}
			public void mouseEntered(MouseEvent e) {
				navAbout.setIcon(new ImageIcon(MainFrame.class.getResource("/Images/AboutHover.png")));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				navAbout.setIcon(new ImageIcon(MainFrame.class.getResource("/Images/About.png")));
			}
		});
		navAbout.setIcon(new ImageIcon(MainFrame.class.getResource("/Images/About.png")));
		navAbout.setPressedIcon(new ImageIcon(MainFrame.class.getResource("/Images/AboutPressed.png")));
		navAbout.setBounds(0, 352, 300, 60);
		navAbout.setBorderPainted(false);
		navAbout.setFocusable(false);
		navigationBar.add(navAbout);

		JPanel UpperPanel = new JPanel();
		UpperPanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				parent.setLocation(x-orgX, y-orgY);
				repaint();
			}
		});
		UpperPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				orgX = e.getX();
				orgY = e.getY();
			}
		});
		UpperPanel.setBounds(0, 0, 1280, 30);
		UpperPanel.setBackground(upperColor);
		getContentPane().add(UpperPanel);
		UpperPanel.setLayout(null);

		JButton closeButton = new JButton("");
		closeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				store();
				closeALL();
				dispose();
			}
		});
		closeButton.setIcon(new ImageIcon(MainFrame.class.getResource("/Images/close.png")));
		closeButton.setPressedIcon(new ImageIcon(MainFrame.class.getResource("/Images/closePressed.png")));
		closeButton.setBounds(1250, 0, 30, 30);
		closeButton.setFont(new Font("Verdana", Font.PLAIN, 20));
		closeButton.setMargin(new Insets(0, 0, 0, 0));
		closeButton.setContentAreaFilled(false);
		closeButton.setBorderPainted(false);
		closeButton.setFocusable(false);
		UpperPanel.add(closeButton);
		
		JLabel groupNameLabel = new JLabel("GROUP 5");
		groupNameLabel.setForeground(Color.WHITE);
		groupNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		groupNameLabel.setBounds(597, 5, 85, 20);
		UpperPanel.add(groupNameLabel);

		ContentPanel = new JPanel();
		ContentPanel.setBounds(300, 30, 980, 690);
		ContentPanel.setBackground(Background);
		getContentPane().add(ContentPanel);
		ContentPanel.setLayout(null);
		
		JButton algoFCFS = new JButton("");
		algoFCFS.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CallAlgo("FCFS", Background);
			}
			public void mouseEntered(MouseEvent e) {
				algoFCFS.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				algoFCFS.setIcon(new ImageIcon(new ImageIcon(MainFrame.class.getResource("/Images/FCFS Icon.png")).getImage().getScaledInstance(220, 220, java.awt.Image.SCALE_SMOOTH)));
			}
			public void mouseExited(MouseEvent e) {
				algoFCFS.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				algoFCFS.setIcon(new ImageIcon(new ImageIcon(MainFrame.class.getResource("/Images/FCFS Icon.png")).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH)));
			}
		});
		algoFCFS.setBounds(566, 146, 218, 218);
		algoFCFS.setIcon(new ImageIcon(new ImageIcon(MainFrame.class.getResource("/Images/FCFS Icon.png")).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH)));
		algoFCFS.setOpaque(false);
		algoFCFS.setFocusPainted(false);
		algoFCFS.setContentAreaFilled(false);
		algoFCFS.setBorderPainted(false);
		ContentPanel.add(algoFCFS);
		
		JButton algoRR = new JButton("");
		algoRR.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CallAlgo("RR", Background);
			}
			public void mouseEntered(MouseEvent e) {
				algoRR.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				algoRR.setIcon(new ImageIcon(new ImageIcon(MainFrame.class.getResource("/Images/RR Icon.png")).getImage().getScaledInstance(220, 220, java.awt.Image.SCALE_SMOOTH)));
			}
			public void mouseExited(MouseEvent e) {
				algoRR.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				algoRR.setIcon(new ImageIcon(new ImageIcon(MainFrame.class.getResource("/Images/RR Icon.png")).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH)));
			}
		});
		algoRR.setBounds(566, 396, 218, 218);
		algoRR.setIcon(new ImageIcon(new ImageIcon(MainFrame.class.getResource("/Images/RR Icon.png")).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH)));
		algoRR.setOpaque(false);
		algoRR.setFocusPainted(false);
		algoRR.setContentAreaFilled(false);
		algoRR.setBorderPainted(false);
		ContentPanel.add(algoRR);
		
		JButton algoSJF = new JButton("");
		algoSJF.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CallAlgo("SJF", Background);
			}
			public void mouseEntered(MouseEvent e) {
				algoSJF.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				algoSJF.setIcon(new ImageIcon(new ImageIcon(MainFrame.class.getResource("/Images/SJB Icon.png")).getImage().getScaledInstance(220, 220, java.awt.Image.SCALE_SMOOTH)));
			}
			public void mouseExited(MouseEvent e) {
				algoSJF.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				algoSJF.setIcon(new ImageIcon(new ImageIcon(MainFrame.class.getResource("/Images/SJB Icon.png")).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH)));
			}
		});
		algoSJF.setBounds(816, 146, 218, 218);
		algoSJF.setIcon(new ImageIcon(new ImageIcon(MainFrame.class.getResource("/Images/SJB Icon.png")).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH)));
		algoSJF.setOpaque(false);
		algoSJF.setFocusPainted(false);
		algoSJF.setContentAreaFilled(false);
		algoSJF.setBorderPainted(false);
		ContentPanel.add(algoSJF);
		
		JButton algoPS = new JButton("");
		algoPS.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CallAlgo("PS", Background);
			}
			public void mouseEntered(MouseEvent e) {
				algoPS.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				algoPS.setIcon(new ImageIcon(new ImageIcon(MainFrame.class.getResource("/Images/PS Icon.png")).getImage().getScaledInstance(220, 220, java.awt.Image.SCALE_SMOOTH)));
			}
			public void mouseExited(MouseEvent e) {
				algoPS.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				algoPS.setIcon(new ImageIcon(new ImageIcon(MainFrame.class.getResource("/Images/PS Icon.png")).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH)));
			}
		});
		algoPS.setBounds(816, 396, 218, 218);
		algoPS.setIcon(new ImageIcon(new ImageIcon(MainFrame.class.getResource("/Images/PS Icon.png")).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH)));
		algoPS.setOpaque(false);
		algoPS.setFocusPainted(false);
		algoPS.setContentAreaFilled(false);
		algoPS.setBorderPainted(false);
		ContentPanel.add(algoPS);
		
		JLabel projectTopic = new JLabel("CPU Scheduling Algorithms");
		projectTopic.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		projectTopic.setBounds(627, 80, 380, 40);
		ContentPanel.add(projectTopic);
		
		JButton runXFactor = new JButton();
		runXFactor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CallXF();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				runXFactor.setIcon(new ImageIcon(new ImageIcon(Simulator.class.getResource("/Images/XFHover.png")).getImage().getScaledInstance(280, 45, java.awt.Image.SCALE_SMOOTH)));
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				runXFactor.setIcon(new ImageIcon(new ImageIcon(Simulator.class.getResource("/Images/XF.png")).getImage().getScaledInstance(280, 45, java.awt.Image.SCALE_SMOOTH)));
			}
		});
		runXFactor.setIcon(new ImageIcon(new ImageIcon(Simulator.class.getResource("/Images/XF.png")).getImage().getScaledInstance(280, 45, java.awt.Image.SCALE_SMOOTH)));
		runXFactor.setPressedIcon(new ImageIcon(new ImageIcon(Simulator.class.getResource("/Images/XFPressed.png")).getImage().getScaledInstance(280, 45, java.awt.Image.SCALE_SMOOTH)));
		runXFactor.setBounds(660, 639, 280, 45);
		runXFactor.setBorderPainted(false);
		runXFactor.setFocusable(false);
		runXFactor.setOpaque(false);
		runXFactor.setFocusPainted(false);
		runXFactor.setContentAreaFilled(false);
		ContentPanel.add(runXFactor);
	}
	
	void CallAlgo(String Algo, Color Bg) {
		ContentPanel.removeAll();
		remove(ContentPanel);
		ContentPanel = new AlgoDetails(Algo, Bg, parent);
		ContentPanel.setBounds(300, 30, 980, 690);
		getContentPane().add(ContentPanel);
		repaint();
	}
	
	void CallXF() {
		  ContentPanel.removeAll();
		  remove(ContentPanel);
		  ContentPanel = new X_Factor(300, 30, Background,Secondary, parent);
		  ContentPanel.setBounds(300, 30, 980, 690);
		  getContentPane().add(ContentPanel);
		  repaint();
	}
	
	void CallSimulator(String DefaultAlgo) {
		  ContentPanel.removeAll();
		  remove(ContentPanel);
		  ContentPanel = new Simulator(300, 30, Background,Secondary, DefaultAlgo, parent);
		  ContentPanel.setBounds(300, 30, 980, 690);
		  getContentPane().add(ContentPanel);
		  repaint();
	}
	
	void learn() {
		String filename = "AlgoSimHis.txt";
		try {
			// file data: count,algo_name
			// example: 3,fcfs \n4,sjf
			FileReader f = new FileReader(filename);
			BufferedReader rd = new BufferedReader(f); 
	
			int i=0;
			String[] temp;
			String read = rd.readLine();
			while(read!=null) {
				temp = read.split(",",2);
				this.data[i][1] = Integer.parseInt(temp[0]); 
				i++;
				read = rd.readLine();
			}
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	int max() {
		int index = 0, max=0;
		for(int i=0;i<data.length;i++) {
			if((int)data[i][1] > max) {
				index = i;
				max = (int)data[i][1];
			}
		}
		return index;
	}
	void store() {
		String filename = "AlgoSimHis.txt";
		try {
			// file data: count,algo_name
			// example: 3,fcfs \n4,sjf
			FileWriter f = new FileWriter(filename, false);
			BufferedWriter wt = new BufferedWriter(f); 
	
			int i=0;
			while(i < data.length) {
				wt.append(data[i][1]+","+data[i][0]);
				if(i+1<data.length) wt.append("\n");
				i++;
			}
			wt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
