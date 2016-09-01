#!/bin/bash
# shell script to build project
# example : source ./ads.initTmpDirectory.sh appId
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

scp -r root@120.25.237.22:/__bobofaceAdsCompile__/adsTools/$appId/* /__bobofaceAds__/adsTools/$appId

echo "==下载系统conf成功=="

echo "==下载工具脚本成功=="

scp -r root@120.25.237.22:/__bobofaceAdsCompile__/$appId/$appName/outer/* /__bobofaceAds__/$appId

echo "==下载项目代码成功=="

