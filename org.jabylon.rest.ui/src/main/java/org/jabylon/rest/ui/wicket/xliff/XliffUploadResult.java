/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.xliff;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.zip.ZipEntry;

/**
 * Represents the result of a single XLIFF document import/upload.<br>
 * {@link XliffUploadHelper} will produce one {@link XliffUploadResult} per {@link ZipEntry} in the
 * uploaded file.<br>
 * 
 * Used primarily for displaying formatted, localized error, warning and info messages.<br>
 * 
 * TODO: Possibly Create wrapper class that holds a {@link List} of {@link XliffUploadResult}s.
 * representing the whole ZIP file. Currently these POJOs are sorted via {@link #sort(List)}.<br>
 * 
 * @author c.samulski (2016-02-14)
 */
public final class XliffUploadResult {

	private final String key; // error message key
	private final Level level; // UI position

	enum Level {
		ERROR(0), WARNING(1), INFO(2);
		private final int priority;

		private Level(int priority) {
			this.priority = priority;
		}
	}

	/**
	 * Any Parameters associated with this {@link XliffUploadResult}.<br>
	 * Frankly these will be any information used to complete error messages.<br>
	 */
	private final List<String> parameters = new ArrayList<>();

	public XliffUploadResult(String key, Level level, String fileName) {
		parameters.add(fileName);
		this.key = key;
		this.level = level;
	}

	public XliffUploadResult(String key, Level level, String fileName, String parameter) {
		this.key = key;
		this.level = level;
		parameters.add(fileName);
		parameters.add(parameter);
	}

	public XliffUploadResult(String key, Level level, String fileName, Collection<String> parameters) {
		this.key = key;
		this.level = level;
		parameters.add(fileName);
		parameters.addAll(parameters);
	}

	/**
	 * Sort the {@link List} of {@link XliffUploadResult}s according to priority.<br>
	 */
	public static final List<XliffUploadResult> sort(List<XliffUploadResult> toSort) {
		Collections.sort(toSort, new Comparator<XliffUploadResult>() {
			@Override
			public int compare(XliffUploadResult one, XliffUploadResult two) {
				return one.getPriority() - two.getPriority();
			}
		});
		return toSort;
	}

	public final Object[] getParameters() {
		return parameters.toArray();
	}

	public final String getResultKey() {
		return key;
	}

	public final String getKey() {
		return key;
	}

	public final int getPriority() {
		return level.priority;
	}

	public final Level getLevel() {
		return level;
	}
}
