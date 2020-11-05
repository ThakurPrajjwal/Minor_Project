/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayOutputStream
 *  java.io.OutputStream
 *  java.io.PrintStream
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Map
 *  java.util.Properties
 *  java.util.Vector
 *  javax.security.auth.callback.Callback
 *  javax.security.auth.callback.CallbackHandler
 *  javax.security.auth.callback.NameCallback
 *  javax.security.auth.callback.PasswordCallback
 *  javax.security.sasl.RealmCallback
 *  javax.security.sasl.RealmChoiceCallback
 *  javax.security.sasl.Sasl
 *  javax.security.sasl.SaslClient
 *  javax.security.sasl.SaslException
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.Argument;
import com.sun.mail.iap.ByteArray;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;
import com.sun.mail.imap.protocol.IMAPProtocol;
import com.sun.mail.imap.protocol.SaslAuthenticator;
import com.sun.mail.util.ASCIIUtility;
import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.sasl.RealmCallback;
import javax.security.sasl.RealmChoiceCallback;
import javax.security.sasl.Sasl;
import javax.security.sasl.SaslClient;
import javax.security.sasl.SaslException;

public class IMAPSaslAuthenticator
implements SaslAuthenticator {
    private boolean debug;
    private String host;
    private String name;
    private PrintStream out;
    private IMAPProtocol pr;
    private Properties props;

    public IMAPSaslAuthenticator(IMAPProtocol iMAPProtocol, String string2, Properties properties, boolean bl, PrintStream printStream, String string3) {
        this.pr = iMAPProtocol;
        this.name = string2;
        this.props = properties;
        this.debug = bl;
        this.out = printStream;
        this.host = string3;
    }

    static /* synthetic */ boolean access$0(IMAPSaslAuthenticator iMAPSaslAuthenticator) {
        return iMAPSaslAuthenticator.debug;
    }

    static /* synthetic */ PrintStream access$1(IMAPSaslAuthenticator iMAPSaslAuthenticator) {
        return iMAPSaslAuthenticator.out;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public boolean authenticate(String[] var1_1, final String var2_2, String var3_3, final String var4_4, final String var5_5) throws ProtocolException {
        block31 : {
            var28_7 = var6_6 = this.pr;
            // MONITORENTER : var28_7
            var7_8 = new Vector();
            var8_9 = null;
            var9_10 = false;
            if (!this.debug) ** GOTO lbl12
            this.out.print("IMAP SASL DEBUG: Mechanisms:");
            var27_11 = 0;
            do lbl-1000: // 2 sources:
            {
                if (var27_11 < var1_1.length) break block34;
                this.out.println();
lbl12: // 2 sources:
                var11_12 = new CallbackHandler(){

                    /*
                     * Unable to fully structure code
                     * Enabled aggressive block sorting
                     * Lifted jumps to return sites
                     */
                    public void handle(Callback[] var1_1) {
                        if (IMAPSaslAuthenticator.access$0(IMAPSaslAuthenticator.this)) {
                            IMAPSaslAuthenticator.access$1(IMAPSaslAuthenticator.this).println("IMAP SASL DEBUG: callback length: " + var1_1.length);
                        }
                        var2_2 = 0;
                        block0 : do {
                            block13 : {
                                block12 : {
                                    block11 : {
                                        block10 : {
                                            if (var2_2 >= var1_1.length) {
                                                return;
                                            }
                                            if (IMAPSaslAuthenticator.access$0(IMAPSaslAuthenticator.this)) {
                                                IMAPSaslAuthenticator.access$1(IMAPSaslAuthenticator.this).println("IMAP SASL DEBUG: callback " + var2_2 + ": " + (Object)var1_1[var2_2]);
                                            }
                                            if (!(var1_1[var2_2] instanceof NameCallback)) break block10;
                                            ((NameCallback)var1_1[var2_2]).setName(var4_4);
                                            ** GOTO lbl33
                                        }
                                        if (!(var1_1[var2_2] instanceof PasswordCallback)) break block11;
                                        ((PasswordCallback)var1_1[var2_2]).setPassword(var5_5.toCharArray());
                                        ** GOTO lbl33
                                    }
                                    if (!(var1_1[var2_2] instanceof RealmCallback)) break block12;
                                    var6_6 = (RealmCallback)var1_1[var2_2];
                                    var7_7 = var2_2 != null ? var2_2 : var6_6.getDefaultText();
                                    var6_6.setText(var7_7);
                                    ** GOTO lbl33
                                }
                                if (!(var1_1[var2_2] instanceof RealmChoiceCallback)) ** GOTO lbl33
                                var3_3 = (RealmChoiceCallback)var1_1[var2_2];
                                if (var2_2 != null) break block13;
                                var3_3.setSelectedIndex(var3_3.getDefaultChoice());
                                ** GOTO lbl33
                            }
                            var4_4 = var3_3.getChoices();
                            var5_5 = 0;
                            do {
                                block14 : {
                                    if (var5_5 < var4_4.length) break block14;
lbl33: // 7 sources:
                                    do {
                                        ++var2_2;
                                        continue block0;
                                        break;
                                    } while (true);
                                }
                                if (var4_4[var5_5].equals((Object)var2_2)) {
                                    var3_3.setSelectedIndex(var5_5);
                                    ** continue;
                                }
                                ++var5_5;
                            } while (true);
                            break;
                        } while (true);
                    }
                };
                var13_13 = Sasl.createSaslClient((String[])var1_1, (String)var3_3, (String)this.name, (String)this.host, (Map)this.props, (CallbackHandler)var11_12);
                if (var13_13 != null) break block31;
                if (!this.debug) break block32;
                break;
            } while (true);
            catch (SaslException var12_14) {
                if (this.debug) {
                    this.out.println("IMAP SASL DEBUG: Failed to create SASL client: " + (Object)var12_14);
                }
                // MONITOREXIT : var28_7
                return false;
            }
            {
                block34 : {
                    block32 : {
                        this.out.println("IMAP SASL DEBUG: No SASL support");
                    }
                    // MONITOREXIT : var28_7
                    return false;
                }
                this.out.print(" " + var1_1[var27_11]);
                ++var27_11;
                ** while (true)
            }
        }
        if (this.debug) {
            this.out.println("IMAP SASL DEBUG: SASL client " + var13_13.getMechanismName());
        }
        try {}
        catch (Exception var14_26) {
            if (this.debug) {
                this.out.println("IMAP SASL DEBUG: AUTHENTICATE Exception: " + (Object)var14_26);
            }
            // MONITOREXIT : var28_7
            return false;
        }
        var15_15 = this.pr.writeCommand("AUTHENTICATE " + var13_13.getMechanismName(), null);
        var16_16 = this.pr.getIMAPOutputStream();
        var17_17 = new ByteArrayOutputStream();
        var18_18 = new byte[]{13, 10};
        var19_19 = var13_13.getMechanismName().equals((Object)"XGWTRUSTEDAPP");
        do {
            block33 : {
                if (var9_10) {
                    if (!var13_13.isComplete() || (var21_25 = (String)var13_13.getNegotiatedProperty("javax.security.sasl.qop")) == null || !var21_25.equalsIgnoreCase("auth-int") && !var21_25.equalsIgnoreCase("auth-conf")) break;
                    if (this.debug) {
                        this.out.println("IMAP SASL DEBUG: Mechanism requires integrity or confidentiality");
                    }
                    // MONITOREXIT : var28_7
                    return false;
                }
                try {
                    var8_9 = this.pr.readResponse();
                    if (var8_9.isContinuation()) {
                        var24_22 = null;
                        if (!var13_13.isComplete()) {
                            var26_24 = var8_9.readByteArray().getNewBytes();
                            if (var26_24.length > 0) {
                                var26_24 = BASE64DecoderStream.decode(var26_24);
                            }
                            if (this.debug) {
                                this.out.println("IMAP SASL DEBUG: challenge: " + ASCIIUtility.toString(var26_24, 0, var26_24.length) + " :");
                            }
                            var24_22 = var13_13.evaluateChallenge(var26_24);
                        }
                        if (var24_22 == null) {
                            if (this.debug) {
                                this.out.println("IMAP SASL DEBUG: no response");
                            }
                            var16_16.write(var18_18);
                            var16_16.flush();
                            var17_17.reset();
                        }
                        if (this.debug) {
                            this.out.println("IMAP SASL DEBUG: response: " + ASCIIUtility.toString(var24_22, 0, var24_22.length) + " :");
                        }
                        var25_23 = BASE64EncoderStream.encode(var24_22);
                        if (var19_19) {
                            var17_17.write("XGWTRUSTEDAPP ".getBytes());
                        }
                        var17_17.write(var25_23);
                        var17_17.write(var18_18);
                        var16_16.write(var17_17.toByteArray());
                        var16_16.flush();
                        var17_17.reset();
                        continue;
                    }
                    if (var8_9.isTagged() && var8_9.getTag().equals((Object)var15_15)) {
                        var9_10 = true;
                        continue;
                    }
                    break block33;
                }
                catch (Exception var22_20) {
                    if (this.debug) {
                        var22_20.printStackTrace();
                    }
                    var8_9 = var23_21 = Response.byeResponse(var22_20);
                    var9_10 = true;
                }
                continue;
            }
            if (var8_9.isBYE()) {
                var9_10 = true;
                continue;
            }
            var7_8.addElement((Object)var8_9);
        } while (true);
        var20_27 = new Response[var7_8.size()];
        var7_8.copyInto(var20_27);
        this.pr.notifyResponseHandlers((Response[])var20_27);
        this.pr.handleResult(var8_9);
        this.pr.setCapabilities(var8_9);
        // MONITOREXIT : var28_7
        return true;
    }

}

