* **分布式事务实现模式-事件溯源模式**  
1.事件作为一等数据保存  
2.统一的事件管理器和接口，数据更新都由事件产生  
3.数据库中数据的当前状态根据事件的聚合产生  
4.聚合数据可以保存在数据库中、可以根据事件重新生成  

* **事件溯源的优点**  
1.历史重现：从事件中重新生成视图数据库  
2.方便的数据流处理与报告生成  
3.性能  
4.服务的松耦合  

* **事件溯源的缺点**  
1.只能保证事务的最终一致性  
2.设计和开发思维的转变、学习成本  
3.事件结构的改变  
4.扩展性：Event Store的分布式实现，事件的分布式处理  

* **事件溯源模式下保证数据的一致性**  
1.一个事件只处理一个服务的数据  
2.保证事件的至少一次处理、幂等性  
3.业务请求的错误处理：多次重试失败、网络异常、服务不可用  

* **事件溯源和CQRS**  
1.CQRS：命令查询职责分离  
2.C端执行命令，Q端执行查询

* **Axon框架介绍**  
1.实现Event Sourcing和CQRS模式的框架  
2.实现了命令、事件的分发、处理、聚合、查询、存储  
3.提供标签式开发，易维护，并提供了Spring Boot集成  
4.提供Command和Event  
5.可扩展，可用于分布式环境，如Spring Cloud  

* **Axon框架的构成**  
聚合：Aggregate  
聚合的资源库：Repository  
Command：Command Bus和Command Handler  
Event：Event Bus、Event Handler和Event Store  
Saga：基于事件的流程管理模式  
Query：执行数据查询操作的特殊Command  
可扩展性：  
1.分布式Command分发  
2.通过AMQP实现分布式Event分发和处理  

* **Axon Command处理过程**  
Resource收到请求，send给CommandGateway  
CommandGateway执行拦截器等，再发给CommandBus  
CommandBus创建一个UnitOfWork，关联一个事务，在其中调用CommandHandler处理这个Command  
CommandHandler使用Repository获得一个聚合对象，并聚合所有该对象的event，设置Lock，然后调用处理方法  
CommandHandler再触发一个event  

* **Axon Event处理过程**  
CommandHandler执行apply来触发一个event  
EventBus在这个event上执行拦截器等  
EventBus将这个event保存到EventStore  
EventBus调用在这个event上注册的所有处理方法（在UnitOfWord中执行）  
在EventHandler中更新聚合数据、保存视图数据库、触发其他Command  

* **Command和Event的区别**  
**Command**  
表示某种业务动作  
只被处理一次  
可以有返回值  
只做条件检查，触发相应Event去更新数据  
**Event**  
表示系统内发生的事件，某种业务状态的更新  
可以被多次处理  
没有返回值  
更新聚合数据并保存在Event Store中，用于重新生成聚合数据  

* **Axon简单实例**  
**账户管理**  
功能：账户创建、查看、转账  
**使用Axon框架的设计过程**  
领域模型：账户Account  
业务Command：创建账户、存款、取款  
事件Event：账户创建、存款、取款  
将账户信息保存到数据库中，方便查询  
查询Command：查询账户  



update commit comment