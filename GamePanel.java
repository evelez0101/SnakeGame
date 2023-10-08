import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random; 

public class GamePanel extends JPanel implements ActionListener 
{
    // Save System
    private SaveSystem s;

    // Screen Size
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;

    // Object on screen
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_WIDTH) / UNIT_SIZE; // Used for scale
    static final int DELAY = 65; // delay to dictate game speed

    // Snake Size
    // Our snake will never exceed the game's size so it can be used as an upper
    // bound
    final int x[] = new int[GAME_UNITS]; // x coordinates
    final int y[] = new int[GAME_UNITS]; // y coordinates

    // Game elements & logic
    int bodyParts = 6; // Starting Number of Body Parts
    int applesEaten = 0; // Apples Eaten so far
    int appleX; // X coordinate of Apple
    int appleY; // X coordinate of Apple
    char direction = 'r'; // Staring Direction of snake
    boolean isRunning = false;
    boolean inMenu = true;
    Timer time; // Game Timer (Flow of the Game)
    Random rand;

    JButton topButton;

    public GamePanel() 
    {
        s = new SaveSystem();
        rand = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT)); // Dimesions of Game Window
        this.setBackground(Color.decode("#0A0908")); // Background Color of game
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() 
    {
        // Start a new game by placing apple and starting timer and setting up game
        // speed
        applePlacer();
        isRunning = true;
        time = new Timer(DELAY, this);
        time.start();
    }

    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        draw(g);
    }

    public void drawGrid(Graphics g)
    {
        // Turns game screen into Grid for debugging/visulaization
        for (int i = 0; i < (SCREEN_HEIGHT / UNIT_SIZE); i++) 
        {
            g.setColor(Color.white); // Grid Color
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT); // Columns
            g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE); // rows
        }
    }

    public void draw(Graphics g) 
    {
        if (isRunning)
        {
            // For debugging purposes
            //drawGrid(g);

            // Apple
            g.setColor(Color.decode("#BC4749")); // Apple Color
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE); // Shape of Apple

            // Snake
            for (int i = 0; i < bodyParts; i++)
            {
                // Head of the Snake
                if (i == 0) 
                {
                    g.setColor(Color.decode("#386641")); // Color of Snake Head
                } 
                else 
                {
                    g.setColor(Color.decode("#6A994E")); // color of Snake Body
                }

                // Fill in Rectangle
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
            
            // Display Score
            displayScore(g);
        }
        else
        {
            gameOver(g);
        }

    }


    public void displayScore(Graphics g)
    {
        // Game Score 
        g.setColor(Color.decode("#E9ECEF"));; // Color of Text
        g.setFont(new Font(Font.SERIF,Font.BOLD,40)); // Font of text
        FontMetrics metrics = getFontMetrics(g.getFont());

        // Displays Score
        if (applesEaten >= s.HighestScore())
        {
            g.drawString( ("New High Score: " + applesEaten) , ( ( SCREEN_WIDTH - metrics.stringWidth( ("New High Score: " + applesEaten) ) ) / 2), g.getFont().getSize()); // Center of the Screen
        }
        else
        {
            g.drawString( ("Score: " + applesEaten) , ( ( SCREEN_WIDTH - metrics.stringWidth( ("Score: " + applesEaten) ) ) / 2), g.getFont().getSize()); // Center of the Screen
        }    
    }

    public void applePlacer() 
    {
        // Places Apple evently in the grid
        appleX = rand.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = rand.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void move() 
    {
        // Snake Body Part Movement
        for (int i = bodyParts; i > 0; i--) 
        {
            x[i] = x[i - 1]; // Shift eveyrthing back one on x axis
            y[i] = y[i - 1]; // Shift eveyrthing back one on x axis
        }

        switch (direction) 
        {
            case 'u':
                y[0] -= UNIT_SIZE; // Head of snake y - direction
                break;
            case 'd':
                y[0] += UNIT_SIZE;
                break;
            case 'l':
                x[0] -= UNIT_SIZE; // Head of snake x - direction
                break;
            case 'r':
                x[0] += UNIT_SIZE;
                break;
        }
    }

    public void checkApple() 
    {
        // Check to see if the coordinates of the apple and snake match
        if ((x[0] == appleX) && (y[0] == appleY))
        {
            bodyParts++;
            applesEaten++;
            applePlacer();
        }
    }

    public void checkCollisions() 
    {
        // Check to see if the head of snake hits its own body
        for (int i = bodyParts; i > 0; i--) 
        {
            // Head is colliding with body if this is true
            if ((x[0] == x[i]) && (y[0] == y[i])) 
            {
                isRunning = false;
            }
        }

        // Check to see if head touches left or right border
        if ((x[0] < 0) || (x[0] > SCREEN_WIDTH)) 
        {
            isRunning = false;
        }

        // Checks to see if head touches top or bottom border
        if ((y[0] < 0) || (y[0] > SCREEN_HEIGHT)) 
        {
            isRunning = false;
        }

        // If the game isn't running then stop the animation timer
        if (!isRunning) 
        {
            time.stop();
        }
    }

    public void gameOver(Graphics g) 
    {
        // Game Over Text
        g.setColor(Color.decode("#E9ECEF")); // Color of Text
        g.setFont(new Font(Font.SERIF,Font.BOLD,75)); // Font of text
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", ((SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2 ), SCREEN_HEIGHT / 2); // Center of the Screen

        // Save and Display Score at GameOver Screen
        s.save(applesEaten);
        displayScore(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        // Game Logic
        if (isRunning) 
        {
            move();
            checkApple();
            checkCollisions();
        }
        // Update the Screen
        repaint();
    }

    // Inner Class that handles key presses
    public class MyKeyAdapter extends KeyAdapter 
    {
        // Allows the player to control the snake
        @Override
        public void keyPressed(KeyEvent e) 
        {
            // We want to prevent 180 degree truns
            switch (e.getKeyCode()) 
            {
                case KeyEvent.VK_LEFT:
                    if (direction != 'r') 
                    {
                        direction = 'l';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'l')
                    {
                        direction = 'r';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'd') 
                    {
                        direction = 'u';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'u') 
                    {
                        direction = 'd';
                    }
                    break;
            }
        }
    }
}
