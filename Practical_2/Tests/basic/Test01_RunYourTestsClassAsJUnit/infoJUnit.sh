#!/bin/bash
JUNITPATH=$TESTDIR

java -cp $TESTDIR/junit.jar:$TESTDIR/hamcrest.jar:. org.junit.runner.JUnitCore uk.ac.standrews.cs.cs2001.w03.test.Tests 
