升级操作步骤

1、先使用 zhaojp 账号登录进行
cd /opt/app/jetty-9.4.11/webapps/

2、停止 jetty 服务器
../bin/jetty.sh stop && rm -rf manage.war && rm -rf ../work/*

3、上传 manager.war
rz

4、重启服务器
../bin/jetty.sh restart