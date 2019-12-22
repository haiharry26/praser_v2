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
//  General OS specific tools
// ----------------------------------------------------------------------------
// Change History:
//  2006/03/26  Martin D. Flynn
//      Initial release
//  2006/06/30  Martin D. Flynn
//      Repackaged to "org.opengts.util"
// ----------------------------------------------------------------------------
package com.digitrinity.parser.dateutil;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OSTools
{
    
    // ------------------------------------------------------------------------
    // OS and JVM specific tools    
    // ------------------------------------------------------------------------
    
    private static int OS_INITIALIZE = -1;
    public  static int OS_UNKNOWN    = 0x1000;
    public  static int OS_UNIX       = 0x0010;
    public  static int OS_MAC        = 0x0020;
    public  static int OS_WINDOWS    = 0x0040;
    public  static int OS_WINDOWS_XP = OS_WINDOWS | 0x0001;
    public  static int OS_WINDOWS_9X = OS_WINDOWS | 0x0002;
    private static Logger logger = LogManager.getLogger(OSTools.class.getName());
    
    private static int OSType = OS_INITIALIZE;
    
    public static int getOSType()
    {
        if (OSType == OS_INITIALIZE) {
            String osName = System.getProperty("os.name").toLowerCase();
            logger.info("OS: " + osName);
            if (osName.startsWith("windows")) {
                if (osName.startsWith("windows xp")) {
                    OSType = OS_WINDOWS_XP;
                } else
                if (osName.startsWith("windows 9") || osName.startsWith("windows m")) {
                    OSType = OS_WINDOWS_9X;
                } else {
                    OSType = OS_WINDOWS;
                }
            } else
            if (File.separatorChar == '/') {
                OSType = OS_UNIX;
            } else {
                OSType = OS_UNKNOWN;
            }
        }
        return OSType;
    }
        
    // ------------------------------------------------------------------------

    public static boolean isUnknown()
    {
        return (getOSType() == OS_UNKNOWN);
    }
    
    public static boolean isWindows()
    {
        return ((getOSType() & OS_WINDOWS) != 0);
    }
    
    public static boolean isWindowsXP()
    {
        return (getOSType() == OS_WINDOWS_XP);
    }
    
    public static boolean isWindows9X()
    {
        return (getOSType() == OS_WINDOWS_9X);
    }

    public static boolean isUnix()
    {
        return ((getOSType() & OS_UNIX) != 0);
    }
    
    // ------------------------------------------------------------------------
    
    public static boolean isBrokenToFront()
    {
        return isWindows();
    }
    
    // ------------------------------------------------------------------------


	@SuppressWarnings("deprecation")
	public static void printCallerClasses()
    {
        for (int i = 0; ; i++) {
            Class clz = sun.reflect.Reflection.getCallerClass(i);
            logger.info("" + i + "] class " + StringTools.className(clz));
            if (clz == null) { break; }
        }
    }
    
   
	@SuppressWarnings("deprecation")
	public static Class getCallerClass(int frame)
    {        
        Class clz = sun.reflect.Reflection.getCallerClass(frame + 1);       
        return clz;
    }
    
    // ------------------------------------------------------------------------
    
    public static void main(String argv[])
    {
        RTConfig.setCommandLineArgs(argv);
        logger.trace("Is Windows  : " + isWindows());
        logger.trace("Is Windows9X: " + isWindows9X());
        logger.trace("Is WindowsXP: " + isWindowsXP());
        logger.trace("Is Unix     : " + isUnix());
    }
    
}    