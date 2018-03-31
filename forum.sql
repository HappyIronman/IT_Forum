-- MySQL dump 10.13  Distrib 5.7.21, for macos10.13 (x86_64)
--
-- Host: localhost    Database: forum
-- ------------------------------------------------------
-- Server version	5.7.21

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
-- Table structure for table `SEQUENCE_UNIQUE_ID_00`
--

DROP TABLE IF EXISTS `SEQUENCE_UNIQUE_ID_00`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SEQUENCE_UNIQUE_ID_00` (
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
-- Dumping data for table `SEQUENCE_UNIQUE_ID_00`
--

LOCK TABLES `SEQUENCE_UNIQUE_ID_00` WRITE;
/*!40000 ALTER TABLE `SEQUENCE_UNIQUE_ID_00` DISABLE KEYS */;
/*!40000 ALTER TABLE `SEQUENCE_UNIQUE_ID_00` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `about_me`
--

LOCK TABLES `about_me` WRITE;
/*!40000 ALTER TABLE `about_me` DISABLE KEYS */;
INSERT INTO `about_me` VALUES (36,123,37,0,'1',1,'2018-03-18 17:37:58'),(50,123,51,0,'1',1,'2018-03-18 22:19:08'),(51,123,52,0,'1',1,'2018-03-18 22:19:13'),(52,123,53,0,'1',1,'2018-03-18 22:19:15'),(53,123,54,0,'1',1,'2018-03-18 22:19:17'),(54,123,55,0,'1',1,'2018-03-18 22:19:19'),(55,123,56,0,'1',1,'2018-03-18 22:19:23'),(56,123,57,0,'1',0,'2018-03-18 22:19:24'),(57,123,58,0,'1',1,'2018-03-18 22:22:54'),(58,123,66,0,'1',1,'2018-03-18 22:43:45'),(59,123,67,0,'1',1,'2018-03-18 22:43:49'),(60,123,68,0,'1',0,'2018-03-18 22:43:57'),(61,123,69,0,'1',0,'2018-03-18 22:54:56'),(62,123,70,0,'1',1,'2018-03-18 22:56:03'),(63,123,71,0,'1',1,'2018-03-19 23:40:01'),(64,123,72,0,'1',0,'2018-03-19 23:40:12'),(65,123,73,0,'1',0,'2018-03-21 22:36:10'),(66,123,74,0,'1',0,'2018-03-23 19:50:43'),(67,123,75,0,'1',0,'2018-03-25 22:25:29'),(68,123,76,0,'1',0,'2018-03-25 22:25:31');
/*!40000 ALTER TABLE `about_me` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blog`
--

LOCK TABLES `blog` WRITE;
/*!40000 ALTER TABLE `blog` DISABLE KEYS */;
INSERT INTO `blog` VALUES (6,'8bd12abaadb945ba95a375b366eb551a',123,'vue学习','<p>双大括号会将数据解释为普通文本，而非 HTML 代码。为了输出真正的 HTML，你需要使用&nbsp;<code>v-html</code>&nbsp;指令：</p><figure><table><tbody><tr><td><pre>&lt;p&gt;Using mustaches: {{ rawHtml }}&lt;/p&gt;<br>&lt;p&gt;Using v-html directive: &lt;span v-html=\"rawHtml\"&gt;&lt;/span&gt;&lt;/p&gt;<br></pre></td></tr></tbody></table></figure><div id=\"example1\"><p>Using mustaches: &lt;span&gt;This should be red.&lt;/span&gt;</p><p>Using v-html directive:&nbsp;This should be red.</p></div><p>这个&nbsp;<code>span</code>&nbsp;的内容将会被替换成为属性值&nbsp;<code>rawHtml</code>，直接作为 HTML——会忽略解析属性值中的数据绑定。注意，你不能使用&nbsp;<code>v-html</code>&nbsp;来复合局部模板，因为 Vue 不是基于字符串的模板引擎。反之，对于用户界面 (UI)，组件更适合作为可重用和可组合的基本单位。</p><p>你的站点上动态渲染的任意 HTML 可能会非常危险，因为它很容易导致&nbsp;<a href=\"https://en.wikipedia.org/wiki/Cross-site_scripting\" target=\"_blank\" rel=\"noopener\">XSS 攻击</a>。请只对可信内容使用 HTML 插值，<strong>绝不要</strong>对用户提供的内容使用插值。</p><h3 id=\"特性\"><a href=\"https://cn.vuejs.org/v2/guide/syntax.html#%E7%89%B9%E6%80%A7\" title=\"特性\" text-decoration:=\"\" none;=\"\" color:=\"\" rgb(44,=\"\" 62,=\"\" 80);=\"\" font-weight:=\"\" 600;=\"\" pointer-events:=\"\" auto;\"=\"\">特性</a></h3><p>Mustache 语法不能作用在 HTML 特性上，遇到这种情况应该使用&nbsp;<a href=\"https://cn.vuejs.org/v2/api/#v-bind\">v-bind 指令</a>：</p><figure><table><tbody><tr><td><pre>&lt;div v-bind:id=\"dynamicId\"&gt;&lt;/div&gt;<br></pre></td></tr></tbody></table></figure><p>在布尔特性的情况下，它们的存在即暗示为&nbsp;<code>true</code>，<code>v-bind</code>&nbsp;工作起来略有不同，在这个例子中：</p><figure><table><tbody><tr><td><pre>&lt;button v-bind:disabled=\"isButtonDisabled\"&gt;Button&lt;/button&gt;<br></pre></td></tr></tbody></table></figure><p>如果&nbsp;<code>isButtonDisabled</code>&nbsp;的值是&nbsp;<code>null</code>、<code>undefined</code>&nbsp;或&nbsp;<code>false</code>，则&nbsp;<code>disabled</code>&nbsp;特性甚至不会被包含在渲染出来的&nbsp;<code>&lt;button&gt;</code>&nbsp;元素中。vv</p><p><br></p>',1,0,0,0,0,0,0,0,'2018-03-11 19:18:07.409'),(7,'29ff939a84c84a8fa88557a2619e9b9a',123,'vue学习2','<p>数据绑定最常见的形式就是使用“Mustache”语法 (双大括号) 的文本插值：</p><figure><table><tbody><tr><td><pre>&lt;span&gt;Message: {{ msg }}&lt;/span&gt;<br></pre></td></tr></tbody></table></figure><p>Mustache 标签将会被替代为对应数据对象上&nbsp;<code>msg</code>&nbsp;属性的值。无论何时，绑定的数据对象上&nbsp;<code>msg</code>&nbsp;属性发生了改变，插值处的内容都会更新。</p><p>通过使用&nbsp;<a href=\"https://cn.vuejs.org/v2/api/#v-once\">v-once 指令</a>，你也能执行一次性地插值，当数据改变时，插值处的内容不会更新。但请留心这会影响到该节点上的其它数据绑定：</p><figure><table><tbody><tr><td><pre>&lt;span v-once&gt;这个将不会改变: {{ msg }}&lt;/span&gt;</pre></td></tr></tbody></table></figure><p><br></p>',1,0,0,0,0,0,0,0,'2018-03-11 19:19:51.181'),(8,'b53acbc215f8448ba90fe51f8bfc18b2',123,'转发vue学习','<p data-v-fb605348=\"\">不错哦哦</p><p><br></p>',0,1,0,0,0,0,1,0,'2018-03-19 23:24:43.403');
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT INTO `image` VALUES (1,'c7945955e02a45bc86c553f2d0be40b6.jpg',123,46,1,0,'2018-03-25 20:24:44'),(2,'d6c8a57be6de4724a1062e747c82d253.png',123,46,1,0,'2018-03-25 20:24:44'),(3,'70594409f7cb49f4a3e13678b8d5e328.jpg',123,47,1,0,'2018-03-25 22:24:59');
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `like_log`
--

LOCK TABLES `like_log` WRITE;
/*!40000 ALTER TABLE `like_log` DISABLE KEYS */;
INSERT INTO `like_log` VALUES (51,123,42,1,1,1,'2018-03-18 22:19:08'),(52,123,41,1,0,1,'2018-03-18 22:19:13'),(53,123,41,1,1,1,'2018-03-18 22:19:15'),(54,123,42,1,0,1,'2018-03-18 22:19:17'),(55,123,42,1,1,1,'2018-03-18 22:19:19'),(56,123,42,1,0,1,'2018-03-18 22:19:23'),(57,123,41,1,1,0,'2018-03-18 22:19:24'),(58,123,43,1,0,1,'2018-03-18 22:22:54'),(66,123,7,2,1,1,'2018-03-18 22:43:45'),(67,123,7,2,0,1,'2018-03-18 22:43:49'),(68,123,6,2,1,0,'2018-03-18 22:43:57'),(69,123,7,2,1,0,'2018-03-18 22:54:56'),(70,123,42,1,1,1,'2018-03-18 22:56:03'),(71,123,43,1,1,1,'2018-03-19 23:39:58'),(72,123,42,1,0,0,'2018-03-19 23:40:12'),(73,123,43,1,0,0,'2018-03-21 22:36:07'),(74,123,8,2,0,0,'2018-03-23 19:50:43'),(75,123,48,1,0,0,'2018-03-25 22:25:29'),(76,123,47,1,1,0,'2018-03-25 22:25:31');
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
  `is_share` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0—>origin\n1—>share',
  `is_contain_pic` tinyint(4) NOT NULL DEFAULT '0',
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 COMMENT='动态';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `moment`
--

LOCK TABLES `moment` WRITE;
/*!40000 ALTER TABLE `moment` DISABLE KEYS */;
INSERT INTO `moment` VALUES (41,'3405569b678140e98a4a76b7948f4904','1您好！很高兴能为您解答， 微信的英语版，假设针对的是西方用户，那么在西方人文化习惯里，更喜欢，更舒服记录自己生活的东西是“瞬间”，所以用moments就很合适，当然这也和产品本身要表达的生活理念有关。',123,1,0,0,1,0,0,0,0,0,'2018-03-11 19:16:32'),(42,'75a464820d4e4bc2875a2c838cd5ea60','2注意到下面的中英文字幕，比如Big Bang Theory里面的翻译有时候很有意思，把comedy club翻译成德云社。在同声传译中，也要结合语境，语言习惯。例如多年前很有名温总理的翻译，将“九死其犹未悔”翻译为“rather die a thousand times” ',123,0,1,0,0,0,0,0,0,0,'2018-03-11 19:17:04'),(43,'895584982328494087a477f3989d10e1','转发1呢',123,0,1,0,0,0,0,1,0,0,'2018-03-11 19:18:32'),(44,'78b0be6d03f44c42895767c3a2054f9a','deddwed',123,0,0,0,0,0,0,0,0,0,'2018-03-25 20:09:22'),(46,'2ce9004d0d574dedbfb00f565cd344dc','没啥想说的',123,0,0,0,0,0,0,0,1,0,'2018-03-25 20:24:44'),(47,'22fd7e271cc04574ac5c96480a077a09','唉',123,1,0,0,0,0,0,0,1,0,'2018-03-25 22:24:59'),(48,'ac853a9f44564c9dbbdacbc840188dc7','zmb',123,0,1,0,0,0,0,0,0,0,'2018-03-25 22:25:22');
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
  `is_share` tinyint(4) NOT NULL DEFAULT '0',
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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `share`
--

LOCK TABLES `share` WRITE;
/*!40000 ALTER TABLE `share` DISABLE KEYS */;
INSERT INTO `share` VALUES (14,43,41,1,0,'2018-03-11 19:18:32'),(15,8,6,2,0,'2018-03-19 23:24:43');
/*!40000 ALTER TABLE `share` ENABLE KEYS */;
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
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `time_line`
--

LOCK TABLES `time_line` WRITE;
/*!40000 ALTER TABLE `time_line` DISABLE KEYS */;
INSERT INTO `time_line` VALUES (28,123,41,1,1,1,0,'2018-03-11 19:16:32'),(29,123,42,1,1,1,0,'2018-03-11 19:17:04'),(30,123,6,2,1,1,0,'2018-03-11 19:18:07'),(31,123,43,1,1,1,0,'2018-03-11 19:18:32'),(32,123,7,2,1,1,0,'2018-03-11 19:19:51'),(33,123,8,2,1,1,0,'2018-03-19 23:24:43'),(34,123,44,1,1,1,0,'2018-03-25 20:09:22'),(35,123,46,1,1,1,0,'2018-03-25 20:24:44'),(36,123,47,1,1,1,0,'2018-03-25 22:24:59'),(37,123,48,1,1,1,0,'2018-03-25 22:25:22');
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
INSERT INTO `user` VALUES (123,'asdf','ironman','123456',1,'18340804015','tracy_zh@qq.com','DLUT','A lonely Child','',3,8,9,4,1,'2018-03-03 00:00:00');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
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
  `disabled` tinyint(4) NOT NULL DEFAULT '0',
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

-- Dump completed on 2018-03-26 21:48:53
