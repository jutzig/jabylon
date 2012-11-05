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
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.common.util.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jutzig.jabylon.cdo.connector.Modification;
import de.jutzig.jabylon.cdo.connector.TransactionUtil;
import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.properties.util.PropertyResourceUtil;
import de.jutzig.jabylon.resources.persistence.PropertyPersistenceService;
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
	private PropertyPersistenceService persistence;
	private static final Logger logger = LoggerFactory.getLogger(ApiServlet.class);
	
	public ApiServlet(Workspace workspace, PropertyPersistenceService persistence) {
		this.workspace = workspace;
		this.persistence = persistence;
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
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("API request to {}",req.getPathInfo());
		Resolvable child = getObject(req.getPathInfo(), resp);
		if (child == null) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource " + req.getPathInfo() + " does not exist");
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

	protected Resolvable getObject(String path, HttpServletResponse resp) throws IOException {
		String info = path;
		if(info==null)
			info = "";
		org.eclipse.emf.common.util.URI uri = org.eclipse.emf.common.util.URI.createURI(info);
		return workspace.resolveChild(uri);

	}
	
	@Override
	protected void doPut(final HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Resolvable child = getObject(req.getPathInfo(), resp);
		if (child instanceof PropertyFileDescriptor) {
			PropertyFileDescriptor descriptor = (PropertyFileDescriptor) child;
			updateFile(descriptor,req.getInputStream());
		}
		else
		{
			URI uri = URI.createURI(req.getPathInfo());
			String[] segmentArray = uri.segments();
			//split between the project/version/locale portion and the rest
			String[] projectPart = new String[3];
			final String[] descriptorPart = new String[segmentArray.length-projectPart.length];
			System.arraycopy(segmentArray, 0, projectPart, 0, projectPart.length);
			System.arraycopy(segmentArray, projectPart.length, descriptorPart, 0, descriptorPart.length);
			URI projectURI = URI.createHierarchicalURI(projectPart, null, null);
			Resolvable locale = getObject(projectURI.path(), resp);
			if (locale == null) {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource " + projectURI.path() + " does not exist");
				return;
			}
			if (locale instanceof ProjectLocale) {
				ProjectLocale projectLocale = (ProjectLocale) locale;
				if(!projectLocale.isMaster())
					throw new UnsupportedOperationException("currently only template locale files can be uploaded");

				final PropertyFileDescriptor fileDescriptor = PropertiesFactory.eINSTANCE.createPropertyFileDescriptor();
				PropertyFile properties = fileDescriptor.loadProperties(req.getInputStream());
				fileDescriptor.setLocation(URI.createHierarchicalURI(descriptorPart, null, null));
				fileDescriptor.setName(fileDescriptor.getLocation().lastSegment());
				fileDescriptor.setKeys(properties.getProperties().size());
				try {
					TransactionUtil.commit(projectLocale.getParent(), new Modification<ProjectVersion, ProjectVersion>() {
						@Override
						public ProjectVersion apply(ProjectVersion version) {

							PropertyResourceUtil.addNewTemplateDescriptor(fileDescriptor, version);
							return version;
						}
					});
					persistence.saveProperties(fileDescriptor, properties, false);
				} catch (CommitException e) {
					logger.error("Failed to commit put request to "+req.getPathInfo(),e);
					resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Commit failed");					
				}
				
				
			}
		}
	}



	private void updateFile(PropertyFileDescriptor descriptor, ServletInputStream inputStream) {
		PropertyFile propertyFile = descriptor.loadProperties(inputStream);
		persistence.saveProperties(descriptor, propertyFile, false);
	}

	private void serveFile(PropertyFileDescriptor fileDescriptor, HttpServletResponse resp) throws IOException {

		URI path = fileDescriptor.absolutPath();
		File file = new File(path.toFileString());
		ServletOutputStream outputStream = resp.getOutputStream();
		if (!file.exists()) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource " + fileDescriptor.fullPath() + " does not exist");
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
