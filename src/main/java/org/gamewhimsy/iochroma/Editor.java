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

import org.gamewhimsy.iochroma.actions.ActionHelper;
import org.gamewhimsy.iochroma.actions.CloseAction;
import org.gamewhimsy.iochroma.actions.ExitAction;
import org.gamewhimsy.iochroma.actions.OpenAction;
import org.gamewhimsy.iochroma.actions.SaveAction;
import org.gamewhimsy.iochroma.actions.SaveAsAction;

/**
 * Main class for the Iochroma level editor.
 */
public class Editor {

    /**
     * Application name.
     */
    protected static final String APP_NAME = "Iochroma";
    protected static final int NUM_RECENT_FILES = 8;

    private static final int APP_WIDTH = 800;
    private static final int APP_HEIGHT = 600;

    private JFrame appFrame;
    private JMenuBar menuBar;
    private JPanel mainPanel;

    private final ActionHelper actionHelper;
    private final Action closeAction;
    private final Action exitAction;
    private final Action openAction;
    private final Action saveAction;
    private final Action saveAsAction;

    private Level activeLevel;
    private Level activeLevelNext;

    private List<JMenuItem> listMenuItemNoLevel = new LinkedList<JMenuItem>();
    private List<Level> levels = new LinkedList<Level>();


    /**
     * Constructs the Editor object.
     */
    public Editor() {

        // create the action helper and actions
        actionHelper = new ActionHelper(this);
        closeAction = new CloseAction(actionHelper);
        exitAction = new ExitAction(actionHelper);
        openAction = new OpenAction(actionHelper);
        saveAction = new SaveAction(actionHelper);
        saveAsAction = new SaveAsAction(actionHelper);

        // create the application frame
        appFrame = new JFrame(APP_NAME);
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

        setActiveLevel(null);
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
     * Gets the active level.
     *
     * @return the active level
     */
    public Level getActiveLevel() {
        return activeLevel;
    }

    /**
     * Sets the active level.
     * <p />
     * Specifiy <tt>null</tt> to have no active levels.
     *
     * @param level level to load as active
     */
    public void setActiveLevel(Level level) {

        activeLevel = level;
        boolean flag;

        if (activeLevel == null) {  // level was unloaded
            activeLevelNext = null;
            flag = false;
        } else {  // level was loaded
            flag = true;
            // determine which level would be active if this level is removed
            int cur = levels.indexOf(activeLevel);
            if (cur == 0) {  // active is the only level
                    activeLevelNext = null;
            } else {
                if (cur == (levels.size() - 1)) {  // active is at end of list
                    activeLevelNext = levels.get(cur - 1);
                } else {  // active is not at end
                    activeLevelNext = levels.get(cur + 1);
                }
            }
        }

        // update gui
        updateMenuItems(listMenuItemNoLevel, flag);
    }

    /**
     * Adds a level to the list, making it the active level.
     *
     * @param level level to be added
     * @return true if level added, otherwise false
     */
    public boolean addLevel(Level level) {
        if (levels.add(level)) {
            setActiveLevel(level);
            // TODO update the gui
        }

        return false;
    }

    /**
     * Removes a level from the list, changing the active level.
     * <p />
     * The next level in the list will become active.  If there is no next
     * level, then the previous level will become active.  If there are no
     * subsequent levels, then it sets the active level to <tt>null</tt>.
     *
     * @param level level to be removed
     * @return true if level removed, otherwise false
     */
    public boolean removeLevel(Level level) {

        if (levels.remove(level)) {
            setActiveLevel(activeLevelNext);
            // TODO update the gui
        }

        return false;
    }

    /**
     * Handles a graceful shutdown when the editor is exiting.
     *
     * @return false if cancelled or error, true otherwise
     */
    public boolean shutdown() {
        // TODO

        return true;
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
        fileMenu.add(new JMenuItem(openAction));
        fileMenu.addSeparator();
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
     * Loads a level map.
     *
     * @param filepath absolute filepath of the level to load
     */
    public void loadLevel(String filepath) {
    }

}
