package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import model.Brick;

public class GraphicUI extends JFrame
{
    private final HashMap<Integer, levelsColors> colors = new HashMap<>();
    private ArrayList<ArrayList<Brick>>  bricks;
    private JPanel panel;

    public GraphicUI(int rows, int cols, int level)
    {
        if(rows==-1)
        {
            JLabel label = new JLabel();
            Border border = BorderFactory.createLineBorder(Color.BLACK);
            label.setBorder(border);
            label.setPreferredSize(new Dimension(150, 100));
            label.setText("You reached level "+level);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            
            
            this.setTitle("End of Game");
            this.setSize(150,100); 
            this.setLayout(new FlowLayout());
            this.add(label);
        }
        else
        {
            this.setTitle("Brick Breaker level "+level);
            this.bricks =  new ArrayList<ArrayList<Brick>>();
            this.panel = new JPanel(new GridLayout(rows,cols));
            this.setSize( (50*rows) , (29*cols) ); 
            this.Init(rows, cols, level);
            this.add(panel);
        }
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    

    private void Init(int row, int col , int level)
    {
        int maxColors = 4 + (level-1)/2;
        int maxBricks = (row*col)/maxColors;
                
        this.colors.put(0, new levelsColors(maxBricks,Color.blue));
        this.colors.put(1, new levelsColors(maxBricks,Color.pink));
        this.colors.put(2, new levelsColors(maxBricks,Color.cyan));
        this.colors.put(3, new levelsColors(maxBricks,Color.yellow));
        this.colors.put(4, new levelsColors(maxBricks,Color.green));
        this.colors.put(5, new levelsColors(maxBricks,Color.magenta));
        this.colors.put(6, new levelsColors(maxBricks,Color.orange));
        this.colors.put(7, new levelsColors(maxBricks,Color.red));
        
        int special_bricks_counter = (col*row)*5/100;
        int simple_bricks_counter = (row*col) - special_bricks_counter;
        int randomNum = 0;
        Random random = new Random();
        for(int i=0; i<row; i++)
        {
            ArrayList<Brick> yArray = new ArrayList<>();
            String name = new String();
            for(int j=0; j<col; j++)
            {
                boolean done = false;
                do
                {
                    if( (j>(col*(2/3)) && i>(row*(2/3))) )
                    {
                        randomNum = random.nextInt(colors.size() - 0 + 1) + 0;
                        if(randomNum == 0 || randomNum == 2 || randomNum == 4 || 
                                randomNum == 5 || randomNum == 6 || randomNum == 7)
                        {
                            if(simple_bricks_counter>0)
                            {
                                name = "Simple";
                                simple_bricks_counter--;
                                done = true;
                            }

                        }
                        else
                        {
                            if(special_bricks_counter>0)
                            {
                                name = "Bomb";
                                special_bricks_counter--;
                                done = true;
                            }
                        }
                    }
                    else
                    {
                        randomNum = random.nextInt(colors.size() - 0 + 1) + 0;
                        if(randomNum == 0 || randomNum == 2 || randomNum == 4 || 
                                randomNum == 5 || randomNum == 6 || randomNum == 7)
                        {
                            if(simple_bricks_counter>0)
                            {
                                name = "Simple";
                                simple_bricks_counter--;
                                done = true;
                            }

                        }
                    }
                          
                    
                }while(!done);
                
                Brick newBrick = new Brick(i,j,name);
                
                if(name.equals("Bomb"))
                {
                    newBrick.setBackground(Color.black);
                }
                else
                {
                    this.randomColor(newBrick, maxColors);
                }
                this.panel.add(newBrick);  
                yArray.add(newBrick);
            }
            this.bricks.add(yArray);
        }
    }

    private void randomColor(Brick brick, int maxColors)
    {
        boolean done = false;

        if(maxColors >= colors.size())
        {
            maxColors = colors.size()-1;
        }

        do
        {
            Random rand = new Random();
            int randNum = rand.nextInt(maxColors - 0 + 1) + 0;
            if(colors.get(randNum).getMaxBricks()>0)
            {
                colors.get(randNum).decrementMaxBricks();
                brick.setBackground(colors.get(randNum).getColor());
                done = true;
            }  
        }while(!done);
    }
    public ArrayList<ArrayList<Brick>> getBricks()
    {
        return this.bricks;
    }

    public class levelsColors
    {
        int maxBricks;
        Color color;

        public levelsColors(int maxBricks, Color color)
        {
            this.maxBricks = maxBricks;
            this.color = color;
        }
        public void decrementMaxBricks()
        {
            this.maxBricks--;
        }

        public int getMaxBricks()
        {
            return this.maxBricks;            
        }

        public Color getColor()
        {
            return this.color;
        }
    }
}
