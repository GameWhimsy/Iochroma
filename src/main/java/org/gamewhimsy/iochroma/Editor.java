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

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.gamewhimsy.iochroma.actions.CloseAction;
import org.gamewhimsy.iochroma.actions.ExitAction;
import org.gamewhimsy.iochroma.actions.SaveAction;
import org.gamewhimsy.iochroma.actions.SaveAsAction;

/**
 * Main class for the Iochroma level editor.
 */
public class Editor {

    private static final int APP_WIDTH = 800;
    private static final int APP_HEIGHT = 600;

    private boolean unsavedChanges = false;

    private JFrame appFrame;
    private JMenuBar menuBar;
    private JPanel mainPanel;

    private final Action closeAction;
    private final Action exitAction;
    private final Action saveAction;
    private final Action saveAsAction;

    private Level currentLevel;

    private List<JMenuItem> listMenuItemNoLevel = new LinkedList<JMenuItem>();


    /**
     * Constructs the Editor object.
     */
    public Editor() {

        // create the actions
        closeAction = new CloseAction(this);
        exitAction = new ExitAction(this);
        saveAction = new SaveAction(this);
        saveAsAction = new SaveAsAction(this);

        // create the application frame
        appFrame = new JFrame("Iochroma");
        appFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        appFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                exitAction.actionPerformed(null);
            }
        });
        appFrame.setContentPane(createMainPanel());
        appFrame.setJMenuBar(createMenuBar());
        appFrame.setSize(APP_WIDTH, APP_HEIGHT);
        appFrame.setVisible(true);

        setCurrentLevel(null);
    }

    /**
     * Gets the main application frame.
     *
     * @return the main application frame
     */
    public JFrame getAppFrame() {
        return appFrame;
    }

    /**
     * Gets the current level.
     *
     * @return the current level
     */
    public Level getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Sets the current level.
     *
     * @param level level to load as current
     */
    public void setCurrentLevel(Level current) {

        currentLevel = current;

        // unload level
        if (currentLevel == null) {
            updateMenuItems(listMenuItemNoLevel, false);
            return;
        }

        // load level
        updateMenuItems(listMenuItemNoLevel, true);
    }

    /**
     * Gets the save state of changes for the current level.
     *
     * @return true if the current level has unsaved changes, otherwise false
     */
    // TODO - had undo stuff
    public boolean hasUnsavedChanges() {
        return unsavedChanges;
    }

    /**
     * Handles a graceful shutdown when the editor is exiting.
     */
    public void shutdown() {
        // TODO
    }

    /**
     * Creates the main application panel.
     *
     * @return the main app panel
     */
    private JPanel createMainPanel() {

/*
        levelScrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        levelScrollPane.setBorder(null);
*/

        mainPanel = new JPanel(new BorderLayout());

        return mainPanel;
    }

    /**
     * Create the application menu bar.
     *
     * @return the app menu bar
     */
    private JMenuBar createMenuBar() {

        JMenuItem close = new JMenuItem(closeAction);
        JMenuItem save = new JMenuItem(saveAction);
        JMenuItem saveAs = new JMenuItem(saveAsAction);

        // create file menu
        JMenu fileMenu = new JMenu(Resources.getString("menu.file"));
        fileMenu.add(save);
        fileMenu.add(saveAs);
        fileMenu.addSeparator();
        fileMenu.add(close);
        fileMenu.add(new JMenuItem(exitAction));

        // menu items affected by no level loaded
        listMenuItemNoLevel.add(save);
        listMenuItemNoLevel.add(saveAs);
        listMenuItemNoLevel.add(close);

        // create menu bar
        menuBar = new JMenuBar();
        menuBar.add(fileMenu);

        return menuBar;
    }

    /**
     * Disable or enable menu items.
     *
     * @param list linked list of menu items
     * @param flag enable if true, disable if false
     */
    private void updateMenuItems(List<JMenuItem> list, boolean flag) {
        for (JMenuItem item : list) {
            item.setEnabled(flag);
        }
    }

}
