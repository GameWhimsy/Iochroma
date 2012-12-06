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
 * Exits the app after prompting to save the level if it's been modified.
 */
public class ExitAction extends AbstractSaveAction {

    /**
     * Constructs the exit action.
     *
     * @param editor the level editor
     */
    public ExitAction(Editor editor) {
        super(editor, Resources.getString("menu.file.exit"), Resources.getString("menu.file.exit.tooltip"), Resources.getString("menu.file.exit.key"));
    }

    /**
     * Exits the application after checking for changes and graceful shutdown.
     *
     * @param e action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // save changes returns false if action is cancelled
        if (saveChanges()) {
            editor.shutdown();
            System.exit(0);
        }
    }
}
