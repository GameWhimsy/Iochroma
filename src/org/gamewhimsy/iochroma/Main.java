package org.gamewhimsy.iochroma;

import java.io.File;
import tiled.mapeditor.MapEditor;
import tiled.util.TiledConfiguration;

/**
 * Main class for the Iochroma level editor.
 * <p />
 * This is a heavily modified version of the Tiled map editor.
 *
 * @author modified by Tulonsae
 */
public class Main {

    /**
     * Main entry point for the level editor.
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
