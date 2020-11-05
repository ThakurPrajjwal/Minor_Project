/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.String
 *  javax.mail.Folder
 */
package com.sun.mail.util;

import java.io.IOException;
import javax.mail.Folder;

public class FolderClosedIOException
extends IOException {
    private static final long serialVersionUID = 4281122580365555735L;
    private transient Folder folder;

    public FolderClosedIOException(Folder folder) {
        this(folder, null);
    }

    public FolderClosedIOException(Folder folder, String string2) {
        super(string2);
        this.folder = folder;
    }

    public Folder getFolder() {
        return this.folder;
    }
}

