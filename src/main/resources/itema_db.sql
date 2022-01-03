/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : itema_db

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 03/01/2022 17:22:10
*/

DROP DATABASE IF EXISTS `itema_db`;
CREATE database `itema_db` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
USE `itema_db`;


-- ----------------------------
-- Table structure for goods_info
-- ----------------------------
DROP TABLE IF EXISTS `goods_info`;
CREATE TABLE `goods_info`  (
  `goods_id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '商品表主键id',
  `goods_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品名',
  `goods_intro` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品简介',
  `goods_category_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '关联分类id',
  `goods_cover_img` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '/admin/dist/img/no-img.png' COMMENT '商品主图',
  `goods_carousel` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '/admin/dist/img/no-img.png' COMMENT '商品轮播图',
  `goods_detail_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品详情',
  `original_price` int(0) NOT NULL DEFAULT 1 COMMENT '商品价格',
  `selling_price` int(0) NOT NULL DEFAULT 1 COMMENT '商品实际售价',
  `stock_num` int(0) NOT NULL DEFAULT 0 COMMENT '商品库存数量',
  `tag` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品标签',
  `goods_sell_status` tinyint(0) NOT NULL DEFAULT 0 COMMENT '商品上架状态 0-下架 1-上架',
  `create_user` int(0) NOT NULL DEFAULT 0 COMMENT '添加者主键id',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '商品添加时间',
  `update_user` int(0) NOT NULL DEFAULT 0 COMMENT '修改者主键id',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '商品修改时间',
  `shop_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '商家ID，进行商品关联',
  PRIMARY KEY (`goods_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10913 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods_info
-- ----------------------------
INSERT INTO `goods_info` VALUES (10113, '鸭鸭羽绒服男中长款2021年冬季新款韩版加厚宽松极寒保暖过膝外套W 黑色', 'DRP07B0270 175/L', 0, '/upload/20220102_00000010.jpg', '/upload/20220102_00000010.jpg', '图片介绍', 909, 909, 998, '品质生活', 0, 0, '2021-12-18 13:19:22', 0, '2021-12-21 01:26:25', 0);
INSERT INTO `goods_info` VALUES (10147, '雷朗 精灵宝可梦毛绒玩具皮卡丘公仔抱枕睡觉枕床大娃娃玩偶', '生日圣诞节新年礼物30CM', 0, '/upload/20220102_00000002.jpg', '/upload/20220102_00000002.jpg', '图片介绍', 36, 36, 988, '舒适抱枕', 0, 0, '2021-12-18 13:19:22', 0, '2021-12-21 01:26:25', 0);
INSERT INTO `goods_info` VALUES (10154, '华为 HUAWEI Mate 40 RS 保时捷设计麒麟9000芯片 超感知徕卡电影五摄', '8GB+256GB陶瓷黑5G全网通手机', 0, '/upload/20220102_00000009.jpg', '/upload/20220102_00000009.jpg', '图片介绍', 10999, 8999, 998, '巨大优惠', 0, 0, '2021-12-18 13:19:22', 0, '2021-12-21 01:26:25', 0);
INSERT INTO `goods_info` VALUES (10158, '花花公子卫衣长袖T恤男士秋衣加绒大码宽松潮牌套头打底衫男装体恤服百搭上衣圆领印花秋冬联名加厚保暖衣服', 'WY8042白色【加绒】 L建议115-130斤', 20, '/upload/20220102_00000011.jpg', '/upload/20220102_00000011.jpg', '图片介绍', 139, 139, 985, '清凉T恤', 0, 0, '2021-12-18 13:19:22', 0, '2021-12-21 01:26:25', 0);
INSERT INTO `goods_info` VALUES (10160, 'Apple AirPods 配充电盒 Apple蓝牙耳机', '适用iPhone/iPad/Apple Watch', 51, '/upload/20220102_00000007.jpg', '/upload/20220102_00000007.jpg', '图片介绍', 1046, 1046, 998, '优质耳机', 0, 0, '2021-12-18 13:19:22', 0, '2021-12-21 01:26:25', 0);
INSERT INTO `goods_info` VALUES (10233, '金士顿 (Kingston) FURY 16GB(8G×2)套装', 'DDR4 3200 台式机内存条 Beast野兽系列 骇客神条', 86, '/upload/20220102_00000012.jpg', '/upload/20220102_00000012.jpg', '图片介绍', 499, 499, 998, '高质量内存条', 0, 0, '2021-12-18 13:19:22', 0, '2021-12-21 01:26:25', 0);
INSERT INTO `goods_info` VALUES (10237, '纪梵希（Givenchy）高定香榭唇膏小羊皮口红礼盒N306 3.4g半哑光 斩男番茄红', ' 送女友 新年元旦 生日礼物女', 86, '/upload/20220102_00000004.jpg', '/upload/20220102_00000004.jpg', '图片介绍', 360, 360, 993, '精品口红', 0, 0, '2021-12-18 13:19:22', 0, '2021-12-21 01:26:25', 0);
INSERT INTO `goods_info` VALUES (10254, '华为智慧屏 SE 65英寸 超薄电视 广色域鸿鹄画质 4K超高清智能液晶电视机', 'HD65DESA 2+16GB【HarmonyOS 2', 0, '/upload/20220102_00000008.jpg', '/upload/20220102_00000008.jpg', '图片介绍', 2999, 2999, 992, '再次倾心', 0, 0, '2021-12-18 13:19:22', 0, '2021-12-21 01:26:25', 0);
INSERT INTO `goods_info` VALUES (10284, '惠普（HP）光影精灵7 16.1英寸游戏笔记本电脑 暗影精灵7 设计师电脑', 'i7-11800H RTX3060 6G电竞屏 豪华版16G内存 1T固态硬盘 定制', 47, '/upload/20220102_00000001.jpg', '/upload/20220102_00000001.jpg', '图片介绍', 9299, 9299, 991, '', 0, 0, '2021-12-18 13:19:22', 0, '2021-12-21 01:26:25', 0);
INSERT INTO `goods_info` VALUES (10320, '梦旅者铝框拉杆箱 万向轮高颜值行李箱女耐用密码旅行箱男', '24寸', 47, '/upload/20220102_00000016.jpg', '/upload/20220102_00000016.jpg', '图片介绍', 558, 558, 996, '', 0, 0, '2021-12-18 13:19:22', 0, '2021-12-21 01:26:25', 0);
INSERT INTO `goods_info` VALUES (10893, '三星S21 Ultra全新宜方正品智能数码手机samsung Galaxy全网通骁龙888内充学生游戏手机', '16+512GB', 46, '/upload/20220102_00000013.jpg', '/upload/20220102_00000013.jpg', '图片介绍', 9899, 9899, 988, '重构想象', 0, 0, '2021-12-18 13:19:22', 0, '2021-12-21 01:26:25', 0);
INSERT INTO `goods_info` VALUES (10894, '沫兰999足银项链女士款托帕石吊坠时尚饰品首饰心形锁骨链女生', '生日礼物元旦新年送女朋友老婆 心中有你托帕石项链', 46, '/upload/20220102_00000003.jpg', '/upload/20220102_00000003.jpg', '图片介绍', 245, 245, 994, '精美吊坠', 0, 0, '2021-12-18 13:19:22', 0, '2021-12-21 01:26:25', 0);
INSERT INTO `goods_info` VALUES (10907, '联想(Lenovo)拯救者Y7000P 15.6英寸商用设计笔记本电脑 灰', 'i7-10875H 16G 512G RTX2060 144Hz 100%sRGB', 46, '/upload/20220102_00000000.jpg', '/upload/20220102_00000000.jpg', '图片介绍', 10750, 10750, 990, '电脑', 0, 0, '2021-12-18 13:19:22', 0, '2021-12-21 01:26:25', 0);
INSERT INTO `goods_info` VALUES (10908, '卫龙 辣条 年货节送礼零食大礼包 送女友礼物休闲零食1648g/内含117包', '人气辣条大面筋 小面筋 大辣棒 亲嘴烧', 52, '/upload/20220102_00000017.jpg', '/upload/20220102_00000017.jpg', '图片介绍', 49, 49, 997, '美味辣条', 0, 0, '2021-12-18 13:19:22', 0, '2021-12-21 01:26:25', 0);
INSERT INTO `goods_info` VALUES (10909, '联想拯救者Y9000K 16英寸笔记本设计师高端设计制图渲染', 'I7-11800H/64G/2T固态/RTX3080 16G独显/升级定制K', 46, '/upload/20220102_00000005.jpg', '/upload/20220102_00000005.jpg', '图片介绍', 24800, 24800, 994, '电脑', 0, 0, '2021-12-18 13:19:22', 0, '2021-12-21 01:26:25', 0);
INSERT INTO `goods_info` VALUES (10910, '戴尔(DELL) G15 -5510 高性能笔记本电脑 设计师制图', 'I5-10200H/16G/512G/3050独显4G/WIN10H/15.6英寸/K', 53, '/upload/20220102_00000006.jpg', '/upload/20220102_00000006.jpg', '图片介绍', 7566, 7566, 996, '电脑', 0, 0, '2021-12-18 13:19:22', 0, '2021-12-21 01:26:25', 0);
INSERT INTO `goods_info` VALUES (10911, 'HUAWEI/华为P50 Pocket P50宝盒华为折叠屏手机4G全网通无缝折叠超光谱影像系统新款可折叠手机', '8+256GB', 51, '/upload/20220102_00000014.jpg', '/upload/20220102_00000014.jpg', '图片介绍', 8988, 8988, 1992, '新品大牌', 0, 0, '2021-12-18 13:19:22', 0, '2021-12-21 01:26:25', 0);
INSERT INTO `goods_info` VALUES (10912, 'GUCCI古驰GG Marmont系列mini单肩包', '保真 质量好', 34, '/upload/20220102_00000015.jpg', '/upload/20220102_00000015.jpg', '图片介绍', 16500, 16500, 1000, '大牌保真', 0, 0, '2021-12-18 13:19:22', 0, '2021-12-21 01:26:25', 0);
INSERT INTO `goods_info` VALUES (10913, '1234', '2', 20, 'http://localhost:28079/upload/20220103_1716188.jpg', 'http://localhost:28079/upload/20220103_1716188.jpg', '1234', 12, 12, 122, '134', 0, 0, '2022-01-03 17:16:24', 0, '2022-01-03 17:16:24', 10);

-- ----------------------------
-- Table structure for index_config
-- ----------------------------
DROP TABLE IF EXISTS `index_config`;
CREATE TABLE `index_config`  (
  `config_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '首页配置项主键id',
  `config_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '显示字符(配置搜索时不可为空，其他可为空)',
  `config_type` tinyint(0) NOT NULL DEFAULT 0 COMMENT '1-搜索框热搜 2-搜索下拉框热搜 3-(首页)热销商品 4-(首页)新品上线 5-(首页)为你推荐',
  `goods_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '商品id 默认为0',
  `redirect_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '##' COMMENT '点击后的跳转地址(默认不跳转)',
  `config_rank` int(0) NOT NULL DEFAULT 0 COMMENT '排序值(字段越大越靠前)',
  `is_deleted` tinyint(0) NOT NULL DEFAULT 0 COMMENT '删除标识字段(0-未删除 1-已删除)',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(0) NOT NULL DEFAULT 0 COMMENT '创建者id',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最新修改时间',
  `update_user` int(0) NULL DEFAULT 0 COMMENT '修改者id',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of index_config
-- ----------------------------
INSERT INTO `index_config` VALUES (1, '热销商品 iPhone XR', 3, 10284, '##', 10, 0, '2021-12-18 17:04:56', 0, '2021-12-18 17:04:56', 0);
INSERT INTO `index_config` VALUES (2, '热销商品 oppoFindx3pro', 3, 10910, '##', 100, 0, '2021-12-18 17:04:56', 0, '2021-05-21 18:21:41', 0);
INSERT INTO `index_config` VALUES (3, '热销商品 Redmi K40', 3, 10908, '##', 300, 1, '2021-12-18 17:04:56', 0, '2021-05-15 10:11:32', 0);
INSERT INTO `index_config` VALUES (4, '热销商品 华为Mate40E', 3, 10907, '##', 101, 0, '2021-12-18 17:04:56', 0, '2021-12-20 22:11:02', 0);
INSERT INTO `index_config` VALUES (5, '新品上线 Macbook Pro', 4, 10269, '##', 100, 0, '2021-12-18 17:04:56', 0, '2021-12-20 22:11:02', 0);
INSERT INTO `index_config` VALUES (6, '新品上线 荣耀 9X Pro', 4, 10755, '##', 100, 0, '2021-12-18 17:04:56', 0, '2021-12-20 22:11:02', 0);
INSERT INTO `index_config` VALUES (7, '新品上线 联想R9000P', 4, 10912, '##', 102, 0, '2021-12-18 17:04:56', 0, '2021-12-20 22:11:02', 0);
INSERT INTO `index_config` VALUES (8, '新品上线 iPhone 11 Pro', 4, 10320, '##', 101, 0, '2021-12-18 17:04:56', 0, '2021-12-20 22:11:02', 0);
INSERT INTO `index_config` VALUES (9, '新品上线 华为无线耳机', 4, 10186, '##', 100, 0, '2021-12-18 17:04:56', 0, '2021-12-20 22:11:02', 0);
INSERT INTO `index_config` VALUES (10, '纪梵希高定香榭天鹅绒唇膏', 5, 10233, '##', 98, 0, '2021-12-18 17:04:56', 0, '2021-12-20 22:11:02', 0);
INSERT INTO `index_config` VALUES (11, 'MAC 磨砂系列', 5, 10237, '##', 100, 0, '2021-12-18 17:04:56', 0, '2021-12-20 22:11:02', 0);
INSERT INTO `index_config` VALUES (12, 'Redmi K40', 5, 10908, '##', 102, 0, '2021-12-18 17:04:56', 0, '2021-12-20 22:11:02', 0);
INSERT INTO `index_config` VALUES (13, '小米11pro ultra', 5, 10911, '##', 101, 1, '2021-12-18 17:04:56', 0, '2021-12-20 22:11:02', 0);
INSERT INTO `index_config` VALUES (14, '小米 Redmi AirDots', 5, 10160, '##', 100, 0, '2021-12-18 17:04:56', 0, '2021-12-20 22:11:02', 0);
INSERT INTO `index_config` VALUES (15, '2021 MacBookAir 13', 5, 10254, '##', 100, 0, '2021-12-18 17:04:56', 0, '2021-12-20 22:11:02', 0);
INSERT INTO `index_config` VALUES (16, '女式粗棉线条纹长袖T恤', 5, 10158, '##', 99, 0, '2021-12-18 17:04:56', 0, '2021-12-20 22:11:02', 0);
INSERT INTO `index_config` VALUES (17, '塑料浴室座椅', 5, 10154, '##', 100, 0, '2021-12-18 17:04:56', 0, '2021-12-20 22:11:02', 0);
INSERT INTO `index_config` VALUES (18, '靠垫', 5, 10147, '##', 101, 0, '2021-12-18 17:04:56', 0, '2021-12-20 22:11:02', 0);
INSERT INTO `index_config` VALUES (19, '小型超声波香薰机', 5, 10113, '##', 100, 0, '2021-12-18 17:04:56', 0, '2021-12-20 22:11:02', 0);
INSERT INTO `index_config` VALUES (20, '11', 5, 1, '##', 0, 1, '2021-12-18 17:04:56', 0, '2021-12-20 22:11:02', 0);
INSERT INTO `index_config` VALUES (21, '热销商品 华为Mate40', 3, 10909, '##', 200, 0, '2021-12-18 17:04:56', 0, '2021-12-20 22:11:02', 0);
INSERT INTO `index_config` VALUES (22, '新品上线 华为Mate30 Pro', 4, 10893, '##', 200, 0, '2021-12-18 17:04:56', 0, '2021-12-20 22:11:02', 0);
INSERT INTO `index_config` VALUES (23, '新品上线 小米11pro ultra', 4, 10911, '##', 199, 0, '2021-12-18 17:04:56', 0, '2021-12-20 22:11:02', 0);
INSERT INTO `index_config` VALUES (24, '华为 Mate 30 Pro', 5, 10894, '##', 101, 0, '2021-12-18 17:04:56', 0, '2021-12-20 22:11:02', 0);
INSERT INTO `index_config` VALUES (25, '新品上线 华为Mate40 pro', 4, 10907, '##', 300, 0, '2021-12-18 17:04:56', 0, '2021-12-20 22:11:02', 0);

-- ----------------------------
-- Table structure for itema_admin
-- ----------------------------
DROP TABLE IF EXISTS `itema_admin`;
CREATE TABLE `itema_admin`  (
  `admin_user_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `login_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员登陆名称',
  `login_password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员登陆密码',
  `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员显示昵称',
  `locked` tinyint(0) NULL DEFAULT 0 COMMENT '是否锁定 0未锁定 1已锁定无法登陆',
  PRIMARY KEY (`admin_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of itema_admin
-- ----------------------------
INSERT INTO `itema_admin` VALUES (1, 'admin', '123456', 'king', 0);

-- ----------------------------
-- Table structure for itema_carousel
-- ----------------------------
DROP TABLE IF EXISTS `itema_carousel`;
CREATE TABLE `itema_carousel`  (
  `carousel_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '首页轮播图主键id',
  `carousel_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '轮播图',
  `redirect_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '\'##\'' COMMENT '点击后的跳转地址(默认不跳转)',
  `carousel_rank` int(0) NOT NULL DEFAULT 0 COMMENT '排序值(字段越大越靠前)',
  `is_deleted` tinyint(0) NOT NULL DEFAULT 0 COMMENT '删除标识字段(0-未删除 1-已删除)',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(0) NOT NULL DEFAULT 0 COMMENT '创建者id',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` int(0) NOT NULL DEFAULT 0 COMMENT '修改者id',
  PRIMARY KEY (`carousel_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of itema_carousel
-- ----------------------------
INSERT INTO `itema_carousel` VALUES (2, 'http://localhost:28079/upload/20220103_17054140.png', 'http://localhost:28079/index', 13, 0, '2021-12-18 13:00:00', 0, '2021-12-21 13:00:00', 0);
INSERT INTO `itema_carousel` VALUES (5, '/upload/2.jpg', 'http://localhost:28079/index', 0, 0, '2021-12-18 13:00:00', 0, '2021-12-21 13:00:00', 0);

-- ----------------------------
-- Table structure for itema_category
-- ----------------------------
DROP TABLE IF EXISTS `itema_category`;
CREATE TABLE `itema_category`  (
  `category_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `category_level` tinyint(0) NOT NULL DEFAULT 0 COMMENT '分类级别(1-一级分类 2-二级分类 3-三级分类)',
  `parent_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '父分类id',
  `category_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '分类名称',
  `category_rank` int(0) NOT NULL DEFAULT 0 COMMENT '排序值(字段越大越靠前)',
  `is_deleted` tinyint(0) NOT NULL DEFAULT 0 COMMENT '删除标识字段(0-未删除 1-已删除)',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(0) NOT NULL DEFAULT 0 COMMENT '创建者id',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` int(0) NULL DEFAULT 0 COMMENT '修改者id',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 107 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of itema_category
-- ----------------------------
INSERT INTO `itema_category` VALUES (15, 1, 0, '家电 数码 手机', 100, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (16, 1, 0, '女装 男装 穿搭', 99, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (17, 2, 15, '家电', 10, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (18, 2, 15, '数码', 9, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (19, 2, 15, '手机', 8, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (20, 3, 17, '生活电器', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (21, 3, 17, '厨房电器', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (22, 3, 17, '扫地机器人', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (23, 3, 17, '吸尘器', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (24, 3, 17, '取暖器', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (25, 3, 17, '豆浆机', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (26, 3, 17, '暖风机', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (27, 3, 17, '加湿器', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (28, 3, 17, '蓝牙音箱', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (29, 3, 17, '烤箱', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (30, 3, 17, '卷发器', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (31, 3, 17, '空气净化器', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (32, 3, 18, '游戏主机', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (33, 3, 18, '数码精选', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (34, 3, 18, '平板电脑', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (35, 3, 18, '苹果 Apple', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (36, 3, 18, '电脑主机', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (37, 3, 18, '数码相机', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (38, 3, 18, '电玩动漫', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (39, 3, 18, '单反相机', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (40, 3, 18, '键盘鼠标', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (41, 3, 18, '无人机', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (42, 3, 18, '二手电脑', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (43, 3, 18, '二手手机', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (44, 3, 19, 'iPhone 11', 89, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (45, 3, 19, '荣耀手机', 99, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (46, 3, 19, '华为手机', 98, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (47, 3, 19, 'iPhone', 88, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (48, 3, 19, '华为 Mate 20', 79, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (49, 3, 19, '华为 P30', 97, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (50, 3, 19, '华为 P30 Pro', 0, 1, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (51, 3, 19, '小米手机', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (52, 3, 19, '红米', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (53, 3, 19, 'OPPO', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (54, 3, 19, '一加', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (55, 3, 19, '小米 MIX', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (56, 3, 19, 'Reno', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (57, 3, 19, 'vivo', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (58, 3, 19, '手机以旧换新', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (59, 1, 0, '运动 户外 乐器', 97, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (60, 1, 0, '游戏 动漫 影视', 96, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (61, 1, 0, '家具 家饰 家纺', 98, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (62, 1, 0, '美妆 清洁 宠物', 94, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (63, 1, 0, '工具 装修 建材', 93, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (64, 1, 0, '珠宝 金饰 眼镜', 92, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (65, 1, 0, '玩具 孕产 用品', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (66, 1, 0, '鞋靴 箱包 配件', 91, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (67, 2, 16, '女装', 10, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (68, 2, 16, '男装', 9, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (69, 2, 16, '穿搭', 8, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (70, 2, 61, '家具', 10, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (71, 2, 61, '家饰', 9, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (72, 2, 61, '家纺', 8, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (73, 2, 59, '运动', 10, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (74, 2, 59, '户外', 9, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (75, 2, 59, '乐器', 8, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (76, 3, 67, '外套', 10, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (77, 3, 70, '沙发', 10, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (78, 3, 73, '跑鞋', 10, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (79, 2, 60, '游戏', 10, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (80, 2, 60, '动漫', 9, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (81, 2, 60, '影视', 8, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (82, 3, 79, 'LOL', 10, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (83, 2, 62, '美妆', 10, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (84, 2, 62, '宠物', 9, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (85, 2, 62, '清洁', 8, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (86, 3, 83, '口红', 10, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (87, 2, 63, '工具', 10, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (88, 2, 63, '装修', 9, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (89, 2, 63, '建材', 8, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (90, 3, 87, '转换器', 10, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (91, 2, 64, '珠宝', 10, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (92, 2, 64, '金饰', 9, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (93, 2, 64, '眼镜', 8, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (94, 3, 91, '钻石', 10, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (95, 2, 66, '鞋靴', 10, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (96, 2, 66, '箱包', 9, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (97, 2, 66, '配件', 8, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (98, 3, 95, '休闲鞋', 10, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (99, 3, 83, '气垫', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (100, 3, 83, '美白', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (101, 3, 83, '隔离霜', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (102, 3, 83, '粉底', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (103, 3, 83, '腮红', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (104, 3, 83, '睫毛膏', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (105, 3, 83, '香水', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);
INSERT INTO `itema_category` VALUES (106, 3, 83, '面膜', 0, 0, '2021-12-11 18:45:40', 0, '2021-12-12 18:00:00', 0);

-- ----------------------------
-- Table structure for itema_coupon
-- ----------------------------
DROP TABLE IF EXISTS `itema_coupon`;
CREATE TABLE `itema_coupon`  (
  `coupon_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `coupon_name` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '优惠券名称',
  `coupon_desc` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '优惠券介绍，通常是显示优惠券使用限制文字',
  `coupon_total` int(0) NOT NULL DEFAULT 0 COMMENT '优惠券数量，如果是0，则是无限量',
  `discount` int(0) NULL DEFAULT 0 COMMENT '优惠金额，',
  `min` int(0) NULL DEFAULT 0 COMMENT '最少消费金额才能使用优惠券。',
  `coupon_limit` tinyint(0) NULL DEFAULT 1 COMMENT '用户领券限制数量，如果是0，则是不限制；默认是1，限领一张.',
  `coupon_type` tinyint(0) NULL DEFAULT 0 COMMENT '优惠券赠送类型，如果是0则通用券，用户领取；如果是1，则是注册赠券；如果是2，则是优惠券码兑换；',
  `coupon_status` tinyint(0) NULL DEFAULT 0 COMMENT '优惠券状态，如果是0则是正常可用；如果是1则是过期; 如果是2则是下架。',
  `goods_type` tinyint(0) NULL DEFAULT 0 COMMENT '商品限制类型，如果0则全商品，如果是1则是类目限制，如果是2则是商品限制。',
  `goods_value` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '商品限制值，goods_type如果是0则空集合，如果是1则是类目集合，如果是2则是商品集合。',
  `coupon_code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '优惠券兑换码',
  `coupon_start_time` datetime(0) NULL DEFAULT NULL COMMENT '优惠卷开始时间',
  `coupon_end_time` datetime(0) NULL DEFAULT NULL COMMENT '优惠卷结束时间',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(0) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`coupon_id`) USING BTREE,
  INDEX `code`(`coupon_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '优惠券信息及规则表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for itema_order
-- ----------------------------
DROP TABLE IF EXISTS `itema_order`;
CREATE TABLE `itema_order`  (
  `order_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '订单表主键id',
  `order_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '订单号',
  `user_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '用户主键id',
  `total_price` int(0) NOT NULL DEFAULT 1 COMMENT '订单总价',
  `pay_status` tinyint(0) NOT NULL DEFAULT 0 COMMENT '支付状态:0.未支付,1.支付成功,-1:支付失败',
  `pay_type` tinyint(0) NOT NULL DEFAULT 0 COMMENT '0.无 1.支付宝支付 2.微信支付',
  `pay_time` datetime(0) NULL DEFAULT NULL COMMENT '支付时间',
  `order_status` tinyint(0) NOT NULL DEFAULT 0 COMMENT '订单状态:0.待支付 1.已支付 2.配货完成 3:出库成功 4.交易成功 -1.手动关闭 -2.超时关闭 -3.商家关闭',
  `extra_info` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '订单body',
  `user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '收货人姓名',
  `user_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '收货人手机号',
  `user_address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '收货人收货地址',
  `is_deleted` tinyint(0) NOT NULL DEFAULT 0 COMMENT '删除标识字段(0-未删除 1-已删除)',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最新修改时间',
  `shop_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '商家ID，将订单与商家关联',
  `comment` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单评论',
  `goods_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '货物ID，将货物与订单关联',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 112 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of itema_order
-- ----------------------------
INSERT INTO `itema_order` VALUES (112, '16412013189231230', 16, 10750, 1, 2, '2022-01-03 17:15:25', 1, 'newbeemall-plus支付宝沙箱支付', '', '', '江西省南昌市红谷滩新区999', 0, '2022-01-03 17:15:18', '2022-01-03 17:15:25', 0, NULL, 10907);
INSERT INTO `itema_order` VALUES (113, '16412013189446098', 16, 245, 1, 2, '2022-01-03 17:15:25', 1, 'newbeemall-plus支付宝沙箱支付', '', '', '江西省南昌市红谷滩新区999', 0, '2022-01-03 17:15:18', '2022-01-03 17:15:25', 0, NULL, 10894);
INSERT INTO `itema_order` VALUES (114, '16412014093276850', 16, 12, 1, 2, '2022-01-03 17:17:00', 4, 'newbeemall-plus支付宝沙箱支付', '', '', '江西省南昌市红谷滩新区999', 0, '2022-01-03 17:16:49', '2022-01-03 17:17:38', 10, '\r\n                                                    13325krm', 10913);

-- ----------------------------
-- Table structure for itema_seckill
-- ----------------------------
DROP TABLE IF EXISTS `itema_seckill`;
CREATE TABLE `itema_seckill`  (
  `seckill_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `goods_id` bigint(0) NOT NULL COMMENT '秒杀商品ID',
  `seckill_price` int(0) NOT NULL COMMENT '秒杀价格',
  `seckill_num` int(0) NOT NULL COMMENT '秒杀数量',
  `seckill_status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '秒杀商品状态（0下架，1上架）',
  `seckill_begin` datetime(0) NULL DEFAULT NULL COMMENT '秒杀开始时间',
  `seckill_end` datetime(0) NULL DEFAULT NULL COMMENT '秒杀结束时间',
  `seckill_rank` int(0) NULL DEFAULT NULL COMMENT '秒杀商品排序',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '删除标识字段(0-未删除 1-已删除)',
  PRIMARY KEY (`seckill_id`) USING BTREE,
  INDEX `status_index`(`seckill_status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for itema_shop
-- ----------------------------
DROP TABLE IF EXISTS `itema_shop`;
CREATE TABLE `itema_shop`  (
  `shop_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '商店主键id',
  `shop_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商铺名',
  `login_name` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户名(默认为手机号)',
  `ID_card` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商家身份证',
  `real_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商家真实姓名',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  PRIMARY KEY (`shop_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of itema_shop
-- ----------------------------
INSERT INTO `itema_shop` VALUES (10, '天猫商城', '15707061101', '36292038479384', '吕俊', '2022-01-03 17:15:42');

-- ----------------------------
-- Table structure for itema_user
-- ----------------------------
DROP TABLE IF EXISTS `itema_user`;
CREATE TABLE `itema_user`  (
  `user_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '用户主键id',
  `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户昵称',
  `login_name` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '登陆名称(默认为手机号)',
  `password_md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'MD5加密后的密码',
  `introduce_sign` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '个性签名',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '收货地址',
  `is_deleted` tinyint(0) NOT NULL DEFAULT 0 COMMENT '注销标识字段(0-正常 1-已注销)',
  `locked_flag` tinyint(0) NOT NULL DEFAULT 0 COMMENT '锁定标识字段(0-未锁定 1-已锁定)',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `is_merchant` tinyint(0) NOT NULL DEFAULT 0 COMMENT '标识字段，是否为商家',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of itema_user
-- ----------------------------
INSERT INTO `itema_user` VALUES (6, '测试用户1', '13711113333', 'dda01dc6d334badcd031102be6bee182', '测试用户1', '上海浦东新区XX路XX号 999 137xxxx7797', 0, 0, '2021-12-20 10:51:39', 0);
INSERT INTO `itema_user` VALUES (7, '测试用户2测试用户2测试用户2测试用户2', '13811113333', 'dda01dc6d334badcd031102be6bee182', '测试用户2', '杭州市西湖区xx小区x幢419 十三 137xxxx2703', 0, 0, '2021-12-20 10:55:08', 0);
INSERT INTO `itema_user` VALUES (8, '测试用户3', '13911113333', 'dda01dc6d334badcd031102be6bee182', '测试用户3', '杭州市西湖区xx小区x幢419 十三 137xxxx2703', 0, 0, '2021-12-20 10:55:16', 0);

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item`  (
  `order_item_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '订单关联购物项主键id',
  `order_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '订单主键id',
  `seckill_id` bigint(0) NULL DEFAULT NULL COMMENT '秒杀商品ID',
  `goods_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '关联商品id',
  `goods_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '下单时商品的名称(订单快照)',
  `goods_cover_img` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '下单时商品的主图(订单快照)',
  `selling_price` int(0) NOT NULL DEFAULT 1 COMMENT '下单时商品的价格(订单快照)',
  `goods_count` int(0) NOT NULL DEFAULT 1 COMMENT '数量(订单快照)',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`order_item_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 127 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_item
-- ----------------------------
INSERT INTO `order_item` VALUES (127, 112, NULL, 10907, '联想(Lenovo)拯救者Y7000P 15.6英寸商用...', '/upload/20220102_00000000.jpg', 10750, 1, '2022-01-03 17:15:18');
INSERT INTO `order_item` VALUES (128, 113, NULL, 10894, '沫兰999足银项链女士款托帕石吊坠时尚饰品首饰心形锁骨链...', '/upload/20220102_00000003.jpg', 245, 1, '2022-01-03 17:15:18');
INSERT INTO `order_item` VALUES (129, 114, NULL, 10913, '1234', 'http://localhost:28079/upload/20220103_1716188.jpg', 12, 1, '2022-01-03 17:16:49');

-- ----------------------------
-- Table structure for seckill_success
-- ----------------------------
DROP TABLE IF EXISTS `seckill_success`;
CREATE TABLE `seckill_success`  (
  `sec_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `seckill_id` bigint(0) NOT NULL COMMENT '商品商品id',
  `user_id` bigint(0) NOT NULL COMMENT '用户ID',
  `state` tinyint(0) NOT NULL DEFAULT -1 COMMENT '状态信息：-1无效，0成功，1已付款，2已发货',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`sec_id`) USING BTREE,
  INDEX `idx_create_time`(`create_time`) USING BTREE,
  INDEX `seckill_user_id`(`seckill_id`, `user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 617 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '秒杀库存表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shopping_cart_item
-- ----------------------------
DROP TABLE IF EXISTS `shopping_cart_item`;
CREATE TABLE `shopping_cart_item`  (
  `cart_item_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '购物项主键id',
  `user_id` bigint(0) NOT NULL COMMENT '用户主键id',
  `goods_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '关联商品id',
  `goods_count` int(0) NOT NULL DEFAULT 1 COMMENT '数量(最大为5)',
  `is_deleted` tinyint(0) NOT NULL DEFAULT 0 COMMENT '删除标识字段(0-未删除 1-已删除)',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最新修改时间',
  PRIMARY KEY (`cart_item_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 159 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shopping_cart_item
-- ----------------------------
INSERT INTO `shopping_cart_item` VALUES (159, 16, 10894, 1, 1, '2022-01-03 17:14:59', '2022-01-03 17:14:59');
INSERT INTO `shopping_cart_item` VALUES (160, 16, 10913, 1, 1, '2022-01-03 17:16:41', '2022-01-03 17:16:41');

-- ----------------------------
-- Table structure for user_coupon_record
-- ----------------------------
DROP TABLE IF EXISTS `user_coupon_record`;
CREATE TABLE `user_coupon_record`  (
  `coupon_user_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(0) NOT NULL COMMENT '用户ID',
  `coupon_id` bigint(0) NOT NULL COMMENT '优惠券ID',
  `use_status` tinyint(0) NULL DEFAULT 0 COMMENT '使用状态, 如果是0则未使用；如果是1则已使用；如果是2则已过期；如果是3则已经下架；',
  `used_time` datetime(0) NULL DEFAULT NULL COMMENT '使用时间',
  `order_id` bigint(0) NULL DEFAULT NULL COMMENT '订单ID',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(0) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`coupon_user_id`) USING BTREE,
  INDEX `user_coupin_index`(`user_id`, `coupon_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 134 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '优惠券用户使用表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
