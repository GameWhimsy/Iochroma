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
 * Closes the currently active level.
 * <p />
 * Before closing, the user will be prompted to save any unsaved changes.
 * If the user cancels saving, then the close action will be cancelled also.
 */
public class CloseAction extends AbstractBaseAction {

    private ActionHelper helper;

    /**
     * Constructs this action.
     *
     * @param helper the action helper
     */
    public CloseAction(ActionHelper helper) {
        super(Resources.getString("menu.file.close"), Resources.getString("menu.file.close.tooltip"), Resources.getString("menu.file.close.key"));

        this.helper = helper;
    }

    /**
     * Performs this action.
     *
     * @param e action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        helper.doCloseAction();
    }
}
