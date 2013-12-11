Jabylon
=======

Jabylon is an extensible web based translation tool that helps managing and translating java property files to different locales.
The built-in team support for (currently) Git and CVS allows Jabylon to check out projects, update them regulary and commit the translations back into the team repository.

* Jabylon is available as a [WAR](https://buildhive.cloudbees.com/job/jutzig/job/jabylon/org.jabylon$jabylon.war/lastSuccessfulBuild/artifact/org.jabylon/jabylon.war/1.0.0-SNAPSHOT/jabylon.war-1.0.0-SNAPSHOT.war) to be deployed in Tomcat or Jetty (tested with Tomcat 7 and Jetty 9)
 * You can modify the workspace location by setting the `JABYLON_HOME` system property
* Or as [standalone product](https://buildhive.cloudbees.com/job/jutzig/job/jabylon/org.jabylon$jabylon.product/lastSuccessfulBuild/artifact/org.jabylon/jabylon.product/1.0.0-SNAPSHOT/jabylon.product-1.0.0-SNAPSHOT-distribution.zip) with start scripts for linux systems
* A [live demo](https://demo-jabylon.rhcloud.com) with some imported sample projects is available to try it out
