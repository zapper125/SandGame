import java.awt.*;
import java.util.*;

public class SandLab
{
	// Step 4,6
	// add constants for particle types here
	public static final int EMPTY = 0;
	public static final int METAL = 1;
	public static final int SAND = 2;
	public static final int WATER = 3;

	// do not add any more fields below
	private int[][] grid;
	private SandDisplay display;

	/**
	 * Constructor for SandLab
	 * 
	 * @param numRows
	 *            The number of rows to start with
	 * @param numCols
	 *            The number of columns to start with;
	 */
	public SandLab(int numRows, int numCols)
	{
		String[] names;
		// Change this value to add more buttons
		// Step 4,6
		names = new String[4];
		// Each value needs a name for the button
		names[EMPTY] = "Empty";
		names[METAL] = "Metal";
		names[SAND] = "Sand";
		names[WATER] = "Water";

		// 1. Add code to initialize the data member grid with same dimensions

		grid = new int[numRows][numCols];

		display = new SandDisplay("Falling Sand", numRows, numCols, names);
	}

	// called when the user clicks on a location using the given tool
	private void locationClicked(int row, int col, int tool)
	{
		// 2. Assign the values associated with the parameters to the grid

		grid[row][col] = tool;
	}

	// copies each element of grid into the display
	public void updateDisplay()
	{
		// Step 3
		// Hint - use a nested for loop
		for (int r = 0; r < grid.length; r++)
		{
			for (int c = 0; c < grid[0].length; c++)
			{
				if (grid[r][c] == METAL)
				{
					display.setColor(r, c, Color.GRAY);
				} 
				else if (grid[r][c] == EMPTY)
				{
					display.setColor(r, c, Color.BLACK);
				} 
				else if (grid[r][c] == SAND)
				{
					display.setColor(r, c, Color.YELLOW);
				} 
				else if (grid[r][c] == WATER)
				{
					display.setColor(r, c, Color.BLUE);
				}
			}
		}
	}

	// Step 5,7
	// called repeatedly.
	// causes one random particle in grid to maybe do something.
	public void step()
	{
		// Remember, you need to access both row and column to specify a spot in the
		// array
		// The scalar refers to how big the value could be
		// int someRandom = (int) (Math.random() * scalar)
		// remember that you need to watch for the edges of the array

		int rowScalar = grid.length;
		int colScalar = grid[0].length;
		int rowNumber = (int) (Math.random() * rowScalar);
		int colNumber = (int) (Math.random() * colScalar);
		int waterNumber = (int) (Math.random() * 3);

		if (grid[rowNumber][colNumber] == SAND && rowNumber + 1 != rowScalar)
		{
			if (grid[rowNumber + 1][colNumber] == EMPTY)
			{
				grid[rowNumber + 1][colNumber] = SAND;
				grid[rowNumber][colNumber] = EMPTY;
			}
		}
		// Water movement directions

		if (grid[rowNumber][colNumber] == WATER)
		{
			if (waterNumber == 0 && rowNumber + 1 != rowScalar && grid[rowNumber + 1][colNumber] == EMPTY)
			{
				grid[rowNumber + 1][colNumber] = WATER;
				grid[rowNumber][colNumber] = EMPTY;
			} 
			else if (waterNumber == 1 && colNumber - 1 != 0 && grid[rowNumber][colNumber - 1] == EMPTY)
			{
				grid[rowNumber][colNumber - 1] = WATER;
				grid[rowNumber][colNumber] = EMPTY;
			} 
			else if (waterNumber == 2 && colNumber + 1 != grid[0].length && grid[rowNumber][colNumber + 1] == EMPTY)
			{
				grid[rowNumber][colNumber + 1] = WATER;
				grid[rowNumber][colNumber] = EMPTY;
			}
		}
	}

	// do not modify this method!
	public void run()
	{
		while (true) // infinite loop
		{
			for (int i = 0; i < display.getSpeed(); i++)
			{
				step();
			}
			updateDisplay();
			display.repaint();
			display.pause(1); // wait for redrawing and for mouse
			int[] mouseLoc = display.getMouseLocation();
			if (mouseLoc != null) // test if mouse clicked
			{
				locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
			}
		}
	}
}
