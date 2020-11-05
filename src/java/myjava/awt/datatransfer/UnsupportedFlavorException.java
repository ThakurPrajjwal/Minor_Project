/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 */
package myjava.awt.datatransfer;

import myjava.awt.datatransfer.DataFlavor;

public class UnsupportedFlavorException
extends Exception {
    private static final long serialVersionUID = 5383814944251665601L;

    public UnsupportedFlavorException(DataFlavor dataFlavor) {
        super("flavor = " + String.valueOf((Object)dataFlavor));
    }
}

