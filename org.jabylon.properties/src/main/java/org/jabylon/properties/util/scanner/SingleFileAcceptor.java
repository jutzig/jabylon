/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.util.scanner;

import java.io.File;

public class SingleFileAcceptor implements PropertyFileAcceptor {


    private boolean match = false;

    @Override
    public void newMatch(File file) {
        match = true;
    }

    public boolean isMatch() {
        return match;
    }


}
