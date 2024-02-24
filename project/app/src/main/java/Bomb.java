/**
 * 2023s2-project1-mon17-15-team-1
 * @author Luolan (Melanie) Huang, Luoman (Mary) Huang, Praghash Thanasegeren
 *
 * Handles bomb creation and logic
 */

// Bomb.java
// Used for SpaceInvader

import Alien.Alien;
import ch.aplu.jgamegrid.*;

import java.util.List;

public class Bomb extends Actor
{
  public Bomb()
  {
    super("sprites/bomb.gif");
  }

  /**
   * reset the bombs location
   */
  public void reset()
  {
    setDirection(Location.NORTH);
  }

  /**
   * The main logic for the bomb and initiates collision
   */
  public void act()
  {
    // Acts independently searching a possible target and bring it to explosion
    move();
    SpaceInvader spaceInvader = (SpaceInvader) gameGrid;
    List<Actor> actors = gameGrid.getActorsAt(getLocation(), Alien.class);
    if (!actors.isEmpty())
    {
      spaceInvader.notifyAlienHit(actors);
      HitAlien.getInstance().notifyAlienHit(this, spaceInvader, getLocation());
      return;
    }
    if (getY() < 5)
      removeSelf();
  }
}
