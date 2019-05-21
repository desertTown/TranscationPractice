drop table if exists `customer`;
create table customer(
  id bigint(20) not null auto_increment,
  user_name char(40) default '' comment '用户名',
  deposit int(11) ,
  PRIMARY KEY (id)
) CHARSET=utf8;


insert into customer(id,user_name,deposit) values (1,'Evan',2000);