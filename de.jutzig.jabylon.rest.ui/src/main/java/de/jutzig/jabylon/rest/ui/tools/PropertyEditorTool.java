package de.jutzig.jabylon.rest.ui.tools;

import java.io.Serializable;

import de.jutzig.jabylon.rest.ui.model.PropertyPair;
import de.jutzig.jabylon.rest.ui.wicket.PanelFactory;

public interface PropertyEditorTool extends PanelFactory<PropertyPair>, Serializable{


    String getName();

    int getPrecedence();

}
