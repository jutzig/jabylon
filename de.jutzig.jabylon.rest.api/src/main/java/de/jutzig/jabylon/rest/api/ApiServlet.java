/*
 * ApiServlet.java
 *
 * created at 13.09.2012 by utzig <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package de.jutzig.jabylon.rest.api;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.emf.common.util.URI;

import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.rest.api.json.JSONEmitter;

/**
 * TODO short description for ApiServlet.
 * <p>
 * Long description for ApiServlet.
 * 
 * @author utzig
 */
public class ApiServlet extends HttpServlet
// implements Servlet
{

	/** field <code>serialVersionUID</code> */
	private static final long serialVersionUID = -1167994739560620821L;
	private Workspace workspace;

	public ApiServlet(Workspace workspace) {
		this.workspace = workspace;
	}

	/**
	 * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see javax.servlet.Servlet#getServletConfig()
	 */
	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String info = req.getPathInfo();
		if(info==null)
			info = "";
		else if(info.startsWith("/"))
			info = info.substring(1);
		org.eclipse.emf.common.util.URI uri = org.eclipse.emf.common.util.URI.createURI(info);
		Resolvable child = workspace.resolveChild(uri);
		if (child == null) {

			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			resp.getOutputStream().println("Resource " + req.getPathInfo() + " does not exist");
			return;
		}
		JSONEmitter emitter = new JSONEmitter();
		StringBuilder result = new StringBuilder();
		String depthString = req.getParameter("depth");
		int depth = 1;
		if (depthString != null)
			depth = Integer.valueOf(depthString);
		String type = req.getParameter("type");
		if ("file".equals(type)) {
			if (child instanceof PropertyFileDescriptor) {
				serveFile((PropertyFileDescriptor) child, resp);
			}
		}
		// TODO: use appendable
		emitter.serialize(child, result, depth);
		resp.getOutputStream().print(result.toString());
		resp.getOutputStream().close();
	}

	private void serveFile(PropertyFileDescriptor fileDescriptor, HttpServletResponse resp) throws IOException {

		URI path = fileDescriptor.absolutPath();
		File file = new File(path.toFileString());
		ServletOutputStream outputStream = resp.getOutputStream();
		if (!file.exists()) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			outputStream.println("Resource " + fileDescriptor.fullPath() + " does not exist");
		} else {
			BufferedInputStream in = null;
			try {
				in = new BufferedInputStream(new FileInputStream(file));
				resp.setContentLength((int) file.length());
				resp.setContentType("application/octet-stream");
				byte[] buffer = new byte[1024];
				while(true)
				{
					int read = in.read(buffer);
					if(read<=0)
						break;
					outputStream.write(buffer, 0, read);
				}
				
			} finally {
				if (in != null)
					in.close();
			}

		}
		outputStream.flush();
	}

	/**
	 * @see javax.servlet.Servlet#getServletInfo()
	 */
	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see javax.servlet.Servlet#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
