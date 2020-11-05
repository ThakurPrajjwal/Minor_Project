/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayInputStream
 *  java.lang.Object
 *  java.lang.System
 */
package com.sun.mail.iap;

import java.io.ByteArrayInputStream;

public class ByteArray {
    private byte[] bytes;
    private int count;
    private int start;

    public ByteArray(int n) {
        this(new byte[n], 0, n);
    }

    public ByteArray(byte[] arrby, int n, int n2) {
        this.bytes = arrby;
        this.start = n;
        this.count = n2;
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public int getCount() {
        return this.count;
    }

    public byte[] getNewBytes() {
        byte[] arrby = new byte[this.count];
        System.arraycopy((Object)this.bytes, (int)this.start, (Object)arrby, (int)0, (int)this.count);
        return arrby;
    }

    public int getStart() {
        return this.start;
    }

    public void grow(int n) {
        byte[] arrby = new byte[n + this.bytes.length];
        System.arraycopy((Object)this.bytes, (int)0, (Object)arrby, (int)0, (int)this.bytes.length);
        this.bytes = arrby;
    }

    public void setCount(int n) {
        this.count = n;
    }

    public ByteArrayInputStream toByteArrayInputStream() {
        return new ByteArrayInputStream(this.bytes, this.start, this.count);
    }
}

