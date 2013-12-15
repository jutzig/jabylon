/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.db.migration;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.flyway.core.Flyway;
import com.googlecode.flyway.core.api.MigrationInfo;
import com.googlecode.flyway.core.api.MigrationVersion;
import com.googlecode.flyway.core.util.logging.LogFactory;


public class DBMigrator
{

    private static final Logger logger = LoggerFactory.getLogger(DBMigrator.class);


    public void migrate(DataSource dataSource)
    {
        initializeLogging();
        migrateDB(dataSource);
    }


    private void migrateDB(DataSource dataSource)
    {
        boolean dbExists = dbExists(dataSource);
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.setTable("SCHEMA_VERSION");
        // flyway.setInitOnMigrate(true);
        if (!dbExists)
        {
            MigrationVersion migrationVersion = getLatestVersion(flyway);
            if (migrationVersion != null)
                flyway.setInitVersion(migrationVersion);
        }
        MigrationInfo current = flyway.info().current();
        if (current == null)
            flyway.init();
        flyway.migrate();
    }


    private MigrationVersion getLatestVersion(Flyway flyway)
    {
        MigrationInfo[] pending = flyway.info().pending();
        if (pending == null || pending.length == 0)
            return null;
        return pending[pending.length - 1].getVersion();
    }


    private boolean dbExists(DataSource dataSource)
    {
        Connection connection = null;
        Statement statement = null;
        try
        {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT TABLE_SCHEMA FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'WORKSPACE'");
            boolean hasResult = result.next();
            result.close();
            statement.close();
            connection.close();
            return hasResult;
        }
        catch (SQLException e)
        {
            logger.error("Failed to determine if the DB already exists", e);
        }
        finally
        {
            if (statement != null)
                try
                {
                    statement.close();
                    if (connection != null)
                        connection.close();
                }
                catch (SQLException e)
                {
                    logger.warn("Failed to close connection", e);
                }
        }

        return false;
    }


    private void initializeLogging()
    {

        // configures logging for Flyway
        LogFactory.setLogCreator(new SLF4JLogCreator());

    }
}
