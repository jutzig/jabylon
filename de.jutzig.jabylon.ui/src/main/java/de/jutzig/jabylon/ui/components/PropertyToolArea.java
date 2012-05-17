/**
 * 
 */
package de.jutzig.jabylon.ui.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.ecore.EObject;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.TabSheet.Tab;

import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.Review;
import de.jutzig.jabylon.ui.Activator;
import de.jutzig.jabylon.ui.container.PropertyPairContainer.PropertyPairItem;
import de.jutzig.jabylon.ui.tools.PropertyEditorTool;
import de.jutzig.jabylon.ui.util.PreferencesUtil;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class PropertyToolArea extends CustomComponent implements PropertyEditorTool {

	private List<PropertyEditorTool> tools;
	private Map<Component, String> toolIDMap;
	private TabSheet tabSheet;
	
	public PropertyToolArea() {
		tools = new ArrayList<PropertyEditorTool>();
		createContents();
		setSizeFull();
		
	}

	private void createContents() {
		tabSheet = new TabSheet();
		tabSheet.addListener(new SelectedTabChangeListener() {
			
			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {
				Component selectedTab = event.getTabSheet().getSelectedTab();
				String id = toolIDMap.get(selectedTab);
				if(getApplication()!=null)
				{
					Object user = getApplication().getUser();
					if (user != null) {
						Preferences scope = PreferencesUtil.scopeFor((EObject) user);
						scope.put("selected.property.tool", id);
						try {
							scope.flush();
						} catch (BackingStoreException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
			}
		});
		tabSheet.setSizeFull();
		setCompositionRoot(tabSheet);
		buildItems(tabSheet);
		
	}

	private void buildItems(TabSheet accordion) {
		toolIDMap = new HashMap<Component, String>();

		
		IConfigurationElement[] tools = Activator.getDefault().getPropertyEditorTools();
		for (IConfigurationElement element : tools) {
			String name = element.getAttribute("name");
			try {
				PropertyEditorTool tool = (PropertyEditorTool) element.createExecutableExtension("class");
				String iconString = element.getAttribute("icon");
				String id = element.getAttribute("id");
				Component component = tool.createComponent();
				toolIDMap.put(component, id);
				Tab tab = accordion.addTab(component);
				
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
	public void selectionChanged(PropertyPairItem currentSelection, Collection<Review> reviews) {
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
	public Component createComponent() {
		return this;
	}
	
	
	@Override
	public void attach() {
		super.attach();
		
		Object user = getApplication().getUser();
		if(user!=null)
		{
			Preferences scope = PreferencesUtil.scopeFor((EObject) user);
			String activeID = scope.get("selected.property.tool", null);
			if(activeID!=null && toolIDMap.containsValue(activeID))
			{
				for (Entry<Component, String> entry : toolIDMap.entrySet()) {
					if(entry.getValue().equals(activeID))
						tabSheet.setSelectedTab(entry.getKey());
				}
			}
		}
	}
}
