/*
 *  Mappy Plugin for Tiled, (c) 2004
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 * 
 *  Adam Turk <aturk@biggeruniverse.com>
 *  Bjorn Lindeijer <b.lindeijer@xs4all.nl>
 */

package tiled.plugins.mappy;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;

import tiled.io.MapWriter;
import tiled.plugins.mappy.MappyMapReader.BlkStr;
import tiled.core.Map;
import tiled.core.TileSet;

public class MappyMapWriter implements MapWriter
{
    private LinkedList chunks;

    public MappyMapWriter() {
        chunks = new LinkedList();
    }

    /**
     * Loads a map from a file.
     *
     * @param filename the filename of the map file
     */
    public void writeMap(Map map, String filename) throws Exception {
        writeMap(map, new FileOutputStream(filename));
    }
                                                                                
    /**
     * Loads a tileset from a file.
     *
     * @param filename the filename of the tileset file
     */
    public void writeTileset(TileSet set, String filename) throws Exception {
        System.out.println("Asked to write "+filename);
    }

    public void writeMap(Map map, OutputStream in) throws Exception {
        in.write("FORM".getBytes());
        // TODO: write the size of the file minus this header
        in.write("FMAP".getBytes());
        createMPHDChunk(map);


        // TODO: write all the chunks
    }
    
    public void writeTileset(TileSet set, OutputStream out) throws Exception {
        System.out.println("Tilesets are not supported!");
    }
    
    /**
     * @see tiled.io.MapReader#getFilter()
     */
    public String getFilter() throws Exception {
        return "*.map";
    }
                                                                                
    public String getDescription() {
        return
            "+---------------------------------------------+\n" +
            "|    A sloppy writer for Mappy FMAP (v0.36)   |\n" +
            "|             (c) Adam Turk 2004              |\n" +
            "|          aturk@biggeruniverse.com           |\n" +
            "+---------------------------------------------+";
    }
                                                                                
    public String getPluginPackage() {
        return "Mappy Reader/Writer Plugin";
    }

    public String getName() {
        return "Mappy Writer";
    }
                                                                                
    public boolean accept(File pathname) {
        try {
            String path = pathname.getCanonicalPath().toLowerCase();
            if (path.endsWith(".fmp")) {
                return true;
            }
        } catch (IOException e) {}
        return false;
    }

    
    private void createMPHDChunk(Map m) throws IOException {
        Chunk c = new Chunk("MPHD");
        OutputStream out = c.getOutputStream();
        String ver = m.getPropertyValue("version");
        if (ver == null) {
            ver = "0.3";                            // default the value
        }
        TileSet set = (TileSet) m.getTilesets().get(0);

        out.write(Integer.parseInt(ver.substring(0,ver.indexOf('.')-1)));
        out.write(Integer.parseInt(ver.substring(ver.indexOf('.')+1)));
        out.write(1); out.write(0);                 // LSB, reserved
        Util.writeShort(m.getWidth(), out);  Util.writeShort(m.getHeight(), out);
        out.write(0); out.write(0); out.write(0); out.write(0);     // reserved
        Util.writeShort(m.getTileWidth(), out); Util.writeShort(m.getTileHeight(), out);
        Util.writeShort(16, out);                   // tile bitdepth
        Util.writeShort(32, out);                   // blkstr bytewidth
        Util.writeShort(findAllBlocks(m).size(), out);
        Util.writeShort(set.getMaxTileId(), out);

        chunks.add(c);
    }
    
    private void createBKDTChunk(Map m) {
        Chunk c = new Chunk("BKDT");
        LinkedList blocks = findAllBlocks(m);
        Iterator itr = blocks.iterator();
        while(itr.hasNext()) {
            MappyMapReader.BlkStr b = (BlkStr) itr.next();
            // TODO: write the block
        }
        chunks.add(c);
    }
    
    private LinkedList findAllBlocks(Map m) {
        // TODO: this
        return null;
    }
}
