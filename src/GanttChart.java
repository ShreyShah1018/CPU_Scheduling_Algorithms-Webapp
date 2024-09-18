import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class GanttChart extends JFrame {
	String[] chart;
	JTable table;
	JFrame TableFrame;
	MainFrame parent;
	Color bg;

	GanttChart(String[] chart, ArrayList<process> processList, Color bg, MainFrame parent) {
		this.bg = bg;
		this.parent = parent;
		this.setTitle("Gantt Chart");
		this.setLayout(null);
		this.setVisible(true);
		this.setSize(400, 200);
		this.setBackground(bg);
		this.setResizable(true);
		this.chart = chart;
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		float avgTAT = 0, avgWT = 0;
		int n = processList.size(); // no. of processes
		
		TableFrame = new JFrame("Table");
		TableFrame.setLayout(null);
		TableFrame.setBackground(bg);
		TableFrame.setVisible(true);
		if((n+5)*20 > 200)
			TableFrame.setSize(650, (n+5)*20);
		else
			TableFrame.setSize(650, 200);
		TableFrame.setAlwaysOnTop(true);
		TableFrame.setResizable(false);
		
		JLabel aTAT = new JLabel(String.format("Average Turn Around Time: %.2f",avgTAT));
		if(n>=5)
			aTAT.setBounds(30, (n)*20+15, 200,20);
		else
			aTAT.setBounds(30, (n+1)*20, 200,20);
		TableFrame.add(aTAT);
		
		JLabel aWT = new JLabel(String.format("Average Wait Time: %.2f",avgWT));
		if(n>=5)
			aWT.setBounds(30, (n+1)*20+15, 200,20);
		else
			aWT.setBounds(30, (n+2)*20, 200,20);
		TableFrame.add(aWT);
		
		table = new JTable();
		table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		table.setColumnSelectionAllowed(false);
		if((n+4)*20 > 200)
			table.setBounds(5, 5, 650, (n+5)*20);
		else
			table.setBounds(5, 5, 650, 200);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"Process ID", "Arrival Time", "Burst Time", "Completion Time", "Turn Around Time", "Wait Time"},
			},
			new String[] {
				"Process ID", "Arrival Time", "Burst Time", "Completion Time", "Turn Around Time", "Wait Time"
			}) {
			boolean[] columnEditables = new boolean[] {
					false, false, false, false, false, false
				};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(4).setPreferredWidth(101);

		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for(process p: processList) {
			avgTAT += p.turnAroundTime;
			avgWT += p.waitTime;
			model.addRow(new Object[]{p.name, p.arrival, p.burst, p.completionTime, p.turnAroundTime, p.waitTime});
		}
		avgWT/= n;
		avgTAT/= n;
		
		aTAT.setText(String.format("Average Turn Around Time: %.2f",avgTAT));
		aWT.setText(String.format("Average Wait Time: %.2f",avgWT));
		TableFrame.add(table);
		table.setBackground(bg);
		TableFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		repaint();
		parent.frames.add(this);
		parent.frames.add(TableFrame);
	}

	public void paint(Graphics gp){
        int X = 50, Y = 101, W = 30;
        int scale = 30;
        int completion;
		String name;
		gp.setColor(bg);
		int width = Integer.parseInt(chart[chart.length-1].split(",",2)[0]);
		if( (2*X)+(width*scale) > 400)
			gp.fillRect(0, 0, (2*X)+(width*scale), 200);
		else
			gp.fillRect(0, 0, 400, 200);
		gp.drawString("GanttChart", 50, 20);
		
		String[] val1, val2;
		String temp, n1, n2;
		int t1,t2;
		
		for (int i = 0; i< chart.length; i++) {	
			for(int j= i+1; j< chart.length; j++) {
				// split and get data, completion time and process name
				val1 = chart[i].split(",", 2);
				t1 = Integer.parseInt(val1[0]);
				
				val2 = chart[j].split(",", 2);
				t2 = Integer.parseInt(val2[0]);
				
				if(t2<t1) {
					temp =  chart[j];
					 chart[j] =  chart[i];
					 chart[i] = temp;
				}
			}
		}
		
//		System.out.println("Before: change time of same");
//		for(String i:chart) {
//			System.out.println(i);
//		}
		
		for (int i = chart.length-1; i>0; i--) {
			val1 = chart[i].split(",", 2);
			val2 = chart[i-1].split(",", 2);
			
			n1 = val1[1];
			n2 = val2[1];
			
			if(!n1.equalsIgnoreCase(n2)) {
				val1[0]=val2[0];
			}
			chart[i-1] = val1[0]+","+n2;
		}
//		System.out.println("after: change time of same");
//		for(String i:chart) {
//			System.out.println(i);
//		}
		
		for (int i = chart.length - 1; i>-1 ;i--) {
			gp.setFont(getFont());
			
			// split and get data, completion time and process name
			String[] val = chart[i].split(",", 2);
			completion = Integer.parseInt(val[0]);
            completion *= scale;
            if(i==chart.length-1 && (2*X)+completion > 400) {
            	this.setSize((2*X)+completion, 200);
            }
			name = val[1];
			
			// draws red border
			gp.setColor(Color.red);
			gp.drawRect(X-1, Y-1, completion + 1, W+1);
            
			// if Idle the set color to blue else yellow
			// draws the filled rectangular box
			if(name.equalsIgnoreCase("idle"))
                gp.setColor(Color.blue);
            else
			    gp.setColor(Color.yellow);
			gp.fillRect(X, Y, completion, W);
			
			// if Idle the set text color to white else black
			if(name.equalsIgnoreCase("idle"))
                gp.setColor(Color.white);
            else
				gp.setColor(Color.black);
			gp.drawString(name, completion + (X/2), Y+19);
			
			gp.setColor(Color.black);
            gp.drawString(val[0], completion + X, Y+49);

            // sets the font to Arial 20
            gp.setFont(new Font("Arial",Font.BOLD,20));
            
            gp.drawString("Gantt Chart:", 50, 80);
		}
		// reset the font
		gp.setFont(getFont());
        gp.drawString("0", X , Y+49);
    }
	
//	public static void main(String[] args) {
//		process p1 = new process(), p2 = new process(), p4 = new process(), p3 = new process();
//		process p5 = new process(), p6 = new process();
//		p1.setValues(3, 4, 1, "p1");
//		p1.burstTimeField = new JTextField();
//		p1.burstTimeField.setText("4");
//
//		p2.setValues(0, 2, 1, "p2");
//		p2.burstTimeField = new JTextField();
//		p2.burstTimeField.setText("2");
//
//		p3.setValues(0, 2, 1, "p3");
//		p3.burstTimeField = new JTextField();
//		p3.burstTimeField.setText("2");
//		
//		p4.setValues(1, 1, 1, "p4");
//		p4.burstTimeField = new JTextField();
//		p4.burstTimeField.setText("1");
//
//		p5.setValues(0, 4, 1, "p5");
//		p5.burstTimeField = new JTextField();
//		p5.burstTimeField.setText("4");
//		
//		p6.setValues(3, 5, 1, "p6");
//		p6.burstTimeField = new JTextField();
//		p6.burstTimeField.setText("5");
//		
//		ArrayList<process> p = new ArrayList<process>();
//		p.add(p1);
//		p.add(p2);
//		p.add(p3);
//		p.add(p4);
//		p.add(p5);
//		p.add(p6);
//
//		SJF s = new SJF(p);
//		GanttChart a = new GanttChart(s.getChart(), s.processList, new Color(0xB3FFFF), parent);
//		a.setVisible(true);
//	}
}