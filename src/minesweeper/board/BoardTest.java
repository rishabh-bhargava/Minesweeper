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
	 * 1) All public methods. They might not be in the standard assertEquals() form, but have been checked thoroughly
	 * 2) The private methods are being tested through the public methods
	 */
	@Test
	public void testLookNewBoard()
	{
		Board b = new Board(5);
		assertEquals(b.look(), "- - - - -"+'\n'+"- - - - -"+'\n'+"- - - - -"+'\n'+"- - - - -"+'\n'+"- - - - -"+'\n');
	}
	
	//This test is to test whether the random board producing constructor works or not
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
	
	//Reading from a file and producing a look
	@Test
	public void lookFromFile()throws IOException
	{
		Board b = new Board(new File("src\\autograder\\resources\\board_file_5"));
		System.out.println(b.look());
		assertEquals(b.look(), "- - - - - - -"+'\n'+"- - - - - - -"+'\n'+"- - - - - - -"+'\n'+"- - - - - - -"+'\n'+"- - - - - - -"+'\n'+"- - - - - - -"+'\n'+"- - - - - - -"+'\n');
	}
	
	//Reading from a file, testing the bomb positions and doing a few digs to test.
	//It was much easier to test print statements
	//The file was the autograder file
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
		System.out.println(b.dig(1, 3));
		System.out.println(b.dig(1, 4));
		System.out.println(b.look());
	}
	
	//Tests for flag, deflag, dig and look on the following new file
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
		for(int i = 0; i < b.size; i++)
		{
			for(int j = 0; j < b.size; j++)
			{
				if(b.squares[i][j].isBomb())
					System.out.print(1 + " ");
				else
					System.out.print(0 + " ");
			}
			System.out.println();
		}
		System.out.println(b.flag(0, 0));
		System.out.println(b.deflag(0, 0));
		System.out.println(b.dig(0, 3));
		System.out.println(b.look());
	}
	
}
