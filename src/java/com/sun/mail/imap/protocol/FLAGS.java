/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Character
 *  java.lang.String
 *  javax.mail.Flags
 *  javax.mail.Flags$Flag
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import com.sun.mail.imap.protocol.IMAPResponse;
import com.sun.mail.imap.protocol.Item;
import javax.mail.Flags;

public class FLAGS
extends Flags
implements Item {
    static final char[] name = new char[]{'F', 'L', 'A', 'G', 'S'};
    private static final long serialVersionUID = 439049847053756670L;
    public int msgno;

    /*
     * Enabled aggressive block sorting
     */
    public FLAGS(IMAPResponse iMAPResponse) throws ParsingException {
        this.msgno = iMAPResponse.getNumber();
        iMAPResponse.skipSpaces();
        String[] arrstring = iMAPResponse.readSimpleList();
        if (arrstring != null) {
            for (int i = 0; i < arrstring.length; ++i) {
                String string2 = arrstring[i];
                if (string2.length() >= 2 && string2.charAt(0) == '\\') {
                    switch (Character.toUpperCase((char)string2.charAt(1))) {
                        default: {
                            this.add(string2);
                            break;
                        }
                        case 'S': {
                            this.add(Flags.Flag.SEEN);
                            break;
                        }
                        case 'R': {
                            this.add(Flags.Flag.RECENT);
                            break;
                        }
                        case 'D': {
                            if (string2.length() >= 3) {
                                char c = string2.charAt(2);
                                if (c == 'e' || c == 'E') {
                                    this.add(Flags.Flag.DELETED);
                                    break;
                                }
                                if (c != 'r' && c != 'R') break;
                                this.add(Flags.Flag.DRAFT);
                                break;
                            }
                            this.add(string2);
                            break;
                        }
                        case 'A': {
                            this.add(Flags.Flag.ANSWERED);
                            break;
                        }
                        case 'F': {
                            this.add(Flags.Flag.FLAGGED);
                            break;
                        }
                        case '*': {
                            this.add(Flags.Flag.USER);
                            break;
                        }
                    }
                    continue;
                }
                this.add(string2);
            }
        }
    }
}

