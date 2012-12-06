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

import org.gamewhimsy.iochroma.Editor;
import org.gamewhimsy.iochroma.Resources;

/**
 * Saves the level after prompting for a file name if it's not set.
 */
public class SaveAction extends AbstractSaveAction {

    /**
     * Constructs the save action.
     *
     * @param editor the level editor
     */
    public SaveAction(Editor editor) {
        super(editor, Resources.getString("menu.file.save"), Resources.getString("menu.file.save.tooltip"), Resources.getString("menu.file.save.key"));
    }

    /**
     * Saves the level.
     *
     * @param e action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // check if the level has been assigned a file name and type
        if (!editor.getCurrentLevel().hasFile()) {
            // file chooser returns false if action is cancelled
            if (!chooseFile()) {
                return;
            }
        }
        saveLevel();
    }

}
