import java.util.Arrays;

public class Minesweeper {
	private char[][] mineBoard;
	private char[][] clearBoard;
	//mines is not the actual number of mines, its how many the player thinks are gone
	//basically just actual number of mines minus number of flags
	private int mines;
	
	private static final char UNTOUCHED = '-';
	private static final char TOUCHED = '\u0000';
	private static final char MINE = '*';
	private static final char FLAG = 'F';
	
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
				this.clearBoard[j][i] = UNTOUCHED;
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
				if (mineBoard[i][j] == MINE)
				{
					continue;
				}
				Integer mineCount = 0;
				for (int k = -1; k <= 1; k++)
				{
					for (int l = -1; l <=1; l++)
					{
						try
						{
							if(mineBoard[i + k][j + l] == MINE)
							{
								mineCount++;
							}
						}
						catch (ArrayIndexOutOfBoundsException a) {}
					}
				}
				mineBoard[i][j] = mineCount.toString().charAt(0);
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
	
	public void printBoard() {
		for (int i = 0; i < mineBoard.length; i++)
		{
			for (int j = 0; j < mineBoard[i].length; j++)
			{
				if (clearBoard[i][j] == TOUCHED)
				{
					System.out.print(mineBoard[i][j]);
				}
				else
				{
					System.out.print(clearBoard[i][j]);
				}
			}
			System.out.print("\n");
		}
	}
	
	public void toggleFlag(int y, int x)
	{
		if(clearBoard[y][x] == UNTOUCHED)
		{
			clearBoard[y][x] = FLAG;
			mines--;
		} else
		{
			if (clearBoard[y][x] == FLAG)
			{
				clearBoard[y][x] = UNTOUCHED;
				mines++;
			}
		}
	}
	
	public static void main(String[] args)
	{
		Minesweeper mines = new Minesweeper(10, 10, 10);
		mines.printBoard();
	}
}
