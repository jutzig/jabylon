/** 
 * add readOnly field to DB
 * https://github.com/jutzig/jabylon/issues/79
 */
ALTER TABLE PROJECTVERSION ADD "READONLY" bool DEFAULT FALSE;
