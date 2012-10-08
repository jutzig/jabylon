package de.jutzig.jabylon.rest.ui.wicket.components;

import java.util.Random;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;

public class ProgressPanel extends Panel {
	
	public ProgressPanel(String id) {
		super(id, new RandomModel());
		add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(1)));
		WebComponent bar = new WebComponent("bar");
		bar.add(new AttributeModifier("style", new RandomModel()));
		add(bar);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2573454585436627297L;

	static class RandomModel extends Model<String>
	{
		@Override
		public String getObject() {
			return "width: "+new Random().nextInt(100) +"%;";
		}
	}
}
