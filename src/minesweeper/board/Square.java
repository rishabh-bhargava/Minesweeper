package minesweeper.board;

public class Square 
{
	//This is the current value shown (either '-' for undug or 'F' for flagged or realValue if dug up)
	private char currentValue;
	//This keeps track of whether the square has been dug up or not
	private boolean isBomb;
	//This variable represents what it will be once it's dug up
	
	/**
	 * Constructor to create a square with external representation as '-'
	 * @param bomb which is true if bomb present. else false
	 */
	public Square(boolean bomb)
	{
		this.currentValue = '-';
		this.isBomb = bomb;
	}
	
	/**
	 * Method to return current value of the square
	 * @return currentValue as char
	 */
	public char getCurrentValue()
	{
		return this.currentValue;
	}
	
	/**
	 * Method to return whether the square is a bomb or not
	 * @return true if bomb, else false
	 */
	public boolean isBomb()
	{
		return this.isBomb;
	}
	
	/**
	 * Method that sets currentValue to new value n
	 * @param n is the new value for the given square
	 */
	public void setCurrentValue(char n)
	{
		this.currentValue = n;
	}
	
	/**
	 * This method is called when a square containing a bomb is dug. It sets the isBomb for the square as false.
	 */
	public void bombWasDug()
	{
		this.isBomb = false;
	}
	
	/**
	 * This method returns whether the current square has been dug or not
	 * @return true if has been dug, else false
	 */
	public boolean isDug()
	{
		if(this.currentValue == ' ' || this.currentValue == '-' || this.currentValue == 'F')
			return false;
		return true;
	}
}
