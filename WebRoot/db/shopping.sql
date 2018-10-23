/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.7.21-log : Database - shopping
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`shopping` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `shopping`;

/*Table structure for table `items` */

DROP TABLE IF EXISTS `items`;

CREATE TABLE `items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `picture` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `items` */

insert  into `items`(`id`,`name`,`city`,`price`,`number`,`picture`) values (1,'沃特篮球鞋','佛山',180,500,'001.jpg'),(2,'安踏运动鞋','福州',120,800,'002.jpg'),(3,'耐克运动鞋','广州',500,1000,'003.jpg'),(4,'阿迪达斯T血衫','上海',388,600,'004.jpg'),(5,'李宁文化衫','广州',180,900,'005.jpg'),(6,'小米3','北京',1999,3000,'006.jpg'),(7,'小米2S','北京',1299,1000,'007.jpg'),(8,'thinkpad笔记本','北京',6999,500,'008.jpg'),(9,'dell笔记本','北京',3999,500,'009.jpg'),(10,'ipad5','北京',5999,500,'010.jpg');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `uid` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `loginname` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `loginpass` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `activationCode` char(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `telephone` varchar(11) COLLATE utf8_unicode_ci DEFAULT NULL,
  `passwordProtected` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `answer` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `users` */

insert  into `users`(`uid`,`loginname`,`loginpass`,`email`,`status`,`activationCode`,`telephone`,`passwordProtected`,`answer`) values ('5EAD009D318745DC8B6838AA6C8243AD','zwq','123zwq','1107717335@qq.com',1,'F62C344D0BED4C9E9A9E5A32BBCD87C5FA74B565482B402D85AEEBBCA9ED356F','13264860490','您最喜欢的偶像','JonyJ');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
