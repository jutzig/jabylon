/**
 * 
 */
package de.jutzig.jabylon.ui.components;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class EditableTable extends CustomComponent implements ClickListener{

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
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSpacing(true);
		table = new Table();
		layout.addComponent(table);
		VerticalLayout buttonArea = new VerticalLayout();
		
		addButton = new Button("Add");
		addButton.addListener(this);
		buttonArea.addComponent(addButton);
		removeButton = new Button("Remove");
		removeButton.addListener(this);
		buttonArea.addComponent(removeButton);
		buttonArea.setSpacing(true);
		
		layout.addComponent(buttonArea);
		
		
		if(editable)
		{
			edit = new CheckBox("Edit");
			edit.addListener(this);
			edit.setImmediate(true);

			buttonArea.addComponent(edit);
		}
		
		setCompositionRoot(layout);
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
