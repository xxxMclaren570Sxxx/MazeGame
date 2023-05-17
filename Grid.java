package MazeGame;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.Random;

public class MazeGame extends JFrame {
	private static final int GRID_SIZE = 20;
	public int[][] grid = new int[GRID_SIZE][GRID_SIZE];
	public JButton[][] buttons = new JButton[GRID_SIZE][GRID_SIZE];
	int playerX;
	int playerY;

    int goalX;
    int goalY;

    int points = 0;

    Icon Wall = new ImageIcon("C:\\Users\\junkm\\Coding\\MazeGame\\res\\Wall.png");
    Icon Player = new ImageIcon("C:\\Users\\junkm\\Coding\\MazeGame\\res\\Player.png");
    Icon PathPoint = new ImageIcon("C:\\Users\\junkm\\Coding\\MazeGame\\res\\PathPoint.png");
    Icon Path = new ImageIcon("C:\\Users\\junkm\\Coding\\MazeGame\\res\\Path.png");
    Icon Goal = new ImageIcon("C:\\Users\\junkm\\Coding\\MazeGame\\res\\EndPoint.png");

	Random rnd = new Random();

	public MazeGame() {
		setTitle("MINORS MINE");
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
		DrawMaze();
	}



	public void DrawMaze() {
		// Read everything from a file
        String filePath = "C:\\Users\\junkm\\Coding\\MazeGame\\MazeLayout.dat"; // Replace "file.txt" with the actual file path
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int x = 0;
            while ((line = br.readLine()) != null && x < GRID_SIZE) {
                for (int y = 0; y < GRID_SIZE && y < line.length(); y++) {
                    int key = Character.getNumericValue(line.charAt(y));
                    grid[x][y] = key;
                    if(key == 1){
                        buttons[x][y].setIcon(Wall);
                       // buttons[x][y].setEnabled(false);
                    }
                    if(key == 7){
                        buttons[x][y].setIcon(Player);
                        playerX = x;
                        playerY = y;
                    }
                    if(key == 8){
                        buttons[x][y].setIcon(Goal);
                        goalX = x; 
                        goalY= y;

                    }
                    if(key == 0){
                        //air
                        buttons[x][y].setIcon(Path);
                    }
                    if(key == 6){
                        //coin
                        buttons[x][y].setIcon(PathPoint);
                    }
                }
                x++;
            }
        } catch (IOException e) {
            e.printStackTrace();
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
			
			//Get where the player is
			if(playerY > y && playerX == x){
				
				for(int i = playerY; i >= 0; i--){
					if(grid[playerX][i] == 6){
                        //Is on a path point
                        points++;
                        buttons[playerX][i].setIcon(Path);
                    
                    }
					if(grid[playerX][i] == 1){
						grid[playerX][playerY] = 0;
						grid[playerX][i + 1] = 7;
						buttons[playerX][playerY].setIcon(Path);
						buttons[playerX][i + 1].setIcon(Player);
						playerY = i + 1;
                        break;
					}
				}
			}else if(playerY < y && playerX == x){
				
				for(int i = playerY; i < GRID_SIZE; i++){
					if(grid[playerX][i] == 6){
                        //Is on a path point
                        points++;
                        buttons[playerX][i].setIcon(Path);
                    
                    }
					if(grid[playerX][i] == 1){
						grid[playerX][playerY] = 0;
						grid[playerX][i - 1] = 7;
						buttons[playerX][playerY].setIcon(Path);
						buttons[playerX][i - 1].setIcon(Player);
						playerY = i - 1;
                        break;
					}
				}
			}else if(playerX > x && playerY == y){
			
				for(int i = playerX; i >= 0; i--){
                    if(grid[i][playerY] == 6){
                        //Is on a path point
                        points++;
                        buttons[i][playerY].setIcon(Path);
                    
                    }
					if(grid[i][playerY] == 1){
						grid[playerX][playerY] = 0;
						grid[i+1][playerY] = 7;
						buttons[playerX][playerY].setIcon(Path);
						buttons[i + 1][playerY].setIcon(Player);
						playerX = i + 1;
                        break;
					}
				}
			}else if(playerX < x && playerY == y){
			
				for(int i = playerX; i < GRID_SIZE; i++){
                    if(grid[i][playerY] == 6){
                        //Is on a path point
                        points++;
                        buttons[i][playerY].setIcon(Path);
                    
                    }
					if(grid[i][playerY] == 1){
						grid[playerX][playerY] = 0;
						grid[i-1][playerY] = 7;
						buttons[playerX][playerY].setIcon(Path);
						buttons[i - 1][playerY].setIcon(Player);
						playerX = i - 1;
                        break;
					}
				}
			}else if(playerX == goalX && playerY == goalY){
                System.out.println("You won!");
            }
			
			
		}

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			MazeGame example = new MazeGame();
			example.setVisible(true);
		});
	}
}
