#!/usr/bin/env bash

# --rm 启动时创建一个新的docker
docker run --name='activemq' -d --rm -p 61616:61616 -p 8161:8161 webcenter/activemq:latest

# 访问MQ地址：  http://192.168.50.131:8161
# 默认账号密码是: admin/admin

