#!/bin/sh


pidfile=/tmp/jabylon.pid

VMARGS="-XX:MaxPermSize=256m -Xms40m -Xmx768m -Dorg.eclipse.equinox.http.jetty.http.port=8080 -Declipse.ignoreApp -Dosgi.noShutdown"


if [ $# -gt 0 ]; then
	if [ $1 = start ]; then
		echo "Starting Jabylon"
		PROGRAM="java $VMARGS -jar plugins/org.eclipse.equinox.launcher_1.2.0.v20110502.jar"
		nohup $PROGRAM > wrapper.log 2>&1 &
		PID=$!
		echo $PID > "$pidfile"
		echo "Jabylon running as $PID"
	fi
	if [ $1 = stop ]; then
		pid2kill=`cat $pidfile`
		kill -9 $pid2kill
		echo "shut down Jabylon"
		rm $pidfile
	fi	  
else
    echo "Usage: jabylon [start|stop]"
fi