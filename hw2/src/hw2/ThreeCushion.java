package hw2;

import static api.PlayerPosition.*;

import api.BallType;
import api.PlayerPosition;

import static api.BallType.*;

/**
 * Class that models the game of three-cushion billiards.
 * 
 * @author Jackson Collalti!!!
 */
public class ThreeCushion {

	// TODO: EVERTHING ELSE GOES HERE
	// Note that this code will not compile until you have put in stubs for all
	// the required methods.

	// The method below is provided for you and you should not modify it.
	// The compile errors will go away after you have written stubs for the
	// rest of the API methods.

	/**
	 * Returns a one-line string representation of the current game state. The
	 * format is:
	 * <p>
	 * <tt>Player A*: X Player B: Y, Inning: Z</tt>
	 * <p>
	 * The asterisks next to the player's name indicates which player is at the
	 * table this inning. The number after the player's name is their score. Z is
	 * the inning number. Other messages will appear at the end of the string.
	 * 
	 * @return one-line string representation of the game state
	 */
	public String toString() {
		String fmt = "Player A%s: %d, Player B%s: %d, Inning: %d %s%s";
		String playerATurn = "";
		String playerBTurn = "";
		String inningStatus = "";
		String gameStatus = "";
		if (getInningPlayer() == Player_1) {
			playerATurn = "*";
		} else if (getInningPlayer() == Player_2) {
			playerBTurn = "*";
		}
		if (isInningStarted()) {
			inningStatus = "started";
		} else {
			inningStatus = "not started";
		}
		if (isGameOver()) {
			gameStatus = ", game result final";
		}
		return String.format(fmt, playerATurn, getPlayerAScore(), playerBTurn, getPlayerBScore(), getInning(),
				inningStatus, gameStatus);
	}

	/**
	 * State of Game Instance Variables
	 * 
	 * @variable currentInning - Tracks current inning
	 * @variable pointsToWin - Max points before win
	 * @variable ballsInMotion - Tracks if ball is moving or not moving
	 * @variable Break - Tracks if the current shot is a break
	 * @variable numCushionHits - number of times the cue ball has hit the cushion
	 * @variable lagWinnerSelected - Tracks if the lag is finished
	 * @variable foulAlreadyCalled - Tracks if a foul has already been called
	 * @variable canBank - Tracks if the current shot can be a bank shot
	 * @variable cueHitWhite - tracks if cue ball has hit WWhite ball
	 * @variable cueHitRed - tracks if cue ball has hit Red ball
	 * @variable cueHitYellow - tracks if cue ball has hit Yellow ball
	 * 
	 */
	private int currentInning;
	private int pointsToWin;
	private boolean ballsInMotion;
	private boolean Break;
	private int numCushionHits;
	private boolean lagWinnerSelected = false;
	private boolean foulAlreadyCalled = false;
	private boolean canBank = false;
	private boolean cueHitWhite = false;
	private boolean cueHitRed = false;
	private boolean cueHitYellow = false;

	/**
	 * State of Player
	 * 
	 * @variable Player_1 - Player that takes first shot
	 * @variable Player_2 - Player that takes second shot
	 * @variable Player_1Points - tracks player 1 score
	 * @variable Player_2Points - tracks player 2 score
	 * @variable Player_1CueBall - Tracks player 1 cue ball
	 * @variable Player_2CueBall - Tracks player 2 cue ball
	 * @variable currentShooter - Tracks who is currently shooting
	 * @variable currentShootersBall - Tracks current shooters cue ball
	 */
	private PlayerPosition Player_1;
	private PlayerPosition Player_2;
	private int Player_1Points;
	private int Player_2Points;
	private BallType Player_1CueBall;
	private BallType Player_2CueBall;
	private PlayerPosition currentShooter;
	private BallType currentShootersBall;

	/**
	 * Creates new game of Three Cushion given lag winner and the points until win
	 * Inning count starts at 1
	 * 
	 * @param playerA - first person to take shot
	 * @param i - points to win game
	 */
	public ThreeCushion(PlayerPosition playerA, int i) {
		currentInning = 1; 						// Sets inning to 1 when game starts
		pointsToWin = i;
		Player_1 = playerA; 					// Initially sets Player_1 to the input of the method
		if (Player_1 == PLAYER_A) { 			// If statement to assign Player_2 to Player A or B
			Player_2 = PLAYER_B;				// If Player_1 is Player_A then Player_2 must be Player_B
		} else {
			Player_2 = PLAYER_A; 				// If Player_1 isn't Player_A then Player_2 must be Player_A
		}
		lagWinnerSelected = false; 				// Sets lag winner to false when game is started
	}

	/**
	 * Sets whether the lag winner takes first shot or not Set the lag winners cue
	 * ball Once called it can't be recalled
	 * 
	 * @param selfBreak - if true lag winner takes break shot
	 * @param cueBall - the lag winners cue ball (White || Yellow)
	 */
	public void lagWinnerChooses(boolean selfBreak, BallType cueBall) {
		if (selfBreak == true) {				// Player_1 chooses to self break
			currentShooter = Player_1;			// Player_1 takes first and is the first current shooter
		} else { 								// Player_1 choose Player_2 to take break
			currentShooter = Player_2; 			// Player_2 is taking break shot
		}
		Player_1CueBall = cueBall;				// Sets Player_1 cue ball to the ball selected
		if (Player_1CueBall == WHITE) {			// Player_1 chooses White
			Player_2CueBall = YELLOW;			// Player_2 must be Yellow
		} else {								// Player_1 chooses Yellow
			Player_2CueBall = WHITE;			// Player_2 must be White
		}
		Break = true;							// Sets break to true
		lagWinnerSelected = true;				// Lag winner has been selected
	}

	/**
	 * gets current players cue ball If called in between innings, cue ball should
	 * be the for the player of next inning If this method is called before the lag
	 * winner has chosen a cue ball, cue ball is undefined
	 * 
	 * @return the current shooter's ball
	 */
	public BallType getCueBall() {
		if (Break == true) {							// When break is true
			if (currentShooter == Player_1) {			// If Player_1 is taking break shot
				currentShootersBall = Player_1CueBall;  // current shooters ball is Player_1's
			} else {
				currentShootersBall = Player_2CueBall;	// Player_2 is taking break so ball is Player_2's
			}
		}
		return currentShootersBall;						// All other shots return current shooters ball
	}

	/**
	 * gets current players If called in between innings, player should be the for
	 * the player of next inning If this method is called before the lag winner has
	 * chosen a break, player is undefined
	 * 
	 * @return
	 */
	public PlayerPosition getInningPlayer() {
		if (Break == true) {							// When break is true
			if (currentShooter == Player_1) { 			// Break shooter is Player_1
				return Player_1;						// Current shooter is Player_1
			} else if (currentShooter == Player_2) {	// Break shooter is Player_2
				return Player_2;						// Current shooter is Player_2
			}
		}
		return currentShooter; 							// All other shots return current shooter
	}

	/**
	 * Indicates if the ball impacts a cushion Can only be called after stick strike
	 * If called after game nothing should happen
	 */
	public void cueBallImpactCushion() {
		numCushionHits += 1;															// Add 1 to current value of
																						// numCushionHits
		int numCushionHitsBeforeBallHit = numCushionHits;
		if (cueHitWhite == true || (cueHitYellow == true) || (cueHitRed == true)) { 	// If cue ball hits any other
																					 	// balls
			canBank = false; 															// Shot can no longer bank
			numCushionHitsBeforeBallHit = 0;											// Resets
																						// numCushionHitsBeforeBallHit
		}

		// If numCushionHitsBeforeBallHit greater than 3 then shot can bank

		if (numCushionHitsBeforeBallHit >= 3
				&& !(cueHitWhite == true || (cueHitYellow == true) || (cueHitRed == true))) {

			canBank = true;		 														// canBank is true
		}
		if (((cueHitWhite == true) || (cueHitYellow == true)) && (cueHitRed == true)) { // Test case 36
			numCushionHits = 0;
		}
		if (Break == true && (cueHitWhite == true || cueHitYellow == true || numCushionHits != 0)
				&& cueHitRed != true) { 												// Rule 3a must make direct
										 												// contact w/ Red ball on break
										 												// shot
			if (foulAlreadyCalled == false) { 											// If no foul already called
												 											// (Avoids double foul call
												 											// issues)
				foul();																	// Foul
				foulAlreadyCalled = true;												// Set foul already called to
																							// true
			}
		}
	}

	/**
	 * Indicates cue ball has hit given ball Ball strike can't happen before stick
	 * strike Ball strike can't happen after end of game
	 * 
	 * @param ball - Ball impacted by cue ball
	 */
	public void cueBallStrike(BallType ball) {

		if (ball == YELLOW) { 									// If cue ball strikes Yellow ball
			cueHitYellow = true;
		}
		if (ball == RED) { 										// If cue ball strikes Red ball
			cueHitRed = true;
		}
		if (ball == WHITE) { 									// If cue ball strikes White ball
			cueHitWhite = true;
		}

		// if break shot and a ball other than Red gets hit

		if (Break == true && (cueHitWhite == true || cueHitYellow == true || numCushionHits != 0)
				&& cueHitRed != true) {

			if (foulAlreadyCalled == false) { 					// If no foul already called (Avoids double foul call
												 					// issues)
				foul(); 										// Foul
				foulAlreadyCalled = true; 						// Set foul already called to true
			}

		}
	}

	/**
	 * Indicates that the ball has been stricken If shot hasn't started it has now
	 * If already a shot can't be called until after endShot() If foul() then method
	 * can't be called
	 * 
	 * Calling method means start of inning if not already Even if a foul has been
	 * committed, calling this method is considered the start of a shot. Includes
	 * when the player strikes a ball other than their own cue ball. It is expected
	 * that the endShot() called
	 * 
	 * No play can begin until the lagWinner selects. If called after the game has
	 * ended nothing happens.
	 * 
	 * @param Ball - Players cue ball
	 */
	public void cueStickStrike(BallType Ball) {
		canBank = false; 													// Resets can bank at start of shot
		if (isGameOver() == false) { 										// If game isn't over
			ballsInMotion = true; 											// Ball is set into motion, starting the
									 											// shot
			if (currentShooter == Player_1 && Ball != Player_1CueBall) { 	// if stick hits ball other than current
																		 	// shooter's (Player_1)
				if (foulAlreadyCalled == false) { 							// If no foul already called (Avoids double
													 							// foul call issues)
					foul(); 												// Foul
					foulAlreadyCalled = true; 								// Set foul already called to true
				}
			}
			if (currentShooter == Player_2 && Ball != Player_2CueBall) { 	// if stick hits ball other than current
																		 	// shooter's (Player_2)
				if (foulAlreadyCalled == false) { 							// If no foul already called (Avoids double
													 							// foul call issues)
					foul(); 												// Foul
					foulAlreadyCalled = true; 								// Set foul already called to true
				}
			}
		}
	}

	/**
	 * Indicates that motion has stopped if valid shot and no foul add point Shot
	 * cannot end before it has started with a call to cueStickStrike. If called
	 * before cueStickStrike, should be ignored. If called after games end nothing
	 * happens
	 * 
	 * @variable pointScored - Keeps track of if a point could've been scored
	 */
	public void endShot() {

		if (ballsInMotion == true) { 										// if ball was moving set ball motion to false
			ballsInMotion = false;
			boolean pointScored = false;									

			// If the shot had a point scored and shooter was Player_1

			if (numCushionHits >= 3 && (currentShooter == Player_1) && (cueHitRed == true)
					&& ((cueHitWhite == true) || (cueHitYellow == true))) {

				Player_1Points += 1; 										// Adds 1 to current score of Player_1
				pointScored = true; 										// Sets point scored to true

			}

			// If the shot had a point scored and shooter was Player_2

			if (numCushionHits >= 3 && (currentShooter == Player_2) && (cueHitRed == true)
					&& ((cueHitWhite == true) || (cueHitYellow == true))) {

				Player_2Points += 1;	 									// Adds 1 to current score of Player_2
				pointScored = true; 										// Sets point scored to true
			}
			if (numCushionHits > 0 && cueHitRed == true && cueHitYellow == false && cueHitWhite == false) {
				if (foulAlreadyCalled == false) {							// If no foul already called (Avoids double
																				// foul call issues)
					foul();													// Foul
					foulAlreadyCalled = true; 								// Set foul already called to true
				}
			}
			if (pointScored == false && canBank == true) { 					// If point was scored on shot
				canBank = false;
			}
			if (pointScored == false) { 									// If no point was scored
				currentInning += 1; 										// Add one to inning
				if (currentShooter == Player_1) { 							// Swap shooters, previous shooter was
													 							// Player_1
					currentShooter = Player_2; 								// Swap current shooter to Player_2
					currentShootersBall = Player_2CueBall; 					// Swap current cue ball to Player_2
				} else { 													// Swap shooters, previous shooter was
						 													// Player_2
					currentShooter = Player_1; 								// Swap current shooter to Player_1
					currentShootersBall = Player_1CueBall;					// Swap current cue ball to Player_1
				}
			}
		}
		foulAlreadyCalled = false;											// Resets foul already called
		if (Break == true) {												// If shot was break
			Break = false;													// Sets break shot to false
		}
		cueHitRed = false; 													// Reset cue hit red ball
		cueHitYellow = false; 												// Resets cue hit yellow ball
		cueHitWhite = false; 												// Resets cue hit white ball
		numCushionHits = 0; 												// Resets cushion hits
	}

	/**
	 * A foul immediately ends the player's inning, even if the current shot has not
	 * yet ended. When a foul is called, the player does not score a point for the
	 * shot. A foul may also be called before the inning has started. The player
	 * whose turn it was to shot has their inning forfeited and the inning count is
	 * increased.
	 * 
	 * No foul can be called until the lag player has chosen who will break If this
	 * method is called before the break is chosen, it should do nothing. If called
	 * after the game has ended, nothing happens.
	 */
	public void foul() {
		ballsInMotion = false; 											// stops ball motion
		if (isGameOver() == false) { 									// If game over
			if (lagWinnerSelected == true) { 							// If lag winner selected
				currentInning += 1;										// Adds one to current inning
				if (currentShooter == Player_1) { 						// If shooter was Player_1
					currentShooter = Player_2; 							// Swap current shooter to Player_2
					currentShootersBall = Player_2CueBall; 				// Swap current cue ball to Player_2's
				} else { 												// Shooter was Player_2
					currentShooter = Player_1; 							// Swap current shooter to Player_1
					currentShootersBall = Player_1CueBall; 				// Swap current cue ball to Player_1's
				}
			}
		}
	}

	/**
	 * gets inning count, starts at 1
	 * 
	 * @return - current inning
	 */
	public int getInning() {
		return currentInning;
	}

	/**
	 * Gets number of points scored by player A
	 * 
	 * @return Player A score
	 */
	public int getPlayerAScore() {
		if (Player_1 == PLAYER_A) {					// If Player_1 was PLAYER_A
			return Player_1Points; 					// Player_1 points
		} else { 									// Player_2 was PLAYER_A
			return Player_2Points; 					// Player_2 point
		}
	}

	/**
	 * Gets number of points scored by player B
	 * 
	 * @return Player B score
	 */
	public int getPlayerBScore() {
		if (Player_1 == PLAYER_B) { 				// If Player_1 was PLAYER_B
			return Player_1Points; 					// Player_1 points
		} else { 									// Player_2 was PLAYER_B
			return Player_2Points; 					// Player_2 points
		}
	}

	/**
	 * returns true if last shot was a bank shot. Bank shot - hits cushion 3 times
	 * and strikes both object
	 * 
	 * @return true or false
	 */
	public boolean isBankShot() {
		if ((canBank == true)) {					// If point was scored and 3 cushion hits before ball strike
			return true;							// was bank shot
		} else {									// If one condition failed in above if statement
			return false;							// wasn't bank shot
		}
	}

	/**
	 * Returns true only if the first shot
	 *
	 * @return true or false
	 */
	public boolean isBreakShot() {
		if (Break == true) {						// If break shot
			return true;							// Is break shot
		} else {									// If not break shot
			return false;							// not break shot
		}
	}

	/**
	 * Returns true if game if over and is a winner
	 * 
	 * @return true or false
	 */
	public boolean isGameOver() {
		
		// If either player is above points to win
		
		if ((Player_1Points >= pointsToWin) || (Player_2Points >= pointsToWin)) {
			
			return true;							// Is game over
		} else {									// If not player at points to win
			return false;							// game is still being played
		}
	}

	/**
	 * True if first shot of the inning has happened Inning starts at beginning of
	 * shot
	 * 
	 * @return
	 */
	public boolean isInningStarted() {
		if (isGameOver() == false) {				// If game is over
			if (ballsInMotion == true) {			// If ball is in motion
				return true;						// Inning has started
			} else {								// ball not in motion
				return false;						// Inning hasn't started
			}
		} else {									// Game is over
			return false;							// Inning not started
		}
	}

	/**
	 * True if cueStickStrike but hasn't ended (endShot not called)
	 * 
	 * @return true or false
	 */
	public boolean isShotStarted() {
		if (ballsInMotion == true) {				// If ball in motion
			return true;							// shot started
		} else {									// ball not in motion
			return false;							// shot not started
		}
	}

}
