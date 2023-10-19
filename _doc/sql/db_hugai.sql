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

 Date: 19/10/2023 10:36:19
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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

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
INSERT INTO `base_dict_data` VALUES (1696322229467471873, '2023-08-29 08:41:50', '1000000000', '2023-08-29 08:41:50', '1000000000', 0, 0, 2, '创作', '2', 'domain_type', NULL, NULL, 'N', '0');
INSERT INTO `base_dict_data` VALUES (1696322296886714369, '2023-08-29 08:42:06', '1000000000', '2023-08-29 08:42:06', '1000000000', 0, 0, 3, '文案', '3', 'domain_type', NULL, NULL, 'N', '0');

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统参数配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of base_resource_config
-- ----------------------------
INSERT INTO `base_resource_config` VALUES (102061615, NULL, NULL, '2023-09-28 09:34:43', '1000000000', 0, 12, 'main', '{\"maxUserLogin\":5,\"staticWebsite\":\"http://static.xxx.com\",\"website\":\"http://localhost:9000\",\"fileSavePathWin\":\"D:\\\\\\\\resourceServer\\\\\\\\\",\"fileSavePathLinux\":\"/projectResource/temp/file/\",\"registerOpen\":true,\"authCodeOpen\":false,\"fileSaveStrategy\":\"local\",\"proxyHost\":\"127.0.0.1\",\"proxyPort\":\"7890\",\"ableSystemApiKey\":true,\"webIssueTime\":\"2023-08-01\",\"websocketUrl\":\"ws://localhost:7100\",\"streamResponseType\":\"SSE\",\"projectVersion\":\"v1.0\"}');
INSERT INTO `base_resource_config` VALUES (112061615, NULL, NULL, '2023-09-26 17:12:14', '1000000000', 0, 0, 'openai', '{\"proxyHost\":\"127.0.0.1\",\"proxyPort\":\"7890\",\"chatModel\":\"gpt-3.5-turbo\",\"textModel\":\"text-davinci-003\",\"openDraw\":false}');
INSERT INTO `base_resource_config` VALUES (112241615, NULL, NULL, '2023-10-09 09:01:11', '1000000000', 0, 0, 'draw', '{\"sdHostUrl\":\"http://xxxx.com\",\"openDrawOpenai\":false,\"defaultNegativePrompt\":\"(worst quality:2), (low quality:2), (normal quality:2), lowres, normal quality,(monochrome)), ((grayscale)), skin spots, acnes, skin blemishes, age spot, (ugly:1.331),duplicate:1.331), (morbid:1.21), (mutilated:1.21), (tranny:l.331), mutated hands, (poorly drawnands:1.5), blurry, (bad anatomy:1.21), (bad proportions:1.331), extra limbs, (disfigured:1.331),missing arms:1.331), (extra legs:1.331), (fused fingers:1.61051), (too many fingers:1.61051),unclear eyes:1.331), lowers, bad hands, missing fingers, extra digit,bad hands, missing fingers.((extra arms and legs))),lowres, bad anatomy, bad hands, text, error, missing fingers, extra digit, fewer digits, cropped, worst quality, low quality, normal quality, jpeg artifacts, signature, watermark, username, blurry, bad-hands-5\",\"openSensitiveLimit\":true,\"sensitiveContent\":\"NSFW\",\"openBeforePromptContent\":false,\"beforePromptContent\":\"NSFW\",\"defaultRequestBean\":\"{\\n    \\\"steps\\\": 25,\\n    \\\"sampler_name\\\": \\\"DPM++ 2M Karras\\\",\\n    \\\"restore_faces\\\": true,\\n    \\\"seed\\\": -1,\\n    \\\"denoisingStrength\\\": 0.7,\\n    \\\"hrScale\\\": 2.0\\n}\",\"openBeforeNegativePromptContent\":true,\"beforeNegativePromptContent\":\"nfsw, (nude:1.5) \"}');

-- ----------------------------
-- Table structure for sys_attachment
-- ----------------------------
DROP TABLE IF EXISTS `sys_attachment`;
CREATE TABLE `sys_attachment`  (
  `id` bigint NOT NULL,
  `create_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` int NULL DEFAULT 0,
  `business_id` bigint NULL DEFAULT NULL COMMENT '业务主键id',
  `original_file_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '原始文件名',
  `file_size` int NULL DEFAULT NULL COMMENT '文件大小',
  `file_name_md5` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件唯一名称',
  `file_absolute_path` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件绝对路径',
  `file_suffix` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件后缀',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '附件管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_attachment
-- ----------------------------

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件上传配置' ROW_FORMAT = Dynamic;

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
  `if_save_log` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否持久化',
  PRIMARY KEY (`id`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务调度表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_job
-- ----------------------------
INSERT INTO `sys_job` VALUES (1, '系统默认（无参）', 'DEFAULT', 'baseDictTypeServiceImpl.test()', '0/10 * * * * ?', '3', '1', '1', '', '2022-10-13 10:36:16', NULL, '2023-06-03 16:27:29', '1000000000', 0, 28, NULL);
INSERT INTO `sys_job` VALUES (2, '系统默认（有参）', 'DEFAULT', 'ryTask.ryParams(\'ry\')', '0/15 * * * * ?', '3', '1', '1', '', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `sys_job` VALUES (3, '系统默认（多参）', 'DEFAULT', 'ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)', '0/20 * * * * ?', '3', '1', '1', '', NULL, NULL, NULL, NULL, 0, 0, NULL);

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务调度日志表' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统访问记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_logininfor
-- ----------------------------
INSERT INTO `sys_logininfor` VALUES (1711213462388011009, '2023-10-09 10:54:16', NULL, '2023-10-09 10:54:16', NULL, 0, 0, 'admin', '127.0.0.1', '内网IP', 'CHROME11', 'Windows 10', 0, '登录成功', '2023-10-09 10:54:17', '1');

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

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
INSERT INTO `sys_menu` VALUES (500, '2022-06-06 11:17:50', 'admin', '2023-09-19 08:58:02', '1000000000', 0, 0, '操作日志', 108, 1, 'operlog', 'monitor/operlog/index', '', 0, 0, 'C', '0', '0', 'form', 0, NULL);
INSERT INTO `sys_menu` VALUES (501, '2022-06-06 11:17:50', 'admin', '2022-06-06 11:17:50', 'admin', 0, 0, '登录日志', 108, 2, 'logininfor', 'monitor/logininfor/index', '', 0, 0, 'C', '0', '0', 'logininfor', 0, NULL);
INSERT INTO `sys_menu` VALUES (116497084306948101, '2022-09-07 20:45:08', 'admin', '2023-09-19 08:58:36', '1000000000', 0, 3, '系统工具', 0, 0, '/tool', NULL, NULL, 0, 0, 'M', '0', '0', 'Inbox', 1004, NULL);
INSERT INTO `sys_menu` VALUES (116896341546500101, '2022-09-25 11:49:14', 'admin', '2023-06-27 16:51:07', '1000000000', 0, 2, '权限管理', 0, 0, '/auth', NULL, NULL, 0, 0, 'M', '0', '0', 'Share', 1001, NULL);
INSERT INTO `sys_menu` VALUES (116901224459272197, '2022-09-25 16:59:41', 'admin', '2023-06-27 16:58:07', '1000000000', 0, 1, '接口管理', 116896341546500101, 0, 'permission', 'system/permission/index', NULL, 0, 0, 'C', '0', '0', 'Diary', 3, NULL);
INSERT INTO `sys_menu` VALUES (117242727687258117, '2022-10-10 18:51:52', 'admin', '2023-06-27 16:57:00', '1000000000', 0, 14, '网站配置', 117256171332567045, 0, 'webset', 'system/webset/index', NULL, 0, 0, 'C', '0', '0', 'Toggle', 1, NULL);
INSERT INTO `sys_menu` VALUES (117256171332567045, '2022-10-11 09:06:36', 'admin', '2023-06-27 16:45:04', '1000000000', 0, 2, '网站设置', 0, 0, '/webset', NULL, NULL, 0, 0, 'M', '0', '0', 'Toggle', 99, NULL);
INSERT INTO `sys_menu` VALUES (1673254130736492546, '2023-06-26 16:57:26', '1000000000', '2023-08-03 17:41:44', '1000000000', 0, 0, '业务配置', 0, 0, '/businessConfig', NULL, NULL, 0, 0, 'M', '0', '0', 'Briefcase', 2, NULL);
INSERT INTO `sys_menu` VALUES (1673256869570867202, '2023-06-26 17:08:19', '1000000000', '2023-06-27 16:54:52', '1000000000', 0, 0, '领域会话配置', 1673254130736492546, 0, 'domainconfig', 'business/domain/index', NULL, 0, 0, 'C', '0', '0', 'Attachment', 1, NULL);
INSERT INTO `sys_menu` VALUES (1687035996077555714, '2023-08-03 17:41:39', '1000000000', '2023-08-03 17:41:53', '1000000000', 0, 0, '用户中心', 0, 0, '/userBusiness', NULL, NULL, 0, 0, 'M', '0', '0', 'Users', 1, NULL);
INSERT INTO `sys_menu` VALUES (1687036271567831042, '2023-08-03 17:42:45', '1000000000', '2023-08-03 17:43:17', '1000000000', 0, 0, '用户信息维护', 1687035996077555714, 0, 'clientUser', 'business/user/index', NULL, 0, 0, 'C', '0', '0', 'User', 1, NULL);
INSERT INTO `sys_menu` VALUES (1691709718004781057, '2023-08-16 15:13:21', '1000000000', '2023-08-16 15:35:22', '1000000000', 0, 0, '系统秘钥管理', 1673254130736492546, 0, 'apiKey', 'business/apikeys/index', NULL, 0, 0, 'C', '0', '0', 'Book', 2, NULL);
INSERT INTO `sys_menu` VALUES (1692358229926928385, '2023-08-18 10:10:19', '1000000000', '2023-08-18 10:10:19', '1000000000', 0, 0, '通知公告管理', 1673254130736492546, 0, 'noticeClient', 'business/noticeclient/index', NULL, 0, 0, 'C', '0', '0', 'Notification', 3, NULL);
INSERT INTO `sys_menu` VALUES (1701852775748579330, '2023-09-13 14:58:15', '1000000000', '2023-09-15 17:10:44', '1000000000', 0, 0, '文本会话记录', 1687035996077555714, 0, 'txtSession', 'business/session/sessionRecord/index', NULL, 0, 0, 'C', '0', '0', 'Comment Lines', 2, NULL);
INSERT INTO `sys_menu` VALUES (1703583800203718657, '2023-09-18 09:36:43', '1000000000', '2023-09-18 09:37:26', '1000000000', 0, 0, '绘图记录', 1687035996077555714, 0, 'drawRecord', 'business/session/drawRecord/index', NULL, 0, 0, 'C', '0', '0', 'Pie Chart', 3, NULL);
INSERT INTO `sys_menu` VALUES (1703942017458929666, '2023-09-19 09:20:09', '1000000000', '2023-09-19 09:20:09', '1000000000', 0, 0, '绘图任务', 1687035996077555714, 0, 'taskDraw', 'business/session/taskDraw/index', NULL, 0, 0, 'C', '0', '0', 'Computer', 4, NULL);
INSERT INTO `sys_menu` VALUES (1709470336816365570, '2023-10-04 15:27:43', '1000000000', '2023-10-04 15:37:28', '1000000000', 0, 0, 'minio配置', 117256171332567045, 0, 'minioConfig', 'system/webset/minio/minioConfig', NULL, 0, 0, 'C', '0', '0', 'Settings', 2, NULL);
INSERT INTO `sys_menu` VALUES (1709493291432669186, '2023-10-04 16:58:56', '1000000000', '2023-10-04 16:58:56', '1000000000', 0, 0, 'mj账户配置', 117256171332567045, 0, 'mjconfig', 'system/webset/midjourney/index', NULL, 0, 0, 'C', '0', '0', 'Settings', 3, NULL);

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件上传配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_minio_secret
-- ----------------------------
INSERT INTO `sys_minio_secret` VALUES (1, NULL, NULL, '1000000000', '2023-10-04 15:41:02', 0, 'qiniu', 'xxxxxx', 'xxxxxx', 'hugai', 'http://xxxx.com');

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '操作日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_oper_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` bigint NOT NULL COMMENT '主键',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `create_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1674578728694370305, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582491907805185, '0,1674579384591241218,1674582491907805185', 'sysRoleController', '角色信息', 'selectAuthUserAll', '/module/system/sysrole/authUser/selectAll', '/module/system/sysrole/authUser/selectAll', 'module:system:sysrole:authUser:selectAll', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728694370306, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582439277678594, '0,1674579384591241218,1674582439277678594', 'loginPcController', '登陆控制器', 'register', '/auth/system/register', '/auth/system/register', 'auth:system:register', 'POST', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1674578728694370307, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674580927755374593, '0,1674578853248421890,1674580927755374593', 'sysUserController', '用户信息', 'baseQueryByParam', '/module/system/sysuser/baseQueryByParam', '/module/system/sysuser/baseQueryByParam', 'module:system:sysuser:baseQueryByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728694370309, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582491907805185, '0,1674579384591241218,1674582491907805185', 'sysRoleController', '角色信息', 'baseAdd', '/module/system/sysrole/baseAdd', '/module/system/sysrole/baseAdd', 'module:system:sysrole:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728694370310, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674581849239769090, '0,1674579114499035137,1674581849239769090', 'sysOperLogController', '操作日志记录- 控制器', 'baseQueryPageByParam', '/module/system/sysoperlog/baseQueryPageByParam', '/module/system/sysoperlog/baseQueryPageByParam', 'module:system:sysoperlog:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728694370311, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674581814276050945, '0,1674579114499035137,1674581814276050945', 'sysJobLogController', '定时任务调度日志表', 'baseQueryByParam', '/module/quartz/sysjoblog/baseQueryByParam', '/module/quartz/sysjoblog/baseQueryByParam', 'module:quartz:sysjoblog:baseQueryByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479171, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674581814276050945, '0,1674579114499035137,1674581814276050945', 'sysJobLogController', '定时任务调度日志表', 'baseQueryPageByParam', '/module/quartz/sysjoblog/baseQueryPageByParam', '/module/quartz/sysjoblog/baseQueryPageByParam', 'module:quartz:sysjoblog:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479172, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674581645400788994, '0,1674579089589063681,1674581645400788994', 'sessionInfoController', '会话表', 'deleteSession', '/module/session/sessioninfo/deleteSession/{sessionIds}', '/module/session/sessioninfo/deleteSession/*', 'module:session:sessioninfo:deleteSession:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479173, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582466536460289, '0,1674579384591241218,1674582466536460289', 'sysPermissionController', '权限管理', 'baseQueryPageByParam', '/module/system/syspermission/baseQueryPageByParam', '/module/system/syspermission/baseQueryPageByParam', 'module:system:syspermission:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479174, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582466536460289, '0,1674579384591241218,1674582466536460289', 'sysPermissionController', '权限管理', 'getPermissionModuleLabelOption', '/module/system/syspermission/getPermissionModuleLabelOption', '/module/system/syspermission/getPermissionModuleLabelOption', 'module:system:syspermission:getPermissionModuleLabelOption', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479175, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582491907805185, '0,1674579384591241218,1674582491907805185', 'sysRoleController', '角色信息', 'getLabelOption', '/module/system/sysrole/getLabelOption', '/module/system/sysrole/getLabelOption', 'module:system:sysrole:getLabelOption', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479176, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582505417658369, '0,1674579384591241218,1674582505417658369', 'sysMenuController', '菜单权限', 'selectPermissionTree', '/module/system/sysmenu/selectPermissionTree', '/module/system/sysmenu/selectPermissionTree', 'module:system:sysmenu:selectPermissionTree', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479177, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictDataController', '字典数据', 'baseQueryByParam', '/module/system/basedictdata/baseQueryByParam', '/module/system/basedictdata/baseQueryByParam', 'module:system:basedictdata:baseQueryByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479178, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674580927755374593, '0,1674578853248421890,1674580927755374593', 'sysProfileController', '个人业务', 'avatar', '/module/system/profile/avatar', '/module/system/profile/avatar', 'module:system:profile:avatar', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479179, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674579841480970241, '0,1674578906511888386,1674579841480970241', 'sysJobController', '定时任务调度表', 'baseAdd', '/module/quartz/sysjob/baseAdd', '/module/quartz/sysjob/baseAdd', 'module:quartz:sysjob:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479180, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674581849239769090, '0,1674579114499035137,1674581849239769090', 'sysOperLogController', '操作日志记录- 控制器', 'baseAdd', '/module/system/sysoperlog/baseAdd', '/module/system/sysoperlog/baseAdd', 'module:system:sysoperlog:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479181, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582439277678594, '0,1674579384591241218,1674582439277678594', 'loginPcController', '登陆控制器', 'info', '/auth/system/info', '/auth/system/info', 'auth:system:info', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479182, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674580927755374593, '0,1674578853248421890,1674580927755374593', 'sysUserController', '用户信息', 'insertAuthRole', '/module/system/sysuser/insertAuthRole', '/module/system/sysuser/insertAuthRole', 'module:system:sysuser:insertAuthRole', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479183, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674579841480970241, '0,1674578906511888386,1674579841480970241', 'sysJobController', '定时任务调度表', 'baseQueryPageByParam', '/module/quartz/sysjob/baseQueryPageByParam', '/module/quartz/sysjob/baseQueryPageByParam', 'module:quartz:sysjob:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479184, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674583564886265858, '0,1674579089589063681,1674583564886265858', 'domainController', '领域会话', 'baseQueryPageByParam', '/module/session/domain/baseQueryPageByParam', '/module/session/domain/baseQueryPageByParam', 'module:session:domain:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479187, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674578906511888386, '0,1674578906511888386', 'apiResourceController', '', 'uiConfiguration', '/swagger-resources/configuration/ui', '/swagger-resources/configuration/ui', 'swagger-resources:configuration:ui', '', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479188, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674581892462071809, '0,1674579114499035137,1674581892462071809', 'sysLogininforController', '系统访问记录- 控制器', 'baseQueryByParam', '/module/system/syslogininfor/baseQueryByParam', '/module/system/syslogininfor/baseQueryByParam', 'module:system:syslogininfor:baseQueryByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479189, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582466536460289, '0,1674579384591241218,1674582466536460289', 'sysPermissionController', '权限管理', 'baseQueryByParam', '/module/system/syspermission/baseQueryByParam', '/module/system/syspermission/baseQueryByParam', 'module:system:syspermission:baseQueryByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479190, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictDataController', '字典数据', 'baseEdit', '/module/system/basedictdata/baseEdit', '/module/system/basedictdata/baseEdit', 'module:system:basedictdata:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479193, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674580956448608258, '0,1674578853248421890,1674580956448608258', 'userInfoController', '用户控制器', 'login', '/module/user/userinfo/login', '/module/user/userinfo/login', 'module:user:userinfo:login', 'POST', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479194, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674583544443228161, '0,1674579089589063681,1674583544443228161', 'sessionRecordController', '会话详情', 'getRecordSession', '/module/session/sessionrecord/getRecordSession', '/module/session/sessionrecord/getRecordSession', 'module:session:sessionrecord:getRecordSession', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479195, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674583564886265858, '0,1674579089589063681,1674583564886265858', 'domainController', '领域会话', 'baseAdd', '/module/session/domain/baseAdd', '/module/session/domain/baseAdd', 'module:session:domain:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479196, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582466536460289, '0,1674579384591241218,1674582466536460289', 'sysPermissionController', '权限管理', 'allocationRouteModule', '/module/system/syspermission/allocationRouteModule', '/module/system/syspermission/allocationRouteModule', 'module:system:syspermission:allocationRouteModule', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479197, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674580956448608258, '0,1674578853248421890,1674580956448608258', 'userInfoController', '用户控制器', 'register', '/module/user/userinfo/register', '/module/user/userinfo/register', 'module:user:userinfo:register', 'POST', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479198, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674579841480970241, '0,1674578906511888386,1674579841480970241', 'sysJobController', '定时任务调度表', 'baseDeleteByIds', '/module/quartz/sysjob/baseDeleteByIds/{ids}', '/module/quartz/sysjob/baseDeleteByIds/*', 'module:quartz:sysjob:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479199, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674580927755374593, '0,1674578853248421890,1674580927755374593', 'sysUserController', '用户信息', 'changeStatus', '/module/system/sysuser/changeStatus', '/module/system/sysuser/changeStatus', 'module:system:sysuser:changeStatus', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479200, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582491907805185, '0,1674579384591241218,1674582491907805185', 'sysRoleController', '角色信息', 'baseQueryById', '/module/system/sysrole/baseQueryById/{id}', '/module/system/sysrole/baseQueryById/*', 'module:system:sysrole:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479201, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictDataController', '字典数据', 'baseQueryPageByParam', '/module/system/basedictdata/baseQueryPageByParam', '/module/system/basedictdata/baseQueryPageByParam', 'module:system:basedictdata:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479203, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674581849239769090, '0,1674579114499035137,1674581849239769090', 'sysOperLogController', '操作日志记录- 控制器', 'baseEdit', '/module/system/sysoperlog/baseEdit', '/module/system/sysoperlog/baseEdit', 'module:system:sysoperlog:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479204, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictDataController', '字典数据', 'baseQueryById', '/module/system/basedictdata/baseQueryById/{id}', '/module/system/basedictdata/baseQueryById/*', 'module:system:basedictdata:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479205, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674581849239769090, '0,1674579114499035137,1674581849239769090', 'sysOperLogController', '操作日志记录- 控制器', 'baseDeleteByIds', '/module/system/sysoperlog/baseDeleteByIds/{ids}', '/module/system/sysoperlog/baseDeleteByIds/*', 'module:system:sysoperlog:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479206, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582466536460289, '0,1674579384591241218,1674582466536460289', 'sysPermissionController', '权限管理', 'baseAdd', '/module/system/syspermission/baseAdd', '/module/system/syspermission/baseAdd', 'module:system:syspermission:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479207, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674581892462071809, '0,1674579114499035137,1674581892462071809', 'sysLogininforController', '系统访问记录- 控制器', 'clean', '/module/system/syslogininfor/clean', '/module/system/syslogininfor/clean', 'module:system:syslogininfor:clean', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479208, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674583564886265858, '0,1674579089589063681,1674583564886265858', 'domainController', '领域会话', 'baseQueryByParam', '/module/session/domain/baseQueryByParam', '/module/session/domain/baseQueryByParam', 'module:session:domain:baseQueryByParam', 'POST', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479209, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674581645400788994, '0,1674579089589063681,1674581645400788994', 'sessionInfoController', '会话表', 'userLastSession', '/module/session/sessioninfo/userLastSession/{sessionType}', '/module/session/sessioninfo/userLastSession/*', 'module:session:sessioninfo:userLastSession:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728761479210, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582466536460289, '0,1674579384591241218,1674582466536460289', 'sysPermissionController', '权限管理', 'baseDeleteByIds', '/module/system/syspermission/baseDeleteByIds/{ids}', '/module/system/syspermission/baseDeleteByIds/*', 'module:system:syspermission:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588034, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582466536460289, '0,1674579384591241218,1674582466536460289', 'sysPermissionController', '权限管理', 'getPermissionNotRouteLabelOption', '/module/system/syspermission/getPermissionNotRouteLabelOption', '/module/system/syspermission/getPermissionNotRouteLabelOption', 'module:system:syspermission:getPermissionNotRouteLabelOption', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588035, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictTypeController', '字典类型', 'flushCache', '/module/system/basedicttype/flushCache', '/module/system/basedicttype/flushCache', 'module:system:basedicttype:flushCache', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588036, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674581849239769090, '0,1674579114499035137,1674581849239769090', 'sysOperLogController', '操作日志记录- 控制器', 'baseQueryById', '/module/system/sysoperlog/baseQueryById/{id}', '/module/system/sysoperlog/baseQueryById/*', 'module:system:sysoperlog:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588037, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674583564886265858, '0,1674579089589063681,1674583564886265858', 'domainController', '领域会话', 'getWindowData', '/module/session/domain/getWindowData/{domainKey}', '/module/session/domain/getWindowData/*', 'module:session:domain:getWindowData:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588038, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582491907805185, '0,1674579384591241218,1674582491907805185', 'sysRoleController', '角色信息', 'roleAllocationRoute', '/module/system/sysrole/roleAllocationRoute', '/module/system/sysrole/roleAllocationRoute', 'module:system:sysrole:roleAllocationRoute', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588039, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674578906511888386, '0,1674578906511888386', 'apiResourceController', '', 'swaggerResources', '/swagger-resources', '/swagger-resources', 'swagger-resources', '', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588040, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674581849239769090, '0,1674579114499035137,1674581849239769090', 'sysOperLogController', '操作日志记录- 控制器', 'baseQueryByParam', '/module/system/sysoperlog/baseQueryByParam', '/module/system/sysoperlog/baseQueryByParam', 'module:system:sysoperlog:baseQueryByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588041, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674581892462071809, '0,1674579114499035137,1674581892462071809', 'sysLogininforController', '系统访问记录- 控制器', 'remove', '/module/system/syslogininfor/{infoIds}', '/module/system/syslogininfor/*', 'module:system:syslogininfor:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588042, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582505417658369, '0,1674579384591241218,1674582505417658369', 'sysMenuController', '菜单权限', 'selectPermissionTreeModel', '/module/system/sysmenu/selectPermissionTreeModel', '/module/system/sysmenu/selectPermissionTreeModel', 'module:system:sysmenu:selectPermissionTreeModel', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588043, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582491907805185, '0,1674579384591241218,1674582491907805185', 'sysRoleController', '角色信息', 'unallocatedList', '/module/system/sysrole/authUser/unallocatedList', '/module/system/sysrole/authUser/unallocatedList', 'module:system:sysrole:authUser:unallocatedList', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588044, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674581814276050945, '0,1674579114499035137,1674581814276050945', 'sysJobLogController', '定时任务调度日志表', 'baseEdit', '/module/quartz/sysjoblog/baseEdit', '/module/quartz/sysjoblog/baseEdit', 'module:quartz:sysjoblog:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588045, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582491907805185, '0,1674579384591241218,1674582491907805185', 'sysRoleController', '角色信息', 'allocatedList', '/module/system/sysrole/authUser/allocatedList', '/module/system/sysrole/authUser/allocatedList', 'module:system:sysrole:authUser:allocatedList', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588046, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674583564886265858, '0,1674579089589063681,1674583564886265858', 'domainController', '领域会话', 'baseQueryById', '/module/session/domain/baseQueryById/{id}', '/module/session/domain/baseQueryById/*', 'module:session:domain:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588047, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674580927755374593, '0,1674578853248421890,1674580927755374593', 'sysProfileController', '个人业务', 'profile', '/module/system/profile', '/module/system/profile', 'module:system:profile', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588048, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674581892462071809, '0,1674579114499035137,1674581892462071809', 'sysLogininforController', '系统访问记录- 控制器', 'baseAdd', '/module/system/syslogininfor/baseAdd', '/module/system/syslogininfor/baseAdd', 'module:system:syslogininfor:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588049, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582491907805185, '0,1674579384591241218,1674582491907805185', 'sysRoleController', '角色信息', 'baseDeleteByIds', '/module/system/sysrole/baseDeleteByIds/{ids}', '/module/system/sysrole/baseDeleteByIds/*', 'module:system:sysrole:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588050, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674579841480970241, '0,1674578906511888386,1674579841480970241', 'sysJobController', '定时任务调度表', 'changeStatus', '/module/quartz/sysjob/changeStatus', '/module/quartz/sysjob/changeStatus', 'module:quartz:sysjob:changeStatus', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588052, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674578906511888386, '0,1674578906511888386', 'basicErrorController', '', 'errorHtml', '/error', '/error', 'error', '', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588053, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674579182287376385, '0,1674578853248421890,1674579182287376385', 'sysUserOnlineController', '在线用户监控', 'baseEdit', '/module/system/monitor/online/baseEdit', '/module/system/monitor/online/baseEdit', 'module:system:monitor:online:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588054, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674580927755374593, '0,1674578853248421890,1674580927755374593', 'sysUserController', '用户信息', 'authRole', '/module/system/sysuser/authRole/{id}', '/module/system/sysuser/authRole/*', 'module:system:sysuser:authRole:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588055, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674578906511888386, '0,1674578906511888386', 'apiResourceController', '', 'securityConfiguration', '/swagger-resources/configuration/security', '/swagger-resources/configuration/security', 'swagger-resources:configuration:security', '', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588057, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582818715389954, '0,1674580535470510082,1674582818715389954', 'resourceConfigController', '系统参数配置', 'queryByConfigKey', '/module/system/baseresourceconfig/queryByConfigKey/{configKey}', '/module/system/baseresourceconfig/queryByConfigKey/*', 'module:system:baseresourceconfig:queryByConfigKey:*', 'GET', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588058, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674579708391510017, '0,1674578906511888386,1674579708391510017', 'commonController', '通用控制器', 'getEnumLabel', '/common/getEnumLabel/{key}', '/common/getEnumLabel/*', 'common:getEnumLabel:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588059, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582505417658369, '0,1674579384591241218,1674582505417658369', 'sysMenuController', '菜单权限', 'roleMenuTreeselect', '/module/system/sysmenu/roleMenuTreeselect/{roleId}', '/module/system/sysmenu/roleMenuTreeselect/*', 'module:system:sysmenu:roleMenuTreeselect:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588061, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674581814276050945, '0,1674579114499035137,1674581814276050945', 'sysJobLogController', '定时任务调度日志表', 'baseQueryById', '/module/quartz/sysjoblog/baseQueryById/{id}', '/module/quartz/sysjoblog/baseQueryById/*', 'module:quartz:sysjoblog:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588062, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674580927755374593, '0,1674578853248421890,1674580927755374593', 'sysUserController', '用户信息', 'baseDeleteByIds', '/module/system/sysuser/baseDeleteByIds/{ids}', '/module/system/sysuser/baseDeleteByIds/*', 'module:system:sysuser:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588063, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582439277678594, '0,1674579384591241218,1674582439277678594', 'loginPcController', '登陆控制器', 'login', '/auth/system/login', '/auth/system/login', 'auth:system:login', 'POST', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588064, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictDataController', '字典数据', 'baseDeleteByIds', '/module/system/basedictdata/baseDeleteByIds/{ids}', '/module/system/basedictdata/baseDeleteByIds/*', 'module:system:basedictdata:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588065, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674579182287376385, '0,1674578853248421890,1674579182287376385', 'sysUserOnlineController', '在线用户监控', 'baseDeleteByIds', '/module/system/monitor/online/baseDeleteByIds/{ids}', '/module/system/monitor/online/baseDeleteByIds/*', 'module:system:monitor:online:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588066, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictTypeController', '字典类型', 'baseAdd', '/module/system/basedicttype/baseAdd', '/module/system/basedicttype/baseAdd', 'module:system:basedicttype:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588067, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582818715389954, '0,1674580535470510082,1674582818715389954', 'resourceConfigController', '系统参数配置', 'editByConfigKey', '/module/system/baseresourceconfig/editByConfigKey', '/module/system/baseresourceconfig/editByConfigKey', 'module:system:baseresourceconfig:editByConfigKey', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588069, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674579841480970241, '0,1674578906511888386,1674579841480970241', 'sysJobController', '定时任务调度表', 'baseEdit', '/module/quartz/sysjob/baseEdit', '/module/quartz/sysjob/baseEdit', 'module:quartz:sysjob:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588070, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674581645400788994, '0,1674579089589063681,1674581645400788994', 'sessionInfoController', '会话表', 'getSessionInfo', '/module/session/sessioninfo/getSessionInfo/{sessionId}', '/module/session/sessioninfo/getSessionInfo/*', 'module:session:sessioninfo:getSessionInfo:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588071, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictTypeController', '字典类型', 'baseQueryPageByParam', '/module/system/basedicttype/baseQueryPageByParam', '/module/system/basedicttype/baseQueryPageByParam', 'module:system:basedicttype:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588072, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674580927755374593, '0,1674578853248421890,1674580927755374593', 'sysUserController', '用户信息', 'baseAdd', '/module/system/sysuser/baseAdd', '/module/system/sysuser/baseAdd', 'module:system:sysuser:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588073, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674580927755374593, '0,1674578853248421890,1674580927755374593', 'sysUserController', '用户信息', 'resetPwd', '/module/system/sysuser/resetPwd', '/module/system/sysuser/resetPwd', 'module:system:sysuser:resetPwd', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588074, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674583544443228161, '0,1674579089589063681,1674583544443228161', 'sessionRecordController', '会话详情', 'getPageRecordSession', '/module/session/sessionrecord/getPageRecordSession', '/module/session/sessionrecord/getPageRecordSession', 'module:session:sessionrecord:getPageRecordSession', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588075, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674581814276050945, '0,1674579114499035137,1674581814276050945', 'sysJobLogController', '定时任务调度日志表', 'clean', '/module/quartz/sysjoblog/clean', '/module/quartz/sysjoblog/clean', 'module:quartz:sysjoblog:clean', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588076, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674580927755374593, '0,1674578853248421890,1674580927755374593', 'sysUserController', '用户信息', 'baseQueryPageByParam', '/module/system/sysuser/baseQueryPageByParam', '/module/system/sysuser/baseQueryPageByParam', 'module:system:sysuser:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588077, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674579182287376385, '0,1674578853248421890,1674579182287376385', 'sysUserOnlineController', '在线用户监控', 'baseQueryById', '/module/system/monitor/online/baseQueryById/{id}', '/module/system/monitor/online/baseQueryById/*', 'module:system:monitor:online:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588078, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582491907805185, '0,1674579384591241218,1674582491907805185', 'sysRoleController', '角色信息', 'editRole', '/module/system/sysrole/editRole', '/module/system/sysrole/editRole', 'module:system:sysrole:editRole', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588079, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674581849239769090, '0,1674579114499035137,1674581849239769090', 'sysOperLogController', '操作日志记录- 控制器', 'cleanOperlog', '/module/system/sysoperlog/clean', '/module/system/sysoperlog/clean', 'module:system:sysoperlog:clean', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588080, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582466536460289, '0,1674579384591241218,1674582466536460289', 'sysPermissionController', '权限管理', 'baseQueryById', '/module/system/syspermission/baseQueryById/{id}', '/module/system/syspermission/baseQueryById/*', 'module:system:syspermission:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588081, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674579841480970241, '0,1674578906511888386,1674579841480970241', 'sysJobController', '定时任务调度表', 'run', '/module/quartz/sysjob/run', '/module/quartz/sysjob/run', 'module:quartz:sysjob:run', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588082, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674581645400788994, '0,1674579089589063681,1674581645400788994', 'sessionInfoController', '会话表', 'clearSession', '/module/session/sessioninfo/clearSession/{sessionId}', '/module/session/sessioninfo/clearSession/*', 'module:session:sessioninfo:clearSession:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588083, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674580927755374593, '0,1674578853248421890,1674580927755374593', 'sysUserController', '用户信息', 'baseEdit', '/module/system/sysuser/baseEdit', '/module/system/sysuser/baseEdit', 'module:system:sysuser:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588084, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictTypeController', '字典类型', 'baseDeleteByIds', '/module/system/basedicttype/baseDeleteByIds/{ids}', '/module/system/basedicttype/baseDeleteByIds/*', 'module:system:basedicttype:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578728828588085, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674579841480970241, '0,1674578906511888386,1674579841480970241', 'sysJobController', '定时任务调度表', 'baseQueryById', '/module/quartz/sysjob/baseQueryById/{id}', '/module/quartz/sysjob/baseQueryById/*', 'module:quartz:sysjob:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729461927938, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582505417658369, '0,1674579384591241218,1674582505417658369', 'sysMenuController', '菜单权限', 'selectPermissionRoute', '/module/system/sysmenu/selectPermissionRoute', '/module/system/sysmenu/selectPermissionRoute', 'module:system:sysmenu:selectPermissionRoute', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729461927939, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582466536460289, '0,1674579384591241218,1674582466536460289', 'sysPermissionController', '权限管理', 'mappingSync', '/module/system/syspermission/mappingSync', '/module/system/syspermission/mappingSync', 'module:system:syspermission:mappingSync', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729529036801, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674583564886265858, '0,1674579089589063681,1674583564886265858', 'domainController', '领域会话', 'baseEdit', '/module/session/domain/baseEdit', '/module/session/domain/baseEdit', 'module:session:domain:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729529036802, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674581814276050945, '0,1674579114499035137,1674581814276050945', 'sysJobLogController', '定时任务调度日志表', 'baseAdd', '/module/quartz/sysjoblog/baseAdd', '/module/quartz/sysjoblog/baseAdd', 'module:quartz:sysjoblog:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729529036803, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582466536460289, '0,1674579384591241218,1674582466536460289', 'sysPermissionController', '权限管理', 'rolePermissionTreeList', '/module/system/syspermission/rolePermissionTreeList', '/module/system/syspermission/rolePermissionTreeList', 'module:system:syspermission:rolePermissionTreeList', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729529036804, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582491907805185, '0,1674579384591241218,1674582491907805185', 'sysRoleController', '角色信息', 'cancelAuthUser', '/module/system/sysrole/authUser/cancel', '/module/system/sysrole/authUser/cancel', 'module:system:sysrole:authUser:cancel', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729529036805, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictDataController', '字典数据', 'baseAdd', '/module/system/basedictdata/baseAdd', '/module/system/basedictdata/baseAdd', 'module:system:basedictdata:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729529036807, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674581892462071809, '0,1674579114499035137,1674581892462071809', 'sysLogininforController', '系统访问记录- 控制器', 'baseQueryPageByParam', '/module/system/syslogininfor/baseQueryPageByParam', '/module/system/syslogininfor/baseQueryPageByParam', 'module:system:syslogininfor:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729529036809, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictTypeController', '字典类型', 'baseQueryById', '/module/system/basedicttype/baseQueryById/{id}', '/module/system/basedicttype/baseQueryById/*', 'module:system:basedicttype:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922882, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582505417658369, '0,1674579384591241218,1674582505417658369', 'sysMenuController', '菜单权限', 'baseAdd', '/module/system/sysmenu/baseAdd', '/module/system/sysmenu/baseAdd', 'module:system:sysmenu:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922883, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582491907805185, '0,1674579384591241218,1674582491907805185', 'sysRoleController', '角色信息', 'baseQueryPageByParam', '/module/system/sysrole/baseQueryPageByParam', '/module/system/sysrole/baseQueryPageByParam', 'module:system:sysrole:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922884, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674580956448608258, '0,1674578853248421890,1674580956448608258', 'userInfoController', '游客登陆', 'touristLogin', '/module/user/userinfo/touristLogin', '/module/user/userinfo/touristLogin', 'module:user:userinfo:touristLogin', 'GET', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922885, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674580927755374593, '0,1674578853248421890,1674580927755374593', 'sysProfileController', '个人业务', 'updateUser', '/module/system/profile/updateUser', '/module/system/profile/updateUser', 'module:system:profile:updateUser', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922886, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582466536460289, '0,1674579384591241218,1674582466536460289', 'sysPermissionController', '权限管理', 'flushPermissionConfig', '/module/system/syspermission/flushPermissionConfig', '/module/system/syspermission/flushPermissionConfig', 'module:system:syspermission:flushPermissionConfig', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922887, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674580927755374593, '0,1674578853248421890,1674580927755374593', 'sysProfileController', '个人业务', 'resetPassword', '/module/system/profile/updatePwd', '/module/system/profile/updatePwd', 'module:system:profile:updatePwd', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922888, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674581892462071809, '0,1674579114499035137,1674581892462071809', 'sysLogininforController', '系统访问记录- 控制器', 'baseDeleteByIds', '/module/system/syslogininfor/baseDeleteByIds/{ids}', '/module/system/syslogininfor/baseDeleteByIds/*', 'module:system:syslogininfor:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922889, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582505417658369, '0,1674579384591241218,1674582505417658369', 'sysMenuController', '菜单权限', 'baseDeleteByIds', '/module/system/sysmenu/baseDeleteByIds/{ids}', '/module/system/sysmenu/baseDeleteByIds/*', 'module:system:sysmenu:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922890, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582505417658369, '0,1674579384591241218,1674582505417658369', 'sysMenuController', '菜单权限', 'baseQueryById', '/module/system/sysmenu/baseQueryById/{id}', '/module/system/sysmenu/baseQueryById/*', 'module:system:sysmenu:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922891, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictTypeController', '字典类型', 'optionSelect', '/module/system/basedicttype/optionSelect', '/module/system/basedicttype/optionSelect', 'module:system:basedicttype:optionSelect', 'GET', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922892, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674581645400788994, '0,1674579089589063681,1674581645400788994', 'sessionInfoController', '会话表', 'userLastDomainSession', '/module/session/sessioninfo/userLastDomainSession', '/module/session/sessioninfo/userLastDomainSession', 'module:session:sessioninfo:userLastDomainSession', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922893, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582491907805185, '0,1674579384591241218,1674582491907805185', 'sysRoleController', '角色信息', 'baseEdit', '/module/system/sysrole/baseEdit', '/module/system/sysrole/baseEdit', 'module:system:sysrole:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922894, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674579182287376385, '0,1674578853248421890,1674579182287376385', 'sysUserOnlineController', '在线用户监控', 'baseQueryPageByParam', '/module/system/monitor/online/baseQueryPageByParam', '/module/system/monitor/online/baseQueryPageByParam', 'module:system:monitor:online:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922895, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674581814276050945, '0,1674579114499035137,1674581814276050945', 'sysJobLogController', '定时任务调度日志表', 'baseDeleteByIds', '/module/quartz/sysjoblog/baseDeleteByIds/{ids}', '/module/quartz/sysjoblog/baseDeleteByIds/*', 'module:quartz:sysjoblog:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922896, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictDataController', '字典数据', 'getListByDictType', '/module/system/basedictdata/getListByDictType/{dictType}', '/module/system/basedictdata/getListByDictType/*', 'module:system:basedictdata:getListByDictType:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922897, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582491907805185, '0,1674579384591241218,1674582491907805185', 'sysRoleController', '角色信息', 'cancelAuthUserAll', '/module/system/sysrole/authUser/cancelAll', '/module/system/sysrole/authUser/cancelAll', 'module:system:sysrole:authUser:cancelAll', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922900, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674579182287376385, '0,1674578853248421890,1674579182287376385', 'sysUserOnlineController', '在线用户监控', 'baseAdd', '/module/system/monitor/online/baseAdd', '/module/system/monitor/online/baseAdd', 'module:system:monitor:online:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922901, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582466536460289, '0,1674579384591241218,1674582466536460289', 'sysPermissionController', '权限管理', 'flushRouteCache', '/module/system/syspermission/flushRouteCache', '/module/system/syspermission/flushRouteCache', 'module:system:syspermission:flushRouteCache', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922902, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictTypeController', '字典类型', 'baseEdit', '/module/system/basedicttype/baseEdit', '/module/system/basedicttype/baseEdit', 'module:system:basedicttype:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922903, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674580927755374593, '0,1674578853248421890,1674580927755374593', 'sysUserController', '用户信息', 'baseQueryById', '/module/system/sysuser/baseQueryById/{id}', '/module/system/sysuser/baseQueryById/*', 'module:system:sysuser:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922904, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674581645400788994, '0,1674579089589063681,1674581645400788994', 'sessionInfoController', '会话表', 'addSession', '/module/session/sessioninfo/addSession/{sessionType}', '/module/session/sessioninfo/addSession/*', 'module:session:sessioninfo:addSession:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922905, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674581645400788994, '0,1674579089589063681,1674581645400788994', 'sessionInfoController', '会话表', 'addDomainSession', '/module/session/sessioninfo/addDomainSession', '/module/session/sessioninfo/addDomainSession', 'module:session:sessioninfo:addDomainSession', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922906, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582466536460289, '0,1674579384591241218,1674582466536460289', 'sysPermissionController', '权限管理', 'changeRouteVisitRule', '/module/system/syspermission/changeRouteVisitRule', '/module/system/syspermission/changeRouteVisitRule', 'module:system:syspermission:changeRouteVisitRule', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922907, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674581892462071809, '0,1674579114499035137,1674581892462071809', 'sysLogininforController', '系统访问记录- 控制器', 'baseEdit', '/module/system/syslogininfor/baseEdit', '/module/system/syslogininfor/baseEdit', 'module:system:syslogininfor:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922908, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674581892462071809, '0,1674579114499035137,1674581892462071809', 'sysLogininforController', '系统访问记录- 控制器', 'baseQueryById', '/module/system/syslogininfor/baseQueryById/{id}', '/module/system/syslogininfor/baseQueryById/*', 'module:system:syslogininfor:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922910, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674579841480970241, '0,1674578906511888386,1674579841480970241', 'sysJobController', '定时任务调度表', 'baseQueryByParam', '/module/quartz/sysjob/baseQueryByParam', '/module/quartz/sysjob/baseQueryByParam', 'module:quartz:sysjob:baseQueryByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922912, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674580927755374593, '0,1674578853248421890,1674580927755374593', 'sysProfileController', '个人业务', 'update', '/module/system/profile/update', '/module/system/profile/update', 'module:system:profile:update', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922913, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674583564886265858, '0,1674579089589063681,1674583564886265858', 'domainController', '领域会话', 'baseDeleteByIds', '/module/session/domain/baseDeleteByIds/{ids}', '/module/session/domain/baseDeleteByIds/*', 'module:session:domain:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922914, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674580956448608258, '0,1674578853248421890,1674580956448608258', 'userInfoController', '用户控制器', 'getInfo', '/module/user/userinfo/getInfo', '/module/user/userinfo/getInfo', 'module:user:userinfo:getInfo', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922915, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582466536460289, '0,1674579384591241218,1674582466536460289', 'sysPermissionController', '权限管理', 'rolePermissionByRoleId', '/module/system/syspermission/rolePermissionByRoleId/{roleId}', '/module/system/syspermission/rolePermissionByRoleId/*', 'module:system:syspermission:rolePermissionByRoleId:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729612922916, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582505417658369, '0,1674579384591241218,1674582505417658369', 'sysMenuController', '菜单权限', 'baseEdit', '/module/system/sysmenu/baseEdit', '/module/system/sysmenu/baseEdit', 'module:system:sysmenu:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729663254531, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674578906511888386, '0,1674578906511888386', 'basicErrorController', '', 'error', '/error', '/error', 'error', '', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1674578729663254532, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674578906511888386, '0,1674578906511888386', 'errorController', '', 'printError', '/error/print', '/error/print', 'error:print', '', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1674578729663254533, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582505417658369, '0,1674579384591241218,1674582505417658369', 'sysMenuController', '菜单权限', 'baseQueryPageByParam', '/module/system/sysmenu/baseQueryPageByParam', '/module/system/sysmenu/baseQueryPageByParam', 'module:system:sysmenu:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729663254534, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictTypeController', '字典类型', 'baseQueryByParam', '/module/system/basedicttype/baseQueryByParam', '/module/system/basedicttype/baseQueryByParam', 'module:system:basedicttype:baseQueryByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729663254535, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582491907805185, '0,1674579384591241218,1674582491907805185', 'sysRoleController', '角色信息', 'baseQueryByParam', '/module/system/sysrole/baseQueryByParam', '/module/system/sysrole/baseQueryByParam', 'module:system:sysrole:baseQueryByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729663254536, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582790324146177, '0,1674580535470510082,1674582790324146177', 'baseDictDataController', '字典数据', 'optionSelect', '/module/system/basedictdata/optionSelect/{dictType}', '/module/system/basedictdata/optionSelect/*', 'module:system:basedictdata:optionSelect:*', 'GET', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1674578729663254537, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582466536460289, '0,1674579384591241218,1674582466536460289', 'sysPermissionController', '权限管理', 'baseEdit', '/module/system/syspermission/baseEdit', '/module/system/syspermission/baseEdit', 'module:system:syspermission:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729663254538, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674579182287376385, '0,1674578853248421890,1674579182287376385', 'sysUserOnlineController', '在线用户监控', 'baseQueryByParam', '/module/system/monitor/online/baseQueryByParam', '/module/system/monitor/online/baseQueryByParam', 'module:system:monitor:online:baseQueryByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578729663254541, '2023-06-30 08:40:55', '1000000000', NULL, '2023-06-30 08:40:55', 0, 0, 1674582505417658369, '0,1674579384591241218,1674582505417658369', 'sysMenuController', '菜单权限', 'baseQueryByParam', '/module/system/sysmenu/baseQueryByParam', '/module/system/sysmenu/baseQueryByParam', 'module:system:sysmenu:baseQueryByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674578853248421890, '2023-06-30 08:41:25', '1000000000', '1000000000', '2023-06-30 08:41:25', 0, 0, 0, '0', '用户', '用户', '用户', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674578906511888386, '2023-06-30 08:41:38', '1000000000', '1000000000', '2023-06-30 08:41:38', 0, 0, 0, '0', '其他', '其他', '其他', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674579089589063681, '2023-06-30 08:42:21', '1000000000', '1000000000', '2023-06-30 08:42:21', 0, 0, 0, '0', '客户端会话', '客户端会话', '客户端会话', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674579114499035137, '2023-06-30 08:42:27', '1000000000', '1000000000', '2023-06-30 08:42:27', 0, 0, 0, '0', '日志', '日志', '日志', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674579182287376385, '2023-06-30 08:42:43', '1000000000', '1000000000', '2023-06-30 08:42:43', 0, 0, 1674578853248421890, '0,1674578853248421890', '在线用户监控', '在线用户监控', '在线用户监控', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674579384591241218, '2023-06-30 08:43:32', '1000000000', '1000000000', '2023-06-30 08:43:32', 0, 0, 0, '0', '权限', '权限', '权限', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674579708391510017, '2023-06-30 08:44:49', '1000000000', '1000000000', '2023-06-30 08:44:49', 0, 0, 1674578906511888386, '0,1674578906511888386', '通用', '通用', '通用', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674579841480970241, '2023-06-30 08:45:21', '1000000000', '1000000000', '2023-06-30 08:45:21', 0, 0, 1674578906511888386, '0,1674578906511888386', '定时任务', '定时任务', '定时任务', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674580535470510082, '2023-06-30 08:48:06', '1000000000', '1000000000', '2023-06-30 08:48:06', 0, 0, 0, '0', '系统', '系统', '系统', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674580927755374593, '2023-06-30 08:49:40', '1000000000', '1000000000', '2023-06-30 08:49:40', 0, 0, 1674578853248421890, '0,1674578853248421890', '系统用户', '系统用户', '系统用户', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674580956448608258, '2023-06-30 08:49:46', '1000000000', '1000000000', '2023-06-30 08:49:46', 0, 0, 1674578853248421890, '0,1674578853248421890', '客户端用户', '客户端用户', '客户端用户', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674581584893759489, '2023-06-30 08:52:16', '1000000000', '1000000000', '2023-06-30 08:52:16', 0, 0, 1674579089589063681, '0,1674579089589063681', '消息发送', '消息发送', '消息发送', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674581645400788994, '2023-06-30 08:52:31', '1000000000', '1000000000', '2023-06-30 08:52:31', 0, 0, 1674579089589063681, '0,1674579089589063681', '会话管理', '会话管理', '会话管理', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674581814276050945, '2023-06-30 08:53:11', '1000000000', '1000000000', '2023-06-30 08:53:11', 0, 0, 1674579114499035137, '0,1674579114499035137', '定时任务日志', '定时任务日志', '定时任务日志', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674581849239769090, '2023-06-30 08:53:19', '1000000000', '1000000000', '2023-06-30 08:53:19', 0, 0, 1674579114499035137, '0,1674579114499035137', '操作记录日志', '操作记录日志', '操作记录日志', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674581892462071809, '2023-06-30 08:53:30', '1000000000', '1000000000', '2023-06-30 08:53:30', 0, 0, 1674579114499035137, '0,1674579114499035137', '访问日志', '访问日志', '访问日志', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674582439277678594, '2023-06-30 08:55:40', '1000000000', '1000000000', '2023-06-30 08:55:40', 0, 0, 1674579384591241218, '0,1674579384591241218', '管理员登陆', '管理员登陆', '管理员登陆', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674582466536460289, '2023-06-30 08:55:46', '1000000000', '1000000000', '2023-06-30 08:55:46', 0, 0, 1674579384591241218, '0,1674579384591241218', '接口权限', '接口权限', '接口权限', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674582491907805185, '2023-06-30 08:55:52', '1000000000', '1000000000', '2023-06-30 08:55:52', 0, 0, 1674579384591241218, '0,1674579384591241218', '角色', '角色', '角色', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674582505417658369, '2023-06-30 08:55:56', '1000000000', '1000000000', '2023-06-30 08:55:56', 0, 0, 1674579384591241218, '0,1674579384591241218', '菜单', '菜单', '菜单', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674582790324146177, '2023-06-30 08:57:04', '1000000000', '1000000000', '2023-06-30 08:57:04', 0, 0, 1674580535470510082, '0,1674580535470510082', '字典', '字典', '字典', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674582818715389954, '2023-06-30 08:57:10', '1000000000', '1000000000', '2023-06-30 08:57:10', 0, 0, 1674580535470510082, '0,1674580535470510082', '系统参数', '系统参数', '系统参数', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674582856791281665, '2023-06-30 08:57:19', '1000000000', '1000000000', '2023-06-30 08:57:19', 0, 0, 1674580535470510082, '0,1674580535470510082', 'apikeys', 'apikeys', 'apikeys', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674583544443228161, '2023-06-30 09:00:03', '1000000000', '1000000000', '2023-06-30 09:00:03', 0, 0, 1674579089589063681, '0,1674579089589063681', '会话记录', '会话记录', '会话记录', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674583564886265858, '2023-06-30 09:00:08', '1000000000', '1000000000', '2023-06-30 09:00:08', 0, 0, 1674579089589063681, '0,1674579089589063681', '领域会话', '领域会话', '领域会话', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1674622533179109377, '2023-06-30 11:34:59', '1000000000', NULL, '2023-06-30 11:34:59', 0, 0, 1674579708391510017, '0,1674578906511888386,1674579708391510017', 'commonController', '通用下载', 'download', '/common/download', '/common/download', 'common:download', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1674622533200080898, '2023-06-30 11:34:59', '1000000000', NULL, '2023-06-30 11:34:59', 0, 0, 1674579708391510017, '0,1674578906511888386,1674579708391510017', 'commonController', '文件上传', 'upload', '/common/upload', '/common/upload', 'common:upload', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1675013065540587522, '2023-07-01 13:26:49', '1000000000', NULL, '2023-07-01 13:26:49', 0, 0, 1674579708391510017, '0,1674578906511888386,1674579708391510017', 'commonController', '通用控制器', 'uploadImage', '/common/uploadImage', '/common/uploadImage', 'common:uploadImage', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1675793439851036674, '2023-07-03 17:07:45', '1000000000', NULL, '2023-07-03 17:07:45', 0, 0, 1676790579284992001, '0,1674580535470510082,1676790579284992001', 'sysFileConfigController', '文件配置', 'baseQueryPageByParam', '/module/system/sysfileconfig/baseQueryPageByParam', '/module/system/sysfileconfig/baseQueryPageByParam', 'module:system:sysfileconfig:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1675793439851036675, '2023-07-03 17:07:45', '1000000000', NULL, '2023-07-03 17:07:45', 0, 0, 1676790579284992001, '0,1674580535470510082,1676790579284992001', 'sysFileConfigController', '文件配置', 'baseEdit', '/module/system/sysfileconfig/baseEdit', '/module/system/sysfileconfig/baseEdit', 'module:system:sysfileconfig:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1675793439851036676, '2023-07-03 17:07:45', '1000000000', NULL, '2023-07-03 17:07:45', 0, 0, 1674580956448608258, '0,1674578853248421890,1674580956448608258', 'userInfoController', '用户控制器', 'clientUpdateUser', '/module/user/userinfo/clientUpdateUser', '/module/user/userinfo/clientUpdateUser', 'module:user:userinfo:clientUpdateUser', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1675793439851036677, '2023-07-03 17:07:45', '1000000000', NULL, '2023-07-03 17:07:45', 0, 0, 1676790579284992001, '0,1674580535470510082,1676790579284992001', 'sysFileConfigController', '文件配置', 'baseQueryByParam', '/module/system/sysfileconfig/baseQueryByParam', '/module/system/sysfileconfig/baseQueryByParam', 'module:system:sysfileconfig:baseQueryByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1675793439851036678, '2023-07-03 17:07:45', '1000000000', NULL, '2023-07-03 17:07:45', 0, 0, 1676790579284992001, '0,1674580535470510082,1676790579284992001', 'sysFileConfigController', '文件配置', 'baseDeleteByIds', '/module/system/sysfileconfig/baseDeleteByIds/{ids}', '/module/system/sysfileconfig/baseDeleteByIds/*', 'module:system:sysfileconfig:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1675793439851036679, '2023-07-03 17:07:45', '1000000000', NULL, '2023-07-03 17:07:45', 0, 0, 1676790579284992001, '0,1674580535470510082,1676790579284992001', 'sysFileConfigController', '文件配置', 'baseAdd', '/module/system/sysfileconfig/baseAdd', '/module/system/sysfileconfig/baseAdd', 'module:system:sysfileconfig:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1675793439851036680, '2023-07-03 17:07:45', '1000000000', NULL, '2023-07-03 17:07:45', 0, 0, 1676790579284992001, '0,1674580535470510082,1676790579284992001', 'sysFileConfigController', '文件配置', 'baseQueryById', '/module/system/sysfileconfig/baseQueryById/{id}', '/module/system/sysfileconfig/baseQueryById/*', 'module:system:sysfileconfig:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1676790579284992001, '2023-07-06 11:10:02', '1000000000', '1000000000', '2023-07-06 11:10:02', 0, 0, 1674580535470510082, '0,1674580535470510082', '文件配置', '文件配置', '文件配置', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1676790618824695810, '2023-07-06 11:10:11', '1000000000', NULL, '2023-07-06 11:10:11', 0, 0, 1674580956448608258, '0,1674578853248421890,1674580956448608258', 'userInfoController', '用户控制器', 'getUserGrants', '/module/user/userinfo/getUserGrants', '/module/user/userinfo/getUserGrants', 'module:user:userinfo:getUserGrants', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1676790618824695811, '2023-07-06 11:10:11', '1000000000', NULL, '2023-07-06 11:10:11', 0, 0, 1674581584893759489, '0,1674579089589063681,1674581584893759489', 'sseController', 'sse通用请求控制器', 'connect', '/sse/connect', '/sse/connect', 'sse:connect', 'GET', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1677188224147001346, '2023-07-07 13:30:07', '1000000000', NULL, '2023-07-07 13:30:07', 0, 0, 1674580956448608258, '0,1674578853248421890,1674580956448608258', 'userInfoController', '用户修改桌面背景', 'editUserBackground', '/module/user/userinfo/editUserBackground', '/module/user/userinfo/editUserBackground', 'module:user:userinfo:editUserBackground', 'GET', 1, 1, '0');
INSERT INTO `sys_permission` VALUES (1683755122745708546, '2023-07-25 16:24:38', '1000000000', NULL, '2023-07-25 16:24:38', 0, 0, 1674582856791281665, '0,1674580535470510082,1674582856791281665', 'openaiKeysController', 'apikeys', 'delUserApiKey', '/module/config/openaikeys/delUserApiKey/{id}', '/module/config/openaikeys/delUserApiKey/*', 'module:config:openaikeys:delUserApiKey:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1683755122808623105, '2023-07-25 16:24:38', '1000000000', NULL, '2023-07-25 16:24:38', 0, 0, 1674581645400788994, '0,1674579089589063681,1674581645400788994', 'sessionInfoController', '会话表', 'addDrawSession', '/module/session/sessioninfo/addDrawSession', '/module/session/sessioninfo/addDrawSession', 'module:session:sessioninfo:addDrawSession', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1683755122808623106, '2023-07-25 16:24:38', '1000000000', NULL, '2023-07-25 16:24:38', 0, 0, 1674582856791281665, '0,1674580535470510082,1674582856791281665', 'openaiKeysController', 'apikeys', 'addUserApiKey', '/module/config/openaikeys/addUserApiKey', '/module/config/openaikeys/addUserApiKey', 'module:config:openaikeys:addUserApiKey', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1683755122808623107, '2023-07-25 16:24:38', '1000000000', NULL, '2023-07-25 16:24:38', 0, 0, 1684019553161465857, '0,1674580535470510082,1684019553161465857', 'captchaController', '验证码校验', 'check', '/captcha/check', '/captcha/check', 'captcha:check', 'POST', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1683755122808623110, '2023-07-25 16:24:38', '1000000000', NULL, '2023-07-25 16:24:38', 0, 0, 1674580956448608258, '0,1674578853248421890,1674580956448608258', 'userInfoController', '用户控制器注册验证码发送', 'registerSendMail', '/module/user/userinfo/registerSendMail', '/module/user/userinfo/registerSendMail', 'module:user:userinfo:registerSendMail', 'GET', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1683755122808623112, '2023-07-25 16:24:38', '1000000000', NULL, '2023-07-25 16:24:38', 0, 0, 1674581584893759489, '0,1674579089589063681,1674581584893759489', 'chatController', '聊天会话控制器', 'send', '/module/chat/send', '/module/chat/send', 'module:chat:send', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1683755122808623114, '2023-07-25 16:24:38', '1000000000', NULL, '2023-07-25 16:24:38', 0, 0, 1674581584893759489, '0,1674579089589063681,1674581584893759489', 'chatController', '聊天会话控制器', 'sendDomain', '/module/chat/sendDomain', '/module/chat/sendDomain', 'module:chat:sendDomain', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1683755122808623115, '2023-07-25 16:24:38', '1000000000', NULL, '2023-07-25 16:24:38', 0, 0, 1674582856791281665, '0,1674580535470510082,1674582856791281665', 'openaiKeysController', 'apikeys', 'getUserApiKeys', '/module/config/openaikeys/getUserApiKeys', '/module/config/openaikeys/getUserApiKeys', 'module:config:openaikeys:getUserApiKeys', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1683755122808623117, '2023-07-25 16:24:38', '1000000000', NULL, '2023-07-25 16:24:38', 0, 0, 1674581645400788994, '0,1674579089589063681,1674581645400788994', 'sessionInfoController', '会话表', 'userLastDrawSession', '/module/session/sessioninfo/userLastDrawSession', '/module/session/sessioninfo/userLastDrawSession', 'module:session:sessioninfo:userLastDrawSession', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1683755420184776705, '2023-07-25 16:25:49', '1000000000', '1000000000', '2023-07-25 16:25:49', 0, 0, 1674579089589063681, '0,1674579089589063681', '绘图会话管理', '绘图会话管理', '绘图会话管理', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1684018183868010497, '2023-07-26 09:49:57', '1000000000', NULL, '2023-07-26 09:49:57', 0, 0, 1684019553161465857, '0,1674580535470510082,1684019553161465857', 'captchaController', '验证码获取', 'get', '/captcha/get', '/captcha/get', 'captcha:get', 'POST', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1684019553161465857, '2023-07-26 09:55:23', '1000000000', '1000000000', '2023-07-26 09:55:23', 0, 0, 1674580535470510082, '0,1674580535470510082', '验证码', '验证码', '验证码', '0', NULL, NULL, NULL, 0, NULL, '1');
INSERT INTO `sys_permission` VALUES (1688829555168522242, '2023-08-08 16:28:37', '1000000000', NULL, '2023-08-08 16:28:37', 0, 0, 1683755420184776705, '0,1674579089589063681,1683755420184776705', 'sessionInfoDrawController', '绘图会话', 'userLastDrawSession', '/module/session/sessioninfodraw/userLastDrawSession', '/module/session/sessioninfodraw/userLastDrawSession', 'module:session:sessioninfodraw:userLastDrawSession', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1688829555168522243, '2023-08-08 16:28:37', '1000000000', NULL, '2023-08-08 16:28:37', 0, 0, 1674580956448608258, '0,1674578853248421890,1674580956448608258', 'userInfoController', '用户控制器', 'updateUserInfo', '/module/user/userinfo/updateUserInfo', '/module/user/userinfo/updateUserInfo', 'module:user:userinfo:updateUserInfo', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1688829555168522244, '2023-08-08 16:28:37', '1000000000', NULL, '2023-08-08 16:28:37', 0, 0, 1674581645400788994, '0,1674579089589063681,1674581645400788994', 'sessionInfoController', '获取用户会话列表（分页）', 'getUserSessionList', '/module/session/sessioninfo/getUserSessionList', '/module/session/sessioninfo/getUserSessionList', 'module:session:sessioninfo:getUserSessionList', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1688829555168522245, '2023-08-08 16:28:37', '1000000000', NULL, '2023-08-08 16:28:37', 0, 0, 1674580956448608258, '0,1674578853248421890,1674580956448608258', 'userInfoController', '用户控制器', 'queryPageListByParam', '/module/user/userinfo/queryPageListByParam', '/module/user/userinfo/queryPageListByParam', 'module:user:userinfo:queryPageListByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1692413616569528321, '2023-08-18 13:50:24', '1000000000', NULL, '2023-08-18 13:50:24', 0, 0, 1692414232754728962, '0,1692414190664888321,1692414232754728962', 'noticeClientController', '通知公告', 'baseQueryById', '/module/business/noticeclient/baseQueryById/{id}', '/module/business/noticeclient/baseQueryById/*', 'module:business:noticeclient:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1692413616573722625, '2023-08-18 13:50:24', '1000000000', NULL, '2023-08-18 13:50:24', 0, 0, 1692414232754728962, '0,1692414190664888321,1692414232754728962', 'noticeClientController', '通知公告', 'getLastNotice', '/module/business/noticeclient/getLastNotice', '/module/business/noticeclient/getLastNotice', 'module:business:noticeclient:getLastNotice', 'GET', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1692413616573722626, '2023-08-18 13:50:24', '1000000000', NULL, '2023-08-18 13:50:24', 0, 0, 1692414285837840386, '0,1692414190664888321,1692414285837840386', 'openaiKeysAdminController', 'apikeys（后台）', 'baseEdit', '/module/config/admin/openaikeys/baseEdit', '/module/config/admin/openaikeys/baseEdit', 'module:config:admin:openaikeys:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1692413616573722627, '2023-08-18 13:50:24', '1000000000', NULL, '2023-08-18 13:50:24', 0, 0, 1692414232754728962, '0,1692414190664888321,1692414232754728962', 'noticeClientController', '通知公告', 'baseEdit', '/module/business/noticeclient/baseEdit', '/module/business/noticeclient/baseEdit', 'module:business:noticeclient:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1692413616573722628, '2023-08-18 13:50:24', '1000000000', NULL, '2023-08-18 13:50:24', 0, 0, 1692414232754728962, '0,1692414190664888321,1692414232754728962', 'noticeClientController', '通知公告', 'baseDeleteByIds', '/module/business/noticeclient/baseDeleteByIds/{ids}', '/module/business/noticeclient/baseDeleteByIds/*', 'module:business:noticeclient:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1692413616573722629, '2023-08-18 13:50:24', '1000000000', NULL, '2023-08-18 13:50:24', 0, 0, 1692414285837840386, '0,1692414190664888321,1692414285837840386', 'openaiKeysAdminController', 'apikeys（后台）', 'baseQueryPageByParam', '/module/config/admin/openaikeys/baseQueryPageByParam', '/module/config/admin/openaikeys/baseQueryPageByParam', 'module:config:admin:openaikeys:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1692413616573722630, '2023-08-18 13:50:24', '1000000000', NULL, '2023-08-18 13:50:24', 0, 0, 1692414232754728962, '0,1692414190664888321,1692414232754728962', 'noticeClientController', '通知公告', 'baseQueryPageByParam', '/module/business/noticeclient/baseQueryPageByParam', '/module/business/noticeclient/baseQueryPageByParam', 'module:business:noticeclient:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1692413616573722631, '2023-08-18 13:50:24', '1000000000', NULL, '2023-08-18 13:50:24', 0, 0, 1692414285837840386, '0,1692414190664888321,1692414285837840386', 'openaiKeysAdminController', 'apikeys（后台）', 'baseQueryById', '/module/config/admin/openaikeys/baseQueryById/{id}', '/module/config/admin/openaikeys/baseQueryById/*', 'module:config:admin:openaikeys:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1692413616590499842, '2023-08-18 13:50:24', '1000000000', NULL, '2023-08-18 13:50:24', 0, 0, 1692414232754728962, '0,1692414190664888321,1692414232754728962', 'noticeClientController', '通知公告', 'baseQueryByParam', '/module/business/noticeclient/baseQueryByParam', '/module/business/noticeclient/baseQueryByParam', 'module:business:noticeclient:baseQueryByParam', 'POST', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1692413616590499843, '2023-08-18 13:50:24', '1000000000', NULL, '2023-08-18 13:50:24', 0, 0, 1692414232754728962, '0,1692414190664888321,1692414232754728962', 'noticeClientController', '通知公告', 'baseAdd', '/module/business/noticeclient/baseAdd', '/module/business/noticeclient/baseAdd', 'module:business:noticeclient:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1692413616590499844, '2023-08-18 13:50:24', '1000000000', NULL, '2023-08-18 13:50:24', 0, 0, 1692414285837840386, '0,1692414190664888321,1692414285837840386', 'openaiKeysAdminController', 'apikeys（后台）', 'baseAdd', '/module/config/admin/openaikeys/baseAdd', '/module/config/admin/openaikeys/baseAdd', 'module:config:admin:openaikeys:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1692413616590499845, '2023-08-18 13:50:24', '1000000000', NULL, '2023-08-18 13:50:24', 0, 0, 1692414285837840386, '0,1692414190664888321,1692414285837840386', 'openaiKeysAdminController', 'apikeys（后台）', 'baseQueryByParam', '/module/config/admin/openaikeys/baseQueryByParam', '/module/config/admin/openaikeys/baseQueryByParam', 'module:config:admin:openaikeys:baseQueryByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1692413616590499846, '2023-08-18 13:50:24', '1000000000', NULL, '2023-08-18 13:50:24', 0, 0, 1692413675570802689, '0,1692413675570802689', 'interfaceStatistics', '统计web访问量', 'incr', '/common/interface/info', '/common/interface/info', 'common:interface:info', 'GET', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1692413616590499848, '2023-08-18 13:50:24', '1000000000', NULL, '2023-08-18 13:50:24', 0, 0, 1692414285837840386, '0,1692414190664888321,1692414285837840386', 'openaiKeysAdminController', 'apikeys（后台）', 'baseDeleteByIds', '/module/config/admin/openaikeys/baseDeleteByIds/{ids}', '/module/config/admin/openaikeys/baseDeleteByIds/*', 'module:config:admin:openaikeys:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1692413675570802689, '2023-08-18 13:50:38', '1000000000', '1000000000', '2023-08-18 13:50:38', 0, 0, 0, '0', '统计相关', '统计相关', '统计相关', '0', NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `sys_permission` VALUES (1692414190664888321, '2023-08-18 13:52:41', '1000000000', '1000000000', '2023-08-18 13:52:41', 0, 0, 0, '0', '业务模块', '业务模块', '业务模块', '0', NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `sys_permission` VALUES (1692414232754728962, '2023-08-18 13:52:51', '1000000000', '1000000000', '2023-08-18 13:52:51', 0, 0, 1692414190664888321, '0,1692414190664888321', '通知公告', '通知公告', '通知公告', '0', NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `sys_permission` VALUES (1692414285837840386, '2023-08-18 13:53:03', '1000000000', '1000000000', '2023-08-18 13:53:03', 0, 0, 1692414190664888321, '0,1692414190664888321', 'apikeys管理', 'apikeys管理', 'apikeys管理', '0', NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `sys_permission` VALUES (1698628754909769729, '2023-09-04 17:27:08', '1000000000', NULL, '2023-09-04 17:27:08', 0, 0, 1692413675570802689, '0,1692413675570802689', 'statisticsController', '', 'getSettingInfo', '/module/statistics/getSettingInfo', '/module/statistics/getSettingInfo', 'module:statistics:getSettingInfo', 'GET', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1701137324339441665, '2023-09-11 15:35:18', '1000000000', NULL, '2023-09-11 15:35:18', 0, 0, 1683755420184776705, '0,1674579089589063681,1683755420184776705', 'drawTaskController', '绘画控制器', 'userTaskList', '/module/draw/task/userTaskList/{drawType}', '/module/draw/task/userTaskList/*', 'module:draw:task:userTaskList:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1701137324372996098, '2023-09-11 15:35:18', '1000000000', NULL, '2023-09-11 15:35:18', 0, 0, 1683755420184776705, '0,1674579089589063681,1683755420184776705', 'drawTaskController', '绘画控制器', 'getTaskList', '/module/draw/task/getTaskDetail/{drawType}', '/module/draw/task/getTaskDetail/*', 'module:draw:task:getTaskDetail:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1701137324372996099, '2023-09-11 15:35:18', '1000000000', NULL, '2023-09-11 15:35:18', 0, 0, 1683755420184776705, '0,1674579089589063681,1683755420184776705', 'drawTaskController', '绘画控制器', 'createTaskOpenai', '/module/draw/task/createTask/{apiKey}', '/module/draw/task/createTask/*', 'module:draw:task:createTask:*', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1701762581962240001, '2023-09-13 08:59:51', '1000000000', NULL, '2023-09-13 08:59:51', 0, 0, 1674582818715389954, '0,1674580535470510082,1674582818715389954', 'resourceConfigController', '系统参数配置', 'getConfigMain', '/module/system/baseresourceconfig/configMain', '/module/system/baseresourceconfig/configMain', 'module:system:baseresourceconfig:configMain', 'GET', 1, 3, '1');
INSERT INTO `sys_permission` VALUES (1701762581995794433, '2023-09-13 08:59:51', '1000000000', NULL, '2023-09-13 08:59:51', 0, 0, 1683755420184776705, '0,1674579089589063681,1683755420184776705', 'sessionInfoDrawController', '绘图会话', 'getSessionDetail', '/module/session/sessioninfodraw/getSessionDetail/{id}', '/module/session/sessioninfodraw/getSessionDetail/*', 'module:session:sessioninfodraw:getSessionDetail:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1703653248938356738, '2023-09-18 14:12:41', '1000000000', NULL, '2023-09-18 14:12:41', 0, 0, 1674581584893759489, '0,1674579089589063681,1674581584893759489', 'chatController', '聊天会话控制器', 'stopStreamResponse', '/module/chat/stopStreamResponse/{contentId}', '/module/chat/stopStreamResponse/*', 'module:chat:stopStreamResponse:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1703653248955133954, '2023-09-18 14:12:41', '1000000000', NULL, '2023-09-18 14:12:41', 0, 0, 1703653411778015233, '0,1674579089589063681,1703653411778015233', 'sessionRecordDrawController', '绘图会话记录', 'baseEdit', '/module/session/sessionrecorddraw/baseEdit', '/module/session/sessionrecorddraw/baseEdit', 'module:session:sessionrecorddraw:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1703653248955133955, '2023-09-18 14:12:41', '1000000000', NULL, '2023-09-18 14:12:41', 0, 0, 1703653411778015233, '0,1674579089589063681,1703653411778015233', 'sessionRecordDrawController', '绘图会话记录', 'baseAdd', '/module/session/sessionrecorddraw/baseAdd', '/module/session/sessionrecorddraw/baseAdd', 'module:session:sessionrecorddraw:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1703653248955133956, '2023-09-18 14:12:41', '1000000000', NULL, '2023-09-18 14:12:41', 0, 0, 1703653411778015233, '0,1674579089589063681,1703653411778015233', 'sessionRecordDrawController', '绘图会话记录', 'baseQueryByParam', '/module/session/sessionrecorddraw/baseQueryByParam', '/module/session/sessionrecorddraw/baseQueryByParam', 'module:session:sessionrecorddraw:baseQueryByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1703653248955133957, '2023-09-18 14:12:41', '1000000000', NULL, '2023-09-18 14:12:41', 0, 0, 1703653411778015233, '0,1674579089589063681,1703653411778015233', 'sessionRecordDrawController', '绘图会话记录', 'baseQueryById', '/module/session/sessionrecorddraw/baseQueryById/{id}', '/module/session/sessionrecorddraw/baseQueryById/*', 'module:session:sessionrecorddraw:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1703653248955133958, '2023-09-18 14:12:41', '1000000000', NULL, '2023-09-18 14:12:41', 0, 0, 1703653411778015233, '0,1674579089589063681,1703653411778015233', 'sessionRecordDrawController', '绘图会话记录', 'baseDeleteByIds', '/module/session/sessionrecorddraw/baseDeleteByIds/{ids}', '/module/session/sessionrecorddraw/baseDeleteByIds/*', 'module:session:sessionrecorddraw:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1703653248955133959, '2023-09-18 14:12:41', '1000000000', NULL, '2023-09-18 14:12:41', 0, 0, 1703653411778015233, '0,1674579089589063681,1703653411778015233', 'sessionRecordDrawController', '绘图会话记录', 'baseQueryPageByParam', '/module/session/sessionrecorddraw/baseQueryPageByParam', '/module/session/sessionrecorddraw/baseQueryPageByParam', 'module:session:sessionrecorddraw:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1703653248963522562, '2023-09-18 14:12:41', '1000000000', NULL, '2023-09-18 14:12:41', 0, 0, 1674581645400788994, '0,1674579089589063681,1674581645400788994', 'sessionInfoController', '会话表', 'baseQueryPageByParam', '/module/session/sessioninfo/baseQueryPageByParam', '/module/session/sessioninfo/baseQueryPageByParam', 'module:session:sessioninfo:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1703653411778015233, '2023-09-18 14:13:20', '1000000000', '1000000000', '2023-09-18 14:13:20', 0, 0, 1674579089589063681, '0,1674579089589063681', '绘图会话记录', '绘图会话记录', '绘图会话记录', '0', NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `sys_permission` VALUES (1703702326275551234, '2023-09-18 17:27:42', '1000000000', NULL, '2023-09-18 17:27:42', 0, 0, 1674579708391510017, '0,1674578906511888386,1674579708391510017', 'commonController', '通用控制器', 'uploadHeadImage', '/common/uploadHeadImage', '/common/uploadHeadImage', 'common:uploadHeadImage', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1703955969524445186, '2023-09-19 10:15:35', '1000000000', NULL, '2023-09-19 10:15:35', 0, 0, 1683755420184776705, '0,1674579089589063681,1683755420184776705', 'drawTaskController', '绘画控制器', 'baseQueryPageByParam', '/module/draw/task/baseQueryPageByParam', '/module/draw/task/baseQueryPageByParam', 'module:draw:task:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1710498047507116033, '2023-10-07 11:31:28', '1000000000', NULL, '2023-10-07 11:31:28', 0, 0, 1710498762187157506, '0,1692414190664888321,1710498762187157506', 'sysMinioSecretController', '菜单权限', 'baseEdit', '/module/system/sysminiosecret/baseEdit', '/module/system/sysminiosecret/baseEdit', 'module:system:sysminiosecret:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1710498047553253377, '2023-10-07 11:31:28', '1000000000', NULL, '2023-10-07 11:31:28', 0, 0, 1710498485581197313, '0,1692414190664888321,1710498485581197313', 'cmjAccountController', 'mj账户配置', 'baseDeleteByIds', '/module/cmj/cmjaccount/baseDeleteByIds/{ids}', '/module/cmj/cmjaccount/baseDeleteByIds/*', 'module:cmj:cmjaccount:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1710498047553253378, '2023-10-07 11:31:28', '1000000000', NULL, '2023-10-07 11:31:28', 0, 0, 1710498762187157506, '0,1692414190664888321,1710498762187157506', 'sysMinioSecretController', '菜单权限', 'baseDeleteByIds', '/module/system/sysminiosecret/baseDeleteByIds/{ids}', '/module/system/sysminiosecret/baseDeleteByIds/*', 'module:system:sysminiosecret:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1710498047553253379, '2023-10-07 11:31:28', '1000000000', NULL, '2023-10-07 11:31:28', 0, 0, 1710498485581197313, '0,1692414190664888321,1710498485581197313', 'cmjChannelController', 'mj频道配置', 'baseDeleteByIds', '/module/cmj/cmjchannelconfig/baseDeleteByIds/{ids}', '/module/cmj/cmjchannelconfig/baseDeleteByIds/*', 'module:cmj:cmjchannelconfig:baseDeleteByIds:*', 'DELETE', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1710498047561641986, '2023-10-07 11:31:28', '1000000000', NULL, '2023-10-07 11:31:28', 0, 0, 1710498485581197313, '0,1692414190664888321,1710498485581197313', 'cmjChannelController', 'mj频道配置', 'baseQueryById', '/module/cmj/cmjchannelconfig/baseQueryById/{id}', '/module/cmj/cmjchannelconfig/baseQueryById/*', 'module:cmj:cmjchannelconfig:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1710498047561641987, '2023-10-07 11:31:28', '1000000000', NULL, '2023-10-07 11:31:28', 0, 0, 1710498485581197313, '0,1692414190664888321,1710498485581197313', 'cmjAccountController', 'mj账户配置', 'baseEdit', '/module/cmj/cmjaccount/baseEdit', '/module/cmj/cmjaccount/baseEdit', 'module:cmj:cmjaccount:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1710498047561641988, '2023-10-07 11:31:28', '1000000000', NULL, '2023-10-07 11:31:28', 0, 0, 1710498485581197313, '0,1692414190664888321,1710498485581197313', 'cmjAccountController', 'mj账户配置', 'baseQueryPageByParam', '/module/cmj/cmjaccount/baseQueryPageByParam', '/module/cmj/cmjaccount/baseQueryPageByParam', 'module:cmj:cmjaccount:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1710498047561641989, '2023-10-07 11:31:28', '1000000000', NULL, '2023-10-07 11:31:28', 0, 0, 1710498762187157506, '0,1692414190664888321,1710498762187157506', 'sysMinioSecretController', '菜单权限', 'baseQueryByParam', '/module/system/sysminiosecret/baseQueryByParam', '/module/system/sysminiosecret/baseQueryByParam', 'module:system:sysminiosecret:baseQueryByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1710498047574224898, '2023-10-07 11:31:28', '1000000000', NULL, '2023-10-07 11:31:28', 0, 0, 1710498485581197313, '0,1692414190664888321,1710498485581197313', 'cmjAccountController', 'mj账户配置', 'baseQueryById', '/module/cmj/cmjaccount/baseQueryById/{id}', '/module/cmj/cmjaccount/baseQueryById/*', 'module:cmj:cmjaccount:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1710498047574224899, '2023-10-07 11:31:28', '1000000000', NULL, '2023-10-07 11:31:28', 0, 0, 1710498485581197313, '0,1692414190664888321,1710498485581197313', 'cmjChannelController', 'mj频道配置', 'baseQueryByParam', '/module/cmj/cmjchannelconfig/baseQueryByParam', '/module/cmj/cmjchannelconfig/baseQueryByParam', 'module:cmj:cmjchannelconfig:baseQueryByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1710498047595196418, '2023-10-07 11:31:28', '1000000000', NULL, '2023-10-07 11:31:28', 0, 0, 1710498485581197313, '0,1692414190664888321,1710498485581197313', 'cmjAccountController', 'mj账户配置', 'againConnect', '/module/cmj/cmjaccount/againConnect/{id}', '/module/cmj/cmjaccount/againConnect/*', 'module:cmj:cmjaccount:againConnect:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1710498047595196419, '2023-10-07 11:31:28', '1000000000', NULL, '2023-10-07 11:31:28', 0, 0, 1710498485581197313, '0,1692414190664888321,1710498485581197313', 'cmjChannelController', 'mj频道配置', 'baseAdd', '/module/cmj/cmjchannelconfig/baseAdd', '/module/cmj/cmjchannelconfig/baseAdd', 'module:cmj:cmjchannelconfig:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1710498047595196420, '2023-10-07 11:31:28', '1000000000', NULL, '2023-10-07 11:31:28', 0, 0, 1710498762187157506, '0,1692414190664888321,1710498762187157506', 'sysMinioSecretController', '菜单权限', 'baseQueryById', '/module/system/sysminiosecret/baseQueryById/{id}', '/module/system/sysminiosecret/baseQueryById/*', 'module:system:sysminiosecret:baseQueryById:*', 'GET', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1710498047611973633, '2023-10-07 11:31:28', '1000000000', NULL, '2023-10-07 11:31:28', 0, 0, 1710498485581197313, '0,1692414190664888321,1710498485581197313', 'cmjAccountController', 'mj账户配置', 'baseAdd', '/module/cmj/cmjaccount/baseAdd', '/module/cmj/cmjaccount/baseAdd', 'module:cmj:cmjaccount:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1710498047611973634, '2023-10-07 11:31:28', '1000000000', NULL, '2023-10-07 11:31:28', 0, 0, 1710498485581197313, '0,1692414190664888321,1710498485581197313', 'cmjChannelController', 'mj频道配置', 'baseQueryPageByParam', '/module/cmj/cmjchannelconfig/baseQueryPageByParam', '/module/cmj/cmjchannelconfig/baseQueryPageByParam', 'module:cmj:cmjchannelconfig:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1710498047611973635, '2023-10-07 11:31:28', '1000000000', NULL, '2023-10-07 11:31:28', 0, 0, 1710498762187157506, '0,1692414190664888321,1710498762187157506', 'sysMinioSecretController', '菜单权限', 'baseQueryPageByParam', '/module/system/sysminiosecret/baseQueryPageByParam', '/module/system/sysminiosecret/baseQueryPageByParam', 'module:system:sysminiosecret:baseQueryPageByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1710498047624556545, '2023-10-07 11:31:28', '1000000000', NULL, '2023-10-07 11:31:28', 0, 0, 1710498485581197313, '0,1692414190664888321,1710498485581197313', 'cmjChannelController', 'mj频道配置', 'baseEdit', '/module/cmj/cmjchannelconfig/baseEdit', '/module/cmj/cmjchannelconfig/baseEdit', 'module:cmj:cmjchannelconfig:baseEdit', 'PUT', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1710498047624556546, '2023-10-07 11:31:28', '1000000000', NULL, '2023-10-07 11:31:28', 0, 0, 1710498762187157506, '0,1692414190664888321,1710498762187157506', 'sysMinioSecretController', '菜单权限', 'baseAdd', '/module/system/sysminiosecret/baseAdd', '/module/system/sysminiosecret/baseAdd', 'module:system:sysminiosecret:baseAdd', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1710498047624556547, '2023-10-07 11:31:28', '1000000000', NULL, '2023-10-07 11:31:28', 0, 0, 1710498485581197313, '0,1692414190664888321,1710498485581197313', 'cmjAccountController', 'mj账户配置', 'baseQueryByParam', '/module/cmj/cmjaccount/baseQueryByParam', '/module/cmj/cmjaccount/baseQueryByParam', 'module:cmj:cmjaccount:baseQueryByParam', 'POST', 1, 1, '1');
INSERT INTO `sys_permission` VALUES (1710498485581197313, '2023-10-07 11:33:13', '1000000000', '1000000000', '2023-10-07 11:33:13', 0, 0, 1692414190664888321, '0,1692414190664888321', 'mj配置', 'mj配置', 'mj配置', '0', NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `sys_permission` VALUES (1710498762187157506, '2023-10-07 11:34:19', '1000000000', '1000000000', '2023-10-07 11:34:19', 0, 0, 1692414190664888321, '0,1692414190664888321', 'minio配置', 'minio配置', 'minio配置', '0', NULL, NULL, NULL, 0, NULL, NULL);

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

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
  `update_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `create_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标识 0 未删除 1 已删除',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  `permission_id` bigint NOT NULL COMMENT '权限ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和权限关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (1701915810800939009, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578853248421890, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810800939010, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674579182287376385, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810800939011, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674580927755374593, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810817716226, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674580956448608258, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810817716227, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578906511888386, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810817716228, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674579841480970241, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810817716229, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674579089589063681, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810817716230, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674581645400788994, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810817716231, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674583564886265858, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810817716232, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674579114499035137, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810817716233, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674581814276050945, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810817716234, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674581849239769090, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810817716235, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674581892462071809, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810830299137, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674579384591241218, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810830299138, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674582439277678594, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810830299139, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674582466536460289, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810851270658, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674582491907805185, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810851270659, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674582505417658369, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810851270660, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674580535470510082, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810851270661, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674582790324146177, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810851270662, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674582818715389954, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810851270663, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674582856791281665, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810851270664, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1676790579284992001, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810851270665, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1692414190664888321, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810851270666, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1692414232754728962, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810851270667, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1692414285837840386, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810868047873, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728828588077, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810868047874, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578729612922894, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810868047875, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578729663254538, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810868047876, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728694370307, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810868047877, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728828588047, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810868047878, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728828588076, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810868047879, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578729612922903, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810868047880, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578729612922914, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810876436482, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1676790618824695810, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810876436483, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1688829555168522245, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810876436484, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728761479187, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810876436485, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728828588039, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810876436486, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728828588052, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810876436487, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728828588055, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810876436488, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578729663254531, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810876436489, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578729663254532, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810918379522, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674579708391510017, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810918379523, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728828588058, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810918379524, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674622533179109377, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810918379525, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674622533200080898, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810918379526, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1675013065540587522, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810918379527, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728761479183, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810918379528, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728828588085, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810918379529, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578729612922910, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810918379530, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728761479209, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810918379531, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578729612922892, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810918379532, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1683755122808623117, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810935156737, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1688829555168522244, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810935156738, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674583544443228161, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810935156739, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728761479194, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810947739650, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728828588074, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810947739651, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728761479184, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810947739652, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728761479208, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810947739653, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728828588046, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810947739654, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728694370311, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810947739655, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728761479171, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810947739656, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728694370310, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810947739657, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728828588036, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810985488385, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728828588040, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810985488386, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728761479188, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810985488387, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578729529036807, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810985488388, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578729612922908, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810985488389, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728761479181, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810985488390, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728828588063, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810985488391, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728761479173, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810998071297, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728761479174, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810998071298, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728761479189, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810998071299, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728828588034, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810998071300, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728828588080, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810998071301, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578729529036803, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810998071302, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578729612922915, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810998071303, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728694370305, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810998071304, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728761479175, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810998071305, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728761479200, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810998071306, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728828588043, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915810998071307, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728828588045, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811014848513, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578729612922883, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811014848514, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578729663254535, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811014848515, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728761479176, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811019042818, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728828588042, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811019042819, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578729461927938, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811019042820, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578729663254533, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811019042821, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578729663254541, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811019042822, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728761479177, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811019042823, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728761479201, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811019042824, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728761479204, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811027431426, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728828588071, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811027431427, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578729529036809, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811027431428, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578729612922891, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811048402946, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578729612922896, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811048402947, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578729663254534, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811048402948, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578729663254536, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811048402949, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1674578728828588057, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811048402950, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1701762581962240001, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811165843457, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1683755122808623115, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811165843458, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1675793439851036674, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811165843459, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1675793439851036677, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811165843460, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1675793439851036680, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811174232066, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1684019553161465857, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811174232067, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1683755122808623107, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811174232068, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1684018183868010497, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811174232069, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1692413675570802689, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811174232070, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1692413616590499846, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811174232071, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1698628754909769729, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811182620673, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1692413616569528321, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811191009282, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1692413616573722625, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811191009283, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1692413616573722630, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811191009284, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1692413616590499842, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811191009285, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1692413616573722629, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811191009286, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1692413616573722631, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1701915811195203585, '2023-09-13 19:08:44', '1000000000', NULL, '2023-09-13 19:08:44', 0, 0, 1692413616590499845, 1701914767149051905);
INSERT INTO `sys_role_permission` VALUES (1703702786633969665, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674578853248421890, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786633969666, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674580956448608258, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786633969667, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674578906511888386, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786633969668, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674579708391510017, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786633969669, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674579089589063681, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786633969670, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674583564886265858, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786633969671, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1683755420184776705, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786633969672, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674580535470510082, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786633969673, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674582790324146177, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786633969674, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674582818715389954, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786633969675, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1692413675570802689, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786633969676, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1692414190664888321, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786659135490, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1692414232754728962, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786659135491, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674578728761479193, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786659135492, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674578728761479197, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786659135493, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674578729612922884, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786659135494, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674578729612922914, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786659135495, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1675793439851036676, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786659135496, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1676790618824695810, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786659135497, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1677188224147001346, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786659135498, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1683755122808623110, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786659135499, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674578728828588058, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786659135500, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674622533200080898, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786659135501, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1675013065540587522, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786659135502, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1703702326275551234, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786659135503, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674581584893759489, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786659135504, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1676790618824695811, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786659135505, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1683755122808623112, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786659135506, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1683755122808623114, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786659135507, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1703653248938356738, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786684301314, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674581645400788994, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786684301315, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674578728761479172, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786684301316, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674578728761479209, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786684301317, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674578728828588070, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786684301318, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674578728828588082, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786692689922, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674578729612922892, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786692689923, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674578729612922904, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786692689924, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674578729612922905, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786692689925, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1683755122808623105, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786692689926, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1683755122808623117, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786692689927, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1688829555168522244, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786692689928, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1703653248963522562, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786692689929, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674583544443228161, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786692689930, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674578728761479194, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786692689931, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674578728828588074, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786692689932, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674578728761479208, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786705272833, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674578728828588037, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786705272834, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674578728828588046, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786705272835, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1688829555168522242, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786705272836, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1701137324339441665, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786705272837, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1701137324372996098, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786705272838, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1701137324372996099, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786705272839, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1701762581995794433, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786705272840, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674578729612922891, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786705272841, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674578729663254536, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786705272842, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1701762581962240001, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786705272843, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1674582856791281665, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786705272844, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1683755122745708546, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786717855745, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1683755122808623106, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786717855746, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1683755122808623115, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786717855747, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1684019553161465857, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786717855748, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1683755122808623107, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786717855749, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1684018183868010497, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786717855750, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1692413616590499846, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786717855751, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1692413616569528321, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786717855752, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1692413616573722625, 53814717356965893);
INSERT INTO `sys_role_permission` VALUES (1703702786717855753, '2023-09-18 17:29:32', '1000000000', NULL, '2023-09-18 17:29:32', 0, 0, 1692413616590499842, 53814717356965893);

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '登录人token信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_token
-- ----------------------------
INSERT INTO `sys_user_token` VALUES (1711213462388011010, '2023-10-09 10:54:16', NULL, '2023-10-09 10:54:16', NULL, 0, 0, 1000000000, '728a333e-cf5f-4220-bb01-02c4c60749b0', '2023-10-09 10:54:16');

-- ----------------------------
-- Table structure for tb_cmj_account
-- ----------------------------
DROP TABLE IF EXISTS `tb_cmj_account`;
CREATE TABLE `tb_cmj_account`  (
  `id` bigint NOT NULL COMMENT '主键',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `create_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标识 0 未删除 1 已删除',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  `user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账户名',
  `user_token` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'token',
  `user_agent` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ua',
  `data_object` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'dataObject',
  `account_status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '帐号状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'mj账户配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_cmj_account
-- ----------------------------

-- ----------------------------
-- Table structure for tb_cmj_channel_config
-- ----------------------------
DROP TABLE IF EXISTS `tb_cmj_channel_config`;
CREATE TABLE `tb_cmj_channel_config`  (
  `id` bigint NOT NULL COMMENT '主键',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `create_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标识 0 未删除 1 已删除',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  `cmj_account_id` bigint NOT NULL COMMENT '关联账户',
  `guild_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '服务器id',
  `channel_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '频道id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'mj频道配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_cmj_channel_config
-- ----------------------------

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '领域会话' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_domain
-- ----------------------------
INSERT INTO `tb_domain` VALUES (1, NULL, NULL, '2023-08-09 16:39:18', '1000000000', 0, 0, 'ad', 'I want you to advertise. You will create a campaign to promote the product or service of your choice. You will select a target audience, develop key messages and slogans, choose media channels to promote, and decide on any other activities needed to achieve your goals. Please reply to the following template with no other content,Using markdown output,Answer in Chinese:[\n\n我将为你的(广告标题)创建一个促销活动！\n目标受众:\n- xxxxx\n关键信息:\n1. xxxxx\n口号:\n- xxxx\n媒体渠道:\n1. xxxxx\n额外的活动:\n1. xxxxx\n\n宣传结语：xxx\n]  \nThe first AD I want you to make is:', 'COMMON', 1, NULL, '/sessionDomain/ad', '广告创意', '/image/2023/08/26/27c6adfd225b43d3ba12983e6e398681.png?e=1693060491&token=y47kvl-NkTt0IHtqKZ2fNJilbsyhW9L16WKdp5JC:zd-dC6KSkrtn0yjMmrgao6xuIRY=', '{\"title\":\"广告创意助手\",\"sessionType\":\"DOMAIN\",\"description\":\"它可以编写各种类型的广告文案，包括产品广告、服务广告、品牌广告、活动宣传等。\",\"contentShowType\":\"Markdown\",\"inputType\":\"Input\"}', '0', '1', '我是广告创意文案助手，可以编写各种类型的广告文案，包括产品广告、服务广告、品牌广告、活动宣传等。它可以为你编写具有吸引力、清晰明了的广告文案，让你的目标受众更容易接受你的产品或服务嗷~', 'gpt-3.5-turbo', '1');
INSERT INTO `tb_domain` VALUES (1673942163144056834, '2023-06-28 14:31:26', '1000000000', '2023-08-17 16:02:44', '1000000000', 0, 0, 'etranslateChinese', 'Below, I will ask you to act as a translator. Your goal is to translate any language into Chinese,Do not output pronunciation letters, only output Chinese. Please do not use a translation accent when translating, but rather translate naturally, fluently, and authentically, using beautiful and elegant expressions. The first sentence I need to translate: ', 'COMMON', 2, NULL, '/sessionDomain/etranslateChinese', '英文翻译官', '/image/2023/08/26/e7037c7a9a46435a981c46c007d76982.png?e=1693060510&token=y47kvl-NkTt0IHtqKZ2fNJilbsyhW9L16WKdp5JC:qmOTlZc9_Mn9RhyzLarZTsRkbLE=', '{\"title\":\"英文翻译官\",\"sessionType\":\"DOMAIN\",\"description\":\"英文翻译成中文\",\"contentShowType\":\"Markdown\",\"inputType\":\"Input\"}', '0', '1', '我是一个英文翻译官，可以进行英语和中文之间的翻译工作，包括但不限于学术文献、商业文档、网站内容、软件界面等。可以保证翻译的准确性和专业性。', 'gpt-3.5-turbo', '0');
INSERT INTO `tb_domain` VALUES (1673993963171753986, '2023-06-28 17:57:16', '1000000000', '2023-08-03 10:34:48', '1000000000', 0, 0, 'svgdesign', 'I hope you become an SVG designer. I will ask you to create an image and provide it with SVG code. Place the result marker in the code block. Only output SVG tag code, do not output other text descriptions or descriptions, nor output base64 data Url. My first requirement is:', 'COMMON', 3, NULL, '/sessionDomain/svgdesign', 'svg设计师', '/image/2023/08/26/837d583768c24a10a7f686667c5f37c7.png?e=1693060519&token=y47kvl-NkTt0IHtqKZ2fNJilbsyhW9L16WKdp5JC:L4eQbYQPMVDRmW6hM1FDgKF7Iok=', '{\"title\":\"SVG设计师\",\"sessionType\":\"DOMAIN\",\"description\":\"svg设计师\",\"contentShowType\":\"Html\",\"inputType\":\"Input\"}', '0', '1', '我是一个 SVG 设计师，可以对我说设计需求，我会根据你的需求来设计svg~💪，例如：画一个红色边框空心五角星', 'gpt-3.5-turbo', '1');
INSERT INTO `tb_domain` VALUES (1682289504188231682, '2023-07-21 15:20:47', '1000000000', '2023-08-03 10:35:02', '1000000000', 0, 0, 'minRedCopyWritting', 'You are a copywriting expert in Xiaohongshu. Based on the characteristics of Xiaohongshu\'s copywriting style, please help me generate a copy based on the template I provided.\n\nTemplate:\n[Title]\n[Main text]\n\nRequirements:\n1. There should be 2 emoji emojis before and after the title content. Based on my input, I can infer the type of copy, such as (\"xxx Scenic Spots\"), and the title content is: Xiaohongshu Scenic Spots Recommendation\n2. The Writing style of the text is peaceful. Please use a cute title at the beginning of the text. Judge the main audience according to the content, such as \"dear fairies\", \"dear little brothers\", etc. Do not repeat.\n3. Please introduce the main content of the text based on the characteristics of Xiaohongshu\'s writing. If it is possible to describe the content one by one, please output emoji emoji with a serial number before each description, otherwise there is no need to output emoji emoji emoji\n4. After each paragraph of the main text, please output 2 emoji emojis that match the content and wrap them\n5. The output language is based on the language of the input content, with default Chinese output\n6. emoji, please do not be too repetitive\n\nPlease strictly follow the template and content above for output. The first copy I need to generate is:', 'COMMON', 4, NULL, '/sessionDomain/minRedCopyWritting', '小红书文案', '/image/2023/08/26/23c35e277eb54eb5b8491a4ef73fa635.png?e=1693060528&token=y47kvl-NkTt0IHtqKZ2fNJilbsyhW9L16WKdp5JC:pgbhZsbGGmNhUl-7_NB0EPzNS8c=', '{\"title\":\"小红书文案创作\",\"sessionType\":\"DOMAIN\",\"inputType\":\"Input\",\"contentShowType\":\"Markdown\",\"description\":\"小红书文案创作助手，带你玩转小红书社区~\"}', '0', '1', '我是小红书文案创作助手，您可以对说，例如： 口红色号安利、智能家居推荐等。', 'gpt-3.5-turbo', '1');
INSERT INTO `tb_domain` VALUES (1692066780366766082, '2023-08-17 14:52:12', '1000000000', '2023-08-17 15:10:11', '1000000000', 0, 0, 'travelHelper', 'I want you to make a travel guide. I will write down my location for you, and you will recommend a place close to my location. In some cases, I will also tell you the type of place I will visit. You will also recommend a similar type of place close to my first location. Please output according to my language. My first request for advice is：', 'COMMON', 5, NULL, '/sessionDomain/travelHelper', '旅游指南助手', '/image/2023/08/26/4cdc6461060744a38c6816df2fd37f58.png?e=1693060540&token=y47kvl-NkTt0IHtqKZ2fNJilbsyhW9L16WKdp5JC:w1w8B7OKFjX0yYYSwRT98Yi9dis=', '{\"title\":\"旅游指南\",\"sessionType\":\"DOMAIN\",\"inputType\":\"Input\",\"contentShowType\":\"Markdown\",\"description\":\"我是旅游指南助手，我会根据你的诉求来为你提供合适的旅游路线。\"}', '0', '1', '我是旅游指南助手，我会根据你的诉求来为你提供合适的旅游路线，例如你可以问我：我在上海，我只想参观博物馆', 'gpt-3.5-turbo', '0');
INSERT INTO `tb_domain` VALUES (1692069585513086977, '2023-08-17 15:03:20', '1000000000', '2023-08-17 15:28:14', '1000000000', 0, 0, 'xiaoshuojia', 'I want you to play a novelist. You will come up with creative and engaging stories that can attract readers in the long run. You can choose any type, such as fantasy, romance, historical novels, etc. But your goal is to write works with excellent plot, captivating characters, and unexpected climaxes.Please output according to my language.  My first requirement is:', 'COMMON', 6, NULL, '/sessionDomain/xiaoshuojia', '小说家', '/image/2023/08/26/93ccf77d96e144f784135fa7b789a037.png?e=1693060551&token=y47kvl-NkTt0IHtqKZ2fNJilbsyhW9L16WKdp5JC:TNA9nBW0Qjk2jNgoMrTMta4CtQ0=', '{\"title\":\"小说家\",\"sessionType\":\"DOMAIN\",\"inputType\":\"Input\",\"contentShowType\":\"Markdown\",\"description\":\"我是一个小说家，能够创造出富有创意、引人入胜的故事\"}', '0', '1', '我是一个小说家，可以根据你的要求创造出富有创意、引人入胜的故事。例如，你可以对我说：我要写一部以未来为背景的科幻小说', 'gpt-3.5-turbo', '1');
INSERT INTO `tb_domain` VALUES (1692075020353798145, '2023-08-17 15:24:56', '1000000000', '2023-08-17 15:28:28', '1000000000', 0, 0, 'QGDS', 'I want you to serve as a relationship coach. I will provide some details about the two individuals involved in the conflict, and your job is to provide suggestions on how they can solve the problem that caused their separation. This may include suggestions on communication skills or different strategies to improve their understanding of each other\'s perspectives.Please output according to my language.  My first request is:', 'COMMON', 7, NULL, '/sessionDomain/QGDS', '情感大师', '/image/2023/08/26/4a385a8ca48f41fea2c622bda4eb37be.png?e=1693060558&token=y47kvl-NkTt0IHtqKZ2fNJilbsyhW9L16WKdp5JC:Xq6md7Vw70KLnYI5KuRxtAZkcpg=', '{\"title\":\"情感大师\",\"sessionType\":\"DOMAIN\",\"contentShowType\":\"Markdown\",\"inputType\":\"Input\",\"description\":\"我是一个专门处理人际关系的专家，提供有关冲突的信息与细节，我会给出沟通技巧与建议\"}', '0', '1', '我是一个专门处理人际关系的专家，提供有关冲突的信息与细节，我会给出沟通技巧与建议。例如，你可以对我说：我需要帮助解决我和配偶之间的冲突', 'gpt-3.5-turbo', '1');
INSERT INTO `tb_domain` VALUES (1701158843419004930, '2023-09-11 17:00:48', '1000000000', '2023-09-11 17:06:45', '1000000000', 0, 0, 'SD-tips', '你是一个Stable Diffusion提示词生成器。\n\nStable Diffusion是一种基于文本的AI图像生成模型，可以根据用户给出的文本提示词来生成相应的图像。你需要在编写提示词时遵循下面这些准则：\n\n1）尽可能详细和具体。Stable Diffusion处理具体提示比抽象或模糊的提示更好。例如，与其写“一位女性的肖像”，不如写“文艺复兴风格的红发棕眼女性肖像”。\n2）指定特定的艺术风格或笔触。如果你想获得特定风格或特定纹理的图像，应在请求中明确的指定。例如，与其写“风景”，不如写“水彩画风格的山和湖水的风景”。\n\n3）指定参考艺术家。如果你想获得类似于某位艺术家作品的图像，应在请求中指定他的名字。例如，与其写“抽象图像”，不如写“像毕加索风格的抽象画”。\n\n4）关键字的权重。你可以使用“关键字：数字”的格式来指定提示词中某个关键字的权重。关键字的权重越大，它对结果的影响就越大。例如，如果你想获得一张有绿眼睛和粉鼻子的猫的图像，那么你可以写“猫：1.5，绿眼睛：1.3，粉鼻子：1”。这意味着猫将是图像中最重要的元素，绿眼睛的重要性较小，粉鼻子的重要性最小。\n\n另一种调整关键字权重大小的方法是使用符号（）和[]。 （关键字）会将关键字的强度增加1.1倍，相当于（关键字：1.1）。 [关键字]将关键字的强度减少0.9倍，相当于（关键字：0.9）。你可以使用多个符合，效果就是乘法。如：（关键字）：1.1，（（关键字））：1.21，（（（关键字）））：1.33\n\n同样，使用多个[]的效果如下：[关键字]：0.9，[[关键字]]：0.81，[[[关键字]]]：0.73\n\n下面是一些提示词的示例：\n\n示例1：a painting of a woman in medieval knight armor with a castle in the background and clouds in the sky behind her, (impressionism:1.1), (\'rough painting style\':1.5), (\'large brush texture\':1.2), (\'palette knife\':1.2), (dabbing:1.4), (\'highly detailed\':1.5), professional majestic painting by Vasily Surikov, Victor Vasnetsov, (Konstantin Makovsky:1.3), trending on ArtStation, trending on CGSociety, Intricate, High Detail, Sharp focus, dramatic\n\n示例2：Jane Eyre with headphones, natural skin texture, 24mm, 4k textures, soft cinematic light, adobe lightroom, photolab, hdr, intricate, elegant, highly detailed, sharp focus, ((((cinematic look)))), soothing tones, insane details, intricate details, hyperdetailed, low contrast, soft cinematic light, dim colors, exposure blend, hdr, faded\n\n示例4：(8k, RAW photo, highest quality), beautiful girl, close up, t-shirt, (detailed eyes:0.8), (looking at the camera:1.4), (highest quality), (best shadow), intricate details, interior, (ponytail, ginger hair:1.3), dark studio, muted colors, freckles\n\n每当我列出一个主题，你就写出1个关于主题的详细提示词，只写出提示词，与提示词无关的不需要，记得遵循上述规则，用英语编写，不需要翻译成中文。\n\n主题：', 'COMMON', 8, NULL, '/sessionDomain/SD-tips', 'SD prompt优化专家', '/image/2023/09/11/e1924530ee2341858200b73c4ee34b62.png?e=1694459000&token=y47kvl-NkTt0IHtqKZ2fNJilbsyhW9L16WKdp5JC:Ko1fPXyc_-_Bv1aij0Lh7lCtudU=', '{\"sessionType\":\"DOMAIN\",\"inputType\":\"Input\",\"contentShowType\":\"Markdown\",\"description\":\"我是Stable Diffusion提示词优化专家，给我一段场景描述，我来为你生成提示词~~如可以对我说：一个金发女孩微笑的在沙滩上奔跑\",\"title\":\"Stable Diffustion优化专家\"}', '2', '1', '我是Stable Diffusion提示词优化专家，给我一段场景描述，我来为你生成提示词~~如可以对我说：一个金发女孩微笑的在沙滩上奔跑', 'gpt-3.5-turbo', '0');

-- ----------------------------
-- Table structure for tb_notice_client
-- ----------------------------
DROP TABLE IF EXISTS `tb_notice_client`;
CREATE TABLE `tb_notice_client`  (
  `id` bigint NOT NULL COMMENT '主键',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `update_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标识 0 未删除 1 已删除',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '通知标题',
  `notice_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通知类型',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `if_show` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否开启',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '通告信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_notice_client
-- ----------------------------

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'apikeys' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_openai_keys
-- ----------------------------
INSERT INTO `tb_openai_keys` VALUES (1, NULL, NULL, NULL, NULL, 1, 0, 'xxxx', 'test', NULL, NULL, NULL, '1', NULL, '0');

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会话表' ROW_FORMAT = Dynamic;

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
  `create_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `update_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标识 0 未删除 1 已删除',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  `prompt` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'prompt',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `task_id` bigint NULL DEFAULT NULL COMMENT '任务id',
  `draw_unique_key` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '绘画接口类型唯一表示（DrawType）',
  `draw_api_key` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '绘图接口标识',
  `session_num` int NULL DEFAULT NULL COMMENT '用户会话号',
  `show_img` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '展示图',
  `sd_response_info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'sd info响应参数json',
  `original_img_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '原图地址url',
  `base_img` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '底图',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '绘图会话' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会话详情' ROW_FORMAT = Dynamic;

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
  `create_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `update_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标识 0 未删除 1 已删除',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  `session_info_draw_id` bigint NULL DEFAULT NULL COMMENT '绘图会话id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `draw_unique_key` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '绘图接口类型唯一标识（DrawType）',
  `draw_api_key` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '绘图接口标识',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `draw_base64_img` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '图片base64内容',
  `draw_img_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片路径',
  `prompt` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '输入prompt',
  `if_common` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否公开',
  `assistant_img1` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '副图1',
  `assistant_img2` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '副图2',
  `original_img_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '原图地址url',
  `mj_extend_param` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'mj扩展参数',
  `mj_image_index` int NULL DEFAULT NULL COMMENT 'mj u v下标',
  `task_id` bigint NULL DEFAULT NULL COMMENT '任务id',
  `base_img` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '底图',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '图像会话详情' ROW_FORMAT = Dynamic;

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
  `create_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `update_oper` varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标识 0 未删除 1 已删除',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  `draw_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '绘图任务类型',
  `draw_api_key` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '绘图接口标识',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `task_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务状态',
  `session_info_draw_id` bigint NULL DEFAULT NULL COMMENT '绘图会话id',
  `task_end_time` datetime NULL DEFAULT NULL COMMENT '任务结束时间',
  `request_param` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '任务请求参数',
  `show_img` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '展示图',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '绘图任务列表' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_info
-- ----------------------------
INSERT INTO `tb_user_info` VALUES (1665958959871291394, '2023-06-06 13:49:02', NULL, '2023-07-27 17:57:39', '1665958959871291394', 0, 0, 'test', '$2a$10$f3z0krpEzkHbO6AUAADCnuMC9GwO3JbotZ7SrFzOAnW2BX1dJ2Pmq', 'test', '0', '/image/2023/07/27/185216cdebcd4d1eac8c0483dc8f8ecd.jpeg?e=1690487857&token=y47kvl-NkTt0IHtqKZ2fNJilbsyhW9L16WKdp5JC:kFkjTavTPy9EpwQ9u80m2lQlWpY=', '0', '0', NULL, NULL, NULL, NULL, '/image/2023/07/27/fb5bb74b4d35443c931ea2f333ebaf6b.jpeg?e=1690488122&token=y47kvl-NkTt0IHtqKZ2fNJilbsyhW9L16WKdp5JC:vHXMGHdwOWegqFEr12FFHyGGHrs=', NULL);
INSERT INTO `tb_user_info` VALUES (1708404175232114690, '2023-10-01 16:51:10', NULL, '2023-10-01 16:51:10', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '0', '1', '127.0.0.1', NULL, NULL, NULL, NULL, '内网IP');

SET FOREIGN_KEY_CHECKS = 1;
