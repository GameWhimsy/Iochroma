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
 * Closes the currently opened level.
 */
public class CloseAction extends AbstractSaveAction {

    /**
     * Constructs the close action.
     *
     * @param editor the level editor
     */
    public CloseAction(Editor editor) {
        super(editor, Resources.getString("menu.file.close"), Resources.getString("menu.file.close.tooltip"), Resources.getString("menu.file.close.key"));
    }

    /**
     * Closes the level after checking for changes.
     *
     * @param e action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // save changes returns false if action is cancelled
        if (saveChanges()) {
            editor.setCurrentLevel(null);
        }
    }
}
