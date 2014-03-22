
# Plug-Ins

Jabylon Plug-Ins are managed through the [Update Center](./updateCenter.html). 
If you are interested in adding a plug-in of your own to the update center, please contact us through the [mailing list](./mail-lists.html). 

## CVS

The CVS plug-in contributes an additional team provider for Jabylon that enables you to connect to CVS repositories. 
 
## SVN
 
The SVN plug-in allows you to synchronize your translation projects with Subversion repositories.


## iOS

The iOS plug-in contributes two new project types to Jabylon. **iOS (UTF-8)** and **iOS (UTF-16)**. Typical iOS `*.strings` files are encoded in **UTF-16**, but if you prefer **UTF-8** use the **iOS (UTF-8)** type. iOS projects scan by default for `**/*.strings`.
 

## Android

The Android plug-in adds the new project type **Android**. By default, Android type projects will scan for `**/res/values/strings.xml`. Even though Android translation files are XML based, the translators can use the exact same web editor as with other projects.

Android XML has a few more abilities than just key/value mappings. Namely those are [String Array](http://developer.android.com/guide/topics/resources/string-resource.html#StringArray) and [Quantity Strings (Plurals(](http://developer.android.com/guide/topics/resources/string-resource.html#Plurals).


### String Array

For each entry in a String-Arrays the Jabylon web editor will create a new line for the entry. In the translation comment you will see `@string-array` to mark this translation as part of a String-Array in the XML.

### Plurals

Each quantity of a Plural should be put on a new line in the Jabylon web editor. The translation needs to start with the quantity and a parenthesis to seperate the quantity from the translation. In the editor this would look like so:

```
zero) No songs found.
one) One song found.
other) %d songs found.
``` 

This input would produce the following Android XML file:

```
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <plurals name="numberOfSongsAvailable">
    	<item quantity="zero">No songs found.</item>
        <item quantity="one">One song found.</item>
        <item quantity="other">%d songs found.</item>
    </plurals>
</resources>
```

If an Android Plural is translated, you will see `@plurals` in the translation comment.