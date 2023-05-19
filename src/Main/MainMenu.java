package Main;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu {
	 JFrame frame;
	    boolean hasWon = false;
	    public MainMenu(boolean doSmt){
	    	
	    }

	    public MainMenu() {
	        // Makes a main menu for the game
	        frame = new JFrame("Game Main Menu");
	        frame.setSize(500, 500);
	        frame.setResizable(false);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setLocationRelativeTo(null);
	        ImageIcon icon = new ImageIcon("res\\MainMenuBG.jpg");
	        
	        JLabel background = new JLabel();
	        background.setIcon(icon);
	        background.setHorizontalAlignment(SwingConstants.CENTER);
	        frame.add(background);

	        JLabel titleLabel = new JLabel("Game Name");
	        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
	        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
	        frame.add(titleLabel);


	        JButton startButton = new JButton("Start Game");
	        startButton.setIcon(icon);
	        startButton.setBounds(0, 0, 100, 100);
	        startButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                // Code to start the game
	                Grid grid = new Grid();
	                grid.setVisible(true);
	                frame.setLocationRelativeTo(null);
	                //JOptionPane.showMessageDialog(frame, "Starting the game...");
	            }
	        });

	       


	        frame.add(startButton);

	        frame.setVisible(true);
	    }

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            MainMenu mainMenu = new MainMenu();
	        });
	    }
}
