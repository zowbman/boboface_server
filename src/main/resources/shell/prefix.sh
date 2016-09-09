#!/bin/bash

source ads.default.conf

time=$(/usr/bin/sudo -u $owner /bin/date +%Y%m%d%H%M)
/usr/bin/sudo -u $owner /bin/mkdir -p ${installPath}_${time}_${curVersion}

/usr/bin/sudo -u $owner /bin/rm -rf $installPath
/usr/bin/sudo -u $owner /bin/ln -s ${installPath}_${time}_${curVersion} $installPath

cd $installPath
cd ..

ls -1d * | grep -P '_[0-9]{12}_' |sort -ur |awk '{if(NR>5){print $0}}' | grep -v $time | xargs rm -rf

softpath=$(/bin/pwd)

if [ ! -d ${softpath}/work ]
then
  /usr/bin/sudo -u $owner /bin/mkdir -p ${softpath}/work
fi

/bin/ln -s ${softpath}/work ${installPath}/work
chown -R ${owner}.${owner} ${installPath}/work