/**
 *
 */
package de.jutzig.jabylon.ui.tools;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public interface SuggestionAcceptor {

    void append(String suggestion);

    void replace(String suggestion);

}
