import javax.swing.*;

public class Ghost extends Mover {

	public static final ImageIcon[] IMAGE =
		{
		 new ImageIcon("images/homer.jpg"),
		 new ImageIcon("images/boba.jpg"),
		 new ImageIcon("images/r2d2.jpg")	
		};
		
		public Ghost(int gNum) {
			this.setIcon(IMAGE[gNum]);
		}
	
}
	
