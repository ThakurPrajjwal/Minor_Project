/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.EOFException
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Exception
 *  java.lang.IllegalStateException
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.reflect.Constructor
 *  java.util.StringTokenizer
 *  java.util.Vector
 *  javax.mail.FetchProfile
 *  javax.mail.FetchProfile$Item
 *  javax.mail.Flags
 *  javax.mail.Folder
 *  javax.mail.FolderClosedException
 *  javax.mail.FolderNotFoundException
 *  javax.mail.Message
 *  javax.mail.MessageRemovedException
 *  javax.mail.MessagingException
 *  javax.mail.MethodNotSupportedException
 *  javax.mail.Store
 *  javax.mail.UIDFolder
 *  javax.mail.UIDFolder$FetchProfileItem
 */
package com.sun.mail.pop3;

import com.sun.mail.pop3.DefaultFolder;
import com.sun.mail.pop3.POP3Message;
import com.sun.mail.pop3.POP3Store;
import com.sun.mail.pop3.Protocol;
import com.sun.mail.pop3.Status;
import com.sun.mail.util.LineInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.FolderNotFoundException;
import javax.mail.Message;
import javax.mail.MessageRemovedException;
import javax.mail.MessagingException;
import javax.mail.MethodNotSupportedException;
import javax.mail.Store;
import javax.mail.UIDFolder;

public class POP3Folder
extends Folder {
    private boolean doneUidl = false;
    private boolean exists = false;
    private Vector message_cache;
    private String name;
    private boolean opened = false;
    private Protocol port;
    private int size;
    private int total;

    POP3Folder(POP3Store pOP3Store, String string2) {
        super((Store)pOP3Store);
        this.name = string2;
        if (string2.equalsIgnoreCase("INBOX")) {
            this.exists = true;
        }
    }

    public void appendMessages(Message[] arrmessage) throws MessagingException {
        throw new MethodNotSupportedException("Append not supported");
    }

    void checkClosed() throws IllegalStateException {
        if (this.opened) {
            throw new IllegalStateException("Folder is Open");
        }
    }

    void checkOpen() throws IllegalStateException {
        if (!this.opened) {
            throw new IllegalStateException("Folder is not Open");
        }
    }

    void checkReadable() throws IllegalStateException {
        if (!this.opened || this.mode != 1 && this.mode != 2) {
            throw new IllegalStateException("Folder is not Readable");
        }
    }

    void checkWritable() throws IllegalStateException {
        if (!this.opened || this.mode != 2) {
            throw new IllegalStateException("Folder is not Writable");
        }
    }

    /*
     * Exception decompiling
     */
    public void close(boolean var1_1) throws MessagingException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK]], but top level block is 14[UNCONDITIONALDOLOOP]
        // org.benf.cfr.reader.b.a.a.j.a(Op04StructuredStatement.java:432)
        // org.benf.cfr.reader.b.a.a.j.d(Op04StructuredStatement.java:484)
        // org.benf.cfr.reader.b.a.a.i.a(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:692)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:182)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:127)
        // org.benf.cfr.reader.entities.attributes.f.c(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.g.p(Method.java:396)
        // org.benf.cfr.reader.entities.d.e(ClassFile.java:890)
        // org.benf.cfr.reader.entities.d.b(ClassFile.java:792)
        // org.benf.cfr.reader.b.a(Driver.java:128)
        // org.benf.cfr.reader.a.a(CfrDriverImpl.java:63)
        // com.njlabs.showjava.decompilers.JavaExtractionWorker.decompileWithCFR(JavaExtractionWorker.kt:61)
        // com.njlabs.showjava.decompilers.JavaExtractionWorker.doWork(JavaExtractionWorker.kt:130)
        // com.njlabs.showjava.decompilers.BaseDecompiler.withAttempt(BaseDecompiler.kt:108)
        // com.njlabs.showjava.workers.DecompilerWorker$b.run(DecompilerWorker.kt:118)
        // java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1167)
        // java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:641)
        // java.lang.Thread.run(Thread.java:919)
        throw new IllegalStateException("Decompilation failed");
    }

    public boolean create(int n) throws MessagingException {
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected POP3Message createMessage(Folder folder, int n) throws MessagingException {
        Constructor constructor = ((POP3Store)this.store).messageConstructor;
        POP3Message pOP3Message = null;
        if (constructor != null) {
            try {
                Object[] arrobject = new Object[]{this, new Integer(n)};
                pOP3Message = (POP3Message)((Object)constructor.newInstance(arrobject));
            }
            catch (Exception exception) {
                pOP3Message = null;
            }
        }
        if (pOP3Message != null) return pOP3Message;
        return new POP3Message(this, n);
    }

    public boolean delete(boolean bl) throws MessagingException {
        throw new MethodNotSupportedException("delete");
    }

    public boolean exists() {
        return this.exists;
    }

    public Message[] expunge() throws MessagingException {
        throw new MethodNotSupportedException("Expunge not supported");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void fetch(Message[] var1_1, FetchProfile var2_2) throws MessagingException {
        block15 : {
            var15_3 = this;
            // MONITORENTER : var15_3
            this.checkReadable();
            if (this.doneUidl || !var2_2.contains((FetchProfile.Item)UIDFolder.FetchProfileItem.UID)) ** GOTO lbl20
            var10_4 = new String[this.message_cache.size()];
            try {
                var13_5 = this.port.uidl(var10_4);
                if (var13_5) break block15;
                return;
            }
            catch (EOFException var12_6) {
                this.close(false);
                throw new FolderClosedException((Folder)this, var12_6.toString());
            }
            catch (IOException var11_7) {
                throw new MessagingException("error getting UIDL", (Exception)var11_7);
            }
        }
        var14_8 = 0;
        do {
            block16 : {
                if (var14_8 < var10_4.length) break block16;
                this.doneUidl = true;
lbl20: // 2 sources:
                if (!var2_2.contains(FetchProfile.Item.ENVELOPE)) return;
                {
                    break;
                }
            }
            if (var10_4[var14_8] != null) {
                ((POP3Message)this.getMessage((int)(var14_8 + 1))).uid = var10_4[var14_8];
            }
            ++var14_8;
        } while (true);
        for (var4_9 = 0; var4_9 < (var5_10 = var1_1.length); ++var4_9) {
            try {
                var7_12 = (POP3Message)var1_1[var4_9];
                var7_12.getHeader("");
                var7_12.getSize();
                continue;
            }
            catch (MessageRemovedException var6_11) {}
        }
        // MONITOREXIT : var15_3
    }

    protected void finalize() throws Throwable {
        super.finalize();
        this.close(false);
    }

    public Folder getFolder(String string2) throws MessagingException {
        throw new MessagingException("not a directory");
    }

    public String getFullName() {
        return this.name;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Message getMessage(int n) throws MessagingException {
        POP3Folder pOP3Folder = this;
        synchronized (pOP3Folder) {
            this.checkOpen();
            POP3Message pOP3Message = (POP3Message)((Object)this.message_cache.elementAt(n - 1));
            if (pOP3Message == null) {
                pOP3Message = this.createMessage(this, n);
                this.message_cache.setElementAt((Object)pOP3Message, n - 1);
            }
            return pOP3Message;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int getMessageCount() throws MessagingException {
        POP3Folder pOP3Folder = this;
        synchronized (pOP3Folder) {
            block6 : {
                boolean bl = this.opened;
                if (bl) break block6;
                return -1;
            }
            this.checkReadable();
            int n = this.total;
            return n;
        }
    }

    public String getName() {
        return this.name;
    }

    public Folder getParent() {
        return new DefaultFolder((POP3Store)this.store);
    }

    public Flags getPermanentFlags() {
        return new Flags();
    }

    Protocol getProtocol() throws MessagingException {
        this.checkOpen();
        return this.port;
    }

    public char getSeparator() {
        return '\u0000';
    }

    public int getSize() throws MessagingException {
        POP3Folder pOP3Folder = this;
        synchronized (pOP3Folder) {
            this.checkOpen();
            int n = this.size;
            return n;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public int[] getSizes() throws MessagingException {
        block30 : {
            block28 : {
                var20_1 = this;
                // MONITORENTER : var20_1
                this.checkOpen();
                var2_2 = new int[this.total];
                var3_3 = null;
                var4_4 = null;
                try {
                    var3_3 = this.port.list();
                    var11_5 = new LineInputStream(var3_3);
                    break block28;
                }
                catch (IOException var8_11) {}
                ** GOTO lbl-1000
                catch (Throwable var5_13) {}
                ** GOTO lbl-1000
            }
            do {
                block29 : {
                    var13_8 = var11_5.readLine();
                    if (var13_8 != null) break block29;
                    ** if (var11_5 == null) goto lbl-1000
lbl-1000: // 1 sources:
                    {
                        var11_5.close();
                    }
lbl-1000: // 2 sources:
                    {
                        break block30;
                    }
                }
                try {
                    var14_9 = new StringTokenizer(var13_8);
                    var16_6 = Integer.parseInt((String)var14_9.nextToken());
                    var17_7 = Integer.parseInt((String)var14_9.nextToken());
                    if (var16_6 <= 0 || var16_6 > this.total) continue;
                    var2_2[var16_6 - 1] = var17_7;
                    continue;
                }
                catch (Exception var15_10) {
                    continue;
                }
                break;
            } while (true);
            catch (IOException var19_20) {}
        }
        if (var3_3 == null) return var2_2;
        try {
            var3_3.close();
            // MONITOREXIT : var20_1
            return var2_2;
        }
        catch (IOException var18_16) {
            return var2_2;
        }
        catch (Throwable var5_15) {
            var4_4 = var11_5;
        }
lbl-1000: // 2 sources:
        {
            if (var4_4 != null) {
                try {
                    var4_4.close();
                }
                catch (IOException var7_18) {}
            }
            if (var3_3 == null) throw var5_14;
            try {
                var3_3.close();
            }
            catch (IOException var6_19) {
                throw var5_14;
            }
            throw var5_14;
        }
        catch (IOException var12_21) {
            var4_4 = var11_5;
        }
lbl-1000: // 2 sources:
        {
            if (var4_4 != null) {
                try {
                    var4_4.close();
                }
                catch (IOException var10_17) {}
            }
            if (var3_3 == null) return var2_2;
            try {
                var3_3.close();
                return var2_2;
            }
            catch (IOException var9_12) {
                return var2_2;
            }
        }
    }

    public int getType() {
        return 1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String getUID(Message message) throws MessagingException {
        POP3Folder pOP3Folder = this;
        synchronized (pOP3Folder) {
            this.checkOpen();
            POP3Message pOP3Message = (POP3Message)message;
            try {
                if (pOP3Message.uid != "UNKNOWN") return pOP3Message.uid;
                pOP3Message.uid = this.port.uidl(pOP3Message.getMessageNumber());
                return pOP3Message.uid;
            }
            catch (EOFException eOFException) {
                this.close(false);
                throw new FolderClosedException((Folder)this, eOFException.toString());
            }
            catch (IOException iOException) {
                throw new MessagingException("error getting UIDL", (Exception)((Object)iOException));
            }
        }
    }

    public boolean hasNewMessages() throws MessagingException {
        return false;
    }

    public boolean isOpen() {
        if (!this.opened) {
            return false;
        }
        if (this.store.isConnected()) {
            return true;
        }
        try {
            this.close(false);
            return false;
        }
        catch (MessagingException messagingException) {
            return false;
        }
    }

    public Folder[] list(String string2) throws MessagingException {
        throw new MessagingException("not a directory");
    }

    public InputStream listCommand() throws MessagingException, IOException {
        POP3Folder pOP3Folder = this;
        synchronized (pOP3Folder) {
            this.checkOpen();
            InputStream inputStream = this.port.list();
            return inputStream;
        }
    }

    protected void notifyMessageChangedListeners(int n, Message message) {
        super.notifyMessageChangedListeners(n, message);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void open(int n) throws MessagingException {
        POP3Folder pOP3Folder = this;
        synchronized (pOP3Folder) {
            this.checkClosed();
            if (!this.exists) {
                throw new FolderNotFoundException((Folder)this, "folder is not INBOX");
            }
            try {
                this.port = ((POP3Store)this.store).getPort(this);
                Status status = this.port.stat();
                this.total = status.total;
                this.size = status.size;
                this.mode = n;
                this.opened = true;
            }
            catch (IOException iOException) {
                try {
                    if (this.port == null) throw new MessagingException("Open failed", (Exception)((Object)iOException));
                    this.port.quit();
                    throw new MessagingException("Open failed", (Exception)((Object)iOException));
                }
                catch (IOException iOException2) {
                    throw new MessagingException("Open failed", (Exception)((Object)iOException));
                }
                finally {
                    this.port = null;
                    ((POP3Store)this.store).closePort(this);
                    throw new MessagingException("Open failed", (Exception)((Object)iOException));
                }
            }
            this.message_cache = new Vector(this.total);
            this.message_cache.setSize(this.total);
            this.doneUidl = false;
            this.notifyConnectionListeners(1);
            return;
        }
    }

    public boolean renameTo(Folder folder) throws MessagingException {
        throw new MethodNotSupportedException("renameTo");
    }
}

