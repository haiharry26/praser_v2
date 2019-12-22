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
//  This class provides an ordered Set
// ----------------------------------------------------------------------------
// Change History:
//  2006/03/26  Martin D. Flynn
//      Initial release
//  2006/06/30  Martin D. Flynn
//      Repackaged to "org.opengts.util"
// ----------------------------------------------------------------------------
package com.digitrinity.parser.dateutil;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrderedSet implements Set, java.util.List, Cloneable
{
    
    // ------------------------------------------------------------------------

    protected static final int ENTRY_ADDED   = 1;
    protected static final int ENTRY_REMOVED = 2;
    private static Logger logger = LogManager.getLogger(OrderedSet.class.getName());
    
    public static interface ChangeListener
    {
        public void entryAdded(OrderedSet set, Object obj);
        public void entryRemoved(OrderedSet set, Object obj);
    }
    
    public static class ChangeListenerAdapter
        implements ChangeListener
    {
        public void entryAdded(OrderedSet set, Object obj) {   
        }
        public void entryRemoved(OrderedSet set, Object obj) {
        }
    }
    
    // ------------------------------------------------------------------------
    
    private java.util.List  elements = null;
    private boolean         retainOriginalValue = false;
    private java.util.List  changeListeners = null;
    @SuppressWarnings("unused")
	private int             addChangeCount = 0;
    @SuppressWarnings("unused")
	private int             removeChangeCount = 0;

    public OrderedSet()
    {
        super();
    }

    public OrderedSet(boolean retainOriginalValue)
    {
        this.setRetainOriginalValue(retainOriginalValue);
    }

    public OrderedSet(Collection c, boolean retainOriginalValue)
    {
        this(retainOriginalValue);
        this.addAll(c);
    }

    public OrderedSet(Collection c)
    {
        this(c, false);
    }

    public OrderedSet(Object a[], boolean retainOriginalValue)
    {
        this(retainOriginalValue);
        this.addAll(a);
    }

    public OrderedSet(Object a[])
    {
        this(a, false);
    }

    public OrderedSet(OrderedSet os)
    {
        super();
        this.setRetainOriginalValue(os.getRetainOriginalValue());
        this.getBackingList().addAll(os.getBackingList());
    }
    
    // ------------------------------------------------------------------------
    
    public Object clone()
    {
        return new OrderedSet(this);
    }
    
    // ------------------------------------------------------------------------

    protected java.util.List getChangeListeners(boolean create)
    {
        if ((this.changeListeners == null) && create) { this.changeListeners = new Vector(); }
        return this.changeListeners;
    }
    
    protected boolean hasChangeListeners()
    {
        return (this.getChangeListeners(false) != null);
    }
    
    public void addChangeListener(ChangeListener cl)
    {
        if (cl != null) {
            java.util.List listeners = this.getChangeListeners(true);
            if (!listeners.contains(cl)) {               
                listeners.add(cl);
            }
        }
    }
    
    public void removeChangeListener(ChangeListener cl)
    {
        if (cl != null) {
            java.util.List listeners = this.getChangeListeners(false);
            if (listeners != null) {               
                listeners.remove(cl);
            }
        }
    }
    
    protected void notifyChangeListeners(int action, Object obj)
    {
        java.util.List listeners = this.getChangeListeners(false);
        if (listeners != null) {
            for (Iterator i = listeners.iterator(); i.hasNext();) {
                ChangeListener cl = (ChangeListener)i.next();
                if (action == ENTRY_ADDED) {
                    cl.entryAdded(this, obj);
                    addChangeCount++;
                } else
                if (action == ENTRY_REMOVED) {
                    cl.entryRemoved(this, obj);
                    removeChangeCount++;
                } else {
                    logger.error("Unrecognized action: " + action);
                }
            }
        }
    }
    
    // ------------------------------------------------------------------------
    
    protected java.util.List getBackingList()
    {
        if (this.elements == null) { this.elements = new Vector(); }
        return this.elements;
    }
    
    public java.util.List getList()
    {
        return this.getBackingList();
    }
    
    // ------------------------------------------------------------------------

    public boolean getRetainOriginalValue()
    {
        return this.retainOriginalValue;
    }
    
    public void setRetainOriginalValue(boolean state)
    {
        this.retainOriginalValue = state;
    }
    
    // ------------------------------------------------------------------------
    
    public Object get(int ndx)
    {
        // java.util.List (mandatory)
        // allowed, since this is an ordered set (backed by a List)
        return this.getBackingList().get(ndx);
    }
    
    public Object set(int ndx, Object obj)
    {
        // java.util.List (optional)
        throw new UnsupportedOperationException();
    }
    
    // ------------------------------------------------------------------------
    
    protected void _add(int ndx, Object obj)
    {
        if ((ndx < 0) || (ndx >= this.getBackingList().size())) {
            this.getBackingList().add(obj); // add to end
        } else {
            this.getBackingList().add(ndx, obj); // insert at index
        }
        this.notifyChangeListeners(ENTRY_ADDED, obj);
    }
    
    public boolean add(Object obj)
    {
        if (this.getRetainOriginalValue()) {
            boolean contained = this.contains(obj);
            if (!contained) { // retain original
                this._add(-1, obj);
            }
            return !contained;
        } else {
            boolean contained = this.remove(obj);   // remove original
            this._add(-1, obj);
            return !contained;
        }
    }
    
    public boolean addAll(Collection c)
    {
        if ((c != null) && (c.size() > 0)) {
            for (Iterator i = c.iterator(); i.hasNext();) {
                this.add(i.next());
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean addAll(Object a[])
    {
        if ((a != null) && (a.length > 0)) {
            for (int i = 0; i < a.length; i++) {
                this.add(a[i]);
            }
            return true;
        } else {
            return false;
        }
    }
    
    public void add(int ndx, Object obj) 
    {
        if (this.getRetainOriginalValue()) {
            boolean contained = this.contains(obj);
            if (!contained) { // retain original
                this._add(ndx, obj);
            }
            //return !contained;
        } else {
            this._add(ndx, obj);
            //return !contained;
        }
    }
    
    public boolean addAll(int ndx, Collection c) 
    {
        // java.util.List (optional)
        throw new UnsupportedOperationException();
    }
    
    // ------------------------------------------------------------------------

    public boolean contains(Object obj)
    {
        return this.getBackingList().contains(obj);
    }

    public boolean containsAll(Collection c)
    {
        return this.getBackingList().containsAll(c);
    }
    
    // ------------------------------------------------------------------------

    public boolean equals(Object other)
    {
        if (other instanceof OrderedSet) {
            OrderedSet os = (OrderedSet)other;
            java.util.List L1 = this.getBackingList();
            java.util.List L2 = os.getBackingList();
            //boolean eq = L1.containsAll(L2) && L2.containsAll(L1);
            boolean eq = L1.equals(L2); // same elements, same order
            return eq;
        } else {
            return false;
        }
    }
    
    // ------------------------------------------------------------------------

    public int hashCode()
    {
        return this.getBackingList().hashCode();
    }
    
    // ------------------------------------------------------------------------

    protected boolean _remove(Object obj)
    {
        if (this.getBackingList().remove(obj)) {
            this.notifyChangeListeners(ENTRY_REMOVED, obj);
            return true;
        } else {
            return false;
        }
    }

    protected boolean _remove(Object obj, Iterator i)
    {
        i.remove();
        this.notifyChangeListeners(ENTRY_REMOVED, obj);
        return true;
    }
    
    public Object remove(int ndx)
    {
        // java.util.List (optional)
        throw new UnsupportedOperationException();
    }
    
    public boolean remove(Object obj)
    {
        return this._remove(obj);
    }
    
    public boolean removeAll(Collection c)
    {
        if (!this.hasChangeListeners()) {
            return this.getBackingList().removeAll(c);
        } else
        if (c == this) {
            if (this.size() > 0) {
                this.clear();
                return true;
            } else {
                return false;
            }
        } else
        if ((c != null) && (c.size() > 0)) {
            boolean changed = false;
            for (Iterator i = c.iterator(); i.hasNext();) {
                if (this.remove(i.next())) { changed = true; }
            }
            return changed;
        } else {
            return false;
        }
    }
    
    public boolean retainAll(Collection c)
    {
        if (!this.hasChangeListeners()) {
            return this.getBackingList().retainAll(c);
        } else
        if (c == this) {
            return false;
        } else
        if ((c != null) && (c.size() > 0)) {
            boolean changed = false;
            for (Iterator i = this.getBackingList().iterator(); i.hasNext();) {
                Object obj = i.next();
                if (!c.contains(obj)) {
                    this._remove(obj, i);
                    changed = true;
                }
            }
            return changed;
        } else {
            return false;
        }
    }

    public void clear()
    {
        if (!this.hasChangeListeners()) {
            this.getBackingList().clear();
        } else {
            for (Iterator i = this.getBackingList().iterator(); i.hasNext();) {
                Object obj = i.next();
                this._remove(obj, i);
            }
        }
    }
    
    // ------------------------------------------------------------------------

    public int size()
    {
        return this.getBackingList().size();
    }
    
    public boolean isEmpty()
    {
        return (this.size() == 0);
    }
   
    // ------------------------------------------------------------------------
    
    public int indexOf(Object obj)
    {
        // java.util.List
        return this.getBackingList().indexOf(obj);
    }
    
    public int lastIndexOf(Object obj)
    {
        // java.util.List
        return this.getBackingList().lastIndexOf(obj);
    }
   
    // ------------------------------------------------------------------------

    public Iterator iterator()
    {
        if (!this.hasChangeListeners()) {
            // OK, since only 'remove' is allowed
            return this.getBackingList().iterator();
        } else {
            return new Iterator() {
                private Object thisObject = null;
                private Iterator i = OrderedSet.this.getBackingList().iterator();
                public boolean hasNext() {
                    return i.hasNext();
                }
                public Object next() {
                    this.thisObject = i.next();
                    return this.thisObject;
                }
                public void remove() {
                    OrderedSet.this._remove(this.thisObject, i);
                    this.thisObject = null;
                }
            };
        }
    }
    
    public ListIterator listIterator()
    {
        // java.util.List (mandatory)
        return this.listIterator(-1);
    }
    
    public ListIterator listIterator(final int ndx)
    {
        if (!this.hasChangeListeners()) {
            // OK, since only 'remove' is allowed
            return (ndx >= 0)?
                this.getBackingList().listIterator(ndx) :
                this.getBackingList().listIterator();
        } else {
            return new ListIterator() {
                private Object thisObject = null;
                private ListIterator i = (ndx >= 0)?
                    OrderedSet.this.getBackingList().listIterator(ndx) :
                    OrderedSet.this.getBackingList().listIterator();
                public boolean hasNext() {
                    return i.hasNext();
                }
                public boolean hasPrevious() {
                    return i.hasPrevious();
                }
                public Object next() {
                    this.thisObject = i.next();
                    return this.thisObject;
                }
                public int nextIndex() {
                    return i.nextIndex();
                }
                public Object previous() {
                    this.thisObject = i.previous();
                    return this.thisObject;
                }
                public int previousIndex() {
                    return i.previousIndex();
                }
                public void remove() {
                    OrderedSet.this._remove(this.thisObject, i);
                    this.thisObject = null;
                }
                public void add(Object obj) {
                    throw new UnsupportedOperationException();
                }
                public void set(Object obj) {
                    throw new UnsupportedOperationException();
                }
            };
        }
    }
   
    // ------------------------------------------------------------------------
    
    public List subList(int fromIndex, int toIndex)
    {
        // java.util.List (mandatory?)
        // not currently worth the effort to implement this
        throw new UnsupportedOperationException();
    }
    
    // ------------------------------------------------------------------------

    public Object[] toArray()
    {
        return this.getBackingList().toArray();
    }

    public Object[] toArray(Object a[])
    {
        return this.getBackingList().toArray(a);
    }
    
    // ------------------------------------------------------------------------

    public void printContents()
    {
        int n = 0;
        for (Iterator i = this.iterator(); i.hasNext();) {
            logger.debug("" + (n++) + "] " + i.next());
        }
    }

//	@Override
//	public Spliterator spliterator() {
//		// TODO Auto-generated method stub
//		return List.super.spliterator();
//	}
//    
    // ------------------------------------------------------------------------
    
}
