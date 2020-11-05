/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayInputStream
 *  java.io.CharArrayReader
 *  java.io.Externalizable
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.InputStreamReader
 *  java.io.ObjectInput
 *  java.io.ObjectOutput
 *  java.io.Reader
 *  java.io.Serializable
 *  java.io.StringReader
 *  java.lang.Class
 *  java.lang.ClassLoader
 *  java.lang.ClassNotFoundException
 *  java.lang.CloneNotSupportedException
 *  java.lang.Cloneable
 *  java.lang.Deprecated
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Thread
 *  java.lang.Throwable
 *  java.nio.ByteBuffer
 *  java.nio.CharBuffer
 *  java.nio.charset.Charset
 *  java.nio.charset.IllegalCharsetNameException
 *  java.util.Arrays
 *  java.util.Collection
 *  java.util.Comparator
 *  java.util.Iterator
 *  java.util.LinkedList
 *  java.util.List
 *  org.apache.harmony.awt.datatransfer.DTK
 *  org.apache.harmony.awt.internal.nls.Messages
 */
package myjava.awt.datatransfer;

import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import myjava.awt.datatransfer.MimeTypeProcessor;
import myjava.awt.datatransfer.Transferable;
import myjava.awt.datatransfer.UnsupportedFlavorException;
import org.apache.harmony.awt.datatransfer.DTK;
import org.apache.harmony.awt.internal.nls.Messages;

public class DataFlavor
implements Externalizable,
Cloneable {
    public static final DataFlavor javaFileListFlavor;
    public static final String javaJVMLocalObjectMimeType = "application/x-java-jvm-local-objectref";
    public static final String javaRemoteObjectMimeType = "application/x-java-remote-object";
    public static final String javaSerializedObjectMimeType = "application/x-java-serialized-object";
    @Deprecated
    public static final DataFlavor plainTextFlavor;
    private static DataFlavor plainUnicodeFlavor;
    private static final long serialVersionUID = 8367026044764648243L;
    private static final String[] sortedTextFlavors;
    public static final DataFlavor stringFlavor;
    private String humanPresentableName;
    private MimeTypeProcessor.MimeType mimeInfo;
    private Class<?> representationClass;

    static {
        plainTextFlavor = new DataFlavor("text/plain; charset=unicode; class=java.io.InputStream", "Plain Text");
        stringFlavor = new DataFlavor("application/x-java-serialized-object; class=java.lang.String", "Unicode String");
        javaFileListFlavor = new DataFlavor("application/x-java-file-list; class=java.util.List", "application/x-java-file-list");
        sortedTextFlavors = new String[]{"text/sgml", "text/xml", "text/html", "text/rtf", "text/enriched", "text/richtext", "text/uri-list", "text/tab-separated-values", "text/t140", "text/rfc822-headers", "text/parityfec", "text/directory", "text/css", "text/calendar", javaSerializedObjectMimeType, "text/plain"};
        plainUnicodeFlavor = null;
    }

    public DataFlavor() {
        this.mimeInfo = null;
        this.humanPresentableName = null;
        this.representationClass = null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public DataFlavor(Class<?> class_, String string2) {
        this.mimeInfo = new MimeTypeProcessor.MimeType("application", "x-java-serialized-object");
        this.humanPresentableName = string2 != null ? string2 : javaSerializedObjectMimeType;
        this.mimeInfo.addParameter("class", class_.getName());
        this.representationClass = class_;
    }

    public DataFlavor(String string2) throws ClassNotFoundException {
        this.init(string2, null, null);
    }

    public DataFlavor(String string2, String string3) {
        try {
            this.init(string2, string3, null);
            return;
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new IllegalArgumentException(Messages.getString((String)"awt.16C", (Object)this.mimeInfo.getParameter("class")), (Throwable)classNotFoundException);
        }
    }

    public DataFlavor(String string2, String string3, ClassLoader classLoader) throws ClassNotFoundException {
        this.init(string2, string3, classLoader);
    }

    private static List<DataFlavor> fetchTextFlavors(List<DataFlavor> list, String string2) {
        LinkedList linkedList = new LinkedList();
        Iterator iterator = list.iterator();
        do {
            if (!iterator.hasNext()) {
                if (linkedList.isEmpty()) {
                    linkedList = null;
                }
                return linkedList;
            }
            DataFlavor dataFlavor = (DataFlavor)iterator.next();
            if (dataFlavor.isFlavorTextType()) {
                if (!dataFlavor.mimeInfo.getFullType().equals((Object)string2)) continue;
                if (!linkedList.contains((Object)dataFlavor)) {
                    linkedList.add((Object)dataFlavor);
                }
                iterator.remove();
                continue;
            }
            iterator.remove();
        } while (true);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private String getCharset() {
        if (this.mimeInfo == null) return "";
        if (this.isCharsetRedundant()) {
            return "";
        }
        String string2 = this.mimeInfo.getParameter("charset");
        if (this.isCharsetRequired()) {
            if (string2 == null) return DTK.getDTK().getDefaultCharset();
            if (string2.length() == 0) {
                return DTK.getDTK().getDefaultCharset();
            }
        }
        if (string2 != null) return string2;
        return "";
    }

    private static List<DataFlavor> getFlavors(List<DataFlavor> list, Class<?> class_) {
        LinkedList linkedList = new LinkedList();
        Iterator iterator = list.iterator();
        do {
            if (!iterator.hasNext()) {
                if (linkedList.isEmpty()) {
                    list = null;
                }
                return list;
            }
            DataFlavor dataFlavor = (DataFlavor)iterator.next();
            if (!dataFlavor.representationClass.equals(class_)) continue;
            linkedList.add((Object)dataFlavor);
        } while (true);
    }

    private static List<DataFlavor> getFlavors(List<DataFlavor> list, String[] arrstring) {
        LinkedList linkedList = new LinkedList();
        Iterator iterator = list.iterator();
        block0 : do {
            if (!iterator.hasNext()) {
                if (linkedList.isEmpty()) {
                    list = null;
                }
                return list;
            }
            DataFlavor dataFlavor = (DataFlavor)iterator.next();
            if (DataFlavor.isCharsetSupported(dataFlavor.getCharset())) {
                int n = arrstring.length;
                int n2 = 0;
                do {
                    if (n2 >= n) continue block0;
                    if (Charset.forName((String)arrstring[n2]).equals((Object)Charset.forName((String)dataFlavor.getCharset()))) {
                        linkedList.add((Object)dataFlavor);
                    }
                    ++n2;
                } while (true);
            }
            iterator.remove();
        } while (true);
    }

    private String getKeyInfo() {
        String string2 = String.valueOf((Object)this.mimeInfo.getFullType()) + ";class=" + this.representationClass.getName();
        if (!this.mimeInfo.getPrimaryType().equals((Object)"text") || this.isUnicodeFlavor()) {
            return string2;
        }
        return String.valueOf((Object)string2) + ";charset=" + this.getCharset().toLowerCase();
    }

    public static final DataFlavor getTextPlainUnicodeFlavor() {
        if (plainUnicodeFlavor == null) {
            plainUnicodeFlavor = new DataFlavor("text/plain; charset=" + DTK.getDTK().getDefaultCharset() + "; class=java.io.InputStream", "Plain Text");
        }
        return plainUnicodeFlavor;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void init(String string2, String string3, ClassLoader classLoader) throws ClassNotFoundException {
        try {
            this.mimeInfo = MimeTypeProcessor.parse(string2);
            this.humanPresentableName = string3 != null ? string3 : String.valueOf((Object)this.mimeInfo.getPrimaryType()) + '/' + this.mimeInfo.getSubType();
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new IllegalArgumentException(Messages.getString((String)"awt.16D", (Object)string2));
        }
        String string4 = this.mimeInfo.getParameter("class");
        if (string4 == null) {
            string4 = "java.io.InputStream";
            this.mimeInfo.addParameter("class", string4);
        }
        Class class_ = classLoader == null ? Class.forName((String)string4) : classLoader.loadClass(string4);
        this.representationClass = class_;
    }

    private boolean isByteCodeFlavor() {
        return this.representationClass != null && (this.representationClass.equals(InputStream.class) || this.representationClass.equals(ByteBuffer.class) || this.representationClass.equals(byte[].class));
    }

    private boolean isCharsetRedundant() {
        String string2 = this.mimeInfo.getFullType();
        return string2.equals((Object)"text/rtf") || string2.equals((Object)"text/tab-separated-values") || string2.equals((Object)"text/t140") || string2.equals((Object)"text/rfc822-headers") || string2.equals((Object)"text/parityfec");
    }

    private boolean isCharsetRequired() {
        String string2 = this.mimeInfo.getFullType();
        return string2.equals((Object)"text/sgml") || string2.equals((Object)"text/xml") || string2.equals((Object)"text/html") || string2.equals((Object)"text/enriched") || string2.equals((Object)"text/richtext") || string2.equals((Object)"text/uri-list") || string2.equals((Object)"text/directory") || string2.equals((Object)"text/css") || string2.equals((Object)"text/calendar") || string2.equals((Object)javaSerializedObjectMimeType) || string2.equals((Object)"text/plain");
    }

    private static boolean isCharsetSupported(String string2) {
        try {
            boolean bl = Charset.isSupported((String)string2);
            return bl;
        }
        catch (IllegalCharsetNameException illegalCharsetNameException) {
            return false;
        }
    }

    private boolean isUnicodeFlavor() {
        return this.representationClass != null && (this.representationClass.equals(Reader.class) || this.representationClass.equals(String.class) || this.representationClass.equals(CharBuffer.class) || this.representationClass.equals(char[].class));
    }

    /*
     * Enabled aggressive block sorting
     */
    private static List<DataFlavor> selectBestByAlphabet(List<DataFlavor> list) {
        Object[] arrobject = new String[list.size()];
        LinkedList linkedList = new LinkedList();
        int n = 0;
        do {
            if (n >= arrobject.length) break;
            arrobject[n] = ((DataFlavor)list.get(n)).getCharset();
            ++n;
        } while (true);
        Arrays.sort((Object[])arrobject, (Comparator)String.CASE_INSENSITIVE_ORDER);
        Iterator iterator = list.iterator();
        do {
            if (!iterator.hasNext()) {
                if (!linkedList.isEmpty()) return linkedList;
                return null;
            }
            DataFlavor dataFlavor = (DataFlavor)iterator.next();
            if (!arrobject[0].equalsIgnoreCase(dataFlavor.getCharset())) continue;
            linkedList.add((Object)dataFlavor);
        } while (true);
    }

    private static DataFlavor selectBestByCharset(List<DataFlavor> list) {
        List<DataFlavor> list2 = DataFlavor.getFlavors(list, new String[]{"UTF-16", "UTF-8", "UTF-16BE", "UTF-16LE"});
        if (list2 == null) {
            String[] arrstring = new String[]{DTK.getDTK().getDefaultCharset()};
            list2 = DataFlavor.getFlavors(list, arrstring);
            if (list2 == null) {
                list2 = DataFlavor.getFlavors(list, new String[]{"US-ASCII"});
                if (list2 == null) {
                    list2 = DataFlavor.selectBestByAlphabet(list);
                }
            }
        }
        if (list2 != null) {
            if (list2.size() == 1) {
                return (DataFlavor)list2.get(0);
            }
            return DataFlavor.selectBestFlavorWOCharset(list2);
        }
        return null;
    }

    private static DataFlavor selectBestFlavorWCharset(List<DataFlavor> list) {
        List<DataFlavor> list2 = DataFlavor.getFlavors(list, Reader.class);
        if (list2 != null) {
            return (DataFlavor)list2.get(0);
        }
        List<DataFlavor> list3 = DataFlavor.getFlavors(list, String.class);
        if (list3 != null) {
            return (DataFlavor)list3.get(0);
        }
        List<DataFlavor> list4 = DataFlavor.getFlavors(list, CharBuffer.class);
        if (list4 != null) {
            return (DataFlavor)list4.get(0);
        }
        List<DataFlavor> list5 = DataFlavor.getFlavors(list, char[].class);
        if (list5 != null) {
            return (DataFlavor)list5.get(0);
        }
        return DataFlavor.selectBestByCharset(list);
    }

    private static DataFlavor selectBestFlavorWOCharset(List<DataFlavor> list) {
        List<DataFlavor> list2 = DataFlavor.getFlavors(list, InputStream.class);
        if (list2 != null) {
            return (DataFlavor)list2.get(0);
        }
        List<DataFlavor> list3 = DataFlavor.getFlavors(list, ByteBuffer.class);
        if (list3 != null) {
            return (DataFlavor)list3.get(0);
        }
        List<DataFlavor> list4 = DataFlavor.getFlavors(list, byte[].class);
        if (list4 != null) {
            return (DataFlavor)list4.get(0);
        }
        return (DataFlavor)list.get(0);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static final DataFlavor selectBestTextFlavor(DataFlavor[] arrdataFlavor) {
        List<List<DataFlavor>> list;
        if (arrdataFlavor == null || (list = DataFlavor.sortTextFlavorsByType((List<DataFlavor>)new LinkedList((Collection)Arrays.asList((Object[])arrdataFlavor)))).isEmpty()) {
            return null;
        }
        List list2 = (List)list.get(0);
        if (list2.size() == 1) {
            return (DataFlavor)list2.get(0);
        }
        if (((DataFlavor)list2.get(0)).getCharset().length() == 0) {
            return DataFlavor.selectBestFlavorWOCharset((List<DataFlavor>)list2);
        }
        return DataFlavor.selectBestFlavorWCharset((List<DataFlavor>)list2);
    }

    private static List<List<DataFlavor>> sortTextFlavorsByType(List<DataFlavor> list) {
        LinkedList linkedList = new LinkedList();
        String[] arrstring = sortedTextFlavors;
        int n = arrstring.length;
        int n2 = 0;
        do {
            if (n2 >= n) {
                if (!list.isEmpty()) {
                    linkedList.addLast(list);
                }
                return linkedList;
            }
            List<DataFlavor> list2 = DataFlavor.fetchTextFlavors(list, arrstring[n2]);
            if (list2 != null) {
                linkedList.addLast(list2);
            }
            ++n2;
        } while (true);
    }

    protected static final Class<?> tryToLoadClass(String string2, ClassLoader classLoader) throws ClassNotFoundException {
        try {
            Class class_ = Class.forName((String)string2);
            return class_;
        }
        catch (ClassNotFoundException classNotFoundException) {
            try {
                Class class_ = ClassLoader.getSystemClassLoader().loadClass(string2);
                return class_;
            }
            catch (ClassNotFoundException classNotFoundException2) {
                ClassLoader classLoader2 = Thread.currentThread().getContextClassLoader();
                if (classLoader2 != null) {
                    try {
                        Class class_ = classLoader2.loadClass(string2);
                        return class_;
                    }
                    catch (ClassNotFoundException classNotFoundException3) {
                        // empty catch block
                    }
                }
                return classLoader.loadClass(string2);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public Object clone() throws CloneNotSupportedException {
        DataFlavor dataFlavor = new DataFlavor();
        dataFlavor.humanPresentableName = this.humanPresentableName;
        dataFlavor.representationClass = this.representationClass;
        MimeTypeProcessor.MimeType mimeType = this.mimeInfo != null ? (MimeTypeProcessor.MimeType)this.mimeInfo.clone() : null;
        dataFlavor.mimeInfo = mimeType;
        return dataFlavor;
    }

    public boolean equals(Object object) {
        if (object == null || !(object instanceof DataFlavor)) {
            return false;
        }
        return this.equals((DataFlavor)object);
    }

    @Deprecated
    public boolean equals(String string2) {
        if (string2 == null) {
            return false;
        }
        return this.isMimeTypeEqual(string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(DataFlavor dataFlavor) {
        if (dataFlavor == this) return true;
        if (dataFlavor == null) {
            return false;
        }
        if (this.mimeInfo == null) {
            if (dataFlavor.mimeInfo == null) return true;
            return false;
        }
        if (!this.mimeInfo.equals(dataFlavor.mimeInfo) || !this.representationClass.equals(dataFlavor.representationClass)) {
            return false;
        }
        if (!this.mimeInfo.getPrimaryType().equals((Object)"text") || this.isUnicodeFlavor()) {
            return true;
        }
        String string2 = this.getCharset();
        String string3 = dataFlavor.getCharset();
        if (!DataFlavor.isCharsetSupported(string2) || !DataFlavor.isCharsetSupported(string3)) return string2.equalsIgnoreCase(string3);
        return Charset.forName((String)string2).equals((Object)Charset.forName((String)string3));
    }

    public final Class<?> getDefaultRepresentationClass() {
        return InputStream.class;
    }

    public final String getDefaultRepresentationClassAsString() {
        return this.getDefaultRepresentationClass().getName();
    }

    public String getHumanPresentableName() {
        return this.humanPresentableName;
    }

    MimeTypeProcessor.MimeType getMimeInfo() {
        return this.mimeInfo;
    }

    public String getMimeType() {
        if (this.mimeInfo != null) {
            return MimeTypeProcessor.assemble(this.mimeInfo);
        }
        return null;
    }

    public String getParameter(String string2) {
        String string3 = string2.toLowerCase();
        if (string3.equals((Object)"humanpresentablename")) {
            return this.humanPresentableName;
        }
        if (this.mimeInfo != null) {
            return this.mimeInfo.getParameter(string3);
        }
        return null;
    }

    public String getPrimaryType() {
        if (this.mimeInfo != null) {
            return this.mimeInfo.getPrimaryType();
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public Reader getReaderForText(Transferable transferable) throws UnsupportedFlavorException, IOException {
        InputStream inputStream;
        Object object = transferable.getTransferData(this);
        if (object == null) {
            throw new IllegalArgumentException(Messages.getString((String)"awt.16E"));
        }
        if (object instanceof Reader) {
            Reader reader = (Reader)object;
            reader.reset();
            return reader;
        }
        if (object instanceof String) {
            return new StringReader((String)object);
        }
        if (object instanceof CharBuffer) {
            return new CharArrayReader(((CharBuffer)object).array());
        }
        if (object instanceof char[]) {
            return new CharArrayReader((char[])object);
        }
        String string2 = this.getCharset();
        if (object instanceof InputStream) {
            inputStream = (InputStream)object;
            inputStream.reset();
        } else if (object instanceof ByteBuffer) {
            inputStream = new ByteArrayInputStream(((ByteBuffer)object).array());
        } else {
            if (!(object instanceof byte[])) {
                throw new IllegalArgumentException(Messages.getString((String)"awt.16F"));
            }
            inputStream = new ByteArrayInputStream((byte[])object);
        }
        if (string2.length() == 0) {
            return new InputStreamReader(inputStream);
        }
        return new InputStreamReader(inputStream, string2);
    }

    public Class<?> getRepresentationClass() {
        return this.representationClass;
    }

    public String getSubType() {
        if (this.mimeInfo != null) {
            return this.mimeInfo.getSubType();
        }
        return null;
    }

    public int hashCode() {
        return this.getKeyInfo().hashCode();
    }

    public boolean isFlavorJavaFileListType() {
        return List.class.isAssignableFrom(this.representationClass) && this.isMimeTypeEqual(javaFileListFlavor);
    }

    public boolean isFlavorRemoteObjectType() {
        return this.isMimeTypeEqual(javaRemoteObjectMimeType) && this.isRepresentationClassRemote();
    }

    public boolean isFlavorSerializedObjectType() {
        return this.isMimeTypeSerializedObject() && this.isRepresentationClassSerializable();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean isFlavorTextType() {
        String string2;
        block6 : {
            block5 : {
                if (this.equals(stringFlavor) || this.equals(plainTextFlavor)) break block5;
                if (this.mimeInfo != null && !this.mimeInfo.getPrimaryType().equals((Object)"text")) {
                    return false;
                }
                string2 = this.getCharset();
                if (!this.isByteCodeFlavor()) {
                    return this.isUnicodeFlavor();
                }
                if (string2.length() != 0) break block6;
            }
            return true;
        }
        return DataFlavor.isCharsetSupported(string2);
    }

    public boolean isMimeTypeEqual(String string2) {
        try {
            boolean bl = this.mimeInfo.equals(MimeTypeProcessor.parse(string2));
            return bl;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return false;
        }
    }

    public final boolean isMimeTypeEqual(DataFlavor dataFlavor) {
        if (this.mimeInfo != null) {
            return this.mimeInfo.equals(dataFlavor.mimeInfo);
        }
        return dataFlavor.mimeInfo == null;
    }

    public boolean isMimeTypeSerializedObject() {
        return this.isMimeTypeEqual(javaSerializedObjectMimeType);
    }

    public boolean isRepresentationClassByteBuffer() {
        return ByteBuffer.class.isAssignableFrom(this.representationClass);
    }

    public boolean isRepresentationClassCharBuffer() {
        return CharBuffer.class.isAssignableFrom(this.representationClass);
    }

    public boolean isRepresentationClassInputStream() {
        return InputStream.class.isAssignableFrom(this.representationClass);
    }

    public boolean isRepresentationClassReader() {
        return Reader.class.isAssignableFrom(this.representationClass);
    }

    public boolean isRepresentationClassRemote() {
        return false;
    }

    public boolean isRepresentationClassSerializable() {
        return Serializable.class.isAssignableFrom(this.representationClass);
    }

    public boolean match(DataFlavor dataFlavor) {
        return this.equals(dataFlavor);
    }

    @Deprecated
    protected String normalizeMimeType(String string2) {
        return string2;
    }

    @Deprecated
    protected String normalizeMimeTypeParameter(String string2, String string3) {
        return string3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        DataFlavor dataFlavor = this;
        synchronized (dataFlavor) {
            this.humanPresentableName = (String)objectInput.readObject();
            this.mimeInfo = (MimeTypeProcessor.MimeType)objectInput.readObject();
            Class class_ = this.mimeInfo != null ? Class.forName((String)this.mimeInfo.getParameter("class")) : null;
            this.representationClass = class_;
            return;
        }
    }

    public void setHumanPresentableName(String string2) {
        this.humanPresentableName = string2;
    }

    public String toString() {
        return String.valueOf((Object)this.getClass().getName()) + "[MimeType=(" + this.getMimeType() + ");humanPresentableName=" + this.humanPresentableName + "]";
    }

    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        DataFlavor dataFlavor = this;
        synchronized (dataFlavor) {
            objectOutput.writeObject((Object)this.humanPresentableName);
            objectOutput.writeObject((Object)this.mimeInfo);
            return;
        }
    }
}

