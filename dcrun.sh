#!/bin/bash
JAVA=/usr/local/java/bin/java
JAVA_OPTS="-server -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintTenuringDistribution -Xloggc:$LOG_DIR/gc.log"
APP_JAVA_MAIN_CLASS=com.boboface.MainServer
APP_DIR=/app
CONF_DIR=/app/etc
CLASSPATH=$CONF_DIR


#从环境变量获取配置
get_conf()
{
	env |grep ^APP_ |awk -F\= '{print $1}' |while read key; do
		value=$(eval echo \$$key)
		value="$(echo $value |sed -e 's/#/\\#/g' -e 's#&#\\&#g')" #处理特殊字符
		sed -i "s#@${key}@#${value}#g" $(find $CONF_DIR -type f |xargs)
	done
}

#启动服务
app_start()
{
	export CLASSPATH="$CLASSPATH:$APP_DIR/lib/*"
	run_cmd="$JAVA $APP_JAVA_OPTS $APP_JAVA_MAIN_CLASS"
	$run_cmd
}

cd $APP_DIR
get_conf
app_start