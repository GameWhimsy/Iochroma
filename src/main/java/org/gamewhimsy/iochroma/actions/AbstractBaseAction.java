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

/**
 * Provides a common abstract class for actions that save, discard, or load a
 * level.
 */
public abstract class AbstractBaseAction extends AbstractAction {

    protected String openFilepath = null;

    /**
     * Constructs the base action.
     *
     * @param name name text
     * @param description tooltip text
     * @param key accelerator key
     */
    protected AbstractBaseAction(String name, String description, String key) {
        super(name);
        putValue(SHORT_DESCRIPTION, description);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(key));
    }

    /**
     * Performs the action.
     */
    public abstract void actionPerformed(ActionEvent e);

}
