#!/bin/bash
`cat README.bison`
mvn install
 for test in $(ls -1 ../../docs/cml-examples/); do 
     echo -n ${test};
     echo -n -e "\t\t\t\t";
     ./run.sh ${test} 1>/dev/null  2>/dev/null;
     retcode=$?;
     if [ "${retcode}" == "0" ]; 
     then 
	 echo "[ok]"; 
     elif [ "${retcode}" == "255" ]; then
	 echo "[not parsing]";
     elif [ "${retcode}" == "254" ]; then
	 echo "[file not found]";
     elif [ "${retcode}" == "253" ]; then
	 echo "[IO Error]";
     elif [ "${retcode}" == "252" ]; then
	 echo "[Unexpected error]"; 
     else
	 echo -e "[What Bitches] ${retcode}";
     fi
     
done