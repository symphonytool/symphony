#!/bin/bash
#Who am I ?
#I am Who.
red=31
green=32
yellow=33
blue=34
width=$(expr $(tput cols) - 27 )
function colortxt()
{
    color=$1
    txt=$2
    echo -e "\e[1;${color}m${txt}\e[0m"
};

if [[ "build" == "$1" ]]; then

#
# Perform bison generation
#
`cat README.bison`
if [[ ! "$?" == "0" ]]; then
echo -n -e "$(colortxt ${red} 'Bison Failed to build the parser')";
echo "";
exit -1;
fi

#
# Build with Maven
#
mvn install
if [[ ! "$?" == "0" ]]; then
echo -n -e "$(colortxt  ${red} 'Maven build failed')";
echo "";
exit -1;
fi
fi

#
# Okay lets run the tests
#
 for test in $(ls -1 ../../docs/cml-examples/); do 
     txt=$(echo -n ${test});
     notabs=$(expr ${width} - ${#txt} );
     str=$(printf "%-${notabs}s" " ");
     tabstr=$(echo -n -e "${str// / }\t");
     ./run.sh ${test} 1>/dev/null  2>/dev/null;
     retcode=$?;
     if [ "${retcode}" == "0" ]; 
     then 
	 echo -n -e "$(colortxt ${green} ${txt}) ${tabstr}";
	 echo $(colortxt ${green} "[ok]"); 
     elif [ "${retcode}" == "255" ]; then
	 echo -n -e "$(colortxt ${yellow} ${txt}) ${tabstr}";
	 echo $(colortxt ${yellow} "[not parsing]");
     elif [ "${retcode}" == "254" ]; then
	 echo -n -e "$(colortxt ${yellow} ${txt}) ${tabstr}";
	 echo $(colortxt ${blue} "[file not found]");
     elif [ "${retcode}" == "253" ]; then
	 echo -n -e "$(colortxt ${red} ${txt}) ${tabstr}";
	 echo $(colortxt ${red} "[IO Error]");
     elif [ "${retcode}" == "252" ]; then
	 echo -n -e "$(colortxt ${red} ${txt}) ${tabstr}";
	 echo $(colortxt ${red} "[Unexpected error]"); 
     else
	 echo -n -e "$(colortxt ${red} ${txt}) ${tabstr}";
	 echo -e "[Unknown return code ${retcode}]";
     fi
     
done