#!/bin/bash

do_thing() {
	echo "$@ ... "
}


CODEBASE="$1"
SERVER_CLASS="chatroom.server.ChatServer"

cd $CODEBASE || exit 1

do_thing Killing existing rmiregistry
pkill rmiregistry

do_thing Starting new rmiregistry
rmiregistry &

do_thing Waiting for rmiregistry to startup
sleep 0.5

do_thing Starting server
java -cp . -Djava.rmi.server.codebase=file:./ $SERVER_CLASS

do_thing Shutting down
