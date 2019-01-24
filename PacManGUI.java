
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PacManGUI extends JFrame implements ActionListener{

	//adding a menubars
	JMenuBar menuBar = new JMenuBar();

	//music option in music bar
	JMenu musicMenu = new JMenu("Music");
	JMenuItem musicToggle = new JMenuItem("Toggle music on/off");

	//variable to start and stop music.
	int musicPlay = 2;

	//score panel
	static JPanel scorePanel = new JPanel();
	static JLabel scoreLabel = new JLabel();

	//highscore label and lives label
	static JLabel highscoreLabel = new JLabel();
	static JLabel livesLabel = new JLabel();

	//music clips
	static Clip pacManFeverSong;

	static int score = 0; // 1pt per food item eaten
	
	public Timer deathTimer = new Timer(250, this);
	
	public PacManGUI(){

		//create and add the score panel
		extraPanels();

		//Create a board on the GUI frame that will listen to key press
		Board board = new Board();
		setTitle("Pac Man");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(board);
		add(board);

		setVisible(true);
		setResizable(false);
		setLayout(null);

		this.setBounds(50, 50, 600, 700); //set the PacManGUI's pop-up location on screen and size
		board.setBounds(0, 50, 600, 600); //set the Board's location and size on frame (PacManGUI)

		//create the menubar
		this.setJMenuBar(menuBar);

		//create the menu
		menuCreator();
		
		//play music - starts when program runs
		pacManFever();
	}

	public void extraPanels() {

		scorePanel.setLayout(null);

		Font smallFont = new Font ("Arial",Font.BOLD,16);
		Font scoreFont = new Font ("Arial",Font.BOLD,48);
		
		//score label
		scoreLabel.setBounds(210, 0, 400, 50);
		scoreLabel.setBackground(Color.BLACK);		
		scoreLabel.setForeground(Color.YELLOW);
		scoreLabel.setFont(scoreFont);
		
		livesLabel.setBounds(25, -6, 210, 50);
		livesLabel.setBackground(Color.BLACK);		
		livesLabel.setForeground(Color.YELLOW);
		livesLabel.setFont(smallFont);
		

		//highscore label
		highscoreLabel.setBounds(25, 10, 210, 50);
		highscoreLabel.setBackground(Color.BLACK);
		highscoreLabel.setForeground(Color.YELLOW);
		highscoreLabel.setFont(smallFont);

		scorePanel.add(scoreLabel);
		scorePanel.add(livesLabel);
		scorePanel.add(highscoreLabel);

		//score panel settings
		scorePanel.setBounds(0, 0, 600, 50);
		scorePanel.setBackground(Color.BLACK);
		add(scorePanel);

	}


	//method that creates the menubar
	public void menuCreator() {

		//add music option onto menubar
		menuBar.add(musicMenu);
		musicMenu.add(musicToggle);
		musicToggle.addActionListener(this);

	}	

	public void actionPerformed(ActionEvent e) {

		//	   Toggle music on and off when you press 
		if (e.getSource() == musicToggle){				

			if (musicPlay % 2 == 0)
				pacManFeverSong.stop(); //stop music

			else 
				pacManFeverSong.start(); //start music				

			musicPlay++;	
		}
		
		else if (e.getSource() == deathTimer) {
			
			if (PacManGame.death = true) {
				
				setVisible(false);
				dispose();
				
			}
			
		}
		
	}

	public static void pacManFever() {	

		try {

			//define the music clip
			File pacManFeverURL = new File("sounds/Pac-Man Fever.wav");

			pacManFeverSong = AudioSystem.getClip();

			AudioInputStream musicClip = AudioSystem.getAudioInputStream( pacManFeverURL );

			pacManFeverSong.open(musicClip);

			pacManFeverSong.start();

			pacManFeverSong.loop(50); //loops to allow continuous play 


		} catch(Exception ex) {

			System.out.println("Pac-Man Fever.wav not found");

		}

	}

	public static int getScore() {

		return score;

	}

}
