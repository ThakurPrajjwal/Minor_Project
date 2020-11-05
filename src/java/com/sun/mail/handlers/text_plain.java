/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.io.OutputStreamWriter
 *  java.io.UnsupportedEncodingException
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  javax.activation.ActivationDataFlavor
 *  javax.activation.DataContentHandler
 *  javax.activation.DataSource
 *  javax.mail.internet.ContentType
 *  javax.mail.internet.MimeUtility
 */
package com.sun.mail.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeUtility;
import myjava.awt.datatransfer.DataFlavor;

public class text_plain
implements DataContentHandler {
    private static ActivationDataFlavor myDF = new ActivationDataFlavor(String.class, "text/plain", "Text String");

    private String getCharset(String string2) {
        String string3;
        block3 : {
            try {
                string3 = new ContentType(string2).getParameter("charset");
                if (string3 != null) break block3;
                string3 = "us-ascii";
            }
            catch (Exception exception) {
                return null;
            }
        }
        String string4 = MimeUtility.javaCharset((String)string3);
        return string4;
    }

    /*
     * Exception decompiling
     */
    public Object getContent(DataSource var1_1) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK]], but top level block is 9[UNCONDITIONALDOLOOP]
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

    protected ActivationDataFlavor getDF() {
        return myDF;
    }

    public Object getTransferData(DataFlavor dataFlavor, DataSource dataSource) throws IOException {
        if (this.getDF().equals(dataFlavor)) {
            return this.getContent(dataSource);
        }
        return null;
    }

    public DataFlavor[] getTransferDataFlavors() {
        DataFlavor[] arrdataFlavor = new DataFlavor[]{this.getDF()};
        return arrdataFlavor;
    }

    public void writeTo(Object object, String string2, OutputStream outputStream) throws IOException {
        OutputStreamWriter outputStreamWriter;
        if (!(object instanceof String)) {
            throw new IOException("\"" + this.getDF().getMimeType() + "\" DataContentHandler requires String object, " + "was given object of type " + object.getClass().toString());
        }
        String string3 = null;
        try {
            string3 = this.getCharset(string2);
            outputStreamWriter = new OutputStreamWriter(outputStream, string3);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new UnsupportedEncodingException(string3);
        }
        String string4 = (String)object;
        outputStreamWriter.write(string4, 0, string4.length());
        outputStreamWriter.flush();
    }
}

