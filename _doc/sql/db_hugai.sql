/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80028 (8.0.28)
 Source Host           : localhost:3306
 Source Schema         : db_hugai

 Target Server Type    : MySQL
 Target Server Version : 80028 (8.0.28)
 File Encoding         : 65001

 Date: 12/09/2023 22:09:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for base_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `base_dict_data`;
CREATE TABLE `base_dict_data`  (
  `id` bigint NOT NULL COMMENT '主键',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `update_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标识 0 未删除 1 已删除',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  `sort` int NULL DEFAULT 0 COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'N' COMMENT '是否默认（0 否 1 是）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `dict_value`(`dict_value` ASC, `dict_type` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of base_dict_data
-- ----------------------------
INSERT INTO `base_dict_data` VALUES (70519612373991429, '2022-07-14 22:19:45', 'admin', '2022-07-14 22:32:37', 'admin', 0, 4, 3, '无', '2', 'sys_user_sex', NULL, 'default', '0', '0');
INSERT INTO `base_dict_data` VALUES (70519650416328709, '2022-07-14 22:19:54', 'admin', '2022-07-14 22:21:08', 'admin', 0, 0, 2, '男', '1', 'sys_user_sex', NULL, 'default', '0', '0');
INSERT INTO `base_dict_data` VALUES (70519706510950405, '2022-07-14 22:20:08', 'admin', '2022-07-16 18:22:25', 'admin', 0, 2, 1, '女', '0', 'sys_user_sex', NULL, 'default', '1', '0');
INSERT INTO `base_dict_data` VALUES (71185073868636165, '2022-07-16 18:24:04', 'admin', '2022-07-16 19:04:24', 'admin', 0, 6, 1, '正常', '0', 'sys_normal_disable', NULL, 'primary', '1', '0');
INSERT INTO `base_dict_data` VALUES (71185151308070917, '2022-07-16 18:24:22', 'admin', '2023-03-31 14:09:20', '1000000000', 0, 7, 2, '停用', '1', 'sys_normal_disable', NULL, 'danger', '0', '0');
INSERT INTO `base_dict_data` VALUES (115949247513755653, '2022-08-14 16:14:37', 'admin', '2023-04-23 15:08:18', '1000000000', 0, 0, 1, '显示', '0', 'sys_show_hide', NULL, 'success', 'N', '0');
INSERT INTO `base_dict_data` VALUES (115949251380379653, '2022-08-14 16:14:52', 'admin', '2023-04-23 15:08:13', '1000000000', 0, 0, 2, '隐藏', '1', 'sys_show_hide', NULL, 'warning', 'N', '0');
INSERT INTO `base_dict_data` VALUES (116335305682321413, '2022-08-31 17:19:32', 'admin', '2022-08-31 17:19:32', 'admin', 0, 0, 1, '正常', '0', 'sys_common_status', NULL, 'success', 'N', '0');
INSERT INTO `base_dict_data` VALUES (116335309412630533, '2022-08-31 17:19:46', 'admin', '2022-08-31 17:19:46', 'admin', 0, 0, 2, '失败', '1', 'sys_common_status', NULL, 'danger', 'N', '0');
INSERT INTO `base_dict_data` VALUES (116494052907286533, '2022-09-07 17:32:25', 'admin', '2022-09-07 17:32:25', 'admin', 0, 0, 1, '正常', '0', 'sys_job_status', NULL, 'success', 'N', '0');
INSERT INTO `base_dict_data` VALUES (116494056427618309, '2022-09-07 17:32:38', 'admin', '2022-09-07 17:32:53', 'admin', 0, 2, 2, '暂停', '1', 'sys_job_status', NULL, 'info', 'N', '0');
INSERT INTO `base_dict_data` VALUES (116494076731457541, '2022-09-07 17:33:55', 'admin', '2022-09-07 17:33:55', 'admin', 0, 0, 1, '默认', 'DEFAULT', 'sys_job_group', NULL, 'primary', 'N', '0');
INSERT INTO `base_dict_data` VALUES (116494080265682949, '2022-09-07 17:34:09', 'admin', '2022-09-24 23:53:45', 'admin', 0, 1, 2, '系统', 'SYSTEM', 'sys_job_group', NULL, 'primary', 'N', '0');
INSERT INTO `base_dict_data` VALUES (1672250907955421186, '2023-06-23 22:31:00', '1000000000', '2023-06-23 22:31:00', '1000000000', 0, 0, 1, '全部', '0', 'domain_type', NULL, NULL, 'N', '0');
INSERT INTO `base_dict_data` VALUES (1677131223199125505, '2023-07-07 09:43:37', '1000000000', '2023-07-07 09:46:52', '1000000000', 0, 0, 1, 'gpt-3.5-turbo', 'gpt-3.5-turbo', 'openai_chat_model', NULL, 'success', 'N', '0');
INSERT INTO `base_dict_data` VALUES (1677131522705985538, '2023-07-07 09:44:49', '1000000000', '2023-07-07 09:46:56', '1000000000', 0, 0, 2, 'gpt-4', 'gpt-4', 'openai_chat_model', NULL, 'success', 'N', '0');
INSERT INTO `base_dict_data` VALUES (1677132016526561281, '2023-07-07 09:46:47', '1000000000', '2023-07-07 09:46:47', '1000000000', 0, 0, 3, 'gpt-3.5-turbo-16k', 'gpt-3.5-turbo-16k', 'openai_chat_model', NULL, 'primary', 'N', '0');
INSERT INTO `base_dict_data` VALUES (1677132817252749313, '2023-07-07 09:49:57', '1000000000', '2023-07-07 09:49:57', '1000000000', 0, 0, 1, 'text-davinci-003', 'text-davinci-003', 'openai_text_model', NULL, 'success', 'N', '0');
INSERT INTO `base_dict_data` VALUES (1677132850886873090, '2023-07-07 09:50:05', '1000000000', '2023-07-07 09:50:05', '1000000000', 0, 0, 2, 'text-davinci-002', 'text-davinci-002', 'openai_text_model', NULL, NULL, 'N', '0');

-- ----------------------------
-- Table structure for base_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `base_dict_type`;
CREATE TABLE `base_dict_type`  (
  `id` bigint NOT NULL COMMENT '主键',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_oper` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `update_oper` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标识 0 未删除 1 已删除',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典名称',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`dict_type` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典类型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of base_dict_type
-- ----------------------------
INSERT INTO `base_dict_type` VALUES (70519580258205658, '2022-07-16 18:54:59', NULL, '2022-11-14 18:03:12', 'admin', 0, 8, 'sys_normal_disable', '系统开关列表', '0', '系统开关列表');
INSERT INTO `base_dict_type` VALUES (70519580258205701, '2022-07-14 22:19:38', 'admin', '2022-07-18 22:00:33', 'admin', 0, 2, 'sys_user_sex', '性别', '0', '性别');
INSERT INTO `base_dict_type` VALUES (115949241785647109, '2022-08-14 16:14:15', 'admin', '2023-03-31 14:10:05', '1000000000', 0, 1, 'sys_show_hide', '菜单状态', '0', NULL);
INSERT INTO `base_dict_type` VALUES (116335299423109125, '2022-08-31 17:19:08', 'admin', '2022-08-31 17:25:49', 'admin', 0, 1, 'sys_common_status', '公共状态', '0', '公共状态');
INSERT INTO `base_dict_type` VALUES (116494046560780293, '2022-09-07 17:32:00', 'admin', '2022-09-07 17:32:00', 'admin', 0, 0, 'sys_job_status', '任务状态', '0', '任务状态');
INSERT INTO `base_dict_type` VALUES (116494070838984709, '2022-09-07 17:33:33', 'admin', '2022-09-07 17:33:33', 'admin', 0, 0, 'sys_job_group', '任务分组', '0', NULL);
INSERT INTO `base_dict_type` VALUES (1672250682494803970, '2023-06-23 22:30:06', '1000000000', '2023-06-23 22:30:06', '1000000000', 0, 0, 'domain_type', '领域会话类型', '0', '领域会话类型');
INSERT INTO `base_dict_type` VALUES (1677131122405806081, '2023-07-07 09:43:13', '1000000000', '2023-07-07 09:44:32', '1000000000', 0, 0, 'openai_chat_model', 'OpenAi聊天模型', '0', '/v1/chat/completions');
INSERT INTO `base_dict_type` VALUES (1677132762823266306, '2023-07-07 09:49:44', '1000000000', '2023-07-07 09:49:44', '1000000000', 0, 0, 'openai_text_model', 'Openai文本模型', '0', '/v1/completions');

-- ----------------------------
-- Table structure for base_resource_config
-- ----------------------------
DROP TABLE IF EXISTS `base_resource_config`;
CREATE TABLE `base_resource_config`  (
  `id` bigint NOT NULL COMMENT '主键',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `update_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标识 0 未删除 1 已删除',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  `config_key` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源键',
  `resource_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源值',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `config_key`(`config_key` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统参数配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of base_resource_config
-- ----------------------------
INSERT INTO `base_resource_config` VALUES (102061615, NULL, NULL, '2023-08-23 08:55:18', '1000000000', 0, 12, 'main', '{\"maxUserLogin\":5,\"staticWebsite\":\"http://chat.static.equinox19.xyz\",\"website\":\"http://localhost:9000\",\"registerOpen\":true,\"authCodeOpen\":false,\"fileSaveStrategy\":\"qiniu\",\"proxyHost\":\"127.0.0.1\",\"proxyPort\":\"7890\",\"ableSystemApiKey\":true,\"streamResponseType\":\"Websocket\"}');
INSERT INTO `base_resource_config` VALUES (112061615, NULL, NULL, '2023-08-09 10:21:32', '1000000000', 0, 0, 'openai', '{\"proxyHost\":\"127.0.0.1\",\"proxyPort\":\"7890\",\"chatModel\":\"gpt-3.5-turbo\",\"textModel\":\"text-davinci-003\",\"drawApiCacheTime\":\"2\",\"drawApiSendMax\":\"1\"}');
INSERT INTO `base_resource_config` VALUES (112241615, NULL, NULL, '2023-09-11 13:15:06', '1000000000', 0, 0, 'draw', '{\"sdHostUrl\":\"http://xxxx.com\",\"openDrawOpenai\":false,\"defaultNegativePrompt\":\"(worst quality:2), (low quality:2), (normal quality:2), lowres, normal quality,(monochrome)), ((grayscale)), skin spots, acnes, skin blemishes, age spot, (ugly:1.331),duplicate:1.331), (morbid:1.21), (mutilated:1.21), (tranny:l.331), mutated hands, (poorly drawnands:1.5), blurry, (bad anatomy:1.21), (bad proportions:1.331), extra limbs, (disfigured:1.331),missing arms:1.331), (extra legs:1.331), (fused fingers:1.61051), (too many fingers:1.61051),unclear eyes:1.331), lowers, bad hands, missing fingers, extra digit,bad hands, missing fingers.((extra arms and legs))),lowres, bad anatomy, bad hands, text, error, missing fingers, extra digit, fewer digits, cropped, worst quality, low quality, normal quality, jpeg artifacts, signature, watermark, username, blurry, bad-hands-5\",\"openSensitiveLimit\":true,\"sensitiveContent\":\"NSFW\",\"openBeforePromptContent\":true,\"beforePromptContent\":\"NSFW\",\"defaultRequestBean\":\"{\\n    \\\"steps\\\": 25,\\n    \\\"sampler_name\\\": \\\"Euler a\\\",\\n    \\\"restore_faces\\\": true,\\n    \\\"seed\\\": -1,\\n    \\\"denoisingStrength\\\": 0.7,\\n    \\\"hrScale\\\": 2.0\\n}\"}');

-- ----------------------------
-- Table structure for sys_file_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_file_config`;
CREATE TABLE `sys_file_config`  (
  `id` bigint NOT NULL,
  `create_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` int NULL DEFAULT 0,
  `unique_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '策略标识',
  `save_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '保存路径',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件上传配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_file_config
-- ----------------------------
INSERT INTO `sys_file_config` VALUES (1, NULL, NULL, '1000000000', '2023-07-02 14:41:55', 0, 'local', 'E:\\doc\\hugai\\fileUpload\\');
INSERT INTO `sys_file_config` VALUES (2, NULL, NULL, NULL, NULL, 0, 'server', '/root/fileUpload/');
INSERT INTO `sys_file_config` VALUES (3, NULL, NULL, '1000000000', '2023-07-19 18:06:11', 0, 'qiniu', '/image/');

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job`  (
  `id` bigint NOT NULL COMMENT '任务ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注信息',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `update_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标识 0 未删除 1 已删除',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  PRIMARY KEY (`id`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '定时任务调度表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_job
-- ----------------------------
INSERT INTO `sys_job` VALUES (1, '系统默认（无参）', 'DEFAULT', 'baseDictTypeServiceImpl.test()', '0/10 * * * * ?', '3', '1', '1', '', '2022-10-13 10:36:16', NULL, '2023-06-03 16:27:29', '1000000000', 0, 28);
INSERT INTO `sys_job` VALUES (2, '系统默认（有参）', 'DEFAULT', 'ryTask.ryParams(\'ry\')', '0/15 * * * * ?', '3', '1', '1', '', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `sys_job` VALUES (3, '系统默认（多参）', 'DEFAULT', 'ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)', '0/20 * * * * ?', '3', '1', '1', '', NULL, NULL, NULL, NULL, 0, 0);

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log`  (
  `id` bigint NOT NULL COMMENT '任务日志ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志信息',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '异常信息',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `update_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标识 0 未删除 1 已删除',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '定时任务调度日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_job_log
-- ----------------------------
INSERT INTO `sys_job_log` VALUES (116516108378046469, '系统默认（无参）', 'DEFAULT', 'baseDictTypeServiceImpl.test()', '系统默认（无参） 总共耗时：36毫秒', '0', '', '2022-09-08 16:54:40', NULL, '2022-09-08 16:54:40', NULL, 0, 0);

-- ----------------------------
-- Table structure for sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor`  (
  `id` bigint NOT NULL COMMENT '主键',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `update_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标识 0 未删除 1 已删除',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户账号',
  `ipaddr` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `status` int NULL DEFAULT 0 COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '提示消息',
  `login_time` datetime NULL DEFAULT NULL COMMENT '访问时间',
  `login_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登陆类型 0 系统用户 1 普通用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统访问记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_logininfor
-- ----------------------------
INSERT INTO `sys_logininfor` VALUES (1694145912932175874, '2023-08-23 08:33:55', NULL, '2023-08-23 08:33:56', NULL, 0, 0, 'admin', '127.0.0.1', '内网IP', 'FIREFOX11', 'Windows 10', 0, '登录成功', '2023-08-23 08:33:56', '1');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint NOT NULL COMMENT '主键',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `update_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标识 0 未删除 1 已删除',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由参数',
  `state_frame` int NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `state_cache` int NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `sort` int NULL DEFAULT NULL COMMENT '显示顺序',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '别名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '2022-06-06 11:17:50', 'admin', '2023-06-27 16:50:56', '1000000000', 0, 5, '系统管理', 0, 1, '/system', NULL, '', 0, 0, 'M', '0', '0', 'Settings', 1000, NULL);
INSERT INTO `sys_menu` VALUES (2, '2022-06-06 11:17:50', 'admin', '2023-06-27 16:58:52', '1000000000', 0, 6, '系统监控', 116497084306948101, 2, 'monitor', NULL, '', 0, 0, 'M', '0', '0', 'Signals', 1003, NULL);
INSERT INTO `sys_menu` VALUES (100, '2022-06-06 11:17:50', 'admin', '2023-06-27 16:57:13', '1000000000', 0, 3, '系统用户管理', 1, 1, 'user', 'system/user/index', '', 0, 0, 'C', '0', '0', 'User', 1, NULL);
INSERT INTO `sys_menu` VALUES (101, '2022-06-06 11:17:50', 'admin', '2023-06-27 16:57:43', '1000000000', 0, 3, '角色管理', 116896341546500101, 2, 'role', 'system/role/index', '', 0, 0, 'C', '0', '0', 'Profile', 1, NULL);
INSERT INTO `sys_menu` VALUES (102, '2022-06-06 11:17:50', 'admin', '2023-06-27 16:57:53', '1000000000', 0, 3, '菜单管理', 116896341546500101, 3, 'menu', 'system/menu/index', '', 0, 0, 'C', '0', '0', 'Credit Card', 2, NULL);
INSERT INTO `sys_menu` VALUES (105, '2022-06-06 11:17:50', 'admin', '2023-06-27 16:57:21', '1000000000', 0, 2, '字典管理', 1, 6, 'dict', 'system/dict/index', '', 0, 0, 'C', '0', '0', 'Book', 6, NULL);
INSERT INTO `sys_menu` VALUES (108, '2022-06-06 11:17:50', 'admin', '2023-06-27 16:58:41', '1000000000', 0, 6, '日志管理', 116497084306948101, 9, '/log', '', '', 0, 0, 'M', '0', '0', 'Document', 1002, NULL);
INSERT INTO `sys_menu` VALUES (109, '2022-06-06 11:17:50', 'admin', '2022-06-06 11:17:50', 'admin', 0, 0, '在线用户', 2, 1, 'online', 'monitor/online/index', '', 0, 0, 'C', '0', '0', 'online', 0, NULL);
INSERT INTO `sys_menu` VALUES (110, '2022-06-06 11:17:50', 'admin', '2022-06-06 11:17:50', 'admin', 0, 0, '定时任务', 2, 2, 'job', 'monitor/job/index', '', 0, 0, 'C', '0', '0', 'job', 0, NULL);
INSERT INTO `sys_menu` VALUES (115, '2022-06-06 11:17:50', 'admin', '2022-09-07 20:46:03', 'admin', 1, 2, '代码生成', 116497084306948101, 2, 'gen', 'tool/gen/index', '', 0, 0, 'C', '0', '0', 'code', 2, NULL);
INSERT INTO `sys_menu` VALUES (500, '2022-06-06 11:17:50', 'admin', '2022-06-06 11:17:50', 'admin', 0, 0, '操作日志', 108, 1, 'operlog', 'monitor/operlog/index', '', 0, 0, 'C', '0', '0', 'form', 0, NULL);
INSERT INTO `sys_menu` VALUES (501, '2022-06-06 11:17:50', 'admin', '2022-06-06 11:17:50', 'admin', 0, 0, '登录日志', 108, 2, 'logininfor', 'monitor/logininfor/index', '', 0, 0, 'C', '0', '0', 'logininfor', 0, NULL);
INSERT INTO `sys_menu` VALUES (116497084306948101, '2022-09-07 20:45:08', 'admin', '2023-06-27 16:51:20', '1000000000', 0, 3, '系统工具', 0, 0, '/tool', NULL, NULL, 0, 0, 'M', '0', '0', 'Inbox', 1004, NULL);
INSERT INTO `sys_menu` VALUES (116896341546500101, '2022-09-25 11:49:14', 'admin', '2023-06-27 16:51:07', '1000000000', 0, 2, '权限管理', 0, 0, '/auth', NULL, NULL, 0, 0, 'M', '0', '0', 'Share', 1001, NULL);
INSERT INTO `sys_menu` VALUES (116901224459272197, '2022-09-25 16:59:41', 'admin', '2023-06-27 16:58:07', '1000000000', 0, 1, '接口管理', 116896341546500101, 0, 'permission', 'system/permission/index', NULL, 0, 0, 'C', '0', '0', 'Diary', 3, NULL);
INSERT INTO `sys_menu` VALUES (117242727687258117, '2022-10-10 18:51:52', 'admin', '2023-06-27 16:57:00', '1000000000', 0, 14, '网站配置', 117256171332567045, 0, 'webset', 'system/webset/index', NULL, 0, 0, 'C', '0', '0', 'Toggle', 1, NULL);
INSERT INTO `sys_menu` VALUES (117256171332567045, '2022-10-11 09:06:36', 'admin', '2023-06-27 16:45:04', '1000000000', 0, 2, '网站设置', 0, 0, '/webset', NULL, NULL, 0, 0, 'M', '0', '0', 'Toggle', 99, NULL);
INSERT INTO `sys_menu` VALUES (1673254130736492546, '2023-06-26 16:57:26', '1000000000', '2023-06-27 16:44:22', '1000000000', 0, 0, '业务配置', 0, 0, '/businessConfig', NULL, NULL, 0, 0, 'M', '0', '0', 'Briefcase', 1, NULL);
INSERT INTO `sys_menu` VALUES (1673256869570867202, '2023-06-26 17:08:19', '1000000000', '2023-06-27 16:54:52', '1000000000', 0, 0, '领域会话配置', 1673254130736492546, 0, 'domainconfig', 'business/domain/index', NULL, 0, 0, 'C', '0', '0', 'Attachment', 1, NULL);
INSERT INTO `sys_menu` VALUES (1687035996077555714, '2023-08-03 17:41:39', '1000000000', '2023-08-03 17:41:53', '1000000000', 0, 0, '用户中心', 0, 0, '/userBusiness', NULL, NULL, 0, 0, 'M', '0', '0', 'Users', 1, NULL);
INSERT INTO `sys_menu` VALUES (1687036271567831042, '2023-08-03 17:42:45', '1000000000', '2023-08-03 17:43:17', '1000000000', 0, 0, '用户信息维护', 1687035996077555714, 0, 'clientUser', 'business/user/index', NULL, 0, 0, 'C', '0', '0', 'User', 1, NULL);
INSERT INTO `sys_menu` VALUES (1691709718004781057, '2023-08-16 15:13:21', '1000000000', '2023-08-16 15:13:38', '1000000000', 0, 0, '系统秘钥管理', 1673254130736492546, 0, 'apikeymanager', 'business/apikeys/index', NULL, 0, 0, 'C', '0', '0', 'Book', 2, NULL);
INSERT INTO `sys_menu` VALUES (1692358229926928385, '2023-08-18 10:10:19', '1000000000', '2023-08-18 10:10:19', '1000000000', 0, 0, '通知公告管理', 1673254130736492546, 0, 'noticeClient', 'business/noticeclient/index', NULL, 0, 0, 'C', '0', '0', 'Notification', 3, NULL);

-- ----------------------------
-- Table structure for sys_minio_secret
-- ----------------------------
DROP TABLE IF EXISTS `sys_minio_secret`;
CREATE TABLE `sys_minio_secret`  (
  `id` bigint NOT NULL,
  `create_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` int NULL DEFAULT 0,
  `unique_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '策略标识',
  `access_key` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'accessKey',
  `secret_key` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'secretKey ',
  `bucket_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '存储空间名称',
  `data_handle_domain` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据处理服务域名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件上传配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_minio_secret
-- ----------------------------
INSERT INTO `sys_minio_secret` VALUES (1, NULL, NULL, NULL, NULL, 0, 'qiniu', 'xxxxxx', 'xxxxx', 'hugai', 'http://xxxxx.com');

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log`  (
  `id` bigint NOT NULL COMMENT '主键',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '模块标题',
  `method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求方式',
  `operator_type` int NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '操作人员',
  `oper_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '返回参数',
  `status` int NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '错误消息',
  `oper_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `update_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标识 0 未删除 1 已删除',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '操作日志记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_oper_log
-- ----------------------------
INSERT INTO `sys_oper_log` VALUES (1694151291011371009, '系统参数配置 [编辑系统参数配置信息]', 'com.hugai.modules.system.controller.ResourceConfigController.editByConfigKey()', 'PUT', 0, 'admin', '/module/system/baseresourceconfig/editByConfigKey', '127.0.0.1', '内网IP', '{\"configKey\":\"main\",\"delFlag\":0,\"page\":1,\"paramExtMap\":{},\"queryCondition\":{},\"resourceValue\":\"{\\\"maxUserLogin\\\":5,\\\"staticWebsite\\\":\\\"http://chat.static.equinox19.xyz\\\",\\\"website\\\":\\\"http://localhost:9000\\\",\\\"registerOpen\\\":true,\\\"authCodeOpen\\\":false,\\\"fileSaveStrategy\\\":\\\"qiniu\\\",\\\"proxyHost\\\":\\\"127.0.0.1\\\",\\\"proxyPort\\\":\\\"7890\\\",\\\"ableSystemApiKey\\\":true,\\\"streamResponseType\\\":\\\"Websocket\\\"}\",\"size\":10,\"sortCondition\":{}}', '{\"code\":200,\"message\":\"请求成功\",\"status\":true,\"timestamp\":1692752118177}', 0, '', NULL, '2023-08-23 08:55:18', NULL, '2023-08-23 08:55:18', NULL, 0, 0);

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` bigint NOT NULL COMMENT '主键',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `update_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标识 0 未删除 1 已删除',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父id',
  `ancestors` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '祖籍列表',
  `module_controller` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模块控制器对象',
  `module_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模块名称',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `original_route_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '原始路由',
  `route_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接口路由',
  `permission_tag` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接口标识',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方式 GET POST...',
  `if_route` tinyint NULL DEFAULT 0 COMMENT '是否路由 0 否 1 是',
  `route_visit_rule` tinyint NULL DEFAULT NULL COMMENT '接口访问规则\r\n1 授权访问\r\n2 匿名访问（不携带token）\r\n3 完全放行\r\n4 不可访问',
  `if_usable` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否可用的',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `parent_id`(`parent_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1674578728694370305, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582491907805185, '0,1674579384591241218,1674582491907805185', 'sysRoleController', '角色信息', 'selectAuthUserAll', '/module/system/sysrole/authUser/selectAll', '/module/system/sysrole/authUser/selectAll', 'module:system:sysrole:authUser:selectAll', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728694370306, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582439277678594, '0,1674579384591241218,1674582439277678594', 'loginPcController', '登陆控制器', 'register', '/auth/system/register', '/auth/system/register', 'auth:system:register', 'POST', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1674578728694370307, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674580927755374593, '0,1674578853248421890,1674580927755374593', 'sysUserController', '用户信息', 'baseQueryByParam', '/module/system/sysuser/baseQueryByParam', '/module/system/sysuser/baseQueryByParam', 'module:system:sysuser:baseQueryByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728694370309, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582491907805185, '0,1674579384591241218,1674582491907805185', 'sysRoleController', '角色信息', 'baseAdd', '/module/system/sysrole/baseAdd', '/module/system/sysrole/baseAdd', 'module:system:sysrole:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728694370310, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674581849239769090, '0,1674579114499035137,1674581849239769090', 'sysOperLogController', '操作日志记录- 控制器', 'baseQueryPageByParam', '/module/system/sysoperlog/baseQueryPageByParam', '/module/system/sysoperlog/baseQueryPageByParam', 'module:system:sysoperlog:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728694370311, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674581814276050945, '0,1674579114499035137,1674581814276050945', 'sysJobLogController', '定时任务调度日志表', 'baseQueryByParam', '/module/quartz/sysjoblog/baseQueryByParam', '/module/quartz/sysjoblog/baseQueryByParam', 'module:quartz:sysjoblog:baseQueryByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479170, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582818715389954, '0,1674580535470510082,1674582818715389954', 'resourceConfigController', '系统参数配置', 'baseEdit', '/module/system/baseresourceconfig/baseEdit', '/module/system/baseresourceconfig/baseEdit', 'module:system:baseresourceconfig:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479171, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674581814276050945, '0,1674579114499035137,1674581814276050945', 'sysJobLogController', '定时任务调度日志表', 'baseQueryPageByParam', '/module/quartz/sysjoblog/baseQueryPageByParam', '/module/quartz/sysjoblog/baseQueryPageByParam', 'module:quartz:sysjoblog:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479172, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674581645400788994, '0,1674579089589063681,1674581645400788994', 'sessionInfoController', '会话表', 'deleteSession', '/module/session/sessioninfo/deleteSession/{sessionIds}', '/module/session/sessioninfo/deleteSession/*', 'module:session:sessioninfo:deleteSession:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479173, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582466536460289, '0,1674579384591241218,1674582466536460289', 'sysPermissionController', '权限管理', 'baseQueryPageByParam', '/module/system/syspermission/baseQueryPageByParam', '/module/system/syspermission/baseQueryPageByParam', 'module:system:syspermission:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479174, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582466536460289, '0,1674579384591241218,1674582466536460289', 'sysPermissionController', '权限管理', 'getPermissionModuleLabelOption', '/module/system/syspermission/getPermissionModuleLabelOption', '/module/system/syspermission/getPermissionModuleLabelOption', 'module:system:syspermission:getPermissionModuleLabelOption', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479175, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582491907805185, '0,1674579384591241218,1674582491907805185', 'sysRoleController', '角色信息', 'getLabelOption', '/module/system/sysrole/getLabelOption', '/module/system/sysrole/getLabelOption', 'module:system:sysrole:getLabelOption', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479176, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582505417658369, '0,1674579384591241218,1674582505417658369', 'sysMenuController', '菜单权限', 'selectPermissionTree', '/module/system/sysmenu/selectPermissionTree', '/module/system/sysmenu/selectPermissionTree', 'module:system:sysmenu:selectPermissionTree', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479177, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictDataController', '字典数据', 'baseQueryByParam', '/module/system/basedictdata/baseQueryByParam', '/module/system/basedictdata/baseQueryByParam', 'module:system:basedictdata:baseQueryByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479178, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674580927755374593, '0,1674578853248421890,1674580927755374593', 'sysProfileController', '个人业务', 'avatar', '/module/system/profile/avatar', '/module/system/profile/avatar', 'module:system:profile:avatar', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479179, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674579841480970241, '0,1674578906511888386,1674579841480970241', 'sysJobController', '定时任务调度表', 'baseAdd', '/module/quartz/sysjob/baseAdd', '/module/quartz/sysjob/baseAdd', 'module:quartz:sysjob:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479180, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674581849239769090, '0,1674579114499035137,1674581849239769090', 'sysOperLogController', '操作日志记录- 控制器', 'baseAdd', '/module/system/sysoperlog/baseAdd', '/module/system/sysoperlog/baseAdd', 'module:system:sysoperlog:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479181, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582439277678594, '0,1674579384591241218,1674582439277678594', 'loginPcController', '登陆控制器', 'info', '/auth/system/info', '/auth/system/info', 'auth:system:info', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479182, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674580927755374593, '0,1674578853248421890,1674580927755374593', 'sysUserController', '用户信息', 'insertAuthRole', '/module/system/sysuser/insertAuthRole', '/module/system/sysuser/insertAuthRole', 'module:system:sysuser:insertAuthRole', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479183, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674579841480970241, '0,1674578906511888386,1674579841480970241', 'sysJobController', '定时任务调度表', 'baseQueryPageByParam', '/module/quartz/sysjob/baseQueryPageByParam', '/module/quartz/sysjob/baseQueryPageByParam', 'module:quartz:sysjob:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479184, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674583564886265858, '0,1674579089589063681,1674583564886265858', 'domainController', '领域会话', 'baseQueryPageByParam', '/module/session/domain/baseQueryPageByParam', '/module/session/domain/baseQueryPageByParam', 'module:session:domain:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479185, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582818715389954, '0,1674580535470510082,1674582818715389954', 'resourceConfigController', '系统参数配置', 'baseAdd', '/module/system/baseresourceconfig/baseAdd', '/module/system/baseresourceconfig/baseAdd', 'module:system:baseresourceconfig:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479187, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674578906511888386, '0,1674578906511888386', 'apiResourceController', '', 'uiConfiguration', '/swagger-resources/configuration/ui', '/swagger-resources/configuration/ui', 'swagger-resources:configuration:ui', '', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479188, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674581892462071809, '0,1674579114499035137,1674581892462071809', 'sysLogininforController', '系统访问记录- 控制器', 'baseQueryByParam', '/module/system/syslogininfor/baseQueryByParam', '/module/system/syslogininfor/baseQueryByParam', 'module:system:syslogininfor:baseQueryByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479189, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582466536460289, '0,1674579384591241218,1674582466536460289', 'sysPermissionController', '权限管理', 'baseQueryByParam', '/module/system/syspermission/baseQueryByParam', '/module/system/syspermission/baseQueryByParam', 'module:system:syspermission:baseQueryByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479190, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictDataController', '字典数据', 'baseEdit', '/module/system/basedictdata/baseEdit', '/module/system/basedictdata/baseEdit', 'module:system:basedictdata:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479193, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674580956448608258, '0,1674578853248421890,1674580956448608258', 'userInfoController', '用户控制器', 'login', '/module/user/userinfo/login', '/module/user/userinfo/login', 'module:user:userinfo:login', 'POST', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479194, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674583544443228161, '0,1674579089589063681,1674583544443228161', 'sessionRecordController', '会话详情', 'getRecordSession', '/module/session/sessionrecord/getRecordSession', '/module/session/sessionrecord/getRecordSession', 'module:session:sessionrecord:getRecordSession', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479195, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674583564886265858, '0,1674579089589063681,1674583564886265858', 'domainController', '领域会话', 'baseAdd', '/module/session/domain/baseAdd', '/module/session/domain/baseAdd', 'module:session:domain:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479196, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582466536460289, '0,1674579384591241218,1674582466536460289', 'sysPermissionController', '权限管理', 'allocationRouteModule', '/module/system/syspermission/allocationRouteModule', '/module/system/syspermission/allocationRouteModule', 'module:system:syspermission:allocationRouteModule', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479197, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674580956448608258, '0,1674578853248421890,1674580956448608258', 'userInfoController', '用户控制器', 'register', '/module/user/userinfo/register', '/module/user/userinfo/register', 'module:user:userinfo:register', 'POST', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479198, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674579841480970241, '0,1674578906511888386,1674579841480970241', 'sysJobController', '定时任务调度表', 'baseDeleteByIds', '/module/quartz/sysjob/baseDeleteByIds/{ids}', '/module/quartz/sysjob/baseDeleteByIds/*', 'module:quartz:sysjob:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479199, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674580927755374593, '0,1674578853248421890,1674580927755374593', 'sysUserController', '用户信息', 'changeStatus', '/module/system/sysuser/changeStatus', '/module/system/sysuser/changeStatus', 'module:system:sysuser:changeStatus', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479200, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582491907805185, '0,1674579384591241218,1674582491907805185', 'sysRoleController', '角色信息', 'baseQueryById', '/module/system/sysrole/baseQueryById/{id}', '/module/system/sysrole/baseQueryById/*', 'module:system:sysrole:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479201, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictDataController', '字典数据', 'baseQueryPageByParam', '/module/system/basedictdata/baseQueryPageByParam', '/module/system/basedictdata/baseQueryPageByParam', 'module:system:basedictdata:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479203, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674581849239769090, '0,1674579114499035137,1674581849239769090', 'sysOperLogController', '操作日志记录- 控制器', 'baseEdit', '/module/system/sysoperlog/baseEdit', '/module/system/sysoperlog/baseEdit', 'module:system:sysoperlog:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479204, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictDataController', '字典数据', 'baseQueryById', '/module/system/basedictdata/baseQueryById/{id}', '/module/system/basedictdata/baseQueryById/*', 'module:system:basedictdata:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479205, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674581849239769090, '0,1674579114499035137,1674581849239769090', 'sysOperLogController', '操作日志记录- 控制器', 'baseDeleteByIds', '/module/system/sysoperlog/baseDeleteByIds/{ids}', '/module/system/sysoperlog/baseDeleteByIds/*', 'module:system:sysoperlog:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479206, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582466536460289, '0,1674579384591241218,1674582466536460289', 'sysPermissionController', '权限管理', 'baseAdd', '/module/system/syspermission/baseAdd', '/module/system/syspermission/baseAdd', 'module:system:syspermission:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479207, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674581892462071809, '0,1674579114499035137,1674581892462071809', 'sysLogininforController', '系统访问记录- 控制器', 'clean', '/module/system/syslogininfor/clean', '/module/system/syslogininfor/clean', 'module:system:syslogininfor:clean', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479208, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674583564886265858, '0,1674579089589063681,1674583564886265858', 'domainController', '领域会话', 'baseQueryByParam', '/module/session/domain/baseQueryByParam', '/module/session/domain/baseQueryByParam', 'module:session:domain:baseQueryByParam', 'POST', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479209, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674581645400788994, '0,1674579089589063681,1674581645400788994', 'sessionInfoController', '会话表', 'userLastSession', '/module/session/sessioninfo/userLastSession/{sessionType}', '/module/session/sessioninfo/userLastSession/*', 'module:session:sessioninfo:userLastSession:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479210, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582466536460289, '0,1674579384591241218,1674582466536460289', 'sysPermissionController', '权限管理', 'baseDeleteByIds', '/module/system/syspermission/baseDeleteByIds/{ids}', '/module/system/syspermission/baseDeleteByIds/*', 'module:system:syspermission:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588034, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582466536460289, '0,1674579384591241218,1674582466536460289', 'sysPermissionController', '权限管理', 'getPermissionNotRouteLabelOption', '/module/system/syspermission/getPermissionNotRouteLabelOption', '/module/system/syspermission/getPermissionNotRouteLabelOption', 'module:system:syspermission:getPermissionNotRouteLabelOption', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588035, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictTypeController', '字典类型', 'flushCache', '/module/system/basedicttype/flushCache', '/module/system/basedicttype/flushCache', 'module:system:basedicttype:flushCache', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588036, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674581849239769090, '0,1674579114499035137,1674581849239769090', 'sysOperLogController', '操作日志记录- 控制器', 'baseQueryById', '/module/system/sysoperlog/baseQueryById/{id}', '/module/system/sysoperlog/baseQueryById/*', 'module:system:sysoperlog:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588037, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674583564886265858, '0,1674579089589063681,1674583564886265858', 'domainController', '领域会话', 'getWindowData', '/module/session/domain/getWindowData/{domainKey}', '/module/session/domain/getWindowData/*', 'module:session:domain:getWindowData:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588038, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582491907805185, '0,1674579384591241218,1674582491907805185', 'sysRoleController', '角色信息', 'roleAllocationRoute', '/module/system/sysrole/roleAllocationRoute', '/module/system/sysrole/roleAllocationRoute', 'module:system:sysrole:roleAllocationRoute', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588039, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674578906511888386, '0,1674578906511888386', 'apiResourceController', '', 'swaggerResources', '/swagger-resources', '/swagger-resources', 'swagger-resources', '', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588040, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674581849239769090, '0,1674579114499035137,1674581849239769090', 'sysOperLogController', '操作日志记录- 控制器', 'baseQueryByParam', '/module/system/sysoperlog/baseQueryByParam', '/module/system/sysoperlog/baseQueryByParam', 'module:system:sysoperlog:baseQueryByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588041, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674581892462071809, '0,1674579114499035137,1674581892462071809', 'sysLogininforController', '系统访问记录- 控制器', 'remove', '/module/system/syslogininfor/{infoIds}', '/module/system/syslogininfor/*', 'module:system:syslogininfor:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588042, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582505417658369, '0,1674579384591241218,1674582505417658369', 'sysMenuController', '菜单权限', 'selectPermissionTreeModel', '/module/system/sysmenu/selectPermissionTreeModel', '/module/system/sysmenu/selectPermissionTreeModel', 'module:system:sysmenu:selectPermissionTreeModel', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588043, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582491907805185, '0,1674579384591241218,1674582491907805185', 'sysRoleController', '角色信息', 'unallocatedList', '/module/system/sysrole/authUser/unallocatedList', '/module/system/sysrole/authUser/unallocatedList', 'module:system:sysrole:authUser:unallocatedList', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588044, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674581814276050945, '0,1674579114499035137,1674581814276050945', 'sysJobLogController', '定时任务调度日志表', 'baseEdit', '/module/quartz/sysjoblog/baseEdit', '/module/quartz/sysjoblog/baseEdit', 'module:quartz:sysjoblog:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588045, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582491907805185, '0,1674579384591241218,1674582491907805185', 'sysRoleController', '角色信息', 'allocatedList', '/module/system/sysrole/authUser/allocatedList', '/module/system/sysrole/authUser/allocatedList', 'module:system:sysrole:authUser:allocatedList', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588046, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674583564886265858, '0,1674579089589063681,1674583564886265858', 'domainController', '领域会话', 'baseQueryById', '/module/session/domain/baseQueryById/{id}', '/module/session/domain/baseQueryById/*', 'module:session:domain:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588047, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674580927755374593, '0,1674578853248421890,1674580927755374593', 'sysProfileController', '个人业务', 'profile', '/module/system/profile', '/module/system/profile', 'module:system:profile', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588048, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674581892462071809, '0,1674579114499035137,1674581892462071809', 'sysLogininforController', '系统访问记录- 控制器', 'baseAdd', '/module/system/syslogininfor/baseAdd', '/module/system/syslogininfor/baseAdd', 'module:system:syslogininfor:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588049, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582491907805185, '0,1674579384591241218,1674582491907805185', 'sysRoleController', '角色信息', 'baseDeleteByIds', '/module/system/sysrole/baseDeleteByIds/{ids}', '/module/system/sysrole/baseDeleteByIds/*', 'module:system:sysrole:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588050, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674579841480970241, '0,1674578906511888386,1674579841480970241', 'sysJobController', '定时任务调度表', 'changeStatus', '/module/quartz/sysjob/changeStatus', '/module/quartz/sysjob/changeStatus', 'module:quartz:sysjob:changeStatus', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588052, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674578906511888386, '0,1674578906511888386', 'basicErrorController', '', 'errorHtml', '/error', '/error', 'error', '', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588053, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674579182287376385, '0,1674578853248421890,1674579182287376385', 'sysUserOnlineController', '在线用户监控', 'baseEdit', '/module/system/monitor/online/baseEdit', '/module/system/monitor/online/baseEdit', 'module:system:monitor:online:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588054, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674580927755374593, '0,1674578853248421890,1674580927755374593', 'sysUserController', '用户信息', 'authRole', '/module/system/sysuser/authRole/{id}', '/module/system/sysuser/authRole/*', 'module:system:sysuser:authRole:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588055, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674578906511888386, '0,1674578906511888386', 'apiResourceController', '', 'securityConfiguration', '/swagger-resources/configuration/security', '/swagger-resources/configuration/security', 'swagger-resources:configuration:security', '', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588056, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582818715389954, '0,1674580535470510082,1674582818715389954', 'resourceConfigController', '系统参数配置', 'baseQueryById', '/module/system/baseresourceconfig/baseQueryById/{id}', '/module/system/baseresourceconfig/baseQueryById/*', 'module:system:baseresourceconfig:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588057, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582818715389954, '0,1674580535470510082,1674582818715389954', 'resourceConfigController', '系统参数配置', 'queryByConfigKey', '/module/system/baseresourceconfig/queryByConfigKey/{configKey}', '/module/system/baseresourceconfig/queryByConfigKey/*', 'module:system:baseresourceconfig:queryByConfigKey:*', 'GET', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588058, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674579708391510017, '0,1674578906511888386,1674579708391510017', 'commonController', '通用控制器', 'getEnumLabel', '/common/getEnumLabel/{key}', '/common/getEnumLabel/*', 'common:getEnumLabel:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588059, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582505417658369, '0,1674579384591241218,1674582505417658369', 'sysMenuController', '菜单权限', 'roleMenuTreeselect', '/module/system/sysmenu/roleMenuTreeselect/{roleId}', '/module/system/sysmenu/roleMenuTreeselect/*', 'module:system:sysmenu:roleMenuTreeselect:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588061, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674581814276050945, '0,1674579114499035137,1674581814276050945', 'sysJobLogController', '定时任务调度日志表', 'baseQueryById', '/module/quartz/sysjoblog/baseQueryById/{id}', '/module/quartz/sysjoblog/baseQueryById/*', 'module:quartz:sysjoblog:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588062, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674580927755374593, '0,1674578853248421890,1674580927755374593', 'sysUserController', '用户信息', 'baseDeleteByIds', '/module/system/sysuser/baseDeleteByIds/{ids}', '/module/system/sysuser/baseDeleteByIds/*', 'module:system:sysuser:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588063, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582439277678594, '0,1674579384591241218,1674582439277678594', 'loginPcController', '登陆控制器', 'login', '/auth/system/login', '/auth/system/login', 'auth:system:login', 'POST', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588064, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictDataController', '字典数据', 'baseDeleteByIds', '/module/system/basedictdata/baseDeleteByIds/{ids}', '/module/system/basedictdata/baseDeleteByIds/*', 'module:system:basedictdata:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588065, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674579182287376385, '0,1674578853248421890,1674579182287376385', 'sysUserOnlineController', '在线用户监控', 'baseDeleteByIds', '/module/system/monitor/online/baseDeleteByIds/{ids}', '/module/system/monitor/online/baseDeleteByIds/*', 'module:system:monitor:online:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588066, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictTypeController', '字典类型', 'baseAdd', '/module/system/basedicttype/baseAdd', '/module/system/basedicttype/baseAdd', 'module:system:basedicttype:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588067, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582818715389954, '0,1674580535470510082,1674582818715389954', 'resourceConfigController', '系统参数配置', 'editByConfigKey', '/module/system/baseresourceconfig/editByConfigKey', '/module/system/baseresourceconfig/editByConfigKey', 'module:system:baseresourceconfig:editByConfigKey', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588068, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582818715389954, '0,1674580535470510082,1674582818715389954', 'resourceConfigController', '系统参数配置', 'baseQueryByParam', '/module/system/baseresourceconfig/baseQueryByParam', '/module/system/baseresourceconfig/baseQueryByParam', 'module:system:baseresourceconfig:baseQueryByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588069, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674579841480970241, '0,1674578906511888386,1674579841480970241', 'sysJobController', '定时任务调度表', 'baseEdit', '/module/quartz/sysjob/baseEdit', '/module/quartz/sysjob/baseEdit', 'module:quartz:sysjob:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588070, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674581645400788994, '0,1674579089589063681,1674581645400788994', 'sessionInfoController', '会话表', 'getSessionInfo', '/module/session/sessioninfo/getSessionInfo/{sessionId}', '/module/session/sessioninfo/getSessionInfo/*', 'module:session:sessioninfo:getSessionInfo:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588071, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictTypeController', '字典类型', 'baseQueryPageByParam', '/module/system/basedicttype/baseQueryPageByParam', '/module/system/basedicttype/baseQueryPageByParam', 'module:system:basedicttype:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588072, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674580927755374593, '0,1674578853248421890,1674580927755374593', 'sysUserController', '用户信息', 'baseAdd', '/module/system/sysuser/baseAdd', '/module/system/sysuser/baseAdd', 'module:system:sysuser:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588073, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674580927755374593, '0,1674578853248421890,1674580927755374593', 'sysUserController', '用户信息', 'resetPwd', '/module/system/sysuser/resetPwd', '/module/system/sysuser/resetPwd', 'module:system:sysuser:resetPwd', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588074, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674583544443228161, '0,1674579089589063681,1674583544443228161', 'sessionRecordController', '会话详情', 'getPageRecordSession', '/module/session/sessionrecord/getPageRecordSession', '/module/session/sessionrecord/getPageRecordSession', 'module:session:sessionrecord:getPageRecordSession', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588075, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674581814276050945, '0,1674579114499035137,1674581814276050945', 'sysJobLogController', '定时任务调度日志表', 'clean', '/module/quartz/sysjoblog/clean', '/module/quartz/sysjoblog/clean', 'module:quartz:sysjoblog:clean', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588076, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674580927755374593, '0,1674578853248421890,1674580927755374593', 'sysUserController', '用户信息', 'baseQueryPageByParam', '/module/system/sysuser/baseQueryPageByParam', '/module/system/sysuser/baseQueryPageByParam', 'module:system:sysuser:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588077, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674579182287376385, '0,1674578853248421890,1674579182287376385', 'sysUserOnlineController', '在线用户监控', 'baseQueryById', '/module/system/monitor/online/baseQueryById/{id}', '/module/system/monitor/online/baseQueryById/*', 'module:system:monitor:online:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588078, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582491907805185, '0,1674579384591241218,1674582491907805185', 'sysRoleController', '角色信息', 'editRole', '/module/system/sysrole/editRole', '/module/system/sysrole/editRole', 'module:system:sysrole:editRole', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588079, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674581849239769090, '0,1674579114499035137,1674581849239769090', 'sysOperLogController', '操作日志记录- 控制器', 'cleanOperlog', '/module/system/sysoperlog/clean', '/module/system/sysoperlog/clean', 'module:system:sysoperlog:clean', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588080, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582466536460289, '0,1674579384591241218,1674582466536460289', 'sysPermissionController', '权限管理', 'baseQueryById', '/module/system/syspermission/baseQueryById/{id}', '/module/system/syspermission/baseQueryById/*', 'module:system:syspermission:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588081, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674579841480970241, '0,1674578906511888386,1674579841480970241', 'sysJobController', '定时任务调度表', 'run', '/module/quartz/sysjob/run', '/module/quartz/sysjob/run', 'module:quartz:sysjob:run', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588082, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674581645400788994, '0,1674579089589063681,1674581645400788994', 'sessionInfoController', '会话表', 'clearSession', '/module/session/sessioninfo/clearSession/{sessionId}', '/module/session/sessioninfo/clearSession/*', 'module:session:sessioninfo:clearSession:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588083, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674580927755374593, '0,1674578853248421890,1674580927755374593', 'sysUserController', '用户信息', 'baseEdit', '/module/system/sysuser/baseEdit', '/module/system/sysuser/baseEdit', 'module:system:sysuser:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588084, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictTypeController', '字典类型', 'baseDeleteByIds', '/module/system/basedicttype/baseDeleteByIds/{ids}', '/module/system/basedicttype/baseDeleteByIds/*', 'module:system:basedicttype:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588085, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674579841480970241, '0,1674578906511888386,1674579841480970241', 'sysJobController', '定时任务调度表', 'baseQueryById', '/module/quartz/sysjob/baseQueryById/{id}', '/module/quartz/sysjob/baseQueryById/*', 'module:quartz:sysjob:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729461927938, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582505417658369, '0,1674579384591241218,1674582505417658369', 'sysMenuController', '菜单权限', 'selectPermissionRoute', '/module/system/sysmenu/selectPermissionRoute', '/module/system/sysmenu/selectPermissionRoute', 'module:system:sysmenu:selectPermissionRoute', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729461927939, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582466536460289, '0,1674579384591241218,1674582466536460289', 'sysPermissionController', '权限管理', 'mappingSync', '/module/system/syspermission/mappingSync', '/module/system/syspermission/mappingSync', 'module:system:syspermission:mappingSync', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729529036801, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674583564886265858, '0,1674579089589063681,1674583564886265858', 'domainController', '领域会话', 'baseEdit', '/module/session/domain/baseEdit', '/module/session/domain/baseEdit', 'module:session:domain:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729529036802, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674581814276050945, '0,1674579114499035137,1674581814276050945', 'sysJobLogController', '定时任务调度日志表', 'baseAdd', '/module/quartz/sysjoblog/baseAdd', '/module/quartz/sysjoblog/baseAdd', 'module:quartz:sysjoblog:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729529036803, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582466536460289, '0,1674579384591241218,1674582466536460289', 'sysPermissionController', '权限管理', 'rolePermissionTreeList', '/module/system/syspermission/rolePermissionTreeList', '/module/system/syspermission/rolePermissionTreeList', 'module:system:syspermission:rolePermissionTreeList', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729529036804, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582491907805185, '0,1674579384591241218,1674582491907805185', 'sysRoleController', '角色信息', 'cancelAuthUser', '/module/system/sysrole/authUser/cancel', '/module/system/sysrole/authUser/cancel', 'module:system:sysrole:authUser:cancel', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729529036805, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictDataController', '字典数据', 'baseAdd', '/module/system/basedictdata/baseAdd', '/module/system/basedictdata/baseAdd', 'module:system:basedictdata:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729529036807, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674581892462071809, '0,1674579114499035137,1674581892462071809', 'sysLogininforController', '系统访问记录- 控制器', 'baseQueryPageByParam', '/module/system/syslogininfor/baseQueryPageByParam', '/module/system/syslogininfor/baseQueryPageByParam', 'module:system:syslogininfor:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729529036809, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictTypeController', '字典类型', 'baseQueryById', '/module/system/basedicttype/baseQueryById/{id}', '/module/system/basedicttype/baseQueryById/*', 'module:system:basedicttype:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729529036810, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674580956448608258, '0,1674578853248421890,1674580956448608258', 'userInfoController', '用户控制器', 'baseQueryPageByParam', '/module/user/userinfo/baseQueryPageByParam', '/module/user/userinfo/baseQueryPageByParam', 'module:user:userinfo:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922882, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582505417658369, '0,1674579384591241218,1674582505417658369', 'sysMenuController', '菜单权限', 'baseAdd', '/module/system/sysmenu/baseAdd', '/module/system/sysmenu/baseAdd', 'module:system:sysmenu:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922883, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582491907805185, '0,1674579384591241218,1674582491907805185', 'sysRoleController', '角色信息', 'baseQueryPageByParam', '/module/system/sysrole/baseQueryPageByParam', '/module/system/sysrole/baseQueryPageByParam', 'module:system:sysrole:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922884, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674580956448608258, '0,1674578853248421890,1674580956448608258', 'userInfoController', '游客登陆', 'touristLogin', '/module/user/userinfo/touristLogin', '/module/user/userinfo/touristLogin', 'module:user:userinfo:touristLogin', 'GET', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922885, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674580927755374593, '0,1674578853248421890,1674580927755374593', 'sysProfileController', '个人业务', 'updateUser', '/module/system/profile/updateUser', '/module/system/profile/updateUser', 'module:system:profile:updateUser', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922886, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582466536460289, '0,1674579384591241218,1674582466536460289', 'sysPermissionController', '权限管理', 'flushPermissionConfig', '/module/system/syspermission/flushPermissionConfig', '/module/system/syspermission/flushPermissionConfig', 'module:system:syspermission:flushPermissionConfig', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922887, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674580927755374593, '0,1674578853248421890,1674580927755374593', 'sysProfileController', '个人业务', 'resetPassword', '/module/system/profile/updatePwd', '/module/system/profile/updatePwd', 'module:system:profile:updatePwd', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922888, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674581892462071809, '0,1674579114499035137,1674581892462071809', 'sysLogininforController', '系统访问记录- 控制器', 'baseDeleteByIds', '/module/system/syslogininfor/baseDeleteByIds/{ids}', '/module/system/syslogininfor/baseDeleteByIds/*', 'module:system:syslogininfor:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922889, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582505417658369, '0,1674579384591241218,1674582505417658369', 'sysMenuController', '菜单权限', 'baseDeleteByIds', '/module/system/sysmenu/baseDeleteByIds/{ids}', '/module/system/sysmenu/baseDeleteByIds/*', 'module:system:sysmenu:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922890, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582505417658369, '0,1674579384591241218,1674582505417658369', 'sysMenuController', '菜单权限', 'baseQueryById', '/module/system/sysmenu/baseQueryById/{id}', '/module/system/sysmenu/baseQueryById/*', 'module:system:sysmenu:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922891, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictTypeController', '字典类型', 'optionSelect', '/module/system/basedicttype/optionSelect', '/module/system/basedicttype/optionSelect', 'module:system:basedicttype:optionSelect', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922892, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674581645400788994, '0,1674579089589063681,1674581645400788994', 'sessionInfoController', '会话表', 'userLastDomainSession', '/module/session/sessioninfo/userLastDomainSession', '/module/session/sessioninfo/userLastDomainSession', 'module:session:sessioninfo:userLastDomainSession', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922893, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582491907805185, '0,1674579384591241218,1674582491907805185', 'sysRoleController', '角色信息', 'baseEdit', '/module/system/sysrole/baseEdit', '/module/system/sysrole/baseEdit', 'module:system:sysrole:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922894, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674579182287376385, '0,1674578853248421890,1674579182287376385', 'sysUserOnlineController', '在线用户监控', 'baseQueryPageByParam', '/module/system/monitor/online/baseQueryPageByParam', '/module/system/monitor/online/baseQueryPageByParam', 'module:system:monitor:online:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922895, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674581814276050945, '0,1674579114499035137,1674581814276050945', 'sysJobLogController', '定时任务调度日志表', 'baseDeleteByIds', '/module/quartz/sysjoblog/baseDeleteByIds/{ids}', '/module/quartz/sysjoblog/baseDeleteByIds/*', 'module:quartz:sysjoblog:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922896, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictDataController', '字典数据', 'getListByDictType', '/module/system/basedictdata/getListByDictType/{dictType}', '/module/system/basedictdata/getListByDictType/*', 'module:system:basedictdata:getListByDictType:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922897, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582491907805185, '0,1674579384591241218,1674582491907805185', 'sysRoleController', '角色信息', 'cancelAuthUserAll', '/module/system/sysrole/authUser/cancelAll', '/module/system/sysrole/authUser/cancelAll', 'module:system:sysrole:authUser:cancelAll', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922900, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674579182287376385, '0,1674578853248421890,1674579182287376385', 'sysUserOnlineController', '在线用户监控', 'baseAdd', '/module/system/monitor/online/baseAdd', '/module/system/monitor/online/baseAdd', 'module:system:monitor:online:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922901, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582466536460289, '0,1674579384591241218,1674582466536460289', 'sysPermissionController', '权限管理', 'flushRouteCache', '/module/system/syspermission/flushRouteCache', '/module/system/syspermission/flushRouteCache', 'module:system:syspermission:flushRouteCache', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922902, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictTypeController', '字典类型', 'baseEdit', '/module/system/basedicttype/baseEdit', '/module/system/basedicttype/baseEdit', 'module:system:basedicttype:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922903, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674580927755374593, '0,1674578853248421890,1674580927755374593', 'sysUserController', '用户信息', 'baseQueryById', '/module/system/sysuser/baseQueryById/{id}', '/module/system/sysuser/baseQueryById/*', 'module:system:sysuser:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922904, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674581645400788994, '0,1674579089589063681,1674581645400788994', 'sessionInfoController', '会话表', 'addSession', '/module/session/sessioninfo/addSession/{sessionType}', '/module/session/sessioninfo/addSession/*', 'module:session:sessioninfo:addSession:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922905, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674581645400788994, '0,1674579089589063681,1674581645400788994', 'sessionInfoController', '会话表', 'addDomainSession', '/module/session/sessioninfo/addDomainSession', '/module/session/sessioninfo/addDomainSession', 'module:session:sessioninfo:addDomainSession', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922906, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582466536460289, '0,1674579384591241218,1674582466536460289', 'sysPermissionController', '权限管理', 'changeRouteVisitRule', '/module/system/syspermission/changeRouteVisitRule', '/module/system/syspermission/changeRouteVisitRule', 'module:system:syspermission:changeRouteVisitRule', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922907, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674581892462071809, '0,1674579114499035137,1674581892462071809', 'sysLogininforController', '系统访问记录- 控制器', 'baseEdit', '/module/system/syslogininfor/baseEdit', '/module/system/syslogininfor/baseEdit', 'module:system:syslogininfor:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922908, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674581892462071809, '0,1674579114499035137,1674581892462071809', 'sysLogininforController', '系统访问记录- 控制器', 'baseQueryById', '/module/system/syslogininfor/baseQueryById/{id}', '/module/system/syslogininfor/baseQueryById/*', 'module:system:syslogininfor:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922910, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674579841480970241, '0,1674578906511888386,1674579841480970241', 'sysJobController', '定时任务调度表', 'baseQueryByParam', '/module/quartz/sysjob/baseQueryByParam', '/module/quartz/sysjob/baseQueryByParam', 'module:quartz:sysjob:baseQueryByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922912, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674580927755374593, '0,1674578853248421890,1674580927755374593', 'sysProfileController', '个人业务', 'update', '/module/system/profile/update', '/module/system/profile/update', 'module:system:profile:update', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922913, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674583564886265858, '0,1674579089589063681,1674583564886265858', 'domainController', '领域会话', 'baseDeleteByIds', '/module/session/domain/baseDeleteByIds/{ids}', '/module/session/domain/baseDeleteByIds/*', 'module:session:domain:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922914, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674580956448608258, '0,1674578853248421890,1674580956448608258', 'userInfoController', '用户控制器', 'getInfo', '/module/user/userinfo/getInfo', '/module/user/userinfo/getInfo', 'module:user:userinfo:getInfo', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922915, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582466536460289, '0,1674579384591241218,1674582466536460289', 'sysPermissionController', '权限管理', 'rolePermissionByRoleId', '/module/system/syspermission/rolePermissionByRoleId/{roleId}', '/module/system/syspermission/rolePermissionByRoleId/*', 'module:system:syspermission:rolePermissionByRoleId:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922916, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582505417658369, '0,1674579384591241218,1674582505417658369', 'sysMenuController', '菜单权限', 'baseEdit', '/module/system/sysmenu/baseEdit', '/module/system/sysmenu/baseEdit', 'module:system:sysmenu:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729663254530, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582818715389954, '0,1674580535470510082,1674582818715389954', 'resourceConfigController', '系统参数配置', 'baseQueryPageByParam', '/module/system/baseresourceconfig/baseQueryPageByParam', '/module/system/baseresourceconfig/baseQueryPageByParam', 'module:system:baseresourceconfig:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729663254531, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674578906511888386, '0,1674578906511888386', 'basicErrorController', '', 'error', '/error', '/error', 'error', '', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1674578729663254532, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674578906511888386, '0,1674578906511888386', 'errorController', '', 'printError', '/error/print', '/error/print', 'error:print', '', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1674578729663254533, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582505417658369, '0,1674579384591241218,1674582505417658369', 'sysMenuController', '菜单权限', 'baseQueryPageByParam', '/module/system/sysmenu/baseQueryPageByParam', '/module/system/sysmenu/baseQueryPageByParam', 'module:system:sysmenu:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729663254534, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictTypeController', '字典类型', 'baseQueryByParam', '/module/system/basedicttype/baseQueryByParam', '/module/system/basedicttype/baseQueryByParam', 'module:system:basedicttype:baseQueryByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729663254535, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582491907805185, '0,1674579384591241218,1674582491907805185', 'sysRoleController', '角色信息', 'baseQueryByParam', '/module/system/sysrole/baseQueryByParam', '/module/system/sysrole/baseQueryByParam', 'module:system:sysrole:baseQueryByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729663254536, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictDataController', '字典数据', 'optionSelect', '/module/system/basedictdata/optionSelect/{dictType}', '/module/system/basedictdata/optionSelect/*', 'module:system:basedictdata:optionSelect:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729663254537, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582466536460289, '0,1674579384591241218,1674582466536460289', 'sysPermissionController', '权限管理', 'baseEdit', '/module/system/syspermission/baseEdit', '/module/system/syspermission/baseEdit', 'module:system:syspermission:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729663254538, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674579182287376385, '0,1674578853248421890,1674579182287376385', 'sysUserOnlineController', '在线用户监控', 'baseQueryByParam', '/module/system/monitor/online/baseQueryByParam', '/module/system/monitor/online/baseQueryByParam', 'module:system:monitor:online:baseQueryByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729663254539, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582818715389954, '0,1674580535470510082,1674582818715389954', 'resourceConfigController', '系统参数配置', 'baseDeleteByIds', '/module/system/baseresourceconfig/baseDeleteByIds/{ids}', '/module/system/baseresourceconfig/baseDeleteByIds/*', 'module:system:baseresourceconfig:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729663254541, '2023-06-30 08:40:55', NULL, '2023-06-30 08:40:55', '1000000000', 0, 0, 1674582505417658369, '0,1674579384591241218,1674582505417658369', 'sysMenuController', '菜单权限', 'baseQueryByParam', '/module/system/sysmenu/baseQueryByParam', '/module/system/sysmenu/baseQueryByParam', 'module:system:sysmenu:baseQueryByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578853248421890, '2023-06-30 08:41:25', '1000000000', '2023-06-30 08:41:25', '1000000000', 0, 0, 0, '0', '用户', '用户', '用户', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674578906511888386, '2023-06-30 08:41:38', '1000000000', '2023-06-30 08:41:38', '1000000000', 0, 0, 0, '0', '其他', '其他', '其他', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674579089589063681, '2023-06-30 08:42:21', '1000000000', '2023-06-30 08:42:21', '1000000000', 0, 0, 0, '0', '客户端会话', '客户端会话', '客户端会话', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674579114499035137, '2023-06-30 08:42:27', '1000000000', '2023-06-30 08:42:27', '1000000000', 0, 0, 0, '0', '日志', '日志', '日志', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674579182287376385, '2023-06-30 08:42:43', '1000000000', '2023-06-30 08:42:43', '1000000000', 0, 0, 1674578853248421890, '0,1674578853248421890', '在线用户监控', '在线用户监控', '在线用户监控', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674579384591241218, '2023-06-30 08:43:32', '1000000000', '2023-06-30 08:43:32', '1000000000', 0, 0, 0, '0', '权限', '权限', '权限', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674579708391510017, '2023-06-30 08:44:49', '1000000000', '2023-06-30 08:44:49', '1000000000', 0, 0, 1674578906511888386, '0,1674578906511888386', '通用', '通用', '通用', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674579841480970241, '2023-06-30 08:45:21', '1000000000', '2023-06-30 08:45:21', '1000000000', 0, 0, 1674578906511888386, '0,1674578906511888386', '定时任务', '定时任务', '定时任务', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674580535470510082, '2023-06-30 08:48:06', '1000000000', '2023-06-30 08:48:06', '1000000000', 0, 0, 0, '0', '系统', '系统', '系统', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674580927755374593, '2023-06-30 08:49:40', '1000000000', '2023-06-30 08:49:40', '1000000000', 0, 0, 1674578853248421890, '0,1674578853248421890', '系统用户', '系统用户', '系统用户', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674580956448608258, '2023-06-30 08:49:46', '1000000000', '2023-06-30 08:49:46', '1000000000', 0, 0, 1674578853248421890, '0,1674578853248421890', '客户端用户', '客户端用户', '客户端用户', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674581584893759489, '2023-06-30 08:52:16', '1000000000', '2023-06-30 08:52:16', '1000000000', 0, 0, 1674579089589063681, '0,1674579089589063681', '消息发送', '消息发送', '消息发送', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674581645400788994, '2023-06-30 08:52:31', '1000000000', '2023-06-30 08:52:31', '1000000000', 0, 0, 1674579089589063681, '0,1674579089589063681', '会话管理', '会话管理', '会话管理', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674581814276050945, '2023-06-30 08:53:11', '1000000000', '2023-06-30 08:53:11', '1000000000', 0, 0, 1674579114499035137, '0,1674579114499035137', '定时任务日志', '定时任务日志', '定时任务日志', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674581849239769090, '2023-06-30 08:53:19', '1000000000', '2023-06-30 08:53:19', '1000000000', 0, 0, 1674579114499035137, '0,1674579114499035137', '操作记录日志', '操作记录日志', '操作记录日志', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674581892462071809, '2023-06-30 08:53:30', '1000000000', '2023-06-30 08:53:30', '1000000000', 0, 0, 1674579114499035137, '0,1674579114499035137', '访问日志', '访问日志', '访问日志', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674582439277678594, '2023-06-30 08:55:40', '1000000000', '2023-06-30 08:55:40', '1000000000', 0, 0, 1674579384591241218, '0,1674579384591241218', '管理员登陆', '管理员登陆', '管理员登陆', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674582466536460289, '2023-06-30 08:55:46', '1000000000', '2023-06-30 08:55:46', '1000000000', 0, 0, 1674579384591241218, '0,1674579384591241218', '接口权限', '接口权限', '接口权限', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674582491907805185, '2023-06-30 08:55:52', '1000000000', '2023-06-30 08:55:52', '1000000000', 0, 0, 1674579384591241218, '0,1674579384591241218', '角色', '角色', '角色', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674582505417658369, '2023-06-30 08:55:56', '1000000000', '2023-06-30 08:55:56', '1000000000', 0, 0, 1674579384591241218, '0,1674579384591241218', '菜单', '菜单', '菜单', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674582790324146177, '2023-06-30 08:57:04', '1000000000', '2023-06-30 08:57:04', '1000000000', 0, 0, 1674580535470510082, '0,1674580535470510082', '字典', '字典', '字典', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674582818715389954, '2023-06-30 08:57:10', '1000000000', '2023-06-30 08:57:10', '1000000000', 0, 0, 1674580535470510082, '0,1674580535470510082', '系统参数', '系统参数', '系统参数', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674582856791281665, '2023-06-30 08:57:19', '1000000000', '2023-06-30 08:57:19', '1000000000', 0, 0, 1674580535470510082, '0,1674580535470510082', 'apikeys', 'apikeys', 'apikeys', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674583544443228161, '2023-06-30 09:00:03', '1000000000', '2023-06-30 09:00:03', '1000000000', 0, 0, 1674579089589063681, '0,1674579089589063681', '会话记录', '会话记录', '会话记录', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674583564886265858, '2023-06-30 09:00:08', '1000000000', '2023-06-30 09:00:08', '1000000000', 0, 0, 1674579089589063681, '0,1674579089589063681', '领域会话', '领域会话', '领域会话', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674622533179109377, '2023-06-30 11:34:59', NULL, '2023-06-30 11:34:59', '1000000000', 0, 0, 1674579708391510017, '0,1674578906511888386,1674579708391510017', 'commonController', '通用下载', 'download', '/common/download', '/common/download', 'common:download', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674622533200080898, '2023-06-30 11:34:59', NULL, '2023-06-30 11:34:59', '1000000000', 0, 0, 1674579708391510017, '0,1674578906511888386,1674579708391510017', 'commonController', '文件上传', 'upload', '/common/upload', '/common/upload', 'common:upload', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1675013065540587522, '2023-07-01 13:26:49', NULL, '2023-07-01 13:26:49', '1000000000', 0, 0, 1674579708391510017, '0,1674578906511888386,1674579708391510017', 'commonController', '通用控制器', 'uploadImage', '/common/uploadImage', '/common/uploadImage', 'common:uploadImage', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1675793439851036674, '2023-07-03 17:07:45', NULL, '2023-07-03 17:07:45', '1000000000', 0, 0, 1676790579284992001, '0,1674580535470510082,1676790579284992001', 'sysFileConfigController', '文件配置', 'baseQueryPageByParam', '/module/system/sysfileconfig/baseQueryPageByParam', '/module/system/sysfileconfig/baseQueryPageByParam', 'module:system:sysfileconfig:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1675793439851036675, '2023-07-03 17:07:45', NULL, '2023-07-03 17:07:45', '1000000000', 0, 0, 1676790579284992001, '0,1674580535470510082,1676790579284992001', 'sysFileConfigController', '文件配置', 'baseEdit', '/module/system/sysfileconfig/baseEdit', '/module/system/sysfileconfig/baseEdit', 'module:system:sysfileconfig:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1675793439851036676, '2023-07-03 17:07:45', NULL, '2023-07-03 17:07:45', '1000000000', 0, 0, 1674580956448608258, '0,1674578853248421890,1674580956448608258', 'userInfoController', '用户控制器', 'clientUpdateUser', '/module/user/userinfo/clientUpdateUser', '/module/user/userinfo/clientUpdateUser', 'module:user:userinfo:clientUpdateUser', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1675793439851036677, '2023-07-03 17:07:45', NULL, '2023-07-03 17:07:45', '1000000000', 0, 0, 1676790579284992001, '0,1674580535470510082,1676790579284992001', 'sysFileConfigController', '文件配置', 'baseQueryByParam', '/module/system/sysfileconfig/baseQueryByParam', '/module/system/sysfileconfig/baseQueryByParam', 'module:system:sysfileconfig:baseQueryByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1675793439851036678, '2023-07-03 17:07:45', NULL, '2023-07-03 17:07:45', '1000000000', 0, 0, 1676790579284992001, '0,1674580535470510082,1676790579284992001', 'sysFileConfigController', '文件配置', 'baseDeleteByIds', '/module/system/sysfileconfig/baseDeleteByIds/{ids}', '/module/system/sysfileconfig/baseDeleteByIds/*', 'module:system:sysfileconfig:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1675793439851036679, '2023-07-03 17:07:45', NULL, '2023-07-03 17:07:45', '1000000000', 0, 0, 1676790579284992001, '0,1674580535470510082,1676790579284992001', 'sysFileConfigController', '文件配置', 'baseAdd', '/module/system/sysfileconfig/baseAdd', '/module/system/sysfileconfig/baseAdd', 'module:system:sysfileconfig:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1675793439851036680, '2023-07-03 17:07:45', NULL, '2023-07-03 17:07:45', '1000000000', 0, 0, 1676790579284992001, '0,1674580535470510082,1676790579284992001', 'sysFileConfigController', '文件配置', 'baseQueryById', '/module/system/sysfileconfig/baseQueryById/{id}', '/module/system/sysfileconfig/baseQueryById/*', 'module:system:sysfileconfig:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1676790579284992001, '2023-07-06 11:10:02', '1000000000', '2023-07-06 11:10:02', '1000000000', 0, 0, 1674580535470510082, '0,1674580535470510082', '文件配置', '文件配置', '文件配置', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1676790618824695810, '2023-07-06 11:10:11', NULL, '2023-07-06 11:10:11', '1000000000', 0, 0, 1674580956448608258, '0,1674578853248421890,1674580956448608258', 'userInfoController', '用户控制器', 'getUserGrants', '/module/user/userinfo/getUserGrants', '/module/user/userinfo/getUserGrants', 'module:user:userinfo:getUserGrants', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1676790618824695811, '2023-07-06 11:10:11', NULL, '2023-07-06 11:10:11', '1000000000', 0, 0, 1674581584893759489, '0,1674579089589063681,1674581584893759489', 'sseController', 'sse通用请求控制器', 'connect', '/sse/connect', '/sse/connect', 'sse:connect', 'GET', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1677188224147001346, '2023-07-07 13:30:07', NULL, '2023-07-07 13:30:07', '1000000000', 0, 0, 1674580956448608258, '0,1674578853248421890,1674580956448608258', 'userInfoController', '用户修改桌面背景', 'editUserBackground', '/module/user/userinfo/editUserBackground', '/module/user/userinfo/editUserBackground', 'module:user:userinfo:editUserBackground', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1683755122745708546, '2023-07-25 16:24:38', NULL, '2023-07-25 16:24:38', '1000000000', 0, 0, 1674582856791281665, '0,1674580535470510082,1674582856791281665', 'openaiKeysController', 'apikeys', 'delUserApiKey', '/module/config/openaikeys/delUserApiKey/{id}', '/module/config/openaikeys/delUserApiKey/*', 'module:config:openaikeys:delUserApiKey:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1683755122808623105, '2023-07-25 16:24:38', NULL, '2023-07-25 16:24:38', '1000000000', 0, 0, 1674581645400788994, '0,1674579089589063681,1674581645400788994', 'sessionInfoController', '会话表', 'addDrawSession', '/module/session/sessioninfo/addDrawSession', '/module/session/sessioninfo/addDrawSession', 'module:session:sessioninfo:addDrawSession', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1683755122808623106, '2023-07-25 16:24:38', NULL, '2023-07-25 16:24:38', '1000000000', 0, 0, 1674582856791281665, '0,1674580535470510082,1674582856791281665', 'openaiKeysController', 'apikeys', 'addUserApiKey', '/module/config/openaikeys/addUserApiKey', '/module/config/openaikeys/addUserApiKey', 'module:config:openaikeys:addUserApiKey', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1683755122808623107, '2023-07-25 16:24:38', NULL, '2023-07-25 16:24:38', '1000000000', 0, 0, 1684019553161465857, '0,1674580535470510082,1684019553161465857', 'captchaController', '验证码校验', 'check', '/captcha/check', '/captcha/check', 'captcha:check', 'POST', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1683755122808623108, '2023-07-25 16:24:38', NULL, '2023-07-25 16:24:38', '1000000000', 0, 0, 1683755420184776705, '0,1674579089589063681,1683755420184776705', 'drawOpenaiController', '绘画控制器（openai）', 'sendAiDraw', '/module/draw/openai/sendAiDraw', '/module/draw/openai/sendAiDraw', 'module:draw:openai:sendAiDraw', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1683755122808623109, '2023-07-25 16:24:38', NULL, '2023-07-25 16:24:38', '1000000000', 0, 0, 1674583544443228161, '0,1674579089589063681,1674583544443228161', 'sessionRecordController', '会话详情获取单个会话信息', 'getLastOneRecordSession', '/module/session/sessionrecord/getLastOneRecordSession', '/module/session/sessionrecord/getLastOneRecordSession', 'module:session:sessionrecord:getLastOneRecordSession', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1683755122808623110, '2023-07-25 16:24:38', NULL, '2023-07-25 16:24:38', '1000000000', 0, 0, 1674580956448608258, '0,1674578853248421890,1674580956448608258', 'userInfoController', '用户控制器注册验证码发送', 'registerSendMail', '/module/user/userinfo/registerSendMail', '/module/user/userinfo/registerSendMail', 'module:user:userinfo:registerSendMail', 'GET', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1683755122808623112, '2023-07-25 16:24:38', NULL, '2023-07-25 16:24:38', '1000000000', 0, 0, 1674581584893759489, '0,1674579089589063681,1674581584893759489', 'chatController', '聊天会话控制器', 'send', '/module/chat/send', '/module/chat/send', 'module:chat:send', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1683755122808623113, '2023-07-25 16:24:38', NULL, '2023-07-25 16:24:38', '1000000000', 0, 0, 1674582856791281665, '0,1674580535470510082,1674582856791281665', 'openaiKeysController', 'apikeys', 'updateUserApiKey', '/module/config/openaikeys/updateUserApiKey', '/module/config/openaikeys/updateUserApiKey', 'module:config:openaikeys:updateUserApiKey', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1683755122808623114, '2023-07-25 16:24:38', NULL, '2023-07-25 16:24:38', '1000000000', 0, 0, 1674581584893759489, '0,1674579089589063681,1674581584893759489', 'chatController', '聊天会话控制器', 'sendDomain', '/module/chat/sendDomain', '/module/chat/sendDomain', 'module:chat:sendDomain', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1683755122808623115, '2023-07-25 16:24:38', NULL, '2023-07-25 16:24:38', '1000000000', 0, 0, 1674582856791281665, '0,1674580535470510082,1674582856791281665', 'openaiKeysController', 'apikeys', 'getUserApiKeys', '/module/config/openaikeys/getUserApiKeys', '/module/config/openaikeys/getUserApiKeys', 'module:config:openaikeys:getUserApiKeys', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1683755122808623116, '2023-07-25 16:24:38', NULL, '2023-07-25 16:24:38', '1000000000', 0, 0, 1683755420184776705, '0,1674579089589063681,1683755420184776705', 'drawOpenaiController', '绘画控制器（openai）', 'sendAiDrawEdit', '/module/draw/openai/sendAiDrawEdit', '/module/draw/openai/sendAiDrawEdit', 'module:draw:openai:sendAiDrawEdit', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1683755122808623117, '2023-07-25 16:24:38', NULL, '2023-07-25 16:24:38', '1000000000', 0, 0, 1674581645400788994, '0,1674579089589063681,1674581645400788994', 'sessionInfoController', '会话表', 'userLastDrawSession', '/module/session/sessioninfo/userLastDrawSession', '/module/session/sessioninfo/userLastDrawSession', 'module:session:sessioninfo:userLastDrawSession', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1683755420184776705, '2023-07-25 16:25:49', '1000000000', '2023-07-25 16:25:49', '1000000000', 0, 0, 1674579089589063681, '0,1674579089589063681', '绘图会话管理', '绘图会话管理', '绘图会话管理', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1684018183868010497, '2023-07-26 09:49:57', NULL, '2023-07-26 09:49:57', '1000000000', 0, 0, 1684019553161465857, '0,1674580535470510082,1684019553161465857', 'captchaController', '验证码获取', 'get', '/captcha/get', '/captcha/get', 'captcha:get', 'POST', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1684019553161465857, '2023-07-26 09:55:23', '1000000000', '2023-07-26 09:55:23', '1000000000', 0, 0, 1674580535470510082, '0,1674580535470510082', '验证码', '验证码', '验证码', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1688829555168522242, '2023-08-08 16:28:37', NULL, '2023-08-08 16:28:37', '1000000000', 0, 0, 1683755420184776705, '0,1674579089589063681,1683755420184776705', 'sessionInfoDrawController', '绘图会话', 'userLastDrawSession', '/module/session/sessioninfodraw/userLastDrawSession', '/module/session/sessioninfodraw/userLastDrawSession', 'module:session:sessioninfodraw:userLastDrawSession', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1688829555168522243, '2023-08-08 16:28:37', NULL, '2023-08-08 16:28:37', '1000000000', 0, 0, 1674580956448608258, '0,1674578853248421890,1674580956448608258', 'userInfoController', '用户控制器', 'updateUserInfo', '/module/user/userinfo/updateUserInfo', '/module/user/userinfo/updateUserInfo', 'module:user:userinfo:updateUserInfo', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1688829555168522244, '2023-08-08 16:28:37', NULL, '2023-08-08 16:28:37', '1000000000', 0, 0, 1674581645400788994, '0,1674579089589063681,1674581645400788994', 'sessionInfoController', '获取用户会话列表（分页）', 'getUserSessionList', '/module/session/sessioninfo/getUserSessionList', '/module/session/sessioninfo/getUserSessionList', 'module:session:sessioninfo:getUserSessionList', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1688829555168522245, '2023-08-08 16:28:37', NULL, '2023-08-08 16:28:37', '1000000000', 0, 0, 1674580956448608258, '0,1674578853248421890,1674580956448608258', 'userInfoController', '用户控制器', 'queryPageListByParam', '/module/user/userinfo/queryPageListByParam', '/module/user/userinfo/queryPageListByParam', 'module:user:userinfo:queryPageListByParam', 'POST', 1, 1, '1');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL COMMENT '主键',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `update_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标识 0 未删除 1 已删除',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int NOT NULL COMMENT '显示顺序',
  `data_scope` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'ALL' COMMENT '数据范围\r\n    ALL,        // 所有权限\r\n    CUSTOM,     // 自定义\r\n    DEPT_MY,    // 本部门\r\n    DEPT,       // 本部门以及以下\r\n    MY;         // 本人',
  `menu_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '部门树选择项是否关联显示',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `role_key`(`role_key` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '2022-10-05 00:49:02', 'admin', '2023-06-30 09:08:35', '1000000000', 0, 4, '系统人员', 'admin', 2, 'DEPT_MY', 1, 1, '0');
INSERT INTO `sys_role` VALUES (1000000000, '2022-05-29 19:58:03', NULL, '2022-08-15 18:29:19', 'admin', 0, 23, '系统管理员', 'system', 1, 'ALL', 1, 0, '0');
INSERT INTO `sys_role` VALUES (53814717356965893, '2022-05-29 20:00:28', NULL, '2023-07-07 10:26:15', '1000000000', 0, 43, '客户端用户', 'tourist', 3, 'CUSTOM', 1, 0, '0');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint NOT NULL COMMENT '主键',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `update_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标识 0 未删除 1 已删除',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` bigint NOT NULL COMMENT '主键',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `update_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标识 0 未删除 1 已删除',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  `permission_id` bigint NOT NULL COMMENT '权限ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和权限关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (1684021764503392258, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1674578853248421890, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392259, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1674580956448608258, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392260, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1674578906511888386, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392261, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1674579089589063681, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392262, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1674583564886265858, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392263, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1674580535470510082, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392264, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1674578728761479193, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392265, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1674578728761479197, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392266, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1674578729612922884, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392267, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1674578729612922914, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392268, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1675793439851036676, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392269, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1676790618824695810, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392270, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1677188224147001346, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392271, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1683755122808623110, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392272, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1674579708391510017, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392273, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1674578728828588058, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392274, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1674622533179109377, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392275, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1674622533200080898, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392276, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1675013065540587522, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392277, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1674581584893759489, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392278, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1676790618824695811, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392279, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1683755122808623112, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392280, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1683755122808623114, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392281, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1674581645400788994, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392282, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1674578728761479172, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392283, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1674578728761479209, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392284, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1674578728828588070, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392285, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1674578728828588082, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392286, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1674578729612922892, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392287, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1674578729612922904, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392288, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1674578729612922905, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392289, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1683755122808623105, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392290, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1683755122808623117, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392291, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1674583544443228161, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392292, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1674578728761479194, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392293, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1674578728828588074, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392294, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1683755122808623109, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392295, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1674578728761479208, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392296, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1674578728828588037, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392297, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1674578728828588046, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392298, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1683755420184776705, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392299, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1683755122808623108, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392300, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1683755122808623116, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392301, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1674582856791281665, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392302, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1683755122745708546, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392303, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1683755122808623106, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392304, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1683755122808623113, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392305, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1683755122808623115, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392306, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1684019553161465857, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392307, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1683755122808623107, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1684021764503392308, '2023-07-26 10:04:10', NULL, '2023-07-26 10:04:10', '1000000000', 0, 0, 1684018183868010497, 53814717356965893);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL COMMENT '主键',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `update_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标识 0 未删除 1 已删除',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1000000000, '2022-05-22 19:34:41', NULL, '2023-07-07 10:34:18', '1000000000', 0, 38, 1, 'admin', '管理员', '00', '156156432@qq.com', '15642346516', '0', '/image/2023/07/07/4438f77b89604f9d93e827d9babccfa7.jpg', '$2a$10$.eQAjqPOuo73CoG/4khMH.Re1m9N2GCokaTegDK8TFyzAhEOT6QMe', '0', '', NULL);
INSERT INTO `sys_user` VALUES (117398913374486533, '2022-10-17 16:21:53', NULL, '2023-03-29 15:52:08', '1000000000', 0, 7, 116107518461345797, 'test', 'test', '00', 'qwe21qwe@qq.com', NULL, '0', '', '$2a$10$gajKXwvSxZqAi5OSxElOzu1P9f.2muAI1lkZ7AeleRdX1HPGiHv8u', '0', '', NULL);
INSERT INTO `sys_user` VALUES (120386829357154309, '2023-02-26 14:28:28', '1000000000', '2023-03-29 15:42:09', '1000000000', 0, 5, 1, 'test2', 'test2', '00', 'test2@qq.com', '15621221225', '2', '', '$2a$10$bczAwMlxrFwaO3pptSWce.h6Rw8cD8o5s5dM9A6MiB0o1zovTtzI2', '0', '', NULL);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint NOT NULL COMMENT '主键',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `update_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标识 0 未删除 1 已删除',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, NULL, NULL, NULL, NULL, 0, 0, 1, 1);
INSERT INTO `sys_user_role` VALUES (2, NULL, NULL, NULL, NULL, 0, 0, 2, 1);
INSERT INTO `sys_user_role` VALUES (3, NULL, NULL, NULL, NULL, 0, 0, 3, 1);
INSERT INTO `sys_user_role` VALUES (5, NULL, NULL, NULL, NULL, 0, 0, 4, 1);
INSERT INTO `sys_user_role` VALUES (116448379170455557, '2022-09-05 17:08:33', NULL, '2022-09-05 17:08:33', 'admin', 0, 0, 1000000000, 53814717356965893);
INSERT INTO `sys_user_role` VALUES (121112175104753669, '2023-03-30 15:04:43', NULL, '2023-03-30 15:04:43', '1000000000', 0, 0, 120386829357154309, 53814717356965893);
INSERT INTO `sys_user_role` VALUES (121112175104753670, '2023-03-30 15:04:43', NULL, '2023-03-30 15:04:43', '1000000000', 0, 0, 120386829357154309, 1000000000);
INSERT INTO `sys_user_role` VALUES (121112175104753671, '2023-03-30 15:04:43', NULL, '2023-03-30 15:04:43', '1000000000', 0, 0, 120386829357154309, 1);
INSERT INTO `sys_user_role` VALUES (121155883176558597, '2023-04-01 13:23:36', NULL, '2023-04-01 13:23:36', '1000000000', 0, 0, 117398913374486533, 53814717356965893);
INSERT INTO `sys_user_role` VALUES (121155883176558598, '2023-04-01 13:23:36', NULL, '2023-04-01 13:23:36', '1000000000', 0, 0, 117398913374486533, 1);

-- ----------------------------
-- Table structure for sys_user_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token`  (
  `id` bigint NOT NULL COMMENT '主键',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_oper` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `update_oper` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标识 0 未删除 1 已删除',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  `user_id` bigint NOT NULL COMMENT '登录人主键',
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'token key',
  `last_time` timestamp NULL DEFAULT NULL COMMENT '最新更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '登录人token信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_token
-- ----------------------------
INSERT INTO `sys_user_token` VALUES (1689096540762718209, '2023-08-09 10:09:31', NULL, '2023-08-09 10:09:31', NULL, 0, 0, 1000000000, 'cd318b69-af50-481d-9f22-a445f9139c26', '2023-08-09 10:09:31');
INSERT INTO `sys_user_token` VALUES (1689105972603203585, '2023-08-09 10:47:00', NULL, '2023-08-09 10:47:00', NULL, 0, 0, 1665958959871291394, '877d4c6e-d3b8-4c83-8fa8-26e6ae42390b', '2023-08-09 10:47:00');
INSERT INTO `sys_user_token` VALUES (1694145912932175875, '2023-08-23 08:33:56', NULL, '2023-08-23 08:33:56', NULL, 0, 0, 1000000000, '806f3704-78c5-435c-a51c-4a8f5fe022d2', '2023-08-23 08:33:55');

-- ----------------------------
-- Table structure for tb_domain
-- ----------------------------
DROP TABLE IF EXISTS `tb_domain`;
CREATE TABLE `tb_domain`  (
  `id` bigint NOT NULL COMMENT '主键',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `update_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标识 0 未删除 1 已删除',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  `unique_key` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '唯一标识',
  `above_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '上文内容',
  `domain_group` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '领域组（DomainGroup）',
  `sort` int NULL DEFAULT NULL COMMENT '排序号',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `route_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '跳转路由（前端跳转）',
  `icon_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '显示名称',
  `icon_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片路径（本地）',
  `window_data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '窗口会话数据json',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '领域类型（domain_type）',
  `if_show` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否显示',
  `first_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '首条语句内容',
  `use_model` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '使用模型 可为空',
  `if_desk_show` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否桌面显示',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_key`(`unique_key` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '领域会话' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_domain
-- ----------------------------
INSERT INTO `tb_domain` VALUES (1, NULL, NULL, '2023-06-29 13:24:09', '1000000000', 0, 0, 'ad', 'I want you to advertise. You will create a campaign to promote the product or service of your choice. You will select a target audience, develop key messages and slogans, choose media channels to promote, and decide on any other activities needed to achieve your goals. Please reply to the following template with no other content,Using markdown output,Answer in Chinese:[\n\n我将为你的(广告标题)创建一个促销活动！\n目标受众:\n- xxxxx\n关键信息:\n1. xxxxx\n口号:\n- xxxx\n媒体渠道:\n1. xxxxx\n额外的活动:\n1. xxxxx\n\n宣传结语：xxx\n]  \nThe first AD I want you to make is:', 'COMMON', 1, NULL, '/sessionDomain/ad', '广告创意', 'domainAd.png', '{\"title\":\"广告创意助手\",\"sessionType\":\"DOMAIN\",\"description\":\"它可以编写各种类型的广告文案，包括产品广告、服务广告、品牌广告、活动宣传等。\",\"contentShowType\":\"Markdown\",\"inputType\":\"Input\"}', '0', '1', '我是广告创意文案助手，可以编写各种类型的广告文案，包括产品广告、服务广告、品牌广告、活动宣传等。它可以为你编写具有吸引力、清晰明了的广告文案，让你的目标受众更容易接受你的产品或服务嗷~', NULL, '1');
INSERT INTO `tb_domain` VALUES (1673942163144056834, '2023-06-28 14:31:26', '1000000000', '2023-06-29 14:41:19', '1000000000', 0, 0, 'etranslateChinese', 'Below, I will ask you to act as a translator. Your goal is to translate any language into Chinese,Do not output pronunciation letters, only output Chinese. Please do not use a translation accent when translating, but rather translate naturally, fluently, and authentically, using beautiful and elegant expressions. The first sentence I need to translate: ', 'COMMON', 2, NULL, '/sessionDomain/etranslateChinese', '英文翻译官', 'icon-translate.png', '{\"title\":\"英文翻译官\",\"sessionType\":\"DOMAIN\",\"description\":\"英文翻译成中文\",\"contentShowType\":\"Markdown\",\"inputType\":\"Input\"}', '0', '1', '我是一个英文翻译官，可以进行英语和中文之间的翻译工作，包括但不限于学术文献、商业文档、网站内容、软件界面等。可以保证翻译的准确性和专业性。', NULL, '1');
INSERT INTO `tb_domain` VALUES (1673993963171753986, '2023-06-28 17:57:16', '1000000000', '2023-07-10 13:35:57', '1000000000', 0, 0, 'svgdesign', 'I hope you become an SVG designer. I will ask you to create an image and provide it with SVG code. Place the result marker in the code block. Only output SVG tag code, do not output other text descriptions or descriptions, nor output base64 data Url. My first requirement is:', 'COMMON', 3, NULL, '/sessionDomain/svgdesign', 'svg设计师', 'icon-svgdesign.png', '{\"title\":\"SVG设计师\",\"sessionType\":\"DOMAIN\",\"description\":\"svg设计师\",\"contentShowType\":\"Html\",\"inputType\":\"Input\"}', '0', '1', '我是一个 SVG 设计师，可以对我说设计需求，我会根据你的需求来设计svg~💪，例如：画一个红色边框空心五角星', NULL, '1');
INSERT INTO `tb_domain` VALUES (1682289504188231682, '2023-07-21 15:20:47', '1000000000', '2023-07-21 17:25:16', '1000000000', 0, 0, 'minRedCopyWritting', 'You are a copywriting expert in Xiaohongshu. Based on the characteristics of Xiaohongshu\'s copywriting style, please help me generate a copy based on the template I provided.\n\nTemplate:\n[Title]\n[Main text]\n\nRequirements:\n1. There should be 2 emoji emojis before and after the title content. Based on my input, I can infer the type of copy, such as (\"xxx Scenic Spots\"), and the title content is: Xiaohongshu Scenic Spots Recommendation\n2. The Writing style of the text is peaceful. Please use a cute title at the beginning of the text. Judge the main audience according to the content, such as \"dear fairies\", \"dear little brothers\", etc. Do not repeat.\n3. Please introduce the main content of the text based on the characteristics of Xiaohongshu\'s writing. If it is possible to describe the content one by one, please output emoji emoji with a serial number before each description, otherwise there is no need to output emoji emoji emoji\n4. After each paragraph of the main text, please output 2 emoji emojis that match the content and wrap them\n5. The output language is based on the language of the input content, with default Chinese output\n6. emoji, please do not be too repetitive\n\nPlease strictly follow the template and content above for output. The first copy I need to generate is:', 'COMMON', 4, NULL, '/sessionDomain/minRedCopyWritting', '小红书文案', 'domainRedBookCopyWriting.png', '{\"title\":\"小红书文案创作\",\"sessionType\":\"DOMAIN\",\"inputType\":\"Input\",\"contentShowType\":\"Markdown\",\"description\":\"小红书文案创作助手，带你玩转小红书社区~\"}', '0', '1', '我是小红书文案创作助手，您可以对说，例如： 口红色号安利、智能家居推荐等。', NULL, '1');
INSERT INTO `tb_domain` VALUES (1692066780366766082, '2023-08-17 14:52:12', '1000000000', '2023-08-17 15:10:11', '1000000000', 0, 0, 'travelHelper', 'I want you to make a travel guide. I will write down my location for you, and you will recommend a place close to my location. In some cases, I will also tell you the type of place I will visit. You will also recommend a similar type of place close to my first location. Please output according to my language. My first request for advice is：', 'COMMON', 5, NULL, '/sessionDomain/travelHelper', '旅游指南助手', 'travelIcon.png', '{\"title\":\"旅游指南\",\"sessionType\":\"DOMAIN\",\"inputType\":\"Input\",\"contentShowType\":\"Markdown\",\"description\":\"我是旅游指南助手，我会根据你的诉求来为你提供合适的旅游路线。\"}', '0', '1', '我是旅游指南助手，我会根据你的诉求来为你提供合适的旅游路线，例如你可以问我：我在上海，我只想参观博物馆', 'gpt-3.5-turbo', '0');
INSERT INTO `tb_domain` VALUES (1692069585513086977, '2023-08-17 15:03:20', '1000000000', '2023-08-17 15:28:14', '1000000000', 0, 0, 'xiaoshuojia', 'I want you to play a novelist. You will come up with creative and engaging stories that can attract readers in the long run. You can choose any type, such as fantasy, romance, historical novels, etc. But your goal is to write works with excellent plot, captivating characters, and unexpected climaxes.Please output according to my language.  My first requirement is:', 'COMMON', 6, NULL, '/sessionDomain/xiaoshuojia', '小说家', 'storyIcon.png', '{\"title\":\"小说家\",\"sessionType\":\"DOMAIN\",\"inputType\":\"Input\",\"contentShowType\":\"Markdown\",\"description\":\"我是一个小说家，能够创造出富有创意、引人入胜的故事\"}', '0', '1', '我是一个小说家，可以根据你的要求创造出富有创意、引人入胜的故事。例如，你可以对我说：我要写一部以未来为背景的科幻小说', 'gpt-3.5-turbo', '1');
INSERT INTO `tb_domain` VALUES (1692075020353798145, '2023-08-17 15:24:56', '1000000000', '2023-08-17 15:28:28', '1000000000', 0, 0, 'QGDS', 'I want you to serve as a relationship coach. I will provide some details about the two individuals involved in the conflict, and your job is to provide suggestions on how they can solve the problem that caused their separation. This may include suggestions on communication skills or different strategies to improve their understanding of each other\'s perspectives.Please output according to my language.  My first request is:', 'COMMON', 7, NULL, '/sessionDomain/QGDS', '情感大师', 'qgdsIcon.png', '{\"title\":\"情感大师\",\"sessionType\":\"DOMAIN\",\"contentShowType\":\"Markdown\",\"inputType\":\"Input\",\"description\":\"我是一个专门处理人际关系的专家，提供有关冲突的信息与细节，我会给出沟通技巧与建议\"}', '0', '1', '我是一个专门处理人际关系的专家，提供有关冲突的信息与细节，我会给出沟通技巧与建议。例如，你可以对我说：我需要帮助解决我和配偶之间的冲突', 'gpt-3.5-turbo', '1');

-- ----------------------------
-- Table structure for tb_notice_client
-- ----------------------------
DROP TABLE IF EXISTS `tb_notice_client`;
CREATE TABLE `tb_notice_client`  (
  `id` bigint NOT NULL COMMENT '主键',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_oper` varchar(26) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `update_oper` varchar(26) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标识 0 未删除 1 已删除',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '通知标题',
  `notice_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '通知类型',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '内容',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '通告信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_notice_client
-- ----------------------------
INSERT INTO `tb_notice_client` VALUES (1692361147728060418, '2023-08-18 10:21:54', '1000000000', '2023-08-18 11:10:19', '1000000000', 0, 0, '通知', NULL, '通知', 1);

-- ----------------------------
-- Table structure for tb_openai_keys
-- ----------------------------
DROP TABLE IF EXISTS `tb_openai_keys`;
CREATE TABLE `tb_openai_keys`  (
  `id` bigint NOT NULL COMMENT '主键',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `update_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标识 0 未删除 1 已删除',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  `api_key` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'openai key',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'key名称',
  `total_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '总额度',
  `total_usage` decimal(10, 2) NULL DEFAULT NULL COMMENT '使用额度',
  `expired_time` datetime NULL DEFAULT NULL COMMENT '过期时间',
  `if_common` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否通用key',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `enable_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '可用状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'apikeys' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_openai_keys
-- ----------------------------
INSERT INTO `tb_openai_keys` VALUES (1, NULL, NULL, NULL, NULL, 0, 0, 'sk-xxxxxxx', 'test', NULL, NULL, NULL, '1', NULL, '0');

-- ----------------------------
-- Table structure for tb_session_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_session_info`;
CREATE TABLE `tb_session_info`  (
  `id` bigint NOT NULL COMMENT '主键',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `update_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标识 0 未删除 1 已删除',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  `session_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会话名称',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会话状态（session_status）',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型',
  `domain_unique_key` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '领域会话类型唯一标识',
  `draw_unique_key` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '绘画接口类型唯一表示（DrawType）',
  `all_consumer_token` int NULL DEFAULT NULL COMMENT '总token消耗',
  `session_num` int NULL DEFAULT NULL COMMENT '用户会话号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会话表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_session_info
-- ----------------------------

-- ----------------------------
-- Table structure for tb_session_info_draw
-- ----------------------------
DROP TABLE IF EXISTS `tb_session_info_draw`;
CREATE TABLE `tb_session_info_draw`  (
  `id` bigint NOT NULL COMMENT '主键',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_oper` varchar(26) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `update_oper` varchar(26) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标识 0 未删除 1 已删除',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  `prompt` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'prompt',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `draw_unique_key` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '绘画接口类型唯一表示（DrawType）',
  `session_num` int NULL DEFAULT NULL COMMENT '用户会话号',
  `show_img` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '展示图',
  `sd_response_info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'sd info响应参数json',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '绘图会话' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_session_info_draw
-- ----------------------------

-- ----------------------------
-- Table structure for tb_session_record
-- ----------------------------
DROP TABLE IF EXISTS `tb_session_record`;
CREATE TABLE `tb_session_record`  (
  `id` bigint NOT NULL COMMENT '主键',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `update_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标识 0 未删除 1 已删除',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  `session_id` bigint NOT NULL COMMENT '会话id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `domain_unique_key` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '领域类型唯一标识',
  `draw_unique_key` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '绘图接口类型唯一标识（DrawType）',
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `draw_base64_img` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '图片base64内容',
  `if_show` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否显示',
  `if_context` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否可统计为上下文 0 否 1 是',
  `if_domain_top` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否为领域会话的最上文',
  `consumer_token` int NULL DEFAULT NULL COMMENT '消耗token数',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `session_id`(`session_id` ASC) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  INDEX `tb_session_record_domain_unique_key_IDX`(`domain_unique_key` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会话详情' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_session_record
-- ----------------------------

-- ----------------------------
-- Table structure for tb_session_record_draw
-- ----------------------------
DROP TABLE IF EXISTS `tb_session_record_draw`;
CREATE TABLE `tb_session_record_draw`  (
  `id` bigint NOT NULL COMMENT '主键',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_oper` varchar(26) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `update_oper` varchar(26) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标识 0 未删除 1 已删除',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  `session_info_draw_id` bigint NULL DEFAULT NULL COMMENT '绘图会话id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `draw_unique_key` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '绘图接口类型唯一标识（DrawType）',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `draw_base64_img` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '图片base64内容',
  `draw_img_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片路径',
  `prompt` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '输入prompt',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '图像会话详情' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_session_record_draw
-- ----------------------------

-- ----------------------------
-- Table structure for tb_task_draw
-- ----------------------------
DROP TABLE IF EXISTS `tb_task_draw`;
CREATE TABLE `tb_task_draw`  (
  `id` bigint NOT NULL COMMENT '主键',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_oper` varchar(26) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `update_oper` varchar(26) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标识 0 未删除 1 已删除',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  `draw_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '绘图任务类型',
  `draw_api_key` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '绘图接口标识',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `task_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '任务状态',
  `session_info_draw_id` bigint NULL DEFAULT NULL COMMENT '绘图会话id',
  `task_end_time` datetime NULL DEFAULT NULL COMMENT '任务结束时间',
  `request_param` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '任务请求参数',
  `show_img` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '展示图',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '绘图任务列表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_task_draw
-- ----------------------------

-- ----------------------------
-- Table structure for tb_user_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_info`;
CREATE TABLE `tb_user_info`  (
  `id` bigint NOT NULL COMMENT '主键',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `update_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标识 0 未删除 1 已删除',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  `user_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录名',
  `password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `sex` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
  `img_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `if_tourist` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否为游客',
  `ipaddress` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `qq_number` int NULL DEFAULT NULL COMMENT 'qq号',
  `desk_img_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '桌面背景',
  `ip_location` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip归属地',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_user_info
-- ----------------------------
INSERT INTO `tb_user_info` VALUES (1665958959871291394, '2023-06-06 13:49:02', NULL, '2023-07-26 10:22:32', '1665958959871291394', 0, 0, 'test', '$2a$10$f3z0krpEzkHbO6AUAADCnuMC9GwO3JbotZ7SrFzOAnW2BX1dJ2Pmq', 'test', '0', '/image/2023/07/26/2147633666b34baeaf9c23ba3e6463b4.jpeg?e=1690374149&token=y47kvl-NkTt0IHtqKZ2fNJilbsyhW9L16WKdp5JC:0npIiCSxElYDgnC3BU_zx-zGKqc=', '0', '0', NULL, NULL, NULL, NULL, '', NULL);
INSERT INTO `tb_user_info` VALUES (1689104410241765377, '2023-08-09 10:40:48', NULL, '2023-08-09 10:40:48', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '0', '1', '127.0.0.1', NULL, NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
