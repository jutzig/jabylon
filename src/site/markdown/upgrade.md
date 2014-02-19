
# Upgrading Jabylon

Upgrading Jabylon is straightforward since the database and configurations are migrated automatically as the software evolves.


## Database Backup

In case you need to roll back an upgrade, it is advisable to backup the database before upgrading. Jabylon uses an embedded H2 database so for a full backup it is enough to create a copy of `[JABYLON_HOME]/cdo`. Please note that Jabylon should be shut down before creating a database backup.


## WAR

If you run Jabylon as a WAR it is sufficient to deploy a new version of the WAR to the servlet container.


## Standalone

If you run Jabylon in standalone mode, backup your jaas config if you customized it (`configuration/jaas.config`) and then delete the folders `configuration` and `plugins`. Extract the new version into the same directory, restore your jaas config and start Jabylon.

## Plug-Ins

plug-ins can be upgraded independently of Jabylon, but if a plug-in was built against an older version of Jabylon you might have to upgrade the plug-in as well for it to stay functional.