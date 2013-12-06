package org.jabylon.rest.ui.tools;

import java.io.Serializable;

import org.jabylon.rest.ui.model.PropertyPair;
import org.jabylon.rest.ui.wicket.PanelFactory;

public interface PropertyEditorTool extends PanelFactory<PropertyPair>, Serializable{


    String getName();

    int getPrecedence();

}
