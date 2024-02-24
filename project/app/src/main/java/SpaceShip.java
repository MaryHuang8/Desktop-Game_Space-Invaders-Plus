/**
 * 2023s2-project1-mon17-15-team-1
 * @author Luolan (Melanie) Huang, Luoman (Mary) Huang, Praghash Thanasegeren
 * Extending from Unimelb setup.
 *
 * Handles creation and logic for Shapeships
 */

// SpaceShip.java
// Used for SpaceInvader

import Alien.Alien;
import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.util.List;

public class SpaceShip extends Actor implements GGKeyListener
{
  //movement speed control
  private final int NB_SHOTS_LV2=10;
  private final int NB_SHOTS_LV3=50;
  private final int NB_SHOTS_LV4=100;
  private final int NB_SHOTS_LV5=500;
  private int nbShots = 0;
  private SpaceInvader spaceInvader;
  private boolean isAutoTesting = false;
  private List<String> controls = null;
  private int controlIndex = 0;

  public SpaceShip(SpaceInvader spaceInvader)
  {
    super("sprites/spaceship.gif");
    this.spaceInvader = spaceInvader;
  }

  /**
   * Setting if the game is autotesting
   * @param isAutoTesting the boolean
   * @param controls reads the controls to be automatically moved
   */
  public void setTestingConditions(boolean isAutoTesting, List<String> controls) {
    this.isAutoTesting = isAutoTesting;
    this.controls = controls;
  }

  private void autoMove() {
    if (isAutoTesting) {
      if (controls != null && controlIndex < controls.size()) {
        String control = controls.get(controlIndex);
        Location next = null;

        switch(control) {
          case "L":
            next = getLocation().getAdjacentLocation(Location.WEST);
            moveTo(next);
            break;

          case "R":
            next = getLocation().getAdjacentLocation(Location.EAST);
            moveTo(next);
            break;

          case "F":
            Bomb bomb = new Bomb();
            gameGrid.addActor(bomb, getLocation());
            nbShots++;
            break;
          case "E":
            spaceInvader.setIsGameOver(true);
            break;
        }
        controlIndex++;
      }
    }
  }

  /**
   * Handles what needs to be done at update at every frame.
   * For SpaceShip
   */
  public void act()
  {
    autoMove();
    Location location = getLocation();
    if (spaceInvader.getNumberOfActorsAt(location, Alien.class) > 0)
    {
      spaceInvader.removeAllActors();
      spaceInvader.addActor(new Actor("sprites/explosion2.gif"), location);
      spaceInvader.setIsGameOver(true);
      return;
    }
    if (spaceInvader.getNumberOfActors(Alien.class) == 0)
    {
      spaceInvader.getBg().drawText("Number of shots: " + nbShots, new Point(10, 30));
      spaceInvader.getBg().drawText("Game constructed with JGameGrid (www.aplu.ch)", new Point(10, 50));
      spaceInvader.addActor(new Actor("sprites/you_win.gif"), new Location(100, 60));
      spaceInvader.setIsGameOver(true);
      return;
    }

    if (spaceInvader.isPlus()){
      switch(nbShots){
        case(NB_SHOTS_LV2):
          Alien.setStepSize(2);
          spaceInvader.notifyAliensMoveFast();
          break;
        case(NB_SHOTS_LV3):
          Alien.setStepSize(3);
          spaceInvader.notifyAliensMoveFast();
          break;
        case(NB_SHOTS_LV4):
          Alien.setStepSize(4);
          spaceInvader.notifyAliensMoveFast();
          break;
        case(NB_SHOTS_LV5):
          Alien.setStepSize(5);
          spaceInvader.notifyAliensMoveFast();
          break;
      }
    }
  }

  /**
   * logic for when there are keys are pressed
   * @param keyEvent
   * @return boolean
   */
  public boolean keyPressed(KeyEvent keyEvent)
  {
    Location next = null;
    switch (keyEvent.getKeyCode())
    {
      case KeyEvent.VK_LEFT:
        next = getLocation().getAdjacentLocation(Location.WEST);
        moveTo(next);
        break;

      case KeyEvent.VK_RIGHT:
        next = getLocation().getAdjacentLocation(Location.EAST);
        moveTo(next);
        break;

      case KeyEvent.VK_SPACE:
        Bomb bomb = new Bomb();
        gameGrid.addActor(bomb, getLocation());
        nbShots++;
        break;
    }

    return false;
  }

  private void moveTo(Location location)
  {
    if (location.x > 10 && location.x < 190)
      setLocation(location);
  }

  @Override
  /**
   * logic for when there are keys are released
   * Tbh, forced to implement it so yee
   * @param evt
   * @return false everything
   */
  public boolean keyReleased(KeyEvent keyEvent) {
    return false;
  }


}
