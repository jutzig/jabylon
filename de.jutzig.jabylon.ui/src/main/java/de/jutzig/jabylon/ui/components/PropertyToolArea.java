/**
 * 
 */
package de.jutzig.jabylon.ui.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TabSheet.Tab;

import de.jutzig.jabylon.properties.Property;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.review.Review;
import de.jutzig.jabylon.ui.Activator;
import de.jutzig.jabylon.ui.container.PropertyPairContainer;
import de.jutzig.jabylon.ui.tools.PropertyEditorTool;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class PropertyToolArea extends CustomComponent implements PropertyEditorTool {

	private List<PropertyEditorTool> tools;
	
	public PropertyToolArea() {
		tools = new ArrayList<PropertyEditorTool>();
		createContents();
		
	}

	private void createContents() {
		Accordion accordion = new Accordion();
		accordion.setSizeFull();
		setCompositionRoot(accordion);
		buildItems(accordion);
		
	}

	private void buildItems(Accordion accordion) {
		IConfigurationElement[] tools = Activator.getDefault().getPropertyEditorTools();
		for (IConfigurationElement element : tools) {
			String name = element.getAttribute("name");
			try {
				PropertyEditorTool tool = (PropertyEditorTool) element.createExecutableExtension("class");
				String iconString = element.getAttribute("icon");
				Tab tab = accordion.addTab(tool.createComponent());
				tab.setCaption(name);
				this.tools.add(tool);
				if(iconString!=null && iconString.length()>0)
				{
					tab.setIcon(new ThemeResource(iconString));
				}
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
	}

	@Override
	public void selectionChanged(PropertyPairContainer currentSelection, Collection<Review> reviews) {
		for (PropertyEditorTool tool : tools) {
			tool.selectionChanged(currentSelection, reviews);
		}
		
	}

	@Override
	public void init(PropertyFileDescriptor template, PropertyFileDescriptor translation) {
		for (PropertyEditorTool tool : tools) {
			tool.init(template, translation);
		}
		
	}

	@Override
	public void textSelection(String text, Property owner, EStructuralFeature feature) {
		for (PropertyEditorTool tool : tools) {
			tool.textSelection(text, owner, feature);
		}
		
	}

	@Override
	public Component createComponent() {
		return this;
	}
	
}
