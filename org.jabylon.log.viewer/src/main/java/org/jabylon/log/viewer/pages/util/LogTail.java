/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.log.viewer.pages.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Deque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.CountingInputStream;

public class LogTail implements Serializable{

	private static final long serialVersionUID = 5184339368885208428L;
	private String logFile;
	private long currentChunk;
	private static final Logger LOG = LoggerFactory.getLogger(LogTail.class);

	public LogTail(String logFile) {
		this.logFile = logFile;
	}

	public void nextChunk(int maxLines, Deque<String> buffer) {
		BufferedReader reader = null;
		try {
			CountingInputStream in = new CountingInputStream(new FileInputStream(logFile));
			//buffer of 1 is slow, but at least predictable, so we can reset
			reader = new BufferedReader(new InputStreamReader(in),1);
			reader.skip(currentChunk);
			String s = null;
			int lines = 0;
			while ((s = reader.readLine()) != null) {
				buffer.add(s);
				lines++;
				//unless it's the first chunk we stop once we reached max lines
				if(currentChunk>0 && lines==maxLines)
					break;
			}
			currentChunk = in.getCount();
		} catch (FileNotFoundException e) {
			LOG.warn("Logfile does not seem to exist (yet)", e);
		} catch (IOException e) {
			LOG.warn("Failed to read logfile", e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				LOG.error("Failed to close the logfile", e);
			}
		}	}
}
