/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.lang.Object
 */
package com.sun.mail.iap;

import java.io.IOException;
import java.io.OutputStream;

public interface Literal {
    public int size();

    public void writeTo(OutputStream var1) throws IOException;
}

