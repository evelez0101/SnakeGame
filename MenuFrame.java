import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuFrame extends JFrame implements ActionListener
{
    JButton topButton;
    JButton middleButton;
    JButton endButton;

    // Screen Size
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;

    public MenuFrame()
    {
        // Title 
        JLabel jlabel = new JLabel("Snake Game");
        jlabel.setFont(new Font("Verdana",1,40));
        jlabel.setBounds(120, 0, 500, 100);

        // Top Button Initalization
        topButton = new JButton();
		topButton.setBounds(135, 100, 250, 100);
		topButton.addActionListener(this);
		topButton.setText("Start Game!");

        // Third Button Intialization
        endButton = new JButton();
	    endButton.setBounds(135, 225, 250, 100);
	    endButton.addActionListener(this);
	    endButton.setText("Exit Button");
        
        // JFrame
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(500,500);
		this.setVisible(true);
        this.add(jlabel);
		this.add(topButton);
        this.add(endButton);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == topButton)
        {
            new GameFrame();
            dispose();
        }

        if (e.getSource() == endButton)
        {
            dispose();
            System.exit(0);
        }
    }
}
