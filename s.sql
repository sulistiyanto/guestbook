-- MySQL dump 10.13  Distrib 5.6.21, for Win32 (x86)
--
-- Host: localhost    Database: guestbookserver
-- ------------------------------------------------------
-- Server version	5.6.21

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
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book` (
  `book_id` char(6) NOT NULL,
  `book_name` varchar(15) NOT NULL,
  `book_date` date NOT NULL,
  `book_information` varchar(25) NOT NULL,
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES ('B.0001','wisuda 10','2015-09-10','wisuda ke 10'),('B.0002','wisuda 11','2015-03-13','df');
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `family`
--

DROP TABLE IF EXISTS `family`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `family` (
  `family_id` varchar(10) NOT NULL,
  `family_name` varchar(25) NOT NULL,
  `family_sex` char(1) NOT NULL,
  `family_presence` varchar(5) NOT NULL,
  `guest_id` varchar(10) NOT NULL,
  PRIMARY KEY (`family_id`),
  KEY `guest_id` (`guest_id`),
  CONSTRAINT `family_ibfk_1` FOREIGN KEY (`guest_id`) REFERENCES `guest` (`guest_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `family`
--

LOCK TABLES `family` WRITE;
/*!40000 ALTER TABLE `family` DISABLE KEYS */;
INSERT INTO `family` VALUES ('F.00000001','arif','L','Tidak','G.00000001'),('F.00000002','mamah','P','Tidak','G.00000001'),('F.00000003','papah','L','Tidak','G.00000001'),('F.00000004','pacar','L','Tidak','G.00000001'),('F.00000005','pacar','L','Tidak','G.00000002'),('F.00000007','cewekq','P','Tidak','G.00000002'),('F.00000008','chacha','P','Tidak','G.00000002'),('F.00000010','tiiii','L','Tidak','G.00000001'),('F.00000011','fffff','P','Tidak','G.00000001'),('F.00000012','cdd','P','Tidak','G.00000001'),('F.00000013','sada','L','Tidak','G.00000011'),('F.00000014','coba coba','L','Tidak','G.00000013'),('F.00000015','riska pacar','P','Tidak','G.00000016');
/*!40000 ALTER TABLE `family` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guest`
--

DROP TABLE IF EXISTS `guest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `guest` (
  `guest_id` varchar(10) NOT NULL,
  `guest_name` varchar(25) NOT NULL,
  `guest_sex` char(1) NOT NULL,
  `guest_job` varchar(15) NOT NULL,
  `guest_phone` varchar(12) NOT NULL,
  `guest_address` varchar(30) NOT NULL,
  `guest_foto` varchar(14) NOT NULL,
  `guest_presence` varchar(5) NOT NULL,
  `book_id` char(6) NOT NULL,
  PRIMARY KEY (`guest_id`),
  KEY `book_id` (`book_id`),
  CONSTRAINT `guest_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guest`
--

LOCK TABLES `guest` WRITE;
/*!40000 ALTER TABLE `guest` DISABLE KEYS */;
INSERT INTO `guest` VALUES ('G.00000001','chacha','P','mahasiswa','085230036555','singgahan','G.00000001.jpg','Tidak','B.0001'),('G.00000002','tiyan','L','ceo','085330049555','singgahan','G.00000002.jpg','Tidak','B.0001'),('G.00000003','erf','L','sdf','','dsf','G.00000003.jpg','Tidak','B.0001'),('G.00000004','df','L','dsf','','dsf','G.00000004.jpg','Tidak','B.0001'),('G.00000005','fsdf','L','dfsf','','dsfsf','G.00000005.jpg','Tidak','B.0001'),('G.00000006','sffa','L','sfaf','','asfsaf','G.00000006.jpg','Tidak','B.0001'),('G.00000007','adasd','L','sadasd','','sadsa','G.00000007.jpg','Tidak','B.0001'),('G.00000008','sadsad','L','dsad','','asdsad','G.00000008.jpg','Tidak','B.0001'),('G.00000009','sdad','L','czxscsad','','sad','G.00000009.jpg','Tidak','B.0001'),('G.00000010','sadd','L','sadsad','','dasdd','G.00000010.jpg','Tidak','B.0001'),('G.00000011','d','L','dfs','','dsf','G.00000011.jpg','Tidak','B.0002'),('G.00000012','sd','L','xzc','','xzc','G.00000012.jpg','Tidak','B.0001'),('G.00000013','c','L','d','','d','G.00000013.jpg','Tidak','B.0001'),('G.00000014','sdku','L','sdfs','','sdf','G.00000014.jpg','Tidak','B.0001'),('G.00000015','andi','L','asdas','','sad','G.00000015.jpg','Tidak','B.0001'),('G.00000016','tiyan','L','mahasiswa','34325','singga','G.00000016.jpg','Tidak','B.0002');
/*!40000 ALTER TABLE `guest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `login` (
  `login_name` varchar(15) NOT NULL,
  `login_password` varchar(40) NOT NULL,
  `login_level` varchar(5) NOT NULL,
  PRIMARY KEY (`login_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login`
--

LOCK TABLES `login` WRITE;
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` VALUES ('admin','86f7e437faa5a7fce15d1ddcb9eaeaea377667b8','Admin'),('riska','2d0a289874ef0cc560eb4233720be3cb0cfad1d5','User');
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-03-14 16:53:44
