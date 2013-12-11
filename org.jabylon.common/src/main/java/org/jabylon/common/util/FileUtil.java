/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.common.util;

import java.io.File;

public class FileUtil {

    public static void delete(File file)
    {
        if(file.isDirectory())
        {
            File[] files = file.listFiles();
            for (File child : files) {
                delete(child);
            }
        }
        file.delete();
    }


}
