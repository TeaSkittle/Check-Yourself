#!/bin/bash
# Create jar file, and create /tmp/Current.txt

echo "1" > /tmp/Current.txt
jar cfe CheckYourself.jar src.Main src/*.class src/resources/*
echo "To run: java -jar CheckYourself.jar"
