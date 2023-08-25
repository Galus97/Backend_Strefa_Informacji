#!/bin/bash
cd target
pkill -f "java -jar backend-0.0.1-SNAPSHOT.jar"
nohup java -jar backend-0.0.1-SNAPSHOT.jar &