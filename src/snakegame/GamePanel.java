package snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener
{
	
//	private ImageIcon snaketitle = new ImageIcon(ClassLoader.getSystemResource("icons/snaketitle.jpg"));
	
	private int dots;
	
	private Image head;
	private Image dot;
	private Image apple;
	
	private int XDimension = 300;
	private int YDimension = 300;
	
	private final int ALL_DOTS = 900;
	private final int DOT_SIZE = 10;
	private final int RANDOM_POS = 10;
	
	private int apple_x;
	private int apple_y;
	
	private final int x[] = new int[ALL_DOTS];
	private final int y[] = new int[ALL_DOTS];
	
	private boolean leftDirec = false;
	private boolean rightDirec = true;
	private boolean upDirec = false;
	private boolean downDirec = false;
	
	private boolean inGame = true;
	
	private Timer timer;
	
	GamePanel()
	{
		addKeyListener(new TAdapter());
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(XDimension, XDimension));
		setFocusable(true);
		loadImage();
		initGame();
	}
	
	public void loadImage()
	{
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/apple.png"));
		apple = i1.getImage();
		ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("icons/head.png"));
		head = i2.getImage();
		ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("icons/dot.png"));
		dot = i3.getImage();
	}
	
	public void initGame()
	{
		dots = 3;
		for(int i=0;i<dots;i++)
		{
			y[i] = 50;
			x[i] = 50 - i * DOT_SIZE;
		}
		
		locateApple();
		timer = new Timer(140, this);
		timer.start();
	}
	
	public void locateApple()
	{	
		int r;
		r = (int)(Math.random() * RANDOM_POS);
		apple_x = r * DOT_SIZE;
		r = (int)(Math.random() * RANDOM_POS);
		apple_y = r * DOT_SIZE;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g)
	{
		if(inGame)
		{			
			g.drawImage(apple, apple_x, apple_y, this);
			for(int i=0;i<dots;i++)
			{
				if(i==0)
				{
					g.drawImage(head, x[i], y[i], this);
				}
				else
				{
					g.drawImage(dot, x[i], y[i], this);
				}
			}
			Toolkit.getDefaultToolkit().sync();
		}
		else
		{
			gameOver(g);
		}
	}
	
	public void gameOver(Graphics g)
	{
		String msg = "Game Over!";
		Font font = new Font("SAN_SERIF", Font.BOLD, 14);
		FontMetrics metrics = getFontMetrics(font);
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString(msg, (XDimension - metrics.stringWidth(msg))/2, YDimension/2);
	}
	
	public void move()
	{
		for(int i=dots;i>0;i--)
		{
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		if(leftDirec)
		{
			x[0] = x[0] - DOT_SIZE;
		}
		if(rightDirec)
		{
			x[0] = x[0] + DOT_SIZE;
		}
		if(upDirec)
		{
			y[0] = y[0] - DOT_SIZE;
		}
		if(downDirec)
		{
			y[0] = y[0] + DOT_SIZE;
		}
	}
	
	public void checkApple()
	{
		if((x[0] == apple_x) && (y[0] == apple_y))
		{
			dots++;
			locateApple();
		}
	}
	
	public void checkCollision()
	{
		for(int i=dots;i>0;i--)
		{
			if((i>4) && (x[0] == x[i]) && (y[0] == y[i]))
			{
				inGame = false;
			}
		}
		if(x[0] >= XDimension || y[0] >= YDimension || x[0] < 0 || y[0] < 0)
		{
			inGame = false;
		}
		if(!inGame)
		{
			timer.stop();
		}
		
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if(inGame)
		{			
			checkApple();
			checkCollision();
			move();
		}
		repaint();
	}
	
	/*
	public void paint(Graphics g)
	{
		super.paint(g);
		g.setColor(Color.WHITE);
		g.drawRect(23, 10, 852, 55);
		snaketitle.paintIcon(this, g, 24, 11);
	}
	*/
	
	public class TAdapter extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_LEFT && (!rightDirec))
			{
				leftDirec = true;
				upDirec = false;
				downDirec = false;
			}
			if(key == KeyEvent.VK_RIGHT && (!leftDirec))
			{
				rightDirec = true;
				upDirec = false;
				downDirec = false;
			}
			if(key == KeyEvent.VK_UP && (!downDirec))
			{
				upDirec = true;
				rightDirec = false;
				leftDirec = false;
			}
			if(key == KeyEvent.VK_DOWN && (!upDirec))
			{
				downDirec = true;
				rightDirec = false;
				leftDirec = false;
			}
		}
	}
}
