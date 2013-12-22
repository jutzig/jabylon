
# Translating

Once you have successfully created and populated your first project (see [working with projects](./workingWIthProjects.html)), you are able to browse through the translation relevant content.

When clicking on a project, you can first select which version you want to work on, and next which language you want to translate. If your target language is not listed here, click the _Configure_ button in the toolbar to create a new one.

![Configure](images/configure.png)


## Browsing

Once your desired language is selected, you can browse the folders and translation files.

![Browing a Project](images/browsing.png)

Depending on the icon, the link points to either a translatable file (1) or a folder that contains additional resources (2). The length of the progress bar (3) indicates your overall translation progress for that file/folder in the given language.

If parts of the progress bar are yellow (as can be seen in (4)) that means that some of the translations are considered _fuzzy_. That happens if e.g. the project uses automated translation checks and one or more translations fail one or more of the checks. You will be able to see exactly what is wrong with those translations once you open the files.

Clicking on the table headers will allow you to sort the contents ascending or descending on the given criteria. You can also use the table's filter box to filter out resources that you are currently not interested in.


## Overview

When clicking on a translatable file you will first see the overview page. This will show all available properties in the file. You will also find a _Download_ button at the bottom of the page that allows you the download the current translations.

![Overview](images/propertiesOverview.png "Overview") 

Each line (1) represents one key in the template language. On the right (2) you can see the status of the current translation. The green _OK_ means the key is translated and passed all checks. Yellow indicates a _fuzzy_ translation. This is usually the case if an automatic check failed for the translation.

The buttons on the top (3) allow you to filter which properties you are interested in. _All_ means that all properties are displayed. _Missing_ will hide all keys that have already been translated and _fuzzy_ will only show missing and _fuzzy_ properties (failed check).

Clicking the _edit_ button on any of the rows will bring you to the actual translation editor.


## Translation Editor

The translation editor is the center piece of Jabylon. This is where translators will spend most of their time. 

![Translation Editor](images/propertyEditor.png "Translation Editor") 

The central area (1) splits into the template part (left) and the translation part (right). The upper text area displays the translated/to-be-translated value and the lower text area shows any translator comments that might be associated with the value. Comments can be used for instance to give more context to the translator if the value alone isn't clear enough without seeing the translation.

On the bottom (2) is a progress bar to give you an idea of how many more keys are left in the resouce you are editing.

When you are satisfied with the translation click _Save & Next_ (3) to save your changes and advance to the next translation. Please note, for a faster workflow you can also use [keyboard shortcuts](#shortcuts "Keyboard_Shortcuts")  for this.

On the top you will find two more buttons (4) _Save_ and _Reset_. You can use those to revert your current change or to save without proceeding to the next property.



## Translation Checks

If your project is confiured to execute automatic translation checks and the current translation fails for one ore more of those, you can see this in the upper left corner. In the example of the screenshot, the template language was _'Search {0}'_. The _{0}_ is an indicator that the application will insert a value at runtime into the string. The translation check now validates if the translation contains the right amount (and numbering) of replacement variables. In this case, it does not because the _{0}_ is missing in the translation.

![Translation Checks](images/reviewExample.png)


## Translation Tools

Jabylon offers an extensible set of translation tools to support translators in creating good translations for the end user. The available tools can be seen at the bottom of the page.

![Translation Tools](images/similarTranslations.png "Translation Tools")

The tabs on the left side (1) allow you to switch between the different translation tools. You can also use [keyboard shortcuts](#shortcuts "Keyboard_Shortcuts")  to switch between the tabs. The translation tool that can be seen (2) in the screenshot offers a selection of alternatives on how similar strings have been translated within your projects so far. This can be very useful to get an idea on how other people translated certain terms/phrasings so far and to keep things consistent.


## <a id="shortcuts" name="shortcuts"></a>Keyboard Shortcuts

Since there is typically many translations in a software application, Jabylon is optimized for power users by providing keyboard shortcuts for the most common actions. The currently available shortcuts are:

* **CTRL** + **Right Arrow** = _Save & Previous_
* **CTRL** + **Right Arrow** = _Save & Previous_
* **CTRL** + **1** to **9** = open the respective translation tool tab 
 