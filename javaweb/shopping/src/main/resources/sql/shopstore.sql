-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: shopstore
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
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `customerID` bigint NOT NULL,
  `merchantID` bigint NOT NULL,
  `productID` bigint NOT NULL,
  `productName` varchar(100) NOT NULL,
  `count` int NOT NULL,
  `totalMoney` double NOT NULL,
  `date` datetime DEFAULT NULL,
  `serial_number` varchar(100) NOT NULL,
  `send` varchar(20) NOT NULL,
  `received` varchar(20) NOT NULL,
  `confirm` varchar(20) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2031 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (2021,20210620,20210621,2022,'香蕉',2,17.96,'2021-06-15 20:59:42','5e8b3b3051c34b5ca8ab89ae39e3a4a71623761982559','1','0','0'),(2022,20210620,20210618,2023,'绿心猕猴桃奇异果',2,59.6,'2021-06-15 20:59:42','ae077ecf6a174e61b342d8f941e45f891623761982591','1','1','1'),(2023,20210620,20210621,2028,'海南金鲳鱼',2,91.3,'2021-06-15 21:13:31','21c83875e1dc43e48ca0a25fde2f3b181623762811184','1','1','1'),(2024,20210620,20210618,2027,'水果莲蓬',2,117.6,'2021-06-16 17:41:43','ab84278f5c0d4623b1c4be6010c0c17f1623836503860','0','0','0'),(2025,20210620,20210618,2023,'绿心猕猴桃奇异果',1,29.8,'2021-06-16 17:41:44','3c9d8b639f7f4adfad1b97c0437acb921623836504018','0','0','0'),(2026,20210620,20210618,2031,'全自动滚筒洗衣机',1,4539,'2021-06-16 17:41:44','446a532926d846b1b07da1da8f5c2eda1623836504080','1','0','0'),(2027,20210620,20210621,2022,'香蕉',4,35.92,'2021-06-16 17:41:44','b5227617d6004ef0a60d40c947af29341623836504137','1','0','0'),(2028,20210620,20210618,2021,'苹果',1,10.7,'2021-06-16 17:46:42','9abe7f1fae8e442a9893adb9f7eb0a481623836802821','0','0','0'),(2029,20210620,20210618,2027,'水果莲蓬',1,58.8,'2021-06-17 11:11:01','df542e14df53476a8277e6146d5ba4521623899461888','0','0','0'),(2030,20210620,20210618,2023,'绿心猕猴桃奇异果',3,89.4,'2021-06-17 11:11:01','6842fc1229a84b95964b23d04bff740b1623899461912','0','0','0');
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `productName` varchar(45) NOT NULL,
  `merchantID` bigint NOT NULL,
  `storeID` bigint NOT NULL,
  `stock` int NOT NULL,
  `price` double NOT NULL,
  `type` varchar(45) NOT NULL,
  `addtime` datetime DEFAULT NULL,
  `description` varchar(10000) DEFAULT NULL,
  `image` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2069 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (2021,'苹果',20210618,1,9999,10.7,'FRESH_FOOD','2021-06-15 17:20:32','苹果中含有丰富的有机酸和纤维素，这些营养物质可以加快肠胃的蠕动，促进排便。经常食用苹果，就可以轻松排出体内毒素，同时也能帮助我们缓解大便干燥的症状。','http://localhost:9090/file/images/41580018273e4f5f9b537f9a76bd6f20.jpg'),(2022,'香蕉',20210621,1,9994,8.98,'FRESH_FOOD','2021-06-15 17:23:36','香蕉味香、富含营养，植株为大型草本，从根状茎发出，由叶鞘下部形成高3-6公尺的假杆；叶长圆形至椭圆形，宽65公分，10-20枚簇生茎顶。','http://localhost:9090/file/images/378e5f4e470a412a8f2639d507cc6a8a.jpg'),(2023,'绿心猕猴桃奇异果',20210618,1,9994,29.8,'FRESH_FOOD','2021-06-15 17:30:41','陕西 绿心猕猴桃奇异果 6粒装特级果 单果90-110g 生鲜水果 健康轻食','http://localhost:9090/file/images/d55e629a22ce49908ac992af8424aaad.jpg'),(2024,'国产大虾',20210618,1,10000,129,'FRESH_FOOD','2021-06-15 17:33:29','健康轻食 禧美 国产大虾 净重1.8kg 90-108只/盒 对虾 烧烤 火锅 生鲜 海鲜水产','http://localhost:9090/file/images/9ee46d67ead14c129f34fa7e67e4ea06.jpg'),(2025,'云南甜玉米',20210618,1,10000,32.9,'FRESH_FOOD','2021-06-15 17:34:57','绿鲜知 云南甜玉米 水果玉米 新鲜玉米棒 约2.5kg 烧烤食材 产地直供 新鲜蔬菜 健康轻食','http://localhost:9090/file/images/6ef850a4647f4103a8fb71a2c2c2e6a1.jpg'),(2026,'豆腐串',20210621,1,10000,38,'FRESH_FOOD','2021-06-15 17:36:21','半亩庄园 豆腐串干串豆干兰花干关东煮火锅豆腐 【15cm】50串家庭装','http://localhost:9090/file/images/7ea8a33c94dd4cc38001b480ffdea21c.jpg'),(2027,'水果莲蓬',20210618,1,9997,58.8,'FRESH_FOOD','2021-06-15 17:37:38','【加冰顺丰空运】新鲜太空水果莲蓬 洪湖新鲜莲蓬 嫩莲蓬鲜莲子带莲蓬现摘现发 2斤装','http://localhost:9090/file/images/bd5b9149bf6b4205aa146e847c2ccc17.jpg'),(2028,'海南金鲳鱼',20210621,1,9998,45.65,'FRESH_FOOD','2021-06-15 17:38:57','翔泰 国产海南金鲳鱼700g（2条）火锅食材 无公害认证 全程可追溯 鱼类 生鲜 BAP认证 海鲜水产','http://localhost:9090/file/images/0f3ff6e96141418ea77737f3a60520fd.jpg'),(2029,'麻辣小龙虾尾',20210618,1,10000,69.9,'FRESH_FOOD','2021-06-15 17:40:20','红功夫 麻辣小龙虾尾（每盒250g 33-40只）豪华2盒装 火锅食材 海鲜水产','http://localhost:9090/file/images/8b2a0ac83a0c4cf4aee39dc1a1ff1c41.jpg'),(2030,'智能平板电视',20210618,1,10000,2779,'HOUSEHOLD_APPLICATION','2021-06-15 17:43:28','海信（Hisense）55E3F-Y 55英寸4K超高清悬浮全面屏 智慧语音超薄机身 智能平板电视','http://localhost:9090/file/images/52a5eaa83189410bade4a329690b8485.jpg'),(2031,'全自动滚筒洗衣机',20210618,1,9999,4539,'HOUSEHOLD_APPLICATION','2021-06-15 17:44:43','LG 10KG全自动滚筒洗衣机 AI智能DD直驱变频 蒸汽除菌速净喷淋 14分快洗 FCK10Y4T','http://localhost:9090/file/images/eb857837fc22475abe2dcce26be738ca.jpg'),(2032,'抽油烟机燃气灶',20210621,1,10000,2698.9,'HOUSEHOLD_APPLICATION','2021-06-15 17:46:07','美的（Midea）家用21大吸力侧吸式自清洗自动开合JC506抽油烟机燃气灶具热水器消毒柜厨房多件套 烟灶两件套 天然气','http://localhost:9090/file/images/f03d2a8b63cb457f88fa845171bcc9fd.jpg'),(2033,'烤箱',20210618,1,10000,3130.9,'HOUSEHOLD_APPLICATION','2021-06-15 18:47:11','乐创(lecon)商用烤箱大型燃气电热蛋挞烤炉烘培西点月饼蛋糕面包店大容量多规格可定制 二层二盘智能电脑版220V','http://localhost:9090/file/images/8fbe51f726714c8893c8265100178315.jpg'),(2034,'电视音响',20210621,1,10000,509,'HOUSEHOLD_APPLICATION','2021-06-15 18:52:45','宝华韦健（B&W）FORMATION FLEX无线家庭影院音响音箱电视音响蓝牙音响电视回音壁音响 黑色','http://localhost:9090/file/images/271b5f799e39469e97001806cc21d264.jpg'),(2035,'扬声器音响',20210621,1,10000,4100.9,'HOUSEHOLD_APPLICATION','2021-06-15 18:54:18','KEF Q350 HiFi扬声器音响 家庭影院音响 书架环绕无源音箱 1对 黑色','http://localhost:9090/file/images/1c5fe62030014580a195b01f95cf6cd6.jpg'),(2036,'十字对开门冰箱',20210618,1,10000,2999,'HOUSEHOLD_APPLICATION','2021-06-15 18:55:32','美的(Midea)450升十字对开门冰箱变频风冷无霜纤薄双开门四开门冰箱BCD-450WTPM(E) 银色','http://localhost:9090/file/images/f06ba1c6451c4d52a17759491c62de33.jpg'),(2037,'蒸烤一体Z20-ZK',20210621,1,10000,9999.99,'HOUSEHOLD_APPLICATION','2021-06-15 18:57:48','美大（Meida）集成灶无油网设计蒸烤一体Z20-ZK燃气灶抽油烟机烤箱蒸箱集成一体 黑色 天然气','http://localhost:9090/file/images/4784da6ee36f4ffcb57ac3aa843c9260.jpg'),(2038,'美的电热水器',20210618,1,10000,1999,'HOUSEHOLD_APPLICATION','2021-06-15 18:59:21','美的电热水器60升80升智能家电家用储水安全速热节能洗澡灭菌电子镁棒真正免清洗TG8系列 F6030-TG8【60升】','http://localhost:9090/file/images/b092f9da23f4434d83ead3edcd6f32d9.jpg'),(2039,'蓝牙耳机',20210621,1,10000,699,'ELECTRONIC_PRODUCT','2021-06-15 19:01:12','三星（SAMSUNG）Galaxy Buds Pro 主动降噪真无线蓝牙耳机/IPX7防水/运动音乐手机安卓耳机 幽夜黑','http://localhost:9090/file/images/f7316df1375a434285b5c003e8e081d6.jpg'),(2040,'微单套机',20210618,1,10000,19999,'ELECTRONIC_PRODUCT','2021-06-15 19:02:29','尼康（Nikon）Z 7II（Z7 2/Z72）全画幅微单 微单套机 （24-70mm f/4 微单镜头 约4,575万有效像素 5轴防抖）','http://localhost:9090/file/images/ef43a9954c5146d7854399e4a5e3e008.jpg'),(2041,'监控器',20210618,1,10000,229.9,'ELECTRONIC_PRODUCT','2021-06-15 19:03:58','小米摄像头 云台2K版 家用监控器 红外夜视 2K超高清 智能摄像机 300W像素升级版','http://localhost:9090/file/images/9cc4662aaf294d68ad81a46d53eb7b31.jpg'),(2042,'智能手环',20210618,1,10000,199,'ELECTRONIC_PRODUCT','2021-06-15 19:04:58','OPPO 手环活力版 智能手环 运动手环 心率手环 血氧睡眠监测/支持第三方支付 通用小米苹果华为手机 蓝色风暴','http://localhost:9090/file/images/254bded19fbb4eb394601c5a90f81132.jpg'),(2043,'电脑主机 ',20210618,1,10000,19999.8,'ELECTRONIC_PRODUCT','2021-06-15 19:08:49','Apple iMac 【2020新款 】27 英寸5K屏 3.3GHz 六核十代 i5/8GB/512GB固态/RP5300 一体式电脑主机 MXWU2CH/A','http://localhost:9090/file/images/2da254a336c64b2bb32db233759c17d0.jpg'),(2044,'平板电脑',20210618,1,10000,4599,'ELECTRONIC_PRODUCT','2021-06-15 19:09:49','Apple iPad Air 10.9英寸 平板电脑（ 2020年新款 64G WLAN版/A14芯片/触控ID/全面屏MYFR2CH/A）绿色','http://localhost:9090/file/images/8683e359723f4ec9bdc70322f88dec47.jpg'),(2045,'笔记本电脑',20210618,1,10000,20092.9,'ELECTRONIC_PRODUCT','2021-06-15 19:10:58','Apple 2019新品 MacBook Pro 16【带触控栏】九代八核i9 16G 1TB 银色 笔记本电脑 轻薄本 MVVM2CH/A','http://localhost:9090/file/images/81362d8e38ca43238f4a9bd218fbf72d.jpg'),(2046,'Apple iPhone 12',20210621,1,10000,6799,'ELECTRONIC_PRODUCT','2021-06-15 19:15:00','Apple iPhone 12 (A2404) 128GB 紫色 支持移动联通电信5G 双卡双待手机','http://localhost:9090/file/images/392a9bbef2ec4a418150b436dc2d85de.jpg'),(2047,'OPPO Reno5 5G',20210618,1,10000,2699,'ELECTRONIC_PRODUCT','2021-06-15 19:16:12','OPPO Reno5 5G 6400万水光人像四摄 65W超级闪充 12+256GB 星河入梦 全网通手机','http://localhost:9090/file/images/4579c8cc65cc4182b8c4ecc1856115fa.jpg'),(2048,'罗尔德达尔作品',20210621,1,10000,125,'LITERATURE','2021-06-15 19:18:48','【新华正版】罗尔德达尔作品典藏全套13册罗尔德·达尔了不起的狐狸爸爸查理和巧克力工厂女巫独闯天下','http://localhost:9090/file/images/01744c7ad8974536ac4ba03b40786583.jpg'),(2049,'心安即是归处',20210618,1,10000,36.5,'LITERATURE','2021-06-15 19:19:38','心安即是归处 京东专享（季羡林百年生命智慧。央视《朗读者》多次动情朗读本书名篇，贾平凹、白岩松、金庸、林青霞诚意推荐）','http://localhost:9090/file/images/08ed290acbcb4654a6426d1df98e7073.jpg'),(2050,'三体',20210618,1,10000,90.8,'LITERATURE','2021-06-15 19:20:44','《三体》为“中国科幻基石丛书”之一。小说主要讲述了在文化大革命如火如荼进行的同时，军方探寻外星文明的绝秘计划“红岸工程”取得了突破性进展。但在按下发射键的那一刻，历经劫难且对人类充满愤恨的叶文洁没有意识到，她彻底改变了人类的命运。地球文明向宇宙发出的一声啼鸣，以太阳为中心，以光速向宇宙深处飞驰…… ','http://localhost:9090/file/images/ab6043dc77b14f71b85d4bf152f075cd.jpg'),(2051,'你当像鸟飞往你的山',20210618,1,10000,34.9,'LITERATURE','2021-06-15 19:30:28','你当像鸟飞往你的山（张哲瀚推荐 登顶《纽约时报》畅销榜80+周 一个真实故事打开无限可能）','http://localhost:9090/file/images/29fffda83545408a86c484312db16d08.jpg'),(2052,'局外人',20210618,1,10000,39.1,'LITERATURE','2021-06-15 19:32:04','《局外人》是加缪的成名作，也是存在主义文学杰作，更是荒诞小说的代表作。小说讲述一位寻常的年轻职员，终日麻木地生活在漫无目的惯性中，某日去海边度假，卷进一宗冲突，犯下杀人案，因“他没有在母亲的葬礼上流泪”为由，被法庭以“法兰西人民”的名义判处死刑。小说阐述了存在主义的一个重要命题：人类社会的荒诞和陌生感导致个体的绝望与虚无。','http://localhost:9090/file/images/4a12ba2c15714979bf99f28f828378cd.jpg'),(2053,'手提行李包',20210618,1,10000,69,'CLOTHING_LUGGAGE','2021-06-15 19:34:03','小米世家大容量旅行包男士运动健身包独立鞋仓斜挎短途出差包手提行李包袋套拉杆包 黑色带独立鞋仓 大号','http://localhost:9090/file/images/872999aef6bc40428f5b4749e7d2c246.jpg'),(2054,'行礼拉箱',20210618,1,10000,138,'CLOTHING_LUGGAGE','2021-06-15 19:34:52','行礼拉箱男行李箱旅行包拉杆包女行李包袋短途旅游出差包大容量轻便手提拉杆登机包 黑色 套装版 大','http://localhost:9090/file/images/bd6c7ac5c8874812be67572bdd0b25d9.jpg'),(2055,'行李包',20210621,1,10000,79,'CLOTHING_LUGGAGE','2021-06-15 19:36:20','丛林鸟 新款休闲旅行超大容量双肩背包65升男女款行李包防水户外运动登山包 黑色','http://localhost:9090/file/images/ff1e971c63934801ad041b8aeeb2576c.jpg'),(2056,'短袖恤',20210621,1,10000,99,'CLOTHING_LUGGAGE','2021-06-15 19:44:33','短袖t恤男纯棉半截袖衣服圆领休闲宽松潮初中学生印花胖青年大码新品纯色夏季体恤汗衫 四格白+KY蓝+SP灰+海Z黑 L建议120-135斤','http://localhost:9090/file/images/68b952120aca4b36a2f0ac4b0face48d.jpg'),(2057,'短袖t恤男士',20210618,1,10000,89,'CLOTHING_LUGGAGE','2021-06-15 19:47:43','【2件装】南极人 短袖t恤男士宽松修身春夏装白色打底衫潮流情侣装衣服半袖男装上衣圆领体恤内搭T恤 【2件装】3004H羽毛蓝+6014黄蓝【白】 L','http://localhost:9090/file/images/6b12816c404f4caeabaa023c9d1add86.jpg'),(2058,'两件套',20210618,1,10000,169,'CLOTHING_LUGGAGE','2021-06-15 19:49:19','【两件套】花花公子短袖t恤男夏季潮流印花宽松透气休闲半袖套装男士上衣服男五分袖体恤工装裤子男一套夏装 【两件套】3128白色+6605深灰 L','http://localhost:9090/file/images/7882006e3b4e4daa944a77dab9071a8e.jpg'),(2059,'男防晒服',20210621,1,10000,138,'CLOTHING_LUGGAGE','2021-06-15 19:50:41','吉普JEEP防晒衣男防晒服男士上衣夏季皮肤衣轻薄夹克外套遮阳男女款防紫外线情侣款连帽度假工作服定制 男（白色） 男2XL（150斤-170斤）','http://localhost:9090/file/images/3b66d73de68d4d41bce7a66af8f1f15b.jpg'),(2060,'特步运动裤',20210621,1,10000,85,'CLOTHING_LUGGAGE','2021-06-15 20:03:25','特步运动裤男长裤2021年春夏季新款潮流服饰黑色休闲裤男韩系梭织卫裤束脚裤运动健身跑步裤子男 黑色长裤【春夏款】店长推荐 XL','http://localhost:9090/file/images/70114ea6edd34fcc95beffe51111361c.jpg'),(2061,'CBA 篮球裤',20210621,1,10000,119,'CLOTHING_LUGGAGE','2021-06-15 20:14:01','CBA 篮球裤官方训练裤长裤春秋全开扣束脚排扣裤宽松秒脱扯裤潮牌运动裤男士青少年学生针织裤 CBA【JY15】灰色束脚 3XL（185-190 170-185斤）','http://localhost:9090/file/images/883b18e34aea4e3fa447d322c9686fad.jpg'),(2062,'运动短裤',20210621,1,10000,23.9,'CLOTHING_LUGGAGE','2021-06-15 20:15:29','运动短裤男篮球裤街球潮流速干沙滩训练女宽松过膝五分裤嘻哈裤子BROTHERS TOGETHER 101 粉色 2XL (170-175cm)\r\n　原 始 价：￥29.0','http://localhost:9090/file/images/723f69addbb34e6a98b8f9e6f41823c0.jpg'),(2063,'牛排套餐',20210621,1,10000,119,'FRESH_FOOD','2021-06-15 20:17:29','【京东秒杀·109再送2片共1560g】美享时刻整切静腌牛排套餐10片新鲜牛肉黑椒西冷儿童牛扒 生鲜','http://localhost:9090/file/images/453439cce7ba417b917128d24243cb9d.jpg'),(2064,'奶酪片',20210618,1,10000,65.8,'FRESH_FOOD','2021-06-15 21:46:35','瑞慕（swissmooh)大孔奶酪片原制奶酪switzerland swiss瑞士进口120g','http://localhost:9090/file/images/51ebf121e2d2402486ba487cdde609e1.jpg'),(2065,'原制天然奶酪',20210618,1,10000,98,'FRESH_FOOD','2021-06-15 21:48:47','荷兰原装进口 贝斯隆马苏里拉芝士1kg 原制天然奶酪 芝士块 干酪 披萨拉丝奶酪 焗饭 烘焙原料','http://localhost:9090/file/images/1430f1d065d14282a43d61129208ab75.jpg'),(2066,'山海经',20210621,1,10000,65,'LITERATURE','2021-06-15 21:52:28','《山海经》是一部记述中国古代志怪的奇书。该书作者不详，大体是战国中后期到汉代初中期的楚国或巴蜀人所作。《山海经》是地理方志也是神话故事集，书中涉及地理、动物、植物、矿物、巫术、宗教、医药、民族等方面，并保存了夸父逐日、女娲补天、精卫填海等远古神话和寓言故事，包罗万象，多彩纷呈。《山海经》充满着神奇色彩，又饱含中国古代人文、自然知识，是古今众多名家推崇的国学必读书。','http://localhost:9090/file/images/9deed8227a0b46489f29e564d92c5f3c.jpg'),(2067,'植物蛋白饮料',20210621,1,10000,64.9,'FRESH_FOOD','2021-06-15 21:55:27','养元六个核桃易智优＋核桃乳植物蛋白饮料 240ml*20罐 整箱装 新老包装交替发货','http://localhost:9090/file/images/62071d31abee489a9dc873f244f1f1e0.jpg'),(2068,' 食用油',20210621,1,10000,125.9,'FRESH_FOOD','2021-06-15 21:57:52','鲁花 食用油 5S 物理压榨 压榨一级 花生油4L （新老包装随机发放）','http://localhost:9090/file/images/6f544fd018fd46678682de246d13d135.jpg');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shopcart`
--

DROP TABLE IF EXISTS `shopcart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shopcart` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `customerID` bigint NOT NULL,
  `merchantID` bigint NOT NULL,
  `productID` bigint NOT NULL,
  `productCount` int NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2034 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shopcart`
--

LOCK TABLES `shopcart` WRITE;
/*!40000 ALTER TABLE `shopcart` DISABLE KEYS */;
/*!40000 ALTER TABLE `shopcart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store`
--

DROP TABLE IF EXISTS `store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `store` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `storeName` varchar(45) NOT NULL,
  `registerTime` datetime DEFAULT NULL,
  `homeImage` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store`
--

LOCK TABLES `store` WRITE;
/*!40000 ALTER TABLE `store` DISABLE KEYS */;
INSERT INTO `store` VALUES (1,'专卖店','2021-06-14 08:56:23','http://localhost:9090/file/images/default.jpg');
/*!40000 ALTER TABLE `store` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) NOT NULL,
  `password` varchar(250) NOT NULL,
  `registerTime` datetime DEFAULT NULL,
  `email` varchar(25) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `avatarImage` varchar(200) DEFAULT NULL,
  `status` varchar(45) NOT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  UNIQUE KEY `userName_UNIQUE` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=20210631 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (20210617,'admin','swXHIx/t48ZL2C9sN/I9ewswzM/jqNc8OOV0M8hRaY2H32F+B53Ieu9/XrQUY+6W','2021-06-15 16:56:36','915323422@qq.com','14736813183','http://localhost:9090/file/images/66029a8d683c4dbab50436d007fac1b6.jpg','1','Manager'),(20210618,'dahuang','elF1iRr8weuz579G4Mboci9J62Tcb6YFPxGqFcKREqysVmLIKW92YLNfzrPVm+Y7','2021-06-15 17:00:44','915323422@qq.com','14678394501','http://localhost:9090/file/images/03cafe99f01b4bf3a20443f508e74474.jpg','1','Merchant'),(20210619,'dabai','mno5Ou5Xn5ClDbrYn0ymsmKVvDKaxjfv9PUVgYv0tOE+GTLyoH7VZ2nvsp17gAT/','2021-06-15 17:02:49','915323422@qq.com','19056734627','http://localhost:9090/file/images/8fcea2d65dd0482d8cc3566735239632.png','1','Customer'),(20210620,'xiaoxiao','dn6ZADCI6bKGSnCOjhouxB7YTzbWRZRgwc7pd3IM4sG0py5OKI1KdcH7En65+nsE','2021-06-15 17:06:19','915323422@qq.com','18765402107','http://localhost:9090/file/images/3b6c1e88a57440969de8957338db2319.jpg','1','Customer'),(20210621,'merchant1','BH9nEHOirI/WiTIAD+PYFLcdhV1ofIPWU8RP5+KmzcS2AIn6dYFvdy17D0fiPH9E','2021-06-15 17:07:55','201302307@qq.com','18739503457','http://localhost:9090/file/images/fee645ebc0bf43fd914ea2333166e0da.jpg','1','Merchant'),(20210622,'merchant2','xQuPVi965sc17xb7RRgBr/uEsL9r5wycl2Xkw2W6rIMRwFC6KiCfQZQS5v7k2jaX','2021-06-16 19:38:36','915323422@qq.com','15678494363','http://localhost:9090/file/images/65b75b9aef664d0a9116446eae4d46c2.jpg','1','Merchant'),(20210623,'merchant3','AqkKF/7ksKnsIvQBZ+7CvjHImXSMJarVBheAaKMM2NYj3mb3FWhwueWVqPekt7K5','2021-06-16 19:39:24','915323422@qq.com','15723433243','http://localhost:9090/file/images/8c81effdea70475a83a0513902cfb055.jpg','0','Merchant'),(20210624,'merchant4','4CbJ+le2AQKplWR9dPvCctfZJIB9ZDiK8pMbYfQ7/voWIuVZf1pSfQHTKi9LTq8r','2021-06-16 21:17:23','201303345@qq.com','15435454676','http://localhost:9090/file/images/6f0a9eac23b24397ba84a7561989719c.jpg','0','Merchant'),(20210625,'merchant5','uyWhN8FJfZbWWtV0iM28pAOVXcIvOwvnkbvD+GIhOIHhk3Pn4q2whq6BU8BQOpgI','2021-06-16 21:17:59','201303345@qq.com','15123232323','http://localhost:9090/file/images/299b77daee6b4fbb8f0b6f388d028d32.jpg','0','Merchant'),(20210626,'merchant6','JIfqefrmpcT74BGcOh346vs6lRS/KGybJVkOE98Yfa6VQ0xZfvZMa3fzGg9X3vGz','2021-06-16 21:18:37','915323422@qq.com','14673501734','http://localhost:9090/file/images/93f0fb0220ec403a98c55c796ba51eb5.jpg','0','Merchant'),(20210627,'merchant7','2LgSMG5dpGH3/d9DwWi9C637DzX6h71T7y5qoi8iO/IsXYBfxmXazQrlG1aBeEzN','2021-06-16 21:19:17','915323422@qq.com','14657032742','http://localhost:9090/file/images/dbbcbfcb039e419cbf22d15a8ae9c49c.jpg','0','Merchant'),(20210628,'merchant8','VAAOqI52q8jBahlBC9dMHyS1+yWZ4GQXuFhxCoJNm271sP7K3D03IBbOhUv0Qp9w','2021-06-16 21:20:03','915323422@qq.com','14567345612','http://localhost:9090/file/images/d7f06e425acd47acb8b3dff8cb28c79a.jpg','0','Merchant'),(20210629,'merchant9','UtyxuMgb5TFPtsKjzt0jPsjwE2rDvfJFS4uLdhTpsenP9B/P6t4jOfhU4DZlqT1D','2021-06-16 21:23:24','915323422@qq.com','16784675012','http://localhost:9090/file/images/4b03f0fce2294f4cacc54ca716311495.jpg','0','Merchant'),(20210630,'merchant10','P3My+zZYeEMTHrNJPzAOrpZYny5IJQ3K67lToa7Kw0XFZ76gJfBnE8Q7g6tGAKwI','2021-06-16 21:25:00','915323422@qq.com','15703467501','http://localhost:9090/file/images/8a0d8f0722d64652b77a47cedca783e4.jpg','0','Merchant');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'shopstore'
--

--
-- Dumping routines for database 'shopstore'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-17 18:58:19
