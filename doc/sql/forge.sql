/*
PostgreSQL Backup
Database: forge_dev/public
Backup Time: 2023-07-11 18:01:40
*/

DROP SEQUENCE IF EXISTS "public"."t_category_attribute_id_seq";
DROP SEQUENCE IF EXISTS "public"."t_category_attribute_value_id_seq";
DROP SEQUENCE IF EXISTS "public"."t_category_id_seq";
DROP SEQUENCE IF EXISTS "public"."t_dept_id_seq";
DROP SEQUENCE IF EXISTS "public"."t_login_log_id_seq";
DROP SEQUENCE IF EXISTS "public"."t_operate_log_id_seq";
DROP SEQUENCE IF EXISTS "public"."t_permission_id_seq";
DROP SEQUENCE IF EXISTS "public"."t_role_id_seq";
DROP SEQUENCE IF EXISTS "public"."t_role_permission_id_seq";
DROP SEQUENCE IF EXISTS "public"."t_user_id_seq";
DROP SEQUENCE IF EXISTS "public"."t_user_role_id_seq";
DROP TABLE IF EXISTS "public"."t_category";
DROP TABLE IF EXISTS "public"."t_category_attribute";
DROP TABLE IF EXISTS "public"."t_category_attribute_value";
DROP TABLE IF EXISTS "public"."t_dept";
DROP TABLE IF EXISTS "public"."t_permission";
DROP TABLE IF EXISTS "public"."t_role";
DROP TABLE IF EXISTS "public"."t_role_permission";
DROP TABLE IF EXISTS "public"."t_user";
DROP TABLE IF EXISTS "public"."t_user_role";
CREATE SEQUENCE "t_category_attribute_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
CREATE SEQUENCE "t_category_attribute_value_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
CREATE SEQUENCE "t_category_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
CREATE SEQUENCE "t_dept_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
CREATE SEQUENCE "t_login_log_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
CREATE SEQUENCE "t_operate_log_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
CREATE SEQUENCE "t_permission_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
CREATE SEQUENCE "t_role_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
CREATE SEQUENCE "t_role_permission_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
CREATE SEQUENCE "t_user_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
CREATE SEQUENCE "t_user_role_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
CREATE TABLE "t_category" (
  "id" int8 NOT NULL DEFAULT nextval('t_category_id_seq'::regclass),
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "parent_id" int8,
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "sort" int8,
  "status_flag" bool,
  "sub_flag" bool
)
;
ALTER TABLE "t_category" OWNER TO "postgres";
CREATE TABLE "t_category_attribute" (
  "id" int8 NOT NULL DEFAULT nextval('t_category_attribute_id_seq'::regclass),
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "category_id" int8 NOT NULL,
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "form_type" int4,
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "remark" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "t_category_attribute" OWNER TO "postgres";
CREATE TABLE "t_category_attribute_value" (
  "id" int8 NOT NULL DEFAULT nextval('t_category_attribute_value_id_seq'::regclass),
  "attribute_id" int8,
  "sort" int8,
  "value" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "t_category_attribute_value" OWNER TO "postgres";
CREATE TABLE "t_dept" (
  "id" int8 NOT NULL DEFAULT nextval('t_dept_id_seq'::regclass),
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "dept_name" varchar(255) COLLATE "pg_catalog"."default",
  "parent_id" int8,
  "sort" int4 NOT NULL
)
;
ALTER TABLE "t_dept" OWNER TO "postgres";
CREATE TABLE "t_permission" (
  "id" int8 NOT NULL DEFAULT nextval('t_permission_id_seq'::regclass),
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "component" varchar(127) COLLATE "pg_catalog"."default",
  "icon" varchar(32) COLLATE "pg_catalog"."default",
  "keep_alive" bool NOT NULL,
  "name" varchar(127) COLLATE "pg_catalog"."default" NOT NULL,
  "parent_id" int8,
  "path" varchar(2048) COLLATE "pg_catalog"."default" NOT NULL,
  "redirect" varchar(2048) COLLATE "pg_catalog"."default",
  "sort" int4 NOT NULL,
  "title" varchar(127) COLLATE "pg_catalog"."default" NOT NULL,
  "type" int4 NOT NULL
)
;
ALTER TABLE "t_permission" OWNER TO "postgres";
CREATE TABLE "t_role" (
  "id" int8 NOT NULL DEFAULT nextval('t_role_id_seq'::regclass),
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "code" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "description" varchar(127) COLLATE "pg_catalog"."default",
  "name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "t_role" OWNER TO "postgres";
CREATE TABLE "t_role_permission" (
  "id" int8 NOT NULL DEFAULT nextval('t_role_permission_id_seq'::regclass),
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "permission_id" int8 NOT NULL,
  "role_id" int8 NOT NULL
)
;
ALTER TABLE "t_role_permission" OWNER TO "postgres";
CREATE TABLE "t_user" (
  "id" int8 NOT NULL DEFAULT nextval('t_user_id_seq'::regclass),
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "administrator_flag" bool,
  "avatar" varchar(1023) COLLATE "pg_catalog"."default",
  "deleted_flag" bool,
  "department_id" int8,
  "description" varchar(127) COLLATE "pg_catalog"."default",
  "email" varchar(127) COLLATE "pg_catalog"."default",
  "nickname" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "password" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "status" int4,
  "username" varchar(50) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "t_user" OWNER TO "postgres";
CREATE TABLE "t_user_role" (
  "id" int8 NOT NULL DEFAULT nextval('t_user_role_id_seq'::regclass),
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "role_id" int8 NOT NULL,
  "user_id" int8 NOT NULL
)
;
ALTER TABLE "t_user_role" OWNER TO "postgres";
BEGIN;
LOCK TABLE "public"."t_category" IN SHARE MODE;
DELETE FROM "public"."t_category";
INSERT INTO "public"."t_category" ("id","create_time","update_time","code","name","parent_id","remark","sort","status_flag","sub_flag") VALUES (2, '2023-07-11 17:48:17.791339', '2023-07-11 17:48:17.791339', '1002', '男装', NULL, NULL, 2, 't', 'f'),(1, NULL, '2023-07-11 17:49:01.969288', '2000', '手机', NULL, NULL, 1, 't', 'f'),(3, NULL, '2023-07-11 17:51:07.233845', '2001', 'T恤', 1, NULL, NULL, 't', 't'),(4, '2023-07-11 17:54:43.604207', '2023-07-11 17:54:43.604207', '2002', '卫衣', 1, NULL, 2, 't', 't')
;
COMMIT;
BEGIN;
LOCK TABLE "public"."t_category_attribute" IN SHARE MODE;
DELETE FROM "public"."t_category_attribute";
INSERT INTO "public"."t_category_attribute" ("id","create_time","update_time","category_id","code","form_type","name","remark") VALUES (1, '2023-07-11 17:51:07.124759', '2023-07-11 17:51:07.124759', 3, 'size', 1, '尺码', NULL),(2, '2023-07-11 17:54:43.630653', '2023-07-11 17:54:43.630653', 4, 'size', 1, '尺码', NULL)
;
COMMIT;
BEGIN;
LOCK TABLE "public"."t_category_attribute_value" IN SHARE MODE;
DELETE FROM "public"."t_category_attribute_value";
INSERT INTO "public"."t_category_attribute_value" ("id","attribute_id","sort","value") VALUES (1, 1, 0, 'S'),(2, 1, 1, 'M'),(3, 1, 2, 'L'),(4, 2, 0, 'S'),(5, 2, 1, 'M'),(6, 2, 2, 'L')
;
COMMIT;
BEGIN;
LOCK TABLE "public"."t_dept" IN SHARE MODE;
DELETE FROM "public"."t_dept";
INSERT INTO "public"."t_dept" ("id","create_time","update_time","dept_name","parent_id","sort") VALUES (1, NULL, '2023-05-05 17:09:23.052049', 'Koodar集团', NULL, 0),(2, '2023-05-05 17:10:49.12262', '2023-05-05 17:10:49.12262', '技术部门', 1, 1)
;
COMMIT;
BEGIN;
LOCK TABLE "public"."t_permission" IN SHARE MODE;
DELETE FROM "public"."t_permission";
INSERT INTO "public"."t_permission" ("id","create_time","update_time","component","icon","keep_alive","name","parent_id","path","redirect","sort","title","type") VALUES (3, '2022-11-03 12:52:53.594', '2022-11-03 12:52:53.594', 'LAYOUT', 'SecurityScanOutlined', 't', 'auth', 0, '/auth', '/auth/user', 2, '权限管理', 1),(5, '2022-11-03 12:52:53.806', '2022-11-03 12:52:53.806', '/system/role/role', NULL, 't', 'system_role', 3, 'role', NULL, 0, '角色管理', 2),(7, '2022-11-03 12:52:53.806', '2022-11-03 12:52:53.806', '/system/menu/menu', NULL, 't', 'system_menu', 6, 'menu', NULL, 0, '菜单权限', 2),(8, '2022-11-03 12:52:53.806', '2022-11-03 12:52:53.806', NULL, NULL, 't', 'system_user_list', 4, '/user/list', NULL, 0, '用户列表', 3),(9, '2022-11-03 12:52:53.806', '2022-11-03 12:52:53.806', NULL, NULL, 't', 'system_user_upd', 4, '/user/update', NULL, 0, '修改用户', 3),(11, '2022-11-03 12:52:53.806', '2022-11-03 12:52:53.806', NULL, NULL, 't', 'system_user_add', 4, '/user/add', NULL, 0, '添加用户', 3),(13, '2022-11-03 12:52:53.806', '2022-11-03 12:52:53.806', NULL, NULL, 't', 'system_role_upd', 5, '/role/update', NULL, 0, '修改角色', 3),(14, '2022-11-03 12:52:53.806', '2022-11-03 12:52:53.806', NULL, NULL, 't', 'system_role_del', 5, '/role/delete', NULL, 0, '删除角色', 3),(15, '2022-11-03 12:52:53.806', '2022-11-03 12:52:53.806', NULL, NULL, 't', 'system_role_add', 5, '/role/add', NULL, 0, '添加角色', 3),(16, '2022-11-03 12:52:53.806', '2022-11-03 12:52:53.806', NULL, NULL, 't', 'system_permission_upd', 7, '/permission/update', NULL, 0, '修改权限', 3),(17, '2022-11-03 12:52:53.806', '2022-11-03 12:52:53.806', NULL, NULL, 't', 'system_permission_del', 7, '/permission/delete', NULL, 0, '删除权限', 3),(18, '2022-11-03 12:52:53.806', '2022-11-03 12:52:53.806', NULL, NULL, 't', 'system_permission_add', 7, '/permission/add', NULL, 0, '添加权限', 3),(2, '2022-11-03 12:52:53.806', '2022-11-03 12:52:53.806', '/system/dashboard/index', NULL, 't', 'dashboard_workplace', 1, 'workplace', NULL, 0, '工作台', 2),(26, '2023-03-09 21:12:23.093', '2023-03-09 21:12:23.093', '', '', 't', 'operate_log_list', 20, '/operateLog/list', '', 1, '操作日志列表', 3),(24, '2023-03-10 15:01:45.196', '2023-03-10 23:46:59.877', '', '', 't', 'login_log_list', 22, '/loginLog/list', '', 1, '登录日志列表', 3),(4, '2022-11-03 12:52:53.806', '2022-11-03 12:52:53.806', '/system/user/user', NULL, 't', 'system_user', 3, 'user', NULL, 0, '用户管理', 2),(29, '2023-03-15 00:44:33.559', '2023-03-15 00:47:17.03', '', '', 't', 'department_add', 32, '/department/add', '', 1, '添加部门', 3),(28, '2023-03-14 23:59:22.718', '2023-03-14 23:59:22.718', '', '', 't', 'department_tree', 32, '/department/tree', '', 1, '部门查询', 3),(31, '2023-03-15 00:44:22.078', '2023-03-15 00:44:22.078', '', '', 't', 'department_delete', 32, '/department/delete', '', 1, '删除部门', 3),(30, '2023-03-15 00:44:22.078', '2023-03-16 17:40:56.773', '', '', 't', 'department_update', 32, '/department/update', '', 1, '修改部门', 3),(32, '2022-11-03 12:52:53.806', '2022-11-03 12:52:53.806', '/system/department/index', NULL, 't', 'system_department', 6, 'department', NULL, 0, '部门管理', 2),(6, '2022-11-03 12:52:53.594', '2022-11-03 12:52:53.594', 'LAYOUT', 'ControlOutlined', 't', 'system', 0, '/system', NULL, 3, '系统管理', 1),(1, '2022-11-03 12:52:53.594', '2022-11-03 12:52:53.594', 'LAYOUT', 'DashboardOutlined', 't', 'dashboard', 0, '/dashboard', NULL, 1, 'Dashboard', 1),(12, NULL, '2023-05-04 23:44:14.223217', NULL, NULL, 't', 'system_role_list', 5, '/role/list', NULL, 0, '角色列表', 3),(22, '2023-03-10 15:01:45.196', '2023-03-10 15:02:32.095', '/system/login-log/loginLog', '', 't', 'login_log', 6, '/system/loginLog', '', 2, '登录日志', 2),(20, '2023-03-10 15:01:45.196', '2023-03-10 15:01:45.196', '/system/operate-log/operateLog', '', 't', 'operate_log', 6, '/system/operateLog', '', 1, '请求监控', 2),(33, NULL, '2023-05-07 22:43:30.340578', '', '', 't', 'system_user_res_password', 4, '/user/update/password/password/reset', '', 1, '重置密码', 3),(10, NULL, '2023-05-08 22:54:40.362885', NULL, NULL, 't', 'system_user_dis', 4, '/user/delete', NULL, 0, '禁用用户', 3),(34, NULL, '2023-05-09 23:44:32.600364', '', '', 't', 'system_user_batch_del', 4, '/user/batch/delete/*', '', 1, '批量删除用户', 3),(69, NULL, '2023-07-10 21:48:04.965421', 'LAYOUT', 'ApartmentOutlined', 't', 'goods', NULL, '/goods', '', 3, '商品管理', 1),(70, NULL, '2023-07-11 16:50:26.491762', '/business/goods/category/index', '', 't', 'goods_category', 69, 'category', '', 1, '商品分类', 2),(71, NULL, '2023-07-11 16:54:57.257187', '', '', 'f', 'goods_category_add', 70, '/goods/category/add', '', 1, '添加分类', 3),(75, NULL, '2023-07-11 16:55:01.04525', '', '', 'f', 'goods_category_delete', 70, '/goods/category/delete/*', '', 1, '删除分类', 3),(76, NULL, '2023-07-11 16:55:08.650073', '', '', 'f', 'goods_category_disable', 70, '/goods/category/disable/*', '', 1, '禁用分类', 3),(74, NULL, '2023-07-11 16:55:13.551298', '', '', 'f', 'goods_category_update', 70, '/goods/category/update', '', 1, '修改分类', 3)
;
COMMIT;
BEGIN;
LOCK TABLE "public"."t_role" IN SHARE MODE;
DELETE FROM "public"."t_role";
INSERT INTO "public"."t_role" ("id","create_time","update_time","code","description","name") VALUES (1, '2023-04-26 18:05:43.636', '2023-04-26 18:05:43.636', 'super-role', '超级管理员', 'super-role'),(2, '2023-05-04 23:28:54.181194', '2023-05-04 23:28:54.181194', 'admin', '管理员角色', '管理员')
;
COMMIT;
BEGIN;
LOCK TABLE "public"."t_role_permission" IN SHARE MODE;
DELETE FROM "public"."t_role_permission";
INSERT INTO "public"."t_role_permission" ("id","create_time","update_time","permission_id","role_id") VALUES (12, '2023-05-04 23:40:17.354245', '2023-05-04 23:40:17.354245', 3, 2),(13, '2023-05-04 23:40:17.376078', '2023-05-04 23:40:17.376078', 5, 2),(14, '2023-05-04 23:40:17.379507', '2023-05-04 23:40:17.379507', 7, 2),(15, '2023-05-04 23:40:17.383039', '2023-05-04 23:40:17.383039', 8, 2),(16, '2023-05-04 23:40:17.387161', '2023-05-04 23:40:17.387161', 9, 2),(17, '2023-05-04 23:40:17.391531', '2023-05-04 23:40:17.391531', 10, 2),(18, '2023-05-04 23:40:17.395025', '2023-05-04 23:40:17.395025', 11, 2),(19, '2023-05-04 23:40:17.399643', '2023-05-04 23:40:17.399643', 12, 2),(20, '2023-05-04 23:40:17.403174', '2023-05-04 23:40:17.403174', 13, 2),(21, '2023-05-04 23:40:17.406791', '2023-05-04 23:40:17.406791', 14, 2),(22, '2023-05-04 23:40:17.410306', '2023-05-04 23:40:17.410306', 15, 2),(23, '2023-05-04 23:40:17.414401', '2023-05-04 23:40:17.414401', 16, 2),(24, '2023-05-04 23:40:17.417873', '2023-05-04 23:40:17.417873', 17, 2),(25, '2023-05-04 23:40:17.420947', '2023-05-04 23:40:17.420947', 18, 2),(26, '2023-05-04 23:40:17.424343', '2023-05-04 23:40:17.424343', 22, 2),(27, '2023-05-04 23:40:17.428453', '2023-05-04 23:40:17.428453', 26, 2),(28, '2023-05-04 23:40:17.431994', '2023-05-04 23:40:17.431994', 24, 2),(29, '2023-05-04 23:40:17.435479', '2023-05-04 23:40:17.435479', 4, 2),(30, '2023-05-04 23:40:17.438384', '2023-05-04 23:40:17.438384', 29, 2),(31, '2023-05-04 23:40:17.441849', '2023-05-04 23:40:17.441849', 28, 2),(32, '2023-05-04 23:40:17.445343', '2023-05-04 23:40:17.445343', 31, 2),(33, '2023-05-04 23:40:17.448244', '2023-05-04 23:40:17.448244', 30, 2),(34, '2023-05-04 23:40:17.452978', '2023-05-04 23:40:17.452978', 32, 2),(35, '2023-05-04 23:40:17.456424', '2023-05-04 23:40:17.456424', 20, 2),(36, '2023-05-04 23:40:17.459928', '2023-05-04 23:40:17.459928', 6, 2)
;
COMMIT;
BEGIN;
LOCK TABLE "public"."t_user" IN SHARE MODE;
DELETE FROM "public"."t_user";
INSERT INTO "public"."t_user" ("id","create_time","update_time","administrator_flag","avatar","deleted_flag","department_id","description","email","nickname","password","status","username") VALUES (1, '2023-04-26 18:05:43.636', '2023-05-05 18:58:59.166838', 't', '', 'f', 1, '', 'hello@koodar.net', 'admin', '$2a$10$QM9UcvitZOO6H.qs2oe7ielcsVuYdJJkJNWG6NxYAaBXRCYgE6TXC', 1, 'admin'),(2, '2023-04-30 09:35:10.141993', '2023-05-08 22:15:53.585876', 'f', '', 'f', NULL, '', 'hello@koodar.net', 'user', '$2a$10$bXh5L6AaoVjDcGu6NAJaNOcVcSBk8PIZiyRNvG2UTj8QKt.Bk7EGm', 1, 'user')
;
COMMIT;
BEGIN;
LOCK TABLE "public"."t_user_role" IN SHARE MODE;
DELETE FROM "public"."t_user_role";
INSERT INTO "public"."t_user_role" ("id","create_time","update_time","role_id","user_id") VALUES (5, '2023-05-05 18:58:53.866838', '2023-05-05 18:58:53.866838', 2, 2),(6, '2023-05-05 18:58:59.178149', '2023-05-05 18:58:59.178149', 1, 1)
;
COMMIT;
ALTER TABLE "t_category" ADD CONSTRAINT "t_category_pkey" PRIMARY KEY ("id");
ALTER TABLE "t_category_attribute" ADD CONSTRAINT "t_category_attribute_pkey" PRIMARY KEY ("id");
ALTER TABLE "t_category_attribute_value" ADD CONSTRAINT "t_category_attribute_value_pkey" PRIMARY KEY ("id");
ALTER TABLE "t_dept" ADD CONSTRAINT "t_dept_pkey" PRIMARY KEY ("id");
ALTER TABLE "t_permission" ADD CONSTRAINT "t_permission_pkey" PRIMARY KEY ("id");
ALTER TABLE "t_role" ADD CONSTRAINT "t_role_pkey" PRIMARY KEY ("id");
ALTER TABLE "t_role_permission" ADD CONSTRAINT "t_role_permission_pkey" PRIMARY KEY ("id");
ALTER TABLE "t_user" ADD CONSTRAINT "t_user_pkey" PRIMARY KEY ("id");
ALTER TABLE "t_user_role" ADD CONSTRAINT "t_user_role_pkey" PRIMARY KEY ("id");
ALTER TABLE "t_role_permission" ADD CONSTRAINT "fk90j038mnbnthgkc17mqnoilu9" FOREIGN KEY ("role_id") REFERENCES "public"."t_role" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "t_role_permission" ADD CONSTRAINT "fkjobmrl6dorhlfite4u34hciik" FOREIGN KEY ("permission_id") REFERENCES "public"."t_permission" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "t_user_role" ADD CONSTRAINT "fka9c8iiy6ut0gnx491fqx4pxam" FOREIGN KEY ("role_id") REFERENCES "public"."t_role" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "t_user_role" ADD CONSTRAINT "fkq5un6x7ecoef5w1n39cop66kl" FOREIGN KEY ("user_id") REFERENCES "public"."t_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER SEQUENCE "t_category_attribute_id_seq"
OWNED BY "t_category_attribute"."id";
SELECT setval('"t_category_attribute_id_seq"', 34, true);
ALTER SEQUENCE "t_category_attribute_id_seq" OWNER TO "postgres";
ALTER SEQUENCE "t_category_attribute_value_id_seq"
OWNED BY "t_category_attribute_value"."id";
SELECT setval('"t_category_attribute_value_id_seq"', 36, true);
ALTER SEQUENCE "t_category_attribute_value_id_seq" OWNER TO "postgres";
ALTER SEQUENCE "t_category_id_seq"
OWNED BY "t_category"."id";
SELECT setval('"t_category_id_seq"', 36, true);
ALTER SEQUENCE "t_category_id_seq" OWNER TO "postgres";
ALTER SEQUENCE "t_dept_id_seq"
OWNED BY "t_dept"."id";
SELECT setval('"t_dept_id_seq"', 2, true);
ALTER SEQUENCE "t_dept_id_seq" OWNER TO "postgres";
ALTER SEQUENCE "t_login_log_id_seq"
OWNED BY "t_login_log"."id";
SELECT setval('"t_login_log_id_seq"', 78, true);
ALTER SEQUENCE "t_login_log_id_seq" OWNER TO "postgres";
ALTER SEQUENCE "t_operate_log_id_seq"
OWNED BY "t_operate_log"."id";
SELECT setval('"t_operate_log_id_seq"', 618, true);
ALTER SEQUENCE "t_operate_log_id_seq" OWNER TO "postgres";
ALTER SEQUENCE "t_permission_id_seq"
OWNED BY "t_permission"."id";
SELECT setval('"t_permission_id_seq"', 76, true);
ALTER SEQUENCE "t_permission_id_seq" OWNER TO "postgres";
ALTER SEQUENCE "t_role_id_seq"
OWNED BY "t_role"."id";
SELECT setval('"t_role_id_seq"', 2, true);
ALTER SEQUENCE "t_role_id_seq" OWNER TO "postgres";
ALTER SEQUENCE "t_role_permission_id_seq"
OWNED BY "t_role_permission"."id";
SELECT setval('"t_role_permission_id_seq"', 36, true);
ALTER SEQUENCE "t_role_permission_id_seq" OWNER TO "postgres";
ALTER SEQUENCE "t_user_id_seq"
OWNED BY "t_user"."id";
SELECT setval('"t_user_id_seq"', 2, true);
ALTER SEQUENCE "t_user_id_seq" OWNER TO "postgres";
ALTER SEQUENCE "t_user_role_id_seq"
OWNED BY "t_user_role"."id";
SELECT setval('"t_user_role_id_seq"', 6, true);
ALTER SEQUENCE "t_user_role_id_seq" OWNER TO "postgres";
