package org.gamewhimsy.iochroma;

/**
 * The level class for the Iochroma level editor.
 *
 * @author Tulonsae
 */
public class Level {

    private boolean unsavedChanges = false;

    /**
     * Constructor for the level.
     */
    public Level() {
    }

    /**
     * Indicates if a file (name and type) is associated with this level.
     *
     * @return true if a file name and type is known, otherwise false
     */
    public boolean hasFile() {
        return false;
    }

    /**
     * Gets the save state of changes for this level.
     *
     * @return true if the active level has unsaved changes, otherwise false
     */
    // TODO - add undo stuff
    public boolean hasUnsavedChanges() {
        return unsavedChanges;
    }

    /**
     * Saves the level.
     *
     * @return true if saved, false otherwise
     */
    public boolean save() {
        // TODO
        return true;
    }

}
