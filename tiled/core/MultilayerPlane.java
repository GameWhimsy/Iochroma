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
 */

package tiled.core;

import java.awt.Rectangle;
import java.util.*;


/**
 * MultilayerPlane makes up the core functionality of both Maps and Brushes.
 * This class handles the order of layers as a group.
 */
public class MultilayerPlane
{
    private Vector layers;
    protected int widthInTiles = 0, heightInTiles = 0;

    public MultilayerPlane() {
        layers = new Vector();
    }

    public MultilayerPlane(int width, int height) {
        this();
        widthInTiles = width;
        heightInTiles = height;
    }

    /**
     * Returns the total number of layers.
     * @return the size of the layer vector
     */
    public int getTotalLayers() {
        return layers.size();
    }


    /**
     * Returns a <code>Rectangle</code> representing the maximum bounds in
     * tiles.
     * 
     * @return a Rectangle
     */
    public Rectangle getBounds() {
        return new Rectangle(0, 0, widthInTiles, heightInTiles);
    }

    /**
     * Adds a layer to the map.
     * 
     * @return the layer passed to the function
     */
    public MapLayer addLayer(MapLayer l) {
        layers.add(l);
        return l;
    }

    /**
     * Adds the MapLayer <code>l</code> after the MapLayer <code>after</code>.
     * 
     * @param l the layer to add
     * @param after specifies the layer to add <code>l</code> after 
     */
    public void addLayerAfter(MapLayer l, MapLayer after) {
        layers.add(layers.indexOf(after) + 1, l);
    }

    /**
     * Add a layer at the specified index, which should be a valid.
     * 
     * @param index
     * @param layer
     */
    public void addLayer(int index, MapLayer layer) {
        layers.add(index, layer);
    }

    public void addAllLayers(Collection c) {
        layers.addAll(c);
    }

    /**
     * Removes the layer at the specified index. Layers above this layer
     * will move down to fill the gap.
     *
     * @param index Index of layer to be removed.
     */
    public MapLayer removeLayer(int index) {
        MapLayer layer = (MapLayer)layers.remove(index);
        return layer;
    }

    public void removeAllLayers() {
        layers.removeAllElements();
    }

    public Vector getLayerVector() {
        return layers;
    }

    public void setLayerVector(Vector layers) {
        this.layers = layers;
    }

    /**
     * Moves the layer at <code>index</code> up one in the vector
     * 
     * @param index
     * @throws Exception
     */
    public void swapLayerUp(int index) throws Exception {
        if (index + 1 == layers.size()) {
            throw new Exception(
                    "Can't swap up when already at the top.");
        }

        MapLayer hold = (MapLayer)layers.get(index + 1);
        layers.set(index + 1, getLayer(index));
        layers.set(index, hold);
    }

    /**
     * Moves the layer at <code>index</code> down one in the vector
     * 
     * @param index
     * @throws Exception
     */
    public void swapLayerDown(int index) throws Exception {
        if (index - 1 < 0) {
            throw new Exception(
                    "Can't swap down when already at the bottom.");
        }

        MapLayer hold = (MapLayer)layers.get(index - 1);
        layers.set(index - 1, getLayer(index));
        layers.set(index, hold);
    }

    /**
     * Merges the layer at <code>index</code> with the layer below it
     * 
     * @see tiled.core.MapLayer#mergeOnto
     * @param index
     * @throws Exception
     */
    public void mergeLayerDown(int index) throws Exception {
        if (index - 1 < 0) {
            throw new Exception(
                    "Can't merge down bottom layer.");
        }

        getLayer(index).mergeOnto(getLayer(index - 1));
        removeLayer(index);
    }

    /**
     * Returns the layer at the specified vector index
     * 
     * @param i the index of the layer to return
     * @return the layer at the specified index, or null if the index is out of
     *         bounds
     */
    public MapLayer getLayer(int i) {
        try {
            return (MapLayer)layers.get(i);
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        return null;
    }

    /**
     * Gets a listIterator of all layers
     * 
     * @return a listIterator
     */
    public ListIterator getLayers() {
        return layers.listIterator();
    }

    /**
     * Resizes this plane. The (dx, dy) pair determines where the original
     * plane should be positioned on the new area. It only affects layers with
     * dimensions equal to that of the MutlilayerPlane.
     *
     * @see MapLayer#resize
     *
     * @param width  The new width of the map.
     * @param height The new height of the map.
     * @param dx     The shift in x direction in tiles.
     * @param dy     The shift in y direction in tiles.
     */
    public void resize(int width, int height, int dx, int dy) {
        ListIterator itr = getLayers();
        while (itr.hasNext()) {
            MapLayer l = (MapLayer)itr.next();
            if (l.getBounds().equals(getBounds())) {
                l.resize(width, height, dx, dy);
            }
        }

        widthInTiles = width;
        heightInTiles = height;
    }
}
