/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : eh

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2017-12-03 16:08:42
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('2', 'admin_1', '249811E9EBEBAB67C8DB3851C259582C', 'N8UCL.FJY@KGYEP9PIE75.OJ-FKQ9LEB', '胖肥臀', null, '1056042624@qq.com', '2017-12-02 15:54:11', null, null, null, null);
INSERT INTO `admin` VALUES ('3', '123456', '04D15ADE20932FF77BC0D3F4C46F404D', 'IEL1QS+EZJ+16-%2O7EMNEYT$@%7Z25X', '欧阳大', null, 'qq@qq.com', '2017-12-02 16:00:05', null, null, null, null);
INSERT INTO `admin` VALUES ('4', 'admin_6', '0253724A73473C624FE1E24FAB921F9E', '7WJ37ZS/WR2~F2-F&UVFI#3G*L9N4%@2', '欧阳大', null, '10560426241056042@qq.com', '2017-12-02 16:01:01', null, null, null, null);
INSERT INTO `admin` VALUES ('5', 'admin_7', '7DB207D901FEC62C3F65D14A2A4D449A', 'N#E/H1L0#VEU0T&%YASRZQ$1@Q2VI.@P', '欧阳大', null, '10560426241056042112311@qq.com', '2017-12-02 16:02:21', null, null, null, null);
INSERT INTO `admin` VALUES ('6', 'admin', '1E337A9B7144B26909DBB539506CEF91', '*KFBO#O6X1I/TJ480V/X8S&AOPRRFXB/', '伍锐保', '18269652102', '1056042624@qq.com', '2017-12-02 16:08:26', null, null, null, null);
INSERT INTO `admin` VALUES ('7', 'admin_9', '9269882938D3B1CF67D97FD8A8247AA2', '&5GHR0TKEEFAPHI94FKK&TC*BN#756./', '神仙伍', null, '10775@sina.cn', '2017-12-03 16:07:50', null, null, null, null);

-- ----------------------------
-- Table structure for adminpermissions
-- ----------------------------
DROP TABLE IF EXISTS `adminpermissions`;
CREATE TABLE `adminpermissions` (
  `id` int(11) NOT NULL,
  `admin` int(11) NOT NULL,
  `low` bit(1) NOT NULL,
  `in` bit(1) NOT NULL,
  `height` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `admin_adminPermissions` (`admin`),
  CONSTRAINT `admin_adminPermissions` FOREIGN KEY (`admin`) REFERENCES `admin` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of adminpermissions
-- ----------------------------

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
  PRIMARY KEY (`id`),
  KEY `sellUser_order` (`sellUser`),
  KEY `buyUser_order` (`buyUser`),
  CONSTRAINT `buyUser_order` FOREIGN KEY (`buyUser`) REFERENCES `user` (`id`),
  CONSTRAINT `sellUser_order` FOREIGN KEY (`sellUser`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order
-- ----------------------------

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productName` varchar(30) NOT NULL,
  `productNumber` int(11) NOT NULL,
  `productType` int(11) NOT NULL,
  `productPrice` float NOT NULL,
  `school` int(11) DEFAULT NULL,
  `productIntroduce` text NOT NULL,
  `degree` int(11) DEFAULT NULL,
  `grounding` bit(1) NOT NULL,
  `buyTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `expire` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `user` int(11) NOT NULL,
  `seeNumber` int(10) unsigned zerofill DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `productType_product` (`productType`),
  KEY `school_product` (`school`),
  KEY `user_product` (`user`),
  CONSTRAINT `productType_product` FOREIGN KEY (`productType`) REFERENCES `producttype` (`id`),
  CONSTRAINT `school_product` FOREIGN KEY (`school`) REFERENCES `school` (`id`),
  CONSTRAINT `user_product` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of productimg
-- ----------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of producttype
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'user', '123456', '312', 'hhh', '1', '1', '2', '18269652102', '3', '2017-11-30 16:59:51', '2017-11-30 16:59:51', null, null);
