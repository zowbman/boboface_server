#!/bin/bash
# shell script to build project
# example : source ./ads.onlineOperation.sh appId appName
# version 1.0
# create by zowbman@hotmail 2016/8/26

# $1 appId

appId=$1
appName=$2

# 1. create tmp directory

echo "===上机操作==="

echo "==准备临时目录=="

ssh -p 22 root@120.25.237.22 mkdir -p /__bobofaceAds__/$appId /__bobofaceAds__/adsConfTpl/$appId /__bobofaceAds__/adsTools/$appId || exit $?

echo "==准备临时目录成功=="

echo ""

scp -r root@120.25.237.22:/__bobofaceAdsCompile__/adsTools/$appId/* /__bobofaceAds__/adsTools/$appId

echo "==下载工具脚本成功=="

echo ""

scp -r root@120.25.237.22:/__bobofaceAdsCompile__/$appId/$appName/outer/* /__bobofaceAds__/$appId

echo "==下载项目代码成功=="

echo ""

echo "==运行前置脚本=="

ssh -p 22 root@120.25.237.22 'cd /__bobofaceAds__/adsTools/'$appId'/ && . ./prefix.sh'

echo "==从临时目录同步文件到工作目录=="

ssh -p 22 root@120.25.237.22 '/usr/bin/rsync -rltgoDLP /__bobofaceAds__/'$appId'/ /usr/local/boboface.com1/boboface_server'

echo "==运行后置脚本=="

ssh -p 22 root@120.25.237.22 'cd /__bobofaceAds__/adsTools/'$appId'/ && . ./suffix.sh'



