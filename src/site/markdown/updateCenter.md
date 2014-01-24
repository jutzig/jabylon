Update Center
=============

Jabylon is running in an [OSGi](http://www.osgi.org/Main/HomePage) container to be easily extended by additional plug-ins. That means you can install additional plug-ins (like team provider or new translation checks) through the online update center or even install your own locally built plug-ins.  

To access the update center, click the _System_ link in the toolbar.

![Update Center](images/updateCenter.png)

In the update center you can see which plug-ins you can currently install (1), which updates are available(2) and inspect the current bundle configuration (3).

Select one or more plugins by checking the _Install_ box (4) and press submit to start the download. In most cases plug-ins and updates can be installed on-the-fly without restarting Jabylon.

All plug-ins will be downloaded to `JABYLON_HOME/addons`. To uninstall a plugin, you can simply shutdown the server, delete the respective file in that directory and start Jabylon again. If you want to place your own plug-ins manually in this folder, please make sure to follow the file name convention `bundle.symbolic.name_version.jar` 


## Installed Software

In the installed software tab you can see the complete list of which bundles (plug-ins) are currently deployed in your system (1) as well as their current state (2).

![Installed Bundles](images/bundles.png)  

You can also start and stop individual bundles by pressing the respective button (3). This can be useful to intentionally (temporary) disable one of Jabylons sub-systems like the _JSON API_.


### Attention!

**Starting and stopping bundles affects the running system**. Stopping a bundle can have dire consequences. If you would for instance stop the **updatecenter** bundle, the updatecenter will shut down and you will no longer be able to access this page to start it up again. In such a case you would have to restart the server.