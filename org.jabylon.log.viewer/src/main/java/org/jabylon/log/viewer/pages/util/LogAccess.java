package org.jabylon.log.viewer.pages.util;

import java.util.List;

import org.jabylon.cdo.server.ServerConstants;

import ch.qos.logback.classic.Level;

public abstract class LogAccess {

	public enum LogLevel {

		OFF(Level.OFF_INT), ERROR(Level.ERROR_INT), WARN(Level.WARN_INT), INFO(Level.INFO_INT), DEBUG(Level.DEBUG_INT), TRACE(Level.TRACE_INT), ALL(
				Level.ALL_INT);

		protected int intValue;

		private LogLevel(int intValue) {
			this.intValue = intValue;
		}

		public static LogLevel fromInt(int intValue) {
			for (LogLevel level : values()) {
				if (level.intValue == intValue)
					return level;
			}
			return null;
		}
	}

	private static LogAccess INSTANCE;
	
	static {
		if(ServerConstants.IS_KARAF) {
			INSTANCE = new PaxLogUtil();
		}
		else {
			INSTANCE = new LogbackUtil();
		}
	}
	
	public static LogAccess get() {
		return INSTANCE;
	}
	

	/**
	 * retrieves information about the available file appenders
	 * 
	 * @return
	 */
	public abstract List<LogFile> getLogFiles();

	public abstract LogLevel getLogLevel();

	public abstract void setLogLevel(LogLevel level);

}