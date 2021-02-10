:: By: Travis Dowd
:: Date: 2-10-2021
::
:: Create jar file, and create C:\Current.txt, and run game
@ECHO OFF
copy \src\resources\Current.txt C:\Current.txt
javac src\*.java
jar cfe CheckYourself.jar src.Main src\*.class src\resources\*
java -jar CheckYourself.jar 
