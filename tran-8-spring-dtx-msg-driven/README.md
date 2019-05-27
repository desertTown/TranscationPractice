1. 根据res文件夹中的文件分别启动mysql 容器和ActiveMQ容器
2. 执行db.script文件中的创建数据库语句 (建表语句自动由hibernate生成)
3. 启动各自服务
4. rest-api.http 文件调用接口