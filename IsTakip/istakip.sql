CREATE DATABASE  IF NOT EXISTS `istakip` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `istakip`;
-- MySQL dump 10.13  Distrib 5.5.41, for debian-linux-gnu (i686)
--
-- Host: localhost    Database: istakip
-- ------------------------------------------------------
-- Server version	5.5.41-0ubuntu0.14.04.1

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
-- Table structure for table `table_dep_is`
--

DROP TABLE IF EXISTS `table_dep_is`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `table_dep_is` (
  `dep_id` int(11) NOT NULL,
  `is_id` int(11) NOT NULL,
  PRIMARY KEY (`dep_id`,`is_id`),
  KEY `fk_table_dep_is_2_idx` (`is_id`),
  CONSTRAINT `fk_table_dep_is_table_departman1` FOREIGN KEY (`dep_id`) REFERENCES `table_departman` (`dep_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_table_dep_is_table_is1` FOREIGN KEY (`is_id`) REFERENCES `table_is` (`is_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_dep_is`
--

LOCK TABLES `table_dep_is` WRITE;
/*!40000 ALTER TABLE `table_dep_is` DISABLE KEYS */;
INSERT INTO `table_dep_is` VALUES (0,11),(0,12),(0,13),(0,14),(0,15),(0,16),(0,17),(0,18),(0,19),(0,20),(0,21),(0,22),(0,23);
/*!40000 ALTER TABLE `table_dep_is` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table_departman`
--

DROP TABLE IF EXISTS `table_departman`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `table_departman` (
  `dep_id` int(11) NOT NULL AUTO_INCREMENT,
  `dep_aciklama` varchar(45) NOT NULL,
  PRIMARY KEY (`dep_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_departman`
--

LOCK TABLES `table_departman` WRITE;
/*!40000 ALTER TABLE `table_departman` DISABLE KEYS */;
INSERT INTO `table_departman` VALUES (0,'Yazılım'),(1,'Tasarım'),(2,'SAP');
/*!40000 ALTER TABLE `table_departman` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table_is`
--

DROP TABLE IF EXISTS `table_is`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `table_is` (
  `is_id` int(11) NOT NULL AUTO_INCREMENT,
  `is_baslik` varchar(100) NOT NULL,
  `is_ozet` varchar(750) NOT NULL,
  `is_amac` varchar(750) NOT NULL,
  `is_yenilikunsur` varchar(750) NOT NULL,
  `is_teknolojialan` varchar(750) NOT NULL,
  `is_yontemvemetod` varchar(750) NOT NULL,
  `is_sonuc` varchar(750) NOT NULL,
  PRIMARY KEY (`is_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_is`
--

LOCK TABLES `table_is` WRITE;
/*!40000 ALTER TABLE `table_is` DISABLE KEYS */;
INSERT INTO `table_is` VALUES (11,'asdf','asdf','asd','asd','safasdf','asdsf','asdfsaf'),(12,'başlık','özet','amaç','unsur','teknolojikalan','yöntemvemetod','sonuç'),(13,'asd','asd','asds','asdsad','sdsda','asd','asd'),(14,'a','a','a','a','a','a','a'),(15,'asd','asqqqqqqqq','asd','as','a','a','a'),(16,'q','q','q','q','q','q','q'),(17,'aaaaaaaaaaa','aaaaaaaaaaa','aaaaaaaaaaa','aaaaaaaaaaa','aaaaaaaaaaa','aaaaaaaaaaa','aaaaaaaaaaa'),(18,'bbbbbbb','bbbbbbb','bbbbbbb','bbbbbbb','bbbbbbb','bbbbbbb','bbbbbbb'),(19,'ccccccccccc','ccccccccccc','ccccccccccc','ccccccccccc','ccccccccccc','ccccccccccc','ccccccccccc'),(20,'abc','abc','abc','abc','abc','abc','abc'),(21,'abcd','abcd','abcd','abcd','abcd','abcd','abcd'),(22,'aosdads','aosdads','aosdads','aosdads','aosdads','aosdads','aosdads'),(23,'xyz','xyz','xyz','xyz','xyz','xyz','xyz'),(24,'wewewewewe','wewewewewe','wewewewewe','wewewewewe','wewewewewe','wewewewewe','wewewewewe'),(25,'wqwqwqwqw','wqwqwqwqw','wqwqwqwqw','wqwqwqwqw','wqwqwqwqw','wqwqwqwqw','wqwqwqwqw');
/*!40000 ALTER TABLE `table_is` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table_isayrinti`
--

DROP TABLE IF EXISTS `table_isayrinti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `table_isayrinti` (
  `is_id` int(11) NOT NULL,
  `is_durum` mediumtext,
  `is_bastarihi` date NOT NULL,
  `is_bittarihi` date NOT NULL,
  `oncelik_id` int(2) NOT NULL,
  `is_bitimi` tinyint(1) NOT NULL,
  `is_ek` varchar(45) DEFAULT NULL,
  `is_gelen` tinyint(1) NOT NULL,
  PRIMARY KEY (`is_id`),
  KEY `fk_table_uye_is_table_oncelik1_idx` (`oncelik_id`),
  KEY `fk_table_uye_is_table_is1_idx` (`is_id`),
  CONSTRAINT `fk_table_uye_is_table_is1` FOREIGN KEY (`is_id`) REFERENCES `table_is` (`is_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_table_uye_is_table_oncelik1` FOREIGN KEY (`oncelik_id`) REFERENCES `table_oncelik` (`oncelik_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_isayrinti`
--

LOCK TABLES `table_isayrinti` WRITE;
/*!40000 ALTER TABLE `table_isayrinti` DISABLE KEYS */;
INSERT INTO `table_isayrinti` VALUES (11,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<Comment>\r\n  <bilgi>\r\n    <adsoyad>Birdal Özcan</adsoyad>\r\n    <yorum>denemeyorummanager</yorum>\r\n  </bilgi>\r\n</Comment>\r\n','2015-03-24','2015-03-30',2,0,NULL,1),(12,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<Comment>\r\n  <bilgi>\r\n    <adsoyad>Steve Miller</adsoyad>\r\n    <yorum>deneme</yorum>\r\n  </bilgi>\r\n  <bilgi>\r\n    <adsoyad>Nameiki Surnameiki</adsoyad>\r\n    <yorum>weııeuoaı</yorum>\r\n  </bilgi>\r\n  <bilgi>\r\n    <adsoyad>Nameiki Surnameiki</adsoyad>\r\n    <yorum>pqawsıaoısdı</yorum>\r\n  </bilgi>\r\n  <bilgi>\r\n    <adsoyad>Name Surname</adsoyad>\r\n    <yorum>aşğğpspldğfk</yorum>\r\n  </bilgi>\r\n  <bilgi>\r\n    <adsoyad>Name Surname</adsoyad>\r\n    <yorum>aşğğpspldğfk</yorum>\r\n  </bilgi>\r\n  <bilgi>\r\n    <adsoyad>Name Surname</adsoyad>\r\n    <yorum>asdasdas</yorum>\r\n  </bilgi>\r\n  <bilgi>\r\n    <adsoyad>Steve Miller</adsoyad>\r\n    <yorum>wswws</yorum>\r\n  </bilgi>\r\n  <bilgi>\r\n    <adsoyad>Birdall Özcan</adsoyad>\r\n    <yorum>deneme yorumları2</yorum>\r\n  </bilgi>\r\n</Comment>\r\n','2015-03-23','2015-03-24',2,1,NULL,0),(13,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Comment>\n  <bilgi>\n    <adsoyad />\n    <yorum />\n  </bilgi>\n</Comment>','2015-03-03','2015-03-02',1,0,NULL,1),(14,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Comment>\n  <bilgi>\n    <adsoyad />\n    <yorum />\n  </bilgi>\n</Comment>','2015-01-01','2015-01-02',0,0,NULL,0),(15,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Comment>\n  <bilgi>\n    <adsoyad />\n    <yorum />\n  </bilgi>\n</Comment>','2015-03-26','2015-03-30',1,0,NULL,1),(16,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Comment>\n  <bilgi>\n    <adsoyad />\n    <yorum />\n  </bilgi>\n</Comment>','2015-03-25','2015-03-30',1,0,NULL,1),(17,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Comment>\n  <bilgi>\n    <adsoyad />\n    <yorum />\n  </bilgi>\n</Comment>','2015-03-25','2015-03-26',2,0,NULL,1),(18,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Comment>\n  <bilgi>\n    <adsoyad />\n    <yorum />\n  </bilgi>\n</Comment>','2015-03-26','2015-03-27',1,0,NULL,1),(19,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Comment>\n  <bilgi>\n    <adsoyad />\n    <yorum />\n  </bilgi>\n</Comment>','2015-03-24','2015-03-24',0,0,NULL,1),(20,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Comment>\n  <bilgi>\n    <adsoyad />\n    <yorum />\n  </bilgi>\n</Comment>','2015-03-26','2015-03-27',1,0,NULL,0),(21,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<Comment>\r\n  <bilgi>\r\n    <adsoyad>Steve Miller</adsoyad>\r\n    <yorum>gelenişyönlendirdepartmangörevlisi</yorum>\r\n  </bilgi>\r\n  <bilgi>\r\n    <adsoyad>Name Surname</adsoyad>\r\n    <yorum>asdasdasdas</yorum>\r\n  </bilgi>\r\n  <bilgi>\r\n    <adsoyad>Name Surname</adsoyad>\r\n    <yorum>asdasdasdasdsddd</yorum>\r\n  </bilgi>\r\n  <bilgi>\r\n    <adsoyad>Name Surname</adsoyad>\r\n    <yorum>asassss</yorum>\r\n  </bilgi>\r\n  <bilgi>\r\n    <adsoyad>Steve Miller</adsoyad>\r\n    <yorum>deneme</yorum>\r\n  </bilgi>\r\n  <bilgi>\r\n    <adsoyad>Birdall Özcan</adsoyad>\r\n    <yorum>denme yorumları</yorum>\r\n  </bilgi>\r\n</Comment>\r\n','2015-03-27','2015-03-30',2,0,NULL,0),(22,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Comment>\n  <bilgi>\n    <adsoyad />\n    <yorum />\n  </bilgi>\n</Comment>','2015-03-27','2015-03-27',0,0,NULL,1),(23,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<Comment>\r\n  <bilgi>\r\n    <adsoyad>Steve Miller</adsoyad>\r\n    <yorum>öçöçççööçççççşşiiğğğööüüüüüüıı</yorum>\r\n  </bilgi>\r\n  <bilgi>\r\n    <adsoyad>Birdal Özcan</adsoyad>\r\n    <yorum>denemeyorummanager123</yorum>\r\n  </bilgi>\r\n  <bilgi>\r\n    <adsoyad>Birdal Özcan</adsoyad>\r\n    <yorum>manager2</yorum>\r\n  </bilgi>\r\n  <bilgi>\r\n    <adsoyad>Birdal Özcan</adsoyad>\r\n    <yorum>sorucevapdeneme</yorum>\r\n  </bilgi>\r\n  <bilgi>\r\n    <adsoyad>Steve Miller</adsoyad>\r\n    <yorum>sorucevapdepartman</yorum>\r\n  </bilgi>\r\n</Comment>\r\n','2015-03-27','2015-03-30',0,0,NULL,0),(24,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Comment>\n  <bilgi>\n    <adsoyad />\n    <yorum />\n  </bilgi>\n</Comment>','2015-03-25','2015-03-27',0,0,NULL,1),(25,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<Comment>\r\n  <bilgi>\r\n    <adsoyad>Steve Miller</adsoyad>\r\n    <yorum>qwqwqwwqqqqqqqqqqqqqqqqq</yorum>\r\n  </bilgi>\r\n</Comment>\r\n','2015-03-30','2015-03-31',1,0,NULL,0);
/*!40000 ALTER TABLE `table_isayrinti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table_oncelik`
--

DROP TABLE IF EXISTS `table_oncelik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `table_oncelik` (
  `oncelik_id` int(11) NOT NULL AUTO_INCREMENT,
  `oncelik_aciklama` varchar(45) NOT NULL,
  PRIMARY KEY (`oncelik_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_oncelik`
--

LOCK TABLES `table_oncelik` WRITE;
/*!40000 ALTER TABLE `table_oncelik` DISABLE KEYS */;
INSERT INTO `table_oncelik` VALUES (0,'Yüksek Öncelikli'),(1,'Normal Öncelikli'),(2,'Düşük Öncelikli');
/*!40000 ALTER TABLE `table_oncelik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table_uyeler`
--

DROP TABLE IF EXISTS `table_uyeler`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `table_uyeler` (
  `uye_id` int(11) NOT NULL AUTO_INCREMENT,
  `uye_adi` varchar(100) NOT NULL,
  `uye_soyadi` varchar(100) NOT NULL,
  `uye_eposta` varchar(100) NOT NULL,
  `uye_sifre` varchar(50) NOT NULL,
  `uye_telefon` varchar(45) DEFAULT NULL,
  `uye_aylikrapor` varchar(45) DEFAULT NULL,
  `uyetip_id` int(11) NOT NULL,
  `dep_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`uye_id`,`uye_eposta`),
  KEY `fk_table_uyeler_table_uyetip1_idx` (`uyetip_id`),
  KEY `fk_table_uyeler_table_departman1_idx` (`dep_id`),
  CONSTRAINT `fk_table_uyeler_table_departman1` FOREIGN KEY (`dep_id`) REFERENCES `table_departman` (`dep_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_table_uyeler_table_uyetip1` FOREIGN KEY (`uyetip_id`) REFERENCES `table_uyetip` (`uyetip_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_uyeler`
--

LOCK TABLES `table_uyeler` WRITE;
/*!40000 ALTER TABLE `table_uyeler` DISABLE KEYS */;
INSERT INTO `table_uyeler` VALUES (1,'Birdal','Özcan','birdalozcan@gmail.com','1234','05312702775',NULL,0,NULL),(2,'Steve','Miller','stevemiller@gmail.com','1234','123123','',1,0),(3,'Name','Surname','personel01@gmail.com','1234','05312707070',NULL,2,0),(4,'Nameiki','Surnameiki','personel02@gmail.com','1234','05312707071',NULL,2,0),(5,'Nameüç','Surnameüç','personel03@gmail.com','1234','05312707072',NULL,2,0),(6,'Namedört','Surnamedört','personel04@gmail.com','1234','05312707073',NULL,2,1),(8,'Ertan','Bütün','ebutun@gmail.com','1234','05555555555',NULL,0,NULL),(9,'DepAd','DepSoyad','dep01@gmail.com','1234','555555235235',NULL,1,0),(10,'DepAd1','DepSoyad1','dep02@gmail.com','1234','5555552352352',NULL,1,1),(11,'DepAd2','DepSoyad2','dep03@gmail.com','1234','5555552351231',NULL,1,2);
/*!40000 ALTER TABLE `table_uyeler` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table_uyeler_is`
--

DROP TABLE IF EXISTS `table_uyeler_is`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `table_uyeler_is` (
  `uye_id` int(11) NOT NULL,
  `is_id` int(11) NOT NULL,
  `is_puan` int(3) DEFAULT NULL,
  `is_atanmadegeri` int(1) NOT NULL,
  PRIMARY KEY (`uye_id`,`is_id`),
  KEY `fk_table_uyeler_has_table_is_table_is1_idx` (`is_id`),
  KEY `fk_table_uyeler_has_table_is_table_uyeler1_idx` (`uye_id`),
  CONSTRAINT `fk_table_uyeler_is_table_is1` FOREIGN KEY (`is_id`) REFERENCES `table_is` (`is_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_table_uyeler_is_table_uyeler1` FOREIGN KEY (`uye_id`) REFERENCES `table_uyeler` (`uye_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_uyeler_is`
--

LOCK TABLES `table_uyeler_is` WRITE;
/*!40000 ALTER TABLE `table_uyeler_is` DISABLE KEYS */;
INSERT INTO `table_uyeler_is` VALUES (3,12,NULL,0),(3,14,NULL,0),(3,20,NULL,0),(3,21,NULL,1),(4,12,NULL,0),(4,14,NULL,0),(4,20,NULL,0),(4,21,NULL,0),(5,12,NULL,0),(5,14,NULL,0),(5,20,NULL,0),(5,21,NULL,0);
/*!40000 ALTER TABLE `table_uyeler_is` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table_uyetip`
--

DROP TABLE IF EXISTS `table_uyetip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `table_uyetip` (
  `uyetip_id` int(11) NOT NULL AUTO_INCREMENT,
  `uyetip_aciklama` varchar(45) NOT NULL,
  PRIMARY KEY (`uyetip_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_uyetip`
--

LOCK TABLES `table_uyetip` WRITE;
/*!40000 ALTER TABLE `table_uyetip` DISABLE KEYS */;
INSERT INTO `table_uyetip` VALUES (0,'Yönetici'),(1,'Departman Görevlisi'),(2,'Personel');
/*!40000 ALTER TABLE `table_uyetip` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-03-31 17:08:13
