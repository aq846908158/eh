/*
Navicat MySQL Data Transfer

Source Server         : my
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : eh

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-02-27 18:57:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(32) NOT NULL,
  `userPassword` char(32) NOT NULL,
  `salt` char(32) NOT NULL,
  `trueName` varchar(4) NOT NULL,
  `phone` char(11) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `lastTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `title` varchar(30) DEFAULT NULL,
  `loginState` int(11) DEFAULT NULL,
  `loginNum` int(11) DEFAULT NULL,
  `loginLastTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('6', 'admin', '09676D10745E97D2D2D0708BE5349DC3', '*KFBO#O6X1I/TJ480V/X8S&AOPRRFXB/', '伍锐保', '18269652102', '1056042624@qq.com', '2018-01-30 15:33:47', '2018-01-30 15:47:35', '', '0', '0', '2018-01-30 15:44:08');
INSERT INTO `admin` VALUES ('7', 'admin_wu', '42CBF4697C955318C45CE028CBD2016A', 'MI0~&9Z%866W/Z9R6NJRRTDEVEKL.HUL', '伍锐保', '18648857502', '1056042624@qq.com', '2018-01-30 15:53:35', '2018-02-22 17:22:11', '测试', '0', '23', '2018-02-12 16:38:46');

-- ----------------------------
-- Table structure for adminpermissions
-- ----------------------------
DROP TABLE IF EXISTS `adminpermissions`;
CREATE TABLE `adminpermissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admin` int(11) NOT NULL,
  `low` bit(1) NOT NULL,
  `height` bit(1) NOT NULL,
  `middle` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `admin_adminPermissions` (`admin`),
  CONSTRAINT `admin_adminPermissions` FOREIGN KEY (`admin`) REFERENCES `admin` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of adminpermissions
-- ----------------------------
INSERT INTO `adminpermissions` VALUES ('4', '6', '', '', '\0');
INSERT INTO `adminpermissions` VALUES ('5', '7', '', '', '');

-- ----------------------------
-- Table structure for favorites
-- ----------------------------
DROP TABLE IF EXISTS `favorites`;
CREATE TABLE `favorites` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) NOT NULL,
  `product` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_favorites` (`user`),
  KEY `product_favorites` (`product`),
  CONSTRAINT `product_favorites` FOREIGN KEY (`product`) REFERENCES `product` (`id`),
  CONSTRAINT `user_favorites` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of favorites
-- ----------------------------

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(255) NOT NULL,
  `user` int(11) NOT NULL,
  `product` int(11) NOT NULL,
  `code` char(6) DEFAULT NULL,
  `superCode` char(6) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_message` (`user`),
  KEY `product_message` (`product`),
  CONSTRAINT `product_message` FOREIGN KEY (`product`) REFERENCES `product` (`id`),
  CONSTRAINT `user_message` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderCode` char(20) NOT NULL,
  `number` int(11) NOT NULL,
  `sellUser` int(11) NOT NULL,
  `buyUser` int(11) NOT NULL,
  `orderPrice` float NOT NULL,
  `chalkUp` bit(1) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `message` varchar(50) DEFAULT NULL,
  `orderState` varchar(255) DEFAULT NULL,
  `product` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sellUser_order` (`sellUser`),
  KEY `buyUser_order` (`buyUser`),
  KEY `FK_rnou8y85sf3r5j64ihavenm4r` (`product`),
  CONSTRAINT `FK_rnou8y85sf3r5j64ihavenm4r` FOREIGN KEY (`product`) REFERENCES `product` (`id`),
  CONSTRAINT `buyUser_order` FOREIGN KEY (`buyUser`) REFERENCES `user` (`id`),
  CONSTRAINT `sellUser_order` FOREIGN KEY (`sellUser`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES ('2', '20170314646', '1', '3', '1', '92.08', '\0', '2017-12-05 13:10:44', '发圆通', null, null);

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productName` varchar(30) NOT NULL,
  `productNumber` int(11) NOT NULL,
  `productType` int(11) NOT NULL,
  `productPrice` float(10,0) NOT NULL,
  `school` int(11) NOT NULL,
  `productIntroduce` text NOT NULL,
  `degree` int(11) NOT NULL,
  `grounding` bit(1) NOT NULL,
  `buyTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `expire` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `user` int(11) NOT NULL,
  `seeNumber` int(10) unsigned zerofill DEFAULT NULL,
  `criateTime` datetime DEFAULT NULL,
  `state` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `productType_product` (`productType`),
  KEY `school_product` (`school`),
  KEY `user_product` (`user`),
  CONSTRAINT `productType_product` FOREIGN KEY (`productType`) REFERENCES `producttype` (`id`),
  CONSTRAINT `school_product` FOREIGN KEY (`school`) REFERENCES `school` (`id`),
  CONSTRAINT `user_product` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('1', 'iphone x', '1', '66', '700', '2', '史上最强Apple 产品', '99', '', '2018-02-27 16:07:31', '2018-02-27 16:07:31', '4', '0000000000', '2018-02-18 12:12:29', '');
INSERT INTO `product` VALUES ('2', 'iphone 6s', '2', '66', '700', '1', '史上最强Apple 产品', '99', '', '2018-02-27 16:07:40', '2018-02-27 16:07:40', '4', '0000000000', '2018-02-18 12:40:27', '\0');
INSERT INTO `product` VALUES ('3', '锤子pro', '1', '66', '1200', '1', '测试测试测试', '95', '', '2018-02-27 16:07:45', '2018-02-27 16:07:45', '4', '0000000000', '2018-02-18 12:43:09', '');
INSERT INTO `product` VALUES ('4', 'iphone xx', '1', '66', '222', '1', '2', '99', '', '2018-02-27 16:07:50', '2018-02-27 16:07:50', '4', '0000000000', '2018-02-18 12:47:41', '');
INSERT INTO `product` VALUES ('5', '詹姆斯哈登一代', '1', '1', '599', '1', '大胡子~~', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-18 17:16:25', '\0');
INSERT INTO `product` VALUES ('6', '凯里欧文 4代', '1', '16', '200', '1', 'eeeeee', '99', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-19 00:07:20', '\0');
INSERT INTO `product` VALUES ('7', '吹风机', '2', '52', '65', '1', '过年前买的，现在不用了，卖出去、', '99', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-22 17:36:16', '\0');
INSERT INTO `product` VALUES ('9', '测试测试', '1', '9', '299', '1', 'xxxx', '99', '', '2018-02-27 12:13:56', '2018-02-27 12:13:56', '4', '0000000002', '2018-02-22 19:58:26', '\0');
INSERT INTO `product` VALUES ('10', '测试测试', '1', '9', '299', '1', 'xxxx', '99', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-22 19:59:20', '\0');
INSERT INTO `product` VALUES ('11', 'weadas', '1', '1', '231', '1', '321321', '95', '', '2018-02-27 12:14:29', '2018-02-27 12:14:29', '4', '0000000213', '2018-02-22 20:00:25', '');
INSERT INTO `product` VALUES ('13', 'weadas', '1', '1', '231', '1', '321321', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-22 20:01:26', '');
INSERT INTO `product` VALUES ('14', 'weadas', '1', '1', '231', '1', '321321', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-22 20:02:23', '');
INSERT INTO `product` VALUES ('15', 'weewqe2', '1', '1', '233', '2', '22', '99', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-22 20:06:12', '');
INSERT INTO `product` VALUES ('16', 'sad', '1', '1', '223', '1', '11', '99', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-22 20:09:21', '');
INSERT INTO `product` VALUES ('17', 'sad', '1', '1', '223', '1', '11', '99', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-22 20:11:24', '');
INSERT INTO `product` VALUES ('18', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-27 12:14:32', '2018-02-27 12:14:32', '4', '0000000023', '2018-02-25 17:14:31', '\0');
INSERT INTO `product` VALUES ('19', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:37', '\0');
INSERT INTO `product` VALUES ('20', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-27 12:14:34', '2018-02-27 12:14:34', '4', '0000000014', '2018-02-25 17:14:38', '\0');
INSERT INTO `product` VALUES ('21', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:39', '\0');
INSERT INTO `product` VALUES ('22', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-27 12:14:38', '2018-02-27 12:14:38', '4', '0000000065', '2018-02-25 17:14:40', '\0');
INSERT INTO `product` VALUES ('23', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:40', '\0');
INSERT INTO `product` VALUES ('24', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:40', '\0');
INSERT INTO `product` VALUES ('25', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:40', '\0');
INSERT INTO `product` VALUES ('26', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:40', '\0');
INSERT INTO `product` VALUES ('27', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:41', '\0');
INSERT INTO `product` VALUES ('28', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:41', '\0');
INSERT INTO `product` VALUES ('29', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-27 12:14:45', '2018-02-27 12:14:45', '4', '0000000063', '2018-02-25 17:14:41', '\0');
INSERT INTO `product` VALUES ('30', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:41', '\0');
INSERT INTO `product` VALUES ('31', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:41', '\0');
INSERT INTO `product` VALUES ('32', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:41', '\0');
INSERT INTO `product` VALUES ('33', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:42', '\0');
INSERT INTO `product` VALUES ('34', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-27 12:13:35', '2018-02-27 12:13:35', '4', '0000000056', '2018-02-25 17:14:42', '\0');
INSERT INTO `product` VALUES ('35', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:42', '\0');
INSERT INTO `product` VALUES ('36', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:42', '\0');
INSERT INTO `product` VALUES ('37', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:42', '\0');
INSERT INTO `product` VALUES ('38', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:42', '\0');
INSERT INTO `product` VALUES ('39', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:43', '\0');
INSERT INTO `product` VALUES ('40', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:43', '\0');
INSERT INTO `product` VALUES ('41', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:43', '\0');
INSERT INTO `product` VALUES ('42', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:43', '\0');
INSERT INTO `product` VALUES ('43', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:44', '\0');
INSERT INTO `product` VALUES ('44', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:44', '\0');
INSERT INTO `product` VALUES ('45', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:44', '\0');
INSERT INTO `product` VALUES ('46', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:44', '\0');
INSERT INTO `product` VALUES ('47', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:45', '\0');
INSERT INTO `product` VALUES ('48', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:45', '\0');
INSERT INTO `product` VALUES ('49', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:45', '\0');
INSERT INTO `product` VALUES ('50', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:45', '\0');
INSERT INTO `product` VALUES ('51', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-27 12:14:51', '2018-02-27 12:14:51', '4', '0000000032', '2018-02-25 17:14:45', '\0');
INSERT INTO `product` VALUES ('52', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:46', '\0');
INSERT INTO `product` VALUES ('53', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:46', '\0');
INSERT INTO `product` VALUES ('54', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:46', '\0');
INSERT INTO `product` VALUES ('55', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-27 12:14:53', '2018-02-27 12:14:53', '4', '0000000015', '2018-02-25 17:14:46', '\0');
INSERT INTO `product` VALUES ('56', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:46', '\0');
INSERT INTO `product` VALUES ('57', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:46', '\0');
INSERT INTO `product` VALUES ('58', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:47', '\0');
INSERT INTO `product` VALUES ('59', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:47', '\0');
INSERT INTO `product` VALUES ('60', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:47', '\0');
INSERT INTO `product` VALUES ('61', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:47', '\0');
INSERT INTO `product` VALUES ('62', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:47', '\0');
INSERT INTO `product` VALUES ('63', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:47', '\0');
INSERT INTO `product` VALUES ('64', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:48', '');
INSERT INTO `product` VALUES ('65', '阿迪达斯 海洋之心', '1', '33', '1299', '1', '阿迪达斯联名 海洋之心！！！！', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 17:14:48', '\0');
INSERT INTO `product` VALUES ('66', '余文乐同款卫衣', '1', '6', '466', '1', '惺惺惜惺惺想寻寻寻寻寻寻寻寻寻寻寻过过，交接就好看护工就给一个看，屁屁\r\nJOJO', '99', '', '2018-02-27 12:06:59', '2018-02-27 12:06:59', '4', '0000000099', '2018-02-25 20:16:50', '\0');
INSERT INTO `product` VALUES ('67', '周冬雨同款羽绒服', '1', '26', '466', '1', '惺惺惜惺惺想寻寻寻寻寻寻寻寻寻寻寻过过，交接就好看护工就给一个看，屁屁\r\nJOJO', '99', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 20:17:29', '\0');
INSERT INTO `product` VALUES ('68', '爱马仕30寸旅行箱', '1', '46', '466', '1', '惺惺惜惺惺想寻寻寻寻寻寻寻寻寻寻寻过过，交接就好看护工就给一个看，屁屁\r\nJOJO', '99', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 20:18:15', '\0');
INSERT INTO `product` VALUES ('69', '美的 豆浆机', '1', '54', '120', '1', '惺惺惜惺惺想寻寻寻寻寻寻寻寻寻寻寻过过，交接就好看护工就给一个看，屁屁\r\nJOJO', '99', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 20:18:56', '\0');
INSERT INTO `product` VALUES ('70', 'Apple 平板电脑', '1', '59', '2300', '1', '惺惺惜惺惺想寻寻寻寻寻寻寻寻寻寻寻过过，交接就好看护工就给一个看，屁屁\r\nJOJO', '99', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 20:19:28', '\0');
INSERT INTO `product` VALUES ('71', 'oppo X10', '1', '71', '1800', '1', '惺惺惜惺惺想寻寻寻寻寻寻寻寻寻寻寻过过，交接就好看护工就给一个看，屁屁\r\nJOJO', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 20:20:22', '\0');
INSERT INTO `product` VALUES ('72', '雅诗兰黛 经典版口红', '1', '86', '800', '1', '惺惺惜惺惺想寻寻寻寻寻寻寻寻寻寻寻过过，交接就好看护工就给一个看，屁屁\r\nJOJO', '95', '', '2018-02-27 11:11:00', '2018-02-27 11:11:00', '4', '0000000040', '2018-02-25 20:21:02', '\0');
INSERT INTO `product` VALUES ('73', '西门子夜光手表', '1', '82', '1600', '1', '惺惺惜惺惺想寻寻寻寻寻寻寻寻寻寻寻过过，交接就好看护工就给一个看，屁屁\r\nJOJO', '95', '', '2018-02-27 11:10:57', '2018-02-27 11:10:57', '4', '0000000020', '2018-02-25 20:21:46', '\0');
INSERT INTO `product` VALUES ('74', '绿源防辐射眼镜', '1', '83', '600', '1', '惺惺惜惺惺想寻寻寻寻寻寻寻寻寻寻寻过过，交接就好看护工就给一个看，屁屁\r\nJOJO', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 20:22:13', '\0');
INSERT INTO `product` VALUES ('75', 'nike 正版篮球', '1', '87', '1200', '1', '惺惺惜惺惺想寻寻寻寻寻寻寻寻寻寻寻过过，交接就好看护工就给一个看，屁屁\r\nJOJO', '95', '', '2018-02-25 20:44:44', '2018-02-25 20:44:44', '4', '0000000000', '2018-02-25 20:22:38', '\0');
INSERT INTO `product` VALUES ('76', '槐木家具一套', '1', '88', '1200', '1', '惺惺惜惺惺想寻寻寻寻寻寻寻寻寻寻寻过过，交接就好看护工就给一个看，屁屁\r\nJOJO', '95', '', '2018-02-25 20:47:01', '2018-02-25 20:47:01', '4', '0000000000', '2018-02-25 20:23:03', '\0');
INSERT INTO `product` VALUES ('77', 'iphone 5s', '1', '66', '500', '1', 'aiyaobuy', '9', '', '2018-02-05 00:00:00', null, '4', '0000000000', '2018-02-25 22:45:40', '\0');

-- ----------------------------
-- Table structure for productimg
-- ----------------------------
DROP TABLE IF EXISTS `productimg`;
CREATE TABLE `productimg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product` int(11) NOT NULL,
  `imgUrl` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `product_productImg` (`product`),
  CONSTRAINT `product_productImg` FOREIGN KEY (`product`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of productimg
-- ----------------------------
INSERT INTO `productimg` VALUES ('1', '1', '/eh_admin/producImage_temporary/4889615.jpeg');
INSERT INTO `productimg` VALUES ('2', '1', '/eh_admin/producImage_temporary/12099.jpeg');
INSERT INTO `productimg` VALUES ('3', '1', '/eh_admin/producImage_temporary/9050211.jpeg');
INSERT INTO `productimg` VALUES ('4', '2', '/eh_admin/producImage_temporary/4889615.jpeg');
INSERT INTO `productimg` VALUES ('5', '2', '/eh_admin/producImage_temporary/12099.jpeg');
INSERT INTO `productimg` VALUES ('6', '2', '/eh_admin/producImage_temporary/9050211.jpeg');
INSERT INTO `productimg` VALUES ('7', '3', '/eh_admin/producImage_temporary/2030727.jpeg');
INSERT INTO `productimg` VALUES ('8', '3', '/eh_admin/producImage_temporary/7710545.jpeg');
INSERT INTO `productimg` VALUES ('9', '3', '/eh_admin/producImage_temporary/4457207.jpeg');
INSERT INTO `productimg` VALUES ('10', '3', '/eh_admin/producImage_temporary/1887816.jpeg');
INSERT INTO `productimg` VALUES ('11', '3', '/eh_admin/producImage_temporary/1089554.jpeg');
INSERT INTO `productimg` VALUES ('12', '4', '/eh_admin/producImage_temporary/2805608.jpeg');
INSERT INTO `productimg` VALUES ('13', '5', '/eh_admin/producImage_temporary/1461573.jpeg');
INSERT INTO `productimg` VALUES ('14', '5', '/eh_admin/producImage_temporary/9961026.jpeg');
INSERT INTO `productimg` VALUES ('15', '5', '/eh_admin/producImage_temporary/2221771.jpeg');
INSERT INTO `productimg` VALUES ('16', '5', '/eh_admin/producImage_temporary/9306650.jpeg');
INSERT INTO `productimg` VALUES ('17', '5', '/eh_admin/producImage_temporary/7478586.jpeg');
INSERT INTO `productimg` VALUES ('18', '6', '/eh_admin/producImage_temporary/4275386.jpeg');
INSERT INTO `productimg` VALUES ('19', '6', '/eh_admin/producImage_temporary/332689.jpeg');
INSERT INTO `productimg` VALUES ('20', '6', '/eh_admin/producImage_temporary/9606197.jpeg');
INSERT INTO `productimg` VALUES ('21', '6', '/eh_admin/producImage_temporary/4148067.jpeg');
INSERT INTO `productimg` VALUES ('22', '7', '/eh_admin/producImage_temporary/6540717.jpeg');
INSERT INTO `productimg` VALUES ('23', '7', '/eh_admin/producImage_temporary/9452953.jpeg');
INSERT INTO `productimg` VALUES ('25', '9', '/eh_admin/producImage_temporary/5140260.jpeg');
INSERT INTO `productimg` VALUES ('26', '10', '/eh_admin/producImage_temporary/5140260.jpeg');
INSERT INTO `productimg` VALUES ('27', '11', '/eh_admin/producImage_temporary/6309390.jpeg');
INSERT INTO `productimg` VALUES ('29', '13', '/eh_admin/producImage_temporary/6309390.jpeg');
INSERT INTO `productimg` VALUES ('30', '14', '/eh_admin/producImage_temporary/6309390.jpeg');
INSERT INTO `productimg` VALUES ('31', '15', '/eh_admin/producImage_temporary/116909.jpeg');
INSERT INTO `productimg` VALUES ('32', '16', '/eh_admin/producImage_temporary/3923248.jpeg');
INSERT INTO `productimg` VALUES ('33', '17', '/eh_admin/producImage_temporary/3923248.jpeg');
INSERT INTO `productimg` VALUES ('34', '18', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('35', '19', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('36', '20', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('37', '21', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('38', '22', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('39', '23', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('40', '24', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('41', '25', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('42', '26', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('43', '27', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('44', '28', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('45', '29', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('46', '30', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('47', '31', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('48', '32', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('49', '33', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('50', '34', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('51', '35', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('52', '36', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('53', '37', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('54', '38', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('55', '39', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('56', '40', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('57', '41', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('58', '42', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('59', '43', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('60', '44', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('61', '45', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('62', '46', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('63', '47', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('64', '48', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('65', '49', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('66', '50', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('67', '51', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('68', '52', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('69', '53', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('70', '54', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('71', '55', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('72', '56', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('73', '57', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('74', '58', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('75', '59', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('76', '60', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('77', '61', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('78', '62', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('79', '63', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('80', '64', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('81', '65', '/eh_admin/producImage_temporary/8203591.png');
INSERT INTO `productimg` VALUES ('82', '66', '/eh_admin/producImage_temporary/8354402.png');
INSERT INTO `productimg` VALUES ('83', '67', '/eh_admin/producImage_temporary/8354402.png');
INSERT INTO `productimg` VALUES ('84', '68', '/eh_admin/producImage_temporary/8354402.png');
INSERT INTO `productimg` VALUES ('85', '69', '/eh_admin/producImage_temporary/8354402.png');
INSERT INTO `productimg` VALUES ('86', '70', '/eh_admin/producImage_temporary/8354402.png');
INSERT INTO `productimg` VALUES ('87', '71', '/eh_admin/producImage_temporary/8354402.png');
INSERT INTO `productimg` VALUES ('88', '72', '/eh_admin/producImage_temporary/8354402.png');
INSERT INTO `productimg` VALUES ('89', '73', '/eh_admin/producImage_temporary/8354402.png');
INSERT INTO `productimg` VALUES ('90', '74', '/eh_admin/producImage_temporary/8354402.png');
INSERT INTO `productimg` VALUES ('91', '75', '/eh_admin/producImage_temporary/8354402.png');
INSERT INTO `productimg` VALUES ('92', '76', '/eh_admin/producImage_temporary/8354402.png');
INSERT INTO `productimg` VALUES ('93', '77', '/eh_admin/producImage_temporary/3297291.png');

-- ----------------------------
-- Table structure for producttype
-- ----------------------------
DROP TABLE IF EXISTS `producttype`;
CREATE TABLE `producttype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productTypeName` varchar(10) NOT NULL,
  `productTypeRank` int(11) NOT NULL,
  `productTypeCode` char(6) NOT NULL,
  `superType` char(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of producttype
-- ----------------------------
INSERT INTO `producttype` VALUES ('1', '男装', '1', '562228', '0');
INSERT INTO `producttype` VALUES ('2', 'T恤', '2', '179605', '562228');
INSERT INTO `producttype` VALUES ('3', '衬衫', '2', '530972', '562228');
INSERT INTO `producttype` VALUES ('4', '夹克', '2', '132292', '562228');
INSERT INTO `producttype` VALUES ('6', '卫衣', '2', '235074', '562228');
INSERT INTO `producttype` VALUES ('7', '皮衣', '2', '943244', '562228');
INSERT INTO `producttype` VALUES ('8', '休闲裤', '2', '228493', '562228');
INSERT INTO `producttype` VALUES ('9', '牛仔裤', '2', '738985', '562228');
INSERT INTO `producttype` VALUES ('10', '沙滩裤', '2', '245935', '562228');
INSERT INTO `producttype` VALUES ('11', '运动裤', '2', '132834', '562228');
INSERT INTO `producttype` VALUES ('12', '九分裤', '2', '401414', '562228');
INSERT INTO `producttype` VALUES ('13', '棒球服', '2', '178163', '562228');
INSERT INTO `producttype` VALUES ('14', '风衣', '2', '425525', '562228');
INSERT INTO `producttype` VALUES ('15', '针织衫', '2', '931243', '562228');
INSERT INTO `producttype` VALUES ('16', '运动外套', '2', '171953', '562228');
INSERT INTO `producttype` VALUES ('17', '呢大衣', '2', '543588', '562228');
INSERT INTO `producttype` VALUES ('18', '西装', '2', '364508', '562228');
INSERT INTO `producttype` VALUES ('19', '马甲', '2', '419100', '562228');
INSERT INTO `producttype` VALUES ('20', '毛衣', '2', '394308', '562228');
INSERT INTO `producttype` VALUES ('21', '羽绒服', '2', '557771', '562228');
INSERT INTO `producttype` VALUES ('22', '女装', '1', '245428', '0');
INSERT INTO `producttype` VALUES ('23', '羽绒服', '2', '294884', '245428');
INSERT INTO `producttype` VALUES ('24', '毛衣', '2', '820268', '245428');
INSERT INTO `producttype` VALUES ('26', '羊毛绒', '3', '254665', '294884');
INSERT INTO `producttype` VALUES ('27', '鸭毛绒', '3', '316185', '294884');
INSERT INTO `producttype` VALUES ('28', '鞋靴', '1', '736794', '0');
INSERT INTO `producttype` VALUES ('29', '女鞋', '2', '536925', '736794');
INSERT INTO `producttype` VALUES ('30', '男鞋', '2', '302825', '736794');
INSERT INTO `producttype` VALUES ('31', '长靴', '2', '735252', '736794');
INSERT INTO `producttype` VALUES ('32', '短靴', '2', '102443', '736794');
INSERT INTO `producttype` VALUES ('33', '运动鞋', '2', '750900', '736794');
INSERT INTO `producttype` VALUES ('34', '乐福鞋', '2', '821056', '736794');
INSERT INTO `producttype` VALUES ('35', '高帮鞋', '2', '608852', '736794');
INSERT INTO `producttype` VALUES ('36', '皮鞋', '2', '947458', '736794');
INSERT INTO `producttype` VALUES ('37', '休闲鞋', '2', '517987', '736794');
INSERT INTO `producttype` VALUES ('38', '箱包', '1', '171916', '0');
INSERT INTO `producttype` VALUES ('39', '双肩包', '2', '631406', '171916');
INSERT INTO `producttype` VALUES ('40', '旅行包', '2', '183739', '171916');
INSERT INTO `producttype` VALUES ('41', '钱包', '2', '724126', '171916');
INSERT INTO `producttype` VALUES ('42', '迷你包', '2', '461297', '171916');
INSERT INTO `producttype` VALUES ('43', '手提包', '2', '711123', '171916');
INSERT INTO `producttype` VALUES ('44', '单肩包', '2', '306403', '171916');
INSERT INTO `producttype` VALUES ('45', '斜跨包', '2', '825457', '171916');
INSERT INTO `producttype` VALUES ('46', '拉杆箱', '2', '495773', '171916');
INSERT INTO `producttype` VALUES ('47', '家电', '1', '830004', '0');
INSERT INTO `producttype` VALUES ('48', '生活电器', '2', '417419', '830004');
INSERT INTO `producttype` VALUES ('49', '厨房电器', '2', '819811', '830004');
INSERT INTO `producttype` VALUES ('50', '吸尘器', '3', '718650', '417419');
INSERT INTO `producttype` VALUES ('51', '取暖机', '3', '497186', '417419');
INSERT INTO `producttype` VALUES ('52', '电吹风', '3', '705396', '417419');
INSERT INTO `producttype` VALUES ('53', '电热毯', '3', '644112', '417419');
INSERT INTO `producttype` VALUES ('54', '豆浆机', '3', '817253', '819811');
INSERT INTO `producttype` VALUES ('55', '烘烤箱', '3', '641496', '819811');
INSERT INTO `producttype` VALUES ('56', '电饭煲', '3', '427731', '819811');
INSERT INTO `producttype` VALUES ('57', '电磁炉', '3', '270999', '819811');
INSERT INTO `producttype` VALUES ('58', '数码', '1', '430141', '0');
INSERT INTO `producttype` VALUES ('59', '平板电脑', '2', '300943', '430141');
INSERT INTO `producttype` VALUES ('60', '单反相机', '2', '513316', '430141');
INSERT INTO `producttype` VALUES ('61', '无人机', '2', '685077', '430141');
INSERT INTO `producttype` VALUES ('62', '电脑主机', '2', '481470', '430141');
INSERT INTO `producttype` VALUES ('63', '笔记本', '2', '563041', '430141');
INSERT INTO `producttype` VALUES ('64', '数码相机', '2', '354086', '430141');
INSERT INTO `producttype` VALUES ('65', '手机', '1', '829485', '0');
INSERT INTO `producttype` VALUES ('66', 'iphone', '2', '859205', '829485');
INSERT INTO `producttype` VALUES ('67', '华为', '2', '481921', '829485');
INSERT INTO `producttype` VALUES ('68', '三星', '2', '126827', '829485');
INSERT INTO `producttype` VALUES ('69', '锤子', '2', '406100', '829485');
INSERT INTO `producttype` VALUES ('70', 'vivo', '2', '518876', '829485');
INSERT INTO `producttype` VALUES ('71', 'oppo', '2', '433771', '829485');
INSERT INTO `producttype` VALUES ('72', '小米', '2', '795033', '829485');
INSERT INTO `producttype` VALUES ('73', '魅族', '2', '634325', '829485');
INSERT INTO `producttype` VALUES ('74', '酷派', '2', '644614', '829485');
INSERT INTO `producttype` VALUES ('75', 'HTC', '2', '592323', '829485');
INSERT INTO `producttype` VALUES ('76', '索尼', '2', '298334', '829485');
INSERT INTO `producttype` VALUES ('77', '乐视', '2', '567498', '829485');
INSERT INTO `producttype` VALUES ('78', '360', '2', '878082', '829485');
INSERT INTO `producttype` VALUES ('79', '一加', '2', '752147', '829485');
INSERT INTO `producttype` VALUES ('80', '其他品牌', '2', '696300', '829485');
INSERT INTO `producttype` VALUES ('81', '美妆', '1', '917788', '0');
INSERT INTO `producttype` VALUES ('82', '手表', '1', '223719', '0');
INSERT INTO `producttype` VALUES ('83', '眼镜', '1', '241080', '0');
INSERT INTO `producttype` VALUES ('84', '运动', '1', '363694', '0');
INSERT INTO `producttype` VALUES ('86', '口红', '2', '619700', '917788');
INSERT INTO `producttype` VALUES ('87', '篮球', '2', '981458', '363694');
INSERT INTO `producttype` VALUES ('88', '家具', '1', '653648', '0');
INSERT INTO `producttype` VALUES ('89', '乐器', '1', '984058', '0');
INSERT INTO `producttype` VALUES ('90', '木吉他', '2', '100882', '984058');

-- ----------------------------
-- Table structure for school
-- ----------------------------
DROP TABLE IF EXISTS `school`;
CREATE TABLE `school` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `schoolName` varchar(35) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of school
-- ----------------------------
INSERT INTO `school` VALUES ('1', '柳州城职');
INSERT INTO `school` VALUES ('2', '柳州铁道');

-- ----------------------------
-- Table structure for shoppingcartitem
-- ----------------------------
DROP TABLE IF EXISTS `shoppingcartitem`;
CREATE TABLE `shoppingcartitem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) NOT NULL,
  `product` int(11) NOT NULL,
  `number` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_shoppingCartItem` (`user`),
  KEY `product_shoppingCartItem` (`product`),
  CONSTRAINT `product_shoppingCartItem` FOREIGN KEY (`product`) REFERENCES `product` (`id`),
  CONSTRAINT `user_shoppingCartItem` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shoppingcartitem
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` char(32) NOT NULL,
  `userPassword` char(32) NOT NULL,
  `salt` char(32) NOT NULL,
  `trueName` varchar(4) NOT NULL,
  `school` int(11) DEFAULT NULL,
  `sellNumber` int(11) NOT NULL,
  `forSaleNumber` int(11) NOT NULL,
  `phone` char(11) NOT NULL,
  `email` varchar(35) DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `lastTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `banLogin` bit(1) DEFAULT NULL,
  `banSell` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `school_user` (`school`),
  CONSTRAINT `school_user` FOREIGN KEY (`school`) REFERENCES `school` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'user', '123456', '312', 'hhh', '1', '10', '2', '18269652102', '3', '2017-12-05 21:57:55', '2017-12-05 21:57:55', '\0', '\0');
INSERT INTO `user` VALUES ('2', 'user1111', '8597D2D22983B61EA0C6E0B392B04801', '/X*AYMC0%ZL.~Q.NKEJ~QKNZQPMQR%3$', '哈哈浏览', '1', '17', '3', '13999999999', '599999@qq.com', '2017-12-05 22:02:32', '2017-12-05 22:02:32', '\0', '\0');
INSERT INTO `user` VALUES ('3', 'user2222', '58A8A3534160128C05DB01F9586832F7', 'X&M.1KRV.OCYU3#37R&B&W507O5UA-SW', '哈哈浏览', '1', '8', '4', '13999999999', '599999@qq.com', '2017-12-05 21:58:15', '2017-12-05 21:58:15', '\0', '\0');
INSERT INTO `user` VALUES ('4', 'wuruibao', '810E25C42C2140D412D2FA1646CA33D4', 'UT+MS@CAN5EIXC+-Q%Q/4XXDK07K&ZJF', '伍锐保', '1', '0', '0', '18269652102', '1056042624@qq.com', '2018-02-09 11:51:37', '2018-02-25 12:08:59', '\0', '\0');
