### Docker启动2个MYSQL实例

在centos中创建如下两个目录

```
mysql1
    - conf
        - my.cnf
    - data(文件夹)
    - start.sh


mysql2
    - conf
        - my.cnf  
    - data(文件夹)
    - start.sh    
``` 

**my.cnf 文件内容**
```sql
    [mysqld]
    character-set-server=utf8mb4
    
    [client]
    default-character-set=utf8mb4
    
    [mysql]

```

**mysql1目``录下的start.sh文件内容**
```sql
#!/bin/bash
cur_dir=`pwd`
docker stop mysql1
docker rm mysql1
docker run --name mysql1 -v ${cur_dir}/conf:/etc/mysql/conf.d -v ${cur_dir}/data:/var/lib/mysql -p 3307:3306 -e MYSQL_ROOT_PASSWORD=123456 -e MYSQL_DATABASE=evan_user   -d mysql:latest

```


**mysql1目录下的start.sh文件内容**

```sql
#!/bin/bash
cur_dir=`pwd`
docker stop mysql2
docker rm mysql2
docker run --name mysql2 -v ${cur_dir}/conf:/etc/mysql/conf.d -v ${cur_dir}/data:/var/lib/mysql -p 3308:3306 -e MYSQL_ROOT_PASSWORD=123456 -e MYSQL_DATABASE=evan_order   -d mysql:latest
```



**启动命令(到各自目录下执行):**
>  sh start.sh



**在使用sqlYog, navicat等可视化工具链接docker上的mysql（IP形式连接）时， 出现这个问题**

> Client dose not support authentication protocol requested by server...

这时候使用下列命令 
```javascript 1.8
    docker exec -it mysql1 bash （进入mysql1 的容器）
    mysql -uroot -p123456 (进入mysql 操作命令界面)
    ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY '123456';
    FLUSH PRIVILEGES;
    sh start.sh （回到centos 环境， 重启mysql 容器）
```

在做对start.sh进行了一些修改之后， 再重启之后， 可能会出现 
>  ERROR 1045 (28000): Access denied for user 'root'@'xxxx' (using password: YES)

出现这种情况的时候， 将data目录删除之后再重启
