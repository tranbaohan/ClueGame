package ClueGame;

import java.awt.Color;
import java.awt.Graphics;

public class RoomCell extends BoardCell {
	private String title="";
	public void setTitle(String str){
		title = str;
	}
	
	public enum DoorDirection {
		UP, DOWN, LEFT, RIGHT, NONE;
	}
	
	private DoorDirection doorDirection;
	private char roomInitial;
	
	public RoomCell(char roomInitial, DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
		this.roomInitial = roomInitial;
	}
	
	@Override
	public boolean isWalkaway() {
		return false;
	}

	@Override
	public boolean isRoom() {
		return true;
	}

	@Override
	public boolean isDoorway() {
		if(doorDirection != DoorDirection.NONE) {
			return true;
		}
		return false;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public char getInitial() {
		return roomInitial;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof RoomCell) {
			RoomCell tempRoom = (RoomCell)o;
			if(tempRoom.getInitial()==roomInitial) {
				return true;
			}
		}
		return false;
	}
	
	public void draw(Graphics g, Board board){
		g.setColor(Color.GRAY);
		g.fillRect(toPixel(this.getCol()), toPixel(this.getRow()), WIDTH, HEIGHT);
		if (isDoorway()){
			g.setColor(Color.BLUE);
			switch(this.getDoorDirection()){
			case RIGHT:
				g.fillRect((int) (toPixel(this.getCol()) + WIDTH*.8), toPixel(this.getRow()), WIDTH/5, HEIGHT);
				break;
			case LEFT:
				g.fillRect(toPixel(this.getCol()), toPixel(this.getRow()), WIDTH/5, HEIGHT);
				break;
			case UP:
				g.fillRect(toPixel(this.getCol()), toPixel(this.getRow()), WIDTH, HEIGHT/5);
				break;
			case DOWN:
				g.fillRect(toPixel(this.getCol()), (int) (toPixel(this.getRow()) + WIDTH*.8), WIDTH, HEIGHT/5);
				break;
			}
		}
		if(title!="") {
			g.setColor(Color.BLACK);
			g.drawString(title, toPixel(this.getCol()), toPixel(this.getRow()));
		}
	}

}
