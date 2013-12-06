/** https://github.com/jutzig/jabylon/issues/137 */
update CDO_EXTERNAL_REFS set URI = replace(URI, 'http://jutzig.de/jabylon', 'http://uri.jabylon.org');
update CDO_PACKAGE_INFOS set URI = replace(URI, 'http://jutzig.de/jabylon', 'http://uri.jabylon.org');
update CDO_PACKAGE_INFOS set UNIT = replace(UNIT, 'http://jutzig.de/jabylon', 'http://uri.jabylon.org');
update CDO_PACKAGE_UNITS set ID = replace(ID, 'http://jutzig.de/jabylon', 'http://uri.jabylon.org');