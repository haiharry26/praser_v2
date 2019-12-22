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
//  General Printing/Logging utilities.
// ----------------------------------------------------------------------------
// Change History:
//  2006/03/26  Martin D. Flynn
//     -Initial release
//  2006/04/23  Martin D. Flynn
//     -Updated to support a more granular message logging.  Eventually, this
//      should be modified to support Log4J.
//  2006/06/30  Martin D. Flynn
//     -Repackaged to "org.opengts.util"
//  2007/03/13  Martin D. Flynn
//     -Optimized calls to 'getLogLevel' and 'getLogHeaderLevel'
//  2007/09/16  Martin D. Flynn
//     -Moved all runtime configuration initialization to 'initRTConfig()'
// ----------------------------------------------------------------------------
package com.digitrinity.parser.dateutil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;

public class Print
{

    // ------------------------------------------------------------------------

    public  static final int    LOG_UNDEFINED       = -1;
    public  static final int    LOG_OFF             = 0;
    public  static final int    LOG_FATAL           = 1;
    public  static final int    LOG_ERROR           = 2;
    public  static final int    LOG_WARN            = 3;
    public  static final int    LOG_INFO            = 4;
    public  static final int    LOG_DEBUG           = 5;
    public  static final int    LOG_ALL             = 6;

    // ------------------------------------------------------------------------

    private static final String _JAVA               = ".java";
    
    // ------------------------------------------------------------------------

    private static PrintStream stdout               = null;
    private static PrintStream stderr               = null;
    private static PrintStream sysStdout            = null;
    private static PrintStream sysStderr            = null;
    
    // ------------------------------------------------------------------------

    private static boolean printLogIncludeFrame     = true;
    private static boolean printLogIncludeDate      = true;
    private static boolean printEmailExceptions     = false;
    private static String  printLogName             = null;
    private static File    printLogFile             = null;
    private static long    printMaxLogFileSize      = 0L;
    private static int     printLogLevel            = LOG_INFO;
    private static int     printLogHeaderLevel      = LOG_INFO;

    public static void initRTConfig()
    {
        if (RTConfig.isInitialized()) {
            
            /* logging level */
            printLogLevel        = Print.parseLogLevel(RTConfig.getString(RTKey.LOG_LEVEL,null));
            printLogHeaderLevel  = Print.parseLogLevel(RTConfig.getString(RTKey.LOG_LEVEL_HEADER,null));

            /* log message format */
            printLogIncludeFrame = RTConfig.getBoolean(RTKey.LOG_INCL_STACKFRAME,false) || Print.isDebugLoggingLevel();
            printLogIncludeDate  = RTConfig.getBoolean(RTKey.LOG_INCL_DATE,false);
            
            /* email exceptions? */
            printEmailExceptions = RTConfig.getBoolean(RTKey.LOG_EMAIL_EXCEPTIONS,false);
            
            /* max log file size */
            printMaxLogFileSize  = RTConfig.getLong(RTKey.LOG_FILE_MAX_SIZE,0L);
            
            /* log name */
            printLogName = RTConfig.getString(RTKey.LOG_NAME,null);
            if ((printLogName == null) || printLogName.equals("")) {
                printLogName = RTConfig.getString(RTKey.WEBAPP_CONTEXT_NAME, null);
                if ((printLogName == null) || printLogName.equals("")) {
                    String cn = RTConfig.getString(RTKey.MAIN_CLASS, null);
                    if (cn != null) {
                        int p = cn.lastIndexOf(".");
                        if (p >= 0) {
                            printLogName = cn.substring(p+1);
                        }
                    }
                }
                if ((printLogName == null) || printLogName.equals("")) {
                    printLogName = RTConfig.isWebApp()? "webapp" : "main";
                }
                RTConfig.setString(RTKey.LOG_NAME, printLogName);
            }

            /* log file */
            String fileStr = RTConfig.insertKeyValues(RTConfig.getString(RTKey.LOG_FILE,null));
            printLogFile = ((fileStr != null) && !fileStr.equals(""))? new File(fileStr) : null;
            
            

        } else {
            
            Print.sysPrintln("ERROR: RTConfig has not been initialized!");
            
        }
    }

    protected static boolean _includeStackFrame()
    {
        return printLogIncludeFrame;
    }

    protected static boolean _includeDate()
    {
        return printLogIncludeDate;
    }

    protected static boolean _emailExceptions()
    {
        return printEmailExceptions;
    }

    // ------------------------------------------------------------------------

    private static String localhostName = null;

    public static String getHostName()
    {
        /* host name */
        if (Print.localhostName == null) {
            try {
                String hd = InetAddress.getLocalHost().getHostName();
                int p = hd.indexOf(".");
                Print.localhostName = (p >= 0)? hd.substring(0,p) : hd;
            } catch (UnknownHostException uhe) {
                Print.localhostName = "UNKNOWN";
            }
        }
        return Print.localhostName;
    }

    // ------------------------------------------------------------------------

    public static String formatDate(String fmt)
    {
        return (new DateTime()).format(fmt,null);        
    }

    // ------------------------------------------------------------------------

    public static void setStdout(PrintStream out)
    {
        Print.stdout = out;
    }

    public static void setStderr(PrintStream err)
    {
        Print.stderr = err;
    }

    public static PrintStream getStdout()
    {
        return (Print.stdout != null)? Print.stdout : System.out;
    }

    public static PrintStream getStderr()
    {
        return (Print.stdout != null)? Print.stderr : System.err;
    }

    // ------------------------------------------------------------------------

    protected static String _getStackFrame(int frame)
    {

        /* extract stack frame */
        Throwable t = new Throwable();
        t.fillInStackTrace();
        StackTraceElement st[] = t.getStackTrace();
        StackTraceElement sf = (st != null)? st[frame + 1] : null;

        /* no stack frame? */
        if (sf == null) {
            return "?";
        }

        /* get file */
        String clazz = sf.getClassName();
        String file  = sf.getFileName();
        if (file == null) {
            // Java code was compiled with 'debug=false'
            int p = 0;
            for (; (p < clazz.length()) && !Character.isUpperCase(clazz.charAt(p)); p++);
            if (p < clazz.length()) { clazz = clazz.substring(p); }
        } else
        if (file.toLowerCase().endsWith(_JAVA)) { 
            file = file.substring(0, file.length() - _JAVA.length()); 
            int p = clazz.indexOf(file);
            if (p >= 0) { clazz = clazz.substring(p); }
        }

        /* format frame description */
        StringBuffer sb = new StringBuffer();
        sb.append(clazz);
        sb.append(".").append(sf.getMethodName());
        sb.append(":").append(sf.getLineNumber());

        return sb.toString();
    }

    // ------------------------------------------------------------------------

    public static void _println(String msg)
    {
        // Does not use RTConfig
        Print._println(null, msg);
    }
    
    public static void _println(PrintStream ps, String msg)
    {
        // Does not use RTConfig
        Print._print(ps, 1, true, msg + "\n");
    }

    protected static void _println(PrintStream ps, int frame, boolean printFrame, String msg)
    {
        // Does not use RTConfig
        Print._print(ps, frame + 1, printFrame, msg + "\n");
    }

    protected static void _println(PrintStream ps, int frame, String msg)
    {
        Print._print(ps, frame + 1, _includeStackFrame(), msg + "\n");
    }

    protected static void _print(PrintStream ps, int frame, String msg)
    {
        Print._print(ps, frame + 1, _includeStackFrame(), msg);
    }

    protected static void _print(PrintStream ps, int frame, boolean printFrame, String msg)
    {
        // - use of RTConfig is NOT allowed in this method!
        // - if not writing to 'Print.stdout', then we really want to open/close this file

        /* Print stream */
        PrintStream out = (ps != null)? ps : Print.getStdout();

        /* write */
        if (out != null) {
            StringBuffer sb = new StringBuffer();
            if (printFrame) {
                sb.append("[");
                sb.append(_getStackFrame(frame + 1));
                sb.append("] ");
            }
            sb.append(msg);
            out.print(sb.toString());
            out.flush();
        }

    }

    // ------------------------------------------------------------------------

    public static void setSysStdout(PrintStream out)
    {
        Print.sysStdout = out;
    }

    public static PrintStream getSysStdout()
    {
        return (Print.sysStdout != null)? Print.sysStdout : Print.getStdout();
    }

    public static void setSysStderr(PrintStream out)
    {
        Print.sysStderr = out;
    }

    public static PrintStream getSysStderr()
    {
        return (Print.sysStderr != null)? Print.sysStderr : Print.getStderr();
    }

    // ------------------------------------------------------------------------

    public static void sysPrint(String msg)
    {
        PrintStream out = Print.getSysStdout();
        Print._print(out, 1, false, msg);
    }

    public static void sysPrint(StringBuffer msg)
    {
        PrintStream out = Print.getSysStdout();
        Print._print(out, 1, false, msg.toString());
    }

    public static void sysPrintln(String msg)
    {
        PrintStream out = Print.getSysStdout();
        Print._println(out, 1, false, msg);
    }

    public static void sysPrintln(StringBuffer msg)
    {
        PrintStream out = Print.getSysStdout();
        Print._println(out, 1, false, msg.toString());
    }

    // ------------------------------------------------------------------------

    public static void errPrint(String msg)
    {
        PrintStream out = Print.getSysStderr();
        Print._print(out, 1, false, msg);
    }

    public static void errPrint(StringBuffer msg)
    {
        PrintStream out = Print.getSysStderr();
        Print._print(out, 1, false, msg.toString());
    }

    public static void errPrintln(String msg)
    {
        PrintStream out = Print.getSysStderr();
        Print._println(out, 1, false, msg);
    }

    public static void errPrintln(StringBuffer msg)
    {
        PrintStream out = Print.getSysStderr();
        Print._println(out, 1, false, msg.toString());
    }

    // ------------------------------------------------------------------------

    public static void print(String msg)
    {
        Print._print(null, 1, false, msg);
    }

    public static void print(StringBuffer msg)
    {
        Print._print(null, 1, false, msg.toString());
    }

    public static void println(String msg)
    {
        Print._println(null, 1, false, msg);
    }

    public static void println(StringBuffer msg)
    {
        Print._println(null, 1, false, msg.toString());
    }

    // ------------------------------------------------------------------------
    
    protected static void _logStackTrace(int level, int frame, String msg, Throwable t)
    {

        /* log stack trace */
        Print._log(level, frame + 1, msg);
        try {
            PrintStream out = Print.openLogFile();
            _printStackTrace(out, frame + 1, null, t);
        } catch (Throwable loge) {
            _printStackTrace(null, frame + 1, null, t);
        } finally {
            Print.closeLogFile();
        }
        
        /* email */
        if (_emailExceptions()) {
            Print.sysPrintln("EMailing error...");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            PrintStream out = new PrintStream(bos);
            String host = Print.getHostName();
            
            /* include hostname */
            out.println("From host: " + host);
            
            /* include stacktrace */
            _printStackTrace(out, frame + 1, msg, t);
            
            /* close and send email */
            out.close(); // may not be necessary           
        }
    }
    
    protected static void _printStackTrace(PrintStream out, int frame, String msg, Throwable t)
    {
        
        /* get default print stream */
        if (out == null) {
            /* first try default stdout */
            out = Print.getStdout(); 
            if (out == null) {
                // failing that, set to stderr
                out = System.err;
            }
        }
        
        /* print stack trace */
        if (msg != null) {
            boolean printExceptionFrame = true;
            Print._println(out, frame + 1, printExceptionFrame, msg);
        }
        if (t == null) {
            t = new Throwable();
            t.fillInStackTrace();
            StackTraceElement oldst[] = t.getStackTrace();
            StackTraceElement newst[] = new StackTraceElement[oldst.length - (frame + 1)];
            System.arraycopy(oldst, frame + 1, newst, 0, newst.length);
            t.setStackTrace(newst);
        }
        t.printStackTrace(out);
        if (t instanceof SQLException) {
            SQLException sqe = ((SQLException)t).getNextException();
            for (; (sqe != null); sqe = sqe.getNextException()) { 
                sqe.printStackTrace(out); 
            }
        }
        
    }

    // ------------------------------------------------------------------------

    public static void logNotImplemented(String msg)
    {
        Print._logStackTrace(LOG_ERROR, 1, "Feature Not Implemented: " + msg, null);
    }

    public static void logException(String msg, Throwable t)
    {
        Print._logStackTrace(LOG_ERROR, 1, "Exception: " + msg, t);
    }

    public static void logStackTrace(String msg, Throwable t)
    {
        Print._logStackTrace(LOG_INFO, 1, "Stacktrace: " + msg, t);
    }

    public static void logStackTrace(Throwable t)
    {
        Print._logStackTrace(LOG_INFO, 1, "Stacktrace: ", t);
    }

    public static void logStackTrace(String msg)
    {
        Print._logStackTrace(LOG_INFO, 1, "Stacktrace: " + msg, null);
    }

    // ------------------------------------------------------------------------
    
    public static void logSQLError(int frame, String msg, SQLException sqe)
    {
        PrintStream ps = null;
        Print._log(LOG_ERROR, frame + 1, "==> SQLException: " + msg);
        while (sqe != null) {
            Print._log(LOG_ERROR, frame + 1, "Message:   " + sqe.getMessage());
            Print._log(LOG_ERROR, frame + 1, "SQLState:  " + sqe.getSQLState());
            Print._log(LOG_ERROR, frame + 1, "ErrorCode: " + sqe.getErrorCode());            
            Print._printStackTrace(ps, frame + 1, sqe.toString(), sqe);           
            sqe = sqe.getNextException();
        }
    }

    public static void logSQLError(SQLException sqe)
    {
        Print.logSQLError(1, "", sqe);
    }

    public static void logSQLError(String msg, SQLException sqe)
    {
        Print.logSQLError(1, msg, sqe);
    }

    // ------------------------------------------------------------------------

    private static Object       logLock             = new Object();
    private static PrintStream  logOutput           = null;
    private static long         logRefCount         = 0L;
    
    public static void setLogFile(File file)
    {
        printLogFile = file;
    }
    
    public static File getLogFile()
    {
        return printLogFile;
    }
    
    private static int openLogFile_recursion = 0;
    protected static PrintStream openLogFile()
    {
        // Do not make calls to "logXXXXXX" from within this method (infinite recursion could result)
        // Calls to 'println' and 'sysPrintln' are ok.

        /* check to see if this has been called before RTConfig has completed initialization */
        if (!RTConfig.isInitialized()) {
            return System.err;
        } 

        /* return log PrintStream */
        PrintStream out = null;
        synchronized (Print.logLock) {
            if (openLogFile_recursion > 0) {
                Print.sysPrintln("[Print.openLogFile] Recursive call to 'openLogFile'!!!");
                Throwable t = new Throwable();
                t.fillInStackTrace();
                t.printStackTrace();
                return System.err;
            } else {
                openLogFile_recursion++;
                
                /* increment log counter */
                Print.logRefCount++;
    
                /* get log file */
                if (Print.logOutput != null) {
                    
                    /* already open */
                    out = Print.logOutput;
                    
                } else {
    
                    /* get/return log file */
                    File file = Print.getLogFile();
                    if ((file == null) || file.toString().equals("")) {
                        out = System.err;
                    } else
                    if (file.isDirectory()) {
                        Print.setLogFile(null);
                        Print.sysPrintln("ERROR: Invalid Print log file specification: " + file);
                        out = System.err;
                    } else {
                        if (file.exists()) {
                            long maxLogFileSize = printMaxLogFileSize;
                            if ((maxLogFileSize > 8000L) && (file.length() > maxLogFileSize)) {
                                String bkuName = file.getAbsolutePath() + "." + Print.formatDate("yyMMdd");
                                File bkuFile = new File(bkuName);
                                for (int i = 1; bkuFile.exists(); i++) { bkuFile = new File(bkuName + "." + i); }
                                file.renameTo(bkuFile);
                            }
                        }
                        try {
                            out = new PrintStream(new FileOutputStream(file,true));
                        } catch (IOException ioe) {
                            Print.setLogFile(null);
                            Print.sysPrintln("ERROR: Unable to open Print log file: " + file);
                            out = System.err;
                        }
                    }
                    Print.logOutput = out;
                
                }
            
                openLogFile_recursion--;
            }
        }
        return out;
    }
    
    protected static void closeLogFile()
    {
        synchronized (Print.logLock) {
            
            /* decrement log counter */
            Print.logRefCount--;
            if (Print.logRefCount < 0) { Print.logRefCount = 0L; }
            
            /* close */
            if ((Print.logRefCount == 0L) && (Print.logOutput != null)) {
                // don't close if stderr or stdout
                if ((Print.logOutput != System.out) && (Print.logOutput != System.err)) {
                    try {
                        Print.logOutput.close();
                    } catch (Throwable t) {
                        Print.sysPrintln("Unable to close log file: " + t);
                    }
                }
                Print.logOutput = null;
            }
            
        }
    }

    // ------------------------------------------------------------------------

    public static void setLogLevel(int level, boolean inclDate, boolean inclFrame)
    {
        Print.setLogLevel(level);
        printLogIncludeDate = inclDate;
        printLogIncludeFrame = inclFrame;
    }

    public static void setLogLevel(int level)
    {
        if (level < LOG_OFF) {
            level = LOG_OFF;
        } else 
        if (level > LOG_ALL) {
            level = LOG_ALL;
        }
        printLogLevel = level;
    }

    public static int getLogLevel()
    {
        return printLogLevel;
    }
    
    public static boolean isDebugLoggingLevel()
    {
        return (Print.getLogLevel() >= Print.LOG_DEBUG);
    }

    public static void setLogHeaderLevel(int level)
    {
        if (level < LOG_OFF) {
            level = LOG_OFF;
        } else 
        if (level > LOG_ALL) {
            level = LOG_ALL;
        }
        printLogHeaderLevel = level;
    }

    public static int getLogHeaderLevel()
    {
        return printLogHeaderLevel;
    }

    public static String getLogLevelString(int level)
    {
        if (level <= LOG_OFF) { return "OFF"; }
        switch (level) {
            case LOG_FATAL: return "FATAL";
            case LOG_ERROR: return "ERROR";
            case LOG_WARN : return "WARN ";
            case LOG_INFO : return "INFO ";
            case LOG_DEBUG: return "DEBUG";
        }
        return "ALL";
    }

    public static int parseLogLevel(String val)
    {
        String v = (val != null)? val.toUpperCase() : null;
        if ((v == null) || v.equals("")) {
            return LOG_OFF;
        } else
        if (Character.isDigit(v.charAt(0))) {
            int lvl = StringTools.parseInt(v.substring(0,1),LOG_ALL);
            if (lvl < LOG_OFF) {
                return LOG_OFF;
            } else 
            if (lvl > LOG_ALL) {
                return LOG_ALL;
            } else {
                return lvl;
            }
        } else
        if (v.startsWith("OFF")) {
            return LOG_OFF;
        } else
        if (v.startsWith("FAT")) {
            return LOG_FATAL;
        } else
        if (v.startsWith("ERR")) {
            return LOG_ERROR;
        } else
        if (v.startsWith("WAR")) {
            return LOG_WARN;
        } else
        if (v.startsWith("INF")) {
            return LOG_INFO;
        } else
        if (v.startsWith("DEB")) {
            return LOG_DEBUG;
        } else {
            return LOG_ALL;
        }
    }

    // ------------------------------------------------------------------------

    public static void log(int level, String msg)
    {
        Print._log(level, 1, msg);
    }

    public static void logFatal(String msg)
    {
        Print._log(LOG_FATAL, 1, msg);
    }

    public static void logError(String msg)
    {
        Print._log(LOG_ERROR, 1, msg);
    }

    public static void logWarn(String msg)
    {
        Print._log(LOG_WARN, 1, msg);
    }

    public static void logInfo(String msg)
    {
        Print._log(LOG_INFO, 1, msg);
    }

    public static void logDebug(String msg)
    {
        Print._log(LOG_DEBUG, 1, msg);
    }

    protected static void _log(int level, int frame, String msg)
    {

        /* pertinent level? */
        if (level > Print.getLogLevel()) {
            return;
        }

        /* message accumulator */
        StringBuffer logMsg = new StringBuffer();

        /* log message */
        if (level <= Print.getLogHeaderLevel()) {
            // Print this 'header' info for logged messages with a level < 'headerLevel'
            // ie. print header for errors/warnings, but not for info/debug
            logMsg.append("[");
            logMsg.append(Print.getLogLevelString(level));
            if (Print._includeDate()) {
                logMsg.append("|");
                logMsg.append(Print.formatDate("MM/dd HH:mm:ss")); // "yyyy/MM/dd HH:mm:ss"
            }
            if (Print._includeStackFrame()) {
                logMsg.append("|");
                logMsg.append(_getStackFrame(frame + 1));
            }
            logMsg.append("|");
            logMsg.append(Thread.currentThread().getName());
            
            logMsg.append("] ");
        }
        
        /* message */
        logMsg.append(msg);
        if (!msg.endsWith("\n")) { logMsg.append("\n"); }
        
        /* print message */
        try {
            @SuppressWarnings("resource")
			PrintStream out =  RTConfig.isInitialized()? Print.openLogFile() : System.err;
            if (out != null) { 
                out.write(StringTools.getBytes(logMsg.toString())); 
                out.flush();
            } else {
                Print._print(null, frame + 1, false, logMsg.toString());
            }
        } catch (IOException ioe) {
            Print.setLogFile(null);
            Print.logError("Unable to open/write log file: " + ioe);
            Print._print(null, frame + 1, false, logMsg.toString());
        } finally {
            Print.closeLogFile();
        }

    }

    // ------------------------------------------------------------------------

    public static class NullOutputStream
        extends OutputStream
    {
        public NullOutputStream() {}
        public void write(int b) throws IOException {}
        public void write(byte[] b) throws IOException {}
        public void write(byte[] b, int off, int len) throws IOException {}
        public void flush() throws IOException {}
        public void close() throws IOException {}
    }
    
    public static class NullPrintStream
        extends PrintStream
    {
        public NullPrintStream() { super(new NullOutputStream()); }
    }

    // ------------------------------------------------------------------------

}
