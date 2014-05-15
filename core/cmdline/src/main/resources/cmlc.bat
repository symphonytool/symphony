@ECHO OFF
REM
REM Author: Rasmus Lauritsen, Joey Coleman
REM Created: 2012-06-12
REM 
REM Run the CML Checker tool
REM
set BASE=%~dp0
set VERSION=${project.version}
set JAR=%BASE%\cmlc-%VERSION%.jar

IF NOT EXIST %JAR% GOTO ERR_NOJAR

java -jar %JAR% %*

GOTO END

:ERR_NOJAR
echo Executable jar: %JAR% does not exist.
echo Please ensure it is in the same directory as the cmlc.bat file.

:END
