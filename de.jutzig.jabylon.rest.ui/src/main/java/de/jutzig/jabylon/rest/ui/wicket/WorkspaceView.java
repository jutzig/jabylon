/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
public class WorkspaceView extends BasicPage {
	public WorkspaceView() {
		// add(new Label("message", "Hello World!"));
		List<String> projects = new ArrayList<String>();
		projects.add("Jabylon");
		projects.add("Jenkins");
		projects.add("JBoss AS");
		final DataView<String> dataView = new DataView<String>("projects", new ListDataProvider<String>(projects)) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 6913939295181516945L;

			@Override
			protected void populateItem(Item<String> item) {
				final String project = (String) item.getModelObject();
				item.add(new Label("id", project));
				Label label = new Label("progress", "");
				label.add(new AttributeModifier("style", "width: "+new Random().nextInt(100)));
				item.add(label);
//				style="width: '+project.percentComplete+'%;">
			}
		};

//		dataView.setItemsPerPage(10);

		add(dataView);
	}
}
