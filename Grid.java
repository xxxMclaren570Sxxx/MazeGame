package Main;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;

import javax.swing.*;

public class Grid extends JFrame {
	private int mazeWidth;
	private int mazeHeight;
	private int[][] maze;

	private int playerX;
	private int playerY;
	private BufferedImage image;

	private int delay = 100;

	public Grid(int width, int height) {
		mazeWidth = width;
		mazeHeight = height;

		setTitle("Maze Generator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		// Generate the maze
		maze = generateMaze();

		// Set player start position
		playerX = 1;
		playerY = 1;

		// Add keyboard listener for arrow key navigation
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				movePlayer(e.getKeyCode());
			}
		});
		setFocusable(true);
	}

	private int[][] generateMaze() {
		int[][] maze = new int[mazeHeight][mazeWidth];

		// Initialize maze with walls
		for (int i = 0; i < mazeHeight; i++) {
			for (int j = 0; j < mazeWidth; j++) {
				maze[i][j] = 1;
			}
		}

		// Generate the maze using depth-first search
		generateMazeDFS(maze, 1, 1);

		return maze;
	}

	private void delay(int delay) {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
		}
	}

	private void generateMazeDFS(int[][] maze, int x, int y) {
		maze[y][x] = 0; // Mark current cell as visited

		// Shuffle the neighbors
		int[] neighbors = { 1, 2, 3, 4 };
		shuffleArray(neighbors);

		// Visit each neighbor in a random order
		for (int i = 0; i < neighbors.length; i++) {
			switch (neighbors[i]) {
			case 1: // Up
				if (y - 2 >= 0 && maze[y - 2][x] != 0) {
					maze[y - 1][x] = 0; // Remove the wall
					generateMazeDFS(maze, x, y - 2);
				}
				break;
			case 2: // Right
				if (x + 2 < mazeWidth && maze[y][x + 2] != 0) {
					maze[y][x + 1] = 0; // Remove the wall
					generateMazeDFS(maze, x + 2, y);
				}
				break;
			case 3: // Down
				if (y + 2 < mazeHeight && maze[y + 2][x] != 0) {
					maze[y + 1][x] = 0; // Remove the wall
					generateMazeDFS(maze, x, y + 2);
				}
				break;
			case 4: // Left
				if (x - 2 >= 0 && maze[y][x - 2] != 0) {
					maze[y][x - 1] = 0; // Remove the wall
					generateMazeDFS(maze, x - 2, y);
				}
				break;
			}
		}
	}

	private void shuffleArray(int[] array) {
		for (int i = array.length - 1; i > 0; i--) {
			int j = (int) (Math.random() * (i + 1));
			int temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}
	}

	private void movePlayer(int keyCode) {
		int dx = 0;
		int dy = 0;

		// Determine movement direction based on arrow key
		switch (keyCode) {
		case KeyEvent.VK_UP:
			dy = -1;
			break;
		case KeyEvent.VK_DOWN: 	
			dy = 1;
			break;
		case KeyEvent.VK_LEFT:
			dx = -1;
			break;
		case KeyEvent.VK_RIGHT:
			dx = 1;
			break;
		case KeyEvent.VK_W:
			dy = -1;
			break;
		case KeyEvent.VK_S:
			dy = 1;
			break;
		case KeyEvent.VK_A:
			dx = -1;
			break;
		case KeyEvent.VK_D:
			dx = 1;
			break;
		}

		// Calculate new player position
		int newPlayerX = playerX + dx;
		int newPlayerY = playerY + dy;

		// Check if the new position is within the bounds and not a wall
		if (newPlayerX >= 0 && newPlayerX < mazeWidth && newPlayerY >= 0 && newPlayerY < mazeHeight
				&& maze[newPlayerY][newPlayerX] == 0) {
			// Update player position
			playerX = newPlayerX;
			playerY = newPlayerY;

			repaint(delay);
			
			// Check if the player has reached the exit
			if (playerX == mazeWidth - 2 && playerY == mazeHeight - 2) {
				JOptionPane.showMessageDialog(this, "Congratulations! You reached the exit.");
				resetMaze();
			}
		}
	}

	private void resetMaze() {
		// Generate a new maze
		maze = generateMaze();

		// Reset player position
		playerX = 1;
		playerY = 1;

		// Redraw the maze
		
		
		repaint(delay);
	}
	
	

	public void paint(Graphics g) {
		super.paint(g);
	

		// Clear the screen
		g.clearRect(0, 0, getWidth(), getHeight());

		// Set wall color
		g.setColor(Color.BLACK);

		// Draw walls
		for (int i = 0; i < mazeHeight; i++) {
			for (int j = 0; j < mazeWidth; j++) {
				if (maze[i][j] == 1) {
					g.fillRect(j * 20, i * 20, 20, 20);
				}
			}
		}

		// Set player color
		g.setColor(Color.GREEN);

		// Draw player
		g.fillOval(playerX * 20, playerY * 20, 20, 20);

		// Set exit color
		g.setColor(Color.RED);

		// Draw exit
		g.fillRect((mazeWidth - 2) * 20, (mazeHeight - 2) * 20, 20, 20);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Grid mazeGenerator = new Grid(30, 40);
			mazeGenerator.setSize(mazeGenerator.mazeWidth * 20 + 5, mazeGenerator.mazeHeight * 20 + 5);
			mazeGenerator.setVisible(true);
		});
	}
}
