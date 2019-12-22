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
//  This class provides an ordered HashMap
// ----------------------------------------------------------------------------
// Change History:
//  2006/03/26  Martin D. Flynn
//      Initial release
//  2006/06/30  Martin D. Flynn
//      Repackaged to "org.opengts.util"
// ----------------------------------------------------------------------------
package com.digitrinity.parser.dateutil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("serial")
public class OrderedMap extends HashMap
    implements Map
{
    
    // ------------------------------------------------------------------------
    
    private OrderedSet  keyOrder = null;
    private Map         ignoredCaseMap = null;
    private static Logger logger = LogManager.getLogger(OrderedMap.class.getName());
    
    public OrderedMap() 
    {
        super();
        this.keyOrder = new OrderedSet();
    }
    
    public OrderedMap(Map map) 
    {
        this();
        this.putAll(map);
    }
    
    // ------------------------------------------------------------------------

    public boolean isIgnoreCase()
    {
        return (this.ignoredCaseMap != null);
    }
    
    public void setIgnoreCase(boolean ignoreCase)
    {
        if (ignoreCase) {
            if (this.ignoredCaseMap == null) {
                this.ignoredCaseMap = new HashMap();
                for (Iterator i = this.keyOrder.iterator(); i.hasNext();) {
                    Object key = i.next();
                    if (key instanceof String) {
                        this.ignoredCaseMap.put(((String)key).toLowerCase(), key);
                    }
                }
            }
        } else {
            if (this.ignoredCaseMap != null) {
                this.ignoredCaseMap = null;
            }
        }
    }
    
    public Object keyCaseFilter(Object key)
    {
        if ((this.ignoredCaseMap != null) && (key instanceof String)) {
            Object k = this.ignoredCaseMap.get(((String)key).toLowerCase());
            if (k != null) {
                return k;
            }
        }
        return key;
    }
    
    // ------------------------------------------------------------------------
    
    public void clear() 
    {
        super.clear();
        this.keyOrder.clear();
        if (this.ignoredCaseMap != null) { 
            this.ignoredCaseMap.clear(); 
        }
    }
    
    // ------------------------------------------------------------------------
    
    public Set entrySet()
    {
        // Attempting to return an ordered set of 'Map.Entry' entries.
        // The effect this will have on calls to this method from HashMap itself
        // isn't fully known.

        /* Map.Entry map */
        Set es = super.entrySet(); // unordered
        HashMap meMap = new HashMap();
        for (Iterator i = es.iterator(); i.hasNext();) {
            Map.Entry me = (Map.Entry)i.next();
            Object key = me.getKey();
            meMap.put(key, me);
        }
        
        /* place in keyOrder */
        OrderedSet entSet = new OrderedSet();
        for (Iterator i = this.keyOrder.iterator(); i.hasNext();) {
            Object key = i.next();
            Object me  = meMap.get(key);
            if (me == null) { logger.error("Map.Entry is null!!!"); }
            entSet.add(me);
        }
        return entSet;

    }
    
    // ------------------------------------------------------------------------

    public Set keySet()
    {
        return new OrderedSet(this.keyOrder);
    }
    
    public Set valueSet()
    {
        OrderedSet valSet = new OrderedSet();
        for (Iterator i = this.keyOrder.iterator(); i.hasNext();) {
            valSet.add(super.get(i.next()));
        }
        return valSet;
    }
    
    // ------------------------------------------------------------------------
        
    public Collection values()
    {
        // All this work is to make sure the returned Collection is still backed by the Map
        return new ListTools.CollectionProxy(super.values()) {
            public Iterator iterator() {
                return new Iterator() {
                    private Iterator i = OrderedMap.this.keySet().iterator();
                    public boolean hasNext() {
                        return i.hasNext();
                    }
                    public Object next() {
                        return OrderedMap.this.get(i.next());
                    }
                    public void remove() {
                        throw new UnsupportedOperationException("'remove' not supported here");
                    }
                };
            }
            public Object[] toArray() {
                return ListTools.toList(this.iterator()).toArray();
            }
            public Object[] toArray(Object a[]) {
                return ListTools.toList(this.iterator()).toArray(a);
            }
        };
    }
    
    // ------------------------------------------------------------------------
    
    public Object put(int ndx, Object key, Object value) 
    {
        if ((this.ignoredCaseMap != null) && (key instanceof String)) {
            this.ignoredCaseMap.put(((String)key).toLowerCase(), key);
        }
        this.keyOrder.add(ndx, key);
        return super.put(key, value);
    }
    
    public Object put(Object key, Object value) 
    {
        if ((this.ignoredCaseMap != null) && (key instanceof String)) {
            this.ignoredCaseMap.put(((String)key).toLowerCase(), key);
        }
        this.keyOrder.add(key);
        return super.put(key, value);
    }
    
    public Object setProperty(String key, String value) 
    {
        return this.put(key, value);
    }
    
    public void putAll(Map map)
    {
        for (Iterator i = map.keySet().iterator(); i.hasNext();) {
            Object key = i.next();
            this.put(key, map.get(key));
        }
    }
    
    // ------------------------------------------------------------------------
    
    public boolean containsKeyIgnoreCase(String key)
    {
        if (key != null) {
            // TODO: Optimize!
            for (Iterator i = this.keyOrder.iterator(); i.hasNext();) {
                Object k = i.next();
                if ((k instanceof String) && key.equalsIgnoreCase((String)k)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean containsKey(Object key)
    {
        return super.containsKey(this.keyCaseFilter(key));
    }
    
    public int indexOfKey(Object key)
    {
        Object k = this.keyCaseFilter(key);
        return this.keyOrder.indexOf(k);
    }
    
    // ------------------------------------------------------------------------
    
    public Object remove(Object key)
    {
        Object k = this.keyCaseFilter(key);
        if ((this.ignoredCaseMap != null) && (key instanceof String)) {
            this.ignoredCaseMap.remove(((String)key).toLowerCase());
        }
        this.keyOrder.remove(k);
        return super.remove(k);
    }
        
    // ------------------------------------------------------------------------
    
    public Object get(Object key)
    {
        return super.get(this.keyCaseFilter(key));
    }
    
    public String getProperty(String key, String dft)
    {
        if (this.containsKey(key)) {
            Object val = this.get(key);
            return (val != null)? val.toString() : null;
        } else {
            return dft;
        }
    }
    
    public String getProperty(String key)
    {
        return this.getProperty(key, null);
    }
    
    // ------------------------------------------------------------------------

    public Object getKey(int ndx)
    {
        return ((ndx >= 0) && (ndx < this.keyOrder.size()))? this.keyOrder.get(ndx) : null;
    }

    public Object getValue(int ndx)
    {
        Object key = this.getKey(ndx); // returns null if 'ndx' is invalid
        return (key != null)? super.get(key) : null;
    }
    
    public void remove(int ndx)
    {
        this.remove(this.getKey(ndx));
    }
    
    public Iterator keys() 
    {
        // copied to prevent 'remove'
        return (new Vector(this.keyOrder)).iterator(); 
    }
    
    // ------------------------------------------------------------------------
    
}
