
# Global Permissions

Global permissions are applied to individual roles. Roles can then be assigned to specific users to let them inherit all permissions defined in the role.

To configure the roles access the settings and select the _Security_ section.

![Security Settings](images/securitySettings.png)

To configure roles you must first select the _Roles_ tab (1)
![Roles Overview](images/rolesTab.png)

You should now see an overview of the existing roles. You can _edit_ and _delete_ (2) existing roles or create a new one with the _Add new_ button (3).
Please note that _Administrator_ and _Anonymous_ are system roles and you should not delete them. You can edit _Anonymous_ though to configure which permissions users should have when they are not logged in. 

When editing a role, you will be able to add and remove permissions from the role.

![Role Permission Selection](images/rolePermissions.png) 

You can use the arrows in the center or double click to add/remove a certain permission.

The permissions are based on a naming scheme. The `*` symbol serves as a wildcard.

    ObjectKind:[scope]:action
    
So e.g. `Project:Jabylon:edit` would allow users with this role to _edit_ the project _Jabylon_. A permission `Project:*:view` would allow users with this role to gain _view_ access to all projects. The permission `*` grants all access rights.

