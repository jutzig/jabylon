package de.jutzig.jabylon.db.migration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.flyway.core.util.logging.Log;
import com.googlecode.flyway.core.util.logging.LogCreator;

public class SLF4JLogCreator implements LogCreator {

    @Override
    public Log createLogger(Class<?> clazz) {
        return new SLF4JLog(LoggerFactory.getLogger(clazz));
    }

    private static final class SLF4JLog implements Log {

        private Logger logger;



        public SLF4JLog(Logger logger) {
            super();
            this.logger = logger;
        }

        @Override
        public void debug(String message) {
            logger.debug(message);

        }

        @Override
        public void info(String message) {
            logger.info(message);

        }

        @Override
        public void warn(String message) {
            logger.warn(message);

        }

        @Override
        public void error(String message) {
            logger.error(message);

        }

        @Override
        public void error(String message, Exception e) {
            logger.error(message,e);

        }

    }

}
