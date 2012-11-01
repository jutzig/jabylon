#!/bin/sh


pidfile=jabylon.pid

VMARGS="-XX:MaxPermSize=100m -Xms40m -Xmx256m -Declipse.ignoreApp=true -Dosgi.noShutdown=true"
#SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

DATA="workspace"
PORT="8080"
HOST="0.0.0.0"

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
    VMARGS="${VMARGS} -Dorg.eclipse.equinox.http.jetty.http.host=${HOST}";
    VMARGS="${VMARGS} -Dorg.eclipse.equinox.http.jetty.http.port=${PORT}";
    VMARGS="${VMARGS} -Dosgi.instance.area=${DATA}"       	
	PROGRAM="java ${VMARGS} -jar plugins/org.eclipse.equinox.launcher-1.2.0.jar"
	nohup ${PROGRAM} > wrapper.log 2>&1 &
	PID=$!
	echo $PID > "$pidfile"
	echo "Jabylon running at port ${PORT} (PID=${PID})"
}


while [ "$1" != "" ]; do
    case $1 in
        -d | --data )           shift
                                DATA=$1
                                ;;
        -h | --host )    		shift
        						HOST=$1
                                ;;
        -p | --port )           shift
        						PORT=$1
                                ;;
        start )					start
                                ;;
        stop )	pid2kill=`cat $pidfile`
				echo "stopping Jabylon"
				kill -9 $pid2kill
				#wait doesn't work if the process is not a child process...
				while [ -e /proc/$pid2kill ]; do sleep 0.1; done
				echo "Jabylon stopped"
				rm $pidfile
                                ;;
        console )           	
				VMARGS="${VMARGS} -Dosgi.console=true -Declipse.consoleLog=true"
			    echo "Starting Jabylon at port ${PORT}"
			    VMARGS="${VMARGS} -Dorg.eclipse.equinox.http.jetty.http.host=${HOST}";
    			VMARGS="${VMARGS} -Dorg.eclipse.equinox.http.jetty.http.port=${PORT}";
				PROGRAM="java ${VMARGS} -jar plugins/org.eclipse.equinox.launcher-1.2.0.jar"
				$PROGRAM
                                ;;
        * )                     usage
                                exit 1                                
    esac
    shift
done

