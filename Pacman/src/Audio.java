import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {

	//music clips
	static Clip pacManChomp;
	static Clip pacKilled;
	static Clip fruitEat;

	public static void fruitEatSound() {

		try {

			//define the music clip
			File cherryEatURL = new File("sounds/fruiteat.wav");

			fruitEat = AudioSystem.getClip();

			AudioInputStream fruitEatClip = AudioSystem.getAudioInputStream( cherryEatURL );

			fruitEat.open(fruitEatClip);

			fruitEat.start();


		} catch(Exception ex) {

			System.out.println("fruiteat.wav not found.");

		}

	}
	
	
	//dead sound
	public static void deadSound() {

		try {

			//define the music clip
			File pacKilledURL = new File("sounds/killed.wav");

			pacKilled = AudioSystem.getClip();

			AudioInputStream pacKilledClip = AudioSystem.getAudioInputStream( pacKilledURL );

			pacKilled.open(pacKilledClip);

			pacKilled.start();


		} catch(Exception ex) {

			System.out.println("pacchomp.wav not found.");

		}

	}
	
	//method that plays the chomping sound when pacman eats a pellet
	public static void pacManChomp() {

		try {

			//define the music clip
			File pacChompUrl = new File("sounds/pacchomp.wav");

			pacManChomp = AudioSystem.getClip();

			AudioInputStream pacChompClip = AudioSystem.getAudioInputStream( pacChompUrl );

			pacManChomp.open(pacChompClip);

			pacManChomp.start();


		} catch(Exception ex) {

			System.out.println("pacchomp.wav not found.");

		}

	}
}
