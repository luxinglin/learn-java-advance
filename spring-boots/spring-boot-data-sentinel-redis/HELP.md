### 哨兵集群

> Redis Sentinel is the official high availability solution for Redis

准备Redis实例

```bash
#配置&启动实例
[root@itam-server-dev sentinel]# redis-server redis-6380.conf
[root@itam-server-dev sentinel]# redis-server redis-6381.conf
[root@itam-server-dev sentinel]# redis-server redis-6382.conf
```



创建sentinel

```bash
[root@itam-server-dev sentinel]# redis-sentinel sentinel-6380.conf
[root@itam-server-dev sentinel]# redis-sentinel sentinel-6381.conf
[root@itam-server-dev sentinel]# redis-sentinel sentinel-6382.conf

#连接测试  Master
[root@itam-server-dev sentinel]# redis-cli -p 6380
127.0.0.1:6380> info replication
# Replication
role:master
connected_slaves:2
slave0:ip=10.200.132.171,port=6381,state=online,offset=614956,lag=1
slave1:ip=10.200.132.171,port=6382,state=online,offset=614956,lag=0
master_replid:7ac75d3e54ebaaa4451309e014422b5694f63461
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:614956
second_repl_offset:-1
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:1
repl_backlog_histlen:614956
127.0.0.1:6380> 

#Slave
[root@itam-server-dev sentinel]# redis-cli -p 6382
127.0.0.1:6382> info replication
# Replication
role:slave
master_host:10.200.132.171
master_port:6380
master_link_status:up
master_last_io_seconds_ago:0
master_sync_in_progress:0
slave_repl_offset:622103
slave_priority:100
slave_read_only:1
connected_slaves:0
master_replid:7ac75d3e54ebaaa4451309e014422b5694f63461
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:622103
second_repl_offset:-1
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:1
repl_backlog_histlen:622103
127.0.0.1:6382> 

```