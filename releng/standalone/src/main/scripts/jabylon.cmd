@echo off
set "JAVA_OPTS=-XX:MaxPermSize=160m -Xms40m -Xmx512m -Declipse.ignoreApp=true -Dosgi.noShutdown=true"

if not "%PORT%" == "" goto noPort
set PORT=8080
:noPort
set "JAVA_OPTS=%JAVA_OPTS% -Dorg.eclipse.equinox.http.jetty.http.port=%PORT%"

if not "%HOST%" == "" goto noHost
set HOST=0.0.0.0
:noHost
set "JAVA_OPTS=%JAVA_OPTS% -Dorg.eclipse.equinox.http.jetty.http.host=%HOST%"

if not "%DATA%" == "" goto noData
set DATA=workspace
:noData
set "JAVA_OPTS=%JAVA_OPTS% -Dosgi.instance.area=%DATA%"

set JAVA_EXEC=
if "%JAVA_HOME%" == "" goto noHome
set "JAVA_EXEC="%JAVA_HOME%"\bin\"
:noHome
set "JAVA_EXEC=%JAVA_EXEC%java"
%JAVA_EXEC% %JAVA_OPTS% -jar plugins/org.eclipse.equinox.launcher-1.3.0.jar
