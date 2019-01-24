/*
	Sounds - Pacman Fever, pacchomp, fruit eat, toggle pacman fever on and off
	Score
	AI
	highscore saving
	Cherry
	Teleporter
	Changed ghosts picture
	game speeds up with higher score
	Play again
	Lives
	
*/
public class PacManGame {
	
	static int lives = 1;
	
	static boolean death = false;
	
	public static void main (String[] args){
		
		highScore.highscoreInput();
		
		new PacManGUI();
		
	}
	
}
