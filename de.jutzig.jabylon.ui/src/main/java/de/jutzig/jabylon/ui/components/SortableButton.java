/**
 *
 */
package de.jutzig.jabylon.ui.components;

import com.vaadin.ui.Button;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class SortableButton extends Button implements Comparable<SortableButton>{

    public SortableButton() {
        super();
    }

    public SortableButton(String caption, ClickListener listener) {
        super(caption, listener);
    }

    public SortableButton(String caption, Object target, String methodName) {
        super(caption, target, methodName);
    }

    public SortableButton(String caption) {
        super(caption);
    }

    @Override
    public int compareTo(SortableButton o) {
        if(getCaption()==null)
            return -1;
        return getCaption().compareTo(o.getCaption());
    }

}
