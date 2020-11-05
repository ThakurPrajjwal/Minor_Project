/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.database.sqlite.SQLiteOpenHelper
 *  android.os.AsyncTask
 *  java.io.PrintStream
 *  java.io.UnsupportedEncodingException
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Void
 *  java.text.SimpleDateFormat
 *  java.util.Calendar
 *  java.util.Date
 *  java.util.Properties
 *  java.util.UUID
 *  javax.activation.DataHandler
 *  javax.activation.DataSource
 *  javax.activation.FileDataSource
 *  javax.mail.Address
 *  javax.mail.Authenticator
 *  javax.mail.BodyPart
 *  javax.mail.Message
 *  javax.mail.Message$RecipientType
 *  javax.mail.MessagingException
 *  javax.mail.Multipart
 *  javax.mail.PasswordAuthentication
 *  javax.mail.Session
 *  javax.mail.Transport
 *  javax.mail.internet.AddressException
 *  javax.mail.internet.InternetAddress
 *  javax.mail.internet.MimeBodyPart
 *  javax.mail.internet.MimeMessage
 *  javax.mail.internet.MimeMultipart
 */
package com.neoAntara.newrestaurant;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import com.neoAntara.newrestaurant.MainActivity;
import com.neoAntara.newrestaurant.Models.AppActivationModel;
import com.neoAntara.newrestaurant.Models.RolesModel;
import com.neoAntara.newrestaurant.UtilityHelper;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class DBHelper
extends SQLiteOpenHelper {
    public Context context = null;
    public String deviceID = null;

    public DBHelper(Context context, String string2) {
        super(context, "MyDB", null, 1);
        this.context = context;
        this.deviceID = string2;
    }

    private Message createMessage(String string2, String string3, String string4, Session session) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom((Address)new InternetAddress("rohit46.karande@gmail.com", "Rohit"));
        mimeMessage.addRecipient(Message.RecipientType.TO, (Address)new InternetAddress(string2, string2));
        mimeMessage.setSubject(string3);
        mimeMessage.setContent((Object)string4, "text/html");
        return mimeMessage;
    }

    private Message createMessageWithAttachment(String string2, String string3, String string4, Session session, String string5, String string6) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom((Address)new InternetAddress("rohit46.karande@gmail.com", "Rohit"));
        mimeMessage.addRecipient(Message.RecipientType.TO, (Address)new InternetAddress(string2, string2));
        mimeMessage.setSubject(string3);
        mimeMessage.setText(string4);
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        MimeMultipart mimeMultipart = new MimeMultipart();
        mimeMultipart.addBodyPart((BodyPart)mimeBodyPart);
        MimeBodyPart mimeBodyPart2 = new MimeBodyPart();
        mimeBodyPart2.setDataHandler(new DataHandler((DataSource)new FileDataSource(string6)));
        mimeBodyPart2.setFileName("Restaurant_DB");
        mimeMultipart.addBodyPart((BodyPart)mimeBodyPart2);
        mimeMessage.setContent((Multipart)mimeMultipart);
        return mimeMessage;
    }

    private Session createSessionObject() {
        Properties properties = new Properties();
        properties.put((Object)"mail.smtp.auth", (Object)"true");
        properties.put((Object)"mail.smtp.starttls.enable", (Object)"true");
        properties.put((Object)"mail.smtp.host", (Object)"smtp.gmail.com");
        properties.put((Object)"mail.smtp.port", (Object)"587");
        return Session.getInstance((Properties)properties, (Authenticator)new Authenticator(){

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("rohit46.karande@gmail.com", "ngbzvxqzbpdxmikk");
            }
        });
    }

    private String getMaxOrderNoFromHistory(SQLiteDatabase sQLiteDatabase) {
        Cursor cursor = sQLiteDatabase.rawQuery("SELECT orderno FROM payed_orders_history", null);
        int n = cursor.getCount();
        cursor.moveToFirst();
        if (n > 0) {
            Cursor cursor2 = sQLiteDatabase.rawQuery("SELECT max(orderno) FROM payed_orders_history", null);
            cursor2.moveToFirst();
            int n2 = 1 + Integer.parseInt((String)cursor2.getString(0));
            Object[] arrobject = new Object[]{n2};
            return String.format((String)"%06d", (Object[])arrobject);
        }
        return "000001";
    }

    private void sendMail(String string2, String string3, String string4) {
        Session session = this.createSessionObject();
        try {
            Message message = this.createMessage(string2, string3, string4, session);
            Address[] arraddress = new Address[]{new InternetAddress("abhijeet25990@gmail.com"), new InternetAddress("rohit46.karande@gmail.com")};
            message.setRecipients(Message.RecipientType.TO, arraddress);
            new SendMailTask().execute((Object[])new Message[]{message});
            return;
        }
        catch (AddressException addressException) {
            addressException.printStackTrace();
            return;
        }
        catch (MessagingException messagingException) {
            messagingException.printStackTrace();
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            unsupportedEncodingException.printStackTrace();
            return;
        }
    }

    private void sendMailToCustomer(String string2, String string3, String string4, String string5) {
        Session session = this.createSessionObject();
        try {
            Message message = this.createMessage(string2, string3, string4, session);
            Address[] arraddress = new Address[]{new InternetAddress(string5)};
            message.setRecipients(Message.RecipientType.TO, arraddress);
            new SendMailTask().execute((Object[])new Message[]{message});
            return;
        }
        catch (AddressException addressException) {
            addressException.printStackTrace();
            return;
        }
        catch (MessagingException messagingException) {
            messagingException.printStackTrace();
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            unsupportedEncodingException.printStackTrace();
            return;
        }
    }

    private void sendMailWithAttachment(String string2, String string3, String string4, String string5, String string6) {
        Session session = this.createSessionObject();
        try {
            Message message = this.createMessageWithAttachment(string2, string3, string4, session, string5, string6);
            new SendMailTask().execute((Object[])new Message[]{message});
            return;
        }
        catch (AddressException addressException) {
            addressException.printStackTrace();
            return;
        }
        catch (MessagingException messagingException) {
            messagingException.printStackTrace();
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            unsupportedEncodingException.printStackTrace();
            return;
        }
    }

    public int ActivateApp(String string2) {
        try {
            this.getReadableDatabase().execSQL("Update register_app set status = 1 where uniqueID = '" + string2 + "'");
            return 0;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return 0;
        }
    }

    public boolean ActivateDeActivateApp(String string2, int n) {
        try {
            this.getReadableDatabase().execSQL("Update register_app set status = '" + n + "' where deviceID = '" + string2 + "'");
            return true;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return false;
        }
    }

    public int CheckActivation(String string2) {
        try {
            int n = this.getReadableDatabase().rawQuery("SELECT * from register_app where deviceID = '" + string2 + "' AND status = 1", null).getCount();
            return n;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return 0;
        }
    }

    public Cursor CreateResto(String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, String string10) {
        try {
            this.getReadableDatabase().execSQL("INSERT INTO setting VALUES('" + string2 + "','" + string3 + "','" + string4 + "','" + string5 + "','" + string6 + "','" + string7 + "','" + string8 + "','" + string9 + "','" + string10 + "')");
            return null;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor CreateUser(String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, String string10, String string11, String string12, String string13, String string14) {
        Cursor cursor = null;
        try {
            SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();
            cursor = sQLiteDatabase.rawQuery("select max(emp_code) from emp_master", null);
            cursor.moveToFirst();
            int n = 1 + Integer.parseInt((String)cursor.getString(0));
            String string15 = new SimpleDateFormat("dd/mm/yyyy").format(Calendar.getInstance().getTime());
            sQLiteDatabase.execSQL("INSERT INTO emp_master values('" + n + "','" + string2 + "','" + string4 + "','" + string5 + "','" + string6 + "','" + string7 + "','" + string8 + "','" + string9 + "','" + string10 + "','" + string11 + "','" + string12 + "','" + string13 + "','" + string15 + "','" + string15 + "','null','null','" + string15 + "')");
            sQLiteDatabase.execSQL("INSERT INTO user_master values('" + string2 + "','" + n + "','" + string14 + "','" + string3 + "','null','" + string15 + "')");
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return cursor;
        }
    }

    public String GetToken() {
        return this.context.getSharedPreferences(MainActivity.class.getSimpleName(), 0).getString("Token", "");
    }

    public String GetUniqueId(String string2) {
        String string3 = "";
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * from register_app where deviceID = '" + string2 + "' AND status = 1", null);
            string3 = cursor.getString(cursor.getColumnIndex(""));
            cursor.close();
            return string3;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return string3;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int InsertIntoItemMaster(String string2, String string3, String string4) {
        try {
            SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();
            Cursor cursor = sQLiteDatabase.rawQuery("SELECT * FROM item_master where item_descr='" + string2 + "'", null);
            int n = cursor.getCount();
            cursor.moveToFirst();
            if (n > 0) {
                return 1;
            }
            Cursor cursor2 = sQLiteDatabase.rawQuery("SELECT * FROM item_master", null);
            int n2 = cursor2.getCount();
            cursor2.moveToFirst();
            if (n2 > 0) {
                Cursor cursor3 = sQLiteDatabase.rawQuery("SELECT max(item_code) FROM item_master", null);
                cursor3.moveToFirst();
                int n3 = 1 + Integer.parseInt((String)cursor3.getString(0));
                String string5 = this.getBusinessDate();
                sQLiteDatabase.execSQL("INSERT INTO item_master VALUES('" + n3 + "','" + string2 + "','" + string3 + "','" + string4 + "',null,'" + string5 + "',null,null)");
                cursor3.close();
            } else {
                String string6 = this.getBusinessDate();
                sQLiteDatabase.execSQL("INSERT INTO item_master VALUES('" + 101 + "','" + string2 + "','" + string3 + "','" + string4 + "',null,'" + string6 + "',null,null)");
            }
            sQLiteDatabase.close();
            return 0;
        }
        catch (Exception exception) {
            int n = -1;
            UtilityHelper.LogException(exception);
            return n;
        }
    }

    public void InsertIntoRegisterApp(String string2, String string3) {
        try {
            SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();
            if (sQLiteDatabase.rawQuery("SELECT * from register_app where deviceID = '" + string2 + "' AND status = 0", null).getCount() == 0) {
                sQLiteDatabase.execSQL("INSERT INTO register_app VALUES('" + string2 + "','" + string3 + "', 0)");
                return;
            }
            sQLiteDatabase.execSQL("UPDATE register_app set uniqueID= '" + string3 + "' where deviceID = '" + string2 + "' AND status = 0");
            return;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return;
        }
    }

    public void InsertIntoTableMaster() {
        Cursor cursor;
        SQLiteDatabase sQLiteDatabase;
        block5 : {
            sQLiteDatabase = this.getReadableDatabase();
            cursor = sQLiteDatabase.rawQuery("SELECT * FROM table_master", null);
            int n = cursor.getCount();
            System.out.println("count = " + n);
            if (n == 20) break block5;
            for (int i = 1; i <= 20; ++i) {
                sQLiteDatabase.execSQL("INSERT INTO table_master VALUES('" + i + "',null,'yes',null,null)");
            }
        }
        try {
            cursor.close();
            sQLiteDatabase.close();
            return;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return;
        }
    }

    public void InsertToken(String string2) {
        this.context.getSharedPreferences(MainActivity.class.getSimpleName(), 0).edit().putString("Token", string2).commit();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void PrintOrder(String string2, String string3, String string4, String string5, int n, String string6, String string7, String string8, String string9, String string10, String string11, String string12, int n2) {
        try {
            SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();
            String string13 = this.getBusinessDate();
            sQLiteDatabase.execSQL("INSERT INTO print_order VALUES('" + string2 + "','" + string3 + "','" + string4 + "','" + string5 + "','" + n + "',null,'" + string6 + "','" + string7 + "','" + string8 + "','" + string9 + "','" + string10 + "','" + string13 + "','" + string11 + "','" + "0000" + "')");
            if (string12 != "0000" && n2 == 1) {
                sQLiteDatabase.execSQL("Update print_order set reprint_orderno='" + string3 + "' where orderno ='" + string12 + "'");
            }
            sQLiteDatabase.close();
            return;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void SubmitOrder(String string2, String string3, String string4, String string5, int n, String string6, String string7, String string8, String string9, String string10, String string11, String string12, String string13, int n2) {
        try {
            SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();
            String string14 = this.getBusinessDate();
            sQLiteDatabase.execSQL("INSERT INTO payed_orders VALUES('" + string2 + "','" + string3 + "','" + string4 + "','" + string5 + "','" + n + "',null,'" + string6 + "','" + string7 + "','" + string8 + "','" + string9 + "','" + string10 + "','" + string11 + "','" + string14 + "','" + string12 + "', '" + "0000" + "')");
            if (string13 != "0000" && n2 == 1) {
                sQLiteDatabase.execSQL("Update payed_orders set reprint_orderno='" + string3 + "' where orderno ='" + string13 + "'");
            }
            sQLiteDatabase.close();
            return;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return;
        }
    }

    public void UpdateItemInItemMaster(String string2, String string3, String string4, int n) {
        try {
            SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();
            sQLiteDatabase.execSQL("Update item_master set item_descr='" + string2 + "',item_prize='" + string3 + "',item_active='" + string4 + "' where item_code='" + n + "'");
            sQLiteDatabase.close();
            return;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return;
        }
    }

    public Cursor UpdateOpeningBal(String string2) {
        try {
            this.getReadableDatabase().execSQL("UPDATE setting SET opening_bal='" + string2 + "'");
            return null;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor UpdateOpeningBalFromCreditTable(String string2, String string3, String string4, String string5) {
        try {
            this.getReadableDatabase().execSQL("INSERT INTO credit_amount VALUES((select IFNULL(MAX(srno),0)+1 from credit_amount),'" + string5 + "','" + string3 + "','" + string4 + "')");
            return null;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor UpdateResto(String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, String string10) {
        try {
            this.getReadableDatabase().execSQL("UPDATE setting SET restaurant_name= '" + string2 + "',address='" + string3 + "',phone_no='" + string4 + "',website='" + string5 + "',cgst='" + string6 + "',sgst='" + string7 + "',service_charge='" + string8 + "',opening_bal='" + string9 + "',gstn_number='" + string10 + "'");
            return null;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor changepassword(String string2, String string3) {
        try {
            this.getReadableDatabase().execSQL("UPDATE user_master SET password='" + string3 + "' WHERE userid='" + string2 + "'");
            return null;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor deleteOrder(String string2, String string3) {
        try {
            this.getReadableDatabase().execSQL("delete from payed_orders where orderno='" + string2 + "' and tableno='" + string3 + "'");
            return null;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor deletePrintOrder(String string2, String string3) {
        try {
            this.getReadableDatabase().execSQL("delete from print_order where orderno='" + string2 + "' and tableno='" + string3 + "'");
            return null;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor deletefromCrAmount(int n) {
        try {
            this.getReadableDatabase().execSQL("delete from credit_amount where srno='" + n + "'");
            return null;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor deleteitem(String string2) {
        try {
            this.getReadableDatabase().execSQL("delete from item_master where item_descr='" + string2 + "'");
            return null;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public int demoItemCount() {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM item_master", null);
        int n = cursor.getCount();
        cursor.moveToFirst();
        return n >= 5;
    }

    protected void getActivationKey(String string2, String string3, String string4, String string5, String string6) {
        try {
            String string7 = UUID.randomUUID().toString();
            this.sendEmail(this.deviceID, string7, string2, string3, string4, string5, string6);
            this.InsertIntoRegisterApp(this.deviceID, string7);
            return;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return;
        }
    }

    public String getBusinessDate() {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM system_parameter", null);
            cursor.moveToFirst();
            String string2 = cursor.getString(0);
            return string2;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public String getCashBookCr(String string2, String string3, String string4) {
        String string5 = "";
        String string6 = "";
        SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sQLiteDatabase.rawQuery("SELECT distinct orderno,date FROM print_order where reprint_orderno = '" + string4 + "' and date between '" + string2 + "' and '" + string3 + "'", null);
        int n = cursor.getCount();
        cursor.moveToFirst();
        for (int i = 0; i < n; ++i) {
            block8 : {
                Cursor cursor2 = sQLiteDatabase.rawQuery("SELECT * FROM credit_amount where reprint_orderno = '" + string4 + "' and date between '" + string2 + "' and '" + string3 + "'", null);
                cursor2.moveToFirst();
                if (i != 0) break block8;
                int n2 = 0;
                do {
                    if (n2 >= cursor2.getCount()) break;
                    String string7 = string6 + cursor2.getString(1) + " " + cursor2.getString(2);
                    string6 = string7 + "<";
                    cursor2.moveToNext();
                    ++n2;
                } while (true);
            }
            Cursor cursor3 = sQLiteDatabase.rawQuery("select TOTAL(item_prize) as total from print_order  where reprint_orderno = '" + string4 + "' and date='" + cursor.getString(1) + "' and orderno='" + cursor.getString(0) + "'", null);
            cursor3.moveToFirst();
            string5 = string5 + cursor.getString(0) + " " + cursor.getString(1) + " " + cursor3.getString(0);
            string5 = string5 + ":";
            cursor.moveToNext();
        }
        try {
            String string8 = string5 + "#" + string6;
            return string8;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return string5;
        }
    }

    public Cursor getCrAmtDetails(String string2) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM credit_amount where date='" + string2 + "'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public String getDailyReport(String string2, String string3) {
        String string4 = "";
        SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sQLiteDatabase.rawQuery("SELECT distinct orderno,tableno FROM print_order where reprint_orderno = '" + string3 + "' and date='" + string2 + "'", null);
        int n = cursor.getCount();
        cursor.moveToFirst();
        for (int i = 0; i < n; ++i) {
            try {
                Cursor cursor2 = sQLiteDatabase.rawQuery("select TOTAL(item_prize) as total from print_order  where reprint_orderno = '" + string3 + "' and date='" + string2 + "' and orderno='" + cursor.getString(0) + "'", null);
                cursor2.moveToFirst();
                string4 = string4 + cursor.getString(0) + " " + cursor.getString(1) + " " + cursor2.getString(0);
                string4 = string4 + ":";
                cursor.moveToNext();
            }
            catch (Exception exception) {
                UtilityHelper.LogException(exception);
                break;
            }
            continue;
        }
        return string4;
    }

    public Cursor getDailyReportForItemName(String string2, String string3, String string4) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM print_order where reprint_orderno = '" + string4 + "' and item_descr='" + string2 + "' and date='" + string3 + "'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getDailyReportTotal(String string2, String string3) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT TOTAL(item_prize) as total_amt,TOTAL(other_charges) as total_other_charges, TOTAL(discount) as discount FROM print_order where reprint_orderno = '" + string3 + "' and date='" + string2 + "'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getDailyReportTotalCashCardAmt(String string2, String string3, String string4) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT TOTAL(item_prize) as total_amt FROM print_order where reprint_orderno = '" + string4 + "' and date='" + string2 + "' AND payment_type = '" + string3 + "'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getDailyReportTotalForItemNameTotal(String string2, String string3, String string4) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT TOTAL(item_prize) as total_amt, TOTAL(other_charges) as total_other_charges, TOTAL(discount) as discount FROM print_order where reprint_orderno = '" + string4 + "' and item_descr='" + string2 + "' and date='" + string3 + "'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getExistingOrders(int n) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT distinct orderno FROM payed_orders where tableno='" + n + "'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getExistingOrdersDetails(String string2, String string3) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM payed_orders where reprint_orderno = '" + string3 + "' and orderno='" + string2 + "'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getExpenseReport(String string2, String string3) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM credit_amount where date between '" + string2 + "' and '" + string3 + "'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getHDOrder() {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT distinct orderno FROM payed_orders where tableno='Home Delivery' and print_flag='no'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getInstallationDate() {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT created_date FROM user_master WHERE userid='admin'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getItem(String string2) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT item_code,item_active FROM item_master where item_descr='" + string2 + "'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getItemCode(String string2) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT item_code,item_prize FROM item_master where item_descr='" + string2 + "' and item_active='" + "yes" + "'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getItemDetails() {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM item_master ORDER BY item_descr", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getItemDetails(String string2) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM item_master  where  item_descr like '%" + string2 + "%' ORDER BY item_descr", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getItemName() {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT item_code,item_descr FROM item_master where item_active='yes'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getItemPriceTotal() {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT TOTAL(item_prize),discount,other_charges FROM payed_orders", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getItem_code(String string2) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT item_code,item_active FROM item_master where item_descr='" + string2 + "'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String getOrderDetails() {
        SQLiteDatabase sQLiteDatabase;
        String string2 = null;
        try {
            sQLiteDatabase = this.getReadableDatabase();
            Cursor cursor = sQLiteDatabase.rawQuery("SELECT orderno FROM payed_orders", null);
            int n = cursor.getCount();
            cursor.moveToFirst();
            if (n <= 0) return this.getMaxOrderNoFromHistory(sQLiteDatabase);
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return string2;
        }
        Cursor cursor = sQLiteDatabase.rawQuery("SELECT max(orderno) FROM payed_orders", null);
        cursor.moveToFirst();
        string2 = cursor.getString(0);
        int n = 1 + Integer.parseInt((String)string2);
        Object[] arrobject = new Object[]{n};
        return String.format((String)"%06d", (Object[])arrobject);
    }

    public Cursor getOrders(String string2, String string3) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM payed_orders where date between '" + string2 + "' and '" + string3 + "'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getParcelOrder() {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT distinct orderno FROM payed_orders where tableno='Parcel' and print_flag='no'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Cursor getPrintOrdersToExport() {
        int n;
        Cursor cursor = null;
        try {
            int n2;
            cursor = this.getReadableDatabase().rawQuery("SELECT * from payed_orders_history", null);
            n = n2 = cursor.getCount();
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            n = 0;
        }
        if (n > 0) {
            return cursor;
        }
        return null;
    }

    public Cursor getPrintReport(String string2, String string3, String string4) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM print_order where reprint_orderno = '" + string4 + "' and date between '" + string2 + "' and '" + string3 + "'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getPrintReportTotal(String string2, String string3, String string4) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT TOTAL(item_prize) as total_amt, TOTAL(other_charges) as total_other_charges, TOTAL(discount) as discount FROM print_order where reprint_orderno = '" + string4 + "' and date between '" + string2 + "' and '" + string3 + "'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getPrintReportTotalCashCardAmt(String string2, String string3, String string4, String string5) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT TOTAL(item_prize) as total_amt FROM print_order where reprint_orderno = '" + string5 + "' and payment_type ='" + string4 + "' and date between '" + string2 + "' and '" + string3 + "'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getPrintedOrder(String string2, String string3, String string4) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT distinct orderno FROM print_order where reprint_orderno = '" + string4 + "' and tableno ='" + string2 + "'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getPrintedOrdersDetails(String string2) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM print_order where orderno='" + string2 + "'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getReportCashBookTotal(String string2, String string3, String string4) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT TOTAL(item_prize) as total_amt, TOTAL(other_charges) as total_other_charges, TOTAL(discount) as discount FROM print_order where reprint_orderno = '" + string4 + "' and date between '" + string2 + "' and '" + string3 + "'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getReportCashBookTotalCardCashAmt(String string2, String string3, String string4, String string5) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT TOTAL(item_prize) as total_amt FROM print_order where reprint_orderno = '" + string5 + "' and payment_type = '" + string4 + "' and date between '" + string2 + "' and '" + string3 + "'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getReportForAllTables(String string2, String string3, String string4) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM print_order where reprint_orderno = '" + string4 + "' and tableno!='Home Delivery' and tableno!='Parcel' and date between '" + string2 + "' and '" + string3 + "'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getReportForAllTablesTotal(String string2, String string3, String string4) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT TOTAL(item_prize) as total_amt, TOTAL(other_charges) as total_other_charges, TOTAL(discount) as discount FROM print_order where reprint_orderno = '" + string4 + "' and tableno!='Parcel' and tableno!='Home Delivery' and date between '" + string2 + "' and '" + string3 + "'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getReportFormItemName(String string2, String string3, String string4, String string5) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM print_order where item_descr='" + string2 + "' and reprint_orderno = '" + string5 + "' and date between '" + string3 + "' and '" + string4 + "'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getReportFormItemNameTotal(String string2, String string3, String string4, String string5) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT total(item_prize) as total_amt FROM print_order where item_descr='" + string2 + "' and reprint_orderno = '" + string5 + "' and date between '" + string3 + "' and '" + string4 + "'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getReportFormTableNo(String string2, String string3, String string4, String string5) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM print_order where reprint_orderno = '" + string5 + "' and tableno='" + string2 + "' and date between '" + string3 + "' and '" + string4 + "'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getReportFormTableNoAndItemName(String string2, String string3, String string4, String string5, String string6) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM print_order where reprint_orderno = '" + string6 + "' and item_descr='" + string2 + "' and tableno='" + string3 + "' and date between '" + string4 + "' and '" + string5 + "'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getReportFormTableNoAndItemNameTotal(String string2, String string3, String string4, String string5, String string6) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT TOTAL(item_prize) as total_amt, TOTAL(other_charges) as total_other_charges, TOTAL(discount) as discount FROM print_order where reprint_orderno = '" + string6 + "' and item_descr='" + string2 + "' and tableno='" + string3 + "' and date between '" + string4 + "' and '" + string5 + "'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getReportFormTableNoTotal(String string2, String string3, String string4, String string5) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT TOTAL(item_prize) as total_amt, TOTAL(other_charges) as total_other_charges, TOTAL(discount) as discount FROM print_order where reprint_orderno = '" + string5 + "' and tableno='" + string2 + "' and date between '" + string3 + "' and '" + string4 + "'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getReprintOrdersDetails(String string2, String string3, String string4) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT reprint_orderno, orderno FROM print_order where reprint_orderno != '" + string4 + "' and date between '" + string2 + "' and '" + string3 + "' ", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getReprintReport(String string2, String string3, String string4) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT TOTAL(item_prize) as total, MAX(date) FROM print_order where orderno = '" + string2 + "' and date between '" + string3 + "' and '" + string4 + "' ", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getRestoDetails() {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM setting", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getSubmittedOrder(String string2) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT distinct orderno FROM payed_orders where tableno='" + string2 + "' and print_flag='no'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getTableNo() {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT tableno FROM table_master order by tableno", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getTaxInfo() {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM tax_master", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getTotalCrAmount(String string2) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT TOTAL(cr_amount) FROM credit_amount where date='" + string2 + "'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor getTotalExpense(String string2, String string3) {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT TOTAL(cr_amount) as total_amt FROM credit_amount where date between '" + string2 + "' and '" + string3 + "'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void getUserRoles(String string2) {
        boolean bl = true;
        if (string2.equalsIgnoreCase("user1")) {
            try {
                Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM user_rights where userid = '" + string2 + "'", null);
                if (cursor == null || cursor.getCount() <= 0) return;
                cursor.moveToFirst();
                RolesModel.GetInstance();
                RolesModel.set_loggedInUser(string2);
                RolesModel.GetInstance();
                boolean bl2 = cursor.getInt(1) != 0 ? bl : false;
                RolesModel.set_addItem(bl2);
                RolesModel.GetInstance();
                boolean bl3 = cursor.getInt(2) != 0 ? bl : false;
                RolesModel.set_editItem(bl3);
                RolesModel.GetInstance();
                boolean bl4 = cursor.getInt(3) != 0 ? bl : false;
                RolesModel.set_deleteItem(bl4);
                RolesModel.GetInstance();
                boolean bl5 = cursor.getInt(4) != 0 ? bl : false;
                RolesModel.set_newOrder(bl5);
                RolesModel.GetInstance();
                boolean bl6 = cursor.getInt(5) != 0 ? bl : false;
                RolesModel.set_editOrder(bl6);
                RolesModel.GetInstance();
                boolean bl7 = cursor.getInt(6) != 0 ? bl : false;
                RolesModel.set_reprintOrder(bl7);
                RolesModel.GetInstance();
                boolean bl8 = cursor.getInt(7) != 0 ? bl : false;
                RolesModel.set_reports(bl8);
                RolesModel.GetInstance();
                boolean bl9 = cursor.getInt(8) != 0 ? bl : false;
                RolesModel.set_dayEnd(bl9);
                RolesModel.GetInstance();
                boolean bl10 = cursor.getInt(9) != 0 ? bl : false;
                RolesModel.set_changePassword(bl10);
                RolesModel.GetInstance();
                boolean bl11 = cursor.getInt(10) != 0 ? bl : false;
                RolesModel.set_debitAmount(bl11);
                RolesModel.GetInstance();
                boolean bl12 = cursor.getInt(11) != 0 ? bl : false;
                RolesModel.set_updateSetting(bl12);
                RolesModel.GetInstance();
                boolean bl13 = cursor.getInt(12) != 0 ? bl : false;
                RolesModel.set_exportDB(bl13);
                RolesModel.GetInstance();
                if (cursor.getInt(13) == 0) {
                    bl = false;
                }
                RolesModel.set_accessRights(bl);
                return;
            }
            catch (Exception exception) {
                UtilityHelper.LogException(exception);
                return;
            }
        } else {
            if (!string2.equalsIgnoreCase("admin")) return;
            {
                RolesModel.GetInstance();
                RolesModel.set_loggedInUser(string2);
                RolesModel.GetInstance();
                RolesModel.set_addItem(bl);
                RolesModel.GetInstance();
                RolesModel.set_editItem(bl);
                RolesModel.GetInstance();
                RolesModel.set_deleteItem(bl);
                RolesModel.GetInstance();
                RolesModel.set_newOrder(bl);
                RolesModel.GetInstance();
                RolesModel.set_editOrder(bl);
                RolesModel.GetInstance();
                RolesModel.set_reprintOrder(bl);
                RolesModel.GetInstance();
                RolesModel.set_reports(bl);
                RolesModel.GetInstance();
                RolesModel.set_dayEnd(bl);
                RolesModel.GetInstance();
                RolesModel.set_changePassword(bl);
                RolesModel.GetInstance();
                RolesModel.set_debitAmount(bl);
                RolesModel.GetInstance();
                RolesModel.set_updateSetting(bl);
                RolesModel.GetInstance();
                RolesModel.set_exportDB(bl);
                RolesModel.GetInstance();
                RolesModel.set_accessRights(bl);
                return;
            }
        }
    }

    public Cursor geteditHDOrder() {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT distinct orderno FROM payed_orders where reprint_orderno='0000' and tableno='Home Delivery' and print_flag='yes'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public Cursor geteditParcelOrder() {
        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT distinct orderno FROM payed_orders where reprint_orderno='0000' and tableno='Parcel' and print_flag='yes'", null);
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    public boolean isAppDeactive() {
        try {
            int n = this.getReadableDatabase().rawQuery("SELECT * from register_app where deviceID = '" + this.deviceID + "' AND status = 2", null).getCount();
            if (n > 0) {
                return true;
            }
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
        }
        return false;
    }

    public Cursor loginDetails(String string2, String string3) {
        Cursor cursor;
        block3 : {
            Cursor cursor2;
            try {
                boolean bl = this.isAppDeactive();
                cursor = null;
                if (bl) break block3;
            }
            catch (Exception exception) {
                UtilityHelper.LogException(exception);
                return null;
            }
            cursor = cursor2 = this.getReadableDatabase().rawQuery("SELECT em.emp_name,um.user_active FROM user_master um inner join emp_master em on um.emp_code = em.emp_code where um.userid='" + string2 + "' and um.password='" + string3 + "'", null);
        }
        return cursor;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS user_master(userid VARCHAR primary key,emp_code VARCHAR,user_active VARCHAR,password VARCHAR,created_by VARCHAR null,created_date VARCHAR null)");
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS emp_master(emp_code VARCHAR primary key,emp_name VARCHAR,address VARCHAR null,city VARCHAR null,state VARCHAR null,pincode VARCHAR null,phone_no VARCHAR null,mobile_no VARCHAR,birth_date VARCHAR null,ID_proof_descr VARCHAR null,addr_proof_descr VARCHAR null,reffered_by VARCHAR null, emp_startdate VARCHAR null, emp_enddate VARCHAR null,emp_type VARCHAR null, created_by VARCHAR null,created_date VARCHAR null)");
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS emp_type_master(emp_code VARCHAR primary key,emp_type VARCHAR, login_applicable VARCHAR)");
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS user_rights(userid VARCHAR,additem int,edititem int,deleteitem int,neworder int,editorder int,reprint int,reports int,dayend int,changepassword int,debitamount int, updatesettings int, exportdb int, accessrights int)");
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS table_master(tableno integer primary key,table_descr VARCHAR null,table_active VARCHAR,created_by VARCHAR null,created_date VARCHAR null)");
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS item_master(item_code integer primary_key,item_descr VARCHAR,item_prize VARCHAR,item_active VARCHAR,created_by VARCHAR null,created_date VARCHAR null,update_by VARCHAR null,updated_date VARCHAR null)");
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS payed_orders(tableno varchar,orderno VARCHAR,item_descr VARCHAR,item_prize VARCHAR,quantity integer,userid VARCHAR null,cust_name varchar null,address varchar null,phone_no varchar null,discount varchar null,other_charges varchar null,print_flag VARCHAR,date VARCHAR null,payment_type varchar null,reprint_orderno VARCHAR null)");
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS payed_orders_history(tableno varchar,orderno VARCHAR,item_descr VARCHAR,item_prize VARCHAR,quantity integer,userid VARCHAR null,cust_name varchar null,address varchar null,phone_no varchar null,discount varchar null,other_charges varchar null,print_flag VARCHAR,date VARCHAR null,payment_type varchar null,reprint_orderno VARCHAR null)");
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS print_order(tableno varchar,orderno VARCHAR,item_descr VARCHAR,item_prize VARCHAR,quantity integer,userid VARCHAR null,cust_name varchar null,address varchar null,phone_no varchar null,discount varchar null,other_charges varchar null,date VARCHAR null,payment_type varchar null,reprint_orderno VARCHAR null)");
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS print_orders_history(tableno varchar,orderno VARCHAR,item_descr VARCHAR,item_prize VARCHAR,quantity integer,userid VARCHAR null,cust_name varchar null,address varchar null,phone_no varchar null,discount varchar null,other_charges varchar null,date VARCHAR null,payment_type varchar null,reprint_orderno VARCHAR null)");
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS tax_master(taxcode integer primary key,tax_descr VARCHAR null,tax_perct integer null,tax_fixamt integer null,taxapplicable VARCHAR null,created_by VARCHAR null,created_date VARCHAR null,update_by VARCHAR null,updated_date VARCHAR null)");
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS system_parameter(businessdate VARCHAR)");
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS setting(restaurant_name VARCHAR primary key,address VARCHAR,phone_no VARCHAR null,website VARCHAR null,cgst VARCHAR null,sgst VARCHAR null,service_charge VARCHAR null,opening_bal VARCHAR null,gstn_number VARCHAR null)");
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS credit_amount(srno integer primary key,date VARCHAR null,cr_amount VARCHAR,description VARCHAR null)");
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS register_app(deviceID VARCHAR,uniqueID VARCHAR,status int)");
            Cursor cursor = sQLiteDatabase.rawQuery("select * from system_parameter", null);
            if (cursor.getCount() < 1) {
                String string2 = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
                sQLiteDatabase.execSQL("INSERT INTO system_parameter VALUES('" + string2 + "')");
            }
            cursor.close();
            Cursor cursor2 = sQLiteDatabase.rawQuery("select * from tax_master", null);
            if (cursor2.getCount() < 1) {
                sQLiteDatabase.execSQL("INSERT INTO tax_master VALUES(1,'VAT','5',null,'yes',null,null,null,null)");
            }
            cursor2.close();
            Cursor cursor3 = sQLiteDatabase.rawQuery("SELECT * FROM user_master", null);
            int n = cursor3.getCount();
            Cursor cursor4 = sQLiteDatabase.rawQuery("SELECT * FROM emp_master", null);
            int n2 = cursor4.getCount();
            if (n < 1 && n2 < 1) {
                String string3 = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
                sQLiteDatabase.execSQL("INSERT INTO user_master VALUES('admin','000','yes','admin123',null,'" + string3 + "')");
                sQLiteDatabase.execSQL("INSERT INTO emp_master VALUES('000','admin','abc','abc','mah','410002','121212','98989898','10/10/2015',null,null,null,null,null,null,null,null)");
                sQLiteDatabase.execSQL("INSERT INTO user_master VALUES('user1','001','yes','user123',null,'" + string3 + "')");
                sQLiteDatabase.execSQL("INSERT INTO emp_master VALUES('001','user1','abc','abc','mah','410002','121212','98989898','10/10/2015',null,null,null,null,null,null,null,null)");
                sQLiteDatabase.execSQL("INSERT INTO user_rights VALUES('user1',1,1,0,1,1,1,0,1,0,1,1,0,0)");
            }
            cursor3.close();
            cursor4.close();
            this.GetToken();
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int n, int n2) {
    }

    public Cursor removeUserDetails() {
        try {
            this.getReadableDatabase().execSQL("delete from user_master where userid !='admin'");
            return null;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    protected void sendActivationConfirmationEmail(AppActivationModel appActivationModel, String string2) {
        try {
            String string3 = "Confirmed Activation for Restaurant : " + appActivationModel.get_resturantName().toUpperCase();
            String string4 = "Hi,<br><br>" + "Device ID : " + this.deviceID + "<br><br>";
            String string5 = string4 + "Key : <b>" + string2 + "</b><br><br>";
            String string6 = string5 + "Owner Name : " + appActivationModel.get_ownerName() + "<br><br>";
            String string7 = string6 + "Owner Mobile No : " + appActivationModel.get_mobileNo() + "<br><br>";
            String string8 = string7 + "Email Id : " + appActivationModel.get_emailId() + "<br><br>";
            String string9 = string8 + "Address : " + appActivationModel.get_address() + "<br><br>";
            String string10 = string9 + "Token : " + this.GetToken() + "<br><br>";
            String string11 = string10 + "Thanks.<br>";
            this.sendMail("rohit46.karande@gmail.com", string3, string11 + "NeoAntara.");
            return;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return;
        }
    }

    protected void sendEmail() {
        try {
            String string2 = "Hi,<br><br>" + "Device ID : " + this.deviceID + "<br><br>";
            String string3 = string2 + "Token : " + this.GetToken() + "<br><br>";
            String string4 = string3 + "Thanks.<br>";
            this.sendMail("rohit46.karande@gmail.com", "Restaurant Installed", string4 + "NeoAntara.");
            return;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return;
        }
    }

    protected void sendEmail(String string2, String string3, String string4, String string5, String string6, String string7, String string8) {
        try {
            String string9 = "Activation KEY for Restaurant : " + string4.toUpperCase();
            String string10 = "Hi,<br><br>" + "Device ID : " + string2 + "<br><br>";
            String string11 = string10 + "Key : <b>" + string3 + "</b><br><br>";
            String string12 = string11 + "Restaurant Name : " + string4 + "<br><br>";
            String string13 = string12 + "Owner Name : " + string5 + "<br><br>";
            String string14 = string13 + "Owner Mobile No : " + string6 + "<br><br>";
            String string15 = string14 + "Email Id : " + string8 + "<br><br>";
            String string16 = string15 + "Address : " + string7 + "<br><br>";
            String string17 = string16 + "Token : " + this.GetToken() + "<br><br>";
            String string18 = string17 + "Thanks.<br>";
            this.sendMail("rohit46.karande@gmail.com", string9, string18 + "NeoAntara.");
            String string19 = "Hello " + string5 + "<br><br>";
            String string20 = string19 + "Thank you for installing our app.<br><br>";
            String string21 = string20 + "Key : <b>" + string3 + "</b><br>";
            String string22 = string21 + "Please do not share the key with anyone.<br>";
            String string23 = string22 + "Write to us or call un on <b>9527919159</b>, if you have any query, problem or suggestion.<br><br>";
            String string24 = string23 + "To Login, use following username and password<br>";
            String string25 = string24 + "Primary/Main user - Administartor :<br>";
            String string26 = string25 + "Username : <b>admin</b><br>";
            String string27 = string26 + "Password : <b>admin123</b><br><br>";
            String string28 = string27 + "Secondary User - Having limited access rights :<br>";
            String string29 = string28 + "Username : <b>user1</b><br>";
            String string30 = string29 + "Password : <b>user123</b><br><br>";
            String string31 = string30 + "How to use App?<br>";
            String string32 = string31 + "<a href='https://drive.google.com/file/d/1oKYF4BdyY3xKqGVSXaRZkWUSgbTOGAhc/view?usp=sharing'>Click here to Download documentation</a><br><br>";
            String string33 = string32 + "<b>Sincerely,</b><br>";
            this.sendMailToCustomer("rohit46.karande@gmail.com", string9, string33 + "<b>NeoAntara.</b><br>", string8);
            return;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return;
        }
    }

    protected void sendEmailWithAttachment(String string2, String string3, String string4) {
        try {
            this.sendMailWithAttachment("rohit46.karande@gmail.com", "DB Backup file : " + string3, "Hi,\nYour Database backup. \nThanks.", string4, string2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    public void sendEmail_Settings(String string2, String string3, String string4, String string5, String string6) {
        try {
            String string7 = "Settings for Restaurant : " + string2.toUpperCase();
            String string8 = "Hi,<br><br>" + "Restaurant Name : " + string2 + "<br><br>";
            String string9 = string8 + "Phone No : " + string3 + "<br><br>";
            String string10 = string9 + "Address : " + string4 + "<br><br>";
            String string11 = string10 + "Website : " + string5 + "<br><br>";
            String string12 = string11 + "GSTN No : " + string6 + "<br><br>";
            String string13 = string12 + "Token : " + this.GetToken() + "<br><br>";
            String string14 = string13 + "Thanks.<br>";
            this.sendMail("rohit46.karande@gmail.com", string7, string14 + "NeoAntara.");
            return;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return;
        }
    }

    public Cursor transferPrintordertohistory(String string2) {
        Cursor cursor = null;
        SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();
        cursor = sQLiteDatabase.rawQuery("select * from print_order where date!='" + string2 + "'", null);
        cursor.moveToFirst();
        int n = 0;
        do {
            if (n >= cursor.getCount()) break;
            int n2 = Integer.parseInt((String)cursor.getString(0));
            String string3 = cursor.getString(1);
            String string4 = cursor.getString(2);
            String string5 = cursor.getString(3);
            int n3 = Integer.parseInt((String)cursor.getString(4));
            String string6 = cursor.getString(5);
            String string7 = cursor.getString(6);
            String string8 = cursor.getString(7);
            String string9 = cursor.getString(8);
            String string10 = cursor.getString(9);
            String string11 = cursor.getString(10);
            String string12 = cursor.getString(11);
            String string13 = cursor.getString(12);
            String string14 = cursor.getString(13);
            String string15 = cursor.getString(14);
            sQLiteDatabase.execSQL("INSERT INTO print_orders_history VALUES('" + n2 + "','" + string3 + "','" + string4 + "','" + string5 + "','" + n3 + "','" + string6 + "','" + string7 + "','" + string8 + "','" + string9 + "','" + string10 + "','" + string11 + "','" + string12 + "','" + string13 + "','" + string14 + "', '" + string15 + "')");
            cursor.moveToNext();
            ++n;
        } while (true);
        try {
            sQLiteDatabase.execSQL("delete from print_order where date!='" + string2 + "'");
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return cursor;
        }
    }

    public Cursor transferorderstohistory(String string2) {
        Cursor cursor = null;
        SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();
        cursor = sQLiteDatabase.rawQuery("select * from payed_orders where date!='" + string2 + "'", null);
        cursor.moveToFirst();
        int n = 0;
        do {
            if (n >= cursor.getCount()) break;
            String string3 = cursor.getString(0);
            String string4 = cursor.getString(1);
            String string5 = cursor.getString(2);
            String string6 = cursor.getString(3);
            int n2 = Integer.parseInt((String)cursor.getString(4));
            String string7 = cursor.getString(6);
            String string8 = cursor.getString(7);
            String string9 = cursor.getString(8);
            String string10 = cursor.getString(9);
            String string11 = cursor.getString(10);
            String string12 = cursor.getString(11);
            String string13 = cursor.getString(12);
            String string14 = cursor.getString(13);
            sQLiteDatabase.execSQL("INSERT INTO payed_orders_history VALUES('" + string3 + "','" + string4 + "','" + string5 + "','" + string6 + "','" + n2 + "',null,'" + string7 + "','" + string8 + "','" + string9 + "','" + string10 + "','" + string11 + "',null,'" + string12 + "','" + string13 + "','" + string14 + "')");
            cursor.moveToNext();
            ++n;
        } while (true);
        try {
            sQLiteDatabase.execSQL("delete from payed_orders where date!='" + string2 + "'");
            return cursor;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return cursor;
        }
    }

    public void updateUserRights(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11, int n12) {
        SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();
        try {
            String string2 = "UPDATE user_rights set additem= '" + n + "',edititem= '" + n2 + "',deleteitem= '" + n3 + "'";
            String string3 = string2 + ",neworder = '" + n4 + "',editorder = '" + n5 + "', reprint = '" + n6 + "', reports = '" + n7 + "'";
            String string4 = string3 + ",dayend = '" + n8 + "',changepassword = '" + n9 + "', debitamount = '" + n10 + "'";
            String string5 = string4 + ",updatesettings = '" + n11 + "', exportdb='" + n12 + "'";
            sQLiteDatabase.execSQL(string5 + "where userid = 'user1'");
            return;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return;
        }
    }

    public Cursor updatebusinessdate(String string2) {
        try {
            this.getReadableDatabase().execSQL("update system_parameter set businessdate='" + string2 + "'");
            return null;
        }
        catch (Exception exception) {
            UtilityHelper.LogException(exception);
            return null;
        }
    }

    private class SendMailTask
    extends AsyncTask<Message, Void, Void> {
        private SendMailTask() {
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        protected /* varargs */ Void doInBackground(Message ... arrmessage) {
            try {
                Transport.send((Message)arrmessage[0]);
                do {
                    return null;
                    break;
                } while (true);
            }
            catch (MessagingException messagingException) {
                messagingException.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(Void void_) {
            super.onPostExecute((Object)void_);
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

}

