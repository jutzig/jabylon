package de.jutzig.jabylon.db.migration;

import javax.sql.DataSource;

import com.googlecode.flyway.core.Flyway;
import com.googlecode.flyway.core.api.MigrationInfo;
import com.googlecode.flyway.core.util.logging.LogFactory;

public class DBMigrator {

	public void migrate(DataSource dataSource) {
		initializeLogging();
		migrateDB(dataSource);
	}

	private void migrateDB(DataSource dataSource) {

		Flyway flyway = new Flyway();
		flyway.setDataSource(dataSource);
//		flyway.setInitOnMigrate(true);
		flyway.setInitVersion("2");
		MigrationInfo current = flyway.info().current();
		if(current==null)
			flyway.init();
		flyway.migrate();
	}

	private void initializeLogging() {

		// configures logging for Flyway
		LogFactory.setLogCreator(new SLF4JLogCreator());

	}
}
