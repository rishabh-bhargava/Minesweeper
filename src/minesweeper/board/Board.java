package minesweeper.board;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 * Rep invariant- 	size > 0
 * 					board is always square 
 * @author Rishabh
 *
 */

public class Board 
{
	//Dimension of board
	final int size;
	
	//2-D array of squares (length = breadth = n)
	final Square[][] squares;
	
	/**
	 * This constructor constructs a board using size input wit random placements of bombs
	 * @param n: Size of board
	 */
	public Board(int n)
	{
		this.size = n;
		squares = new Square[n][n];
		this.setSquares();
		this.checkRep();
	}
	
	/**
	 * This constructor creates a board from an input file. File should be structured as follows-
	 * FILE :== LINE+
	 * LINE :== (VAL SPACE)* VAL NEWLINE
	 * VAL :== 0 | 1
	 * SPACE :== " "
	 * NEWLINE :== "\r?\n"
	 * 
	 * @param file with correct syntax of a board.
	 * @throws IOException: In the case of file errors
	 */
	public Board(File file) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String nextLine  = reader.readLine();
		String[] tokens = nextLine.split(" ");
		this.size = tokens.length;
		squares = new Square[this.size][this.size];
		for(int j = 0; j< size; j++)
		{
			squares[0][j] = new Square(tokens[j].equals("1"));
		}
		for(int i = 1; i< size; i++)
		{
			nextLine = reader.readLine();
			tokens = nextLine.split(" ");
			for(int j= 0;j < size; j++)
			{
				squares[i][j] = new Square(tokens[j].equals("1"));
			}
		}
		this.checkRep();
	}
	
	/**
	 * Make sure rep invariant is maintained. Assertions need to be enabled to view effects.
	 * @param none
	 */
	private void checkRep()
	{
		assert this.size > 0;
		assert this.squares.length == this.squares[0].length;
	}
	
	/**
	 * Assigns the squares in the board randomly as having a bomb or not
	 * Criteria: 25% chance of having a bomb
	 * 
	 */
	private synchronized void setSquares()
	{
		for(int i = 0; i < this.size; i++)
		{
			for(int j = 0; j < this.size; j++)
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
	
	/**
	 * Performs a look operation on the board
	 * @return Minesweeper Board in a String form
	 */
	public synchronized String look()
	{
		String result = "";
		for(int i = 0; i< this.size; i++)
		{
			for(int j = 0; j< this.size; j++)
			{
				result+=squares[i][j].getCurrentValue();
				if(j!= this.size -1)
				{
					result += " ";
				}
			}
			result += '\n';
		}
		return result;
	}
	
	/**
	 * Flags the location on the board whose row, column are given if valid indices, and untouched
	 * @param row of board 
	 * @param column of board
	 * @return String representation of Board
	 */
	public synchronized String flag(int row, int column)
	{
		if(!(row < 0 || column < 0 || row >= this.size || column >=this.size) && squares[row][column].getCurrentValue() == '-')
			squares[row][column].setCurrentValue('F');
		return this.look();
	}
	
	/**
	 * Deflags the location on board whose row, column are given if valid indices, and flagged
	 * @param row of board
	 * @param column of board
	 * @return String representation of Board
	 */
	public synchronized String deflag(int row, int column)
	{
		if(!(row < 0 || column < 0 || row >= this.size || column >=this.size) && squares[row][column].getCurrentValue() == 'F')
			squares[row][column].setCurrentValue('-');
		return this.look();
	}
	
	public synchronized String dig(int row, int column)
	{
		if(row < 0 || column < 0 || row >= this.size || column >=this.size || squares[row][column].getCurrentValue() != '-')
			return this.look();
		
		if(squares[row][column].isBomb())
		{
			squares[row][column].bombWasDug();
			squares[row][column].setCurrentValue((char)this.getCurrentValueSquareAfterDig(row, column));
			return "BOOM!" + '\n';
		}
		int neighboursWithBombs = this.getCurrentValueSquareAfterDig(row, column);
		if(neighboursWithBombs == 0)
		{
			squares[row][column].setCurrentValue(' ');
			
		}
		else
		{
			squares[row][column].setCurrentValue((char)neighboursWithBombs);
		}
		return this.look();
	}
	
	private synchronized int getCurrentValueSquareAfterDig(int row, int column)
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
		
		return count;
	}
	
	/**
	 * Finds number of surrounding bombs
	 * @param r Row on Board
	 * @param c Column on Board
	 * @return No of surrounding bombs
	 */
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
