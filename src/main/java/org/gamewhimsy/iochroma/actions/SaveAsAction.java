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
 * Saves the level with the chosen name and file format.
 */
public class SaveAsAction extends AbstractSaveAction {

    /**
     * Constructs the save as action.
     *
     * @param editor the level editor
     */
    public SaveAsAction(Editor editor) {
        super(editor, Resources.getString("menu.file.saveas"), Resources.getString("menu.file.saveas.tooltip"), Resources.getString("menu.file.saveas.key"));
    }

    /**
     * Saves the level after prompting for choice.
     *
     * @param e action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // file chooser returns false if action is cancelled
        if (chooseFile()) {
            // TODO add file format
            saveLevel();
        }
    }

}
