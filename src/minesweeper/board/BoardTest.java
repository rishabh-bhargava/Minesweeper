package minesweeper.board;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class BoardTest 
{
	@Test
	public void testToStringNewBoard()
	{
		Board b = new Board(5);
		assertEquals(b.look(), "-----"+'\n'+"-----"+'\n'+"-----"+'\n'+"-----"+'\n'+"-----"+'\n');
	}
	
}
