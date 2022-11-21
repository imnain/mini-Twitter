-- MySQL dump 10.13  Distrib 8.0.30, for Linux (x86_64)
--
-- Host: localhost    Database: mini_twitter_db
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `followers`
--

DROP TABLE IF EXISTS `followers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `followers` (
  `id` int NOT NULL,
  `fid` int DEFAULT NULL,
  `sid` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `followers_UNIQUE` (`fid`,`sid`),
  KEY `fk_followers_1_idx` (`fid`),
  KEY `fk_followers_2_idx` (`sid`),
  CONSTRAINT `fk_followers_1` FOREIGN KEY (`fid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_followers_2` FOREIGN KEY (`sid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `followers`
--

LOCK TABLES `followers` WRITE;
/*!40000 ALTER TABLE `followers` DISABLE KEYS */;
INSERT INTO `followers` VALUES (63,9,53),(62,44,53),(3,47,10),(70,53,10),(98,53,44),(71,53,45),(85,53,46),(69,53,60),(101,100,10);
/*!40000 ALTER TABLE `followers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (105);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `likes`
--

DROP TABLE IF EXISTS `likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `likes` (
  `lid` int NOT NULL AUTO_INCREMENT,
  `tid` int DEFAULT NULL,
  `uid` int DEFAULT NULL,
  PRIMARY KEY (`lid`),
  UNIQUE KEY `UQ_UserId_TweetId` (`uid`,`tid`),
  KEY `tid_idx` (`tid`),
  KEY `fk_like_1_idx` (`uid`),
  CONSTRAINT `fk_like_0` FOREIGN KEY (`tid`) REFERENCES `tweet` (`t_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_like_1` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `likes`
--

LOCK TABLES `likes` WRITE;
/*!40000 ALTER TABLE `likes` DISABLE KEYS */;
INSERT INTO `likes` VALUES (6,48,47),(98,54,47),(99,1,53),(90,50,53),(89,51,53),(86,52,53),(92,54,53),(104,48,100);
/*!40000 ALTER TABLE `likes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tweet`
--

DROP TABLE IF EXISTS `tweet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tweet` (
  `t_id` int NOT NULL,
  `description` varchar(5000) NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`t_id`),
  UNIQUE KEY `t_id_UNIQUE` (`t_id`),
  KEY `FKpdu2nnuuo0ld9sfsxwicpqti3` (`user_id`),
  CONSTRAINT `FKpdu2nnuuo0ld9sfsxwicpqti3` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tweet`
--

LOCK TABLES `tweet` WRITE;
/*!40000 ALTER TABLE `tweet` DISABLE KEYS */;
INSERT INTO `tweet` VALUES (1,'this is the first tweet.',47),(48,'I am typing my second tweet now.',47),(49,'jj,hh',47),(50,'welcome to twitter my friend.',47),(51,'how\'s everything going...',47),(52,'great day to tweet',47),(54,'this is tweet',53),(62,'this is another tweet.',53);
/*!40000 ALTER TABLE `tweet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL,
  `bio` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (9,'this is mandeep','mandeepnain84@gmail.com','Sandeep Nain','nain','ROLE_USER'),(10,'cricket','rohit@sharma.com','Rohit Cricket','cricket','ROLE_USER'),(44,'i am jj','jj@jj.com','Rohan Sharma','asdlfja','ROLE_USER'),(45,'i am john ','john@gmail.com','John Wick','johnjohn','ROLE_USER'),(46,'i am john wick','wick@gmail.com','Mr Wick','$2a$10$AKw6vvf8/vYjxwRyokRI8OKB3aCO15g8V6E/jxoaMFdIwV0GNLdj2','ROLE_USER'),(47,'I am human','human@gmail.com','Human Being','$2a$10$E2vt6HtSOFMDiwm0fG.XUeVjxu.pmOB93puIaa3kMEzSe45uonh5S','ROLE_USER'),(53,'i am mandeep','mandeep@gmail.com','Mandeep Nain','$2a$10$nvb11IxMt3MrDHt3WOWgLOb7FE9UwAmDNsfMT.Sn.9g2rtiuDViqm','ROLE_USER'),(60,'i am shanky','shanky@gmail.com','Shanky Singh','$2a$10$NSfoRFUjk3xeMjWbgMAQz.f4dsnqWyZZhvLL8OH4cGTH.o12.QqoC','ROLE_USER'),(100,'Hi There!!','vishalsheokand007@gmail.com','Vishal Sheokand','$2a$10$orzopqSkhCz3ac.kQCAezOzoBgs21yw4qgC8Y8OcuypmIw6WggGlu','ROLE_USER');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_tweet`
--

DROP TABLE IF EXISTS `user_tweet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_tweet` (
  `user_id` int NOT NULL,
  `tweet_t_id` int NOT NULL,
  UNIQUE KEY `UK_imwpcwkp1e98n1hc06tb8kal4` (`tweet_t_id`),
  KEY `FKjh9645m0lvscfin504cha0x4o` (`user_id`),
  CONSTRAINT `FK4w0a1xliss33o75emtr004hbt` FOREIGN KEY (`tweet_t_id`) REFERENCES `tweet` (`t_id`),
  CONSTRAINT `FKjh9645m0lvscfin504cha0x4o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_tweet`
--

LOCK TABLES `user_tweet` WRITE;
/*!40000 ALTER TABLE `user_tweet` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_tweet` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-21 12:27:30
