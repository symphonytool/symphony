#!/bin/bash
#
# This script is configured to build agains Eclipse 3.7.2 Update
# equinox.version and pde.version for if using another version of
# Eclipse to build.
#

function usage {
    echo "Usage: $(basename ${0}) <path to an eclipse 3.7.2 installation>"
}

if [ "" == "${1}" ];
 then
usage
exit 0
fi
mvn clean install -Declipse.installation="${1}"\
    -Dequinox.version="1.2.0.v20110502"\
    -Dpde.version="3.7.0.v20111116-2009"\
    -Dbaseos="win32"\
    -Dbasews="win32"\
    -Dbasearch="x86"