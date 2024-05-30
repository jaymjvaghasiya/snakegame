package snakegame;

import java.awt.Color;

import javax.swing.*;

public class Main extends JFrame 
{
	
	Main()
	{
		super("Snake Game");
		
		add(new GamePanel());
		pack();
		
		setLocationRelativeTo(null);
		setResizable(false);
		
		setVisible(true);
	}
	
	public static void main(String args[])
	{
		new Main();
	}
}
