package org.jabylon.log.viewer.pages.util;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.wicket.util.file.File;
import org.ops4j.pax.logging.PaxLoggingService;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

public class PaxLogUtil extends LogAccess {

	private PaxLoggingService manager;

	public PaxLogUtil() {
		Bundle bundle = FrameworkUtil.getBundle(getClass());
		BundleContext context = bundle.getBundleContext();
		ServiceTracker<PaxLoggingService,PaxLoggingService> tracker = new ServiceTracker<PaxLoggingService,PaxLoggingService>(context, PaxLoggingService.class, null);
		tracker.open();
		try {
			manager = tracker.waitForService(TimeUnit.SECONDS.toMillis(30));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	@Override
	public List<LogFile> getLogFiles() {
		File logFile = new File(System.getProperty("karaf.data"),"log/karaf.log");
		LogFile logFileInfo = new LogFile();
		logFileInfo.setLocation(logFile.getAbsolutePath());
		return Collections.singletonList(logFileInfo);
	}

	@Override
	public LogLevel getLogLevel() {
		int logLevel = manager.getLogLevel();
		LogLevel level = mapLevel(logLevel);
		return level;
	}

	private LogLevel mapLevel(int logLevel) {
		switch (logLevel) {
		case PaxLoggingService.LOG_DEBUG:
			return LogLevel.DEBUG;
		case PaxLoggingService.LOG_ERROR:
			return LogLevel.ERROR;
		case PaxLoggingService.LOG_INFO:
			return LogLevel.INFO;
		case PaxLoggingService.LOG_WARNING:
			return LogLevel.WARN;
		default:
			return LogLevel.INFO;
		}
	}

	@Override
	public void setLogLevel(LogLevel level) {
		//TODO: not yet implemented
	}

}
