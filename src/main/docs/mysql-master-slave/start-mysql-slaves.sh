#!/bin/bash
docker stop mysql-slave1 || true
docker rm mysql-slave1 || true
docker run --name=mysql-slave1 --privileged=true --restart=always --network mysql-net -v /opt/mysql-cluster/slave1/data:/var/lib/mysql -v /opt/mysql-cluster/slave1.cnf:/etc/mysql/conf.d/slave.cnf -p 10001:3306 -e MYSQL_ROOT_PASSWORD=123456 -d -e TZ=Asia/Shanghai mysql:5.7 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci 

docker stop mysql-slave2 || true
docker rm mysql-slave2 || true
docker run --name=mysql-slave2 --privileged=true --restart=always --network mysql-net -v /opt/mysql-cluster/slave2/data:/var/lib/mysql -v /opt/mysql-cluster/slave2.cnf:/etc/mysql/conf.d/slave.cnf -p 10002:3306 -e MYSQL_ROOT_PASSWORD=123456 -d -e TZ=Asia/Shanghai mysql:5.7 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci

docker stop mysql-slave3 || true
docker rm mysql-slave3 || true
docker run --name=mysql-slave3 --privileged=true --restart=always --network mysql-net -v /opt/mysql-cluster/slave3/data:/var/lib/mysql -v /opt/mysql-cluster/slave3.cnf:/etc/mysql/conf.d/slave.cnf -p 10003:3306 -e MYSQL_ROOT_PASSWORD=123456 -d -e TZ=Asia/Shanghai mysql:5.7 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci 

