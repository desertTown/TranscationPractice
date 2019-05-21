DROP TABLE IF EXISTS  `customer_order`;
CREATE TABLE `customer_order` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT,
 `customer_id` int(11) default null  COMMENT '用户',
 `title` char(20) default null comment '标题',
 `amount` int(11) default 0 comment '总额',
  PRIMARY KEY (`id`) USING BTREE
) comment '订单表';
