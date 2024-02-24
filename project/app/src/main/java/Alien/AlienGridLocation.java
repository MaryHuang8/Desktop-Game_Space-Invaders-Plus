/**
 * 2023s2-project1-mon17-15-team-1
 * @author Luolan (Melanie) Huang, Luoman (Mary) Huang, Praghash Thanasegeren
 *
 * Handle the grid for where the Aliens are created
 */
package Alien;

public class AlienGridLocation {
    private int rowIndex;
    private int colIndex;

    public AlienGridLocation(int rowIndex, int colIndex) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }
}
