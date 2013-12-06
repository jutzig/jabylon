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
