/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.util.Calendar
 *  java.util.Date
 *  java.util.GregorianCalendar
 *  javax.mail.Address
 *  javax.mail.Flags
 *  javax.mail.Flags$Flag
 *  javax.mail.Message
 *  javax.mail.Message$RecipientType
 *  javax.mail.search.AddressTerm
 *  javax.mail.search.AndTerm
 *  javax.mail.search.BodyTerm
 *  javax.mail.search.DateTerm
 *  javax.mail.search.FlagTerm
 *  javax.mail.search.FromStringTerm
 *  javax.mail.search.FromTerm
 *  javax.mail.search.HeaderTerm
 *  javax.mail.search.MessageIDTerm
 *  javax.mail.search.NotTerm
 *  javax.mail.search.OrTerm
 *  javax.mail.search.ReceivedDateTerm
 *  javax.mail.search.RecipientStringTerm
 *  javax.mail.search.RecipientTerm
 *  javax.mail.search.SearchException
 *  javax.mail.search.SearchTerm
 *  javax.mail.search.SentDateTerm
 *  javax.mail.search.SizeTerm
 *  javax.mail.search.StringTerm
 *  javax.mail.search.SubjectTerm
 */
package com.sun.mail.imap.protocol;

import com.sun.mail.iap.Argument;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.search.AddressTerm;
import javax.mail.search.AndTerm;
import javax.mail.search.BodyTerm;
import javax.mail.search.DateTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.FromStringTerm;
import javax.mail.search.FromTerm;
import javax.mail.search.HeaderTerm;
import javax.mail.search.MessageIDTerm;
import javax.mail.search.NotTerm;
import javax.mail.search.OrTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.RecipientStringTerm;
import javax.mail.search.RecipientTerm;
import javax.mail.search.SearchException;
import javax.mail.search.SearchTerm;
import javax.mail.search.SentDateTerm;
import javax.mail.search.SizeTerm;
import javax.mail.search.StringTerm;
import javax.mail.search.SubjectTerm;

class SearchSequence {
    private static Calendar cal;
    private static String[] monthTable;

    static {
        monthTable = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        cal = new GregorianCalendar();
    }

    SearchSequence() {
    }

    private static Argument and(AndTerm andTerm, String string2) throws SearchException, IOException {
        SearchTerm[] arrsearchTerm = andTerm.getTerms();
        Argument argument = SearchSequence.generateSequence(arrsearchTerm[0], string2);
        int n = 1;
        while (n < arrsearchTerm.length) {
            argument.append(SearchSequence.generateSequence(arrsearchTerm[n], string2));
            ++n;
        }
        return argument;
    }

    private static Argument body(BodyTerm bodyTerm, String string2) throws SearchException, IOException {
        Argument argument = new Argument();
        argument.writeAtom("BODY");
        argument.writeString(bodyTerm.getPattern(), string2);
        return argument;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static Argument flag(FlagTerm flagTerm) throws SearchException {
        boolean bl = flagTerm.getTestSet();
        Argument argument = new Argument();
        Flags flags = flagTerm.getFlags();
        Flags.Flag[] arrflag = flags.getSystemFlags();
        String[] arrstring = flags.getUserFlags();
        if (arrflag.length == 0 && arrstring.length == 0) {
            throw new SearchException("Invalid FlagTerm");
        }
        int n = 0;
        do {
            if (n >= arrflag.length) break;
            if (arrflag[n] == Flags.Flag.DELETED) {
                String string2 = bl ? "DELETED" : "UNDELETED";
                argument.writeAtom(string2);
            } else if (arrflag[n] == Flags.Flag.ANSWERED) {
                String string3 = bl ? "ANSWERED" : "UNANSWERED";
                argument.writeAtom(string3);
            } else if (arrflag[n] == Flags.Flag.DRAFT) {
                String string4 = bl ? "DRAFT" : "UNDRAFT";
                argument.writeAtom(string4);
            } else if (arrflag[n] == Flags.Flag.FLAGGED) {
                String string5 = bl ? "FLAGGED" : "UNFLAGGED";
                argument.writeAtom(string5);
            } else if (arrflag[n] == Flags.Flag.RECENT) {
                String string6 = bl ? "RECENT" : "OLD";
                argument.writeAtom(string6);
            } else if (arrflag[n] == Flags.Flag.SEEN) {
                String string7 = bl ? "SEEN" : "UNSEEN";
                argument.writeAtom(string7);
            }
            ++n;
        } while (true);
        int n2 = 0;
        while (n2 < arrstring.length) {
            String string8 = bl ? "KEYWORD" : "UNKEYWORD";
            argument.writeAtom(string8);
            argument.writeAtom(arrstring[n2]);
            ++n2;
        }
        return argument;
    }

    private static Argument from(String string2, String string3) throws SearchException, IOException {
        Argument argument = new Argument();
        argument.writeAtom("FROM");
        argument.writeString(string2, string3);
        return argument;
    }

    static Argument generateSequence(SearchTerm searchTerm, String string2) throws SearchException, IOException {
        if (searchTerm instanceof AndTerm) {
            return SearchSequence.and((AndTerm)searchTerm, string2);
        }
        if (searchTerm instanceof OrTerm) {
            return SearchSequence.or((OrTerm)searchTerm, string2);
        }
        if (searchTerm instanceof NotTerm) {
            return SearchSequence.not((NotTerm)searchTerm, string2);
        }
        if (searchTerm instanceof HeaderTerm) {
            return SearchSequence.header((HeaderTerm)searchTerm, string2);
        }
        if (searchTerm instanceof FlagTerm) {
            return SearchSequence.flag((FlagTerm)searchTerm);
        }
        if (searchTerm instanceof FromTerm) {
            return SearchSequence.from(((FromTerm)searchTerm).getAddress().toString(), string2);
        }
        if (searchTerm instanceof FromStringTerm) {
            return SearchSequence.from(((FromStringTerm)searchTerm).getPattern(), string2);
        }
        if (searchTerm instanceof RecipientTerm) {
            RecipientTerm recipientTerm = (RecipientTerm)searchTerm;
            return SearchSequence.recipient(recipientTerm.getRecipientType(), recipientTerm.getAddress().toString(), string2);
        }
        if (searchTerm instanceof RecipientStringTerm) {
            RecipientStringTerm recipientStringTerm = (RecipientStringTerm)searchTerm;
            return SearchSequence.recipient(recipientStringTerm.getRecipientType(), recipientStringTerm.getPattern(), string2);
        }
        if (searchTerm instanceof SubjectTerm) {
            return SearchSequence.subject((SubjectTerm)searchTerm, string2);
        }
        if (searchTerm instanceof BodyTerm) {
            return SearchSequence.body((BodyTerm)searchTerm, string2);
        }
        if (searchTerm instanceof SizeTerm) {
            return SearchSequence.size((SizeTerm)searchTerm);
        }
        if (searchTerm instanceof SentDateTerm) {
            return SearchSequence.sentdate((DateTerm)((SentDateTerm)searchTerm));
        }
        if (searchTerm instanceof ReceivedDateTerm) {
            return SearchSequence.receiveddate((DateTerm)((ReceivedDateTerm)searchTerm));
        }
        if (searchTerm instanceof MessageIDTerm) {
            return SearchSequence.messageid((MessageIDTerm)searchTerm, string2);
        }
        throw new SearchException("Search too complex");
    }

    private static Argument header(HeaderTerm headerTerm, String string2) throws SearchException, IOException {
        Argument argument = new Argument();
        argument.writeAtom("HEADER");
        argument.writeString(headerTerm.getHeaderName());
        argument.writeString(headerTerm.getPattern(), string2);
        return argument;
    }

    private static boolean isAscii(String string2) {
        int n = string2.length();
        int n2 = 0;
        while (n2 < n) {
            if (string2.charAt(n2) > '') {
                return false;
            }
            ++n2;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    static boolean isAscii(SearchTerm searchTerm) {
        if (!(searchTerm instanceof AndTerm) && !(searchTerm instanceof OrTerm)) {
            if (searchTerm instanceof NotTerm) {
                return SearchSequence.isAscii(((NotTerm)searchTerm).getTerm());
            }
            if (searchTerm instanceof StringTerm) {
                return SearchSequence.isAscii(((StringTerm)searchTerm).getPattern());
            }
            if (!(searchTerm instanceof AddressTerm)) return true;
            return SearchSequence.isAscii(((AddressTerm)searchTerm).getAddress().toString());
        } else {
            SearchTerm[] arrsearchTerm = searchTerm instanceof AndTerm ? ((AndTerm)searchTerm).getTerms() : ((OrTerm)searchTerm).getTerms();
            for (int i = 0; i < arrsearchTerm.length; ++i) {
                if (SearchSequence.isAscii(arrsearchTerm[i])) continue;
                return false;
            }
        }
        return true;
    }

    private static Argument messageid(MessageIDTerm messageIDTerm, String string2) throws SearchException, IOException {
        Argument argument = new Argument();
        argument.writeAtom("HEADER");
        argument.writeString("Message-ID");
        argument.writeString(messageIDTerm.getPattern(), string2);
        return argument;
    }

    private static Argument not(NotTerm notTerm, String string2) throws SearchException, IOException {
        Argument argument = new Argument();
        argument.writeAtom("NOT");
        SearchTerm searchTerm = notTerm.getTerm();
        if (searchTerm instanceof AndTerm || searchTerm instanceof FlagTerm) {
            argument.writeArgument(SearchSequence.generateSequence(searchTerm, string2));
            return argument;
        }
        argument.append(SearchSequence.generateSequence(searchTerm, string2));
        return argument;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static Argument or(OrTerm var0, String var1_1) throws SearchException, IOException {
        var2_2 = var0.getTerms();
        if (var2_2.length <= 2) ** GOTO lbl8
        var4_3 = var2_2[0];
        var5_4 = 1;
        do {
            block8 : {
                if (var5_4 < var2_2.length) break block8;
                var2_2 = ((OrTerm)var4_3).getTerms();
lbl8: // 2 sources:
                var3_6 = new Argument();
                if (var2_2.length > 1) {
                    var3_6.writeAtom("OR");
                }
                if (var2_2[0] instanceof AndTerm || var2_2[0] instanceof FlagTerm) {
                    var3_6.writeArgument(SearchSequence.generateSequence(var2_2[0], var1_1));
                } else {
                    var3_6.append(SearchSequence.generateSequence(var2_2[0], var1_1));
                }
                if (var2_2.length <= 1) return var3_6;
                if (!(var2_2[1] instanceof AndTerm) && !(var2_2[1] instanceof FlagTerm)) {
                    var3_6.append(SearchSequence.generateSequence(var2_2[1], var1_1));
                    return var3_6;
                }
                var3_6.writeArgument(SearchSequence.generateSequence(var2_2[1], var1_1));
                return var3_6;
            }
            var6_5 = new OrTerm(var4_3, var2_2[var5_4]);
            ++var5_4;
            var4_3 = var6_5;
        } while (true);
    }

    private static Argument receiveddate(DateTerm dateTerm) throws SearchException {
        Argument argument = new Argument();
        String string2 = SearchSequence.toIMAPDate(dateTerm.getDate());
        switch (dateTerm.getComparison()) {
            default: {
                throw new SearchException("Cannot handle Date Comparison");
            }
            case 5: {
                argument.writeAtom("SINCE " + string2);
                return argument;
            }
            case 3: {
                argument.writeAtom("ON " + string2);
                return argument;
            }
            case 2: {
                argument.writeAtom("BEFORE " + string2);
                return argument;
            }
            case 6: {
                argument.writeAtom("OR SINCE " + string2 + " ON " + string2);
                return argument;
            }
            case 1: {
                argument.writeAtom("OR BEFORE " + string2 + " ON " + string2);
                return argument;
            }
            case 4: 
        }
        argument.writeAtom("NOT ON " + string2);
        return argument;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static Argument recipient(Message.RecipientType recipientType, String string2, String string3) throws SearchException, IOException {
        Argument argument = new Argument();
        if (recipientType == Message.RecipientType.TO) {
            argument.writeAtom("TO");
        } else if (recipientType == Message.RecipientType.CC) {
            argument.writeAtom("CC");
        } else {
            if (recipientType != Message.RecipientType.BCC) {
                throw new SearchException("Illegal Recipient type");
            }
            argument.writeAtom("BCC");
        }
        argument.writeString(string2, string3);
        return argument;
    }

    private static Argument sentdate(DateTerm dateTerm) throws SearchException {
        Argument argument = new Argument();
        String string2 = SearchSequence.toIMAPDate(dateTerm.getDate());
        switch (dateTerm.getComparison()) {
            default: {
                throw new SearchException("Cannot handle Date Comparison");
            }
            case 5: {
                argument.writeAtom("SENTSINCE " + string2);
                return argument;
            }
            case 3: {
                argument.writeAtom("SENTON " + string2);
                return argument;
            }
            case 2: {
                argument.writeAtom("SENTBEFORE " + string2);
                return argument;
            }
            case 6: {
                argument.writeAtom("OR SENTSINCE " + string2 + " SENTON " + string2);
                return argument;
            }
            case 1: {
                argument.writeAtom("OR SENTBEFORE " + string2 + " SENTON " + string2);
                return argument;
            }
            case 4: 
        }
        argument.writeAtom("NOT SENTON " + string2);
        return argument;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static Argument size(SizeTerm sizeTerm) throws SearchException {
        Argument argument = new Argument();
        switch (sizeTerm.getComparison()) {
            default: {
                throw new SearchException("Cannot handle Comparison");
            }
            case 5: {
                argument.writeAtom("LARGER");
                break;
            }
            case 2: {
                argument.writeAtom("SMALLER");
            }
        }
        argument.writeNumber(sizeTerm.getNumber());
        return argument;
    }

    private static Argument subject(SubjectTerm subjectTerm, String string2) throws SearchException, IOException {
        Argument argument = new Argument();
        argument.writeAtom("SUBJECT");
        argument.writeString(subjectTerm.getPattern(), string2);
        return argument;
    }

    private static String toIMAPDate(Date date) {
        StringBuffer stringBuffer = new StringBuffer();
        cal.setTime(date);
        stringBuffer.append(cal.get(5)).append("-");
        stringBuffer.append(monthTable[cal.get(2)]).append('-');
        stringBuffer.append(cal.get(1));
        return stringBuffer.toString();
    }
}

