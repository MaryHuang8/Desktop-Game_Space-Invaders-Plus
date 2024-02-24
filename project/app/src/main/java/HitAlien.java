/**
 * 2023s2-project1-mon17-15-team-1
 * @author Luolan (Melanie) Huang, Luoman (Mary) Huang, Praghash Thanasegeren
 *
 * Handles logic when the Bomb hits Alien
 */

import ch.aplu.jgamegrid.*;
import Alien.*;

import java.util.ArrayList;

public class HitAlien{
    private static HitAlien _instance;

    private HitAlien(){
    }


    /**
     * Part of HitALien's Singleton pattern. Returns the same instance of HitALien when called.
     * @return HitAlien
     */
    protected static HitAlien getInstance(){
        if (_instance == null){
            _instance = new HitAlien();
        }
        return _instance;

    }


    /**
     * Handles bomb hits alien logic.
     * Decrements Alien's nbLives and removes bomb if ALien is not invulnerable and is visible.
     * Creates explosion when Alien is killed on impact.
     * If an unmultiplied MultipleAlien is killed, call removeInvisibleNormalAliensPrepared().
     * @param bomb
     * @param spaceInvader
     * @param location
     */
    protected void notifyAlienHit(Bomb bomb, SpaceInvader spaceInvader, Location location){
        ArrayList<Actor> list = spaceInvader.getActorsAt(location);

        for (Actor actor: list) {
            if (actor instanceof Alien) {

                Alien alien = (Alien) actor;
                if (!alien.isInvulnerable() && alien.isVisible()) {
                    alien.decrementLife();
                    bomb.removeSelf();
                }
                
                if (alien.getNbLives()==0){
                    explode(spaceInvader, location);

                    // remove the row of normal aliens prepared for multiple alien multiplication.
                    if (alien.getType() == "multiple" && !((MultipleAlien) alien).isMultiplied()) {
                        Alien[][] alienGrid = spaceInvader.getAlienGrid();

                        removeInvisibleNormalAliensPrepared(alienGrid);

                    }
                }
            }
        }
    }


    /**
     * Used when bomb kills an alien.
     * Remove alien and create explosion.
     * @param spaceInvader
     * @param location
     */
    private void explode(SpaceInvader spaceInvader, Location location){
        spaceInvader.removeActorsAt(location, Alien.class);
        Explosion explosion = new Explosion();
        spaceInvader.addActor(explosion, location);
    }


    /**
     * Remove the topmost row of invisible Normal ALiens prepared for multiplication,
     * used when an unmultiplied MultipleAlien is killed.
     * @param alienGrid
     */
    private void removeInvisibleNormalAliensPrepared(Alien[][] alienGrid) {
        for (int k=0; k<alienGrid[0].length; k++) {
            alienGrid[MultipleAlien.getRowToRemove()][k].removeSelf();
        }
        MultipleAlien.decrementRowToRemove();
    }

}
