import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
//import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class HomeScreen extends JPanel{
	JButton algoFCFS, algoSJF, algoRR, algoPS, runXFactor;
	JLabel projectTopic;
	
	HomeScreen(Color Background, MainFrame parent){
		setBounds(300, 30, 980, 690);
		setBackground(Background);
		parent.getContentPane().add(this);
		setLayout(null);
		
		algoFCFS = new JButton("");
		algoFCFS.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				parent.CallAlgo("FCFS", parent.Background);
			}
			public void mouseEntered(MouseEvent e) {
				algoFCFS.setIcon(new ImageIcon(new ImageIcon(MainFrame.class.getResource("/Images/FCFS Icon.png")).getImage().getScaledInstance(220, 220, java.awt.Image.SCALE_SMOOTH)));
				resetLocation();
			}
			public void mouseExited(MouseEvent e) {
				algoFCFS.setIcon(new ImageIcon(new ImageIcon(MainFrame.class.getResource("/Images/FCFS Icon.png")).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH)));
			}
		});
		algoFCFS.setBounds(266, 116, 218, 218);
		algoFCFS.setIcon(new ImageIcon(new ImageIcon(MainFrame.class.getResource("/Images/FCFS Icon.png")).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH)));
		algoFCFS.setOpaque(false);
		algoFCFS.setFocusPainted(false);
		algoFCFS.setContentAreaFilled(false);
		algoFCFS.setBorderPainted(false);
		add(algoFCFS);
		
		algoRR = new JButton("");
		algoRR.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				parent.CallAlgo("RR", parent.Background);
			}
			public void mouseEntered(MouseEvent e) {
				algoRR.setIcon(new ImageIcon(new ImageIcon(MainFrame.class.getResource("/Images/RR Icon.png")).getImage().getScaledInstance(220, 220, java.awt.Image.SCALE_SMOOTH)));
				resetLocation();
			}
			public void mouseExited(MouseEvent e) {
				algoRR.setIcon(new ImageIcon(new ImageIcon(MainFrame.class.getResource("/Images/RR Icon.png")).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH)));
			}
		});
		algoRR.setBounds(266, 366, 218, 218);
		algoRR.setIcon(new ImageIcon(MainFrame.class.getResource("/Images/RR Icon.png")));
		algoRR.setIcon(new ImageIcon(new ImageIcon(MainFrame.class.getResource("/Images/RR Icon.png")).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH)));
		algoRR.setOpaque(false);
		algoRR.setFocusPainted(false);
		algoRR.setContentAreaFilled(false);
		algoRR.setBorderPainted(false);
		add(algoRR);
		
		algoSJF = new JButton("");
		algoSJF.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				parent.CallAlgo("SJF", parent.Background);
			}
			public void mouseEntered(MouseEvent e) {
				algoSJF.setIcon(new ImageIcon(new ImageIcon(MainFrame.class.getResource("/Images/SJB Icon.png")).getImage().getScaledInstance(220, 220, java.awt.Image.SCALE_SMOOTH)));
				resetLocation();
			}
			public void mouseExited(MouseEvent e) {
				algoSJF.setIcon(new ImageIcon(new ImageIcon(MainFrame.class.getResource("/Images/SJB Icon.png")).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH)));
			}
		});
		algoSJF.setBounds(516, 116, 218, 218);
		algoSJF.setIcon(new ImageIcon(MainFrame.class.getResource("/Images/SJB Icon.png")));
		algoSJF.setIcon(new ImageIcon(new ImageIcon(MainFrame.class.getResource("/Images/SJB Icon.png")).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH)));
		algoSJF.setOpaque(false);
		algoSJF.setFocusPainted(false);
		algoSJF.setContentAreaFilled(false);
		algoSJF.setBorderPainted(false);
		add(algoSJF);
		
		algoPS = new JButton("");
		algoPS.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				parent.CallAlgo("PS", parent.Background);
			}
			public void mouseEntered(MouseEvent e) {
				algoPS.setIcon(new ImageIcon(new ImageIcon(MainFrame.class.getResource("/Images/PS Icon.png")).getImage().getScaledInstance(220, 220, java.awt.Image.SCALE_SMOOTH)));
				resetLocation();
			}
			public void mouseExited(MouseEvent e) {
				algoPS.setIcon(new ImageIcon(new ImageIcon(MainFrame.class.getResource("/Images/PS Icon.png")).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH)));
			}
		});
		algoPS.setBounds(516, 366, 218, 218);
		algoPS.setIcon(new ImageIcon(new ImageIcon(MainFrame.class.getResource("/Images/PS Icon.png")).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH)));
		algoPS.setOpaque(false);
		algoPS.setFocusPainted(false);
		algoPS.setContentAreaFilled(false);
		algoPS.setBorderPainted(false);
		add(algoPS);
		
		projectTopic = new JLabel("CPU Scheduling Algorithms");
		projectTopic.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		projectTopic.setBounds(327, 50, 380, 40);
		add(projectTopic);
		parent.repaint();
		
		runXFactor = new JButton();
		runXFactor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				parent.CallXF();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				runXFactor.setIcon(new ImageIcon(new ImageIcon(Simulator.class.getResource("/Images/XFHover.png")).getImage().getScaledInstance(280, 45, java.awt.Image.SCALE_SMOOTH)));
				resetLocation();
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				runXFactor.setIcon(new ImageIcon(new ImageIcon(Simulator.class.getResource("/Images/XF.png")).getImage().getScaledInstance(280, 45, java.awt.Image.SCALE_SMOOTH)));
			}
		});
		runXFactor.setIcon(new ImageIcon(new ImageIcon(Simulator.class.getResource("/Images/XF.png")).getImage().getScaledInstance(280, 45, java.awt.Image.SCALE_SMOOTH)));
		runXFactor.setPressedIcon(new ImageIcon(new ImageIcon(Simulator.class.getResource("/Images/XFPressed.png")).getImage().getScaledInstance(280, 45, java.awt.Image.SCALE_SMOOTH)));
		runXFactor.setBounds(360, 609, 280, 45);
		runXFactor.setBorderPainted(false);
		runXFactor.setFocusable(false);
		runXFactor.setOpaque(false);
		runXFactor.setFocusPainted(false);
		runXFactor.setContentAreaFilled(false);
		add(runXFactor);
	}
	
	// fix of floating screen bug
	void resetLocation() {
		projectTopic.setBounds(627, 80, 380, 40);
		algoFCFS.setBounds(566, 146, 218, 218);
		algoRR.setBounds(566, 396, 218, 218);
		algoSJF.setBounds(816, 146, 218, 218);
		algoPS.setBounds(816, 396, 218, 218);
		runXFactor.setBounds(660, 639, 280, 45);
	}
}
