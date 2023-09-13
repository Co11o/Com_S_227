package hw3;

import java.util.Random;
import java.util.*;

import api.ScoreUpdateListener;
import api.ShowDialogListener;
import api.Tile;

/**
 * Class that models a game.
 */
public class ConnectGame {
	private ShowDialogListener dialogListener;
	private ScoreUpdateListener scoreListener;
	/**
	 * Variables
	 * 
	 * width - Width of grid
	 * 
	 * height - Height of grid
	 * 
	 * min - minimum tile level
	 * 
	 * max - maximum tile level
	 * 
	 * rand - random number generator
	 * 
	 * grid - grid with tiles
	 * 
	 * score - player score
	 * 
	 * tempScore - temporary player score
	 * 
	 * lastLevel - level of the last selected tile
	 * 
	 * selectInProgress - if selection of the tiles in progress
	 * 
	 * firstTile - if only the first tile has been selected
	 * 
	 * selectedTiles - list of currently selected tiles
	 * 
	 * dropTime - if its time to drop tiles and sets a new max
	 */
	
	private int width;
	private int height;
	private int min;
	private int max;
	private Random rand;
	private Grid grid;
	private long score;
	private long tempScore;
	private int lastLevel;
	private boolean selectInProgress;
	private boolean firstTile;
	private List<Tile> selectedTiles;
	private boolean dropTime;
	
	/**
	 * Constructs a new ConnectGame object with given grid dimensions and minimum
	 * and maximum tile levels.
	 * 
	 * @param width  grid width
	 * @param height grid height
	 * @param min    minimum tile level
	 * @param max    maximum tile level
	 * @param rand   random number generator
	 */
	public ConnectGame(int width, int height, int min, int max, Random rand) {
		
		//setting variables
		this.width = width;
		this.height = height;
		this.rand = rand;
		score = 0;
		selectInProgress = false;
		tempScore = 0;
		dropTime = false;
		setMinTileLevel(min);
		setMaxTileLevel(max);
		
		//New filled grid
		grid = new Grid(width, height);
		radomizeTiles();
		
		//New List of selected tiles
		selectedTiles = new ArrayList<Tile>();
	}

	/**
	 * Gets a random tile with level between minimum tile level inclusive and
	 * maximum tile level exclusive. For example, if minimum is 1 and maximum is 4,
	 * the random tile can be either 1, 2, or 3.
	 * <p>
	 * DO NOT RETURN TILES WITH MAXIMUM LEVEL
	 * 
	 * @return a tile with random level between minimum inclusive and maximum
	 *         exclusive
	 */
	public Tile getRandomTile() {
		//makes sure the max isn't included in range
		Tile random = new Tile(rand.nextInt(max - min) + min);
		return random;
	}

	/**
	 * Regenerates the grid with all random tiles produced by getRandomTile().
	 */
	public void radomizeTiles() {
				//Sets full row then moves to next row with random tiles 	
				for(int y = 0; y < height; y++) {
					for(int x  = 0; x < width; x++) {
						grid.setTile(getRandomTile(), x, y);
					}
				}
	}

	/**
	 * Determines if two tiles are adjacent to each other. The may be next to each
	 * other horizontally, vertically, or diagonally.
	 * 
	 * @param t1 one of the two tiles
	 * @param t2 one of the two tiles
	 * @return true if they are next to each other horizontally, vertically, or
	 *         diagonally on the grid, false otherwise
	 */
	public boolean isAdjacent(Tile t1, Tile t2) {
		//X's and Y's for each tile
		int T1X = t1.getX();
		int T1Y = t1.getY();
		int T2X = t2.getX();
		int T2Y = t2.getY();
		
		//Tile 2, Same row, within 1 tile vertically as Tile 1 
		if(((T2X == T1X) && ((T2Y == T1Y + 1) || (T2Y == T1Y -1)))) {
			return true;
		}
		//Tile 2, 1 row to the right, within 1 tile vertical or at same height as Tile 1 
		else if((T2X == T1X + 1) && ((T2Y == T1Y)||(T2Y == T1Y + 1) || ( T2Y == T1Y -1))) {
			return true;
		}
		//Tile 2, 1 row to the right, within 1 tile vertical or at same height as Tile 1
		else if((T2X == T1X - 1) && ((T2Y == T1Y)||(T2Y == T1Y + 1) || ( T2Y == T1Y -1))){
			return true;
		}
		//Not within the range of adjacent Tiles
		else {
		return false;
		}
	}

	/**
	 * Indicates the user is trying to select (clicked on) a tile to start a new
	 * selection of tiles.
	 * <p>
	 * If a selection of tiles is already in progress, the method should do nothing
	 * and return false.
	 * <p>
	 * If a selection is not already in progress (this is the first tile selected),
	 * then start a new selection of tiles and return true.
	 * 
	 * @param x the column of the tile selected
	 * @param y the row of the tile selected
	 * @return true if this is the first tile selected, otherwise false
	 */
	public boolean tryFirstSelect(int x, int y) {
		
		//stops if selection in progress
		if(selectInProgress) {
			return false;
		}
		
		//starts selection
		selectInProgress = true;
		
		//gets tile by coordinate and adds to selected list
		Tile tile = grid.getTile(x, y);
		tile.setSelect(true);
		selectedTiles.add(tile);
		
		//updates temporary Score
		tempScore += tile.getValue();
		
		//sets the lastLevel variable
		lastLevel = tile.getLevel();
		
		firstTile = true;
		
		return true;
	}

	/**
	 * Indicates the user is trying to select (mouse over) a tile to add to the
	 * selected sequence of tiles. The rules of a sequence of tiles are:
	 * 
	 * <pre>
	 * 1. The first two tiles must have the same level.
	 * 2. After the first two, each tile must have the same level or one greater than the level of the previous tile.
	 * </pre>
	 * 
	 * For example, given the sequence: 1, 1, 2, 2, 2, 3. The next selected tile
	 * could be a 3 or a 4. If the use tries to select an invalid tile, the method
	 * should do nothing. If the user selects a valid tile, the tile should be added
	 * to the list of selected tiles.
	 * 
	 * @param x the column of the tile selected
	 * @param y the row of the tile selected
	 */
	public void tryContinueSelect(int x, int y) {
		
		//checks if there is a select in progress and is adjacent
		if(selectInProgress) {
			
			//grabs tile
			Tile tile = grid.getTile(x, y);
			
		
			if((isAdjacent(selectedTiles.get(selectedTiles.size() - 1), tile)) && (!tile.isSelected())) {

			
				//checks for if only one tile has been selected
				if(firstTile) {
					
					if(tile.getLevel() == lastLevel) {
						
						//if second tile has been selected
						firstTile = false;
						
						//adds tile to selected group
						tile.setSelect(true);
						selectedTiles.add(tile);
						
						//updates score
						tempScore += tile.getValue();
					}
				}
				else if((tile.getLevel() == lastLevel) || (tile.getLevel() == lastLevel + 1)) {
					
					//updates last level to currently selected tile
					lastLevel = tile.getLevel();
					
					//adds tile to selected group
					tile.setSelect(true);
					selectedTiles.add(tile);
					
					//updates score
					tempScore += tile.getValue();
				}
			}
			else {
				if(selectedTiles.size() >= 2) {
					
					//grabs second to last tile
					tile = selectedTiles.get(selectedTiles.size() - 2);
					
					//compares with current hovered tile
					if((tile.getX() == x) &&(tile.getY() == y)){
						
						//grabs tile that needs to be unselected
						tile = selectedTiles.get(selectedTiles.size() - 1);
						
						//unselect
						unselect(tile.getX(),tile.getY());
					}
				}
			}

		}
	}

	/**
	 * Indicates the user is trying to finish selecting (click on) a sequence of
	 * tiles. If the method is not called for the last selected tile, it should do
	 * nothing and return false. Otherwise it should do the following:
	 * 
	 * <pre>
	 * 1. When the selection contains only 1 tile reset the selection and make sure all tiles selected is set to false.
	 * 2. When the selection contains more than one block:
	 *     a. Upgrade the last selected tiles with upgradeLastSelectedTile().
	 *     b. Drop all other selected tiles with dropSelected().
	 *     c. Reset the selection and make sure all tiles selected is set to false.
	 * </pre>
	 * 
	 * @param x the column of the tile selected
	 * @param y the row of the tile selected
	 * @return return false if the tile was not selected, otherwise return true
	 */
	public boolean tryFinishSelection(int x, int y) {
		
		//if selection is in progress
		if(selectInProgress == false){
			return false;
		}
		
		//gets last tile
		Tile tile = selectedTiles.get(selectedTiles.size() - 1);
				
		//if selected is the last tile
		if((x != tile.getX()) || (y != tile.getY())) {
			return false;
		}
		
		//if only 1 tile was selected
		if(selectedTiles.size() == 1) {
			
			//sets tile to false and removes from list
			for(int i = 0; i < selectedTiles.size(); i++) {
				tile = selectedTiles.get(i);
				tile.setSelect(false);
			}
			selectedTiles.clear();
			
			tempScore = 0;
			selectInProgress = false;
			return true;
		}
		
		
		//upgrades last selected tile
		upgradeLastSelectedTile();

		//removes all selected tiles from grid
		dropSelected();
		
		//if upgraded over max level
		if(dropTime) {
			//removes all min level tiles
			dropLevel(min - 1);
		}
		
		//updates score
		
		score += tempScore;
		scoreListener.updateScore(score);
		
		//clears and resets variables
		for(int i = 0; i < selectedTiles.size(); i++) {
			tile = selectedTiles.get(i);
			tile.setSelect(false);
		}
		selectedTiles.clear();
		tempScore = 0;
		selectInProgress = false;
		
		return true;
	}

	/**
	 * Increases the level of the last selected tile by 1 and removes that tile from
	 * the list of selected tiles. The tile itself should be set to unselected.
	 * <p>
	 * If the upgrade results in a tile that is greater than the current maximum
	 * tile level, both the minimum and maximum tile level are increased by 1. A
	 * message dialog should also be displayed with the message "New block 32,
	 * removing blocks 2". Not that the message shows tile values and not levels.
	 * Display a message is performed with dialogListener.showDialog("Hello,
	 * World!");
	 */
	public void upgradeLastSelectedTile() {
		
		//gets last tile 
		Tile tile = selectedTiles.get(selectedTiles.size() - 1);

		//removes selection
		tile.setSelect(false);
		selectedTiles.remove(selectedTiles.size() - 1);	
		
		//sets new level
		int newLevel = tile.getLevel() + 1;
		tile.setLevel(newLevel);
		
		//if new level exceeds max
		if(newLevel > max) {

			//displays message
			dialogListener.showDialog("New block " + tile.getValue() + ", removing blocks " + (int) Math.pow(2, min));
			
			//sets new max and min
			max += 1;
			min += 1;
			
			dropTime = true;
		}
	}

	/**
	 * Gets the selected tiles in the form of an array. This does not mean selected
	 * tiles must be stored in this class as a array.
	 * 
	 * @return the selected tiles in the form of an array
	 */
	public Tile[] getSelectedAsArray() {
		Tile[] arr = new Tile[selectedTiles.size()];
		
		//gets every item from the list and inputs into array
		for(int i = 0; i < selectedTiles.size(); i++) {
			arr[i] = selectedTiles.get(i);
		}
		return arr;
	}

	/**
	 * Removes all tiles of a particular level from the grid. When a tile is
	 * removed, the tiles above it drop down one spot and a new random tile is
	 * placed at the top of the grid.
	 * 
	 * @param level the level of tile to remove
	 */
	public void dropLevel(int level) {
		Tile tile1;
		Tile tile2;
		
		//finding each tile 
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				tile1 = grid.getTile(x,  y);
			
				//if min level and shifts if true
				if(tile1.getLevel() == level) {
					for(int h = tile1.getY(); h > 0; h--) {	
						//gets tile above tile1
						tile2 = grid.getTile(tile1.getX(), h - 1);
						//sets new location
						grid.setTile(tile2, tile1.getX(), h);
						//tile2 becomes tile1
						tile1 = tile2;
					}
					
					//adds new block on top
					grid.setTile(getRandomTile(), tile1.getX(), 0);
					
				}
			}
		}
	}

	/**
	 * Removes all selected tiles from the grid. When a tile is removed, the tiles
	 * above it drop down one spot and a new random tile is placed at the top of the
	 * grid.
	 */
	public void dropSelected() {
		
		Tile tile1;
		Tile tile2;
		
		//finding selected tile
		for(int i = 0; i < selectedTiles.size(); i++) {
			tile1 = selectedTiles.get(i);
			
			//moving upwards tile onto the next unless top
			for(int j = tile1.getY(); j > 0; j--) {
				
				//grab tile on top of tile1
				tile2 = grid.getTile(tile1.getX(), j - 1);
				
				//sets tile in new location
				grid.setTile(tile2, tile1.getX(), j);
				
				//tile2 becomes the reference point
				tile1 = tile2;
			}
			
			//adds new block on top
			grid.setTile(getRandomTile(), tile1.getX(), 0);
			
		}
	
	}

	/**
	 * Remove the tile from the selected tiles.
	 * 
	 * @param x column of the tile
	 * @param y row of the tile
	 */
	public void unselect(int x, int y) {
		//if selection is in progress
		if(selectInProgress) {
			Tile tile;
			
			//checks selected tiles for coordinates
			for(int i = 0; i < selectedTiles.size(); i++) {
				
				tile = selectedTiles.get(i);
				
				if((tile.getX() == x) && (tile.getY() == y)) {
					
					//removes tile value from temporary score
					tempScore -= tile.getValue();
					
					//found file, remove and unselect
					selectedTiles.remove(i);
					tile.setSelect(false);
				}
			}
		}
	}

	/**
	 * Gets the player's score.
	 * 
	 * @return the score
	 */
	public long getScore() {
		return score;
	}

	/**
	 * Gets the game grid.
	 * 
	 * @return the grid
	 */
	public Grid getGrid() {
		return grid;
	}

	/**
	 * Gets the minimum tile level.
	 * 
	 * @return the minimum tile level
	 */
	public int getMinTileLevel() {
		return min;
	}

	/**
	 * Gets the maximum tile level.
	 * 
	 * @return the maximum tile level
	 */
	public int getMaxTileLevel() {
		return max;
	}

	/**
	 * Sets the player's score.
	 * 
	 * @param score number of points
	 */
	public void setScore(long score) {
		this.score = score;
	}

	/**
	 * Sets the game's grid.
	 * 
	 * @param grid game's grid
	 */
	public void setGrid(Grid grid) {
		this.grid = grid;

	}

	/**
	 * Sets the minimum tile level.
	 * 
	 * @param minTileLevel the lowest level tile
	 */
	public void setMinTileLevel(int minTileLevel) {
		min = minTileLevel;
	}

	/**
	 * Sets the maximum tile level.
	 * 
	 * @param maxTileLevel the highest level tile
	 */
	public void setMaxTileLevel(int maxTileLevel) {
		max = maxTileLevel;
	}

	/**
	 * Sets callback listeners for game events.
	 * 
	 * @param dialogListener listener for creating a user dialog
	 * @param scoreListener  listener for updating the player's score
	 */
	public void setListeners(ShowDialogListener dialogListener, ScoreUpdateListener scoreListener) {
		this.dialogListener = dialogListener;
		this.scoreListener = scoreListener;
	}

	/**
	 * Save the game to the given file path.
	 * 
	 * @param filePath location of file to save
	 */
	public void save(String filePath) {
		GameFileUtil.save(filePath, this);
	}

	/**
	 * Load the game from the given file path
	 * 
	 * @param filePath location of file to load
	 */
	public void load(String filePath) {
		GameFileUtil.load(filePath, this);
	}
}
