package de.jutzig.jabylon.rest.ui.util;

import java.util.ArrayDeque;
import java.util.Deque;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.jutzig.jabylon.properties.Resolvable;

public class WicketUtil {
	
	public static PageParameters buildPageParametersFor(Resolvable<?, ?> r)
	{
		PageParameters params = new PageParameters();
		Deque<String> segments = new ArrayDeque<String>();
		Resolvable<?, ?> part = r;
		while(part!=null)
		{
			String name = part.getName();
			if(name!=null)
				segments.push(name);
			part = part.getParent(); 
		}
		int count = 0;
		for (String string : segments) {
			params.set(count++, string);
		}
		return params;
	}
}
