/**
 * 2023s2-project1-mon17-15-team-1
 * @author Luolan (Melanie) Huang, Luoman (Mary) Huang, Praghash Thanasegeren
 */
package Alien;

import java.util.Random;

/**
 * MultipleAlien is a child class of Alien, with the power to multiply a top row of normal aliens and turn into normal.
 */
public class MultipleAlien extends Alien {
    private static final int MAX_MULTIPLY_COUNT_DOWN = 20;
    private static int multiplyCountDown = (new Random()).nextInt(MAX_MULTIPLY_COUNT_DOWN);
    private static boolean multiplyCountDownTriggered = false;
    private boolean multiplied = false;
    private static int rowToRemove; //stores the index of the row of invisible normal aliens in alienGrid to remove
    private static int rowToMultiply; //stores the index of the row of invisible normal aliens in alienGrid to show

    /**
     * Constructor for multiple aliens.
     * @param imageName String array that stores image name of multiple alien and image name of normal alien.
     * @param type String type of the alien "multiple".
     * @param rowIndex Row index of this multiple alien in the alien grid.
     * @param colIndex Column index of this multiple alien in the alien grid.
     */
    public MultipleAlien(String[] imageName, String type, int rowIndex, int colIndex) {
        super(imageName, type, rowIndex, colIndex);
    }

    /**
     * add a row of normal aliens at the top of the current grid, and the multiple alien image into normal,
     * when random count down reaches 0.
     * @param alienGrid A 2D array that stores all aliens.
     */
    public void multiply(Alien[][] alienGrid) {

        // the multiplied normal aliens are rowToMultiply-th row of the alien grid
        for (int i=0; i<alienGrid[0].length; i++) {
            alienGrid[rowToMultiply][i].show();
        }
        rowToMultiply++;

        // change sprite to normal
        turnIntoNormal();
        multiplied = true;

    }

    private void turnIntoNormal() {
        show(1);
    }


    /**
     * Initiates the multiple alien's random count-down for multiplication.
     */
    public static void initiateMultiplyCountDown() {
        multiplyCountDownTriggered = true;
    }


    /**
     * Decrements the multiplication count-down after count down is initiated, on top of Actor's act method.
     */
    @Override
    public void act() {
        super.act();

        // count down for multiple alien multiplication
        if (multiplyCountDownTriggered) {
            if (multiplyCountDown != 0) {
                multiplyCountDown--;
            }
        }
    }


    /**
     * Returns the current multiplication count-down value.
     * @return multiplyCountDown This is the number of seconds until a multiple alien multiplies.
     */
    public static int getMultiplyCountDown() {
        return multiplyCountDown;
    }

    /**
     * Checks whether the multiplication count-down has initiated or not.
     * @return multiplyCountDownTriggered This is the boolean status of whether the count-down has started.
     */
    public static boolean isMultiplyCountDownTriggered() {
        return multiplyCountDownTriggered;
    }

    /**
     * Resets the multiplication count-down to a random value for multiple aliens. Use after previous multiplication.
     */
    public static void resetCountDownAndTrigger() {
        multiplyCountDown = (new Random()).nextInt(MAX_MULTIPLY_COUNT_DOWN);
        multiplyCountDownTriggered = false;
    }


    /**
     * Checks whether this multiple alien has multiplied.
     * @return multiplied Boolean multiplication status of this multiple alien.
     */
    public boolean isMultiplied() {
        return multiplied;
    }


    /**
     * For setting the alienGrid index of the initial row of invisible normal aliens to remove,
     * when a multiple alien is dead before multiplying.
     *  (starting from the top row)
     * @param rowToRemove
     */
    public static void setInitRowToRemove(int rowToRemove){
        MultipleAlien.rowToRemove = rowToRemove;
    }


    /**
     * For setting the alienGrid index of the initial row of invisible normal aliens to show,
     * when a multiple alien uses its power.
     * @param rowToMultiply
     */
    public static void setInitRowToMultiply(int rowToMultiply){
        MultipleAlien.rowToMultiply = rowToMultiply;
    }


    /**
     * For when a multiple alien dies, need to remove a top row of pre-prepared invisible normal aliens.
     * @return rowToRemove The row index of the invisible normal aliens to remove in the alien grid 2D array.
     */
    public static int getRowToRemove(){
        return rowToRemove;
    }


    /** To be used when removing a row of invisible normal aliens,
     * for flagging the next row to be removed if any **/
    public static void decrementRowToRemove(){
        rowToRemove--;
    }


    /**
     * Checks whether the row for alien multiplication is low enough
     * to be in the game grid to perform multiplication.
     * @param alienGrid
     * @return boolean isTopSpaceRowEmpty
     */
    public static boolean checkIsTopSpaceRowEmpty(Alien[][] alienGrid){
        if (alienGrid[rowToMultiply][0].getY() >= 10){
            return true;
        }
        return false;
    }
}
