// ----------------------------------------------------------------------------
// Copyright 2006-2008, Martin D. Flynn
// All rights reserved
// ----------------------------------------------------------------------------
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
// http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
// ----------------------------------------------------------------------------
// Description:
//  This class provides many String based utilities.
// ----------------------------------------------------------------------------
// Change History:
//  2006/03/26  Martin D. Flynn
//     -Initial release
//  2006/04/11  Martin D. Flynn
//     -Decimal formatting now explicitly uses "Locale.US" symbols.  This fixes 
//      a problem that caused values such as Latitude "37,1234" from appearing 
//      in CSV files.
//  2006/05/11  Martin D. Flynn
//     -Replaced deprecated 'Character.isSpace' with 'Character.isWhitespace'
//  2006/06/30  Martin D. Flynn
//     -Repackaged to "org.opengts.util"
//  2007/02/25  Martin D. Flynn
//     -Added 'isDouble', 'isFloat', 'isInt', 'isLong', 'isBoolean'
//     -Fixed negative case in 'parseBoolean'.
//     -Removed "on"/"off" from valid boolean string values.
//  2007/07/27  Martin D. Flynn
//     -Added support for encoding primitive arrays
//  2007/09/16  Martin D. Flynn
//     -Fixed 'insertKeyValues' to properly account for the length of the beginning
//      and ending delimiter lengths.
//  2008/02/04  Martin D. Flynn
//     -Added 'parseFloat'/'parseDouble' method for decoding 32-bit and 64-bit 
//      IEEE 754 floating-point values.
//     -Validate 'blockLen' argument in 'formatHexString'.
// ----------------------------------------------------------------------------
package com.digitrinity.parser.dateutil;

import java.awt.Dimension;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class StringTools
{
    
    public static final String DEFAULT_CHARSET          = "ISO-8859-1";
    
    // ------------------------------------------------------------------------

    public static final char   BACKSPACE                = '\b';
    public static final char   FORM_FEED                = '\f';
    public static final char   NEW_LINE                 = '\n';
    public static final char   CARRIAGE_RETURN          = '\r';
    public static final char   TAB                      = '\t';
    
    public static final String WhitespaceChars          = " \t\b\f\r\n";

    public static final char   KeyValSeparatorChar      = '='; // "="
    public static final char   PropertySeparatorChar    = ' '; // ';'
    private static Logger logger = LogManager.getLogger(StringTools.class.getName());

    // ------------------------------------------------------------------------

    private static final String BOOLEAN_TRUE[]          = { "true" , "yes", "1" }; // must be lower-case
    
    private static final String BOOLEAN_FALSE[]         = { "false", "no" , "0" }; // must be lower-case

    // ------------------------------------------------------------------------
    
    public static char[] getChars(String s)
    {
        return (s != null)? s.toCharArray() : null;
    }

    public static char[] getChars(byte b[])
    {
        if (b != null) {
            char c[] = new char[b.length];
            for (int i = 0; i < b.length; i++) { 
                c[i] = (char)((int)b[i] & 0xFF); 
            }
            return c;
        } else {
            return null;
        }
    }
        
    // ------------------------------------------------------------------------
    
    public static byte[] getBytes(StringBuffer sb)
    {
        return (sb != null)? getBytes(sb.toString()) : null;
    }

    public static byte[] getBytes(String s)
    {
        if (s != null) {
            try {
                return s.getBytes(DEFAULT_CHARSET);
            } catch (UnsupportedEncodingException uce) {
                // will not occur
                logger.error("Charset not found: " + DEFAULT_CHARSET);
                return s.getBytes();
            }
        } else {
            return null;
        }
    }

    public static byte[] getBytes(char c[])
    {
        if (c != null) {
            byte b[] = new byte[c.length];
            for (int i = 0; i < c.length; i++) { 
                b[i] = (byte)c[i]; 
            }
            return b;
        } else {
            return null;
        }
    }

    // ------------------------------------------------------------------------

    /* replace unprintable characters and convert to String */
    public static String toStringValue(byte b[], char repUnp)
    {
        if (b != null) {
            byte p[] = new byte[b.length];
            System.arraycopy(b, 0, p, 0, b.length);
            for (int i = 0; i < p.length; i++) {
                if ((p[i] < 32) || (p[i] > 126)) {
                    p[i] = (byte)repUnp;
                }
            }
            return StringTools.toStringValue(p, 0, p.length);
        } else {
            return null;
        }
    }

    /* convert byte array to String */
    public static String toStringValue(byte b[])
    {
        return (b != null)? StringTools.toStringValue(b, 0, b.length) : null;
    }
    
    /* convert byte array to String */
    public static String toStringValue(byte b[], int ofs, int len)
    {
        if (b != null) {
            try {
                return new String(b, ofs, len, DEFAULT_CHARSET);
            } catch (Throwable t) {
                // This should NEVER occur (at least not because of the charset)
               logger.error("Byte=>String conversion error", t);
                return new String(b, ofs, len);
            }
        } else {
            return null; // what goes around ...
        }
    }
    
    /* convert character array to String */
    public static String toStringValue(char c[])
    {
        return new String(c);
    }
    
    // ------------------------------------------------------------------------

    private static final char ESCAPE_CHAR = '\\';
    
    public static String quoteString(String s)
    {
        return StringTools.quoteString(s, '\"');
    }

    public static String quoteString(String s, char q)
    {
        if (s == null) { s = ""; }
        int c = 0, len = s.length();
        char ch[] = new char[len];
        s.getChars(0, len, ch, 0);
        StringBuffer qsb = new StringBuffer();
        qsb.append(q);
        for (;c < len; c++) {
            if (ch[c] == q) {
                // TODO: option should be provided to literal quotes to be specified as
                // either "\\"" or "\"\""
                qsb.append(ESCAPE_CHAR).append(q);
            } else
            if (ch[c] == ESCAPE_CHAR) {
                qsb.append(ESCAPE_CHAR).append(ESCAPE_CHAR);
            } else
            if (ch[c] == '\n') {
                qsb.append(ESCAPE_CHAR).append('n');
            } else
            if (ch[c] == '\r') {
                qsb.append(ESCAPE_CHAR).append('r');
            } else
            if (ch[c] == '\t') {
                qsb.append(ESCAPE_CHAR).append('t');
            } else {
                qsb.append(ch[c]);
            }
        }
        qsb.append(q);
        return qsb.toString();
    }
    public static String quoteCSVString(String s)
    {
        if (s == null) { s = ""; }
        boolean needsQuotes = true; // (s.indexOf(',') >= 0);
        char q = '\"';
        if (s.indexOf(q) >= 0) {
            StringBuffer sb = new StringBuffer();
            if (needsQuotes) { sb.append(q); }
            for (int i = 0; i < s.length(); i++) {
                char ch = s.charAt(i);
                if (ch == q) {
                    sb.append("\"\"");
                } else {
                    sb.append(ch);
                }
            }
            if (needsQuotes) { sb.append(q); }
            return sb.toString();
        } else
        if (needsQuotes) {
            return "\"" + s + "\"";
        } else {
            return s;
        }
    }
    
    public static String encodeCSV(String d[], boolean checkTextQuote)
    {
        if (d != null) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < d.length; i++) {
                if (sb.length() > 0) { sb.append(","); }
                String v = (d[i] != null)? d[i] : "";
                String t = checkTextQuote? ("'" + v) : v;
                sb.append(StringTools.quoteCSVString(t));
            }
            return sb.toString();
        } else {
            return "";
        }
    }
    
    public static String encodeCSV(String d[])
    {
        return StringTools.encodeCSV(d, false);
    }
    
    // ------------------------------------------------------------------------

    public static String parseQuote(String s)
    {
        StringBuffer sb = new StringBuffer();
        StringTools.parseQuote(s.toCharArray(), 0, sb);
        return sb.toString();
    }
    
    public static int parseQuote(char ch[], int a, StringBuffer sb)
    {
        // Note on escaped octal values:
        //   Java supports octal values specified in Strings
        //   MySQL dump files do not support octal values in strings
        //   Thus, the interpretation of the value "\00" is ambiguous:
        //     - Java  == 0x00
        //     - MySQL == 0x0030
        // 'parseOctal' currently forced to false in order to support MySQL dump files.
        boolean parseOctal = false;
        
        /* validate args */
        int chLen = (ch != null)? ch.length : 0;
        if ((chLen <= 0) || (a < 0) || (a >= chLen)) {
            return a;
        }

        /* check first character to determine if value is quoted */
        if ((ch[a] == '\"') || (ch[a] == '\'')) { // quoted string
            char quote = ch[a]; // save type of quote

            /* skip past first quote */
            a++; // skip past first quote
            
            /* parse quoted string */
            for (; (a < chLen) && (ch[a] != quote); a++) {
                
                /* '\' escaped character? */
                if (((a + 1) < chLen) && (ch[a] == '\\')) {
                    a++; // skip past '\\'
                    
                    /* parse octal values */
                    if (parseOctal) {
                        // look for "\<octal>" values
                        int n = a;
                        for (;(n < chLen) && (n < (a + 3)) && (ch[n] >= '0') && (ch[n] <= '8'); n++);
                        if (n > a) {
                            String octalStr = new String(ch, a, (n - a));
                            try {
                                int octal = Integer.parseInt(octalStr, 8) & 0xFF;
                                sb.append((char)octal);
                            } catch (NumberFormatException nfe) {
                                // highly unlikely, since we pre-qualified the parsed value
                            	logger.error("Unable to parse octal: " , octalStr);
                                //sb.append("?");
                            }
                            a = n - 1; // reset a to last character of octal value
                            continue;
                        }
                    }
                    
                    /* check for specific filtered characters */
                    if (ch[a] == '0') { // "\0" (this is the only 'octal' value that is allowed
                        sb.append((char)0); 
                    } else
                    if (ch[a] == 'r') { // "\r"
                        sb.append('\r'); // ch[a]);
                    } else
                    if (ch[a] == 'n') { // "\n"
                        sb.append('\n'); // ch[a]);
                    } else
                    if (ch[a] == 't') { // "\t"
                        sb.append('\t'); // ch[a]);
                    } else {
                        sb.append(ch[a]);
                    }
                    
                } else {
                    
                    /* standard unfiltered characters */
                    sb.append(ch[a]);
                    
                }
            }
            
            /* skip past last quote */
            if (a < chLen) { a++; } // skip past last quote
            
        } else {
            
            /* break at first whitespace */
            for (;(a < chLen) && !Character.isWhitespace(ch[a]); a++) {
                sb.append(ch[a]);
            }
            
        }
        return a;
    }

    // ------------------------------------------------------------------------

    public static double parseDouble(byte b[], int ofs, boolean isBigEndian, double dft)
    {
        
        /* valid byte array */
        if ((b == null) || ((ofs + 8) > b.length)) {
            return dft;
        }
        
        /* parse IEEE 754 double */
        int i = ofs;
        long doubleLong = 0L;
        if (isBigEndian) {
            doubleLong = 
                (((long)b[i+0] & 0xFF) << (7*8)) + 
                (((long)b[i+1] & 0xFF) << (6*8)) + 
                (((long)b[i+2] & 0xFF) << (5*8)) + 
                (((long)b[i+3] & 0xFF) << (4*8)) + 
                (((long)b[i+4] & 0xFF) << (3*8)) + 
                (((long)b[i+5] & 0xFF) << (2*8)) + 
                (((long)b[i+6] & 0xFF) << (1*8)) + 
                 ((long)b[i+7] & 0xFF);
        } else {
            doubleLong = 
                (((long)b[i+7] & 0xFF) << (7*8)) + 
                (((long)b[i+6] & 0xFF) << (6*8)) + 
                (((long)b[i+5] & 0xFF) << (5*8)) + 
                (((long)b[i+4] & 0xFF) << (4*8)) + 
                (((long)b[i+3] & 0xFF) << (3*8)) + 
                (((long)b[i+2] & 0xFF) << (2*8)) + 
                (((long)b[i+1] & 0xFF) << (1*8)) + 
                 ((long)b[i+0] & 0xFF);
        }
        return Double.longBitsToDouble(doubleLong);

    }

    public static double parseDouble(Object data, double dft)
    {
        if (data == null) {
            return dft;
        } else
        if (data instanceof Number) {
            return ((Number)data).doubleValue();
        } else {
            return StringTools.parseDouble(data.toString(), dft);
        }
    }
    
    public static double parseDouble(String data, double dft)
    {
        return StringTools.parseDouble(new FilterNumber(data, Double.class), dft);
    }
    
    public static double parseDouble(FilterNumber num, double dft)
    {
        if ((num != null) && num.supportsType(Double.class)) {
            try {
                return Double.parseDouble(num.getValueString());
            } catch (NumberFormatException nfe) {
                // ignore
            }
        }
        return dft;
    }

    public static boolean isDouble(String data, boolean strict)
    {
        if ((data == null) || data.equals("")) {
            return false;
        } else {
            FilterNumber fn = new FilterNumber(data, Double.class);
            return fn.isValid(strict);
        }
    }

    // ------------------------------------------------------------------------

    public static float parseFloat(byte b[], int ofs, boolean isBigEndian, float dft)
    {
        
        /* valid byte array */
        if ((b == null) || ((ofs + 4) > b.length)) {
            return dft;
        }
        
        /* parse IEEE 754 float */
        int i = ofs;
        int floatInt = 0;
        if (isBigEndian) {
            floatInt = 
                (((int)b[i+0] & 0xFF) << (3*8)) + 
                (((int)b[i+1] & 0xFF) << (2*8)) + 
                (((int)b[i+2] & 0xFF) << (1*8)) + 
                 ((int)b[i+3] & 0xFF);
        } else {
            floatInt = 
                (((int)b[i+3] & 0xFF) << (3*8)) + 
                (((int)b[i+2] & 0xFF) << (2*8)) + 
                (((int)b[i+1] & 0xFF) << (1*8)) + 
                 ((int)b[i+0] & 0xFF);
        }
        return Float.intBitsToFloat(floatInt);

    }
    
    public static float parseFloat(Object data, float dft)
    {
        if (data == null) {
            return dft;
        } else
        if (data instanceof Number) {
            return ((Number)data).floatValue();
        } else {
            return StringTools.parseFloat(data.toString(), dft);
        }
    }
    
    public static float parseFloat(String data, float dft)
    {
        return StringTools.parseFloat(new FilterNumber(data, Float.class), dft);
    }
    
    public static float parseFloat(FilterNumber num, float dft)
    {
        if ((num != null) && num.supportsType(Float.class)) {
            try {
                return Float.parseFloat(num.getValueString());
            } catch (NumberFormatException nfe) {
                // ignore
            }
        }
        return dft;
    }
        
    public static boolean isFloat(String data, boolean strict)
    {
        if ((data == null) || data.equals("")) {
            return false;
        } else {
            FilterNumber fn = new FilterNumber(data, Float.class);
            return fn.isValid(strict);
        }
    }

    // ------------------------------------------------------------------------

    public static long parseLong(Object data, long dft)
    {
        if (data == null) {
            return dft;
        } else
        if (data instanceof Number) {
            return ((Number)data).longValue();
        } else {
            return StringTools.parseLong(data.toString(), dft);
        }
    }
    
    public static long parseLong(String data, long dft)
    {
        return StringTools.parseLong(new FilterNumber(data, Long.class), dft);
    }
    
    public static long parseLong(FilterNumber num, long dft)
    {
        if ((num != null) && num.supportsType(Long.class)) {
            if (num.isHex()) {
                return StringTools.parseHexLong(num.getValueString(), dft);
            } else {
                try {
                    return Long.parseLong(num.getValueString());
                } catch (NumberFormatException nfe) {
                    // Since 'FilterNumber' makes sure that only digits are parsed,
                    // this likely means that the specified digit string is too large
                    // for this required data type.  Our last ditch effort is to 
                    // attempt to convert it to a BigInteger and extract the lower
                    // number of bits to match our data type.
                    BigInteger bigLong = parseBigInteger(num, null);
                    if (bigLong != null) {
                        return bigLong.longValue();
                    }
                }
            }
        }
        return dft;
    }
    
    public static boolean isLong(String data, boolean strict)
    {
        if ((data == null) || data.equals("")) {
            return false;
        } else {
            FilterNumber fn = new FilterNumber(data, Long.class);
            return fn.isValid(strict);
        }
    }

    // ------------------------------------------------------------------------

    public static int parseInt(Object data, int dft)
    {
        if (data == null) {
            return dft;
        } else
        if (data instanceof Number) {
            return ((Number)data).intValue();
        } else {
            return StringTools.parseInt(data.toString(), dft);
        }
    }
    
    public static int parseInt(String data, int dft)
    {
        return StringTools.parseInt(new FilterNumber(data, Integer.class), dft);
    }
    
    public static int parseInt(FilterNumber num, int dft)
    {
        if ((num != null) && num.supportsType(Integer.class)) {
            if (num.isHex()) {
                return (int)StringTools.parseHexLong(num.getValueString(), dft);
            } else {
                try {
                    return Integer.parseInt(num.getValueString());
                } catch (NumberFormatException nfe) {
                    // Since 'FilterNumber' makes sure that only digits are parsed,
                    // this likely means that the specified digit string is too large
                    // for this required data type.  Our last ditch effort is to 
                    // attempt to convert it to a BigInteger and extract the lower
                    // number of bits to match our data type.
                    BigInteger bigLong = parseBigInteger(num, null);
                    if (bigLong != null) {
                        return bigLong.intValue();
                    }
                }
            }
        }
        return dft;
    }
    
    public static boolean isInt(String data, boolean strict)
    {
        if ((data == null) || data.equals("")) {
            return false;
        } else {
            FilterNumber fn = new FilterNumber(data, Integer.class);
            return fn.isValid(strict);
        }
    }

    // ------------------------------------------------------------------------

    public static int parseShort(Object data, short dft)
    {
        if (data == null) {
            return dft;
        } else
        if (data instanceof Number) {
            return ((Number)data).shortValue();
        } else {
            return StringTools.parseShort(data.toString(), dft);
        }
    }

    public static short parseShort(String data, short dft)
    {
        return StringTools.parseShort(new FilterNumber(data, Short.class), dft);
    }
    
    public static short parseShort(FilterNumber num, short dft)
    {
        if ((num != null) && num.supportsType(Short.class)) {
            if (num.isHex()) {
                return (short)StringTools.parseHexLong(num.getValueString(), dft);
            } else {
                try {
                    return Short.parseShort(num.getValueString());
                } catch (NumberFormatException nfe) {
                    // Since 'FilterNumber' makes sure that only digits are parsed,
                    // this likely means that the specified digit string is too large
                    // for this required data type.  Our last ditch effort is to 
                    // attempt to convert it to a BigInteger and extract the lower
                    // number of bits to match our data type.
                    BigInteger bigLong = parseBigInteger(num, null);
                    if (bigLong != null) {
                        return bigLong.shortValue();
                    }
                }
            }
        }
        return dft;
    }
    
    public static boolean isShort(String data, boolean strict)
    {
        if ((data == null) || data.equals("")) {
            return false;
        } else {
            FilterNumber fn = new FilterNumber(data, Short.class);
            return fn.isValid(strict);
        }
    }

    // ------------------------------------------------------------------------

    /* convert the value specfied by the String to a BigInteger */
    public static BigInteger parseBigInteger(String data, BigInteger dft)
    {
        return StringTools.parseBigInteger(new FilterNumber(data, BigInteger.class), dft);
    }
    
    public static BigInteger parseBigInteger(FilterNumber num, BigInteger dft)
    {
        if ((num != null) && num.supportsType(BigInteger.class)) {
            if (num.isHex()) {
                try {
                    return new BigInteger(num.getHexBytes());
                } catch (NumberFormatException nfe) {
                    // ignore (not likely to occur)
                }
                return null;
            } else {
                try {
                    return new BigInteger(num.getValueString());
                } catch (NumberFormatException nfe) {
                    // ignore (not likely to occur)
                }
            }
        }
        return dft;
    }
    
    // ------------------------------------------------------------------------
    
    public static class FilterNumber
    {
        
        private String inpStr = null;
        private Class  type   = null;
        private boolean isHex = false;
        
        private String numStr = null;
        private int startPos  = -1;
        private int endPos    = -1;
        
        public FilterNumber(String val, Class type) {
             
            /* null string */
            if (val == null) { // null string
                return;
            }
            
            /* skip initial whitespace */
            int s = 0;
            while ((s < val.length()) && Character.isWhitespace(val.charAt(s))) { s++; }
            if (s == val.length()) { // empty string
                return;
            }
            String v = val; // (val != null)? val.trim() : "";
            int vlen = v.length();
            
            /* hex number */
            boolean hex = false;
            if (((s + 1) < vlen) && (v.charAt(s) == '0') && (Character.toLowerCase(v.charAt(s + 1)) == 'x')) {
                // we will be parsing a hex value
                hex = true;
                s += 2; // skip "0x"
            }
            
            /* plus sign? */
            if (!hex && (s < vlen) && (v.charAt(s) == '+')) {
                // skip over prefixing '+'
                s++;
            }

            /* negative number */
            int ps, p = (!hex && (s < vlen) && (v.charAt(s) == '-'))? (s + 1) : s;
                        
            /* skip initial digits */
            if (hex) {
                // scan until end of hex digits
                for (ps = p; (p < vlen) && ("0123456789ABCDEF".indexOf(Character.toUpperCase(v.charAt(p))) >= 0);) { p++; }
            } else {
                // scan until end of decimal digits
                for (ps = p; (p < vlen) && Character.isDigit(v.charAt(p));) { p++; }
            }
            boolean foundDigit = (p > ps);
            
            /* end of digits? */
            String num;
            if ((p >= vlen)
                || Long.class.isAssignableFrom(type)
                || Integer.class.isAssignableFrom(type)
                || Short.class.isAssignableFrom(type)
                || Byte.class.isAssignableFrom(type)
                || BigInteger.class.isAssignableFrom(type)
                ) {
                // end of String or Long/Integer/Short/Byte/BigInteger
                num = v.substring(s, p);
            } else
            if (v.charAt(p) != '.') {
                // Double/Float, but doesn't contain decimal
                num = v.substring(s, p);
            } else {
                // Double/Float, decimal digits
                p++; // skip '.'
                for (ps = p; (p < vlen) && Character.isDigit(v.charAt(p));) { p++; }
                if (p > ps) { foundDigit = true; }
                num = v.substring(s, p);
            }
            
            /* set instance vars */
            if (foundDigit) {
                this.isHex      = hex;
                this.inpStr     = val;
                this.type       = type;
                this.numStr     = num;
                this.startPos   = s;
                this.endPos     = p;
            }
            
        }
        
        public boolean supportsType(Class ct) {
            if ((this.numStr != null) && (this.type != null)) {
                if (this.type.isAssignableFrom(ct)) {
                    return true; // quick check (Double/Float/BigInteger/Long/Integer/Byte)
                } else
                if (Short.class.isAssignableFrom(this.type)) {
                    return this.supportsType(Byte.class);
                } else
                if (Integer.class.isAssignableFrom(this.type)) {
                    return this.supportsType(Short.class);
                } else
                if (Long.class.isAssignableFrom(this.type)) {
                    return this.supportsType(Integer.class);
                } else
                if (BigInteger.class.isAssignableFrom(this.type)) {
                    return this.supportsType(Long.class);
                } else
                if (Float.class.isAssignableFrom(this.type)) {
                    return this.supportsType(BigInteger.class);
                } else
                if (Double.class.isAssignableFrom(this.type)) {
                    return this.supportsType(Float.class);
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        
        public String getInputString() {
            return this.inpStr;
        }
        
        public Class getClassType() {
            return this.type;
        }
        
        public String getClassTypeName() {
            if (this.type != null) {
                String cn = this.type.getName();
                if (cn.startsWith("java.lang.")) {
                    return cn.substring("java.lang.".length());
                } else
                if (cn.startsWith("java.math.")) {
                    return cn.substring("java.math.".length());
                } else {
                    return cn;
                }
            } else {
                return "null";
            }
        }
        
        public boolean isHex() {
            return this.isHex;
        }
        
        public String getValueString() {
            return this.numStr;
        }
        
        public boolean isValid(boolean strict) {
            if (this.getValueString() == null) {
                return false;
            } else
            if (!strict) {
                // don't care about trailing characters
                return true;
            } else {
                // must not have any trailing characters
                return (this.getInputString().length() == this.getEnd());
            }
        }

        public byte[] getHexBytes() {
            if (this.isHex) {
                return StringTools.parseHex(this.getValueString(), new byte[0]);
            } else {
                // not tested yet
                return (new BigInteger(this.getValueString())).toByteArray();
            }
        }
        
        public int getStart() {
            return this.startPos;
        }
        
        public int getEnd() {
            return this.endPos;
        }
        
        public int getLength() {
            return (this.endPos - this.startPos);
        }
        
        public String toString() {
            StringBuffer sb = new StringBuffer();
            sb.append(StringTools.quoteString(this.getInputString()));
            sb.append("/");
            sb.append(this.getClassTypeName());
            sb.append("/");
            sb.append(this.getStart());
            sb.append("/");
            sb.append(this.getEnd());
            return sb.toString();
        }
        
    }
    
    // ------------------------------------------------------------------------

    /* convert the value specfied by the object to a boolean value */
    public static boolean parseBoolean(Object data, boolean dft)
    {
        if (data == null) {
            return dft;
        } else
        if (data instanceof Boolean) {
            return ((Boolean)data).booleanValue();
        } else {
            return StringTools.parseBoolean(data.toString(), dft);
        }
    }
    
    /* convert the value specfied by the string to a boolean value */
    public static boolean parseBoolean(String data, boolean dft)
    {
        if (data != null) {
            String v = data.toLowerCase();
            if (dft) {
                // if default is 'true', only test for 'false'
                for (int i = 0; i < BOOLEAN_FALSE.length; i++) {
                    if (v.startsWith(BOOLEAN_FALSE[i])) {
                        return false;
                    }
                }
            } else {
                // if default is 'false', only test for 'true'
                for (int i = 0; i < BOOLEAN_TRUE.length; i++) {
                    if (v.startsWith(BOOLEAN_TRUE[i])) {
                        return true;
                    }
                }
            }
            // else return default
            return dft;
        }
        return dft;
    }

    /* return true if the specified value represents a valid boolean value */
    public static boolean isBoolean(String data, boolean strict)
    {
        String v = data.toLowerCase();
        for (int i = 0; i < BOOLEAN_TRUE.length; i++) {
            boolean ok = strict? v.equals(BOOLEAN_TRUE[i]) : v.startsWith(BOOLEAN_TRUE[i]);
            if (ok) {
                return true;
            }
        }
        for (int i = 0; i < BOOLEAN_FALSE.length; i++) {
            boolean ok = strict? v.equals(BOOLEAN_FALSE[i]) : v.startsWith(BOOLEAN_FALSE[i]);
            if (ok) {
                return true;
            }
        }
        return false;
    }

    // ------------------------------------------------------------------------

    /* parse width/height values from string and return them as a Dimension */
    public static Dimension parseDimension(String data, Dimension dft)
    {
        if ((data != null) && !data.trim().equals("")) {
            int p = data.indexOf("/");
            if (p > 0) {
                int w = StringTools.parseInt(data.substring(0,p),0);
                int h = StringTools.parseInt(data.substring(p+1),0);
                return new Dimension(w, h);
            }
        }
        return dft;
    }
    
    // ------------------------------------------------------------------------

    public static final String HEX = "0123456789ABCDEF";
    
    /* return decimal value for hex character */
    public static int hexIndex(char ch)
    {
        return StringTools.HEX.indexOf(Character.toUpperCase(ch));
    }
    
    /* return hex character for decimal value */
    public static char hexNybble(byte nybble)
    {
        return HEX.charAt(nybble & 0xF);
    }
    
    /* parse hex value into byte array */
    public static byte[] parseHex(String data, byte dft[])
    {
        if (data != null) {
            
            /* get data string */
            String d = data.toUpperCase();
            String s = d.startsWith("0X")? d.substring(2) : d;
            
            /* remove any invalid trailing characters */
            for (int i = 0; i < s.length(); i++) {
                if (HEX.indexOf(s.charAt(i)) < 0) {
                    s = s.substring(0, i);
                    break;
                }
            }
            
            /* return default if nothing to parse */
            if (s.equals("")) { 
                return dft; 
            }
            
            /* right justify */
            if ((s.length() & 1) == 1) { s = "0" + s; } // right justified
            
            /* parse data */
            byte rtn[] = new byte[s.length() / 2];
            for (int i = 0; i < s.length(); i += 2) {
                int c1 = HEX.indexOf(s.charAt(i));
                if (c1 < 0) { c1 = 0; /* Invalid Hex char */ }
                int c2 = HEX.indexOf(s.charAt(i+1));
                if (c2 < 0) { c2 = 0; /* Invalid Hex char */ }
                rtn[i/2] = (byte)(((c1 << 4) & 0xF0) | (c2 & 0x0F));
            }
            
            /* return value */
            return rtn;
            
        } else {
            
            return dft;
            
        }
    }
    
    /* parse hex value into integer */
    public static int parseHex(String data, int dft)
    {
        return (int)StringTools.parseHexLong(data, (long)dft);
    }
    
    /* parse hex value into integer */
    public static int parseHexInt(String data, int dft)
    {
        return (int)StringTools.parseHexLong(data, (long)dft);
    }
    
    /* parse hex value into long */
    public static long parseHex(String data, long dft)
    {
        return StringTools.parseHexLong(data, dft);
    }
    
    /* parse hex value into long */
    public static long parseHexLong(String data, long dft)
    {
        byte b[] = parseHex(data, null);
        if (b != null) {
            long val = 0L;
            for (int i = 0; i < b.length; i++) {
                val = (val << 8) | ((int)b[i] & 0xFF);
            }
            return val;
        } else {
            return dft;
        }
    }
    
    /* return the number of hex characters found in the specified string */
    public static int hexLength(String data)
    {
        if ((data == null) || data.equals("")) {
            return 0;
        } else {
            String d = data.toUpperCase();
            int s = d.startsWith("0X")? 2 : 0, e = s;
            for (; (e < d.length()) && (HEX.indexOf(d.charAt(e)) >= 0); e++);
            return e;
        }
    }

    /* return true if the specified string represents a valid hex value */
    public static boolean isHex(String data, boolean strict)
    {
        if ((data == null) || data.equals("")) {
            return false;
        } else {
            String d = data.toUpperCase();
            int s = d.startsWith("0X")? 2 : 0, e = s;
            for (; e < d.length(); e++) {
                if (HEX.indexOf(d.charAt(e)) < 0) {
                    if (strict) {
                        return false;
                    } else {
                        break;
                    }
                }
            }
            return (e > s);
        }
    }
    
    // ------------------------------------------------------------------------
    
    public static StringBuffer formatHexString(byte b[])
    {
        return StringTools.formatHexString(b, 0, -1, 16, true, null);
    }
    
    public static StringBuffer formatHexString(byte b[], int blockLen)
    {
        return StringTools.formatHexString(b, 0, -1, blockLen, true, null);
    }

    public static StringBuffer formatHexString(byte b[], int blockLen, StringBuffer sb)
    {
        return StringTools.formatHexString(b, 0, -1, blockLen, true, sb);
    }
    
    public static StringBuffer formatHexString(byte b[], int bOfs, int bLen, int blockLen, boolean showAscii, StringBuffer sb)
    {
        int headerLen = 0;
        if (b  == null) { b = new byte[0]; }
        if (sb == null) { sb = new StringBuffer(); }
        int bi = (bOfs >= 0)? bOfs : 0; // byte index
        int bMaxNdx = ((bLen >= 0) && ((bi + bLen) <= b.length))? (bi + bLen) : b.length;

        /* validate block length */
        if (blockLen <= 0) {
            blockLen = ((bMaxNdx - bi) < 16)? bLen : 16;
        }

        /* position ruler */
        int rulerLen = (headerLen > blockLen)? headerLen : blockLen;
        sb.append("    : ** ");
        for (int ri = 1; ri < rulerLen;) {
            for (int j = ri; (ri < rulerLen) & ((ri - j) < 4); ri++) { sb.append("-- "); }
            if (ri < rulerLen) { sb.append("++ "); ri++; }
            for (int j = ri; (ri < rulerLen) & ((ri - j) < 4); ri++) { sb.append("-- "); }
            if (ri < rulerLen) { sb.append(format(ri,"00 ")); ri++; }
        }
        sb.append("\n");

        /* byte header */
        if (headerLen > 0) {
            sb.append(format(bi,"0000")).append(": ");
            for (int j = bi; ((j - bi) < headerLen); j++) {
                if (j < bMaxNdx) {
                    toHexString(b[j], sb);
                } else {
                    sb.append("  ");
                }
                sb.append(" ");
            }
            if (showAscii) {
                sb.append(" ");
                for (int j = bi; ((j - bi) < headerLen); j++) {
                    if (j < bMaxNdx) {
                        if ((b[j] >= ' ') && (b[j] <= '~')) {
                            sb.append((char)b[j]);
                        } else {
                            sb.append('.');
                        }
                    } else {
                        sb.append(" ");
                    }
                }
            }
            sb.append("\n");
            bi += headerLen;
        }

        /* byte data */
        int count = 0;
        for (; bi < bMaxNdx; bi += blockLen) {
            sb.append(format(bi,"0000")).append(": ");
            for (int j = bi; ((j - bi) < blockLen); j++) {
                if (j < bMaxNdx) {
                    toHexString(b[j], sb);
                    count++;
                } else {
                    sb.append("  ");
                }
                sb.append(" ");
            }
            if (showAscii) {
                sb.append(" ");
                for (int j = bi; ((j - bi) < blockLen); j++) {
                    if (j < bMaxNdx) {
                        if ((b[j] >= ' ') && (b[j] <= '~')) {
                            sb.append((char)b[j]);
                        } else {
                            sb.append('.');
                        }
                    } else {
                        sb.append(" ");
                    }
                }
            }
            sb.append("\n");
        }
        
        sb.append(count).append(" bytes\n");
        return sb;
    }
    
    // ------------------------------------------------------------------------
    
    public static StringBuffer toHexString(byte b, StringBuffer sb)
    {
        if (sb == null) { sb = new StringBuffer(); }
        sb.append(HEX.charAt((b >> 4) & 0xF));
        sb.append(HEX.charAt(b & 0xF));
        return sb;
    }
    
    public static String toHexString(byte b)
    {
        return StringTools.toHexString(b,null).toString();
    }
    
    public static StringBuffer toHexString(byte b[], int ofs, int len, StringBuffer sb)
    {
        if (sb == null) { sb = new StringBuffer(); }
        if (b != null) {
            int bstrt = (ofs < 0)? 0 : ofs;
            int bstop = (len < 0)? b.length : Math.min(b.length,(ofs + len));
            for (int i = bstrt; i < bstop; i++) { StringTools.toHexString(b[i], sb); }
        }
        return sb;
    }
    
    public static StringBuffer toHexString(byte b[], StringBuffer sb)
    {
        return StringTools.toHexString(b,0,-1,sb);
    }
    
    public static String toHexString(byte b[])
    {
        return StringTools.toHexString(b,0,-1,null).toString();
    }
    
    public static String toHexString(byte b[], int ofs, int len)
    {
        return StringTools.toHexString(b,ofs,len,null).toString();
    }
    
    public static String toHexString(long val, int bitLen)
    {

        /* bounds check 'bitLen' */
        // TODO: what if 'val' is < 0?
        if (bitLen <= 0) {
            if ((val & 0xFFFFFFFF00000000L) != 0L) {
                bitLen = 64;
            } else
            if ((val & 0x00000000FFFF0000L) != 0L) {
                bitLen = 32;
            } else
            if ((val & 0x000000000000FF00L) != 0L) {
                bitLen = 16;
            } else {
                bitLen = 8;
            }
        } else 
        if (bitLen > 64) {
            bitLen = 64;
        }

        /* format and return hex value */
        int nybbleLen = ((bitLen + 7) / 8) * 2;
        StringBuffer hex = new StringBuffer(Long.toHexString(val).toUpperCase());
        if ((nybbleLen <= 16) && (nybbleLen > hex.length())) {
            String mask = "0000000000000000"; // 64 bit (16 nybbles)
            hex.insert(0, mask.substring(0, nybbleLen - hex.length()));
        }
        return hex.toString();

    }
    
    public static String toHexString(long val)
    {
        return StringTools.toHexString(val, 64);
    }
    
    public static String toHexString(int val)
    {
        return StringTools.toHexString((long)val & 0xFFFFFFFF, 32);
    }
    
    public static String toHexString(short val)
    {
        return StringTools.toHexString((long)val & 0xFFFF, 16);
    }
    
    // ------------------------------------------------------------------------
    
    public static StringBuffer toBinaryString(byte b, StringBuffer sb)
    {
        if (sb == null) { sb = new StringBuffer(); }
        String bs = Long.toBinaryString((long)b);
        if (bs.length() > 8) {
            bs = bs.substring(bs.length() - 8);
        } else
        if (bs.length() < 8) {
            while (bs.length() < 8) { bs = "0" + bs; }
        }
        sb.append(bs);
        return sb;
    }
    
    public static String toBinaryString(byte b)
    {
        return StringTools.toBinaryString(b, new StringBuffer()).toString();
    }

    public static StringBuffer toBinaryString(byte b[], int ofs, int len, StringBuffer sb)
    {
        if (sb == null) { sb = new StringBuffer(); }
        if (b != null) {
            int bstrt = (ofs < 0)? 0 : ofs;
            int bstop = (len < 0)? b.length : Math.min(b.length,(ofs + len));
            for (int i = bstrt; i < bstop; i++) { 
                if (i > 0) { sb.append(" "); }
                StringTools.toBinaryString(b[i], sb); 
            }
        }
        return sb;
    }
    
    public static StringBuffer toBinaryString(byte b[], StringBuffer sb)
    {
        if (sb == null) { sb = new StringBuffer(); }
        if (b != null) {
            for (int i = 0; i < b.length; i++) { 
                if (i > 0) { sb.append(" "); }
                StringTools.toBinaryString(b[i], sb); 
            }
        }
        return sb;
    }

    public static String toBinaryString(byte b[])
    {
        return StringTools.toBinaryString(b,new StringBuffer()).toString();
    }
    
    public static String toBinaryString(byte b[], int ofs, int len)
    {
        return StringTools.toBinaryString(b,ofs,len,null).toString();
    }
    
    // ------------------------------------------------------------------------

    /* escape special characters in argument String */
    public static String encodeText(String text)
    {
        if (text == null) { return null; }
        StringBuffer sb = new StringBuffer();
        for (int c = 0; c < text.length(); c++) {
            char ch = text.charAt(c);
            if (ch == '\\') {
                sb.append("\\");
            } else
            if (ch == '\n') {
                sb.append("\\n"); // NL
            } else
            if (ch == '\r') {
                //sb.append("\\r"); <-- do not include CR
            } else
            if (ch == '\t') {
                sb.append(" "); // <-- change tabs to spaces
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    public static String decodeText(String text)
    {
        if (text == null) { return null; }
        int len = text.length();
        StringBuffer sb = new StringBuffer();
        for (int c = 0; c < len; c++) {
            char ch = text.charAt(c);
            if ((ch == '\\') && ((c + 1) < len)) {
                char ch2 = text.charAt(++c);
                if (ch2 == '\\') {
                    sb.append("\\");
                } else
                if (ch2 == 'n') {
                    sb.append("\n");
                } else
                if (ch2 == 'r') {
                    //sb.append("\r"); <-- don't include CR
                } else
                if (ch2 == 't') {
                    sb.append(" "); // <-- change tabs to spaces
                }
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }
    
    // ------------------------------------------------------------------------

    public static String setFirstUpperCase(String s)
    {
        if (s != null) {
            boolean space = true, digitSpace = true;
            StringBuffer sb = new StringBuffer(s);
            for (int i = 0; i < sb.length(); i++) {
                char ch = sb.charAt(i);
                if (Character.isWhitespace(ch)) { // isSpace
                    space = true;
                } else
                if (digitSpace && Character.isDigit(ch)) {
                    space = true;
                } else
                if (space) {
                    if (Character.isLowerCase(ch)) {
                        sb.setCharAt(i, (char)(ch - ' ')); // toUpperCase
                    }
                    space = false;
                } else
                if (Character.isUpperCase(ch)) {
                    sb.setCharAt(i, (char)(ch + ' ')); // toLowerCase
                }
            }
            return sb.toString();
        } else {
            return null;
        }
    }
    
    // ------------------------------------------------------------------------

    public static boolean startsWithIgnoreCase(String t, String m)
    {
        if ((t != null) && (m != null)) {
            return t.toLowerCase().startsWith(m.toLowerCase());
        } else {
            return false;
        }
    }

    public static int indexOfIgnoreCase(String t, String m)
    {
        if ((t != null) && (m != null)) {
            return t.toLowerCase().indexOf(m.toLowerCase());
        } else {
            return -1;
        }
    }
    
    // ------------------------------------------------------------------------

    public static int indexOf(char A[], char c)
    {
        if (A != null) {
            for (int i = 0; i < A.length; i++) {
                if (A[i] == c) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public static int indexOf(byte B[], byte b)
    {
        if (B != null) {
            for (int i = 0; i < B.length; i++) {
                if (B[i] == b) {
                    return i;
                }
            }
        }
        return -1;
    }

    // ------------------------------------------------------------------------
    // Parse/Encode array from/to String, quoting as necessary

    public  static final String ArraySeparator      = ",";
    public  static final char   ARRAY_DELIM         = ',';
    public  static final char   ARRAY_DOUBLE_QUOTE  = '\"';
    public  static final char   ARRAY_SINGLE_QUOTE  = '\'';
    public  static final char   ARRAY_QUOTE         = ARRAY_DOUBLE_QUOTE;

    public static String[] parseArray(String s)
    {
        return StringTools.parseArray(s, ARRAY_DELIM);
    }
    
    public static String[] parseArray(String s, char arrayDelim)
    {
        
        /* invalid string? */
        if ((s == null) || s.equals("")) {
            return new String[0];
        }
        
        /* parse */
        int len = s.length();
        char ch[] = new char[len];
        s.getChars(0, len, ch, 0);
        Vector v = new Vector();
        for (int a = 0; a < len;) {
            if (ch[a] == arrayDelim) { // token == ','
                v.add("");
                a++;
            } else
            if ((ch[a] == ARRAY_DOUBLE_QUOTE) || (ch[a] == ARRAY_SINGLE_QUOTE)) { // token = '\"'
                StringBuffer sb = new StringBuffer();
                a = StringTools.parseQuote(ch, a, sb);
                v.add(sb.toString());
                while ((a < len) && (ch[a] != arrayDelim)) { a++; } // discard
                if ((a < len) && (ch[a] == arrayDelim)) { a++; }
            } else
            if (Character.isWhitespace(ch[a])) {
                while ((a < len) && Character.isWhitespace(ch[a])) { a++; }
            } else {
                StringBuffer sb = new StringBuffer();
                while ((a < len) && (ch[a] != arrayDelim)) { sb.append(ch[a++]); }
                v.add(sb.toString());
                if ((a < len) && (ch[a] == arrayDelim)) { a++; }
            }
        }
        return (String[])ListTools.toArray(v, String.class);
        
    }

    public static String encodeArray(Object list[], char delim, boolean alwaysQuote)
    {
        return StringTools.encodeArray(list, 0, -1, delim, alwaysQuote);
    }
    
    public static String encodeArray(Object list[], int ofs, int max, char delim, boolean alwaysQuote)
    {
        StringBuffer sb = new StringBuffer();
        if (list != null) {
            if ((max < 0) || (max > list.length)) { max = list.length; }
            for (int i = ((ofs >= 0)? ofs : 0); i < max; i++) {
                if (sb.length() > 0) { sb.append(delim); }
                String s = (list[i] != null)? list[i].toString() : "";
                if (alwaysQuote || (s.indexOf(' ') >= 0) || (s.indexOf('\t') >= 0) || (s.indexOf('\"') >= 0) || (s.indexOf(delim) >= 0)) {
                    s = StringTools.quoteString(s);
                }
                sb.append(s);
            }
        }
        return sb.toString();
    }

    public static String encodeArray(Object list, char delim, boolean alwaysQuote)
    {
        return StringTools.encodeArray(list, 0, -1, delim, alwaysQuote);
    }

    public static String encodeArray(Object list, int ofs, int max, char delim, boolean alwaysQuote)
    {
        if (list == null) {
            // nothing to encode
            return "";
        } else
        if (list instanceof Object[]) {
            // standard Object array
            return StringTools.encodeArray((Object[])list, ofs, max, delim, alwaysQuote);
        } else 
        if (list.getClass().isArray()) {
            // assume a primitive array
            StringBuffer sb = new StringBuffer();
            int listLen = Array.getLength(list);
            if ((max < 0) || (max > listLen)) { max = listLen; }
            for (int i = ((ofs >= 0)? ofs : 0); i < max; i++) {
                if (sb.length() > 0) { sb.append(delim); }
                Object list_i = Array.get(list, i);
                String s = (list_i != null)? list_i.toString() : "";
                if (alwaysQuote || (s.indexOf(' ') >= 0) || (s.indexOf('\t') >= 0) || (s.indexOf('\"') >= 0) || (s.indexOf(delim) >= 0)) {
                    s = StringTools.quoteString(s);
                }
                sb.append(s);
            }
            return sb.toString();
        } else {
            // a single object (assume a single element array)
            if ((ofs <= 0) && (max != 0)) {
                // offset is '0' with at least '1' element to copy
                return alwaysQuote? StringTools.quoteString(list.toString()) : list.toString();
            } else {
                // offset is out of bounds, or max == 0
                return "";
            }
        }
    }
    
    public static String encodeArray(java.util.List list, char delim, boolean alwaysQuote)
    {
        return StringTools.encodeArray(ListTools.toArray(list), delim, alwaysQuote);
    }                                          

    public static String encodeArray(java.util.List list)
    {
        return StringTools.encodeArray(ListTools.toArray(list), ARRAY_DELIM, true);
    }

    public static String encodeArray(java.util.List list, boolean alwaysQuote)
    {
        return StringTools.encodeArray(ListTools.toArray(list), ARRAY_DELIM, alwaysQuote);
    }

    public static String encodeArray(Object list[])
    {
        return StringTools.encodeArray(list, ARRAY_DELIM, true);
    }

    public static String encodeArray(Object list[], boolean alwaysQuote)
    {
        return StringTools.encodeArray(list, ARRAY_DELIM, alwaysQuote);
    }

    public static String encodeArray(String list[])
    {
        return StringTools.encodeArray((Object[])list, ARRAY_DELIM, true);
    }
    
    public static String encodeArray(String list[], boolean alwaysQuote)
    {
        return StringTools.encodeArray((Object[])list, ARRAY_DELIM, alwaysQuote);
    }
    
    public static String encodeArray(Object list)
    {
        return StringTools.encodeArray((Object)list, ARRAY_DELIM, true);
    }
        
    public static String encodeArray(Object list, boolean alwaysQuote)
    {
        return StringTools.encodeArray((Object)list, ARRAY_DELIM, alwaysQuote);
    }

    // ------------------------------------------------------------------------

    public static String[] toArray(java.util.List list)
    {
        if (list != null) {
            String s[] = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                Object obj = list.get(i);
                s[i] = (obj != null)? obj.toString() : null;
            }
            return s;
        } else {
            return new String[0];
        }
    }
    
    // ------------------------------------------------------------------------
    // 'parseString' does not take quoted values into account
    // similar to Perl "split""
    
    /* simple string parser delimited by specified character */
    // Does *not* return null
    public static String[] parseString(String value, char delim)
    {
        return StringTools.parseString(value, String.valueOf(delim));
    }

    /* simple string parser delimited by specified character(s) */
    // Does *not* return null
    public static String[] parseString(String value, String sdelim)
    {
        if (value != null) {
            
            /* parse */
            Vector v1 = new Vector();
            ListTools.toList(new StringTokenizer(value, sdelim, true), v1);
            
            /* examine all tokens to make sure we include blank items */
            int dupDelim = 1; // assume we've started with a delimiter
            Vector v2 = new Vector();
            for (Iterator i = v1.iterator(); i.hasNext();) {
                String s = (String)i.next();
                if ((s.length() == 1) && (sdelim.indexOf(s) >= 0)) { 
                    // found a delimiter
                    if (dupDelim > 0) { v2.add(""); } // blank item
                    dupDelim++;
                } else {
                    v2.add(s.trim());
                    dupDelim = 0;
                }
            }
            
            /* return parsed array */
            return (String[])v2.toArray(new String[v2.size()]);
            
        } else {
            
            /* nothing parsed */
            return new String[0];
            
        }
    }
    
    /* split string into array, delimited on the specified char */
    // Does *not* return null
    public static String[] split(String value, char delim)
    {
        return StringTools.parseString(value, String.valueOf(delim));
    }
    
    /* join array into String (supressing blank lines) */
    public static String join(String val[], char delim)
    {
        StringBuffer sb = new StringBuffer();
        if (val != null) {
            for (int i = 0; i < val.length; i++) {
                if (sb.length() > 0) { sb.append(delim); }
                if ((val[i] != null) && !val[i].equals("")) {
                    sb.append(val[i]);
                }
            }
        }
        return sb.toString();
    }
    
    /* join array into String */
    public static String join(String val[], String delim)
    {
        StringBuffer sb = new StringBuffer();
        if (val != null) {
            for (int i = 0; i < val.length; i++) {
                if (sb.length() > 0) { sb.append(delim); }
                if (val[i] != null) {
                    sb.append(val[i]);
                }
            }
        }
        return sb.toString();
    }
    
    /* join array into String */
    public static String join(java.util.List list, String delim)
    {
        StringBuffer sb = new StringBuffer();
        if (list != null) {
            for (Iterator i = list.iterator(); i.hasNext();) {
                Object val = i.next();
                if (sb.length() > 0) { sb.append(delim); }
                if (val != null) {
                    sb.append(val.toString());
                }
            }
        }
        return sb.toString();
    }

    // ------------------------------------------------------------------------

    private static boolean _isPropSep(char sep, char ch)
    {
        if ((sep == 0) || (sep == ' ')) {
            return Character.isWhitespace(ch);
        } else {
            return (ch == sep);
        }
    }
    
    public static Map parseProperties(String props)
    {
        return StringTools.parseProperties(props, StringTools.PropertySeparatorChar, null);
    }
    
    public static Map parseProperties(String props, char propSep)
    {
        return StringTools.parseProperties(props, propSep, null);
    }

    public static Map parseProperties(String props, Map properties)
    {
        return StringTools.parseProperties(props, StringTools.PropertySeparatorChar, properties);
    }
    
    @SuppressWarnings("unused")
	public static Map parseProperties(String props, char propSep, Map properties)
    {
        char keyValSep = StringTools.KeyValSeparatorChar;
        boolean spacePropSep = (propSep == 0) || (propSep == ' ');

        /* new properties? */
        if (properties == null) { properties = new OrderedMap(); }

        /* init */
        String r = (props != null)? props.trim() : "";
        char ch[] = new char[r.length()];
        r.getChars(0, r.length(), ch, 0);
        int c = 0;

        /* skip prefixing spaces */
        while ((c < ch.length) && (ch[c] == ' ')) { c++; }
        if (c > 0) { r = r.substring(c); }

        /* check for name */
        int n1 = 0, n2 = r.indexOf(" "), n3 = r.indexOf(keyValSep);
        if (n2 < 0) { n2 = r.length(); }
        if ((n3 < 0) || (n2 < n3)) { // no '=', or position of '=' is before ' '
            //if (allowNameChange) { this.setName(r.substring(n1, n2)); }
            //if (this.getIncludeNameInArgs()) { n2 = 0; }
            n2 = 0; // start at beginning of string
        } else {
            n2 = 0; // start at beginning of string
        }

        /* extract properties */
        int argsLen = r.length() - n2, a = 0;
        char args[] = new char[argsLen];
        r.getChars(n2, r.length(), args, 0);
        for (;a < argsLen;) {

            /* skip whitespace (and any prefixing property separators) */
            while ((a < argsLen) && (Character.isWhitespace(args[a]) || _isPropSep(propSep,args[a]))) { a++; }

            /* prop name */
            // scan until first Whitespace, PropertySeparator, or KeyValSeparator
            StringBuffer propName = new StringBuffer();
            for (;(a < argsLen) && !Character.isWhitespace(args[a]) && !_isPropSep(propSep,args[a]) && (args[a] != keyValSep); a++) {
                propName.append(args[a]);
            }
            
            /* skip whitespace? */
            if (!spacePropSep) {
                while ((a < argsLen) && Character.isWhitespace(args[a])) { a++; }
            }

            /* prop value */
            // only if next char is '='
            StringBuffer propValue = new StringBuffer();
            if ((a < argsLen) && (args[a] == keyValSep)) {
                a++; // skip past '='
                if (!spacePropSep) {
                    // skip whitespace
                    while ((a < argsLen) && Character.isWhitespace(args[a])) { a++; }
                }
                if ((a < argsLen) && (args[a] == '\"')) { // quoted string
                    // stop at end of quoted string
                    a++; // skip past first '\"'
                    for (; (a < argsLen) && (args[a] != '\"'); a++) {
                        if (((a + 1) < argsLen) && (args[a] == '\\')) { a++; }
                        propValue.append(args[a]);
                    }
                    if (a < argsLen) { a++; } // skip past last '\"'
                } else {
                    // stop at first Whitespace or PropertySeparator
                    for (;(a < argsLen) && !Character.isWhitespace(args[a]) && !_isPropSep(propSep,args[a]); a++) {
                        propValue.append(args[a]);
                    }
                }
            }

            /* add property */
            if (propName.length() > 0) {
                // we must have a property key
                String key = propName.toString();
                String val = propValue.toString();
                properties.put(key, val);
            }

            /* skip past any trailing junk */
            // stop at first PropertySeparator
            while ((a < argsLen) && !_isPropSep(propSep,args[a])) { a++; } // trailing junk
            if ((a < argsLen) && !_isPropSep(propSep,args[a])) { a++; } // skip PropertySeparator

        }
        
        return properties;

    }
    
    // ------------------------------------------------------------------------

    public static final int STRIP_INCLUDE = 0;
    public static final int STRIP_EXCLUDE = 1;
    
    public static String stripChars(String src, char chars[])
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++) { sb.append(chars[i]); }
        return stripChars(src, sb.toString(), STRIP_EXCLUDE);
    }
    
    public static String stripChars(String src, char chars)
    {
        return stripChars(src, String.valueOf(chars), STRIP_EXCLUDE);
    }

    public static String stripChars(String src, String chars)
    {
        return stripChars(src, chars, STRIP_EXCLUDE);
    }
    
    public static String stripChars(String src, char chars, int stripType)
    {
        return stripChars(src, String.valueOf(chars), stripType);
    }
    
    public static String stripChars(String src, String chars, int stripType)
    {
        if ((src != null) && (chars != null)) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < src.length(); i++) {
                char ch = src.charAt(i);
                if (stripType == STRIP_INCLUDE) { // include chars
                    if (chars.indexOf(ch) >= 0) { sb.append(ch); }
                } else { // exclude chars
                    if (chars.indexOf(ch) <  0) { sb.append(ch); }
                }
            }
            return sb.toString();
        } else {
            return src;
        }
    }
    
    // ------------------------------------------------------------------------

    public static String replaceChars(String src, char chars, char repChar)
    {
        return StringTools.replaceChars(src, String.valueOf(chars), String.valueOf(repChar));
    }

    public static String replaceChars(String src, String chars, char repChar)
    {
        return StringTools.replaceChars(src, chars, String.valueOf(repChar));
    }

    public static String replaceChars(String src, String chars, String repStr)
    {
        if ((src != null) && (chars != null)) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < src.length(); i++) {
                char ch = src.charAt(i);
                if (chars.indexOf(ch) >= 0) {
                    sb.append(repStr); 
                } else {
                    sb.append(ch);
                }
            }
            return sb.toString();
        } else {
            return src;
        }
    }
    
    // ------------------------------------------------------------------------

    public static String replaceWhitespace(String src, char repChar)
    {
        return StringTools.replaceWhitespace(src, String.valueOf(repChar));
    }
    
    public static String replaceWhitespace(String src, String repStr)
    {
        if ((src != null) && (repStr != null)) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < src.length(); i++) {
                char ch = src.charAt(i);
                if (Character.isWhitespace(ch)) {
                    sb.append(repStr); 
                } else {
                    sb.append(ch);
                }
            }
            return sb.toString();
        } else {
            return src;
        }
    }
    
    // ------------------------------------------------------------------------
    // Replace keys found in target String with their corresponding values
    // Key format: ${key[:arg]}

    private static final String KEY_START               = "${";
    private static final String KEY_END                 = "}";
    
    public interface KeyValueMap
    {
        public String getKeyValue(String key, String arg);
    }

    public static String replaceKeys(String text, KeyValueMap keyMap)
    {
        
        /* first check to see if there are any keys in this string */
        int ks = text.indexOf(KEY_START);
        if (ks < 0) {
            return text;
        }
        
        /* start replacing keys */
        StringBuffer repText = new StringBuffer(text);
        for (;(ks >= 0);) {
            
            /* find end of key */
            int ke = repText.indexOf(KEY_END,ks);
            if (ke < 0) {
                // invalid key specification, stop here
                break;
            }
            
            /* extract key/arg */
            String key = repText.substring(ks+2, ke);
            String arg = null;
            int a = key.indexOf(':');
            if (a >= 0) {
                arg = key.substring(a+1);
                key = key.substring(0,a);
            }
            
            /* replace key with value */
            String val = (keyMap != null)? keyMap.getKeyValue(key,arg) : null;
            repText.replace(ks, ke+1, ((val!=null)?val:""));
            
            /* find start of next key */
            ks = repText.indexOf(KEY_START, ks);
        }

        /* return new text */
        return repText.toString();
        
    }

    // ------------------------------------------------------------------------

    public static String replicateString(String pattern, int count)
    {
        if ((pattern != null) && (count > 0)) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < count; i++) { sb.append(pattern); }
            return sb.toString();
        } else {
            return "";
        }
    }
    
    // ------------------------------------------------------------------------
    
    public static String padRight(String s, char padChar, int len)
    {
        if ((s == null) || (s.length() >= len)) {
            return s;
        } else {
            return s + StringTools.replicateString(String.valueOf(padChar), len - s.length());
        }
    }

    public static String leftAlign(String s, int len)
    {
        return StringTools.padRight(s, ' ', len);
    }

    public static String leftJustify(String s, int len)
    {
        return StringTools.padRight(s, ' ', len);
    }

    public static String padLeft(String s, char padChar, int len)
    {
        if ((s == null) || (s.length() >= len)) {
            return s;
        } else {
            return StringTools.replicateString(String.valueOf(padChar), len - s.length()) + s;
        }
    }

    public static String rightAlign(String s, int len)
    {
        return StringTools.padLeft(s, ' ', len);
    }

    public static String rightJustify(String s, int len)
    {
        return StringTools.padLeft(s, ' ', len);
    }
    
    // ------------------------------------------------------------------------
    
    public static String replace(String text, String key, String val)
    {
        if (text != null) {
            return StringTools.replace(new StringBuffer(text), key, val).toString();
        } else {
            return null;
        }
    }
    
    public static StringBuffer replace(StringBuffer sb, String key, String val)
    {
        if (sb != null) {
            int s = 0;
            while (true) {
                s = sb.indexOf(key, s);
                if (s < 0) { break; }
                int e = s + key.length();
                sb.replace(s, e, val);
                s += val.length(); // = e;
            }
        }
        return sb;
    }
        
    // ------------------------------------------------------------------------

    public static String regexReplace(String target, String regex, String val)
    {
        int flags = Pattern.MULTILINE | Pattern.CASE_INSENSITIVE;
        Pattern pattern = Pattern.compile(regex, flags);
        Matcher matcher = pattern.matcher(target);
        return matcher.replaceAll(val);
    }

    public static StringBuffer regexReplace(StringBuffer target, String regexKey, String val)
    {
        String s = StringTools.regexReplace(target.toString(), regexKey, val);
        return target.replace(0, target.length(), s);
    }
        
    // ------------------------------------------------------------------------

    public static boolean regexMatches(String target, String regex)
    {
        return Pattern.matches(regex, target);
    }
    
    // ------------------------------------------------------------------------
    
    public static RegexIndex regexIndexOf(String target, String regex)
    {
        return StringTools.regexIndexOf(target, regex, 0);
    }
    
    public static RegexIndex regexIndexOf(String target, String regex, int ndx)
    {
        int flags = Pattern.MULTILINE; //  | Pattern.CASE_INSENSITIVE;
        Pattern pattern = Pattern.compile(regex, flags);
        Matcher match = pattern.matcher(target);
        if (match.find(ndx)) {
            return new RegexIndex(match);
        } else {
            return null;
        }
    }

    public static RegexIndex regexIndexOf(RegexIndex regNdx)
    {
        if (regNdx == null) {
            return null;
        } else
        if (regNdx.getMatcher() == null) { 
            return null; 
        } else
        if (regNdx.getMatcher().find()) {
            return regNdx;
        } else {
            return null;
        }
    }
    
    public static class RegexIndex
    {
        private Matcher matcher = null;
        private int startPos    = -1;
        private int endPos      = -1;
        public RegexIndex(Matcher match) {
            this.matcher = match;
        }
        public RegexIndex(int start, int end) {
            this.startPos = start;
            this.endPos = end;
        }
        public Matcher getMatcher() {
            return this.matcher;
        }
        public int getStart() {
            return (this.matcher != null)? this.matcher.start() : this.startPos;
        }
        public int getEnd() {
            return (this.matcher != null)? this.matcher.end() : this.endPos;
        }
        public String toString() {
            StringBuffer sb = new StringBuffer();
            sb.append(this.getStart());
            sb.append("/");
            sb.append(this.getEnd());
            return sb.toString();
        }
    }
        
    // ------------------------------------------------------------------------

    public static interface ReplacementMap
    {
        public String get(String key);
    }

    public static String insertKeyValues(String text, String startDelim, String endDelim, String rep[][])   
    {
        return StringTools.insertKeyValues(text, startDelim, endDelim, rep, false);
    }
    
    public static String insertKeyValues(String text, String startDelim, String endDelim, String rep[][], boolean htmlFilter)   
    {
        if (text != null) {
            HashMap repMap = new HashMap();
            for (int i = 0; i < rep.length; i++) {
                if ((rep[i] == null) || (rep[i].length < 2)) { continue; }
                repMap.put(rep[i][0], rep[i][1]);
            }
            return insertKeyValues(text, startDelim, endDelim, repMap, htmlFilter);
        } else {
            return text;
        }
    }

    public static String insertKeyValues(String text, String startDelim, String endDelim, Map map)   
    {
        return StringTools.insertKeyValues(text, startDelim, endDelim, map, false);
    }
        
    public static String insertKeyValues(String text, String startDelim, String endDelim, final Map map, boolean htmlFilter)   
    {
        if (text != null) {
            ReplacementMap rm = new ReplacementMap() {
                public String get(String key) {
                    Object val = (key != null)? map.get(key) : null;
                    return (val != null)? val.toString() : "";
                }
            };
            return insertKeyValues(text, startDelim, endDelim, rm, htmlFilter);
        } else {
            return text;
        }
    }

    public static String insertKeyValues(String text, String startDelim, String endDelim, StringTools.ReplacementMap rmap)   
    {
        return StringTools.insertKeyValues(text, startDelim, endDelim, rmap, false);
    }
    
    public static String insertKeyValues(String text, String startDelim, String endDelim, StringTools.ReplacementMap rmap, boolean htmlFilter)   
    {
        
        /* null text? */
        if (text == null) {
            return text;
        }

        /* replace keys in text string */
        int startDelimLen = startDelim.length();
        StringBuffer sb = new StringBuffer(text);
        int s = 0;
        while (s < sb.length()) {
            
            /* start delimiter */
            s = sb.indexOf(startDelim, s);
            if (s < 0) { break; } // no more starting delimiters (exit)
            
            /* ignore delimiter is prefixed with '\' [ie. \${hello}] */
            if ((s > 0) && (sb.charAt(s - 1) == '\\')) {
                // skip this literal delimiter char
                s += startDelimLen;
                continue;
            }
            
            /* end delimiter */
            int e = sb.indexOf(endDelim, s + startDelimLen);
            if (e < 0) { break; } // ending delimiter not found (exit)
            
            /* ignore this start/end delimiter? */
            int sn = sb.indexOf(startDelim, s + startDelimLen); // next start delimiter
            if ((sn >= 0) && (e > sn)) { 
                // ending delimiter is beyond next start delimiter
                s = sn; // reset starting delimiter
                continue;
            } 
            
            /* set replacement value */
            String key = sb.substring(s + startDelimLen, e).trim();
            String val = (rmap != null)? rmap.get(key) : ("?" + key + "?");
            if (val != null) {
                sb.replace(s, e + endDelim.length(), (htmlFilter?StringTools.htmlFilter(val):val));
                s += val.length();
            } else {
                s = e + endDelim.length();
            }
            
        }
        return sb.toString();

    }
    
    // ------------------------------------------------------------------------
    
    public static int compare(byte b1[], byte b2[], int len)
    {
        if ((b1 == null) && (b2 == null)) {
            return 0;
        } else
        if (b1 == null) {
            return 1;
        } else
        if (b2 == null) {
            return -1;
        } else {
            int n1 = b1.length, n2 = b2.length, i = 0;
            if (len < 0) { len = (n1 >= n2)? n1 : n2; }
            for (i = 0; (i < n1) && (i < n2) && (i < len); i++) {
                if (b1[i] != b2[i]) { return b1[i] - b2[i]; }
            }
            return (i < len)? (n1 - n2) : 0;
        }
    }

    public static int compare(byte b1[], String s)
    {
        return StringTools.compare(b1, ((s != null)? StringTools.getBytes(s) : null), -1);
    }

    public static boolean compareEquals(byte b1[], byte b2[], int len) 
    {
        return (StringTools.compare(b1, b2, len) == 0);
    }

    public static boolean compareEquals(byte b[], String s) 
    {
        return StringTools.compareEquals(b, ((s != null)? StringTools.getBytes(s) : null), -1);
    }

    // ------------------------------------------------------------------------
    
    /* check for difference between 2 byte arrays */
    // returns the first byte index where the 2 arrays are different, or -1 if they are equal
    public static int diff(byte b1[], byte b2[], int len)
    {
        if ((b1 == null) && (b2 == null)) {
            return -1; // equals
        } else
        if ((b1 == null) || (b2 == null)) {
            return 0; // diff on first byte
        } else {
            int n1 = b1.length, n2 = b2.length, i = 0;
            if (len < 0) { len = (n1 >= n2)? n1 : n2; } // larger of two lengths
            for (i = 0; (i < n1) && (i < n2) && (i < len); i++) {
                if (b1[i] != b2[i]) { return i; }
            }
            return (i < len)? i : -1;
        }
    }

    // ------------------------------------------------------------------------
    // Number formatting
    // [may be useful: http://pws.prserv.net/ad/programs/Programs.html#PaddedDecimalFormat]
    
    /* return a DecimalFormat for specified format String */
    private static HashMap formatMap = null;
    private static DecimalFormat _getFormatter(String fmt)
    {
        if (formatMap == null) { formatMap = new HashMap(); }
        DecimalFormat df = (DecimalFormat)formatMap.get(fmt);
        if (df == null) {
            //df = new DecimalFormat(fmt); // use default locale
            df = new DecimalFormat(fmt, new DecimalFormatSymbols(Locale.US));
            formatMap.put(fmt, df);
        }
        return df;
    }

    /* format double */
    public static String format(double val, String fmt)
    {
        return format(val, fmt, -1);
    }

    /* format double with specified field size */
    public static String format(double val, String fmt, int fieldSize)
    {
        String s = _getFormatter(fmt).format(val);
        if (fieldSize > s.length()) {
            s = StringTools.rightAlign(s, fieldSize);
        }
        return s;
    }

    /* format long */
    public static String format(long val, String fmt)
    {
        return format(val, fmt, -1);
    }
    
    /* format long with specified field size */
    public static String format(long val, String fmt, int fieldSize)
    {
        String s = null;
        if (fmt == null) {
            s = String.valueOf(val);
        } else
        if (fmt.startsWith("x") || fmt.startsWith("X")) {
            int byteLen = StringTools.parseInt(fmt.substring(1), -1);
            String hex = StringTools.toHexString(val, byteLen * 8); // uppercase
            if (fmt.startsWith("x")) { hex = hex.toLowerCase(); }
            s = "0x" + hex;
        } else
        if (fmt.startsWith("hex") || fmt.startsWith("HEX")) {
            int byteLen = StringTools.parseInt(fmt.substring(3), -1);
            String hex = StringTools.toHexString(val, byteLen * 8); // uppercase
            if (fmt.startsWith("h")) { hex = hex.toLowerCase(); }
            s = "0x" + hex;
        } else {
            s = _getFormatter(fmt).format(val);
        }
        if (fieldSize > s.length()) {
            s = StringTools.rightAlign(s, fieldSize);
        }
        return s;
    }

    /* format int */
    public static String format(int val, String fmt)
    {
        return StringTools.format((long)val, fmt, -1);
    }

    /* format int with specified field size */
    public static String format(int val, String fmt, int fieldSize)
    {
        return StringTools.format((long)val, fmt, fieldSize);
    }

    /* format short */
    public static String format(short val, String fmt)
    {
        return StringTools.format((long)val, fmt, -1);
    }

    /* format short with specified field size */
    public static String format(short val, String fmt, int fieldSize)
    {
        return StringTools.format((long)val, fmt, fieldSize);
    }

    // ------------------------------------------------------------------------
    // Probably should be in a module called 'ClassTools'

    public static String className(Object c)
    {
        if (c == null) {
            return "null";
        } else
        if (c instanceof Class) {
            Class clzz = (Class)c;
            if (clzz.isArray()) {
                Class elemClz = clzz.getComponentType();
                return elemClz.getName() + "[]";
            } else {
                return clzz.getName();
            }
        } else
        if (c.getClass().isArray()) {
            Class clzz = (Class)c.getClass();
            Class elemClz = clzz.getComponentType();
            return elemClz.getName() + "[]";
        } else {
            return c.getClass().getName();
        }
    }
    
    // ------------------------------------------------------------------------
 
	@SuppressWarnings("unused")
	private static void printArray(String m, String s[])
    {
        logger.debug(m);
        for (int i = 0; i < s.length; i++) {
            logger.debug(i + ") " + s[i]);
        }
    }
    
    // ------------------------------------------------------------------------

    private static String BASE_DIGITS =  "0123456789abcdefghijklmnopqrstvwxyzABCDEFGHIJKLMNOPQRSTVWXYZ()[]{}<>!@#&-=_+:~";
   
    
    /* obfuscate 'long' value using default String */
    public static String compressDigits(long num)
    {
        return compressDigits(num, BASE_DIGITS);
    }
    
    /* obfuscate 'long' value using specified String */
    public static String compressDigits(long num, String alpha)
    {
        int alphaLen = alpha.length();
        StringBuffer sb = new StringBuffer();
        for (long v = num; v > 0; v /= alphaLen) {
            sb.append(alpha.charAt((int)(v % alphaLen)));
        }
        return sb.reverse().toString();
    }
    
    /* un-obfuscate 'long' value using default String */
    public static long decompressDigits(String str)
    {
        return decompressDigits(str, BASE_DIGITS);
    }
    
    /* un-obfuscate 'long' value using specified String */
    public static long decompressDigits(String str, String alpha)
    {
        int alphaLen = alpha.length();
        long accum = 0L;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            accum = (accum * alphaLen) + alpha.indexOf(ch);
        }
        return accum;
    }
    
    // ------------------------------------------------------------------------
    
    private static int CHARSET_SOURCE = 1;
    
    public static String getDefaultCharset()
    {
        String charSet = null;
        switch (CHARSET_SOURCE) {
            case 0:
                // not crossplateform safe
                charSet = System.getProperty("file.encoding");
                break;
            case 1:
                // jdk1.4
                charSet = new java.io.OutputStreamWriter(new java.io.ByteArrayOutputStream()).getEncoding();
                break;
            case 2:
                // jdk1.5
                //charSet = java.nio.charset.Charset.defaultCharset().name();
                break;
        }
        return charSet;
    }
    
    public static void setDefaultCharset(String charSet)
    {
        // 'ISO-8859-1' should be 8-bit clean
        // BTW, don't expect this to work.  This is just here for testing
        String cs = (charSet != null)? charSet : DEFAULT_CHARSET;
        System.setProperty("file.encoding", cs);
    }

    // ------------------------------------------------------------------------

    public static final String HTML_SP              = "&nbsp;";
    public static final String HTML_LT              = "&lt;";
    public static final String HTML_GT              = "&gt;";
    public static final String HTML_AMP             = "&amp;";
    public static final String HTML_QUOTE           = "&quote;";
    public static final String HTML_DOUBLE_QUOTE    = HTML_QUOTE;
    public static final String HTML_APOS            = "&apos;";
    public static final String HTML_SINGLE_QUOTE    = HTML_APOS;
    public static final String HTML_BR              = "<BR>";
    public static final String HTML_HR              = "<HR>";

    /**
    *** Encode special HTML character string
    *** @param text The Object to encode [via 'toString()' method]
    *** @return     The encoded string.
    **/
    public static String htmlFilter(Object text)
    {
        String s = (text != null)? text.toString() : "";

        /* empty */
        if (s.length() == 0) {
            return "";
        }
        
        /* single space */
        if (s.equals(" ")) { 
            return HTML_SP;
        }

        /* encode special characters */
        int sp = 0; // adjacent space counter
        char ch[] = new char[s.length()];
        s.getChars(0, s.length(), ch, 0);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ch.length; i++) {
            if ((i == 0) && (ch[i] == ' ')) {
                sb.append(HTML_SP); // first character is a space
            } else 
            if ((i == (ch.length - 1)) && (ch[i] == ' ')) {
                sb.append(HTML_SP); // last character is a space
            } else {
                sp = (ch[i] == ' ')? (sp + 1) : 0; // count adjacent spaces
                switch (ch[i]) {
                    case '<' : sb.append(HTML_LT          ); break;
                    case '>' : sb.append(HTML_GT          ); break;
                    case '&' : sb.append(HTML_AMP         ); break;
                  //case '"' : sb.append(HTML_DOUBLE_QUOTE); break;
                  //case '\'': sb.append(HTML_SINGLE_QUOTE); break;
                    case '\n': sb.append(HTML_BR          ); break;
                    case ' ' : sb.append(((sp & 1) == 0)? HTML_SP : " "); break; // every even space
                    default  : sb.append(ch[i]            ); break;
                }
            }
        }

        return sb.toString();
    }

    // ------------------------------------------------------------------------
    
    private static String RANDOM_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ.!@#&-=_+";

    public static String createRandomString(int len)
    {
        return StringTools.createRandomString(len, RANDOM_CHARS);
    }

    public static String createRandomString(int len, String alpha)
    {
        Random ran = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            sb.append(alpha.charAt(ran.nextInt(alpha.length())));
        }
        return sb.toString();
    }

    // ------------------------------------------------------------------------
    // ------------------------------------------------------------------------
    
    public static void main(String argv[])
    {
        RTConfig.setCommandLineArgs(argv);
        
        /* hex test */
        String hex = RTConfig.getString("hex",null);
        if (hex != null) {
            byte b[] = StringTools.parseHex(hex, null);
            if (b != null) {
                byte sb[] = new byte[b.length];
                for (int i = 0; i < b.length; i++) {
                    if ((b[i] < ' ') || (b[i] > '~')) {
                        sb[i] = '.';
                    } else {
                        sb[i] = b[i];
                    }
                }
               logger.debug("String: " + StringTools.toStringValue(sb));
            } else {
                logger.error("Invalid hex value");
            }
        }
        
    }

}
