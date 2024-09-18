import javax.swing.*;

import java.awt.*;

@SuppressWarnings("serial")
public class AboutPage extends JPanel{

	AboutPage(Color primary, Color secondary, Color background){
		repaint();
    }
	public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        ImageIcon img = new ImageIcon(MainFrame.class.getResource("/Images/bg.png"));
        g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
    }
}
