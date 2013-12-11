/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.util;

import java.text.MessageFormat;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Locale;

import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.request.resource.UrlResourceReference;

import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.PropertiesPackage;
import org.jabylon.properties.Resolvable;

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

    /**
     *
     * @return the web context or "" if mounted as /
     */
    public static String getContextPath()
    {
        String path = RequestCycle.get().getRequest().getFilterPath();
        if(path==null || path.isEmpty())
            return "";
        return path;
    }

    public static ResourceReference getIconForLocale(Locale locale)
    {
        if(locale==null)
            return null;
        String iconName = "";
        if(locale.getCountry()!=null && locale.getCountry().length()>0)
        {
            iconName = locale.getCountry().toLowerCase();
        }
        else
        {
            iconName = derriveCountry(locale);
        }

        String url = "img/flags/gif/{0}.gif";
        url = MessageFormat.format(url ,iconName);
        UrlResourceReference ref = new WebContextUrlResourceReference(url);
        return ref;
    }

    public static String derriveCountry(Locale locale) {
        String language = locale.getLanguage();
        if("da".equals(language)) //denmark
            return "dk";

        else if("ja".equals(language)) //japanese
            return "jp";
        else if("uk".equals(language)) //ukraine
            return "ua";
        else if("eu".equals(language)) //basque
            return null; //don't have this one yet
        else if("he".equals(language)) //hebrew
            return "il"; //isreal
        else if("iw".equals(language)) //old hebrew code, still used in java
            return "il"; //isreal
        else if("el".equals(language)) //greek
            return "gr";
        else if("ko".equals(language)) //korean
            return "kr";
        else if("te".equals(language)) //telegu
            return "in"; //india
        else if("ca".equals(language)) //catalan
            return "catalonia"; //official language in andorra (AD), but also other places. Use catalonia for now
        else if("zh".equals(language))
            return "cn";
        return language.toLowerCase(); //this works in many cases, but is wrong in some
    }

    public static Locale getUserLocale()
    {
        return getUserLocale(RequestCycle.get().getRequest());
    }


    public static Locale getUserLocale(Request request)
    {
        return request.getLocale();
    }

    public static Locale getLocaleFromString(String locale)
    {
        return (Locale) PropertiesFactory.eINSTANCE.createFromString(PropertiesPackage.Literals.LOCALE, locale);
    }
}
