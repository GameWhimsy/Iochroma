package tiled;

import java.io.File;
import tiled.mapeditor.MapEditor;
import tiled.util.TiledConfiguration;

/**
 *
 * @author upachler
 */
public class Main {

    /**
     * Starts Tiled.
     *
     * @param args unused
     */
    public static void main(String[] args) {

        MapEditor editor = new MapEditor();
        if (TiledConfiguration.node("io").getBoolean("autoOpenLast", false)) {
            // Load last map if it still exists
            java.util.List<String> recent = TiledConfiguration.getRecentFiles();
            if (!recent.isEmpty()) {
                String filename = recent.get(0);
                if (new File(filename).exists()) {
                    editor.loadMap(filename);
                }
            }
        }        
    }

}
