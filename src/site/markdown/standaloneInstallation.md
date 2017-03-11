
# Installing as a Standalone Application

Jabylon can be launched as a standalone application based on [Apache Karaf](http://karaf.apache.org). Start by [downloading](./download.html) the standalone distribution and extract it to a directory of your choice. Please make sure that the user running Jabylon has write permission in this directory.

## Starting Jabylon

To start Jabylon simply open a shell, `cd` to the directory where you extracted Jabylon and run 

`bin/start[.bat]`

For more information on starting and stopping see the [Karaf documentation](http://karaf.apache.org/manual/latest/#_start_stop_restart_connect)

Jabylon should now start up and be available at [http://localhost:8080](http://localhost:8080).

By default, it will bind to 0.0.0.0 so it will be reachable on all network interfaces.
To adjust the default port and bind host you can change it in `etc/org.ops4j.pax.web.cf`.

If you more advanced listener configuration you can modify `etc/jetty.xml`.
Additional details can be found in the  [Karaf documentation](https://karaf.apache.org/manual/latest/webcontainer)

## Integration in the operating system - Service Wrapper

To run Jabylon as an operating system service first start it in interactive mode with `bin/karaf[.bat]`.
Once the command prompt is shown, type

`feature:install service-wrapper`

Next, type

`wrapper:install -n jabylon -d "Jabylon translation server"`

Quit the application by pressing `CTRL+D` and follow the instructions in the command prompt to finish the service installations.
Additional details can be found in the [Karaf documentation](http://karaf.apache.org/manual/latest/#_integration_in_the_operating_system_the_service_wrapper). 
