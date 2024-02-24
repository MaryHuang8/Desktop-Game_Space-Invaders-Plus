/**
 * 2023s2-project1-mon17-15-team-1
 * @author Luolan (Melanie) Huang, Luoman (Mary) Huang, Praghash Thanasegeren
 *
 * Main communicator to SpaceInvader. Controls most or all Alien related items
 */

import Alien.*;
import Alien.AlienGridLocation;
import ch.aplu.jgamegrid.Location;

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


public class AlienController {
    private ArrayList<AlienGridLocation> powerfulAlienLocations = new ArrayList<AlienGridLocation>();
    private ArrayList<AlienGridLocation> invulnerableAlienLocations = new ArrayList<AlienGridLocation>();
    private ArrayList<AlienGridLocation> multipleAlienLocations = new ArrayList<AlienGridLocation>();
    private ArrayList<MultipleAlien> multipleAliens = new ArrayList<>();
    private MultipleAlien curMultiplyingAlien = null;


    private ArrayList<AlienGridLocation> convertFromProperty(Properties properties,String propertyName) {
        String powerfulAlienString = properties.getProperty(propertyName);
        ArrayList<AlienGridLocation> alienLocations = new ArrayList<>();
        if (powerfulAlienString != null) {
            String [] locations = powerfulAlienString.split(";");
            for (String location: locations) {
                String [] locationPair = location.split("-");
                int rowIndex = Integer.parseInt(locationPair[0]);
                int colIndex = Integer.parseInt(locationPair[1]);
                alienLocations.add(new AlienGridLocation(rowIndex, colIndex));
            }
        }

        return alienLocations;
    }


    private void setupAlienLocations(Properties properties) {
        powerfulAlienLocations = convertFromProperty(properties,"Powerful.locations");
        invulnerableAlienLocations = convertFromProperty(properties,"Invulnerable.locations");
        multipleAlienLocations = convertFromProperty(properties, "Multiple.locations");
    }


    private boolean arrayContains(ArrayList<AlienGridLocation>locations, int rowIndex, int colIndex) {
        for (AlienGridLocation location : locations) {
            if (location.getRowIndex() == rowIndex && location.getColIndex() == colIndex) {
                return true;
            }
        }

        return false;
    }


    /**
     * Method sets up the Aliens in the gameGrid.
     * Each MultipleAlien added to the gameGrid would result in a row of invisible Normal Aliens added
     * above the existing alienGrid, in preparation for multiplication.
     * @param properties
     * @param nbRows
     * @param nbCols
     * @param spaceInvader
     * @return alienGrid 2D Alien[][] array
     */
    protected Alien[][] setupAliens(Properties properties, int nbRows, int nbCols, SpaceInvader spaceInvader) {
        setupAlienLocations(properties);
        Boolean isAutoTesting = Boolean.parseBoolean(properties.getProperty("isAuto"));
        String aliensControl = properties.getProperty("aliens.control");
        List<String> movements = null;
        if (aliensControl != null) {
            movements = Arrays.asList(aliensControl.split(";"));
        }
        Alien[][] alienGrid = new Alien[nbRows][nbCols];
        for (int i = 0; i < nbRows; i++) {
            for (int k = 0; k < nbCols; k++) {
                Alien alien = createAlien(i, k);

                alien.setTestingConditions(isAutoTesting, movements);
                spaceInvader.actorAdd(alien,new Location(100 - 5 * nbCols + 10 * k, 10 + 10 * i));

                alienGrid[i][k] = alien;
                spaceInvader.setIsAutoTesting(isAutoTesting);
            }
        }
        Alien[][] alienGrid2 = new Alien[nbRows + multipleAliens.size()][nbCols];

        for (int i = 0; i < nbRows; i++) {
            System.arraycopy(alienGrid[i], 0, alienGrid2[i], 0, nbCols);
        }


        //for games with multiple aliens, initialise the necessary static parameters for MultipleAlien.
        MultipleAlien.setInitRowToMultiply(alienGrid2.length - multipleAliens.size());
        MultipleAlien.setInitRowToRemove(alienGrid2.length - 1);

        for (int i=nbRows; i<nbRows + multipleAliens.size(); i++) {
            for (int k=0; k<nbCols; k++) {
                Alien multipliedAlien = new NormalAlien("sprites/alien.gif", "alien", i, k);
                multipliedAlien.setTestingConditions(isAutoTesting, movements);
                // set the normal aliens for multiplying above the preset alien grid
                spaceInvader.actorAdd(multipliedAlien,new Location(100 - 5 * nbCols + 10 * k, 10 - 10 * (i-nbRows+1)));
                spaceInvader.setIsAutoTesting(isAutoTesting);
                multipliedAlien.hide();
                alienGrid2[i][k] = multipliedAlien;
            }
        }

        return alienGrid2;
    }


    /**
     * Method controlling Alien creation dependent on Alien type.
     * @param i rowIndex
     * @param k colIndex
     * @return alien Alien
     */
    private Alien createAlien(int i, int k) {
        String imageName = "sprites/alien.gif";
        String type = "alien";
        Alien alien;
        if (arrayContains(powerfulAlienLocations, i, k)) {
            imageName = "sprites/powerful_alien.gif";
            type = "powerful";
            alien = new PowerfulAlien(imageName, type, i, k);
        } else if (arrayContains(invulnerableAlienLocations, i, k)) {
            imageName = "sprites/invulnerable_alien.gif";
            type = "invulnerable";
            alien = new InvulnerableAlien(imageName, type, i, k);
        } else if (arrayContains(multipleAlienLocations, i, k)) {
            imageName = "sprites/multiple_alien.gif";
            String[] filenames = {imageName, "sprites/alien.gif"};
            type = "multiple";
            alien = new MultipleAlien(filenames, type, i, k);
            multipleAliens.add((MultipleAlien)alien);

        } else{ //Normal alien
            alien = new NormalAlien(imageName, type, i, k);
        }
        return alien;
    }


    /**
     * Method for initiating the MultipleAlien multiplying process,
     * when the gameGrid still has MultipleAlien and the top space row is empty for multiplication.
     * @param spaceInvader SpaceInvader extending from GameGrid.
     * @return aboutToMultiply boolean status of multiplication initiation.
     */
    protected boolean triggerMultipleAlienMultiplication(SpaceInvader spaceInvader) {
        if (spaceInvader.getNumberOfActors(MultipleAlien.class) > 0 &&
                MultipleAlien.checkIsTopSpaceRowEmpty(spaceInvader.getAlienGrid())) {
            MultipleAlien.initiateMultiplyCountDown();
            return true;
        }
        return false;
    }


    /**
     * Method for updating the Alien grid after multiplying.
     * Process involves calling the multiply() method on a random alive and unmultiplied MultipleAlien
     * and resetting the multiplyCountdown.
     * @param alienGrid 2D Alien[][] array of alien grid
     */
    protected void updateAlienGridAfterMultiplying(Alien[][] alienGrid) {

        if (MultipleAlien.isMultiplyCountDownTriggered() && MultipleAlien.getMultiplyCountDown() == 0) {

            // first clean out the dead multiple aliens
            multipleAliens.removeIf(multipleAlien -> (multipleAlien.isRemoved()));

            // then clean out the multiple aliens who have already multiplied
            multipleAliens.removeIf(multipleAlien -> (multipleAlien.isMultiplied()));


            // a random alive multiple alien will multiply
            if (!multipleAliens.isEmpty()) {
                Random random = new Random();
                int multipleAlienToMultiply = random.nextInt(multipleAliens.size());
                curMultiplyingAlien = multipleAliens.get(multipleAlienToMultiply);
                curMultiplyingAlien.multiply(alienGrid);
                multipleAliens.remove(multipleAlienToMultiply);
                MultipleAlien.resetCountDownAndTrigger();
            }

        }
    }


    /**
     * Method checks if the multiplyCountdown is triggered.
     * @return isMultiplyCountDownTriggered boolean status of whether multiply alien's count-down is triggered.
     */
    protected boolean isMultiplyCountDownTriggered() {
        return MultipleAlien.isMultiplyCountDownTriggered();
    }


    /**
     * Method returns the number of MultipleALiens still alive and unmultiplied.
     * @return nbMultipleAliens in number of multiple aliens alive and unmultiplied.
     */
    public int getNbMultipleAliens() {
        return multipleAliens.size();
    }

}
