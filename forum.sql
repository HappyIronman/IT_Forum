-- MySQL dump 10.13  Distrib 5.7.10, for Win32 (AMD64)
--
-- Host: localhost    Database: forum
-- ------------------------------------------------------
-- Server version	5.7.10

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
-- Table structure for table `blog`
--

DROP TABLE IF EXISTS `blog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `unique_id` varchar(45) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `title` varchar(45) NOT NULL,
  `content` mediumtext NOT NULL,
  `like_num` int(11) NOT NULL DEFAULT '0',
  `dislike_num` int(11) NOT NULL DEFAULT '0',
  `comment_num` int(11) NOT NULL DEFAULT '0',
  `view_num` int(11) NOT NULL DEFAULT '0',
  `is_private` tinyint(4) NOT NULL DEFAULT '0',
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blog`
--

LOCK TABLES `blog` WRITE;
/*!40000 ALTER TABLE `blog` DISABLE KEYS */;
/*!40000 ALTER TABLE `blog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `target_id` bigint(20) NOT NULL,
  `target_type` tinyint(4) NOT NULL COMMENT '0--->回复的为评论\n1--->回复的为moment\n2--->回复的为blog\n3--->回复的为question',
  `content` text NOT NULL,
  `reply_num` int(11) NOT NULL DEFAULT '0',
  `like_num` int(11) NOT NULL DEFAULT '0',
  `dislike_num` int(11) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `follow`
--

DROP TABLE IF EXISTS `follow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `follow` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `follower_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `disabled` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `follow`
--

LOCK TABLES `follow` WRITE;
/*!40000 ALTER TABLE `follow` DISABLE KEYS */;
/*!40000 ALTER TABLE `follow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `like_log`
--

DROP TABLE IF EXISTS `like_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `like_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `target_id` bigint(20) NOT NULL,
  `type` tinyint(4) NOT NULL COMMENT '0--->评论\n1--->moment\n2--->blog\n3--->question',
  `is_like` tinyint(4) NOT NULL DEFAULT '1',
  `disabled` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `like_log`
--

LOCK TABLES `like_log` WRITE;
/*!40000 ALTER TABLE `like_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `like_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `moment`
--

DROP TABLE IF EXISTS `moment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `moment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `unique_id` varchar(45) NOT NULL,
  `content` text NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `like_num` int(11) NOT NULL DEFAULT '0',
  `dislike_num` int(11) NOT NULL DEFAULT '0',
  `comment_num` int(11) NOT NULL DEFAULT '0',
  `share_num` int(11) NOT NULL DEFAULT '0',
  `view_num` int(11) NOT NULL DEFAULT '0',
  `is_private` tinyint(4) NOT NULL DEFAULT '0',
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='动态';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `moment`
--

LOCK TABLES `moment` WRITE;
/*!40000 ALTER TABLE `moment` DISABLE KEYS */;
INSERT INTO `moment` VALUES (2,'d879b8fadcce404e8c350753881faa43','这是我的第一条说说',123,0,0,0,0,0,0,0,'2018-03-06 21:21:06'),(3,'01fd192caf5f42eb8891b66c4f1b93ac','第二条',123,0,0,0,0,0,1,0,'2018-03-06 21:27:11'),(4,'2ca7fb5c6ee441b19742c1c111412b88','第三条哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈',123,0,0,0,0,0,0,0,'2018-03-06 21:37:49'),(5,'e97854d781af4d5b87d972568b7168de','4th',123,0,0,0,0,0,0,0,'2018-03-07 11:00:08'),(6,'37eb1ea4e13343819f546a9ba3482608','',123,0,0,0,0,0,0,0,'2018-03-07 11:00:42'),(7,'23487b670e0f4eb9b186229c4f1b6ad2','xsxsxcdsd',123,0,0,0,0,0,0,0,'2018-03-07 11:19:09'),(8,'dcbfa9c5fe614abda9eb06d59a1076fc','xqxqwxq',123,0,0,0,0,0,0,0,'2018-03-07 11:20:27'),(9,'a9fcd7bf61b44a079c40584f24fed9b5','xwx',123,0,0,0,0,0,0,0,'2018-03-07 11:20:57'),(10,'265e397904c146daa3556d128433e195','fwew',123,0,0,0,0,0,0,0,'2018-03-07 11:24:30'),(11,'f396c8a9ca374f2885351d4c70942758','iii',123,0,0,0,0,0,0,0,'2018-03-07 11:26:11'),(12,'5065ae36dcd94e38ae211ad47dc97861','dawdwd',123,0,0,0,0,0,0,0,'2018-03-07 11:28:52'),(13,'39ea91322ab542149ff2c9709758281e','dqdw',123,0,0,0,0,0,0,0,'2018-03-07 11:32:58'),(14,'ca35b6114a9b4479bb21c407b5dacf3f','dwdqd',123,0,0,0,0,0,0,0,'2018-03-07 11:34:10'),(15,'dafac833912042ff815830251c8a4d17','123',123,0,0,0,0,0,0,0,'2018-03-07 11:43:26');
/*!40000 ALTER TABLE `moment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `unique_id` varchar(45) NOT NULL,
  `content` text NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `answer_num` int(11) NOT NULL DEFAULT '0',
  `view_num` int(11) NOT NULL DEFAULT '0',
  `closed` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `search_log`
--

DROP TABLE IF EXISTS `search_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `search_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `keyword` varchar(100) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `hit` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `search_log`
--

LOCK TABLES `search_log` WRITE;
/*!40000 ALTER TABLE `search_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `search_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `time_line`
--

DROP TABLE IF EXISTS `time_line`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `time_line` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `article_id` bigint(20) NOT NULL,
  `type` tinyint(4) NOT NULL,
  `is_self` tinyint(4) NOT NULL DEFAULT '0',
  `is_new` tinyint(4) NOT NULL DEFAULT '1',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `time_line`
--

LOCK TABLES `time_line` WRITE;
/*!40000 ALTER TABLE `time_line` DISABLE KEYS */;
/*!40000 ALTER TABLE `time_line` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `unique_id` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `sex` tinyint(4) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `school` varchar(45) DEFAULT NULL,
  `intro` varchar(100) DEFAULT NULL,
  `profile` varchar(55) DEFAULT NULL,
  `follower_num` int(11) NOT NULL DEFAULT '0',
  `following_num` int(11) NOT NULL DEFAULT '0',
  `moment_num` int(11) NOT NULL DEFAULT '0',
  `blog_num` int(11) NOT NULL DEFAULT '0',
  `question_num` int(11) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (123,'asdf','ironman','',NULL,NULL,NULL,NULL,NULL,'',0,0,14,0,0,'2018-03-03 00:00:00');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_blog`
--

DROP TABLE IF EXISTS `user_blog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_blog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `blog_id` bigint(20) NOT NULL,
  `reason` varchar(45) DEFAULT NULL,
  `type` tinyint(4) NOT NULL DEFAULT '0',
  `is_private` tinyint(4) NOT NULL DEFAULT '0',
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_blog`
--

LOCK TABLES `user_blog` WRITE;
/*!40000 ALTER TABLE `user_blog` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_blog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_moment`
--

DROP TABLE IF EXISTS `user_moment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_moment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `moment_id` bigint(20) NOT NULL,
  `reason` varchar(45) DEFAULT NULL,
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0-->原创\n1-->转载',
  `is_private` tinyint(4) NOT NULL DEFAULT '0',
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_moment`
--

LOCK TABLES `user_moment` WRITE;
/*!40000 ALTER TABLE `user_moment` DISABLE KEYS */;
INSERT INTO `user_moment` VALUES (2,123,2,NULL,0,0,0,'2018-03-06 21:21:06'),(3,123,3,NULL,0,1,0,'2018-03-06 21:27:11'),(4,123,4,NULL,0,0,0,'2018-03-06 21:37:49'),(5,123,5,NULL,0,0,0,'2018-03-07 11:00:08'),(6,123,6,NULL,0,0,0,'2018-03-07 11:00:42'),(7,123,7,NULL,0,0,0,'2018-03-07 11:19:09'),(8,123,8,NULL,0,0,0,'2018-03-07 11:20:27'),(9,123,9,NULL,0,0,0,'2018-03-07 11:20:57'),(10,123,10,NULL,0,0,0,'2018-03-07 11:24:30'),(11,123,11,NULL,0,0,0,'2018-03-07 11:26:11'),(12,123,12,NULL,0,0,0,'2018-03-07 11:28:52'),(13,123,13,NULL,0,0,0,'2018-03-07 11:32:58'),(14,123,14,NULL,0,0,0,'2018-03-07 11:34:10'),(15,123,15,NULL,0,0,0,'2018-03-07 11:43:26');
/*!40000 ALTER TABLE `user_moment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `view_log`
--

DROP TABLE IF EXISTS `view_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `view_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `target_id` bigint(20) NOT NULL,
  `type` tinyint(4) NOT NULL,
  `length` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `view_log`
--

LOCK TABLES `view_log` WRITE;
/*!40000 ALTER TABLE `view_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `view_log` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-03-10 11:23:19
