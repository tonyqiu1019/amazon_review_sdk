#!/bin/bash

cnt=0
for name in `ls lib/`; do
    cnt=$(( $cnt + 1 ))
    mvn install:install-file -Dfile=./lib/${name} -DgroupId=com.IRBase -DartifactId=IRBase${cnt} -Dversion=1.0 -Dpackaging=jar
done
