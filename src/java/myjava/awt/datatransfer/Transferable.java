/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 */
package myjava.awt.datatransfer;

import java.io.IOException;
import myjava.awt.datatransfer.DataFlavor;
import myjava.awt.datatransfer.UnsupportedFlavorException;

public interface Transferable {
    public Object getTransferData(DataFlavor var1) throws UnsupportedFlavorException, IOException;

    public DataFlavor[] getTransferDataFlavors();

    public boolean isDataFlavorSupported(DataFlavor var1);
}

