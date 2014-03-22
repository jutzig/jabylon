Jabylon Translation Server
=========================

Jabylon is an open-source translation server designed to ease the translation of software projects as a team effort.
It provides a web ui for both developers and translators that can be seen as a [live demo](http://demo-jabylon.rhcloud.com/ "Jabylon Demo"). 

![Jabylon](images/jabylon_intro.png) 


## Is Jabylon the right Tool for me?

Jabylon helps you, your user community and translators to translate software projects more easily. If you are looking for a feature rich web based translation tool that is easy to setup, easy to use and can be integrated tightly into your source code repository and build process, Jabylon might be what you are looking for.

So far Jabylon can be used to translate

 * Java [property files](http://en.wikipedia.org/wiki/.properties)
 * Android Applications
 * iOS Applications
 
The application is based on [OSGi](http://en.wikipedia.org/wiki/OSGi) and designed to be highly extensible. That means that other translation methods and formats will be supported in the future and you are free to create your own plugins.

Jabylon can be a great help if you are working on a software project that needs to be translated into multiple languages. You can either use it as a public server to enable your community to contribute suggestions and translations to your software or in your intranet to simplify the translation collaboration in your company.


## Key Features

 * powerful web based translation editor
 * supports multiple formats
     * Java
     * Android
     * iOS
 * automatic translation checks
 * terminology support
 * translation memory
 * tight integration with source code management systems to synchronize and automatically commit new translations
     * supports multiple branches
     * built-in support for [Git](http://git-scm.com/), [Subversion](http://subversion.apache.org/) and [CVS](http://savannah.nongnu.org/projects/cvs) 
     * plug-in interface for other SCMs
 * full text search
 * fine grained access rights
 * LDAP support
 * JSON API for programmatic access
 * update center to update and install plug-ins
     
# Deployment

Jabylon runs in an OSGi Container ([Equinox](http://www.eclipse.org/equinox/)) and comes pre-packaged as either a standalone application or as a WAR packaged web application. For details see the [Download Section](./download.html)     

