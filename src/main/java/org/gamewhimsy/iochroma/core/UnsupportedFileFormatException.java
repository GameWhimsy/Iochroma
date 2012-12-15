/*
 * Iochroma Level Editor (c) 2012 Tulonsae
 */

package org.gamewhimsy.iochroma.core;

/**
 * Thrown when an unsupported file format is encountered.
 */
public class UnsupportedFileFormatException extends RuntimeException {

    public UnsupportedFileFormatException(String message) {
        super(message);
    }
}
