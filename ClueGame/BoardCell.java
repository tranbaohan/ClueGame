package ClueGame;

public abstract class BoardCell {
	private int row, col;
	
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
	
	public abstract void draw();
}
