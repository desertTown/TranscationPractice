DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer`(
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` CHAR (20) NOT NULL DEFAULT '',
  `deposit` INT(11) NOT NULL DEFAULT 0,
  UNIQUE KEY `u_user_name` (`user_name`),
  PRIMARY KEY (`id`)
);

