create database USER;
use USER;
CREATE TABLE `user_auth` (
                             `uid` bigint(20) unsigned NOT NULL,
                             `email` varchar(50) DEFAULT NULL,
                             `country_code` varchar(4) DEFAULT NULL,
                             `phone` varchar(100) DEFAULT NULL,
                             `password` varchar(100) DEFAULT NULL,
                             `create_time` bigint(20) DEFAULT NULL,
                             `update_time` bigint(20) DEFAULT NULL,
                             `deleted` tinyint(4) DEFAULT NULL,
                             `activated` tinyint(1) DEFAULT NULL,
                             PRIMARY KEY (`uid`),
                             UNIQUE KEY `idx_email` (`email`),
                             UNIQUE KEY `idx_code_phone` (`country_code`,`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

create database PROFILE;
use PROFILE;
CREATE TABLE `user_profile` (
                                `uid` bigint(11) unsigned NOT NULL,
                                `icon` varchar(200) NOT NULL,
                                `nickname` varchar(50) NOT NULL,
                                `activated` tinyint(1) NOT NULL,
                                `create_time` bigint(20) NOT NULL,
                                `update_time` bigint(20) NOT NULL,
                                `deleted` tinyint(1) DEFAULT NULL,
                                PRIMARY KEY (`uid`),
                                UNIQUE KEY `idx_nickname` (`nickname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

create database ADMIN;
use ADMIN;
CREATE TABLE `user_admin` (
                              `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                              `name` varchar(50) NOT NULL,
                              `password` varchar(100) NOT NULL,
                              `create_time` bigint(20) NOT NULL,
                              `update_time` bigint(20) NOT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO user_admin VALUES(1,"admin","20c98b51e8ddac0b0e32add2f207987027d5ez",1657078849643,1657078849643)