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

import org.gamewhimsy.iochroma.Resources;

/**
 * Opens and loads a level.
 * <ul>
 * <li>The user will be prompted to save any unsaved changes of the active
 *     level.</li>
 * <li>Prompts the user for a level file to open.</li>
 * <li>Loads the level from the file.</li>
 * <li>Makes it the new active level.</li>
 * </ul>
 * <p />
 * If the user is presented any choice and cancels it, then the open action
 * will be cancelled also.
 */
public class OpenAction extends AbstractBaseAction {

    private ActionHelper helper;

    /**
     * Constructs this action.
     *
     * @param helper the action helper
     */
    public OpenAction(ActionHelper helper) {
        super(Resources.getString("menu.file.open"), Resources.getString("menu.file.open.tooltip"), Resources.getString("menu.file.open.key"));

        this.helper = helper;
    }

    /**
     * Performs this action.
     *
     * @param e action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        helper.doOpenAction();
    }
}
