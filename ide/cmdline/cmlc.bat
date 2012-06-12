@ECHO OFF
REM
REM Author: Rasmus Lauritsen
REM Created: 2012-06-12
REM 
REM Run the CML Checker tool
REM
set BASE=%0\..
set REPO=%BASE%\..\..
set JAR=%BASE%\target\commandlinetool-*-jar-with-dependencies.jar
set CLASSPATH=.;%REPO%\core\parser\target\parser-*.jar;%REPO%\core\ast\target\ast-*.jar

IF NOT EXIST %JAR% GOTO ERR_NOJAR

java -cp %CLASSPATH% -jar %JAR% %*
GOTO END


:ERR_NOJAR
echo Executable jar: %JAR% does not exists, run mvn package in directory ide\cmdline to build it.

:END