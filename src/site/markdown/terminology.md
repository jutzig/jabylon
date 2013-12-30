
# Terminology

One of the automatic translation checks shipped with Jabylon is the terminology check. It checks if the template string contains one of the terms defined in the terminology project and if so, checks if the translated string contains the proper translation for that term.

This check is useful if you want to make sure that certain terms like e.g. _email_ or a company name are always translated uniformly into the target languages.

## Creating a new Terminology Project

The easiest way to start using terminology is by creating a new project and checking the _Terminology_ box before submitting.

![Create a Terminology Project](images/createTerminology.png)

This will not only mark the new project as the terminology project, but also seed the project with an essential terminology list for general software localisation in english, german, french, italian and spanish. These terminology files will be downloaded from [Pootle](http://pootle.locamotion.org/export/terminology/).

Of course you can also just mark any existing project as the terminology project. Just keep in mind that there can only be one active terminology project at a time and it should have only a single version with a single template file. Other than that, you can administer and translate it exactly like a normal project. 

## Working with Terminology

Once you got a terminology project set up, you can translate the terms into additional languages as required. You can also replace the seeded terminology with your own files by either replacing them on the filesystem or uploading replacement files throught the [JSON API](./jsonAPI.html).

Once the terminology is in place and the check is activated for a project, each modified translation will be checked against the terminology project.

![Terminology](images/terminology.png)  
