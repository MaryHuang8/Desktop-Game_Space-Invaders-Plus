/**
 * 2023s2-project1-mon17-15-team-1
 * @author Luolan (Melanie) Huang, Luoman (Mary) Huang, Praghash Thanasegeren
 * Extending from Unimelb setup.
 *
 * Technically the main class, handles the creation of game entities and
 * when the game starts and ends
 */

// SpaceInvader.java
// Sprite images from http://www.cokeandcode.com/tutorials
// Nice example how the different actor classes: SpaceShip, Bomb, SpaceInvader, Explosion
// act almost independently of each other. This decoupling simplifies the logic of the application

import Alien.Alien;
import ch.aplu.jgamegrid.*;

import java.awt.Point;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class SpaceInvader extends GameGrid implements GGKeyListener
{
  private int nbRows = 3;
  private int nbCols = 11;
  private boolean isGameOver = false;
  private boolean isAutoTesting = false;
  private Properties properties = null;

  private StringBuilder logResult = new StringBuilder();

  private Alien[][] alienGrid = null;
  private static AlienController alienController = new AlienController();

  public SpaceInvader(Properties properties) {
    super(200, 100, 5, false);
    this.properties = properties;
  }

  /**
   * Adding actors into the game. For some reason, needs to be done in this way
   * @param actor The entities
   * @param location Where is it
   */
  protected void actorAdd(Actor actor, Location location){
    addActor(actor, location);
  }

  /**
   * Setting if the game is autotesting
   * @param isAutoTesting the boolean
   */
  protected void setIsAutoTesting(Boolean isAutoTesting){
    this.isAutoTesting = isAutoTesting;
  }
  private void setupSpaceShip() {
    SpaceShip ss = new SpaceShip(this);
    addActor(ss, new Location(100, 90));

    String spaceShipControl = properties.getProperty("space_craft.control");
    List<String> controls = null;
    if (spaceShipControl != null) {
      controls = Arrays.asList(spaceShipControl.split(";"));
    }

    ss.setTestingConditions(isAutoTesting, controls);
    addKeyListener(ss);
  }

  /**
   * Does primary displaying UI
   * @param isDisplayingUI
   * @return Log messages
   */
  public String runApp(boolean isDisplayingUI) {
    setSimulationPeriod(Integer.parseInt(properties.getProperty("simulationPeriod")));
    nbRows = Integer.parseInt(properties.getProperty("rows"));
    nbCols = Integer.parseInt(properties.getProperty("cols"));
    alienGrid = alienController.setupAliens(properties,nbRows,nbCols,this);
    setupSpaceShip();
    addKeyListener(this);
    getBg().setFont(new Font("SansSerif", Font.PLAIN, 12));
    getBg().drawText("Use <- -> to move, spacebar to shoot", new Point(400, 330));
    getBg().drawText("Press any key to start...", new Point(400, 350));

    if (isDisplayingUI) {
      show();
    }

    if (isAutoTesting) {
      setBgColor(java.awt.Color.black);  // Erase text
      doRun();
    }

    while(!isGameOver) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }

    doPause();
    return logResult.toString();
  }

  /**
   * Handles what needs to be done at update at every frame
   */
  @Override
  public void act() {

    if (alienController.getNbMultipleAliens() > 0) {

      // trigger multiply alien's multiplication process if top row is empty and multiplication not triggered yet.
      if (!alienController.isMultiplyCountDownTriggered() && alienController.triggerMultipleAlienMultiplication(this)) {
      } else if (alienController.isMultiplyCountDownTriggered()) {
        alienController.updateAlienGridAfterMultiplying(alienGrid);
      }

    }


    logResult.append("Alien locations: ");

    for (int i = 0; i < nbRows; i++) {
      for (int j = 0; j < nbCols; j++) {
        Alien alienData = alienGrid[i][j];

        if (alienData == null) {
          continue;
        }
        String isDeadStatus = alienData.isRemoved() ? "Dead" : "Alive";
        String gridLocation = "0-0";
        if (!alienData.isRemoved()) {
          gridLocation = alienData.getX() + "-" + alienData.getY();
        }
        String alienDataString = String.format("%s@%d-%d@%s@%s#", alienData.getType(),
                alienData.getRowIndex(), alienData.getColIndex(), isDeadStatus, gridLocation);
        logResult.append(alienDataString);
      }
    }
    logResult.append("\n");
  }

  /**
   * logs when the alien moves fast
   */
  protected void notifyAliensMoveFast() {

      logResult.append("Aliens start moving fast\n");
  }

  /**
   * logs when actors are hit and location of it as well
   * @param actors Aliens
   */
  protected void notifyAlienHit(List<Actor> actors) {
    for (Actor actor: actors) {
      Alien alien = (Alien)actor;
      String alienData = String.format("%s@%d-%d",
              alien.getType(), alien.getRowIndex(), alien.getColIndex());
      logResult.append("An alien has been hit.");
      logResult.append(alienData + "\n");
    }
  }

  protected void setIsGameOver(boolean isOver) {
    isGameOver = isOver;
  }

  /**
   * logic for when there are keys are pressed
   * @param evt
   * @return
   */
  public boolean keyPressed(KeyEvent evt)
  {
    if (!isRunning())
    {
      setBgColor(java.awt.Color.black);  // Erase text
      doRun();
    }
    return false;  // Do not consume key
  }

  /**
   * logic for when there are keys are released
   * Tbh, forced to implement it so yee
   * @param evt
   * @return
   */
  public boolean keyReleased(KeyEvent evt)
  {
    return false;
  }

  protected boolean isPlus(){
    return "plus".equals(properties.getProperty("version"));
  }


  protected Alien[][] getAlienGrid() {
    return alienGrid;
  }

}






