import java.io.*;
import java.util.*;

public class highScore {

	static int highscore;

	public static void writeFile() {

		try {
			FileWriter fw = new FileWriter("highscore.txt");
			PrintWriter pw = new PrintWriter(fw);

			pw.println(PacManGUI.score);

			pw.close();

		} catch (IOException e) {
			System.out.println("Error!");
		}


	}

	public static int getHighscore() {

		return highscore;
	}

	public static void highscoreInput() {

		try {
			Scanner input = new Scanner(new File("highscore.txt"));

			highscore = input.nextInt();

		} catch (FileNotFoundException e) {
			System.out.println("File does not exist");
		}


	}

}
