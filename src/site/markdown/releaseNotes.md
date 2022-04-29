
# Release Notes

## Jabylon 1.4.2

Jabylon 1.4.2 was released on the **30th April 2022** and is available for [download](download.html).
The release is completely based on karaf and retires all other distribution. The release focuses on the switch to karaf and security updates.
Starting from this version, Java 11 is required

## Jabylon 1.3.0

Jabylon 1.3.0 was released on the **16th March 2017** and is available for [download](download.html).

This release switches from the previous standalone distribution to an Apache Karaf based distribution. Please see the  [upgrade guide](upgrade.html) for specifics on
how to migrate from an older release to 1.3.0.
 
The release includes several new features and bugfixes:

 * [46](https://github.com/jutzig/jabylon/issues/46) -  Create a Apache Karaf distribution
 * [14](https://github.com/jutzig/jabylon/issues/14) -  Allow editing the template language
 * [253](https://github.com/jutzig/jabylon/issues/253) - Allow searching the template language
 * [261](https://github.com/jutzig/jabylon/issues/261) - User combobox should be sorted in alphabetical manor
 * [262](https://github.com/jutzig/jabylon/issues/262) - Mark properties as non-translatable
 
  
For a full list see [here](https://github.com/jutzig/jabylon/issues?q=milestone%3A1.3.0+is%3Aclosed) 

## Jabylon 1.2.0

Jabylon 1.2.0 was released on the **14th April 2016** and is available for [download](download.html).

The release includes several bugfixes as well as several new features:

 * [255](https://github.com/jutzig/jabylon/issues/255) - support private key auth for git
 * [243](https://github.com/jutzig/jabylon/issues/243) - XLIFF support (export and import)
 * [240](https://github.com/jutzig/jabylon/issues/240) - file upload through web ui
 * [21](https://github.com/jutzig/jabylon/issues/21) - self registration of users
  
For a full list see [here](https://github.com/jutzig/jabylon/issues?q=milestone%3A1.2.0+is%3Aclosed) 


## Jabylon 1.1.4

Jabylon 1.1.4 was released on the **4th March 2015** and is available for [download](download.html).

The release includes support for LDAP groups as well as several bugfixes and security fixes.  
 
For a full list see [here](https://github.com/jutzig/jabylon/issues?q=milestone%3A1.1.4+is%3Aclosed) 

## Jabylon 1.1.3

Jabylon 1.1.3 was released on the **12th December 2014** and is available for [download](download.html).

The release includes bugfixes comes with a batch script to start the standalone version on windows and provides better support for integrating with [Gerrit](https://code.google.com/p/gerrit/)  
 
For a full list see [here](https://github.com/jutzig/jabylon/issues?q=milestone%3A1.1.3+is%3Aclosed) 

## Jabylon 1.1.2

Jabylon 1.1.2 was released on the **1st July 2014** and is available for [download](download.html).

The release includes bugfixes and provides a quicker way to accept user suggestions 

 * [217](https://github.com/jutzig/jabylon/issues/217) - easier access to accept suggestions
 
For a full list see [here](https://github.com/jutzig/jabylon/issues?milestone=7&amp;page=1&amp;state=closed "Issue List") 

## Jabylon 1.1.1

Jabylon 1.1.1 was released on the **20th May 2014** and is available for [download](download.html).

The release includes bugfixes, usability and performance enhancements as well as two new features 

 * [201](https://github.com/jutzig/jabylon/issues/201) - Support Project Announcements
 * [207](https://github.com/jutzig/jabylon/issues/207) - Support Rebase (Git)
 
For a full list see [here](https://github.com/jutzig/jabylon/issues?milestone=6&amp;page=1&amp;state=closed "Issue List") 

## Jabylon 1.1.0

Jabylon 1.1.0 was released on the **23th March 2014** and is available for [download](download.html). 

Starting from this release iOS and Android projects.

 * [193](https://github.com/jutzig/jabylon/issues/193) - Use terminology in equality check
 * [192](https://github.com/jutzig/jabylon/issues/192) - Add a remove action 
 * [191](https://github.com/jutzig/jabylon/issues/192) - support for iOS
 * [190](https://github.com/jutzig/jabylon/issues/190) - Jabylon needs a logo
 * [177](https://github.com/jutzig/jabylon/issues/177) - Add support for Android
 * [28](https://github.com/jutzig/jabylon/issues/28) - add annotation support for properties
 
For a full list see [here](https://github.com/jutzig/jabylon/issues?milestone=5&amp;page=1&amp;state=closed "Issue List")  
  

## Jabylon 1.0.2

Jabylon 1.0.2 was released on the **19th February 2014** and is available for [download](download.html). 

The release contains several bugfixes as well as some new features:

 * [188](https://github.com/jutzig/jabylon/issues/188) - Ability to configure the refspec for Git
 * [189](https://github.com/jutzig/jabylon/issues/189) - Ability to reset to remote version 
 * [35](https://github.com/jutzig/jabylon/issues/35) - allow bulk download through REST API
 
For a full list see [here](https://github.com/jutzig/jabylon/issues?milestone=4&amp;page=1&amp;state=closed "Issue List")  
  

### Upgrade Notes

1.0.2 is compatible to previous releases. Simply follow the [upgrade guide](upgrade.html). 

If you use the CVS or SVN plug-ins you need to update them as well in order to use the [reset](https://github.com/jutzig/jabylon/issues/189) functionality.