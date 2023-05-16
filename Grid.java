import javax.print.attribute.standard.RequestingUserName;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Test2 extends JFrame {
    private static final int GRID_SIZE = 20;
    public int[][] grid = new int[GRID_SIZE][GRID_SIZE];
    public JButton[][] buttons = new JButton[GRID_SIZE][GRID_SIZE];
    Random rnd = new Random();

    public Test2() {
    	setTitle("THE DUNGEON");
		setSize(600, 600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
		

        // Create and add buttons to the JFrame 
        for (int i = 0; i < GRID_SIZE; i++) {
        	for(int j = 0; j < GRID_SIZE; j++) {
        		
        		grid[i][j] = 0;
        		buttons[i][j] = new JButton();
        		buttons[i][j].setBackground(Color.white);
        		buttons[i][j].addActionListener(new ButtonClickListener(i, j));
        		add(buttons[i][j]);
        	}
            
        }

        
        setLocationRelativeTo(null);
        DrawWalls();
        DrawMaze();
    }
    
    public void DrawMaze() {
    	//randomly place the goal in the maze
    	//The goal cannot be on a wall, or on the top half of the maze
    	int x = rnd.nextInt(GRID_SIZE);
    	int y = rnd.nextInt(GRID_SIZE);
    	
    	
    }
    
    
    
    public void DrawWalls() {
    	for(int i = 0; i < GRID_SIZE; i++) {
    		for(int j = 0; j < GRID_SIZE; j++) {
    			//change grid when only i = 0, i = gridsize, j = 0, j = GRIDSIZAE
    			if(i == 0) {
    				//Changes the Top to walls
    				grid[i][j] = 1;
    				buttons[i][j].setBackground(Color.black);
    				buttons[i][j].setEnabled(false);
    			}
    			if(i == GRID_SIZE - 1) {
    				//Changes the bottom to walls
    				grid[i][j] = 1;
    				buttons[i][j].setBackground(Color.black);
    				buttons[i][j].setEnabled(false);
    			}
    			if(j == 0) {
    				//changes the left to walls
    				grid[i][j] = 1;
    				buttons[i][j].setBackground(Color.black);
    				buttons[i][j].setEnabled(false);
    			}
    			if(j == GRID_SIZE - 1) {
    				//changes the right to walls
    				grid[i][j] = 1;
    				buttons[i][j].setBackground(Color.black);
    				buttons[i][j].setEnabled(false);
    			}
    		}
    	}
    	
    }
    
    private class ButtonClickListener implements ActionListener {
        private int x;
        private int y;

        public ButtonClickListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(x + " " + y);
        }
    
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Test2 example = new Test2();
            example.setVisible(true);
        });
    }
}
