package minesweeper.board;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class BoardTest 
{
	@Test
	public void testLookNewBoard()
	{
		Board b = new Board(5);
		assertEquals(b.look(), "- - - - -"+'\n'+"- - - - -"+'\n'+"- - - - -"+'\n'+"- - - - -"+'\n'+"- - - - -"+'\n');
	}
	
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
	
	@Test
	public void lookFromFile()throws IOException
	{
		Board b = new Board(new File("C:\\Users\\Rishabh\\Documents\\MIT\\Fall Semester\\6.005\\Eclipse\\Workspace\\ps3\\src\\autograder\\resources\\board_file_5"));
		System.out.println(b.look());
		assertEquals(b.look(), "- - - - - - -"+'\n'+"- - - - - - -"+'\n'+"- - - - - - -"+'\n'+"- - - - - - -"+'\n'+"- - - - - - -"+'\n'+"- - - - - - -"+'\n'+"- - - - - - -"+'\n');
	}
	
	@Test
	public void seeBombPatternFromFile()throws IOException
	{
		Board b = new Board(new File("C:\\Users\\Rishabh\\Documents\\MIT\\Fall Semester\\6.005\\Eclipse\\Workspace\\ps3\\src\\autograder\\resources\\board_file_5"));
		/*for(int i = 0; i < b.size; i++)
		{
			for(int j = 0; j < b.size; j++)
			{
				if(b.squares[i][j].isBomb())
					System.out.print(1 + " ");
				else
					System.out.print(0 + " ");
			}
			System.out.println();
		}*/
		System.out.println(b.dig(1, 3));
		System.out.println(b.dig(1, 4));
		System.out.println(b.look());
	}
	
	@Test
	public void FileTest()throws IOException
	{
		Board b = new Board(new File("C:\\Users\\Rishabh\\Documents\\MIT\\Fall Semester\\6.005\\Eclipse\\Workspace\\ps3\\src\\autograder\\resources\\board_test.txt"));
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
		//System.out.println(b.dig(0, 0));
		System.out.println(b.look());
	}
	
}
