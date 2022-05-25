-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: wechat
-- ------------------------------------------------------
-- Server version	8.0.23

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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_friends`
--

LOCK TABLES `chat_friends` WRITE;
/*!40000 ALTER TABLE `chat_friends` DISABLE KEYS */;
INSERT INTO `chat_friends` VALUES (1,20210520,20210521,'2021-05-05 13:36:49'),(2,20210520,20210522,'2021-05-05 13:36:53'),(3,20210520,20210523,'2021-05-05 13:36:59'),(5,20210520,20210525,'2021-05-05 13:37:08'),(6,20210523,20210522,'2021-05-05 13:39:17'),(7,20210520,20210524,'2021-05-07 10:13:08');
/*!40000 ALTER TABLE `chat_friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_group`
--

DROP TABLE IF EXISTS `chat_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_group` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `groupId` bigint NOT NULL COMMENT '群聊id',
  `group_userId` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_group`
--

LOCK TABLES `chat_group` WRITE;
/*!40000 ALTER TABLE `chat_group` DISABLE KEYS */;
INSERT INTO `chat_group` VALUES (1,20216,20210520),(2,20217,20210520),(3,20216,20210523),(4,20217,20210523),(5,20217,20210522),(6,20216,20210522);
/*!40000 ALTER TABLE `chat_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_info`
--

DROP TABLE IF EXISTS `group_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_info` (
  `groupId` bigint NOT NULL AUTO_INCREMENT,
  `groupName` varchar(45) NOT NULL,
  `groupAvatarUrl` varchar(45) DEFAULT 'img/avatar/default.jpg',
  PRIMARY KEY (`groupId`)
) ENGINE=InnoDB AUTO_INCREMENT=20218 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_info`
--

LOCK TABLES `group_info` WRITE;
/*!40000 ALTER TABLE `group_info` DISABLE KEYS */;
INSERT INTO `group_info` VALUES (20216,'快乐星球','img/avatar/default.jpg'),(20217,'海绵宝宝','img/avatar/default.jpg');
/*!40000 ALTER TABLE `group_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `userId` bigint NOT NULL AUTO_INCREMENT COMMENT 'useId',
  `username` varchar(18) NOT NULL COMMENT 'username',
  `password` varchar(125) NOT NULL COMMENT 'password',
  `avatarUrl` varchar(512) DEFAULT 'img/avatar/avatar0.jpg' COMMENT '用户头像',
  PRIMARY KEY (`userId`),
  UNIQUE KEY `userId_UNIQUE` (`userId`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=20210526 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (20210520,'dahuang','C/CyUHB9q57Vw71VyWEjBFY9u+xUNq9JwPvWvhny3dMcaf6/gboAk4B1cDeCIQNA','img/avatar/00b9eabeb4444806bb4870e5c6154e48.jpg'),(20210521,'dahei','C/CyUHB9q57Vw71VyWEjBFY9u+xUNq9JwPvWvhny3dMcaf6/gboAk4B1cDeCIQNA','img/avatar/avatar2.jpg'),(20210522,'dabai','C/CyUHB9q57Vw71VyWEjBFY9u+xUNq9JwPvWvhny3dMcaf6/gboAk4B1cDeCIQNA','img/avatar/avatar3.jpg'),(20210523,'huihui','C/CyUHB9q57Vw71VyWEjBFY9u+xUNq9JwPvWvhny3dMcaf6/gboAk4B1cDeCIQNA','img/avatar/4ce922601ddd428fac44db7634e010b1.jpg'),(20210524,'caicai','C/CyUHB9q57Vw71VyWEjBFY9u+xUNq9JwPvWvhny3dMcaf6/gboAk4B1cDeCIQNA','img/avatar/avatar0.jpg'),(20210525,'feifei','5upGe+zy/2qBea5nTS+kFrNYEMLr6cFdt5BOejfVhaAVG2LSKzdzNzRUTZfd7Eho','img/avatar/avatar0.jpg');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'wechat'
--

--
-- Dumping routines for database 'wechat'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-17 19:14:59
