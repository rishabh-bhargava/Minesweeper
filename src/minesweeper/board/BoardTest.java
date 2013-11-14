package minesweeper.board;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class BoardTest 
{
	/**
	 * Tests have been written for
	 * 1) Boards created from files
	 * 2) Boards created randomly
	 * 
	 * Tests have been created for-
	 * 1) All public methods and constructors.
	 * 2) The private methods are being tested through the public methods
	 */
	@Test
	public void testLookNewBoard()
	{
		Board b = new Board(5);
		assertEquals(b.look(), "- - - - -"+'\n'+"- - - - -"+'\n'+"- - - - -"+'\n'+"- - - - -"+'\n'+"- - - - -"+'\n');
	}
	
	//This test is to test whether the random board producing constructor works or not
	//This test cannot be used however with assert statements because the squares with bombs are produced randomly
	@Test
	public void testRandomBombPattern()
	{
		Board b = new Board(5);
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				if(b.squares[i][j].isBomb())
					System.out.print(1 + " ");
				else
					System.out.print(0 + " ");
			}
			System.out.println();
		}
	}
	
	//Reading from a file and testing a look
	@Test
	public void lookFromFile()throws IOException
	{
		Board b = new Board(new File("src\\autograder\\resources\\board_file_5"));
		assertEquals(b.look(), "- - - - - - -"+'\n'+"- - - - - - -"+'\n'+"- - - - - - -"+'\n'+"- - - - - - -"+'\n'+"- - - - - - -"+'\n'+"- - - - - - -"+'\n'+"- - - - - - -"+'\n');
	}
	
	//Reading from a file, testing the bomb positions and doing a few digs to test.
	//A dig in a non bomb and bomb location was done
	//Also a dig on a square previously dug was done.
	//A look is performed after digging a bomb to make sure necessary changes have happened
	//The file was the autograder file, which is shown below-
	/* 0 0 0 0 0 0 0
	 * 0 0 0 0 1 0 0
	 * 0 0 0 0 0 0 0
	 * 0 0 0 0 0 0 0
	 * 0 0 0 0 0 0 0
	 * 0 0 0 0 0 0 0
	 * 1 0 0 0 0 0 0 
	 */
	@Test
	public void seeBombPatternFromFile()throws IOException
	{
		Board b = new Board(new File("src\\autograder\\resources\\board_file_5"));
		String test = "";
		for(int i = 0; i < b.size; i++)
		{
			for(int j = 0; j < b.size; j++)
			{
				if(b.squares[i][j].isBomb())
					test += "1 ";
				else
					test += "0 ";
			}
			test += '\n';
		}
		assertEquals(true, test.equals("0 0 0 0 0 0 0 " + '\n'+ "0 0 0 0 1 0 0 " + '\n'+"0 0 0 0 0 0 0 " + '\n'+"0 0 0 0 0 0 0 " + '\n'+"0 0 0 0 0 0 0 " + '\n'+"0 0 0 0 0 0 0 " + '\n'+"1 0 0 0 0 0 0 " + '\n'));
		test = "";
		test +="- - - - - - -" + '\n';
		test +="- - - 1 - - -" + '\n';
		test +="- - - - - - -" + '\n';
		test +="- - - - - - -" + '\n';
		test +="- - - - - - -" + '\n';
		test +="- - - - - - -" + '\n';
		test +="- - - - - - -" + '\n';
		assertEquals(true, test.equals(b.dig(1, 3)));
		//Dig repeated on already dug location
		assertEquals(true, test.equals(b.dig(1, 3)));
		test = "BOOM!" + '\n';
		assertEquals(true, test.equals(b.dig(1, 4)));
		
		test = "";
		test +="             " + '\n';
		test +="             " + '\n';
		test +="             " + '\n';
		test +="             " + '\n';
		test +="             " + '\n';
		test +="1 1          " + '\n';
		test +="- 1          " + '\n';
		assertEquals(true, test.equals(b.look()));
	}
	
	//Tests for flag and deflag on the following new file
	//Deflag was tested on flagged and unflagged square
	/*
	 * 1 0 0 0 
	 * 0 1 0 0 
	 * 0 0 0 0 
	 * 1 0 0 1 
	 */
	@Test
	public void FileTest()throws IOException
	{
		Board b = new Board(new File("src\\autograder\\resources\\board_test.txt"));
		String test = "";
		for(int i = 0; i < b.size; i++)
		{
			for(int j = 0; j < b.size; j++)
			{
				if(b.squares[i][j].isBomb())
					test += "1 ";
				else
					test +="0 ";
			}
			test += '\n';
		}
		assertEquals(true, test.equals("1 0 0 0 "+ '\n'+ "0 1 0 0 "+ '\n' + "0 0 0 0 "+ '\n' + "1 0 0 1 "+ '\n'));
		
		test = "";
		test +="F - - -" + '\n';
		test +="- - - -" + '\n';
		test +="- - - -" + '\n';
		test +="- - - -" + '\n';
		assertEquals(true, test.equals(b.flag(0, 0)));
		
		test = "";
		test +="- - - -" + '\n';
		test +="- - - -" + '\n';
		test +="- - - -" + '\n';
		test +="- - - -" + '\n';
		assertEquals(true, test.equals(b.deflag(0, 0)));
		
		//Deflag done on a square not dug up, with no result
		assertEquals(true, test.equals(b.deflag(0, 0)));
	}
	
}
