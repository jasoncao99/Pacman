import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class EndMenu extends JFrame implements ActionListener {
	
	//End screen
	static JPanel endPanel = new JPanel();

	//end screen buttons
	static JButton playButton = new JButton("Play Again");
	static JButton exitButton = new JButton("Exit");
	
	public EndMenu() {
		
		this.setBounds(50, 50, 600, 700);
		setTitle("PacMan");
		setVisible(true);
		setResizable(false);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		endPanel();
	}
	
	public void endPanel() {
		
		Font endFont = new Font ("Arial",Font.BOLD,78);
		
		//end panel
		endPanel.setLayout(null);
		endPanel.setVisible(true);
		endPanel.setBounds(0,0,600,700);
		endPanel.setBackground(Color.BLACK);		
		endPanel.setFont(endFont);

		//play button
		playButton.setBounds(0,0,600,350);
		playButton.setFont(endFont);
		playButton.setBackground(Color.BLACK);
		playButton.setForeground(Color.YELLOW);
		playButton.addActionListener(this);
		endPanel.add(playButton);

		//exit button
		exitButton.setBounds(0,350,600,350);
		exitButton.setFont(endFont);
		exitButton.setBackground(Color.BLACK);
		exitButton.setForeground(Color.YELLOW);
		exitButton.addActionListener(this);
		endPanel.add(exitButton);
		
		this.add(endPanel);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
  		//if playButton is clicked
		if(e.getSource() == playButton){
			
			PacManGUI.score = 0;
			PacManGUI.pacManFeverSong.stop();
			setVisible(false);
			dispose();
			
			PacManGame.lives = 1;
			
			new PacManGUI();
				
		}	   //if exitButton is clicked 
		else if(e.getSource() == exitButton){

			System.exit(0); //exit program		
		}
		
	}
	
}
