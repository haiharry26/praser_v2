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
//  This class provides many File based utilities
// ----------------------------------------------------------------------------
// Change History:
//  2006/03/26  Martin D. Flynn
//      Initial release
//  2006/06/30  Martin D. Flynn
//      Repackaged to "org.opengts.util"
// ----------------------------------------------------------------------------
package com.digitrinity.parser.dateutil;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileTools
{   
    // ------------------------------------------------------------------------
	private static Logger logger = LogManager.getLogger(FileTools.class.getName());
    /* copy from input stream to output stream */
    public static int copyStreams(InputStream input, OutputStream output)
        throws IOException
    {
        return FileTools.copyStreams(input, output, -1);
    }
    
    /* copy from input stream to output stream */
    public static int copyStreams(InputStream input, OutputStream output, int maxLen)
        throws IOException
    {
        
        /* copy nothing? */
        if (maxLen == 0) {
            return 0;
        }
        
        /* copy bytes */
        int length = 0; // count of bytes copied
        byte tmpBuff[] = new byte[10 * 1024]; // 10K blocks
        while (true) {
            
            /* read length */
            int readLen;
            if (maxLen >= 0) {
                readLen = maxLen - length;
                if (readLen == 0) {
                    break; // done reading
                } else
                if (readLen > tmpBuff.length) { 
                    readLen = tmpBuff.length; // max block size
                }
            } else {
                readLen = tmpBuff.length;
            }
            
            /* read input stream */
            int cnt = input.read(tmpBuff, 0, readLen);
            
            /* copy to output stream */
            if (cnt < 0) { 
                if ((maxLen >= 0) && (length != maxLen)) {
                    logger.error("Copy size mismatch: " + maxLen + " => " + length);
                }
                break; 
            } else
            if (cnt > 0) {
                output.write(tmpBuff, 0, cnt);
                length += cnt;
                if ((maxLen >= 0) && (length >= maxLen)) {
                    break; // per 'maxLen', done copying
                }
            }             
        }
        output.flush();
        
        /* return number of bytes copied */
        return length;
    }
    
    // ------------------------------------------------------------------------

    public static InputStream openInputFile(String file)
    {
        if ((file != null) && !file.equals("")) {
            return FileTools.openInputFile(new File(file));
        } else {
            return null;
        }
    }
    
    public static InputStream openInputFile(File file)
    {
        try {
            return new FileInputStream(file);
        } catch (IOException ioe) {
            logger.error("Unable to open file: " + file + " [" + ioe + "]");
            return null;
        }
    }
    
    // ------------------------------------------------------------------------

    public static void closeStream(InputStream in)
    {
        if (in != null) {
            try {
                in.close();
            } catch (IOException ioe) {              
            }
        }
    }
    
    public static void closeStream(OutputStream out)
    {
        if (out != null) {
            try {
                out.close();
            } catch (IOException ioe) {              
            }
        }
    }

    // ------------------------------------------------------------------------
    
    /* read stream into byte array */
    public static byte[] readStream(InputStream input)
        throws IOException
    {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copyStreams(input, output);
        return output.toByteArray();
    }

    // ------------------------------------------------------------------------
    
    /* write String to OutputStream */
    public static void writeStream(OutputStream output, String dataStr)
        throws IOException
    {
        byte data[] = dataStr.getBytes();
        output.write(data, 0, data.length);
    }

    // ------------------------------------------------------------------------

    public static byte[] readFile(String file)
    {
        if ((file != null) && !file.equals("")) {
            return FileTools.readFile(new File(file));
        } else {
            return null;
        }
    }
    
    public static byte[] readFile(File file)
    {
        if (file == null) {
            return null;
        } else
        if (!file.exists()) {
            logger.error("File does not exist: " + file);
            return null;
        } else {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                return readStream(fis);
            } catch (IOException ioe) {
                logger.error("Unable to read file: " + file + " [" + ioe + "]");
            } finally {
                if (fis != null) { try { fis.close(); } catch (IOException ioe) {/*ignore*/} }
            }
            return null;
        }
    }
   
    // ------------------------------------------------------------------------

    public static String readLine(InputStream input)
        throws IOException
    {
        StringBuffer sb = new StringBuffer();
        while (true) {
            int ch = input.read();
            if (ch < 0) { // eof
                throw new EOFException("End of InputStream");
            } else
            if ((ch == '\r') || (ch == '\n')) {
                return sb.toString();
            }
            sb.append((char)ch);
        }
    }

    public static String readLine_stdin()
        throws IOException
    {
        while (System.in.available() > 0) { System.in.read(); }
        return FileTools.readLine(System.in);
    }

    public static String readString_stdin(String msg, String dft)
        throws IOException
    {
        if (msg == null) { msg = ""; }
        Print.sysPrintln(msg + "    [String: default='" + dft + "'] ");
        for (;;) {
            Print.sysPrint("?");
            String line = FileTools.readLine_stdin();
            if (line.equals("")) {
                if (dft != null) {
                    return dft;
                } else {
                    // if there is no default, a non-empty String is required
                    logger.warn("String required, please re-enter] ");
                    continue;
                }
            }
            return line;
        }
    }

    public static boolean readBoolean_stdin(String msg, boolean dft)
        throws IOException
    {
        if (msg == null) { msg = ""; }
        logger.trace(msg + "    [Boolean: default='" + dft + "'] ");
        for (;;) {
            Print.sysPrint("?");
            String line = FileTools.readLine_stdin().trim();
            if (line.equals("")) {
                return dft;
            } else
            if (!StringTools.isBoolean(line,true)) {
                logger.info("Boolean required, please re-enter] ");
                continue;
            }
            return StringTools.parseBoolean(line, dft);
        }
    }

    public static long readLong_stdin(String msg, long dft)
        throws IOException
    {
        if (msg == null) { msg = ""; }
        Print.sysPrintln(msg + "    [Long: default='" + dft + "'] ");
        for (;;) {
        	logger.info("?");
            String line = FileTools.readLine_stdin().trim();
            if (line.equals("")) {
                return dft;
            } else
            if (!Character.isDigit(line.charAt(0)) && (line.charAt(0) != '-')) {
            	logger.info("Long required, please re-enter] ");
                continue;
            }
            return StringTools.parseLong(line, dft);
        }
    }

    public static double readDouble_stdin(String msg, double dft)
        throws IOException
    {
        if (msg == null) { msg = ""; }
        logger.info(msg + "    [Double: default='" + dft + "'] ");
        for (;;) {
            Print.sysPrint("?");
            String line = FileTools.readLine_stdin().trim();
            if (line.equals("")) {
                return dft;
            } else
            if (!Character.isDigit(line.charAt(0)) && (line.charAt(0) != '-') && (line.charAt(0) != '.')) {
            	logger.info("Double required, please re-enter] ");
                continue;
            }
            return StringTools.parseDouble(line, dft);
        }
    }

    // ------------------------------------------------------------------------
    
    public static boolean writeFile(byte data[], File file)
        throws IOException
    {
        return FileTools.writeFile(data, file, false);
    }
    
    public static boolean writeFile(byte data[], File file, boolean append)
        throws IOException
    {
        if ((data != null) && (file != null)) {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file, append);
                fos.write(data, 0, data.length);
                return true;
            } finally {
                try { fos.close(); } catch (Throwable t) {/* ignore */}
            }
        } 
        return false;
    }
    
    // ------------------------------------------------------------------------

    public static String getExtension(String filePath) 
    {
        File file = new File(filePath);
        String fileName = file.getName();
        int p = fileName.indexOf(".");
        if ((p >= 0) && (p < (fileName.length() - 1))) {
            return fileName.substring(p + 1);
        }
        return "";
    }

    public static boolean hasExtension(String filePath, String extn[])
    {
        if (filePath != null) {
            String e = getExtension(filePath);
            for (int i = 0; i < extn.length; i++) {
                if (e.equalsIgnoreCase(extn[i])) { return true; }
            }
        }
        return false;
    }
    
    public static String removeExtension(String filePath)
    {
        File file = new File(filePath);
        String fileName = file.getName();
        int p = fileName.indexOf(".");
        if (p > 0) { // '.' in column 0 not allowed
            file = new File(file.getParentFile(), fileName.substring(0, p));
        }
        return file.getPath();
    }

    // ------------------------------------------------------------------------

    /* return true if the specified char is a file path separator for this platform */
    public static boolean isFileSeparatorChar(char ch)
    {
        if (ch == File.separatorChar) {
            // simple test, matches Java's understanding of a file path separator
            return true;
        } else 
        if (OSTools.isWindows() && (ch == '/')) {
            // '/' can be used as a file path separator on Windows
            return true;
        } else {
            // not a file path separator character
            return false;
        }
    }
    
    /* return true if the specified string contains a file separator char */
    public static boolean hasFileSeparator(String fn)
    {
        if (fn == null) {
            // no string, no file separator
            return false;
        } else
        if (fn.indexOf(File.separator) >= 0) {
            // simple test, matches Java's understanding of a file path separator
            return true;
        } else
        if (OSTools.isWindows() && (fn.indexOf('/') >= 0)) {
            // '/' can be used as a file path separator on Windows
            return true;
        } else {
            // no file path separator found
            return false;
        }
    }

    // ------------------------------------------------------------------------

    public static void main(String argv[])
        throws Throwable
    {
        RTConfig.setCommandLineArgs(argv);

        /* file hex dump */
        File binFile = RTConfig.getFile("file",null);
        if (binFile != null) {
            byte b[] = FileTools.readFile(binFile);
            if (RTConfig.getBoolean("hex",false)) {
                String hexStr = new String(b);
                b = StringTools.parseHex(hexStr, b);
            }
            logger.trace("Size " + ((b!=null)?b.length:-1));
            logger.trace(StringTools.formatHexString(b));
            System.exit(0);
        }

    }

}
