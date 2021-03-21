import java.util.Scanner;

public class Game {
	final static String ST_UND = "\u001b[4m";
	final static String SP_UND = "\u001b[24m";

	public static void main(String[] args) {
		boolean restart = true;
		Scanner reader = new Scanner(System.in);

		while (restart)
		{
			boolean isPlaying = true;
			boolean isWon = false;
			int height, width, mines;
			System.out.println(ST_UND+"Enter Starting Parameters"+SP_UND);
			System.out.print("Height: ");
			height = reader.nextInt();
			System.out.print("Width: ");
			width = reader.nextInt();
			System.out.print("Mine Count: ");
			mines = reader.nextInt();
			if (height * mines < mines)
			{
				mines = width*height;
			}
			Minesweeper game = new Minesweeper(height, width, mines);
			
			//first turn is special because if a mine is picked first it has to be moved
			game.printBoard();
			System.out.println(ST_UND+"Enter the coordinate of the space you want to sweep"+SP_UND);
			{
				System.out.print("Y: ");
				int y = reader.nextInt();
				System.out.print("X: ");
				int x = reader.nextInt();
				if (game.sweep(y, x))
				{
					game.moveMine(y, x);
					game.calculateMines();
					isPlaying = !game.sweep(y, x);
				}
			}
			//main game loop
			while(isPlaying)
			{
				//Player turn
				game.printBoard();
				int choice = menu("Select an Option", new String[] {
					"Sweep a Space",
					"Toggle the Flag on a Space"
				});
				switch (choice)
				{
				case 0 : {
					System.out.println(ST_UND+"Enter the Coordinate of the Space to Sweep"+SP_UND);
					System.out.print("Y: ");
					int y = reader.nextInt();
					System.out.print("X: ");
					int x = reader.nextInt();
					isPlaying = !game.sweep(y, x);
					break;
				}
				case 1 : {
					System.out.println(ST_UND+"Enter the Coordinate of the Space to Toggle"+SP_UND);
					System.out.print("Y: ");
					int y = reader.nextInt();
					System.out.print("X: ");
					int x = reader.nextInt();
					game.toggleFlag(y, x);
					break;
				}
				}
				
				//Checking to see if player won
				if (game.getMines() == 0)
				{
					if(game.checkFlags())
					{
						isPlaying = false;
						isWon = true;
					}
				}
			}
			if (isWon)
			{
				game.printMines();
				System.out.println("You Won! :)");
			} else
			{
				game.printMines();
				System.out.println("You Lost! :(");
			}
			System.out.println("Press one to play again");
			if(reader.nextInt() != 1)
			{
				break;
			}
		}
	}
	
	private static int menu(String title, String[] options) {
		final int length = options.length;
		Scanner reader = new Scanner(System.in);
		System.out.println(ST_UND+title+SP_UND);
		for (int i = 0; i < length; i++)
		{
			System.out.println(i + ". " + options[i]);
		}
		int choice;
		do
		{
			System.out.print("Enter the index of the option you want: ");
			choice = reader.nextInt();
		} while (choice > length);
		return choice;
	}

}
