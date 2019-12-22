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
//  This class provides many Date/Time utilities
// ----------------------------------------------------------------------------
// Change History:
//  2006/03/26  Martin D. Flynn
//      Initial release
//  2006/06/30  Martin D. Flynn
//      Repackaged to "org.opengts.util"
//  2008/01/10  Martin D. Flynn
//     -Added 'getMonthStart'/'getMonthEnd' methods.
// ----------------------------------------------------------------------------
package com.digitrinity.parser.dateutil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DateTime implements Comparable, Cloneable
{
    // ------------------------------------------------------------------------

    public static String GMT_TIMEZONE           = "GMT";

    // ------------------------------------------------------------------------

    public static final long HOURS_PER_DAY      = 24L;
    public static final long SECONDS_PER_MINUTE = 60L;
    public static final long MINUTES_PER_HOUR   = 60L;
    public static final long DAYS_PER_WEEK      = 7L;
    public static final long SECONDS_PER_HOUR   = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
    public static final long MINUTES_PER_DAY    = HOURS_PER_DAY * MINUTES_PER_HOUR;
    public static final long SECONDS_PER_DAY    = MINUTES_PER_DAY * SECONDS_PER_MINUTE;
    public static final long MINUTES_PER_WEEK   = DAYS_PER_WEEK * MINUTES_PER_DAY;
    public static final long SECONDS_PER_WEEK   = MINUTES_PER_WEEK * SECONDS_PER_MINUTE;
    static Logger logger = LogManager.getLogger(DateTime.class.getName());

    public static long DaySeconds(long days)
    {
        return days * SECONDS_PER_DAY;
    }

    public static long DaySeconds(double days)
    {
        return (long)Math.round(days * (double)SECONDS_PER_DAY);
    }

    public static long HourSeconds(long hours)
    {
        return hours * SECONDS_PER_HOUR;
    }

    public static long MinuteSeconds(long minutes)
    {
        return minutes * SECONDS_PER_MINUTE;
    }

    // ------------------------------------------------------------------------

    public static final int JAN       = 0;
    public static final int FEB       = 1;
    public static final int MAR       = 2;
    public static final int APR       = 3;
    public static final int MAY       = 4;
    public static final int JUN       = 5;
    public static final int JUL       = 6;
    public static final int AUG       = 7;
    public static final int SEP       = 8;
    public static final int OCT       = 9;
    public static final int NOV       = 10;
    public static final int DEC       = 11;

    public static final int JANUARY   = JAN;
    public static final int FEBRUARY  = FEB;
    public static final int MARCH     = MAR;
    public static final int APRIL     = APR;  
    public static final int JUNE      = JUN;
    public static final int JULY      = JUL;
    public static final int AUGUST    = AUG;
    public static final int SEPTEMBER = SEP;
    public static final int OCTOBER   = OCT;
    public static final int NOVEMBER  = NOV;
    public static final int DECEMBER  = DEC;

    // I18N?
    private static final String MONTH_NAME[][] = {
        { "January"  , "Jan" },
        { "February" , "Feb" },
        { "March"    , "Mar" },
        { "April"    , "Apr" },
        { "May"      , "May" },
        { "June"     , "Jun" },
        { "July"     , "Jul" },
        { "August"   , "Aug" },
        { "September", "Sep" },
        { "October"  , "Oct" },
        { "November" , "Nov" },
        { "December" , "Dec" },
    };

    public static int getMonthNumber0(String month)
    {
        String m = (month != null)? month.toLowerCase().trim() : null;
        if ((m != null) && !m.equals("")) {
            if (Character.isDigit(m.charAt(0))) {
                int v = StringTools.parseInt(m,-1);
                return (v < 0)? 0 : ((v > 30)? 30 : v);
            } else {
                for (int i = 0; i < MONTH_NAME.length; i++) {
                    if (m.startsWith(MONTH_NAME[i][1].toLowerCase())) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    public static int getMonthNumber1(String month)
    {
        int m = DateTime.getMonthNumber0(month);
        return (m < 0)? -1 : (m + 1);
    }

    public static String getMonthName(int mon1, boolean abbrev)
    {
        int mon0 = mon1 - 1;
        if ((mon0 >= JANUARY) && (mon0 <= DECEMBER)) {
            return abbrev? MONTH_NAME[mon0][1] : MONTH_NAME[mon0][0];
        } else {
            return "";
        }
    }

    public static String[] getMonthNames(boolean abbrev)
    {
        String mo[] = new String[MONTH_NAME.length];
        for (int i = 0; i < MONTH_NAME.length; i++) {
            mo[i] = DateTime.getMonthName(i, abbrev);
        }
        return mo;
    }

    public static Map getMonthNameMap(boolean abbrev)
    {
        Map map = new OrderedMap();
        for (int i = 0; i < MONTH_NAME.length; i++) {
            map.put(DateTime.getMonthName(i, abbrev), new Integer(i));
        }
        return map;
    }

    // ------------------------------------------------------------------------

    private static final int MONTH_DAYS[] = {
        31, // Jan
        29, // Feb
        31, // Mar
        30, // Apr
        31, // May
        30, // Jun
        31, // Jul
        31, // Aug
        30, // Sep
        31, // Oct
        30, // Nov
        31, // Dec
    };

    public static int getDaysInMonth(TimeZone tz, int mon1, int year)
    {
        int yy = (year > mon1)? year : mon1; // larger of the two
        int mm = (year > mon1)? mon1 : year; // smaller of the two
        DateTime dt = new DateTime(tz, yy, mm, 1);
        return DateTime.getMaxMonthDayCount(mm, dt.isLeapYear(tz));
    }

    public static int getMaxMonthDayCount(int m1, boolean isLeapYear)
    {
        int m0 = m1 - 1;
        int d = ((m0 >= 0) && (m0 < DateTime.MONTH_DAYS.length))? DateTime.MONTH_DAYS[m0] : 31;
        return ((m0 != FEBRUARY) || isLeapYear)? d : 28;
    }

    // ------------------------------------------------------------------------

    public static final int SUN       = 0;
    public static final int MON       = 1;
    public static final int TUE       = 2;
    public static final int WED       = 3;
    public static final int THU       = 4;
    public static final int FRI       = 5;
    public static final int SAT       = 6;

    public static final int SUNDAY    = SUN;
    public static final int MONDAY    = MON;
    public static final int TUESDAY   = TUE;
    public static final int WEDNESDAY = WED;
    public static final int THURSDAY  = THU;
    public static final int FRIDAY    = FRI;
    public static final int SATURDAY  = SAT;

    // I18N?
    private static final String DAY_NAME[][] = {
        // 0            1      2
        { "Sunday"   , "Sun", "Su" },
        { "Monday"   , "Mon", "Mo" },
        { "Tuesday"  , "Tue", "Tu" },
        { "Wednesday", "Wed", "We" },
        { "Thursday" , "Thu", "Th" },
        { "Friday"   , "Fri", "Fr" },
        { "Saturday" , "Sat", "Sa" },
    };

    public static int getDayNumber(String day)
    {
        String d = (day != null)? day.toLowerCase().trim() : null;
        if ((d != null) && !d.equals("")) {
            if (Character.isDigit(d.charAt(0))) {
                int v = StringTools.parseInt(d,-1);
                return (v < 0)? 0 : (v % 7);
            } else {
                for (int i = 0; i < DAY_NAME.length; i++) {
                    if (d.startsWith(DAY_NAME[i][2].toLowerCase())) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    public static String getDayName(int day, int abbrev)
    {
        if ((day >= SUNDAY) && (day <= SATURDAY) && (abbrev >= 0) && (abbrev <= 2)) {
            return DAY_NAME[day][abbrev];
        } else {
            return "";
        }
    }

    public static String getDayName(int day, boolean abbrev)
    {
        return DateTime.getDayName(day, abbrev? 1 : 0);
    }

    public static String[] getDayNames(int abbrev)
    {
        String dy[] = new String[DAY_NAME.length];
        for (int i = 0; i < DAY_NAME.length; i++) {
            dy[i] = DateTime.getDayName(i, abbrev);
        }
        return dy;
    }

    public static String[] getDayNames(boolean abbrev)
    {
        return DateTime.getDayNames(abbrev? 1 : 0);
    }

    public static Map getDayNameMap(int abbrev)
    {
        Map map = new OrderedMap();
        for (int i = 0; i < DAY_NAME.length; i++) {
            map.put(DateTime.getDayName(i, abbrev), new Integer(i));
        }
        return map;
    }

    public static Map getDayNameMap(boolean abbrev)
    {
        return DateTime.getDayNameMap(abbrev? 1 : 0);
    }

    // ------------------------------------------------------------------------

    public static String[] getHours(boolean hr24)
    {
        String hrs[] = new String[hr24? 24 : 12];
        for (int i = 0; i < hrs.length; i++) {
            hrs[i] = String.valueOf(i);
        }
        return hrs;
    }

    public static String[] getMinutes()
    {
        String min[] = new String[60];
        for (int i = 0; i < min.length; i++) {
            min[i] = String.valueOf(i);
        }
        return min;
    }

    // ------------------------------------------------------------------------

    public static String toString(long timeSec)
    {
        return (new java.util.Date(timeSec * 1000L)).toString();
    }

    public static long getCurrentTimeSec()
    {
        // Number of seconds since the 'epoch' January 1, 1970, 00:00:00 GMT
        return getCurrentTimeMillis() / 1000L;
    }

    public static long getCurrentTimeMillis()
    {
        // Number of milliseconds since the 'epoch' January 1, 1970, 00:00:00 GMT
        return System.currentTimeMillis();
    }

    public static boolean isRecentSec(long timeSec, long lapseSec)
    {
        logger.debug("timeSec: " + timeSec);
        logger.debug("getCurrentTimeSec: " + DateTime.getCurrentTimeSec());
        return (timeSec >= (DateTime.getCurrentTimeSec() - lapseSec));
    }

    // ------------------------------------------------------------------------

    public static DateTime getMinDate()
    {
        return DateTime.getMinDate(null);
    }

    public static DateTime getMinDate(TimeZone tz)
    {
        return new DateTime(1, tz);
    }

    public static DateTime getMaxDate()
    {
        return DateTime.getMaxDate(null);
    }

    public static DateTime getMaxDate(TimeZone tz)
    {
        return new DateTime(tz, 2020, 12, 31);
    }

    // ------------------------------------------------------------------------

    private TimeZone timeZone   = null;
    private long     timeMillis = 0L; // ms since January 1, 1970, 00:00:00 GMT

    public DateTime()
    {
        this.setTimeMillis(getCurrentTimeMillis());
    }

    public DateTime(TimeZone tz)
    {
        this();
        this.setTimeZone(tz);
    }

    public DateTime(Date date)
    {
        this.setTimeMillis(date.getTime());
    }

    public DateTime(Date date, TimeZone tz)
    {
        this.setTimeMillis(date.getTime());
        this.setTimeZone(tz);
    }

    public DateTime(TimeZone tz, int year, int month1, int day)
    {
        this.setDate(tz, year, month1, day);
    }

    public DateTime(TimeZone tz, int year, int month1, int day, int hour24, int minute, int second)
    {
        this.setDate(tz, year, month1, day, hour24, minute, second);
    }

    public DateTime(long timeSec)
    {
        this.setTimeSec(timeSec);
    }

    public DateTime(long timeSec, TimeZone tz)
    {
        this.setTimeSec(timeSec);
        this.setTimeZone(tz);
    }

    public DateTime(String d)
        throws ParseException
    {
        this.setDate(d);
    }

    public DateTime(DateTime dt)
    {
        this.timeMillis = dt.timeMillis;
        this.timeZone   = dt.timeZone;
    }

    public DateTime(DateTime dt, long deltaOffsetSec)
    {
        this.timeMillis = dt.timeMillis + (deltaOffsetSec * 1000L);
        this.timeZone   = dt.timeZone;
    }

    // ------------------------------------------------------------------------

    public java.util.Date getDate()
    {
        return new java.util.Date(this.getTimeMillis());
    }

    public void setDate(TimeZone tz, int year, int month1, int day)
    {
        this.setDate(tz, year, month1, day, 12, 0, 0);
    }

    public void setDate(TimeZone tz, int year, int month1, int day, int hour24, int minute, int second)
    {
        int month0 = month1 - 1;
        this.setTimeZone(tz);
        Calendar cal = new GregorianCalendar(this._timeZone(tz));
        cal.set(year, month0, day, hour24, minute, second);
        Date date = cal.getTime();
        this.setTimeMillis(date.getTime());
    }

    public void setDate(String d)
        throws ParseException
    {
        this.setDate(d, (TimeZone)null);
    }

    public void setDate(String d, TimeZone dftTMZ)
        throws ParseException
    {
        String ds[] = StringTools.parseString(d, " _");
        String dt = (ds.length > 0)? ds[0] : "";
        String tm = (ds.length > 1)? ds[1] : "";
        String tz = (ds.length > 2)? ds[2] : "";
        // Valid format: "YYYY/MM/DD [HH:MM:SS] [PST]"

        /* time-zone */
        TimeZone timeZone = null;
        if (ds.length > 2) {
            if (tz.equals("+0000") || tz.equals("-0000")) {
                timeZone = DateTime.getGMTTimeZone();
            } else
            if (DateTime.isValidTimeZone(tz)) {
                timeZone = DateTime.getTimeZone(tz);
            } else {
                throw new ParseException("Invalid TimeZone: " + tz, 0);
            }
        } else
        if ((ds.length > 1) && DateTime.isValidTimeZone(tm)) {
            tz = tm;
            tm = "";
            timeZone = DateTime.getTimeZone(tz);
        } else {
            timeZone = (dftTMZ != null)? dftTMZ : this.getTimeZone();
        }
        this.setTimeZone(timeZone);
        Calendar calendar = new GregorianCalendar(timeZone);

        /* date */
        int yr = -1, mo = -1, dy = -1;
        int d1 = dt.indexOf('/'), d2 = (d1 > 0)? dt.indexOf('/', d1 + 1) : -1;
        if ((d1 > 0) && (d2 > d1)) {

            /* year */
            String YR = dt.substring(0, d1);
            yr = StringTools.parseInt(YR, -1);
            if ((yr >=  0) && (yr <= 49)) { yr += 2000; } else
            if ((yr >= 50) && (yr <= 99)) { yr += 1900; }
            if ((yr < 1900) || (yr > 2100)) {
                throw new ParseException("Date/Year out of range: " + YR, 0);
            }

            /* month */
            String MO = dt.substring(d1 + 1, d2);
            mo = StringTools.parseInt(MO, -1) - 1; // 0 indexed
            if ((mo < 0) || (mo > 11)) {
                throw new ParseException("Date/Month out of range: " + MO, 0);
            }

            /* seconds */
            String DY = dt.substring(d2 + 1);
            dy = StringTools.parseInt(DY, -1);
            if ((dy < 1) || (dy > 31)) {
                throw new ParseException("Date/Day out of range: " + DY, 0);
            }

        } else {

            throw new ParseException("Invalid date format (Y/M/D): " + dt, 0);

        }

        /* time */
        if (tm.equals("")) {            
            calendar.set(yr, mo, dy);

        } else {

            int hr = -1, mn = -1, sc = -1;
            int t1 = tm.indexOf(':'), t2 = (t1 > 0)? tm.indexOf(':', t1 + 1) : -1;
            if (t1 > 0) {

                /* hour */
                String HR = tm.substring(0, t1);
                hr = StringTools.parseInt(HR, -1);
                if ((hr < 0) || (hr > 23)) {
                    throw new ParseException("Time/Hour out of range: " + HR, 0);
                }
                if (t2 > t1) {

                    /* minute */
                    String MN = tm.substring(t1 + 1, t2);
                    mn = StringTools.parseInt(MN, -1);
                    if ((mn < 0) || (mn > 59)) {
                        throw new ParseException("Time/Minute out of range: " + MN, 0);
                    }

                    /* second */
                    String SC = tm.substring(t2 + 1);
                    sc = StringTools.parseInt(SC, -1);
                    if ((sc < 0) || (sc > 59)) {
                        throw new ParseException("Time/Second out of range: " + SC, 0);
                    }                   
                    calendar.set(yr, mo, dy, hr, mn, sc);

                } else {

                    /* minute */
                    String MN = tm.substring(t1 + 1);
                    mn = StringTools.parseInt(MN, -1);
                    if ((mn < 0) || (mn > 59)) {
                        throw new ParseException("Time/Minute out of range: " + MN, 0);
                    }                   
                    calendar.set(yr, mo, dy, hr, mn);
                }
            } else {

                throw new ParseException("Invalid time format (H:M:S): " + tm, 0);

            }
        }

        /* ok */
        this.setTimeMillis(calendar.getTime().getTime());

    }

    public boolean setParsedDate_formatted(String d) // does not work!
    {
        try {
            java.util.Date date = DateFormat.getDateInstance().parse(d);
            this.setTimeMillis(date.getTime());
            return true;
        } catch (ParseException pe) {
            logger.trace("Unable to parse date: " + d, pe);
            this.setTimeMillis(0L);
            return false;
        }
    }

    // ------------------------------------------------------------------------

    private int _get(TimeZone tz, int value)
    {
        return this.getCalendar(tz).get(value);
    }

    public Calendar getCalendar(TimeZone tz)
    {
        Calendar c = new GregorianCalendar(this._timeZone(tz));
        c.setTimeInMillis(this.getTimeMillis());
        return c;
    }
    public Calendar getCalendar()
    {
        return this.getCalendar(null);
    }

    public int getMonth0(TimeZone tz)
    {
        // return 0..11
        return this._get(tz, Calendar.MONTH); // 0 indexed
    }
    public int getMonth0()
    {
        return this.getMonth0(null);
    }

    public int getMonth1(TimeZone tz)
    {
        // return 1..12
        return this.getMonth0(tz) + 1;
    }
    public int getMonth1()
    {
        return this.getMonth1(null);
    }

    public int getDayOfMonth(TimeZone tz)
    {
        // return 1..31
        return this._get(tz, Calendar.DAY_OF_MONTH);
    }
    public int getDayOfMonth()
    {
        return this.getDayOfMonth(null);
    }

    public int getDaysInMonth(TimeZone tz)
    {
        return DateTime.getDaysInMonth(tz, this.getMonth1(tz), this.getYear(tz));
    }
    public int getDaysInMonth()
    {
        return this.getDaysInMonth(null);
    }

    public int getDayOfWeek(TimeZone tz)
    {
        // return 0..6
        return this._get(tz, Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
    }
    public int getDayOfWeek()
    {
        return this.getDayOfWeek(null);
    }

    public int getYear(TimeZone tz)
    {
        return this._get(tz, Calendar.YEAR);
    }
    public int getYear()
    {
        return this.getYear(null);
    }

    public boolean isLeapYear(TimeZone tz)
    {
        GregorianCalendar gc = (GregorianCalendar)this.getCalendar(tz);
        return gc.isLeapYear(gc.get(Calendar.YEAR));
    }
    public boolean isLeapYear()
    {
        return this.isLeapYear(null);
    }

    public int getHour24(TimeZone tz)
    {
        return this._get(tz, Calendar.HOUR_OF_DAY);
    }
    public int getHour24()
    {
        return this.getHour24(null);
    }

    public int getHour12(TimeZone tz)
    {
        return this._get(tz, Calendar.HOUR);
    }
    public int getHour12()
    {
        return this.getHour12(null);
    }

    public boolean isAM(TimeZone tz)
    {
        return this._get(tz, Calendar.AM_PM) == Calendar.AM;
    }
    public boolean isAM()
    {
        return this.isAM(null);
    }

    public boolean isPM(TimeZone tz)
    {
        return this._get(tz, Calendar.AM_PM) == Calendar.PM;
    }
    public boolean isPM()
    {
        return this.isPM(null);
    }

    public int getMinute(TimeZone tz)
    {
        return this._get(tz, Calendar.MINUTE);
    }
    public int getMinute()
    {
        return this.getMinute(null);
    }

    public int getSecond(TimeZone tz)
    {
        return this._get(tz, Calendar.SECOND);
    }
    public int getSecond()
    {
        return this.getSecond(null);
    }

    public boolean isDaylightSavings(TimeZone tz)
    {
        return _timeZone(tz).inDaylightTime(this.getDate());
    }
    public boolean isDaylightSavings()
    {
        return this.isDaylightSavings(null);
    }

    // ------------------------------------------------------------------------

    public long getTimeSec()
    {
        return this.getTimeMillis() / 1000L;
    }

    public void setTimeSec(long timeSec)
    {
        this.timeMillis = timeSec * 1000L;
    }

    // ------------------------------------------------------------------------

    public long getTimeMillis()
    {
        return this.timeMillis;
    }

    public void setTimeMillis(long timeMillis)
    {
        this.timeMillis = timeMillis;
    }

    // ------------------------------------------------------------------------

    /* return time at start of day */
    public long getDayStart(TimeZone tz)
    {
        if (tz == null) { tz = _timeZone(tz); }
        Calendar c  = this.getCalendar(tz);
        Calendar nc = new GregorianCalendar(tz);
        nc.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return nc.getTime().getTime() / 1000L;
    }
    public long getDayStart()
    {
        return this.getDayStart(null);
    }

    /* return time at end of day */
    public long getDayEnd(TimeZone tz)
    {
        if (tz == null) { tz = _timeZone(tz); }
        Calendar c  = this.getCalendar(tz);
        Calendar nc = new GregorianCalendar(tz);
        nc.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        return nc.getTime().getTime() / 1000L;
    }
    public long getDayEnd()
    {
        return this.getDayEnd(null);
    }

    // ------------------------------------------------------------------------

    /* return time at start of month */
    public long getMonthStart(TimeZone tz, int deltaMo)
    {
        if (tz == null) { tz = _timeZone(tz); }
        Calendar c  = this.getCalendar(tz);
        int YY = c.get(Calendar.YEAR);
        int MM = c.get(Calendar.MONTH); // 0..11
        if (deltaMo != 0) {
            MM += deltaMo;
            if (MM < 0) {
                YY += (MM - 11) / 12;
                MM %= 12;
                if (MM < 0) {
                    MM += 12;
                }
            } else {
                YY += MM / 12;
                MM %= 12;
            }
        }
        int DD = 1;
        Calendar nc = new GregorianCalendar(tz);
        nc.set(YY, MM, DD, 0, 0, 0);
        return nc.getTime().getTime() / 1000L;
    }
    public long getMonthStart(TimeZone tz)
    {
        return this.getMonthStart(tz, 0);
    }
    public long getMonthStart(int deltaMo)
    {
        return this.getMonthStart(null, deltaMo);
    }
    public long getMonthStart()
    {
        return this.getMonthStart(null, 0);
    }

    /* return time at end of month */
    public long getMonthEnd(TimeZone tz, int deltaMo)
    {
        if (tz == null) { tz = _timeZone(tz); }
        Calendar c  = this.getCalendar(tz);
        int YY = c.get(Calendar.YEAR);
        int MM = c.get(Calendar.MONTH); // 0..11
        if (deltaMo != 0) {
            MM += deltaMo;
            if (MM < 0) {
                YY += (MM - 11) / 12;
                MM %= 12;
                if (MM < 0) {
                    MM += 12;
                }
            } else {
                YY += MM / 12;
                MM %= 12;
            }
        }
        int DD = DateTime.getDaysInMonth(tz, MM + 1, YY);
        Calendar nc = new GregorianCalendar(tz);
        nc.set(YY, MM, DD, 23, 59, 59);
        return nc.getTime().getTime() / 1000L;
    }
    public long getMonthEnd(TimeZone tz)
    {
        return this.getMonthEnd(tz, 0);
    }
    public long getMonthEnd(int deltaMo)
    {
        return this.getMonthEnd(null, deltaMo);
    }
    public long getMonthEnd()
    {
        return this.getMonthEnd(null, 0);
    }

    // ------------------------------------------------------------------------

    public long getDayStartGMT()
    {
        // GMT TimeZone
        return (this.getTimeSec() / SECONDS_PER_DAY) * SECONDS_PER_DAY;
    }

    public long getDayEndGMT()
    {
        // GMT TimeZone
        return this.getDayStartGMT() + SECONDS_PER_DAY - 1L;
    }

    // ------------------------------------------------------------------------

    public boolean after(DateTime dt)
    {
        return this.after(dt, false);
    }

    public boolean after(DateTime dt, boolean inclusive)
    {
        if (dt == null) {
            return true; // arbitrary
        } else
        if (inclusive) {
            return (this.getTimeMillis() >= dt.getTimeMillis());
        } else {
            return (this.getTimeMillis() > dt.getTimeMillis());
        }
    }

    public boolean before(DateTime dt)
    {
        return this.before(dt, false);
    }

    public boolean before(DateTime dt, boolean inclusive)
    {
        if (dt == null) {
            return false; // arbitrary
        } else
        if (inclusive) {
            return (this.getTimeMillis() <= dt.getTimeMillis());
        } else {
            return (this.getTimeMillis() < dt.getTimeMillis());
        }
    }

    public boolean equals(Object obj)
    {
        if (obj instanceof DateTime) {
            return (this.getTimeMillis() == ((DateTime)obj).getTimeMillis());
        } else {
            return false;
        }
    }

    public int compareTo(Object other)
    {
        if (other instanceof DateTime) {
            long otherTime = ((DateTime)other).getTimeMillis();
            long thisTime  = this.getTimeMillis();
            if (thisTime < otherTime) { return -1; }
            if (thisTime > otherTime) { return  1; }
            return 0;
        } else {
            return -1;
        }
    }

    // ------------------------------------------------------------------------

    public interface TimeZoneProvider
    {
        public TimeZone getTimeZone();
    }

    protected TimeZone _timeZone(TimeZone tz)
    {
        return (tz != null)? tz : this.getTimeZone();
    }

    public TimeZone getTimeZone()
    {
        return (this.timeZone != null)? this.timeZone : DateTime.getDefaultTimeZone();
    }

    public String getTimeZoneShortName()
    {
        boolean dst = this.isDaylightSavings();
        return this.getTimeZone().getDisplayName(dst, TimeZone.SHORT);
    }

    public void setTimeZone(TimeZone tz)
    {
        this.timeZone = tz;
    }

    public void setTimeZone(String tz)
    {
        this.setTimeZone(DateTime.getTimeZone(tz, null));
    }

    public static String[] readTimeZones(File tmzFile)
    {
        if ((tmzFile != null) && tmzFile.exists()) {
            java.util.List tzList = new Vector();
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(tmzFile));
                for (;;) {
                    String rline = br.readLine();
                    if (rline == null) { break; }
                    String tline = rline.trim();
                    if (!tline.equals("") && !tline.startsWith("#")) {
                        tzList.add(tline);
                    }
                }
                return (String[])tzList.toArray(new String[tzList.size()]);
            } catch (IOException ioe) {
                logger.error("Unable to read file: " + tmzFile + " [" + ioe + "]");
                return null;
            } finally {
                if (br != null) { try { br.close(); } catch (IOException ioe) {/*ignore*/} }
            }
        }
        return null;
    }

    // ------------------------------------------------------------------------

    public static boolean isValidTimeZone(String tzid)
    {
        if ((tzid == null) || tzid.equals("")) {
            return false;
        } else
        if (tzid.equalsIgnoreCase("GMT") ||
            tzid.equalsIgnoreCase("UTC") ||
            tzid.equalsIgnoreCase("Zulu")) {
            return true;
        } else {
            // "TimeZone.getTimeZone" returns GMT for invalid timezones
            TimeZone tmz = TimeZone.getTimeZone(tzid);
            if (tmz.getRawOffset() != 0) { // ie. !(GMT+0)
                return true; // must be a valid time-zone
            } else {
                return false; // GMT+0 (must be invalid)
            }
        }
    }

    public static TimeZone getTimeZone(String tzid, TimeZone dft)
    {
        if ((tzid == null) || tzid.equals("")) {
            return dft;
        } else
        if (tzid.equalsIgnoreCase("GMT") ||
            tzid.equalsIgnoreCase("UTC") ||
            tzid.equalsIgnoreCase("Zulu")) {
            return TimeZone.getTimeZone(tzid);
        } else {
            // "TimeZone.getTimeZone" returns GMT for invalid timezones
            TimeZone tmz = TimeZone.getTimeZone(tzid);
            if (tmz.getRawOffset() != 0) { // ie. !(GMT+0)
                return tmz; // must be a valid time-zone
            } else {
                return dft; // GMT+0 (must be invalid)
            }
        }
    }

    public static TimeZone getTimeZone(String tzid)
    {		
        if ((tzid == null) || tzid.equals("")) {
            return TimeZone.getDefault(); // local system default time-zone
        } else {			
            // 'TimeZone' will return GMT if an invalid name is specified
            return TimeZone.getTimeZone(tzid);
        }
    }

    public static TimeZone getDefaultTimeZone()
    {
        return DateTime.getTimeZone(null);
    }

    public static TimeZone getGMTTimeZone()
    {
        return TimeZone.getTimeZone(GMT_TIMEZONE);
    }

    // ------------------------------------------------------------------------

    private static SimpleDateFormat simpleFormatter = null;
    public String toString()
    {
        if (simpleFormatter == null) {
            // eg. "Sun Mar 26 12:38:12 PST 2006"
            simpleFormatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        }
        synchronized (simpleFormatter) {
            simpleFormatter.setTimeZone(this.getTimeZone());
            return simpleFormatter.format(this.getDate());
        }
    }

    // ------------------------------------------------------------------------

    public String format(String fmt, TimeZone tz, StringBuffer sb)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        sdf.setTimeZone(this._timeZone(tz));
        if (sb == null) { sb = new StringBuffer(); }
        sdf.format(this.getDate(), sb, new FieldPosition(0));
        return sb.toString();
    }

    public String format(String fmt, TimeZone tz)
    {
        return this.format(fmt, tz, null);
    }

    public String format(TimeZone tz)
    {   // ie. "Oct 22, 2003 7:23:18 PM"
        return this.format("MMM dd, yyyy HH:mm:ss z", tz, null);        
    }

    public String format(String fmt)
    {
        return this.format(fmt, null, null);
    }

    public String gmtFormat()
    {
        return this.gmtFormat("dd/MM/yyyy HH:mm:ss 'GMT'");
    }

    public String gmtFormat(String fmt)
    {
        return this.format(fmt, DateTime.getGMTTimeZone(), null);
    }

    public String gmtFormat(String fmt, StringBuffer sb)
    {
        return this.format(fmt, DateTime.getGMTTimeZone(), sb);
    }

    // ------------------------------------------------------------------------

    public Object clone()
    {
        return new DateTime(this);
    }

    // ------------------------------------------------------------------------

    protected static String encodeHourMinuteSecond(long tod, String fmt)
    {
        StringBuffer sb = new StringBuffer();
        int h = (int)(tod / (60L * 60L)), m = (int)((tod / 60L) % 60), s = (int)(tod % 60);
        if (fmt != null) {
            // format: "00:00:00", "00:00"
            String f[] = StringTools.parseString(fmt, ':');
            if (f.length > 0) { sb.append(StringTools.format(h,f[0])); }              // hours
            if (f.length > 1) { sb.append(':').append(StringTools.format(m,f[1])); }  // minutes
            if (f.length > 2) { sb.append(':').append(StringTools.format(s,f[2])); }  // seconds
        } else {
            sb.append(h);
            sb.append(':').append(StringTools.format(m,"00"));
            if (s > 0) {
                sb.append(':').append(StringTools.format(s,"00"));
            }
        }
        return sb.toString();
    }

    protected static int parseHourMinuteSecond(String hms)
    {
        return parseHourMinuteSecond(hms, 0);
    }

    protected static int parseHourMinuteSecond(String hms, int dft)
    {
        String a[] = StringTools.parseString(hms,":");
        if (a.length <= 1) {
            // assume all seconds
            return StringTools.parseInt(hms, dft);
        } else
        if (a.length == 2) {
            // assume hh:mm
            int h = StringTools.parseInt(a[0], -1);
            int m = StringTools.parseInt(a[1], -1);
            return ((h >= 0) && (m >= 0))? (((h * 60) + m) * 60) : dft;
        } else { // (a.length >= 3)
            // assume hh:mm:ss
            int h = StringTools.parseInt(a[0], -1);
            int m = StringTools.parseInt(a[1], -1);
            int s = StringTools.parseInt(a[2], -1);
            return ((h >= 0) && (m >= 0) && (s >= 0))? ((((h * 60) + m) * 60) + s) : dft;
        }
    }

    // ------------------------------------------------------------------------
    public static void main(String argv[])
    {
        RTConfig.setCommandLineArgs(argv);

        /* All available TimeZones */
        if (RTConfig.getBoolean("tzlist",false)) {
            String tzid[] = (String[])ListTools.sort(TimeZone.getAvailableIDs(), null);
            for (int i = 0; i < tzid.length; i++) {
                TimeZone tz       = TimeZone.getTimeZone(tzid[i]);
                String shortName  = tz.getDisplayName(false, TimeZone.SHORT);
                String longName   = tz.getDisplayName(false, TimeZone.LONG);               
                Print.sysPrintln(tzid[i] + ": " + shortName + " / " + longName); // + " [" + testName + "]");
            }
            System.exit(0);
        }

        /* Read TimeZones from file */
        File tmzFile = RTConfig.getFile("tmzfile",null);
        if (tmzFile != null) {
            long nowTime = DateTime.getCurrentTimeSec();
            String TMZ_NAME[] = DateTime.readTimeZones(tmzFile);
            for (int i = 0; i < TMZ_NAME.length; i++) {
                String tzname    = StringTools.leftJustify(TMZ_NAME[i], 28);
                TimeZone tz      = TimeZone.getTimeZone(TMZ_NAME[i]);
                String idName    = tz.getID().equals(TMZ_NAME[i])? "*" : tz.getID();
                String shortName = StringTools.leftJustify(tz.getDisplayName(false, TimeZone.SHORT), 5);
                String longName  = tz.getDisplayName(false, TimeZone.LONG);
                int rawOfsMIN    = tz.getOffset(nowTime) / (1000 * 60);
                int rawOfsHH     = Math.abs(rawOfsMIN) / 60;
                int rawOfsMM     = Math.abs(rawOfsMIN) % 60;
                String gmtStr    = "GMT"+((rawOfsMIN>=0)?"+":"-")+StringTools.format(rawOfsHH,"00")+":"+StringTools.format(rawOfsMM,"00");
                Print.sysPrintln(tzname+" ["+gmtStr+"]: " + idName + " , " + shortName + " , " + longName);
            }
            System.exit(0);
        }

        /* TimeZone */
        String tmz = RTConfig.getString("tmz",null);
        if ((tmz != null) && !tmz.equals("")) {
            long nowTime = DateTime.getCurrentTimeSec();
            TimeZone tz = TimeZone.getTimeZone(tmz);
            int rawOfsMIN  = (tz.getOffset(nowTime) + tz.getDSTSavings()) / (1000 * 60);
            int rawOfsHH   = Math.abs(rawOfsMIN) / 60;
            int rawOfsMM   = Math.abs(rawOfsMIN) % 60;
            String gmtStr  = "GMT"+((rawOfsMIN>=0)?"+":"-")+StringTools.format(rawOfsHH,"00")+":"+StringTools.format(rawOfsMM,"00");
            Print.sysPrintln(tmz + " [" + gmtStr + "]: " + tz);
            Print.sysPrintln("Time : " + (new DateTime(nowTime,tz)));
            Print.sysPrintln("GMT  : " + (new DateTime(nowTime,DateTime.getGMTTimeZone())));
            System.exit(0);
        }

        /* time zones */
        String tzStr   = RTConfig.getString("tz",null);
        TimeZone tz    = (tzStr != null)? DateTime.getTimeZone(tzStr) : DateTime.getDefaultTimeZone();
        TimeZone pstTZ = DateTime.getTimeZone("US/Pacific");
        TimeZone gmtTZ = DateTime.getGMTTimeZone();

        /* time */
        long now = RTConfig.getLong("time",-1L);
        if (now <= 0L) {
            String stime = RTConfig.getString("stime","");
            if (!stime.equals("")) {
                DateTime dtTime = new DateTime();
                try {
                    dtTime.setDate(stime,pstTZ);
                    Print.sysPrintln("Parsed '" + stime + "' ==> " + dtTime);
                    now = dtTime.getTimeSec();
                } catch (ParseException pde) {
                    Print.sysPrintln("Unable to parse date: " + stime);
                }
            }
            if (now <= 0L) {
                now = DateTime.getCurrentTimeSec();
            }
        }

        /* display dates */
        DateTime gmtNowDt = new DateTime(now,gmtTZ);
        DateTime pstNowDt = new DateTime(now,pstTZ);
        DateTime pstStrDt = new DateTime(pstNowDt.getDayStart(),pstTZ);
        DateTime pstEndDt = new DateTime(pstNowDt.getDayEnd(),pstTZ);
        DateTime tmzNowDt = new DateTime(now,tz);
        Print.sysPrintln("");
        logger.trace("GMT time  : " + gmtNowDt + " [" + now + ":0x" + StringTools.toHexString(now,32) + "]");
        logger.trace("TZ  time  : " + tmzNowDt);
        logger.trace("PST time  : " + pstNowDt);
        logger.trace("PST start : " + pstStrDt + " [" + pstStrDt.getTimeSec() + "]");
        logger.trace("PST end   : " + pstEndDt + " [" + pstEndDt.getTimeSec() + "]");

    }

}
