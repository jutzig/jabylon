/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.components;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.form.IFormModelUpdateListener;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;

public abstract class ListEditor<T> extends RepeatingView
                             implements IFormModelUpdateListener
{

    private static final long serialVersionUID = 1L;
    private List<T> items;

    public ListEditor(String id, IModel<List<T>> model)
    {
        super(id, model);
    }

    protected abstract void onPopulateItem(ListItem<T> item);

    public void addItem(T value)
    {
        items.add(value);
        ListItem<T> item = new ListItem<T>(newChildId(),
                                              items.size() - 1);
        add(item);
        onPopulateItem(item);
    }

    protected void onBeforeRender()
    {
        if (!hasBeenRendered())
        {
            @SuppressWarnings("unchecked")
            List<T> list = (List<T>) getDefaultModelObject();
            items = new ArrayList<T>(list);
            for (int i = 0; i < items.size(); i++)
            {
                ListItem<T> li = new ListItem<T>(newChildId(), i);
                add(li);
                onPopulateItem(li);
            }
        }
        super.onBeforeRender();
    }

    public void updateModel()
    {
        setDefaultModelObject(items);
    }
}
