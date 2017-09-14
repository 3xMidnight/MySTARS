@echo off

javac -cp ".;.\lib\javax.mail.jar;.\lib\opencsv-3.9.jar;" -d build @source.txt
