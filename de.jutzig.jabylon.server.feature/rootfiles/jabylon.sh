#!/bin/sh


pidfile=jabylon.pid

VMARGS="-XX:MaxPermSize=256m -Xms40m -Xmx768m -Dorg.eclipse.equinox.http.jetty.http.port=8080 -Declipse.ignoreApp=true -Dosgi.noShutdown=true -Dosgi.instance.area=$2"
#SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"


usage()
{
    echo "Usage: jabylon [start|stop|console] workspace"
}

start()
{
	if [ -f $pidfile ]; then 
		echo "Jabylon is already running"
		exit 1
	fi
       	echo "Starting Jabylon"
	PROGRAM="java ${VMARGS} -jar plugins/org.eclipse.equinox.launcher_1.2.0.v20110502.jar"
	nohup ${PROGRAM} > wrapper.log 2>&1 &
	PID=$!
	echo $PID > "$pidfile"
	echo "Jabylon running as $PID"
}

if [ $# -gt 0 ]; then	
    case $1 in
        start )			start
                                ;;
        stop )    		pid2kill=`cat $pidfile`
				kill -9 $pid2kill
				echo "shutting down Jabylon"
				rm $pidfile
                                ;;
        console )           	
				VMARGS="${VMARGS} -Dosgi.console=true -Declipse.consoleLog=true"
				echo "Vmargs: ${VMARGS}"
			       	echo "Starting Jabylon"
				PROGRAM="java ${VMARGS} -jar plugins/org.eclipse.equinox.launcher_1.2.0.v20110502.jar"
				echo ${PROGRAM}
				$PROGRAM
                                ;;
        * )                     usage
                                exit 1
    esac  
else
    usage
    exit 1	
fi
