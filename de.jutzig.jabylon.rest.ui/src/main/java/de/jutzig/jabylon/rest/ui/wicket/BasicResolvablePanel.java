package de.jutzig.jabylon.rest.ui.wicket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.rest.ui.model.EObjectModel;

public class BasicResolvablePanel<T extends Resolvable<?, ?>> extends BasicPanel<T> {

	public BasicResolvablePanel(String id, T object, PageParameters parameters) {
		super(id,new EObjectModel<T>(object),parameters);
	}
	
}
