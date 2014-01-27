/** 
 * add support for Android
 * https://github.com/jutzig/jabylon/issues/177
 * to enable plugins to contribute new property types, the enum
 * must be turned into a varchar
 */
ALTER TABLE PROJECT ALTER COLUMN PROPERTYTYPE VARCHAR;

UPDATE PROJECT SET PROPERTYTYPE = 'PROPERTIES_ENCODED' WHERE PROPERTYTYPE = '0';
UPDATE PROJECT SET PROPERTYTYPE = 'PROPERTIES_UTF8' WHERE PROPERTYTYPE = '1'; 