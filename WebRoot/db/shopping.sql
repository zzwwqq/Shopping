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
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;

/*Data for the table `items` */

insert  into `items`(`id`,`name`,`city`,`price`,`number`,`picture`) values (1,'高端智能平衡车','郑州',2354,2222,'高端智能平衡车.jpg'),(2,'宝生园马桶','石家庄',998,1666,'宝生园马桶699.jpg'),(3,'雅居汇沙发','太原',5639,235,'雅居汇沙发.jpg'),(4,'太空轮悬浮滑板车','贵阳',2988,258,'太空轮悬浮滑板车.jpg'),(5,'沙发','北京',3999,369,'沙发.jpg'),(6,'海信电视 49 英寸','成都',2999,199,'海信TV 49.jpg'),(7,'长虹电视 65 英寸','重庆',2569,789,'长虹TV65.jpg'),(8,'thinkpad笔记本','北京',6999,500,'008.jpg'),(9,'无手杆平衡车','武汉',2999,147,'无手杆平衡车.jpg'),(10,'纳蒂兰卡马桶','呼和浩特',2999,887,'纳蒂兰卡马桶 668.jpg'),(11,'华帝热水器','成都',8575,456,'华帝热水器1199.jpg'),(12,'Chuck Taylor AllStar70高帮复古 ','上海',569,256,'012.jpg'),(13,'康佳电冰箱','青岛',2592,3698,'康佳电冰箱.jpg'),(14,'威博热水器','杭州',2655,1989,'威博热水器.jpg'),(15,'沙发2','上海',9999,258,'沙发2.jpg'),(16,'One Star 低帮撞色板鞋 ','莆田',285,259,'016.jpg'),(17,'沃特篮球鞋','佛山',180,500,'001.jpg'),(18,'马桶','南京',2999,199,'马桶.jpg'),(19,'沙发3','青海',2999,199,'沙发3.jpg'),(20,'Chuck Taylor All Star70复古低帮','杭州',639,126,'020.jpg'),(21,'安踏运动鞋','福州',120,800,'002.jpg'),(22,'耐克运动鞋','广州',500,1000,'003.jpg'),(23,'小米2S','北京',1299,1000,'007.jpg'),(24,'Jack Purcell Pro 低帮开口笑','武汉',599,15,'024.jpg'),(25,'One Star Pro 3V 魔术贴设计','杭州',569,346,'021.jpg'),(26,'海信电视 40 英寸','成都',2999,568,'海信TV 40.jpg'),(27,'匡威 经典款休闲男女帆布鞋 低帮','莆田',339,759,'017.jpg'),(28,'Jack Purcell LP L/S纯色薄底开口笑','北京',499,96,'023.jpg'),(29,'Chuck TaylorAllStar70复古高帮','北京',432,229,'022.jpg'),(30,'阿迪达斯T血衫','上海',388,600,'004.jpg'),(31,'小米3','北京',1999,3000,'006.jpg'),(32,'智能扫地机器人','昆明',5999,136,'智能扫地机器人.jpg'),(33,'李宁文化衫','广州',180,900,'005.jpg'),(34,'ipad5','北京',5999,500,'010.jpg'),(35,'One Star 低帮撞色板鞋 ','上海',599,123,'011.jpg'),(36,'凯米蒂亚沙发','孝感',2369,269,'凯米蒂亚沙发.jpg'),(37,'Chuck Taylor All Star 70 低帮','上海',539,105,'013.jpg'),(38,'经典款 休闲男女帆布鞋 高帮','上海',359,562,'014.jpg'),(39,'Chuck Taylor All Star 70纯色低帮','上海',639,78,'015.jpg'),(40,'帕莎眼镜','西安',1955,555,'帕莎眼镜.jpg'),(41,'奥克斯空调大一匹','成都',2556,123,'奥克斯空调大一匹.jpg'),(42,'Chuck Taylor All Star \'70','莆田',405,506,'018.jpg'),(43,'Chuck Taylor All Star70复古低帮','莆田',669,223,'019.jpg'),(44,'dell笔记本','北京',3999,500,'009.jpg');

/*Table structure for table `t_cartitem` */

DROP TABLE IF EXISTS `t_cartitem`;

CREATE TABLE `t_cartitem` (
  `cartItemId` int(11) NOT NULL AUTO_INCREMENT COMMENT '购物车中每件商品的数量',
  `uid` varchar(32) NOT NULL COMMENT '用户id',
  `id` int(11) NOT NULL COMMENT '商品id',
  `itemQuantity` int(11) NOT NULL COMMENT '购物车中对应用户的商品数量',
  PRIMARY KEY (`cartItemId`)
) ENGINE=InnoDB AUTO_INCREMENT=997 DEFAULT CHARSET=utf8;

/*Data for the table `t_cartitem` */

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

insert  into `users`(`uid`,`loginname`,`loginpass`,`email`,`status`,`activationCode`,`telephone`,`passwordProtected`,`answer`) values ('BA88A628C54744D1A7235D49CA122960','zwq','123zwq','1107717335@qq.com',1,'F511903EDD1D46ADB6F28FB2C54D035CE379E752E6FB4AB3861D88B56C9813BA','13264860490','您最喜欢的偶像','JonyJ');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
