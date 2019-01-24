import javax.swing.Icon;
import javax.swing.JLabel;


public abstract class Mover extends JLabel{

	//fields
	private int row; 				//current row 
	private int column;				//current column
	
	private int dRow;				//current row direction
	private int dColumn;			//current column direction
	
	private boolean isDead;			//death status

	//getters and setters
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getdRow() {
		return dRow;
	}

	public void setdRow(int dRow) {
		this.dRow = dRow;
	}

	public int getdColumn() {
		return dColumn;
	}

	public void setdColumn(int dColumn) {
		this.dColumn = dColumn;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}
	
	public void move() {
		row += dRow;		
		column += dColumn; 
	}

	public void setDirection(int dir) {
		
		dRow = 0;
		dColumn = 0;
		
		if (dir == 0)			//left
			dColumn = -1;
		
		else if (dir == 1)		//up
			dRow = -1;
		
		else if (dir == 2)		//right
			dColumn = 1;
		
		else if (dir == 3)		//down
			dRow = 1;
		
	}
	
	public int getDirection() {
		
		if (dRow == 0 && dColumn == -1)				//left
			return 0;
		
		else if (dRow == -1 && dColumn == 0)		//up
			return 1;
		
		else if (dRow == 0 && dColumn == 1)			//right
			return 2;
		
		else 										//down
			return 3;
		
	}
	
	public int getNextRow() {
		
		return row + dRow;
		
	}
	
	public int getNextColumn() {
		
		return column + dColumn;
		
	}
}
