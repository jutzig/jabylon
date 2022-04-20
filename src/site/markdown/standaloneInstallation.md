
# Installing as a Standalone Application

Jabylon can be launched as a standalone application based on [Apache Karaf](http://karaf.apache.org). Start by [downloading](./download.html) the standalone distribution and extract it to a directory of your choice. Please make sure that the user running Jabylon has write permission in this directory.

## Starting Jabylon

To start Jabylon simply open a shell, `cd` to the directory where you extracted Jabylon and run

`bin/start[.bat]`

For more information on starting and stopping see the [Karaf documentation](http://karaf.apache.org/manual/latest/#_start_stop_restart_connect)

Jabylon should now start up and be available at [http://localhost:8080](http://localhost:8080).

By default, it will bind to 0.0.0.0 so it will be reachable on all network interfaces.
To adjust the default port and bind host you can change it in `etc/org.ops4j.pax.web.cfg`.

If you more advanced listener configuration you can modify `etc/jetty.xml`.
Additional details can be found in the  [Karaf documentation](https://karaf.apache.org/manual/latest/webcontainer)

## Integration in the operating system - Service Wrapper

### Linux - Systemd

To run Jabylon as a systemd service, start by opening a shell and entering the jabylons installation directory.
Execute `bin/contrib/karaf-service.sh -k <install-dir> -n <service-name> -u <runtime-user>` to create the systemd unit files. Next copy the unit files to the systemd directory, e.g.:

    $ sudo cp /opt/jabylon/bin/contrib/jabylon.service /etc/systemd/system
    $ sudo cp /opt/jabylon/bin/contrib/jabylon@.service /etc/systemd/system

Lastly, enable the service with

    $ systemctl enable jabylon.service

Additional information can be found in the karaf documentation [](https://karaf.apache.org/manual/latest/#_service_script_templates "Karaf Documentation")

### Windows

To run jabylon as a Windows service, start by opening `bin/contrib/jabylon-service.xml` and search/replace %JABYLON_PATH% with the actual installation directory. Next, open a Cmd as administrator and enter the jabylon installation directory.
Type `bin/contrib/jabylon-service.exe install` to install the service.

