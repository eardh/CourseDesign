CREATE DATABASE  IF NOT EXISTS `mobile_chat` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `mobile_chat`;
-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: mobile_chat
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `chat_friends`
--

DROP TABLE IF EXISTS `chat_friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_friends` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `userId` bigint NOT NULL COMMENT '用户',
  `fuserId` bigint NOT NULL COMMENT '好友',
  `addtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_friends`
--

LOCK TABLES `chat_friends` WRITE;
/*!40000 ALTER TABLE `chat_friends` DISABLE KEYS */;
INSERT INTO `chat_friends` VALUES (1,20210520,20210521,'2021-05-05 13:36:49'),(2,20210520,20210522,'2021-05-05 13:36:53'),(3,20210520,20210523,'2021-05-05 13:36:59'),(5,20210520,20210525,'2021-05-05 13:37:08'),(6,20210523,20210522,'2021-05-05 13:39:17'),(7,20210520,20210524,'2021-05-07 10:13:08'),(8,20210520,20210526,'2021-12-09 04:56:50'),(9,20210523,20210526,'2021-12-09 04:58:40'),(10,20210523,20210524,'2021-12-09 05:01:55'),(11,20210523,20210523,'2021-12-09 05:07:11'),(12,20210523,20210525,'2021-12-09 05:07:23'),(13,20210523,20210528,'2021-12-09 12:01:08'),(14,20210523,20210530,'2021-12-10 12:30:24'),(15,20210523,20210529,'2021-12-10 12:30:41'),(16,20210523,20210527,'2021-12-10 12:31:06'),(17,20210523,20210521,'2021-12-10 12:31:47'),(18,20210520,20210530,'2021-12-10 12:32:24'),(19,20210520,20210529,'2021-12-11 12:19:15');
/*!40000 ALTER TABLE `chat_friends` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-11 21:39:16