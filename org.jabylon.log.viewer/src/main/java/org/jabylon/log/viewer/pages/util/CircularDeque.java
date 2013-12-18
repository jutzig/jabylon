/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.log.viewer.pages.util;

import java.util.ArrayDeque;

/**
 * CircularDeque behaves like an ArrayDeque but never grows. Instead adding new entries
 * that would exceed the specified size will remove older entries.
 * Vise-versa, when adding entries at the beginning, entries from the end will be removed
 * @author jutzig.dev@googlemail.com
 *
 * @param <E>
 */
public class CircularDeque<E> extends ArrayDeque<E> {

	private static final long serialVersionUID = 5295747708916756005L;
	private int limit;
	
	public CircularDeque(int size) {
		super();
		this.limit = size;
	}

	@Override
	public void addFirst(E e) {
		if(size()==limit)
			pollLast();
		super.addFirst(e);
	}
	
	@Override
	public void addLast(E e) {
		if(size()==limit)
			pollFirst();
		super.addLast(e);
	}	
	
}

