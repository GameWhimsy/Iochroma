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

package org.gamewhimsy.iochroma;

import java.util.ResourceBundle;

/**
 * Implements static accessors to editor resources: strings and icons.
 */
public final class Resources {

    // the resource bundle
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("resources.gui");

    /**
     * Retrieves a string from the resource bundle in the default locale.
     *
     * @param key the key
     * @return the resource string
     */
    public static String getString(String key) {
        return resourceBundle.getString(key);
    }

    // TODO - add icons
}
