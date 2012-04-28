/**
 * 
 */
package de.jutzig.jabylon.ui.components;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class ConfirmationDialog extends Window {

	private Window parent;
	private Runnable proceedAction;
	private Runnable cancelAction;

	public ConfirmationDialog(Window parent, String message) {
		this.parent = parent;
		setModal(true);
		setWidth(440, UNITS_PIXELS);
		setHeight(200, UNITS_PIXELS);
		createContents(message);
	}
	
	private void createContents(String message) {
		GridLayout layout = new GridLayout();
		layout.setColumns(2);
		layout.setRows(2);
		layout.setMargin(true);
		layout.setSpacing(true); 
		layout.setSizeFull();
		
		Label label = new Label(message);
		layout.addComponent(label, 0, 0,1,0);
		
		Button cancel = new Button("Cancel");
		cancel.addListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(cancelAction!=null)
					cancelAction.run();
				parent.removeWindow(ConfirmationDialog.this);
				
			}
		});
		layout.addComponent(cancel);
		
		
		Button ok = new Button("Proceed");
		ok.addListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(proceedAction!=null)
					proceedAction.run();
				parent.removeWindow(ConfirmationDialog.this);
				
			}
		});
		layout.addComponent(ok);
		
		setContent(layout);

	}
	
	public void setCancelAction(Runnable r)
	{
		cancelAction = r;
	}
	
	public void setProceedAction(Runnable r)
	{
		proceedAction = r;
	}
}
