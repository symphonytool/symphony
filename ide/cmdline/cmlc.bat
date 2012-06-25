@ECHO OFF
REM
REM Author: Rasmus Lauritsen
REM Created: 2012-06-12
REM 
REM Run the CML Checker tool
REM
set BASE=%~dp0
set REPO=%BASE%\..\..
set VERSION=0.0.1
set JAR=%BASE%\target\commandlinetool-%VERSION%-jar-with-dependencies.jar
set CLASSPATH=.;%REPO%\core\parser\target\parser-%VERSION%.jar;%REPO%\core\ast\target\ast-%VERSION%.jar;%JAR%

IF NOT EXIST %JAR% GOTO ERR_NOJAR

REM java -cp %CLASSPATH% -jar %JAR% %*
java -cp %CLASSPATH% eu.compassresearch.ide.CheckCml %*
GOTO END


:ERR_NOJAR
echo Executable jar: %JAR% does not exists, run mvn package in directory ide\cmdline to build it.

:END
