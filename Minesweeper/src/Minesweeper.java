import java.util.Arrays;

public class Minesweeper {
	private char[][] mineBoard;
	private char[][] clearBoard;
	private int mines;
	
	public Minesweeper(int width, int height, int mines)
	{
		this.mineBoard = new char[height][width];
		this.clearBoard = new char[height][width];
		this.mines = mines;
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				this.mineBoard[j][i] = '\u0000';
				this.clearBoard[j][i] = '\u0000';
			}
		}
		
		for (int i = 0; i < mines; i++)
		{
			int random = (int) (width * height * Math.random());
			this.mineBoard[(int)(random / height)][(int)(random%width)] = '*';
		}
		
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				
			}
		}
	}
	
	public void printMines() {
		for (int i = 0; i < mineBoard.length; i++)
		{
			for (int j = 0; j < mineBoard[i].length; j++)
			{
				System.out.print(mineBoard[j][i]);
			}
			System.out.print("\n");
		}
	}
	
	public static void main(String[] args)
	{
		Minesweeper mines = new Minesweeper(10, 10, 10);
		mines.printMines();
	}
}
