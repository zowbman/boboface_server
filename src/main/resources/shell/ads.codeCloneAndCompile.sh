#!/bin/bash
# shell script to build project
# example : source ./ads.codeCloneAndCompile.sh appId appName gitPath branch|tag
# version 1.0
# create by zowbman@hotmail 2016/8/24

# $1 appId
# $2 appName
# $3 gitPath
# $4 targetCode

appId=$1
appName=$2
gitPath=$3
targetCode=$4

# 1. create compile folder

mkdir -p /__bobofaceAdsCompile__/$appId

# 2. clone git repertory

cd /__bobofaceAdsCompile__/$appId

rm -rf $appName

git clone $gitPath || exit $?

# 3. checkout branch or tag

cd $appName

git checkout $targetCode

# 4. gradle build project

/usr/bin/dos2unix dcrun.sh make.sh run.sh

/usr/bin/sh make.sh