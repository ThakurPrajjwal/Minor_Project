/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 */
package com.sun.mail.iap;

import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;

public class LiteralException
extends ProtocolException {
    private static final long serialVersionUID = -6919179828339609913L;

    public LiteralException(Response response) {
        super(response.toString());
        this.response = response;
    }
}

