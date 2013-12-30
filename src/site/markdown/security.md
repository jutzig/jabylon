
# Security

Jabylon comes with modular and fine grained authentication and authorization mechanisms. The following documents will describe how you can utilize them.


## System Users

There are two system users predefined. The user `Administrator` with the password `changeme` has the role `Administrator`. This role is predefined and has all permissions. The second user is called `Anonymous` and has no password and by default the single role `Anonymous`. A user that is not logged in is automatically considered `Anonymous`.

Initially the `Anonymous` role has permission to browse the projects, but not edit them. You can remove permissions or add additional ones as you see fit.