package org.jabylon.properties.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.emf.common.util.EList;

public class SynchronizedEList<E> implements EList<E> {

    final EList<E> list;
    final Object mutex; // Object on which to synchronize

    public SynchronizedEList(EList<E> c) {
        this(c, new Object());
    }

    SynchronizedEList(EList<E> c, Object mutex) {
        super();
        this.list = c;
        this.mutex = mutex;
    }

    @Override
    public int size() {
        synchronized (mutex) {
            return list.size();
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (mutex) {
            return list.isEmpty();
        }
    }

    @Override
    public boolean contains(Object o) {
        synchronized (mutex) {
            return list.contains(o);
        }
    }

    @Override
    public Iterator<E> iterator() {
        synchronized (mutex) {
            return list.iterator();
        }
    }

    @Override
    public Object[] toArray() {
        synchronized (mutex) {
            return list.toArray();
        }
    }

    @Override
    public <T> T[] toArray(T[] a) {
        synchronized (mutex) {
            return list.toArray(a);
        }
    }

    @Override
    public boolean add(E e) {
        synchronized (mutex) {
            return list.add(e);
        }
    }

    @Override
    public boolean remove(Object o) {
        synchronized (mutex) {
            return list.remove(o);
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        synchronized (mutex) {
            return this.list.containsAll(c);
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        synchronized (mutex) {
            return list.addAll(c);
        }
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        synchronized (mutex) {
            return list.addAll(index, c);
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        synchronized (mutex) {
            return list.removeAll(c);
        }
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        synchronized (mutex) {
            return list.retainAll(c);
        }
    }

    @Override
    public void clear() {
        synchronized (mutex) {
            list.clear();
        }

    }

    @Override
    public E get(int index) {
        synchronized (mutex) {
            return list.get(index);
        }
    }

    @Override
    public E set(int index, E element) {
        synchronized (mutex) {
            return list.set(index, element);
        }
    }

    @Override
    public void add(int index, E element) {
        synchronized (mutex) {
            list.add(index, element);
        }
    }

    @Override
    public E remove(int index) {
        synchronized (mutex) {
            return list.remove(index);
        }
    }

    @Override
    public int indexOf(Object o) {
        synchronized (mutex) {
            return list.indexOf(o);
        }
    }

    @Override
    public int lastIndexOf(Object o) {
        synchronized (mutex) {
            return list.lastIndexOf(o);
        }
    }

    @Override
    public ListIterator<E> listIterator() {
        synchronized (mutex) {
            return list.listIterator();
        }
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        synchronized (mutex) {
            return list.listIterator(index);
        }
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        synchronized (mutex) {
            return list.subList(fromIndex, toIndex);
        }
    }

    @Override
    public void move(int arg0, E arg1) {
        synchronized (mutex) {
            list.move(arg0, arg1);
        }
    }

    @Override
    public E move(int arg0, int arg1) {
        synchronized (mutex) {
            return list.move(arg0, arg1);
        }
    }

}
