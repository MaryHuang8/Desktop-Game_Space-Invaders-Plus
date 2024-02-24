/**
 * 2023s2-project1-mon17-15-team-1
 * @author Luolan (Melanie) Huang, Luoman (Mary) Huang, Praghash Thanasegeren
 *
 * Invulnerable Alien from abstraction of Alien
 */
package Alien;

public class InvulnerableAlien extends Alien{

    /**
     * Max numbers of steps being invulnerable
     */
    public final int STEPS_INVULNERABLE = 5;
    private int invulnerabilityTimer = 0;


    /**
     * Creates an invulnerable alien.
     * @param imageFile String file name of the invulnerable alien image.
     * @param type Invulnerable alien type "invulnerable"
     * @param rowIndex Row index of this multiple alien in the alien grid.
     * @param colIndex Column index of this multiple alien in the alien grid.
     */
    public InvulnerableAlien(String imageFile, String type, int rowIndex, int colIndex){
        super(imageFile, type, rowIndex, colIndex);
        super.nbLives = 1;

    }


    /**
     * Update function for update
     */
    @Override
    public void act()
    {
        super.act();
        if (!super.isInvulnerable()){
            super.setInvulnerable(randomBoolean());

            if (super.isInvulnerable()) {
                invulnerabilityTimer = 0;
            }
        }
        if (super.isInvulnerable()) {
            invulnerabilityTimer++;


            if (invulnerabilityTimer >= STEPS_INVULNERABLE) {
                super.setInvulnerable(false);
                invulnerabilityTimer = 0;
            }
        }
    }

}
