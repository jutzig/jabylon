
# Security Settings

Jabylon comes with 2 pre-installed security modules. However, it is designed in an extensible fashion so you can provide additional [JAAS](http://en.wikipedia.org/wiki/Java_Authentication_and_Authorization_Service) login module plug-ins as required.

Which security modules are used in which order depends on the contents of `configuration/jaas.config`. You can add, delete, modify and reorder the modules there as you see fit.


## Database Login

The default login module is the internal database login. All users, their passwords, roles and additional data is stored in the database. You do not have to configure anything to use this module. 


## LDAP Authentication

The second pre-installed module enables LDAP authentication. On first login Jabylon creates a new user in the internal database that is linked to the LDAP account. The password will only be in LDAP (and the user cannot change it) but the roles and additional information (email, full name,...) are synced from LDAP into the internal database.

To enable LDAP authentication you need to enter a few parameters into `configuration/jaas.config`

 *  **ldap** the ldap server url
 * **ldap.port** the ldap server port
 * **root.dn** the root dn to query agains
 * **manager** the CN of the manager. Jabylon will use this account to access LDAP
 * **manager.password** the password of the manager. Jabylon will use this account to access LDAP
 * **user.id** the uid attribute of a user
 * **user.name** the full name attribute of a user (optional)
 * **user.mail** the email attribute of a user (optional)
 