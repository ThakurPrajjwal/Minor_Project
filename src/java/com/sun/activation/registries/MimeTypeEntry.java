/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package com.sun.activation.registries;

public class MimeTypeEntry {
    private String extension;
    private String type;

    public MimeTypeEntry(String string2, String string3) {
        this.type = string2;
        this.extension = string3;
    }

    public String getFileExtension() {
        return this.extension;
    }

    public String getMIMEType() {
        return this.type;
    }

    public String toString() {
        return "MIMETypeEntry: " + this.type + ", " + this.extension;
    }
}

