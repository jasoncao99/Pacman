import javax.swing.*;

public class PacMan extends Mover {

	public static final ImageIcon[][] IMAGE =

		{	
			//image set for when pacman is moving left
			{ new ImageIcon("images/pacLeftClosed.bmp"),
			  new ImageIcon("images/pacLeftOpen.bmp")},

			//image set for when pacman is moving up
			{ new ImageIcon("images/pacUpClosed.bmp"),
			  new ImageIcon("images/pacUpOpen.bmp")},

			//image set for when pacman is moving right
			{ new ImageIcon("images/pacRightClosed.bmp"),
			  new ImageIcon("images/pacRightOpen.bmp")},

			//image set for when pacman is moving down
			{ new ImageIcon("images/pacDownClosed.bmp"),
			  new ImageIcon("images/pacDownOpen.bmp")},

		};

	public PacMan() {
		this.setIcon(IMAGE[0][0]); //left closed when game starts
	}
	
}


