/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 */
package com.sun.mail.iap;

import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;

public class BadCommandException
extends ProtocolException {
    private static final long serialVersionUID = 5769722539397237515L;

    public BadCommandException() {
    }

    public BadCommandException(Response response) {
        super(response);
    }

    public BadCommandException(String string2) {
        super(string2);
    }
}

