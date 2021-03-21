import java.util.Arrays;

public class Minesweeper {
	private char[][] mineBoard;
	private char[][] clearBoard;
	//mines is not the actual number of mines, its how many the player thinks are gone
	//basically just actual number of mines minus number of flags
	private int mines;
	
	public static final char UNTOUCHED = '-';
	public static final char TOUCHED = '\u0000';
	public static final char MINE = '*';
	public static final char FLAG = 'F';
	
	/**
	 * Constructs a new minesweeper game with the given parameters.
	 * @param height The height of the game board
	 * @param wdith The width of the game board
	 * @param mines The number of mines on the Board;
	 */
	public Minesweeper(int height, int width, int mines)
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
			if (this.mineBoard[(int)(random / height)][(int)(random%width)] == '*')
				i--;
			this.mineBoard[(int)(random / height)][(int)(random%width)] = '*';
		}
		calculateMines();
		}
	
	/**
	 * Prints out the full board showing the positions of all the mines and number of mines around each space.
	 */
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
	
	/**
	 * Prints out a "safe" version of the board. This will only show the spaces the player has sweeped so the player can't cheat.
	 */
	public void printBoard() {
		System.out.print("  ");
		for (int i = 0; i < mineBoard.length; i++)
		{
			System.out.print(i);
		}
		System.out.print("\n");
		for (int i = 0; i < mineBoard.length; i++)
		{
			System.out.print(i+" ");
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
	
	/**
	 * Toggles the given space. If the space was already sweeped nothing happens;
	 * @param y The y coordinate of the space to toggle
	 * @param x The x coordinate of the space to toggle
	 * @return True if the space was toggled, false otherwise
	 */
	public boolean toggleFlag(int y, int x)
	{
		if(clearBoard[y][x] == UNTOUCHED)
		{
			clearBoard[y][x] = FLAG;
			mines--;
			return true;
		} else
		{
			if (clearBoard[y][x] == FLAG)
			{
				clearBoard[y][x] = UNTOUCHED;
				mines++;
				return true;
			} else
			{
				return false;
			}
		}
	}
	/**
	 * Sweeps the given space. Automatically sweeps around its self if 0 mines are around the given space
	 * @param y The y coordinate of the space to sweep
	 * @param x The x coordinate of the space to sweep
	 * @return true if a mine was sweeped. false otherwise
	 */
	public boolean sweep(int y, int x)
	{
		if (mineBoard[y][x] == MINE)
		{
			return true;
		} else {
			if (clearBoard[y][x] != UNTOUCHED)
			{
				return false;
			}
			else
			{
				clearBoard[y][x] = TOUCHED;
				if (mineBoard[y][x] == '0')
				{
					for (int i = -1; i <= 1; i++)
					{
						for (int j = -1; j <=1; j++)
						{
							try
							{
								sweep(y + i, x + j);
							}
							catch (ArrayIndexOutOfBoundsException a) {}
						}
					}
				}
				return false;
			}
		}
	}
	
	/**
	 * @return the mines
	 */
	public int getMines() {
		return mines;
	}
	/**
	 * Calculates how many mines are around each square on the board.
	 * @param recalculate True to recalculate the number of mines, False to not recalculate
	 */
	public void calculateMines() {
		for (int i = 0; i < mineBoard.length; i++)
		{
			for (int j = 0; j < mineBoard[i].length; j++)
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
		this.mines = mines;
	}
	
	/**
	 * Sets a space on the mine board to the character.
	 * @param y The y coordinate of the space
	 * @param x The x coordinate of the space
	 * @param c The character to put at the space
	 */
	public void setSpace(int y, int x, char c)
	{
		this.mineBoard[y][x] = c;
	}
	
	/**
	 * Checks if all the flags are on mines
	 * @return Returns true if every flag is on a mine. Returns false otherwise
	 */
	public boolean checkFlags()
	{
		for (int i = 0; i < mineBoard.length; i++)
		{
			for (int j = 0; j < mineBoard[i].length; j++)
			{
				if (mineBoard[i][j] == MINE ^ clearBoard[i][j] == FLAG)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Moves the mine from the specified space and attempts to move it out of the way
	 * @param y The y coordinate of the space to move from
	 * @param x The x coordinate of the space to move from
	 */
	public void moveMine(int y, int x)
	{
		mineBoard[y][x] = '\u0000';
		for (int i = 0; i < mineBoard.length; i++)
		{
			for (int j = 0; j < mineBoard[i].length; j++)
			{
				if (mineBoard[i][j] != MINE)
				{
					mineBoard[i][j] = MINE;
					return;
				}
			}
		}
	}

	public static void main(String[] args)
	{
		Minesweeper mines = new Minesweeper(10, 10, 10);
		mines.printMines();
	}
}
