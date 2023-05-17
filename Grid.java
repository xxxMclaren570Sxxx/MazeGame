package Main;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu {
	JFrame frame;
	boolean hasWon = false;
	ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("res\\MainMenuBG.png"));
	Image img = icon.getImage();
	public MainMenu(boolean DoSomt){
		
	}
	public MainMenu() {
		// Makes a main menu for the game
		frame = new JFrame("Game Main Menu");
		frame.setSize(500, 500);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setLocationRelativeTo(null);
		frame.setIconImage(img);

		JLabel titleLabel = new JLabel("Game Name");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frame.add(titleLabel, BorderLayout.NORTH);

		JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));

		JButton startButton = new JButton("Start Game");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Code to start the game
				Grid grid = new Grid();
				grid.setVisible(true);
				frame.setVisible(false);
				frame.setLocationRelativeTo(null);
				//JOptionPane.showMessageDialog(frame, "Starting the game...");
				
			}
		});
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Code to exit the game
				System.exit(0);
			}
		});

		buttonPanel.add(startButton);
		buttonPanel.add(exitButton);

		frame.add(buttonPanel, BorderLayout.CENTER);

		frame.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			MainMenu mainMenu = new MainMenu();
			
		});
	}
}
