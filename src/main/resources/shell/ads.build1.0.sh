#!/bin/bash
# shell script to build project
# example : source ./insJdk.sh appName gitPath branch|tag appFolder
# version 1.0
# create by zowbman@hotmail 2016/8/24

# $1 appName
# $2 gitPath
# $3 targetCode
# $4 appFolder

appName=$1
gitPath=$2
targetCode=$3
appFolder=$4

# 1. check compile app folder

if [[ ! -d "/compile/$appName" ]];
	then
	echo "no such file or directory"
	exit 1
fi

# 2. clone git repertory

cd /compile/$appName

rm -rf *

git clone $gitPath || exit $?

# 3. checkout branch or tag

cd $appName

git checkout targetCode

# 4. gradle build project

/usr/bin/dos2unix dcrun.sh make.sh run.sh

/usr/bin/sh make.sh

# 5. cp to app folder

#cp -r ./outer/* /usr/local/boboface.com/boboface_server/
