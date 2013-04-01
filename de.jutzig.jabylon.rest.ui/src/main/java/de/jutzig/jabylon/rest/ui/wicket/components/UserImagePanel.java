package de.jutzig.jabylon.rest.ui.wicket.components;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.request.resource.UrlResourceReference;

import de.jutzig.jabylon.users.User;

public class UserImagePanel extends Panel {

	private static final String GRAVATAR_BASE_URL = "https://www.gravatar.com/avatar/";
	private static final int DEFAULT_SIZE = 30;
	private int size = 30;

	private static final long serialVersionUID = 1L;

	public UserImagePanel(String id, IModel<User> model) {
		this(id,model,DEFAULT_SIZE);
	}

	public UserImagePanel(String id, IModel<User> model, int size) {
		super(id, model);
		this.size = size;
		add(new Image("image", getImageUrl(model)));
		add(new Label("name", getUsername(model)));
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
		for (int i = 0; i < array.length; ++i) {
			sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
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
