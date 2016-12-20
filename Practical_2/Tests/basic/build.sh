#!/bin/bash
JUNITPATH=$TESTDIR/Test01_RunYourTestsClassAsJUnit
for i in $(find . -name '*.java')
do
   javac -cp $JUNITPATH/junit.jar:$JUNITPATH/hamcrest.jar:. $i
done
