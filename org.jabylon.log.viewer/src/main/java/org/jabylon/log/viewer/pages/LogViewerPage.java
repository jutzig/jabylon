/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
/**
 *
 */
package org.jabylon.log.viewer.pages;

import java.io.File;
import java.util.AbstractList;
import java.util.Deque;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.PriorityHeaderItem;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.time.Duration;
import org.jabylon.log.viewer.pages.util.CircularDeque;
import org.jabylon.log.viewer.pages.util.LogTail;
import org.jabylon.log.viewer.pages.util.LogbackUtil;
import org.jabylon.log.viewer.pages.util.LogbackUtil.LogLevel;
import org.jabylon.rest.ui.security.RestrictedComponent;
import org.jabylon.rest.ui.wicket.JabylonApplication;
import org.jabylon.rest.ui.wicket.pages.GenericPage;
import org.jabylon.security.CommonPermissions;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class LogViewerPage extends GenericPage<String> implements RestrictedComponent{


	private static final long serialVersionUID = 1L;

    private LogTail logTail;
    
    private IModel<String> logcontent;
    
    public LogViewerPage(PageParameters parameters) {
    	super(parameters);
    }


    @Override
    protected void construct() {
    	super.construct();
    	final TextArea<String> nextLog = new TextArea<String>("nextLog", logcontent);
    	add(nextLog);
    	nextLog.setOutputMarkupId(true);
    	nextLog.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(1)) {
    		
			private static final long serialVersionUID = 4831467550166004945L;

			@Override
    		protected void onPostProcessTarget(AjaxRequestTarget target) {
    			super.onPostProcessTarget(target);
    			String chunk = readChunk(40);
    			logcontent.setObject(chunk);
    			target.appendJavaScript("updateLog();");
    			target.add(nextLog);
    		}
    	});      	
    	final DropDownChoice<LogLevel> logLevel = new DropDownChoice<LogLevel>("loglevel", new EnumSetList(), new LogLevelRenderer());
    	logLevel.setModel(Model.of(LogbackUtil.getLogLevel()));
//    	logLevel.setModelObject();
        logLevel.add(new AjaxFormComponentUpdatingBehavior("onchange") {
            
			private static final long serialVersionUID = -4582780686636922915L;

			protected void onUpdate(AjaxRequestTarget target) {
            	LogbackUtil.setLogLevel(logLevel.getModelObject());
            }
        });
        add(logLevel);
        
        File logFile = new File(LogbackUtil.getLogFiles().get(0).getLocation());
        add(new DownloadLink("dowloadLog", logFile));
    }
    
    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forReference(JabylonApplication.get().getJavaScriptLibrarySettings().getJQueryReference())));
        response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forUrl("/bootstrap/js/bootstrap.min.js")));
        super.renderHead(response);
    }

   

	private String readChunk(int lines) {
		Deque<String> buffer = new CircularDeque<String>(lines);
		logTail.nextChunk(lines, buffer);
		StringBuilder result = new StringBuilder();
		for (String string : buffer) {
			result.append(string);
			result.append("\r\n");
		}
		return result.toString();
		
	}

	@Override
	protected IModel<String> createModel(PageParameters params) {
    	logTail = new LogTail(LogbackUtil.getLogFiles().get(0).getLocation());
    	String content = readChunk(20);
    	logcontent = Model.of(content);
    	return logcontent;
	}


	@Override
	public String getRequiredPermission() {
		return CommonPermissions.WORKSPACE_CONFIG;
	}

}

class LogLevelRenderer implements IChoiceRenderer<LogLevel> {

	private static final long serialVersionUID = 5412202409908394630L;

	@Override
	public Object getDisplayValue(LogLevel object) {
		return object.toString();
	}

	@Override
	public String getIdValue(LogLevel object, int index) {
		return object.toString();
	}
	
}

class EnumSetList extends AbstractList<LogLevel> {

	@Override
	public LogLevel get(int index) {
		return LogLevel.values()[index];
	}

	@Override
	public int size() {
		return LogLevel.values().length;
	}
	
}
