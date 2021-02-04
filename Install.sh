#!/bin/bash
# Create jar file, and move Current.txt to /tmp

cp src/resources/Current.txt /tmp/Current.txt
jar cfve out.jar src.Main src/*.class src/resources/*
echo " "
echo "To run: java -jar out.jar"
