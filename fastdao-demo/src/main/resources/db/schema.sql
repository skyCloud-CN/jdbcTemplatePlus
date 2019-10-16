CREATE TABLE `auto_inc` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT '',
  `text` text ,
  `longTime` bigint(20) unsigned,
  `updated` timestamp DEFAULT CURRENT_TIMESTAMP,
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  `deleted` tinyint(4) NOT NULL,
  `notNeed` integer(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 ;

CREATE TABLE `non_auto_inc` (
  `id` bigint(20) unsigned NOT NULL ,
  `name` varchar(20) DEFAULT '',
  `text` text ,
  `longTime` bigint(20) unsigned,
  `updated` timestamp DEFAULT CURRENT_TIMESTAMP,
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  `deleted` tinyint(4) DEFAULT NOT NULL,
  `notNeed` integer(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB ;