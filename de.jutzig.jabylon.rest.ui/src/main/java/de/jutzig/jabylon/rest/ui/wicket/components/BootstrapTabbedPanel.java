package de.jutzig.jabylon.rest.ui.wicket.components;



import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.Loop;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Args;


/**
 * copy of org.apache.wicket.extensions.markup.html.tabs.TabbedPanel to enable bootstrap tabs
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * @see TabbedPanel
 * @param <T>
 */
public class BootstrapTabbedPanel<T extends ITab> extends Panel
{
	private static final long serialVersionUID = 1L;

	/** id used for child panels */
	public static final String TAB_PANEL_ID = "panel";

	private final List<T> tabs;

	/** the current tab */
	private int currentTab = -1;

	private transient Boolean[] tabsVisibilityCache;

	/**
	 * Constructor
	 * 
	 * @param id
	 *            component id
	 * @param tabs
	 *            list of ITab objects used to represent tabs
	 */
	public BootstrapTabbedPanel(final String id, final List<T> tabs)
	{
		this(id, tabs, null);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            component id
	 * @param tabs
	 *            list of ITab objects used to represent tabs
	 * @param model
	 *            model holding the index of the selected tab
	 */
	public BootstrapTabbedPanel(final String id, final List<T> tabs, IModel<Integer> model)
	{
		super(id, model);

		this.tabs = Args.notNull(tabs, "tabs");

		final IModel<Integer> tabCount = new AbstractReadOnlyModel<Integer>()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public Integer getObject()
			{
				return BootstrapTabbedPanel.this.tabs.size();
			}
		};

		WebMarkupContainer tabsContainer = newTabsContainer("tabs-container");
		add(tabsContainer);

		// add the loop used to generate tab names
		tabsContainer.add(new Loop("tabs", tabCount)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final LoopItem item)
			{
				final int index = item.getIndex();
				final T tab = BootstrapTabbedPanel.this.tabs.get(index);

				final WebMarkupContainer titleLink = newLink("link", index);

				titleLink.add(newTitle("title", tab.getTitle(), index));
				item.add(titleLink);
			}

			@Override
			protected LoopItem newItem(final int iteration)
			{
				return newTabContainer(iteration);
			}
		});

		add(newPanel());
	}

	/**
	 * Initialize the component's model.
	 * 
	 * @return a new model containing {@code -1} if the super implementation doesn't supply one
	 */
	@Override
	protected IModel<?> initModel()
	{
		IModel<?> model = super.initModel();
		if (model == null)
		{
			model = new Model<Integer>(-1);
		}

		return model;
	}

	/**
	 * Generates the container for all tabs. The default container automatically adds the css
	 * <code>class</code> attribute based on the return value of {@link #getTabContainerCssClass()}
	 * 
	 * @param id
	 *            container id
	 * @return container
	 */
	protected WebMarkupContainer newTabsContainer(final String id)
	{
		return new WebMarkupContainer(id)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onComponentTag(final ComponentTag tag)
			{
				super.onComponentTag(tag);
				tag.put("class", getTabContainerCssClass());
			}
		};
	}

	/**
	 * Generates a loop item used to represent a specific tab's <code>li</code> element.
	 * 
	 * @param tabIndex
	 * @return new loop item
	 */
	protected LoopItem newTabContainer(final int tabIndex)
	{
		return new LoopItem(tabIndex)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onComponentTag(final ComponentTag tag)
			{
				super.onComponentTag(tag);
				String cssClass = tag.getAttribute("class");
				if (cssClass == null)
				{
					cssClass = " ";
				}
				cssClass += " tab" + getIndex();

				if (getIndex() == getSelectedTab())
				{
					cssClass += ' ' + getSelectedTabCssClass();
				}
				if (getIndex() == getTabs().size() - 1)
				{
					cssClass += ' ' + getLastTabCssClass();
				}
				tag.put("class", cssClass.trim());
			}

			@Override
			public boolean isVisible()
			{
				return getTabs().get(tabIndex).isVisible();
			}
		};
	}

	@Override
	protected void onBeforeRender()
	{
		int index = getSelectedTab();

		if ((index == -1) || (isTabVisible(index) == false))
		{
			// find first visible tab
			index = -1;
			for (int i = 0; i < tabs.size(); i++)
			{
				if (isTabVisible(i))
				{
					index = i;
					break;
				}
			}

			if (index != -1)
			{
				/*
				 * found a visible tab, so select it
				 */
				setSelectedTab(index);
			}
		}

		setCurrentTab(index);

		super.onBeforeRender();
	}

	/**
	 * @return the value of css class attribute that will be added to last tab. The
	 *         default value is <code>last</code>
	 */
	protected String getLastTabCssClass()
	{
		return "last";
	}

	/**
	 * @return list of tabs that can be used by the user to add/remove/reorder tabs in the panel
	 */
	public final List<T> getTabs()
	{
		return tabs;
	}

	/**
	 * Factory method for tab titles. Returned component can be anything that can attach to span
	 * tags such as a fragment, panel, or a label
	 * 
	 * @param titleId
	 *            id of title component
	 * @param titleModel
	 *            model containing tab title
	 * @param index
	 *            index of tab
	 * @return title component
	 */
	protected Component newTitle(final String titleId, final IModel<?> titleModel, final int index)
	{
		return new Label(titleId, titleModel);
	}

	/**
	 * Factory method for links used to switch between tabs.
	 * 
	 * The created component is attached to the following markup. Label component with id: title
	 * will be added for you by the tabbed panel.
	 * 
	 * <pre>
	 * &lt;a href=&quot;#&quot; wicket:id=&quot;link&quot;&gt;&lt;span wicket:id=&quot;title&quot;&gt;[[tab title]]&lt;/span&gt;&lt;/a&gt;
	 * </pre>
	 * 
	 * Example implementation:
	 * 
	 * <pre>
	 * protected WebMarkupContainer newLink(String linkId, final int index)
	 * {
	 * 	return new Link(linkId)
	 * 	{
	 * 		private static final long serialVersionUID = 1L;
	 * 
	 * 		public void onClick()
	 * 		{
	 * 			setSelectedTab(index);
	 * 		}
	 * 	};
	 * }
	 * </pre>
	 * 
	 * @param linkId
	 *            component id with which the link should be created
	 * @param index
	 *            index of the tab that should be activated when this link is clicked. See
	 *            {@link #setSelectedTab(int)}.
	 * @return created link component
	 */
	protected WebMarkupContainer newLink(final String linkId, final int index)
	{
		return new Link<Void>(linkId)
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
				setSelectedTab(index);
			}
		};
	}

	/**
	 * sets the selected tab
	 * 
	 * @param index
	 *            index of the tab to select
	 * @return this for chaining
	 * @throws IndexOutOfBoundsException
	 *             if index is not {@code -1} or in the range of available tabs
	 */
	public BootstrapTabbedPanel<T> setSelectedTab(final int index)
	{
		if ((index < 0) || (index >= tabs.size()))
		{
			throw new IndexOutOfBoundsException();
		}

		setDefaultModelObject(index);

		// force the tab's component to be aquired again if already the current tab
		currentTab = -1;
		setCurrentTab(index);

		return this;
	}

	private void setCurrentTab(int index)
	{
		if (this.currentTab == index)
		{
			// already current
			return;
		}
		this.currentTab = index;

		final Component component;

		if (currentTab == -1 || (tabs.size() == 0) || !isTabVisible(currentTab))
		{
			// no tabs or the current tab is not visible
			component = newPanel();
		}
		else
		{
			// show panel from selected tab
			T tab = tabs.get(currentTab);
			component = tab.getPanel(TAB_PANEL_ID);
			if (component == null)
			{
				throw new WicketRuntimeException("ITab.getPanel() returned null. TabbedPanel [" +
					getPath() + "] ITab index [" + currentTab + "]");
			}
		}

		if (!component.getId().equals(TAB_PANEL_ID))
		{
			throw new WicketRuntimeException(
				"ITab.getPanel() returned a panel with invalid id [" +
					component.getId() +
					"]. You must always return a panel with id equal to the provided panelId parameter. TabbedPanel [" +
					getPath() + "] ITab index [" + currentTab + "]");
		}

		addOrReplace(component);
	}

	private WebMarkupContainer newPanel()
	{
		return new WebMarkupContainer(TAB_PANEL_ID);
	}

	/**
	 * @return index of the selected tab
	 */
	public final int getSelectedTab()
	{
		return (Integer)getDefaultModelObject();
	}

	/**
	 * 
	 * @param tabIndex
	 * @return visible
	 */
	private boolean isTabVisible(final int tabIndex)
	{
		if (tabsVisibilityCache == null)
		{
			tabsVisibilityCache = new Boolean[tabs.size()];
		}

		if (tabsVisibilityCache.length < tabIndex + 1)
		{
			Boolean[] resized = new Boolean[tabIndex + 1];
			System.arraycopy(tabsVisibilityCache, 0, resized, 0, tabsVisibilityCache.length);
			tabsVisibilityCache = resized;
		}

		if (tabsVisibilityCache.length > 0)
		{
			Boolean visible = tabsVisibilityCache[tabIndex];
			if (visible == null)
			{
				visible = tabs.get(tabIndex).isVisible();
				tabsVisibilityCache[tabIndex] = visible;
			}
			return visible;
		}
		else
		{
			return false;
		}
	}

	@Override
	protected void onDetach()
	{
		tabsVisibilityCache = null;
		super.onDetach();
	}
	

	protected String getSelectedTabCssClass() {
		return "active";
	}
	
	protected String getTabContainerCssClass() {
		return "tabbable";
	}
	
}
