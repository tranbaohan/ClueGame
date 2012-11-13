package ClueGame;

import java.awt.Graphics;

public abstract class BoardCell {
	private int row, col;
	public static final int WIDTH = 25;
	public static final int HEIGHT = 25;
	
	public abstract boolean isWalkaway();
	public abstract boolean isRoom();
	public abstract boolean isDoorway();
	
	public int getRow(){
		return row;
	}
	
	public int getCol(){
		return col;
	}
	
	public void setRow(int num){
		row = num;
	}
	
	public void setCol(int num){
		col = num;
	}
	
	abstract public void draw(Graphics g, Board board);
	
	static public int toPixel(int x){
		return x*WIDTH;
	}
}
