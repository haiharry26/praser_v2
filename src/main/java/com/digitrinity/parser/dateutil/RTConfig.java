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
//  Support for hierarchical runtime properties
// ----------------------------------------------------------------------------
// Change History:
//  2006/03/26  Martin D. Flynn
//     -Initial release
//  2006/04/23  Martin D. Flynn
//     -Changed support for default properties
//  2006/06/30  Martin D. Flynn
//     -Repackaged to "org.opengts.util"
//  2007/03/30  Martin D. Flynn
//     -Added support for immutable System properties
//  2007/05/06  Martin D. Flynn
//     -Added support for checking invalid command-line args
//  2007/06/13  Martin D. Flynn
//     -Catch 'SecurityException's when getting System properties.
//  2007/08/09  Martin D. Flynn
//     -Changed the way this module searches for runtime config files.
//  2007/09/16  Martin D. Flynn
//     -Added method 'insertKeyValues'
//     -Added support for key/value replace in config-file value strings
// ----------------------------------------------------------------------------
package com.digitrinity.parser.dateutil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RTConfig
{
    // ------------------------------------------------------------------------
	
    private static boolean verbose = false;

    // ------------------------------------------------------------------------
    private static Logger logger = LogManager.getLogger(RTConfig.class.getName());

    private static String localhostName = null;

    public static String getHostName()
    {
        /* host name */
        if (RTConfig.localhostName == null) {
            try {
                String hd = InetAddress.getLocalHost().getHostName();
                int p = hd.indexOf(".");
                RTConfig.localhostName = (p >= 0)? hd.substring(0,p) : hd;
            } catch (UnknownHostException uhe) {
                RTConfig.localhostName = "UNKNOWN";
            }
        }
        return RTConfig.localhostName;
    }

    // ------------------------------------------------------------------------

    private static final int    THREAD_LOCAL        = 0;
    private static final int    SERVLET_CONFIG      = 1;
    private static final int    SERVLET_CONTEXT     = 2;
    private static final int    COMMAND_LINE        = 3;
    private static final int    CONFIG_FILE         = 4;
    private static final int    SYSTEM              = 5;

    private static RTProperties CFG_PROPERTIES[] = new RTProperties[] {
        null,                                       // ThreadLocal properties
        null,                                       // ServletConfig properties
        null,                                       // ServletContext properties
        null,                                       // CommandLine properties [on-demand init]
        null,                                       // ConfigFile properties [lazy init]
        null // new RTProperties(System.getProperties()), // System properties
    };

    public static RTProperties getThreadProperties()
    {
        if (CFG_PROPERTIES[THREAD_LOCAL] == null) {
            synchronized (CFG_PROPERTIES) {
                if (CFG_PROPERTIES[THREAD_LOCAL] == null) { // still null?
                    CFG_PROPERTIES[THREAD_LOCAL] = new RTProperties(new ThreadLocalMap());
                }
            }
        }
        return CFG_PROPERTIES[THREAD_LOCAL];
    }

    public static RTProperties getServletContextProperties()
    {
        return CFG_PROPERTIES[SERVLET_CONTEXT]; // will be null until this is fully implemented
    }

    public static RTProperties getServletConfigProperties()
    {
        return CFG_PROPERTIES[SERVLET_CONFIG]; // will be null until this is fully implemented
    }

    public static RTProperties getCommandLineProperties()
    {
        return CFG_PROPERTIES[COMMAND_LINE]; // may be null if not initialized
    }

    public static RTProperties getConfigFileProperties()
    {
        if (CFG_PROPERTIES[CONFIG_FILE] == null) {
            // this should have been initialized before, but force initialization now
            if (RTConfig.verbose) { logger.info("Late initialization!!!"); }
            _startupInit(false);
        }
        if (CFG_PROPERTIES[CONFIG_FILE] != null) {
            return CFG_PROPERTIES[CONFIG_FILE];
        } else {
            logger.info("'RTConfig.getConfigFileProperties()' returning temporary RTProperties");
            return new RTProperties();
        }
    }

    public static RTProperties getSystemProperties()
    {
        if (CFG_PROPERTIES[SYSTEM] == null) {
            // this should have been initialized before, but force initialization now
            if (RTConfig.verbose) { logger.info("Late initialization!!!"); }
            _startupInit(false);
        }
        return CFG_PROPERTIES[SYSTEM];
    }

    public static RTProperties getPropertiesForKey(String key)
    {
        return RTConfig.getPropertiesForKey(key, true);
    }

    public static RTProperties getPropertiesForKey(String key, boolean dftOk)
    {
        if (key != null) {
            if (!isInitialized()) {
            }
            // look for key in our property list stack
            for (int i = 0; i < CFG_PROPERTIES.length; i++) {
                RTProperties rtProps = CFG_PROPERTIES[i];
                if ((rtProps != null) && rtProps.hasProperty(key)) { 
                    return rtProps; 
                }
            }
            // still not found, try the default properties
            if (dftOk) {
                RTProperties dftProps = RTKey.getDefaultProperties();
                if ((dftProps != null) && dftProps.hasProperty(key)) {
                    return dftProps;
                }
            }
        }
        return null;
    }

    // ------------------------------------------------------------------------

    public static void setCommandLineArgs(String argv[])
    {
        if (argv != null) {
            RTConfig.setCommandLineArgs(new RTProperties(argv), false);
        }
    }

    public static void setCommandLineArgs(RTProperties cmdLineProps)
    {
        if (cmdLineProps != null) {
            RTConfig.setCommandLineArgs(cmdLineProps, false);
        }
    }

    public static void setCommandLineArgs(String argv[], boolean testMode)
    {
        if (argv != null) {
            RTProperties cmdArgs = new RTProperties(argv);
            RTConfig.setCommandLineArgs(cmdArgs, testMode);
        }
    }

    public static void setCommandLineArgs(RTProperties cmdLineProps, boolean testMode)
    {
        if (cmdLineProps != null) {
            cmdLineProps.setIgnoreKeyCase(true);
            cmdLineProps.setProperty(RTKey.MAIN_CLASS, getMainClass());
            if (CFG_PROPERTIES[COMMAND_LINE] == null) {
                // first initialization
                CFG_PROPERTIES[COMMAND_LINE] = cmdLineProps;     
                _startupInit(true); // initialize now to allow for overriding 'configFile'
            } else {
                // subsequent re-initialization
                CFG_PROPERTIES[COMMAND_LINE].setProperties(cmdLineProps);
            }
        }
    }

    // ------------------------------------------------------------------------

    private static Set _parseArgs(Object argv, Set set)
    {
        if ((argv != null) && (set != null)) {
            if (argv instanceof Object[]) {
                Object a[] = (Object[])argv;
                for (int i = 0; i < a.length; i++) {
                    RTConfig._parseArgs(a[i], set);
                }
            } else {
                set.add(argv.toString());
            }
        }
        return set;
    }

    public static String[] validateCommandLineArgs(Object argv)
    {
        RTProperties cmdLineProps = RTConfig.getCommandLineProperties();
        if (cmdLineProps != null) {
            java.util.List badArgs = new Vector();
            Set argSet = new HashSet();
            argSet.add(RTKey.MAIN_CLASS);
            RTConfig._parseArgs(argv, argSet);
            for (Iterator keys = cmdLineProps.keyIterator(); keys.hasNext();) {
                String k = (String)keys.next();
                if (RTKey.hasDefault(k) || RTKey.COMMAND_LINE_CONF.equals(k)) {
                    // "-conf", "-configFile", "-configFileDir" are ok
                } else
                if (!argSet.contains(k)) {
                    badArgs.add(k);
                }
            }
            return badArgs.isEmpty()? null : (String[])badArgs.toArray(new String[badArgs.size()]);
        } else {
            return null;
        }
    }
    
    // ------------------------------------------------------------------------

    public static void setServletContextProperties(Map props)
    {
        // Don't do anything right now except initialize RTConfig
        // --------------------------------------------------------------
        // Set via servlet v2.3 ServletContextListener
        // Reference:
        //  http://livedocs.macromedia.com/jrun/4/Programmers_Guide/servletlifecycleevents3.htm
        RTConfig.verbose = true; // default to verbose
        CFG_PROPERTIES[SERVLET_CONTEXT] = new RTProperties(props);
        _startupInit(false);
        setWebApp(true); // force isWebapp=true
    }

    public static void clearServletContextProperties(Object servlet)
    {
        CFG_PROPERTIES[SERVLET_CONTEXT] = null;
    }

    // ------------------------------------------------------------------------

    private static int      _didStartupInit  = 0;  // 0=not initialized, 1=initializing, 2=initialized
    private static URL      _foundConfigURL  = null;
    private static File     _foundConfigFile = null;

    public static boolean isInitializing()
    {
        return (_didStartupInit == 1);
    }

    public static boolean isInitialized()
    {
        return (_didStartupInit == 2);
    }

    public static URL getLoadedConfigURL()
    {
        return _foundConfigURL;
    }

    public static File getLoadedConfigFile()
    {
        if (_foundConfigFile == null) {
            if ((_foundConfigURL != null) && (_foundConfigURL.getProtocol().equals("file"))) {
                String path = _foundConfigURL.getPath();
                _foundConfigFile = new File(path);
            }
        }
        return _foundConfigFile;
    }
    
    protected static synchronized void _startupInit(boolean allowChangeSystemProperties)
    {
        // Note: this method is synchronized

        /* check init */
        if (_didStartupInit == 2) {
            // already initialized
            return; 
        } else
        if (_didStartupInit == 1) {
            logger.error("_startupInit' is already initializing!");
            return; 
        }
        _didStartupInit = 1;
        
        /* System properties */
        Properties propMap = null;
        if (allowChangeSystemProperties) {
            try {
                propMap = System.getProperties();
            } catch (SecurityException se) { // SecurityException, AccessControlException
               logger.error("ERROR: Attempting to call 'System.getProperties()': " , se);
            }
        } else {
            propMap = new Properties();
            for (Iterator i = RTKey.getRuntimeKeyIterator(); i.hasNext();) {
                String key = (String)i.next();
                try {
                    String val = System.getProperty(key, null);
                    if (val != null) {
                        propMap.setProperty(key, val);
                    }
                } catch (SecurityException se) { // SecurityException, AccessControlException
                	logger.error("Attempting to get System property '" + key + "': " , se);
                }
            }
        }
        CFG_PROPERTIES[SYSTEM] = new RTProperties(propMap);

        /* verbose? */
        if (hasProperty(RTKey.RT_VERBOSE, false)) {
            RTConfig.verbose = RTConfig.getBoolean(RTKey.RT_VERBOSE, false);
        } else
        if (hasProperty(RTKey.RT_QUIET, false)) {
            RTConfig.verbose = RTConfig.getBoolean(RTKey.RT_QUIET, false);
        }

        /* config file */
        _foundConfigFile = null;
        _foundConfigURL = RTConfig.getConfigURL();
        if (_foundConfigURL != null) {
            CFG_PROPERTIES[CONFIG_FILE] = new RTProperties(_foundConfigURL);
            if (RTConfig.verbose) { logger.info("Found config file at " + _foundConfigURL); }
        } else {
            //String cfgDir = RTConfig.getFile(RTKey.CONFIG_FILE_DIR);
            //String cfgFile = RTConfig.getFile(RTKey.CONFIG_FILE);
            CFG_PROPERTIES[CONFIG_FILE] = new RTProperties(); // must be non-null
            if (RTConfig.verbose) { logger.warn("No config file was found"); }
        }
        CFG_PROPERTIES[CONFIG_FILE].setKeyReplacementMode(RTProperties.KEY_REPLACEMENT_GLOBAL);

        /* initialize http proxy */
        // http.proxyHost
        // http.proxyPort
        // http.nonProxyHosts
        String proxyHost = RTConfig.getString(RTKey.HTTP_PROXY_HOST);
        int    proxyPort = RTConfig.getInt   (RTKey.HTTP_PROXY_PORT);
        if ((proxyHost != null) && (proxyPort > 1024)) {
            String port = String.valueOf(proxyPort);
            System.setProperty("proxySet" , "true");            // <  jdk 1.3
            System.setProperty("proxyHost", proxyHost);         // <  jdk 1.3
            System.setProperty("proxyPort", port);              // <  jdk 1.3
            System.setProperty("http.proxyHost", proxyHost);    // >= jdk 1.3
            System.setProperty("http.proxyPort", port);         // >= jdk 1.3
            System.setProperty("firewallSet", "true");          // MS JVM
            System.setProperty("firewallHost", proxyHost);      // MS JVM
            System.setProperty("firewallPort", port);           // MS JVM
        }

        /* URLConnection timeouts */
        // sun.net.client.defaultConnectTimeout
        // sun.net.client.defaultReadTimeout
        long urlConnectTimeout = RTConfig.getLong(RTKey.URL_CONNECT_TIMEOUT);
        if (urlConnectTimeout > 0) {
            String timeout = String.valueOf(urlConnectTimeout);
            //System.getProperties().put("sun.net.client.defaultConnectTimeout", timeout);
            System.setProperty("sun.net.client.defaultConnectTimeout", timeout);
        }
        long urlReadTimeout = RTConfig.getLong(RTKey.URL_READ_TIMEOUT);
        if (urlReadTimeout > 0) {
            String timeout = String.valueOf(urlReadTimeout);
            //System.getProperties().put("sun.net.client.defaultReadTimeout", timeout);
            System.setProperty("sun.net.client.defaultReadTimeout", timeout);
        }
        
        /* now initialized */
        _didStartupInit = 2;
        
        /* set all of the Print configuration */
        Print.initRTConfig();

    }

    protected static URL getConfigURL()
    {
        try{
            URL cfgURL = RTConfig._getConfigURL();
            if (cfgURL != null) {
                logger.error("Config URL found at " + cfgURL);
                return cfgURL;
            } else {
                logger.warn("No valid config URL was found");
            }
        } catch (MalformedURLException mue) {
            logger.error("Invalid URL: " , mue);
        } catch (Throwable t) {
            logger.error("Invalid URL", t);
        }
        return null;
    }
    

	@SuppressWarnings("deprecation")
	protected static URL _getConfigURL()
        throws MalformedURLException
    {
        /* init */
        String hostName = RTConfig.getHostName(); // should not be null
        Class servletClass = null;
        RTProperties cmdLineProps = getCommandLineProperties();
        boolean isCommandLine = (cmdLineProps != null);
        RTProperties servletProps = getServletContextProperties();
        boolean isServlet = !isCommandLine && ((servletProps != null) || ((servletClass = getServletClass()) != null));

        /* explicitly defined (command-line, etc) "-configFile=<file>" */
        // Check for "-configFile=<file>"
        if (RTConfig.hasProperty(RTKey.CONFIG_FILE,false)) {
            File cfgFile = RTConfig.getFile(RTKey.CONFIG_FILE);
            if (RTConfig.hasProperty(RTKey.CONFIG_FILE_DIR,false)) {
                File cfgDir = RTConfig.getFile(RTKey.CONFIG_FILE_DIR);
                cfgFile = new File(cfgDir, cfgFile.toString());
            }
            if (cfgFile.isFile()) {
                return cfgFile.toURL();
            } else {
                logger.error("Specified config file does not exist: " + cfgFile);
                return null;
            }
        } else
        // Check for "-conf=<file>"
        if (isCommandLine) {
            /* check for alternate command line override '-conf=<file>' */
            File cfgFile = cmdLineProps.getFile(RTKey.COMMAND_LINE_CONF, null);
            if (cfgFile != null) {
                if (RTConfig.hasProperty(RTKey.CONFIG_FILE_DIR,false)) {
                    File cfgDir = RTConfig.getFile(RTKey.CONFIG_FILE_DIR);
                    cfgFile = new File(cfgDir, cfgFile.toString());
                }
                if (cfgFile.isFile()) {
                    return cfgFile.toURL();
                } else {
                   logger.error("Specified config file does not exist: " + cfgFile);
                    return null;
                }
            }
        }

        /* separate directory from path */
        File rtCfgDir;
        File rtCfgFile;
        if (isServlet) {
            rtCfgDir  = RTConfig.getFile(RTKey.CONFIG_FILE_DIR);
            rtCfgFile = RTConfig.getFile(RTKey.WEBAPP_FILE); // should be simply "webapp.conf"
        } else {
            rtCfgFile = RTConfig.getFile(RTKey.CONFIG_FILE);
            if (rtCfgFile.isAbsolute()) {
                rtCfgDir  = rtCfgFile.getParentFile();
                rtCfgFile = new File(rtCfgFile.getName());
            } else {
                File cf   = new File(RTConfig.getFile(RTKey.CONFIG_FILE_DIR), rtCfgFile.toString());
                rtCfgDir  = cf.getParentFile();
                rtCfgFile = new File(cf.getName());
            }
        }

        /* separate file name from extension */
        String cfgFileName = rtCfgFile.toString();
        int    cfgExtPos   = cfgFileName.lastIndexOf(".");
        String cfgName     = (cfgExtPos >= 0)? cfgFileName.substring(0,cfgExtPos) : cfgFileName;
        String cfgExtn     = (cfgExtPos >= 0)? cfgFileName.substring(cfgExtPos  ) : "";

        /* check for config file in resources */
        Class mainClass = RTConfig.getMainClass();
        if (mainClass != null) {
            try {
                ClassLoader mainClassLoader = mainClass.getClassLoader();
                if (mainClassLoader == null) {
                    // bootstrap classloader
                    mainClassLoader = ClassLoader.getSystemClassLoader(); // may still be null
                }
                if (mainClassLoader != null) {
                    // Check for resource "default_<host>.conf"
                    URL cfgHostRes = mainClassLoader.getResource(cfgName + "_" + hostName + cfgExtn);
                    if (cfgHostRes != null) {
                        return cfgHostRes;
                    }
                    // Check for resource "default.conf"
                    URL cfgRes = mainClassLoader.getResource(cfgFileName);
                    if (cfgRes != null) {
                        return cfgRes;
                    }
                } else {
                    logger.warn("System class loader is null");
                }
            } catch (Throwable t) {
                logger.error("Error retrieving class loader", t);
            }
        }

        /* special context (command-line/servlet) config files */
        if (isCommandLine) {

            // continue below

        } else
        if (servletProps != null) {

            /* check "<ContextPath>/[WEB-INF/]webapp[_<host>].conf" */
            String ctxPath = servletProps.getString(RTKey.WEBAPP_CONTEXT_PATH, null);
            if (ctxPath != null) {
                File web_inf = new File(ctxPath, "WEB-INF");
                
                // Check for "<Context>/WEB-INF/webapp_<host>.conf"
                File webInfHostFile = new File(web_inf, cfgName + "_" + hostName + cfgExtn);
                if (webInfHostFile.isFile()) {
                    return webInfHostFile.toURL();
                }
                
                // Check for "<Context>/WEB-INF/webapp.conf"
                File webInfFile = new File(web_inf, cfgFileName);
                if (webInfFile.isFile()) {
                    return webInfFile.toURL();
                }
            }

        } else
        if (servletClass != null) {

            // If we are here, then this means that 'ContextListener' was not specified for this 
            // context and we are initializing late (possible too late).
            if (RTConfig.verbose) {
            	logger.debug("--------------------------------------------------------");
            	logger.debug("******* WebApp: " + StringTools.className(servletClass));
            	logger.debug("--------------------------------------------------------");
            }

        } else {

            // this only occurs if we're attempting to acces RTConfig properties and we
            // haven't previously been properly initialized
            logger.error("CommandLine/ServletContext properties not specified");

        }

        // Check for "<CONFIG_FILE_DIR>/[default|webapp]_<host>.conf"
        File cfgDirHostFile = new File(rtCfgDir, cfgName + "_" + hostName + cfgExtn);
        if (cfgDirHostFile.isFile()) {
            return cfgDirHostFile.toURL();
        }

        //Check for "<CONFIG_FILE_DIR>/[default|webapp].conf"
        File cfgDirFile = new File(rtCfgDir, rtCfgFile.toString());
        if (cfgDirFile.isFile()) {
            return cfgDirFile.toURL();
        }

        /* no config file */
        return null;

    }
    
    protected static boolean _resourceExists(File file)
    {
        if (file == null) {
            return false;
        }
        Class mainClass = RTConfig.getMainClass();
        logger.debug("MainClass: " + mainClass);
        URL cfgRes = (mainClass != null)? mainClass.getClassLoader().getResource(file.toString()) : null;
        if (cfgRes != null) {
        	logger.debug("ConfigFile found as resource: " + file);
            return true;
        } else {
        	logger.debug("ConfigFile NOT found as resource: " + file);
            return false;
        }
    }

    // ------------------------------------------------------------------------

    public static Properties loadResourceProperties(String name)
    {
        try {
            ClassLoader cl = ClassLoader.getSystemClassLoader();
            InputStream inpStream = cl.getResourceAsStream(name);
            if (inpStream != null) {
                Properties props = new Properties();
                props.load(inpStream);
                return props;
            } else {
                return null;
            }
        } catch (Throwable t) {
           logger.error("Loading properties: " + name, t);
            return null;
        }
    }

    // ------------------------------------------------------------------------

    public static Properties loadManifestProperties(Class clzz)
    {
        // NOTE: Experimental! This currently does not work!!! DO NOT USE
        String manifestResource = "/META-INF/MANIFEST.MF";
        try {
            ClassLoader cl = clzz.getClassLoader();
            InputStream input = cl.getResourceAsStream(manifestResource);
            if (input == null) {
                throw new FileNotFoundException("MANIFEST not found");
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            Properties props = new Properties();
            for (;;) {
                String line = br.readLine();
                if (line == null) { break; }
                int p = line.indexOf(':');
                if (p > 0) {
                    String key = line.substring(0,p).trim();
                    String val = line.substring(p+1).trim();
                    props.setProperty(key, val);
                }
            }
            return props;
        } catch (Throwable t) {
            logger.error("Loading MANIFEST properties", t);
            return null;
        }
    }

    // ------------------------------------------------------------------------
    // ------------------------------------------------------------------------

    /* replace ${key} strings in the specified text with values from the runtime properties */
    // default key delimiters will be used
    public static String insertKeyValues(String text)   
    {
        return RTConfig._insertKeyValues(null, text, RTProperties.KEY_START_DELIMITER, RTProperties.KEY_END_DELIMITER);
    }

    /* replace ${key} strings in the specified text with values from the runtime properties */
    // specified key delimiters will be used
    public static String insertKeyValues(String text, String startDelim, String endDelim)
    {
        return RTConfig._insertKeyValues(null, text, startDelim, endDelim);
    }

    public static String _insertKeyValues(Object mainKey, String text)   
    {
        return RTConfig._insertKeyValues(mainKey, text, RTProperties.KEY_START_DELIMITER, RTProperties.KEY_END_DELIMITER);
    }

    public static String _insertKeyValues(final Object mainKey, String text, String startDelim, String endDelim)   
    {
        if (text != null) {
            // replacment call-back 
            StringTools.ReplacementMap rm = new StringTools.ReplacementMap() {
                private HashSet thisKeySet = new HashSet();
                private HashSet fullKeySet = new HashSet();
                public String get(String k) {
                    if (k == null) {
                        // a bit of a hack here to tell this map to reset the cached keys
                        fullKeySet.addAll(thisKeySet);
                        if (mainKey != null) { fullKeySet.add(mainKey); }
                        thisKeySet.clear();
                        return null;
                    } else
                    if (fullKeySet.contains(k)) {
                        return null;
                    } else {
                        thisKeySet.add(k);
                        return RTConfig._getString(k, null);
                    }
                }
            };
            // iterate until the string doesn't change
            String s_old = text;
            for (int i = 0; i < RTProperties.KEY_MAX_RECURSION; i++) {
                rm.get(null); // hack to reset the cached keys
                String s_new = StringTools.insertKeyValues(s_old, startDelim, endDelim, rm, false);
                if (s_new.equals(s_old)) {
                    return s_new;
                }
                s_old = s_new;
            }
            return s_old;
        } else {
            return text;
        }
    }

    // ------------------------------------------------------------------------
    // ------------------------------------------------------------------------

    public static boolean hasProperty(String key)
    {
        return RTConfig.hasProperty(key, true);
    }

    public static boolean hasProperty(String key, boolean dftOk)
    {
        RTProperties rtp = getPropertiesForKey(key, dftOk);
        return (rtp != null);
    }

    public static boolean hasProperty(String key[])
    {
        return RTConfig.hasProperty(key, true);
    }

    public static boolean hasProperty(String key[], boolean dftOk)
    {
        if (key != null) {
            for (int i = 0; i < key.length; i++) {
                RTProperties rtp = getPropertiesForKey(key[i], dftOk);
                if (rtp != null) { return true; }
            }
        }
        return false;
    }

    // ------------------------------------------------------------------------

    public static Object getProperty(String key)
    {
        return getProperty(key, null);
    }

    public static Object getProperty(String key, Object dft)
    {
        RTProperties rtp = getPropertiesForKey(key);
        return (rtp != null)? rtp.getProperty(key, dft) : dft;
    }

    public static void setProperty(String key, Object value)
    {
        RTProperties cfgProps = getConfigFileProperties();
        cfgProps.setProperty(key, value);
        if ((key != null) && (value == null)) {
            getSystemProperties().removeProperty(key);
        }
    }

    public static void setProperties(Properties props)
    {
        RTProperties cfgProps = getConfigFileProperties();
        cfgProps.setProperties(props);
    }

    // ------------------------------------------------------------------------

    // Extract a Map containing a group of key/values from the config file properties
    public static Map extractMap(String keyEnd, String valEnd)
    {
        // TODO: should include keyEnd/valEnd from all properties
        RTProperties cfgProps = getConfigFileProperties();
        return cfgProps.extractMap(keyEnd, valEnd);
    }

    // ------------------------------------------------------------------------

    public static String _getString(String key, String dft)
    {
        RTProperties rtp = getPropertiesForKey(key);
        if (rtp != null) {
            Object obj = rtp._getProperty(key, dft);
            return (obj != null)? obj.toString() : dft;
        } else {
            return dft;
        }
    }

    public static String _getString(String key[], String dft)
    {
        if (key != null) {
            for (int i = 0; i < key.length; i++) {
                RTProperties rtp = getPropertiesForKey(key[i]);
                if (rtp != null) { 
                    Object obj = rtp._getProperty(key[i], dft);
                    return (obj != null)? obj.toString() : dft;
                }
            }
        }
        return dft;
    }

    public static String getString(String key)
    {
        return getString(key, null);
    }

    public static String getString(String key, String dft)
    {
        RTProperties rtp = getPropertiesForKey(key);
        return (rtp != null)? rtp.getString(key, dft) : dft;
    }

    public static String getString(String key[], String dft)
    {
        if (key != null) {
            for (int i = 0; i < key.length; i++) {
                RTProperties rtp = getPropertiesForKey(key[i]);
                if (rtp != null) { 
                    return rtp.getString(key[i], dft);
                }
            }
        }
        return dft;
    }

    public static void setString(String key, String value)
    {
        RTProperties cfgFileProps = getConfigFileProperties();
        cfgFileProps.setString(key, value);
    }

    // ------------------------------------------------------------------------

    public static String[] getStringArray(String key)
    {
        return getStringArray(key, null);
    }

    public static String[] getStringArray(String key, String dft[])
    {
        RTProperties rtp = getPropertiesForKey(key);
        return (rtp != null)? rtp.getStringArray(key, dft) : dft;
    }

    public static String[] getStringArray(String key[], String dft[])
    {
        if (key != null) {
            for (int i = 0; i < key.length; i++) {
                RTProperties rtp = getPropertiesForKey(key[i]);
                if (rtp != null) { 
                    return rtp.getStringArray(key[i], dft); 
                }
            }
        }
        return dft;
    }

    public static void setStringArray(String key, String val[])
    {
        RTProperties cfgProps = getConfigFileProperties();
        cfgProps.setStringArray(key, val);
    }

    // ------------------------------------------------------------------------

    public static File getFile(String key)
    {
        return getFile(key, null);
    }

    // do not include this method, otherwise "getFile(file, null)" would be ambiguous
    //public File getFile(String key, String dft)

    public static File getFile(String key, File dft)
    {
        RTProperties rtp = getPropertiesForKey(key);
        return (rtp != null)? rtp.getFile(key, dft) : dft;
    }

    public static File getFile(String key[], File dft)
    {
        if (key != null) {
            for (int i = 0; i < key.length; i++) {
                RTProperties rtp = getPropertiesForKey(key[i]);
                if (rtp != null) { return rtp.getFile(key[i], dft); }
            }
        }
        return dft;
    }

    public static void setFile(String key, File value)
    {
        RTProperties cfgProps = getConfigFileProperties();
        cfgProps.setFile(key, value);
    }

    // ------------------------------------------------------------------------

    public static double getDouble(String key)
    {
        return getDouble(key, 0.0);
    }

    public static double getDouble(String key, double dft)
    {
        RTProperties rtp = getPropertiesForKey(key);
        return (rtp != null)? rtp.getDouble(key, dft) : dft;
    }

    public static double getDouble(String key[], double dft)
    {
        if (key != null) {
            for (int i = 0; i < key.length; i++) {
                RTProperties rtp = getPropertiesForKey(key[i]);
                if (rtp != null) { return rtp.getDouble(key[i], dft); }
            }
        }
        return dft;
    }

    public static void setDouble(String key, double value)
    {
        RTProperties cfgProps = getConfigFileProperties();
        cfgProps.setDouble(key, value);
    }

    // ------------------------------------------------------------------------

    public static float getFloat(String key)
    {
        return RTConfig.getFloat(key, 0.0F);
    }

    public static float getFloat(String key, float dft)
    {
        RTProperties rtp = getPropertiesForKey(key);
        return (rtp != null)? rtp.getFloat(key, dft) : dft;
    }

    public static float getFloat(String key[], float dft)
    {
        if (key != null) {
            for (int i = 0; i < key.length; i++) {
                RTProperties rtp = getPropertiesForKey(key[i]);
                if (rtp != null) { return rtp.getFloat(key[i], dft); }
            }
        }
        return dft;
    }

    public static void setFloat(String key, float value)
    {
        RTProperties cfgProps = getConfigFileProperties();
        cfgProps.setFloat(key, value);
    }

    // ------------------------------------------------------------------------

    public static long getLong(String key)
    {
        return RTConfig.getLong(key, 0L);
    }

    public static long getLong(String key, long dft)
    {
        RTProperties rtp = getPropertiesForKey(key);
        return (rtp != null)? rtp.getLong(key, dft) : dft;
    }

    public static long getLong(String key[], long dft)
    {
        if (key != null) {
            for (int i = 0; i < key.length; i++) {
                RTProperties rtp = getPropertiesForKey(key[i]);
                if (rtp != null) { return rtp.getLong(key[i], dft); }
            }
        }
        return dft;
    }

    public static void setLong(String key, long value)
    {
        RTProperties cfgProps = getConfigFileProperties();
        cfgProps.setLong(key, value);
    }

    // ------------------------------------------------------------------------

    public static int getInt(String key)
    {
        return RTConfig.getInt(key, 0);
    }

    public static int getInt(String key, int dft)
    {
        RTProperties rtp = getPropertiesForKey(key);
        return (rtp != null)? rtp.getInt(key, dft) : dft;
    }

    public static int getInt(String key[], int dft)
    {
        if (key != null) {
            for (int i = 0; i < key.length; i++) {
                RTProperties rtp = getPropertiesForKey(key[i]);
                if (rtp != null) { return rtp.getInt(key[i], dft); }
            }
        }
        return dft;
    }

    public static void setInt(String key, int value)
    {
        RTProperties cfgProps = getConfigFileProperties();
        cfgProps.setInt(key, value);
    }

    // ------------------------------------------------------------------------

    public static boolean getBoolean(String key)
    {
        return getBoolean(key, hasProperty(key));
    }

    public static boolean getBoolean(String key, boolean dft)
    {
        RTProperties rtp = getPropertiesForKey(key);
        if (rtp == null) {
            return dft; // no key, return default
        } else {
            String s = rtp.getString(key, "");
            if ((s != null) && s.equals("")) {
                return rtp.getBoolean(key, true); // key with no argument
            } else {
                return rtp.getBoolean(key, dft);  // key with argument, use dft if not parsable.
            }
        }
        //return (rtp != null)? rtp.getBoolean(key, dft) : dft;
    }

    public static boolean getBoolean(String key[], boolean dft)
    {
        if (key != null) {
            for (int i = 0; i < key.length; i++) {
                RTProperties rtp = getPropertiesForKey(key[i]);
                if (rtp != null) { return rtp.getBoolean(key[i], dft); }
            }
        }
        return dft;
    }

    public static void setBoolean(String key, boolean value)
    {
        RTProperties cfgProps = getConfigFileProperties();
        cfgProps.setBoolean(key, value);
    }

    // ------------------------------------------------------------------------

    public static void setAdminMode(boolean admin)
    {
        RTConfig.setBoolean(RTKey.ADMIN_MODE, admin);
    }

    public static boolean isAdminMode()
    {
        return RTConfig.getBoolean(RTKey.ADMIN_MODE);
    }

    // ------------------------------------------------------------------------

    public static void setDebugMode(boolean debug)
    {
        RTConfig.setBoolean(RTKey.DEBUG_MODE, debug);
    }

    //private static int _debug_recursion = 0;
    public static boolean isDebugMode()
    {
        //if (_debug_recursion > 0) { Thread.dumpStack(); System.exit(0); }
        //try { _debug_recursion++;
        return !isInitialized() || getBoolean(RTKey.DEBUG_MODE);
        //} finally { _debug_recursion--; }
    }

    // ------------------------------------------------------------------------

    public static void setTestMode(boolean test)
    {
        setBoolean(RTKey.TEST_MODE, test);
    }

    public static boolean isTestMode()
    {
        return getBoolean(RTKey.TEST_MODE);
    }

    // ------------------------------------------------------------------------
    // ------------------------------------------------------------------------

    private static Boolean isRunningAsWebApp = null;

    public static void setWebApp(boolean webapp)
    {
        setBoolean(RTKey.IS_WEBAPP, webapp);
        isRunningAsWebApp = null; // <== to bypass Boolean check
    }

    public static boolean isWebApp()
    {

        /* already know where we are running? */
        if (isRunningAsWebApp != null) {
            return isRunningAsWebApp.booleanValue();
        }

        /* "isWebApp" explicitly defined? */
        if (hasProperty(RTKey.IS_WEBAPP, false)) {
            return getBoolean(RTKey.IS_WEBAPP);
        }

        /* check invocation stack */
        isRunningAsWebApp = new Boolean(_isWebApp_2());
        return isRunningAsWebApp.booleanValue();

    }

    private static String WebAppClassNames[] = {
        "javax.servlet.http.HttpServlet", // as long as the servlet didn't override 'service'
        "org.apache.catalina.core.ApplicationFilterChain"
    };
    protected static boolean _isWebApp_1()
    {
        // We should also check the invocation stack
        // A typical stack-trace segment for a servlet is as follows:
        //   ...
        //   at com.example.war.DataMessage.doPost(DataMessage.java:46)
        //   at javax.servlet.http.HttpServlet.service(HttpServlet.java:760)
        //   at javax.servlet.http.HttpServlet.service(HttpServlet.java:853)
        //   at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:247)
        //   at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:193)
        //   at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:256)
        //   ...
        // Possible search Strings would be:
        //  - "javax.servlet.http.HttpServlet" (assuming 'service' was not overridden)
        //  - "org.apache.catalina.core.ApplicationFilterChain" (only valid for Tomcat)
        Throwable t = new Throwable();
        t.fillInStackTrace();
        //t.printStackTrace();
        StackTraceElement stackFrame[] = t.getStackTrace();
        for (int i = 0; i < stackFrame.length; i++) {
            String cn = stackFrame[i].getClassName();
            for (int w = 0; w < WebAppClassNames.length; w++) {
                if (cn.equalsIgnoreCase(WebAppClassNames[w])) {
                    return true;
                }
            }
        }
        return false;
    }

    protected static boolean _isWebApp_2()
    {
        return (getServletClass() != null);
    }

    // ------------------------------------------------------------------------
    // ------------------------------------------------------------------------

    private static Class Main_class = null;
    public static Class getMainClass()
    {
        if (Main_class == null) {
            Class lastClz = null;
            for (int sf = 2; ; sf++) {
                Class clz = OSTools.getCallerClass(sf);
                if (clz == null) { break; }
                lastClz = clz;
            }
            Main_class = lastClz;
        }
        return Main_class;
    }

    // ------------------------------------------------------------------------
    // ------------------------------------------------------------------------

    private static String SERVLET_CLASS = "javax.servlet.Servlet"; // GenericServlet
    private static boolean Servlet_init = false;
    private static Class Servlet_class = null;
    public static Class getServletClass()
    {

        /* init for Servlet class */
        if (!Servlet_init) {
            try {
                Servlet_class = Class.forName(SERVLET_CLASS);
            } catch (Throwable t) {
            }
            Servlet_init = true;
        }

        /* find Servlet in invocation stack */
        if (Servlet_class != null) {
            for (int sf = 2; ; sf++) {
                Class clz = OSTools.getCallerClass(sf);
                if (clz == null) { break; }
                if (Servlet_class.isAssignableFrom(clz)) {
                    return clz;
                }
            }
        }

        /* not found */
        return null;

    }

    // ------------------------------------------------------------------------
    // ------------------------------------------------------------------------

    public static void main(String argv[])
    {
       logger.debug("Before RTConfig was initialized ...");
        RTConfig.setCommandLineArgs(argv);
        logger.debug("String test = " + RTConfig.getString("test","???"));
        logger.debug("Double test = " + RTConfig.getDouble("test",0.0));
        logger.debug("Long   test = " + RTConfig.getLong("test",0L));
    }

}
