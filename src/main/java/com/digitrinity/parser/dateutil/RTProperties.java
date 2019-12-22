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
//  Runtime properties container
// ----------------------------------------------------------------------------
// Change History:
//  2006/03/26  Martin D. Flynn
//     -Initial release
//  2006/04/02  Martin D. Flynn
//     -Added ability to separate command-line key/value pairs with a ':'.
//  2006/04/23  Martin D. Flynn
//     -Integrated logging changes made to Print
//  2006/06/30  Martin D. Flynn
//     -Repackaged to "org.opengts.util"
//  2007/07/27  Martin D. Flynn
//     -Added support for primitive array types
//  2007/08/09  Martin D. Flynn
//     -Added support for URL and InputStream initializers.
//  2007/09/16  Martin D. Flynn
//     -Added method 'insertKeyValues'
//     -Added support for key/value replace in config-file value strings
// ----------------------------------------------------------------------------
package com.digitrinity.parser.dateutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RTProperties
    implements Cloneable
{

    // ------------------------------------------------------------------------

    public static final String KEY_NAME                 = "$name";

    public static final char   NameSeparatorChar        = ':';
    public static final char   KeyValSeparatorChar      = StringTools.KeyValSeparatorChar;
    public static final char   PropertySeparatorChar    = StringTools.PropertySeparatorChar;
    private static Logger logger = LogManager.getLogger(RTProperties.class.getName());
    public static final char   ARRAY_DELIM              = StringTools.ARRAY_DELIM;
    
    // ------------------------------------------------------------------------

    public static final String KEY_START_DELIMITER      = "${";
    public static final String KEY_END_DELIMITER        = "}";
    public static final int    KEY_MAX_RECURSION        = 5;

    public static final int    KEY_REPLACEMENT_NONE     = 0;
    public static final int    KEY_REPLACEMENT_LOCAL    = 1;
    public static final int    KEY_REPLACEMENT_GLOBAL   = 2;

    private static final boolean DEFAULT_TRUE_IF_BOOLEAN_STRING_EMPTY = true;

    // ------------------------------------------------------------------------

    private Map     cfgProperties           = null;
    private boolean ignoreCase              = false;
    private boolean chkEnvProperties        = false;
    private char    propertySeparator       = PropertySeparatorChar;
    private int     keyReplacementMode      = KEY_REPLACEMENT_NONE;

    // ------------------------------------------------------------------------

    private static int _findKeyValSeparator(String kv)
    {
        //return kv.indexOf('=');
        for (int i = 0; i < kv.length(); i++) {
            char ch = kv.charAt(i);
            if ((ch == '=') || (ch == ':')) {
                return i;
            }
        }
        return -1;
    }

    // ------------------------------------------------------------------------

    /* default initializer */
    public RTProperties(Map map)
    {
        this.cfgProperties = map;
    }

    /* standard properties */
    public RTProperties()
    {
        this((Map)null);
    }

    /* String properties */
    public RTProperties(String props)
    {
        this();
        this.setProperties(props, true);
    }

    /* String properties */
    public RTProperties(String props, char propSep)
    {
        this();
        this.setPropertySeparatorChar(propSep);
        this.setProperties(props, true);
    }

    /* command-line argument properties */
    // Prefixing '-' on keys will be removed.
    public RTProperties(String argv[])
    {
        this();
        if (argv != null) {
            for (int i = 0; i < argv.length; i++) {
                int p = _findKeyValSeparator(argv[i]); // argv[i].indexOf("=");
                String key = (p >= 0)? argv[i].substring(0, p)  : argv[i];
                String val = (p >= 0)? argv[i].substring(p + 1) : "";
                while (key.startsWith("-")) { key = key.substring(1); }
                if (!key.equals("")) {
                    this.setString(key, val);
                }
            }
        }
    }

    /* file properties */
    public RTProperties(File cfgFile)
    {
        this(CreateDefaultMap());
        if ((cfgFile == null) || cfgFile.equals("")) {
            // ignore this case
        } else
        if (cfgFile.isFile()) {
            if (!RTConfig.getBoolean(RTKey.RT_QUIET,true)) {
                logger.info("Loading config file: " + cfgFile);
            }
            try {
                this.setProperties(cfgFile, true);
            } catch (IOException ioe) {
               logger.error("Unable to load config file: " + cfgFile + " [" + ioe + "]");
            }
        } else {
            logger.error("Config file doesn't exist: " + cfgFile);
        }
    }

    /* file properties */
    public RTProperties(URL cfgURL)
    {
        this(CreateDefaultMap());
        if (cfgURL == null) {
            // ignore this case
        } else {
            if (!RTConfig.getBoolean(RTKey.RT_QUIET,true)) {
                logger.info("Loading config file: " + cfgURL);
            }
            try {
                this.setProperties(cfgURL, true);
            } catch (IOException ioe) {
                logger.error("Unable to load config file: " + cfgURL + " [" + ioe + "]");
            }
        }
    }

    /* copy constructor */
    public RTProperties(RTProperties rtp)
    {
        this();
        this.setProperties(rtp, true);
    }

    // ------------------------------------------------------------------------

    public Object clone()
    {
        return new RTProperties(this);
    }

    // ------------------------------------------------------------------------

    public boolean getIgnoreKeyCase()
    {
        return this.ignoreCase;
    }

    public void setIgnoreKeyCase(boolean ignCase)
    {
        this.ignoreCase = ignCase;
        Map props = this.getProperties();
        if (props instanceof OrderedMap) {
            ((OrderedMap)props).setIgnoreCase(this.ignoreCase);
        } else {
            logger.warn("Backing map is not an 'OrderedMap', case insensitive keys not in effect");
        }
    }

    // ------------------------------------------------------------------------

    public boolean getCheckEnvProperties()
    {
        return this.chkEnvProperties;
    }

    public void setCheckEnvProperties(boolean chkEnv)
    {
        // The purpose of this is to set the environment variable properties as the 
        // backstop when obtaining default property values.  Currently this is only
        // used by RTKey when setting the properties for defaults.  In the JDK 1.5
        // the System environment is available as a Map, which will make this a bit
        // easier when this code is eventually converted to JDK 1.5.
        this.chkEnvProperties = chkEnv;
    }

    protected boolean _hasEnvironmentProperty(String key)
    {
        try {
            return this.getCheckEnvProperties() && (key != null) && (System.getenv(key) != null);
        } catch (Throwable th) {
            // 'getenv' may not be supported
            return false;
        }
    }

    protected String _getEnvironmentProperty(String key)
    {
        try {
            return (this.getCheckEnvProperties() && (key != null))? System.getenv(key) : null;
        } catch (Throwable th) {
            // 'getenv' may not be supported
            return null;
        }
    }

    // ------------------------------------------------------------------------

    public String getName()
    {
        return this.getString(KEY_NAME, "");
    }

    public void setName(String name)
    {
        this.setString(KEY_NAME, name);
    }

    // ------------------------------------------------------------------------

    public void checkDefaults()
    {
        // This produces a list of keys in the properties list for which RTKey has not 
        // default value.  This is typically for listing unregistered, and possibly 
        // obsolete, properties found in a config file.
        for (Iterator i = this.keyIterator(); i.hasNext();) {
            String key = i.next().toString();
            if (!RTKey.hasDefault(key)) {
               logger.debug("No default for key: " + key);
            }
        }
    }

    // ------------------------------------------------------------------------

    protected static Class DefaultMapClass = OrderedMap.class;

    protected static Map CreateDefaultMap()
    {
        try {
            Map map = (Map)DefaultMapClass.newInstance();
            return map;
        } catch (Throwable t) {
            // Give up and try a Hashtable (Do not use 'Print' here!!!)
            System.out.println("[RTProperties] Error instantiating: " + DefaultMapClass); // 
            return new OrderedMap();
        }
    }

    // ------------------------------------------------------------------------

    public Map getProperties()
    {
        if (this.cfgProperties == null) { 
            this.cfgProperties = CreateDefaultMap();
            if (this.cfgProperties instanceof OrderedMap) {
                ((OrderedMap)this.cfgProperties).setIgnoreCase(this.ignoreCase);
            }
        }
        return this.cfgProperties;
    }

    public Set getPropertyKeys()
    {
        return this.getProperties().keySet();
    }

    public Iterator keyIterator()
    {
        return this.getPropertyKeys().iterator();
    }

    public Set getPropertyKeys(String startsWith)
    {
        OrderedSet keys = new OrderedSet();
        for (Iterator i = this.keyIterator(); i.hasNext();) {
            String k = (String)i.next(); // assume keys can only be Strings
            if (StringTools.startsWithIgnoreCase(k, startsWith)) {
                keys.add(k);
            }
        }
        return keys;
    }

    public RTProperties getSubset(String keyStartsWith)
    {
        RTProperties rtp = new RTProperties();
        for (Iterator i = this.keyIterator(); i.hasNext();) {
            Object k = i.next();
            if (k instanceof String) {
                String ks = (String)k;
                if (StringTools.startsWithIgnoreCase(ks,keyStartsWith)) {
                    String v = this.getString(ks, null);
                    rtp.setProperty(ks, v);
                }
            }
        }
        return rtp;
    }

    /* Extract a Map containing a group of key/values from the runtime config */
    public Map extractMap(String keyEnd, String valEnd)
    {
        Map m = new OrderedMap();
        for (Iterator i = this.keyIterator(); i.hasNext();) {
            String mkKey = (String)i.next();
            if (mkKey.endsWith(keyEnd)) {
                String key = getString(mkKey, null);
                if (key != null) { // <-- will never be null anyway
                    String mvKey = mkKey.substring(0, mkKey.length() - keyEnd.length()) + valEnd;
                    String val = this.getString(mvKey, "");
                    m.put(key, val);
                }
            }
        }
        return m;
    }

    // ------------------------------------------------------------------------

    public boolean hasProperty(Object key)
    {
        if (key == null) {
            return false;
        } else
        if (this.getProperties().containsKey(key)) {
            return true;
        } else
        if (this._hasEnvironmentProperty(key.toString())) {
            return true;
        } else {
            return false;
        }
    }

    // ------------------------------------------------------------------------

    public void setProperty(Object key, Object value)
    {
        if (key != null) {
            Map props = this.getProperties();

            /* "!<key>" implies removable of <key> from Map (value is ignored) */
            String k = (key instanceof String)? (String)key : null;
            if ((k != null) && !k.equals("") && ("|!^".indexOf(k.charAt(0)) >= 0)) {
                key   = k.substring(1);
                value = null;
            }
            
            /* encode arrays? */
            if ((value != null) && value.getClass().isArray()) {
                Class arrayClass = value.getClass();
                if (arrayClass.getComponentType().isPrimitive()) {
                    value = StringTools.encodeArray(value, ARRAY_DELIM, false);
                } else {
                    Object a[] = (Object[])value;
                    boolean quote = (a instanceof Number[])? false : true;
                    value = StringTools.encodeArray(a, ARRAY_DELIM, quote);
                }
            } else {
                //
            }

            /* add/remove key/value */
            if (!(props instanceof Properties) || (key instanceof String)) {
                if (value == null) {                   
                    props.remove(key);
                } else
                if ((props instanceof OrderedMap) && key.equals(KEY_NAME)) {                    
                    ((OrderedMap)props).put(0, key, value);
                } else {                  
                    props.put(key, value);
                }
            } 
        }
    }

    // ------------------------------------------------------------------------

    public String setProperties(RTProperties rtp)
    {
        return this.setProperties(rtp, false);
    }

    public String setProperties(RTProperties rtp, boolean inclName)
    {
        if (rtp != null) {
            return this.setProperties(rtp.getProperties(), inclName);
        } else {
            return null;
        }
    }

    // ------------------------------------------------------------------------

    public String setProperties(URL url)
        throws IOException
    {
        return this.setProperties(url, false);
    }
    
    public String setProperties(URL url, boolean inclName)
        throws IOException
    {
        String name = null;
        if (url != null) {
            InputStream uis = url.openStream();
            try {
                name = this.setProperties(uis, inclName);
            } finally {
                try { uis.close(); } catch (IOException ioe) {/*ignore*/}
            }
        }
        return name;
    }

    // ------------------------------------------------------------------------

    public String setProperties(File file)
        throws IOException
    {
        return this.setProperties(file, false);
    }
    
    public String setProperties(File file, boolean inclName)
        throws IOException
    {
        String name = null;
        if (file != null) {
            FileInputStream fis = new FileInputStream(file);
            try {
                name = this.setProperties(fis, inclName);
            } finally {
                try { fis.close(); } catch (IOException ioe) {/*ignore*/}
            }
        }
        return name;
    }
    
    // ------------------------------------------------------------------------

    public String setProperties(InputStream in)
        throws IOException
    {
        return this.setProperties(in, false);
    }
    
    public String setProperties(InputStream in, boolean inclName)
        throws IOException
    {
        OrderedProperties props = new OrderedProperties();
        props.load(in); // "props.put(key,value)" is used for insertion
        return this.setProperties(props.getOrderedMap(), inclName);
    }
    
    // ------------------------------------------------------------------------

    public String setProperties(Map props)
    {
        return this.setProperties(props, false);
    }

    public String setProperties(Map props, boolean saveName)
    {
        // Note: Does NOT remove old properties (by design)
        if (props != null) {
            String n = null;
            for (Iterator i = props.keySet().iterator(); i.hasNext();) {
                Object key = i.next();
                Object val = props.get(key);
                if (KEY_NAME.equals(key)) {
                    n = (val != null)? val.toString() : null;
                    if (saveName) {
                        this.setName(n);
                    }
                } else {
                    this.setProperty(key, val);
                }
            }
            return n;
        } else {
            return null;
        }
    }

    // ------------------------------------------------------------------------
    
    public void setPropertySeparatorChar(char propSep)
    {
        this.propertySeparator = propSep;
    }
    
    public char getPropertySeparatorChar()
    {
        return this.propertySeparator;
    }

    public String setProperties(String props)
    {
        return this.setProperties(props, false);
    }

    public String setProperties(String props, boolean saveName)
    {
        if (props != null) {
            char propSep = this.getPropertySeparatorChar();

            /* check for prefixing name in string (ie. "[name] key=value") */
            String n = null, p = props.trim();
            if (p.startsWith("[")) {
                int x = p.indexOf("]");
                if (x > 0) {
                    // found "[name]"
                    n = p.substring(1,x).trim();
                    p = p.substring(x+1).trim();
                } else {
                    // missing name terminating ']'
                    p = p.substring(1).trim(); // just skip first '['
                }
            }

            /* parse and set properties */
            Map propMap = StringTools.parseProperties(p, propSep);
            if (n == null) {
                n = this.setProperties(propMap, saveName);
            } else {
                this.setProperties(propMap, false);
                if (saveName) {
                    this.setName(n);
                }
            }

            /* return name, if any */
            return n;

        } else {

            return null;

        }
    }

    // ------------------------------------------------------------------------

    public void removeProperty(Object key)
    {
        if (key != null) {
            Map props = this.getProperties();
            if (!(props instanceof Properties) || (key instanceof String)) {
                props.remove(key);
            }
        }
    }

    public void clearProperties()
    {
        this.getProperties().clear();
    }

    public void resetProperties(Map props)
    {
        this.clearProperties();
        this.setProperties(props, true);
    }
                                          
    // ------------------------------------------------------------------------

    public String insertKeyValues(String text)   
    {
        return this._insertKeyValues(null, text, KEY_START_DELIMITER, KEY_END_DELIMITER);
    }

    public String insertKeyValues(String text, String startDelim, String endDelim)
    {
        return this._insertKeyValues(null, text, startDelim, endDelim);
    }

    public String _insertKeyValues(Object key, String text)   
    {
        return this._insertKeyValues(key, text, KEY_START_DELIMITER, KEY_END_DELIMITER);
    }

    public String _insertKeyValues(final Object mainKey, String text, String startDelim, String endDelim)
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
                        Object obj = RTProperties.this._getProperty(k, null);
                        return (obj != null)? obj.toString() : null;
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
            return text; // return null
        }
    }

    // ------------------------------------------------------------------------

    public void setKeyReplacementMode(int mode)
    {
        this.keyReplacementMode = mode;
    }
    
    private Object _replaceKeyValues(Object key, Object obj)   
    {
        if (this.keyReplacementMode == KEY_REPLACEMENT_NONE) {          
            return obj;
        } else
        if ((obj == null) || !(obj instanceof String)) {           
            return obj;
        } else
        if (this.keyReplacementMode == KEY_REPLACEMENT_LOCAL) {          
            return this._insertKeyValues(key,(String)obj);
        } else {           
            return RTConfig._insertKeyValues(key,(String)obj);
        }
    }

    private Object _getProperty(Object key, Object dft, Class dftClass, boolean replaceKeys)
    {
        Object value = this.getProperties().get(key);
        if (value == null) {
            if (key != null) {
                String env = this._getEnvironmentProperty(key.toString());
                if (env != null) {
                    return replaceKeys? this._replaceKeyValues(key,env) : env;
                }
            }
            return replaceKeys? this._replaceKeyValues(key,dft) : dft; // no value, return default
        } else
        if ((dft == null) && (dftClass == null)) {
            return replaceKeys? this._replaceKeyValues(key,value) : value; // return as-is
        } else {
            // convert 'value' to same type (class) as 'dft' (if specified)
            Class c = (dftClass != null)? dftClass : dft.getClass();
            try {
                return convertToType(replaceKeys? this._replaceKeyValues(key,value) : value, c);
            } catch (Throwable t) {
                return replaceKeys? this._replaceKeyValues(key,dft) : dft; // inconvertable, return as-is
            }
        }
    }

    public Object _getProperty(Object key, Object dft)
    {
        return this._getProperty(key, dft, null, false);
    }

    public Object getProperty(Object key, Object dft)
    {
        return this._getProperty(key, dft, null, true);
    }

    protected static Object convertToType(Object val, Class type)
        throws Throwable
    {
        if ((type == null) || (val == null)) {
            // not converted
            return val;
        } else
        if (type.isAssignableFrom(val.getClass())) {
            // already converted
            return val;
        } else
        if (type == String.class) {
            // convert to String
            return val.toString();
        } else {
            // IE:
            //   new File(String.class)
            //   new Long(String.class)
            try {
                Constructor meth = type.getConstructor(new Class[] { type });
                return meth.newInstance(new Object[] { val });
            } catch (Throwable t1) {
                try {
                    Constructor meth = type.getConstructor(new Class[] { String.class });
                    return meth.newInstance(new Object[] { val.toString() });
                } catch (Throwable t2) {
                    logger.error("Can't convert value to " + type.getName() + ": " + val);
                    throw t2; // inconvertable
                }
            }
        }
    }

    // ------------------------------------------------------------------------

    public String getString(String key)
    {
        return this.getString(key, null);
    }

    public String getString(String key, String dft)
    {
        Object val = this._getProperty(key, dft, String.class, true);
        if (val == null) {
            return null;
        } else
        if (val.equals(RTKey.NULL_VALUE)) {
            return null;
        } else {
            return val.toString();
        }
    }

    public void setString(String property, String value)
    {
        this.setProperty(property, value);
    }

    // ------------------------------------------------------------------------

    public String[] getStringArray(String key)
    {
        return this.getStringArray(key, null);
    }

    public String[] getStringArray(String key, String dft[])
    {
        String val = this.getString(key, null);
        if (val == null) {
            return dft;
        } else {
            String va[] = StringTools.parseArray(val);
            // TODO: check for RTKey.NULL_VALUE in string array
            return va;
        }
    }

    public void setStringArray(String key, String val[])
    {
        this.setStringArray(key, val, true);
    }

    public void setStringArray(String key, String val[], boolean alwaysQuote)
    {
        String valStr = StringTools.encodeArray(val, ARRAY_DELIM, alwaysQuote);
        this.setString(key, valStr);
    }

    public void setProperty(String key, String val[])
    {
        this.setStringArray(key, val, true);
    }

    // ------------------------------------------------------------------------

    public File getFile(String key)
    {
        return this.getFile(key, null);
    }

    // do not include the following method, otherwise "getFile(file, null)" would be ambiguous
    //public File getFile(String key, String dft)

    public File getFile(String key, File dft)
    {
        Object val = this._getProperty(key, null, null, true);
        if (val == null) {
            return dft;
        } else
        if (val instanceof File) {
            return (File)val;
        } else {
            return new File(val.toString());
        }
    }

    public void setFile(String key, File value)
    {
        this.setProperty(key, value);
    }

    // ------------------------------------------------------------------------

    public double getDouble(String key)
    {
        return this.getDouble(key, 0.0);
    }

    public double getDouble(String key, double dft)
    {
        Object val = this._getProperty(key, null, null, true);
        if (val == null) {
            return dft;
        } else
        if (val instanceof Number) {
            return ((Number)val).doubleValue();
        } else {
            return StringTools.parseDouble(val.toString(), dft);
        }
    }

    public double[] getDoubleArray(String key, double dft[])
    {
        String val[] = this.getStringArray(key, null);
        if (val == null) {
            return dft;
        } else {
            double n[] = new double[val.length];
            for (int i = 0; i < val.length; i++) {
                n[i] = StringTools.parseDouble(val[i], 0.0);
            }
            return n;
        }
    }

    public void setDouble(String key, double value)
    {
        this.setProperty(key, value);
    }

    public void setDoubleArray(String key, double value[])
    {
        this.setProperty(key, value);
    }

    public void setProperty(String key, double value)
    {
        this.setProperty(key, new Double(value));
    }

    // ------------------------------------------------------------------------

    public float getFloat(String key)
    {
        return this.getFloat(key, 0.0F);
    }

    public float getFloat(String key, float dft)
    {
        Object val = this._getProperty(key, null, null, true);
        if (val == null) {
            return dft;
        } else
        if (val instanceof Number) {
            return ((Number)val).floatValue();
        } else {
            return StringTools.parseFloat(val.toString(), dft);
        }
    }

    public float[] getFloatArray(String key, float dft[])
    {
        String val[] = this.getStringArray(key, null);
        if (val == null) {
            return dft;
        } else {
            float n[] = new float[val.length];
            for (int i = 0; i < val.length; i++) {
                n[i] = StringTools.parseFloat(val[i], 0.0F);
            }
            return n;
        }
    }

    public void setFloat(String key, float value)
    {
        this.setProperty(key, value);
    }

    public void setFloatArray(String key, float value[])
    {
        this.setProperty(key, value);
    }

    public void setProperty(String key, float value)
    {
        this.setProperty(key, new Float(value));
    }

    // ------------------------------------------------------------------------

    public long getLong(String key)
    {
        return this.getLong(key, 0L);
    }

    public long getLong(String key, long dft)
    {
        Object val = this._getProperty(key, null, null, true);
        if (val == null) {
            return dft;
        } else
        if (val instanceof Number) {
            return ((Number)val).longValue();
        } else {
            return StringTools.parseLong(val.toString(), dft);
        }
    }

    public long[] getLongArray(String key, long dft[])
    {
        String val[] = this.getStringArray(key, null);
        if (val == null) {
            return dft;
        } else {
            long n[] = new long[val.length];
            for (int i = 0; i < val.length; i++) {
                n[i] = StringTools.parseLong(val[i], 0L);
            }
            return n;
        }
    }

    public void setLong(String key, long value)
    {
        this.setProperty(key, value);
    }

    public void setLongArray(String key, long value[])
    {
        this.setProperty(key, value);
    }

    public void setProperty(String key, long value)
    {
        this.setProperty(key, new Long(value));
    }

    // ------------------------------------------------------------------------

    public int getInt(String key)
    {
        return this.getInt(key, 0);
    }

    public int getInt(String key, int dft)
    {
        Object val = this._getProperty(key, null, null, true);
        if (val == null) {
            return dft;
        } else
        if (val instanceof Number) {
            return ((Number)val).intValue();
        } else {
            return StringTools.parseInt(val.toString(), dft);
        }
    }

    public int[] getIntArray(String key, int dft[])
    {
        String val[] = this.getStringArray(key, null);
        if (val == null) {
            return dft;
        } else {
            int n[] = new int[val.length];
            for (int i = 0; i < val.length; i++) {
                n[i] = StringTools.parseInt(val[i], 0);
            }
            return n;
        }
    }

    public void setInt(String key, int value)
    {
        this.setProperty(key, value);
    }

    public void setIntArray(String key, int value[])
    {
        this.setProperty(key, value);
    }

    public void setProperty(String key, int value)
    {
        this.setProperty(key, new Integer(value));
    }

    // ------------------------------------------------------------------------

    public boolean getBoolean(String key)
    {
        boolean dft = false;
        return this._getBoolean_dft(key, dft, true);
    }

    public boolean getBoolean(String key, boolean dft)
    {
        return this._getBoolean_dft(key, dft, DEFAULT_TRUE_IF_BOOLEAN_STRING_EMPTY);
    }

    private boolean _getBoolean_dft(String key, boolean dft, boolean dftTrueIfEmpty)
    {
        Object val = this._getProperty(key, null, null, true);
        if (val == null) {
            return dft;
        } else
        if (val instanceof Boolean) {
            return ((Boolean)val).booleanValue();
        } else
        if (val.toString().equals("")) {
            return dftTrueIfEmpty? true : dft;
        } else {
            return StringTools.parseBoolean(val.toString(), dft);
        }
    }

    public void setBoolean(String key, boolean value)
    {
        this.setProperty(key, value);
    }

    public void setBooleanArray(String key, boolean value[])
    {
        this.setProperty(key, value);
    }

    public void setProperty(String key, boolean value)
    {
        this.setProperty(key, new Boolean(value));
    }

    // ------------------------------------------------------------------------

    public void printProperties()
    {
        this.printProperties(null, null);
    }

    public void printProperties(RTProperties exclProps)
    {
        this.printProperties(exclProps, null);
    }

    public void printProperties(Collection orderBy)
    {
        this.printProperties(null, orderBy);
    }

    public void printProperties(RTProperties exclProps, Collection orderBy)
    {
       logger.debug(this.toString(exclProps, orderBy, true));
    }

    // ------------------------------------------------------------------------

    public boolean equals(Object other)
    {
        if (other instanceof RTProperties) {
            // We need to perform our own 'equals' checking here:
            // Two RTProperties are equal if they contain the same properties irrespective of ordering.
            // [All property values are compared as Strings]
            RTProperties rtp = (RTProperties)other;
            Map M1 = this.getProperties();
            Map M2 = rtp.getProperties();
            if (M1.size() == M2.size()) {
                for (Iterator i = M1.keySet().iterator(); i.hasNext();) {
                    Object key = i.next();
                    if (M2.containsKey(key)) {
                        Object m1Val = M1.get(key);
                        Object m2Val = M2.get(key);
                        String m1ValStr = (m1Val != null)? m1Val.toString() : null;
                        String m2ValStr = (m2Val != null)? m2Val.toString() : null;
                        if (m1Val == m2Val) {
                            continue; // they are the same object (or both null)
                        } else
                        if ((m1ValStr != null) && m1ValStr.equals(m2ValStr)) {
                            continue; // the values are equals
                        } else {                            
                            return false; // values are not equal
                        }
                    } else {                      
                        return false; // key doesn't exist in M2
                    }
                }
                return true; // all key/vals matched
            } else {                
                return false;
            }
        } else {
            return false;
        }
    }

    // ------------------------------------------------------------------------
    
    public void saveProperties(File cfgFile)
        throws IOException
    {

        /* property maps */
        Map propMap = this.getProperties();

        /* encode properties */
        StringBuffer strProps = new StringBuffer();
        for (Iterator i = propMap.keySet().iterator(); i.hasNext();) {
            Object keyObj = i.next();
            Object valObj = propMap.get(keyObj);
            strProps.append(keyObj.toString());
            strProps.append(KeyValSeparatorChar);
            if (valObj != null) {
                strProps.append(valObj.toString());
            }
            strProps.append("\n");
        }
        
        /* save to file */
        FileTools.writeFile(strProps.toString().getBytes(), cfgFile);

    }

    // ------------------------------------------------------------------------

    public String toString()
    {
        return this.toString(null, null, false);
    }

    public String toString(RTProperties exclProps)
    {
        return this.toString(exclProps, null, false);
    }

    public String toString(Collection orderBy)
    {
        return this.toString(null, orderBy, false);
    }

    public String toString(RTProperties exclProps, Collection orderBy)
    {
        return this.toString(null, orderBy, false);
    }

    public String toString(RTProperties exclProps, Collection orderBy, boolean inclNewline)
    {
        StringBuffer sb = new StringBuffer();

        /* append name */
        String n = this.getName();
        if (!n.equals("")) {
            sb.append(n).append(NameSeparatorChar).append(" ");
        }

        /* property maps */
        Map propMap = this.getProperties();
        Map exclMap = (exclProps != null)? exclProps.getProperties() : null;

        /* order by */
        Set orderSet = null;
        if (orderBy != null) {
            orderSet = new OrderedSet(orderBy, true);
            orderSet.addAll(propMap.keySet());
            // 'orderSet' now contains the union of keys from 'orderBy' and 'propMap.keySet()'
        } else {
            orderSet = propMap.keySet();
        }

        /* encode properties */
        for (Iterator i = orderSet.iterator(); i.hasNext();) {
            Object keyObj = i.next(); // possible this key doesn't exist in 'propMap' if 'orderBy' used.
            if (!KEY_NAME.equals(keyObj) && propMap.containsKey(keyObj)) {
                Object valObj = propMap.get(keyObj); // key guaranteed here to be in 'propMap'
                if ((exclMap == null) || !RTProperties.compareMapValues(valObj, exclMap.get(keyObj))) {
                    sb.append(keyObj.toString()).append(KeyValSeparatorChar);
                    String v = (valObj != null)? valObj.toString() : "";
                    if ((v.indexOf(" ") >= 0) || (v.indexOf("\t") >= 0) || (v.indexOf("\"") >= 0)) {
                        sb.append(StringTools.quoteString(v));
                    } else {
                        sb.append(v);
                    }
                    if (inclNewline) {
                        sb.append("\n");
                    } else
                    if (i.hasNext()) {
                        sb.append(" ");
                    }
                } 
            }
        }
        return sb.toString().trim();

    }

    private static boolean compareMapValues(Object value, Object target)
    {
        if ((value == null) && (target == null)) {
            return true;
        } else
        if ((value == null) || (target == null)) {
            return false;
        } else
        if (value.equals(target)) {
            return true;
        } else {
            return value.toString().equals(target.toString());
        }
    }

    // ------------------------------------------------------------------------

  
	@SuppressWarnings("serial")
	public static class OrderedProperties extends Properties
    {
        private OrderedMap orderedMap = null;
        public OrderedProperties() {
            super();
            this.orderedMap = new OrderedMap();
        }
        public Object put(Object key, Object value) {
            Object rtn = super.put(key, value);
            this.orderedMap.put(key, value);
            return rtn;
        }
        public OrderedMap getOrderedMap() {
            return this.orderedMap;
        }
    }

    // ------------------------------------------------------------------------

}
