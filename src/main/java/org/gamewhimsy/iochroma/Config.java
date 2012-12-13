/*
 * Iochroma Level Editor (c) 2012 Tulonsae
 *
 * Modified from:
 *  Tiled Map Editor, (c) 2004-2008
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  Adam Turk <aturk@biggeruniverse.com>
 *  Bjorn Lindeijer <bjorn@lindeijer.nl>
 */

package org.gamewhimsy.iochroma;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.prefs.Preferences;

/**
 * This class handles preferences.
 */
public final class Config
{
    private static final Preferences prefs = Preferences.userRoot().node(Editor.APP_NAME);
    private static final int numRecentFiles = Editor.NUM_RECENT_FILES;

    private static final String recentKey = "recent";
    private static final String filepathKey = "file";

    /**
     * Gets the root preferences node for the application configuration.
     *
     * @return the root preferences node
     */
    public static Preferences getRootNode() {
        return prefs;
    }

    /**
     * Gets the specified preferences node from the application configuration.
     *
     * @param name the path name relative from the root node
     * @return the requested preferences node
     */
    public static Preferences getNode(String name) {
        return prefs.node(name);
    }

    /**
     * Gets the directory location of the most recent level file.
     *
     * @return the directory of the most recent file, null if none found
     */
    public static File getMostRecentFileDirectory() {
        String recent = prefs.node(recentKey).get(filepathKey + "0", null);

        if (recent == null) {
            return null;
        }

        File filepath = new File(recent);
        return filepath.getParentFile();
    }

    /**
     * Gets the list of recently used level files.
     * <p />
     * The list is ordered by when used, with most recent first.
     *
     * @return the list of recent files
     */
    public static List<String> getRecentFiles() {
        List<String> recentList = new ArrayList<String>(numRecentFiles);

        for (int i = 0; i < numRecentFiles; i++) {
            String recentFile = prefs.node(recentKey).get(filepathKey + i, "");
            if (recentFile.length() > 0) {
                recentList.add(recentFile);
            }
        }

        return recentList;
    }

    /**
     * Add the specified file to the beginning of the recent file list.
     * Remove any previous instances of the file path from the list.
     *
     * @param newRecentFile the absolute path of the file to add
     * @return false if the newRecentFile was null, otherwise true
     */
    public static boolean addToRecentFiles(String newRecentFile) {
        if (newRecentFile == null) {
            return false;
        }

        List<String> recentList = getRecentFiles();

        // remove any existing occurences of the file
        while (recentList.contains(newRecentFile)) {
            recentList.remove(newRecentFile);
        }

        // add the new recent file to the top of the list
        recentList.add(0, newRecentFile);

        // save the new list in preferences, limited to numRecentFiles
        Preferences recentNode = prefs.node(recentKey);
        for (int i = 0; (i < numRecentFiles) && (i < recentList.size()); i++) {
            recentNode.put(filepathKey + i, recentList.get(i));
        }

        return true;
    }

}
