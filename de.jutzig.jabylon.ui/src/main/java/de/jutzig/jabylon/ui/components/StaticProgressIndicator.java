package de.jutzig.jabylon.ui.components;

import java.text.MessageFormat;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

public class StaticProgressIndicator extends Label {

	private int percentage;
	private static final String PATTERN = "<div class=\"jabylon-progress\">" +
			"<div class=\"jabylon-progress-bar\" style=\"width:{0}%; background-color: {1};\">&nbsp;</div>" +
			"<div class=\"jabylon-progress-label\">{0}%</div>" +
			"</div>";
			
	
	public StaticProgressIndicator() {
		setContentMode(CONTENT_XHTML);
	}
	
	
	
	public void setPercentage(int percentage) {
		this.percentage = percentage;
		int rgb = calculateBackgroundColor(percentage);
		String color = Integer.toHexString(rgb);
		if(color.length()==4) //pure green, no red left
			color = "00"+color;
		setValue(MessageFormat.format(PATTERN, percentage, "#"+color));
		
	}



	private int calculateBackgroundColor(int percentage) {
		
		//TODO: use HSV instead
		int green=(255*percentage)/100;
		int	red =(255*(100-percentage))/100;
		
		int color = 0; 
		color += red << 16;
		color += green << 8;
		return color;

	}
	
}
