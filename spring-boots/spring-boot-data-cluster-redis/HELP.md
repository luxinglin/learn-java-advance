### Redis集群

实例节点配置

```bash
#redis.conf
port 7000
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 5000
appendonly yes
daemon yes

#instance folder
mkdir cluster-cluster
cd cluster-cluster
mkdir 7000 7001 7002 7003 7004 7005

#start instances
cd 7000
../redis-server ./redis.conf
```

通过redis-cli -c create 命令创建集群

```bash
[root@itam-server-dev redis-cluster]# redis-cli --cluster create 127.0.0.1:7000 127.0.0.1:7001 \
> 127.0.0.1:7002 127.0.0.1:7003 127.0.0.1:7004 127.0.0.1:7005 \
> --cluster-replicas 1
>>> Performing hash slots allocation on 6 nodes...
Master[0] -> Slots 0 - 5460
Master[1] -> Slots 5461 - 10922
Master[2] -> Slots 10923 - 16383
Adding replica 127.0.0.1:7004 to 127.0.0.1:7000
Adding replica 127.0.0.1:7005 to 127.0.0.1:7001
Adding replica 127.0.0.1:7003 to 127.0.0.1:7002
>>> Trying to optimize slaves allocation for anti-affinity
[WARNING] Some slaves are in the same host as their master
M: 40ad362c5740a0fade177b2e7438206f4d6c8913 127.0.0.1:7000
   slots:[0-5460] (5461 slots) master
M: 02c12390f0803038d9c5238e124c901042bdde6f 127.0.0.1:7001
   slots:[5461-10922] (5462 slots) master
M: d81122d408898ae9530398fc320995ad6cd6ea04 127.0.0.1:7002
   slots:[10923-16383] (5461 slots) master
S: 708d2098accf5bc42e537337cc920369bb11f1f2 127.0.0.1:7003
   replicates 40ad362c5740a0fade177b2e7438206f4d6c8913
S: acf8f1e7467a939c7d8697e0add5e2b97876004c 127.0.0.1:7004
   replicates 02c12390f0803038d9c5238e124c901042bdde6f
S: a961b2d2566bda0e51e7b64d3476bd49e9ee6bb3 127.0.0.1:7005
   replicates d81122d408898ae9530398fc320995ad6cd6ea04
Can I set the above configuration? (type 'yes' to accept): yes

>>> Nodes configuration updated
>>> Assign a different config epoch to each node
>>> Sending CLUSTER MEET messages to join the cluster
Waiting for the cluster to join
.
>>> Performing Cluster Check (using node 127.0.0.1:7000)
M: 40ad362c5740a0fade177b2e7438206f4d6c8913 127.0.0.1:7000
   slots:[0-5460] (5461 slots) master
   1 additional replica(s)
S: a961b2d2566bda0e51e7b64d3476bd49e9ee6bb3 127.0.0.1:7005
   slots: (0 slots) slave
   replicates d81122d408898ae9530398fc320995ad6cd6ea04
M: d81122d408898ae9530398fc320995ad6cd6ea04 127.0.0.1:7002
   slots:[10923-16383] (5461 slots) master
   1 additional replica(s)
M: 02c12390f0803038d9c5238e124c901042bdde6f 127.0.0.1:7001
   slots:[5461-10922] (5462 slots) master
   1 additional replica(s)
S: acf8f1e7467a939c7d8697e0add5e2b97876004c 127.0.0.1:7004
   slots: (0 slots) slave
   replicates 02c12390f0803038d9c5238e124c901042bdde6f
S: 708d2098accf5bc42e537337cc920369bb11f1f2 127.0.0.1:7003
   slots: (0 slots) slave
   replicates 40ad362c5740a0fade177b2e7438206f4d6c8913
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.
```

集群测试

```bash
#连接测试
[root@itam-server-dev redis-cluster]# redis-cli -c -p 7000
127.0.0.1:7000> ping
PONG
127.0.0.1:7000> get name
-> Redirected to slot [5798] located at 127.0.0.1:7004
(nil)
127.0.0.1:7004> set name luxinglin
OK
127.0.0.1:7004> get name
"luxinglin"
127.0.0.1:7004> 

#集群状态查看
[root@itam-server-dev redis-cluster]# redis-cli --cluster check 127.0.0.1:7000
127.0.0.1:7000 (40ad362c...) -> 0 keys | 5461 slots | 1 slaves.
127.0.0.1:7004 (acf8f1e7...) -> 1 keys | 5462 slots | 1 slaves.
127.0.0.1:7005 (a961b2d2...) -> 0 keys | 5461 slots | 1 slaves.
[OK] 1 keys in 3 masters.
0.00 keys per slot on average.
>>> Performing Cluster Check (using node 127.0.0.1:7000)
M: 40ad362c5740a0fade177b2e7438206f4d6c8913 127.0.0.1:7000
   slots:[0-5460] (5461 slots) master
   1 additional replica(s)
M: acf8f1e7467a939c7d8697e0add5e2b97876004c 127.0.0.1:7004
   slots:[5461-10922] (5462 slots) master
   1 additional replica(s)
M: a961b2d2566bda0e51e7b64d3476bd49e9ee6bb3 127.0.0.1:7005
   slots:[10923-16383] (5461 slots) master
   1 additional replica(s)
S: d81122d408898ae9530398fc320995ad6cd6ea04 127.0.0.1:7002
   slots: (0 slots) slave
   replicates a961b2d2566bda0e51e7b64d3476bd49e9ee6bb3
S: 708d2098accf5bc42e537337cc920369bb11f1f2 127.0.0.1:7003
   slots: (0 slots) slave
   replicates 40ad362c5740a0fade177b2e7438206f4d6c8913
S: 02c12390f0803038d9c5238e124c901042bdde6f 127.0.0.1:7001
   slots: (0 slots) slave
   replicates acf8f1e7467a939c7d8697e0add5e2b97876004c
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.
```