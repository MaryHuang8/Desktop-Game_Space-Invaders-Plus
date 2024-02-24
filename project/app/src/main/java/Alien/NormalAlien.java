/**
 * 2023s2-project1-mon17-15-team-1
 * @author Luolan (Melanie) Huang, Luoman (Mary) Huang, Praghash Thanasegeren
 */
package Alien;
/**
 * General Alien extended from Alien. Essentially, default normal Alien.
 */
public class NormalAlien extends Alien {
    /**
     * Creates an invulnerable alien.
     * @param imageName String file name of the normal alien image.
     * @param type String normal alien type "alien"
     * @param rowIndex Row index of this normal alien in the alien grid.
     * @param colIndex Column index of this normal alien in the alien grid.
     */
    public NormalAlien(String imageName, String type, int rowIndex, int colIndex){
        super(imageName, type, rowIndex, colIndex);
    }

}
