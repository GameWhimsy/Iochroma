/*
 *  Tiled Map Editor, (c) 2004
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 * 
 *  Adam Turk <aturk@biggeruniverse.com>
 *  Bjorn Lindeijer <b.lindeijer@xs4all.nl>
 *  Rainer Deyke <rainerd@eldwood.com>
 */

package tiled.mapeditor;

import java.awt.*;
import java.awt.event.*;
//import java.awt.image.BufferedImage;
import java.util.Enumeration;
import java.util.Vector;

//import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import tiled.core.*;
import tiled.mapeditor.util.*;
import tiled.mapeditor.widget.*;


public class TileImageDialog extends JDialog
    implements ActionListener, ListSelectionListener
{
    private JList imageList;
    private JButton bOk, bCancel;
    private JCheckBox horizFlipCheck, vertFlipCheck, rotateCheck;
    private int imageId, imageOrientation;
    private TileSet tileset;
    private JLabel imageLabel;
    private int[] imageIds;

    public TileImageDialog(Dialog parent, TileSet set) {
        this(parent, set, 0, 0);
    }

    public TileImageDialog(Dialog parent, TileSet set, int id,
        int orientation) {
        super(parent, "Choose Tile Image", true);
        tileset = set;
        imageId = id;
        imageOrientation = orientation;
        init();
        queryImages();
        updateImageLabel();
        pack();
        setLocationRelativeTo(getOwner());
    }

    private void init() {
        // image list
        imageList = new JList();
        imageList.setCellRenderer(new ImageCellRenderer());
        imageList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        imageList.addListSelectionListener(this);
        JScrollPane sp = new JScrollPane();
        sp.getViewport().setView(imageList);
        sp.setPreferredSize(new Dimension(150, 150));
        
        // image panel
        JPanel image_panel = new JPanel();
        image_panel.setLayout(new BoxLayout(image_panel, BoxLayout.Y_AXIS));
        imageLabel = new JLabel(new ImageIcon());
        horizFlipCheck = new JCheckBox("Flip horizontally",
            (imageOrientation & 1) == 1);
        horizFlipCheck.addActionListener(this);
        vertFlipCheck = new JCheckBox("Flip vertically",
            (imageOrientation & 2) == 2);
        vertFlipCheck.addActionListener(this);
        rotateCheck = new JCheckBox("Rotate",
            (imageOrientation & 4) == 4);
        rotateCheck.addActionListener(this);
        image_panel.add(imageLabel);
        image_panel.add(horizFlipCheck);
        image_panel.add(vertFlipCheck);
        image_panel.add(rotateCheck);

        // buttons
        bOk = new JButton("OK");
        bOk.addActionListener(this);
        bCancel = new JButton("Cancel");
        bCancel.addActionListener(this);
        JPanel buttons = new VerticalStaticJPanel();
        buttons.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        buttons.add(bCancel);
        buttons.add(bOk);

        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1; c.weighty = 1;
        mainPanel.add(sp, c);
        c.weightx = 0; c.gridx = 1;
        mainPanel.add(image_panel, c);
        c.gridx = 0; c.weighty = 0; c.gridy = 1; c.gridwidth = 2;
        mainPanel.add(buttons, c);
        getContentPane().add(mainPanel);
        getRootPane().setDefaultButton(bOk);
    }

    public void queryImages() {
        Vector listData = new Vector();
        int curSlot = 0;
        int initialIndex = 0;

        Enumeration ids = tileset.getImageIds();
        imageIds = new int[tileset.getTotalImages()];
        for (int i = 0; i < imageIds.length; ++i) {
            imageIds[i] = Integer.parseInt((String)ids.nextElement());
        }

        java.util.Arrays.sort(imageIds);

        for (int i = 0; i < imageIds.length; ++i) {
            if (imageIds[i] == imageId) initialIndex = i;
            Image img = tileset.getImageById(Integer.toString(imageIds[i]));
            // assert img != null;
            listData.add(img);
        }

        imageList.setListData(listData);
        imageList.setSelectedIndex(initialIndex);
        imageList.ensureIndexIsVisible(initialIndex);
    }

    private void updateEnabledState() {
        bOk.setEnabled(imageId >= 0);
    }

    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if (source == bOk) {
            this.dispose();
        } else if (source == bCancel) {
            imageId = -1;
            this.dispose();
        } else if (source == horizFlipCheck) {
            imageOrientation ^= 1;
            updateImageLabel();
        } else if (source == vertFlipCheck) {
            imageOrientation ^= 2;
            updateImageLabel();
        } else if (source == rotateCheck) {
            imageOrientation ^= 4;
            updateImageLabel();
        }

        repaint();
    }

    private void updateImageLabel() {
        if (imageId >= 0) {
            Image img = tileset.getImageById(Integer.toString(imageId));
            img = ImageGroup.orientImage(img, imageOrientation);
            imageLabel.setIcon(new ImageIcon(img));
        }
    }

    public void valueChanged(ListSelectionEvent e) {
        imageId = imageIds[imageList.getSelectedIndex()];
        updateImageLabel();
        updateEnabledState();
    }

    int getImageId() {
        return imageId;
    }

    int getImageOrientation() {
        return imageOrientation;
    }
}
