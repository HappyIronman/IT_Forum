-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
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
-- Table structure for table `about_me`
--

DROP TABLE IF EXISTS `about_me`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `about_me` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `log_id` bigint(20) NOT NULL,
  `type` int(11) NOT NULL,
  `is_new` varchar(45) NOT NULL,
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=174 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `share_num` int(11) NOT NULL DEFAULT '0',
  `comment_num` int(11) NOT NULL DEFAULT '0',
  `view_num` int(11) NOT NULL DEFAULT '0',
  `is_private` tinyint(4) NOT NULL DEFAULT '0',
  `is_share` tinyint(4) NOT NULL DEFAULT '0',
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `unique_id` varchar(45) NOT NULL,
  `content` text NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `like_num` int(11) NOT NULL DEFAULT '0',
  `dislike_num` int(11) NOT NULL DEFAULT '0',
  `reply_id` bigint(20) NOT NULL,
  `type` tinyint(4) NOT NULL COMMENT '0--->回复的为评论\n1--->回复的为moment\n2--->回复的为blog\n3--->回复的为question',
  `comment_num` int(11) NOT NULL DEFAULT '0',
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(155) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `article_id` bigint(20) NOT NULL,
  `type` tinyint(4) NOT NULL,
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `is_share` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0—>origin\n1—>share',
  `is_contain_pic` tinyint(4) NOT NULL DEFAULT '0',
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8 COMMENT='动态';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `unique_id` varchar(45) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `title` varchar(100) NOT NULL,
  `content` text NOT NULL,
  `comment_num` int(11) NOT NULL DEFAULT '0',
  `view_num` int(11) NOT NULL DEFAULT '0',
  `like_num` int(11) NOT NULL DEFAULT '0',
  `dislike_num` int(11) NOT NULL DEFAULT '0',
  `closed` tinyint(4) NOT NULL DEFAULT '0',
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `sequence_unique_id_00`
--

DROP TABLE IF EXISTS `sequence_unique_id_00`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sequence_unique_id_00` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `value` int(11) DEFAULT NULL,
  `min_value` int(11) DEFAULT NULL,
  `max_value` int(11) DEFAULT NULL,
  `step` int(11) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `share`
--

DROP TABLE IF EXISTS `share`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `share` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `article_id` bigint(20) NOT NULL,
  `origin_id` bigint(20) NOT NULL,
  `type` tinyint(4) NOT NULL,
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
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
  `disabled` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `disabled` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2051 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-05 18:18:42
