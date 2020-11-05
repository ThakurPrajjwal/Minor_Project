/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.PrintStream
 *  java.lang.AssertionError
 *  java.lang.Boolean
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.IllegalStateException
 *  java.lang.IndexOutOfBoundsException
 *  java.lang.InterruptedException
 *  java.lang.Long
 *  java.lang.NullPointerException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Thread
 *  java.lang.Throwable
 *  java.util.Date
 *  java.util.Hashtable
 *  java.util.NoSuchElementException
 *  java.util.Vector
 *  javax.mail.FetchProfile
 *  javax.mail.FetchProfile$Item
 *  javax.mail.Flags
 *  javax.mail.Flags$Flag
 *  javax.mail.Folder
 *  javax.mail.FolderClosedException
 *  javax.mail.FolderNotFoundException
 *  javax.mail.Message
 *  javax.mail.MessageRemovedException
 *  javax.mail.MessagingException
 *  javax.mail.Quota
 *  javax.mail.Session
 *  javax.mail.Store
 *  javax.mail.StoreClosedException
 *  javax.mail.UIDFolder
 *  javax.mail.UIDFolder$FetchProfileItem
 *  javax.mail.internet.MimeMessage
 *  javax.mail.search.FlagTerm
 *  javax.mail.search.SearchException
 *  javax.mail.search.SearchTerm
 */
package com.sun.mail.imap;

import com.sun.mail.iap.BadCommandException;
import com.sun.mail.iap.CommandFailedException;
import com.sun.mail.iap.ConnectionException;
import com.sun.mail.iap.Literal;
import com.sun.mail.iap.Protocol;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;
import com.sun.mail.iap.ResponseHandler;
import com.sun.mail.imap.ACL;
import com.sun.mail.imap.AppendUID;
import com.sun.mail.imap.DefaultFolder;
import com.sun.mail.imap.IMAPMessage;
import com.sun.mail.imap.IMAPStore;
import com.sun.mail.imap.MessageLiteral;
import com.sun.mail.imap.Rights;
import com.sun.mail.imap.Utility;
import com.sun.mail.imap.protocol.FetchResponse;
import com.sun.mail.imap.protocol.IMAPProtocol;
import com.sun.mail.imap.protocol.IMAPResponse;
import com.sun.mail.imap.protocol.Item;
import com.sun.mail.imap.protocol.ListInfo;
import com.sun.mail.imap.protocol.MailboxInfo;
import com.sun.mail.imap.protocol.MessageSet;
import com.sun.mail.imap.protocol.Status;
import com.sun.mail.imap.protocol.UID;
import com.sun.mail.imap.protocol.UIDSet;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.Vector;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.FolderNotFoundException;
import javax.mail.Message;
import javax.mail.MessageRemovedException;
import javax.mail.MessagingException;
import javax.mail.Quota;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.StoreClosedException;
import javax.mail.UIDFolder;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchException;
import javax.mail.search.SearchTerm;

public class IMAPFolder
extends Folder
implements UIDFolder,
ResponseHandler {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ABORTING = 2;
    private static final int IDLE = 1;
    private static final int RUNNING = 0;
    protected static final char UNKNOWN_SEPARATOR = '\uffff';
    protected String[] attributes;
    protected Flags availableFlags;
    private Status cachedStatus = null;
    private long cachedStatusTime = 0L;
    private boolean connectionPoolDebug;
    private boolean debug = false;
    private boolean doExpungeNotification = true;
    protected boolean exists = false;
    protected String fullName;
    private int idleState = 0;
    protected boolean isNamespace = false;
    protected Vector messageCache;
    protected Object messageCacheLock;
    protected String name;
    private boolean opened = false;
    private PrintStream out;
    protected Flags permanentFlags;
    protected IMAPProtocol protocol;
    private int realTotal = -1;
    private boolean reallyClosed = true;
    private int recent = -1;
    protected char separator;
    private int total = -1;
    protected int type;
    protected Hashtable uidTable;
    private long uidnext = -1L;
    private long uidvalidity = -1L;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !IMAPFolder.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    protected IMAPFolder(ListInfo listInfo, IMAPStore iMAPStore) {
        this(listInfo.name, listInfo.separator, iMAPStore);
        if (listInfo.hasInferiors) {
            this.type = 2 | this.type;
        }
        if (listInfo.canOpen) {
            this.type = 1 | this.type;
        }
        this.exists = true;
        this.attributes = listInfo.attrs;
    }

    protected IMAPFolder(String string2, char c, IMAPStore iMAPStore) {
        int n;
        super((Store)iMAPStore);
        if (string2 == null) {
            throw new NullPointerException("Folder name is null");
        }
        this.fullName = string2;
        this.separator = c;
        this.messageCacheLock = new Object();
        this.debug = iMAPStore.getSession().getDebug();
        this.connectionPoolDebug = iMAPStore.getConnectionPoolDebug();
        this.out = iMAPStore.getSession().getDebugOut();
        if (this.out == null) {
            this.out = System.out;
        }
        this.isNamespace = false;
        if (c != '\uffff' && c != '\u0000' && (n = this.fullName.indexOf((int)c)) > 0 && n == -1 + this.fullName.length()) {
            this.fullName = this.fullName.substring(0, n);
            this.isNamespace = true;
        }
    }

    protected IMAPFolder(String string2, char c, IMAPStore iMAPStore, boolean bl) {
        this(string2, c, iMAPStore);
        this.isNamespace = bl;
    }

    static /* synthetic */ Status access$0(IMAPFolder iMAPFolder) throws ProtocolException {
        return iMAPFolder.getStatus();
    }

    static /* synthetic */ void access$2(IMAPFolder iMAPFolder, int n) {
        iMAPFolder.idleState = n;
    }

    private void checkClosed() {
        if (this.opened) {
            throw new IllegalStateException("This operation is not allowed on an open folder");
        }
    }

    private void checkExists() throws MessagingException {
        if (!this.exists && !this.exists()) {
            throw new FolderNotFoundException((Folder)this, String.valueOf((Object)this.fullName) + " not found");
        }
    }

    private void checkFlags(Flags flags) throws MessagingException {
        if (!$assertionsDisabled && !Thread.holdsLock((Object)this)) {
            throw new AssertionError();
        }
        if (this.mode != 2) {
            throw new IllegalStateException("Cannot change flags on READ_ONLY folder: " + this.fullName);
        }
    }

    private void checkOpened() throws FolderClosedException {
        if (!$assertionsDisabled && !Thread.holdsLock((Object)this)) {
            throw new AssertionError();
        }
        if (!this.opened) {
            if (this.reallyClosed) {
                throw new IllegalStateException("This operation is not allowed on a closed folder");
            }
            throw new FolderClosedException((Folder)this, "Lost folder connection to server");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void checkRange(int n) throws MessagingException {
        Object object;
        if (n < 1) {
            throw new IndexOutOfBoundsException();
        }
        if (n <= this.total) {
            return;
        }
        Object object2 = object = this.messageCacheLock;
        // MONITORENTER : object2
        try {
            this.keepConnectionAlive(false);
            // MONITOREXIT : object2
            if (n <= this.total) return;
        }
        catch (ConnectionException connectionException) {
            throw new FolderClosedException((Folder)this, connectionException.getMessage());
        }
        catch (ProtocolException protocolException) {
            throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
        }
        throw new IndexOutOfBoundsException();
    }

    private void cleanup(boolean bl) {
        this.releaseProtocol(bl);
        this.protocol = null;
        this.messageCache = null;
        this.uidTable = null;
        this.exists = false;
        this.attributes = null;
        this.opened = false;
        this.idleState = 0;
        this.notifyConnectionListeners(3);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void close(boolean var1_1, boolean var2_2) throws MessagingException {
        block30 : {
            block29 : {
                if (!IMAPFolder.$assertionsDisabled && !Thread.holdsLock((Object)this)) {
                    throw new AssertionError();
                }
                var10_4 = var3_3 = this.messageCacheLock;
                // MONITORENTER : var10_4
                if (!this.opened && this.reallyClosed) {
                    throw new IllegalStateException("This operation is not allowed on a closed folder");
                }
                this.reallyClosed = true;
                if (!this.opened) {
                    // MONITOREXIT : var10_4
                    return;
                }
                try {
                    this.waitIfIdle();
                    if (var2_2) {
                        if (this.debug) {
                            this.out.println("DEBUG: forcing folder " + this.fullName + " to close");
                        }
                        if (this.protocol == null) return;
                        {
                            this.protocol.disconnect();
                            return;
                        }
                    }
                    if (((IMAPStore)this.store).isConnectionPoolFull()) {
                        if (this.debug) {
                            this.out.println("DEBUG: pool is full, not adding an Authenticated connection");
                        }
                        if (var1_1) {
                            this.protocol.close();
                        }
                        if (this.protocol == null) return;
                        {
                            this.protocol.logout();
                            return;
                        }
                    }
                    if (var1_1) break block27;
                }
lbl29: // 4 sources:
                catch (ProtocolException var6_5) {
                    throw new MessagingException(var6_5.getMessage(), (Exception)var6_5);
                }
                {
                    block27 : {
                        var7_7 = this.mode;
                        if (var7_7 != 2) break block27;
                        this.protocol.examine(this.fullName);
                    }
                    ** try [egrp 10[TRYBLOCK] [24 : 277->313)] { 
lbl38: // 1 sources:
                    ** GOTO lbl42
                    {
                        block28 : {
                            catch (ProtocolException var8_8) {
                                if (this.protocol == null) break block28;
                                this.protocol.disconnect();
                            }
                        }
                        if (this.protocol == null) break block29;
                        break block30;
                    }
                }
                finally {
                    if (this.opened) {
                        this.cleanup(true);
                    }
                }
            }
            // MONITOREXIT : var10_4
            return;
        }
        this.protocol.close();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Folder[] doList(final String string2, final boolean bl) throws MessagingException {
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            this.checkExists();
            if (!this.isDirectory()) {
                return new Folder[0];
            }
            final char c = this.getSeparator();
            ListInfo[] arrlistInfo = (ListInfo[])this.doCommandIgnoreFailure(new ProtocolCommand(){

                @Override
                public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                    if (bl) {
                        return iMAPProtocol.lsub("", String.valueOf((Object)IMAPFolder.this.fullName) + c + string2);
                    }
                    return iMAPProtocol.list("", String.valueOf((Object)IMAPFolder.this.fullName) + c + string2);
                }
            });
            if (arrlistInfo == null) {
                return new Folder[0];
            }
            int n = arrlistInfo.length;
            int n2 = 0;
            if (n > 0) {
                boolean bl2 = arrlistInfo[0].name.equals((Object)(String.valueOf((Object)this.fullName) + c));
                n2 = 0;
                if (bl2) {
                    n2 = 1;
                }
            }
            Folder[] arrfolder = new IMAPFolder[arrlistInfo.length - n2];
            int n3 = n2;
            while (n3 < arrlistInfo.length) {
                arrfolder[n3 - n2] = new IMAPFolder(arrlistInfo[n3], (IMAPStore)this.store);
                ++n3;
            }
            return arrfolder;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private int findName(ListInfo[] arrlistInfo, String string2) {
        int n;
        for (n = 0; n < arrlistInfo.length && !arrlistInfo[n].name.equals((Object)string2); ++n) {
        }
        if (n < arrlistInfo.length) return n;
        return 0;
    }

    private IMAPProtocol getProtocol() throws ProtocolException {
        if (!$assertionsDisabled && !Thread.holdsLock((Object)this.messageCacheLock)) {
            throw new AssertionError();
        }
        this.waitIfIdle();
        return this.protocol;
    }

    private Status getStatus() throws ProtocolException {
        IMAPProtocol iMAPProtocol;
        Status status;
        block5 : {
            int n = ((IMAPStore)this.store).getStatusCacheTimeout();
            if (n > 0 && this.cachedStatus != null && System.currentTimeMillis() - this.cachedStatusTime < (long)n) {
                return this.cachedStatus;
            }
            iMAPProtocol = null;
            iMAPProtocol = this.getStoreProtocol();
            status = iMAPProtocol.status(this.fullName, null);
            if (n <= 0) break block5;
            this.cachedStatus = status;
            this.cachedStatusTime = System.currentTimeMillis();
        }
        return status;
        finally {
            this.releaseStoreProtocol(iMAPProtocol);
        }
    }

    private boolean isDirectory() {
        return (2 & this.type) != 0;
    }

    private void keepConnectionAlive(boolean bl) throws ProtocolException {
        IMAPProtocol iMAPProtocol;
        block5 : {
            if (System.currentTimeMillis() - this.protocol.getTimestamp() > 1000L) {
                this.waitIfIdle();
                this.protocol.noop();
            }
            if (bl && ((IMAPStore)this.store).hasSeparateStoreConnection()) {
                iMAPProtocol = null;
                iMAPProtocol = ((IMAPStore)this.store).getStoreProtocol();
                if (System.currentTimeMillis() - iMAPProtocol.getTimestamp() <= 1000L) break block5;
                iMAPProtocol.noop();
            }
        }
        return;
        finally {
            ((IMAPStore)this.store).releaseStoreProtocol(iMAPProtocol);
        }
    }

    private void releaseProtocol(boolean bl) {
        block3 : {
            block2 : {
                if (this.protocol == null) break block2;
                this.protocol.removeResponseHandler(this);
                if (!bl) break block3;
                ((IMAPStore)this.store).releaseProtocol(this, this.protocol);
            }
            return;
        }
        ((IMAPStore)this.store).releaseProtocol(this, null);
    }

    private void setACL(final ACL aCL, final char c) throws MessagingException {
        this.doOptionalCommand("ACL not supported", new ProtocolCommand(){

            @Override
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                iMAPProtocol.setACL(IMAPFolder.this.fullName, c, aCL);
                return null;
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void throwClosedException(ConnectionException connectionException) throws FolderClosedException, StoreClosedException {
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            if (this.protocol != null && connectionException.getProtocol() == this.protocol || this.protocol == null && !this.reallyClosed) {
                throw new FolderClosedException((Folder)this, connectionException.getMessage());
            }
            throw new StoreClosedException(this.store, connectionException.getMessage());
        }
    }

    public void addACL(ACL aCL) throws MessagingException {
        this.setACL(aCL, '\u0000');
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Message[] addMessages(Message[] arrmessage) throws MessagingException {
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            this.checkOpened();
            MimeMessage[] arrmimeMessage = new MimeMessage[arrmessage.length];
            AppendUID[] arrappendUID = this.appendUIDMessages(arrmessage);
            int n = 0;
            do {
                long l;
                long l2;
                int n2;
                if (n >= (n2 = arrappendUID.length)) {
                    return arrmimeMessage;
                }
                AppendUID appendUID = arrappendUID[n];
                if (appendUID != null && (l2 = appendUID.uidvalidity) == (l = this.uidvalidity)) {
                    try {
                        arrmimeMessage[n] = this.getMessageByUID(appendUID.uid);
                    }
                    catch (MessagingException messagingException) {}
                }
                ++n;
            } while (true);
        }
    }

    public void addRights(ACL aCL) throws MessagingException {
        this.setACL(aCL, '+');
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void appendMessages(Message[] arrmessage) throws MessagingException {
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            this.checkExists();
            int n = ((IMAPStore)this.store).getAppendBufferSize();
            int n2 = 0;
            do {
                MessageLiteral messageLiteral;
                int n3;
                if (n2 >= (n3 = arrmessage.length)) {
                    return;
                }
                Message message = arrmessage[n2];
                try {
                    int n4 = message.getSize() > n ? 0 : n;
                    messageLiteral = new MessageLiteral(message, n4);
                }
                catch (IOException iOException) {
                    throw new MessagingException("IOException while appending messages", (Exception)((Object)iOException));
                }
                catch (MessageRemovedException messageRemovedException) {
                    // empty catch block
                }
                Date date = message.getReceivedDate();
                if (date == null) {
                    date = message.getSentDate();
                }
                Date date2 = date;
                this.doCommand(new ProtocolCommand(message.getFlags(), date2, messageLiteral){
                    private final /* synthetic */ Date val$dd;
                    private final /* synthetic */ Flags val$f;
                    private final /* synthetic */ MessageLiteral val$mos;
                    {
                        this.val$f = flags;
                        this.val$dd = date;
                        this.val$mos = messageLiteral;
                    }

                    @Override
                    public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                        iMAPProtocol.append(IMAPFolder.this.fullName, this.val$f, this.val$dd, this.val$mos);
                        return null;
                    }
                });
                ++n2;
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public AppendUID[] appendUIDMessages(Message[] arrmessage) throws MessagingException {
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            this.checkExists();
            int n = ((IMAPStore)this.store).getAppendBufferSize();
            AppendUID[] arrappendUID = new AppendUID[arrmessage.length];
            int n2 = 0;
            do {
                MessageLiteral messageLiteral;
                int n3;
                if (n2 >= (n3 = arrmessage.length)) {
                    return arrappendUID;
                }
                Message message = arrmessage[n2];
                try {
                    int n4 = message.getSize() > n ? 0 : n;
                    messageLiteral = new MessageLiteral(message, n4);
                }
                catch (IOException iOException) {
                    throw new MessagingException("IOException while appending messages", (Exception)((Object)iOException));
                }
                catch (MessageRemovedException messageRemovedException) {
                    // empty catch block
                }
                Date date = message.getReceivedDate();
                if (date == null) {
                    date = message.getSentDate();
                }
                Date date2 = date;
                arrappendUID[n2] = (AppendUID)this.doCommand(new ProtocolCommand(message.getFlags(), date2, messageLiteral){
                    private final /* synthetic */ Date val$dd;
                    private final /* synthetic */ Flags val$f;
                    private final /* synthetic */ MessageLiteral val$mos;
                    {
                        this.val$f = flags;
                        this.val$dd = date;
                        this.val$mos = messageLiteral;
                    }

                    @Override
                    public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                        return iMAPProtocol.appenduid(IMAPFolder.this.fullName, this.val$f, this.val$dd, this.val$mos);
                    }
                });
                ++n2;
            } while (true);
        }
    }

    public void close(boolean bl) throws MessagingException {
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            this.close(bl, false);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void copyMessages(Message[] arrmessage, Folder folder) throws MessagingException {
        Object object;
        IMAPFolder iMAPFolder = this;
        // MONITORENTER : iMAPFolder
        this.checkOpened();
        int n = arrmessage.length;
        if (n == 0) {
            // MONITOREXIT : iMAPFolder
            return;
        }
        if (folder.getStore() != this.store) {
            super.copyMessages(arrmessage, folder);
            return;
        }
        Object object2 = object = this.messageCacheLock;
        // MONITORENTER : object2
        try {
            IMAPProtocol iMAPProtocol = this.getProtocol();
            MessageSet[] arrmessageSet = Utility.toMessageSet(arrmessage, null);
            if (arrmessageSet == null) {
                throw new MessageRemovedException("Messages have been removed");
            }
            iMAPProtocol.copy(arrmessageSet, folder.getFullName());
            // MONITOREXIT : object2
            return;
        }
        catch (CommandFailedException commandFailedException22) {
            if (commandFailedException22.getMessage().indexOf("TRYCREATE") == -1) throw new MessagingException(commandFailedException22.getMessage(), (Exception)commandFailedException22);
            throw new FolderNotFoundException(folder, String.valueOf((Object)folder.getFullName()) + " does not exist");
        }
        catch (ConnectionException connectionException) {
            throw new FolderClosedException((Folder)this, connectionException.getMessage());
        }
        catch (ProtocolException protocolException) {
            throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean create(final int n) throws MessagingException {
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            Object object;
            int n2 = n & 1;
            final char c = '\u0000';
            if (n2 == 0) {
                c = this.getSeparator();
            }
            if ((object = this.doCommandIgnoreFailure(new ProtocolCommand(){

                /*
                 * Enabled aggressive block sorting
                 */
                @Override
                public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                    if ((1 & n) == 0) {
                        iMAPProtocol.create(String.valueOf((Object)IMAPFolder.this.fullName) + c);
                        return Boolean.TRUE;
                    } else {
                        ListInfo[] arrlistInfo;
                        iMAPProtocol.create(IMAPFolder.this.fullName);
                        if ((2 & n) == 0 || (arrlistInfo = iMAPProtocol.list("", IMAPFolder.this.fullName)) == null || arrlistInfo[0].hasInferiors) return Boolean.TRUE;
                        {
                            iMAPProtocol.delete(IMAPFolder.this.fullName);
                            throw new ProtocolException("Unsupported type");
                        }
                    }
                }
            })) == null) {
                return false;
            }
            boolean bl = this.exists();
            if (!bl) return bl;
            this.notifyFolderListeners(1);
            return bl;
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
    public boolean delete(boolean var1_1) throws MessagingException {
        var8_2 = this;
        // MONITORENTER : var8_2
        this.checkClosed();
        if (!var1_1) ** GOTO lbl9
        var3_3 = this.list();
        var4_4 = 0;
        do {
            block7 : {
                if (var4_4 < var3_3.length) break block7;
lbl9: // 2 sources:
                var5_5 = this.doCommandIgnoreFailure(new ProtocolCommand(){

                    @Override
                    public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                        iMAPProtocol.delete(IMAPFolder.this.fullName);
                        return Boolean.TRUE;
                    }
                });
                var6_6 = false;
                if (var5_5 != null) break;
                // MONITOREXIT : var8_2
                return var6_6;
            }
            var3_3[var4_4].delete(var1_1);
            ++var4_4;
        } while (true);
        this.exists = false;
        this.attributes = null;
        this.notifyFolderListeners(2);
        return true;
    }

    public Object doCommand(ProtocolCommand protocolCommand) throws MessagingException {
        try {
            Object object = this.doProtocolCommand(protocolCommand);
            return object;
        }
        catch (ConnectionException connectionException) {
            this.throwClosedException(connectionException);
            return null;
        }
        catch (ProtocolException protocolException) {
            throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
        }
    }

    public Object doCommandIgnoreFailure(ProtocolCommand protocolCommand) throws MessagingException {
        try {
            Object object = this.doProtocolCommand(protocolCommand);
            return object;
        }
        catch (CommandFailedException commandFailedException) {
            return null;
        }
        catch (ConnectionException connectionException) {
            this.throwClosedException(connectionException);
            return null;
        }
        catch (ProtocolException protocolException) {
            throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
        }
    }

    public Object doOptionalCommand(String string2, ProtocolCommand protocolCommand) throws MessagingException {
        try {
            Object object = this.doProtocolCommand(protocolCommand);
            return object;
        }
        catch (BadCommandException badCommandException) {
            throw new MessagingException(string2, (Exception)badCommandException);
        }
        catch (ConnectionException connectionException) {
            this.throwClosedException(connectionException);
            return null;
        }
        catch (ProtocolException protocolException) {
            throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected Object doProtocolCommand(ProtocolCommand protocolCommand) throws ProtocolException {
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            if (this.opened && !((IMAPStore)this.store).hasSeparateStoreConnection()) {
                Object object;
                Object object2 = object = this.messageCacheLock;
                synchronized (object2) {
                    return protocolCommand.doCommand(this.getProtocol());
                }
            }
        }
        IMAPProtocol iMAPProtocol = null;
        try {
            iMAPProtocol = this.getStoreProtocol();
            Object object = protocolCommand.doCommand(iMAPProtocol);
            return object;
        }
        finally {
            this.releaseStoreProtocol(iMAPProtocol);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean exists() throws MessagingException {
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            (ListInfo[])null;
            final String string2 = this.isNamespace && this.separator != '\u0000' ? String.valueOf((Object)this.fullName) + this.separator : this.fullName;
            ListInfo[] arrlistInfo = (ListInfo[])this.doCommand(new ProtocolCommand(){

                @Override
                public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                    return iMAPProtocol.list("", string2);
                }
            });
            if (arrlistInfo != null) {
                int n = this.findName(arrlistInfo, string2);
                this.fullName = arrlistInfo[n].name;
                this.separator = arrlistInfo[n].separator;
                int n2 = this.fullName.length();
                if (this.separator != '\u0000' && n2 > 0 && this.fullName.charAt(n2 - 1) == this.separator) {
                    this.fullName = this.fullName.substring(0, n2 - 1);
                }
                this.type = 0;
                if (arrlistInfo[n].hasInferiors) {
                    this.type = 2 | this.type;
                }
                if (arrlistInfo[n].canOpen) {
                    this.type = 1 | this.type;
                }
                this.exists = true;
                this.attributes = arrlistInfo[n].attrs;
                return this.exists;
            } else {
                this.exists = this.opened;
                this.attributes = null;
            }
            return this.exists;
        }
    }

    public Message[] expunge() throws MessagingException {
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            Message[] arrmessage = this.expunge(null);
            return arrmessage;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Message[] expunge(Message[] arrmessage) throws MessagingException {
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            Object object;
            this.checkOpened();
            Vector vector = new Vector();
            if (arrmessage != null) {
                FetchProfile fetchProfile = new FetchProfile();
                fetchProfile.add((FetchProfile.Item)UIDFolder.FetchProfileItem.UID);
                this.fetch(arrmessage, fetchProfile);
            }
            Object object2 = object = this.messageCacheLock;
            synchronized (object2) {
                int n;
                this.doExpungeNotification = false;
                try {
                    IMAPProtocol iMAPProtocol = this.getProtocol();
                    if (arrmessage != null) {
                        iMAPProtocol.uidexpunge(Utility.toUIDSet(arrmessage));
                    } else {
                        iMAPProtocol.expunge();
                    }
                    n = 0;
                }
                catch (CommandFailedException commandFailedException) {
                    if (this.mode == 2) throw new MessagingException(commandFailedException.getMessage(), (Exception)commandFailedException);
                    throw new IllegalStateException("Cannot expunge READ_ONLY folder: " + this.fullName);
                }
                catch (Throwable throwable) {
                    throw throwable;
                }
                catch (ConnectionException connectionException) {
                    throw new FolderClosedException((Folder)this, connectionException.getMessage());
                }
                catch (ProtocolException protocolException) {
                    throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
                }
                do {
                    if (n >= this.messageCache.size()) {
                        // MONITOREXIT [18, 20, 11, 12] lbl32 : w: MONITOREXIT : var19_6
                        this.total = this.messageCache.size();
                        Object[] arrobject = new Message[vector.size()];
                        vector.copyInto(arrobject);
                        if (arrobject.length <= 0) return arrobject;
                        this.notifyMessageRemovedListeners(true, (Message[])arrobject);
                        return arrobject;
                    }
                    IMAPMessage iMAPMessage = (IMAPMessage)((Object)this.messageCache.elementAt(n));
                    if (iMAPMessage.isExpunged()) {
                        long l;
                        vector.addElement((Object)iMAPMessage);
                        this.messageCache.removeElementAt(n);
                        if (this.uidTable == null || (l = iMAPMessage.getUID()) == -1L) continue;
                        this.uidTable.remove((Object)new Long(l));
                        continue;
                    }
                    iMAPMessage.setMessageNumber(iMAPMessage.getSequenceNumber());
                    ++n;
                } while (true);
            }
        }
    }

    public void fetch(Message[] arrmessage, FetchProfile fetchProfile) throws MessagingException {
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            this.checkOpened();
            IMAPMessage.fetch(this, arrmessage, fetchProfile);
            return;
        }
    }

    public void forceClose() throws MessagingException {
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            this.close(false, true);
            return;
        }
    }

    public ACL[] getACL() throws MessagingException {
        return (ACL[])this.doOptionalCommand("ACL not supported", new ProtocolCommand(){

            @Override
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                return iMAPProtocol.getACL(IMAPFolder.this.fullName);
            }
        });
    }

    public String[] getAttributes() throws MessagingException {
        if (this.attributes == null) {
            this.exists();
        }
        return (String[])this.attributes.clone();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public int getDeletedMessageCount() throws MessagingException {
        int n;
        IMAPFolder iMAPFolder = this;
        // MONITORENTER : iMAPFolder
        if (!this.opened) {
            this.checkExists();
            n = -1;
            // MONITOREXIT : iMAPFolder
            return n;
        }
        Flags flags = new Flags();
        flags.add(Flags.Flag.DELETED);
        try {
            Object object;
            Object object2 = object = this.messageCacheLock;
            // MONITORENTER : object2
        }
        catch (ConnectionException connectionException) {
            throw new FolderClosedException((Folder)this, connectionException.getMessage());
        }
        catch (ProtocolException protocolException) {
            throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
        }
        n = this.getProtocol().search((SearchTerm)new FlagTerm(flags, true)).length;
        // MONITOREXIT : object2
        return n;
    }

    public Folder getFolder(String string2) throws MessagingException {
        if (this.attributes != null && !this.isDirectory()) {
            throw new MessagingException("Cannot contain subfolders");
        }
        char c = this.getSeparator();
        return new IMAPFolder(String.valueOf((Object)this.fullName) + c + string2, c, (IMAPStore)this.store);
    }

    public String getFullName() {
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            String string2 = this.fullName;
            return string2;
        }
    }

    public Message getMessage(int n) throws MessagingException {
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            this.checkOpened();
            this.checkRange(n);
            Message message = (Message)this.messageCache.elementAt(n - 1);
            return message;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    IMAPMessage getMessageBySeqNumber(int n) {
        int n2 = n - 1;
        while (n2 < this.total) {
            IMAPMessage iMAPMessage = (IMAPMessage)((Object)this.messageCache.elementAt(n2));
            if (iMAPMessage.getSequenceNumber() == n) return iMAPMessage;
            ++n2;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public Message getMessageByUID(long l) throws MessagingException {
        UID uID;
        IMAPFolder iMAPFolder = this;
        // MONITORENTER : iMAPFolder
        this.checkOpened();
        IMAPMessage iMAPMessage = null;
        try {
            Object object;
            Object object2 = object = this.messageCacheLock;
            // MONITORENTER : object2
        }
        catch (ConnectionException connectionException) {
            throw new FolderClosedException((Folder)this, connectionException.getMessage());
        }
        catch (ProtocolException protocolException) {
            throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
        }
        Long l2 = new Long(l);
        if (this.uidTable != null) {
            iMAPMessage = (IMAPMessage)((Object)this.uidTable.get((Object)l2));
            if (iMAPMessage != null) {
                // MONITOREXIT : object2
                IMAPMessage iMAPMessage2 = iMAPMessage;
                // MONITOREXIT : iMAPFolder
                return iMAPMessage2;
            }
        } else {
            this.uidTable = new Hashtable();
        }
        if ((uID = this.getProtocol().fetchSequenceNumber(l)) != null && uID.seqnum <= this.total) {
            iMAPMessage = this.getMessageBySeqNumber(uID.seqnum);
            iMAPMessage.setUID(uID.uid);
            this.uidTable.put((Object)l2, (Object)iMAPMessage);
        }
        // MONITOREXIT : object2
        return iMAPMessage;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public int getMessageCount() throws MessagingException {
        int n;
        Object object;
        IMAPFolder iMAPFolder = this;
        // MONITORENTER : iMAPFolder
        if (!this.opened) {
            this.checkExists();
            try {
                n = this.getStatus().total;
                // MONITOREXIT : iMAPFolder
                return n;
            }
            catch (BadCommandException badCommandException) {
                IMAPProtocol iMAPProtocol = null;
                try {
                    iMAPProtocol = this.getStoreProtocol();
                    MailboxInfo mailboxInfo = iMAPProtocol.examine(this.fullName);
                    iMAPProtocol.close();
                    n = mailboxInfo.total;
                    return n;
                }
                catch (ProtocolException protocolException) {
                    throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
                }
                finally {
                    this.releaseStoreProtocol(iMAPProtocol);
                    return n;
                }
            }
            catch (ConnectionException connectionException) {
                throw new StoreClosedException(this.store, connectionException.getMessage());
            }
            catch (ProtocolException protocolException) {
                throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
            }
        }
        Object object2 = object = this.messageCacheLock;
        // MONITORENTER : object2
        try {
            this.keepConnectionAlive(true);
            n = this.total;
            // MONITOREXIT : object2
            return n;
        }
        catch (ConnectionException connectionException) {
            throw new FolderClosedException((Folder)this, connectionException.getMessage());
        }
        catch (ProtocolException protocolException) {
            throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
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
    public Message[] getMessagesByUID(long var1_1, long var3_2) throws MessagingException {
        var15_3 = this;
        // MONITORENTER : var15_3
        this.checkOpened();
        try {
            var16_5 = var8_4 = this.messageCacheLock;
            // MONITORENTER : var16_5
            ** if (this.uidTable != null) goto lbl-1000
        }
        catch (ConnectionException var7_10) {
            throw new FolderClosedException((Folder)this, var7_10.getMessage());
        }
        catch (ProtocolException var6_11) {
            throw new MessagingException(var6_11.getMessage(), (Exception)var6_11);
        }
lbl-1000: // 1 sources:
        {
            this.uidTable = new Hashtable();
        }
lbl-1000: // 2 sources:
        {
        }
        var10_6 = this.getProtocol().fetchSequenceNumbers(var1_1, var3_2);
        var11_7 = new Message[var10_6.length];
        var12_8 = 0;
        do {
            if (var12_8 >= var10_6.length) {
                // MONITOREXIT : var16_5
                // MONITOREXIT : var15_3
                return var11_7;
            }
            var13_9 = this.getMessageBySeqNumber(var10_6[var12_8].seqnum);
            var13_9.setUID(var10_6[var12_8].uid);
            var11_7[var12_8] = var13_9;
            this.uidTable.put((Object)new Long(var10_6[var12_8].uid), (Object)var13_9);
            ++var12_8;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public Message[] getMessagesByUID(long[] var1_1) throws MessagingException {
        block22 : {
            block21 : {
                var20_2 = this;
                // MONITORENTER : var20_2
                this.checkOpened();
                try {
                    var21_4 = var5_3 = this.messageCacheLock;
                    // MONITORENTER : var21_4
                    var6_5 = var1_1;
                    if (this.uidTable != null) break block21;
                }
                catch (ConnectionException var4_17) {
                    throw new FolderClosedException((Folder)this, var4_17.getMessage());
                }
                catch (ProtocolException var3_18) {
                    throw new MessagingException(var3_18.getMessage(), (Exception)var3_18);
                }
                this.uidTable = new Hashtable();
                break block22;
            }
            var8_6 = new Vector();
            var9_7 = 0;
            do {
                block23 : {
                    if (var9_7 < var1_1.length) break block23;
                    var12_10 = var8_6.size();
                    var6_5 = new long[var12_10];
                    var13_11 = 0;
                    ** GOTO lbl52
                }
                var10_8 = this.uidTable;
                var11_9 = new Long(var1_1[var9_7]);
                if (!var10_8.containsKey((Object)var11_9)) {
                    var8_6.addElement((Object)var11_9);
                }
                ++var9_7;
            } while (true);
        }
lbl32: // 2 sources:
        do {
            if (var6_5.length > 0) {
                var16_12 = this.getProtocol().fetchSequenceNumbers(var6_5);
                for (var17_13 = 0; var17_13 < var16_12.length; ++var17_13) {
                    var18_14 = this.getMessageBySeqNumber(var16_12[var17_13].seqnum);
                    var18_14.setUID(var16_12[var17_13].uid);
                    this.uidTable.put((Object)new Long(var16_12[var17_13].uid), (Object)var18_14);
                }
            }
            var14_15 = new Message[var1_1.length];
            var15_16 = 0;
            do {
                if (var15_16 >= var1_1.length) {
                    // MONITOREXIT : var21_4
                    // MONITOREXIT : var20_2
                    return var14_15;
                }
                var14_15[var15_16] = (Message)this.uidTable.get((Object)new Long(var1_1[var15_16]));
                ++var15_16;
            } while (true);
            break;
        } while (true);
lbl-1000: // 1 sources:
        {
            var6_5[var13_11] = (Long)var8_6.elementAt(var13_11);
            ++var13_11;
lbl52: // 2 sources:
            ** while (var13_11 < var12_10)
        }
lbl53: // 1 sources:
        ** while (true)
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String getName() {
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            String string2 = this.name;
            if (string2 != null) return this.name;
            try {
                this.name = this.fullName.substring(1 + this.fullName.lastIndexOf((int)this.getSeparator()));
            }
            catch (MessagingException messagingException) {
                return this.name;
            }
            do {
                return this.name;
                break;
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public int getNewMessageCount() throws MessagingException {
        int n;
        Object object;
        IMAPFolder iMAPFolder = this;
        // MONITORENTER : iMAPFolder
        if (!this.opened) {
            this.checkExists();
            try {
                n = this.getStatus().recent;
                // MONITOREXIT : iMAPFolder
                return n;
            }
            catch (BadCommandException badCommandException) {
                IMAPProtocol iMAPProtocol = null;
                try {
                    iMAPProtocol = this.getStoreProtocol();
                    MailboxInfo mailboxInfo = iMAPProtocol.examine(this.fullName);
                    iMAPProtocol.close();
                    n = mailboxInfo.recent;
                    return n;
                }
                catch (ProtocolException protocolException) {
                    throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
                }
                finally {
                    this.releaseStoreProtocol(iMAPProtocol);
                    return n;
                }
            }
            catch (ConnectionException connectionException) {
                throw new StoreClosedException(this.store, connectionException.getMessage());
            }
            catch (ProtocolException protocolException) {
                throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
            }
        }
        Object object2 = object = this.messageCacheLock;
        // MONITORENTER : object2
        try {
            this.keepConnectionAlive(true);
            n = this.recent;
            // MONITOREXIT : object2
            return n;
        }
        catch (ConnectionException connectionException) {
            throw new FolderClosedException((Folder)this, connectionException.getMessage());
        }
        catch (ProtocolException protocolException) {
            throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Folder getParent() throws MessagingException {
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            void var5_6;
            char c = this.getSeparator();
            int n = this.fullName.lastIndexOf((int)c);
            if (n != -1) {
                IMAPFolder iMAPFolder2;
                IMAPFolder iMAPFolder3 = iMAPFolder2 = new IMAPFolder(this.fullName.substring(0, n), c, (IMAPStore)this.store);
            } else {
                DefaultFolder defaultFolder;
                DefaultFolder defaultFolder2 = defaultFolder = new DefaultFolder((IMAPStore)this.store);
            }
            return var5_6;
        }
    }

    public Flags getPermanentFlags() {
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            Flags flags = (Flags)this.permanentFlags.clone();
            return flags;
        }
    }

    public Quota[] getQuota() throws MessagingException {
        return (Quota[])this.doOptionalCommand("QUOTA not supported", new ProtocolCommand(){

            @Override
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                return iMAPProtocol.getQuotaRoot(IMAPFolder.this.fullName);
            }
        });
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public char getSeparator() throws MessagingException {
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            block6 : {
                if (this.separator != '\uffff') return this.separator;
                (ListInfo[])null;
                ListInfo[] arrlistInfo = (ListInfo[])this.doCommand(new ProtocolCommand(){

                    @Override
                    public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                        if (iMAPProtocol.isREV1()) {
                            return iMAPProtocol.list(IMAPFolder.this.fullName, "");
                        }
                        return iMAPProtocol.list("", IMAPFolder.this.fullName);
                    }
                });
                if (arrlistInfo == null) break block6;
                this.separator = arrlistInfo[0].separator;
                do {
                    return this.separator;
                    break;
                } while (true);
            }
            this.separator = (char)47;
            return this.separator;
        }
    }

    protected IMAPProtocol getStoreProtocol() throws ProtocolException {
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            if (this.connectionPoolDebug) {
                this.out.println("DEBUG: getStoreProtocol() - borrowing a connection");
            }
            IMAPProtocol iMAPProtocol = ((IMAPStore)this.store).getStoreProtocol();
            return iMAPProtocol;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int getType() throws MessagingException {
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            block5 : {
                if (!this.opened) break block5;
                if (this.attributes != null) return this.type;
                this.exists();
                do {
                    return this.type;
                    break;
                } while (true);
            }
            this.checkExists();
            return this.type;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long getUID(Message message) throws MessagingException {
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            long l;
            Object object;
            if (message.getFolder() != this) {
                throw new NoSuchElementException("Message does not belong to this folder");
            }
            this.checkOpened();
            IMAPMessage iMAPMessage = (IMAPMessage)message;
            long l2 = l = iMAPMessage.getUID();
            if (l2 != -1L) {
                return l2;
            }
            Object object2 = object = this.messageCacheLock;
            synchronized (object2) {
                try {
                    IMAPProtocol iMAPProtocol = this.getProtocol();
                    iMAPMessage.checkExpunged();
                    UID uID = iMAPProtocol.fetchUID(iMAPMessage.getSequenceNumber());
                    if (uID == null) return l2;
                    l2 = uID.uid;
                    iMAPMessage.setUID(l2);
                    if (this.uidTable == null) {
                        this.uidTable = new Hashtable();
                    }
                    this.uidTable.put((Object)new Long(l2), (Object)iMAPMessage);
                    return l2;
                }
                catch (ConnectionException connectionException) {
                    throw new FolderClosedException((Folder)this, connectionException.getMessage());
                }
                catch (ProtocolException protocolException) {
                    throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
                }
            }
        }
    }

    /*
     * Exception decompiling
     */
    public long getUIDNext() throws MessagingException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.b.a.a.j.b(Op04StructuredStatement.java:409)
        // org.benf.cfr.reader.b.a.a.j.d(Op04StructuredStatement.java:487)
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

    /*
     * Exception decompiling
     */
    public long getUIDValidity() throws MessagingException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.b.a.a.j.b(Op04StructuredStatement.java:409)
        // org.benf.cfr.reader.b.a.a.j.d(Op04StructuredStatement.java:487)
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public int getUnreadMessageCount() throws MessagingException {
        int n;
        IMAPFolder iMAPFolder = this;
        // MONITORENTER : iMAPFolder
        if (!this.opened) {
            this.checkExists();
            try {
                n = this.getStatus().unseen;
                // MONITOREXIT : iMAPFolder
                return n;
            }
            catch (BadCommandException badCommandException) {
                return -1;
            }
            catch (ConnectionException connectionException) {
                throw new StoreClosedException(this.store, connectionException.getMessage());
            }
            catch (ProtocolException protocolException) {
                throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
            }
        }
        Flags flags = new Flags();
        flags.add(Flags.Flag.SEEN);
        try {
            Object object;
            Object object2 = object = this.messageCacheLock;
            // MONITORENTER : object2
        }
        catch (ConnectionException connectionException) {
            throw new FolderClosedException((Folder)this, connectionException.getMessage());
        }
        catch (ProtocolException protocolException) {
            throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
        }
        n = this.getProtocol().search((SearchTerm)new FlagTerm(flags, false)).length;
        // MONITOREXIT : object2
        return n;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void handleResponse(Response response) {
        if (!$assertionsDisabled && !Thread.holdsLock((Object)this.messageCacheLock)) {
            throw new AssertionError();
        }
        if (response.isOK() || response.isNO() || response.isBAD() || response.isBYE()) {
            ((IMAPStore)this.store).handleResponseCode(response);
        }
        if (response.isBYE()) {
            if (!this.opened) return;
            this.cleanup(false);
            return;
        }
        if (response.isOK()) return;
        if (!response.isUnTagged()) return;
        if (!(response instanceof IMAPResponse)) {
            this.out.println("UNEXPECTED RESPONSE : " + response.toString());
            this.out.println("CONTACT javamail@sun.com");
            return;
        }
        IMAPResponse iMAPResponse = (IMAPResponse)response;
        if (!iMAPResponse.keyEquals("EXISTS")) {
            if (!iMAPResponse.keyEquals("EXPUNGE")) {
                if (!iMAPResponse.keyEquals("FETCH")) {
                    if (!iMAPResponse.keyEquals("RECENT")) return;
                    this.recent = iMAPResponse.getNumber();
                    return;
                }
                if (!$assertionsDisabled && !(iMAPResponse instanceof FetchResponse)) {
                    throw new AssertionError((Object)"!ir instanceof FetchResponse");
                }
                FetchResponse fetchResponse = (FetchResponse)iMAPResponse;
                Flags flags = (Flags)fetchResponse.getItem(Flags.class);
                if (flags == null) return;
                IMAPMessage iMAPMessage = this.getMessageBySeqNumber(fetchResponse.getNumber());
                if (iMAPMessage == null) return;
                iMAPMessage._setFlags(flags);
                this.notifyMessageChangedListeners(1, (Message)iMAPMessage);
                return;
            }
        } else {
            int n = iMAPResponse.getNumber();
            if (n <= this.realTotal) return;
            int n2 = n - this.realTotal;
            Message[] arrmessage = new Message[n2];
            int n3 = 0;
            do {
                int n4;
                int n5;
                if (n3 >= n2) {
                    this.notifyMessageAddedListeners(arrmessage);
                    return;
                }
                this.total = n4 = 1 + this.total;
                this.realTotal = n5 = 1 + this.realTotal;
                IMAPMessage iMAPMessage = new IMAPMessage(this, n4, n5);
                arrmessage[n3] = iMAPMessage;
                this.messageCache.addElement((Object)iMAPMessage);
                ++n3;
            } while (true);
        }
        IMAPMessage iMAPMessage = this.getMessageBySeqNumber(iMAPResponse.getNumber());
        iMAPMessage.setExpunged(true);
        int n = iMAPMessage.getMessageNumber();
        do {
            if (n >= this.total) {
                this.realTotal = -1 + this.realTotal;
                if (!this.doExpungeNotification) return;
                this.notifyMessageRemovedListeners(false, new Message[]{iMAPMessage});
                return;
            }
            IMAPMessage iMAPMessage2 = (IMAPMessage)((Object)this.messageCache.elementAt(n));
            if (!iMAPMessage2.isExpunged()) {
                iMAPMessage2.setSequenceNumber(-1 + iMAPMessage2.getSequenceNumber());
            }
            ++n;
        } while (true);
    }

    void handleResponses(Response[] arrresponse) {
        int n = 0;
        while (n < arrresponse.length) {
            if (arrresponse[n] != null) {
                this.handleResponse(arrresponse[n]);
            }
            ++n;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean hasNewMessages() throws MessagingException {
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            boolean bl;
            if (this.opened) {
                Object object;
                Object object2 = object = this.messageCacheLock;
                synchronized (object2) {
                    try {
                        this.keepConnectionAlive(true);
                        int n = this.recent;
                        bl = false;
                        if (n <= 0) return bl;
                        return true;
                    }
                    catch (ConnectionException connectionException) {
                        throw new FolderClosedException((Folder)this, connectionException.getMessage());
                    }
                    catch (ProtocolException protocolException) {
                        throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
                    }
                }
            }
            this.checkExists();
            Boolean bl2 = (Boolean)this.doCommandIgnoreFailure(new ProtocolCommand(){

                @Override
                public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                    ListInfo[] arrlistInfo = iMAPProtocol.list("", IMAPFolder.this.fullName);
                    if (arrlistInfo != null) {
                        if (arrlistInfo[0].changeState == 1) {
                            return Boolean.TRUE;
                        }
                        if (arrlistInfo[0].changeState == 2) {
                            return Boolean.FALSE;
                        }
                    }
                    if (IMAPFolder.access$0((IMAPFolder)IMAPFolder.this).recent > 0) {
                        return Boolean.TRUE;
                    }
                    return Boolean.FALSE;
                }
            });
            bl = false;
            if (bl2 == null) return bl;
            boolean bl3 = bl2;
            return bl3;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void idle() throws MessagingException {
        if (!$assertionsDisabled && Thread.holdsLock((Object)this)) {
            throw new AssertionError();
        }
        IMAPFolder iMAPFolder = this;
        // MONITORENTER : iMAPFolder
        this.checkOpened();
        if (!((Boolean)this.doOptionalCommand("IDLE not supported", new ProtocolCommand(){

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            @Override
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                if (IMAPFolder.this.idleState == 0) {
                    iMAPProtocol.idleStart();
                    IMAPFolder.access$2(IMAPFolder.this, 1);
                    return Boolean.TRUE;
                }
                try {
                    IMAPFolder.this.messageCacheLock.wait();
                    do {
                        return Boolean.FALSE;
                        break;
                    } while (true);
                }
                catch (InterruptedException interruptedException) {
                    return Boolean.FALSE;
                }
            }
        })).booleanValue()) {
            // MONITOREXIT : iMAPFolder
            return;
        }
        // MONITOREXIT : iMAPFolder
        do {
            Response response = this.protocol.readIdleResponse();
            try {
                Object object;
                Object object2 = object = this.messageCacheLock;
                // MONITORENTER : object2
            }
            catch (ConnectionException connectionException) {
                this.throwClosedException(connectionException);
                continue;
            }
            if (response == null || this.protocol == null || !this.protocol.processIdleResponse(response)) {
                this.idleState = 0;
                this.messageCacheLock.notifyAll();
                // MONITOREXIT : object2
                int n = ((IMAPStore)this.store).getMinIdleTime();
                if (n <= 0) return;
                long l = n;
                try {
                    Thread.sleep((long)l);
                    return;
                }
                catch (InterruptedException interruptedException) {
                    return;
                }
            }
            // MONITOREXIT : object2
            continue;
            break;
        } while (true);
        catch (ProtocolException protocolException) {
            throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isOpen() {
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            Object object;
            Object object2 = object = this.messageCacheLock;
            synchronized (object2) {
                boolean bl = this.opened;
                if (!bl) return this.opened;
                try {
                    this.keepConnectionAlive(false);
                }
                catch (ProtocolException protocolException) {}
                return this.opened;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isSubscribed() {
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            String string2;
            ListInfo[] arrlistInfo;
            String string3;
            arrlistInfo = null;
            string2 = this.isNamespace && this.separator != '\u0000' ? (string3 = String.valueOf((Object)this.fullName) + this.separator) : this.fullName;
            try {
                arrlistInfo = (ListInfo[])this.doProtocolCommand(new ProtocolCommand(){

                    @Override
                    public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                        return iMAPProtocol.lsub("", string2);
                    }
                });
            }
            catch (ProtocolException protocolException) {}
            if (arrlistInfo == null) return false;
            return arrlistInfo[this.findName((ListInfo[])arrlistInfo, (String)string2)].canOpen;
        }
    }

    public Folder[] list(String string2) throws MessagingException {
        return this.doList(string2, false);
    }

    public Rights[] listRights(final String string2) throws MessagingException {
        return (Rights[])this.doOptionalCommand("ACL not supported", new ProtocolCommand(){

            @Override
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                return iMAPProtocol.listRights(IMAPFolder.this.fullName, string2);
            }
        });
    }

    public Folder[] listSubscribed(String string2) throws MessagingException {
        return this.doList(string2, true);
    }

    public Rights myRights() throws MessagingException {
        return (Rights)this.doOptionalCommand("ACL not supported", new ProtocolCommand(){

            @Override
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                return iMAPProtocol.myRights(IMAPFolder.this.fullName);
            }
        });
    }

    /*
     * Exception decompiling
     */
    public void open(int var1_1) throws MessagingException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [28[SIMPLE_IF_TAKEN]], but top level block is 2[TRYBLOCK]
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

    protected void releaseStoreProtocol(IMAPProtocol iMAPProtocol) {
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            if (iMAPProtocol != this.protocol) {
                ((IMAPStore)this.store).releaseStoreProtocol(iMAPProtocol);
            }
            return;
        }
    }

    public void removeACL(final String string2) throws MessagingException {
        this.doOptionalCommand("ACL not supported", new ProtocolCommand(){

            @Override
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                iMAPProtocol.deleteACL(IMAPFolder.this.fullName, string2);
                return null;
            }
        });
    }

    public void removeRights(ACL aCL) throws MessagingException {
        this.setACL(aCL, '-');
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean renameTo(final Folder folder) throws MessagingException {
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            this.checkClosed();
            this.checkExists();
            if (folder.getStore() != this.store) {
                throw new MessagingException("Can't rename across Stores");
            }
            Object object = this.doCommandIgnoreFailure(new ProtocolCommand(){

                @Override
                public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                    iMAPProtocol.rename(IMAPFolder.this.fullName, folder.getFullName());
                    return Boolean.TRUE;
                }
            });
            boolean bl = false;
            if (object == null) return bl;
            this.exists = false;
            this.attributes = null;
            this.notifyFolderRenamedListeners(folder);
            return true;
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
    public Message[] search(SearchTerm var1_1) throws MessagingException {
        var12_2 = this;
        // MONITORENTER : var12_2
        this.checkOpened();
        try {
            var6_3 /* !! */  = null;
            var13_5 = var8_4 = this.messageCacheLock;
            // MONITORENTER : var13_5
        }
        catch (CommandFailedException var7_8) {
            return super.search(var1_1);
        }
        catch (SearchException var5_9) {
            return super.search(var1_1);
        }
        catch (ConnectionException var4_10) {
            throw new FolderClosedException((Folder)this, var4_10.getMessage());
        }
        catch (ProtocolException var3_11) {
            throw new MessagingException(var3_11.getMessage(), (Exception)var3_11);
        }
        var10_6 = this.getProtocol().search(var1_1);
        if (var10_6 == null) ** GOTO lbl24
        var6_3 /* !! */  = new IMAPMessage[var10_6.length];
        var11_7 = 0;
        do {
            block17 : {
                if (var11_7 < var10_6.length) break block17;
lbl24: // 2 sources:
                // MONITOREXIT : var13_5
                // MONITOREXIT : var12_2
                return var6_3 /* !! */ ;
            }
            var6_3 /* !! */ [var11_7] = this.getMessageBySeqNumber(var10_6[var11_7]);
            ++var11_7;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public Message[] search(SearchTerm var1_1, Message[] var2_2) throws MessagingException {
        block22 : {
            var17_3 = this;
            // MONITORENTER : var17_3
            this.checkOpened();
            var4_4 = var2_2.length;
            if (var4_4 == 0) {
                // MONITOREXIT : var17_3
                return var2_2;
            }
            try {
                var10_5 /* !! */  = null;
                var18_7 = var11_6 = this.messageCacheLock;
                // MONITORENTER : var18_7
            }
            catch (CommandFailedException var8_10) {
                var9_11 = super.search(var1_1, var2_2);
                return var9_11;
            }
            var13_8 = this.getProtocol();
            var14_9 = Utility.toMessageSet(var2_2, null);
            if (var14_9 == null) {
                throw new MessageRemovedException("Messages have been removed");
            }
            var15_12 = var13_8.search(var14_9, var1_1);
            if (var15_12 == null) ** GOTO lbl35
            break block22;
            catch (SearchException var7_14) {
                return super.search(var1_1, var2_2);
            }
            catch (ConnectionException var6_15) {
                throw new FolderClosedException((Folder)this, var6_15.getMessage());
            }
            catch (ProtocolException var5_16) {
                throw new MessagingException(var5_16.getMessage(), (Exception)var5_16);
            }
        }
        var10_5 /* !! */  = new IMAPMessage[var15_12.length];
        var16_13 = 0;
        do {
            block23 : {
                if (var16_13 < var15_12.length) break block23;
lbl35: // 2 sources:
                // MONITOREXIT : var18_7
                return var10_5 /* !! */ ;
            }
            var10_5 /* !! */ [var16_13] = this.getMessageBySeqNumber(var15_12[var16_13]);
            ++var16_13;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void setFlags(Message[] arrmessage, Flags flags, boolean bl) throws MessagingException {
        Object object;
        IMAPFolder iMAPFolder = this;
        // MONITORENTER : iMAPFolder
        this.checkOpened();
        this.checkFlags(flags);
        int n = arrmessage.length;
        if (n == 0) {
            // MONITOREXIT : iMAPFolder
            return;
        }
        Object object2 = object = this.messageCacheLock;
        // MONITORENTER : object2
        try {
            IMAPProtocol iMAPProtocol = this.getProtocol();
            MessageSet[] arrmessageSet = Utility.toMessageSet(arrmessage, null);
            if (arrmessageSet == null) {
                throw new MessageRemovedException("Messages have been removed");
            }
            iMAPProtocol.storeFlags(arrmessageSet, flags, bl);
            // MONITOREXIT : object2
            return;
        }
        catch (ConnectionException connectionException) {
            throw new FolderClosedException((Folder)this, connectionException.getMessage());
        }
        catch (ProtocolException protocolException) {
            throw new MessagingException(protocolException.getMessage(), (Exception)protocolException);
        }
    }

    public void setQuota(final Quota quota) throws MessagingException {
        this.doOptionalCommand("QUOTA not supported", new ProtocolCommand(){

            @Override
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                iMAPProtocol.setQuota(quota);
                return null;
            }
        });
    }

    public void setSubscribed(final boolean bl) throws MessagingException {
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            this.doCommandIgnoreFailure(new ProtocolCommand(){

                /*
                 * Enabled force condition propagation
                 * Lifted jumps to return sites
                 */
                @Override
                public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                    if (bl) {
                        iMAPProtocol.subscribe(IMAPFolder.this.fullName);
                        do {
                            return null;
                            break;
                        } while (true);
                    }
                    iMAPProtocol.unsubscribe(IMAPFolder.this.fullName);
                    return null;
                }
            });
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    void waitIfIdle() throws ProtocolException {
        if (IMAPFolder.$assertionsDisabled || Thread.holdsLock((Object)this.messageCacheLock)) ** GOTO lbl10
        throw new AssertionError();
lbl-1000: // 1 sources:
        {
            if (this.idleState == 1) {
                this.protocol.idleAbort();
                this.idleState = 2;
            }
            try {
                this.messageCacheLock.wait();
                continue;
            }
            catch (InterruptedException var1_1) {}
lbl10: // 3 sources:
            ** while (this.idleState != 0)
        }
lbl11: // 1 sources:
    }

    public static class FetchProfileItem
    extends FetchProfile.Item {
        public static final FetchProfileItem HEADERS = new FetchProfileItem("HEADERS");
        public static final FetchProfileItem SIZE = new FetchProfileItem("SIZE");

        protected FetchProfileItem(String string2) {
            super(string2);
        }
    }

    public static interface ProtocolCommand {
        public Object doCommand(IMAPProtocol var1) throws ProtocolException;
    }

}

