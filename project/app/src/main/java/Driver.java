/**
 * 2023s2-project1-mon17-15-team-1
 * @author Luolan (Melanie) Huang, Luoman (Mary) Huang, Praghash Thanasegeren
 *
 * Main and loads properties for the level
 */

import java.util.Properties;

public class Driver {
    /**
     * Filename for the properties
     */
    public static final String DEFAULT_PROPERTIES_PATH = "properties/game3.properties";

    /**
     * main to initiation of the game
     * @param args
     */
    public static void main(String[] args) {
        String propertiesPath = DEFAULT_PROPERTIES_PATH;
        if (args.length > 0) {
            propertiesPath = args[0];
        }
        final Properties properties = PropertiesLoader.loadPropertiesFile(propertiesPath);

        String logResult = new SpaceInvader(properties).runApp(true);
        System.out.println("logResult = " + logResult);
    }
}
