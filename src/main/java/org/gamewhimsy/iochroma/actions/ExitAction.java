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
 * Exits the application gracefully.
 * <ul>
 * <li>All levels will be closed, presenting the user with an opportunity to
 *     save any unsaved changes.</li>
 * <li>Performs any needed cleanup.</li>
 * <li>Shuts down the application.</li>
 * </ul>
 * <p />
 * If the user is presented any choice and cancels it, then the exit action
 * will be cancelled also.
 */
public class ExitAction extends AbstractBaseAction {

    private ActionHelper helper;

    /**
     * Constructs this action.
     *
     * @param helper the action helper
     */
    public ExitAction(ActionHelper helper) {
        super(Resources.getString("menu.file.exit"), Resources.getString("menu.file.exit.tooltip"), Resources.getString("menu.file.exit.key"));

        this.helper = helper;
    }

    /**
     * Performs this action.
     *
     * @param e action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        helper.doExitAction();
    }
}
