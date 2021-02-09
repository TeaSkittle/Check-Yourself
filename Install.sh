#!/bin/bash
# By: Travis Dowd
# Date: 2-9-2021
#
# Create jar file, and create /tmp/Current.txt, and run game

cat src/resources/Current.txt > /tmp/Current.txt
javac src/*.java
jar cfe CheckYourself.jar src.Main src/*.class src/resources/*
java -jar CheckYourself.jar 
