#!/bin/bash

source rms.default.conf

chmod +x $installPath/run.sh
sudo -u $owner sh $installPath/run.sh restart

if [ $? -ne 0 ]
then
	echo -ne "sudo -u $owner /bin/bash run.sh restart\nFail"
	exit 1
fi
    
exit 0