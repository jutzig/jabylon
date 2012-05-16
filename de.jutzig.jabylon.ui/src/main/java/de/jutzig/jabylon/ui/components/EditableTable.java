/**
 * 
 */
package de.jutzig.jabylon.ui.components;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import de.jutzig.jabylon.ui.styles.JabylonStyle;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class EditableTable extends HorizontalLayout implements ClickListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8653576988287492011L;
	private Table table;
	private Button addButton;
	private Button removeButton;
	private CheckBox edit;

	public EditableTable() {
		this(false);
		
	}
	
	public EditableTable(boolean editable) {
		HorizontalLayout layout = this;
		layout.setSizeFull();
		layout.setSpacing(true);
		table = new Table();
		table.addStyleName(JabylonStyle.TABLE_STRIPED.getCSSName());
		table.setWidth(100, UNITS_PERCENTAGE);
		table.setHeight(300, UNITS_PIXELS);
		layout.addComponent(table);
		layout.setExpandRatio(table, 1);

		VerticalLayout buttonLayout = new VerticalLayout();
		addButton = new Button("Add");
		addButton.setWidth(100, UNITS_PERCENTAGE);
		addButton.addListener(this);
		buttonLayout.addComponent(addButton);
		removeButton = new Button("Remove");
		removeButton.addListener(this);
		removeButton.setWidth(100, UNITS_PERCENTAGE);
		buttonLayout.addComponent(removeButton);

		buttonLayout.setSpacing(true);

		
		buttonLayout.setWidth(100, UNITS_PIXELS);
		buttonLayout.setHeight(100, UNITS_PERCENTAGE);
		layout.addComponent(buttonLayout);
		layout.setExpandRatio(buttonLayout, 0);
		
		if(editable)
		{
			edit = new CheckBox("Edit");
			edit.addListener(this);
			edit.setImmediate(true);

			buttonLayout.addComponent(edit);
		}
		Label spacer = new Label();
		buttonLayout.addComponent(spacer);
		buttonLayout.setExpandRatio(spacer, 1);
		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton()==addButton)
			addPressed();
		else if(event.getButton()==removeButton)
			removePressed();
		else if(event.getButton()==edit)
			editPressed(edit.booleanValue());
		
	}
	
	
	private void editPressed(boolean selected) {
		table.setEditable(selected);
		
	}

	protected void removePressed() {

		
	}

	protected void addPressed() {

		
	}


	public Table getTable() {
		return table;
	}
	
	public void setEditable(boolean editable)
	{
		table.setEditable(editable);
		edit.setValue(editable);
	}
	
}
