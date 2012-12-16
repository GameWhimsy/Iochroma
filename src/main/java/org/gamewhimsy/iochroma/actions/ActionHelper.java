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

package org.gamewhimsy.iochroma.actions;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.gamewhimsy.iochroma.Config;
import org.gamewhimsy.iochroma.Editor;
import org.gamewhimsy.iochroma.Resources;
import org.gamewhimsy.iochroma.core.Level;
import org.gamewhimsy.iochroma.core.UnsupportedFileFormatException;

/**
 * Object containing helper methods for actions.
 */
public class ActionHelper {

    private final Editor editor;
    private String openFilepath = null;

    /**
     * Constructs the action helper object.
     *
     * @param editor the level editor
     */
    public ActionHelper(Editor editor) {
        this.editor = editor;
    }

    /**
     * Handles the close action.
     */
    protected void doCloseAction() {
        Level activeLevel = editor.getActiveLevel();

        closeLevel(activeLevel);
    }

    /**
     * Handles the exit action.
     */
    protected void doExitAction() {
        if (editor.shutdown()) {
            System.exit(0);
        }
    }

    /**
     * Handles the open action.
     */
    protected void doOpenAction() {
        if (chooseOpenFile()) {
            try {
                Level level = openFile(openFilepath);
                editor.addLevel(level);
            } catch (UnsupportedFileFormatException e) {
                showFileTypeError(e.getMessage());
            } catch (Exception e) {
                // do nothing
            }
        }
    }

    /**
     * Handles the save action.
     */
    protected void doSaveAction() {
        Level activeLevel = editor.getActiveLevel();

        saveLevel(activeLevel);
    }

    /**
     * Handles the saveAs action.
     */
    protected void doSaveAsAction() {
        Level activeLevel = editor.getActiveLevel();

        saveLevel(activeLevel, true);
    }

    /**
     * Closes specified level.
     *
     * @param level the level
     */
    private void closeLevel(Level level) {
        if (saveChanges(level)) {
            editor.removeLevel(level);
        }
    }

    /**
     */
    private Level openFile(String filepath) throws Exception {
        File levelFile = new File(filepath);

        if (!checkFile(levelFile)) {
            throw new Exception("Cannot open level file");
        }

        return new Level(levelFile);
    }

    /**
     * Checks if file can be opened.
     * <p />
     * Displays a message with the exact problem if a file can't be opened.
     *
     * @param file file to be checked
     * @return false if file can't be opened, true otherwise
     */
    private boolean checkFile(File file) {

        if (!file.exists()) {
            showOpenFileError(Resources.getString("file.not.exist"));
            return false;
        }
        if (!file.isFile()) {
            showOpenFileError(Resources.getString("file.not.normal"));
            return false;
        }
        if (!file.canRead()) {
            showOpenFileError(Resources.getString("file.not.readable"));
            return false;
        }

        return true;
    }

    /**
     * Shows specified text message for open file error.
     *
     * @param textMessage the text for the message dialog
     */
    protected void showOpenFileError(String textMessage) {
        JOptionPane.showMessageDialog(editor.getAppFrame(), textMessage, Resources.getString("error.open.file"), JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Shows unsupported file type error.
     *
     * @param type the unrecognized extension
     */
    protected void showFileTypeError(String type) {
        JOptionPane.showMessageDialog(editor.getAppFrame(), type + " - " + Resources.getString("file.format.unsupported"), Resources.getString("error.file.format"), JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Prompts the user to select the file to open.
     *
     * @return false if cancelled, otherwise true
     */
    private boolean chooseOpenFile() {
        JFileChooser chooser = new JFileChooser(Config.getMostRecentFileDirectory());

        int answer = chooser.showOpenDialog(editor.getAppFrame());

        switch (answer) {
            // APPROVE option
            case JFileChooser.APPROVE_OPTION:
                openFilepath = chooser.getSelectedFile().getAbsolutePath();
                return true;
            // treat these as CANCEL option
            case JFileChooser.CANCEL_OPTION:
            case JFileChooser.ERROR_OPTION:
            default:
                break;
        }

        // cancel the action
        return false;
    }

    /**
     * Saves any unsaved changes for the specified level if user answers yes.
     * <p />
     * User options are: yes, no, cancel.
     *
     * @param level the level
     * @return false if cancelled or save error, otherwise true
     */
    private boolean saveChanges(Level level) {

        if (level.hasUnsavedChanges()) {
            int answer = JOptionPane.showConfirmDialog(editor.getAppFrame(), Resources.getString("prompt.save.level.changes.text"), Resources.getString("prompt.save.changes"), JOptionPane.YES_NO_CANCEL_OPTION);

            switch (answer) {
                // YES option - attempt to save
                case JOptionPane.YES_OPTION:
                    if (saveLevel(level)) {
                        return true;
                    } else {
                        return false;
                    }
                // NO option - don't save
                case JOptionPane.NO_OPTION:
                    return true;
                // treat these as CANCEL option
                case JOptionPane.CANCEL_OPTION:
                case JOptionPane.CLOSED_OPTION:
                default:
                    return false;
            }
        }

        // no unsaved changes so perform action
        return true;
    }

    /**
     * Saves the specified level.
     *
     * @param level the level
     * @return false if cancelled or save error, otherwise true
     */
    private boolean saveLevel(Level level) {
        boolean flag = true;

        if (level.hasFile()) {
            flag = false;
        }

        return saveLevel(level, flag);
    }

    /**
     * Saves the specified level.
     *
     * @param level the level
     * @param flag if true, prompt for save file
     * @return false if cancelled or save error, otherwise true
     */
    private boolean saveLevel(Level level, boolean flag) {
        if (flag) {
            if (!chooseSaveFile(level)) {
                return false;
            }
        }

        if (level.save()) {
            return true;
        }

        // level failed to save
        return false;
    }

    /**
     * Prompts the user to select the filename and file type when saving.
     *
     * @param level the level
     * @return false if cancelled, otherwise true
     */
    private boolean chooseSaveFile(Level level) {
        // TODO
        return false;
    }

}
