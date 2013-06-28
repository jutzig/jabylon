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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

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
import de.jutzig.jabylon.common.util.PreferencesUtil;
import de.jutzig.jabylon.properties.DiffKind;
import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.PropertyFileDiff;
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
        logger.info("API request to {}", req.getPathInfo());
        Resolvable child = getObject(req.getPathInfo());
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

    protected Resolvable getObject(String path) throws IOException {
        String info = path;
        if (info == null)
            info = "";
        //FIXME: this is for backwards compatibility. Unify this with URI resolver
        if(info.startsWith("/workspace"))
            info = info.replaceFirst("/workspace", "");
        org.eclipse.emf.common.util.URI uri = org.eclipse.emf.common.util.URI.createURI(info);
        return workspace.resolveChild(uri);

    }

    @Override
    protected void doPut(final HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        URI uri = URI.createURI(req.getPathInfo());
        String[] segmentArray = uri.segments();
        if (segmentArray.length == 1)
            putProject(req, uri, resp);
        else if (segmentArray.length == 2)
            putVersion(req, uri, resp);
        else if (segmentArray.length == 3 && uri.hasTrailingPathSeparator())
            putLocale(req, uri, resp);
        else
            putPropertyFile(req, uri, resp);

    }

    private void putPropertyFile(HttpServletRequest req, URI uri, HttpServletResponse resp) throws IOException {
        // split between the project/version/locale portion and the rest
        String[] segmentArray = uri.segments();
        String[] projectPart = new String[2];
        final String[] descriptorPart = new String[segmentArray.length - projectPart.length];
        System.arraycopy(segmentArray, 0, projectPart, 0, projectPart.length);
        System.arraycopy(segmentArray, projectPart.length, descriptorPart, 0, descriptorPart.length);

        URI projectURI = URI.createHierarchicalURI(projectPart, null, null);
        Resolvable version = getObject(projectURI.path());
        if (version instanceof ProjectVersion) {
            ProjectVersion projectVersion = (ProjectVersion) version;
            URI descriptorLocation = URI.createHierarchicalURI(descriptorPart, null, null);
            File folder = new File(projectVersion.absoluteFilePath().toFileString());
            File propertyFile = new File(folder, descriptorLocation.path());
            final PropertyFileDiff diff = PropertiesFactory.eINSTANCE.createPropertyFileDiff();
            diff.setKind(propertyFile.isFile() ? DiffKind.MODIFY : DiffKind.ADD);
            updateFile(propertyFile, req.getInputStream());
            diff.setNewPath(descriptorLocation.path());
            diff.setOldPath(descriptorLocation.path());
            try {
                TransactionUtil.commit(projectVersion, new Modification<ProjectVersion, ProjectVersion>() {
                    @Override
                    public ProjectVersion apply(ProjectVersion object) {

                        object.partialScan(PreferencesUtil.getScanConfigForProject(object.getParent()), diff);
                        return object;
                    }
                });
            } catch (CommitException e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Commit failed: " + e.getMessage());
                logger.error("Commit failed", e);
            }
        } else
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource " + projectURI.path() + " does not exist");

    }

    private void putLocale(HttpServletRequest req, final URI uri, HttpServletResponse resp) throws IOException {
        URI truncated = uri.trimSegments(1);
        Resolvable object = getObject(truncated.path());
        if (object instanceof ProjectVersion) {
            ProjectVersion version = (ProjectVersion) object;
            if (version.getChild(uri.lastSegment()) == null) {
                try {
                    TransactionUtil.commit(version, new Modification<ProjectVersion, ProjectVersion>() {
                        @Override
                        public ProjectVersion apply(ProjectVersion object) {

                            ProjectLocale locale = PropertiesFactory.eINSTANCE.createProjectLocale();
                            locale.setName(uri.lastSegment());
                            locale.setLocale((Locale) PropertiesFactory.eINSTANCE.createFromString(PropertiesPackage.Literals.LOCALE,
                                    uri.lastSegment()));
                            PropertyResourceUtil.addNewLocale(locale, object);
                            return object;
                        }
                    });
                } catch (CommitException e) {
                    logger.error("Commit failed", e);
                }
            }
        } else
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Version " + truncated.path() + " does not exist");
    }

    private void putVersion(HttpServletRequest req, final URI uri, HttpServletResponse resp) throws IOException {
        URI truncated = uri.trimSegments(1);
        Resolvable object = getObject(truncated.path());
        if (object instanceof Project) {
            Project project = (Project) object;
            if (project.getChild(uri.lastSegment()) == null) {
                try {
                    TransactionUtil.commit(project, new Modification<Project, Project>() {
                        @Override
                        public Project apply(Project object) {
                            ProjectVersion child = PropertiesFactory.eINSTANCE.createProjectVersion();
                            ProjectLocale locale = PropertiesFactory.eINSTANCE.createProjectLocale();
                            child.getChildren().add(locale);
                            child.setTemplate(locale);
                            child.setName(uri.lastSegment());
                            object.getChildren().add(child);
                            return object;
                        }
                    });
                } catch (CommitException e) {
                    logger.error("Commit failed", e);
                }
            }
        } else
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Project " + truncated.path() + " does not exist");

    }

    private void putProject(HttpServletRequest req, final URI uri, HttpServletResponse resp) throws IOException {
        // TODO: evaluate JSON stream for settings
        try {
            TransactionUtil.commit(workspace, new Modification<Workspace, Workspace>() {
                @Override
                public Workspace apply(Workspace object) {
                    Project child = PropertiesFactory.eINSTANCE.createProject();
                    child.setName(uri.lastSegment());
                    object.getChildren().add(child);
                    return object;
                }
            });
        } catch (CommitException e) {
            logger.error("Commit failed", e);
        }

    }

    private void updateFile(File destination, ServletInputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        destination.getParentFile().mkdirs();
        FileOutputStream out = new FileOutputStream(destination);
        try {
            while (true) {
                int read = inputStream.read(buffer);
                if (read > 0)
                    out.write(buffer, 0, read);
                if (read < 0)
                    break;
            }
        } finally {
            out.close();
            inputStream.close();
        }
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
                while (true) {
                    int read = in.read(buffer);
                    if (read <= 0)
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
