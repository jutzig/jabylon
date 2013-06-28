/**
 *
 */
package de.jutzig.jabylon.ui.memento;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public interface Memento {

    void put(String key, String value);

    String get(String key);

    Memento getChild(String path);

}
