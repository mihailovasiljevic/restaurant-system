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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ADDRESS`
--

LOCK TABLES `ADDRESS` WRITE;
/*!40000 ALTER TABLE `ADDRESS` DISABLE KEYS */;
INSERT INTO `ADDRESS` VALUES (1,'1',4),(2,'2',8),(3,'2',3),(4,'18',4),(5,'18',1),(6,'1',1),(7,'3',1);
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DISH`
--

LOCK TABLES `DISH` WRITE;
/*!40000 ALTER TABLE `DISH` DISABLE KEYS */;
INSERT INTO `DISH` VALUES (1,'Ovo je madjarski specijalitet','Gulas',20.25,1);
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FRIEND`
--

LOCK TABLES `FRIEND` WRITE;
/*!40000 ALTER TABLE `FRIEND` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `IMAGE`
--

LOCK TABLES `IMAGE` WRITE;
/*!40000 ALTER TABLE `IMAGE` DISABLE KEYS */;
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
  `INV_ACC` bit(1) NOT NULL,
  `INV_NAME` varchar(64) NOT NULL,
  `RES_ID` int(11) DEFAULT NULL,
  `USER_RCV_ID` int(11) DEFAULT NULL,
  `USER_SEND_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`INV_ID`),
  UNIQUE KEY `U_NVTTION_INV_ID` (`INV_ID`),
  KEY `I_NVTTION_RESERVATION` (`RES_ID`),
  KEY `I_NVTTION_USERGUESTINVITATIONRECEIVED` (`USER_RCV_ID`),
  KEY `I_NVTTION_USERGUESTINVITATIONSENDER` (`USER_SEND_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `INVITATION`
--

LOCK TABLES `INVITATION` WRITE;
/*!40000 ALTER TABLE `INVITATION` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MENU`
--

LOCK TABLES `MENU` WRITE;
/*!40000 ALTER TABLE `MENU` DISABLE KEYS */;
INSERT INTO `MENU` VALUES (1,'','2016-02-17 00:00:00',NULL,'Moj meni',1,4);
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESERVATION`
--

LOCK TABLES `RESERVATION` WRITE;
/*!40000 ALTER TABLE `RESERVATION` DISABLE KEYS */;
/*!40000 ALTER TABLE `RESERVATION` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESTAURANT`
--

LOCK TABLES `RESTAURANT` WRITE;
/*!40000 ALTER TABLE `RESTAURANT` DISABLE KEYS */;
INSERT INTO `RESTAURANT` VALUES (1,-1,'Kod mise',3,2,2);
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
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESTAURANT_TABLE`
--

LOCK TABLES `RESTAURANT_TABLE` WRITE;
/*!40000 ALTER TABLE `RESTAURANT_TABLE` DISABLE KEYS */;
INSERT INTO `RESTAURANT_TABLE` VALUES (1,0,'table00','\0',NULL,NULL,0,NULL),(2,1,'table01','\0',NULL,NULL,0,NULL),(3,2,'table02','\0',NULL,NULL,0,NULL),(4,3,'table03','\0',NULL,NULL,0,NULL),(5,5,'table05','\0',NULL,NULL,0,NULL),(6,6,'table06','\0',NULL,NULL,0,NULL),(7,0,'table10','\0',NULL,NULL,1,NULL),(8,1,'table11','\0',NULL,NULL,1,NULL),(9,2,'table12','\0',NULL,NULL,1,NULL),(10,3,'table13','\0',NULL,NULL,1,NULL),(11,4,'table14','\0',NULL,NULL,1,NULL),(12,5,'table15','\0',NULL,NULL,1,NULL),(13,6,'table16','\0',NULL,NULL,1,NULL),(14,0,'table20','\0',NULL,NULL,2,NULL),(15,1,'table21','\0',NULL,NULL,2,NULL),(16,2,'table22','\0',NULL,NULL,2,NULL),(17,3,'table23','\0',NULL,NULL,2,NULL),(18,4,'table24','\0',NULL,NULL,2,NULL),(19,5,'table25','\0',NULL,NULL,2,NULL),(20,6,'table26','\0',NULL,NULL,2,NULL),(21,0,'table30','\0',NULL,NULL,3,NULL),(22,1,'table31','\0',NULL,NULL,3,NULL),(23,2,'table32','\0',NULL,NULL,3,NULL),(24,3,'table33','\0',NULL,NULL,3,NULL),(25,4,'table34','\0',NULL,NULL,3,NULL),(26,5,'table35','\0',NULL,NULL,3,NULL),(27,6,'table36','\0',NULL,NULL,3,NULL),(28,0,'table40','\0',NULL,NULL,4,NULL),(29,1,'table41','\0',NULL,NULL,4,NULL),(30,2,'table42','\0',NULL,NULL,4,NULL),(31,3,'table43','\0',NULL,NULL,4,NULL),(32,4,'table44','\0',NULL,NULL,4,NULL),(33,5,'table45','\0',NULL,NULL,4,NULL),(34,6,'table46','\0',NULL,NULL,4,NULL),(35,0,'table50','\0',NULL,NULL,5,NULL),(36,1,'table51','\0',NULL,NULL,5,NULL),(37,3,'table53','\0',NULL,NULL,5,NULL),(38,4,'table54','\0',NULL,NULL,5,NULL),(39,5,'table55','\0',NULL,NULL,5,NULL),(40,6,'table56','\0',NULL,NULL,5,NULL),(41,2,'table62','\0',NULL,NULL,6,NULL),(42,3,'table63','\0',NULL,NULL,6,NULL),(43,4,'table64','\0',NULL,NULL,6,NULL),(44,5,'table65','\0',NULL,NULL,6,NULL),(45,6,'table66','\0',NULL,NULL,6,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESTAURANT_TYPE`
--

LOCK TABLES `RESTAURANT_TYPE` WRITE;
/*!40000 ALTER TABLE `RESTAURANT_TYPE` DISABLE KEYS */;
INSERT INTO `RESTAURANT_TYPE` VALUES (1,'Kineski restoran',2),(2,'Standa sa pljeskavicama',2);
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TABLES_CONFIGURATION`
--

LOCK TABLES `TABLES_CONFIGURATION` WRITE;
/*!40000 ALTER TABLE `TABLES_CONFIGURATION` DISABLE KEYS */;
INSERT INTO `TABLES_CONFIGURATION` VALUES (1,'','2016-02-19 00:00:00',NULL,'Konfiguracija 1',7,7,1,4);
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
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `U_USER_USER_ID` (`USER_ID`),
  KEY `I_USER_ADDRESS` (`ADDRESS_ID`),
  KEY `I_USER_IMAGE` (`IMAGE_ID`),
  KEY `I_USER_RESTAURANTMENAGEDBY` (`USER_REST_MEN_REST`),
  KEY `I_USER_SYSTEMMENAGER` (`USER_SYS_MEN_ID`),
  KEY `I_USER_USERTYPE` (`USER_TYPE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER`
--

LOCK TABLES `USER` WRITE;
/*!40000 ALTER TABLE `USER` DISABLE KEYS */;
INSERT INTO `USER` VALUES (1,'LOCAL','','marko_jovanovic@gmail.com','\0','Marko','kpLîù ®×Ñ¡ÍL{	jÊ‘¾\r›’CÏ‡æ‚','iLTŠpCcg\"ÍXÍ‹‡ß','','Jovanovic',NULL,NULL,NULL,NULL,NULL,1),(2,'LOCAL','','mihailo931@gmail.com','\0','Mihailo','†ºxÁuêß$òZ®o…¶ÉÊğ‚&Ü |˜s¸¤~\nX','`1ô¬èE>Úden!m','A50B42DE8AC11CB81D0FE8D404BF8CA3','Vasiljevic',NULL,NULL,NULL,NULL,NULL,1),(3,'LOCAL','','petar@petar.com','\0','Petar','.YaOVáı³DpŒî·ìÂS@A¶íRÏİŸÈ—ÕÉG','éZ’Èdyœ}œÔV\"¯õ°[','','Peric','40ñ» i\'Ó]“¥UüPbÏı’¹ª|‘ë‚F˜ë',1,NULL,NULL,2,3),(4,'LOCAL','','stefan@stefan.com','','Stefan','4€¬lòËà¯ßEô¸’^Ô<†>•«Zí‚–«ì¢³\'J','™#gF——^nß=(ÛKªî','48071D8BFED72F8C78DC5E31513C30A8','Stefic','JëàfJ”Z-5YBæ`<‰Ğ‚¬9@4¡¾r€Kû¥',1,NULL,NULL,2,3);
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
  PRIMARY KEY (`VISIT_ID`),
  UNIQUE KEY `U_VISIT_VISIT_ID` (`VISIT_ID`),
  KEY `I_VISIT_RESTAURANT` (`REST_ID`),
  KEY `I_VISIT_USER` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `VISIT`
--

LOCK TABLES `VISIT` WRITE;
/*!40000 ALTER TABLE `VISIT` DISABLE KEYS */;
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

-- Dump completed on 2016-02-19 12:45:09
