/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket.components;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class BootstrapAjaxTabbedPanel<T extends ITab> extends BootstrapTabbedPanel<T> {

	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param tabs
	 */
	public BootstrapAjaxTabbedPanel(final String id, final List<T> tabs)
	{
		super(id, tabs);
		setOutputMarkupId(true);

		setVersioned(false);
	}

	@Override
	protected WebMarkupContainer newLink(final String linkId, final int index)
	{
		return new AjaxFallbackLink<Void>(linkId)
		{

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(final AjaxRequestTarget target)
			{
				setSelectedTab(index);
				if (target != null)
				{
					target.add(BootstrapAjaxTabbedPanel.this);
				}
				onAjaxUpdate(target);
			}

		};
	}


	protected void onAjaxUpdate(final AjaxRequestTarget target)
	{
	}
	
}
