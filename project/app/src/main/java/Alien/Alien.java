/**
 * 2023s2-project1-mon17-15-team-1
 * @author Luolan (Melanie) Huang, Luoman (Mary) Huang, Praghash Thanasegeren
 *
 * Abstract Alien class for creation for other alien things.
 */

package Alien;// Alien.java
// Used for SpaceInvader

import ch.aplu.jgamegrid.*;

import java.util.List;
import java.util.Random;

public abstract class Alien extends Actor
{
  private boolean isInvulnerable = false;
  public final int MAX_NB_STEPS = 16;
  private int nbSteps;
  private static int stepSize = 1; // movement speed
  private boolean isMoving = true;
  private boolean isAutoTesting;
  private List<String> movements;
  private int movementIndex = 0;
  private String type;
  private int rowIndex;
  private int colIndex;

  /**
   * Alien object's number of lives, initialized accordingly with default to 1.
   */
  protected int nbLives = 1;

  /**
   * Alien constructor abstraction.
   * @param imageName String file name of the invulnerable alien image.
   * @param type Invulnerable alien type "invulnerable"
   * @param rowIndex Row index of this multiple alien in the alien grid.
   * @param colIndex Column index of this multiple alien in the alien grid.
   */
  public Alien(String imageName, String type, int rowIndex, int colIndex)
  {
    super(imageName);
    setSlowDown(7);
    this.type = type;
    this.rowIndex = rowIndex;
    this.colIndex = colIndex;
  }

  /**
   * Alien constructor abstraction.
   * @param imageName String array of file names of the invulnerable alien image.
   * @param type Invulnerable alien type "invulnerable"
   * @param rowIndex Row index of this multiple alien in the alien grid.
   * @param colIndex Column index of this multiple alien in the alien grid.
   */
  public Alien(String[] imageName, String type, int rowIndex, int colIndex)
  {
    super(imageName);
    setSlowDown(7);
    this.type = type;
    this.rowIndex = rowIndex;
    this.colIndex = colIndex;
  }


  /**
   * Gets type of this alien.
   * @return type String stype of this alien.
   */
  public String getType() {
    return type;
  }

  /**
   * Gets this alien's row index in alien grid.
   * @return rowIndex int row index.
   */
  public int getRowIndex() {
    return rowIndex;
  }

  /**
   * Gets this alien's column index in alien grid.
   * @return colIndex int column index.
   */
  public int getColIndex() {
    return colIndex;
  }

  /**
   * resets the number of steps
   */
  public void reset()
  {
    nbSteps = 7;
  }

  /**
   * Setting if the game is autotesting
   * @param isAutoTesting the boolean
   * @param movements reads the controls to be automatically moved
   */
  public void setTestingConditions(boolean isAutoTesting, List<String> movements) {
    this.isAutoTesting = isAutoTesting;
    this.movements = movements;
  }

  private void checkMovements() {
    if (isAutoTesting) {
      if (movements != null && movementIndex < movements.size()) {
        String movement = movements.get(movementIndex);
        if (movement.equals("S")) {
          isMoving = false;
        } else if (movement.equals("M")) {
          isMoving = true;
        }
        movementIndex++;
      }
    }
  }

  /**
   * Updates the aliens when called, implementing movement and speed logic.
   */
  @Override
  public void act()
  {
    checkMovements();
    if (!isMoving) {
      return;
    }
    if (nbSteps < MAX_NB_STEPS)
    {
      speedController();
    }
    else
    {
      nbSteps = 0;
      int angle;
      if (getDirection() == 0)
        angle = 90;
      else
        angle = -90;
        turn(angle);
        move();
        turn(angle);
    }
    if (getLocation().y > 90)
      removeSelf();
  }


  /**
   * Manages Alien speed based on the current stepSize.
   */
  private void speedController(){
    for (int i = 0; i < (stepSize); i++){
      move();
      nbSteps++;
//      show();
    }
  }


  /**
   * Get number of lives the Alien currently has.
   * @return int nbLives.
   */
  public int getNbLives() {
    return nbLives;
  }


  /**
   * Decrease the ALien's remaining life by 1.
   */
  public void decrementLife(){
    if (nbLives > 0){
      nbLives--;
    }
  }


  /**
   * For Alien to generate a random switch.
   * @return boolean randomBoolean
   */
  public boolean randomBoolean(){
    return new Random().nextBoolean();
  }


  /**
   * Getter from isInvulnerable.
   * @return boolean isInvulnerable
   */
  public boolean isInvulnerable() {
    return isInvulnerable;
  }


  /**
   * Setter for isInvulnerable.
   * @param invulnerable
   */
  public void setInvulnerable(boolean invulnerable) {
    isInvulnerable = invulnerable;
  }


  /**
   * Static method for setting all ALien's stepSize (movement speed).
   * @param stepSize
   */
  public static void setStepSize(int stepSize) {
    Alien.stepSize = stepSize;
  }

}
