#!/bin/sh
# template script to simplify the update process

#JABYLON_DOWNLOAD_LINK="https://github.com/jutzig/jabylon/releases/latest/download/jabylon.zip"
JABYLON_DOWNLOAD_LINK="https://build.seeburger.de/build/view/OpenSource/job/org.jabylon/lastSuccessfulBuild/artifact/releng/karaf/target/jabylon.zip"

realpath() {
  # Use in priority xpg4 awk or nawk on SunOS as standard awk is outdated
  AWK=awk
  if ${solaris}; then
      if [ -x /usr/xpg4/bin/awk ]; then
          AWK=/usr/xpg4/bin/awk
      elif [ -x /usr/bin/nawk ]; then
          AWK=/usr/bin/nawk
      fi
  fi

  READLINK_EXISTS=`command -v readlink &> /dev/null`
  BINARY_NAME=`basename "${1}"`
  if [ -z "$READLINK_EXISTS" ]; then
    OURPWD="`pwd`"
    cd "`dirname "${1}"`" || exit 2
    LINK=`ls -l "${BINARY_NAME}" | ${AWK} -F"-> " '{print $2}'`
    while [ "${LINK}" ]; do
        echo "link: ${LINK}" >&2
        cd "`dirname "${LINK}"`" || exit 2
        LINK=`ls -l "${BINARY_NAME}" | ${AWK} -F"-> " '{print $2}'`
    done
    REALPATH="`pwd`/${BINARY_NAME}"
    cd "${OURPWD}" || exit 2
    echo "${REALPATH}"
  else
    OURPWD="`pwd`"
    cd "`dirname "${1}"`" || exit 2
    LINK=`readlink "${BINARY_NAME}"`
    while [ "${LINK}" ]; do
        echo "link: ${LINK}" >&2
        cd "`dirname "${LINK}"`" || exit 2
        LINK=`readlink "${BINARY_NAME}"`
    done
    REALPATH="`pwd`/${BINARY_NAME}"
    cd "${OURPWD}" || exit 2
    echo "${REALPATH}"
  fi
}

REALNAME=`realpath "$0"`
DIRNAME=`dirname "${REALNAME}"`
PROGNAME=`basename "${REALNAME}"`

#
# Load common functions
#
. "${DIRNAME}/inc"

#
# Sourcing environment settings for karaf similar to tomcats setenv
#
if [ "x${KARAF_SCRIPT}" = "x" ]; then
    KARAF_SCRIPT="${PROGNAME}"
    export KARAF_SCRIPT
fi
if [ -f "${DIRNAME}/setenv" ]; then
  . "${DIRNAME}/setenv"
fi

init() {
    # KARAF-5332: Unset KARAF_DEBUG
    unset KARAF_DEBUG

    # Determine if there is special OS handling we must perform
    detectOS

    # Locate the Karaf home directory
    locateHome

}

check() {
	echo "Checking Jabylon Status"
	"${KARAF_HOME}/bin/karaf" status

    retVal=$?
    if [ $retVal -eq 0 ]
    then
      echo "Jabylon is still running, please stop it first"
      exit 1
    else
      echo "Jabylon is not running, continuing update"
    fi
}

run() {
    cd $KARAF_HOME
	echo "Cleaning up old files..."
	rm jabylon.zip
	rm -Rf bin
	rm -Rf data
	#leave deploy and etc for config and plugins
	#rm -Rf deploy
	#rm -Rf etc
	rm -Rf instances
	rm -Rf lib
	rm -Rf system
	echo "Downloading new release"
	wget $JABYLON_DOWNLOAD_LINK
	echo "Extracting update"
	unzip -o jabylon.zip -x "deploy/*"
	#echo "finishing up"
	#chmod +x bin/*
	echo "-----------------------------------------------"
	echo "-  Update complete, please restart the server -"
	echo "-----------------------------------------------"
}


main() {
	init
    check
    run "$@"
}

main "$@"
