package org.jabylon.cdo.server.logging;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogReaderService;
import org.osgi.service.log.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.helpers.BasicMarkerFactory;

@Component
public class OSGiLogger implements LogListener {
    @Reference
    private LogReaderService logReader;

    private Logger logger = LoggerFactory.getLogger(OSGiLogger.class);
    private BasicMarkerFactory factory = new BasicMarkerFactory();

    public void bindLogReader(LogReaderService service) {
        this.logReader = service;
        logReader.addLogListener(this);
    }

    public void unbindLogReader(LogReaderService service) {
        service.removeLogListener(this);
        logReader = null;
    }

    @Override
    public void logged(LogEntry entry) {

        Marker marker = null;
        if (entry.getBundle() != null)
            marker = factory.getMarker(entry.getBundle().getSymbolicName());
        switch (entry.getLevel()) {
        case LogService.LOG_DEBUG:
            logger.debug(marker, entry.getMessage(), entry.getException());
            break;
        case LogService.LOG_ERROR:
            logger.error(marker, entry.getMessage(), entry.getException());
            break;
        case LogService.LOG_INFO:
            logger.info(marker, entry.getMessage(), entry.getException());
            break;
        case LogService.LOG_WARNING:
            logger.warn(marker, entry.getMessage(), entry.getException());
            break;
        }

    }
}
