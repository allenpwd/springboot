CREATE DATABASE IF NOT EXISTS pwd
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_general_ci;


-- ----------------------------
-- Table structure for db_user
-- ----------------------------
DROP TABLE IF EXISTS `pwd`.`db_user`;
CREATE TABLE `pwd`.`db_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `age` int(11) NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `create_at` datetime(0) NULL DEFAULT NULL,
  `dept_id` int(11) NULL DEFAULT NULL,
  `msg` blob NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of db_user
-- ----------------------------
INSERT INTO `pwd`.`db_user` VALUES (1, '门那粒沙', 18, 0, '2019-12-30 10:56:35', 1, 0xC4E3CAC7C9B5B1C6);