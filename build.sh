#!/bin/bash
echo  '清理资源'
rm -rf chat2rs-client/app/jar
rm -rf chat2rs-client/app/jre

echo '打包后端工程'
mvn clean  package -B '-Dmaven.test.skip=true' -f  chat2rs-server/pom.xml
echo '创建文件夹'
mkdir -p  chat2rs-client/app/jar
mkdir -p  chat2rs-client/app/jre

echo '移动jar包'
cp chat2rs-server/chat2rs-server-start/target/chat2research.jar  chat2rs-client/app/jar/

echo '解压'
tar -zxvf chat2rs-client/app/jre.zip -C  chat2rs-client/app/