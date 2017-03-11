
# Upgrading Jabylon

Upgrading Jabylon is straightforward since the database and configurations are migrated automatically as the software evolves.


## Database Backup

In case you need to roll back an upgrade, it is advisable to backup the database before upgrading. Jabylon uses an embedded H2 database so for a full backup it is enough to create a copy of `[JABYLON_HOME]/cdo`. Please note that Jabylon should be shut down before creating a database backup.

## Standalone

If you run Jabylon in standalone mode, extract the new version of Jabylon and copy over the `work` folder which contains the database and project rsources. In case you modified the host/port in `etc/org.ops4j.pax.web.cfg` apply your change again.

### Upgrade to 1.3.0

Please note that starting from version 1.3.0 Jabylon is shipped in a Karaf container. To migrate from the old standalone distribution follow these steps:

 * Extract the new archive to a new folder
 * Move or copy the folders `workspace`, `lucene` and `cdo` to a folder called `work` in the new location
 * If you modified the jaas configuration, apply your changes to `deploy/jaas.xml`

## WAR

If you run Jabylon as a WAR it is sufficient to deploy a new version of the WAR to the servlet container.

## Plug-Ins

plug-ins can be upgraded independently of Jabylon, but if a plug-in was built against an older version of Jabylon you might have to upgrade the plug-in as well for it to stay functional.