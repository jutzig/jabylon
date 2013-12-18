/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.log.viewer.pages.util;

import java.io.File;

public class LogFile {

	private String location;
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public long getLastModified() {
		return new File(getLocation()).lastModified();
	}
	
	public long getSize() {
		return new File(getLocation()).length();
	}
	
	public String getName() {
		if(getLocation()==null)
			return null;
		return new File(getLocation()).getName();
	}
}
