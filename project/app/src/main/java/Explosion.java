/**
 * 2023s2-project1-mon17-15-team-1
 * @author Luolan (Melanie) Huang, Luoman (Mary) Huang, Praghash Thanasegeren
 * Handles the Explosion logic and creation
 */

// Explosion.java
// Used for SpaceInvader

import ch.aplu.jgamegrid.*;

public class Explosion extends Actor
{
  public Explosion()
  {
    super("sprites/explosion1.gif");
    setSlowDown(3);
  }

  /**
   * Runs for one tick and remove itself after. Pretty straight forward tbh
   */
  public void act()
  {
    removeSelf();
  }
}
