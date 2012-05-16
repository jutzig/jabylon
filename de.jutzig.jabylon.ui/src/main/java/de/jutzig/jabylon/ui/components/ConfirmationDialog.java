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

import de.jutzig.jabylon.ui.styles.JabylonStyle;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class ConfirmationDialog extends Window {

	private Window parent;
	private Runnable proceedAction;
	private Runnable cancelAction;
	private Label statusLabel;

	public ConfirmationDialog(Window parent, String message) {
		this.parent = parent;
		setModal(true);
		addStyleName("opaque");
		setWidth(440, UNITS_PIXELS);
		setHeight(200, UNITS_PIXELS);
		createContents(message);
	}
	
	private void createContents(String message) {
		GridLayout layout = new GridLayout();
		layout.setColumns(2);
		layout.setRows(3);
		layout.setMargin(true);
		layout.setSpacing(true); 
		layout.setSizeFull();
		
		String status = getCaption()==null ? "" : getCaption(); 
		statusLabel = new Label(status);
		statusLabel.addStyleName(JabylonStyle.BIG_WARNING.getCSSName());
		layout.addComponent(statusLabel, 0, 0,1,0);
		
		Label label = new Label(message);
		layout.addComponent(label, 0, 1,1,1);
		
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
	
	@Override
	public void setCaption(String caption) {
		super.setCaption(caption);
		if(statusLabel!=null)
			statusLabel.setValue(caption);
	}
}
