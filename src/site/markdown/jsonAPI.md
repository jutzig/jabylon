
# JSON API

Jabylon has a JSON API for programmatic access. The API can be used to fetch translations from the server, or upload new property files. Requests are answered directly with a JSON serialized version of the internal [EMF](http://www.eclipse.org/modeling/emf/?project=emf) model, so one needs to understand the structure of the ecore a bit to understand the JSON output. The API is reachable under `/api`.


## Reading Data

All resources can be reached by specifying their path in the data model. The format for this is:

    /api/PROJECT_NAME/BRANCH/LOCALE/path/to/Messages.properties
    
Requesting [/api/Jabylon/master/de](http://demo-jabylon.rhcloud.com/api/Jabylon/master/de) for example produces this output:

    {"percentComplete":100,"name":"de","locale":"de","propertyCount":126}

This is the content of the `de` locale in branch `master` of project `Jabylon`. To get to a specific property file, [append the file path](http://demo-jabylon.rhcloud.com/api/Jabylon/master/de/org.jabylon.log.viewer/src/main/java/org/jabylon/log/viewer/pages/LogViewerPage_de.properties) to the URI. Since the template language usually does not have a defined locale itself, use `template` as the locale if you are interested in the template properties.

By default, a query on a model object will only serialize one depth level of the given object. To obtain more data at once, the query parameter `depth` can be used (just like in the Jenkins API). 

This [query](http://demo-jabylon.rhcloud.com/api/Jabylon/master/de/org.jabylon.log.viewer/src/main/java/org/jabylon/log/viewer/pages/LogViewerPage_de.properties?depth=3) returns the first level properties for this file (statistic, locale, location,…) as well as the properties of its child objects (the actual key/value pairs and review information). Since it is sometimes easier to just download the properties file then parsing a JSON output, the query parameter `type=file` will instead allow you to [query](http://demo-jabylon.rhcloud.com/api/Jabylon/master/de/org.jabylon.log.viewer/src/main/java/org/jabylon/log/viewer/pages/LogViewerPage_de.properties?type=file) the properties file directly.
Currently the `?type=file` parameter will only work for single property files, i.e. not on locales, projects, or branches, although that might be a useful addition for the future.


## Uploading Property Files

You can also upload new property files through the API. To do so, execute a **PUT** request on the path where you want the properties file to be stored:

	/api/testproject/master/en_EN/some/path/Messages.properties

If the resource already exists, it will be replaced with the uploaded one, otherwise the resource will be created. However, this will only create property files. Project, branch and version cannot be created by a PUT (yet) and need to exist prior to the call.