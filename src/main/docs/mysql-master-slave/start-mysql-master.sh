#!/bin/bash
docker stop mysql-master || true
docker rm mysql-master || true
docker run --name=mysql-master --privileged=true --restart=always --network mysql-net -v /opt/mysql-cluster/master/data:/var/lib/mysql -v /opt/mysql-cluster/master.cnf:/etc/mysql/conf.d/master.cnf --publish 10000:3306 -e MYSQL_ROOT_PASSWORD=123456 -d -e TZ=Asia/Shanghai mysql:5.7 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci 
