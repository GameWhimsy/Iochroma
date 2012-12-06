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

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import javax.swing.JOptionPane;

import org.gamewhimsy.iochroma.Editor;
import org.gamewhimsy.iochroma.Resources;

/**
 * Provides a common abstract class for actions that save or might discard
 * the level.
 */
public abstract class AbstractSaveAction extends AbstractAction {

    protected final Editor editor;

    /**
     * Constructs the abstract save action.
     *
     * @param editor the level editor
     * @param name name text
     * @param description tooltip text
     * @param key accelerator key
     */
    protected AbstractSaveAction(Editor editor, String name, String description, String key) {
        super(name);
        this.editor = editor;
        putValue(SHORT_DESCRIPTION, description);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(key));
    }

    /**
     * Performs the action.
     */
    public abstract void actionPerformed(ActionEvent e);

    /**
     * Checks for unsaved level changes and, if any, prompts the user whether
     * save them and then saves if the answer is yes.
     * <p />
     * User options are: yes, no, cancel.
     *
     * @return false if action is cancelled, otherwise true
     */
    protected boolean saveChanges() {

        if (editor.hasUnsavedChanges()) {
            int answer = JOptionPane.showConfirmDialog(editor.getAppFrame(), Resources.getString("prompt.save.level.changes.text"), Resources.getString("prompt.save.changes"), JOptionPane.YES_NO_CANCEL_OPTION);

            switch (answer) {
                case JOptionPane.YES_OPTION:
                    saveLevel();
                    break;
                case JOptionPane.NO_OPTION:
                    // don't save
                    break;
                // treat these as action cancelled
                case JOptionPane.CANCEL_OPTION:
                case JOptionPane.CLOSED_OPTION:
                default:
                    return false;
            }
        }

        return true;

    }

    /**
     * Prompts the user to select the filename and file type.
     * <p />
     * User may cancel action.
     *
     * @return false if action is cancelled, otherwise true
     */
    protected boolean chooseFile() {
        // TODO - implement this and have it return selection info
        return false;
    }

    /**
     * Saves the level.
     */
    protected void saveLevel() {
        // TODO
    }

}
