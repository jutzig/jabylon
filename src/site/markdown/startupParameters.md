
# Available Startup Parameters

Jabylon can be configured by providing a set of parameters and system properties. How to apply them depends on your deployment method.


## Command Line Parameters

These parameters apply to the `jabylon.sh` script of the [standalone application](./download.html) 

 * _--port_ _-p_ same as org.eclipse.equinox.http.jetty.http.port
 * _--host_ _-h_ same as org.eclipse.equinox.http.jetty.http.host
 * _--data_ _-d_ same as osgi.instance.area
 

## System Properties

 * **jabylon.log** the location for the log files
     * default is `logs`
 * **JABYLON_HOME** where the workspace should be created
     * default is `workspace`
 * **osgi.instance.area** the workspace and configuration data will end up in this directory
     * default is the installation directory
 * **org.eclipse.equinox.http.jetty.http.port** the port that Jabylon listens to
     * default is 8080
 * **org.eclipse.equinox.http.jetty.http.host** the network interface that Jabylon listens to
     * default is 0.0.0.0 (all) 