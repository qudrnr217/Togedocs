-- MySQL dump 10.13  Distrib 8.0.31, for Linux (x86_64)
--
-- Host: localhost    Database: togedocs
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL,
  `img_no` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (26,'4b4ef25d-10fa-4fd7-b870-f1fd271f88e6',2),(27,'467eb409-8aee-422c-bfb9-ba45700472ce',7),(28,'050a5e62-6803-4566-a47a-3dd1581777ad',5),(31,'19f6093a-af39-4cf8-8d3c-33f7cc5b6586',2),(34,'a47bfb5d-f87c-4985-8dc3-86ca5dd61138',7),(35,'23bf8ac0-e8c8-45de-8bac-e5e1a750499b',1),(37,'62824eb8-78bd-418b-9c7f-f4eff4a9f560',7),(39,'565d2cf3-9547-447d-918b-cc9964fcdfdf',3);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_user`
--

DROP TABLE IF EXISTS `project_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role` varchar(255) DEFAULT NULL,
  `project_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4ug72llnm0n7yafwntgdswl3y` (`project_id`),
  KEY `FK4jl2o131jivd80xsuw6pivnbx` (`user_id`),
  CONSTRAINT `FK4jl2o131jivd80xsuw6pivnbx` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK4ug72llnm0n7yafwntgdswl3y` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_user`
--

LOCK TABLES `project_user` WRITE;
/*!40000 ALTER TABLE `project_user` DISABLE KEYS */;
INSERT INTO `project_user` VALUES (30,'ADMIN',26,11),(31,'ADMIN',27,11),(32,'ADMIN',28,11),(38,'ADMIN',31,11),(41,'ADMIN',34,13),(42,'ADMIN',35,11),(43,'MEMBER',34,12),(44,'ADMIN',34,16),(45,'MEMBER',34,15),(48,'ADMIN',37,17),(50,'MEMBER',34,17),(52,'ADMIN',39,13),(53,'MEMBER',37,13),(54,'MEMBER',39,17),(55,'MEMBER',39,19);
/*!40000 ALTER TABLE `project_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_achievement`
--

DROP TABLE IF EXISTS `tb_achievement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_achievement` (
  `achieve_seq` bigint NOT NULL AUTO_INCREMENT,
  `achieve_category` varchar(255) DEFAULT NULL,
  `achieve_desc` varchar(255) DEFAULT NULL,
  `achieve_name` varchar(255) DEFAULT NULL,
  `achieve_subcategory` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`achieve_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_achievement`
--

LOCK TABLES `tb_achievement` WRITE;
/*!40000 ALTER TABLE `tb_achievement` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_achievement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_date` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `img_no` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `provider` varchar(255) DEFAULT NULL,
  `provider_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (11,'2022-11-18 17:10:03.255000','inoh0914@gmail.com',1,'홍인호','google','google_114503307606625601699'),(12,'2022-11-18 17:13:31.962000','kng06325@gmail.com',5,'하연~','google','google_114191722658743199147'),(13,'2022-11-18 17:13:55.089000','tmddnrdl333@gmail.com',6,'\"개\"','google','google_108550072086031923631'),(14,'2022-11-18 17:14:28.165000','tmddnrdl333@hanyang.ac.kr',1,'­정승욱','google','google_103725703077883845596'),(15,'2022-11-18 17:28:26.780000','inoh914@gmail.com',4,'콩','google','google_114130360291190013515'),(16,'2022-11-18 17:37:23.472000','dlwhd9635@gmail.com',7,'이종훈','google','google_101206887145451124598'),(17,'2022-11-18 18:05:51.374000','qmrzkd2315@gmail.com',0,'강병국','google','google_103823801447068230340'),(18,'2022-11-20 14:52:00.233000','201601759@inu.ac.kr',1,'강병국','google','google_115028006919700504316'),(19,'2022-11-20 20:58:24.313000','jaewan9074@gmail.com',1,'황재완 (Jaewan Hwang)','google','google_112466143376508476760');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-20 15:58:38
