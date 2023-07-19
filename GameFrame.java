import javax.swing.JFrame;

public class GameFrame extends JFrame
{
    GameFrame()
    {
        // Creates Instane of a Panel
        GamePanel panel = new GamePanel();
        this.add(panel);
        this.setTitle("Snake"); // Title of window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Closing operation
        this.setResizable(false); // Can't resize the window 
        this.pack(); // Fits our JFrame Snuggly to our other components
        this.setVisible(true); // We are able to see out window 
        this.setLocationRelativeTo(null); // Sets our location to the middle of the screen
    }
}