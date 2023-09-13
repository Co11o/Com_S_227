package hw1;

/**
 * @author Jackson
 *
 */

public class CameraBattery {
	// Constants
	public static final int NUM_CHARGER_SETTINGS = 4; // Number of External Charger Settings
	public static final double CHARGE_RATE = 2.0; // Charge Rate for both External and Camera Charging
	public static final double DEFAULT_CAMERA_POWER_CONSUMPTION = 1.0; // Standard Camera Power Consumption

	// Base Instance Variables
	private double batteryStartingCharge; // Keeps track of the starting battery charge
	private double batteryCapacity; // Keeps track of the battery capacity

	// New Instance Variables
	private double currentBatteryCharge; // Variable updated to represent the battery charge at a given moment
	private double currentCameraCharge; // Variable updated to represent the camera charge at a given moment
	private double totalDrain; // Variable to keep track of the total drain since last monitor reset
	private double newDrain; // Variable for the the newest drain
	private double prevDrain; // Variable to hold the value of the newDrain incase of another drain is inputed
	private double lastDrain; // Variable to hold the value of the prevDrain incase of another drain is inputed (a third drain call)
	private double cameraCharge; // Variable for the camera charging output
	private double externalCharge; // Variable for the external charging output
	private double cameraPowerConsuptionRate; // Rate that camera consumes power
	private int numButtonPresses; // Keeps track of button presses for setting
	private int location; // Tells if the location is "chargable" or "drainable"

	// Constructor
	public CameraBattery(double currentBatteryCharge, double batteryCapacity) {
		this.currentBatteryCharge = Math.min(currentBatteryCharge, batteryCapacity); // Makes sure the currentBatteryCharge is less than batteryCapacity
		this.batteryCapacity = batteryCapacity; // assigns the battery capacity
		cameraPowerConsuptionRate = DEFAULT_CAMERA_POWER_CONSUMPTION; // Initilises cameraPowerConsumption to constant
																		// for start
		location = 0; // Initilises location to Zero (battery starts not connect to anything)
		batteryStartingCharge = currentBatteryCharge; // Sets batteryStartingCharge to the currentbatteryCharge at start
		numButtonPresses = 0; // Starts numButtonPresses at zero (Nothing has been pressed yet)

	}

	// Getters
	
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unused") // Isn't used in any testing, got rid of squiggle line
	private double getBatteryStartingCharge() {
		return batteryStartingCharge; // Just returns initial batteryCharge
	}
	/**
	 * getBatteryCapacity
	 * 
	 * @return battery capacity
	 */
	public double getBatteryCapacity() {
		return batteryCapacity; // Just returns batteryCapacity
	}

	/**
	 * getBatteryCharge
	 * 
	 * @return the battery current charge
	 */

	public double getBatteryCharge() {
		return currentBatteryCharge; // returns currentBatteryCharge
	}

	/**
	 * getCameraCharge
	 * 
	 * @return the camera current charge
	 */

	public double getCameraCharge() {
		return currentCameraCharge; // returns currentCameraCharge
	}

	/**
	 * getCameraPowerConsumption
	 * 
	 * @return the rate that battery power is consumed while in camera
	 */

	public double getCameraPowerConsumption() {
		return cameraPowerConsuptionRate; // returns cameraPowerConsumptionRate
	}

	/**
	 * getChargerSetting
	 * 
	 * @return the current external charger setting (numButtonPresses)
	 */

	public int getChargerSetting() {
		return numButtonPresses; // returns numButtonPresses
	}

	/***
	 * getTotalDrain
	 * 
	 * @return total battery drained since last battery monitor was started or reset
	 */

	public double getTotalDrain() {
		totalDrain += (lastDrain + prevDrain); // Adds lastDrain and prevDrain (the values for drains) to totalDrain and it's previous value
		return totalDrain; // returns totalDrain
	}

	// Methods

	/**
	 * buttonPress
	 * 
	 * Indicates User pressed setting button once Increments by one if at max and
	 * button is pressed wraps to 0 Have variable counter for number of button
	 * presses adding one each time button is pressed. Then use Modulo to decide the
	 * max and reset
	 */

	public void buttonPress() {
		numButtonPresses = (numButtonPresses + 1) % NUM_CHARGER_SETTINGS; // Increases numButtonPresses by 1 and takes the remainder (NUM_CHARGER_SETTINGS) to create the loop of [0,3]
	}

	/***
	 * cameraCharge
	 * 
	 * If connected, charges camera battery for @param minutes. The battery charge
	 * can't go over battery capacity Doesn't charge when battery isn't connected
	 * 
	 * @return amount battery has been charged Equation: CHARGE_RATE * @param
	 *         minutes.
	 */

	public double cameraCharge(double minutes) {
		cameraCharge = CHARGE_RATE * minutes * location; //
		currentBatteryCharge += cameraCharge; //
		currentBatteryCharge = Math.min(currentBatteryCharge, batteryCapacity); //
		currentCameraCharge = currentBatteryCharge * location; //
		return Math.min(batteryCapacity + batteryStartingCharge - cameraCharge, cameraCharge); //
	}

	/***
	 * drain
	 * 
	 * With battery connected to camera drains the battery for @param minutes Can't
	 * drain more than amount of charge (no negative battery) No battery drain if
	 * battery isn't connected to camera
	 * 
	 * @return amount battery drained Equation: DEFAULT_CAMERA_POWER_CONSUMPTION
	 *         * @param minutes
	 */

	public double drain(double minutes) {
		newDrain = cameraPowerConsuptionRate * minutes * location; // Calculates the battery drain asd sets it to
																	// newDrain
		newDrain = Math.min(newDrain, currentBatteryCharge);// Makes sure the drain isn't going past the currentBatteryCharge (no negatives
		currentBatteryCharge -= newDrain;// Drains battery
		currentCameraCharge -= newDrain;// Drains Camera
		lastDrain = prevDrain;// Sets lastDrain to the value of prevDrain used when multiple drains are used without reset
		prevDrain = newDrain;// Sets prevDrain to the value of newDrain used when multiple drains are used without reset
		return newDrain;// Returns NewDrain
	}

	/***
	 * externalCharge
	 * 
	 * Charges battery when connected to external charger for @param minutes The
	 * battery charge can't go above the capactiy. No charging if battery isn't
	 * connected
	 * 
	 * @return amount battery has been charged Equation: CHARGE_RATE *
	 *         NUM_CHARGER_SETTING * @param minutes
	 */

	public double externalCharge(double minutes) {
		externalCharge = CHARGE_RATE * numButtonPresses * minutes;// Calculates battery charge from external charger and sets it to externalCharge
		externalCharge = Math.min(externalCharge, batteryCapacity - currentBatteryCharge);// Makes sure the charge doesn't go over capacity
		currentBatteryCharge += externalCharge;// Adds externalCharge to the currentBattryCharge
		currentBatteryCharge = Math.min(currentBatteryCharge, batteryCapacity);// Makes sure the currentBaterryCharge isn't greater than capacity
		return externalCharge;// Returns externalCharge
	}

	/***
	 * resetBatteryMonitor Modifies total battery drain to 0 (resets to 0) and related variables
	 */

	public void resetBatteryMonitor() {
		prevDrain = 0;// Resets prevDrain
		newDrain = 0;// Resets newDrain
		lastDrain = 0;// Resets lastDrain
		totalDrain = 0;// Resets totalDrain
	}

	/***
	 * moveBatteryExternal Moves battery to external charger updates all variables
	 * associated
	 */

	public void moveBatteryExternal() {
		currentCameraCharge = 0;// Sets currentCameeraCharge to Zero since battery is disconnect
		location = 0;// Sets location to Zero meaning drain and charge don't work
	}

	/**
	 * movebatteryCamera Moves battery to the camera updates all variables
	 * associated
	 */

	public void moveBatteryCamera() {
		currentCameraCharge = currentBatteryCharge;// sets currentCameraCharge to currentBatteryCharge
		location = 1;// Location is set to 1, meaning battery and camera can be charged
	}

	/***
	 * removeBattery removes battery for either camera or external charger updates
	 * all variable associated
	 */

	public void removeBattery() {
		currentCameraCharge = 0;// Sets currentCameeraCharge to Zero since battery is disconnect
		location = 0;// Sets location to Zero meaning drain and charge don't work
	}

	/***
	 * setCameraPowerConsumption sets the comsumption of the camera to @param
	 * cameraPowerConsumption
	 * 
	 */

	public void setCameraPowerConsumption(double cameraPowerConsumption) {
		cameraPowerConsuptionRate = cameraPowerConsumption;// Sets cameraPowerConsuptionRate to cameraPowerConsumption
	}

}