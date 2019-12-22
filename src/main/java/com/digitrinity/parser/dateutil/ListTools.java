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
//  This class provides many List/Collection/Array based utilities.
// ----------------------------------------------------------------------------
// Change History:
//  2006/03/26  Martin D. Flynn
//      Initial release
//  2006/06/30  Martin D. Flynn
//      Repackaged to "org.opengts.util"
// ----------------------------------------------------------------------------
package com.digitrinity.parser.dateutil;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

public class ListTools
{

    // ------------------------------------------------------------------------

    private static int _constrainOffset(int ofs, int length)
    {
        if ((ofs < 0) || (length <= 0)) {
            return 0;
        } else
        if (ofs >= length) {
            return length - 1;
        } else {
            return ofs;
        }
    }

    private static int _constrainLength(int ofs, int len, int length)
    {
        if (len < 0) { 
            return length; 
        } else
        if (len > (length - ofs)) { 
            return length - ofs; 
        } else {
            return len;
        }
    }
    
    // ------------------------------------------------------------------------

    public static java.util.List toList(Object a[])
    {
        return ListTools.toList(a, null);
    }

    public static java.util.List toList(Object a[], java.util.List list)
    {
        return ListTools.toList(a, 0, -1, list);
    }
    
    public static java.util.List toList(Object a[], int ofs, int len, java.util.List list)
    {
        java.util.List v = (list != null)? list : new Vector();
        int alen = (a != null)? a.length : 0;
        ofs = _constrainOffset(ofs, alen);
        len = _constrainLength(ofs, len, alen);
        for (int i = ofs; i < len; i++) { v.add(a[i]); }
        return v;
    }

    // ------------------------------------------------------------------------

    public static java.util.List toList(Enumeration e)
    {
        return ListTools.toList(e, null);
    }

    public static java.util.List toList(Enumeration e, java.util.List list)
    {
        java.util.List v = (list != null)? list : new Vector();
        if (e != null) { for (;e.hasMoreElements();) { v.add(e.nextElement()); } }
        return v;
    }

    // ------------------------------------------------------------------------

    public static java.util.List toList(Iterator i)
    {
        return ListTools.toList(i, null);
    }

    public static java.util.List toList(Iterator i, java.util.List list)
    {
        java.util.List v = (list != null)? list : new Vector();
        if (i != null) { for (;i.hasNext();) { v.add(i.next()); } }
        return v;
    }

    // ------------------------------------------------------------------------

    public static java.util.List toList(Set s)
    {
        return ListTools.toList(s, null);
    }

    public static java.util.List toList(Set s, java.util.List list)
    {
        return ListTools.toList(((s != null)? s.iterator() : null), list);
    }

    // ------------------------------------------------------------------------

    public static java.util.List toList(StringTokenizer st)
    {
        return ListTools.toList(st, null);
    }

    public static java.util.List toList(StringTokenizer st, java.util.List list)
    {
        java.util.List v = (list != null)? list : new Vector();
        if (st != null) { for (;st.hasMoreTokens();) { v.add(st.nextToken()); } }
        return v;
    }

    // ------------------------------------------------------------------------

    public static java.util.List toList(java.util.List ls)
    {
        return ListTools.toList(ls, null);
    }

    public static java.util.List toList(java.util.List ls, java.util.List list)
    {
        java.util.List v = (list != null)? list : new Vector();
        if (ls != null) { v.addAll(ls); }
        return v;
    }

    // ------------------------------------------------------------------------

    public static boolean isClassType(java.util.List list, Class type)
    {
        if ((type == null) || (type == Object.class)) {
            return true;
        } else
        if (list != null) {
            for (Iterator i = list.iterator(); i.hasNext();) {
                Object obj = i.next();
                if ((obj != null) && !type.isAssignableFrom(obj.getClass())) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
    
    // ------------------------------------------------------------------------

    public static Object[] toArray(Enumeration e, Class type)
    {
        return ListTools.toArray(ListTools.toList(e), type);
    }

    public static Object[] toArray(Collection list)
    {
        return ListTools.toArray(list, null);
    }

    public static Object[] toArray(Collection list, Class type)
    {
        if (type == null) { type = Object.class; }
        if (list != null) {
            Object array[] = (Object[])Array.newInstance(type, list.size());
            return list.toArray(array);
        } else {
            return (Object[])Array.newInstance(type, 0);
        }
    }
    
    public static Object[] toArray(Object arry[], int ofs, int len)
    {
        if (arry != null) {
            int alen = arry.length;
            ofs = _constrainOffset(ofs, alen);
            len = _constrainLength(ofs, len, alen);
            Class type = arry.getClass().getComponentType();
            Object newArry[] = (Object[])Array.newInstance(type, len);
            System.arraycopy(arry, ofs, newArry, 0, len);
            return newArry;
        } else {
            return arry;
        }
    }
            
    // ------------------------------------------------------------------------

    public static Set toSet(Object a[])
    {
        return ListTools.toSet(a, null);
    }

    public static Set toSet(Object a[], Set set)
    {
        return ListTools.toSet(a, 0, -1, set);
    }
    
    public static Set toSet(Object a[], int ofs, int len, Set set)
    {
        Set v = (set != null)? set : new HashSet();
        int alen = (a != null)? a.length : 0;
        ofs = _constrainOffset(ofs, alen);
        len = _constrainLength(ofs, len, alen);
        for (int i = ofs; i < len; i++) { v.add(a[i]); }
        return v;
    }
    
    // ------------------------------------------------------------------------

    public static Map toMap(Object arry[][])
    {
        return ListTools.toMap(arry, (Map)null);
    }

    public static Map toMap(Object arry[][], Map map)
    {
        Map m = (map != null)? map : new OrderedMap();
        if (arry != null) {
            for (int i = 0; i < arry.length; i++) {
                if (arry[i].length >= 2) {
                    Object key = arry[i][0], val = arry[i][1];
                    if ((key != null) && (val != null)) {
                        m.put(key, val);
                    }
                }
            }
        }
        return m;
    }
    
    // ------------------------------------------------------------------------

    public static java.util.List add(java.util.List list, Object obj)
    {
        return ListTools.insert(list, obj, -1);
    }
    
    public static java.util.List insert(java.util.List list, Object obj, int ndx)
    {
        if (list != null) { 
            list.add(ndx, obj); 
        }
        return list;
    }
    
    public static java.util.List remove(java.util.List list, int ndx)
    {
        if (list != null) {
            list.remove(ndx);
        }
        return list;
    }

    public static Object[] add(Object list[], Object obj)
    {
        return ListTools.insert(list, obj, -1);
    }
    
    public static Object[] insert(Object list[], Object obj, int index)
        // throws ArrayStoreException
    {
        if (list != null) {
            int ndx = ((index > list.length) || (index < 0))? list.length : index;
            Class type = list.getClass().getComponentType();
            int size = (list.length > ndx)? (list.length + 1) : (ndx + 1);
            Object array[] = (Object[])Array.newInstance(type, size);
            if (ndx > 0) {
                int maxLen = (list.length >= ndx)? ndx : list.length;
                System.arraycopy(list, 0, array, 0, maxLen); 
            }
            array[ndx] = obj; // <-- may throw ArrayStoreException
            if (ndx < list.length) { 
                int maxLen = list.length - ndx;
                System.arraycopy(list, ndx, array, ndx + 1, maxLen); 
            }
            return array;
        } else {
            return null;
        }
    }

    public static Object[] remove(Object list[], int ndx)
    {
        if ((list != null) && (ndx >= 0) && (ndx < list.length)) {
            Class type = list.getClass().getComponentType();
            Object array[] = (Object[])Array.newInstance(type, list.length - 1);
            if (ndx > 0) {
                System.arraycopy(list, 0, array, 0, ndx); 
            }
            if (ndx < (list.length - 1)) { 
                System.arraycopy(list, ndx + 1, array, ndx, list.length - ndx - 1); 
            }
            return array;
        } else {
            return null;
        }
    }
    
    // ------------------------------------------------------------------------

    public static int indexOf(java.util.List list, Object item)
    {
        if (list == null) {
            return -1;
        } else {
            return list.indexOf(item);
        }
    }
    
    public static int indexOfIgnoreCase(java.util.List list, String item)
    {
        if (list == null) {
            return -1;
        } else {
            int index = 0;
            for (Iterator i = list.iterator(); i.hasNext(); index++) {
                Object listObj = i.next();
                String listStr = (listObj != null)? listObj.toString() : null;
                if (listStr == item) { // also takes care of 'null == null'
                    return index;
                } else
                if ((listStr != null) && listStr.equalsIgnoreCase(item)) {
                    return index;
                }
            }
            return -1;
        }
    }

    public static int indexOf(Object list[], Object item)
    {
        return ListTools.indexOf(list, 0, -1, item);
    }
    
    public static int indexOf(Object list[], int ofs, int len, Object item)
    {
        if (list == null) {
            
            /* no list */
            return -1;
            
        } else {
            
            /* constrain offset/length */
            int alen = (list != null)? list.length : 0;
            ofs = _constrainOffset(ofs, alen);
            len = _constrainLength(ofs, len, alen);
            
            /* loop through array checking for item */
            for (int i = ofs; i < len; i++) {
                if (list[i] == item) { // also takes care of 'null == null'
                    return i;
                } else
                if ((list[i] != null) && list[i].equals(item)) {
                    return i;
                }
            }
            
            /* still not found */
            return -1;
            
        }
    }

    public static int indexOfIgnoreCase(String list[], String item)
    {
        if (list == null) {
            return -1;
        } else {
            for (int i = 0; i < list.length; i++) {
                if (list[i] == item) { // also takes care of 'null == null'
                    return i;
                } else
                if ((list[i] != null) && list[i].equalsIgnoreCase(item)) {
                    return i;
                }
            }
            return -1;
        }
    }
    
    // ------------------------------------------------------------------------
    
    public static boolean contains(java.util.List list, Object item)
    {
        return (ListTools.indexOf(list, item) >= 0);
    }
    
    public static boolean containsIgnoreCase(java.util.List list, String item)
    {
        return (ListTools.indexOfIgnoreCase(list, item) >= 0);
    }

    public static boolean contains(Object list[], Object item)
    {
        return (ListTools.indexOf(list, 0, -1, item) >= 0);
    }
    
    public static boolean contains(Object list[], int ofs, int len, Object item)
    {
        return (ListTools.indexOf(list, ofs, len, item) >= 0);
    }

    public static boolean containsIgnoreCase(String list[], String item)
    {
        return (ListTools.indexOfIgnoreCase(list, item) >= 0);
    }
        
    // ------------------------------------------------------------------------
    
    public static java.util.List sort(java.util.List list, Comparator comp)
    {
        return ListTools.sort(list, comp, true);
    }
    
    public static java.util.List sort(java.util.List list, Comparator comp, boolean forwardOrder)
    {
        if (list != null) {
            Comparator c = comp;
            if (c == null) { 
                c = new StringComparator(forwardOrder);
            } else
            if (forwardOrder) {
                c = comp;
            } else {
                c = new ReverseOrderComparator(comp);
            }
            Collections.sort(list, c);
        }
        return list;
    }
    
    public static Object[] sort(Object list[], Comparator comp)
    {
        return ListTools.sort(list, comp, true);
    }
    
    public static Object[] sort(Object list[], Comparator comp, boolean forwardOrder)
    {
        if (list != null) {
            Comparator c = comp;
            if (c == null) { 
                c = new StringComparator(forwardOrder);
            } else
            if (forwardOrder) {
                c = comp;
            } else {
                c = new ReverseOrderComparator(comp);
            }
            Arrays.sort(list, c);
        }
        return list;
    }
    
    public static String[] sort(String list[])
    {
        return (String[])ListTools.sort(list, new StringComparator(), true);
    }
   
    public static String[] sort(String list[], boolean forwardOrder)
    {
        return (String[])ListTools.sort(list, new StringComparator(), forwardOrder);
    }

    public static class StringComparator
        implements Comparator
    {
        private boolean ascending  = true;
        private boolean ignoreCase = false;
        public StringComparator() {
            this(true, false);
        }
        public StringComparator(boolean ascending) {
            this(ascending, false);
        }
        public StringComparator(boolean ascending, boolean ignroeCase) {
            this.ascending  = ascending;
            this.ignoreCase = ignroeCase;
        }
        public int compare(Object o1, Object o2) {
            String s1 = (o1 != null)? o1.toString() : "";
            String s2 = (o2 != null)? o2.toString() : "";
            if (this.ignoreCase) {
                s1 = s1.toLowerCase();
                s2 = s2.toLowerCase();
            }
            return this.ascending? s1.compareTo(s2) : s2.compareTo(s1);
        }
        public boolean equals(Object other) {
            if (other instanceof StringComparator) {
                StringComparator sc = (StringComparator)other;
                return (this.ascending == sc.ascending) && (this.ignoreCase == sc.ignoreCase);
            }
            return false;
        }
    }
    
    public static class ReverseOrderComparator
        implements Comparator
    {
        private Comparator otherComp = null;
        public ReverseOrderComparator(Comparator comp) {
            this.otherComp = (comp != null)? comp : new StringComparator();
        }
        public int compare(Object o1, Object o2) {
            int compVal = this.otherComp.compare(o1, o2);
            if (compVal > 0) { return -1; }
            if (compVal < 0) { return  1; }
            return 0;
        }
        public boolean equals(Object obj) {
            if (obj instanceof ReverseOrderComparator) {
                ReverseOrderComparator descComp = (ReverseOrderComparator)obj;
                return this.otherComp.equals(descComp.otherComp);
            }
            return false;
        }
    }
    
    // ------------------------------------------------------------------------

    /* reverse order in place */
    public static Object[] reverseOrder(Object list[])
    {
        if ((list != null) && (list.length > 1)) {
            int len = list.length / 2;
            for (int i = 0; i < len; i++) {
                int i2 = (list.length - 1) - i;
                Object obj = list[i];
                list[i]    = list[i2];
                list[i2]   = obj;
            }
        }
        return list;
    }
    
    /* reverse order in place */
    public static java.util.List reverseOrder(java.util.List list)
    {
        Collections.reverse(list);
        return list;
    }
    
    // ------------------------------------------------------------------------

    public static class CollectionProxy
        implements Collection
    {
        private Collection delegate = null;
        public CollectionProxy(Collection c) {
            this.delegate = c;
        }
        public boolean add(Object o) {
            return this.delegate.add(o);
        }
        public boolean addAll(Collection c) {
            return this.delegate.addAll(c);
        }
        public void clear() {
            this.delegate.clear();
        }
        public boolean contains(Object o) {
            return this.delegate.contains(o);
        }
        public boolean containsAll(Collection c) {
            return this.delegate.containsAll(c);
        }
        public boolean equals(Object o) {
            if (o instanceof CollectionProxy) {
                return this.delegate.equals(((CollectionProxy)o).delegate);
            } else {
                return false;
            }
        }
        public int hashCode() {
            return this.delegate.hashCode();
        }
        public boolean isEmpty() {
            return this.delegate.isEmpty();
        }
        public Iterator iterator() {
            return this.delegate.iterator();
        }
        public boolean remove(Object o) {
            return this.delegate.remove(o);
        }
        public boolean removeAll(Collection c) {
            return this.delegate.removeAll(c);
        }
        public boolean retainAll(Collection c) {
            return this.delegate.retainAll(c);
        }
        public int size() {
            return this.delegate.size();
        }
        public Object[] toArray() {
            return this.delegate.toArray();
        }
        public Object[] toArray(Object[] a) {
            return this.delegate.toArray(a);
        }
    }

    // ------------------------------------------------------------------------
    
}
