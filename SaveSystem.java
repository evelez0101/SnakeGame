import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;
import java.io.FileWriter;

public class SaveSystem
{
    // Instance Variables 
    private String fileName;
    private File file;

    public SaveSystem()
    {
        // Save File
        fileName = "save.txt";

        // Creates and Opens file
        try
        {
            file = new File(fileName);
            file.createNewFile();
        }
        catch( IOException e)
        {
            System.out.println("An error occured while creating file");
        }
    }

    public void save(int score)
    {
        // Writes Save to Save File
        try 
        {
            // Writing to File in appending mode
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(score + ",");
            writer.close();
        } 
        catch (IOException e) 
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public int HighestScore()
    {
        // Creates Scanner
        try
        {
            Scanner scan = new Scanner(file);

            // Reads in Scores
            String lines = "";
            while (scan.hasNextLine()) 
            {
                lines += scan.nextLine();
            }

            // Split up Scores
            String[] stringScoreArray = lines.split(",");

            // Creates an int [] from strings of scores
            int [] highScores = new int [stringScoreArray.length];
                
            // Populates int [] from strings of scores
            for (int i = 0; i < highScores.length;i++)
            {
                highScores[i] = Integer.parseInt(stringScoreArray[i]);
            }

            // Worst Case: O(NLog(N)) Hybrid Sorting Algorithm 
            // Sorts in ascending order 
            Arrays.sort(highScores);

            // Returns Highest Score
            return highScores[highScores.length - 1];
        
        }
        catch(IOException e)
        {
            System.out.print("Something went wrong :/");
        }
        // Indicates error occured
        return -1;
    }
}
