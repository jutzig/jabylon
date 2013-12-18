/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.log.viewer.pages.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.FileAppender;

public class LogbackUtil {

	public enum LogLevel {
		
		OFF(Level.OFF_INT),ERROR(Level.ERROR_INT),WARN(Level.WARN_INT),INFO(Level.INFO_INT),DEBUG(Level.DEBUG_INT),TRACE(Level.TRACE_INT),ALL(Level.ALL_INT);
		
		private int intValue;
		
		private LogLevel(int intValue)
		{
			this.intValue = intValue;
		}
		
		public static LogLevel fromInt(int intValue) {
			for (LogLevel level : values()) {
				if(level.intValue==intValue)
					return level;
			}
			return null;
		}
	}

	/**
	 * Retrieve all configured logback loggers. 
	 * 
	 * @param showAll If set return ALL loggers, not only the configured ones.
	 * @return List of Loggers
	 */
	public static List<ch.qos.logback.classic.Logger> getLoggers(final boolean showAll) {

		final LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		final List<ch.qos.logback.classic.Logger> loggers = new ArrayList<Logger>();

		for (ch.qos.logback.classic.Logger log : lc.getLoggerList()) {
			if(showAll == false) {
				if(log.getLevel() != null || LogbackUtil.hasAppenders(log)) {
					loggers.add(log);
				}
			} else {
				loggers.add(log);
			}
		}

		return loggers;
	}

	/**
	 * Get a single logger. 
	 * 
	 * @return Logger
	 */
	public static ch.qos.logback.classic.Logger getLogger(final String loggerName) {

		final LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		
		return lc.getLogger(loggerName);
		
	}
	
	/** 
	 * Test whether the provided logger has appenders. 
	 * 
	 * @param logger The logger to test
	 * @return true if the logger has appenders.
	 */
	public static boolean hasAppenders(ch.qos.logback.classic.Logger logger) {
		Iterator<Appender<ILoggingEvent>> it = logger.iteratorForAppenders();
		return it.hasNext();
	}

	/**
	 * retrieves information about the available file appenders 
	 * @return
	 */
	public static List<LogFile> getLogFiles() {
		
		List<LogFile> logFiles = new ArrayList<LogFile>();
		Logger logger = getRootLogger();

		Iterator<Appender<ILoggingEvent>> it = logger.iteratorForAppenders();

		while (it.hasNext()) {

			Appender<ILoggingEvent> appender = it.next();

			if (appender instanceof FileAppender) {
				FileAppender<ILoggingEvent> fileAppender = (FileAppender<ILoggingEvent>) appender;
				File logFile = new File(fileAppender.getFile());
				LogFile logFileInfo = new LogFile();
				logFileInfo.setLocation(logFile.getAbsolutePath());
				logFiles.add(logFileInfo);
			}

		}

		return logFiles;
	}

	
	
	public static Logger getRootLogger() {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		return lc.getLogger(Logger.ROOT_LOGGER_NAME);
	}
	
	public static LogLevel getLogLevel() {
		return LogLevel.fromInt(getLogger("org.jabylon").getLevel().toInt());
	}
	
	public static void setLogLevel(LogLevel level) {
		Level newLevel = Level.toLevel(level.intValue);
//		getRootLogger().setLevel(newLevel);
		List<Logger> loggers = getLoggers(false);
		for (Logger logger : loggers) {
			//no point in raising the jetty log level
			if(logger.getName().startsWith("org.jabylon"))
				logger.setLevel(newLevel);
		}
	}	
	
}