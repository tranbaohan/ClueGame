package ClueGame;

import java.awt.Color;
import java.awt.Graphics;

public class WalkwayCell extends BoardCell {
	
	@Override
	public boolean isWalkaway() {
		return true;
	}

	@Override
	public boolean isRoom() {
		return false;
	}

	@Override
	public boolean isDoorway() {	
		return false;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof WalkwayCell) {
			WalkwayCell tempWalk = (WalkwayCell)o;
			if(tempWalk.getCol()== getCol() && tempWalk.getRow() == getRow()) {
				return true;
			}
		}
		return false;
	}
	
	public void draw(Graphics g, Board board){
		g.setColor(Color.YELLOW);
		g.fillRect(toPixel(this.getCol()), toPixel(this.getRow()), WIDTH, HEIGHT);
		g.setColor(Color.BLACK);
		g.drawRect(toPixel(this.getCol()), toPixel(this.getRow()), WIDTH, HEIGHT);
	}

}
