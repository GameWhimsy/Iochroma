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
import org.gamewhimsy.iochroma.Level;
import org.gamewhimsy.iochroma.Resources;

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
            openFile(openFilepath);
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
    private void openFile(String filepath) {
        File levelFile = new File(filepath);

        if (!levelFile.exists()) {
            showLoadLevelError(Resources.getString("file.not.exist"));
            return;
        }
        if (!levelFile.isFile()) {
            showLoadLevelError(Resources.getString("file.not.normal"));
            return;
        }
        if (!levelFile.canRead()) {
            showLoadLevelError(Resources.getString("file.not.readable"));
            return;
        }
    }

    /**
     * Shows specified text message for load level errors.
     *
     * @param textMessage the text for the message dialog
     */
    protected void showLoadLevelError(String textMessage) {
        JOptionPane.showMessageDialog(editor.getAppFrame(), Resources.getString("error.open.level"), textMessage, JOptionPane.ERROR_MESSAGE);
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
