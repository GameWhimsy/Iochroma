package org.gamewhimsy.iochroma.core;

import java.io.File;

/**
 * The level class for the Iochroma level editor.
 * <p />
 * This is part of the core which may be used by other programs to manipulate
 * Iochroma editor files.
 *
 * @author Tulonsae
 */
public final class Level {

    private boolean unsavedChanges = false;

    private String filePath = null;
    private String fileName = null;
    private String fileExtension = null;
    private String fileType = null;

    /**
     * Constructs a new (empty) level.
     */
    public Level() {
    }

    /**
     * Constructs a level from a file.
     * <p />
     * Uses the extension of the file name to determine the format type of
     * the level file.
     *
     * @param file the level file
     */
    public Level(File file) {
        filePath = file.getAbsolutePath();
        fileName = file.getName();
        fileExtension = parseFileExtension(fileName);
        fileType = parseFileType(fileExtension);
    }

    /**
     * Indicates if a file (name and type) is associated with this level.
     *
     * @return true if a file name and type is known, otherwise false
     */
    public boolean hasFile() {
        return ((filePath != null) && (fileType != null));
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
     * Sets the save state of changes for this level.
     *
     * @param flag true if unsaved changes
     */
    public void setUnsavedChanges(boolean flag) {
        unsavedChanges = flag;
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

    /**
     * Parses the file's extension.
     * <p />
     * The exact case is kept.
     *
     * @param name the file name
     * @return the extension (e.g., tmx)
     */
    private String parseFileExtension(String name) {
        return name.substring(name.lastIndexOf(".") + 1);
    }

    /**
     * Parses the file type, based on the file name extension.
     *
     * @param ext file extension (e.g., tmx)
     * @return the file type
     * @throws UnsupportedFileFormatException if the extension is unknown
     */
    private String parseFileType(String ext) throws UnsupportedFileFormatException {
        String type = null;

        if (ext.equalsIgnoreCase("tmx")) {
            type = "tmx";
        } else {
            throw new UnsupportedFileFormatException("unknown extension " + ext);
        }

        return type;
    }

}
