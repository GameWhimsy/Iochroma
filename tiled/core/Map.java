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

import java.util.*;
import javax.swing.event.EventListenerList;

import tiled.mapeditor.util.*;
import tiled.view.*;

/**
 * The Map class is the focal point of the <code>tiled.core</code> package.
 * This class also handles notifing listeners if there is a change to any layer
 * or object contained by the map.
 */
public class Map extends MultilayerPlane implements Cloneable
{
    public static final int MDO_ORTHO   = 1;
    public static final int MDO_ISO     = 2;
    public static final int MDO_OBLIQUE = 3;
    public static final int MDO_HEX     = 4;

    private Vector specialLayers;
    private Vector tilesets;
    private LinkedList objects;
    int id;

    int tileWidth, tileHeight;
    int totalObjects = 0;
    int lit = 1;
    int orientation = MDO_ORTHO;
    EventListenerList mapChangeListeners;
    Properties properties;
    String filename;

    /**
     * @param width  the map width in tiles.
     * @param height the map height in tiles.
     */
    public Map(int width, int height) {
        super(width, height);
        init();
    }

    /**
     * Internal initialization of the Map
     *
     */
    private void init() {
        mapChangeListeners = new EventListenerList();
        properties = new Properties();
        tilesets = new Vector();
        specialLayers = new Vector();
    }

    /**
     * Adds a change listener. The listener will be notified when the map
     * changes in certain ways.
     *
     * @see MapChangeListener#mapChanged(MapChangedEvent)
     */
    public void addMapChangeListener(MapChangeListener l) {
        mapChangeListeners.add(MapChangeListener.class, l);
    }

    /**
     * Removes a change listener.
     */
    public void removeMapChangeListener(MapChangeListener l) {
        mapChangeListeners.remove(MapChangeListener.class, l);
    }

    /**
     * Notifies all registered map change listeners about a change.
     */
    protected void fireMapChanged() {
        Object[] listeners = mapChangeListeners.getListenerList();
        MapChangedEvent event = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == MapChangeListener.class) {
                if (event == null) event = new MapChangedEvent(this);
                ((MapChangeListener)listeners[i + 1]).mapChanged(event);
            }
        }
    }

    /**
     * Causes a MapChangedEvent to be fired.
     */
    public void touch() {
        fireMapChanged();
    }

	public void addLayerSpecial(MapLayer l) {
		l.setMap(this);
		specialLayers.add(l);
		fireMapChanged();
	}

    public MapLayer addLayer(MapLayer l) {		
        l.setMap(this);
        super.addLayer(l);
        fireMapChanged();
        return l;
    }

    public MapLayer addLayer() {
        MapLayer layer = new MapLayer(this, widthInTiles, heightInTiles);
        layer.setName("Layer "+super.getTotalLayers());		
        super.addLayer(layer);
        fireMapChanged();
        return layer;
    }

    /**
     * Adds a Tileset to this Map. If the set is already attached to this map,
     * <code>addTileset</code> simply returns.
     *   
     * @param s	a tileset to add
     */
    public void addTileset(TileSet s) {
        if (s == null || tilesets.indexOf(s) > -1) {
            return;
        }

        Tile t = s.getTile(0);

        if (t != null) {
            int tw = t.getWidth();
            int th = t.getHeight();
            if (tw != tileWidth) {
                if (tileWidth == 0) {
                    tileWidth = tw;
                    tileHeight = th;
                }
            }
        }

        s.setStandardHeight(tileHeight);
        s.setStandardWidth(tileWidth);
        tilesets.add(s);
        fireMapChanged();
    }

    public void removeTileset(TileSet s) {
        // Sanity check
        if (tilesets.indexOf(s) == -1) {
            return;
        }

        // Go through the map and remove any instances of the tiles in the set
        for (int i = 0; i < s.getTotalTiles(); i++) {
            ListIterator itr = getLayers();
            Tile tile = s.getTile(i);
            while (itr.hasNext()) {
                MapLayer ml = (MapLayer)itr.next();
                ml.removeTile(tile);
            }
        }

        tilesets.remove(s);
        fireMapChanged();
    }

    public void addObject(MapObject o) {
        objects.add(o);
    }

    public void addProperty(String key, String value) {
        properties.put(key, value);
    }

    public Enumeration getProperties() {
        return properties.keys();
    }

    public String getPropertyValue(String key) {
        return properties.getProperty(key);
    }

    public void setProperties(Properties prop) {
        properties = prop;
    }

    /**
     * @see MultilayerPlane#removeLayer
     */
    public MapLayer removeLayer(int index) {
        MapLayer layer = super.removeLayer(index);
        fireMapChanged();
        return layer;
    }

	public MapLayer removeLayerSpecial(MapLayer l) {
		try {
			if(specialLayers.remove(l)) {
				fireMapChanged();
			}
		}catch (ArrayIndexOutOfBoundsException e) {
		}
		return l;
	}

	public void removeAllSpecialLayers() {
		specialLayers.clear();
		fireMapChanged();
	}
	
    /**
     * @see MultilayerPlane#removeAllLayers
     */

    public void removeAllLayers() {
        super.removeAllLayers();
        fireMapChanged();
    }

    public void setLayerVector(Vector layers) {
        super.setLayerVector(layers);
        fireMapChanged();
    }

    /**
     * @see MultilayerPlane#swapLayerUp
     */
    public void swapLayerUp(int index) throws Exception {
        super.swapLayerUp(index);
        fireMapChanged();
    }

    /**
     * @see MultilayerPlane#swapLayerDown
     */
    public void swapLayerDown(int index) throws Exception {
        super.swapLayerDown(index);
        fireMapChanged();
    }

    /**
     * @see MultilayerPlane#mergeLayerDown
     */
    public void mergeLayerDown(int index) throws Exception {
        super.mergeLayerDown(index);
        fireMapChanged();
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Sets a new tile width.
     */
    public void setTileWidth(int width) {
        tileWidth = width;
        fireMapChanged();
    }

    /**
     * Sets a new tile height.
     */
    public void setTileHeight(int height) {
        tileHeight = height;
        fireMapChanged();
    }

    /**
     * @see MultilayerPlane#resize
     */
    public void resize(int width, int height, int dx, int dy) {
        super.resize(width, height, dx, dy);
        fireMapChanged();
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
        // TODO: fire mapChangedNotification about orientation change
    }

    public String getFilename() {
        return filename;
    }

    public Iterator getLayersSpecial() {
        return specialLayers.iterator();
    }

    /**
     * Returns a vector with the currently loaded tilesets.
     */
    public Vector getTilesets() {
        return tilesets;
    }

    /**
     * Retrieves the designated "Blank" or "Null" tile
     *
     * @return designated Null tile, or null by default
     */
    public Tile getNullTile() {
        return null;
    }

    /**
     * Get the tile set that matches the given global tile id, only to be used
     * when loading a map.
     */
    public TileSet findTileSetForTileGID(int gid) {
        Iterator itr = tilesets.iterator();
        TileSet has = null;
        while (itr.hasNext()) {
            TileSet ts = (TileSet)itr.next();
            if (ts.getFirstGid() <= gid) {
                has = ts;
            }
        }
        return has;
    }

    /**
     * Returns width of map in tiles.
     */
    public int getWidth() {
        return widthInTiles;
    }

    /**
     * Returns height of map in tiles.
     */
    public int getHeight() {
        return heightInTiles;
    }

    /**
     * Returns default tile width for this map.
     */
    public int getTileWidth() {
        return tileWidth;
    }

    /**
     * Returns default tile height for this map.
     */
    public int getTileHeight() {
        return tileHeight;
    }

    /**
     * Returns the height of the highest tile in all tilesets.
     */
    public int getTileHeightMax() {
        int maxHeight = 0;
        Iterator itr = tilesets.iterator();

        while (itr.hasNext()) {
            int height = ((TileSet)itr.next()).getTileHeightMax();
            if (height > maxHeight) {
                maxHeight = height;
            }
        }

        return maxHeight;
    }

    /**
     * Returns the sum of the size of each tile set.
     */
    public int getTotalTiles() {
        int totalTiles = 0;
        Iterator itr = tilesets.iterator();

        while (itr.hasNext()) {
            TileSet cur = (TileSet)itr.next();
            totalTiles += cur.getTotalTiles();
        }

        return totalTiles;
    }

    /**
     * Returns the amount of objects on the map.
     */
    public int getTotalObjects() {
        return totalObjects;
    }

    /**
     * Creates a MapView instance that will render the map in the right
     * orientation.
     */
    public MapView createView() {
        MapView mapView = null;

        if (orientation == MDO_ISO) {
            mapView = new IsoMapView(this);
        } else if (orientation == MDO_ORTHO) {
            mapView = new OrthoMapView(this);
        } else if (orientation == MDO_HEX) {
            mapView = new HexMapView(this);
        } else if (orientation == MDO_OBLIQUE) {
            mapView = new ObliqueMapView(this);
        }

        return mapView;
    }

    /**
     * Returns the orientation of this map. Orientation will be one of
     * {@link Map#MDO_ISO}, {@link Map#MDO_ORTHO}, {@link Map#MDO_HEX} and
     * {@link Map#MDO_OBLIQUE}.
     */
    public int getOrientation() {
        return orientation;
    }

    /**
     * Returns string describing the map. The form is <code>Map[width x height
     * x layers][tileWidth x tileHeight]</code>, for example <code>
     * Map[64x64x2][24x24]</code>.
     *
     * @return string describing map
     */
    public String toString() {
        return "Map[" + widthInTiles + "x" + heightInTiles + "x" +
            getTotalLayers() + "][" + tileWidth + "x" +
            tileHeight + "]";
    }

    /**
     * Determines wether the point (x,y) falls within the map boundaries.
     */
    public boolean inBounds(int x, int y) {
        return (x >= 0 && y >= 0 &&
                x < this.widthInTiles && y < this.heightInTiles);
    }
}
