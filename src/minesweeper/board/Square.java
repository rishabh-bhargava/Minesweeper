package minesweeper.board;

public class Square 
{
	//This is the current value shown (either '-' for undug or 'F' for flagged or realValue if dug up)
	private char currentValue;
	//This keeps track of whether the square has been dug up or not
	private boolean isBomb;
	//This variable represents what it will be once it's dug up
	
	public Square(boolean bomb)
	{
		this.currentValue = '-';
		this.isBomb = bomb;
	}
	
	public char getCurrentValue()
	{
		return this.currentValue;
	}
	
	public boolean isBomb()
	{
		return this.isBomb;
	}
	
	public void setCurrentValue(char n)
	{
		this.currentValue = n;
	}
	
	public void bombWasDug()
	{
		this.isBomb = false;
	}
}
