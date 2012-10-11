package de.jutzig.jabylon.rest.ui.model;

import org.apache.wicket.model.IModel;

public interface AttachableModel<T> extends IModel<T> {
	void attach();
}
