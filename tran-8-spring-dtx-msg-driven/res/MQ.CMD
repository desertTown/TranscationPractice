docker run --name='activemq' -d --rm -p 61616:61616 -p 8161:8161 webcenter/activemq:latest

# --rm  参数表示每次重启MQ， 都会把之前的数据删掉