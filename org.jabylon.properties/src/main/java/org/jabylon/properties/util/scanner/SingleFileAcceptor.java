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
