package hw3;

import java.io.BufferedWriter;

import java.io.FileWriter;

import java.io.IOException;

import api.Tile;

import java.util.*;

import java.io.File;

import java.io.FileNotFoundException;

/**
 * Utility class with static methods for saving and loading game files.
 */

public class GameFileUtil {

	/**
	 * Saves the current game state to a file at the given file path.
	 * <p>
	 * The format of the file is one line of game data followed by multiple lines of
	 * game grid. The first line contains the: width, height, minimum tile level,
	 * maximum tile level, and score. The grid is represented by tile levels. The
	 * conversion to tile values is 2^level, for example, 1 is 2, 2 is 4, 3 is 8, 4
	 * is 16, etc. The following is an example:
	 * 
	 * <pre>
	 * 5 8 1 4 100
	 * 1 1 2 3 1
	 * 2 3 3 1 3
	 * 3 3 1 2 2
	 * 3 1 1 3 1
	 * 2 1 3 1 2
	 * 2 1 1 3 1
	 * 4 1 3 1 1
	 * 1 3 3 3 3
	 * </pre>
	 * 
	 * @param filePath the path of the file to save
	 * @param game     the game to save
	 */

	public static void save(String filePath, ConnectGame game) {

		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

			// TODO: write to file, can use writer.write()

			// gets grid of game
			Grid grid = game.getGrid();

			// writes information (width, height, minimum tile level, max tile level, and score)
			String line = "";
			line += grid.getWidth() + " " + grid.getHeight() + " " + game.getMinTileLevel() + " "
					+ game.getMaxTileLevel() + " " + (int) game.getScore() + '\n';
			writer.write(line);
			
			//Clears line
			line = "";
			Tile tile;

			// gets each tile level
			for (int y = 0; y < grid.getHeight(); y++) {

				for (int x = 0; x < grid.getWidth(); x++) {
					tile = grid.getTile(x, y);

					// writes the tile to the string and adds space or new line
					if (x == grid.getWidth() - 1) {
						line += tile.getLevel() + "";
						
						if (y != grid.getHeight() - 1) {
							line += "" + '\n';
						}
					}
					else {
						line += tile.getLevel() + " ";
					}
				}

				// write string and clears string
				writer.write(line);			
				line = "";
			}
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	/**
	 * Loads the file at the given file path into the given game object. When the
	 * method returns the game object has been modified to represent the loaded
	 * game.
	 * <p>
	 * See the save() method for the specification of the file format.
	 * 
	 * @param filePath the path of the file to load
	 * @param game     the game to modify
	 */

	public static void load(String filePath, ConnectGame game) {

		try {
			File file = new File(filePath);
			Scanner scnr = new Scanner(file);

			// building the new grid
			int width = scnr.nextInt();
			int height = scnr.nextInt();
			Grid grid = new Grid(width, height);

			// setting tile limits
			game.setMinTileLevel(scnr.nextInt());
			game.setMaxTileLevel(scnr.nextInt());

			// setting score
			game.setScore(scnr.nextInt());

			// entering each tile level and setting to grid
			for (int y = 0; y < height; y++) {
				
				for (int x = 0; x < width; x++) {
					int num = scnr.nextInt();
					Tile tile = new Tile(num);
					grid.setTile(tile, x, y);
				}
			}

			// sets grid
			game.setGrid(grid);
			scnr.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		}

	}

}
