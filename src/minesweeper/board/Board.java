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
	 * @return String representation of Board irrespective of condition on indices
	 */
	public synchronized String flag(int row, int column)
	{
		if(this.isValid(row, column) && squares[row][column].getCurrentValue() == '-')
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
		if(this.isValid(row, column) && squares[row][column].getCurrentValue() == 'F')
			squares[row][column].setCurrentValue('-');
		return this.look();
	}
	
	public synchronized String dig(int row, int column)
	{
		//Return look
		if(row < 0 || column < 0 || row >= this.size || column >=this.size || squares[row][column].getCurrentValue() != '-')
			return this.look();
		
		//Case that you dig a bomb
		if(squares[row][column].isBomb())
		{
			squares[row][column].bombWasDug();
			modifyDugNeighbourSquaresAfterBomb(row, column);
			char c = this.getCurrentValueSquareAfterDig(row, column) > 0 ? Character.forDigit(this.getCurrentValueSquareAfterDig(row, column), 10) : ' ';
			squares[row][column].setCurrentValue(c);
			return "BOOM!" + '\n';
		}
		
		//Case that you don't dig a bomb
		int neighboursWithBombs = this.getCurrentValueSquareAfterDig(row, column);
		
		//Bombs around = 0
		if(neighboursWithBombs == 0)
		{
			squares[row][column].setCurrentValue(' ');
			this.expandOutwards(row, column);
		}
		
		//Bombs around > 0
		else
		{
			squares[row][column].setCurrentValue(Character.forDigit(neighboursWithBombs, 10));
		}
		return this.look();
	}
	
	private void expandOutwards(int row, int column)
	{
		for(int i = -1; i <2; i++)
		{
			for(int j = -1; j < 2 ; j++)
			{
				if(this.isValid(row + i, column+j) && this.squares[row+i][column + j].getCurrentValue() == '-')
				{
					int currentValue = this.getCurrentValueSquareAfterDig((row+i), (column +j));
					if(currentValue == 0)
					{
						this.squares[row+i][column + j].setCurrentValue(' ');
						this.expandOutwards(row+ i, column + j);
					}
					else
						this.squares[row+i][column + j].setCurrentValue(Character.forDigit(currentValue, 10));
				}
			}
		}
	}
	
	/**
	 * This is a private method which modifies the 8 squares around the bomb after the bomb blows up.
	 * @param row
	 * @param column
	 */
	private synchronized void modifyDugNeighbourSquaresAfterBomb(int row, int column)
	{
		for(int i = -1; i<2; i++)
		{
			for(int j= -1; j< 2; j++)
			{
				if(i==0 && j==0)
					continue;
				if(this.isValid(row + i, column+j) && this.squares[row+i][column + j].isDug())
				{
					int currentValue = Character.getNumericValue(this.squares[row+i][column+j].getCurrentValue()) - 1;
					if(currentValue == 0)
					{
						this.squares[row+i][column+j].setCurrentValue(' ');
						this.expandOutwards(row+i, column+j);
					}
					else
						this.squares[row+i][column+j].setCurrentValue(Character.forDigit(currentValue,10));
				}
			}
		}
	}
	
	/**
	 * Checks whether input row and column values are valid for the given Board
	 * @param row 
	 * @param column
	 * @return
	 */
	private synchronized boolean isValid(int row, int column)
	{
		if(row < 0 || column < 0 || row >= this.size ||  column >= this.size)
			return false;
		return true;
	}
	
	/**
	 * Find the number of bombs surrounding current square
	 * @param row of current square
	 * @param column of currenr square
	 * @return Integer number of bombs surrounding the square
	 */
	private synchronized int getCurrentValueSquareAfterDig(int row, int column)
	{
		int count = 0;
		for(int i = -1; i< 2; i++)
		{
			for(int j= -1; j< 2; j++)
			{
				if(i ==0 && j==0)
					continue;
				if(this.isValid(row+i, column+j))
					count += findBombInLocation(row+i, column+j);
			}
		}		
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
		if(!this.isValid(r, c))
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
