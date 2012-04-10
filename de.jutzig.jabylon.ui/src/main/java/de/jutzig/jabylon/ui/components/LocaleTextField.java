package de.jutzig.jabylon.ui.components;

import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.TextField;

public class LocaleTextField extends TextField implements TextChangeListener{
	public LocaleTextField() {
		addListener((TextChangeListener)this);
	}
	
	@Override
	public void textChange(TextChangeEvent event){
		
	}
}
