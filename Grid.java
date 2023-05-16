package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Grid extends JFrame {
	private static final int GRID_SIZE = 20;
	public int[][] grid = new int[GRID_SIZE][GRID_SIZE];
	public JButton[][] buttons = new JButton[GRID_SIZE][GRID_SIZE];
	int playerX;
	int playerY;

	Random rnd = new Random();

	public Grid() {
		setTitle("THE DUNGEON");
		setSize(600, 600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));

		// Create and add buttons to the JFrame
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {

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
		movePlayer();
	}

	public void DrawMaze() {
		// Read everything from a file

	}

	public void movePlayer(){
    	
    	do{
    		playerX = rnd.nextInt(GRID_SIZE);
    		playerY = rnd.nextInt(GRID_SIZE);
    	}while((playerX == 0 || playerX == GRID_SIZE) && (playerY == 0 || playerY == GRID_SIZE));
    	
    	grid[playerX][playerY] = 7;
    	buttons[playerX][playerY].setBackground(Color.red);
    	
    	//Find where the player clicked
    	
    	
    }

	public void DrawWalls() {
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				// change grid when only i = 0, i = gridsize, j = 0, j =
				// GRIDSIZAE
				if (i == 0) {
					// Changes the Top to walls
					grid[i][j] = 1;
					buttons[i][j].setBackground(Color.black);
					buttons[i][j].setEnabled(false);
				}
				if (i == GRID_SIZE - 1) {
					// Changes the bottom to walls
					grid[i][j] = 1;
					buttons[i][j].setBackground(Color.black);
					buttons[i][j].setEnabled(false);
				}
				if (j == 0) {
					// changes the left to walls
					grid[i][j] = 1;
					buttons[i][j].setBackground(Color.black);
					buttons[i][j].setEnabled(false);
				}
				if (j == GRID_SIZE - 1) {
					// changes the right to walls
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
			//Get where the player is
			if(playerY > y && playerX == x){
				System.out.println("Player needs to move to the left");
				for(int i = playerY; i > 0; i--){
					System.out.println(i);
					if(grid[playerX][i] == 1){
						buttons[playerX][playerY].setBackground(Color.white);
						buttons[playerX][i + 1].setBackground(Color.red);
					}
				}
			}else if(playerY < y && playerX == x){
				System.out.println("Player needs to move to the right");
				for(int i = playerY; i < GRID_SIZE; i++){
					System.out.println(i);
					if(grid[playerX][i] == 1){
						grid[playerX][playerY] = 0;
						grid[playerX][i - 1] = 7;
						buttons[playerX][playerY].setBackground(Color.white);
						buttons[playerX][i - 1].setBackground(Color.red);
						playerY = i - 1;
					}
				}
			}
			
		}

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Grid example = new Grid();
			example.setVisible(true);
		});
	}
}
