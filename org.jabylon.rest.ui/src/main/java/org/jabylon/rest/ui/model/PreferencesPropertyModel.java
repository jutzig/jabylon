/**
 *
 */
package org.jabylon.rest.ui.model;

import org.apache.wicket.model.Model;
import org.osgi.service.prefs.Preferences;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class PreferencesPropertyModel extends Model<String> {

    private static final long serialVersionUID = 8851885506195822461L;
    private Preferences prefs;

    public PreferencesPropertyModel(Preferences prefs, String key, String defaultValue) {
        super();
        this.prefs = prefs;
        this.key = key;
        this.defaultValue = defaultValue;
    }

    private String key;
    private String defaultValue;

    @Override
    public String getObject() {
        return prefs.get(key, defaultValue);
    }

    @Override
    public void setObject(String object) {
        prefs.put(key, object);
    }


}
