/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50622
 Source Host           : localhost:3306
 Source Schema         : hmdp

 Target Server Type    : MySQL
 Target Server Version : 50622
 File Encoding         : 65001

 Date: 14/03/2022 21:38:11
*/

-- ----------------------------
-- Create Database
-- ----------------------------
DROP DATABASE IF EXISTS valuation_sys;
CREATE DATABASE valuation_sys DEFAULT CHARACTER SET utf8mb4;

USE valuation_sys;

SET NAMES utf8mb4; -- Set the character set for the current session
SET FOREIGN_KEY_CHECKS = 0; -- Disable foreign key constraint checks for the current session

-- ----------------------------
-- Table structure for tb_blog
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog`;
CREATE TABLE `tb_blog`  (
                            `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
                            `shop_id` bigint(20) NOT NULL COMMENT 'Shop ID',
                            `user_id` bigint(20) UNSIGNED NOT NULL COMMENT 'User ID',
                            `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Title',
                            `images` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Store visit photos, maximum 9, separated by ","',
                            `content` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Textual description of the store visit',
                            `liked` int(8) UNSIGNED NULL DEFAULT 0 COMMENT 'Number of likes',
                            `comments` int(8) UNSIGNED NULL DEFAULT NULL COMMENT 'Number of comments',
                            `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation time',
                            `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for tb_shop_comments
-- ----------------------------
DROP TABLE IF EXISTS `tb_shop_comments`;
CREATE TABLE `tb_shop_comments`  (
                                     `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
                                     `user_id` bigint(20) UNSIGNED NOT NULL COMMENT 'User ID',
                                     `shop_id` bigint(20) UNSIGNED NOT NULL COMMENT 'Store visit ID',
                                     `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Reply content',
                                     `stars` int(8) UNSIGNED NOT NULL DEFAULT 5 COMMENT 'Star rating',
                                     `liked` int(8) UNSIGNED NULL DEFAULT NULL COMMENT 'Number of likes',
                                     `status` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT 'Status, 0: normal, 1: reported, 2: forbidden to view',
                                     `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation time',
                                     `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for tb_follow
-- ----------------------------
DROP TABLE IF EXISTS `tb_follow`;
CREATE TABLE `tb_follow`  (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
                              `user_id` bigint(20) UNSIGNED NOT NULL COMMENT 'User ID',
                              `follow_user_id` bigint(20) UNSIGNED NOT NULL COMMENT 'Associated user ID',
                              `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation time',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for tb_seckill_voucher
-- ----------------------------
DROP TABLE IF EXISTS `tb_seckill_voucher`;
CREATE TABLE `tb_seckill_voucher`  (
                                       `voucher_id` bigint(20) UNSIGNED NOT NULL COMMENT 'ID of the associated coupon',
                                       `stock` int(8) NOT NULL COMMENT 'Stock',
                                       `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation time',
                                       `begin_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Effective time',
                                       `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Expiration time',
                                       `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
                                       PRIMARY KEY (`voucher_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Seckill voucher table, with a one-to-one relationship with the voucher' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for tb_shop
-- ----------------------------
DROP TABLE IF EXISTS `tb_shop`;
CREATE TABLE `tb_shop`  (
                            `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
                            `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Shop name',
                            `type_id` bigint(20) UNSIGNED NOT NULL COMMENT 'Shop type ID',
                            `images` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Shop images, multiple images separated by ","',
                            `area` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Business district, e.g., Lujiazui',
                            `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Address',
                            `x` double UNSIGNED NOT NULL COMMENT 'Longitude',
                            `y` double UNSIGNED NOT NULL COMMENT 'Latitude',
                            `avg_price` bigint(10) UNSIGNED NULL DEFAULT NULL COMMENT 'Average price, integer value',
                            `sold` int(10) UNSIGNED ZEROFILL NOT NULL COMMENT 'Sales volume',
                            `comments` int(10) UNSIGNED ZEROFILL NOT NULL COMMENT 'Number of comments',
                            `score` int(2) UNSIGNED ZEROFILL NOT NULL COMMENT 'Score, 1-5 points, saved multiplied by 10 to avoid decimals',
                            `open_hours` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Business hours, e.g., 10:00-22:00',
                            `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation time',
                            `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
                            PRIMARY KEY (`id`) USING BTREE,
                            INDEX `foreign_key_type`(`type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for tb_shop_type
-- ----------------------------
DROP TABLE IF EXISTS `tb_shop_type`;
CREATE TABLE `tb_shop_type`  (
                                 `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
                                 `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Type name',
                                 `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Icon',
                                 `sort` int(3) UNSIGNED NULL DEFAULT NULL COMMENT 'Sort order',
                                 `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation time',
                                 `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for tb_sign
-- ----------------------------
DROP TABLE IF EXISTS `tb_sign`;
CREATE TABLE `tb_sign`  (
                            `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
                            `user_id` bigint(20) UNSIGNED NOT NULL COMMENT 'User ID',
                            `year` year NOT NULL COMMENT 'Year of sign-in',
                            `month` tinyint(2) NOT NULL COMMENT 'Month of sign-in',
                            `date` date NOT NULL COMMENT 'Date of sign-in',
                            `is_backup` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT 'Whether it is a makeup sign-in',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
                            `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
                            `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Phone number',
                            `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Password, encrypted storage',
                            `nick_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Nickname, defaults to user ID',
                            `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'User avatar',
                            `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation time',
                            `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
                            PRIMARY KEY (`id`) USING BTREE,
                            UNIQUE INDEX `uniqe_key_phone`(`phone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1010 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for tb_user_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_info`;
CREATE TABLE `tb_user_info`  (
                                 `user_id` bigint(20) UNSIGNED NOT NULL COMMENT 'Primary key, User ID',
                                 `city` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'City name',
                                 `introduce` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Personal introduction, do not exceed 128 characters',
                                 `fans` int(8) UNSIGNED NULL DEFAULT 0 COMMENT 'Number of fans',
                                 `followee` int(8) UNSIGNED NULL DEFAULT 0 COMMENT 'Number of people followed',
                                 `gender` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT 'Gender, 0: male, 1: female',
                                 `birthday` date NULL DEFAULT NULL COMMENT 'Birthday',
                                 `credits` int(8) UNSIGNED NULL DEFAULT 0 COMMENT 'Credits',
                                 `level` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT 'Membership level, 0-9, 0 means no membership',
                                 `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation time',
                                 `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
                                 PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for tb_voucher
-- ----------------------------
DROP TABLE IF EXISTS `tb_voucher`;
CREATE TABLE `tb_voucher`  (
                               `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
                               `shop_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT 'Shop ID',
                               `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Voucher title',
                               `sub_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Subtitle',
                               `rules` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Usage rules',
                               `pay_value` bigint(10) UNSIGNED NOT NULL COMMENT 'Payment amount, in cents. e.g., 200 represents 2 CNY',
                               `actual_value` bigint(10) NOT NULL COMMENT 'Discount amount, in cents. e.g., 200 represents 2 CNY',
                               `type` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '0: general voucher; 1: seckill voucher',
                               `status` tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '1: listed; 2: delisted; 3: expired',
                               `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation time',
                               `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tb_voucher
-- ----------------------------
INSERT INTO `tb_voucher` VALUES (1, 1, '50 Yuan Voucher', 'Valid Monday to Sunday', 'Valid for all items\nNo reservation required\nCan be stacked indefinitely\nCannot be redeemed for cash or change\nDine-in only', 4750, 5000, 0, 1, '2022-01-04 09:42:39', '2022-01-04 09:43:31');

-- ----------------------------
-- Table structure for tb_voucher_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_voucher_order`;
CREATE TABLE `tb_voucher_order`  (
                                     `id` bigint(20) NOT NULL COMMENT 'Primary key',
                                     `user_id` bigint(20) UNSIGNED NOT NULL COMMENT 'User ID who placed the order',
                                     `voucher_id` bigint(20) UNSIGNED NOT NULL COMMENT 'ID of the purchased voucher',
                                     `pay_type` tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT 'Payment method 1: balance payment; 2: Alipay; 3: WeChat Pay',
                                     `status` tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT 'Order status, 1: unpaid; 2: paid; 3: redeemed; 4: canceled; 5: refunding; 6: refunded',
                                     `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Order placement time',
                                     `pay_time` timestamp NULL DEFAULT NULL COMMENT 'Payment time',
                                     `use_time` timestamp NULL DEFAULT NULL COMMENT 'Redemption time',
                                     `refund_time` timestamp NULL DEFAULT NULL COMMENT 'Refund time',
                                     `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;


SET FOREIGN_KEY_CHECKS = 1;