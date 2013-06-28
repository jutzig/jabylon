package de.jutzig.jabylon.ui.config;

import org.osgi.service.prefs.Preferences;

import com.vaadin.ui.Component;

public interface ConfigSection {

    Component createContents();

    void init(Object input, Preferences config);

    void apply(Preferences config);

    void commit(Preferences config);

}
