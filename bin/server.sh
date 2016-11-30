#!/bin/sh

CODEBASE="../classes/"
SERVER_CLASS="hello.server.Server"

cd $CODEBASE || exit 1
pkill rmiregistry
rmiregistry &
sleep 0.5
java -cp . -Djava.rmi.server.codebase=file:./ $SERVER_CLASS
