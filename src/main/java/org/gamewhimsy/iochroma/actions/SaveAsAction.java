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
 * Saves the active level with the chosen name and file format.
 * <p />
 * If the user cancels choosing, then the save action will be cancelled also.
 */
public class SaveAsAction extends AbstractBaseAction {

    private ActionHelper helper;

    /**
     * Constructs this action.
     *
     * @param helper the action helper
     */
    public SaveAsAction(ActionHelper helper) {
        super(Resources.getString("menu.file.saveas"), Resources.getString("menu.file.saveas.tooltip"), Resources.getString("menu.file.saveas.key"));

        this.helper = helper;
    }

    /**
     * Performs this action.
     *
     * @param e action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        helper.doSaveAsAction();
    }
}
