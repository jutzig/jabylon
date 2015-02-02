/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.components;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.request.resource.UrlResourceReference;
import org.jabylon.users.User;

public class UserImagePanel extends Panel {

    private static final String GRAVATAR_BASE_URL = "https://www.gravatar.com/avatar/";
    private static final int DEFAULT_SIZE = 30;
    private static final int LARGE_SIZE = 120;
    private int size = DEFAULT_SIZE;

    private static final long serialVersionUID = 1L;

    public UserImagePanel(String id, IModel<User> model) {
        this(id,model,false);
    }

    public UserImagePanel(String id, IModel<User> model, boolean large) {
        super(id,model);
        Label name = new Label("name", getUsername(model));
        add(name);
        if(large) {
            name.add(new AttributeAppender("style", "font-size: large;"));
            size = LARGE_SIZE;
        }
        Image image = new Image("image", getImageUrl(model));
        if(large)
        	image.add(new AttributeAppender("class","img-polaroid"));
        add(image);
    }


    private String getUsername(IModel<User> model) {
        String username = "none";
        if (model.getObject() != null) {
            username = model.getObject().getDisplayName();
            if (username == null || username.isEmpty())
                username = model.getObject().getName();
        }
        return username;
    }

    private ResourceReference getImageUrl(IModel<User> model) {
        String email = getEMail(model);
        if (email == null)
            email = model.getObject().toString();
        Url url = Url.parse(GRAVATAR_BASE_URL + MD5Util.md5Hex(normalize(email)));
        url.addQueryParameter("size", size);
        url.addQueryParameter("d", "wavatar");
        return new UrlResourceReference(url);

    }

    private String normalize(String email) {
        if(email==null)
            return "";

        return email.trim().toLowerCase(getSession().getLocale());
    }

    private String getEMail(IModel<User> model) {
        if (model.getObject() == null)
            return null;
        return model.getObject().getEmail();
    }

}

class MD5Util {
    public static String hex(byte[] array) {
        StringBuffer sb = new StringBuffer();
        for (byte element : array)
        {
            sb.append(Integer.toHexString((element & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

    public static String md5Hex(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return hex(md.digest(message.getBytes("CP1252")));
        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }
}
