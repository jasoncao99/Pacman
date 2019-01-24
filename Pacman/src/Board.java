
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements KeyListener, ActionListener{

	private Timer gameTimer = new Timer(250,this);		//moving
	private Timer animateTimer = new Timer(250,this);	//chomp

	//	setting words to match up with images 
	private final ImageIcon WALL = new ImageIcon ("images/StdWall.bmp");
	private final ImageIcon FOOD = new ImageIcon ("images/StdFood.bmp");
	private final ImageIcon BLANK = new ImageIcon ("images/Black.bmp");
	private final ImageIcon DOOR = new ImageIcon ("images/Black.bmp");
	private final ImageIcon SKULL = new ImageIcon ("images/Skull.bmp");
	private final ImageIcon CHERRY = new ImageIcon ("images/Cherry.bmp");
	private final ImageIcon TELEPORTER = new ImageIcon ("images/teleporter.png");

	private JLabel[][] cell = new JLabel [25][27];	//	images for the grid in the pacman maze 
	private char[][] maze = new char[25][27]; 		//	chars come from text file

	private PacMan pacMan;
	private Ghost[]ghost = new Ghost [3];

	private int pellets = 0; // amount of food

	private int pStep; //steps for animating Pacman's chomp

	/**
	 * Construct the maze
	 */

	public Board() {

		//1.Set the layout (grid), background (black)
		setLayout(new GridLayout(25,27));
		setBackground(Color.black);

		//2. Create Pacman and the ghosts
		pacMan = new PacMan();
		ghost[0] = new Ghost(0);
		ghost[1] = new Ghost(1);
		ghost[2] = new Ghost(2);

		//3. Load the maze
		loadBoard();

	}

	/**
	 * Load the maze onto the screen from a text file
	 */
	private void loadBoard() {

		int r =0; // row

		//1. Open the maze text file for input
		Scanner input;

		try{
			input = new Scanner(new File ("maze.txt"));

			//2. Cycle through all the rows in the maze file
			while(input.hasNext()) {

				//2.1 Read the next line from the maze file
				maze[r] = input.nextLine().toCharArray();

				//2.2 For each row cycle through all the columns 
				for (int c = 0; c < maze[r].length; c++) {
					cell[r][c] = new JLabel(); // create a new picture

					//Depending on the symbol in the maze file
					//Note:This only has to be done for squares

					//2.2.1 If the symbol is a W then assign a wall icon

					if (maze[r][c]== 'W')
						cell[r][c].setIcon(WALL);

					//2.2.2 If the symbol is a F then assign a food icon

					else if (maze[r][c] == 'F')
						cell[r][c].setIcon(FOOD);

					//2.2.3 If the symbol is a P then assign Pac Man's icon

					else if (maze[r][c] == 'P'){
						cell[r][c].setIcon(pacMan.getIcon());
						pacMan.setRow(r);
						pacMan.setColumn(c);
						pacMan.setDirection(0); //start left
					}

					//2.2.4 If the symbol is a X then assign a blank icon
					else if (maze[r][c] == 'X')
						cell[r][c].setIcon(BLANK);

					else if (maze[r][c] == '0' || maze[r][c] == '1' || maze[r][c] == '2'){
						int gNum = Character.getNumericValue(maze[r][c]);

						cell[r][c].setIcon(ghost[gNum].getIcon());
						ghost[gNum].setRow(r);
						ghost[gNum].setColumn(c);
					}

					else if (maze[r][c] == 'D') 
						cell[r][c].setIcon(DOOR);

					else if (maze[r][c] == 'C') 
						cell[r][c].setIcon(CHERRY);
					
					else if (maze[r][c] == 'T') 
						cell[r][c].setIcon(TELEPORTER);					
					
					else 
						cell[r][c].setIcon(BLANK);

					add(cell[r][c]);
				}

				//2.3 increment the rows
				r++;
			}

			//3. Close the maze text file
			input.close();

		} catch (FileNotFoundException e) {

			System.out.println("File not found");

		}

	}

	public void actionPerformed(ActionEvent e) {

		//1. 	If the action is from the game timer
		if (e.getSource() == gameTimer) { 
			
			PacManGUI.livesLabel.setText("Lives left: " + PacManGame.lives);
			PacManGUI.highscoreLabel.setText("Highscore:" + highScore.getHighscore());
			PacManGUI.scoreLabel.setText("Score: " + PacManGUI.score);

			//1.1. 	Then move the Pacman and the ghosts
			performMove(pacMan);
			moveGhosts();
			
			if (PacManGUI.score > 100)
				gameTimer.setDelay(150);
			
			else if (PacManGUI.score > 200)
				gameTimer.setDelay(100);
			
			else if (PacManGUI.score > 250)
				gameTimer.setDelay(50);

		} 
		//2. 	Otherwise, if the aciton is the animation timer
		else if (e.getSource() == animateTimer) {

			//2.1. 	Animate PacMan through the current step
			animatePacMan(pStep);

			//2.2. 	Increment the step number
			pStep++;

			//2.3. If the step is the last step then reset the step
			if (pStep == 3)
				pStep = 0;

		} 
		
	}

	//	Animation: Open the mouth, draw black square, close mouth
	private void animatePacMan(int pStep) {

		//1. 	If it is step 0 of animation
		if (pStep == 0){

			//1.1. 	Open the mouth in current cell
			cell[pacMan.getRow()][pacMan.getColumn()].setIcon(PacMan.IMAGE[pacMan.getDirection()][1]);

			//1.2. 	Delay the animation timer
			animateTimer.setDelay(100);

		}

		//2. 	Otherwise if it is step 1 of animation
		else if (pStep == 1){

			//2.1. 	Blank the current cell
			cell[pacMan.getRow()][pacMan.getColumn()].setIcon(BLANK);

		}


		//3. 	Otherwise if it is step 2 of animation
		else if (pStep == 2){

			//3.1. 	Move Pacman
			pacMan.move();

			//3.2. 	If there is any food in the new square
			if (maze[pacMan.getRow()][pacMan.getColumn()] == 'F') {

				//3.2.1.	Increment the score
				PacManGUI.score++;

				//3.2.2. 	Mark the maze at the new position to empty
				maze[pacMan.getRow()][pacMan.getColumn()] = 'E';

				Audio.pacManChomp();

			} 

			else if (maze[pacMan.getRow()][pacMan.getColumn()] == 'C') {

				PacManGUI.score += 100;

				maze[pacMan.getRow()][pacMan.getColumn()] = 'E';

				Audio.fruitEatSound();

			}
			
			else if (maze[pacMan.getRow()][pacMan.getColumn()] == 'T') {

				PacManGUI.score ++;
				
				pacMan.setRow(2);
				pacMan.setColumn(24);

			}
			
			//3.3. 	Stop the animation timer
			animateTimer.stop();
			
			//3.4
			if (pacMan.isDead())
				cell[pacMan.getRow()][ pacMan.getColumn()].setIcon
				(SKULL);
			//3.5

			else
				cell [ pacMan.getRow()][pacMan.getColumn()].setIcon
				(PacMan.IMAGE[pacMan.getDirection()][0]);

		}

	}

	public void keyPressed(KeyEvent key) {

		//1. 	If the game isn;t running and pacMan is alive

		if (gameTimer.isRunning() == false && pacMan.isDead() == false)
			gameTimer.start();

		//2. 	If PacMan is still alive and the game is not over then
		if(pacMan.isDead() == false && PacManGUI.score!= pellets) {

			//2.1. 	Track direction based on the key pressed
			//		- 37 since ASCII codes for cursor keys start
			//		at 37:
			int direction = key.getKeyCode() - 37;

			//2.2.	Change direction of PacMan
			//		37-left, 38-up, 39-right, 40-down
			if (direction == 0 &&  maze[pacMan.getRow()][pacMan.getColumn() - 1] != 'W')
				pacMan.setDirection(0);

			else if (direction == 1 &&  maze[pacMan.getRow() - 1][pacMan.getColumn()] != 'W')
				pacMan.setDirection(1);

			else if (direction == 2 &&  maze[pacMan.getRow()][pacMan.getColumn() + 1] != 'W')
				pacMan.setDirection(2);

			else if (direction == 3 &&  maze[pacMan.getRow() + 1][pacMan.getColumn()] != 'W')
				pacMan.setDirection(3);

		}

	}

	// Mandatory method to implement KeyListener interface
	public void keyReleased(KeyEvent arg0) {}
	// Mandatory method to implement KeyListener interface
	public void keyTyped(KeyEvent arg0) {}

	private void performMove(Mover mover){
	
		//1. If a mover is at a door then teleport to other side
		if (mover.getColumn() == 1){
	
			mover.setColumn(24);
	
			cell[12][1].setIcon(DOOR);
	
		}
	
		else if (mover.getColumn() == 25){
	
			mover.setColumn(2);
	
			cell[12][25].setIcon(DOOR);
	
		}
	
		//2. 	If there is no wall in the direction that
		//		the Mover objects wants to go then
		if (maze[mover.getNextRow()][mover.getNextColumn()] != 'W'){
	
			//2.1. 	If the Mover object is PacMan then animate a 'chomp'
			if (mover == pacMan)
				animateTimer.start();
	
			//2.2. 	Otherwise the Mover is a ghost
			else{
	
				//2.2.1.	If the cell where the Ghost has food then reset
				if (maze[mover.getRow()][mover.getColumn()] == 'F')
					cell[mover.getRow()][mover.getColumn()].setIcon(FOOD);
	
				//2.2.2. 	Otherwise reset the cell to blank
				else
					cell[mover.getRow()][mover.getColumn()].setIcon(BLANK);
	
				//2.2.3. Move the ghost's position
				mover.move();
	
				//2.2.4. If a collision has occured then death occurs
				if (collided()) {
					
					pacMan.setDead(true);
	
					PacManGame.death = true;
					
					death();
					
					checkDeath();
					
				}
	
				//2.2.5. Otherwise update the picture on the screen
				else
					cell[mover.getRow()][mover.getColumn()].setIcon(mover.getIcon());
			}
	
	
		}
	
	}

	//method that runs when pacman dies
	private void death() {	
	
		PacManGUI.pacManFeverSong.stop(); //stop the music
	
		//1. 	Set pacMan dead
		pacMan.setDead(true);
	
		//2. 	Stop the game timers
		stopGame();
	
		//3. 	Determine the current location of Pacman on the screen and make it a skull
		cell[pacMan.getRow()][pacMan.getColumn()].setIcon(SKULL);
	
		Audio.deadSound();  //play the death sound
	
		//4.	Save the highscore
		if (PacManGUI.score > highScore.getHighscore()) 
			highScore.writeFile();		
		
		if (PacManGame.lives > 0 && pacMan.isDead()) {
			
			pacMan.setDead(false);
			
			PacManGame.death = false;
			PacManGame.lives--;
			
			PacManGUI.score = 0;
			
			new PacManGUI();
			
		}
	
	}

	private void checkDeath(){
		
		if (PacManGame.death = true && pacMan.isDead())
			new EndMenu();
			
	}

	//method that runs when pac man and a ghost collide
	private boolean collided(){

		//1. Cycle through all the ghosts to see if anyone has collided
		for (Ghost g: ghost) {

			//1.1. If the ghost is in the same location then return hit
			if (g.getRow() == pacMan.getRow() && g.getColumn() == pacMan.getColumn())
				return true;
		}

		//2. 	If no ghosts were in the same location then return
		return false;

	}

	private void stopGame() {

		//1. 	if PacMan is dead or all the food is eaten then 
		//		stop the timers
		if (pacMan.isDead() || PacManGUI.score == pellets + 100) {

			animateTimer.stop(); //start the animations
			gameTimer.stop();	 //start the game

		}

	}

	
	
	private void moveGhosts(){

		//1. 	Cycle through all the ghosts
		for (Ghost g: ghost) { 		//enhanced for loop
			
			int dir = 0;

			//1.1. 	Keep selecting random direction to avoid "back tracking"
			do {

				dir = (int)(Math.random() * 4);  

			} while (Math.abs(g.getDirection() - dir) == 2);
				
			if (maze[g.getRow()][g.getColumn()] == 'U')
				g.setDirection(1);

			else if (maze[g.getRow()][g.getColumn()] == 'L')
				g.setDirection(0);

			else if (maze[g.getRow()][g.getColumn()] == 'R')
				g.setDirection(2);			

			//if ghost is in the same column as PacMan, hunt him down
			else if (g.getColumn() == pacMan.getColumn()  && maze[g.getNextRow()][g.getNextColumn()] != 'W'){


				//check if pacman is on top of the ghost
				if (g.getRow() - pacMan.getRow() > 0) 
					g.setDirection(1); //up
				else 
					g.setDirection(3); //down

			}

			//if ghost is in the same row as PacMan, hunt him down
			else if(g.getRow() == pacMan.getRow() && maze[g.getNextRow()][g.getNextColumn()] != 'W'){

				//check if pacman is to the right of the ghost
				if (g.getColumn() - pacMan.getColumn() > 0)
					g.setDirection(0);//left

				else
					g.setDirection(2);//right		

			}
			else if (g.getNextColumn() != 'W' && g.getNextRow() != 'W' && g.getNextRow() != 'M')
				g.setDirection(dir);

			//1.3. 	Move the ghost
			performMove(g);
			
		}

	}

}	


