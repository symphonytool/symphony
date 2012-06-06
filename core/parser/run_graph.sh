first=$1;
file=$1; 
if [ "${first}" == "-n" ]; then
    echo "parser it not compiled";
    file=$2;
else
    `cat README.bison`;
    mvn install;
fi

echo -n -e "\t\t\t\t";

./run.sh $file;
retcode=$?;
if [ "${retcode}" == "0" ]; 
then 
    dot -Tsvg generatedAST.gv > generatedAST.svg;
    eog generatedAST.svg;
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