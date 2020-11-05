/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 */
package com.sun.mail.iap;

import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;

public class CommandFailedException
extends ProtocolException {
    private static final long serialVersionUID = 793932807880443631L;

    public CommandFailedException() {
    }

    public CommandFailedException(Response response) {
        super(response);
    }

    public CommandFailedException(String string2) {
        super(string2);
    }
}

