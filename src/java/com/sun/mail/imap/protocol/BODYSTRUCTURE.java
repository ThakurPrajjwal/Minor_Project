/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.PrintStream
 *  java.lang.Character
 *  java.lang.Object
 *  java.lang.SecurityException
 *  java.lang.String
 *  java.lang.System
 *  java.util.Vector
 *  javax.mail.internet.ParameterList
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import com.sun.mail.iap.Response;
import com.sun.mail.imap.protocol.ENVELOPE;
import com.sun.mail.imap.protocol.FetchResponse;
import com.sun.mail.imap.protocol.Item;
import java.io.PrintStream;
import java.util.Vector;
import javax.mail.internet.ParameterList;

public class BODYSTRUCTURE
implements Item {
    private static int MULTI;
    private static int NESTED;
    private static int SINGLE;
    static final char[] name;
    private static boolean parseDebug;
    public String attachment;
    public BODYSTRUCTURE[] bodies;
    public ParameterList cParams;
    public ParameterList dParams;
    public String description;
    public String disposition;
    public String encoding;
    public ENVELOPE envelope;
    public String id;
    public String[] language;
    public int lines = -1;
    public String md5;
    public int msgno;
    private int processedType;
    public int size = -1;
    public String subtype;
    public String type;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        int n = 1;
        name = new char[]{'B', 'O', 'D', 'Y', 'S', 'T', 'R', 'U', 'C', 'T', 'U', 'R', 'E'};
        SINGLE = n;
        MULTI = 2;
        NESTED = 3;
        parseDebug = false;
        try {
            String string2 = System.getProperty((String)"mail.imap.parse.debug");
            if (string2 == null || !string2.equalsIgnoreCase("true")) {
                n = 0;
            }
            parseDebug = n;
            return;
        }
        catch (SecurityException securityException) {
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public BODYSTRUCTURE(FetchResponse fetchResponse) throws ParsingException {
        if (parseDebug) {
            System.out.println("DEBUG IMAP: parsing BODYSTRUCTURE");
        }
        this.msgno = fetchResponse.getNumber();
        if (parseDebug) {
            System.out.println("DEBUG IMAP: msgno " + this.msgno);
        }
        fetchResponse.skipSpaces();
        if (fetchResponse.readByte() != 40) {
            throw new ParsingException("BODYSTRUCTURE parse error: missing ``('' at start");
        }
        if (fetchResponse.peekByte() == 40) {
            byte by;
            if (parseDebug) {
                System.out.println("DEBUG IMAP: parsing multipart");
            }
            this.type = "multipart";
            this.processedType = MULTI;
            Vector vector = new Vector(1);
            do {
                vector.addElement((Object)new BODYSTRUCTURE(fetchResponse));
                fetchResponse.skipSpaces();
            } while (fetchResponse.peekByte() == 40);
            this.bodies = new BODYSTRUCTURE[vector.size()];
            vector.copyInto((Object[])this.bodies);
            this.subtype = fetchResponse.readString();
            if (parseDebug) {
                System.out.println("DEBUG IMAP: subtype " + this.subtype);
            }
            if (fetchResponse.readByte() == 41) {
                if (!parseDebug) return;
                System.out.println("DEBUG IMAP: parse DONE");
                return;
            }
            if (parseDebug) {
                System.out.println("DEBUG IMAP: parsing extension data");
            }
            this.cParams = this.parseParameters(fetchResponse);
            if (fetchResponse.readByte() == 41) {
                if (!parseDebug) return;
                System.out.println("DEBUG IMAP: body parameters DONE");
                return;
            }
            byte by2 = fetchResponse.readByte();
            if (by2 == 40) {
                if (parseDebug) {
                    System.out.println("DEBUG IMAP: parse disposition");
                }
                this.disposition = fetchResponse.readString();
                if (parseDebug) {
                    System.out.println("DEBUG IMAP: disposition " + this.disposition);
                }
                this.dParams = this.parseParameters(fetchResponse);
                if (fetchResponse.readByte() != 41) {
                    throw new ParsingException("BODYSTRUCTURE parse error: missing ``)'' at end of disposition in multipart");
                }
                if (parseDebug) {
                    System.out.println("DEBUG IMAP: disposition DONE");
                }
            } else {
                if (by2 != 78) {
                    if (by2 != 110) throw new ParsingException("BODYSTRUCTURE parse error: " + this.type + "/" + this.subtype + ": " + "bad multipart disposition, b " + by2);
                }
                if (parseDebug) {
                    System.out.println("DEBUG IMAP: disposition NIL");
                }
                fetchResponse.skip(2);
            }
            if ((by = fetchResponse.readByte()) == 41) {
                if (!parseDebug) return;
                System.out.println("DEBUG IMAP: no body-fld-lang");
                return;
            }
            if (by != 32) {
                throw new ParsingException("BODYSTRUCTURE parse error: missing space after disposition");
            }
            if (fetchResponse.peekByte() == 40) {
                this.language = fetchResponse.readStringList();
                if (parseDebug) {
                    System.out.println("DEBUG IMAP: language len " + this.language.length);
                }
            } else {
                String string2 = fetchResponse.readString();
                if (string2 != null) {
                    this.language = new String[]{string2};
                    if (parseDebug) {
                        System.out.println("DEBUG IMAP: language " + string2);
                    }
                }
            }
            while (fetchResponse.readByte() == 32) {
                this.parseBodyExtension(fetchResponse);
            }
            return;
        }
        if (parseDebug) {
            System.out.println("DEBUG IMAP: single part");
        }
        this.type = fetchResponse.readString();
        if (parseDebug) {
            System.out.println("DEBUG IMAP: type " + this.type);
        }
        this.processedType = SINGLE;
        this.subtype = fetchResponse.readString();
        if (parseDebug) {
            System.out.println("DEBUG IMAP: subtype " + this.subtype);
        }
        if (this.type == null) {
            this.type = "application";
            this.subtype = "octet-stream";
        }
        this.cParams = this.parseParameters(fetchResponse);
        if (parseDebug) {
            System.out.println("DEBUG IMAP: cParams " + (Object)this.cParams);
        }
        this.id = fetchResponse.readString();
        if (parseDebug) {
            System.out.println("DEBUG IMAP: id " + this.id);
        }
        this.description = fetchResponse.readString();
        if (parseDebug) {
            System.out.println("DEBUG IMAP: description " + this.description);
        }
        this.encoding = fetchResponse.readString();
        if (parseDebug) {
            System.out.println("DEBUG IMAP: encoding " + this.encoding);
        }
        this.size = fetchResponse.readNumber();
        if (parseDebug) {
            System.out.println("DEBUG IMAP: size " + this.size);
        }
        if (this.size < 0) {
            throw new ParsingException("BODYSTRUCTURE parse error: bad ``size'' element");
        }
        if (this.type.equalsIgnoreCase("text")) {
            this.lines = fetchResponse.readNumber();
            if (parseDebug) {
                System.out.println("DEBUG IMAP: lines " + this.lines);
            }
            if (this.lines < 0) {
                throw new ParsingException("BODYSTRUCTURE parse error: bad ``lines'' element");
            }
        } else if (this.type.equalsIgnoreCase("message") && this.subtype.equalsIgnoreCase("rfc822")) {
            this.processedType = NESTED;
            this.envelope = new ENVELOPE(fetchResponse);
            BODYSTRUCTURE[] arrbODYSTRUCTURE = new BODYSTRUCTURE[]{new BODYSTRUCTURE(fetchResponse)};
            this.bodies = arrbODYSTRUCTURE;
            this.lines = fetchResponse.readNumber();
            if (parseDebug) {
                System.out.println("DEBUG IMAP: lines " + this.lines);
            }
            if (this.lines < 0) {
                throw new ParsingException("BODYSTRUCTURE parse error: bad ``lines'' element");
            }
        } else {
            fetchResponse.skipSpaces();
            if (Character.isDigit((char)((char)fetchResponse.peekByte()))) {
                throw new ParsingException("BODYSTRUCTURE parse error: server erroneously included ``lines'' element with type " + this.type + "/" + this.subtype);
            }
        }
        if (fetchResponse.peekByte() == 41) {
            fetchResponse.readByte();
            if (!parseDebug) return;
            System.out.println("DEBUG IMAP: parse DONE");
            return;
        }
        this.md5 = fetchResponse.readString();
        if (fetchResponse.readByte() == 41) {
            if (!parseDebug) return;
            System.out.println("DEBUG IMAP: no MD5 DONE");
            return;
        }
        byte by = fetchResponse.readByte();
        if (by == 40) {
            this.disposition = fetchResponse.readString();
            if (parseDebug) {
                System.out.println("DEBUG IMAP: disposition " + this.disposition);
            }
            this.dParams = this.parseParameters(fetchResponse);
            if (parseDebug) {
                System.out.println("DEBUG IMAP: dParams " + (Object)this.dParams);
            }
            if (fetchResponse.readByte() != 41) {
                throw new ParsingException("BODYSTRUCTURE parse error: missing ``)'' at end of disposition");
            }
        } else {
            if (by != 78) {
                if (by != 110) throw new ParsingException("BODYSTRUCTURE parse error: " + this.type + "/" + this.subtype + ": " + "bad single part disposition, b " + by);
            }
            if (parseDebug) {
                System.out.println("DEBUG IMAP: disposition NIL");
            }
            fetchResponse.skip(2);
        }
        if (fetchResponse.readByte() == 41) {
            if (!parseDebug) return;
            System.out.println("DEBUG IMAP: disposition DONE");
            return;
        }
        if (fetchResponse.peekByte() == 40) {
            this.language = fetchResponse.readStringList();
            if (parseDebug) {
                System.out.println("DEBUG IMAP: language len " + this.language.length);
            }
        } else {
            String string3 = fetchResponse.readString();
            if (string3 != null) {
                this.language = new String[]{string3};
                if (parseDebug) {
                    System.out.println("DEBUG IMAP: language " + string3);
                }
            }
        }
        do {
            if (fetchResponse.readByte() != 32) {
                if (!parseDebug) return;
                System.out.println("DEBUG IMAP: all DONE");
                return;
            }
            this.parseBodyExtension(fetchResponse);
        } while (true);
    }

    private void parseBodyExtension(Response response) throws ParsingException {
        response.skipSpaces();
        byte by = response.peekByte();
        if (by == 40) {
            response.skip(1);
            do {
                this.parseBodyExtension(response);
            } while (response.readByte() != 41);
            return;
        }
        if (Character.isDigit((char)((char)by))) {
            response.readNumber();
            return;
        }
        response.readString();
    }

    private ParameterList parseParameters(Response response) throws ParsingException {
        response.skipSpaces();
        byte by = response.readByte();
        if (by == 40) {
            ParameterList parameterList = new ParameterList();
            do {
                String string2 = response.readString();
                if (parseDebug) {
                    System.out.println("DEBUG IMAP: parameter name " + string2);
                }
                if (string2 == null) {
                    throw new ParsingException("BODYSTRUCTURE parse error: " + this.type + "/" + this.subtype + ": " + "null name in parameter list");
                }
                String string3 = response.readString();
                if (parseDebug) {
                    System.out.println("DEBUG IMAP: parameter value " + string3);
                }
                parameterList.set(string2, string3);
            } while (response.readByte() != 41);
            parameterList.set(null, "DONE");
            return parameterList;
        }
        if (by == 78 || by == 110) {
            if (parseDebug) {
                System.out.println("DEBUG IMAP: parameter list NIL");
            }
            response.skip(2);
            return null;
        }
        throw new ParsingException("Parameter list parse error");
    }

    public boolean isMulti() {
        return this.processedType == MULTI;
    }

    public boolean isNested() {
        return this.processedType == NESTED;
    }

    public boolean isSingle() {
        return this.processedType == SINGLE;
    }
}

