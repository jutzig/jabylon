
# Configuring Versions

Versions represent different independent branches of your project. Typically a version maps to a specific branch in your source code repository, but that is not requirement.

If you have a team provider configured, then the name of the version holds a special significance, since that will be the name of the branch that the team provider will try to checkout from the source code repository.

You can create new versions or edit existing ones in the [project configuration](./workingWithProjects.html). Your project needs at least one version before you can start translating.


## Managing Languages

The _General_ tab allows you to modify the name of your version and manage the available locales. 

![General Tab](images/languageSelection.png "General Tab")

Jabylon will automatically determine the available target languages in your project version when you execute a scan or upload new translation files. 

However, you might decide to make your project available into additional locales at a later time. To do so, fill out the locale fields on the left and press the _Add_ button. Likewise, to remove a locale, select it in the list on the right and press the _Remove_ button.

Please note that neither _Add_ not _Remove_ manipulate any translation files. That means if you delete a locale, the translation files for that locale will not be deleted. If you add a new locale and manually execute a full scan on your project
version before adding at least one translation for the new target locale, the new locale will be removed again.  


## Automatic Update/Commit

If you have a team provider configured in your project, you will also see a tab _Jobs_ that allows you to configure automatic actions that should be executed according to a customizable schedule.

![Automatic Update/Commit](images/automaticUpdate.png) 

Jabylon allows you to automatically update your project versions from the source code repository and commit new translations into the repository. The activate this automatic synchronization you need  to enable the checkbox for _Automatic Update_ and/or _Automatic Commit_.

When this actions will be performed depends on the schedule you configured. The syntax for schedule expressions is based on [cron expressions](http://quartz-scheduler.org/documentation/quartz-2.1.x/tutorials/tutorial-lesson-06). 

The default schedule for automatic update is `0 0 * * * ?` which translates to **once every hour**. 

The default schedule for automatic commit is `0 5 * * * ?` which is very similar to the _update_ schedule and translates to **five minutes past the hour**, so five minutes after the update (if activated). On the right of the schedule expression you can see when the next execution of this job is scheduled.

## Git

If you configured the git team provider for your project you can adjust some advanced settings per version. 

The **refspec** can be adjusted if you want to push to a branch other than the remote branch. The access the name of the current branch insert the placeholder `{0}` into the refspec. It will be replaced with the actual name of the version when a push operation is performed.

The **rebase** setting is active by default. It causes Jabylon to attempt a rebase when necessary. Deactivating this setting will create merge commits instead.