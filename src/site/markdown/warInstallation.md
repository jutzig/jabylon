
# Installing as a web application

Jabylon can be deployed into any Java EE application server. This document will show an example on how to do that in Tomcat 7 which can be downloaded [here](http://tomcat.apache.org/download-70.cgi) and Jetty 9 which can be downloaded [here](http://download.eclipse.org/jetty/).

## Deploying Archiva in Tomcat 7

[Download](./download.html) the Jabylon WAR and copy it into `tomcat/webapps`.
Now you can run tomcat with e.g `bin/startup.sh`. Jabylon should automatically get deployed and be available at [http://localhost:8080/jabylon](http://localhost:8080/jabylon).

Jabylon needs a working directory to store the translation projects and the embedded database. By default this directory is located at `$HOME/jabylon`. To change this default edit the file `conf/catalina.properties` and add the value `JABYLON_HOME=/opt/jabylon`. You will have to restart tomcat after saving the file.

The logs will be placed to `logs/jabylon.log` by default. To change this location you can set the system property `jabylon.log` (this can be done in `conf/catalina.properties` again).

## Deploying Archiva in Jetty

[Download](./download.html) the Jabylon WAR and copy it into `jetty/webapps`.
Now you can run jetty with `java -jar start.jar`. Jabylon should automatically get deployed and be available at [http://localhost:8080/jabylon](http://localhost:8080/jabylon).

Jabylon needs a working directory to store the translation projects and the embedded database. By default this directory is located at `$HOME/jabylon`. To change this default start jetty with the command line `java -DJABYLON_HOME=/opt/jabylon -jar start.jar`.

The logs will be placed to `logs/jabylon.log` by default. To change this location you can set the system property `jabylon.log`.