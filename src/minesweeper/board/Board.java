package minesweeper.board;

import java.util.Random;

public class Board 
{
	//Dimension of board
	final int size;
	
	//2-D array of squares (length = breadth = n)
	final Square[][] squares;
	
	public Board(int n)
	{
		this.size = n;
		squares = new Square[n][n];
		this.setSquares(n);
	}
	
	private synchronized void setSquares(int n)
	{
		for(int i = 0; i < n; i++)
		{
			for(int j = 0; j < n; j++)
			{
				Random rand = new Random();
				if(rand.nextFloat()>0.75)
				{
					squares[i][j] = new Square(true);
				}
				else
				{
					squares[i][j] = new Square(false);
				}
			}
		}
	}
	
	public synchronized String look()
	{
		String result = "";
		for(int i = 0; i< this.size; i++)
		{
			for(int j = 0; j< this.size; j++)
			{
				result+=squares[i][j].getCurrentValue();
			}
			result += '\n';
		}
		return result;
	}
	
	private synchronized void setCurrentValueSquareAfterDig(int row, int column)
	{
		int count = 0;
		count += findBombInLocation(row-1, column-1);
		count += findBombInLocation(row, column-1);
		count += findBombInLocation(row+1, column-1);
		count += findBombInLocation(row-1, column);
		count += findBombInLocation(row+1, column);
		count += findBombInLocation(row-1, column+1);
		count += findBombInLocation(row, column+1);
		count += findBombInLocation(row+1, column+1);
		
		squares[row][column].setCurrentValue((char)count);
	}
	
	private synchronized int findBombInLocation(int r, int c)
	{
		if(r < 0 || c < 0 || r >= this.size || c>=this.size)
		{
			return 0;
		}
		else
		{
			if(squares[r][c].isBomb())
				return 1;
		}
		return 0;
	}
	
}
