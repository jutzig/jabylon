
# User Management

To access the user management open the settings  and select the _Security_ section.

![Security Settings](images/securitySettings.png)

You should now see an overview of the existing users and their currently assigned roles (1). You can _edit_ and _delete_ (2) existing users or create a new one with the _Add new_ button (3).
Please note that _Administrator_ and _Anonymous_ are system users and you should not delete them.

![Users Overview](images/usersOverview.png)

## Changing User Data

The amount of data you can edit on this page depends on the login type(1) of the user. E.g. if the user login type is _LDAP_, then you cannot modify username or password and the display name and email address are usually synchronized with _LDAP_ automatically. If the login type is _internal DB_ you can modify all data.

![User Configuration](images/userConfig.png)

To define access rights, switch to the _Roles_ tab(2) to assign roles to the user.

The image in the upper right corner (3) is retrieved from the [Gravatar](https://gravatar.com/) by using a hash of the supplied email address of the user.

You can also _generate_ a new [API](./jsonAPI.html) token or _delete_ an existing one by pressing the respective button (4). Please note that you have to click the _Submit_ button in order to persist the new token.
API tokens can be used as an alternative login means (instead of providing username and password). If a user logs in by API token he gets the same permissions as through a regular login.

 



