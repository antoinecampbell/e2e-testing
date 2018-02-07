#!/bin/sh

export JENKINS_HOME=$(pwd)/jenkins-home

java -Dhudson.model.DirectoryBrowserSupport.CSP="" \
    -jar jenkins.war --httpPort=8001