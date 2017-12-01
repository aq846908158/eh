/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : eh

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2017-11-29 17:10:37
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
  `title` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of school
-- ----------------------------

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
  PRIMARY KEY (`id`),
  KEY `school_user` (`school`),
  CONSTRAINT `school_user` FOREIGN KEY (`school`) REFERENCES `school` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
