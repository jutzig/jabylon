
# Installing as a Standalone Application

Jabylon can be launched as a standalone application with an embedded jetty server. To do that, start by [downloading](./download.html) the standalone distribution and extracting it to a directory of your choice. Please make sure that the user running Jabylon has write permission in this directory.

## Starting Jabylon on Linux

If you are running Jabylon on a linux system you can use the available startup scripts. To do so, open a shell, `cd` to the directory where you extracted Jabylon and run 

`./jabylon.sh start`

Jabylon should now start up and be available at [http://localhost:8080](http://localhost:8080).

You can change the default port by specifying `--port {PORT}`. By default, Jabylon will be bound to all interfaces. To change this setting you can specifiy `--host {IP_ADDRESS}` 

Jabylon needs a working directory to store the translation projects and the embedded database. By default this directory is located at `jabylon/workspace`. To change this default specify `--data {WORKSPACE}` on command line.

The logs will be placed to `logs/jabylon.log` by default. To change this location you can set the system property `-Djabylon.log`.

Here is an example on how to configure these settings in a command line:

`./jabylon.sh --port 10000 --host 127.0.01 --data /opt/jabylon start`


## Starting Jabylon on Windows

There is currently no startup script for windows available so you will need to start the launcher jar directly. To do so, open a terminal and `cd` to the directory where you extracted Jabylon. To start with default settings (Port 8080) execute

`java -jar plugins/org.eclipse.equinox.launcher_1.2.0.v20110502.jar`

To adjust the defaults you can set these system properties:

 * org.eclipse.equinox.http.jetty.http.host={HOST} to change the IP address that Jabylon will be bound to (all by default)
 * org.eclipse.equinox.http.jetty.http.port={PORT} to change the port Jabylon is using 
 * osgi.instance.area={WORKSPACE} to change the location of the workspace
 * jabylon.log={LOG_DIR) to change where the logfiles will be located
 
 The resulting command line could look like this:
 
 `java -Dosgi.instance.area=C:\Jabylon -jar plugins/org.eclipse.equinox.launcher_1.2.0.v20110502.jar`      

