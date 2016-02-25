CREATE DATABASE  IF NOT EXISTS `restaurant` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `restaurant`;
-- MySQL dump 10.13  Distrib 5.5.47, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: restaurant
-- ------------------------------------------------------
-- Server version	5.5.47-0ubuntu0.14.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ACTIVEMQ_ACKS`
--

DROP TABLE IF EXISTS `ACTIVEMQ_ACKS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACTIVEMQ_ACKS` (
  `CONTAINER` varchar(250) NOT NULL,
  `SUB_DEST` varchar(250) DEFAULT NULL,
  `CLIENT_ID` varchar(250) NOT NULL,
  `SUB_NAME` varchar(250) NOT NULL,
  `SELECTOR` varchar(250) DEFAULT NULL,
  `LAST_ACKED_ID` bigint(20) DEFAULT NULL,
  `PRIORITY` bigint(20) NOT NULL DEFAULT '5',
  PRIMARY KEY (`CONTAINER`,`CLIENT_ID`,`SUB_NAME`,`PRIORITY`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACTIVEMQ_ACKS`
--

LOCK TABLES `ACTIVEMQ_ACKS` WRITE;
/*!40000 ALTER TABLE `ACTIVEMQ_ACKS` DISABLE KEYS */;
/*!40000 ALTER TABLE `ACTIVEMQ_ACKS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ACTIVEMQ_LOCK`
--

DROP TABLE IF EXISTS `ACTIVEMQ_LOCK`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACTIVEMQ_LOCK` (
  `ID` bigint(20) NOT NULL,
  `TIME` bigint(20) DEFAULT NULL,
  `BROKER_NAME` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACTIVEMQ_LOCK`
--

LOCK TABLES `ACTIVEMQ_LOCK` WRITE;
/*!40000 ALTER TABLE `ACTIVEMQ_LOCK` DISABLE KEYS */;
INSERT INTO `ACTIVEMQ_LOCK` VALUES (1,NULL,NULL);
/*!40000 ALTER TABLE `ACTIVEMQ_LOCK` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ACTIVEMQ_MSGS`
--

DROP TABLE IF EXISTS `ACTIVEMQ_MSGS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACTIVEMQ_MSGS` (
  `ID` bigint(20) NOT NULL,
  `CONTAINER` varchar(250) DEFAULT NULL,
  `MSGID_PROD` varchar(250) DEFAULT NULL,
  `MSGID_SEQ` bigint(20) DEFAULT NULL,
  `EXPIRATION` bigint(20) DEFAULT NULL,
  `MSG` longblob,
  `PRIORITY` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ACTIVEMQ_MSGS_MIDX` (`MSGID_PROD`,`MSGID_SEQ`),
  KEY `ACTIVEMQ_MSGS_CIDX` (`CONTAINER`),
  KEY `ACTIVEMQ_MSGS_EIDX` (`EXPIRATION`),
  KEY `ACTIVEMQ_MSGS_PIDX` (`PRIORITY`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACTIVEMQ_MSGS`
--

LOCK TABLES `ACTIVEMQ_MSGS` WRITE;
/*!40000 ALTER TABLE `ACTIVEMQ_MSGS` DISABLE KEYS */;
/*!40000 ALTER TABLE `ACTIVEMQ_MSGS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ADDRESS`
--

DROP TABLE IF EXISTS `ADDRESS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ADDRESS` (
  `ADDRESS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ADDRESS_BRUL` varchar(5) NOT NULL,
  `STREET_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ADDRESS_ID`),
  UNIQUE KEY `U_ADDRESS_ADDRESS_ID` (`ADDRESS_ID`),
  KEY `I_ADDRESS_STREET` (`STREET_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ADDRESS`
--

LOCK TABLES `ADDRESS` WRITE;
/*!40000 ALTER TABLE `ADDRESS` DISABLE KEYS */;
INSERT INTO `ADDRESS` VALUES (1,'1',4),(2,'2',8),(3,'2',3),(4,'18',4),(5,'18',1),(6,'1',1),(7,'3',1),(8,'25',6),(9,'133',5),(10,'10',1),(11,'12',4),(12,'9',6),(13,'78c',1),(14,'111',4),(15,'28',5),(16,'11A',5),(17,'112',5),(18,'22',5),(19,'28',4),(20,'121',7),(21,'68',9);
/*!40000 ALTER TABLE `ADDRESS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CITY`
--

DROP TABLE IF EXISTS `CITY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CITY` (
  `CITY_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CITY_NAME` varchar(64) NOT NULL,
  `CNT_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`CITY_ID`),
  UNIQUE KEY `U_CITY_CITY_ID` (`CITY_ID`),
  UNIQUE KEY `U_CITY_CITY_NAME` (`CITY_NAME`),
  KEY `I_CITY_COUNTRY` (`CNT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CITY`
--

LOCK TABLES `CITY` WRITE;
/*!40000 ALTER TABLE `CITY` DISABLE KEYS */;
INSERT INTO `CITY` VALUES (1,'Beograd',1),(2,'Novi Sad',1);
/*!40000 ALTER TABLE `CITY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `COUNTRY`
--

DROP TABLE IF EXISTS `COUNTRY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `COUNTRY` (
  `CNT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CNT_NAME` varchar(64) NOT NULL,
  PRIMARY KEY (`CNT_ID`),
  UNIQUE KEY `U_COUNTRY_CNT_ID` (`CNT_ID`),
  UNIQUE KEY `U_COUNTRY_CNT_NAME` (`CNT_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `COUNTRY`
--

LOCK TABLES `COUNTRY` WRITE;
/*!40000 ALTER TABLE `COUNTRY` DISABLE KEYS */;
INSERT INTO `COUNTRY` VALUES (2,'Egnland'),(1,'Serbia');
/*!40000 ALTER TABLE `COUNTRY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DISH`
--

DROP TABLE IF EXISTS `DISH`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DISH` (
  `DISH_ID` int(11) NOT NULL AUTO_INCREMENT,
  `DISH_DESC` varchar(512) DEFAULT NULL,
  `DISH_NAME` varchar(128) NOT NULL,
  `DISH_PRICE` double DEFAULT NULL,
  `MENU_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`DISH_ID`),
  UNIQUE KEY `U_DISH_DISH_ID` (`DISH_ID`),
  KEY `I_DISH_MENU` (`MENU_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DISH`
--

LOCK TABLES `DISH` WRITE;
/*!40000 ALTER TABLE `DISH` DISABLE KEYS */;
INSERT INTO `DISH` VALUES (1,'Ovo je madjarski specijalitet','Gulas',20.25,1),(2,'Riblji paprikas od raznoraznih vrsti recne ribe.','Riblji paprikas',650.99,2),(3,'Pileci paprikas sadrzi samo pilece filee i povrce.','Pileci paprikas',110,2);
/*!40000 ALTER TABLE `DISH` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FRIEND`
--

DROP TABLE IF EXISTS `FRIEND`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FRIEND` (
  `USER_OWNER_ID` int(11) DEFAULT NULL,
  `USER_FRIEND_ID` int(11) DEFAULT NULL,
  `FRIEND_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`FRIEND_ID`),
  UNIQUE KEY `U_FRIEND_FRIEND_ID` (`FRIEND_ID`),
  KEY `I_FRIEND_ELEMENT` (`USER_FRIEND_ID`),
  KEY `I_FRIEND_USERGUEST` (`USER_ID`),
  KEY `I_FRIEND_USER_OWNER_ID` (`USER_OWNER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FRIEND`
--

LOCK TABLES `FRIEND` WRITE;
/*!40000 ALTER TABLE `FRIEND` DISABLE KEYS */;
INSERT INTO `FRIEND` VALUES (6,5,55,NULL),(5,7,56,NULL),(5,6,57,NULL),(7,5,58,NULL);
/*!40000 ALTER TABLE `FRIEND` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `IMAGE`
--

DROP TABLE IF EXISTS `IMAGE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `IMAGE` (
  `IMAGE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `IMAGE_NAME` tinyblob NOT NULL,
  `IMAGE_PATH` varchar(512) NOT NULL,
  `IMAGE_REAL_NAME` varchar(256) NOT NULL,
  PRIMARY KEY (`IMAGE_ID`),
  UNIQUE KEY `U_IMAGE_IMAGE_ID` (`IMAGE_ID`),
  UNIQUE KEY `U_IMAGE_IMAGE_REAL_NAME` (`IMAGE_REAL_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `IMAGE`
--

LOCK TABLES `IMAGE` WRITE;
/*!40000 ALTER TABLE `IMAGE` DISABLE KEYS */;
INSERT INTO `IMAGE` VALUES (1,'†≠q(Z¥	ÚjÓ¬oı—È¶NäôüÓ;é\Z@√ˇÌcm','/restaurant/images/[B@23b35aea','wallpaper-2552971.jpg'),(2,'≤ˇ%;‚‘ÎY‹<“„Æ•ÿJ“ã¿g·¸Ad˚Ã+','/restaurant/images/[B@31ece11e','wallpaper-2251330.jpg'),(5,'(ã3˙2ÇMcπä´›9Ö^ú˛Ü≤#úE‡ºÙÃu\ryÀ','/restaurant/images/[B@111a2a6c','wallpaper-2251330 (copy).jpg'),(7,'µv≠˚≈ÈÖ_Œå¿âÁ1‘^VOº¡h	Y¿PY,`NG¢','/restaurant/images/[B@5d852771','vintage-music-business-shop.jpg'),(9,'v˙”`w…ãküæùÇÅıê,ªá¯ön¸¨	','/restaurant/images/[B@3dd8d04b','vintage-technology-old-sound.jpg'),(10,'üıøµ–≤	Â4=¯Fv¨y‡ﬂy˙”Ç¥_(ÇÎãÑY\n','/restaurant/images/[B@e4b705d','vintage-technology-keyboard-old.jpg'),(11,'…9X40¿ÿb“Œ¸‰†N4¢ÓçıæYπi‡É/G›”Q','/home/authorit0/Projects/ISA/apache-tomee-plus-1.5.0/webapps/restaurant/restaurant/images/[B@1e6f146e','wallpaper-724965.jpg'),(12,'±-r4fz”GC√ Öè;}+	í%CÂ¿ÌVÇÏ•©','/restaurant/images/[B@54108c25','people-vintage-photo-memories.jpg'),(15,'ﬁtzÏœwjŒ~˚ß#„S/É—ÿ#–®.-4in','https://lh4.googleusercontent.com/-MGXNhOlPq8k/AAAAAAAAAAI/AAAAAAAAAlE/v5wnKMzqWTI/photo.jpg','mihailo93.jpg');
/*!40000 ALTER TABLE `IMAGE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `INVITATION`
--

DROP TABLE IF EXISTS `INVITATION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `INVITATION` (
  `INV_ID` int(11) NOT NULL AUTO_INCREMENT,
  `INV_ACC` tinyint(1) DEFAULT NULL,
  `INV_NAME` varchar(64) NOT NULL,
  `RES_ID` int(11) DEFAULT NULL,
  `USER_RCV_ID` int(11) DEFAULT NULL,
  `USER_SEND_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`INV_ID`),
  UNIQUE KEY `U_NVTTION_INV_ID` (`INV_ID`),
  KEY `I_NVTTION_RESERVATION` (`RES_ID`),
  KEY `I_NVTTION_USERGUESTINVITATIONRECEIVED` (`USER_RCV_ID`),
  KEY `I_NVTTION_USERGUESTINVITATIONSENDER` (`USER_SEND_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `INVITATION`
--

LOCK TABLES `INVITATION` WRITE;
/*!40000 ALTER TABLE `INVITATION` DISABLE KEYS */;
INSERT INTO `INVITATION` VALUES (17,0,'invitation0',10,7,5),(18,0,'invitation0',10,6,5),(19,0,'invitation0',11,7,5),(20,0,'invitation0',12,7,5),(21,0,'invitation0',12,6,5),(22,0,'invitation0',13,6,5),(23,0,'invitation0',13,7,5),(24,0,'invitation0',14,6,5),(25,0,'invitation0',14,7,5),(26,NULL,'invitation0',19,6,5),(27,NULL,'invitation0',19,7,5),(28,NULL,'invitation0',20,5,7);
/*!40000 ALTER TABLE `INVITATION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MENU`
--

DROP TABLE IF EXISTS `MENU`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MENU` (
  `MENU_ID` int(11) NOT NULL AUTO_INCREMENT,
  `MENU_CURR` bit(1) DEFAULT NULL,
  `MENU_DATE_FROM` datetime NOT NULL,
  `MENU_DATE_TO` datetime DEFAULT NULL,
  `MENU_NAME` varchar(64) NOT NULL,
  `REST_ID` int(11) DEFAULT NULL,
  `USER_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`MENU_ID`),
  UNIQUE KEY `U_MENU_MENU_ID` (`MENU_ID`),
  KEY `I_MENU_RESTAURANT` (`REST_ID`),
  KEY `I_MENU_USERRESTAURANTMENAGER` (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MENU`
--

LOCK TABLES `MENU` WRITE;
/*!40000 ALTER TABLE `MENU` DISABLE KEYS */;
INSERT INTO `MENU` VALUES (1,'','2016-02-17 00:00:00',NULL,'Moj meni',1,4),(2,'','2016-02-23 00:00:00',NULL,'Moj prvi meni',6,15);
/*!40000 ALTER TABLE `MENU` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESERVATION`
--

DROP TABLE IF EXISTS `RESERVATION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESERVATION` (
  `RES_ID` int(11) NOT NULL AUTO_INCREMENT,
  `RES_DATE` datetime NOT NULL,
  `RES_FOR` int(11) NOT NULL,
  `RES_GRADE` int(11) NOT NULL,
  `RES_NAME` varchar(64) NOT NULL,
  `REST_ID` int(11) DEFAULT NULL,
  `REST_TABLE_ID` int(11) DEFAULT NULL,
  `USER_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`RES_ID`),
  UNIQUE KEY `U_RSRVTON_RES_ID` (`RES_ID`),
  KEY `I_RSRVTON_RESTAURANT` (`REST_ID`),
  KEY `I_RSRVTON_RESTAURANTTABLE` (`REST_TABLE_ID`),
  KEY `I_RSRVTON_USERGUESTRESERVATIONMAKER` (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESERVATION`
--

LOCK TABLES `RESERVATION` WRITE;
/*!40000 ALTER TABLE `RESERVATION` DISABLE KEYS */;
INSERT INTO `RESERVATION` VALUES (10,'2016-02-26 14:10:00',1,-1,'reservation0',1,NULL,5),(11,'2016-03-28 16:20:00',2,-1,'reservation0',1,NULL,5),(12,'2016-04-22 18:50:00',3,-1,'reservation0',1,NULL,5),(13,'2016-08-18 20:00:00',3,-1,'reservation0',1,NULL,5),(14,'2016-02-29 13:00:00',2,-1,'reservation0',1,NULL,5),(15,'2016-02-23 20:18:00',2,-1,'reservation0',6,NULL,5),(16,'2016-02-22 14:10:00',3,-1,'reservation0',1,NULL,5),(17,'2016-02-25 20:15:00',3,-1,'reservation0',6,NULL,5),(18,'2016-08-28 12:00:00',2,-1,'reservation0',6,NULL,5),(19,'2016-02-22 20:15:00',2,-1,'reservation0',6,NULL,5),(20,'2016-02-28 20:15:00',2,-1,'reservation0',6,NULL,7);
/*!40000 ALTER TABLE `RESERVATION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESERVED_TABLE`
--

DROP TABLE IF EXISTS `RESERVED_TABLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESERVED_TABLE` (
  `RES_ID` int(11) DEFAULT NULL,
  `REST_TABLE_ID` int(11) DEFAULT NULL,
  KEY `I_RSRVTBL_ELEMENT` (`REST_TABLE_ID`),
  KEY `I_RSRVTBL_RES_ID` (`RES_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESERVED_TABLE`
--

LOCK TABLES `RESERVED_TABLE` WRITE;
/*!40000 ALTER TABLE `RESERVED_TABLE` DISABLE KEYS */;
INSERT INTO `RESERVED_TABLE` VALUES (2,2),(2,10),(2,7),(3,18),(3,1),(3,9),(3,21),(4,1),(4,9),(4,6),(6,3),(6,2),(6,4),(5,25),(5,1),(7,9),(7,8),(7,16),(7,15),(8,7),(8,2),(8,1),(8,8),(9,1),(9,14),(9,7),(10,11),(10,3),(10,17),(12,10),(12,24),(12,16),(12,18),(12,17),(13,17),(14,9),(19,50),(19,53),(20,51);
/*!40000 ALTER TABLE `RESERVED_TABLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESTAURANT`
--

DROP TABLE IF EXISTS `RESTAURANT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESTAURANT` (
  `REST_ID` int(11) NOT NULL AUTO_INCREMENT,
  `REST_GRADE` int(11) DEFAULT NULL,
  `REST_NAME` varchar(128) NOT NULL,
  `ADDRESS_ID` int(11) DEFAULT NULL,
  `REST_TYPE_ID` int(11) DEFAULT NULL,
  `USER_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`REST_ID`),
  UNIQUE KEY `U_RSTURNT_REST_ID` (`REST_ID`),
  KEY `I_RSTURNT_ADDRESS` (`ADDRESS_ID`),
  KEY `I_RSTURNT_RESTAURANTTYPE` (`REST_TYPE_ID`),
  KEY `I_RSTURNT_USERSYSTEMMENAGER` (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESTAURANT`
--

LOCK TABLES `RESTAURANT` WRITE;
/*!40000 ALTER TABLE `RESTAURANT` DISABLE KEYS */;
INSERT INTO `RESTAURANT` VALUES (1,4,'Kod Mise',3,2,16),(5,-1,'Totalno pogresan izbor',18,8,2),(6,4,'Restoran 2',20,2,15);
/*!40000 ALTER TABLE `RESTAURANT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESTAURANT_TABLE`
--

DROP TABLE IF EXISTS `RESTAURANT_TABLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESTAURANT_TABLE` (
  `REST_TABLE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `REST_TABLE_COL` int(11) NOT NULL,
  `REST_TABLE_NAME` varchar(64) NOT NULL,
  `REST_TABLE_RES` bit(1) NOT NULL,
  `REST_TABLE_RES_DATE` datetime DEFAULT NULL,
  `REST_TABLE_RES_FOR` int(11) DEFAULT NULL,
  `REST_TABLE_ROW` int(11) NOT NULL,
  `TAB_CONF_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`REST_TABLE_ID`),
  UNIQUE KEY `U_RSTRTBL_REST_TABLE_ID` (`REST_TABLE_ID`),
  KEY `I_RSTRTBL_TABLESCONFIGURATION` (`TAB_CONF_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESTAURANT_TABLE`
--

LOCK TABLES `RESTAURANT_TABLE` WRITE;
/*!40000 ALTER TABLE `RESTAURANT_TABLE` DISABLE KEYS */;
INSERT INTO `RESTAURANT_TABLE` VALUES (1,0,'table00','\0',NULL,NULL,0,1),(2,1,'table01','\0',NULL,NULL,0,1),(3,2,'table02','\0',NULL,NULL,0,1),(4,3,'table03','\0',NULL,NULL,0,1),(5,5,'table05','\0',NULL,NULL,0,1),(6,6,'table06','\0',NULL,NULL,0,1),(7,0,'table10','\0',NULL,NULL,1,1),(8,1,'table11','\0',NULL,NULL,1,1),(9,2,'table12','\0',NULL,NULL,1,1),(10,3,'table13','\0',NULL,NULL,1,1),(11,4,'table14','\0',NULL,NULL,1,1),(12,5,'table15','\0',NULL,NULL,1,1),(13,6,'table16','\0',NULL,NULL,1,1),(14,0,'table20','\0',NULL,NULL,2,1),(15,1,'table21','\0',NULL,NULL,2,1),(16,2,'table22','\0',NULL,NULL,2,1),(17,3,'table23','\0',NULL,NULL,2,1),(18,4,'table24','\0',NULL,NULL,2,1),(19,5,'table25','\0',NULL,NULL,2,1),(20,6,'table26','\0',NULL,NULL,2,1),(21,0,'table30','\0',NULL,NULL,3,1),(22,1,'table31','\0',NULL,NULL,3,1),(23,2,'table32','\0',NULL,NULL,3,1),(24,3,'table33','\0',NULL,NULL,3,1),(25,4,'table34','\0',NULL,NULL,3,1),(26,5,'table35','\0',NULL,NULL,3,NULL),(27,6,'table36','\0',NULL,NULL,3,NULL),(28,0,'table40','\0',NULL,NULL,4,NULL),(29,1,'table41','\0',NULL,NULL,4,NULL),(30,2,'table42','\0',NULL,NULL,4,NULL),(31,3,'table43','\0',NULL,NULL,4,NULL),(32,4,'table44','\0',NULL,NULL,4,NULL),(33,5,'table45','\0',NULL,NULL,4,NULL),(34,6,'table46','\0',NULL,NULL,4,NULL),(35,0,'table50','\0',NULL,NULL,5,NULL),(36,1,'table51','\0',NULL,NULL,5,NULL),(37,3,'table53','\0',NULL,NULL,5,NULL),(38,4,'table54','\0',NULL,NULL,5,NULL),(39,5,'table55','\0',NULL,NULL,5,NULL),(40,6,'table56','\0',NULL,NULL,5,NULL),(41,2,'table62','\0',NULL,NULL,6,NULL),(42,3,'table63','\0',NULL,NULL,6,NULL),(43,4,'table64','\0',NULL,NULL,6,NULL),(44,5,'table65','\0',NULL,NULL,6,NULL),(45,6,'table66','\0',NULL,NULL,6,NULL),(46,0,'table_6_00','\0',NULL,NULL,0,6),(47,1,'table_6_11','\0',NULL,NULL,1,6),(48,3,'table_6_13','\0',NULL,NULL,1,6),(49,4,'table_6_14','\0',NULL,NULL,1,6),(50,0,'table_6_20','\0',NULL,NULL,2,6),(51,2,'table_6_22','\0',NULL,NULL,2,6),(52,4,'table_6_24','\0',NULL,NULL,2,6),(53,0,'table_6_30','\0',NULL,NULL,3,6),(54,2,'table_6_32','\0',NULL,NULL,3,6),(55,3,'table_6_33','\0',NULL,NULL,3,6),(56,4,'table_6_34','\0',NULL,NULL,3,6),(57,1,'table_6_41','\0',NULL,NULL,4,6);
/*!40000 ALTER TABLE `RESTAURANT_TABLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESTAURANT_TYPE`
--

DROP TABLE IF EXISTS `RESTAURANT_TYPE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESTAURANT_TYPE` (
  `REST_TYPE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `REST_TYPE_NAME` varchar(64) NOT NULL,
  `USER_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`REST_TYPE_ID`),
  UNIQUE KEY `U_RSTRTYP_REST_TYPE_ID` (`REST_TYPE_ID`),
  KEY `I_RSTRTYP_USERSYSTEMMENAGER` (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESTAURANT_TYPE`
--

LOCK TABLES `RESTAURANT_TYPE` WRITE;
/*!40000 ALTER TABLE `RESTAURANT_TYPE` DISABLE KEYS */;
INSERT INTO `RESTAURANT_TYPE` VALUES (1,'Kineski restoran',2),(2,'Stand sa pljeskavicama',2),(6,'Riblji restoran.',2),(7,'Bavarska u vasem gradu',2),(8,'Pogresan izbor',2);
/*!40000 ALTER TABLE `RESTAURANT_TYPE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `STREET`
--

DROP TABLE IF EXISTS `STREET`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `STREET` (
  `STREET_ID` int(11) NOT NULL AUTO_INCREMENT,
  `STREET_NAME` varchar(64) NOT NULL,
  `CITY_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`STREET_ID`),
  UNIQUE KEY `U_STREET_STREET_ID` (`STREET_ID`),
  UNIQUE KEY `U_STREET_STREET_NAME` (`STREET_NAME`),
  KEY `I_STREET_CITY` (`CITY_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `STREET`
--

LOCK TABLES `STREET` WRITE;
/*!40000 ALTER TABLE `STREET` DISABLE KEYS */;
INSERT INTO `STREET` VALUES (1,'Bulevar kralja Aleksandra',1),(2,'Petra I Petrovica Njegosa',1),(3,'Kraljevica Marka',2),(4,'Urosa Predica',1),(5,'Jugoslovenske narodne armije',1),(6,'Devet Jugovica',2),(7,'Bulevar oslobodjenja',2),(8,'Kosovska',2),(9,'Jovana Jovanovica Zmaja',2),(10,'Marsala Tita',1),(11,'Zelena',2);
/*!40000 ALTER TABLE `STREET` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TABLES_CONFIGURATION`
--

DROP TABLE IF EXISTS `TABLES_CONFIGURATION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TABLES_CONFIGURATION` (
  `TAB_CONF_ID` int(11) NOT NULL AUTO_INCREMENT,
  `TAB_CONF_CURR` bit(1) NOT NULL,
  `TAB_CONF_DATE_FROM` datetime NOT NULL,
  `TAB_CONF_DATE_TO` datetime DEFAULT NULL,
  `TAB_CONF_NAME` varchar(64) NOT NULL,
  `TAB_CONF_COL_NO` int(11) NOT NULL,
  `TAB_CONF_ROW_NO` int(11) NOT NULL,
  `REST_ID` int(11) DEFAULT NULL,
  `USER_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`TAB_CONF_ID`),
  UNIQUE KEY `U_TBLSRTN_TAB_CONF_ID` (`TAB_CONF_ID`),
  KEY `I_TBLSRTN_RESTAURANT` (`REST_ID`),
  KEY `I_TBLSRTN_USERRESTAURANTMENAGER` (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TABLES_CONFIGURATION`
--

LOCK TABLES `TABLES_CONFIGURATION` WRITE;
/*!40000 ALTER TABLE `TABLES_CONFIGURATION` DISABLE KEYS */;
INSERT INTO `TABLES_CONFIGURATION` VALUES (1,'','2016-02-19 00:00:00',NULL,'Konfiguracija 1',7,7,1,4),(6,'','2016-02-22 00:00:00',NULL,'Tables konf ',5,5,6,15);
/*!40000 ALTER TABLE `TABLES_CONFIGURATION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER`
--

DROP TABLE IF EXISTS `USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER` (
  `USER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ACCOUNT_TYPE` varchar(255) NOT NULL,
  `USER_ACT` bit(1) NOT NULL,
  `USER_EMAIL` varchar(80) NOT NULL,
  `USER_SESSION_ACTIVE` bit(1) NOT NULL,
  `USER_NAME` varchar(64) NOT NULL,
  `USER_PASS` blob NOT NULL,
  `USER_SALT` blob NOT NULL,
  `USER_SESSION_ID` varchar(255) NOT NULL,
  `USER_SURNAME` varchar(64) NOT NULL,
  `USER_ACTIVATION_TOKEN` blob,
  `USER_REST_MEN_REST` int(11) DEFAULT NULL,
  `ADDRESS_ID` int(11) DEFAULT NULL,
  `IMAGE_ID` int(11) DEFAULT NULL,
  `USER_SYS_MEN_ID` int(11) DEFAULT NULL,
  `USER_TYPE_ID` int(11) NOT NULL,
  `USER_ACTIVATION_SALT` blob,
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `U_USER_USER_ID` (`USER_ID`),
  KEY `I_USER_ADDRESS` (`ADDRESS_ID`),
  KEY `I_USER_IMAGE` (`IMAGE_ID`),
  KEY `I_USER_RESTAURANTMENAGEDBY` (`USER_REST_MEN_REST`),
  KEY `I_USER_SYSTEMMENAGER` (`USER_SYS_MEN_ID`),
  KEY `I_USER_USERTYPE` (`USER_TYPE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER`
--

LOCK TABLES `USER` WRITE;
/*!40000 ALTER TABLE `USER` DISABLE KEYS */;
INSERT INTO `USER` VALUES (1,'LOCAL','','marko_jovanovic@gmail.com','\0','Marko','kpLÓ˘†Æ◊—°ÕL{	j ëæ\rõíCœáÊÇ','iLTäpCcg\"ÕXÕãáﬂ','','Jovanovic',NULL,NULL,NULL,NULL,NULL,1,NULL),(2,'LOCAL','','mihailo931@gmail.com','','Mihailo','Ü∫x¡uÍﬂ$ÚZÆoÖ∂… Ç&‹†|òs∏§Å~\nX','`1Ù¨ËE>⁄den!m','FE0253AE1F3D0DB189D7430C5E6FB1EA','Vasiljevic',NULL,NULL,NULL,NULL,NULL,1,NULL),(3,'LOCAL','','petar@petar.com','\0','Petar','.YaOV·˝≥DpåÓ∑Ï¬S@A∂ÌRœ›ü»ó’…G','ÈZí»dyú}ú‘V\"Øı∞[','','Peric','40Òª i\'”]ì•U¸Pbœ˝íçπ™|ëÎÇFòÎ',2,NULL,NULL,2,3,NULL),(4,'LOCAL','','stefan@stefan.com','','Stefan','4Ä¨lÚÀ‡ØﬂEÙ∏í^‘<Ü>ï´ZÌÇñ´Ï¢≥\'J','ô#gFóó^nﬂ=(€K™Ó','C1C8BB706BAD145BC837C1EE672A5448','Stefic','JÎ‡fJîZ-5YBÊ`<â–Ç¨9@4°ærÄK˚•',4,NULL,NULL,2,3,NULL),(5,'LOCAL','','stefan_stephen@gmail.com','','Stefan','ã`pÌÏ^d¿a@À‚ŸÈJÜ\"+ØiHëp†	e›','£VG›˙ègÀéÈrÁÑ%','716F2BA00F8E419EFEF5F299F0A99FC0','Stefanovic',NULL,NULL,21,NULL,NULL,2,NULL),(6,'LOCAL','','mihailo93@gmail.com','','Mirko','îè‚-aå˘·âÃûEßÁoOŸﬂ{^Ê3˛∫ˇtzVã√','èzçß~?ÑC˜X¸ëD','06609B14267E612E46A42613939F8BB1','Maric',NULL,NULL,NULL,NULL,NULL,2,NULL),(7,'LOCAL','','mihailo.vasiljevic93@gmail.com','','Sanja','Ób@ôﬂ]+á: §gÇ¥ñ9úO\ZâøÊVz,','Ë¶ô2Y‡ã“†èﬁœs≤√','EA98C17E26567B299E7FEC0C440B8053','Popovic',NULL,NULL,NULL,NULL,NULL,2,NULL),(14,'LOCAL','','mihailo93@outlook.com','','misa','¯§SS5l<]ÚT°{z	ÇO˛Êõ7å,\'y∏*⁄⁄','e3√¸$ˇ—‰»ÊÊ„ÏÀ!·','6395B85938E8E2CA6DC6A3B02C13DC84','misa','¨ÓÜ\0Ù0ã„ª‹†”öj\r‰ËÅsT∑È+lçñâ',NULL,NULL,NULL,NULL,2,'L∞é$%lÔf\\öÊiˇY'),(15,'LOCAL','','peki@peki.com','','peki','}-Ê+:Ka_]¶˜Èfb©||\'v“Ã]€òû8ª','ó\Zqƒ^	«9…ìmoΩg','FBE890B0AF46F056208B3577D100E78B','peki','nk±fvo¢W0∂	d*TÔ\0Sïf˝ÂCnÈd‹ë',6,NULL,10,2,3,NULL),(16,'LOCAL','','krk@krk.com','\0','krk','b(jŸ[ô,πø@ë?x‘Ûu.é¬ùmû∑S¨ £é',' Ÿ<•9í4à1t°SØFÏ','','krk','çhl*(64©V+è¿-F˜V«(ÃâÁ”π«y1≥û…tW',1,NULL,11,2,3,NULL),(17,'LOCAL','','lekI@leki.com','','leki','œtSpÖö•›˚_„≠æ‘Ì™√5oÛFªÄ˝Ñ;6\ZÈN','ë^ıKŒ%Èò1ıü õBqM','36436ADD84F34B4010BB5B8A000F3340','leki','˘¸⁄ƒ$ô∑¨†˜†HlBvd8kœ…âGÔ}±.Ã',NULL,NULL,12,2,3,NULL),(20,'GLOBAL','','UNKNOWN10209202415837240','','???????','¨äÛt*tÀ0ÅÅ\nœgtk’[∞=»4\\D?3ÊÚm','^‡¢h˜„øù§ÿΩå£.','','?????????',NULL,NULL,NULL,NULL,NULL,2,NULL),(21,'GLOBAL','','mihailo93@gmail.com105131738782737888140','','NEPOZNATO','Ÿ‰]gSºI3á_Ô™ó˚˝∏à›Ú±⁄âÄ¯,R=≥',' ßµ\0ä&\'ïF•sˆw…+','','NEPOZNATO',NULL,NULL,NULL,15,NULL,2,NULL),(22,'LOCAL','\0','extra@extra.com','\0','Extra','ÌzÈµ†$kû∞Ω≤¨°2J¸o˘Æwı—˘ì}Ç{ïñ','I¨üiãwü|±Ï∞\"íVû','','Exttra','∏°¿∫æ^›˘ËyAîÂx¬æ!ÏÙÇàÅ‹⁄ùÑ©ÉÎãÆH',NULL,NULL,NULL,NULL,2,'OØ7\ZÍ*Ë‚6ÇOD«ÒS');
/*!40000 ALTER TABLE `USER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_TYPE`
--

DROP TABLE IF EXISTS `USER_TYPE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_TYPE` (
  `USER_TYPE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_TYPE_NAME` varchar(64) NOT NULL,
  PRIMARY KEY (`USER_TYPE_ID`),
  UNIQUE KEY `U_USR_TYP_USER_TYPE_ID` (`USER_TYPE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_TYPE`
--

LOCK TABLES `USER_TYPE` WRITE;
/*!40000 ALTER TABLE `USER_TYPE` DISABLE KEYS */;
INSERT INTO `USER_TYPE` VALUES (1,'SYSTEM_MENAGER'),(2,'GUEST'),(3,'RESTAURANT_MENAGER');
/*!40000 ALTER TABLE `USER_TYPE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `VISIT`
--

DROP TABLE IF EXISTS `VISIT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `VISIT` (
  `VISIT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `VISIT_GRADE` int(11) NOT NULL,
  `REST_ID` int(11) DEFAULT NULL,
  `USER_ID` int(11) DEFAULT NULL,
  `RES_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`VISIT_ID`),
  UNIQUE KEY `U_VISIT_VISIT_ID` (`VISIT_ID`),
  KEY `I_VISIT_RESTAURANT` (`REST_ID`),
  KEY `I_VISIT_USER` (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `VISIT`
--

LOCK TABLES `VISIT` WRITE;
/*!40000 ALTER TABLE `VISIT` DISABLE KEYS */;
INSERT INTO `VISIT` VALUES (1,3,1,5,12),(2,5,1,5,14),(3,-1,1,6,14),(4,5,6,5,19),(5,4,6,7,20);
/*!40000 ALTER TABLE `VISIT` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-23 18:37:18
