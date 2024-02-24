/**
 * 2023s2-project1-mon17-15-team-1
 * @author Luolan (Melanie) Huang, Luoman (Mary) Huang, Praghash Thanasegeren
 *
 * Aliens that more numbers of hits before dying.
 */
package Alien;

public class PowerfulAlien extends Alien{

    /**
     * Number of maximum lives
     */
    public final int STARTING_NB_LIVES = 5;

    /**
     * Creates an powerful alien.
     * @param imageName String file name of the powerful alien image.
     * @param type String powerful alien type "alien"
     * @param rowIndex Row index of this powerful alien in the alien grid.
     * @param colIndex Column index of this powerful alien in the alien grid.
     */
    public PowerfulAlien(String imageName, String type, int rowIndex, int colIndex){
        super(imageName, type, rowIndex, colIndex);
        super.nbLives = STARTING_NB_LIVES;
    }
}
